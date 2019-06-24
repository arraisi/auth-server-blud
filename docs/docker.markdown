# Springboot in docker container

Untuk menjalankan aplikasi di docker container, terdiri dari 2 image yaitu

- postgres:9.7
- openjdk-8

## Build jar application:

sebelum menjalankan perintah `docker-compose` kita build dulu `.jar` dengan perintah:

```bash
mvn clean install
```

## Run deployment using docker-compose

Setelah membuild project ini dengan maven, kita bisa menjalankan project ini di docker container dengan mudah. dengan bantuan perintah `docker-compose` seperti berikut:

```bash
docker-compose -f docker/docker-compose.yml up -d
```

dengan perintah tersebut, maka kita udah di siapkan system required dari aplikasi ini yaitu seperti yang kita sebutkan diatas yaitu postgresql dan java. jadi kita tidak usah repot2 untuk install di os kita.
