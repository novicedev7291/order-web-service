use event-test;

insert into events values(11, 10931, 'Order', '{ "orderId": "10931" }', 1);
insert into events values(12, 10932, 'Order', '{ "orderId": "10932" }', 1);
insert into events values(13, 10933, 'Order', '{ "orderId": "10933" }', 1);
insert into events values(14, 10934, 'Order', '{ "orderId": "10934" }', 1);

update events set payload = '{ "orderId" : "10937" }' where id = 12;
update events set payload = '{ "orderId" : "11937" }' where id = 13;
