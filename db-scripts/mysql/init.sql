drop database if exists AMA;

create database AMA;

use AMA;

create table USERS (
	user_id varchar(30) not null,
	user_name varchar(50),
	created_timestamp timestamp not null default current_timestamp,
	updated_timestamp timestamp not null default current_timestamp on update current_timestamp,
	primary key (user_id)
);

create table POSTS (
	post_id int unsigned not null auto_increment,
	poster_id varchar(30) not null,
	post_content varchar(500) not null,
	post_rating smallint default 0,
	created_timestamp timestamp not null default current_timestamp,
	updated_timestamp timestamp not null default current_timestamp on update current_timestamp,
	primary key (post_id),
	index (poster_id),
	foreign key (poster_id)
		references USERS(user_id)
);

create table REPLIES (
	reply_id int unsigned not null auto_increment,
	replier_id varchar(30) not null,
	post_id int unsigned not null,
	reply_content varchar(5000) not null,
	reply_rating smallint default 0,
	primary key (reply_id),
	created_timestamp timestamp not null default current_timestamp,
	updated_timestamp timestamp not null default current_timestamp on update current_timestamp,
	index (post_id),
	index (replier_id),
	foreign key (post_id)
		references POSTS(post_id),
	foreign key (replier_id)
		references USERS(user_id)
);