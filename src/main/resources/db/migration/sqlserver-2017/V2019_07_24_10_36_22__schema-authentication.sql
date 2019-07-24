create table Auth.Users
(
    Id                 varchar(64)  not null primary key,
    Username           varchar(100) not null unique,
    Password           varchar(255) not null,
    Email              varchar(50)  not null,
    IsActive           bit          not null default 0,
    IsKeepActive       bit          not null default 0,
    IsLocked           bit          not null default 0,
    IsSudo             bit          not null default 0,
    LoginFailedCounter int          not null default 0,
    CreatedBy          varchar(100) not null,
    CreatedDate        datetime     not null default current_timestamp,
    LastUpdateBy       varchar(100),
    LastUpdateDate     datetime
);

create sequence Auth.RolesSeq start with 1 increment by 1;

create table Auth.Roles
(
    Id          bigint       not null primary key default next value for Auth.RolesSeq,
    Name        varchar(100) not null unique,
    Description text
);

create table Auth.Privileges
(
    Id             varchar(64)  not null primary key default newid(),
    Name           varchar(100) not null,
    Description    text,
    CreatedBy      varchar(100) not null,
    CreatedDate    datetime     not null             default current_timestamp,
    LastUpdateBy   varchar(100),
    LastUpdateDate datetime
);

create table Auth.Authorities
(
    Id          varchar(64)  not null primary key default newid(),
    PrivilegeId varchar(64)  not null,
    RoleId      bigint       not null,
    CreatedBy   varchar(100) not null,
    CreatedDate datetime     not null             default current_timestamp
);

alter table Auth.Authorities
    add constraint FkAuthoritiesPrivilegeId foreign key (PrivilegeId)
        references Auth.Privileges (Id) on update cascade on delete cascade;

alter table Auth.Authorities
    add constraint FkAuthoritiesRoleId foreign key (RoleId)
        references Auth.Roles (Id) on update cascade on delete cascade;

alter table Auth.Authorities
    add constraint UqAuthorities unique (PrivilegeId, RoleId);

create table Auth.UserPrivileges
(
    Id          varchar(64) not null primary key default newid(),
    PrivilegeId varchar(64) not null,
    UserId      varchar(64) not null,
    CreatedBy   varchar(64) not null,
    CreatedDate datetime    not null             default current_timestamp
);

alter table Auth.UserPrivileges
    add constraint FkUserPrivilegesPrivilegeId foreign key (PrivilegeId)
        references Auth.Privileges (Id) on update cascade on delete cascade;

alter table Auth.UserPrivileges
    add constraint FkUserPrivilegesUserId foreign key (UserId)
        references Auth.Users (Id) on update cascade on delete cascade;


