DROP DATABASE IF EXISTS den3_account;
USE DATABASE den3_account;
DROP TABLE IF EXISTS account_repository;
create table account_repository(uuid VARCHAR(255) PRIMARY KEY,mail VARCHAR(255),pass VARCHAR(255),nick VARCHAR(255),icon VARCHAR(255),last_login_time VARCHAR(255),permission VARCHAR(255));
USE TABLE account_repository;
insert into account_repository values ('SYS_ADMIN','ADMIN_MAIL','ADMIN_PASS','ADMIN','https://i.imgur.com/R6tktJ6.jpg','2020/11/04 17:13:00','ADMIN');