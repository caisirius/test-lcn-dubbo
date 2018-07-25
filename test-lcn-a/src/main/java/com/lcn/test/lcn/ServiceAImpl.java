package com.lcn.test.lcn;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.codingapi.tx.annotation.TxTransactionMode;
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


    /////////// TXC 的用例 ////////////
    /**
     * TXC 有数据库
     */
    @Override
    @TxTransaction(isStart=true, mode = TxTransactionMode.TX_MODE_TXC)
    public Result<String> insertTxc(Request<String> request) {
        // 调一个 子事务 readonly 的服务
        serviceB.count(Request.<Void>create());
        // 调一个 子事务 既没配 readonly 也没配 @Transaction 的服务
        serviceB.test1(Request.<Void>create());
        return insertTest(request, true);
    }

    @Override
    @TxTransaction(isStart=true, mode = TxTransactionMode.TX_MODE_TXC)
    public Result<String> insertUpdtTxc(Request<Integer> request) {
        return insertUpdtTest(request);
    }

    @TxTransaction(isStart=true, mode = TxTransactionMode.TX_MODE_TXC)
    @Override
    public BaseResult insertTxcException2(Request<String> request) {
        return inserttTxcException2(request);
    }

    @TxTransaction(isStart=true, mode = TxTransactionMode.TX_MODE_TXC)
    @Override public BaseResult insertTxcException3(Request<String> request) {
        // do my own job
        String key = System.currentTimeMillis() + "-" + request.getData();

        serviceABiz.doBussiness(key);

        if (request.getData().equals("inner")) {
            callBExctptionInner(key);
        }

        if (request.getData().equals("outter")) {
            callBExctptionOutter(key);
        }

        return Result.<String> create().success("success");
    }

    @TxTransaction(isStart=true, mode = TxTransactionMode.TX_MODE_TXC)
    @Override public BaseResult insertTxcTimeoutMain(Request<String> request) {
        return insertTimeoutMain(request);
    }

    @TxTransaction(isStart=true, mode = TxTransactionMode.TX_MODE_TXC)
    @Override
    public BaseResult insertTxcTimeoutSub(Request<String> request) {
        return insertTimeOutSub(request);
    }

    @TxTransaction(isStart=true, mode = TxTransactionMode.TX_MODE_TXC)
    @Override public BaseResult insertTxcTimeoutSubs(Request<String> request) {
        return insertTimeOutSubs(request);
    }


    //////////////////////

    private BaseResult insertTimeoutMain(Request<String> request) {
        // do my own job
        String key = System.currentTimeMillis() + "-" + request.getData();

        serviceABiz.doBussiness(key);

        // do call serivice B
        callB(key);

        try {
            Thread.sleep(11000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Result.<String> create().success("success");
    }

    private BaseResult insertTimeOutSub(Request<String> request) {
        // do my own job
        String key = System.currentTimeMillis() + "-" + request.getData();

        serviceABiz.doBussiness(key);

        // do call serivice B
        callBTimeOut(12);

        return Result.<String> create().success("success");
    }

    private BaseResult insertTimeOutSubs(Request<String> request) {
        // do my own job
        String key = System.currentTimeMillis() + "-" + request.getData();

        // do call serivice B
        callBTimeOut(4);

        serviceABiz.doBussiness(key);

        callBTimeOut(3);

        return Result.<String> create().success("success");
    }

    private void callBTimeOut(int timeout) {
        logger.info("start to call b");
        Result<String> bRet = serviceB.timeout(Request.<Integer> create().data(timeout));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B失败");
        }
        logger.info("end call b");
    }

    private void callBExctptionOutter(String key) {
        logger.info("start to call b");
        Result<String> bRet = serviceB.exceptionOutter(Request.<String> create().data(key));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B失败");
        }
        logger.info("end call b");
    }

    private void callBExctptionInner(String key) {
        logger.info("start to call b");
        Result<String> bRet = serviceB.exceptionInner(Request.<String> create().data(key));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B失败");
        }
        logger.info("end call b");
    }

    private BaseResult inserttTxcException2(Request<String> request) {
        // do my own job
        String key = System.currentTimeMillis() + "-" + request.getData();

        serviceABiz.doBussinessException(key);

        return Result.<String> create().success("success");
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
        return insertTest(request, false);
    }

    /**
     * @param request req
     * @param bizWithAnnotation biz 方法是否带@Transaction 注解
     */
    private Result<String> insertTest(Request<String> request, boolean bizWithAnnotation) {
        // do my own job
        String key = System.currentTimeMillis() + "-" + request.getData();

        if (bizWithAnnotation) {
            serviceABiz.doBussinessWithTran(key);
        } else {
            serviceABiz.doBussiness(key);
        }

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
