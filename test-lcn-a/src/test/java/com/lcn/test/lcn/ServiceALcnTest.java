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
*/
public class ServiceALcnTest extends BaseSpringTest {

    @Autowired
    ServiceA serviceA;

    /**
     * 测试LCN模式 正常提交 a->b
     * a服务：插入一条记录，call b服务连续2次
     * b服务：插入一条记录，正常返回
     * @throws Exception
     */
    @Test
    public void testCommit() throws Exception {
        // 正常提交
        long start = System.currentTimeMillis();
        result = serviceA.insertLcn(Request.<String>create().data("junit"));
        System.out.println("insertLcn cost: " + (System.currentTimeMillis() - start));
        assertForInsert(1,2, 0);

    }
    /**
     * 测试LCN模式 异常-主事务异常 a->b
     * a服务：插入一条记录，call b服务连续2次，之后故意抛异常
     * b服务：插入一条记录，正常返回
     * @throws Exception
     */
    @Test
    public void testRollback() throws Exception {
        result = serviceA.insertLcn(Request.<String>create().data("error"));
        assertForRollback(0);
    }


} 
