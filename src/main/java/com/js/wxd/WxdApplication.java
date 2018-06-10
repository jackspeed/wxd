package com.js.wxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 支持调度--定时任务
 */
@EnableScheduling
/**
 * 开启支持事务
 *
 * @author adming
 */
@EnableTransactionManagement
@SpringBootApplication
public class WxdApplication {
    /**
     * 程序主入口
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WxdApplication.class, args);
    }
}
