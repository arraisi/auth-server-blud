insert into component.menu (id, title, path, icon_id, is_menu, created_by, created_date, resource_id, parent_id)
values ('001', 'Dashboard', '/dashboard', null, false, 'migration', now(), 'sisappra-api', null),
       ('002', 'Data Master', '/master', null, true, 'migration', now(), 'sisappra-api', null),
       ('003', 'Data Provinsi', '/provinsi', null, false, 'migration', now(), 'sisappra-api', '002'),
       ('004', 'Data Kota', '/kota', null, false, 'migration', now(), 'sisappra-api', '002'),
       ('005', 'Data Kecamatan', '/kecamatan', null, false, 'migration', now(), 'sisappra-api', '002'),
       ('006', 'Data Kelurahan', '/keluarahan', null, false, 'migration', now(), 'sisappra-api', '002'),
       ('007', 'Data User Management', '/user-management', null, true, 'migration', now(), 'sisappra-api', null),
       ('008', 'Register User', '/register-user', null, false, 'migration', now(), 'sisappra-api', '007'),
       ('009', 'Privileges Management', '/privileges', null, false, 'migration', now(), 'sisappra-api', '007'),
       ('010', 'Authorization', '/authorization', null, false, 'migration', now(), 'sisappra-api', '007');
