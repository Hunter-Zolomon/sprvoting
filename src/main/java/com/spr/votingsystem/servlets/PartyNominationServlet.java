package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.CandidateController;
import com.spr.votingsystem.controller.ElectionController;
import com.spr.votingsystem.controller.PartyController;
import com.spr.votingsystem.controller.SeatController;
import com.spr.votingsystem.interfaces.CandidateLocal;
import com.spr.votingsystem.interfaces.ElectionLocal;
import com.spr.votingsystem.interfaces.PartyLocal;
import com.spr.votingsystem.interfaces.SeatLocal;
import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Election;
import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.model.Seat;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(name = "PartyNominationServlet", urlPatterns = { "/party/nominate" })
public class PartyNominationServlet extends HttpServlet {

//    private PartyController partycont;
//    private ElectionController electcont;
//    private SeatController seatcont;
//    private CandidateController candidatecont;
    @EJB
    private PartyLocal partycont;
    @EJB
    private ElectionLocal electcont;
    @EJB
    private SeatLocal seatcont;

//    public void init() {
//        partycont = new PartyController();
//        electcont = new ElectionController();
//        seatcont = new SeatController();
//        candidatecont = new CandidateController();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer election_id = null;
        List<Seat> listSeats = null;
        if (request.getParameter("election_id") != null) {
            election_id = Integer.parseInt(request.getParameter("election_id"));
            listSeats = new ArrayList<>(electcont.getElectionById(election_id).getSeats());
        }
        Set<Candidate> listCandidates = partycont.getPartyById((Integer) request.getSession(false).getAttribute("current_party")).getCandidates();
        request.setAttribute("election_id", election_id);
        request.setAttribute("listSeats", listSeats);
        request.setAttribute("listCandidates", listCandidates);
        request.getRequestDispatcher("/party/party_nominate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer seat_id = null;
        Integer candidate_id = null;
        Integer election_id = null;
        Integer party_id = (Integer) request.getSession(false).getAttribute("current_party");
        if (request.getParameter("seat_id") != null
                && request.getParameter("candidate_id") != null
                && request.getParameter("election_id") != null) {
            seat_id = Integer.parseInt(request.getParameter("seat_id"));
            candidate_id = Integer.parseInt(request.getParameter("candidate_id"));
            election_id = Integer.parseInt(request.getParameter("election_id"));
            Seat retrievedSeat = seatcont.getSeatById(seat_id);
            if (retrievedSeat.getContesters().size() >= retrievedSeat.getNumber_contesters()) {
                request.setAttribute("error", "The maximum number of candidates has been reached! Please try again.");
                request.getRequestDispatcher("/party/party_nominate.jsp").forward(request, response);
                return;
            }
            try {
                Seat seatResult = seatcont.addCandidate(seat_id, candidate_id);
                Election electionResult = electcont.addCandidateToElection(candidate_id, election_id);
//            candidatecont.addCandidateToElection(candidate_id, election_id);
                Party partyResult = partycont.addElection(party_id, election_id);
            } catch (EJBException ex) {
                request.setAttribute("error", "An error occurred during the nomination. Please ensure candidate has not been nomiated for this seat.");
                request.getRequestDispatcher("/party/party_nominate.jsp").forward(request, response);
                return;
            }
//            if (seatResult == null || electionResult == null || partyResult == null) {
//                request.setAttribute("error", "An error occurred during the nomination. Please ensure candidate has not been nomiated for this seat.");
//                request.getRequestDispatcher("/party/party_nominate.jsp").forward(request, response);
//                return;
//            }
        }
        response.sendRedirect(request.getContextPath() + "/party");
    }
}
