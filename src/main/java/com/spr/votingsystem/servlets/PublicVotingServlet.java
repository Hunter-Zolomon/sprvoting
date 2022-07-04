package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.*;
import com.spr.votingsystem.interfaces.*;
import com.spr.votingsystem.model.*;
import com.spr.votingsystem.utilities.ReportGeneration;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PublicVotingServlet", urlPatterns = { "/public/voting_management/seat_selection",
        "/public/voting_management/cast_vote", "/public/voting_management/grab_seat_candidates" })
public class PublicVotingServlet extends HttpServlet {

//    private ElectionController electcont;
//    private SeatController seatcont;
//    private VoteController votecont;
//    private CandidateController candidatecont;
//    private UserController usercont;
    @EJB
    private ElectionLocal electcont;
    @EJB
    private SeatLocal seatcont;
    @EJB
    private VoteLocal votecont;
    @EJB
    private CandidateLocal candidatecont;
    @EJB
    private UserLocal usercont;

//    public void init() throws ServletException {
//        electcont = new ElectionController();
//        seatcont = new SeatController();
//        votecont = new VoteController();
//        candidatecont = new CandidateController();
//        usercont = new UserController();
//    }

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
        Integer seat_id = null;
        if (request.getParameter("seat_id") != null) {
            seat_id = Integer.parseInt(request.getParameter("seat_id"));
        }
        String dispatchLocation = null;
        List<Seat> listSeats = null;
        List<Candidate> listCandidates = null;
        Election currentElection = null;
        boolean voteStatus = true;
        switch (servletPath) {
            case "/public/voting_management/seat_selection":
                currentElection = electcont.getElectionById(election_id);

                if (currentElection.getDate().after(new Date()) || currentElection.getSeats().stream().anyMatch(seat -> seat.getVotes().stream().anyMatch(vote -> vote.getUsers().stream().anyMatch(user -> user.getId() == user_id)))) {
                    voteStatus = false;
                }
                listSeats = new ArrayList<>(currentElection.getSeats());
                dispatchLocation = "/public/public_seat_selection.jsp";
                break;
            case "/public/voting_management/grab_seat_candidates":
                Seat selected_seat = seatcont.getSeatById(seat_id);
                User userobj = usercont.getUserById(user_id);
                if (selected_seat.getVotes().stream().anyMatch(vote -> vote.getUsers().contains(userobj))) {
                    request.setAttribute("error", "You have already voted for a candidate in the seat " + seat_id);
                    request.getRequestDispatcher("/public/public_seat_selection.jsp").forward(request, response);
                    return;
                }
                listCandidates = new ArrayList<>(seatcont.getSeatById(seat_id).getContesters());
                dispatchLocation = "/public/public_cast_vote.jsp";
                break;
            default:
                break;
        }
        request.setAttribute("listSeats", listSeats);
        request.setAttribute("listCandidates", listCandidates);
        request.setAttribute("voteStatus", voteStatus);
        request.setAttribute("voteReport", voteReport);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (servletPath.equals("/public/voting_management/cast_vote")) {
            Integer candidateID = null;
            if (request.getParameter("selected_candidate") != null) {
                candidateID = Integer.parseInt(request.getParameter("selected_candidate"));
            }
            Vote returnedVote = votecont.addVote(candidateID);
            Integer user_id = (Integer) request.getSession(false).getAttribute("user_id");
            usercont.addVote(user_id, returnedVote.getId());
            response.sendRedirect(request.getContextPath() + "/public");
        }
    }
}
