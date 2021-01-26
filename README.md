# 大数据相关组件工具类

## 连接类工具开发规范
1. 我们认为对于连接类工具，连接服务端的用户都称为客户端，也就是client，所以对于连接类工具，
我们统一使用名为xxxClient的类来建立与服务端的连接，并且xxxClient只做连接操作。对于类命名，
举例有MysqlClient。（特例：如果存在同名xxxClient，那么加一个前缀，MyxxxClient）

2. 对于连接过程中需要的配置，统一通过properties文件进行配置，命名格式为xxx-config.properties，
例如mysql-client.properties. 而对于xxx-config.properties文件中的配置项，我们在文件中定义的同时，
也在xxxEnv类中使用静态变量定义，同时，配置文件名称也在xxxEnv中定义。

3. 通常来说，对于建立连接的xxxClient通常是用于对服务端进行某些操作的。
那么此时我们定义一个接口，命名为IxxxOperation。该xxxClient在与服务端建立连接之后的可用操作就在此接口中定义。

4. 定义抽象类用于继承xxxClient和IxxxOperation，表示对拥有已经建立连接的client以及相关操作，
那么对于其他需要使用此连接工具类的同学来说，只需要继承此抽象类即可。

5. 在连接类工具中，需要使用日志对连接信息进行记录，也就是说
 - 在建立连接之前需要输出：初始化xxx连接
 - 建立连接成功之后需要输出：xxx连接成功
 - 对于建立连接之后的操作中，在执行操作之前，需要输出：初始化xxx操作
 - 对于操作成功之后，则需要输出：xxx操作成功

6. xml文件注释规范
统一使用<!-- -->进行注释，注释规范如下
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

    <properties resource="generatorConfig.properties"/>
    <classPathEntry location="src/main/resources/mysql-java-jdbc/mysql-connector-java-5.1.49.jar"/>
    <context id="default"   defaultModelType="flat">

        <!--
        注释信息
        -->
        <commentGenerator>
            <property name="suppressDate" value="true"/>
            <property name="addRemarkComments" value="true"/>
        </commentGenerator>

        <!--
        jdbc连接信息  mysql
        -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://hadoop1:3306/test"
                        userId="root" password="m98Edicines#">
        </jdbcConnection>

        <!--
            Model模型生成器,用来生成含有主键key的类，记录类 以及查询Example类
            targetPackage     指定生成的model生成所在的包名
            targetProject     指定在该项目下所在的路径
        -->
        <javaModelGenerator
                targetPackage="com.zh.ch.bigdata.mybatis.bean"
                targetProject="${target.java.dir}">
            <!-- 
            是否允许子包，即targetPackage.schemaName.tableName
            -->
            <property name="enableSubPackages" value="false"/>
            <!--
            是否对model添加构造函数
            -->
            <property name="constructorBased" value="true"/>
            <!--
            是否对类CHAR类型的列的数据进行trim操作
            -->
            <property name="trimStrings" value="true"/>
            <!--
            建立的Model对象是否 不可改变  即生成的Model对象不会有 setter方法，只有构造方法
            -->
            <property name="immutable" value="false"/>
        </javaModelGenerator>

        <!--
        mapper映射文件生成所在的目录 为每一个数据库的表生成对应的SqlMap文件
        -->
        <sqlMapGenerator targetPackage="com.zh.ch.bigdata.mybatis.dao"
                         targetProject="${target.xml.dir}">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>
        <javaClientGenerator targetPackage="com.ztesoft.monitor.dao" targetProject="${target.java.dir}" type="XMLMAPPER"/>

        <table schema="" tableName="zs_dynamic_query_config"
               enableCountByExample="true" enableUpdateByExample="true"
               enableDeleteByExample="true" enableSelectByExample="true"
               selectByExampleQueryId="true">
        </table>

    </context>

</generatorConfiguration>
```

注释信息包在<!-- -->之间，并且与注释符号不在同一行。