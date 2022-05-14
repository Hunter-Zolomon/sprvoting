package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.UserController;
import com.spr.votingsystem.model.User;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AuthenticationServlet", urlPatterns = {"/auth"})
public class AuthenticationServlet extends HttpServlet {

    private UserController usrcont;

    public void init() {
        usrcont = new UserController();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String context = request.getContextPath();
        if (HelperFunctions.isSessionValid(session)) {
            request.getRequestDispatcher( "/usr_redirector").forward(request, response);
            return;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User retrieved_user = usrcont.authenticateUser(username, password);
        if (retrieved_user != null) {
            session = request.getSession();
            session.setAttribute("fname", retrieved_user.getFirstName());
            session.setAttribute("lname", retrieved_user.getLastName());
            session.setAttribute("username", retrieved_user.getUsername());
            session.setAttribute("role", retrieved_user.getRole());
            response.sendRedirect(context + "/staff");
        } else {
            request.getSession().invalidate();
//            request.getRequestDispatcher("/usr_redirector").forward(request, response);
            response.sendRedirect(context + "/usr_redirector");
        }
    }

    public void destroy() {
        usrcont.shutdown();
    }
}
