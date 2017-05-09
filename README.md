# Athena

Book Management System based on Spring Framework and Restful API

## Install & Preparation
### Add Config file
To install the application, first need to create some config file in resources
* application.properties

    > Setting the Database info

    key | value
    ----|------
    spring.datasource.url | The url of datasource
    spring.datasource.username | Username
    spring.datasource.password | Password
    spring.datasource.driverClassName | Drive class
* config.properties

    > Setting the JWT info

    key | value
    ----|------
    security.token.key|The key of JWT Authentication
    security.token.header | The key of JWT in HTTP header
    security.token.prefix | The prefix of JWT
    security.token.expirationtime | The expiration time of JWT

### Install required library
Run following command in command line to install library by maven `mvn`

The following dependencies are required by Athena, you can also find them in the `pom.xml`

* Spring Boot
* Mysql Connector
* Spring Security
* Apache Common
* Jjwt

The dependencies below are required for test

* DBUnit

## Feature

