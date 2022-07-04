<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Party Home Page</title>
</head>
<body>
<jsp:include page="party_banner.jsp"></jsp:include>

<c:forEach var="election" items="${listElections}">
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">${election.name}</h5>
            <p class="card-text">${election.date}</p>
            <c:if test="${election.completed == false}">
                <a href="${pageContext.request.contextPath}/party/nominate?election_id=${election.id}" class="btn btn-primary">Nominate Candidate</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/party/report_management?election_id=${election.id}" class="btn btn-primary">Election Reports</a>
            <a href="${pageContext.request.contextPath}/party/election_results?election_id=${election.id}" class="btn btn-primary">View Results</a>
        </div>
    </div>
</c:forEach>

</body>
</html>
