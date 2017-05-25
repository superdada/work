package com.tutorialspoint;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wang on 2017/4/6.
 */
public class MainApp {
    public static void main(String[] args){
        AbstractApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");
        context.registerShutdownHook();
        HelloWorld obj=(HelloWorld)context.getBean("helloWorld");
        obj.getMessage();
    }
}
