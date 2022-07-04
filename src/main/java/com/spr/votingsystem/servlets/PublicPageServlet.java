package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.ElectionController;
import com.spr.votingsystem.interfaces.ElectionLocal;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "PublicPageServlet", urlPatterns = {"/public"})
public class PublicPageServlet extends HttpServlet {

//    private ElectionController electcont;
    @EJB
    private ElectionLocal electcont;

//    public void init() {
//        electcont = new ElectionController();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("listElections", electcont.listElections());
        request.getRequestDispatcher("/public/public_home.jsp").forward(request, response);
    }
}
