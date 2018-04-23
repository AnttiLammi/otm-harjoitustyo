package hsbancalculator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.MatchupsDao;
import hsbancalculator.daot.PlayerDao;
import hsbancalculator.sovelluslogiikka.Player;
import hsbancalculator.sovelluslogiikka.Deck;
import hsbancalculator.sovelluslogiikka.Calculator;
import java.util.ArrayList;
import java.util.Scanner;

import java.sql.SQLException;

/**
 *
 * @author antlammi
 */
public class HearthstoneBanCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, Exception {
        // TODO code application logic here
        // lisätty tietokantaan lisääminen, sieltä ei vielä lueta tietoa sovelluslogiikkaa varten
        while (true) {

            Scanner lukija = new Scanner(System.in);
            Database db = new Database("jdbc:sqlite:src/main/resources/db/hsbc.db");
            DeckDao dDao = new DeckDao(db);
            MatchupsDao mDao = new MatchupsDao(db);
            PlayerDao pDao = new PlayerDao(db);

            ArrayList<String> pnimet = new ArrayList<>();
            ArrayList<Deck> pelaaja = new ArrayList<>();

            System.out.println("Syötä omat pakat: ");
            for (int i = 1; i <= 4; i++) {
                System.out.print("Pakka " + i + ": ");
                String deck = lukija.nextLine();
                while (deck.isEmpty()) {
                    System.out.println("Pakan nimi puuttui, syötä uusiksi: ");
                    System.out.println("Pakka " + i + ": ");
                    deck = lukija.nextLine();

                }

                while (pnimet.contains(deck)) {
                    System.out.println("Pakka jo olemassa, syötä eri pakka.");
                    System.out.println("Pakka " + i + ": ");
                    deck = lukija.nextLine();
                }
                pnimet.add(deck);
                Deck pakka = new Deck(deck);
                if (dDao.saveOrUpdate(pakka) != null) {
                    System.out.println("Pakka lisätty tietokantaan.");
                }
                pelaaja.add(pakka);

            }

            Player player1 = new Player(pelaaja.get(0), pelaaja.get(1), pelaaja.get(2), pelaaja.get(3));

            System.out.println("");
            System.out.println("--------------------------------------");
            System.out.println("");
            ArrayList<String> p2nimet = new ArrayList<>();
            ArrayList<Deck> pelaaja2 = new ArrayList<>();
            System.out.println("Syötä vastustajan pakat: ");
            for (int i = 1; i <= 4; i++) {
                System.out.print("Pakka " + i + ": ");
                String deck = lukija.nextLine();
                while (deck.isEmpty()) {
                    System.out.println("Pakan nimi puuttui, syötä uusiksi.");
                    System.out.println("Pakka " + i + ": ");
                    deck = lukija.nextLine();

                }
                while (p2nimet.contains(deck)) {
                    System.out.println("Pakka jo olemassa, syötä eri pakka.");
                    System.out.println("Pakka " + i + ": ");
                    deck = lukija.nextLine();
                }

                p2nimet.add(deck);
                Deck pakka = new Deck(deck);
                if (dDao.saveOrUpdate(pakka) != null) {
                    System.out.println("Pakka lisätty tietokantaan.");
                }
                pelaaja2.add(pakka);

            }
            Player player2 = new Player(pelaaja2.get(0), pelaaja2.get(1), pelaaja2.get(2), pelaaja2.get(3));
            System.out.println("Haluatko hyödyntää tietokantaa? (y/n)");
            String syote = lukija.nextLine();
            while (!syote.equals("y") && !syote.equals("n")) {
                System.out.println("Väärä syöte!");
                System.out.println("");
                System.out.println("Haluatko hyödyntää tietokantaa? (y/n)");

            }
            if (!syote.equals("y")) {
                System.out.println("");
                System.out.println("-----------------------------------------");
                System.out.println("");

                System.out.println("Syötä pakkojen winratet kokonaisen prosentin tarkkuudella kokonaislukuna (muodossa a vastaan b: 57): ");
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        System.out.println(pelaaja.get(i) + " vastaan " + pelaaja2.get(j) + ": ");

                        Integer winrateInt = lukija.nextInt();
                        Double winrate = 1.0 * winrateInt / 100;
                        while (winrate < 0 || winrate > 1) {
                            System.out.println("Väärä arvo, syötä uudelleen.");
                            System.out.println(pelaaja.get(i) + " vastaan " + pelaaja2.get(j) + ": ");
                            winrateInt = lukija.nextInt();
                            winrate = 1.0 * winrateInt / 100;

                        }
                        System.out.println("");
                        player1.setWinrate(pelaaja.get(i), pelaaja2.get(j), winrate);

                        Double p2wr = 1.0 - winrate;
                        Double p2wrRounded = (double) Math.round(p2wr * 100) / 100;
                        player2.setWinrate(pelaaja2.get(j), pelaaja.get(i), p2wrRounded);

                    }
                }
            } else {

                System.out.println("Jos pakkojen välinen matchup puuttuu tietokannasta, syötä se kokonaisen prosentin tarkkuudella kokonaislukuna.");
                for (int i = 0; i < pelaaja.size(); i++) {
                    Deck d1 = pelaaja.get(i);
                    Integer d1id = dDao.findIDByName(d1.name);
                    for (int j = 0; j < pelaaja2.size(); j++) {
                        Deck d2 = pelaaja2.get(j);
                        Integer d2id = dDao.findIDByName(d2.name);

                        if (mDao.findOne(d1id, d2id) == null) {
                            System.out.println("Pakkojen välinen matchup puuttui tietokannasta.");
                            System.out.println("");
                            System.out.println("Syötä pakan " + d1.name + " winrate pakkaa " + d2.name + " vastaan: ");
                            Integer winrateInt = lukija.nextInt();
                            Double winrate = 1.0 * winrateInt / 100;
                            while (winrate < 0 || winrate > 1) {
                                System.out.println("Väärä arvo, syötä uudelleen.");
                                System.out.println("Syötä pakan + " + d1.name + "winrate pakkaa " + d2.name + " vastaan: ");
                                winrateInt = lukija.nextInt();
                                winrate = 1.0 * winrateInt / 100;

                            }
                            Double p2wr = 1.0 - winrate;
                            Double p2wrRounded = (double) Math.round(p2wr * 100) / 100;

                            mDao.saveOrUpdate(d1id, d2id, winrate);
                            mDao.saveOrUpdate(d2id, d1id, p2wrRounded);
                            pelaaja.get(i).setWinrate(d2, mDao.findOne(d1id, d2id));
                            pelaaja2.get(j).setWinrate(d1, mDao.findOne(d2id, d1id));
                        } /*else if (mDao.findOne(d2id, d1id) == null) {
                            System.out.println("Pakkojen välinen matchup puuttui tietokannasta.");
                            System.out.println("");
                            System.out.println("Syötä pakan " + d2.name + " winrate pakkaa " + d1.name + " vastaan: ");
                            Integer winrateInt = lukija.nextInt();
                            Double winrate = 1.0 * winrateInt / 100;
                            while (winrate < 0 ||winrate > 1){
                                System.out.println("Väärä arvo, syötä uudelleen.");
                                System.out.println("Syötä pakan " + d2.name + " winrate pakkaa " + d1.name + " vastaan: ");
                                winrateInt = lukija.nextInt();
                                winrate = 1.0*winrateInt/100;
                            }
                            
                        }*/ else {
                            pelaaja.get(i).setWinrate(d2, mDao.findOne(d1id, d2id));
                            pelaaja2.get(j).setWinrate(d1, mDao.findOne(d1id, d2id));
                        }

                    }
                }
            }
            System.out.println("Tulostetaan pakkojen voittoprosentit toisiaan vastaan");

            System.out.println("Omat pakat: ");
            System.out.println("");
            player1.printWR();

            System.out.println("");
            System.out.println("------------------------------------------");
            System.out.println("");
            System.out.println("Vastustajan pakat: ");
            System.out.println("");
            player2.printWR();
            lukija.reset();
            Boolean conquest = false;
            System.out.println("");
            System.out.println("Onko pelimuoto Conquest?(y/n)");

            String pelimuoto = "";
            lukija.nextLine();
            while (!pelimuoto.equals("y") && !pelimuoto.equals("n")) {
                pelimuoto = lukija.nextLine();
                if (pelimuoto.equals("y")) {
                    conquest = true;
                    break;
                } else if (pelimuoto.equals("n")) {
                    break;
                } else {

                    System.out.println("Väärä syöte (y/n)");
                    System.out.println("");
                    System.out.println("Onko pelimuoto Conquest?(y/n)");

                }
            }

            Boolean tiedettyBan = false;
            ArrayList<Deck> mahdollinenBan = new ArrayList<>();
            System.out.println("");
            System.out.println("Tiedätkö mitä vastustaja saattaa bännää? (y/n)");
            String bantieto = lukija.nextLine();
            while (bantieto.equals("y")) {
                System.out.println("Minkä pakoista vastustaja bännää?");
                String vastustajanBan = lukija.nextLine();

                while (!pnimet.contains(vastustajanBan)) {
                    System.out.println("");
                    System.out.println("Lineupista ei löydy kyseistä pakkaa, yritä uudelleen: ");
                    System.out.println("Minkä pakoista vastustaja bännää?");
                    vastustajanBan = lukija.nextLine();

                }
                Deck ban = new Deck("");
                for (int i = 0; i < player1.lineup.size(); i++) {
                    if (player1.lineup.get(i).toString().equals(vastustajanBan)) {
                        ban = player1.lineup.get(i);
                    }
                }
                mahdollinenBan.add(ban);
                System.out.println("");
                System.out.println("Onko jokin toinen pakka minkä vastustaja saattaa bännää?(y/n)");
                bantieto = lukija.nextLine();

                while (true) {
                    if (bantieto.equalsIgnoreCase("y") || bantieto.equalsIgnoreCase("n")) {
                        break;
                    }

                    System.out.println("");
                    System.out.println("Väärä syöte!");
                    System.out.println("Onko jokin toinen pakka minkä vastustaja saattaa bännää?(y/n)");
                    bantieto = lukija.nextLine();
                    System.out.println("");
                }

            }

            if (mahdollinenBan.size() > 0) {
                Calculator laskenta = new Calculator(player1, player2);
                if (conquest) {
                    laskenta.parasBanConquestKnownBan(player1, mahdollinenBan);
                } else {
                    laskenta.parasBanLHSKnownBan(player1, mahdollinenBan);
                }
            } else {
                Calculator laskenta = new Calculator(player1, player2);
                if (conquest) {
                    laskenta.parasBanConquest(player1);
                } else {
                    laskenta.parasBanLHS(player1);
                }

            }

            String lopetus = "";
            System.out.println("Tulokset yllä. Haluatko lopettaa ohjelman käytön? (y/n)");
            lopetus = lukija.nextLine();
            while (!lopetus.equals("y") && !lopetus.equals("n")) {
                System.out.println("Väärä syöte!");
                System.out.println("");
                System.out.println("Haluatko lopettaa ohjelman käytön? (y/n)");
                lopetus = lukija.nextLine();

            }
            if (lopetus.equals("y")) {
                break;
            }
        }

    }
}
