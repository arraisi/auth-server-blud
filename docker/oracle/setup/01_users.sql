SET SERVEROUTPUT ON;
create user auth identified by auth;

grant
    create session, connect, resource,
    create table, create view, create sequence, create procedure to auth;
