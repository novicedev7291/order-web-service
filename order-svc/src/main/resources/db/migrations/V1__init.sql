create table if not exists `events` (
    id varchar(36) primary key,
    aggregate_id varchar(20) not null,
    aggregate_type varchar(20) not null,
    type varchar(30) not null,
    payload text not null,
    occurred_on datetime not null,
    version int not null
);

create table if not exists `orders` (
    id int auto_increment primary key,
    status tinyint(1) not null,
    customer_name varchar(100) not null,
    customer_shipping_address1 varchar(200) not null,
    customer_shipping_address2 varchar(200) null,
    customer_city varchar(100) not null,
    customer_state varchar(100) not null,
    customer_country varchar(100) not null,
    customer_phone varchar(20) not null,
    total_amount decimal(19,2) not null,
    total_tax decimal(19,2) not null,
    total_items int not null,
    created_on datetime not null,
    updated_on datetime not null
);

create table if not exists `orders_items` (
    order_id int not null,
    item_id int not null,
    price decimal(19,2) not null,
    qty int not null,
    total_amount decimal(19,2) not null,
    total_tax decimal(19,2) not null,
    key `FK_order_id` (`order_id`),
    constraint `FK_order_id` foreign key (`order_id`) references `orders` (`id`)
);