<%@ page import="com.spr.votingsystem.model.Election" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SPR Voting</title>
    <link href="${pageContext.request.contextPath}/styles/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">SPR Voting</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/main">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/open_register.jsp">Public Registration</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<c:forEach var="election" items="${listElections}">
  <c:set var="current_election" value="${election}" scope="request"/>
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">${election.name}</h5>
        <p class="card-text">${election.date}</p>
        <a href="${pageContext.request.contextPath}/election_results?election_id=${election.id}"
           class="btn btn-primary">View Results</a>
        <c:if test="<%= ((Election) request.getAttribute("current_election")).getDate().before(new Date()) %>">
          <c:if test="${election.completed == false}">
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Cast Vote</a>
          </c:if>
        </c:if>
      </div>
    </div>
</c:forEach>

</body>
</html>