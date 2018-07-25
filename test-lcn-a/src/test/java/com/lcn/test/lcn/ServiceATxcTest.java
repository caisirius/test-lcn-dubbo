package com.lcn.test.lcn;

import com.lcn.test.BaseSpringTest;
import com.lcn.test.dto.base.Request;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* ServiceAImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>七月 26, 2017</pre> 
* @version 1.0
 * TODO
*/
public class ServiceATxcTest extends BaseSpringTest {

    @Autowired
    ServiceA serviceA;

    /**
     * 测试TXC模式 正常提交 a->b
     * a服务：插入一条记录，call b服务连续2次
     * b服务：插入一条记录，正常返回
     * @throws Exception
     */
    @Test
    public void testCommit() throws Exception {
        // 正常提交
        result = serviceA.insertTxc(Request.<String>create().data("txc"));

        assertForInsert(1,2, 0);
    }

    /**
     * 测试TXC模式 异常-主事务异常 a->b
     * a服务：插入一条记录，call b服务连续2次，之后故意抛异常
     * b服务：插入一条记录，正常返回
     * @throws Exception
     */
    @Test
    public void testRollback_exception_1() throws Exception {
        result = serviceA.insertTxc(Request.<String>create().data("error"));
        assertForRollback();
    }

    /**
     * 测试TXC模式 异常-主事务异常  a
     * a服务：插入一条记录,之后故意抛异常, 异常在 @Transaction内
     * @throws Exception
     */
    @Test
    public void testRollback_exception_2() throws Exception {
        result = serviceA.insertTxcException2(Request.<String>create().data("excetion2"));
        assertForRollback();
    }

    /**
     * 测试TXC模式 异常-主事务异常 a->b
     * a服务：插入一条记录，call b服务1次
     * b服务：插入一条记录，在 @Transaction内发生异常
     * @throws Exception
     */
    @Test
    public void testRollback_exception_3() throws InterruptedException {
        result = serviceA.insertTxcException3(Request.<String>create().data("inner"));
        assertForRollback();
    }


    /**
     * 测试TXC模式 异常-主事务异常 a->b
     * a服务：插入一条记录，call b服务1次
     * b服务：插入一条记录，在 @Transaction 外部发生异常
     * @throws Exception
     */
    @Test
    public void testRollback_exception_4() throws InterruptedException {
        result = serviceA.insertTxcException3(Request.<String>create().data("outter"));
        assertForRollback();
    }


    //子事务超时回滚
    @Test
    public void testRollback_timeout_sub() throws InterruptedException {
        result = serviceA.insertTxcTimeoutSub(Request.<String>create().data("timeout"));
        assertForRollback();
    }

    //主事务超时回滚
    @Test
    public void testRollback_timeout_main() throws InterruptedException {
        result = serviceA.insertTxcTimeoutMain(Request.<String>create().data("timeout"));
        assertForRollback();
    }


    //子事务加起来超时
    @Test
    public void testRollback_timeout_subs() throws InterruptedException {
        result = serviceA.insertTxcTimeoutSubs(Request.<String>create().data("timeout"));
        assertForRollback();
    }


} 
