Create table users(
                     id bigint primary key auto_increment,
                     user_name   varchar (225) NOT NULL ,
                     email varchar (255) unique  not null ,
                     password varchar(255) not null ,
                    role varchar(100)not null
);
