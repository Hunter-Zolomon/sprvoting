package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.CandidateController;
import com.spr.votingsystem.controller.PartyController;
import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Party;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PartyManagementServlet",
        urlPatterns = {"/staff/party_management", "/staff/party_management/grab_party",
                "/staff/party_management/grab_candidate", "/staff/party_management/add_party",
                "/staff/party_management/update_party", "/staff/party_management/add_candidate",
                "/staff/party_management/update_candidate", "/staff/party_management/delete_party",
                "/staff/party_management/delete_candidate"})
public class PartyManagementServlet extends HttpServlet {

    private CandidateController candidatecont;
    private PartyController partycont;

    public void init() {
        candidatecont = new CandidateController();
        partycont = new PartyController();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer party_id = null;
        Integer candidate_id = null;
        if (request.getParameter("party_id") != null)
            party_id = Integer.parseInt(request.getParameter("party_id"));
        if (request.getParameter("candidate_id") != null)
            candidate_id = Integer.parseInt(request.getParameter("candidate_id"));
        String dispatchLocation;
        List<Party> listParties = null;
        List<Candidate> listCandidates = null;
        Party retrievedParty = null;
        Candidate retrievedCandidate = null;
        switch (servletPath) {
            case "/staff/party_management/grab_party":
                listParties = partycont.listParties();
                dispatchLocation = "/staff/staff_party_management.jsp";
                break;
            case "/staff/account_management/grab_candidate":
                listCandidates = candidatecont.listCandidates();
                dispatchLocation = "/staff/staff_party_management.jsp";
                break;
            case "/staff/account_management/add_party":
                dispatchLocation = "/staff/staff_add_edit_party.jsp";
                break;
            case "/staff/account_management/add_candidate":
                dispatchLocation = "/staff/staff_add_edit_candidate.jsp";
                break;
            case "/staff/account_management/update_party":
                dispatchLocation = "/staff/staff_add_edit_party.jsp";
                retrievedParty = partycont.getPartyById(party_id);
                break;
            case "/staff/account_management/update_candidate":
                dispatchLocation = "/staff/staff_add_edit_candidate.jsp";
                retrievedCandidate = candidatecont.getCandidateById(candidate_id);
                break;
            case "/staff/party_management/delete_party":
                dispatchLocation = "/staff/staff_party_management";
                partycont.deleteParty(party_id);
                break;
            case "/staff/party_management/delete_candidate":
                dispatchLocation = "/staff/staf_party_management";
                candidatecont.deleteCandidate(candidate_id);
                break;
            default:
                dispatchLocation = "/staff/staff_party_management.jsp";
                listParties = partycont.listParties();
                break;
        }
        request.setAttribute("listParties", listParties);
        request.setAttribute("listCandidates", listCandidates);
        request.setAttribute("retrievedParty", retrievedParty);
        request.setAttribute("retrievedCandidate", retrievedCandidate);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer party_id = null;
        Integer candidate_id = null;
        if (request.getParameter("party_id") != null)
            party_id = Integer.parseInt(request.getParameter("party_id"));
        if (request.getParameter("candidate_id") != null)
            candidate_id = Integer.parseInt(request.getParameter("candidate_id"));
        String party_name = (String) request.getAttribute("party_name");
        String candidate_fname = (String) request.getAttribute("candidate_fname");
        String candidate_lname = (String) request.getAttribute("candidate_lname");
        String candidate_quals = (String) request.getAttribute("candidate_quals");

        switch (servletPath) {
            case "/staff/party_management/add_party":
                partycont.addParty(party_name);
                break;
            case "/staff/party_management/update_party":
                partycont.updateParty(party_id, party_name); //TODO add list of candidates
                break;
            case "/staff/party_management/add_candidate":
                candidatecont.addCandidate(candidate_fname, candidate_lname, candidate_quals,
                        partycont.getPartyById(party_id));
                break;
            case "/staff/party_management/update_candidate":
                candidatecont.updateCandidate(candidate_id, candidate_fname, candidate_lname, candidate_quals,
                        partycont.getPartyById(party_id));
                break;
            default:
                break;
        }
    }
}
