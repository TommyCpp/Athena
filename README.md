# Athena

Book Management System based on Spring Framework and Restful API

Currently, the Athena Project contains following components:

* A Restful API  `/src/main/java`
* A WeChat Application `/src/main/javascript`

More components like *the front-end based on Angular or React* and *the Android application* will be included in future

This is project is inspired by the question from [here](http://www.cnsoftbei.com/bencandy.php?fid=148&aid=1532)
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

    > Setting the [JWT](http://jwt.io) info

    key | value
    ----|------
    security.token.key|The key of JWT Authentication
    security.token.header | The key of JWT in HTTP header
    security.token.prefix | The prefix of JWT
    security.token.expirationtime | The expiration time of JWT

    > Setting some value regarding the search

    key | value
    ----| -----
    search.default.count| The default value on how much result to return per page

### Install required library
Run following command in command line to install library by maven `mvn`

The following dependencies are required by Athena, you can also find them in the `pom.xml`

* Spring Boot
* Mysql Connector
* Spring Security
* Apache Common
* Jpinyin
* Jjwt

The dependencies below are required for test

* DBUnit
* Spring Security Test
  > Note that the spring security does not contain some of useful component such as @WithMockUser
* Kotlin Stdlib Jre8
* Kotlin Test
  > Athena will use *Kotlin* for test


## Start
To run the application, use
```java
mvn spring-boot:run
```

## Feature
### Overall
The Athena application is a RESTful application, which shall be used with several endpoint such as Android application, Angular/React based website frontend or Wechat App (WeChat Mini Applications)

### Authentication
Currently, Athena has two kind of users, ADMIN and USER.

We define the ADMIN as the admin of library, which has the privilege to lend the books to users.
And the USER shall pay the pledge before they can borrow the book.

The authentication will drive by JWT (JSON Web Token), which is a powerful tool for stateless authentication. Configuration about JWT can found above. The basic authentication process can be described as following:


1. Send authentication information to certain URL
1. Spring Security will take care the process to authentication.
1. After the user info has been verified, an JWT will be generated and send back to client.
1. When client request some confidential data, JWT should be included in *request head* in order to state the user principle.


Notice that since the only authentication token is JWT, It is crucial for the client to protect JWT from leaking.

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
model| Test the model class and the converter
security | Test the authentication function
service | Test the service class

### Configure
Because the user table has one field name `password`, which is the reserve word of MySQL. So before test something regarding the User table, we need to override some of the default config.
Specifically, We need to config the test as follows:
```java
    @Bean
    public DatabaseConfigBean databaseConfigBean() {
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        databaseConfigBean.setEscapePattern("`?`"); //we change the default config here.
        return databaseConfigBean;
    }

    @Bean
    @Autowired
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource, DatabaseConfigBean databaseConfigBean) {
        DatabaseDataSourceConnectionFactoryBean factoryBean = new DatabaseDataSourceConnectionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setDatabaseConfig(databaseConfigBean);
        return factoryBean;
    }
```

### Test Case Explanation
#### model.BooKTest
* Find one book which ISBN is 9787111124444, assert whether the book is the intend one.

#### model.PublisherTest
* Assert whether the publisher can access the book it published

#### model.UserTest
* Create an user, then save it to the database.
* Change some attribute of the User, Assert the password does not be encrypt again.

#### model.WriterConverter
* Assert the WriterConverter can change the String[] into String concatenated by `,`

#### service.PageableHeaderServiceTest
* Query books with `http://www.example.com/books?author=test,test&last_cursor=555&page=4`. Assert the response has the right header param.

#### service.BookServiceTest
* Test the search by title `"埃里克森", "程序设计"`, assert that the corresponding book is in the result.

#### service.PinyinConverterTest
* Test the convert from word to pinyin
* Test the convert from word to short version pinyin (composed by the first letter of every char)

#### service.UserServiceTest
* Test get user with id 11