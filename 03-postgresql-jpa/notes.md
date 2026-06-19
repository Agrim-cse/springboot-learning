# PostgreSQL + Spring Data JPA

## Goal

Connect Spring Boot application to PostgreSQL and persist data using JPA/Hibernate.

---

## Dependencies Added

### PostgreSQL Driver

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Spring Data JPA

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

---

## PostgreSQL Setup

Database:

```sql
CREATE DATABASE springboot_db;
```

Connected using:

```sql
\c springboot_db
```

---

## Spring Boot Configuration

application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot_db
spring.datasource.username=postgres
spring.datasource.password=postgres123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Important Properties

#### ddl-auto=update

Automatically updates database schema based on entity classes.

#### show-sql=true

Displays generated SQL queries in console.

---

## JPA Entity

```java
@Entity
@Table(name = "students")
public class Student
```

### Annotations Learned

#### @Entity

Marks a class as a database entity.

#### @Table

Maps entity to a database table.

#### @Id

Marks primary key.

#### @GeneratedValue

Automatically generates IDs.

Example:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
```

---

## Repository Layer

```java
public interface StudentRepository
        extends JpaRepository<Student, Integer> {
}
```

### JpaRepository Provides

* save()
* findAll()
* findById()
* deleteById()
* count()

without writing SQL.

---

## Dependency Injection

Repository injected into service:

```java
private final StudentRepository studentRepository;

public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
}
```

---

## Saving Data

Service method:

```java
public Student saveStudent(Student student) {
    return studentRepository.save(student);
}
```

Controller:

```java
@PostMapping("/students")
public Student addStudent(@RequestBody Student student) {
    return studentService.saveStudent(student);
}
```

---

## Request Example

POST

```http
POST /students
```

Body:

```json
{
  "name": "Agrim",
  "cgpa": 8.5
}
```

Response:

```json
{
  "id": 1,
  "name": "Agrim",
  "cgpa": 8.5
}
```

---

## Key Concepts Learned

### Mapping

Java Class -> Database Table

Java Object -> Table Row

Java Field -> Table Column

### Application Flow

Client
→ Controller
→ Service
→ Repository
→ JPA/Hibernate
→ PostgreSQL

---

## Debugging Lesson

Problem:

Data was not visible in database.

Investigation:

Hibernate logs showed:

```sql
insert into studnts (...)
```

Issue:

Table name typo:

```java
@Table(name = "studnts")
```

Fixed:

```java
@Table(name = "students")
```

Lesson:

Always check Hibernate-generated SQL when debugging persistence issues.
