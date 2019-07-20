-- drop function auth_authentication;
-- drop type auth_table;
-- drop type auth_object;

create or replace type auth_object as object
(
    username varchar2(100),
    password varchar2(255),
    enabled  number(1)
);

create or replace type  auth_table is table of auth_object;

-- select * from table(auth_authentication(:param));
create or replace function auth_authentication(uname in auth_users.username%TYPE)
    return auth_table
    is return_value auth_table;
begin
    select auth_object(u.username, u.password, u.is_active)
        bulk collect into return_value
    from auth_users u
    where
            u.username = uname and
            u.is_active = 1 and
            u.is_locked = 0;

    return return_value;
end;


