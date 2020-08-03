package com.hy.springboot.aop.proxyFactoryBean;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 增强类，前置通知
 * @Author yang.hu
 * @Date 2020/1/29 13:59
 */
@Component("logMethodBeforeAdvice")
public class LogMethodBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("this is LogMethodBeforeAdvice");
    }
}
