# Bid-Win

Szerver oldal: Dávid
Szépségfelelős: Kornél
Kliens oldal: Kat
Tesztelés: Zoli

Adatbázisterv:
User (id, pw, e-mail, dept, role) (dept=én így valósítanám meg a pénzes részét, nem akarok banki feltöltést / paypalt integrálni, de ha valaki akarja, megcsinálhatja)
Bids (id, auction_id, bidder_id, price)
Auction (id, seller_id, buyer_id, current_price, price_jump,S description, ending_time)
Image (id, auction_id, base64str)
