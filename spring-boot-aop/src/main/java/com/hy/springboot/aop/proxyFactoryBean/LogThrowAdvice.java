package com.hy.springboot.aop.proxyFactoryBean;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

/**
 * 增强类，前置通知
 * @Author yang.hu
 * @Date 2020/1/29 13:59
 */
@Component("logThrowAdvice")
public class LogThrowAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception ex){
        System.out.println(ex.getCause());
    }

}
