# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenteessa arkkitehtuuri on jaettu kolmeen tasoon. Koodin pakkausrakenne on seuraavanlainen:

![Pakkausrakenne](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/kaavio1.jpg)

Pakkaus studytimetracker.ui sisältää komentoriviltä suoritettavan käyttöliittymän,
studytimetracker.domain sovelluksen varsinaisen sovelluslogiikan ja  studytimetracker.dao
tietokannan hallinnasta vastaavat luokat.

## Käyttöliittymä

Käyttöliittymä sisältää seuraavat näkymät/toiminnallisuudet:

- Aloitusvalikko
- Kirjautumisnäkymä
- Tunnuksenluomisnäkymä
- Pää-valikko
- Kurssin valinta-valikko
- Yhteenveto näkymä
- Kurssi-valikko
- Ajanseuranta reaaliajassa näkymä
- Ajan lisääminen erikseen-näkymä
- Kirjatun ajan muokkaamis-näkymä

Jokainen näkymä/toiminnallisuus on toteutettu erillisenä metodina
, jossa valikot toimivat ikäänkuin navigaatio-pisteinä kohti eri
toimintoja kuljettaessa.

Käyttöliittymä on pyritty pitämään erillään toimintalogiikasta
, mutta näiden välillä on vielä jonkin verran häilyvyyttä paikoitellen,
lähinnä debug tarkoituksista johtuen joka olisi tarkoitus vielä
rajata tarkemmin.

Käyttöliittymästä on parhaillaan tekeillä graafinen versio, jossa
sovelluslogiikka on selkeämmin erillään käyttöliittymästä kaikissa vaiheissa.


## Sovelluslogiikka

Sovelluksen looginen datamalli muodostuu luokista User ja Course, joissa
kuvataan käyttäjiä ja käyttäjiin liittyviä kursseja.

![Luokkakaavio](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio1.png)

Keskeisenä toiminnallisuudesta vastaavana luokkana toimii DBWriter, joka
kommunikoi sovelluksen, tietokannan ja käyttöliittymän välillä.
Kyseinen luokka tarjoaa eri toiminnoille omat metodit:

- void updateCourseDB()
- void updateUserDB()
- void addCourse(Course course)
- void addUser(User user)
- List<Course> getCourses()
- List<User> getUsers()
- void addTime(Course course, Double timeToAdd)
- void trackCourse(Course course)
- void editTime(Course course, Double timeToAdd)


## Tietojen tallennus

DBWriter luokka kirjaa tietoja kurssi- ja käyttäjä-tietokantatiedostoihin.
Tiedostot tallennetaan sovelluksen juuri-kansioon.

Tietokanta-tiedostot ovat tekstimuodossa ja niissä tiedot on erotettu
"£" merkillä. Kurssitietokannan rakenne noudattaa kaavaa:

1. Kurssin-nimi
2. Kurssin kokonaisaika
3. Käyttäjän nimi

Tiedoston rakenne näyttää käytännössä tältä:


KURSSINNIMI£KOKONAISAIKA£KÄYTTÄJÄ

## Toiminnallisuus

Sovelluksen toiminnallisuutta voi kuvailla esim. seuraavan kaavion kautta:

![Luokkakaavio](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/kaavio2.png)

Oleellista on huomata kaaviosta, että käyttöliittymä kommunikoi käyttäjän ja tietokannan välillä
kulkevaa dataa DBWriter olion välityksellä. DBWriter pitää huolta tietokannan lukemisesta ja tietokantaan kirjoitamisesta.


## Ohjelmaan jääneet heikkoudet

- Graafisen käyttöliittymänkin osalta jää paljon parannettavaa, ja toteutus
olisi ehkä ollut parempi tehdä jotenkin muuten kuin JavaFX:llä, jolloin
olisi ehkä ollut helpompi "piirtää" näkymät jotenkin erikseen, jolloin
pelkkään käyttöliittymän ulkonäköön ei olisi tarvinnut käyttää niin paljoa aikaa.
- Graafisen käyttöliittymän koodissa on vielä paljon päällekäisyyttä ja luokka on
melko pitkä ja sisältää paljon metodeja, joten sen voisi hajoittaa pienempiin luokkin.
- DAO malli jäi vaiheeseen, ja sitä voisi parantaa, jolloin tietokannan
muuntaminen esim. sql-tietokannaksi olisi helpompaa.
- Testausten osalta testattavat asiat voisi olla jotenkin
kattavammin toteutettu ja sovelluksen koodi voisi olla enemmän
"synkronisoitu" testien kanssa.
- Dokumentaation osalta kaavioita olisi hyvä olla enemmän, ja ohjelman eri vaiheita
olisi kuvattu myös virtauskaavioiden kautta.
- Sovelluksessa voisi myös olla enemmän toiminnallisuutta ja sitä voisi
laajentaa paljonkin nykyisestä.
