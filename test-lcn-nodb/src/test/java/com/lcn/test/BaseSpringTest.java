package com.lcn.test;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.codingapi.tx.listener.service.InitService;
import com.lcn.test.dto.base.BaseResult;
import com.lcn.test.lcn.DataChecker;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * [类描述]
 *
 * @author caican
 * @date 17/7/20
 * @title [confluence页面的title]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/applicationContext.xml"})
public class BaseSpringTest {

    protected BaseResult result;
    private long start;

    @Autowired
    protected DataChecker dataChecker;

    @Autowired
    private InitService initService;

    @Before
    public void before() throws InterruptedException {
        start = System.currentTimeMillis();
//        while (!initService.isInited()) {
//            Thread.sleep(100);
//            if (System.currentTimeMillis() - start > 15000){
//                throw new RuntimeException("waiting for lcn init failed!");
//            }
//        }
        Thread.sleep(5000);
        dataChecker.reset();
        RpcContext.removeContext();
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

    }


    public String getTestSid() {
        return UUID.randomUUID().toString();
    }

}