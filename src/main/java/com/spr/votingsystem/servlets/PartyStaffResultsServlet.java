package com.spr.votingsystem.servlets;

import com.spr.votingsystem.interfaces.*;
import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Election;
import com.spr.votingsystem.model.Seat;
import com.spr.votingsystem.model.User;
import com.spr.votingsystem.utilities.ReportGeneration;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PartyStaffResultsServlet", urlPatterns = {"/staff/election_results",
        "/party/election_results"})
public class PartyStaffResultsServlet extends HttpServlet {

    @EJB
    private ElectionLocal electcont;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer user_id = (Integer) request.getSession(false).getAttribute("user_id");
        Integer election_id = null;
        ReportGeneration reportGenerator = null;
        Map<Integer, Map<String, Long>> voteReport = null;
        if (request.getParameter("election_id") != null) {
            election_id = Integer.parseInt(request.getParameter("election_id"));
            reportGenerator = new ReportGeneration(electcont.getElectionById(election_id));
            voteReport = reportGenerator.generateSeatVoteAnalysis();
        }
        String dispatchLocation = null;
        List<Seat> listSeats = null;
        Election currentElection = null;
        switch (servletPath) {
            case "/party/election_results":
                currentElection = electcont.getElectionById(election_id);
                listSeats = new ArrayList<>(currentElection.getSeats());
                dispatchLocation = "/party/party_election_results.jsp";
                break;
            case "/staff/election_results":
                currentElection = electcont.getElectionById(election_id);
                listSeats = new ArrayList<>(currentElection.getSeats());
                dispatchLocation = "/staff/staff_election_results.jsp";
                break;
            default:
                break;
        }
        request.setAttribute("listSeats", listSeats);
        request.setAttribute("voteReport", voteReport);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
