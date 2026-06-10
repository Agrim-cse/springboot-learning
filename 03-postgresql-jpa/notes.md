# PostgreSQL Basics

## Installed
- PostgreSQL 17.10

## Commands Learned

List databases:

\l

Connect to database:

\c springboot_db

Describe table:

\d students

## Database Created

springboot_db

## Table Created

students

Columns:
- id SERIAL PRIMARY KEY
- name VARCHAR(100)
- cgpa DECIMAL(3,2)

## Mapping

Java Class -> SQL Table
Java Object -> SQL Row
Java Field -> SQL Column