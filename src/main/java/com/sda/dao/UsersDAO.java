package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

public class UsersDAO {

    public void create(User user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }

    public boolean deleteByUsername(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, username);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
            return user != null;
        }
    }

    public List<User> findAll(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        String query = "SELECT u FROM User u";
        List<User> users = session.createQuery(query, User.class).list();
        session.close();
        return users;

    }

    public User findUserByUsername(String username){
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(User.class, username);
        }

    }

//    public void updateUser(User user){
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.merge(user);
//            transaction.commit();
//        }
//
//    }

    public User update(User updatedUser) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User updateUser = session.merge(updatedUser);
        transaction.commit();
        session.close();
        return updateUser;
    }

    public boolean existsByUsername(String username) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        String query = "SELECT count(u) FROM User u WHERE u.username = :username";

        boolean exists = session.createQuery(query, Long.class)
                .setParameter("username", username)
                .uniqueResult() > 0;

        session.close();
        return exists;
    }


}
