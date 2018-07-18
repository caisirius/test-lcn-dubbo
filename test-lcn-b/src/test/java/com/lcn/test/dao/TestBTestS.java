package com.lcn.test.dao;

import com.lcn.test.BaseSpringTest;
import com.lcn.test.dto.base.Result;
import com.lcn.test.lcn.biz.IServiceBBiz;
import com.lcn.test.lcn.biz.ServiceBBiz;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* TestA Tester. 
* 
* @author <Authors name> 
* @since <pre>七月 20, 2017</pre> 
* @version 1.0 
*/ 
public class TestBTestS extends BaseSpringTest {

    @Autowired
    IServiceBBiz serviceBBiz;


    @Test
    public void testTxB() throws Exception {
        try {
            serviceBBiz.doBussiness("key");
        } catch (Exception e) {
            e.printStackTrace();
        }

        result = Result.<String>create().success("OK");
    }

} 
