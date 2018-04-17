insert into user (id, username, password, email, role) values (1, 'user', 'u', 'user@alma.hu', 'USER');
insert into user (username, password, email, role) values ('user2', 'u2', 'user2@alma.hu', 'USER');
insert into user (username, password, email, role) values ('user3', 'u3', 'user3@alma.hu', 'USER');
insert into user (username, password, email, role) values ('admin', 'a', 'admin@alma.hu', 'ADMIN');
insert into user (username, password, email, role) values ('user4', 'u4', 'user4@alma.hu', 'USER');

insert into category(id, name) values (1, 'alapertelmezett');
insert into category(id, name) values (2, 'auto-motor');
insert into category(id, name) values (3, 'ruhazat');
insert into category(id, name) values (4, 'konyv');
insert into category(id, name) values (5, 'elektronika');


insert into item(description, buy_it_price, category_id, start_price, user_id, end_time, bid_increment) values ('nagyon jo kis leiras', 2500, 1, 1000, 1, timestamp '2017-10-12 21:22:23', 100);
insert into item(description, buy_it_price, category_id, start_price, user_id, end_time, bid_increment) values ('nagyon jo kis auto', 499999, 2, 300000, 2, timestamp '2017-10-12 21:22:23', 1000);
insert into item(description, buy_it_price, category_id, start_price, user_id, end_time, bid_increment) values ('a leggyorsabb auto', 499999, 2, 300000, 2, timestamp '2018-05-12 21:22:23', 2000);
insert into item(description, buy_it_price, category_id, start_price, user_id, end_time, bid_increment) values ('rossz leiras', 300000, 5, 100000, 3, timestamp '2018-07-20 10:10:10', 1000);
insert into item(description, buy_it_price, category_id, start_price, user_id, end_time, bid_increment) values ('rossz leiras ddd', 30000, 5, 100000, 1, timestamp '2018-08-20 10:10:10', 2000);

insert into bid(user_id, item_id, bid_offer) values (1, 2, 300000);
insert into bid(user_id, item_id, bid_offer) values (3, 1, 1000);
insert into bid(user_id, item_id, bid_offer) values (2, 3, 300000);
insert into bid(user_id, item_id, bid_offer) values (5, 4, 300000);


insert into image(path, item_id) values ('images/Cat.jpg', 1);
insert into image(path, item_id) values ('images/Cat.jpg', 2);
insert into image(path, item_id) values ('images/potato.png', 2);
