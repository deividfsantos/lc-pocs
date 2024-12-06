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

//    static {
//        try {
//            Configuration configuration = new Configuration();
//
//            // Configurações do Hibernate
//            Properties settings = new Properties();
//            settings.put(Environment.DRIVER, "oracle.jdbc.OracleDriver");
//            settings.put(Environment.URL, "");
//            settings.put(Environment.USER, "");
//            settings.put(Environment.PASS, "");
//            settings.put(Environment.DIALECT, "org.hibernate.dialect.Oracle12cDialect");
//
//            // Configurações do C3P0
//            settings.put(Environment.C3P0_MIN_SIZE, 5);
//            settings.put(Environment.C3P0_MAX_SIZE, 20);
//            settings.put(Environment.C3P0_TIMEOUT, 300);
//            settings.put(Environment.C3P0_MAX_STATEMENTS, 50);
//            settings.put(Environment.C3P0_IDLE_TEST_PERIOD, 3000);
//
//            // Outras configurações
//            settings.put(Environment.SHOW_SQL, "true");
//            settings.put(Environment.HBM2DDL_AUTO, "update");
//            configuration.addAnnotatedClass(ExampleEntity.class); // Adiciona a classe anotada
//            configuration.setProperties(settings);
//            configuration.setInterceptor(new SlowQueryInterceptor());
//
//            sessionFactory = configuration.buildSessionFactory();
//        } catch (Throwable ex) {
//            throw new ExceptionInInitializerError(ex);
//        }
//    }

    static {
        SessionFactory tempSessionFactory = null;
        try {
            Configuration configuration = new Configuration();

            // Configurações do Hibernate
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "oracle.jdbc.OracleDriver");
            settings.put(Environment.URL, "jdbc:oracle:thin:@//localhost:1521/FREE");
            settings.put(Environment.USER, "SYS as SYSDBA");
            settings.put(Environment.PASS, "Test123");

            // Outras configurações
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(ExampleEntity.class); // Adiciona a classe anotada
            configuration.setInterceptor(new SlowQueryInterceptor());

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            tempSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        sessionFactory = tempSessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}