# Simulation of user register and login to a website
#### Video Demo:  https://youtu.be/tgClwLKnM48
#### Description 
This project implements a simulation of a User Registration and Login System in Python that allows users to create accounts and log in as they would do in any website. The system validates usernames and passwords based on defined criteria, and all user data is saved to a text file (users.txt) for persistent storage. It operates entirely via the command line, making it a simple yet effective tool for managing user credentials.

The system is designed to ensure both security and usability through input validation, and it provides an easy-to-follow user experience.

#### Features
###### User Registration:
Users can register by entering a username and password.
The username must be between 3 and 7 characters and consist of only lowercase letters.
The password must be less than 11 characters and must contain at least one number and one uppercase letter.
If the username or password doesn't meet these requirements, the user is notified with a specific error message.
Upon successful registration, user data is saved to a text file (users.txt), allowing for persistence between sessions.

###### User Login:
Registered users can log in by entering their username and password.
The system checks the entered credentials against the users.txt file.
If the credentials match, the user is logged in successfully; otherwise, an error message is displayed.

###### Persistent Storage:
All registered users and their credentials are stored in the users.txt file in the format "username,password".
This ensures that user data is saved and can be accessed across different sessions of the program.
The data is stored in plain text, but it can easily be modified to implement encrypted password storage for added security.

###### Validation Functions:
The system includes validation functions that ensure:
    -The username is between 3 and 7 characters long and consists only of lowercase letters.
    -The password is less than 11 characters and contains at least one number and one uppercase letter.
Users are prompted with clear error messages if their inputs don't meet these criteria.

#### Project files
###### project.py:
This is the main file of the project, containing the core logic and functionality. It includes:

User Class:
    A class that holds user data (username and password) and includes methods to convert user information to and from string format for easy file storage.

Functions for Registration and Login:
    register_user(name, password): Handles the user registration process, including validation of inputs and saving data to users.txt.
    login_user(name, password): Handles user login, verifying credentials against stored data.

Input Validation Functions:
    is_valid_username(name): Ensures that the username is valid based on length and character restrictions.
    is_valid_password(password): Validates the password to ensure it meets the character length and complexity requirements.

Main Function:
    The entry point for the program. It processes the command-line arguments (either register or login), interacts with the user for input, and calls the appropriate functions for registration or login.

###### test_project.py:
This file contains automated tests for various aspects of the project, including:

User Class Tests: Tests the functionality of the User class, ensuring that it correctly stores and retrieves user data.

Input Validation Tests: Tests the validation functions for both usernames and passwords, confirming that they reject invalid input.

Registration and Login Tests: Tests the registration and login functions, ensuring that users are successfully registered and logged in with valid credentials, and that invalid credentials return appropriate error messages.

Edge Case Tests: Tests edge cases, such as usernames that are too short or too long, passwords that lack a number or uppercase letter, and login attempts with incorrect credentials.

###### users.txt:
This text file is used to store registered users' data in the format "username,password". Each time a user successfully registers, their credentials are appended to this file. If the file doesn't exist, it is created automatically during the first registration.
