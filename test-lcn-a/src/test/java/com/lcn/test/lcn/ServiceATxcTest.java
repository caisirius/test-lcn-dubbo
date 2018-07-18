package com.lcn.test.lcn;

import com.lcn.test.dao.test.entity.TestAExample;
import com.lcn.test.dao.test.mapper.TestAMapper;
import com.lcn.test.BaseSpringTest;
import com.lcn.test.dao.test.entity.TestBExample;
import com.lcn.test.dao.test.mapper.TestBMapper;
import com.lcn.test.dto.base.Request;
import org.junit.Assert;
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
    @Autowired
    TestAMapper testAMapper;
    @Autowired
    TestBMapper testBMapper;

    TestAExample example;
    TestBExample example2;

    /** Method: a(Request<String> request)
    */
    @Test
    public void testCommit() throws Exception {
        // 正常提交
//        result = serviceA.insertTxc(Request.<String>create().data("junit"));

//        example = new TestAExample();
//        example2= new TestBExample();
//        example.createCriteria();
//        int counta =testAMapper.countByExample(example);
//        example2.createCriteria();
//
//        int countb =testBMapper.countByExample(example2);
//
//        Assert.assertEquals(1, counta);
//        Assert.assertEquals(2, countb);
    }

    ///// 异常回滚,主事务跑异常（异常不在子事务中）
//    @Test
//    public void testRollback_exception_1() throws Exception {
//        result = serviceA.insertTxc(Request.<String>create().data("error"));
//        assertForRollback();
//    }

    ///// 异常回滚,子事务抛异常,(主子事务在一个工程)
//    @Test
//    public void testRollback_exception_2() throws Exception {
//        result = serviceA.insertTxcException2(Request.<String>create().data("excetion2"));
//        assertForRollback();
//    }

    ///// 异常回滚,子事务抛异常,(主子事务在两个工程)
//    @Test
//    public void testRollback_exception_3() throws InterruptedException {
//        result = serviceA.insertTxcException3(Request.<String>create().data("inner"));
//        assertForRollback();
//    }


    ///// 异常回滚,子事务外跑异常,(子事务和主事务在2个工程)
//    @Test
//    public void testRollback_exception_4() throws InterruptedException {
//        result = serviceA.insertTxcException3(Request.<String>create().data("outter"));
//        assertForRollback();
//    }


    //子事务超时回滚
//    @Test
//    public void testRollback_timeout_sub() throws InterruptedException {
//        result = serviceA.insertTxcTimeoutSub(Request.<String>create().data("timeout"));
//        assertForRollback();
//    }

    //主事务超时回滚
//    @Test
//    public void testRollback_timeout_main() throws InterruptedException {
//        result = serviceA.insertTxcTimeoutMain(Request.<String>create().data("timeout"));
//        assertForRollback();
//    }


    //子事务加起来超时
//    @Test
//    public void testRollback_timeout_subs() throws InterruptedException {
//        result = serviceA.insertTxcTimeoutSubs(Request.<String>create().data("timeout"));
//        assertForRollback();
//    }


} 
