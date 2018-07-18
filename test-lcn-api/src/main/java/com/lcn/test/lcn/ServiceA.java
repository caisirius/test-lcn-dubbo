package com.lcn.test.lcn;


import com.lcn.test.dto.base.BaseResult;
import com.lcn.test.dto.base.Request;
import com.lcn.test.dto.base.Result;

public interface ServiceA {

	Result<String> insertLcn(Request<String> request);

//	Result<String> insertTxc(Request<String> request);

	Result<String> insertUpdtLcn(Request<Integer> request);

//	Result<String> insertUpdtTxc(Request<String> request);

	//子事务超时
//	BaseResult insertTxcTimeoutSub(Request<String> timeout);
//
//	//主事务超时
//	BaseResult insertTxcTimeoutMain(Request<String> timeout);
//
//	//模拟超时，子事务加起来超时
//	BaseResult insertTxcTimeoutSubs(Request<String> timeout);
//
//	//模拟主事务异常
//	BaseResult insertTxcException2(Request<String> error);
//
//	//模拟子事务异常
//	BaseResult insertTxcException3(Request<String> excetion3);
}
