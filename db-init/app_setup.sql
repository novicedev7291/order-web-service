create database if not exists `inventory-svc`;
create database if not exists `web-svc`;

create user `db-user`@`%` identified by 'P@swr11d';
grant all privileges on *.* to `db-user`@`%`;
