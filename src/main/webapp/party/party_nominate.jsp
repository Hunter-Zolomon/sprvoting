<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Candidate Nomination</title>
</head>
<body>
<jsp:include page="party_banner.jsp"></jsp:include>

<form action="${pageContext.request.contextPath}/party/nominate" method="post">
    <input type="text" class="hidden" name="election_id" value="${election_id}" hidden>
    <div class="col-md-3">
        <label for="seat" class="form-label">Select Seat</label>
        <select name="seat_id" class="form-select" id="seat" required>
            <c:forEach var="seat" items="${listSeats}">
                <option value="${seat.id}">${seat.id}</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-md-3">
        <label for="candidate" class="form-label">Select Candidate</label>
        <select name="candidate_id" class="form-select" id="candidate" required>
            <c:forEach var="candidate" items="${listCandidates}">
                <option value="${candidate.id}">${candidate.first_name} ${candidate.last_name}</option>
            </c:forEach>
        </select>
    </div>
    <input type="submit" class="btn btn-primary" value="Nominate Candidate For Seat">
    <c:if test="${error != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
</form>

</body>
</html>
