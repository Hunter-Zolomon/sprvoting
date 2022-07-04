<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Candidate Management</title>
</head>
<body>
<jsp:include page="party_banner.jsp"></jsp:include>

<br>

<form method="get">
<div class="row">
    <div class="container">
        <h3 class="text-center">List of Candidates</h3>
        <hr>
        <c:if test="${error != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="inputGroup-sizing-sm">Search</span>
            <input type="text" name="search_string" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">
            <label class="btn btn-outline-success" for="query_button">Query</label>
            <input type="submit" class="btn-check" formaction="${pageContext.request.contextPath}/party/candidate_management"
                   name="query_button" id="query_button" autocomplete="off">
            <%--          <button type="submit" formaction="/staff/account_management/grab_public" class="btn btn-primary">Filter</button>--%>
            <%--          <button type="submit" formaction="/staff/add_edit_user.jsp" class="btn btn-info">Add New User</button>--%>
            <a href="${pageContext.request.contextPath}/party/candidate_management/add_candidate" class="btn btn-info">Add New Candidate</a>
        </div>
        <%--            <a href="${pageContext.request.contextPath}/staff/staff_add_edit_party.jsp" class="btn btn-access">Add New Party</a>--%>
        <%--        </div>--%>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Qualifications</th>
                <th>Party</th>
                <th>Elections</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="candidate" items="${listCandidates}">
                <tr>
                    <td><c:out value="${candidate.id}"/></td>
                    <td><c:out value="${candidate.first_name}"/></td>
                    <td><c:out value="${candidate.last_name}"/></td>
                    <td><c:out value="${candidate.qualifications}"/></td>
                    <td><c:out value="${candidate.party.name}"/></td>
                    <td>
                        <c:forEach var="election" items="${candidate.elections}">
                            ${election.name} <br>
                        </c:forEach>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/party/candidate_management/update_candidate?candidate_id=<c:out value='${candidate.id}' />">Edit</a>
                        <a href="${pageContext.request.contextPath}/party/candidate_management/delete_candidate?candidate_id=<c:out value='${candidate.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</form>
</body>
</html>
