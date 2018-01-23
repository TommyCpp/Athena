create table audio
(
	isrc varchar(14) not null
		primary key,
	title varchar(128) not null,
	subtitle varchar(128) null,
	author varchar(128) not null,
	translator varchar(128) null,
	publisher_id varchar(16) not null,
	publish_date date not null,
	cover_url varchar(128) null,
	price float default '0' not null,
	title_pinyin varchar(256) null,
	title_short_pinyin varchar(16) null,
	language varchar(32) default 'Chinese' null
)
;

create index audio_publisher_id_fk
	on audio (publisher_id)
;

create table audio_copy
(
	isrc varchar(14) not null,
	copy_id bigint default '0' not null,
	primary key (copy_id, isrc),
	constraint audio_copy_audio_isrc_fk
	foreign key (isrc) references audio (isrc)
)
;

create index audio_copy_audio_isrc_fk
	on audio_copy (isrc)
;

create table block
(
	user_id bigint not null
		primary key,
	handler_id bigint not null,
	created_at datetime null
)
;

create index block_user_handler_id_id_fk
	on block (handler_id)
;

create table book
(
	isbn bigint not null
		primary key,
	title varchar(128) not null,
	subtitle varchar(128) null comment 'Additional info about title/Original title for book in other language',
	language varchar(32) default 'Chinese' not null comment 'English',
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

create table book_author
(
	isbn bigint default '0' not null,
	author_name varchar(128) default '' not null,
	primary key (isbn, author_name),
	constraint book_author_book_isbn_fk
	foreign key (isbn) references book (isbn)
		on update cascade on delete cascade
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

create table book_translator
(
	isbn bigint default '0' not null,
	translator_name varchar(128) default '' not null,
	primary key (isbn, translator_name),
	constraint book_translator_book_isbn_fk
	foreign key (isbn) references book (isbn)
		on update cascade on delete cascade
)
;

create table borrow
(
	id varchar(128) not null
		primary key,
	copy_id bigint not null,
	user_id bigint not null,
	enable tinyint(1) default '1' not null comment 'Is the copy is being borrowed?',
	type varchar(32) not null comment 'type of the copy',
	created_date datetime null,
	updated_date datetime null
)
	comment 'relationship  between the copy and user'
;

create index borrow_copy_id_fk
	on borrow (copy_id)
;

create index borrow_user_id_fk
	on borrow (user_id)
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

alter table borrow
	add constraint borrow_copy_id_fk
foreign key (copy_id) references copy (id)
;

create table journal
(
	issn varchar(8) not null,
	year int default '0' not null,
	issue int default '0' not null,
	title varchar(128) not null,
	publisher_id varchar(16) not null,
	title_short_pinyin varchar(16) null,
	title_pinyin varchar(128) null,
	price float not null,
	cover_url varchar(128) null,
	directory text null comment 'json',
	publish_date date null,
	language varchar(32) default 'Chinese' not null,
	primary key (year, issn, issue)
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
	on journal (issue)
;

create table journal_copy
(
	issn varchar(8) not null,
	year int not null,
	issue int default '0' not null,
	copy_id bigint default '0' not null,
	primary key (issn, year, issue, copy_id),
	constraint journal_copy_journal_issn_year_issue_fk
	foreign key (issn, year, issue) references journal (issn, year, issue),
	constraint journal_copy_journal_issn_fk
	foreign key (issn) references journal (issn),
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

alter table audio
	add constraint audio_publisher_id_fk
foreign key (publisher_id) references publisher (id)
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
	phone_number varchar(11) null,
	password varchar(64) not null
)
;

alter table block
	add constraint block_user_user_id_id_fk
foreign key (user_id) references user (id)
	on update cascade on delete cascade
;

alter table block
	add constraint block_user_handler_id_id_fk
foreign key (handler_id) references user (id)
	on update cascade
;

alter table borrow
	add constraint borrow_user_id_fk
foreign key (user_id) references user (id)
;

create table user_identity
(
	user_id bigint default '0' not null,
	identity varchar(32) default '' not null,
	primary key (user_id, identity),
	constraint user_identity_user_id_fk
	foreign key (user_id) references user (id)
		on update cascade on delete cascade
)
;

