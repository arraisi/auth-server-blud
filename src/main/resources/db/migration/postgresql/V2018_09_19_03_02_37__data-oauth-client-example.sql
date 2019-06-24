insert into resource.client_details(
    id, name, password, is_auto_approve, token_expired_in_second, created_by, created_date, last_update_by, last_update_date)
values
   ('itime-registration', 'itime-registration','$2a$11$52ykJMuT3eooYzfYkZYIF.kZaStN4AGBCMI43aFoBC/Br5QcqHp3G',true , 43200,'migration' , now(), null ,null),
   ('itime-script-custody', 'itime-script-custody','$2a$11$j.jCadhLXDYZoenSCCoduOkffmSlz8XW1KbykKz56ZxYzIARAddm6',true , 43200, 'migration' , now(), null ,null),
   ('itime-resource-management', 'itime-resource-management','$2y$11$biLa.4Q.gQQB3hd5vJ2H4.gjuy8ONIvcXSIPKo7ftT0CIVqa87uYS',true , 43200, 'migration' , now(), null ,null),
   ('sisappra-upload', 'sisappra-upload','$2y$11$PM3Zk67WlrJFE3Y4BSwDXOmYjazr9FeBEpgyTz04xyG9xW8ss2.Ai',true , 43200, 'migration' , now(), null ,null),
   ('sisappra-api', 'sisappra-api','$2y$11$kYR2qvVFNj3p6HxYvjHU7O7mg1yQvPidDBMvx/FO6h/EMp6bG0t6q',true , 43200, 'migration' , now(), null ,null),
   ('tabeldata-oauth2-resource-server', 'tabeldata-oauth2-resource-server','$2a$11$sIDwRPDQLMB5428zrzj//.zsCDk0S7l8BvxZTtrS2f77FuZj68XVu',true , 43200, 'migration' , now(), null ,null),
   ('bblm-api', 'bblm-api','$2y$11$HSv5yvo93C9fj1Y/W3dfQOCT72YNbiitiIWEKQOyZZROOOXDWM9Pe',true , 43200, 'migration' , now(), null ,null);

insert into resource.applications (id, name, created_by, created_date, last_update_by, last_update_date)
values ('example', 'example', 'migration', now(), null, null),
       ('itime', 'itime', 'migration', now(), null, null),
       ('bblm', 'bblm', 'migration', now(), null, null),
       ('sisappra', 'sisappra', 'migration', now(), null, null);

insert into resource.client_detail_applications(
    id, client_detail_id, app_id, created_by,created_date, last_update_by, last_update_date)
values  (uuid_generate_v4(), 'tabeldata-oauth2-resource-server', 'example', 'migration', now(), null, null),
        (uuid_generate_v4(), 'itime-registration', 'itime', 'migration', now(), null, null),
        (uuid_generate_v4(), 'itime-script-custody', 'itime', 'migration', now(), null, null),
        (uuid_generate_v4(), 'itime-resource-management', 'itime', 'migration', now(), null, null),
        (uuid_generate_v4(), 'bblm-api', 'bblm', 'migration', now(), null, null),
        (uuid_generate_v4(), 'sisappra-upload', 'sisappra', 'migration', now(), null, null),
        (uuid_generate_v4(), 'sisappra-api', 'sisappra', 'migration', now(), null, null);

insert into resource.client_detail_grant_types(id, client_id, grant_type, created_by, created_date, last_update_by, last_update_date)
values (uuid_generate_v4(), 'itime-registration', 2, 'migration', now(), null, null),
       (uuid_generate_v4(), 'itime-registration', 5, 'migration', now(), null, null),
       (uuid_generate_v4(), 'itime-script-custody', 2, 'migration', now(), null, null),
       (uuid_generate_v4(), 'itime-script-custody', 5, 'migration', now(), null, null),
       (uuid_generate_v4(), 'itime-resource-management', 2, 'migration', now(), null, null),
       (uuid_generate_v4(), 'itime-resource-management', 5, 'migration', now(), null, null),
       (uuid_generate_v4(), 'sisappra-upload', 2, 'migration', now(), null, null),
       (uuid_generate_v4(), 'sisappra-upload', 5, 'migration', now(), null, null),
       (uuid_generate_v4(), 'sisappra-api', 2, 'migration', now(), null, null),
       (uuid_generate_v4(), 'sisappra-api', 5, 'migration', now(), null, null),
       (uuid_generate_v4(), 'bblm-api', 2, 'migration', now(), null, null),
       (uuid_generate_v4(), 'bblm-api', 5, 'migration', now(), null, null);

insert into oauth.client_scopes(id, name, created_by, created_date, last_update_by, last_update_date)
values (2, 'write', 'migration', now(), null, null),
       (3, 'update', 'migration', now(), null, null),
       (4, 'delete', 'migration', now(), null, null);