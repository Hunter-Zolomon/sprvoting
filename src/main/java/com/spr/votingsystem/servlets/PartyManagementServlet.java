package com.spr.votingsystem.servlets;

import com.spr.votingsystem.controller.CandidateController;
import com.spr.votingsystem.controller.PartyController;
import com.spr.votingsystem.interfaces.CandidateLocal;
import com.spr.votingsystem.interfaces.PartyLocal;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "PartyManagementServlet",
        urlPatterns = {"/staff/party_management", "/staff/party_management/grab_party",
                "/staff/party_management/grab_candidate", "/staff/party_management/add_party",
                "/staff/party_management/update_party", "/staff/party_management/add_candidate",
                "/staff/party_management/update_candidate", "/staff/party_management/delete_party",
                "/staff/party_management/delete_candidate"})
public class PartyManagementServlet extends HttpServlet {

//    private CandidateController candidatecont;
//    private PartyController partycont;
    @EJB
    private CandidateLocal candidatecont;
    @EJB
    private PartyLocal partycont;

//    public void init() {
//        candidatecont = new CandidateController();
//        partycont = new PartyController();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer party_id = null;
        Integer candidate_id = null;
        if (request.getParameter("party_id") != null)
            party_id = Integer.parseInt(request.getParameter("party_id"));
        if (request.getParameter("candidate_id") != null)
            candidate_id = Integer.parseInt(request.getParameter("candidate_id"));
        String search_string = request.getParameter("search_string") != null ? request.getParameter("search_string").toLowerCase() : "";
        String dispatchLocation;
        List<Party> listParties = null;
        List<Candidate> listCandidates = null;
        Party retrievedParty = null;
        switch (servletPath) {
            case "/staff/party_management/add_party":
                dispatchLocation = "/staff/staff_add_edit_party.jsp";
                break;
            case "/staff/party_management/update_party":
                dispatchLocation = "/staff/staff_add_edit_party.jsp";
                retrievedParty = partycont.getPartyById(party_id);
                break;
            case "/staff/party_management/delete_party":
                dispatchLocation = "/staff/staff_party_management.jsp";
                try {
                    partycont.deleteParty(party_id);
                } catch (EJBException ex) {
                    request.setAttribute("error", "An error occurred during the delete. Please Try Again!");
                }
                break;
            case "/staff/party_management/delete_candidate_from_party":
                dispatchLocation = "/staff/staff_party_management.jsp";
                try {
                    partycont.deleteCandidate(party_id, candidate_id);
                } catch (EJBException ex) {
                    request.setAttribute("error", "An error occurred during the delete. Please Try Again!");
                }
                break;
            case "/staff/party_management/grab_party":
//                listParties = partycont.listParties();
//                dispatchLocation = "/staff/staff_party_management.jsp";
//                break;
            default:
                dispatchLocation = "/staff/staff_party_management.jsp";
                listParties = partycont.listParties();
                if (!search_string.isEmpty()) {
                    listParties = listParties
                            .stream()
                            .filter(party -> Integer.toString(party.getId()).toLowerCase().contains(search_string)
                                    || party.getCode().toLowerCase().contains(search_string)
                                    || party.getName().toLowerCase().contains(search_string))
                            .collect(Collectors.toList());
                }
                break;
        }
        request.setAttribute("listParties", listParties);
        request.setAttribute("party", retrievedParty);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Integer party_id = null;
        if (request.getParameter("party_id") != null)
            party_id = Integer.parseInt(request.getParameter("party_id"));
        String party_name = request.getParameter("party_name");
        String party_description = request.getParameter("party_description");

        switch (servletPath) {
            case "/staff/party_management/add_party":
                partycont.addParty(party_name, party_description);
                break;
            case "/staff/party_management/update_party":
                partycont.updateParty(party_id, party_name, party_description); //TODO add list of candidates
                break;
            default:
                break;
        }
        response.sendRedirect(request.getContextPath() + "/staff/party_management");
    }
}
