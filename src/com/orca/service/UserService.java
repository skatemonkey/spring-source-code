package com.orca.service;

import com.orca.spring.*;

@Component()
@Scope("prototype")
public class UserService implements BeanNameAware, InitializingBean {

    @Autowired
    private OrderService orderService;

    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("初始化");
    }
    public void test() {
        System.out.println(orderService);
    }
}
