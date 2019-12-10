# Ohjelmistotekniikka kurssi

# StudyTimeTracker

Ohjelmointitekniikat kurssin harjoitustyö

Opiskelun ajankäytön seurantasovellus.

## Dokumentaatio


[Alustava määrittelydokumentti](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittelu.md)

[Tuntikirjanpito sovellukseen tekemiseen käytetystä ajasta](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

## Release

[Viikko 5](https://github.com/nikomn/ot-harjoitustyo/releases/tag/viikko5)

## Komentoriviltä ajettavia toimintoja

### Automaattiset testit

Ohjelma sisältää automattisia testejä, jotka voi suorittaa seuraavalla komennolla:

```
mvn test
```

Testikattavuusraportin voi luoda komennolla

```
mvn jacoco:report
```

Kattavuusraportti löytyy komennon jälkeen tiedostosta _target/site/jacoco/index.html_. Tiedosto on html muodossa, joten se kannattaa avata esim. firefoxissa tai chromessa.


### Jar paketin generoiminen

Projektista voi generoida suoritettavan jar paketin komennolla:

```
mvn package
```

Jar-paketti _StudyTimeTracker-1.0-SNAPSHOT.jar_ muodostuu komennon ajamisen seurauksena kansioon _target_

### Checkstyle tarkistukset

Tiedostossa [checkstyle.xml](https://github.com/nikomn/ot-harjoitustyo/blob/master/checkstyle.xml) määrittellyt koodin tyyliin liittyvät tarkistukset voi suorittaa komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Tarkistusraportti muodostuu tiedostoon _target/site/checkstyle.html_, joka on html-tiedosto, joka kannattaa avata esim. firefoxissa tai chromessa.

### Javadoc

JavaDoc tiedostot saa luotua komennolla

```
mvn javadoc:javadoc
```

JavaDocia tiedostot ovat html muodossa ja niitä voi lukea esim. firefoxissa avaamalla tiedosto target/site/apidocs/index.html
