# Request access token

Enabled client application to protect resource with my oauth server

## Grant type `authorization_code`

- link to login [http://localhost:${PORT}/oauth/authorize?grant_type=authorization_code&client_id=client-web&client_secret=123456&response_type=code&redirect_uri=${YOUR WEBSITE URL}](http://localhost:10000/oauth/authorize?grant_type=authorization_code&client_id=client-web&client_secret=123456&response_type=code&redirect_uri=http://localhost:10000)

    - `grant_type=authorization_code`
    - `client_id=client-web`
    - `response_code=code`
    - `client_secret=123456`
    - `redirect_uri=${YOUR WEBSITE URI}` example http://localhost:8080 or www.example.com
    - `state=${RANDOM STRING}` (optional) 

- username: `user` dan passwordnya `password`
- getting token

    ```curl
    curl -X POST \
      http://localhost:10000/oauth/token?grant_type=authorization_code&client_id=client-web&code=${AUTH_CODE}&response_type=code&redirect_uri=${REDIRECT_URI_TO_YOUR_WEB} \
      -H 'Authorization: Basic Y2xpZW50LXdlYjoxMjM0NTY=' \
      -H 'Cache-Control: no-cache'
    ```
    
    - `grant_type=authorization_code`
    - `response_type=code`
    - `client_id=${YOUR_CLIENT_ID}`
    - `code=${AUTH_CODE}`
    - `redirect_uri=${YOUR WEBSITE URI}` example http://localhost:8080 or www.example.com

## Grant type `password`

- request token: 

    ```curl
    curl -X POST \
          http://localhost:10000/oauth/token?grant_type=password&client_id=client-web&username=user&password=password \
          -H 'Authorization: Basic Y2xpZW50LXdlYjoxMjM0NTY=' \
          -H 'Cache-Control: no-cache'
    ```
    
    - `grant_type=password`
    - `client_id=${YOUR_CLIENT_ID}`
    - `username=${YOUR_USERNAME}`
    - `password=${YOUR_PASSWORD_OF_USER}`
    
# Schema User Management

![schema users](imgs/schema_auth_users.png)

**Table Descriptions**

- `users` untuk menyimpan data user **resource owner**
- `privileges` tabel untuk menyimpan data group role
- `user_privileges` tabel untuk menyimpan detail group yang berlasi dengan table `roles` (one user many privilage)
- `roles` tabel untuk menyimpan role atau hak akses rest api atau keamanan web
- `authorities` tabel untuk menyimpan relasi `privileges` ke `role` (one privileges many role)

## Schema Oauth Client Details

![schema oauth client details](imgs/schema_oauth_client_details.png)

**Table Description**

- `access_token` currently user login berdasarkan token dan client_id
- `history_access_token` history user logged in
- `grant_type` untuk detail resource server mau konek menggunakan grant seperti berikut:
    1. authorization_code
    2. password
    3. implicit
    4. client_credentials	
    5. refresh_token
- `application` daftar resource server klo mau konek menggunakan oauth2
- `client-detail-application` tabel untuk menyimpan detail resource server bisa akses banyak applikasi
- `client_detail_grant_type` setiap resource server bisa banyak opsi untuk login contohnya menggunakan password dan authorization_code
- `client_detail_redirect_uri` (untuk grant type authorization_code) wajib mendaftarkan redirect uri ke web servernya
