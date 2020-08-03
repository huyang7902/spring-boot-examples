package com.hy.springboot.aop.common.service;

/**
 * @Author yang.hu
 * @Date 2020/1/29 14:04
 */
public class HelloServiceImpl implements  HelloService {
    @Override
    public String hello() {
        System.out.println("this is hello method");
        return "this is hello method";
    }
}
