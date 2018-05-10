package hsbancalculator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import hsbancalculator.ui.JavaFXDecks;
import hsbancalculator.ui.JavaFXMain;
import hsbancalculator.ui.JavaFXPlayers;
import hsbancalculator.ui.JavaFXSimulation;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Sovelluksen main-luokka.
 *
 * @author antlammi
 */
public class HearthstoneBanCalculator extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * Käynnistää sovelluksen: pohjustaa eri ui-luokat ja näiden välisen
     * navigaation.
     *
     * @param main
     * @throws Exception
     */
    @Override
    public void start(Stage main) throws Exception {
        main.setTitle("Hearthstone Ban Calculator");
        main.show();
        main.setMinHeight(600);
        main.setMinWidth(800);

        BorderPane bp = new BorderPane();

        HBox menu = new HBox();

        menu.setPadding(new Insets(0, 20, 20, 0));
        menu.setSpacing(0);
        Button mainview = new Button("main");
        Button decks = new Button("decks");
        Button players = new Button("players");
        Button simulation = new Button("simulation");

        menu.getChildren().addAll(mainview, decks, players, simulation);

        bp.setTop(menu);

        JavaFXMain jfxm = new JavaFXMain(main, bp);

        JavaFXDecks jfxd = new JavaFXDecks(main, bp);

        JavaFXPlayers jfxp = new JavaFXPlayers(main, bp);

        JavaFXSimulation jfxs = new JavaFXSimulation(main, bp);

        Label errorMSG = new Label("");
        errorMSG.setTextFill(Color.RED);
        bp.setBottom(errorMSG);
        bp.setCenter(jfxm.getView());
        mainview.setOnAction((event) -> {
            bp.setCenter(jfxm.getView());
        });
        decks.setOnAction((event) -> {
            try {
                jfxs.p1 = null;                 //Varmistetaan, että jos muutetaan tietoja tietokannassa
                jfxs.p2 = null;                 //Simulaatiota ei yritetä ajaa vanhentuneella tiedolla
                bp.setCenter(jfxd.getView());
            } catch (SQLException | ClassNotFoundException ex) {
                errorMSG.setText(ex.toString());
            }
        });
        players.setOnAction((event) -> {

            try {
                jfxs.p1 = null;
                jfxs.p2 = null;
                bp.setCenter(jfxp.getView());
            } catch (SQLException | ClassNotFoundException ex) {
                errorMSG.setText(ex.toString());
            }

        });
        simulation.setOnAction((event) -> {
            try {
                bp.setCenter(jfxs.getView());
            } catch (SQLException | ClassNotFoundException ex) {
                errorMSG.setText(ex.toString());
            }
        });
        Scene scene = new Scene(bp);
        main.setScene(scene);
    }
}
