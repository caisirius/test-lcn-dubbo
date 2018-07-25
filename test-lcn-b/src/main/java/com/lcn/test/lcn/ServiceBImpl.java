package com.lcn.test.lcn;

//import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.lcn.test.dao.test.entity.TestB;
import com.lcn.test.dao.test.entity.TestBExample;
import com.lcn.test.dao.test.mapper.TestBMapper;
import com.lcn.test.dao.test.mapper.TestExtMapper;
import com.lcn.test.dto.base.Request;
import com.lcn.test.dto.base.Result;
import com.lcn.test.lcn.biz.IServiceBBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * [类描述]
 *
 * @author caican
 * @date 17/7/20
 * @title [confluence页面的title]
 */
@Service("serviceBImpl")
@com.alibaba.dubbo.config.annotation.Service
public class ServiceBImpl implements ServiceB {

    @Autowired
    IServiceBBiz serviceBBiz;

    @Autowired
    TestBMapper testBMapper;

    @Autowired
    TestExtMapper testExtMapper;


    @Override
    @TxTransaction
    public Result<String> b(Request<String> request) {
        // do my own job
        try {
            int id = serviceBBiz.doBussiness(request.getData());
            return Result.<String>create().success("" +id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("B 插入数据失败", e);
        }

    }

    @Override
    public Result<Integer> getAmountById(Request<Integer> request) {
        TestB testB = testBMapper.selectByPrimaryKey(request.getData());
        if (testB == null) {
            return Result.<Integer>create().fail("","nodata");
        }
        return Result.<Integer>create().success(testB.getAmount());
    }

    @Override
    @TxTransaction
    public Result<Void> test1(Request<Void> request) {
        TestB testB = testBMapper.selectByPrimaryKey(1);
        if (testB == null) {
            return Result.<Void>create().fail("","nodata");
        }
        return Result.<Void>create().success();
    }

    @Override
    @TxTransaction
    public Result<String> timeout(Request<Integer> request) {
        try {
            serviceBBiz.doBussiness(System.currentTimeMillis() + "-" + "timeout");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("B 插入数据失败", e);
        }

        try {
            Thread.sleep(request.getData() * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Result.<String>create().success("success");
    }

    @TxTransaction
    @Override public Result<String> exceptionInner(Request<String> request) {
        try {
            serviceBBiz.doBussinessException(System.currentTimeMillis() + "-" + request.getData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("B 插入数据失败", e);
        }


        return Result.<String>create().success("success");
    }

    @TxTransaction
    @Override public Result<String> exceptionOutter(Request<String> request) {
        try {
            serviceBBiz.doBussiness(System.currentTimeMillis() + "-" + request.getData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("B 插入数据失败", e);
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("outer");

    }

    @Override
    @TxTransaction
    public Result<String> updt(Request<Integer> request) {
        try {
            serviceBBiz.autoAdd(request.getData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("B 自加失败", e);
        }

        return Result.<String>create().success("success");
    }

    @Override
    @TxTransaction(readOnly = true)
    public Result<Integer> count(Request<Void> request) {
        testBMapper.selectByPrimaryKey(1);
        return Result.<Integer>create().data(testBMapper.countByExample(new TestBExample()));
    }

    @Override
    public Result<Void> reset(Request<Void> request) {
        testExtMapper.truncateTestB();
        return Result.<Void>create().success();
    }

}
