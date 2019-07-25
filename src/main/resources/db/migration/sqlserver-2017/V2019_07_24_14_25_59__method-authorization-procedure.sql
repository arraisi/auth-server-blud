
CREATE or alter function
    Auth.AuthorizationByUser(@uname VARCHAR(100))
    returns @UserAuthorityByUser table ( username  varchar(100),  authority varchar(100))
AS
BEGIN
    DECLARE
        @IsSudo bit;

    select @IsSudo = activeUser.IsSudo from Auth.Users activeUser where activeUser.Username = @uname;
    if @IsSudo = 'True'
        insert @UserAuthorityByUser
        select role.Name, @uname
        from Auth.Roles role;
    else
        insert @UserAuthorityByUser
        select distinct role.Name as role_name, @uname
        from Auth.Users u
                 join Auth.UserPrivileges granted on u.Id = granted.UserId
                 join Auth.Privileges privilege on granted.PrivilegeId = privilege.Id
                 join Auth.Authorities autority on privilege.Id = autority.PrivilegeId
                 join Auth.Roles role on autority.RoleId = role.Id
        where u.Username = @uname
    return
END;
