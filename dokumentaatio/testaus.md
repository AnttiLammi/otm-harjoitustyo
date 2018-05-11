# Testausdokumentti 

## Yksikkö- ja integraatiotestaus

Automatisoidut testit sijaitsevat kahdessa eri pakkauksessa, sovelluslogiikkaa eli hsbancalculator.sovelluslogiikka pakkausta testaavat testit löytyvät hsbancalculator testipakkauksesta. Pysyväistalletukseen liittyvät testit puolestaan löytyvät database testipakkakauksesta. 

**Sovelluslogiikka**    
Sovelluslogiikkaa testaavat testiluokat *DeckTest.java*, *PlayerTest.java*, ja *CalculatorTest.java*. Näistä DeckTest ja PlayerTest ovat pääosin varsin triviaaleja ja testaavat lähinnä toimivatko luokkien getterit ja setterit ns. normaalissa käytössä, sekä vähän oudommissa tilanteissa.    

CalculatorTest puolestaan testaa sovelluksen simulaatio-ominaisuuksia. Koska näihin liittyy satunnainen elementti, useassa testissä asetetaan sovelluksen käyttämälle random-satunnaislukuoliolle tietty seed, jotta testeistä tulee aina sama tulos. 
    
**Daot**    
Sovelluksen pysyväistalletusta testaavat DaoTestit *DeckDaoTest.java*, *MatchupsDaoTest.java* ja *PlayerDaoTest.java*, luovat kaikki testien suorituksen yhteydessä käyttöön testitietokannan, jonne lisättävän tiedon oikeellisuutta nämä luokat testaavat. 

### Testauskattavuus
Käyttöliittymää lukuunottamatta testien rivikattavuus on 86% ja haaraumakattavuus 95%. Näissä on lisäksi mukana vielä sovelluksen main-luokka, joka käytännössä voitaisiin lukea käyttöliittymäluokaksi.
<img src="https://raw.githubusercontent.com/AnttiLammi/otm-harjoitustyo/master/dokumentaatio/kuvat/Testikattavuus.png">
### Ongelmia
Pääosin tilanteet, joissa voisi tulla omituisia syötteitä joista koituisi ongelmia käsitellään käyttöliittymäluokkien yhteydessä. Voi kuitenkin olla, että sovelluslogiikka luokat eivät aina käsittele mahdollisten käyttöliittymäluokkien muutosten aiheuttamia ongelmasyötteitä tarpeeksi hyvin, eikä näitä välttämättä oteta testeissä tarpeeksi huomioon.
