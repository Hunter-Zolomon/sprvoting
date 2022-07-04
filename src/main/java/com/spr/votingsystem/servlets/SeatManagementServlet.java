package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.ElectionController;
import com.spr.votingsystem.controller.SeatController;
import com.spr.votingsystem.interfaces.ElectionLocal;
import com.spr.votingsystem.interfaces.SeatLocal;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SeatManagementServlet", urlPatterns = {"/staff/seat_management",
        "/staff/seat_management/update_seats"})
public class SeatManagementServlet extends HttpServlet {

//    private ElectionController electcont;
//    private SeatController seatcont;
    @EJB
    private ElectionLocal electcont;
    @EJB
    private SeatLocal seatcont;

//    public void init() {
//        electcont = new ElectionController();
//        seatcont = new SeatController();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().equals("/staff/seat_management")) {
            String election_id = request.getParameter("election_id");
            if (election_id != null) {
               request.setAttribute("election",
                       electcont.getElectionById(Integer.parseInt(election_id)));
                request.getRequestDispatcher("/staff/staff_seat_management.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int seat_id;
        int num_contesters;
        String election_id = request.getParameter("election_id");
//        Election electionObj = (Election) request.getAttribute("election");
        if (request.getServletPath().equals("/staff/seat_management/update_seats")) {
            if (request.getParameter("seat") != null && request.getParameter("num_contesters") != null) {
                seat_id = Integer.parseInt(request.getParameter("seat"));
                num_contesters = Integer.parseInt(request.getParameter("num_contesters"));
                seatcont.updateSeat(seat_id, num_contesters);
                request.setAttribute("election",
                        electcont.getElectionById(Integer.parseInt(election_id)));
//                request.setAttribute("election", electionObj);
                request.getRequestDispatcher("/staff/staff_seat_management.jsp").forward(request, response);
            }
        }
    }
}
