package com.dsantos.hibernate;

import com.dsantos.domain.ExampleEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class DbConnection {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.JAKARTA_JDBC_DRIVER, "oracle.jdbc.OracleDriver");
            settings.put(Environment.JAKARTA_JDBC_URL, "jdbc:oracle:thin:@//localhost:1521/FREE");
            settings.put(Environment.JAKARTA_JDBC_USER, "SYS as SYSDBA");
            settings.put(Environment.JAKARTA_JDBC_PASSWORD, "Test123");

            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(ExampleEntity.class);
            configuration.setInterceptor(new SlowQueryInterceptor());

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}