drop table if exists user_entity_roles;
drop table if exists user_entity;
create table user_entity (email varchar(255) not null, enabled boolean not null, family_name varchar(55), given_name varchar(55), password varchar(340), primary key (email));
create table user_entity_roles (user_entity_email varchar(255) not null, roles varchar(255));
alter table user_entity_roles add constraint FKn6hs2xulrxb06oc101wxtihwv foreign key (user_entity_email) references user_entity;
