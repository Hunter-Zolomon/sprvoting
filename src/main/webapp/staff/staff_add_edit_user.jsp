<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Add/Edit User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: blue">
        <div>
            <a href="#" class="navbar-brand">Account Management</a>
        </div>
        <ul class="navbar-nav">
            <a href="${pageContext.request.contextPath}/staff/account_management/user_public" class="nav-link">Public</a>
            <a href="${pageContext.request.contextPath}/staff/account_management/user_staff" class="nav-link">Staff</a>
            <a href="${pageContext.request.contextPath}/staff/account_management/user_party" class="nav-link">Party</a>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${user != null}">
                <form action="${pageContext.request.contextPath}/staff/account_management/add_user" method="post">
            </c:if>
            <c:if test="${user == null}">
                <form action="${pageContext.request.contextPath}/staff/account_management/update_user" method="post">
            </c:if>
            <caption>
                <c:if test="${user != null}">
                    <h2>Edit User</h2>
                </c:if>
                <c:if test="${user == null}">
                    <h2>Add New User</h2>
                </c:if>
            </caption>
            <c:if test="${user != null}">
                <input type="hidden" name="id" value="<c:out value='${user.id}' />">
            </c:if>
            <label for="username">Username: </label>
            <input type="text" id="username" name="username"
                   pattern="[A-Za-z0-9_]{1,15}"
                   title="Lowercase, Uppercase, and Numbers Allowed. Maximum of 15 characters"
                   value="${user.username}">
            <br>
            <label for="password">Password: </label>
            <input type="password" id="password" name="password"
                   pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                   title="Minimum of 8 characters. At least one uppercase and one number"
                   value="${user.password}">
            <br>
            <label for="role">Role: </label>
            <select id="role" name="role">
                <option value="staff">Staff</option>
                <option value="party">Party</option>
                <option value="public">Public</option>
            </select>
            <br>
            <label for="first_name">First Name: </label>
            <input type="text" id="first_name" name="first_name"
                   pattern="^([^0-9]*)$"
                   title="First name should not contain numbers"
                   value="${user.firstName}">
                <br>
            <label for="last_name">Last Name: </label>
            <input type="text" id="last_name" name="last_name"
                   pattern="^([^0-9]*)$"
                   title="Last name should not contain numbers"
                   value="${user.lastName}">
                <br>
            <label for="ic_no">IC: </label>
            <input type="text" id="ic_no" name="ic_no" value="${user.ic}">
            <br>
            <label for="email">Email: </label>
            <input type="text" id="email" name="email"
                   pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"
                   title="Valid email address is of the form: example@example.com"
                   value="${user.email}">
            <br>
            <label for="address">Address: </label>
            <input type="text" id="address" name="address" value="${user.address}">
            <br>
            <label for="phone_no">Phone Number: </label>
            <input type="text" id="phone_no" name="phone_no"
                   pattern="^(\+?6?01)[02-46-9]-*[0-9]{7}$|^(\+?6?01)[1]-*[0-9]{8}$"
                   title="Valid Malaysian phone number is of the form: +60xxxxxxxxx"
                   value="${user.phone_no}">
            <br>
            <label for="age">Age: </label>
            <input type="text" id="age" name="age"
                   pattern="^(1[89]|[2-9]\d)$"
                   title="You must be older than 18 and younger than 99 to vote"
                   value="${user.age}">
            <br>
            <label for="gender">Gender: </label>
            <select id="gender" name="gender">
                <option value="male">Male</option>
                <option value="female">Female</option>
            </select>
            <br>
            <label for="race">Race: </label>
            <input type="text" id="race" name="race" value="${user.race}">
            <br>
            <label for="religion">Religion: </label>
            <select id="religion" name="religion">
                <option value="christian">Christian</option>
                <option value="muslim">Muslim</option>
                <option value="hindu">Hindu</option>
                <option value="buddhist">Buddhist</option>
                <option value="folk">Folk</option>
                <option value="atheist">Atheist</option>
                <option value="shinto">Shinto</option>
                <option value="taoism">Taoism</option>
                <option value="vodou">Vodou</option>
                <option value="sikhism">Sikhism</option>
                <option value="judaism">Judaism</option>
                <option value="spiritism">Spiritism</option>
                <option value="shamanism">Shamanism</option>
                <option value="caodaism">Caodaism</option>
                <option value="confucianism">Confucianism</option>
                <option value="bahai">Bahai</option>
                <option value="jainism">Jainism</option>
                <option value="cheondoism">Cheondoism</option>
                <option value="hoahaoism">Hoahaoism</option>
                <option value="tenriism">Tenriism</option>
                <option value="druze">Druze</option>
            </select>
            <br>
            <label for="education">Education: </label>
            <select id="education" name="education">
                <option value="diploma">High School Diploma</option>
                <option value="associate">Associate's Degree</option>
                <option value="bachelor">Bachelor's Degree</option>
                <option value="master">Master's Degree</option>
                <option value="phd">Ph.D</option>
            </select>
            <br>
            <label for="income">Average Income: </label>
            <input type="text" id="income" name="income"
                   pattern="^[1-9][0-9]*$"
                   title="Valid income must be a positive whole number"
                   value="${user.income}">
            <br>
            <label for="token">Token: </label>
            <input type="text" id="token" name="token" value="<c:out value='${user.token}' />">
            <br>
            <label for="party">Party: </label>
            <select id="party" name="party">
                <c:forEach var="party" items="${partyList}">
                    <option value="${party.code}">${party.name}</option>
                </c:forEach>
            </select>
            <br>
            <button type="submit" class="btn btn-success">Save</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
