# Validation and Exception Handling

## Validation

### Purpose

Prevent invalid data from reaching the database.

Example:

```json
{
  "name": "",
  "cgpa": -5
}
```

This data should be rejected before it reaches the Service or Database layer.

---

## Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

---

## Validation Annotations

### @NotBlank

```java
@NotBlank
private String name;
```

Rejects:

* null
* ""
* only spaces

---

### @Min and @Max

```java
@Min(0)
@Max(10)
private double cgpa;
```

Ensures CGPA remains between 0 and 10.

---

## @Valid

```java
public Student addStudent(
        @Valid @RequestBody Student student)
```

Purpose:

Triggers validation before the controller method executes.

Without `@Valid`, validation annotations are ignored.

---

## Validation Flow

Request
↓
Validation
↓
Controller
↓
Service
↓
Repository
↓
Database

If validation fails:

Request
↓
Validation ❌
↓
400 Bad Request

---

# Exception Handling

## Problem

Default Spring error responses are often large and difficult to read.

Example:

```json
{
  "timestamp": "...",
  "status": 500,
  "error": "Internal Server Error"
}
```

---

## Global Exception Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        return ex.getMessage();
    }
}
```

---

## @RestControllerAdvice

Used to handle exceptions globally in REST APIs.

Equivalent to:

```java
@ControllerAdvice
+
@ResponseBody
```

---

## @ExceptionHandler

Used to catch exceptions and customize responses.

Example:

```java
@ExceptionHandler(Exception.class)
```

Handles all exceptions of type `Exception`.

---

## Example

Exception:

```java
throw new RuntimeException("Student not found");
```

Response:

```text
Student not found
```

instead of Spring's default error response.

---

## Error Flow

Before:

Exception
↓
Spring Default Error Response

After:

Exception
↓
GlobalExceptionHandler
↓
Custom Response

---

## ControllerAdvice vs RestControllerAdvice

### @ControllerAdvice

Used mainly with MVC applications and HTML views.

### @RestControllerAdvice

Used with REST APIs.

Returns response data directly in the HTTP response body.
