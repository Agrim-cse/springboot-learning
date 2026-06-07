# HTTP & REST Basics

## What is a REST API?

A REST API allows communication between a client and a server using HTTP requests and responses.

Example:

Client -----> Server
        Request

Client <----- Server
        Response

---

## HTTP Methods

### GET
Used to fetch data.

Example:
GET /students

### POST
Used to create data.

Example:
POST /students

### PUT
Used to update existing data.

Example:
PUT /students/1

### DELETE
Used to remove data.

Example:
DELETE /students/1

---

## Common Status Codes

200 OK
201 Created
400 Bad Request
401 Unauthorized
404 Not Found
500 Internal Server Error

---

## JSON

JSON is used to exchange data between client and server.

Example:

```json
{
  "id": 1,
  "name": "Agrim",
  "cgpa": 8.21
}
```

---

## Spring Boot Annotations

### @RestController

Marks a class as a REST Controller that can handle HTTP requests.

### @GetMapping

Handles GET requests.

Example:

```java
@GetMapping("/students")
```

### @PostMapping

Handles POST requests.

### @PutMapping

Handles PUT requests.

### @DeleteMapping

Handles DELETE requests.

### @PathVariable

Used to extract values from the URL.

Example:

```java
@GetMapping("/students/{id}")
public Student getStudent(@PathVariable int id)
```

If URL is:

/students/5

Then id = 5