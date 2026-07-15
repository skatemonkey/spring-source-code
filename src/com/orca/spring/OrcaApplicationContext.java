package com.orca.spring;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class OrcaApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public OrcaApplicationContext(Class configClass) {
        this.configClass = configClass;

        // Scanning
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
        }
    }

    public Object getBean(String beanName) {
        return null;
    }
}
