# Athena
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/21467d5fe5e04162accce3d650b1b533)](https://www.codacy.com/app/a444529216/Athena?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=TommyCpp/Athena&amp;utm_campaign=Badge_Grade)

使用 Spring 系列框架设计的 RESTful API

目前，该项目包含如下组件
* 一个RESTful API，位于 `/src/main/java`
* 一个微信小程序，位于 `/src/main/javascript`
* 一个基于Augular的前端，位于`/src/main/typescript`

预计未来会加入 Android 客户端

该项目灵感来自于[此问题](http://www.cnsoftbei.com/bencandy.php?fid=148&aid=1532)

Library Management System based on Spring Framework and RESTful API

Currently, the Athena Project contains following components:

* A RESTful API  `/src/main/java`
* A WeChat Application `/src/main/javascript`
* A frontend based on Angular `/src/main/typescript`

More components like *the Android application* will be included in future

This is project is inspired by the question from [here](http://www.cnsoftbei.com/bencandy.php?fid=148&aid=1532)
## Install & Preparation
### Setup database
使用 `/src/resource/database.sql` 中的.sql文件来设置数据库
Use .sql file `/src/resource/database.sql` to setup the database.

### Add config file
为了安装此应用，首先创建一些配置文件

可以直接复制样例文件 `/src/main/java/resource/application.properties.example` 和 `src/main/java/resource/config.properties.example` ，修改其文件名并按照其中的配置项进行填写

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
使用 `mvn` 命令安装所需的库

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
使用下列命令来启动应用
To run the application, use
```java
mvn spring-boot:run
```


## API
启动应用程序之后访问如下网址来获取API列表

```
http://localhost:8080/swagger-ui.html#/
```

To access the api documentation,
first run the application and then access the
```
http://localhost:8080/swagger-ui.html#/
```

## Exception Check List
在该应用中，我们自定义了一些异常以及异常码，下表列出了各类异常

Among the Athena, we may encounter different kind of exceptions. The Http standard status code(like 401,400) may not be enough. Thus, we introduce some custom status code which will be include in the response body when exception happens.


code | meaning
-----|--------
***400***|**BadRequest**
4001 | Invalid Copy type
4002 | Illegal entity attribute
4003 | Illegal borrow request
4004 | Illegal return request
4005 | Unsupported Params
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
