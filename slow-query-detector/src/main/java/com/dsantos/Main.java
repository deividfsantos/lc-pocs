package com.dsantos;

import com.dsantos.domain.ExampleEntity;
import com.dsantos.hibernate.DbConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = DbConnection.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            String hql = "FROM example_table";
            Query<ExampleEntity> query = session.createQuery(hql, ExampleEntity.class);
            List<ExampleEntity> resultList = query.getResultList();

            for (ExampleEntity entity : resultList) {
                System.out.println(entity);
            }

            session.getTransaction().commit();
        }
    }
}