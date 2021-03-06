package com.lcn.test.lcn;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.codingapi.tx.annotation.TxTransactionMode;
import com.lcn.test.dto.base.BaseResult;
import com.lcn.test.dto.base.Request;
import com.lcn.test.dto.base.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * [类描述]
 *
 * @author caican
 * @date 17/7/20
 * @title [confluence页面的title]
 */
@Service
@org.springframework.stereotype.Service // 为了单元测试
public class ServiceANoDBImpl implements ServiceA {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    ServiceB serviceB;

    private Result<String> insertb(Request<String> request) {
        String key = System.currentTimeMillis()+"-"+request.getData();

        // do call serivice B
        logger.info("start to call b");
        Result<String> bRet = serviceB.b(Request.<String>create().data(key));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B失败");
        }

        bRet = serviceB.b(Request.<String>create().data(key));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B失败");
        }
        logger.info("end call b");

        if ("ERROR".equalsIgnoreCase(request.getData())) {
            int i = 100/0; // 故意抛异常
        }

        return Result.<String>create().success("success");
    }


    @Override
    @TxTransaction(isStart=true)
    public Result<String> insertLcn(Request<String> request) {
        Result<String> result = Result.create();
        insertb(request);
        return result.success("yes");
    }

    @Override
    @TxTransaction(isStart=true)
    public Result<String> insertUpdtLcn(Request<Integer> request) {
        logger.info("start to call b");
        Result<String> bRet = serviceB.b(Request.<String>create().data("即将更新 " +request.getData()));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B失败");
        }
        int id = Integer.parseInt(bRet.getData());
        bRet = serviceB.updt(Request.<Integer>create().data(request.getData()));
        if (!bRet.isSuccess()) {
            throw new RuntimeException("调B updt 失败");
        }
        return Result.<String>create().success("" + id);
    }

    @Override
    public Result<String> insertUpdtTxc(Request<Integer> request) {
        return null;
    }

    @Override
    public BaseResult insertTxcTimeoutSub(Request<String> timeout) {
        return null;
    }

    @Override
    public BaseResult insertTxcTimeoutMain(Request<String> timeout) {
        return null;
    }

    @Override
    public BaseResult insertTxcTimeoutSubs(Request<String> timeout) {
        return null;
    }

    @Override
    public BaseResult insertTxcException2(Request<String> error) {
        return null;
    }

    @Override
    public BaseResult insertTxcException3(Request<String> excetion3) {
        return null;
    }

    @Override
    @TxTransaction(isStart = true, mode = TxTransactionMode.TX_MODE_TXC)
    public Result<String> insertTxc(Request<String> request) {
        Result<String> result = Result.create();
        insertb(request);
        return result.success("yes");
    }

}
