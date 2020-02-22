# Getting Started
This is a simple spring boot application which enables of creating a customer and updating its marketing consent.

This application is meant as a service against which to implement a test task for Quality Assurance

## Bugs introduced into the application
1) customers can be created without any data validation
2) When creating a new customer, marketing consent will never be saved to database with the initial payload
