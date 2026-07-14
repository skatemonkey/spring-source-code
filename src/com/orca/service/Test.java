package com.orca.service;

import com.orca.spring.OrcaApplicationContext;

public class Test {
    static void main() {
        OrcaApplicationContext applicationContext = new OrcaApplicationContext(AppConfig.class);

        UserService userService = (UserService) applicationContext.getBean("userService");
    }
}
