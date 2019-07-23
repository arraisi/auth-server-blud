# `v1.0.4-release`

- list of menu request by module and roles
- remove package `com.tabeldata.controller`
- remove package `com.tabeldata.endpoint`
- remove docs folder `docs -> wiki`
- add dependency `ojdbc6.jar:11.2.0.4`
- add functionality multiple databases [postgresql-9.6, oracle-11g]
- add pagination using oracle 11g
- set `web-commons:2.0.0-relase` migrate to `web-commons:2.0.1-release`

# `v1.0.3-release`

- update `server.servlet.context-path=${project.artifactId}`
- docker build on phase install
- docker tag, push on phase deploy
- maven deploy to nexus repository

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

