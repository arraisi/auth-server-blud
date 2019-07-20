insert into AUTH_USERS
(ID, USERNAME, PASSWORD, EMAIL, IS_ACTIVE, IS_KEEP_ACTIVE, IS_LOCKED, IS_SUDO,
 LOGIN_FAILED_COUNTER, CREATED_BY, CREATED_DATE, LAST_UPDATE_BY, LAST_UPDATE_DATE)
values ('admin', 'admin', '$2y$11$NRCjQ4UPgofnirMQEXYerOPS5ycxx9CaR1wsTfrS2FB17mURU2HTy', 'admin@gmail.com', 1, 0, 0, 1,
        0, 'migration', current_timestamp, null, null);

insert into AUTH_USERS
(ID, USERNAME, PASSWORD, EMAIL, IS_ACTIVE, IS_KEEP_ACTIVE, IS_LOCKED, IS_SUDO,
 LOGIN_FAILED_COUNTER, CREATED_BY, CREATED_DATE, LAST_UPDATE_BY, LAST_UPDATE_DATE)
values ('user', 'user', '$2y$11$dh.ucxCF1yie3jfFMnbu8uRfBQ1cXPVBGBwg57SLpy6mUhh2eBsbm', 'user@gmail.com', 1, 0, 0, 0,
        0, 'migration', current_timestamp, null, null);

insert into AUTH_ROLES (ID, NAME, DESCRIPTION)
values (ROLES_SEQ.nextval, 'ROLE_PUBLIC', 'Public role');
insert into AUTH_ROLES (ID, NAME, DESCRIPTION)
values (ROLES_SEQ.nextval, 'ROLE_CREATE_USER', 'Untuk membuat user');
insert into AUTH_ROLES (ID, NAME, DESCRIPTION)
values (ROLES_SEQ.nextval, 'ROLE_VIEW_USER', 'Untuk melihat user');

insert into AUTH_PRIVILEGES(ID, NAME, DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATE_BY, LAST_UPDATE_DATE)
VALUES ('register-user', 'PRIVILEGE_REGISTER_USER', null, 'migration', current_timestamp, null, null);
insert into AUTH_PRIVILEGES(ID, NAME, DESCRIPTION, CREATED_BY, CREATED_DATE, LAST_UPDATE_BY, LAST_UPDATE_DATE)
VALUES ('public', 'PRIVILEGE_PUBLIC', null, 'migration', current_timestamp, null, null);

insert into AUTH_AUTHORITIES(ID, PRIVILEGE_ID, ROLE_ID, CREATED_BY, CREATED_DATE)
VALUES (SYS_GUID(), 'public', 1, 'migration', current_timestamp);

insert into AUTH_AUTHORITIES(ID, PRIVILEGE_ID, ROLE_ID, CREATED_BY, CREATED_DATE)
VALUES (SYS_GUID(), 'register-user', 1, 'migration', current_timestamp);
insert into AUTH_AUTHORITIES(ID, PRIVILEGE_ID, ROLE_ID, CREATED_BY, CREATED_DATE)
VALUES (SYS_GUID(), 'register-user', 2, 'migration', current_timestamp);
insert into AUTH_AUTHORITIES(ID, PRIVILEGE_ID, ROLE_ID, CREATED_BY, CREATED_DATE)
VALUES (SYS_GUID(), 'register-user', 3, 'migration', current_timestamp);


insert into AUTH_USER_PRIVILEGES(ID, PRIVILEGE_ID, USER_ID, CREATED_BY, CREATED_DATE)
values (sys_guid(), 'public', 'user', 'migration', current_timestamp);

