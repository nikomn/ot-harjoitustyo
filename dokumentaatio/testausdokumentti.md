# Testausdokumentti

Ohjelman testaus on suoritettu automatisoiduilla JUnit testeillä, sekä manuaalisesti
tehdyillä testauksella. Koodin laatua on testattu Checkstyle testeillä.

## JUnit testit

JUnit testeillä on käyty läpi ohjelman keskeiset toiminnallisuudet, käyttäjien ja
kurssien lisääminen ja tietokantaan kirjoittamiset.

Testit on toteutettu tiedostossa [StudyTimeTrackerTest](https://github.com/nikomn/ot-harjoitustyo/blob/master/src/test/java/StudyTimeTrackerTest.java)

### Testikattavuus

Käyttöliittymää lukkuunottamatta sovelluksen testikattavuus on n. 70% tasolla.

![Raportti](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/testikattavuus.png)

## Checkstyle

Checkstyle testeillä on käyty läpi ohjelman koodi, ja täten pyritty varmistamaan
että koodi on siistiä ja helposti luettavissa, eivätkä luokat ole liian pitkiä.
Käyttöliittymän toteuttavat luokat on jätetty Checkstyle testien ulkopuolelle.

## Järjestelmätestaus ja toiminnallisudet

Järjestelmätestaus on suoritettu manuaalisesti, siten, että ohjelmassa on suoritettu
erikseen kaikki käyttötarkoituksen mukaiset [vaatimusmäärittelyissä](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittelu.md) kuvatut toiminnallisuudet.

Testausten yhteydessä kaikki kentät on täytetty myös virheellisillä arvoilla, esim. tyhjillä käyttäjä ja kurssinimillä.

# Sovellukseen jääneet laatuongelmat

- Sovellus ei anna erityisen kuvaavia ilmoituksia, jos tietokantaan kirjoittamisen kanssa on ongelmia
- Käyttöliittymän ulkonäkö on melko alkeellinen ja esim. poikkeuksellisen pitkät kurssinimet tms. ja tilanne,
jossa käyttäjällä on todella paljon kursseja jne. eivät näytä kauhean hyvältä sovelluksessa asetteluiden kannalta.
