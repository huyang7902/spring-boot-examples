package com.hy.spring.boot.dubbo.demo.provider.bootstrap;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

/**
 * @Author yang.hu
 * @Date 2019/12/20 11:07
 */
@EnableAutoConfiguration
public class DubboRegistryZooKeeperProviderBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboRegistryZooKeeperProviderBootstrap.class)
                .listeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
                    Environment environment = event.getEnvironment();
                    int port = environment.getProperty("embedded.zookeeper.port", int.class);
                    new EmbeddedZooKeeper(port, false).start();
                })
                .run(args);
    }
}