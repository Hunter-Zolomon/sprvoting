<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add/Edit User</title>
</head>
<body>
<jsp:include page="staff_banner.jsp"></jsp:include>

<br>

<c:if test="${error != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<c:if test="${user != null}">
<form class="needs-validation" novalidate
      action="${pageContext.request.contextPath}/staff/account_management/update_user" method="post">
    </c:if>
    <c:if test="${user == null}">
    <form class="needs-validation" novalidate
          action="${pageContext.request.contextPath}/staff/account_management/add_user" method="post">
        </c:if>
        <div class="container col-md-10">
            <div class="card">
                <div class="card-body">
            <caption>
                <c:if test="${user != null}">
                    <h2>Edit User</h2>
                </c:if>
                <c:if test="${user == null}">
                    <h2>Add New User</h2>
                </c:if>
            </caption>
            <c:if test="${user != null}">
                <div class="col-md-2">
                    <label for="id" class="form-label">ID</label>
                    <input type="text" name="id" value="${user.id}"
                           class="form-control" id="id" readonly>
                </div>
            </c:if>
            <div class="col-md-4">
                <label for="username" class="form-label">Username</label>
                <div class="input-group">
                    <span class="input-group-text" id="atSign">@</span>
                    <input type="text" name="username" value="${user.username}"
                           class="form-control" id="username"  aria-describedby="inputGroupPrepend2" required
                           pattern="[A-Za-z0-9_]{1,15}"
                           title="Lowercase, Uppercase, and Numbers Allowed. Maximum of 15 characters">
                    <div class="invalid-feedback">Lowercase, Uppercase, and Numbers Allowed. Maximum of 15 characters</div>
                </div>
            </div>
            <c:if test="${user == null}">
                <div class="col-md-4">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" name="password"
                           class="form-control" id="password"
                    <c:if test="${user == null}"> required </c:if>
                           pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                           title="Minimum of 8 characters. At least one uppercase and one number">
                    <div class="invalid-feedback">Minimum of 8 characters. At least one uppercase and one number</div>
                </div>
            </c:if>
            <div class="col-md-3">
                <label for="role" class="form-label">Role</label>
                <select name="role" class="form-select"
                        id="role" onchange="roleChanged()" required>
                    <option value="Public">Public</option>
                    <option value="Staff">Staff</option>
                    <option value="Party">Party</option>
                </select>
                <div class="invalid-feedback">
                    Please select a valid role.
                </div>
            </div>
            <div class="col-md-4">
                <label for="fname" class="form-label">First name</label>
                <input type="text" name="fname" value="${user.firstName}"
                       class="form-control" id="fname" required
                       pattern="^([^0-9]*)$"
                       title="First name should not contain numbers">
                <div class="invalid-feedback">First name should not contain numbers</div>
            </div>
            <div class="col-md-4">
                <label for="lname" class="form-label">Last name</label>
                <input type="text" name="lname" value="${user.lastName}"
                       class="form-control" id="lname" required
                       pattern="^([^0-9]*)$"
                       title="Last name should not contain numbers">
                <div class="invalid-feedback">Last name should not contain numbers</div>
            </div>
            <div class="col-md-4">
                <label for="ic" class="form-label">IC Number</label>
                <input type="text" name="ic" value="${user.ic}"
                       pattern="(([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01]))-([0-9]{2})-([0-9]{4})"
                       title="IC Number Must Be of Format: xxxxxx-xx-xxxx"
                       class="form-control" id="ic" required>
                <div class="invalid-feedback">IC Number Must Be of Format: xxxxxx-xx-xxxx</div>
            </div>
            <div class="col-md-4">
                <label for="email" class="form-label">Email</label>
                <input type="email" name="email" value="${user.email}"
                       class="form-control" id="email" required
                       pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"
                       title="Valid email address is of the form: example@example.com">
                <div class="invalid-feedback">Valid email address is of the form: example@example.com</div>
            </div>
            <div class="col-md-12">
                <label for="address" class="form-label">Address</label>
                <input type="text" name="address" value="${user.address}"
                       class="form-control" id="address" required>
                <div class="invalid-feedback">Please Enter A Valid Address.</div>
            </div>
            <div class="col-md-4">
                <label for="phone_no" class="form-label">Phone number</label>
                <input type="text" name="phone_no" value="${user.phone_no}"
                       class="form-control" id="phone_no" required
                       pattern="^(\+?6?01)[02-46-9]-*[0-9]{7}$|^(\+?6?01)[1]-*[0-9]{8}$"
                       title="Valid Malaysian phone number is of the form: +60xxxxxxxxx">
                <div class="invalid-feedback">Valid Malaysian phone number is of the form: +60xxxxxxxxx</div>
            </div>
            <div class="col-md-2" id="age_div">
                <label for="age" class="form-label">Age</label>
                <input type="text" name="age" value="${user.age}"
                       class="form-control" id="age" required
                       pattern="^(1[89]|[2-9]\d)$"
                       title="You must be older than 18 and younger than 99 to vote">
                <div class="invalid-feedback">You must be older than 18 and younger than 99 to vote</div>
            </div>
            <div class="col-md-3" id="gender_div">
                <label for="gender" class="form-label">Gender</label>
                <select name="gender" class="form-select" id="gender" required>
                    <option selected disabled value="">Choose...</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>
                <div class="invalid-feedback">
                    Please select a valid gender.
                </div>
            </div>
            <div class="col-md-3" id="race_div">
                <label for="race" class="form-label">Race</label>
                <input type="text" name="race" value="${user.race}"
                       class="form-control" id="race" required
                       pattern="^([^0-9]*)$"
                       title="Race Should Not Contain Numbers">
                <div class="invalid-feedback">Race Should Not Contain Numbers.</div>
            </div>
            <div class="col-md-3" id="religion_div">
                <label for="religion" class="form-label">Religion</label>
                <select name="religion" class="form-select" id="religion" required>
                    <option selected disabled value="">Choose...</option>
                    <option value="Christian">Christian</option>
                    <option value="Muslim">Muslim</option>
                    <option value="Hindu">Hindu</option>
                    <option value="Buddhist">Buddhist</option>
                    <option value="Folk">Folk</option>
                    <option value="Atheist">Atheist</option>
                    <option value="Shinto">Shinto</option>
                    <option value="Taoism">Taoism</option>
                    <option value="Vodou">Vodou</option>
                    <option value="Sikhism">Sikhism</option>
                    <option value="Judaism">Judaism</option>
                    <option value="Spiritism">Spiritism</option>
                    <option value="Shamanism">Shamanism</option>
                    <option value="Caodaism">Caodaism</option>
                    <option value="Confucianism">Confucianism</option>
                    <option value="Bahai">Bahai</option>
                    <option value="Jainism">Jainism</option>
                    <option value="Cheondoism">Cheondoism</option>
                    <option value="Hoahaoism">Hoahaoism</option>
                    <option value="Tenriism">Tenriism</option>
                    <option value="Druze">Druze</option>
                </select>
                <div class="invalid-feedback">
                    Please select a valid religion.
                </div>
            </div>
            <div class="col-md-3" id="education_div">
                <label for="education" class="form-label">Education</label>
                <select name="education" class="form-select" id="education" required>
                    <option selected disabled value="">Choose...</option>
                    <option value="Diploma">High School Diploma</option>
                    <option value="Associate">Associate's Degree</option>
                    <option value="Bachelor">Bachelor's Degree</option>
                    <option value="Master">Master's Degree</option>
                    <option value="Phd">Ph.D</option>
                </select>
                <div class="invalid-feedback">
                    Please select a valid education.
                </div>
            </div>
            <div class="col-md-4" id="income_div">
                <label for="income" class="form-label">Average Income</label>
                <div class="input-group">
                    <span class="input-group-text" id="dollarSign">$</span>
                    <input type="text" name="income" value="${user.income}"
                           class="form-control" id="income"  aria-describedby="inputGroupPrepend2" required
                           pattern="^(?!0*[.,]0*$|[.,]0*$|0*$)\d+[,.]?\d{0,2}$"
                           title="Valid income must be a positive number with a maximum of 2 decimal places">
                    <div class="invalid-feedback">Valid income must be a positive number with a maximum of 2 decimal places</div>
                </div>
            </div>
<%--            <div class="col-md-4">--%>
<%--                <label for="token" class="form-label">Token</label>--%>
<%--                <input type="text" name="token" value="${user.token}"--%>
<%--                       class="form-control" id="token" readonly>--%>
<%--            </div>--%>
            <div class="col-md-3" id="party_div">
                <label for="party" class="form-label">Party</label>
                <select name="sel_party" class="form-select" id="party" required>
                    <c:forEach var="party" items="${listParties}">
                        <option value="${party.id}">${party.name}</option>
                    </c:forEach>
                </select>
                <div class="invalid-feedback">
                    Please select a valid party.
                </div>
            </div>
            <br>
            <button type="submit" class="btn btn-success">Save</button>
                <c:if test="${user != null}">
                    <a href="${pageContext.request.contextPath}/staff/change_user_password?user_id=${user.id}" class="btn btn-success">Change Password</a>
                </c:if>
        </div>
    </div>
</div>
</form>

<script>
(() => {
    const forms = document.querySelectorAll('.needs-validation')
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }

            form.classList.add('was-validated')
        }, false)
    })
})()
<%--    <c:if test="${user != null}">--%>
<%--        sel_party.value = "${user.party.id}";--%>
<%--        role.value = ${user.role};--%>
<%--    </c:if>--%>
<%--    <c:if test="${user != null}">--%>
<%--        role.value = "${user.role}"--%>
<%--        gender.value = "${user.gender}"--%>
<%--        religion.value = "${user.religion}"--%>
<%--        education.value = "${user.education}"--%>
<%--        party.value = "${user.party}"--%>
<%--    </c:if>--%>

<%--    <c:if test="${user.role == 'Public'}">--%>
<%--        party_div.hidden = true;--%>
<%--        party.disabled = true;--%>
<%--        age_div.hidden = false;--%>
<%--        age.disabled = false;--%>
<%--        gender_div.hidden = false;--%>
<%--        gender.disabled = false;--%>
<%--        race_div.hidden = false;--%>
<%--        race.disabled = false;--%>
<%--        religion_div.hidden = false;--%>
<%--        religion.disabled = false;--%>
<%--        education_div.hidden = false;--%>
<%--        education.disabled = false;--%>
<%--        income_div.hidden = false;--%>
<%--        income.disabled = false;--%>
<%--    </c:if>--%>
<%--    <c:if test="${user.role != 'Public'}">--%>
<%--        <c:if test="${user.role == 'Party'}">--%>
<%--            party_div.hidden = false;--%>
<%--            party.disabled = false;--%>
<%--        </c:if>--%>
<%--        <c:if test="${user.role != 'Party'}">--%>
<%--            party_div.hidden = true;--%>
<%--            party.disabled = true;--%>
<%--        </c:if>--%>
<%--        age_div.hidden = true;--%>
<%--        age.disabled = true;--%>
<%--        gender_div.hidden = true;--%>
<%--        gender.disabled = true;--%>
<%--        race_div.hidden = true;--%>
<%--        race.disabled = true;--%>
<%--        religion_div.hidden = true;--%>
<%--        religion.disabled = true;--%>
<%--        education_div.hidden = true;--%>
<%--        education.disabled = true;--%>
<%--        income_div.hidden = true;--%>
<%--        income.disabled = true;--%>
<%--    </c:if>--%>

function roleChanged() {
    if (role.value === "Public") {
        party_div.hidden = true;
        party.disabled = true;
        age_div.hidden = false;
        age.disabled = false;
        gender_div.hidden = false;
        gender.disabled = false;
        race_div.hidden = false;
        race.disabled = false;
        religion_div.hidden = false;
        religion.disabled = false;
        education_div.hidden = false;
        education.disabled = false;
        income_div.hidden = false;
        income.disabled = false;
    }
    else if (role.value !== "Public") {
        if (role.value === "Party") {
            party_div.hidden = false;
            party.disabled = false;
        } else {
            party_div.hidden = true;
            party.disabled = true;
        }
        age_div.hidden = true;
        age.disabled = true;
        gender_div.hidden = true;
        gender.disabled = true;
        race_div.hidden = true;
        race.disabled = true;
        religion_div.hidden = true;
        religion.disabled = true;
        education_div.hidden = true;
        education.disabled = true;
        income_div.hidden = true;
        income.disabled = true;
    }
}

window.onload = function() {
role.value = "${user.role}";
party.value = "${user.party.id}";
gender.value = "${user.gender}";
religion.value = "${user.religion}";
education.value = "${user.education}";
roleChanged();
}

</script>

</body>
</html>
