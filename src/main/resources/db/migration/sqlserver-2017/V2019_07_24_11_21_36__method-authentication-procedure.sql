create or alter function Auth.AuthenticationByUser(@uname varchar(100))
    returns table
        as return
            (
                select u.Username as username,
                       u.Password as password,
                       1          as enabled
                from Auth.Users u
                where u.Username = @uname
                  and u.IsActive = 1
                  and u.IsLocked = 0
            );
