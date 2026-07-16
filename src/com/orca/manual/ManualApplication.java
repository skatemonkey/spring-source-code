package com.orca.manual;

public class ManualApplication {

    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.createUserOrder();
    }
}
