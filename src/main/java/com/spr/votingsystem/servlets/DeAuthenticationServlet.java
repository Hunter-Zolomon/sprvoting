package com.spr.votingsystem.servlets;

import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DeAuthenticationServlet", urlPatterns = {"/deauth"})
public class DeAuthenticationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (HelperFunctions.isSessionValid(session)) {
            session.invalidate();
            request.getRequestDispatcher("/usr_redirector").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (HelperFunctions.isSessionValid(session)) {
            session.invalidate();
//            request.getRequestDispatcher("/usr_redirector").forward(request, response);
            response.sendRedirect(request.getContextPath() + "/usr_redirector");
        }
    }
}
