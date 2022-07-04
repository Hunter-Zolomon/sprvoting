package com.spr.votingsystem.controller;

import com.spr.votingsystem.interfaces.UserLocal;
import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.model.User;
import com.spr.votingsystem.model.Vote;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Stateless
public class UserController implements UserLocal {

    @PersistenceContext
    private EntityManager manager;

    public User authenticateUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) return null;
        if (HelperFunctions.validatePasswordHash(password, user.getPassword())) return user;
        return null;
    }

    public User getUserById(Integer id) {
        return manager.find(User.class, id);
    }

    public User getUserByUsername(String username) {
        return manager.createQuery("select u from User u where u.username = :value1", User.class)
                .setParameter("value1", username).getSingleResult();
    }

    public User addPublic(String usrname, String pass, String fname,
                             String lname, String ic, String email, String addr,
                             String phone, int age, String gender, String race,
                             String religion, String education, double income) {
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
//            user.setToken("x");
        manager.persist(user);
        return user;
    }

    public User addPartyRep(String usrname, String pass, String fname, String lname,
                               String ic, String email, String addr, String phone, Party party) {
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
//            manager.persist(user);
        return manager.merge(user);
    }

    public User addStaff(String usrname, String pass, String fname, String lname,
                               String ic, String email, String addr, String phone) {
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
            return user;
    }

    public User addVote(Integer userID, Integer voteID) {
        User user = manager.find(User.class, userID);
        Vote retrievedVote = manager.find(Vote.class, voteID);
        Set<Vote> votes = user.getVotes();
        votes.add(retrievedVote);
        user.setVotes(votes);
        return manager.merge(user);
    }

    public List<User> listPublic() {
        return manager.createQuery("select u from User u where u.role = :value1", User.class)
                .setParameter("value1", "Public")
                .getResultList();
    }

    public User changePassword(Integer userID, String new_password) {
        User user = manager.find(User.class, userID);
        user.setPassword(HelperFunctions.hashPassword(new_password));
        return manager.merge(user);
    }

    public List<User> searchAllColumns(String type, String search_string) {
        return manager.createQuery("select u from User u where (u.role like :value2) and " +
                        "(u.username like :value1 or " +
                        "u.firstName like :value1 or " +
                        "u.lastName like :value1 or " +
                        "u.ic like :value1 or " +
                        "u.email like :value1 or " +
                        "u.address like :value1 or " +
                        "u.phone_no like :value1 or " +
                        "u.gender like :value1 or " +
                        "u.race like :value1 or " +
                        "u.religion like :value1 or " +
                        "u.education like :value1)", User.class)
                .setParameter("value1", "%" + search_string + "%")
                .setParameter("value2", "%" + type + "%")
                .getResultList();
    }

    public List<User> listStaff() {
        return manager.createQuery("select u from User u where u.role = :value1", User.class)
                .setParameter("value1", "Staff")
                .getResultList();
    }

    public List<User> listParty() {
        return manager.createQuery("select u from User u where u.role = :value1", User.class)
                .setParameter("value1", "Party")
                .getResultList();
    }

    public List<User> listUsers() {
       return manager.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> listUsersBy(String field, Object value) {
        return manager.createQuery("select u from User u." + field + " = :value1", User.class)
                .setParameter("value1", value)
                .getResultList();
    }
    public User updatePublic(Integer userID, String usrname, String fname, String lname,
                             String ic, String email, String addr, String phone, Integer age,
                             String gender, String race, String religion, String education, Double income) {
        User user = manager.find(User.class, userID);
        user.setUsername(usrname);
//            user.setPassword(pass);
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
        return manager.merge(user);
    }

    public User updateStaff(Integer userID, String usrname, String fname, String lname,
                            String ic, String email, String addr, String phone) {
        User user = manager.find(User.class, userID);
        user.setUsername(usrname);
//            user.setPassword(pass);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setIc(ic);
        user.setEmail(email);
        user.setAddress(addr);
        user.setPhone_no(phone);
        return manager.merge(user);
    }

    public User updateParty(Integer userID, String usrname, String fname, String lname,
                            String ic, String email, String addr, String phone, Party party) {
        User user = manager.find(User.class, userID);
        user.setUsername(usrname);
//            user.setPassword(pass);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setIc(ic);
        user.setEmail(email);
        user.setAddress(addr);
        user.setPhone_no(phone);
        user.setParty(party);
        return manager.merge(user);
    }

    public boolean deleteUser(Integer userID) {
        try {
            User user = manager.find(User.class, userID);
            manager.remove(user);
            return true;
        } catch (EJBException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
