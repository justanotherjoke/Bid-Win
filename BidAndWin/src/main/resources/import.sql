insert into user (id, username, password, email, role) values (1, 'user', 'u', 'user@alma.hu', 'USER');
insert into user (username, password, email, role) values ('user2', 'u2', 'user2@alma.hu', 'USER');
insert into user (username, password, email, role) values ('user3', 'u3', 'user3@alma.hu', 'USER');
insert into user (username, password, email, role) values ('admin', 'a', 'admin@alma.hu', 'ADMIN');

insert into bidder(id, credit, user_id) values(1, 100, 1);
insert into bidder(credit, user_id) values(100, 2);
insert into bidder(credit, user_id) values(100, 4);

insert into seller(credit, user_id) values(100, 2);
insert into seller(credit, user_id) values(100, 3);
insert into seller(credit, user_id) values(100, 1);

insert into category(id, name) values (1, 'alapertelmezett');
insert into category(id, name) values (2, 'auto-motor');
insert into category(id, name) values (3, 'ruhazat');
insert into category(id, name) values (4, 'konyv');


insert into item(description, sold_price, category_id, start_price, seller_id) values ('nagyon jo kis leiras', 2500, 1, 1000, 1);
insert into item(description, sold_price, category_id, start_price, seller_id) values ('nagyon jo kis auto', 499999, 2, 300000, 2);

insert into bid(bidder_id, item_id) values (1, 2);
insert into bid(bidder_id, item_id) values (3, 1);

insert into image(url, item_id) values ('https://www.callofdutycheat.net/wp-content/uploads/best-price-logo.jpg', 1);
insert into image(url, item_id) values ('https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/TeamTimeCar.com-BTTF_DeLorean_Time_Machine-OtoGodfrey.com-JMortonPhoto.com-07.jpg/1200px-TeamTimeCar.com-BTTF_DeLorean_Time_Machine-OtoGodfrey.com-JMortonPhoto.com-07.jpg', 2);
insert into image(url, item_id) values ('https://www.telegraph.co.uk/cars/images/2016/06/24/101519846_DeLorean-DMC-12-ReduxEyevine-xlarge_trans_NvBQzQNjv4BqqMKo2z_7YU_WfwkpJGaSMkWQ2KsSeoXntDmU1Wc8Jvc.jpg', 2);