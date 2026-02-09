
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
- Provide documentation and example requests via [Swagger](http://localhost:8080/q/dev-ui/quarkus-smallrye-openapi/swagger-ui) and [DevUI](http://localhost:8080/q/dev-ui/)
---

## Technologies
- **Java 21**
- **Quarkus 3**
- **Jakarta EE** 
- **Swagger UI**
- **Postman**
- **Docker** 
---

## Data Model Overview

### Enums
The API uses enums to get better structure and readability:

#### Race
- *HUMAN*
- *ORC*
- *ELF*
- *DWARF*

#### HeroClass
- *PALADIN*
- *WARRIOR*
- *ROGUE*
- *MAGE*

#### Weapon
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

---

## API Base URL
Default dev URL:
- `http://localhost:8080`

Base API path:
- `http://localhost:8080/api`

---

## Endpoints


**POST** `/hero/new-hero`  
Creates and stores a new hero.

**GET** `/hero/get-all-heroes`  
Returns all heroes.

**GET** `/hero/get-user-heroes`  
Returns heroes belonging to the specific user.

**GET** `/hero/get-hero-by-id/{id}`  
Fetches a hero by its id.

**GET** `/hero/get-hero-by-name/{heroName}`  
Fetches a hero by name.

**GET** `/hero/get-hero-by-class/{heroClass}`  
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



## Common Error Responses

- **400** – Invalid request data  
- **404** – Resource not found  
- **403** – Access denied  
- **200 / 201** – Successful operation
