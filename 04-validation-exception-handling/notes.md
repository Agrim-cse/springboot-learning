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

* `null`
* `""`
* Only spaces

---

### @Min and @Max

```java
@Min(0)
@Max(10)
private double cgpa;
```

Ensures CGPA remains between **0 and 10**.

---

## @Valid

```java
@PostMapping("/students")
public Student addStudent(@Valid @RequestBody Student student) {
    return studentService.saveStudent(student);
}
```

Purpose:

Triggers validation before the controller method executes.

Without `@Valid`, validation annotations are ignored.

---

## Validation Flow

```
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
```

If validation fails:

```
Request
   ↓
Validation ❌
   ↓
400 Bad Request
```

---

# Exception Handling

## Why Exception Handling?

Instead of letting Spring return a large default error response, we can return cleaner and more meaningful responses.

---

## Custom Exception

Created:

```java
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message) {
        super(message);
    }
}
```

### Why create a custom exception?

Instead of:

```java
throw new RuntimeException("Student not found");
```

we write:

```java
throw new StudentNotFoundException("Student not found");
```

This makes the code more readable and allows different exceptions to be handled differently.

---

## orElseThrow()

Instead of:

```java
studentRepository.findById(id).orElse(null);
```

we now use:

```java
studentRepository.findById(id)
        .orElseThrow(() ->
                new StudentNotFoundException(
                        "Student with id " + id + " not found"));
```

### Purpose

If the student exists, return it.

Otherwise, throw a custom exception.

---

## Global Exception Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleStudentNotFound(
            StudentNotFoundException ex) {

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
}
```

---

## @RestControllerAdvice

Used to handle exceptions globally for all REST controllers.

Equivalent to:

```java
@ControllerAdvice
+
@ResponseBody
```

---

## @ExceptionHandler

Used to specify which exception a method should handle.

Example:

```java
@ExceptionHandler(StudentNotFoundException.class)
```

---

## @ResponseStatus

```java
@ResponseStatus(HttpStatus.NOT_FOUND)
```

Changes the HTTP response status.

Example:

```
404 Not Found
```

instead of

```
500 Internal Server Error
```

---

## ErrorResponse

Instead of returning a plain string, we return an object.

```java
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

}
```

Spring automatically converts it into JSON.

Example response:

```json
{
    "status": 404,
    "message": "Student with id 999 not found",
    "timestamp": "2026-06-25T14:30:00"
}
```

---

## Jackson

Spring Boot uses **Jackson** internally to convert Java objects into JSON automatically.

Example:

```java
return new Student(...);
```

↓

```json
{
    "id": 1,
    "name": "Agrim",
    "cgpa": 8.5
}
```

The same happens for `ErrorResponse`.

---

## Error Flow

### Student Exists

```
Request
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Student Found
   ↓
Response
```

### Student Not Found

```
Request
   ↓
Controller
   ↓
Service
   ↓
findById()
   ↓
orElseThrow()
   ↓
StudentNotFoundException
   ↓
GlobalExceptionHandler
   ↓
ErrorResponse
   ↓
404 Not Found
```

---

## HTTP Status Codes Learned

| Status Code                   | Meaning                 |
| ----------------------------- | ----------------------- |
| **200 OK**                    | Request successful      |
| **400 Bad Request**           | Invalid request data    |
| **404 Not Found**             | Resource does not exist |
| **500 Internal Server Error** | Unexpected server error |

---

## Concepts Learned

* Validation using `@Valid`
* `@NotBlank`
* `@Min`
* `@Max`
* `@RestControllerAdvice`
* `@ExceptionHandler`
* Custom Exceptions
* `orElseThrow()`
* `@ResponseStatus`
* `ErrorResponse`
* Jackson Object → JSON conversion
