package com.lcn.test.lcn.biz;

import com.lcn.test.dao.test.entity.TestA;
import com.lcn.test.dao.test.mapper.TestAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * [类描述]
 *
 * @author caican
 * @date 17/7/20
 */
@Component
public class ServiceABiz {

    @Autowired
    TestAMapper testAMapper;


    @Transactional(rollbackFor = Exception.class)
    public void doBussinessWithTran(String key) {
        TestA record = new TestA();
        record.setName("这是系统 A 插入的:" + key);
        record.setCreateTime(new Date());

        testAMapper.insertSelective(record);

    }

    public void doBussiness(String key) {
        TestA record = new TestA();
        record.setName("这是系统 A 插入的:" + key);
        record.setCreateTime(new Date());

        testAMapper.insertSelective(record);

    }

    @Transactional(rollbackFor = Exception.class)
    public void doBussinessWithException(String key) {
        TestA record = new TestA();
        record.setName("这是系统 A 插入的:" + key);
        record.setCreateTime(new Date());

        testAMapper.insertSelective(record);

        throw new RuntimeException("故意的异常");
    }
}
