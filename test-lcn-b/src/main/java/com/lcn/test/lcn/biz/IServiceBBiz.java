package com.lcn.test.lcn.biz;

/**
 * [类描述]
 *
 * @author caican
 * @date 17/12/6
 * @title [confluence页面的title]
 */
public interface IServiceBBiz {

    int doBussiness(String key);

    int doBussinessException(String key);

    void autoAdd(int id);
}
