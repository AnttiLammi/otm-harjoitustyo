package hsbancalculator.ui;

import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.MatchupsDao;
import hsbancalculator.daot.PlayerDao;
import hsbancalculator.sovelluslogiikka.Player;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFXSimulation {

    private DeckDao dDao;
    private PlayerDao pDao;
    private Player p1;
    private Player p2;
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

    }

    public Parent getNakyma() throws SQLException, ClassNotFoundException {
        GridPane gp = new GridPane();
        VBox vb1 = new VBox();
        ToggleGroup tg1 = new ToggleGroup();
        List<Player> pelaajat = pDao.findAll();
        for (int i = 0; i < pelaajat.size(); i++) {
            ToggleButton tb = new ToggleButton(pelaajat.get(i).name);
            tb.setToggleGroup(tg1);
            vb1.getChildren().add(tb);
        }
        VBox vb2 = new VBox();
        if (p1 != null) {
            Button uusi = new Button("Pelaaja 1: " + p1.name);
            vb2.getChildren().add(uusi);
        }
        if (p2 != null) {
            Button uusi = new Button("Pelaaja 2: " + p2.name);
            vb2.getChildren().add(uusi);
        }

        gp.add(vb1, 0, 1);
        gp.add(vb2, 1, 1);
        Button selectp1 = new Button("Confirm player 1");
        selectp1.setOnAction((event) -> {
            List vblist = vb1.getChildren();
            for (int i = 0; i < vblist.size(); i++) {
                ToggleButton tb = (ToggleButton) vblist.get(i);

                if (tb.selectedProperty().getValue() == true) {

                    try {
                        this.p1 = pDao.findOne(pDao.findByName(tb.getText()));
                        bp.setCenter(this.getNakyma());
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex);
                    }
                }

            }
        });

        Button selectp2 = new Button("Confirm player 2");
        selectp2.setOnAction((event) -> {
            List vblist = vb1.getChildren();
            for (int i = 0; i < vblist.size(); i++) {
                ToggleButton tb = (ToggleButton) vblist.get(i);

                if (tb.selectedProperty().getValue() == true) {

                    try {
                        this.p2 = pDao.findOne(pDao.findByName(tb.getText()));
                        bp.setCenter(this.getNakyma());
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex);
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
        simuloi.setOnAction((event) -> {
            Label label = new Label("Täytä puuttuvat matchupit");

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
                            TextField tf = new TextField("Winrate vs");
                            Button smb2 = new Button(p2.lineup.get(j).name);
                            Button smb3 = new Button("submit");

                            smb3.setOnAction((smevent) -> {
                                Integer winrateInt = Integer.parseInt(tf.getText());
                                Double winrate = 1.0 * winrateInt / 100;
                                if (winrate < 0 || winrate > 1) {
                                    tf.setText("Väärä syöte, 0-100");
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
                            });
                            smvb1.getChildren().add(smb1);
                            smvb2.getChildren().add(tf);
                            smvb3.getChildren().add(smb2);
                            smvb4.getChildren().add(smb3);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(JavaFXSimulation.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(JavaFXSimulation.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
            gp.add(label, 0, 6);
            gp.add(smvb1, 0, 7);
            gp.add(smvb2, 1, 7);
            gp.add(smvb3, 2, 7);
            gp.add(smvb4, 3, 7);
        });
        gp.add(simuloi, 0, 5);
        return gp;
    }
}
