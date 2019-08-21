create table AUTH_OTORITAS_BLUD
(
    id           varchar2(64) default sys_guid()        not null primary key,
    otoritas_name  varchar2(64)                           not null,
    created_by   varchar2(100)                          not null,
    created_date timestamp    default current_timestamp not null
);

create table AUTH_GROUP_BLUD
(
    id           varchar2(64) default sys_guid()        not null primary key,
    group_name   varchar2(64)                           not null,
    created_by   varchar2(100)                          not null,
    created_date timestamp    default current_timestamp not null
);

create table AUTH_GROUP_OTORITAS
(
    id           varchar2(64)                        not null primary key,
    group_id     varchar2(64)                        not null,
    otoritas_id  varchar2(64)                        not null,
    created_by   varchar2(100)                       not null,
    created_date timestamp default current_timestamp not null
);

alter table AUTH_GROUP_OTORITAS
    add constraint fk_authorities_group_id foreign key (group_id)
        references AUTH_GROUP_OTORITAS (id) on delete cascade;

alter table AUTH_GROUP_OTORITAS
    add constraint fk_authorities_otoritas_id foreign key (otoritas_id)
        references AUTH_OTORITAS_BLUD (id) on delete cascade;

create table component_menu_map_by_otoritas
(
    id           varchar2(64) default sys_guid()        not null primary key,
    otoritas_id  varchar2(64)                           not null,
    menu_id      varchar2(64)                           not null,
    created_by   varchar2(100)                          not null,
    created_date timestamp    default current_timestamp not null
);

alter table component_menu_map_by_otoritas
    add constraint fk_menu_map_otoritas_id foreign key (otoritas_id)
        references AUTH_OTORITAS_BLUD (id) on delete cascade;

alter table component_menu_map_by_otoritas
    add constraint fk_menu_map_menu_id foreign key (menu_id)
        references component_menus (id) on delete cascade;