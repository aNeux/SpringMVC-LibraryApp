Simple Spring Boot application
==============================

## About

This project has been made due to corresponding course of learning the Spring framework. Please see the commit history
to find how the program has evolved over the time. 

Basically, it is a simple CRUD application which is an informational system of a certain library with the ability
to add readers (particular persons), books and assign the latter to the first.

To set up your database connection, please edit file *src/main/resources/application.properties* with necessary credentials.
Driver class and used dialect should be specified there too.

Also, to prepare your database you can go to *init_postgres.sql* file and query all the statements.
**Be carefully**: queries in that file are prepared to be used with *PostgreSQL*, as mentioned in its name.

## Usage

Although it's a fairly simple application, it has several pages where you can access to the specific information.
Please, see the available ones below:
- */people* - shows all added readers to the library system
- */people/new* - contains the simple form to create new reader
- */people/{id}* - shows information about the certain reader
- */people/{id}/edit* - allows to edit the certain reader
- */books* - lists all available books in the library
- */books/new* - the simple form designed to add new book
- */books/{id}* - shows details of the certain book and its holder
- */books/{id}/edit* - allows to edit details of the certain book
- */books/search* - the simple search form to find books and their holders by book's name
