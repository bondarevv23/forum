package com.github.bondarevv23.forum.util;

import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;

@UtilityClass
public class HibernateUtil {
    public static Class<?> getEffectiveClass(Object obj) {
        if (obj instanceof HibernateProxy hibernateProxy) {
            return hibernateProxy.getHibernateLazyInitializer().getPersistentClass();
        }
        return obj.getClass();
    }
}
