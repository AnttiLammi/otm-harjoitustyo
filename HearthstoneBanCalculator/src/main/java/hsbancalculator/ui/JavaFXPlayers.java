/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.ui;

import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.PlayerDao;
import hsbancalculator.sovelluslogiikka.Player;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Pelaajanäkymä, jossa voi tarkastella tietokannasta löytyviä pelaajia ja
 * siirtyä näiden omiin näkymiinsä.
 *
 * @author antlammi
 */
public class JavaFXPlayers {

    private DeckDao dDao;
    private PlayerDao pDao;

    private Database db;
    private BorderPane bp;
    public Stage main;

    public JavaFXPlayers(Stage main, BorderPane bp) throws ClassNotFoundException, SQLException {
        this.main = main;
        db = new Database("jdbc:sqlite:hsbc.db");
        dDao = new DeckDao(db);
        pDao = new PlayerDao(db);
        this.bp = bp;
    }

    /**
     * Luokan näkymä-metodi, nappuloiden avulla siirrytään pelaajakohtaiseen
     * näkymään tai poistetaan pelaaja kokonaan tietokannasta.
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
        VBox vb2 = new VBox();
        List<Player> db = pDao.findAll();

        for (int i = 0; i < db.size(); i++) {
            Button nb1 = new Button(db.get(i).name);
            Button nb2 = new Button("Delete");
            nb1.setMinWidth(200.0);
            nb1.setMaxWidth(200.0);
            nb1.setOnAction((event) -> {
                try {
                    JavaFXPlayerDecks jfxpd = new JavaFXPlayerDecks(this.main, this.bp, pDao.findOne(pDao.findByName(nb1.getText())));
                    bp.setCenter(jfxpd.getView());
                } catch (SQLException | ClassNotFoundException ex) {
                    errorMSG.setText(ex.toString());
                }

            });

            nb2.setMinWidth(100.0);
            nb2.setMaxWidth(100.0);
            nb2.setOnAction((event) -> {
                try {
                    pDao.delete(pDao.findByName(nb1.getText()));
                    bp.setCenter(this.getView());
                } catch (SQLException | ClassNotFoundException ex) {
                    errorMSG.setText(ex.toString());
                }
            });
            vb1.getChildren().add(nb1);
            vb2.getChildren().add(nb2);
        }

        gp.add(vb1, 0, 0);
        gp.add(vb2, 1, 0);
        return gp;
    }
}
