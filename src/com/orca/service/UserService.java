package com.orca.service;

import com.orca.spring.Autowired;
import com.orca.spring.Component;
import com.orca.spring.Scope;

@Component()
@Scope("prototype")
public class UserService {

    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println(orderService);
    }
}
