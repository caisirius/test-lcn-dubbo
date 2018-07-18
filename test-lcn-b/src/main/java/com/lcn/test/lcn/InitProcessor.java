package com.lcn.test.lcn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * [类描述]
 *
 * @author caican
 * 18/3/15
 */
@Service
public class InitProcessor implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(InitProcessor.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            logger.warn("spring inited");
            try {
                String name = ManagementFactory.getRuntimeMXBean().getName();
                String pid = name.split("@")[0];
                FileWriter fw = new FileWriter("serverb.pid");
                fw.write(pid);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("",e);
            }

        }
    }
}
