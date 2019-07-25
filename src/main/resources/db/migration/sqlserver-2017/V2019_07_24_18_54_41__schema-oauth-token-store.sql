create table Oauth.AccessToken
(
    AuthId         varchar(255)   not null primary key,
    TokenId        varchar(255)   not null,
    Token          varbinary(max) not null,
    Username       varchar(100),
    ClientId       varchar(255),
    Authentication varbinary(max),
    RefreshToken   varchar(255),
    IpAddress      varchar(20)    not null default '127.0.0.1',
    LoginAt        datetime       not null default current_timestamp
);

create table Oauth.RefreshToken
(
    TokenId        varchar(255),
    Token          varbinary(max),
    Authentication varbinary(max)
);

create table Oauth.HistoryAccessToken
(
    Id        varchar(255)   not null primary key,
    AccessId  varchar(255) not null,
    Token     varbinary(max) not null,
    ClientId  varchar(255),
    IpAddress varchar(20)    not null,
    Username  varchar(100)   not null,
    LoginAt   datetime       not null default current_timestamp,
    IsLogout  bit            not null default 0,
    LogoutAt  datetime,
    LogoutBy  varchar(100)
);

create sequence Oauth.GrantTypesSeq start with 1 increment by 1;
create table Oauth.GrantTypes
(
    Id          bigint      not null primary key default next value for Oauth.GrantTypesSeq,
    Name        varchar(50) not null unique,
    Description text
);

insert into Oauth.GrantTypes(Name)
values ('authorization_code'),
       ('password'),
       ('implicit'),
       ('client_credentials'),
       ('refresh_token');

create table Oauth.ClientScope
(
    Id             varchar(64)  not null primary key default newid(),
    Name           varchar(50)  not null unique,
    CreatedBy      varchar(100) null,
    CreatedDate    datetime     not null             default current_timestamp,
    LastUpdateBy   varchar(100),
    LastUpdateDate datetime
);
