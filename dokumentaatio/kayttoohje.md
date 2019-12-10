# Käyttöohje

## Komentorivipohjainen käyttöliittymä

Lataa tiedosto [studytimetracker.jar](https://github.com/nikomn/ot-harjoitustyo/releases/tag/viikko6) tai lataa projekti koneellesi komennolla:

```
git clone https://github.com/nikomn/ot-harjoitustyo.git
```

## Ohjelman käynnistäminen

### Jar paketti

Ohjelma käynnistyy komennolla

```
java -jar studytimetracker.jar
```

### Ladattu projekti

Ohjelma käynnistyy projektin pää-kansiosta komennolla

```
mvn compile exec:java -Dexec.mainClass=studytimetracker.ui.StudyTimeTrackerUi
```

Vaihtoehtoisesti voit myös avata projektin esim. netbeansissa ja suorittaa ohjelmaa netbeansin kautta.

## Käyttäjätunnuksen lisääminen

Aloita luomalla itsellesi käyttäjätunnus, jos sinulla ei sellaista ole ennestään.
Valitse ohjelman käynnistys-valikosta vaihtoehto "2. Create new user account"

Määritä käyttäjätunnus ja paina enter (ohjelma herjaa, jos tunnus on jo olemassa). Jos et
haluakkaan luoda tunnusta, paina pelkkä enter.

Tämän jälkeen pääset valitsemaan kurssin ja kirjaamaan aikaa.

## Kirjautuminen

Jos sinulla on jo käyttäjätunnus, valitse käynnistys-valikosta vaihtoehto "1. Log in"

Syötä käyttäjätunnus ja paina enter, jonka jälkeen pääset valitsemaan kurssin ja kirjaamaan aikaa.

## Kurssin valitseminen ja ajan seuraaminen

Syötä kurssin nimi ja paina enter. Pelkkä enter palaa takaisin käynnistys-valikkoon.
Ohjelma siirtyy seuraamaan aikaa. Kun olet valmis, voit lopettaa ajan seurannan painamalla enter.
Ohjelma näyttää kuinka paljon aikaa kurssille on kirjattu.

## Ohjelman sulkeminen

Voit sulkea ohjelman valitsemalla "x. Close program" käynnistys-valikosta.

## Esimerkkikuva ohjelman suoritusesta

![Komentorivikäyttöliittymä esimerkki](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/esimerkki_output1.jpg)

## GUI käyttöliittymä sneak preview

Ohjelmaan on tarkoitus tehdä graafinen käyttöliittymä, kun perustoiminnallisuus on saatu toimimaan riittävän hyvin.
Komentoriviltä on mahdollista jo tässä vaiheessa suorittaa varhaista luonnosta graafisesta käyttöliittymästä valitsemalla
käynnistys-valikosta vaihtoehto "3. Start gui (early beta - unstable!)",
jossa voi tällä hetkellä "kirjautua" ja käynnistää sekunttikellon ajanseurantaa varten:

![Kirjautuminen](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/gui1.png)

![Ajanseuranta](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/gui2.png)

Graafisen käyttöliittymän luonnos:

[Alustavia luonnoksia käyttöliittymän mahdollisesta ulkoasusta](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kauttoliittymaluonnos.md)
