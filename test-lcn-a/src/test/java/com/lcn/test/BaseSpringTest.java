package com.lcn.test;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.codingapi.tx.listener.service.InitService;
import com.lcn.test.dao.test.entity.TestAExample;
import com.lcn.test.dao.test.entity.TestBExample;
import com.lcn.test.dao.test.mapper.TestAMapper;
import com.lcn.test.dao.test.mapper.TestBMapper;
import com.lcn.test.dao.test.mapper.TestExtMapper;
import com.lcn.test.dto.base.BaseResult;
import org.junit.After;
import org.junit.Assert;
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
    TestExtMapper testExtMapper;
    @Autowired
    TestAMapper testAMapper;
    @Autowired
    TestBMapper testBMapper;

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
        resetDb();
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

    protected void resetDb() {
        testExtMapper.truncateTestB();
        testExtMapper.truncateTestA();
    }

    protected void assertForInsert(int aLen, int bLen, int sleepSec)  {
        TestAExample example;
        TestBExample example2;
        int counta,countb;
        if (sleepSec > 0) {
            try {
                Thread.sleep(sleepSec*1000);// TXC 模式是异步回滚，等待几秒待回滚完成
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        example = new TestAExample();
        example.createCriteria();
        counta =testAMapper.countByExample(example);

        example2= new TestBExample();
        example2.createCriteria();
        countb =testBMapper.countByExample(example2);

        Assert.assertEquals(aLen, counta);
        Assert.assertEquals(bLen, countb);
    }

    protected void assertForRollback(int sleepSec)  {
        assertForInsert(0,0,sleepSec);
    }
}