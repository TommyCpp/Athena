# Athena
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/21467d5fe5e04162accce3d650b1b533)](https://www.codacy.com/app/a444529216/Athena?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=TommyCpp/Athena&amp;utm_campaign=Badge_Grade)

使用 Spring 系列框架设计的 RESTful API

目前，该项目包含如下组件
* 一个RESTful API，位于 `/src/main/java`
* 一个微信小程序，位于 `/src/main/javascript`
* 一个基于Augular的前端，位于`/src/main/typescript`

预计未来会加入 Android 客户端

该项目灵感来自于[此问题](http://www.cnsoftbei.com/bencandy.php?fid=148&aid=1532)

## 安装与准备
### 准备数据库
使用 `/src/resource/database.sql` 中的.sql文件来设置数据库

## 添加配置文件
为了安装此应用，首先创建一些配置文件

可以直接复制样例文件 `/src/main/java/resource/application.properties.example` 和 `src/main/java/resource/config.properties.example` ，修改其文件名并按照其中的配置项进行填写

## 安装需要的库
使用 `mvn` 命令安装所需的库

## 启动
使用下列命令来启动应用
```java
mvn spring-boot:run
```