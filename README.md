# Hearthstone Ban Calculator    
Ohjelmistotekniikan menetelmät kurssia varten suoritettava sovellus, jonka avulla lasketaan parhaita bännistrategioita Hearthstone-keräilykorttipeliä kilpaa pelatessa. 

## Dokumentaatio
[Kilpailuformaatit selitettynä](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/formaatit.md)   
[Käyttöohje](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)    
[Vaatimusmäärittely](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusm%C3%A4%C3%A4rittely.md)    
[Arkkitehtuuri](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)    
[Työaikakirjanpito](https://github.com/AnttiLammi/otm-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
## Releaset
[Viimeisimmän version lataaminen](https://github.com/AnttiLammi/otm-harjoitustyo/releases/download/viikko7%2Ffinal/hsbancalculator.jar)  
  
[Loppupalautus](https://github.com/AnttiLammi/otm-harjoitustyo/releases/tag/viikko7%2Ffinal)    
[Viikko 6](https://github.com/AnttiLammi/otm-harjoitustyo/releases/tag/viikko6)  
[Viikko 5](https://github.com/AnttiLammi/otm-harjoitustyo/releases/tag/viikko5)   

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

### JavaDoc
Komento
```
mvn javadoc:javadoc
```
Generoi JavaDocin jota voi tarkastella avaamalla selaimella tiedoston sijainnissa target/site/apidocs/index.html

### CheckStyle
CheckStyle-tiedoston määrittelemät tarkistukset voidaan tarkistaa 
```
 mvn jxr:jxr checkstyle:checkstyle 
```
Generoi polkuun target/site/checkstyle.html mahdolliset virheilmoitukset
