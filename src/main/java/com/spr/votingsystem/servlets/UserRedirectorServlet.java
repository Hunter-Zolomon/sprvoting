package com.spr.votingsystem.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UserRedirectorServlet", urlPatterns = {"/usr_redirector"})
public class UserRedirectorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String context = request.getContextPath();
        if (session != null && session.getAttribute("role") != null) {
            String usr_role = (String) session.getAttribute("role");
            response.sendRedirect(context + "/" + usr_role.toLowerCase());
        } else {
            response.sendRedirect(context + "/main");
        }
    }
}
