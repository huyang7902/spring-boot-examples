package com.hy.springboot.aop.proxyFactoryBean;

import org.springframework.aop.AfterAdvice;
import org.springframework.stereotype.Component;

/**
 * 增强类，返回通知
 * @Author yang.hu
 * @Date 2020/1/29 13:59
 */
@Component("logAfterAdvice")
public class LogAfterAdvice implements AfterAdvice {

}
