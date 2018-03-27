# Vaativuusmäärittely

## Sovelluksen tarkoitus

Sovellus on apuväline Hearthstone-pelissä kilpaileville, joka laskee molempien pelaajien pakkatyyppien perusteella optimaalisen ban-strategian. 

## Käyttäjät

Alkuun on vain yksi käyttäjätyyppi _(normaalikäyttäjä)_, joka voi syöttää itse molempien pelaajien pakat ja niiden väliset winratet ja saa vastauksena optimaalisen bännin. Tämä ei vaadi kirjautumista, mutta myöhemmin saatetaan lisätä ominaisuuksia, jotka antavat siihen mahdollisuuden. Lisäksi on erillinen käyttäjätyyppi _(admin)_, joka kykenee hallinnoimaan pakkojen tiedot sisältävää tietokantaa.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista
- Normaalikäyttäjä voi valita molemmille pelaajille pakat ja kilpailuformaatin, jolloin ohjelma simuloi ottelusarjan kulkua ja antaa käyttäjälle listan eri ban-strategioista ja niiden vaikutuksesta ottelun voittamisen todennäköisyyteen.
  - Tiedot pakkojen välisistä voittoprosenteista säilytetään tietokannassa.
  - Sovellus tukee kahta yleisintä kilpailuformaattia: Conquestia ja Last Hero Standingiä.

### Kirjautumisen jälkeen
- Admin-käyttäjä voi lisäksi muokata tietokantaa: Lisätä uusia pakkoja ja muokata niiden välisiä voittoprosentteja.

## Jatkokehitysideoita
Perusversion jälkeen jatketaan kehitystä ajan salliessa seuraaviin ominaisuuksiin. Lista on jotakuinkin järjestyksessä ensimmäisenä lisättävästä viimeisenä lisättävään.

- Mahdollisuus laskea paras mahdollinen aloituspakka Last Hero Standing - formaatissa.
- Graafinen käyttöliittymä
- Normaalikäyttäjille käyttäjätunnuksen luomisen ja sisäänkirjautumisen mahdollisuus
    -Tämä tarjoaa mahdollisuuden säilyttää tietoa omista pakoistaan henkilökohtaisilla käyttäjäsivuilla, mikä nopeuttaa käyttöä   turnauksessa. 
- Adminkäyttäjille sisäänkirjautumismahdollisuus ja tietokannan muokkaaminen sovellussivulta suoraan
- Mahdollisuus simuloida kokonainen turnaus erilaisia strategioita hyödyntäen
- Mahdollisuus hyödyntää olemassaolevia tietokantoja automaattisesti pakkojen voittomahdollisuuksien hienosäätöön
- Mahdollisuus ottaa huomioon yksittäisiä korttivalintoja ja arvioida niiden vaikutusta pelien kulkuun
