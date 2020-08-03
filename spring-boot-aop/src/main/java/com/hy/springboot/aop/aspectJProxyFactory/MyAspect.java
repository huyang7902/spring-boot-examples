package com.hy.springboot.aop.aspectJProxyFactory;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
class MyAspect {

    /**
     * 切入点表达式
     */
    @Pointcut("execution(* *.hello(..))")
    private void beforeAdd() {
    }

    /**
     * 前置通知
     */
    @Before("beforeAdd()")
    public void before1() {
        System.out.println("-----------before-----------");
    }

}