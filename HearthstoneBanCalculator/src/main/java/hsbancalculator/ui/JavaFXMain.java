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
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author antlammi
 */
public final class JavaFXMain {

    public Stage main;
    public Database db;
    public DeckDao dDao;
    private ArrayList<Deck> line1;
    private ArrayList<Deck> line2;

    public JavaFXMain(Stage main) throws ClassNotFoundException, SQLException {
        this.main = main;
        db = new Database("jdbc:sqlite:hsbc.db");
        dDao = new DeckDao(db);
        line1 = new ArrayList<>();
        line2 = new ArrayList<>();
    }

    public Parent getNakyma() {
        GridPane gp = new GridPane();

        TextField deck1 = new TextField();
        TextField deck2 = new TextField();
        TextField deck3 = new TextField();
        TextField deck4 = new TextField();

        TextField deck5 = new TextField();
        TextField deck6 = new TextField();
        TextField deck7 = new TextField();
        TextField deck8 = new TextField();
        Label l1 = new Label("Omat Pakat");
        Label l2 = new Label("Vastustajan Pakat");

        Button omat = new Button("Lisää omat");
        omat.setMinWidth(400);
        omat.setMaxWidth(400);
        omat.setOnAction((event) -> {
            try {
                line1.forEach(s-> System.out.println(s));
                line1.clear();
                
                dDao.saveOrUpdate(new Deck(deck1.getText()));
                line1.add(0, dDao.findOne(dDao.findIDByName(deck1.getText())));

                dDao.saveOrUpdate(new Deck(deck2.getText()));
                line1.add(1, dDao.findOne(dDao.findIDByName(deck2.getText())));
                
                dDao.saveOrUpdate(new Deck(deck3.getText()));
                line1.add(2, dDao.findOne(dDao.findIDByName(deck3.getText())));
                
                dDao.saveOrUpdate(new Deck(deck4.getText()));
                line1.add(3, dDao.findOne(dDao.findIDByName(deck4.getText())));
                
                 line1.forEach(s-> System.out.println(s));
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
        });

        Button vastustajan = new Button("Lisää vastustajan");
        vastustajan.setMinWidth(400);
        vastustajan.setMaxWidth(400);
        vastustajan.setOnAction((event) -> {
            try {
                line2.forEach(s-> System.out.println(s));
                line2.clear();
                dDao.saveOrUpdate(new Deck(deck5.getText()));
                line2.add(0, dDao.findOne(dDao.findIDByName(deck5.getText())));
                
                dDao.saveOrUpdate(new Deck(deck6.getText()));
                line2.add(1, dDao.findOne(dDao.findIDByName(deck6.getText())));
                
                dDao.saveOrUpdate(new Deck(deck7.getText()));
                line2.add(2, dDao.findOne(dDao.findIDByName(deck7.getText())));
                
                dDao.saveOrUpdate(new Deck(deck8.getText()));
                line2.add(3, dDao.findOne(dDao.findIDByName(deck8.getText())));
                line2.forEach(s-> System.out.println(s));
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
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

        return gp;
    }
}
