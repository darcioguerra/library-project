# Library Review System

Java Swing desktop application for managing book reviews, developed as a
university project (Advanced Programming, 2nd year, Computer Engineering).
Built by Dárcio Guerra and Rodrigo Pereira.

## Features

- Three user roles: **author**, **reviewer** and **manager**
- Registration with manager approval; the first run requires registering a manager
- Managers can approve or reject registrations, deactivate users and add licences
- Review workflow: managers approve or reject reviews and choose a chief reviewer
- Notifications between users
- Search and filtering of books, reviews and users
- Soft deletion of accounts (account data omission)

## Technologies

- Java (Swing GUI)
- MySQL with JDBC (MySQL Connector/J 8.3.0)
- Javadoc documentation (`docs/`)

## Project structure

```
gui/src/     Java source code
docs/        Javadoc (open docs/index.html)
manual.pdf   User manual (Portuguese)
```

## Requirements

- JDK 17 or newer  <!-- TODO: put the version you used -->
- MySQL server running on `localhost:3306`
- [MySQL Connector/J 8.3.0](https://dev.mysql.com/downloads/connector/j/) on the classpath

## Database configuration

The application connects to a local database with these settings
(defined in `gui/src/Initialization.java`):

| Setting  | Value                                  |
|----------|----------------------------------------|
| URL      | `jdbc:mysql://localhost:3306/pa`       |
| User     | `root`                                 |
| Password | `1234`                                 |

These are local development defaults. To use different credentials, edit
`Initialization.java`. The database schema is not included in this
repository. <!-- TODO: link the SQL file here if you add one -->

## How to run

1. Create the `pa` database in MySQL.
2. Download MySQL Connector/J and note the path to the `.jar`.
3. Compile and run from the repository root:

```bash
javac -cp "path/to/mysql-connector-j-8.3.0.jar" -d bin gui/src/*.java
java -cp "bin:path/to/mysql-connector-j-8.3.0.jar" App
```

On Windows, use `;` instead of `:` in the classpath.

## Documentation

See [`manual.pdf`](manual.pdf) for the user manual (in Portuguese).