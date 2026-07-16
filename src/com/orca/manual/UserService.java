package com.orca.manual;

public class UserService {

    private final OrderService orderService = new OrderService();

    public void createUserOrder() {
        orderService.createOrder();
    }
}
