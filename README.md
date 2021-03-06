﻿# Bid-Win
<br>
Szerver oldal: Dávid <br>
Szépségfelelős: Kornél <br>
Kliens oldal: Kat <br>
Tesztelés: Zoli <br>
<br>

## Backend megvalósítása

### Fejlesztői környezet: NetBeans, STS

#### Felhasznált eszközök

* Spring Boot
* Maven
* JPA
* H2
* Lombok
* Git, GitHub
* Postman, ARC (teszteléshez)
* https://www.jacoco.org/jacoco/ (teszteléshez)
* Jenkins

##### Code coverage report(jacoco):
![Code coverage](BidAndWin/docs/images/code_coverage.png)

##### Jenkins beállítása:
![Jenkins beállítása](BidAndWin/docs/images/jenkins1.png)
![Jenkins beállítása2](BidAndWin/docs/images/jenkins2.png)
![Jenkins beállítása3](BidAndWin/docs/images/jenkins3.png)

##### Jenkins futtatása:
![Jenkins futtatása](BidAndWin/docs/images/jenkins5.png)

## Frontend megvalósítása

### Fejlesztői környezet: Visual Studio Code

#### Felhasznált eszközök
 * Node.js
 * Angular
 * DeepScan (https://marketplace.visualstudio.com/items?itemName=DeepScan.vscode-deepscan)
 
#### Fejlesztői környezet beállítása, indítása
### Backend
  * A pom.xml tartalmazza a használt függőségeket
  * az application.properties-ben a h2-re vonatkozó beállításokat alkalmaztunk
  * az import.sql fájlból az alkalmazás indításakor feltölti a generált táblákat
  * indítás grafikus környezetből(NetBeans) vagy parancssorból: mvn spring-boot:run paranccsal.
  * a backend elérhetősége: http://localhost:8080/ 

### Fronend
  * npm install segítségével a függőségeket telepítjük
  * npm start segítségével elindítjuk a frontendet
  * a frontend elérhetősége http://localhost:4200/
 
