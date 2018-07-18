package com.lcn.test.lcn;

import com.lcn.test.BaseSpringTest;
import com.lcn.test.dto.base.Request;
import com.lcn.test.dto.base.Result;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* ServiceAImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>七月 26, 2017</pre> 
* @version 1.0 
*/
public class ServiceANodbLcnTest extends BaseSpringTest {

    @Autowired
    ServiceA serviceA;

    /**
     * 测试LCN模式 正常提交 a->b
     * a服务：(无数据库配置)call b服务连续2次
     * b服务：插入一条记录，正常返回
     * @throws Exception
     */
    @Test
    public void testCommit() throws Exception {
        // 正常提交
        result = serviceA.insertLcn(Request.<String>create().data("nodb"));

        int countb = dataChecker.countb();

        Assert.assertEquals(2, countb);

    }

    /**
     * 测试LCN模式 异常 a->b
     * a服务：(无数据库配置)call b服务连续2次，之后故意抛异常
     * b服务：插入一条记录，正常返回
     * @throws Exception
     */
    @Test
    public void testRollback() throws Exception {
        result = serviceA.insertLcn(Request.<String>create().data("error"));

        int countb = dataChecker.countb();

        Assert.assertEquals(0, countb);
    }

    /**
     * 测试LCN模式 正常提交 a->b
     * a服务：(无数据库配置)call b服务1，call b服务2
     * b服务1：插入一条记录，正常返回id
     * b服务2：更新 amount 字段更新为 amount+1
     * @throws Exception
     */
    @Test
    public void testCommitUpdt() throws Exception {
        // 正常提交
        // 准备一条数据
        int id = dataChecker.insert();
        result = serviceA.insertUpdtLcn(Request.<Integer>create().data(id));

        int countb = dataChecker.countb();

        Assert.assertEquals(2, countb);

        int amount = dataChecker.getAmount(id);
        Assert.assertEquals(11, amount);
    }

} 
