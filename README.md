# Athena

Book Management System based on Spring Framework and Restful API

Currently, the Athena Project contains following components:

* A Restful API  `/src/main/java`
* A WeChat Application `/src/main/javascript`

More components like *the front-end based on Angular or React* and *the Android application* will be included in future

This is project is inspired by the question from [here](http://www.cnsoftbei.com/bencandy.php?fid=148&aid=1532)
## Install & Preparation
### Setup database
Use `/src/resource/database.sql` to setup the database.


### Add config file
To install the application, first need to create some config file in resources

Or you can copy the key in `/src/main/java/resource/application.properties.example` and `src/main/java/resource/config.properties.example` , then create corresponding new config file
* application.properties

    > Config MySQL

    key | value
    ----|------
    spring.datasource.url | The url of datasource
    spring.datasource.username | Username
    spring.datasource.password | Password
    spring.datasource.driverClassName | Drive class

    > Config Redis
    
    > Spring Boot has configure most of the configuration for us. Table below lists the configuration that I changed. See [this document](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) to find all the auto-configuration that Spring Boot defined

    key | value
    ----|------
    spring.redis.database| Index of the redis, default 0
    spring.redis.host| Host of the redis, default localhost
    spring.redis.port| Port of the redis, default 6379
    spring.redis.password| Password of the redis

    > Config MongoDB

    key | value
    ----|------
    spring.data.mongodb.host | Mongo server host
    spring.data.mongodb.authentication-database | Authentication database name
    spring.data.mongodb.username |Login user of the mongo server
    spring.data.mongodb.password |Login password of the mongo server
    spring.data.mongodb.database |Database name

* config.properties

    > Config [JWT](http://jwt.io)

    key | value
    ----|------
    security.token.key|The key of JWT Authentication
    security.token.header | The key of JWT in HTTP header
    security.token.prefix | The prefix of JWT
    security.token.expirationtime | The expiration time of JWT

    > Config search param

    key | value
    ----| -----
    search.default.count| The default value on how much result to return per page
    search.limit.expiredtime | How long before reset the time that unauthenticated user
    search.limit.prefix | The prefix added to the remote address of the request when stored and queried in Redis
    search.limit.get.times | How much get request can perform for unauthenticated user within expired time


    > Config url


    key | value
    ----| ----
    web.url| the whole url prefix of project. e.g `http://localhost:8080/api/v1`
    web.url.prefix| the url without protocol host and port e.g `/api/v1`
    
    
    > Config Privilege
    
    
    key | value
    ----| -----
    privilege.sequence | The sequence that denote the importance of privilege.




### Install required library
Run following command in command line to install library by maven `mvn`

The following dependencies are required by Athena, you can also find them in the `pom.xml`

* Spring Boot Starter
    * spring-boot-starter-data-redis
    * spring-boot-starter-data-jpa
    * spring-boot-starter-web
    * spring-boot-starter-security
    * spring-boot-starter-data-mongodb
* Mysql Connector
* Apache Common
* Jpinyin
* Jjwt
* Springfox Swagger2

The dependencies below are required for test

* DBUnit
* Spring Security Test
  > Note that the spring security does not contain some of useful component such as @WithMockUser
* Kotlin Stdlib Jre8
* Kotlin Test
  > Athena will use *Kotlin* for test
* Fongo
* NoSqlUnit


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
    * match the partial name (Default)
    * match the exact name
    * match the pronunciation

* search by author
    * match one author in author list
    * match many authors in author list
    * match all the author

### Create Books
User with Admin or SuperAdmin clearance can create books in database

* create single book
* create multiple books

## API
To access the api documentation,
first run the application
```java
mvn spring-boot:run
```

And then access the `http://localhost:8080/swagger-ui.html#/`

## Exception Check List
Among the Athena, we may encounter different kind of exceptions. The Http standard status code(like 401,400) may not be enough. Thus, we introduce some custom status code which will be include in the response body when exception happens.

code | meaning
-----|--------
***400***|**BadRequest**
4001 | Invalid Copy type
4002 | Illegal entity attribute
***401*** | **Unauthorized**
4010 | Unauthorized
4011 | JWT token is expired
4012 | JWT token uses an unsupported algorithm
4013 | Malformed JWT token
4014 | JWT token's signature is not supported
***403***|**Forbidden**
4031 | cannot delete certain resource because some other resource is not deletable now.
***404*** | **Not Found**
4040 | Resource not found
4041 | Resource not found because the provided id is not exist
40411| Copy cannot be stored because correspond book does not exist
***429***| **Too Many Requests**
4290 | Too many requests
4291 | Too many requests for search
***500***| **Server Error**
5001| Error regarding database connection or repository

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