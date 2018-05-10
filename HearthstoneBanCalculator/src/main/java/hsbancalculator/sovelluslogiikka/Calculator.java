package hsbancalculator.sovelluslogiikka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Ohjelman laskennalliset ominaisuudet, sekä simulaatio.
 *
 * @author antlammi
 */
public class Calculator {

    public Player player1;
    public Player player2;

    private Random rng;

    /**
     * Konstruktori, joka pohjustaa rng muuttujan ja pelaajat 1 ja 2.
     *
     * @param p1 - Player-olio, jonka perspektiivistä simulaatio tehdään
     * @param p2 - Player-olio, joka on p1:n vastsutaja
     */
    public Calculator(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;

        this.rng = new Random();

    }

    public void setRandom(Random random) {
        this.rng = random;
    }

    public Random getRandom() {
        return this.rng;
    }

    /**
     *
     * @param p1banlist - Lista pakoista, joita simulaatiossa pelaajalta 1
     * saatetaan ban.
     * @param conquest - Formaattia vastaava boolean arvo. true jos formaatti =
     * conquest, false jos formaatti = LHS.
     * @return Palauttaa simulaation tuloksena saadun HashMap<Deck, Double>,
     * jossa Deck on avain ja Double voittoprosentti kun kyseinen deck on
     * banned.
     */
    public HashMap<Deck, Double> calculateBan(ArrayList<Deck> p1banlist, Boolean conquest) {

        ArrayList<Deck> p1 = player1.lineup;
        ArrayList<Deck> p2 = player2.lineup;

        HashMap<Deck, Double> simresult = new HashMap<>();
        HashMap<Deck, Integer> simwins = new HashMap<>();
        HashMap<Deck, Integer> simhelp = new HashMap<>();

        Integer simkierrokset = 1000000;
        for (int i = 0; i <= simkierrokset; i++) {
            Deck p1ban;
            if (p1banlist.isEmpty()) {
                p1ban = p1.get(rng.nextInt(4));
            } else {
                p1ban = p1banlist.get(rng.nextInt(p1banlist.size()));
            }
            Deck p2ban = p2.get(rng.nextInt(4));

            simhelp.putIfAbsent(p2ban, 0);
            simhelp.put(p2ban, simhelp.get(p2ban) + 1);
            Player winner;
            if (conquest) {
                winner = simulateConquest(p1ban, p2ban);
            } else {
                winner = simulateLHS(p1ban, p2ban);
            }
            if (winner == player1) {
                simwins.putIfAbsent(p2ban, 0);
                simwins.put(p2ban, simwins.get(p2ban) + 1);
            }
        }

        ArrayList<Deck> line = new ArrayList<>();
        simhelp.keySet().forEach(s -> line.add(s));

        for (int i = 0; i < line.size(); i++) {
            Deck deck = line.get(i);
            simresult.put(deck, 1.0 * simwins.get(deck) / simhelp.get(deck));

        }
        return simresult;
    }

    /**
     * Simuloi yksittäisen Conquest ottelun, palauttaa voittaneen pelaajan.
     *
     * @param p1bannedclass - Deck, joka pelaajalta 1 bannittiin tässä ottelussa
     * @param p2bannedclass - Deck, joka pelaajalta 2 bannittiin tässä ottelussa
     * @return
     */
    public Player simulateConquest(Deck p1bannedclass, Deck p2bannedclass) {
        ArrayList<Deck> p1line = new ArrayList<>();
        for (int i = 0; i < player1.lineup.size(); i++) {
            if (!player1.lineup.get(i).equals(p1bannedclass)) {
                p1line.add(player1.lineup.get(i));
            }
        }
        ArrayList<Deck> p2line = new ArrayList<>();
        for (int i = 0; i < player2.lineup.size(); i++) {
            if (!player2.lineup.get(i).equals(p2bannedclass)) {
                p2line.add(player2.lineup.get(i));
            }
        }
        Integer p1wins = 0;
        Integer p2wins = 0;

        while (p1wins < 3 && p2wins < 3) {
            Deck p1deck = p1line.get(rng.nextInt(3 - p1wins));

            Deck p2deck = p2line.get(rng.nextInt(3 - p2wins));
            Double matchup = p1deck.getWinrate(p2deck);
            Double outcome = rng.nextDouble();
            if (outcome < matchup) {
                p1wins++;
                p1line.remove(p1deck);
            } else {
                p2wins++;
                p2line.remove(p2deck);
            }

        }

        if (p1wins > p2wins) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * Simuloi yksittäisen LHS-ottelun, palauttaa voittaneen pelaajan.
     *
     * @param p1bannedclass - Deck, joka pelaajalta 1 bannittiin tässä
     * ottelussa.
     * @param p2bannedclass - Deck, joka pelaajalta 2 bannittiin tässä
     * ottelussa.
     * @return
     */
    public Player simulateLHS(Deck p1bannedclass, Deck p2bannedclass) {
        ArrayList<Deck> p1line = new ArrayList<>();
        for (int i = 0; i < player1.lineup.size(); i++) {
            if (!player1.lineup.get(i).equals(p1bannedclass)) {
                p1line.add(player1.lineup.get(i));
            }
        }
        ArrayList<Deck> p2line = new ArrayList<>();
        for (int i = 0; i < player2.lineup.size(); i++) {
            if (!player2.lineup.get(i).equals(p2bannedclass)) {
                p2line.add(player2.lineup.get(i));
            }
        }
        Integer p1wins = 0;
        Integer p2wins = 0;

        Boolean p1won = false;
        Boolean p2won = false;

        Deck p1deck = new Deck("");
        Deck p2deck = new Deck("");

        while (p1wins < 3 && p2wins < 3) {
            if (!p1won) {
                p1deck = p1line.get(rng.nextInt(3 - p2wins));
            }
            if (!p2won) {
                p2deck = p2line.get(rng.nextInt(3 - p1wins));
            }
            Double matchup = p1deck.getWinrate(p2deck);
            Double outcome = rng.nextDouble();
            if (outcome < matchup) {
                p1wins++;
                p2line.remove(p2deck);
                p1won = true;
                p2won = false;

            } else {
                p2wins++;
                p1line.remove(p1deck);
                p1won = false;
                p2won = true;
            }

        }

        if (p1wins > p2wins) {
            return player1;
        } else {
            return player2;
        }
    }
}
