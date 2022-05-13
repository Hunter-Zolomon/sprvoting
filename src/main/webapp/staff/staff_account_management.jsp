<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Account Management</title>
</head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
      crossorigin="anonymous">
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: blue">
       <div>
           <a href="${pageContext.request.contextPath}/staff/account_management" class="navbar-brand">Account Management</a>
       </div>
        <ul class="navbar-nav">
            <a href="${pageContext.request.contextPath}/staff/account_management/grab_public" class="nav-link">Public</a>
            <a href="${pageContext.request.contextPath}/staff/account_management/grab_staff" class="nav-link">Staff</a>
            <a href="${pageContext.request.contextPath}/staff/account_management/grab_party" class="nav-link">Party</a>
        </ul>
    </nav>
</header>
<br>
<div class="row">
    <div class="container">
        <h3 class="text-center">List of Users</h3>
        <hr>
        <div class="container text-left">
            <a href="#" class="btn btn-access">Add New User</a>
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
                <th>Token</th>
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
                    <td><c:out value="${user.token}"/></td>
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
