package com.spr.votingsystem.servlets;

import com.spr.votingsystem.interfaces.ElectionLocal;
import com.spr.votingsystem.utilities.ReportGeneration;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ReportManagementServlet", urlPatterns = {"/staff/report_management",
        "/party/report_management"})
public class ReportManagementServlet extends HttpServlet {

    @EJB
    private ElectionLocal electcont;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReportGeneration reportGenerator;
        String servletPath = request.getServletPath();
        Integer election_id = null;
        String dispatchLocation = null;
        if (request.getParameter("election_id") == null) { return; }
        election_id = Integer.parseInt(request.getParameter("election_id"));
        reportGenerator = new ReportGeneration(electcont.getElectionById(election_id));
        if (servletPath.equals("/staff/report_management")) {
            dispatchLocation = "/staff/staff_election_reports.jsp";
        } else if (servletPath.equals("/party/report_management")) {
            dispatchLocation = "/party/party_election_reports.jsp";
        } else {
            return;
        }
        List<Integer> ageReport = reportGenerator.generateAgeAnalysis();
        List<Integer> genderReport = reportGenerator.generateGenderAnalysis();
        Map<String, Long> raceReport = reportGenerator.generateRaceAnalysis();
        Map<String, Long> religionReport = reportGenerator.generateReligionAnalysis();
        Map<String, Map<String, Long>> educationReport = reportGenerator.generateEducationAnalysis();
        List<Integer> incomeReport = reportGenerator.generateIncomeAnalysis();
        request.setAttribute("ageData", ageReport);
        request.setAttribute("genderData", genderReport);
        request.setAttribute("raceDataKeys", raceReport.keySet());
        request.setAttribute("raceDataValues", raceReport.values());
        request.setAttribute("religionDataKeys", religionReport.keySet());
        request.setAttribute("religionDataValues", religionReport.values());
        request.setAttribute("educationDataValuesM", educationReport.get("Male").values());
        request.setAttribute("educationDataValuesF", educationReport.get("Female").values());
        request.setAttribute("incomeData", incomeReport);
        request.setAttribute("election_id", election_id);
        request.getRequestDispatcher(dispatchLocation).forward(request, response);
    }
}
