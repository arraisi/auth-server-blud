-- "C_PGUN_GROUP" IS '1-PERENCANAAN ; 2-PENATAUSAHAAN ; 3- PERTANGGUNGJAWABAN ; 4-MONITORING';
-- "C_PGUN_OTOR" IS '0-PERENCANAAN SKPD BLUD / OPERATOR  ; 1-BENDAHARA SKPD ; 2- KEPALA SKPD ; 3-DINAS TEKNIS; 4-TAPD;  8-ADMIN  ;  9-SUPER ADMIN';

-- data otoritas
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('0', 'OPERATOR / PERENCANAAN SKPD', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('1', 'BENDAHARA SKPD', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('2', 'KEPALA SKPD', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('3', 'DINAS TEKNIS', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('4', 'TAPD', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('8', 'ADMIN', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('9', 'SUPER ADMIN', 'migration', current_timestamp);

-- data group blud
insert into AUTH_GROUP_BLUD (ID, GROUP_NAME, CREATED_BY, CREATED_DATE)
values ('1', 'PERENCANAAN', 'migration', current_timestamp);
insert into AUTH_GROUP_BLUD (ID, GROUP_NAME, CREATED_BY, CREATED_DATE)
values ('2', 'PENATAUSAHAAN', 'migration', current_timestamp);
insert into AUTH_GROUP_BLUD (ID, GROUP_NAME, CREATED_BY, CREATED_DATE)
values ('3', 'PERTANGGUNGJAWABAN', 'migration', current_timestamp);
insert into AUTH_GROUP_BLUD (ID, GROUP_NAME, CREATED_BY, CREATED_DATE)
values ('4', 'MONITORING', 'migration', current_timestamp);

-- data menu otoritas

-- data Dinas Teknis
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('005', '3', '005', 'migration', current_timestamp);

-- data OPERATOR
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('007', '0', '001', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('008', '0', '002', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('009', '0', '003', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('010', '0', '004', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('012', '0', '006', 'migration', current_timestamp);

-- data BENDAHARA SKPD
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('017', '1', '005', 'migration', current_timestamp);

-- data KEPALA SKPD BLUD
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('023', '2', '005', 'migration', current_timestamp);

insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('025', '4', '001', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('026', '4', '002', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('027', '4', '003', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('028', '4', '004', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('029', '4', '005', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('030', '4', '006', 'migration', current_timestamp);

insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('031', '8', '001', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('032', '8', '002', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('033', '8', '003', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('034', '8', '004', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('035', '8', '005', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('036', '8', '006', 'migration', current_timestamp);

insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('037', '9', '001', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('038', '9', '002', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('039', '9', '003', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('040', '9', '004', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('041', '9', '005', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('042', '9', '006', 'migration', current_timestamp);