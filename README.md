# Price service monitoring Restful api

This is application for monitoring prices on products in different magazines.
In this one is used such technologies as:

- [x] Spring Boot 2.7.12
- [x] Spring Security (JWT)
- [x] Spring Data
- [x] Hibernate
- [x] Lombok
- [x] Liquibase
- [x] Swagger
- [x] Docker
- [x] PostgresSQL
- [x] ApachePOI
- [x] OpenCSV
### Run the Application

1. Clone the application
```console
git clone https://git-students.senla.eu/senla_java_nn/bogdan_ryazhin.git
```

2. Crete a build for application
```console
$ mvn clean package
```

3. From project directory, start up the application by running.

```console
docker-compose up -d --build
```
Docker-compose pulls and build the images from project, and starts the services.

### Swagger

```
http://localhost:8080/swagger-ui/index.html
```
Enter http://localhost:8080/swagger-ui/index.html in a browser to see the application running.

### Authentication

   ```json
   POST /api/v1/auth/login HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json   
   {
       "username": "AmonRa24",
       "password": "Qwerty_123."
   }
   ```

### Security

The security expects a token named authorization with the generated JWT.

   ```json
    GET /store/products HTTP/1.1
    Host: localhost:8080
    Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwZGZkNTZjOS03NzU4LTRjYzAtOTg0Zi01ZjFhOTA2ZjcyOGMiLCJpYXQiOjE1Nzk0ODY2ODcsImV4cCI6MTU4MDA5MTQ4N30.Ewn0A0OTSX9Ik8dDmQDe33UklZkUD62L-5F_I11dYkpCWqHlpjyOfy8FNS6pJAp4g2EGrRXRFquxaizvfJRQzw
   ```

The project uses two types of roles.

```json
ROLE_ADMIN
ROLE_USER   
```

