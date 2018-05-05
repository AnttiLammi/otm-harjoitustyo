package hsbancalculator.ui;

import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.MatchupsDao;
import hsbancalculator.daot.PlayerDao;
import hsbancalculator.sovelluslogiikka.Calculator;
import hsbancalculator.sovelluslogiikka.Deck;
import hsbancalculator.sovelluslogiikka.Player;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Sovelluksen simulaationäkymä, sisältää kolme erilaista näkymää.
 *
 * @author antlammi
 */
public class JavaFXSimulation {

    private DeckDao dDao;
    private PlayerDao pDao;
    private Player p1;
    private Player p2;
    private Boolean conquest;
    private ArrayList<Deck> vBan;
    private MatchupsDao mDao;
    private Database db;
    private BorderPane bp;
    public Stage main;

    public JavaFXSimulation(Stage main, BorderPane bp) throws ClassNotFoundException, SQLException {
        this.main = main;
        db = new Database("jdbc:sqlite:hsbc.db");
        dDao = new DeckDao(db);
        pDao = new PlayerDao(db);
        mDao = new MatchupsDao(db);
        this.bp = bp;
        vBan = new ArrayList<>();
        this.conquest = true;

    }

    /**
     * Simulaation päänäkymä, jossa valitaan pelaajat ja kilpailuformaatti, sekä
     * täydennetään puuttuvat matchupit.
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Parent getView() throws SQLException, ClassNotFoundException {
        Label errorMSG = new Label();
        errorMSG.setText("");
        errorMSG.setTextFill(Color.RED);
        bp.setBottom(errorMSG);
        GridPane gp = new GridPane();
        VBox vb1 = new VBox();
        ToggleGroup tg1 = new ToggleGroup();
        List<Player> pelaajat = pDao.findAll();
        for (int i = 0; i < pelaajat.size(); i++) {
            ToggleButton tb = new ToggleButton(pelaajat.get(i).name);
            tb.setMinWidth(200);
            tb.setMaxWidth(200);
            tb.setToggleGroup(tg1);
            vb1.getChildren().add(tb);
        }

        VBox vb2 = new VBox();
        if (p1 != null) {
            Button uusi = new Button("Pelaaja 1: " + p1.name);
            uusi.setMinWidth(200);
            uusi.setMaxWidth(200);
            vb2.getChildren().add(uusi);

        }
        if (p2 != null) {
            Button uusi = new Button("Pelaaja 2: " + p2.name);
            uusi.setMinWidth(200);
            uusi.setMaxWidth(200);
            vb2.getChildren().add(uusi);
        }

        gp.add(vb1, 0, 1);
        gp.add(vb2, 1, 1);
        Button selectp1 = new Button("Confirm player 1");
        selectp1.setMinWidth(200);
        selectp1.setMaxWidth(200);

        selectp1.setOnAction((event) -> {

            List vblist = vb1.getChildren();

            for (int i = 0; i < vblist.size(); i++) {
                ToggleButton tb = (ToggleButton) vblist.get(i);

                if (tb.selectedProperty().getValue() == true) {

                    try {
                        this.p1 = pDao.findOne(pDao.findByName(tb.getText()));
                        bp.setCenter(this.getView());
                    } catch (SQLException | ClassNotFoundException ex) {

                        errorMSG.setText(ex.toString());

                    }
                }

            }
        });

        Button selectp2 = new Button("Confirm player 2");
        selectp2.setMinWidth(200);
        selectp2.setMaxWidth(200);
        selectp2.setOnAction((event) -> {

            List vblist = vb1.getChildren();
            for (int i = 0; i < vblist.size(); i++) {
                ToggleButton tb = (ToggleButton) vblist.get(i);

                if (tb.selectedProperty().getValue() == true) {

                    try {
                        this.p2 = pDao.findOne(pDao.findByName(tb.getText()));
                        bp.setCenter(this.getView());
                    } catch (SQLException | ClassNotFoundException ex) {
                        errorMSG.setText(ex.toString());
                    }
                }
            }

        });
        gp.add(new Text(""), 0, 2);

        gp.add(selectp1, 0, 3);
        gp.add(selectp2, 1, 3);
        ToggleGroup tg2 = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Conquest");
        rb1.setToggleGroup(tg2);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("LHS");

        rb2.setToggleGroup(tg2);

        HBox hb = new HBox();
        hb.getChildren().addAll(rb1, rb2);
        gp.add(hb, 0, 4);

        Button simuloi = new Button("simuloi");
        simuloi.setMinWidth(100);
        simuloi.setMaxWidth(100);

        simuloi.setOnAction((event) -> {
            if (p1 != null && p2 != null) {
                this.conquest = rb1.selectedProperty().getValue();
                this.getMissingView();
            } else {

                errorMSG.setText("Error: No players selected.");
            }

        });
        gp.add(simuloi, 0, 5);
        return gp;
    }

    public Parent getMissingView() {
        Label errorMSG = new Label();
        errorMSG.setText("");
        errorMSG.setTextFill(Color.RED);
        bp.setBottom(errorMSG);
        
        GridPane gp = new GridPane();
        this.bp.setCenter(gp);
        Label label = new Label("Insert the missing matchups");
        Label label2 = new Label("Winrate: 0-100");
        VBox smvb1 = new VBox();
        VBox smvb2 = new VBox();
        VBox smvb3 = new VBox();
        VBox smvb4 = new VBox();

        for (int i = 0; i < p1.lineup.size(); i++) {

            for (int j = 0; j < p2.lineup.size(); j++) {
                try {
                    Double matchup = mDao.findOne(dDao.findIDByName(p1.lineup.get(i).name), dDao.findIDByName(p2.lineup.get(j).name));
                    if (matchup == null) {
                        Button smb1 = new Button(p1.lineup.get(i).name);
                        smb1.setMinWidth(200);
                        smb1.setMaxWidth(200);

                        TextField tf = new TextField("");
                        tf.minWidth(200);
                        Button smb2 = new Button(p2.lineup.get(j).name);

                        smb2.setMinWidth(200);
                        smb2.setMaxWidth(200);

                        Button smb3 = new Button("submit");

                        smb3.setMinWidth(100);
                        smb3.setMaxWidth(100);

                        smb3.setOnAction((smevent) -> {

                            Boolean valid = true;
                            Integer winrateInt = -1;
                            try {
                                winrateInt = Integer.parseInt(tf.getText());
                            } catch (NumberFormatException n) {
                                errorMSG.setText("Error: Wrong input type");
                                valid = false;
                            }
                            if (valid) {

                                Double winrate = 1.0 * winrateInt / 100;
                                if (winrate < 0 || winrate > 1) {
                                    errorMSG.setText("Error: Wrong input (0-100)");
                                } else {
                                    try {
                                        mDao.saveOrUpdate(dDao.findIDByName(smb1.getText()), dDao.findIDByName(smb2.getText()), winrate);

                                        Double p2wr = 1.0 - winrate;
                                        Double p2wrRounded = (double) Math.round(p2wr * 100) / 100;
                                        mDao.saveOrUpdate(dDao.findIDByName(smb2.getText()), dDao.findIDByName(smb1.getText()), p2wrRounded);
                                        smvb1.getChildren().remove(smb1);
                                        smvb2.getChildren().remove(tf);
                                        smvb3.getChildren().remove(smb2);
                                        smvb4.getChildren().remove(smb3);

                                    } catch (SQLException ex) {
                                        System.out.println(ex);
                                    } catch (ClassNotFoundException ex) {
                                        System.out.println(ex);
                                    }
                                }
                            }
                        });

                        smvb1.getChildren().add(smb1);
                        smvb2.getChildren().add(tf);
                        smvb3.getChildren().add(smb2);
                        smvb4.getChildren().add(smb3);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(JavaFXSimulation.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        ArrayList<Node> del1 = new ArrayList<>();
        ArrayList<Node> del2 = new ArrayList<>();
        ArrayList<Node> del3 = new ArrayList<>();
        ArrayList<Node> del4 = new ArrayList<>();

        Button submitAll = new Button("submit all");
        submitAll.setMinWidth(100);
        submitAll.setMaxWidth(100);
        submitAll.setOnAction((eventS) -> {
            List<Node> d1List = smvb1.getChildren();
            List<Node> tList = smvb2.getChildren();
            List<Node> d2list = smvb3.getChildren();
            List<Node> sList = smvb4.getChildren();
            Integer size = d1List.size();
            for (int i = 0; i < size; i++) {
                Button d1 = (Button) d1List.get(i);
                TextField t = (TextField) tList.get(i);
                Button d2 = (Button) d2list.get(i);
                Boolean valid = true;
                Integer syote = -1;
                try {
                    syote = Integer.parseInt(t.getText());
                } catch (NumberFormatException n) {
                    errorMSG.setText("Error: Wrong input type");
                    valid = false;
                }
                if (valid) {
                    try {
                        Integer d1id = dDao.findIDByName(d1.getText());
                        Integer d2id = dDao.findIDByName(d2.getText());
                        Double winrate = 1.0 * syote / 100;
                        if (winrate < 0 || winrate > 1) {
                            errorMSG.setText("Error: Wrong input (0-100)");
                        } else {
                            mDao.saveOrUpdate(d1id, d2id, winrate);

                            Double p2wr = 1.0 - winrate;
                            Double p2wrRounded = (double) Math.round(p2wr * 100) / 100;
                            mDao.saveOrUpdate(d2id, d1id, p2wrRounded);

                            del1.add(d1);
                            del2.add(t);
                            del3.add(d2);
                            del4.add(smvb4.getChildren().get(i));

                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        errorMSG.setText(ex.toString());
                    }
                }
            }
            Integer delSize = del1.size();
            for (int i = 0; i < delSize; i++) {
                smvb1.getChildren().remove(del1.get(i));
                smvb2.getChildren().remove(del2.get(i));
                smvb3.getChildren().remove(del3.get(i));
                smvb4.getChildren().remove(del4.get(i));
            }
            if (smvb1.getChildren().isEmpty()) {
                this.getBanView();
            }
        });

        Button simuloi = new Button("simuloi");
        simuloi.setMinWidth(200);
        simuloi.setMaxWidth(200);

        simuloi.setOnAction((transition) -> {
            if (smvb1.getChildren().isEmpty()) {
                this.getBanView();

            } else {
                errorMSG.setText("Error: Missing matchups");
            }
        });
        if (smvb1.getChildren().isEmpty()) {
            this.getBanView();
        }
        gp.add(label, 0, 0);
        gp.add(label2, 1, 0);
        gp.add(smvb1, 0, 1);
        gp.add(smvb2, 1, 1);
        gp.add(smvb3, 2, 1);
        gp.add(smvb4, 3, 1);
        gp.add(errorMSG, 0, 3);
        gp.add(simuloi, 2, 3);
        gp.add(submitAll, 3, 3);
        return gp;
    }

    /**
     * Sivunäkymä, jossa voi halutessaan rajata vastustajan bannia.
     *
     * @return
     */
    public Parent getBanView() {
        Label errorMSG = new Label();
        errorMSG.setText("");
        errorMSG.setTextFill(Color.RED);
        bp.setBottom(errorMSG);
        
        GridPane gp = new GridPane();
        this.bp.setCenter(gp);
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        VBox vb3 = new VBox();

        Button b1 = new Button(this.p1.name + " vs");
        b1.setMinWidth(200);
        b1.setMaxWidth(200);
        Button b2 = new Button(this.p2.name);
        b2.setMinWidth(200);
        b2.setMaxWidth(200);

        Button b3 = new Button("Banned by opponent");
        b3.setMinWidth(200);
        b3.setMaxWidth(200);
        vb1.getChildren().addAll(new Text("Choose your opponent's ban"), new Text(""), b1, new Text(""));
        vb2.getChildren().addAll(new Text(""), new Text(""), b2, new Text(""));
        vb3.getChildren().addAll(new Text(""), new Text(""), b3, new Text(""));

        for (int i = 0; i < vBan.size(); i++) {
            Button banned = new Button(vBan.get(i).name);
            banned.setMinWidth(200);
            banned.setMaxWidth(200);

            banned.setOnAction((event) -> {
                for (int j = 0; j < vBan.size(); j++) {
                    if (vBan.get(j).name.equals(banned.getText())) {
                        vBan.remove(j);
                        this.getBanView();
                    }
                }
            });

            vb3.getChildren().add(banned);
        }
        for (int i = 0; i < p1.lineup.size(); i++) {
            Button d = new Button(p1.lineup.get(i).name);

            d.setMinWidth(200);
            d.setMaxWidth(200);

            Button sel = new Button("submit");

            sel.setMinWidth(200);
            sel.setMaxWidth(200);

            sel.setOnAction((event) -> {

                try {

                    Deck apu = dDao.findOne(dDao.findIDByName(d.getText()));
                    for (int j = 0; j < p1.lineup.size(); j++) {
                        if (p1.lineup.get(j).name.equals(apu.name)) {
                            if (!vBan.contains(p1.lineup.get(j))) {
                                vBan.add(p1.lineup.get(j));

                                this.getBanView();
                            }
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    errorMSG.setText(ex.toString());
                }
            });
            vb1.getChildren().add(d);
            vb2.getChildren().add(sel);
        }

        gp.add(vb1, 0, 0);
        gp.add(vb2, 1, 0);
        gp.add(vb3, 2, 0);
        gp.add(new Text(""), 0, 1);
        gp.add(new Text(""), 1, 1);
        gp.add(new Text(""), 2, 1);

        Button simuloi = new Button("simuloi");
        simuloi.setOnAction((event) -> {
            Calculator calculator = new Calculator(p1, p2);
            try {
                this.getResultView(calculator, this.conquest);
            } catch (SQLException | ClassNotFoundException ex) {
               errorMSG.setText(ex.toString());
            }
        });
        simuloi.setMinWidth(100);
        simuloi.setMaxWidth(100);
        gp.add(simuloi, 0, 1);

        return gp;
    }

    /**
     * Tulosnäkymä, kutsutaan simulaatio-luokkaa ja nähdään simulaation
     * tulokset.
     *
     * @param calculator
     * @param conquest
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Parent getResultView(Calculator calculator, Boolean conquest) throws SQLException, ClassNotFoundException {
        Label errorMSG = new Label();
        errorMSG.setText("");
        errorMSG.setTextFill(Color.RED);
        bp.setBottom(errorMSG);
       
        for (int i = 0; i < p1.lineup.size(); i++) {
            for (int j = 0; j < p2.lineup.size(); j++) {
                Deck d1 = p1.lineup.get(i);
                Deck d2 = p2.lineup.get(j);

                p1.setWinrate(d1, d2, mDao.findOne(dDao.findIDByName(d1.name), dDao.findIDByName(d2.name)));
                p2.setWinrate(d2, d1, mDao.findOne(dDao.findIDByName(d2.name), dDao.findIDByName(d1.name)));
            }
        }
        HashMap<Deck, Double> tulos = new HashMap<>();
        if (this.conquest == true) {
            if (this.vBan.isEmpty()) {
                tulos = calculator.parasBanConquest(p1);
            } else {
                tulos = calculator.parasBanConquest(p1, vBan);
            }
        } else {
            if (this.vBan.isEmpty()) {
                tulos = calculator.parasBanLHS(p1);
            } else {
                tulos = calculator.parasBanLHS(p1, vBan);
            }
        }
        vBan.clear();
        GridPane gp = new GridPane();
        this.bp.setCenter(gp);
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();

        Button p1b = new Button(p1.name + " vs");

        p1b.setMinWidth(200);
        p1b.setMaxWidth(200);

        Button p2b = new Button(p2.name);

        p2b.setMinWidth(200);
        p2b.setMaxWidth(200);

        Button ban = new Button("Ban");

        ban.setMinWidth(200);
        ban.setMaxWidth(200);

        Button wr = new Button("Winrate");

        wr.setMinWidth(200);
        wr.setMaxWidth(200);

        vb1.getChildren().addAll(p1b, ban);
        vb2.getChildren().addAll(p2b, wr);

        for (int i = 0; i < p2.lineup.size(); i++) {
            Deck d = p2.lineup.get(i);
            Button b = new Button(d.name);

            b.setMinWidth(200);
            b.setMaxWidth(200);

            Button r = new Button(tulos.get(d).toString());

            r.setMinWidth(200);
            r.setMaxWidth(200);

            vb1.getChildren().add(b);
            vb2.getChildren().add(r);

        }
        gp.add(vb1, 0, 0);
        gp.add(vb2, 1, 0);

        return gp;
    }
}
