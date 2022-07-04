package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.UserController;
import com.spr.votingsystem.interfaces.UserLocal;
import com.spr.votingsystem.mail.EmailUtility;
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

@WebServlet(name = "PublicRegistrationServlet", urlPatterns = {"/pub_register"})
public class PublicRegistrationServlet extends HttpServlet {

//    private UserController usrcont;
    @EJB
    private UserLocal usrcont;

//    public void init() {
//        usrcont = new UserController();
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String ic = request.getParameter("ic");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone_no = request.getParameter("phone_no");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String race = request.getParameter("race");
        String religion = request.getParameter("religion");
        String education = request.getParameter("education");
        double income = Double.parseDouble(request.getParameter("income"));
        
        String generatedPass = HelperFunctions.randomPasswordGenerator();

        try {
            User newUser = usrcont.addPublic(username, HelperFunctions.hashPassword(generatedPass), firstName, lastName,
                    ic, email, address, phone_no, age, gender, race, religion, education, income);
            if (newUser != null) {
            EmailUtility.sendEmail("smtp.gmail.com",
                    587,
                    "address@localmail.com",
                    "someSecurePassword",
                    "address@localmail.com",
                    "SPR Account Registration",
                    EmailUtility.emailConstructor("pass", generatedPass));
                response.sendRedirect(request.getContextPath() + "/usr_redirector");
            }
        } catch (EJBException ex) {
            if (username.isEmpty()) {
                request.setAttribute("error", "An account with those credentials exists. Please try logging in!");
            } else {
                request.setAttribute("error", "An internal error occurred. Please try again.");
            }
        }

//            response.getWriter().println("Your account wasn't registered due to an error! Please try again.");
            request.getRequestDispatcher("/open_register.jsp").forward(request, response);
        }
    }
