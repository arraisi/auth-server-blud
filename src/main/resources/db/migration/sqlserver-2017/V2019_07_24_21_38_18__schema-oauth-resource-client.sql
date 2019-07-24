create table Resources.ClientDetails
(
    Id                   varchar(64)  not null primary key default newid(),
    Name                 varchar(50)  not null unique,
    Password             varchar(255) not null,
    IsAutoApprove        bit                               default 0,
    TokenExpiredInSecond int          not null             default 43200,
    CreatedBy            varchar(100) not null,
    CreatedDate          datetime     not null             default current_timestamp,
    LastUpdateBy         varchar(100),
    LastUpdateDate       datetime
);

create table Resources.Applications
(
    Id             varchar(64)  not null primary key default newid(),
    Name           varchar(50)  not null unique,
    CreatedBy      varchar(100) not null,
    CreatedDate    datetime     not null             default current_timestamp,
    LastUpdateBy   varchar(100),
    LastUpdateDate timestamp
);

create table Resources.ClientDetailApplications
(
    Id             varchar(64)  not null primary key default newid(),
    ClientDetailId varchar(64)  not null,
    AppId          varchar(64)  not null,
    CreatedBy      varchar(100) not null,
    CreatedDate    datetime     not null             default current_timestamp,
    LastUpdateBy   varchar(64),
    LastUpdateDate datetime
);

alter table Resources.ClientDetailApplications
    add constraint FkResourceClientDetailAppClientDetailId foreign key (ClientDetailId)
        references Resources.ClientDetails (Id) on update cascade on delete cascade;

alter table Resources.ClientDetailApplications
    add constraint FkResourceClientDetailAppAppId foreign key (AppId)
        references Resources.Applications (Id) on update cascade on delete cascade;

alter table Resources.ClientDetailApplications
    add constraint UqResourceClientDetailApp unique (ClientDetailId, AppId);

create table Resources.ClientDetailRedirectUris
(
    Id          varchar(64)  not null primary key default newid(),
    ClientId    varchar(64)  not null,
    RedirectUri varchar(150) not null
);

alter table Resources.ClientDetailRedirectUris
    add constraint FkResourceClientDetailClientId foreign key (ClientId)
        references Resources.ClientDetails (Id) on update cascade on delete cascade;

alter table Resources.ClientDetailRedirectUris
    add constraint UqResourceClientDetailUrls unique (ClientId, RedirectUri);

create table Resources.ClientDetailScopes
(
    Id       varchar(64) not null primary key default newid(),
    ClientId varchar(64) not null,
    ScopeId  varchar(64) not null
);

alter table Resources.ClientDetailScopes
    add constraint FkResourceClientDetailScopeClientId foreign key (ClientId)
        references Resources.ClientDetails (Id) on update cascade on delete cascade;

alter table Resources.ClientDetailScopes
    add constraint FkREsourceClientDetailScopeScopeId foreign key (ScopeId)
        references OAuth.ClientScope (Id) on update cascade on delete cascade;

alter table Resources.ClientDetailScopes
    add constraint UqClientDetailScope unique (ClientId, ScopeId);

create table Resources.ClientDetailGrantTypes
(
    Id             varchar(64)  not null primary key default newid(),
    ClientId       varchar(64)  not null,
    GrantType      bigint       not null,
    CreatedBy      varchar(100) not null,
    CreatedDate    datetime     not null             default current_timestamp,
    LastUpdateBy   varchar(100),
    LastUpdateDate datetime
);

alter table Resources.ClientDetailGrantTypes
    add constraint FkResourceClientGranTypeClientId foreign key (ClientId)
        references Resources.ClientDetails (Id) on update cascade on delete cascade;

alter table Resources.ClientDetailGrantTypes
    add constraint FkResourceClientGrantTypeGrantType foreign key (GrantType)
        references OAuth.GrantTypes (Id) on update cascade on delete cascade;

alter table Resources.ClientDetailGrantTypes
    add constraint UqResourceClientDetailGrantType unique (ClientId, GrantType);
