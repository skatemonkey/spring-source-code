package com.orca.spring;

import java.io.File;
import java.net.URL;

public class OrcaApplicationContext {
    private Class configClass;

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
                            Class<?> clazz = classLoader.loadClass("");
                            if (clazz.isAnnotationPresent(Component.class)) {
                                // Bean

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
