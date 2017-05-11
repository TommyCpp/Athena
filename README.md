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
### Overall
The Athena application is a RESTful application, which shall be used with several endpoint such as Android application, Angular/React based website frontend or Wechat App (WeChat Mini Applications)

### Authentication
Currently, Athena has two kind of users, ADMIN and USER.

We define the ADMIN as the admin of library, which has the privilege to lend the books to users.
And the USER shall pay the pledge before they can borrow the book.

### Search
Athena support the following search strategy

* search by name
    * march the partial name (Default)
    * march the exact name
    * march the pronunciation

* search by author
    * march one author in author list
    * march all the author



## Test
This section will introduce the basic test component in Athena
### Structure
The test files is located in `src\test\java\com\athena` it contains following folders

name | content
-----|--------
model|| Test the model class and the converter
security | Test the authentication function
service | Test the service class

### Test Case Explanation
#### model.BooKTest
* find one book which ISBN is 9787111124444, assert whether the book is the intend one.

#### model.PublisherTest
* Assert whether the publisher can access the book it published

