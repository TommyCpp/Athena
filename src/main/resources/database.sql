create table book
(
	isbn bigint not null
		primary key,
	title varchar(128) not null,
	subtitle varchar(128) null comment 'Additional info about title/Original title for book in other language',
	language varchar(32) default 'Chinese' not null comment 'English',
	author varchar(128) not null comment 'Multiple author should divided with comma.And should ordered by dictionary order',
	translator varchar(128) null comment 'Multiple translator should be divided with comma. And should ordered by dictionary order',
	publisher_id varchar(16) not null,
	publish_date date not null,
	category_id varchar(32) not null,
	version int default '1' not null,
	cover_url varchar(128) null,
	preface text null,
	directory text null comment 'should be json',
	introduction text null,
	price float default '0' not null,
	title_pinyin varchar(256) null,
	title_short_pinyin varchar(16) null
)
;

create table book_copy
(
	isbn bigint(11) not null,
	copy_id bigint default '0' not null,
	primary key (copy_id, isbn),
	constraint book_copy_book_isbn_fk
		foreign key (isbn) references book (isbn)
)
;

create index book_copy_book_isbn_fk
	on book_copy (isbn)
;

create table copy
(
	id bigint auto_increment
		primary key,
	status int null comment '0:new included;1:available;2:booked;3:checked out;4:reserved;5:damaged',
	created_date datetime null,
	updated_date datetime null
)
;

alter table book_copy
	add constraint book_copy_copy_id_fk
		foreign key (copy_id) references copy (id)
			on delete cascade
;

create table journal
(
	issn varchar(8) not null,
	year int default '0' not null,
	`index` int default '0' not null,
	title varchar(128) not null,
	publisher_id varchar(16) not null,
	title_short_pinyin int null,
	title_pinyin int null,
	price float not null,
	cover_url varchar(128) null,
	directory text null comment 'json',
	publish_date date null,
	primary key (year, issn, index)
)
;

create index journal_publisher_id_fk
	on journal (publisher_id)
;

create index journal_issn_index
	on journal (issn)
;

create index journal_year_index
	on journal (year)
;

create index journal_index_index
	on journal (`index`)
;

create table journal_copy
(
	issn varchar(8) not null,
	year int not null,
	`index` int default '0' not null,
	copy_id bigint default '0' not null,
	primary key (issn, year, index, copy_id),
	constraint journal_copy_journal_issn_year_index_fk
		foreign key (issn, year, index) references journal (issn, year, index),
	constraint journal_copy_copy_id_fk
		foreign key (copy_id) references copy (id)
			on delete cascade
)
;

create index journal_copy_copy_id_fk
	on journal_copy (copy_id)
;

create table publisher
(
	id varchar(16) not null comment 'Should be the publisher''s index in isbn'
		primary key,
	name varchar(128) not null,
	location varchar(128) null
)
;

alter table journal
	add constraint journal_publisher_id_fk
		foreign key (publisher_id) references publisher (id)
;

create table user
(
	id bigint auto_increment
		primary key,
	username varchar(64) not null,
	wechat_id varchar(64) not null,
	email varchar(64) not null,
	identity set('ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_READER') default 'ROLE_READER' not null,
	phone_number varchar(11) null,
	password varchar(64) not null
)
;

