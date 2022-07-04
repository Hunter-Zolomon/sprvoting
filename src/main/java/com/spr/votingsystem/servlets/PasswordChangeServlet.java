package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.UserController;
import com.spr.votingsystem.interfaces.UserLocal;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "PasswordChangeServlet", urlPatterns = {"/staff/change_user_password"})
public class PasswordChangeServlet extends HttpServlet {

//    private UserController usrcont;
    @EJB
    private UserLocal usrcont;

//    public void init() {
//        usrcont = new UserController();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer user_id = request.getParameter("user_id") != null
                ? Integer.parseInt(request.getParameter("user_id")) : null;
        request.setAttribute("user_id", user_id);
        request.getRequestDispatcher("/staff/staff_user_change_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer user_id = request.getParameter("user_id") != null
                ? Integer.parseInt(request.getParameter("user_id")) : null;
        String current_password = request.getParameter("current_password") != null
                ? request.getParameter("current_password") : null;
        String new_password = request.getParameter("new_password") != null
                ? request.getParameter("new_password") : null;
        if (HelperFunctions.validatePasswordHash(current_password, usrcont.getUserById(user_id).getPassword())) {
            try {
                usrcont.changePassword(user_id, new_password);
            } catch (EJBException ex) {
               request.setAttribute("error", "Error occurred during password change. Please try again!");
               request.getRequestDispatcher("/staff/staff_user_change_password.jsp").forward(request, response);
               return;
            }
            response.sendRedirect(request.getContextPath() + "/staff/account_management");
        }
    }
}
