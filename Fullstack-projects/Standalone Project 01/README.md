# Web Development Technical Assessment

A full-stack web application developed as part of a web development technical assessment.

The project consists of a contact data management system that allows users to enter, validate, store, and dynamically display contact information using a PHP backend and a MySQL database.

## Technologies

- HTML5
- CSS3
- Bootstrap
- JavaScript
- jQuery
- AJAX
- PHP
- MySQL

## Features

### Data Entry

The application provides a form that allows users to enter:

- Name
- Email
- Phone

Before submitting the information, the application validates the entered data.

The validation includes:

- Checking that the name field is not empty.
- Checking that the email field is not empty.
- Validating the email format.
- Ensuring that email addresses are unique.
- Ensuring that phone numbers are unique.

Once the information passes validation, the data is sent asynchronously using AJAX and stored in the MySQL database through the PHP backend.

The newly inserted record is then displayed immediately at the top of the list without requiring the page to be refreshed.

### Data Listing

The application displays the contact records stored in the MySQL database.

The listing:

- Displays a maximum of 8 records at a time.
- Displays newly inserted records immediately.
- Places the most recently inserted record at the top of the list.
- Updates dynamically without requiring a full page refresh.

## Database

The application uses MySQL to persist contact information.

In addition to the data entered by the user, the database stores the date and time associated with each inserted record.

The SQL script required to create the database structure is included in:

`prueba_tecnica_rodrigo_delgado.sql`

## Local Setup

To run this project locally, you need a development environment capable of running PHP and MySQL.

One recommended option is [XAMPP](https://www.apachefriends.org/), which provides an Apache web server and a MySQL database environment.

### 1. Install XAMPP

Download and install XAMPP on your computer.

Open the XAMPP Control Panel and start:

- Apache
- MySQL

Both services must be running while using the application.

### 2. Place the Project

Copy the project folder into the XAMPP `htdocs` directory.

For example:

`C:\xampp\htdocs\Web-Development-Technical-Assessment`

### 3. Create the Database

Open phpMyAdmin in your browser:

`http://localhost/phpmyadmin`

Create the required database and import the following SQL file:

`prueba_tecnica_rodrigo_delgado.sql`

The SQL file contains the database structure required by the application.

### 4. Configure the Database Connection

Open:

`basedatos.php`

Make sure that the database connection settings match your local MySQL configuration.

Depending on your local environment, you may need to configure:

- Database host
- Database name
- Database username
- Database password

### 5. Run the Application

Once Apache and MySQL are running, open the application through your local server.

For example:

`http://localhost/Web-Development-Technical-Assessment/`

The application should now be able to communicate with the PHP backend and MySQL database.

You can then:

- Enter new contact information.
- Validate the submitted data.
- Store new records in the MySQL database.
- View existing records.
- See newly inserted records immediately without refreshing the page.

## Project Structure

```text
Web-Development-Technical-Assessment/
│
├── index.html
├── estilos.css
├── funciones.js
├── basedatos.php
├── insertar.php
├── lista.php
├── prueba_tecnica_rodrigo_delgado.sql
└── README.md
```

## Technical Requirements

The project was developed using the following requirements:

- HTML, CSS, Bootstrap, JavaScript/jQuery, and PHP.
- Contact data entry through a web form.
- Validation of required fields.
- Email format validation.
- Unique email addresses.
- Unique phone numbers.
- AJAX-based data submission.
- MySQL database persistence.
- Storage of the insertion date and time.
- Dynamic display of newly inserted records.
- A maximum of 8 records displayed at a time.
- Dynamic data updates without requiring a full page refresh.
- Layout primarily based on `div` elements rather than HTML tables.
- Styled form elements and user interface.
- Cross-browser considerations.

## Notes

This project is intended to be run in a local development environment.

Since the application uses PHP and MySQL, it cannot be fully executed by simply opening the `index.html` file directly in a browser. A local web server and a running MySQL database are required for the backend and database functionality to work correctly.

Using XAMPP is one convenient way to provide the required local environment.

## Author

**Rodrigo Delgado**
