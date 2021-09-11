# Customer Management

Develop with Spring boot, Spring Data JPA , Vue.js and Postgresql.

JDK 8 and Vue cli required.

#### Run docker commands (for database):

```
docker pull postgres:9.6
```

```
docker run --name postgres-0 -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres:9.6
```

#### And connect and create database
```
create database customerdb;
```

#### Run backend project

#### And run command in terminal at frontend directory
```
npm install
```
```
npm run serve -- --port 8081
```