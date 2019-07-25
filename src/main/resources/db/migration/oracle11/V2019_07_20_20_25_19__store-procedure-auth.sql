-- drop function auth_authentication;
-- drop type auth_authenction_list;
-- drop type auth_authentication_object;
-- drop function auth_authorization;
-- drop type auth_authorization_list;
-- drop type auth_authorization_object;

create or replace type auth_authentication_object as object
(
    username varchar2(100),
    password varchar2(255),
    enabled  number(1)
);

create or replace type auth_authenction_list is table of auth_authentication_object;

-- select * from table(auth_authentication(:param));
create or replace function auth_authentication(uname in TRRBAPENGGUNA.I_SANDI%TYPE)
    return auth_authenction_list
    is
    return_value auth_authenction_list;
begin
    select auth_authentication_object(u.I_PEG_NRK, u.I_SANDI, 1)
        bulk collect
    into return_value
    from TRRBAPENGGUNA u
    where u.I_PEG_NRK = uname
      and u.C_AKTIF = '1'
      and u.C_LOCK = '0';

    return return_value;
end;

create or replace type auth_authorization_object as object
(
    username  varchar2(100),
    authority varchar2(100)
);

create or replace type auth_authorization_list is table of auth_authorization_object;

create or replace function auth_authorization(uname in TRRBAPENGGUNA.I_PEG_NRK%TYPE)
    return auth_authorization_list
    is
    return_value auth_authorization_list;
begin
    select auth_authorization_object(uname, role.NAME)
        bulk collect
    into return_value
    from AUTH_ROLES role;
    return return_value;
end;
