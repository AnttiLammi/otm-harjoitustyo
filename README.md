# Hearthstone Ban Calculator    
Ohjelmistotekniikan menetelmät kurssia varten suoritettava sovellus, jonka avulla lasketaan parhaita bännistrategioita Hearthstone-keräilykorttipeliä kilpaa pelatessa. 

## Dokumentaatio
[Kilpailuformaatit selitettynä](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/formaatit.md)   
[Käyttöohje](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)    
[Vaativuusmäärittely](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/vaativuusm%C3%A4%C3%A4rittely.md)    
[Arkkitehtuuri](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)    
[Työaikakirjanpito](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Komentorivitoiminnot
Testit suoritetaan komennolla   

```
mvn test    
```
Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _HearthstoneBanCalculator-1.0-SNAPSHOT.jar_

