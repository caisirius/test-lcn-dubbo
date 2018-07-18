package com.lcn.test.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [类描述]
 *
 * @author caican
 *         2018/7/18
 */
public class ServiceAspect {

    private static Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

    public Object aroundService(ProceedingJoinPoint joinPoint) {
        String clazzName = joinPoint.getTarget().getClass().getName();
        String mName = joinPoint.getSignature().getName();
        Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
        Object[] args = joinPoint.getArgs();

        //执行方法
        long start1 = System.currentTimeMillis();
        try {
            //请求日志
            printRequestLog(clazzName, mName, args);

            //执行方法
            long start = System.currentTimeMillis();
            Object returnObj = joinPoint.proceed();

            printResultLog(clazzName, mName, returnObj, System.currentTimeMillis() - start);
            return returnObj;

        } catch (Throwable e) {
            logger.error("执行方法错误-" + mName, e);
            printResultLog(clazzName, mName, null, System.currentTimeMillis() - start1);
            return null;
        }
    }


    public void printRequestLog(String clazz, String mName, Object[] args) {
        if (args != null) {
            if (logger.isInfoEnabled()) {
                String request = JSON.toJSONString(args);
                if (request.length() > 1024) {
                    request = request.substring(0, 1023);
                }
                logger.info(clazz + "  " + mName + " request is " + request);
            }

        } else {
            logger.info(clazz + "  " + mName + " no request.");
        }
    }

    //有参并有返回值的方法
    public void printResultLog(String clazz, String mName, Object returnObj, long ms) {

        if (logger.isInfoEnabled()) {
            String result = JSON.toJSONString(returnObj);
            if (result.length() > 1024) {
                result = result.substring(0, 1023);
            }
            logger.info(clazz + "  " + mName + " result is " + result + " ms: " + ms);
        }
    }
}

