# `v1.0.2-release`

- Fixing oauth client / resource id information

# `v1.0.1-release`

- Secure with JWT
- Enable deployment with docker
- Enable deployment with kubenetes and istio

# `v1.0.0-SNAPSHOT`

- Enabled sso for oauth2
    - grant type authorization code
    - grant type password
- Dynamic privileges by role
- Credential users by jdbc and encrypt by BCrypt
- Enabled resource server, url pattern => `/api/**`
- Securing Web MVC, url any pattern example `/app/**` except `/api/**`
- Fixing `authorization code` and `password` grant type from token to authentication and authorization
- Automatic testing for OAuth2 `password` grant type
- Enabled run as service / systemctl / systemd
- Enabled oauth client details with jdbc / client service
- Enabled jdbc token store
- Enabled login screen
- Enabled webjars
    - bootstrap
    - jquery
    - poper.js
    - moment
- Thymeleaf + Thymeleaf security
- Enabled oauth logout & force logout api
- Enabled history login and logout

