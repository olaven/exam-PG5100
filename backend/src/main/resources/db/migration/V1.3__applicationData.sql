
drop table if exists item;
drop table if exists item_ranks;
drop table if exists rank;
drop table if exists user_entity cascade;
drop table if exists user_entity_roles;
create table item (id bigint not null, category varchar(255), description varchar(355), title varchar(55), primary key (id));
create table item_ranks (item_id bigint not null, ranks_item_id bigint not null, ranks_user_email varchar(255) not null);
create table rank (score integer check (score<=5 AND score>=1), item_id bigint not null, user_email varchar(255) not null, primary key (item_id, user_email));
create table user_entity (email varchar(255) not null, enabled boolean not null, family_name varchar(55), given_name varchar(55), password varchar(340), primary key (email));
create table user_entity_roles (user_entity_email varchar(255) not null, roles varchar(255));
alter table item_ranks add constraint UK_hwxc3rox7n3g7nqf6ov28yxaw unique (ranks_item_id, ranks_user_email);
alter table item_ranks add constraint FK77ido76muq5f95xyjlgtfl4hb foreign key (ranks_item_id, ranks_user_email) references rank;
alter table item_ranks add constraint FKno6yog69x7a3g7bochqmfp924 foreign key (item_id) references item;
alter table rank add constraint FKg8p3bu3hrfx6lmx13bdw7ioga foreign key (item_id) references item;
alter table rank add constraint FK4fxcx6mer4q239mpvfx0no3pe foreign key (user_email) references user_entity;
alter table user_entity_roles add constraint FKn6hs2xulrxb06oc101wxtihwv foreign key (user_entity_email) references user_entity;
