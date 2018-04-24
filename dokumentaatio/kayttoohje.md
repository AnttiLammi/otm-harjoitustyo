Tämänhetkisessä versiossa on yksinkertainen tekstikäyttöliittymä, jonka avulla voidaan syöttää molemmille pelaajille neljän pakan lineup sekä asettamaan erilaisille pakkojen väliset matchupit. Käyttäjä voi valita hyödyntävänsä olemassaolevaa tietokantaa (ja samalla täydentävänsä sitä) tai jättää tätä käyttämättä, jolloin tämä voi syöttää täsmällisesti oman näkemyksen mukaiset matchupit pakkojen välille.

Kun matchupit on syötetty, valitaan kilpailuformaatti (joko Conquest vastaamalla myönteisesti, tai Last Hero Standing vastaamalla kielteisesti). Tämän jälkeen käyttäjä voi halutessaan rajoittaa tutkittavia ban-vaihtoehtoja. Tämä mahdollisuus on olemassa, koska tilanteet, joissa vastustaja käyttää bänninsä 100% väärin ovat erittäin harvinaisia ja saattavat vääristää omia parhaita ban-valintoja koskevia valintoja tai ainakin tuoda numeroihin mukaan "tilastollista kohinaa". 

Tämän jälkeen ohjelma suorittaa simulaation ja tulostaa pelaajan voittoprosentit erilaisilla ban-valinnoilla. Tämän jälkeen käyttäjä voi valita jatkavansa ohjelman käyttöä, tai lopettavansa. Mikäli käyttäjä valitsee jatkavansa, tulee yhä syöttää pakkojen nimet, mutta hyödynnettäessä tietokantaa kaikkien pakkojen tiedot löytyvät jo tietokannasta, joten voi tutkia toisella suorituskerralla esimerkiksi eri tavalla rajattua vastustajan banniä tm.

Ohjelman pystyy tällä hetkellä käynnistämään HearthstoneBanCalculator kansiossa komennolla  

```
java -jar hsbancalculator.jar
```
