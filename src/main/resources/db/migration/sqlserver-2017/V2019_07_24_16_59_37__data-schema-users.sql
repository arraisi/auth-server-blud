insert into Auth.Users(Id, Username, Password, Email, IsActive, IsSudo, CreatedBy)
values ('admin', 'admin', '$2a$11$jvo5gEa399siGTgYFhZ6bOPKkP0FG2ZcUVw/UebAHkr7zTMHWAaZy', 'admin@gmail.com', 1, 1,
        'migration'),
       ('user', 'user', '$2a$11$jvo5gEa399siGTgYFhZ6bOPKkP0FG2ZcUVw/UebAHkr7zTMHWAaZy', 'admin@gmail.com', 1, 0,
        'migration'),
       ('default', 'default', '$2a$11$jvo5gEa399siGTgYFhZ6bOPKkP0FG2ZcUVw/UebAHkr7zTMHWAaZy', 'admin@gmail.com', 1, 0,
        'migration');

insert into Auth.Roles(Id, Name)
values (0, 'ROLE_PUBLIC'),
       (1, 'ROLE_VIEW'),
       (2, 'ROLE_INSERT');

insert into Auth.Privileges (Id, Name, CreatedBy)
values ('app-create', 'PRIVILEGES_CREATE_APP', 'migration'),
       ('public', 'PRIVILEGES_VIEW_APP', 'migration');

insert into Auth.Authorities(Id, PrivilegeId, RoleId, CreatedBy)
values (newid(), 'app-create', 0, 'migration'),
       (newid(), 'app-create', 1, 'migration'),
       (newid(), 'app-create', 2, 'migration'),
       (newid(), 'public', 0, 'migration'),
       (newid(), 'public', 1, 'migration');


insert into Auth.UserPrivileges(PrivilegeId, UserId, CreatedBy)
values ('public', 'user', 'migration'),
       ('app-create', 'user', 'migration'),
       ('public', 'default', 'migration');

