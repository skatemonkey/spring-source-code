package com.orca.service;

import com.orca.spring.*;

@Component()
@Scope("prototype")
public class UserService implements UserInterface{

    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println(orderService);
    }
}
