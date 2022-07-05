# Testcontainers Demo

A regarder : 

* https://www.youtube.com/watch?v=v3eQCIWLYOw&ab_channel=IntelliJIDEAbyJetBrains
  * Creuser la problématique `static` / `@BeforeClass` / `@DynamicPropertySource` 
  * Quarkus : "Quarkus automatically spins up all the TestContainers you need based on the dependencies in your pom.xml (DB, Kafka, etc...) during dev/tests without having to setup/code anything, it's out of the box"

## Base de données

Création de la base de données en local : 

```sql
CREATE DATABASE testcontainers_demo_db;
CREATE USER testcontainers_demo_user WITH PASSWORD 'testcontainers_demo_password';
GRANT ALL PRIVILEGES ON DATABASE testcontainers_demo_db TO testcontainers_demo_user;
```
