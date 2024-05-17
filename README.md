# Testcontainers Demo

https://testcontainers.com/getting-started/

## How to run the app?

Create a local PostgreSQL database with the folling commands: 

```sql
CREATE DATABASE testcontainers_demo_db;
CREATE USER testcontainers_demo_user WITH PASSWORD 'testcontainers_demo_password';
ALTER ROLE testcontainers_demo_user SUPERUSER;
```

Then, run the 2 SQL scripts from the `src/main/resources/database` directory:
* `ups/v001__data_sources.sql`
* `ups/v002__regions.sql`

Finally, run the app with the following command:

```
./mvnw spring-boot:run
```
