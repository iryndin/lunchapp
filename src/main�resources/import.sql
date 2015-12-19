insert into restaurant(id, name, deleted, create_date, update_date) values (1, 'Ginza', false, current_timestamp(), current_timestamp());
insert into restaurant(id, name, deleted, create_date, update_date) values (2, 'Tokyo', false, current_timestamp(), current_timestamp());
insert into restaurant(id, name, deleted, create_date, update_date) values (3, 'Lotos', false, current_timestamp(), current_timestamp());
insert into restaurant(id, name, deleted, create_date, update_date) values (4, 'Vivat', true, current_timestamp(), current_timestamp());
insert into restaurant(id, name, deleted, create_date, update_date) values (5, 'Saint Petersburg', true, current_timestamp(), current_timestamp());

insert into appuser(id, role, username, password) values (1, 'admin', 'admin1',    encrypt('AES', '123456', stringtoutf8('admin1:apassword1')));
insert into appuser(id, role, username, password) values (2, 'admin', 'admin2',    encrypt('AES', '123456', stringtoutf8('admin2:apassword1')));
insert into appuser(id, role, username, password) values (3, 'plainuser', 'user1', encrypt('AES', '123456', stringtoutf8('user1:upassword1')));
insert into appuser(id, role, username, password) values (4, 'plainuser', 'user2', encrypt('AES', '123456', stringtoutf8('user2:upassword2')));
insert into appuser(id, role, username, password) values (5, 'plainuser', 'user3', encrypt('AES', '123456', stringtoutf8('user3:upassword3')));

insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (1,1,false,'Any Time Frittata', 11.50, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (2,1,false,'Breakfast Bagel', 3.47, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (3,1,false,'Granola with Fresh Fruit', 4.50, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (4,2,false,'Healthier Lifestyle Tea', 0.90, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (5,2,false,'Healthy Breakfast Frittata', 8.99, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (6,2,false,'High Energy Breakfast Shake', 5.00, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (7,3,false,'High Fiber Cereal', 4.50, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (8,3,false,'Italian Tofu Frittata', 8.40, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (9,3,false,'Mushroom, Tomato, Basil Frittata', 11.50, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (10,3,false,'Perfect Oatmeal', 3.50, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (11,4,false,'Poached Huevos Rancheros', 7.10, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (12,4,false,'Quinoa Cereal with Fresh Fruit', 9.50, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (13,5,false,'Strawberry Smoothie', 2.50, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (14,5,false,'Swiss Breakfast', 9.99, current_timestamp(), current_timestamp());
insert into menu(id, restaurant_id, deleted, name, price, create_date, update_date) values (15,5,false,'Yogurt with Fruit', 3.99, current_timestamp(), current_timestamp());