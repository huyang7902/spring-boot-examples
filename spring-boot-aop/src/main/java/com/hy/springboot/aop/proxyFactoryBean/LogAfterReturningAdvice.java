package com.hy.springboot.aop.proxyFactoryBean;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 增强类，前置通知
 * @Author yang.hu
 * @Date 2020/1/29 13:59
 */
@Component("logAfterReturningAdvice")
public class LogAfterReturningAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("this is LogAfterReturningAdvice");
    }
}
