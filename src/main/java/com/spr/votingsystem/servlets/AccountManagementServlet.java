package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.PartyController;
import com.spr.votingsystem.controller.UserController;
import com.spr.votingsystem.interfaces.PartyLocal;
import com.spr.votingsystem.interfaces.UserLocal;
import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.model.User;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AccountManagementServlet",
        urlPatterns = {"/staff/account_management", "/staff/account_management/grab_public",
                "/staff/account_management/grab_staff", "/staff/account_management/grab_party",
                "/staff/account_management/add_user", "/staff/account_management/update_user",
                "/staff/account_management/delete_user"})
public class AccountManagementServlet extends HttpServlet {

//    private UserController usrcont;
//    private PartyController partycont;
    @EJB
    private UserLocal usrcont;
    @EJB
    private PartyLocal partycont;

//    public void init() {
//        usrcont = new UserController();
//        partycont = new PartyController();
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        Integer user_id = null;
        if (req.getParameter("id") != null) {
            user_id = Integer.parseInt(req.getParameter("id"));
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String firstName = req.getParameter("fname");
        String lastName = req.getParameter("lname");
        String ic = req.getParameter("ic");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String phone_no = req.getParameter("phone_no");
        Integer age = null;
        if (req.getParameter("age") != null) {
            age = Integer.parseInt(req.getParameter("age"));
        }
        String gender = req.getParameter("gender");
        String race = req.getParameter("race");
        String religion = req.getParameter("religion");
        String education = req.getParameter("education");
        Double income = null;
        if (req.getParameter("income") != null) {
            income = Double.parseDouble(req.getParameter("income"));
        }
//        String token = req.getParameter("token");
        Integer party_id = null;
        if (req.getParameter("sel_party") != null) {
            party_id = Integer.parseInt(req.getParameter("sel_party"));
        }
        User operationResult = null;
        switch (servletPath) {
            case "/staff/account_management/add_user":
                switch (role) {
                    case "Public":
                        operationResult = usrcont.addPublic(username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no, age, gender, race, religion, education, income);
                        break;
                    case "Party":
                        Party selectedParty = partycont.getPartyById(party_id);
                        operationResult = usrcont.addPartyRep(username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no, selectedParty);
                        break;
                    case "Staff":
                        operationResult = usrcont.addStaff(username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no);
                        break;
                    default:
                        break;
                }
                break;
            case "/staff/account_management/update_user":
                switch (role) {
                    case "Public":
                        try {
                            operationResult = usrcont.updatePublic(user_id, username, firstName, lastName,
                                    ic, email, address, phone_no, age, gender, race, religion, education, income);
                        } catch (EJBException ex) {
                        }
                        break;
                    case "Party":
                        try {
                            Party selectedParty = partycont.getPartyById(party_id);
                            operationResult = usrcont.updateParty(user_id, username, firstName, lastName,
                                    ic, email, address, phone_no, selectedParty);
                        } catch (EJBException ex) {
                        }
                        break;
                    case "Staff":
                        try {
                            operationResult = usrcont.updateStaff(user_id, username, firstName, lastName,
                                    ic, email, address, phone_no);
                        } catch (EJBException ex) {
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        if (operationResult == null) {
            if (user_id != null) {
                req.setAttribute("error", "Error while updating user. Please recheck your modifications.");
            } else {
                req.setAttribute("error", "Error while adding new user. A user with those credentials exists.");
            }
            req.getRequestDispatcher("/staff/add_edit_user.jsp");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/staff/account_management");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer user_id = null;
        if (request.getParameter("id") != null)
            user_id = Integer.parseInt(request.getParameter("id"));
        String search_string = "";
        if (request.getParameter("search_string") != null) {
            search_string = request.getParameter("search_string");
        }
        String dispatchLocation = null;
        List<User> listUsers = null;
        List<Party> listParties = null;
        User retrievedUser = null;
        switch (servletPath) {
            case "/staff/account_management/grab_public":
                listUsers = usrcont.searchAllColumns("Public", search_string);
                dispatchLocation = "/staff/staff_account_management.jsp";
                break;
            case "/staff/account_management/grab_party":
                listUsers = usrcont.searchAllColumns("Party", search_string);
                dispatchLocation = "/staff/staff_account_management.jsp";
                break;
            case "/staff/account_management/grab_staff":
                listUsers = usrcont.searchAllColumns("Staff", search_string);
                dispatchLocation = "/staff/staff_account_management.jsp";
                break;
            case "/staff/account_management/add_user":
                listParties = partycont.listParties();
                dispatchLocation = "/staff/staff_add_edit_user.jsp";
                break;
            case "/staff/account_management/update_user":
                retrievedUser = usrcont.getUserById(user_id);
                listParties = partycont.listParties();
                dispatchLocation = "/staff/staff_add_edit_user.jsp";
                break;
            case "/staff/account_management/delete_user":
                try {
                    usrcont.deleteUser(user_id);
                } catch (EJBException ex) {
                    request.setAttribute("error", "Error. User was not deleted due to dependency.");
                    request.getRequestDispatcher("/staff/staff_acount_management.jsp").forward(request, response);
                    return;
                }
//                if (!usrcont.deleteUser(user_id)) {
//                    request.setAttribute("error", "Error. User was not deleted due to dependency.");
//                    request.getRequestDispatcher("/staff/staff_acount_management.jsp").forward(request, response);
//                    return;
//                }
                dispatchLocation = "/staff/staff_account_management.jsp";
                break;
            default:
//                listUsers = usrcont.listUsers();
                listUsers = usrcont.searchAllColumns("", search_string);
                dispatchLocation = "/staff/staff_account_management.jsp";
                break;
        }
        request.setAttribute("user", retrievedUser);
        request.setAttribute("listUsers", listUsers);
        request.setAttribute("listParties", listParties);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }
}
