package com.hy.springboot.aop.proxyFactoryBean;

import com.hy.springboot.aop.common.service.HelloService;
import com.hy.springboot.aop.common.service.HelloServiceImpl;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yang.hu
 * @Date 2020/1/29 14:02
 */
@Configuration
public class RootConfig {

    // 注册一个代理Bean
    @Bean("myProxyFactoryBean")
    public ProxyFactoryBean myProxyFactoryBean(HelloService helloService) {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();

        //代理的目标对象  效果同setTargetSource(@Nullable TargetSource targetSource)
        // 此处需要注意的是，这里如果直接new，那么该类就不能使用@Autowired之类的注入  因此建议此处还是从容器中去拿
        // 因此可以写在入参上（这也是标准的写法~~）
        //factoryBean.setTarget(new HelloServiceImpl());
        factoryBean.setTarget(helloService);

        // setInterfaces和setProxyInterfaces的效果是相同的。设置需要被代理的接口，
        // 若没有实现接口，那就会采用cglib去代理
        // 需要说明的一点是：这里不设置也能正常被代理（若你没指定，Spring内部会去帮你找到所有的接口，然后全部代理上~~~~~~~~~~~~）  设置的好处是只代理指定的接口
        factoryBean.setInterfaces(HelloService.class);
        //factoryBean.setProxyInterfaces(new Class[]{HelloService.class});

        // 需要植入进目标对象的bean列表 此处需要注意：这些bean必须实现类 org.aopalliance.intercept.MethodInterceptor或 org.springframework.aop.Advisor的bean ,配置中的顺序对应调用的顺序
        factoryBean.setInterceptorNames(new String[]{"logMethodBeforeAdvice","logAfterReturningAdvice","logThrowAdvice"});
//        factoryBean.setInterceptorNames("logAfterAdvice");

        // 若设置为true，强制使用cglib，默认是false的
        //factoryBean.setProxyTargetClass(true);

        return factoryBean;
    }

    @Bean
    public HelloService helloService(){
        return new HelloServiceImpl();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(RootConfig.class);
        applicationContext.register(LogMethodBeforeAdvice.class);
        applicationContext.register(LogAfterReturningAdvice.class);
        applicationContext.register(LogThrowAdvice.class);
//        applicationContext.register(LogAfterAdvice.class);
        applicationContext.refresh();

        //expected single matching bean but found 2: helloServiceImpl,proxyFactoryBean
        // 如果通过类型获取，会找到两个Bean：一个我们自己的实现类、一个ProxyFactoryBean所生产的代理类 而此处我们显然是希望要生成的代理类的  因此我们只能通过名称来(或者加上@Primary)
        //HelloService bean = applicationContext.getBean(HelloService.class);
        HelloService bean = (HelloService) applicationContext.getBean("myProxyFactoryBean");
        bean.hello();

        System.out.println(bean); //com.fsx.service.HelloServiceImpl@4e50c791
        System.out.println(bean.getClass()); //class com.sun.proxy.$Proxy22 用得JDK的动态代理
        // 顺便说一句：这样也是没错得。因为Spring AOP代理出来的每个代理对象，都默认实现了这个接口（它是个标记接口）
        // 它这个也就类似于所有的JDK代理出来的，都是Proxy的子类是一样的思想~
        SpringProxy springProxy = (SpringProxy) bean;

        // 结果：其中"this is LogMethodBeforeAdvice"打印次数每次都不一样
        // debug的时候明显会多一些，但是却不进入断点
        /*
        this is LogMethodBeforeAdvice
        this is hello method
        this is LogMethodBeforeAdvice
        com.hy.springboot.aop.common.service.HelloServiceImpl@75329a49
        class com.sun.proxy.$Proxy10
         */
    }


}
