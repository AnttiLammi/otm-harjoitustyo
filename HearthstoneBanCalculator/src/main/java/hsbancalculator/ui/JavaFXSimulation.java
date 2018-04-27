package hsbancalculator.ui;


import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.PlayerDao;
import hsbancalculator.sovelluslogiikka.Deck;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFXSimulation {
    private DeckDao dDao;
    private PlayerDao pDao;
    private ArrayList<Deck> lineup1;
    private ArrayList<Deck> lineup2;
    private Database db;
    public Stage main;
    public JavaFXSimulation(Stage main) throws ClassNotFoundException, SQLException{
        this.main = main;
        db = new Database("jdbc:sqlite:hsbc.db");
        dDao = new DeckDao(db);
        pDao = new PlayerDao(db);
    }
    
    public Parent getNakyma(){
        HBox hb = new HBox();
        hb.getChildren().add(new Text("Added later"));
        return hb;
    }
}