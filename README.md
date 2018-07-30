# SpringbootAdminSystem

#### 项目介绍
该项目在SpringBoot2.0的基础上搭建的一个Java基础开发框架，以Spring MVC为模型视图控制器，MyBatis为数据访问层， Apache Shiro为权限授权层，redis对常用数据进行缓存。
后台系统的基础功能有用户管理、角色管理、权限管理、日志管理等，正在开发中；前台已经实现用户登录，注册等基础功能。 同时前后台会员实现分表管理，可扩展多角色系统、多权限系统。 采用分层设计、双重验证、提交数据安全编码、密码加密、访问验证、数据权限验证。 使用Maven做项目管理，提高项目的易开发性、扩展性。

技术选型
1、后端

核心框架：SpringBoot 2.0
集成运行框架：Tomcat 8.5.11
安全框架：Apache Shiro 
视图框架：Spring MVC 
服务端验证：Hibernate Validator 
布局框架：Thymeleaf 
持久层框架：MyBatis 
缓存框架：Redis
工具类：Apache Commons、Jackson 、Junit 4
2、前端

JQ框架：jQuery 2.2.4
JQ兼容插件：jQuery-Migrate 1.4.1
CSS框架：Twitter Bootstrap 3.3.7+AdminLte 2.3.7
客户端验证：jQuery Validate Plugin 1.15。
数据表格：BootStrap-Table 1.11
树数据列表：jQuery-Treegrid 0.2
树结构控件：BootStrap-Treeview 1.2
工具类框架：Layer 3.0


快速体验
1. JDK1.8+、Maven3.0+、MySql5+
2. 修改src\main\resources\application.yml、application-dev.properties文件中的数据库设置参数(application-dev.properties为开发环境的相应参数)。
3. 根据修改参数创建对应MySql数据库用户和参数
4. 将src\main\resources\table.sql导入数据库
5. 最高管理员账号，用户名：admin 密码：admin
6. 目前有些功能未实现




