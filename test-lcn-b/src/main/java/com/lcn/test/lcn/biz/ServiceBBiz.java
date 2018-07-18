package com.lcn.test.lcn.biz;

import com.lcn.test.dao.test.entity.TestB;
import com.lcn.test.dao.test.mapper.TestBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * [类描述]
 *
 * @author caican
 * @date 17/7/20
 * @title [confluence页面的title]
 */
@Component
public class ServiceBBiz implements IServiceBBiz {

    @Autowired
    TestBMapper testBMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doBussiness(String key) {
        TestB record = new TestB();
        record.setName("这是系统 B 插入的:" + key + "-" + System.currentTimeMillis());
        record.setAmount(10);
        record.setCreateTime(new Date());

        testBMapper.insertSelective(record);

        return record.getId();

    }

    @Transactional(rollbackFor = Exception.class)
    @Override public int doBussinessException(String key) {
        TestB record = new TestB();
        record.setName("这是系统 B 插入的:" + key + "-" + System.currentTimeMillis());
        record.setAmount(10);
        record.setCreateTime(new Date());

        testBMapper.insertSelective(record);

        throw new RuntimeException("exception");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoAdd(int id) {
        TestB testB = testBMapper.selectByPrimaryKey(id);
        if (testB == null) {
            throw new RuntimeException("数据没找到 id=" + id);
        }
        TestB updt = new TestB();
        updt.setId(testB.getId());
        updt.setAmount(testB.getAmount() + 1);
        int ret = testBMapper.updateByPrimaryKeySelective(updt);
        if (ret == 0) {
            throw new RuntimeException("更新数据影响行数为 0 ");
        }
    }
}
