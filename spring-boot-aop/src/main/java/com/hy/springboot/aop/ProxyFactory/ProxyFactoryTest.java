package com.hy.springboot.aop.ProxyFactory;

import com.hy.springboot.aop.common.service.HelloService;
import com.hy.springboot.aop.common.service.HelloServiceImpl;
import com.hy.springboot.aop.proxyFactoryBean.LogMethodBeforeAdvice;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 它和Spring容器没啥关系，可议直接创建代理来使用
 *
 * @Author yang.hu
 * @Date 2020/1/29 16:03
 */
public class ProxyFactoryTest {

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new HelloServiceImpl());

        // 添加两个Advise，一个匿名内部类表示
        proxyFactory.addAdvice((AfterReturningAdvice) (returnValue, method, args1, target) ->
                System.out.println("AfterReturningAdvice method=" + method.getName()));
        proxyFactory.addAdvice(new LogMethodBeforeAdvice());

        HelloService proxy = (HelloService) proxyFactory.getProxy();
        proxy.hello();
    }

}
