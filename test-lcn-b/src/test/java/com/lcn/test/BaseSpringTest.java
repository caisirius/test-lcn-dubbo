package com.lcn.test;

import com.alibaba.fastjson.JSON;
import com.lcn.test.dto.base.BaseResult;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static junit.framework.Assert.assertTrue;

/**
 * [类描述]
 *
 * @author caican
 * @date 17/7/20
 * @title [confluence页面的title]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BaseSpringTest {

    protected BaseResult result;
    private long start;

    @Before
    public void before() {
        start = System.currentTimeMillis();
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - start;
        if (result != null){
            printResult();
        }
        System.out.println("cost : " + cost + " ms");
        System.out.println("------------------------------------------------");
    }

    public void printResult() {
        System.out.println("------------------result------------------------");
        System.out.println(JSON.toJSONString(result));
        System.out.println("------------------------------------------------");

        assertTrue(result.isSuccess());
    }

    public void printResult(BaseResult result) {
        System.out.println("------------------result------------------------");
        System.out.println(JSON.toJSONString(result));
        System.out.println("------------------------------------------------");

        assertTrue(result.isSuccess());
    }

    public String getTestSid() {
        return UUID.randomUUID().toString();
    }
}