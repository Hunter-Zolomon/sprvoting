package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.UserController;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "PublicRegistrationServlet", urlPatterns = {"/pub_register"})
public class PublicRegistrationServlet extends HttpServlet {

    private UserController usrcont;

    public void init() {
        usrcont = new UserController();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String ic = request.getParameter("ic_no");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone_no = request.getParameter("phone_no");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String race = request.getParameter("race");
        String religion = request.getParameter("religion");
        String education = request.getParameter("education");
        double income = Double.parseDouble(request.getParameter("income"));
        
//        String generatedPass = HelperFunctions.randomPasswordGenerator();

        Integer idResult = usrcont.addPublic(username, HelperFunctions.hashPassword(password), firstName, lastName,
                    ic, email, address, phone_no, age, gender, race, religion, education, income);

        if (idResult >= 0) {
//            EmailUtility.sendEmail("", "", "", "<z3D!P1ltF", ).emailConstructor()
            response.sendRedirect(request.getContextPath() + "/usr_redirector");
        } else {
            response.getWriter().println("Your account wasn't registered due to an error! Please try again.");
        }
    }
}
