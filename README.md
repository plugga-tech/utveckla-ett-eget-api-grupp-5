
# Create an API – Group 5

## Project Description

This project is a REST API built with **Quarkus**. The API allows clients to ***create, read, update, and delete heroes***.

A hero consists of:
- **Name**
- **Race**: *Human, Orc, Elf, Dwarf*
- **Hero Class**: *Paladin, Warrior, Rogue, Mage*
- **Weapon**: *Sword, Mace, Dagger, Staff*

The project is designed so that another group (or a frontend team) can continue building on top of it.

---

## Assignment Description
The assignment was to build our own API that another group can use and build a frontend with.  
The API should provide clear endpoints, validations, and support CRUD operations.

---

## Key features

- Full **CRUD** for users to (*Create, Read, Update, Delete*) heroes.
- Use enums for (Race, HeroClass, Weapon)
- Provide clear request/response formats using DTOs
- Provide documentation  [Swagger](http://localhost:8080/q/dev-ui/quarkus-smallrye-openapi/swagger-ui) and [DevUI](http://localhost:8080/q/dev-ui/)
See `SwaggerDocs.java` or SwaggerUI for JavaScript request examples.
---

## Technologies
- **Java 21**
- **Quarkus**
- **Jakarta EE** 
- **Swagger UI**
- **Postman**
- **Docker** 
---

## How To Run

Follow these steps to run the API locally.

### 1. Clone the Repository
```bash
git clone https://github.com/plugga-tech/utveckla-ett-eget-api-Grupp-5.git
cd your/project/local/folder
```

### 2. Start the Application in dev mode with Quarkus

Run Quarkus using Maven:

###### (Remember to start docker before running this in your terminal)
```bash
./mvnw clean compile
./mvnw quarkus:dev
```

The API will run at:

`http://localhost:8080`

---

### 3.Configure properties

Verify application.properties

###### (Only verify this if you encounter issues. The configuration should be correct by default.)

```properties
quarkus.http.cors.enabled=true
quarkus.http.cors.origins=*
quarkus.http.cors.access-control-allow-credentials=false
quarkus.http.cors.access-control-max-age=PT24H
```

---

### 4. Open Swagger UI

Once the application is running, open:

`http://localhost:8080/q/dev-ui/quarkus-smallrye-openapi/swagger-ui`

Swagger UI allows you to:

- View all endpoints  
- Send test requests  
- Inspect request and response models  

---

### 5. Testing with Postman

You can also test the API using **Postman**.

1. Open Postman  
2. Create a new request  
3. Enter an endpoint (example: `GET http://localhost:8080/api/hero/get-all-heroes`)  
4. Send the request and inspect the response  

---
## Common Responses

- **400** – Invalid request data  
- **404** – Resource not found  
- **403** – Access denied  
- **200 / 201** – Successful operation/Created
<small>For detailed response descriptions, refer to Swagger UI.</small>
___

## Data Model Overview

### Enums
The API uses enums to get better structure and readability:

#### Race <small>- Enum</small>
- *HUMAN*
- *ORC*
- *ELF*
- *DWARF*

#### HeroClass <small>- Enum</small>
- *PALADIN*
- *WARRIOR*
- *ROGUE*
- *MAGE*

#### Weapon <small>- Enum</small>
- *SWORD*
- *MACE*
- *DAGGER*
- *STAFF*

Each enum contains:
- A **display name**  - for frontend usage
- A **flavour/description string** -  for UI presentation

---

### Hero Entity Contents

Fields:

- `id` 
- `name`
- `race`
- `heroClass`
- `weapon`

Race traits:

- `focusedFire`	- 	**Elf**
- `steadyFrame` 	-	**Dwarf**
- `strongArms` 	-	**Orc**
- `jackOfAllTrades` -	**Human**

These traits are automatically set based on selected race.

---

### DTOs
This project uses DTOs to control what is sent/received from the client.

#### HeroDto 
Contains only what the client needs to send:
- `race` 
- `name` 
- `heroClass` 
- `weapon`
 
 All the above are sent as strings.

#### HeroResponseDto (outgoing response)
Contains what the API returns to the client:
- `id`
- `name`
- `race` 
- `heroClass` 
- `weapon` 
- Race traits (`focusedFire`, `steadyFrame`, `strongArms`, `jackOfAllTrades`) 
<small>**All traits are booleans**</small>

---

## API Base URL & Endpoints

Base API path:
- `http://localhost:8080/api/hero`
___


**POST** `/new-hero`  
Creates and stores a new hero.

**GET** `/get-all-heroes`  
Returns all heroes.

**GET** `/get-user-heroes`  
Returns heroes belonging to the specific user.

**GET** `/get-hero-by-id/{id}`  
Fetches a hero by its id.

**GET** `/get-hero-by-name/{heroName}`  
Fetches a hero by name.

**GET** `/get-hero-by-class/{heroClass}`  
Returns heroes matching a specific class.
*(Paladin, Warrior, Rogue, Mage)*

**GET** `/hero/get-heroes-by-race/{race}`  
Returns heroes of a specific race.
*(Human, Dwarf, Orc, Elf)* 

**GET** `/hero/get-hero-by-weapon/{weapon}`  
Returns heroes using a specific weapon.
*(Sword, Dagger, Mace , Staff)*

**PATCH** `/hero/update-hero`  
Updates an existing hero.

**DELETE** `/hero/{id}`  
Deletes a hero by id.
___

## Dependencies
- ***quarkus-hibernate-orm-panache***
- ***resteasy-json-binding-provider***
- ***quarkus-primefaces***
- ***quarkus-narayana-jta*** 
- ***quarkus-hibernate-validator***
- ***quarkus-smallrye-openapi***
- ***quarkus-resteasy***
- ***quarkus-jdbc-postgresql***
- ***quarkus-arc*** 
- ***quarkus-security-jpa***  
- ***quarkus-junit***
- ***rest-assured***
------
