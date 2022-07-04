package com.spr.votingsystem.interfaces;

import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.model.User;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface UserLocal {

    public User authenticateUser(String username, String password);

    public User getUserById(Integer id);

    public User getUserByUsername(String username);

    public User addPublic(String usrname, String pass, String fname,
                          String lname, String ic, String email, String addr,
                          String phone, int age, String gender, String race,
                          String religion, String education, double income);

    public User addPartyRep(String usrname, String pass, String fname, String lname,
                            String ic, String email, String addr, String phone, Party party);

    public User addStaff(String usrname, String pass, String fname, String lname,
                         String ic, String email, String addr, String phone);

    public User addVote(Integer userID, Integer voteID);

    public List<User> listPublic();

    public User changePassword(Integer userID, String new_password);

    public List<User> searchAllColumns(String type, String search_string);

    public List<User> listStaff();

    public List<User> listParty();

    public List<User> listUsers();

    public List<User> listUsersBy(String field, Object value);

    public User updatePublic(Integer userID, String usrname, String fname, String lname,
                             String ic, String email, String addr, String phone, Integer age,
                             String gender, String race, String religion, String education, Double income);

    public User updateStaff(Integer userID, String usrname, String fname, String lname,
                            String ic, String email, String addr, String phone);

    public User updateParty(Integer userID, String usrname, String fname, String lname,
                            String ic, String email, String addr, String phone, Party party);

    public boolean deleteUser(Integer userID);

}
