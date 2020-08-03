package com.hy.springboot.aop.aspectJProxyFactory;

import com.hy.springboot.aop.common.service.HelloService;
import com.hy.springboot.aop.common.service.HelloServiceImpl;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @Author yang.hu
 * @Date 2020/2/2 14:07
 */
public class AspectJProxyFactoryTest {

    public static void main(String[] args) {
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(new HelloServiceImpl());
        // 注意：此处得MyAspect类上面的@Aspect注解必不可少
        proxyFactory.addAspect(MyAspect.class);
        //proxyFactory.setProxyTargetClass(true);//是否需要使用CGLIB代理
        HelloService proxy = proxyFactory.getProxy();
        proxy.hello();

        System.out.println(proxy.getClass()); //class com.sun.proxy.$Proxy6
        /*
       结果：
        -----------before-----------
        this is hello method
        class com.sun.proxy.$Proxy5
         */
    }
}
