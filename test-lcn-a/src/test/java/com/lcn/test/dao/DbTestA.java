package com.lcn.test.dao;

import com.lcn.test.BaseSpringTest;
import com.lcn.test.dao.test.mapper.TestAMapper;
import com.lcn.test.dto.base.Result;
import com.lcn.test.lcn.biz.ServiceABiz;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* TestA Tester. 
* 
* @author <Authors name> 
* @since <pre>七月 20, 2017</pre> 
* @version 1.0 
*/ 
public class DbTestA extends BaseSpringTest{

    @Autowired
    TestAMapper testAMapper;
    @Autowired
    ServiceABiz serviceABiz;


    @Test
    public void testTx() throws Exception {
        try {
            serviceABiz.doBussiness("key");
        } catch (Exception e) {
            e.printStackTrace();
        }

        result = Result.<String>create().success("OK");
    }

} 
