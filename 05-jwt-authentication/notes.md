# JWT Authentication with Spring Security

## Goal

Secure REST APIs using JSON Web Tokens (JWT) instead of HTTP Sessions.

---

# Authentication vs Authorization

## Authentication

Answers:

> Who are you?

Verifies the identity of a user.

Examples:

- Username & Password
- OTP
- Fingerprint

---

## Authorization

Answers:

> What are you allowed to do?

Determines which resources an authenticated user can access.

Examples:

- USER
- ADMIN

---

# What is JWT?

JWT (JSON Web Token) is a compact, URL-safe token used for stateless authentication.

Instead of storing user sessions on the server, the server generates a signed token after login.

The client stores this token and sends it with every protected request.

---

# JWT Flow

```text
Client
    │
    ▼
POST /auth/login
(username + password)
    │
    ▼
Generate JWT
    │
    ▼
Return JWT
    │
    ▼
Client stores JWT
    │
    ▼
GET /students
Authorization: Bearer <JWT>
    │
    ▼
JWT Filter
    │
    ▼
Validate Token
    │
    ▼
Controller
```

---

# JWT Structure

A JWT consists of three parts:

```text
Header.Payload.Signature
```

Example:

```text
eyJhbGciOiJIUzI1NiJ9.
eyJzdWIiOiJhZ3JpbSJ9.
ajSxUP...
```

---

## Header

Contains metadata.

Example:

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

- alg → Signing algorithm
- typ → Token type

---

## Payload

Contains claims (information about the user).

Example:

```json
{
  "sub": "agrim",
  "iat": "...",
  "exp": "..."
}
```

Common Claims:

- sub → Username
- iat → Issued At
- exp → Expiration Time

---

## Signature

Created using:

- Header
- Payload
- Secret Key

Purpose:

Ensures the token has not been modified.

---

# JwtUtil

Responsible for creating and validating JWTs.

## generateToken()

Generates a JWT for the authenticated user.

```java
generateToken(username)
```

Returns:

```text
eyJhbGc...
```

---

## extractUsername()

Reads the username stored inside the JWT.

```java
extractUsername(token)
```

Returns:

```text
agrim
```

---

## isTokenValid()

Checks whether:

- Token signature is valid
- Token has not expired
- Token has not been modified

Returns:

```java
true
```

or

```java
false
```

---

# Authorization Header

JWT is sent in the HTTP Authorization header.

```http
Authorization: Bearer <JWT>
```

Example:

```http
Authorization: Bearer eyJhbGc...
```

"Bearer" tells the server that a JWT token is being sent.

---

# JwtAuthenticationFilter

Created by extending:

```java
OncePerRequestFilter
```

Meaning:

Runs exactly once for every HTTP request.

Responsibilities:

1. Read Authorization header.
2. Check if it starts with "Bearer ".
3. Extract JWT.
4. Validate JWT.
5. Extract username.
6. Create Authentication object.
7. Store Authentication inside Spring Security.

---

# SecurityContextHolder

Stores authentication information for the current request.

Before authentication:

```text
Current User = null
```

After authentication:

```text
Current User = agrim
```

Authentication is stored using:

```java
SecurityContextHolder
        .getContext()
        .setAuthentication(authentication);
```

---

# UsernamePasswordAuthenticationToken

Represents the authenticated user.

```java
new UsernamePasswordAuthenticationToken(
        username,
        null,
        null
);
```

Parameters:

- Principal → Username
- Credentials → null
- Authorities → null

---

# Registering the JWT Filter

Inside SecurityConfig:

```java
.addFilterBefore(
        jwtAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
)
```

Purpose:

Runs our custom JWT filter before Spring Security's authentication filter.

---

# Protecting Endpoints

```java
.requestMatchers("/auth/login").permitAll()
.anyRequest().authenticated()
```

Meaning:

- `/auth/login` → Public
- All other endpoints → Require a valid JWT

---

# Complete Request Flow

```text
Client
    │
    ▼
Authorization Header
    │
    ▼
JwtAuthenticationFilter
    │
    ▼
Read Header
    │
    ▼
Extract JWT
    │
    ▼
Validate JWT
    │
    ▼
Extract Username
    │
    ▼
Create Authentication Object
    │
    ▼
Store in SecurityContextHolder
    │
    ▼
Spring Security
    │
    ▼
Controller
```

---

# Testing

## Login

```http
POST /auth/login
```

Body:

```json
{
    "username": "agrim",
    "password": "password123"
}
```

Response:

```text
JWT Token
```

---

## Access Protected Endpoint

```http
GET /students
```

Header:

```http
Authorization: Bearer <JWT>
```

Response:

```http
200 OK
```

---

## Without JWT

```http
GET /students
```

Response:

```http
401 Unauthorized
```

---

# Key Concepts Learned

- Authentication
- Authorization
- Stateless Authentication
- JSON Web Token (JWT)
- JWT Structure
- Claims
- Secret Key
- Bearer Token
- JwtUtil
- OncePerRequestFilter
- SecurityContextHolder
- UsernamePasswordAuthenticationToken
- Security Filter Chain
- Protected REST APIs