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
 *
 * @author antlammi
 */
public class Calculator {

    public Player pelaaja1;
    public Player pelaaja2;

    private Random rng;

    public Calculator(Player pelaaja1, Player pelaaja2) {
        this.pelaaja1 = pelaaja1;
        this.pelaaja2 = pelaaja2;

        this.rng = new Random();

    }

    public HashMap<Deck, Double> parasBanConquestKnownBan(Player pelaaja, ArrayList<Deck> banVaihtoehdot) {
        ArrayList<Deck> p1 = new ArrayList<>();
        ArrayList<Deck> p2 = new ArrayList<>();
        Player toinenPelaaja;
        if (pelaaja == this.pelaaja1) {
            p1 = pelaaja1.lineup;
            p2 = pelaaja2.lineup;
            toinenPelaaja = pelaaja2;
        } else {
            p1 = pelaaja2.lineup;
            p2 = pelaaja1.lineup;
            toinenPelaaja = pelaaja1;
        }
        HashMap<Deck, Double> simtulos = new HashMap<>();
        HashMap<Deck, Integer> simvoitot = new HashMap<>();
        HashMap<Deck, Integer> simapu = new HashMap<>();

        Integer simkierrokset = 10000000;
        for (int i = 0; i <= simkierrokset; i++) {
            Deck p1ban = banVaihtoehdot.get(rng.nextInt(banVaihtoehdot.size()));
            Deck p2ban = p2.get(rng.nextInt(4));

            simapu.putIfAbsent(p2ban, 0);
            simapu.put(p2ban, simapu.get(p2ban) + 1);

            Player voittaja = simuloiConquest(pelaaja, p1ban, pelaaja2, p2ban);
            if (voittaja == pelaaja) {
                simvoitot.putIfAbsent(p2ban, 0);
                simvoitot.put(p2ban, simvoitot.get(p2ban) + 1);
            }
        }

        ArrayList<Deck> line = new ArrayList<>();
        simapu.keySet().forEach(s -> line.add(s));

        for (int i = 0; i < line.size(); i++) {
            Deck pakka = line.get(i);
            simtulos.put(pakka, 1.0 * simvoitot.get(pakka) / simapu.get(pakka));
            System.out.println("Kun vastustajalta oli bännitty: " + pakka + ", voitit " + simvoitot.get(pakka)
                    + " /" + simapu.get(pakka) + " kertaa.");

            System.out.println("Voittoprosentti oli siis: " + simtulos.get(pakka));
            System.out.println("");
            System.out.println("--------------");
            System.out.println("");
        }
        return simtulos;
    }

    public HashMap<Deck, Double> parasBanConquest(Player pelaaja) {
        ArrayList<Deck> p1;
        ArrayList<Deck> p2;

        Player toinenPelaaja;
        if (pelaaja == this.pelaaja1) {
            p1 = pelaaja1.lineup;
            p2 = pelaaja2.lineup;
            toinenPelaaja = pelaaja2;
        } else {
            p1 = pelaaja2.lineup;
            p2 = pelaaja1.lineup;
            toinenPelaaja = pelaaja1;
        }

        HashMap<Deck, Double> simtulos = new HashMap<>();
        HashMap<Deck, Integer> simvoitot = new HashMap<>();
        HashMap<Deck, Integer> simapu = new HashMap<>();

        Integer simkierrokset = 10000000;
        for (int i = 0; i <= simkierrokset; i++) {
            Deck p1ban = p1.get(rng.nextInt(4));
            Deck p2ban = p2.get(rng.nextInt(4));

            simapu.putIfAbsent(p2ban, 0);
            simapu.put(p2ban, simapu.get(p2ban) + 1);

            Player voittaja = simuloiConquest(pelaaja, p1ban, pelaaja2, p2ban);
            if (voittaja == pelaaja) {
                simvoitot.putIfAbsent(p2ban, 0);
                simvoitot.put(p2ban, simvoitot.get(p2ban) + 1);
            }
        }

        ArrayList<Deck> line = new ArrayList<>();
        simapu.keySet().forEach(s -> line.add(s));

        for (int i = 0; i < line.size(); i++) {
            Deck pakka = line.get(i);
            simtulos.put(pakka, 1.0 * simvoitot.get(pakka) / simapu.get(pakka));
            System.out.println("Kun vastustajalta oli bännitty: " + pakka + ", voitit " + simvoitot.get(pakka)
                    + " /" + simapu.get(pakka) + " kertaa.");

            System.out.println("Voittoprosentti oli siis: " + simtulos.get(pakka));
            System.out.println("");
            System.out.println("--------------");
            System.out.println("");
        }
        return simtulos;
    }

    public HashMap<Deck, Double> parasBanLHS(Player pelaaja) {
        ArrayList<Deck> p1;
        ArrayList<Deck> p2;

        Player toinenPelaaja;
        if (pelaaja == this.pelaaja1) {
            p1 = pelaaja1.lineup;
            p2 = pelaaja2.lineup;
            toinenPelaaja = pelaaja2;
        } else {
            p1 = pelaaja2.lineup;
            p2 = pelaaja1.lineup;
            toinenPelaaja = pelaaja1;
        }

        HashMap<Deck, Double> simtulos = new HashMap<>();
        HashMap<Deck, Integer> simvoitot = new HashMap<>();
        HashMap<Deck, Integer> simapu = new HashMap<>();

        Integer simkierrokset = 10000000;
        for (int i = 0; i <= simkierrokset; i++) {
            Deck p1ban = p1.get(rng.nextInt(4));
            Deck p2ban = p2.get(rng.nextInt(4));

            simapu.putIfAbsent(p2ban, 0);
            simapu.put(p2ban, simapu.get(p2ban) + 1);

            Player voittaja = simuloiLHS(pelaaja, p1ban, pelaaja2, p2ban);
            if (voittaja == pelaaja) {
                simvoitot.putIfAbsent(p2ban, 0);
                simvoitot.put(p2ban, simvoitot.get(p2ban) + 1);
            }
        }

        ArrayList<Deck> line = new ArrayList<>();
        simapu.keySet().forEach(s -> line.add(s));

        for (int i = 0; i < line.size(); i++) {
            Deck pakka = line.get(i);
            simtulos.put(pakka, 1.0 * simvoitot.get(pakka) / simapu.get(pakka));
            System.out.println("Kun vastustajalta oli bännitty: " + pakka + ", voitit " + simvoitot.get(pakka)
                    + " /" + simapu.get(pakka) + " kertaa.");

            System.out.println("Voittoprosentti oli siis: " + simtulos.get(pakka));
            System.out.println("");
            System.out.println("--------------");
            System.out.println("");
        }
        return simtulos;
    }

    public HashMap<Deck, Double> parasBanLHSKnownBan(Player pelaaja, ArrayList<Deck> banVaihtoehdot) {
        ArrayList<Deck> p1 = new ArrayList<>();
        ArrayList<Deck> p2 = new ArrayList<>();
        Player toinenPelaaja;
        if (pelaaja == this.pelaaja1) {
            p1 = pelaaja1.lineup;
            p2 = pelaaja2.lineup;
            toinenPelaaja = pelaaja2;
        } else {
            p1 = pelaaja2.lineup;
            p2 = pelaaja1.lineup;
            toinenPelaaja = pelaaja1;
        }
        HashMap<Deck, Double> simtulos = new HashMap<>();
        HashMap<Deck, Integer> simvoitot = new HashMap<>();
        HashMap<Deck, Integer> simapu = new HashMap<>();

        Integer simkierrokset = 10000000;
        for (int i = 0; i <= simkierrokset; i++) {
            Deck p1ban = banVaihtoehdot.get(rng.nextInt(banVaihtoehdot.size()));
            Deck p2ban = p2.get(rng.nextInt(4));

            simapu.putIfAbsent(p2ban, 0);
            simapu.put(p2ban, simapu.get(p2ban) + 1);

            Player voittaja = simuloiLHS(pelaaja, p1ban, pelaaja2, p2ban);
            if (voittaja == pelaaja) {
                simvoitot.putIfAbsent(p2ban, 0);
                simvoitot.put(p2ban, simvoitot.get(p2ban) + 1);
            }
        }

        ArrayList<Deck> line = new ArrayList<>();
        simapu.keySet().forEach(s -> line.add(s));

        for (int i = 0; i < line.size(); i++) {
            Deck pakka = line.get(i);
            simtulos.put(pakka, 1.0 * simvoitot.get(pakka) / simapu.get(pakka));
            System.out.println("Kun vastustajalta oli bännitty: " + pakka + ", voitit " + simvoitot.get(pakka)
                    + " /" + simapu.get(pakka) + " kertaa.");

            System.out.println("Voittoprosentti oli siis: " + simtulos.get(pakka));
            System.out.println("");
            System.out.println("--------------");
            System.out.println("");
        }
        return simtulos;
    }

    public Player simuloiConquest(Player pelaaja1, Deck p1bannedclass, Player pelaaja2, Deck p2bannedclass) {
        ArrayList<Deck> p1line = new ArrayList<>();
        for (int i = 0; i < pelaaja1.lineup.size(); i++) {
            if (!pelaaja1.lineup.get(i).equals(p1bannedclass)) {
                p1line.add(pelaaja1.lineup.get(i));
            }
        }
        ArrayList<Deck> p2line = new ArrayList<>();
        for (int i = 0; i < pelaaja2.lineup.size(); i++) {
            if (!pelaaja2.lineup.get(i).equals(p2bannedclass)) {
                p2line.add(pelaaja2.lineup.get(i));
            }
        }
        Integer p1voitot = 0;
        Integer p2voitot = 0;

        while (p1voitot < 3 && p2voitot < 3) {
            Deck p1pakka = p1line.get(rng.nextInt(3 - p1voitot));

            Deck p2pakka = p2line.get(rng.nextInt(3 - p2voitot));
            Double todennakoisyys = p1pakka.getWinrate(p2pakka);
            Double voittaja = rng.nextDouble();
            if (voittaja < todennakoisyys) {
                p1voitot++;
                p1line.remove(p1pakka);
            } else {
                p2voitot++;
                p2line.remove(p2pakka);
            }

        }

        if (p1voitot > p2voitot) {
            return pelaaja1;
        } else {
            return pelaaja2;
        }
    }

    public Player simuloiLHS(Player pelaaja1, Deck p1bannedclass, Player pelaaja2, Deck p2bannedclass) {
        ArrayList<Deck> p1line = new ArrayList<>();
        for (int i = 0; i < pelaaja1.lineup.size(); i++) {
            if (!pelaaja1.lineup.get(i).equals(p1bannedclass)) {
                p1line.add(pelaaja1.lineup.get(i));
            }
        }
        ArrayList<Deck> p2line = new ArrayList<>();
        for (int i = 0; i < pelaaja2.lineup.size(); i++) {
            if (!pelaaja2.lineup.get(i).equals(p2bannedclass)) {
                p2line.add(pelaaja2.lineup.get(i));
            }
        }
        Integer p1voitot = 0;
        Integer p2voitot = 0;

        Boolean p1w = false;
        Boolean p2w = false;

        Deck p1pakka = new Deck("");
        Deck p2pakka = new Deck("");

        while (p1voitot < 3 && p2voitot < 3) {
            if (!p1w) {
                p1pakka = p1line.get(rng.nextInt(3 - p2voitot));
            }
            if (!p2w) {
                p2pakka = p2line.get(rng.nextInt(3 - p1voitot));
            }
            Double todennakoisyys = p1pakka.getWinrate(p2pakka);
            Double voittaja = rng.nextDouble();
            if (voittaja < todennakoisyys) {
                p1voitot++;
                p2line.remove(p2pakka);
                p1w = true;
                p2w = false;

            } else {
                p2voitot++;
                p1line.remove(p1pakka);
                p1w = false;
                p2w = true;
            }

        }

        if (p1voitot > p2voitot) {
            return pelaaja1;
        } else {
            return pelaaja2;
        }
    }
}
