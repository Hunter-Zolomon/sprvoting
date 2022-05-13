package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.UserController;
import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginPageServlet", urlPatterns = {"/login"})
public class LoginPageServlet extends HttpServlet {

    private UserController usrcont;

    public void init() {
        usrcont = new UserController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        usrcont.addPublic("avi", HelperFunctions.hashPassword("123"), "Avi", "Loeb",
                "X1234567", "avi@mail.com", "Address Public", "+6013758492", 45,
                "Male", "Jew", "Judaism", "Ph.D", 50000);
        Party newParty = new Party();
        newParty.setId(1);
        newParty.setName("Communist");
        newParty.setCode("jgl105931");
        usrcont.addPartyRep("dyson", HelperFunctions.hashPassword("123"), "James", "Dyson", "Y1234567",
                "dyson@mail.com", "Address Party", "+6011235467", newParty);
        usrcont.addStaff("mrg", HelperFunctions.hashPassword("123"), "Morgan", "Freeman", "Z1234567",
                "morgan@mail.com", "Address Staff", "+601925256893");
        HttpSession session = request.getSession(false);
        String context = request.getContextPath();
        if (HelperFunctions.isSessionValid(session)) {
            request.getRequestDispatcher("/usr_redirector").forward(request, response);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
