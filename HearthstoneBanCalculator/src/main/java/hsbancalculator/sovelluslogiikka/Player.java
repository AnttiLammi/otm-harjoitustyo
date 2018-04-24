package hsbancalculator.sovelluslogiikka;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import hsbancalculator.sovelluslogiikka.Deck;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author antlammi
 */
public class Player {

    public ArrayList<Deck> lineup;
    public Integer id;
    public String name;

    public Player(Deck deck1, Deck deck2, Deck deck3, Deck deck4) {
        lineup = new ArrayList<>();

        lineup.add(deck1);
        lineup.add(deck2);
        lineup.add(deck3);
        lineup.add(deck4);

    }

    public void setWinrate(Deck deck1, Deck deck2, Double winrate) {
        for (int i = 0; i < lineup.size(); i++) {
            if (lineup.get(i).name.equals(deck1.name)) {
                lineup.get(i).setWinrate(deck2, winrate);
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public void printWR() {
        for (int i = 0; i < lineup.size(); i++) {
            Deck pakka = lineup.get(i);
            HashMap<Deck, Double> matchupit = pakka.getAllWR();
            ArrayList<Deck> keyset = new ArrayList<>();
            matchupit.keySet().forEach(s -> keyset.add(s));

            for (int j = 0; j < keyset.size(); j++) {
                Deck vastassa = keyset.get(j);
                System.out.println(pakka.name + " vastaan " + vastassa + ": " + matchupit.get(vastassa));
            }

            System.out.println("");
            System.out.println("------");
            System.out.println("");
        }
    }

    public Double getWinrate(Deck deck1, Deck deck2) {
        for (int i = 0; i < lineup.size(); i++) {
            if (lineup.get(i).name.equals(deck1.name)) {
                return lineup.get(i).getWinrate(deck2);
            }
        }
        return -100.0;
    }
}