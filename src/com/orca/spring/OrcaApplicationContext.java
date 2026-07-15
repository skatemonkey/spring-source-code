package com.orca.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class OrcaApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singletonObject = new ConcurrentHashMap<>();

    public OrcaApplicationContext(Class configClass) {
        this.configClass = configClass;

        // Scanning - beandefinition - beanDefinitionMap
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);

            String path = componentScanAnnotation.value(); // com.orca.service
            path = path.replace(".", "/"); // com/orca/service

            ClassLoader classLoader = OrcaApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path); // file:/V:/.../com/orca/service

            File file = new File(resource.getFile()); // V:\...\com\orca\service

            if (file.isDirectory()) {
                File[] files = file.listFiles();

                for (File f : files) {
                    String fileName = f.getAbsolutePath(); // V:\...\com\orca\service\UserService.class

                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class")); // com\orca\service\UserService
                        className = className.replace("\\", "."); // com.orca.service.UserService

                        try {
                            Class<?> clazz = classLoader.loadClass(className);
                            if (clazz.isAnnotationPresent(Component.class)) {

                                Component component = clazz.getAnnotation(Component.class);
                                String beanName = component.value();

                                if (beanName.equals("")) {
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                                }

                                // BeanDefinition
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(clazz);

                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                } else {
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);

                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            // 实例话单例Bean
            for (String beanName : beanDefinitionMap.keySet()) {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition.getScope().equals("singleton")) {
                    Object bean = createBean(beanName, beanDefinition);
                    singletonObject.put(beanName, bean);
                }

            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            Object instance = clazz.getConstructor().newInstance();

            // Dependency Injection 依赖注入
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(Autowired.class)) {
                    f.setAccessible(true);
                    f.set(instance, getBean(f.getName()));
                }
            }

            // Aware 回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // 初始化 AOP

            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getBean(String beanName) {

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            if (scope.equals("singleton")) {
                Object bean = singletonObject.get(beanName);
                if (bean == null) {
                    bean = createBean(beanName, beanDefinition);
                    singletonObject.put(beanName, bean);
                }
                return bean;
            } else {
                // prototype 多例
                return createBean(beanName, beanDefinition);
            }
        }
    }
}
