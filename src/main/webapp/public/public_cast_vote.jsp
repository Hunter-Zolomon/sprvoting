<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cast Vote</title>
</head>
<body>
<jsp:include page="public_banner.jsp"></jsp:include>

<c:if test="${listCandidates != null}">
    <form action="${pageContext.request.contextPath}/public/voting_management/cast_vote" method="post">
        <c:forEach var="candidate" items="${listCandidates}">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="selected_candidate" id="inlineRadio1" value="${candidate.id}">
                <label class="form-check-label" for="inlineRadio1">${candidate.first_name} ${candidate.last_name}</label>
            </div>
        </c:forEach>
        <input type="submit" value="Vote For Selected Candidate" class="btn btn-info">
    </form>
</c:if>

</body>
</html>
