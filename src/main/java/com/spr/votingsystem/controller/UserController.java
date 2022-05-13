package com.spr.votingsystem.controller;

import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.model.User;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class UserController {

    private final EntityManagerFactory factory;
    private EntityManager manager;

    public UserController() {
        try {
            factory = Persistence.createEntityManagerFactory("default");
        } catch (Throwable ex) {
            System.err.println("Failed to create entityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void shutdown() {
        if (manager != null) manager.close();
        if (factory != null) factory.close();
    }

    public User authenticateUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) return null;
        if (HelperFunctions.validatePasswordHash(password, user.getPassword())) return user;
        return null;
    }

    public User getUserById(Integer id) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        User returnUser = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            returnUser = manager.find(User.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return returnUser;
    }

    public User getUserByUsername(String username) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        User returnUser = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            returnUser = manager.createQuery("select u from User u where u.username = :value1", User.class)
                    .setParameter("value1", username).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return returnUser;
}

    public Integer addPublic(String usrname, String pass, String fname,
                             String lname, String ic, String email, String addr,
                             String phone, int age, String gender, String race,
                             String religion, String education, double income) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Integer userID = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            User user = new User();
            user.setUsername(usrname);
            user.setPassword(pass);
            user.setRole("Public");
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setIc(ic);
            user.setEmail(email);
            user.setAddress(addr);
            user.setPhone_no(phone);
            user.setAge(age);
            user.setGender(gender);
            user.setRace(race);
            user.setReligion(religion);
            user.setEducation(education);
            user.setIncome(income);
            user.setToken("x");
            manager.persist(user);
            tx.commit();
            userID = user.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return userID;
    }

    public Integer addPartyRep(String usrname, String pass, String fname, String lname,
                               String ic, String email, String addr, String phone, Party party) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Integer userID = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            User user = new User();
            user.setUsername(usrname);
            user.setPassword(pass);
            user.setRole("Party");
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setIc(ic);
            user.setEmail(email);
            user.setAddress(addr);
            user.setPhone_no(phone);
            user.setParty(party);
            manager.persist(user);
            tx.commit();
            userID = user.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return userID;
    }

    public Integer addStaff(String usrname, String pass, String fname, String lname,
                               String ic, String email, String addr, String phone) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Integer userID = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            User user = new User();
            user.setUsername(usrname);
            user.setPassword(pass);
            user.setRole("Staff");
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setIc(ic);
            user.setEmail(email);
            user.setAddress(addr);
            user.setPhone_no(phone);
            manager.persist(user);
            tx.commit();
            userID = user.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return userID;
    }

    public List<User> listPublic() {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        List<User> allUsers = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            allUsers = manager.createQuery("select u from User u where u.role = :value1", User.class)
                    .setParameter("value1", "Public")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return allUsers;
    }

    public List<User> listStaff() {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        List<User> allUsers = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            allUsers = manager.createQuery("select u from User u where u.role = :value1", User.class)
                    .setParameter("value1", "Staff")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return allUsers;
    }

    public List<User> listParty() {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        List<User> allUsers = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            allUsers = manager.createQuery("select u from User u where u.role = :value1", User.class)
                    .setParameter("value1", "Party")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return allUsers;
    }

    public List<User> listUsers() {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        List<User> allUsers = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            allUsers = manager.createQuery("select u from User u", User.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return allUsers;
    }

    public void updatePublic(Integer userID, String usrname, String pass, String fname, String lname,
                             String ic, String email, String addr, String phone, Integer age,
                             String gender, String race, String religion, String education, Double income) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            User user = manager.find(User.class, userID);
            user.setUsername(usrname);
            user.setPassword(pass);
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setIc(ic);
            user.setEmail(email);
            user.setAddress(addr);
            user.setPhone_no(phone);
            user.setAge(age);
            user.setGender(gender);
            user.setRace(race);
            user.setReligion(religion);
            user.setEducation(education);
            user.setIncome(income);
            manager.merge(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void updateStaff(Integer userID, String usrname, String pass, String fname, String lname,
                            String ic, String email, String addr, String phone) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            User user = manager.find(User.class, userID);
            user.setUsername(usrname);
            user.setPassword(pass);
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setIc(ic);
            user.setEmail(email);
            user.setAddress(addr);
            user.setPhone_no(phone);
            manager.merge(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void updateParty(Integer userID, String usrname, String pass, String fname, String lname,
                            String ic, String email, String addr, String phone, Party party) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            User user = manager.find(User.class, userID);
            user.setUsername(usrname);
            user.setPassword(pass);
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setIc(ic);
            user.setEmail(email);
            user.setAddress(addr);
            user.setPhone_no(phone);
            user.setParty(party);
            manager.merge(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void deleteUser(Integer userID) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            User user = manager.find(User.class, userID);
            manager.remove(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }
}
