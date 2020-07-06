## Online examination system 在线考试系统

### Development environment
![Language](https://img.shields.io/badge/Java-11-yellow.svg)
![Idea](https://img.shields.io/badge/Idea-2019.3-blue.svg)
![MySql WorkBench](https://img.shields.io/badge/MySqlWorkBench-8.0CE-green.svg)


* Maven
    * 持久化： spring-data-jpa
    * 反序列化： jackson
    * 加密： spring-security-crypto
    * 热部署： devtools
    * 日志： lombok
    * 测试： junit
    * 鉴权： java-jwt 3.4

### Deployment environment
* Server
    * 待定
* System
    * Linux  
* Software
    * Docker :penguin:
### :bookmark_tabs: Change Log 
    
### 2020-07-06 
author: tan
* 集成JWT实现token验证，定义`@PassToken`以及`@UserLoginToken`注解。
    - 用来跳过验证的PassToken
    - 需要登录才能进行操作的注解UserLoginToken
    - 无注释时 默认跳过验证
* LoginController 编写
    - 登录并通过jwt验证后，返回token及role信息，role以隐藏形式返回，token同时在header内携带
* TokenService 编写：token的生成方法
* UserService 编写
    - User findByUsername(User user)
    - User findUserById(User user)
    
### 2020-07-06
author: yang
* 添加devtools，热部署组件 
* 添加spring-security-crypto，加密组件
* 添加jackson-datatype-hibernate5，反序列化组件
* 持久层开发

### 2020-07-06
author: tan
* 配置swagger2接口工具，测试controller层运行状况

### 2020-07-03
author: yang
* 实体类设计

### 2020-07-03
author: yang
* 项目初始化
