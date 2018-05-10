/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.ui;

import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.PlayerDao;
import hsbancalculator.sovelluslogiikka.Deck;
import hsbancalculator.sovelluslogiikka.Player;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Sovelluksen päänäkymä, jossa lisätään pakkoja ja pelaajia.
 *
 * @author antlammi
 */
public final class JavaFXMain {

    public Stage main;
    public Database db;
    public DeckDao dDao;
    public PlayerDao pDao;
    private BorderPane bp;
    private ArrayList<Deck> line1;
    private ArrayList<Deck> line2;
    private String p1n;
    private String p2n;

    public JavaFXMain(Stage main, BorderPane bp) throws ClassNotFoundException, SQLException {
        this.main = main;
        db = new Database("jdbc:sqlite:hsbc.db");
        dDao = new DeckDao(db);
        pDao = new PlayerDao(db);
        line1 = new ArrayList<>();
        line2 = new ArrayList<>();
        this.bp = bp;
    }

    /**
     * Palauttaa näkymän, jossa voidaan lisätä kahdelle pelaajalle pakat
     * tietokantaan, sekä tallettaa samalla nämä pelaajat.
     *
     * @return Parent-olio, joka sisältää näkymän
     */
    public Parent getView() {
        Label errorMSG = new Label();
        errorMSG.setText("");
        errorMSG.setTextFill(Color.RED);
        bp.setBottom(errorMSG);

        GridPane gp = new GridPane();

        TextField deck1 = new TextField();
        TextField deck2 = new TextField();
        TextField deck3 = new TextField();
        TextField deck4 = new TextField();

        TextField deck5 = new TextField();
        TextField deck6 = new TextField();
        TextField deck7 = new TextField();
        TextField deck8 = new TextField();
        if (!this.line1.isEmpty()) {
            if (this.line1.size() > 0) {
                deck1.setText(line1.get(0).name);
            }
            if (this.line1.size() > 1) {
                deck2.setText(line1.get(1).name);
            }
            if (this.line1.size() > 2) {
                deck3.setText(line1.get(2).name);
            }
            if (this.line1.size() > 3) {
                deck4.setText(line1.get(3).name);
            }
        }
        if (!this.line2.isEmpty()) {
            if (this.line2.size() > 0) {
                deck5.setText(line2.get(0).name);
            }
            if (this.line2.size() > 1) {
                deck6.setText(line2.get(1).name);
            }
            if (this.line2.size() > 2) {
                deck7.setText(line2.get(2).name);
            }
            if (this.line2.size() > 3) {
                deck8.setText(line2.get(3).name);
            }
        }
        Label l1 = new Label("Player 1 decks");
        Label l2 = new Label("Player 2 decks");

        Button omat = new Button("submit decks - player 1");
        omat.setMinWidth(400);
        omat.setMaxWidth(400);
        omat.setOnAction((event) -> {
            try {

                line1.clear();
                if (!deck1.getText().equals("")) {
                    dDao.saveOrUpdate(new Deck(deck1.getText()));
                    line1.add(dDao.findOne(dDao.findIDByName(deck1.getText())));
                } else {
                    errorMSG.setText("Error: Missing deck");
                }

                if (!deck2.getText().equals("")) {
                    dDao.saveOrUpdate(new Deck(deck2.getText()));
                    line1.add(dDao.findOne(dDao.findIDByName(deck2.getText())));
                } else {
                    errorMSG.setText("Error: Missing deck");
                }

                if (!deck3.getText().equals("")) {
                    dDao.saveOrUpdate(new Deck(deck3.getText()));
                    line1.add(dDao.findOne(dDao.findIDByName(deck3.getText())));
                } else {
                    errorMSG.setText("Error: Missing deck");
                }

                if (!deck4.getText().equals("")) {
                    dDao.saveOrUpdate(new Deck(deck4.getText()));
                    line1.add(dDao.findOne(dDao.findIDByName(deck4.getText())));
                } else {
                    errorMSG.setText("Error: Missing deck");
                }

            } catch (SQLException | ClassNotFoundException ex) {
                errorMSG.setText(ex.toString());
            }
        });

        Button vastustajan = new Button("submit decks - player 2");
        vastustajan.setMinWidth(400);
        vastustajan.setMaxWidth(400);
        vastustajan.setOnAction((event) -> {
            try {

                line2.clear();
                if (!deck5.getText().equals("")) {
                    dDao.saveOrUpdate(new Deck(deck5.getText()));
                    line2.add(dDao.findOne(dDao.findIDByName(deck5.getText())));
                } else {
                    errorMSG.setText("Error: Missing deck");
                }

                if (!deck6.getText().equals("")) {
                    dDao.saveOrUpdate(new Deck(deck6.getText()));
                    line2.add(dDao.findOne(dDao.findIDByName(deck6.getText())));
                } else {
                    errorMSG.setText("Error: Missing deck");
                }

                if (!deck7.getText().equals("")) {
                    dDao.saveOrUpdate(new Deck(deck7.getText()));
                    line2.add(dDao.findOne(dDao.findIDByName(deck7.getText())));
                } else {
                    errorMSG.setText("Error: Missing deck");
                }

                if (!deck8.getText().equals("")) {
                    dDao.saveOrUpdate(new Deck(deck8.getText()));
                    line2.add(dDao.findOne(dDao.findIDByName(deck8.getText())));
                } else {
                    errorMSG.setText("Error: Missing deck");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                errorMSG.setText(ex.toString());
            }

        });
        gp.add(l1, 0, 0);
        gp.add(l2, 1, 0);

        gp.add(deck1, 0, 1);
        gp.add(deck2, 0, 2);
        gp.add(deck3, 0, 3);
        gp.add(deck4, 0, 4);

        gp.add(deck5, 1, 1);
        gp.add(deck6, 1, 2);
        gp.add(deck7, 1, 3);
        gp.add(deck8, 1, 4);

        gp.add(omat, 0, 5);
        gp.add(vastustajan, 1, 5);

        TextField p1name = new TextField("Player 1 name");
        TextField p2name = new TextField("Player 2 name");
        if (p1n != null) {
            p1name.setText(p1n);
        }
        if (p2n != null) {
            p2name.setText(p2n);
        }

        gp.add(p1name, 0, 6);
        gp.add(p2name, 1, 6);
        Button p1db = new Button("submit player 1");
        Button p2db = new Button("submit player 2");
        p1db.setMinWidth(400);
        p1db.setMaxWidth(400);

        p2db.setMinWidth(400);
        p2db.setMaxWidth(400);

        p1db.setOnAction((event) -> {
            try {
                Boolean nullDeck = false;
                if (deck1.getText().equals("") || deck2.getText().equals("") || deck3.getText().equals("") || deck4.getText().equals("")) {
                    nullDeck = true;
                }
                if (!nullDeck) {
                    Deck d1 = dDao.findOne(dDao.findIDByName(deck1.getText()));
                    Deck d2 = dDao.findOne(dDao.findIDByName(deck2.getText()));
                    Deck d3 = dDao.findOne(dDao.findIDByName(deck3.getText()));
                    Deck d4 = dDao.findOne(dDao.findIDByName(deck4.getText()));

                    Player p1 = new Player(d1, d2, d3, d4);
                    p1.setName(p1name.getText());
                    this.p1n = p1name.getText();
                    pDao.saveOrUpdate(p1);
                } else {
                    errorMSG.setText("Error: Missing deck or deck missing from database");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                errorMSG.setText(ex.toString());
            }

        });
        p2db.setOnAction((event) -> {
            try {
                Boolean nullDeck = false;
                if (deck5.getText().equals("") || deck6.getText().equals("") || deck7.getText().equals("") || deck8.getText().equals("")) {
                    nullDeck = true;
                }
                if (!nullDeck) {
                    Deck d5 = dDao.findOne(dDao.findIDByName(deck5.getText()));
                    Deck d6 = dDao.findOne(dDao.findIDByName(deck6.getText()));
                    Deck d7 = dDao.findOne(dDao.findIDByName(deck7.getText()));
                    Deck d8 = dDao.findOne(dDao.findIDByName(deck8.getText()));
                    Player p2 = new Player(d5, d6, d7, d8);
                    p2.setName(p2name.getText());
                    this.p2n = p2name.getText();
                    pDao.saveOrUpdate(p2);
                } else {
                    errorMSG.setText("Error: Missing deck or deck missing from database");
                }

            } catch (SQLException | ClassNotFoundException ex) {
                errorMSG.setText(ex.toString());
            }

        });
        gp.add(p1db, 0, 7);
        gp.add(p2db, 1, 7);
        return gp;
    }
}
