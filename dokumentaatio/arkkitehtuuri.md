# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenteessa arkkitehtuuri on jaettu kolmeen tasoon. Koodin pakkausrakenne on seuraavanlainen:

![Pakkausrakenne](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/kaavio1.jpg)

## Sovelluslogiikka

![Luokkakaavio](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio1.png)

## Toiminnallisuus

Sovelluksen toiminnallisuutta voi kuvailla esim. seuraavan kaavion kautta:

![Luokkakaavio](https://github.com/nikomn/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/kaavio2.png)

Oleellista on huomata kaaviosta, että käyttöliittymä kommunikoi käyttäjän ja tietokannan välillä
kulkevaa dataa DBWriter olion välityksellä. DBWriter pitää huolta tietokannan lukemisesta ja tietokantaan kirjoitamisesta.
