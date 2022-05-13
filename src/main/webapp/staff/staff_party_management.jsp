<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Party Management</title>
</head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
      crossorigin="anonymous">
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: blue">
        <div>
            <a href="${pageContext.request.contextPath}/staff/party_management" class="navbar-brand">Party Management</a>
        </div>
        <ul class="navbar-nav">
            <a href="${pageContext.request.contextPath}/staff/party_management/grab_party" class="nav-link">Party</a>
            <a href="${pageContext.request.contextPath}/staff/party_management/grab_candidate" class="nav-link">Candidate</a>
        </ul>
    </nav>
</header>
<br>
<div class="row">
    <div class="container">
            <a href="#" class="btn btn-access">Add New User</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Code</th>
                <th>Name</th>
                <th>Candidates</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="party" items="${listParties}">
                <tr>
                    <td><c:out value="${party.id}"/></td>
                    <td><c:out value="${party.code}"/></td>
                    <td><a href="/staff/party_management/grab_candidates"></a></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/staff/account_management/update_user?id=<c:out value='${user.id}' />">Edit</a>
                        <a href="${pageContext.request.contextPath}/staff/account_management/delete_user?id=<c:out value='${user.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
