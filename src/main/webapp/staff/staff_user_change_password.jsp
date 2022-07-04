<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Change User Password</title>
</head>
<body>
<jsp:include page="staff_banner.jsp"></jsp:include>

<c:if test="${error != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="container col-md-10">
    <div class="card">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/staff/change_user_password" method="post">
                <div class="col-md-2">
                    <label for="id" class="form-label">ID</label>
                    <input type="text" name="user_id" value="${user_id}"
                           class="form-control" id="id" readonly>
                </div>
                <div class="col-md-4">
                    <label for="current_password" class="form-label">Current Password</label>
                    <input type="password" name="current_password"
                           class="form-control" id="current_password" required
                           pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                           title="Minimum of 8 characters. At least one uppercase and one number">
                </div>
                <div class="col-md-4">
                    <label for="new_password" class="form-label">New Password</label>
                    <input type="password" name="new_password"
                           class="form-control" id="new_password" required
                           pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                           title="Minimum of 8 characters. At least one uppercase and one number">
                </div>
                <div class="col-md-4">
                    <label for="new_password_val" class="form-label">New Password Validation</label>
                    <input type="password" name="new_pasword_val"
                           class="form-control" id="new_password_val" required
                           pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                           title="Minimum of 8 characters. At least one uppercase and one number">
                </div>
                <br>
                <button type="submit" class="btn btn-success">Confirm Password</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
