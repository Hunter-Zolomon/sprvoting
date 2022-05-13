package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.PartyController;
import com.spr.votingsystem.controller.UserController;
import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.model.User;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AccountManagementServlet",
        urlPatterns = {"/staff/account_management", "/staff/account_management/grab_public",
                "/staff/account_management/grab_staff", "/staff/account_management/grab_party",
                "/staff/account_management/add_user", "/staff/account_management/update_user",
                "/staff/account_management/delete_user"})
public class AccountManagementServlet extends HttpServlet {

    private UserController usrcont;
    private PartyController partycont;

    public void init() {
        usrcont = new UserController();
        partycont = new PartyController();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        Integer user_id = Integer.parseInt(req.getParameter("id"));
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String ic = req.getParameter("ic_no");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String phone_no = req.getParameter("phone_no");
        int age = Integer.parseInt(req.getParameter("age"));
        String gender = req.getParameter("gender");
        String race = req.getParameter("race");
        String religion = req.getParameter("religion");
        String education = req.getParameter("education");
        double income = Double.parseDouble(req.getParameter("income"));
        String token = req.getParameter("token");
        Integer party_id = Integer.parseInt(req.getParameter("party"));
        switch (servletPath) {
            case "/staff/account_management/add_user":
                switch (role) {
                    case "Public":
                        usrcont.addPublic(username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no, age, gender, race, religion, education, income);
                        break;
                    case "Party":
                        Party selectedParty = partycont.getPartyById(party_id);
                        usrcont.addPartyRep(username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no, selectedParty);
                        break;
                    case "Staff":
                        usrcont.addStaff(username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no);
                        break;
                    default:
                        break;
                }
                break;
            case "/staff/account_management/update_user":
                switch (role) {
                    case "Public":
                        usrcont.updatePublic(user_id, username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no, age, gender, race, religion, education, income);
                        break;
                    case "Party":
                        Party selectedParty = partycont.getPartyById(party_id);
                        usrcont.updateParty(user_id, username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no, selectedParty);
                        break;
                    case "Staff":
                        usrcont.updateStaff(user_id, username, HelperFunctions.hashPassword(password), firstName, lastName,
                                ic, email, address, phone_no);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer user_id = null;
        if (request.getParameter("id") != null)
            user_id = Integer.parseInt(request.getParameter("id"));
        String dispatchLocation = null;
        List<User> listUsers = null;
        User retrievedUser = null;
        switch (servletPath) {
            case "/staff/account_management/grab_public":
                listUsers = usrcont.listPublic();
                dispatchLocation = "/staff/staff_account_management.jsp";
                break;
            case "/staff/account_management/grab_party":
                listUsers = usrcont.listParty();
                dispatchLocation = "/staff/staff_account_management.jsp";
                break;
            case "/staff/account_management/grab_staff":
                listUsers = usrcont.listStaff();
                dispatchLocation = "/staff/staff_account_management.jsp";
                break;
            case "/staff/account_management/add_user":
                dispatchLocation = "/staff/staff_add_edit_user.jsp";
                break;
            case "/staff/account_management/update_user":
                dispatchLocation = "/staff/staff_add_edit_user.jsp";
                retrievedUser = usrcont.getUserById(user_id);
                break;
            case "/staff/account_management/delete_user":
                dispatchLocation = "/staff/staff_account_management.jsp";
                usrcont.deleteUser(user_id);
                break;
            default:
                dispatchLocation = "/staff/staff_account_management.jsp";
                listUsers = usrcont.listUsers();
                break;
        }
        request.setAttribute("user", retrievedUser);
        request.setAttribute("listUsers", listUsers);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }
}
