/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.ui;

import com.sun.prism.paint.Color;
import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.MatchupsDao;
import hsbancalculator.sovelluslogiikka.Deck;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Matchup-näkymä, tietylle pakalle listataan kaikki tietokannasta löytyvät
 * matchupit ja niitä voi muokata.
 *
 * @author antlammi
 */
public class JavaFXMatchups {

    private Deck deck;

    public Stage main;
    public Database db;
    public DeckDao dDao;
    public MatchupsDao mDao;
    private BorderPane bp;

    public JavaFXMatchups(Stage main, BorderPane bp, Deck versus) throws ClassNotFoundException, SQLException {
        this.main = main;
        db = new Database("jdbc:sqlite:hsbc.db");
        dDao = new DeckDao(db);
        mDao = new MatchupsDao(db);
        this.deck = versus;
        this.bp = bp;
    }

    /**
     * Palauttaa näkymän, jossa on lista tietokantataulusta löytyvistä pakoista
     * ja tekstikenttä, jossa on tietyn pakan matchup niitä vastaan jos se
     * löytyy, arvoa voi muuttaa. Muiden pakkojen nimistä pystyy navigoimaan
     * niiden matchup-näkymiin.
     *
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public Parent getView() throws SQLException, ClassNotFoundException {
        Label errorMSG = new Label();
        errorMSG.setText("");
        errorMSG.setTextFill(javafx.scene.paint.Color.RED);
        bp.setBottom(errorMSG);
        
        GridPane gp = new GridPane();
        Label topLeftLabel = new Label(deck.name + " vs");

        gp.add(topLeftLabel, 0, 0);

        Label topMidLabel = new Label("Winrate: 0-100");

        gp.add(topMidLabel, 1, 0);
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        VBox vb3 = new VBox();

        List<Deck> db = dDao.findAll();
        for (int i = 0; i < db.size(); i++) {
            Button b = new Button(db.get(i).name);
            TextField tf = new TextField();

            Double mu = mDao.findOne(deck.id, dDao.findIDByName(b.getText()));

            if (mu != null) {
                mu = mu * 100;
                mu = (double) Math.round(mu * 100) / 100;
                Integer muInt = mu.intValue();
                tf.setText(muInt.toString());
            }
            Button b2 = new Button("submit");

            b.setMinWidth(200.0);
            b.setMaxWidth(200.0);

            b2.setMinWidth(100.0);
            b2.setMaxWidth(100.0);

            b.setOnAction((event) -> {
                JavaFXMatchups jfxmu;

                try {
                    jfxmu = new JavaFXMatchups(this.main, bp, dDao.findOne(dDao.findIDByName(b.getText())));
                    bp.setCenter(jfxmu.getView());
                } catch (SQLException | ClassNotFoundException ex) {
                    errorMSG.setText(ex.toString());
                }

            });
            b2.setOnAction((event) -> {
                Boolean valid = true;
                Integer syote = -1;
                try {
                    syote = Integer.parseInt(tf.getText());
                } catch (NumberFormatException n) {
                    errorMSG.setText("Error: Wrong input type");
                    valid = false;
                }
                if (valid) {
                    try {
                        Integer id = dDao.findIDByName(b.getText());
                        Double winrate = 1.0 * syote / 100;
                        if (winrate < 0 || winrate > 1) {
                            errorMSG.setText("Error: Wrong input (0-100)");
                        } else {
                            mDao.saveOrUpdate(deck.id, id, winrate);

                            Double p2wr = 1.0 - winrate;
                            Double p2wrRounded = (double) Math.round(p2wr * 100) / 100;
                            mDao.saveOrUpdate(id, deck.id, p2wrRounded);
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        errorMSG.setText(ex.toString());
                    }
                }
            });
            vb1.getChildren().add(b);
            vb2.getChildren().add(tf);
            vb3.getChildren().add(b2);

        }
        Button submitAll = new Button("submit all");
        submitAll.setMinWidth(100);
        submitAll.setMaxWidth(100);
        submitAll.setOnAction((event) -> {
            List<Node> dList = vb1.getChildren();
            List<Node> tList = vb2.getChildren();
            Integer size = dList.size();
            for (int j = 0; j < size; j++) {
                Button d = (Button) dList.get(j);
                TextField t = (TextField) tList.get(j);
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
                        Integer id = dDao.findIDByName(d.getText());
                        Double winrate = 1.0 * syote / 100;
                        if (winrate < 0 || winrate > 1) {
                            errorMSG.setText("Error: Wrong input (0-100)");
                        } else {
                            mDao.saveOrUpdate(deck.id, id, winrate);

                            Double p2wr = 1.0 - winrate;
                            Double p2wrRounded = (double) Math.round(p2wr * 100) / 100;
                            mDao.saveOrUpdate(id, deck.id, p2wrRounded);
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        errorMSG.setText(ex.toString());
                    }
                }
            }
        });

        gp.add(vb1, 0, 1);
        gp.add(vb2, 1, 1);
        gp.add(vb3, 2, 1);
        gp.add(errorMSG, 1, 2);
        gp.add(submitAll, 2, 2);
        return gp;
    }
}
