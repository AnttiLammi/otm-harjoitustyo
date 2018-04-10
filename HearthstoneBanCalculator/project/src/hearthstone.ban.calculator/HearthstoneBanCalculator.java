/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hearthstone.ban.calculator;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author antlammi
 */
public class HearthstoneBanCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Scanner lukija = new Scanner(System.in);
        
        ArrayList<String> pnimet = new ArrayList<>();
        ArrayList<Deck> pelaaja = new ArrayList<>();
        System.out.println("Syötä pelaajan 1 pakat: ");
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
            pelaaja.add(pakka);

        }

        Player player1 = new Player(pelaaja.get(0), pelaaja.get(1), pelaaja.get(2), pelaaja.get(3));

        System.out.println("");
        System.out.println("--------------------------------------");
        System.out.println("");
        ArrayList<String> p2nimet = new ArrayList<>();
        ArrayList<Deck> pelaaja2 = new ArrayList<>();
        System.out.println("Syötä pelaajan 2 pakat: ");
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
            pelaaja2.add(pakka);

        }
        Player player2 = new Player(pelaaja2.get(0), pelaaja2.get(1), pelaaja2.get(2), pelaaja2.get(3));

        System.out.println("");
        System.out.println("-----------------------------------------");
        System.out.println("");

        System.out.println("Syötä pakkojen winratet: ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(pelaaja.get(i) + " vastaan " + pelaaja2.get(j) + ": ");
                Double winrate = lukija.nextDouble();
                while (winrate < 0 || winrate > 1) {
                    System.out.println("Väärä arvo, syötä uudelleen.");
                    System.out.println(pelaaja.get(i) + " vastaan " + pelaaja2.get(j) + ": ");
                    winrate = lukija.nextDouble();
                }
                System.out.println("");
                player1.setWinrate(pelaaja.get(i), pelaaja2.get(j), winrate);
                player2.setWinrate(pelaaja2.get(j), pelaaja.get(i), 1-winrate);
            }
        }
        System.out.println("Tulostetaan pakkojen voittoprosentit toisiaan vastaan");
        
        System.out.println("Pelaaja 1: ");
        System.out.println("");
        player1.printWR();
        
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("");
        System.out.println("Pelaaja 2: ");
        System.out.println("");
        player2.printWR();
    }

}
