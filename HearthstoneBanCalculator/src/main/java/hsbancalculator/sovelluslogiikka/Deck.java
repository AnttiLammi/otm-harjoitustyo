package hsbancalculator.sovelluslogiikka;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashMap;

/**
 * Pakka-olio.
 *
 * @author antlammi
 */
public class Deck {

    public Integer id;
    public String name;
    private HashMap<Deck, Double> winrate;

    /**
     * Luodaan uusi pakka pelkällä nimellä, alustetaan winrate-taulu.
     *
     * @param name
     */
    public Deck(String name) {
        this.name = name;
        this.winrate = new HashMap<>();
    }

    /**
     * Luodaan uusi pakka id:llä ja nimellä.
     *
     * @param id
     * @param name
     */
    public Deck(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.winrate = new HashMap<>();
    }

    /**
     * Asettaa uuden winraten tiettyä pakkaa vastaan, mikäli winrate ei ole
     * laillinen arvo ei lisää sitä ja palauttaa false.
     *
     * @param deck
     * @param winrate
     * @return
     */
    public Boolean setWinrate(Deck deck, Double winrate) {
        if (winrate > 0 && winrate <= 1) {
            this.winrate.put(deck, winrate);
            return true;
        }
        return false;
    }

    public HashMap<Deck, Double> getAllWR() {
        return this.winrate;
    }

    /**
     * Palauttaa winraten tiettyä pakkaa vastaa tai -100.0, mikäli matchuppia ei
     * löydy.
     *
     * @param deck
     * @return
     */
    public Double getWinrate(Deck deck) {
        if (this.winrate.containsKey(deck)) {
            return this.winrate.get(deck);
        } else {
            return -100.0;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

}
