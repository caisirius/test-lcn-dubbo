package com.lcn.test.lcn;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcn.test.dto.base.Request;
import com.lcn.test.dto.base.Result;
import org.springframework.stereotype.Service;

/**
 * [类描述]
 *
 * @author caican
 * @date 18/3/16
 */
@Service
public class DataChecker {
    @Reference
    ServiceB serviceB;

    public void reset() {
        serviceB.reset(Request.<Void>create());
    }

    public int countb() {
        return serviceB.count(Request.<Void>create()).getData();
    }

    public int getAmount(int id) {
        Result<Integer> amount = serviceB.getAmountById(Request.<Integer>create().data(id));
        if (!amount.isSuccess()) {
            throw new RuntimeException("调用 getAmountById 失败 ：" + amount.getDescription());
        }
        return amount.getData();
    }

    /**
     * 插入一条数据
     */
    public int insert() {
        Result<String> ret = serviceB.b(Request.<String>create().data("提前准备的数据"));
        if (!ret.isSuccess()) {
            throw new RuntimeException("准备数据，调用 b 失败 ：" + ret.getDescription());
        }
        return Integer.parseInt(ret.getData());
    }
}
