/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.ui;

import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.sovelluslogiikka.Deck;
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
 * Sovelluksen pakkanäkymä.
 *
 * @author antlammi
 */
public final class JavaFXDecks {

    public Stage main;
    public Database db;
    public DeckDao dDao;
    private BorderPane bp;

    public JavaFXDecks(Stage main, BorderPane bp) throws ClassNotFoundException, SQLException {
        this.main = main;
        this.bp = bp;
        db = new Database("jdbc:sqlite:hsbc.db");
        dDao = new DeckDao(db);

    }

    /**
     * Palauttaa näkymän, jossa on listattuna kaikki tietokannasta löytyvät
     * pakat. Klikkaamalla pakkojen nimiä voi siirtyä niiden Matchup-näkymiin ja
     * delete nappuloilla ne voidaan poistaa.
     *
     * @return Parent - olio, joka sisältää näkymän
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
        List<Deck> db = dDao.findAll();

        for (int i = 0; i < db.size(); i++) {
            Button nb1 = new Button(db.get(i).name);
            Button nb2 = new Button("Delete");
            nb1.setMinWidth(200.0);
            nb1.setMaxWidth(200.0);
            nb1.setOnAction((event) -> {
                try {
                    JavaFXMatchups jfxmu = new JavaFXMatchups(this.main, bp, dDao.findOne(dDao.findIDByName(nb1.getText())));
                    bp.setCenter(jfxmu.getView());
                } catch (SQLException | ClassNotFoundException ex) {
                    errorMSG.setText(ex.toString());
                }

            });

            nb2.setMinWidth(100.0);
            nb2.setMaxWidth(100.0);
            nb2.setOnAction((event) -> {
                try {
                    dDao.delete(dDao.findIDByName(nb1.getText()));
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
