# Spring Boot Basics

## What is Spring Boot?

Spring Boot is a framework that helps build Java backend applications quickly by reducing configuration and providing embedded servers.

---

## Embedded Tomcat

When a Spring Boot application starts, it launches an embedded Tomcat server.

Default port:

8080

Example:

http://localhost:8080

---

## @SpringBootApplication

Placed on the main class.

Responsibilities:
- Enables auto configuration
- Enables component scanning
- Marks the application as a Spring Boot project

Example:

```java
@SpringBootApplication
public class HelloApiApplication {
}
```

---

## @RestController

Marks a class as a REST Controller.

Used to handle HTTP requests and return responses.

Example:

```java
@RestController
public class HelloController {
}
```

---

## @GetMapping

Maps HTTP GET requests to a method.

Example:

```java
@GetMapping("/students")
```

---

## @PostMapping

Maps HTTP POST requests to a method.

Example:

```java
@PostMapping("/students")
```

---

## JSON Serialization

Spring Boot uses Jackson internally.

Java Object → JSON

Example:

```java
return new Student(1, "Agrim", 8.21);
```

becomes

```json
{
  "id": 1,
  "name": "Agrim",
  "cgpa": 8.21
}
```

---

## @RequestBody

Converts incoming JSON into a Java object.

Example:

```java
@PostMapping("/students")
public Student createStudent(@RequestBody Student student)
```

JSON:

```json
{
  "id": 1,
  "name": "Agrim",
  "cgpa": 8.21
}
```

↓

Student object

---

## Service Layer

Business logic should not be placed inside controllers.

Structure:

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

---

## @Service

Marks a class as a service bean managed by Spring.

Example:

```java
@Service
public class StudentService {
}
```

---

## Dependency Injection

Spring creates and injects required objects automatically.

Example:

```java
private final StudentService studentService;

public HelloController(StudentService studentService) {
    this.studentService = studentService;
}
```

Benefits:
- Loose coupling
- Easier testing
- Cleaner architecture