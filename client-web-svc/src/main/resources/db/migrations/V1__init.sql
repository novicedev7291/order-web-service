create table if not exists `inventory` (
    id int not null primary key,
    sku varchar(50) not null,
    name varchar(200) not null,
    free_qty int not null default 0,
    price decimal(19,2) not null
);

create table if not exists `consumed_events` (
    id varchar(36) not null primary key,
    type varchar(50) not null,
    payload text not null,
    added_on datetime not null
);