package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.CandidateController;
import com.spr.votingsystem.controller.PartyController;
import com.spr.votingsystem.controller.SeatController;
import com.spr.votingsystem.interfaces.CandidateLocal;
import com.spr.votingsystem.interfaces.ElectionLocal;
import com.spr.votingsystem.interfaces.PartyLocal;
import com.spr.votingsystem.interfaces.SeatLocal;
import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Party;
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
import java.util.stream.Collectors;

@WebServlet(name = "CandidateManagementServlet", urlPatterns = {"/staff/candidate_management",
        "/staff/candidate_management/add_candidate", "/staff/candidate_management/update_candidate",
        "/staff/candidate_management/delete_candidate", "/staff/candidate_management/grab_party_candidates",
        "/party/candidate_management", "/party/candidate_management/add_candidate",
        "/party/candidate_management/update_candidate", "/party/candidate_management/delete_candidate", "" +
        "/staff/candidate_management/delete_candidate_from_seat", "/party/candidate_management/delete_candidate_from_seat", "" +
        "/staff/candidate_management/delete_candidate_from_election" , "/party/candidate_management/delete_candidate_from_election"})
public class CandidateManagementServlet extends HttpServlet {

    @EJB
    private CandidateLocal candidatecont;
    @EJB
    private PartyLocal partycont;
    @EJB
    private SeatLocal seatcont;
    @EJB
    private ElectionLocal electioncont;

//    private CandidateController candidatecont;
//    private PartyController partycont;
//    private SeatController seatcont;

//    public void init() {
//        candidatecont = new CandidateController();
//        partycont = new PartyController();
//        seatcont = new SeatController();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer party_id = request.getParameter("party_id") != null ? Integer.parseInt(request.getParameter("party_id")) : null;
        Integer candidate_id = request.getParameter("candidate_id") != null ? Integer.parseInt(request.getParameter("candidate_id")) : null;
        Integer session_party_id = (Integer) request.getSession(false).getAttribute("current_party");
        String search_string = request.getParameter("search_string") != null ? request.getParameter("search_string").toLowerCase() : "";
        String dispatchLocation;
        List<Party> listParties = null;
        List<Candidate> listCandidates = null;
        Candidate retrievedCandidate = null;
        Party partyResult = null;
        boolean candidateResult = false;
        switch (servletPath) {
            case "/staff/candidate_management/grab_party_candidates":
                dispatchLocation = "/staff/staff_candidate_management.jsp";
                listCandidates = new ArrayList<>(partycont.getPartyById(party_id).getCandidates());
                break;
            case "/party/candidate_management/add_candidate":
                listParties = partycont.listParties();
                dispatchLocation = "/party/party_add_edit_candidate.jsp";
                break;
            case "/staff/candidate_management/add_candidate":
                dispatchLocation = "/staff/staff_add_edit_candidate.jsp";
                listParties = partycont.listParties();
                break;
            case "/party/candidate_management/update_candidate":
                retrievedCandidate = candidatecont.getCandidateById(candidate_id);
                listParties = partycont.listParties();
                dispatchLocation = "/party/party_add_edit_candidate.jsp";
                break;
            case "/staff/candidate_management/update_candidate":
                dispatchLocation = "/staff/staff_add_edit_candidate.jsp";
                retrievedCandidate = candidatecont.getCandidateById(candidate_id);
                listParties = partycont.listParties();
                break;
            case "/party/candidate_management/delete_candidate":
                try {
                    partyResult = partycont.deleteCandidate(candidatecont.getCandidateById(candidate_id).getParty().getId(),
                            candidate_id);
                    candidateResult = candidatecont.deleteCandidate(candidate_id);
                } catch (EJBException ex) {
                    request.setAttribute("error", "Error while deleting candidate. An internal error occurred please try again.");
                    request.getRequestDispatcher("/party/party_candidate_management.jsp").forward(request, response);
                    return;
                }
//                if (partyResult == null || !candidateResult) {
//                    request.setAttribute("error", "Error while deleting candidate. An internal error occurred please try again.");
//                    request.getRequestDispatcher("/party/party_candidate_management.jsp").forward(request, response);
//                    return;
//                }
                dispatchLocation = "/party/party_candidate_management.jsp";
                break;
            case "/staff/candidate_management/delete_candidate":
                dispatchLocation = "/staff/staff_candidate_management.jsp";
                try {
                    partyResult = partycont.deleteCandidate(candidatecont.getCandidateById(candidate_id).getParty().getId(),
                            candidate_id);
                    candidateResult = candidatecont.deleteCandidate(candidate_id);
                } catch (EJBException ex) {
                    request.setAttribute("error", "Error while deleting candidate. An internal error occurred please try again.");
                    request.getRequestDispatcher("/staff/party_candidate_management.jsp").forward(request, response);
                    return;
                }
//                if (partyResult == null || !candidateResult) {
//                    request.setAttribute("error", "Error while deleting candidate. An internal error occurred please try again.");
//                    request.getRequestDispatcher("/staff/party_candidate_management.jsp").forward(request, response);
//                    return;
//                }
                break;
            case "/party/candidate_management":
                dispatchLocation = "/party/party_candidate_management.jsp";
                listCandidates = new ArrayList<>(partycont.getPartyById(session_party_id).getCandidates());
                if (!search_string.isEmpty()) {
                    listCandidates = listCandidates
                            .stream()
                            .filter(candidate -> Integer.toString(candidate.getId()).contains(search_string)
                                    || candidate.getParty().getName().toLowerCase().contains(search_string)
                                    || candidate.getFirst_name().toLowerCase().contains(search_string)
                                    || candidate.getLast_name().toLowerCase().contains(search_string)
                                    || candidate.getQualifications().toLowerCase().contains(search_string))
                            .collect(Collectors.toList());
                }
                break;
            case "/staff/candidate_management":
                dispatchLocation = "/staff/staff_candidate_management.jsp";
                listCandidates = candidatecont.listCandidates();
                if (!search_string.isEmpty()) {
                    listCandidates = listCandidates
                            .stream()
                            .filter(candidate -> Integer.toString(candidate.getId()).contains(search_string)
                                    || candidate.getParty().getName().toLowerCase().contains(search_string)
                                    || candidate.getFirst_name().toLowerCase().contains(search_string)
                                    || candidate.getLast_name().toLowerCase().contains(search_string)
                                    || candidate.getQualifications().toLowerCase().contains(search_string))
                            .collect(Collectors.toList());
                }
                break;
            default:
                dispatchLocation = "/usr_redirector";
                break;
        }
        Party retrievedParty = null;
        try {
            retrievedParty = partycont.getPartyById(session_party_id);
        } catch (EJBException exception) {
            exception.printStackTrace();
        }
        request.setAttribute("listParties", listParties);
        request.setAttribute("listCandidates", listCandidates);
        request.setAttribute("candidate", retrievedCandidate);
        request.setAttribute("current_party", retrievedParty);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer party_id = null;
        Integer session_party_id = (Integer) request.getSession(false).getAttribute("current_party");
        String dispatch_location = null;
        Integer election_id = request.getParameter("election_id") != null ? Integer.parseInt(request.getParameter("election_id")) : null;
        Integer seat_id = request.getParameter("seat_id") != null ? Integer.parseInt(request.getParameter("seat_id")) : null;
        if (request.getParameter("party_id") != null) {
            party_id = Integer.parseInt(request.getParameter("party_id"));
        }
        Integer candidate_id = null;
        if (request.getParameter("candidate_id") != null) {
            candidate_id = Integer.parseInt(request.getParameter("candidate_id"));
        }
        String candidate_fname = request.getParameter("candidate_fname");
        String candidate_lname = request.getParameter("candidate_lname");
        String candidate_quals = request.getParameter("candidate_quals");

        Candidate candidateResult = null;
        Party partyResult = null;

        switch (servletPath) {
            case "/staff/candidate_management/delete_candidate_from_seat":
                seatcont.deleteCandidate(seat_id, candidate_id);
                dispatch_location = "/staff/party_candidate_management.jsp";
                break;
            case "/party/candidate_management/delete_candidate_from_seat":
                seatcont.deleteCandidate(seat_id, candidate_id);
                dispatch_location = "/party/party_candidate_management.jsp";
                break;
            case "/staff/candidate_management/delete_candidate_from_election":
                electioncont.deleteCandidateFromElection(candidate_id, election_id);
                dispatch_location = "/party/party_candidate_management.jsp";
                break;
            case "/party/candidate_management/delete_candidate_from_election":
                electioncont.deleteCandidateFromElection(candidate_id, election_id);
                dispatch_location = "/staff/party_candidate_management.jsp";
                break;
            case "/party/candidate_management/add_candidate":
                try {
                    candidateResult = candidatecont.addCandidate(candidate_fname, candidate_lname, candidate_quals, session_party_id);
                    partyResult = partycont.addCandidate(party_id, candidateResult.getId());
                } catch (EJBException ex) {
                }
                dispatch_location = "/party/candidate_management";
                break;
            case "/party/candidate_management/update_candidate":
                try {
                    candidateResult = candidatecont.updateCandidate(candidate_id, candidate_fname, candidate_lname, candidate_quals, session_party_id);
                } catch (EJBException ex) {}
                dispatch_location = "/party/candidate_management";
                break;
            case "/staff/candidate_management/add_candidate":
                try {
                    candidateResult = candidatecont.addCandidate(candidate_fname, candidate_lname, candidate_quals, party_id);
                    partyResult = partycont.addCandidate(party_id, candidateResult.getId());
                } catch (EJBException ex) {}
                dispatch_location = "/staff/candidate_management";
            break;
            case "/staff/candidate_management/update_candidate":
                try {
                    candidateResult = candidatecont.updateCandidate(candidate_id, candidate_fname, candidate_lname, candidate_quals, party_id);
                } catch (EJBException ex) {}
                dispatch_location = "/staff/candidate_management";
                break;
            default:
                break;
        }
        if (candidateResult == null || partyResult == null) {
            request.setAttribute("error", "Error during operation. Please recheck candidate details and try again.");
            //TODO Proper Redirection.
        }
        response.sendRedirect(request.getContextPath() + dispatch_location);
    }
}
