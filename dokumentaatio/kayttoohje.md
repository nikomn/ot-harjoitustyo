# Käyttöohje

## Ohjelman lataaminen koneelle

Lataa jar-tiedosto [studytimetracker.jar](https://github.com/nikomn/ot-harjoitustyo/releases/tag/viikko7) tai lataa projekti koneellesi komennolla:

```
git clone https://github.com/nikomn/ot-harjoitustyo.git
```

Tai lataa projekti selaimen kautta zip pakettina ja pura se koneellesi.

## Ohjelman käynnistäminen

### Jar paketti

Ohjelma käynnistyy komennolla

```
java -jar studytimetracker.jar
```

### Ladattu projekti

Ohjelma käynnistyy projektin pää-kansiosta komennolla

```
mvn compile exec:java -Dexec.mainClass=studytimetracker.Main
```

Vaihtoehtoisesti voit myös avata projektin esim. netbeansissa ja suorittaa ohjelmaa netbeansin kautta.

## Ohjelman toiminnallisuudet

### Aloitusnäkymä

Ohjelman käynnistyessä ensimmäisenä näkymänä on kirjautumisruutu. Järjestelmään
kijaudutaan käyttäjänimellä. Aloitusnäkymän kautta pääsee myös luomaan uuden
tunnuksen tai sammuttamaan ohjelman.

![Kirjautuminen](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/login.png)

#### Käyttäjätunnuksen lisääminen

Aloita luomalla itsellesi käyttäjätunnus, jos sinulla ei sellaista ole ennestään.

![Uusi tunnus](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/newuser.png)

Määritä käyttäjätunnus ja klikkaa _create user_ (ohjelma herjaa, jos tunnus on
jo olemassa tai jos tunnus on liian lyhyt). Jos et haluakkaan luoda
tunnusta, pääset takaisin aloitusnäkymään klikkaamalla _Back to login_.

Uuden tunnuksen luomisen jälkeen samalla myös kirjaudutaan sisään ko. tunnuksella.

#### Kirjautuminen

Jos sinulla on jo käyttäjätunnus, kirjoita se aloitusnäkymässä ja klikkaa _Login_.

### Päävalikko

Kirjautumisen jälkeen pääset päävalikkoon, jossa voit lisätä uusia kursseja
tarkkailla kurssien kokonaistilannetta tai siirtyä valitsemaan kurssia.

![Päävalikko](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/mainmenu.png)

#### Kurssin lisääminen

Siirry uuden kurssin lisäämiseen klikkaamalla päävalikossa _Add new course_.

![Uusi kurssi](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/newcourse.png)

Kirjoita kurssin nimi ja klikkaa _Create course_. Ohjelma näyttää virheilmoituksen,
jos kurssi on jo olemassa tai jos kurssin nimi on liian lyhyt.

Jos et haluakkaan lisätä kurssia pääset takaisin valikkoon klikkaamalla _Back to menu_

#### Tilannekatsaus

Kaikkien kurssiesi tilanteen pääset näkemään klikkaamalla päävalikossa _show overview_

![Tilannekatsaus](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/overview.png)

Takaisin pää-valikkoon pääset klikkaamalla _Back to mainmenu_

#### Kurssin valinta

Päävalikon kautta pääset valitsemaan kurssin klikkaamalla _Select course_

Näkyviin avautuu lista olemassa-olevista kursseistasi, joista voit valita klikkaamalla kurssin painiketta.

![Kurssilista](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/selectcourse.png)

#### Kirjautuminen ulos

Päävalikossa voit myös kirjautua ulos järjestelmästä _log out_ painikkeella.

### Kurssivalikko

Kurssivalikosta pääset seuraamaan aikaa reaaliajassa, lisäämään aikaa käsin tai
ylikirjaamaan aikaisemmin kirjatun ajan itse määrittämälläsi.

Näet myös kyseisen kurssin kirjatun ajan kaaviona.

![Kurssivalikko](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/coursemenu.png)

#### Ajan seuraaminen reaaliajassa

Kurssivalikosta painike _Track time_ vie näkymään, jossa aikaa voi seurata reaaliajassa,
esim. jos teet kurssiin liittyviä tehtäviä, voit käynnistää ajastimen aloittaessasi
ja sammuttaa sen, kun lopetat ja käyttämäsi aika kirjautuu näin automaattisesti,
ilman, että sinun täytyy itse pitää erikseen kirjaa siitä, paljonko aikaa meni.

![Ajanseuranta](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/tracktime.png)

Ajastin käynnistyy _Start_ painikkeella ja seurannan voi lopettaa _Stop_ painikkeella.

#### Ajan lisääminen käsin

Kurssivalikosta painike _Track time_ vie näkymään, jossa aikaa voi lisätä käsin,
esim. jos olet ollut luennolla, ilman, että ohjelma on ollut käytössäsi, voit tätä
kautta lisätä ajan myöhemmin.

![Ajanlisäys](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/addtime.png)

Voit määrittää erikseen lisättävät tunnit, minuutit ja sekunnit. Ohjelma antaa virheilmoituksen,
jos määrittämissääsi ajoissa on esim. ei numeerisia arvoja ja yrität kirjata negatiivista aikaa.
_add time_ painike lisää ajan. Jos et halua lisätä aikaa pääset takaisin valikkoon
_back to menu_ painikkeella.

#### Kirjatun ajan muokkaaminen

Kursivalikon painike _Change tracked time_ vie näkymään, jonka kautta voit muokata
vapaasti kirjattua aikaa. Tässä määrittämäsi ajat kirjoittavat yli kaikki aikaisemmin
kirjatut merkinnät.

![Ajanlisäys](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/edittime.png)

Voit määrittää erikseen lisättävät tunnit, minuutit ja sekunnit. Ohjelma antaa virheilmoituksen,
jos määrittämissääsi ajoissa on esim. ei numeerisia arvoja ja yrität kirjata negatiivista aikaa.
_Change time_ painike kirjaa ajan tietokantaan. Jos et halua muokata aikaa pääset takaisin valikkoon
_back to menu_ painikkeella.

#### Päävalikkoon siirtyminen

Kurssivalikosta pääset takaisin päävalikkoon _Back to menu_ painikkeella.
