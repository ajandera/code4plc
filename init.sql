create table user
(
    id int auto_increment primary key,
    username   varchar(255) null,
    password   varchar(100) not null,
    salt       varchar(100) not null,
    last_login timestamp    null,
    constraint user_username_uindex
        unique (username)
);

create table application
(
    id int auto_increment primary key,
    name    varchar(50) not null,
    content longtext    not null,
    setting longtext    not null,
    user_id int         not null,
    constraint application_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

