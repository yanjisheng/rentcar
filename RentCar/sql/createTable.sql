use rentcar;
select * from cars;
select auto_increment from information_schema.tables where table_schema = 'rentcar' and table_name = 'cars';
select * from customers;
select * from rent;
#--汽车表：汽车编号，汽车信息，汽车租金
create table cars
(car_id int(4) primary key auto_increment,
car_detail varchar(200) unique,
car_cost double);
#--顾客表：顾客编号，顾客姓名，顾客地址
create table customers
(customer_id int(4) primary key AUTO_INCREMENT,
customer_name varchar(30) unique,
customer_address varchar(120));
#--出租信息表：订单id，汽车，顾客，订单日期，出租天数，订单状态(1未支付，2未取车，3已取车，4已还车)
create table rent
(rent_id int(6) primary key auto_increment,
rent_car int(4),
rent_customer int(4),
rent_date date,
rent_days int(3),
rent_status int(1),
foreign key (rent_car) references cars(car_id),
foreign key (rent_customer) references customers(customer_id));
#--插入数据
insert into cars(car_detail, car_cost) values('宝马X5', 800);
insert into cars(car_detail, car_cost) values('比亚迪M7', 500);
insert into cars(car_detail, car_cost) values('凯迪拉克XT5', 900);
insert into customers(customer_name, customer_address) values('张三', '北京市朝阳区');
insert into customers(customer_name, customer_address) values('李四', '沈阳市和平区');
insert into customers(customer_name, customer_address) values('王五', '广州市白云区');
#--添加视图
create view view_rent as
select r.rent_id, a.car_id, a.car_detail, c.customer_id, c.customer_name, r.rent_date, r.rent_days, r.rent_status 
from rent r, cars a, customers c 
where r.rent_car = a.car_id and r.rent_customer = c.customer_id;
#create view view_cars as
#select distinct a.car_id, a.car_detail, a.car_cost, r.rent_status from rent r, cars a
#where r.rent_car(+) = a.car_id;
create view view_cars as
select distinct a.car_id, a.car_detail, a.car_cost, r.rent_status from rent r
right join cars a on r.rent_car = a.car_id;
