create database if not exists `inventory-svc`;

create user `db-user`@`%` identified by 'P@swr11d';
grant all privileges on *.* to `db-user`@`%`;