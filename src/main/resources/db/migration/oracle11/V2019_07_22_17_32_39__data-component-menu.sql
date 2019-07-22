-- data menu itime-registration
insert into COMPONENT_MENUS(ID, TITLE, PATH, ICON_ID, IS_MENU, CREATED_BY, RESOURCE_ID, PARENT_ID)
VALUES ('001', 'Dashboard', '/dashboard', null, 0, 'migration', 'itime-registration', null);
insert into COMPONENT_MENUS(ID, TITLE, PATH, ICON_ID, IS_MENU, CREATED_BY, RESOURCE_ID, PARENT_ID)
VALUES ('002', 'Data Master', '/master', null, 1, 'migration', 'itime-registration', null);
insert into COMPONENT_MENUS(ID, TITLE, PATH, ICON_ID, IS_MENU, CREATED_BY, RESOURCE_ID, PARENT_ID)
VALUES ('003', 'Data Provinsi', '/propinsi', null, 0, 'migration', 'itime-registration', '002');
insert into COMPONENT_MENUS(ID, TITLE, PATH, ICON_ID, IS_MENU, CREATED_BY, RESOURCE_ID, PARENT_ID)
VALUES ('004', 'Data Kota', '/kota', null, 0, 'migration', 'itime-registration', '002');

-- data menu itime-script-custody
insert into COMPONENT_MENUS(ID, TITLE, PATH, ICON_ID, IS_MENU, CREATED_BY, RESOURCE_ID, PARENT_ID)
VALUES ('005', 'Dashboard', '/dashboard', null, 0, 'migration', 'itime-script-custody', null);
insert into COMPONENT_MENUS(ID, TITLE, PATH, ICON_ID, IS_MENU, CREATED_BY, RESOURCE_ID, PARENT_ID)
VALUES ('006', 'Data Master', '/master', null, 1, 'migration', 'itime-script-custody', null);
insert into COMPONENT_MENUS(ID, TITLE, PATH, ICON_ID, IS_MENU, CREATED_BY, RESOURCE_ID, PARENT_ID)
VALUES ('007', 'Data Provinsi', '/propinsi', null, 0, 'migration', 'itime-script-custody', '005');
insert into COMPONENT_MENUS(ID, TITLE, PATH, ICON_ID, IS_MENU, CREATED_BY, RESOURCE_ID, PARENT_ID)
VALUES ('008', 'Data Kota', '/kota', null, 0, 'migration', 'itime-script-custody', '005');
