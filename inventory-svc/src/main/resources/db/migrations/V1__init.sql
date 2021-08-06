create table if not exists `items` (
    id int auto_increment primary key,
    sku varchar(50) not null,
    name varchar(200) not null,
    price decimal(19,2) not null,
    created_on datetime not null,
    updated_on datetime not null,
    version int not null
);

create table if not exists `item_inventory` (
    item_id int primary key,
    free_qty int not null,
    committed_qty int not null,
    created_on datetime not null,
    updated_on datetime not null,
    key `FK_item_id` (`item_id`),
    constraint `FK_item_id` foreign key (`item_id`) references `items` (`id`)
);

create table if not exists `events` (
    id varchar(36) primary key,
    aggregate_id varchar(20) not null,
    aggregate_type varchar(20) not null,
    type varchar(30) not null,
    payload text not null,
    occurred_on datetime not null,
    version int not null
);