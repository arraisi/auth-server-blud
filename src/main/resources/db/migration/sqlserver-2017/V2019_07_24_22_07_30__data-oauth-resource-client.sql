insert into Resources.ClientDetails(Id, Name, Password, CreatedBy)
values ('itime-registration', 'itime-registration', '$2a$11$52ykJMuT3eooYzfYkZYIF.kZaStN4AGBCMI43aFoBC/Br5QcqHp3G',
        'migration'),
       ('itime-script-custody', 'itime-script-custody', '$2a$11$52ykJMuT3eooYzfYkZYIF.kZaStN4AGBCMI43aFoBC/Br5QcqHp3G',
        'migration');

insert into Resources.Applications(Id, Name, CreatedBy)
values ('itime', 'itime', 'migration');

insert into Resources.ClientDetailApplications(Id, ClientDetailId, AppId, CreatedBy)
values (newid(), 'itime-registration', 'itime', 'migration'),
       (newid(), 'itime-script-custody', 'itime', 'migration');


insert into Resources.ClientDetailGrantTypes(ClientId, GrantType, CreatedBy)
values ('itime-registration', 1, 'migration'),
       ('itime-registration', 2, 'migration'),
       ('itime-registration', 5, 'migration'),
       ('itime-script-custody', 1, 'migration'),
       ('itime-script-custody', 2, 'migration'),
       ('itime-script-custody', 5, 'migration');
