package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.ElectionController;
import com.spr.votingsystem.interfaces.ElectionLocal;
import com.spr.votingsystem.model.Election;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ElectionManagementServlet", urlPatterns = {"/staff/election_management",
        "/staff/election_management/add_election", "/staff/election_management/end_election",
        "/staff/election_management/grab_election"})
public class ElectionManagementServlet extends HttpServlet {

//    private ElectionController electcont;
    @EJB
    private ElectionLocal electcont;

//    public void init() {
//        electcont = new ElectionController();
//    }

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
            case "/staff/election_management/end_election":
                dispatchLocation = "/staff/staff_home.jsp";
                electcont.endElection(election_id);
                break;
            default:
                dispatchLocation = "/staff/staff_new_election.jsp";
                listElections = electcont.listElections();
                break;
        }
        request.setAttribute("listElections", listElections);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String context = request.getContextPath();
        if (request.getServletPath().equals("/staff/election_management/add_election")) {
            String election_name = request.getParameter("election_name");
            Integer number_seats = Integer.parseInt(request.getParameter("number_seats"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date election_date = null;
            try {
                election_date = formatter.parse(request.getParameter("election_date"));
//                List<Integer> seat_numbering = Arrays.stream(request.getParameterValues("seat_numbering"))
//                        .map(Integer::valueOf)
//                        .collect(Collectors.toList());
//                electcont.addElection(election_name, election_date, seat_numbering);
                Election newElection = null;
                try {
                    newElection = electcont.addElection(election_name, election_date, number_seats);
                } catch (EJBException ex) {
                    request.setAttribute("error", "An election with that name already exists. Please try again.");
                    request.getRequestDispatcher("/staff/staff_new_election.jsp").forward(request, response);
                    return;
                }
//                if (newElection == null) {
//                    request.setAttribute("error", "An election with that name already exists. Please try again.");
//                    request.getRequestDispatcher("/staff/staff_new_election.jsp").forward(request, response);
//                    return;
//                }
                request.setAttribute("election", newElection);
                request.getRequestDispatcher("/staff/staff_seat_management.jsp").forward(request, response);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        response.sendRedirect(request.getContextPath() + "/staff/election_management");
    }
}
