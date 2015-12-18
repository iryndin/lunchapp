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