package com.lcn.test.lcn;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lcn.test.BaseSpringTest;

/**
 * @author jsy.
 * @title
 * @time 18/5/11.
 */
public class ManMade  extends BaseSpringTest {

    @Autowired
    ServiceA serviceA;

    //测试断电、断网线
    @Test
    public void testPowerOff() throws InterruptedException {
        while (true) {
//            result = serviceA.insertTxc(Request.<String>create().data("success"));
//
//            result = serviceA.insertTxc(Request.<String>create().data("error"));
            Thread.sleep(1000);
        }

    }

}
