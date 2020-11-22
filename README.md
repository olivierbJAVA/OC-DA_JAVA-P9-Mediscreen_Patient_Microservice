# Mediscreen - Patient application
Welcome to Mediscreen !

- Mediscreen is a medical company specialized in disease diagnosis
- Mediscreen wants to develop a new application which goal is to diagnose the risk for patients of having diabetes

### Mediscreen Diabetes Application

The application is composed of 3 Microservices :
- Mediscreen Patient : in charge of managing patients and their personal data
- Mediscreen Note : in charge of managing notes written by doctors
- Mediscreen Report : in charge of generating reports avaluating the risk for patients to develop diabetes

This repository contains the Mediscreen Patient Microservice.

You will find the other Microservices in the following repositories :
- Mediscreen Note : 
- Mediscreen Report : 

### Technologies used

Technologies used for this Microservice are the following.

BackEnd side :
- Java as programming language
- SpringBoot for the web application which is based on the MVC pattern
- Server is SpringBoot Tomcat embedded
- Gradle to run the tests, build and package the application
- MySQL as database
- JUnit as tests engine
- Mockito as mocking framework for tests

FrontEnd side :
- HTML/CSS + Bootstrap for the views (User Interface)
- Thymeleaf as template engine

Microservices communicate using REST APIs.

### Getting Started

The following instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

You need to install the following software :

- Java 8
- Gradle 6.6.1
- MySQL Server 5.7
- Docker + Docker-Compose
>You don't need to install SpringBoot by yourself because dependencies will be added by Gradle

### Installing

You will find below a step by step explanation that tell you how to get a development environment running :

1.Install Java :
<https://www.oracle.com/java/technologies/javase-jdk11-downloads.html>

2.Install Gradle :
<https://gradle.org/install/>

3.Install MySQL :
<https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/>

4.Install Docker + Docker-Compose :
<https://docs.docker.com/get-docker/>

### Profiles and Configuration

Three Spring profiles are available for each following phase :

- PROD profile used for Production phase
- DEV profile used for Development phase
- TEST profile used for Test phase

There is a global Spring configuration properties file : application.properties, and a dedicated configuration properties file for each profile : application-*profileName*.properties. 
These files are stored in the src/main/resources directory for PROD and DEV profiles and in the src/test/resources directory for the TEST profile.

### DataBase creation and initialization

The username and password for connection to the database are stored in the configuration application-*profileName*.properties files. You must fill these properties files with your own username and password.

For the DEV profile, the database is initialized with some patients. This is done using the file : data-dev.sql (automatically run by SpringBoot).

>During installing, application running or tests launching you may have an issue (depending on your configuration) related to Time zone configuration. It is an issue due the configuration of MySQL server.

>To solve this issue, you can add the following line in the MySQL server configuration file (my.ini or my.cfg) that is in your MySQL directory : default-time-zone='+02:00'.

>This line must be added in the [mysqld] section of the configuration file and may be adapted to your own local timezone obviously.

### Application running

Then you can import and run the application from your favorite IDE.

>Please note that the application has been developed with the IntelliJ IDE.

### Endpoints

For information about EndPoints that are exposed by the Mediscreen Patient Microservice, please refer to the document in this repository called : 

### Docker container deployment

A Dockerfile is present in this repository in order to deploy the application in a Docker container.
>In order to build a Docker Image using this Dockerfile, please use the following command line (in the *Dockerfile* directory) :
`docker build -t patient .`

When the Patient Docker image is created, you can run the Microservice using the *docker-compose.yml* file present in this repository.
>To do this, please use the following commend line (in the *docker-compose.yml* directory) :
`docker-compose up`
 
This will :
- Create and launch a MySQL server in a container
- Launch the Patient application in a container
- Create a dedicated Docker bridge network to enable their communication 
- Map the directory containing the MySQL data in an external directory so that data are note lost if container is deleted
 
### Tests

Tests are included. You can run them using JUnit runner or using Gradle.