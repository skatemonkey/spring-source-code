package com.orca.service;

import com.orca.spring.BeanPostProcessor;
import com.orca.spring.Component;

@Component
public class OrcaBeanPostProcessor implements BeanPostProcessor {
    @Override
    public void postProcessBeforeInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")) {
            System.out.println("1111");
        }
    }

    @Override
    public void postProcessAfterInitialization(String beanName, Object bean) {

    }
}
