-- insert client details
insert into RESOURCE_CLIENT_DETAILS (ID, NAME, PASSWORD, IS_AUTO_APPROVE, CREATED_BY, CREATED_DATE)
values ('itime-registration', 'itime-registration', '$2a$11$52ykJMuT3eooYzfYkZYIF.kZaStN4AGBCMI43aFoBC/Br5QcqHp3G', 1,
        'migration', current_timestamp);
insert into RESOURCE_CLIENT_DETAILS (ID, NAME, PASSWORD, IS_AUTO_APPROVE, CREATED_BY, CREATED_DATE)
values ('itime-script-custody', 'itime-script-custody', '$2a$11$52ykJMuT3eooYzfYkZYIF.kZaStN4AGBCMI43aFoBC/Br5QcqHp3G', 1,
        'migration', current_timestamp);
insert into RESOURCE_CLIENT_DETAILS (ID, NAME, PASSWORD, IS_AUTO_APPROVE, CREATED_BY, CREATED_DATE)
values ('client-web', 'client-web', '$2a$11$52ykJMuT3eooYzfYkZYIF.kZaStN4AGBCMI43aFoBC/Br5QcqHp3G', 1,
        'migration', current_timestamp);

-- insert applications
insert into RESOURCE_APPLICATION(ID, NAME, CREATED_BY, CREATED_DATE) values ('itime', 'Integration Mandiri Testing Management', 'migration', current_timestamp);
insert into RESOURCE_APPLICATION(ID, NAME, CREATED_BY, CREATED_DATE) values ('blud', 'RBA - BLUD', 'migration', current_timestamp);

-- insert client detail application
insert into RESOURCE_CLIENT_APPLICATIONS(CLIENT_DETAIL_ID, APP_ID, CREATED_BY, CREATED_DATE)
values ('itime-registration', 'itime', 'migration', current_timestamp);
insert into RESOURCE_CLIENT_APPLICATIONS(CLIENT_DETAIL_ID, APP_ID, CREATED_BY, CREATED_DATE)
values ('itime-script-custody', 'itime', 'migration', current_timestamp);
insert into RESOURCE_CLIENT_APPLICATIONS(CLIENT_DETAIL_ID, APP_ID, CREATED_BY, CREATED_DATE)
values ('client-web', 'blud', 'migration', current_timestamp);

-- insert grant-types
insert into RESOURCE_CLIENT_GRANT_TYPES(ID, CLIENT_ID, GRANT_TYPE, CREATED_BY, CREATED_DATE) values(sys_guid(), 'itime-registration', 2, 'migration', current_timestamp);
insert into RESOURCE_CLIENT_GRANT_TYPES(ID, CLIENT_ID, GRANT_TYPE, CREATED_BY, CREATED_DATE) values(sys_guid(), 'itime-registration', 5, 'migration', current_timestamp);
insert into RESOURCE_CLIENT_GRANT_TYPES(ID, CLIENT_ID, GRANT_TYPE, CREATED_BY, CREATED_DATE) values(sys_guid(), 'itime-script-custody', 2, 'migration', current_timestamp);
insert into RESOURCE_CLIENT_GRANT_TYPES(ID, CLIENT_ID, GRANT_TYPE, CREATED_BY, CREATED_DATE) values(sys_guid(), 'itime-script-custody', 5, 'migration', current_timestamp);
