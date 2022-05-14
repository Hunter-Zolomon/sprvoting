package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.ElectionController;
import com.spr.votingsystem.model.Election;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ElectionManagementServlet", urlPatterns = {"/staff/election_management",
        "/staff/election_management/add_election", "/staff/election_management/end_election",
        "/staff/election_management/grab_election"})
public class ElectionManagementServlet extends HttpServlet {

    private ElectionController electcont;

    public void init() {
        electcont = new ElectionController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer election_id = null;
        if (request.getParameter("election_id") != null)
            election_id = Integer.parseInt(request.getParameter("election_id"));
        String dispatchLocation;
        List<Election> listElections = null;
        switch (servletPath) {
            case "/staff/election_management/grab_election":
                listElections = electcont.listElections();
                dispatchLocation = "/staff/staff_election_management.jsp";
                break;
            case "/staff/election_management/add_election":
                dispatchLocation = "/staff/staff_add_edit_election.jsp";
                break;
            case "/staff/account_management/end_election":
                dispatchLocation = "/staff/staff_election_management.jsp";
                electcont.deleteElection(election_id);
                break;
            default:
                dispatchLocation = "/staff/staff_election_management.jsp";
                listElections = electcont.listElections();
                break;
        }
        request.setAttribute("listElections", listElections);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().equals("/staff/election_management/add_election")) {
            String election_name = (String) request.getParameter("election_name");
            List<Integer> seat_numbering = Arrays.stream(request.getParameterValues("seat_numbering"))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            Date election_date = new Date(request.getParameter("election_date"));
            electcont.addElection(election_name, election_date, seat_numbering);
        }
    }
}
