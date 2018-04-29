/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.ui;

import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.MatchupsDao;
import hsbancalculator.daot.PlayerDao;
import hsbancalculator.sovelluslogiikka.Deck;
import hsbancalculator.sovelluslogiikka.Player;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventType;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author antlammi
 */
public class JavaFXPlayerDecks {

    private Player player;
    public Stage main;
    public Database db;
    public DeckDao dDao;
    public PlayerDao pDao;
    public MatchupsDao mDao;
    private BorderPane bp;

    public JavaFXPlayerDecks(Stage main, BorderPane bp, Player player) throws ClassNotFoundException, SQLException {
        this.main = main;
        db = new Database("jdbc:sqlite:hsbc.db");
        dDao = new DeckDao(db);
        mDao = new MatchupsDao(db);
        pDao = new PlayerDao(db);
        this.player = player;
        this.bp = bp;
    }

    public Parent getNakyma() throws SQLException, ClassNotFoundException {
        GridPane gp = new GridPane();
        Button topLeftButton = new Button(player.name);
        topLeftButton.setMinWidth(200.0);
        topLeftButton.setMaxWidth(200.0);

        gp.add(topLeftButton, 0, 0);

        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        VBox vb3 = new VBox();
       

        List<Deck> db = this.player.lineup;
        for (int i = 0; i < db.size(); i++) {
            Button b1 = new Button(db.get(i).name);
            b1.setMinWidth(200);
            b1.setMaxWidth(200);
            b1.setOnAction((event) -> {

                try {
                    JavaFXMatchups jfxmu = new JavaFXMatchups(this.main, bp, dDao.findOne(dDao.findIDByName(b1.getText())));
                    bp.setCenter(jfxmu.getNakyma());
                } catch (SQLException ex) {
                    System.out.println(ex);
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                }
            });

            TextField tf = new TextField("replace");
            Button b2 = new Button("submit");
            b2.setMinWidth(100);
            b2.setMaxWidth(100);
            b2.setOnAction((event) -> {
                try {

                    this.pDao.delete(this.pDao.findByName(player.name));
                    final int size = db.size();
                    for (int j=0; j<size; j++){
                        if (db.get(j).name.equals(b1.getText())){
                            db.remove(j);
                            dDao.saveOrUpdate(new Deck(tf.getText()));
                            Deck uusi = dDao.findOne(dDao.findIDByName(tf.getText()));
                            db.add(uusi);
                        }
                    }
                    
                    
                    this.player.lineup = (ArrayList<Deck>) db;
                    pDao.saveOrUpdate(player);
                    bp.setCenter(this.getNakyma());
                    
                } catch (SQLException ex) {
                    Logger.getLogger(JavaFXPlayerDecks.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JavaFXPlayerDecks.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            vb1.getChildren().add(b1);
            vb2.getChildren().add(tf);
            vb3.getChildren().add(b2);
        }
        gp.add(vb1, 0, 1);
        gp.add(vb2, 1, 1);
        gp.add(vb3, 2, 1);
        return gp;
    }
}
