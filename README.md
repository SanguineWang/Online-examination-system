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
    * 缓存： redis
### Deployment environment
* Server
    * 待定
* System
    * Linux  
* Software
    * Docker :penguin:
### :bookmark_tabs: Change Log 
### 2020-07-07 
author: wang
* Controller接口设计
    - `TeacherController`
    - `StudentController`   
author: chang
* Controller接口设计
    - `AdminController`    
### 2020-07-06 
author: tan
* 集成JWT实现token验证，定义`@PassToken`以及`@UserLoginToken`注解。
    - 用来跳过验证的PassToken
    - 需要登录才能进行操作的注解UserLoginToken
    - 无注释时 默认跳过验证
* Controller 编写
    - `LoginController`：登录并通过jwt验证后，返回token及role信息，role以隐藏形式返回，token同时在header内携带
* Service 编写
    -  `TokenService` 编写：token的生成方法
    -  `UserService` 编写
        - User findByUsername(User user)
        - User findUserById(User user)
* interceptpor 编写
    - `AuthenticationInterceptor`：jwt鉴权
    - `Admin`,`Student`,`Teahcer`:各角色权限拦截器
* Component 编写
    - `InitComponent`:初始化用户（1001,2007）
    - `RequestComponent` ：获取线程级的attribute，将提取Attribute的过程提取出来,减少耦合

author: wang
* 添加`devtools`，热部署组件 
* 添加`spring-security-crypto`，加密组件
* 添加`jackson-datatype-hibernate5`，反序列化组件
* 持久层接口
    - 继承自定义的`BaseRepository`接口，实现了`EntityManager`的`refresh`操作。
* 集成redis（lettuce）
    - 部署在 `114.115.181.66:8080`端口

### 2020-07-03
author: wang
* 实体类设计

### 2020-07-03
author: wang
* 项目初始化
