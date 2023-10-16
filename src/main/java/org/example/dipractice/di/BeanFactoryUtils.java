package org.example.dipractice.di;

import org.example.dipractice.annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeanFactoryUtils {
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        // Inject 애노테이션이 붙은 생성자만 가지고 온다는 의미
        Set<Constructor> injectedConstructors = ReflectionUtils.getAllConstructors(clazz, ReflectionUtils.withAnnotation(Inject.class));
        if(injectedConstructors.isEmpty()) {
            return null;
        }

        return injectedConstructors.iterator().next();
    }
}
