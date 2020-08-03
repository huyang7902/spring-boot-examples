package com.hy.spring.boot.dubbo.demo.provider.service;

import com.hy.spring.boot.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author yang.hu
 * @Date 2019/12/20 10:58
 */
@Service(version = "${demo.service.version}")
public class DefaultDemoService implements DemoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        String format = String.format("[%s] : Hello, %s", serviceName, name);
        logger.info(format);
        return format;
    }
}
