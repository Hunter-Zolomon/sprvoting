<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Account Management</title>
</head>
<body>
<jsp:include page="staff_banner.jsp"></jsp:include>

<br>

<form method="get">
    <div class="row">
    <div class="container">
        <h3 class="text-center">List of Users</h3>
        <hr>
        <c:if test="${error != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="input-group input-group-sm mb-3">
            <span class="input-group-text" id="search">Search</span>
            <input type="text" name="search_string" value=""
                   class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">

            <span class="input-group-text" id="type">Type</span>
            <input type="submit" class="btn-check" formaction="${pageContext.request.contextPath}/staff/account_management"
                   name="user_type" id="all_button" autocomplete="off">
            <label class="btn btn-outline-success" for="all_button">All</label>
            <input type="submit" class="btn-check" formaction="${pageContext.request.contextPath}/staff/account_management/grab_public"
                   name="user_type" id="public_button" autocomplete="off">
            <label class="btn btn-outline-success" for="public_button">Public</label>
            <input type="submit" class="btn-check" formaction="${pageContext.request.contextPath}/staff/account_management/grab_party"
                   name="user_type" id="party_button" autocomplete="off">
            <label class="btn btn-outline-success" for="party_button">Party</label>
            <input type="submit" class="btn-check" formaction="${pageContext.request.contextPath}/staff/account_management/grab_staff"
                   name="user_type" id="staff_button" autocomplete="off">
            <label class="btn btn-outline-success" for="staff_button">Staff</label>

<%--        <select class="form-select" name="type" aria-label="User Type Selection">--%>
<%--            <option value="All">All</option>--%>
<%--            <option value="Public">Public</option>--%>
<%--            <option value="Party">Party</option>--%>
<%--            <option value="Staff">Staff</option>--%>
<%--        </select>--%>
    <%--          <button type="submit" formaction="/staff/account_management/grab_public" class="btn btn-primary">Filter</button>--%>
    <%--          <button type="submit" formaction="/staff/add_edit_user.jsp" class="btn btn-info">Add New User</button>--%>
<%--          <a href="${pageContext.request.contextPath}/staff/account_management/grab_public" class="btn btn-primary">Filter</a>--%>
          <a href="${pageContext.request.contextPath}/staff/account_management/add_user" class="btn btn-info">Add New User</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
<%--                <th>Password</th>--%>
                <th>Role</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>IC</th>
                <th>Email</th>
                <th>Address</th>
                <th>Phone</th>
                <th>Age</th>
                <th>Gender</th>
                <th>Race</th>
                <th>Religion</th>
                <th>Education</th>
                <th>Income</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${listUsers}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.username}"/></td>
<%--                    <td><c:out value="${user.password}"/></td>--%>
                    <td><c:out value="${user.role}"/></td>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.ic}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.address}"/></td>
                    <td><c:out value="${user.phone_no}"/></td>
                    <td><c:out value="${user.age}"/></td>
                    <td><c:out value="${user.gender}"/></td>
                    <td><c:out value="${user.race}"/></td>
                    <td><c:out value="${user.religion}"/></td>
                    <td><c:out value="${user.education}"/></td>
                    <td><c:out value="${user.income}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/staff/account_management/update_user?id=<c:out value='${user.id}' />">Edit</a>
                        <c:if test="${user.id != user_id}">
                            <a href="${pageContext.request.contextPath}/staff/account_management/delete_user?id=<c:out value='${user.id}' />">Delete</a>
                        </c:if>
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
