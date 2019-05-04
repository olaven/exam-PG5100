create sequence hibernate_sequence start with 1 increment by 1;
create table user_entity (username varchar(255) not null, enabled boolean not null, password varchar(255), primary key (username));
create table user_entity_roles (user_entity_username varchar(255) not null, roles varchar(255));
alter table user_entity_roles add constraint FKnkblne5vj59d4pbt8r3n8tifd foreign key (user_entity_username) references user_entity;