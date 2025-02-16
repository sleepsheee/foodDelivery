package com.fd.config;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.fd.audit.AutoFillAuditingEntityListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class HibernateListenerConfiguration {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.MERGE).clearListeners();
        registry.getEventListenerGroup(EventType.MERGE).prependListener(AutoFillAuditingEntityListener.INSTANCE);
    }
}
