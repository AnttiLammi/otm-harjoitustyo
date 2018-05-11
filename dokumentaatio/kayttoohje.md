# Käyttöohje
Lataa tiedosto hsbancalculator.jar
## Ohjelman käynnistäminen
Ohjelman saa käynnistettyä komennolla
```
java -jar hsbancalculator.jar
```
Ohjelman käynnistyttyä aukeaa seuraava näkymä:

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_1.png">

Tässä näkymässä voidaan lisätä tietokantaan uusia pakkoja ja pelaajat. Kun tämä on tehty, voi siirtyä decks-näkymään.

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_2.png">

Delete-napin toimintaa kenties ei tarvitse avata. Painamalla tässä näkymässä pakan nimeä, siirrymme seuraavaan näkymään, jossa voimme lisätä pakkojen välisiä matchuppeja.

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_3.png">

Mikäli pakkojen välinen matchup löytyy jo, se ilmestyy tekstikenttään. Lisäämällä tai muokkaamalla näitä arvoja ja painamalla kyseisen rivin submit nappia, voidaan lisätä tietokantaan pakkojen välinen matchup. Lisäksi tässä näkymässä voi navigoida eri pakkojen välillä klikkaamalla niiden nimiä vasemmalla. Siirrytään seuraavaksi näkymään, jossa voidaan käsitellä pelaajien tietoja players-napin avulla. Sovelluksen tuoreimmassa versiossa on lisäksi olemassa submit all nappi, jota ei kuvassa näy. Tämän avulla voi lisätä kaikki pakat kerralla tietokantaan

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_4.png">

Samaten, kuten decks näkymässä delete napin avulla voidaan poistaa pelaaja tietokannasta. Myös samaan tapaan pelaajan nimeä painamalla voimme siirtyä muokkaamaan kyseisen pelaajan tietoja tietokannassa: 

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_5.png">

Korvaamalla replace-teksti halutulla pakalla voidaan lisätä se pelaajalle. Siirrytään seuraavaksi itse simulaatioon: 

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_6.png">

Valitaan ensin kaksi pelaajaa simulaatioon valitsemalla ensin pelaajan nimi painikkeella ja sitten painamalla confirm-nappia valitaan se. Nappeja on paljon.

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_7.png">

Tämän alapuolella voimme valita käytettävän kilpailuformaatin. Kun tämä on tehty voimme siirtyä ottelun simuloimiseen simuloi-painikkeen avulla.

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_8.png">

Tässä näkymässä voi halutessaan lukita vastustajan bännin, mikäli haluaa vain tutkia yhtä tiettyä vaihtoehtoa. Yhtä lailla voi vain saman tien myös ohittaa tämän vaiheen simuloi-painikkeella. Tässä tutkittiin tilannetta, jossa vastustaja bännii Even Paladinin.

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_9.png">

Nähdään, että tässä tapauksessa kannattaisi siis bannata Quest Rogue, vaikka tilanne ei hirveän hyvältä tässä tilanteessa tunnukaan silti. Tässä esimerkissä kuitenkin kaikki toivotut pakat löytyivät jo ennestään tietokannassa. Palataan vielä uudestaan simulaationäkymään ja katsotaan mitä tapahtuu, kun niitä ei kaikkia löydy. Simulaatiohan ei toimi ellei ole tietoa kuinka usein pakka voittaa toisen.

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbancalculator_ohje_10.png">

Kappas, tällöin ilmestyy lista tietokannasta puuttuvista matchupeista. Tämän näkymän avulla voi täydentää vain puuttuvat pakat tietokantaan ja edetä simulaation kanssa odottelematta turhia. Sovelluksen tuoreimmassa versiossa tämä on tehty kokonaan omaksi näkymäkseen, eli ylhäällä ei näy valittavaa formaattia ym.
