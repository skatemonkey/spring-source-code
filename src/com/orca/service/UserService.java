package com.orca.service;

import com.orca.spring.Autowired;
import com.orca.spring.BeanNameAware;
import com.orca.spring.Component;
import com.orca.spring.Scope;

@Component()
@Scope("prototype")
public class UserService implements BeanNameAware {

    @Autowired
    private OrderService orderService;

    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void test() {
        System.out.println(orderService);
    }
}
