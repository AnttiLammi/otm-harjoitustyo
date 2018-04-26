/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.ui;

import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.MatchupsDao;
import hsbancalculator.sovelluslogiikka.Deck;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
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

    public Parent getNakyma() throws SQLException, ClassNotFoundException {
        GridPane gp = new GridPane();
        Button topLeftButton = new Button(deck.name + " vs");
        topLeftButton.setMinWidth(200.0);
        topLeftButton.setMaxWidth(200.0);

        gp.add(topLeftButton, 0, 0);

        Button topMidButton = new Button("Winrate (kokonaisen prosentin tarkkuudella)");
        topMidButton.setMinWidth(200.0);
        topMidButton.setMaxWidth(200.0);

        gp.add(topMidButton, 1, 0);
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
                    bp.setCenter(jfxmu.getNakyma());
                } catch (SQLException ex) {
                    System.out.println(ex);
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                }
                
            });
            b2.setOnAction((event) -> {
                Integer syote = Integer.parseInt(tf.getText());
                try {
                    Integer id = dDao.findIDByName(b.getText());
                    Double winrate = 1.0 * syote / 100;
                    if (winrate < 0 || winrate > 1) {
                        tf.setText("Väärä syöte, 0-100");
                    } else {
                        mDao.saveOrUpdate(deck.id, id, winrate);

                        Double p2wr = 1.0 - winrate;
                        Double p2wrRounded = (double) Math.round(p2wr * 100) / 100;
                        mDao.saveOrUpdate(id, deck.id, p2wrRounded);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                }
            });
            vb1.getChildren().add(b);
            vb2.getChildren().add(tf);
            vb3.getChildren().add(b2);
        }

        gp.add(vb1, 0, 1);
        gp.add(vb2, 1, 1);
        gp.add(vb3, 2, 1);
        return gp;
    }
}
