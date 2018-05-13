# Bid-Win
<br>
Szerver oldal: Dávid <br>
Szépségfelelős: Kornél <br>
Kliens oldal: Kat <br>
Tesztelés: Zoli <br>
<br>
Adatbázisterv:<br>
User (id, pw, e-mail, dept, role)<br>
Bids (id, auction_id, bidder_id, price)<br>
Auction (id, seller_id, buyer_id, current_price, price_jump, description, ending_time)<br>
Image (id, auction_id, base64str)<br>

## Backend megvalósítása

### Fejlesztői környezet: NetBeans, STS

#### Felhasznált eszközök

* Spring Boot
* Maven
* JPA
* H2
* Lombok
* Git, GiHub
* Postman, ARC (teszteléshez)
* https://www.eclemma.org/ (teszteléshez)
* Jenkins

##### Jenkins beállítása:
![Jenkins beállítása](docs/images/jenkins1.png)
![Jenkins beállítása2](docs/images/jenkins1.png)
![Jenkins beállítása3](docs/images/jenkins1.png)
![Jenkins futtatása](docs/images/jenkins4.png)

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
 
