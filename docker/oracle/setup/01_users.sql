-- schema => public,oauth,auth,resource,component
create user auth identified by auth;

grant
    create session, connect, resource,
    create table, create view, create sequence, create procedure, function to auth;
