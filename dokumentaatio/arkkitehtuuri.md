
# Arkkitehtuuri
## Rakenne

Ohjelman rakenne on kerrosarkkitehtuuri kolmessa tasossa ja pakkausrakenne on seuraava:

<img src="https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png">

Pakkaus *hsbancalculator.ui* sisältää JavaFX:llä toteutetun käyttöliittymän, *hsbancalculator.sovelluslogiikka* nimensä mukaisesti sovelluslogiikan ja *hsbancalculator.daot* tietojen pysyväistalletuksen tietokannan avulla mahdollistavan koodin.
## Käyttöliittymä 
Käyttöliittymä sisältää kokonaisuudessaan 8 näkymää.
 - Pakkojen ja Pelaajien lisääminen
- Pakkojen listaus
- Pakkojen Matchupit
- Pelaajien listaus
- Pelaajien pakkojen muokkaus
- Simulaation pelaajien ja formaatin valinta, sekä puuttuvien matchuppien lisääminen
- Vastustajan bannin rajaaminen
- Simulaation tulokset  
Lisäksi jokaisessa näkymässä on läsnä yläpalkki, jonka avulla pystyy navigoimaan joidenkin näistä näkymistä välillä. Jokainen näkymä on osa samaa BorderPane oliota, jonka yläosassa sijaitsee yläpalkki ja keskiosassa vaihtelee nämä eri näkymät. Käyttöliittymän ulkoasusta ja toteutuksesta lisää infoa [käyttöohjeessa](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md).   


## Sovelluslogiikka
Alla oleva kaavio kuvastaa karkeasti sitä, miten sovelluslogiikan osaset liittyvät toisiinsa. Ohjelman simulaation toteutus ja laskennalliset operaatiot sijaitsevat kokonaan Calculator luokassa. Sen voisi siis sanoa olevan ohjelman sovelluslogiikan runko.

<img src="https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/kuvat/Luokkakaavio.png" width="840">

## Tiedon talletus
Ohjelman hyödyntämät tiedot tallennetaan SQL muotoiseen tietokantaan. Ohjelmaa käynnistettäessä ensimmäistä kertaa Database luokka luo uuden tietokannan hsbc.db ohjelmakansioon, jota se voi jatkossa hyödyntää.

Tämän lisäksi *hsbancalculator.daot* pakkauksesta löytyvät luokat:
- DeckDao
- PlayerDao
- MatchupsDao  
Näistä jokainen vastaa tietyn tietokantataulun operaatioista.

# Ohjelman toiminnallisuus
## Pakkojen ja Pelaajien lisääminen tietokantaan

Alla oleva kaavio kuvastaa miten graafisella pakkojen ja pelaajien lisääminen tietokantaan tapahtuu graafisen käyttöliittymän avulla.

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbcSeq_1.png">

## LHS Ottelun Simulaatio

<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/hsbcSeq_2.png">

