package com.lcn.test.lcn;

import com.lcn.test.dto.base.Request;
import com.lcn.test.dto.base.Result;

import java.math.BigDecimal;

/**
 * @author caisirius
 */
public interface ServiceB {

	/** 模拟插入一条数据
	 * @param request name
	 * @return nothing
	 */
	Result<String> b(Request<String> request);

	/** 根据id查 Amount字段
	 * @param request name
	 * @return nothing
	 */
	Result<Integer> getAmountById(Request<Integer> request);
	/**
	 * 功能:模拟超时
	 * @return
	 * @param request
	 */
	Result<String> timeout(Request<Integer> request);

	/**
	 * 功能:模拟子事务内部异常
	 * @return
	 * @param request
	 */
	Result<String> exceptionInner(Request<String> request);

	/**
	 * 功能:模拟子事务外部异常
	 * @return
	 * @param request
	 */
	Result<String> exceptionOutter(Request<String> request);


	/** 模拟按id 取 amount 自加1
	 * @param request id
	 * @return nothing
	 */
	Result<String> updt(Request<Integer> request);

	/**
	 * @param request
	 * @return
	 */
	Result<Integer> count(Request<Void> request);

	/**
	 * @param request
	 * @return
	 */
	Result<Void> reset(Request<Void> request);
}
