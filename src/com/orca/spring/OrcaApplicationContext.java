package com.orca.spring;

public class OrcaApplicationContext {
    private Class configClass;

    public OrcaApplicationContext(Class configClass) {
        this.configClass = configClass;
    }

    public Object getBean(String beanName) {
        return null;
    }
}
