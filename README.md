说明
=================
本项目是 lcn 分布式事务的自动化测试项目，集成了若干场景。
适用于dubbo + mybatis + mysql 的项目
被测试的工程源码：https://github.com/caisirius/tx-lcn.git
 基于 https://github.com/codingapi/tx-lcn.git 做的修改版本

Test dependencies
=================
Before running the tests, the following must be prepared.

python 安装
----

MySql 服务器
----
默认 localhost 3306
创建数据库 test 字符集 utf-8
新建3张表。
执行 SQL 见 [db.sql](db.sql)

修改数据库 地址及 用户名密码：
修改 test-lcn-common/src/main/resources/common.properties 
中的 
```
jdbc.url=
jdbc.username=
jdbc.password=
```

TxManager
----
默认 127.0.0.1  修改地址需要改各个子应用的 tx.properties
```
url=http://127.0.0.1:7000/tx/manager/
```
各个模块的 TxManager需要保持一致

启动 tx-manager。

**上面的准备工作完成后，便可以执行测试用例了**

执行测试
=================
You can run any single test by calling

    python run-tests.py <testname>
    
Or you can run any combination of tests by calling

    python run-tests.py <testname1> <testname2> <testname3> ...

Run the all test suite with

    python run-tests.py
    
testname is the Class Name of TestClass

TestClass name must ends with "Test"


options:

    --help, -help, -?: print help
    -skippkg : 不执行 mvn package


Zookeeper（可选）
----
本测试默认使用广播作为注册中心

若要使用zk

修改test-lcn-common/src/main/resources/common.properties 
中的 
```
dubbo.registry.address=
```
的值为 zookeeper://xxxx