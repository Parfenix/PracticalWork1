# Library management with readers

## Description
This application is designed to manage the library, including functions for adding, displaying and searching books and readers. It is developed using Java and JDBC to interact with the PostgreSQL database.

## Requirements
- Java Development Kit (JDK) 21 or higher
- PostgreSQL 14 or higher
- Gradle 8.10 or higher

## Installation

```bash
   git clone https://github.com/Parfenix/PracticalWork1.git
   cd PracticalWork1
   ./gradlew build
```

## Setup

```sql
    CREATE DATABASE library_db;
```

```bash
    psql -U <username> -d library_db -a -f sqlScript/setup.sql
```

## Setting up a database connection
Change the database connection settings in the DatabaseManager class to your details:

```
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "your_user";
    private static final String PASSWORD = "your_password";
```

## Usage
Run the application:

```bash
    ./gradlew run
```
The application will perform the following operations:

- Add books to the database.
- Get and display all books.
- Search for a book by title.
- Delete a book.
- Add readers to the database.
- Get and display all readers.
- Search for a reader by email.
- Delete a reader.