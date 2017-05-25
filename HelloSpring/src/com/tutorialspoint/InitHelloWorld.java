package com.tutorialspoint;

import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by wang on 2017/4/7.
 */
public class InitHelloWorld implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean,String beanName){
        System.out.println("BeforeInitial:"+beanName);
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean,String beanName){
        System.out.println("AfterInitial:"+beanName);
        return bean;
    }
}
