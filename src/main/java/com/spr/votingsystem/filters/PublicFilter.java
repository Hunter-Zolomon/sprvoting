package com.spr.votingsystem.filters;

import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "PublicFilter", urlPatterns = {"/public/*"})
public class PublicFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String context = req.getContextPath();

        if (HelperFunctions.isSessionValid(session) && HelperFunctions.isUser(session, "Public")) {
            chain.doFilter(request, response);
        } else if (HelperFunctions.isSessionValid(session) && !HelperFunctions.isUser(session, "Public")) {
            req.getRequestDispatcher("/usr_redirector").forward(req, res);
        } else {
            res.sendRedirect(context + "/login");
        }
    }
}
