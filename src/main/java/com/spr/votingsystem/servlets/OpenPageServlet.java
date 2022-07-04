package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.ElectionController;
import com.spr.votingsystem.interfaces.ElectionLocal;
import com.spr.votingsystem.utilities.ReportGeneration;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OpenPageServlet", urlPatterns = {"/main", "/election_results"})
public class OpenPageServlet extends HttpServlet {

//    private ElectionController electcont;
    @EJB
    private ElectionLocal electcont;

//    public void init() {
//        electcont = new ElectionController();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().equals("/main")) {
            request.setAttribute("listElections", electcont.listElections());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else if (request.getServletPath().equals("/election_results")) {
            if (request.getParameter("election_id") == null) {
                return;
            }
            Integer election_id = Integer.parseInt(request.getParameter("election_id"));
            ReportGeneration reportGenerator = new ReportGeneration(electcont.getElectionById(election_id));
            Map<Integer, Map<String, Long>> voteReport = reportGenerator.generateSeatVoteAnalysis();
            request.setAttribute("listSeats", electcont.getElectionById(election_id).getSeats());
            request.setAttribute("voteReport", voteReport);
            request.getRequestDispatcher("/open_seat_results.jsp").forward(request, response);
        }
    }
}
