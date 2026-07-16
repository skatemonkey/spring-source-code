package com.orca.service;

import com.orca.spring.BeanPostProcessor;
import com.orca.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class OrcaBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")) {
            System.out.println("1111");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {

        if (beanName.equals("userService")) {
            Object proxyInstance = Proxy.newProxyInstance(OrcaBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("切面逻辑");
                    return method.invoke(bean, args);
                }

            });
            return proxyInstance;
        }
        return bean;
    }
}
