package com.fd.audit;

import com.fd.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Component
@Slf4j

/**
 * before persist is called for a new entity – @PrePersist
 * after persist is called for a new entity – @PostPersist
 * before an entity is removed – @PreRemove
 * after an entity has been deleted – @PostRemove
 * before the update operation – @PreUpdate
 * after an entity is updated – @PostUpdate
 * after an entity has been loaded – @PostLoad
 */

public class AutoFillAuditingEntityListener {
    @PrePersist
    public void prePersist(Object object) throws IllegalArgumentException, IllegalAccessException {
        // 如果填充字段被分装在一个父类中： Class<?> aClass = object.getClass().getSuperclass();
        Class<?> aClass = object.getClass();
        try {
            // 填充创建时间，更新时间
            addCreateTime(object, aClass, "createTime");
            addUpdateTime(object, aClass, "updateTime");
            addCreateUser(object, aClass, "createUser");
            addUpdateUser(object, aClass, "updateUser");
        } catch (NoSuchFieldException e) {
            log.error("反射获取属性异常：", e);
        }
    }

    @PreUpdate
    public void preUpdate(Object object) throws IllegalArgumentException, IllegalAccessException {
        Class<?> aClass = object.getClass();
        try {
            // 填充更新时间
            addUpdateTime(object, aClass, "updateTime");
            addUpdateUser(object, aClass, "updateUser");
        } catch (NoSuchFieldException e) {
            log.error("反射获取属性异常：", e);
        }
    }

    protected void addCreateTime(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Field time = aClass.getDeclaredField(propertyName);
        time.setAccessible(true);
        // 获取time值
        Object createdTimeValue = time.get(object);
        if(createdTimeValue == null) {
            // 使用当前时间进行填充
            time.set(object, LocalDateTime.now());
        }
    }

    protected void addUpdateTime(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Field time = aClass.getDeclaredField(propertyName);
        time.setAccessible(true);
        // 使用当前时间进行填充
        time.set(object, LocalDateTime.now());
    }

    protected void addCreateUser(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Field userId = aClass.getDeclaredField(propertyName);
        userId.setAccessible(true);
        // 获取userId值
        Object userIdValue = userId.get(object);
        if (userIdValue == null) {
            // 在此处使用当前用户id或默认用户id
            Long id = BaseContext.getCurrentId();
            userId.set(object, id);
        }
    }

    protected void addUpdateUser(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Field userId = aClass.getDeclaredField(propertyName);
        userId.setAccessible(true);
        // 在此处使用当前用户id或默认用户id
        Long id = BaseContext.getCurrentId();
        userId.set(object, id);
    }

}
