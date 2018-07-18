package com.lcn.test.lcn;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.lcn.test.dto.base.BaseResult;
import com.lcn.test.dto.base.Request;
import com.lcn.test.dto.base.Result;
import com.lcn.test.lcn.biz.ServiceABiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * [类描述]
 *
 * @author caican
 * @date 17/7/20
 * @title [confluence页面的title]
 */
@Service
@org.springframework.stereotype.Service // 为了单元测试
public class ServiceAImpl implements ServiceA {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    ServiceB serviceB;

    @Autowired
    ServiceABiz serviceABiz;

    @Override
    @TxTransaction(isStart=true)
    @Transactional
    public Result<String> insertLcn(Request<String> request) {
        return insertTest(request);
    }


    @Override
    @TxTransaction(isStart=true)
    @Transactional
    public Result<String> insertUpdtLcn(Request<Integer> request) {
        return insertUpdtTest(request);
    }





    private void callB(String key) {
        logger.info("start to call b");
        Result<String> bRet = serviceB.b(Request.<String> create().data(key));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B失败");
        }
        logger.info("end call b");
    }

    private void callUpdate(int key) {
        logger.info("start to call b updt");
        Result<String> bRet = serviceB.updt(Request.<Integer> create().data(key));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B失败");
        }
        logger.info("end call b updt");
    }

    private Result<String> insertTest(Request<String> request) {
        // do my own job
        String key = System.currentTimeMillis() + "-" + request.getData();

        serviceABiz.doBussiness(key);

        // do call serivice B
        callB(key);

        // call twice
        callB(key);
        if ("ERROR".equalsIgnoreCase(request.getData())) {
            int i = 100 / 0; // 故意抛异常
        }

        return Result.<String> create().success("success");
    }

    private Result<String> insertUpdtTest(Request<Integer> request) {
        // do my own job
        String key = System.currentTimeMillis() + "-" + request.getData();
        try {
            serviceABiz.doBussiness(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // do call serivice B
        callUpdate(request.getData());

        if (request.getData() == 0) {
            int i = 100 / 0; // 故意抛异常
        }

        return Result.<String> create().success("success");
    }
}
