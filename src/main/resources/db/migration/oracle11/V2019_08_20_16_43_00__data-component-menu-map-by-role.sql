-- "C_PGUN_GROUP" IS '1-PERENCANAAN ; 2-PENATAUSAHAAN ; 3- PERTANGGUNGJAWABAN ; 4-MONITORING';
-- "C_PGUN_OTOR" IS '0-DINAS TEKNIS  ; 1-PERENCANAAN SKPD BLUD ; 2- PEJABAT KEUANGAN SKPD BLUD ; 3-KEPALA SKPD BLUD SKPD BLUD; 4-TAPD;  8-ADMIN  ;  9-SUPER ADMIN';

-- data otoritas
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('0', 'DINAS TEKNIS', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('1', 'PERENCANAAN SKPD BLUD', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('2', 'PEJABAT KEUANGAN SKPD BLUD', 'migration', current_timestamp);
insert into AUTH_OTORITAS_BLUD (ID, OTORITAS_NAME, CREATED_BY, CREATED_DATE)
values ('3', 'KEPALA SKPD BLUD SKPD BLUD', 'migration', current_timestamp);
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
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('005', '0', '005', 'migration', current_timestamp);

insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('007', '1', '001', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('008', '1', '002', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('009', '1', '003', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('010', '1', '004', 'migration', current_timestamp);
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('012', '1', '006', 'migration', current_timestamp);

-- data pejabat keuangan
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('017', '2', '005', 'migration', current_timestamp);

-- data pimpinan BLUD / KEPALA SKPD BLUD SKPD BLUD
insert into COMPONENT_MENU_MAP_BY_OTORITAS(ID, OTORITAS_ID, MENU_ID, CREATED_BY, CREATED_DATE)
VALUES ('023', '3', '005', 'migration', current_timestamp);

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