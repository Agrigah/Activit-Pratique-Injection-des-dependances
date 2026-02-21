package net.agrigah.framework.context;

import net.agrigah.framework.Autowired;
import net.agrigah.framework.Component;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContextAnnotation {

    private Map<Class<?>, Object> beans = new HashMap<>();

    public ApplicationContextAnnotation(String basePackage) {
        try {
            scanAndInstantiate(basePackage);
            injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load annotation context: " + e.getMessage(), e);
        }
    }

    private void scanAndInstantiate(String basePackage) throws Exception {
        String path = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource == null) throw new RuntimeException("Package not found: " + basePackage);
        scanDirectory(new File(resource.toURI()), basePackage);
    }

    private void scanDirectory(File directory, String packageName) throws Exception {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);

                if (clazz.isAnnotationPresent(Component.class)) {
                    // Try constructor injection first
                    Object instance = tryConstructorInjection(clazz);
                    if (instance == null) {
                        instance = clazz.getDeclaredConstructor().newInstance();
                    }
                    beans.put(clazz, instance);
                    for (Class<?> iface : clazz.getInterfaces()) {
                        beans.put(iface, instance);
                    }
                }
            }
        }
    }

    private Object tryConstructorInjection(Class<?> clazz) throws Exception {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                Class<?>[] paramTypes = constructor.getParameterTypes();
                Object[] args = new Object[paramTypes.length];
                for (int i = 0; i < paramTypes.length; i++) {
                    args[i] = beans.get(paramTypes[i]);
                }
                return constructor.newInstance(args);
            }
        }
        return null;
    }

    private void injectDependencies() throws Exception {
        for (Object bean : beans.values()) {

            // Field injection
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    Object dependency = beans.get(field.getType());
                    if (dependency != null) field.set(bean, dependency);
                }
            }

            // Setter injection
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Autowired.class) && method.getName().startsWith("set")) {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    if (paramTypes.length == 1) {
                        Object dependency = beans.get(paramTypes[0]);
                        if (dependency != null) method.invoke(bean, dependency);
                    }
                }
            }

            // Fix constructor-injected field if null during scan
            for (Constructor<?> constructor : bean.getClass().getConstructors()) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    for (Class<?> paramType : constructor.getParameterTypes()) {
                        Object dependency = beans.get(paramType);
                        for (Field field : bean.getClass().getDeclaredFields()) {
                            field.setAccessible(true);
                            if (field.getType().equals(paramType) && field.get(bean) == null) {
                                field.set(bean, dependency);
                            }
                        }
                    }
                }
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}