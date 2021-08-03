create database if not exists `inventory-svc`;
create database if not exists `event-test`;

create user `db-user`@`%` identified by 'P@swr11d';
grant all privileges on *.* to `db-user`@`%`;

use event-test;

create table if not exists `events`(
    id int primary key,
    aggregate_id int not null,
    aggregate_type varchar(50) not null,
    payload text not null,
    version int not null
);
