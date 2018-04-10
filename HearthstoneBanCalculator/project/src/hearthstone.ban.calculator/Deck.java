/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hearthstone.ban.calculator;

import java.util.HashMap;

/**
 *
 * @author antlammi
 */
public class Deck {

    public String name;
    private HashMap<Deck, Double> winrate;

    public Deck(String name) {
        this.name = name;
         this.winrate = new HashMap<>();
    }

    public void setWinrate(Deck deck, Double winrate) {
        this.winrate.put(deck, winrate);
    }
    
    public HashMap<Deck, Double> getAllWR(){
        return this.winrate;
    }
    public Double getWinrate(Deck deck) {
        if (this.winrate.containsKey(deck)) {
            return this.winrate.get(deck);
        } else {
            return -100.0;
        }
    }
    @Override
    public String toString(){
        return this.name;
    }

}
