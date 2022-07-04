<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add/Edit Party</title>
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

<c:if test="${party != null}">
<form class="needs-validation" novalidate
      action="${pageContext.request.contextPath}/staff/party_management/update_party" method="post">
    </c:if>
    <c:if test="${party == null}">
    <form clasee="needs-validation" novalidate
          action="${pageContext.request.contextPath}/staff/party_management/add_party" method="post">
        </c:if>
        <div class="container col-md-10">
            <div class="card">
                <div class="card-body">
                    <caption>
                        <c:if test="${party != null}">
                            <h2>Edit Party</h2>
                        </c:if>
                        <c:if test="${party == null}">
                            <h2>Add New Party</h2>
                        </c:if>
                    </caption>
                    <c:if test="${party != null}">
                        <div class="col-md-2">
                            <label for="id" class="form-label">ID</label>
                            <input type="text" name="party_id" value="${party.id}"
                                   class="form-control" id="id" readonly>
                        </div>
                    </c:if>
                    <c:if test="${party != null}">
                        <div class="col-md-2">
                            <label for="party_code" class="form-label">Code</label>
                            <input type="text" name="party_code" value="${party.code}"
                                   class="form-control" id="party_code" readonly>
                        </div>
                    </c:if>
                    <div class="col-md-4">
                        <label for="party_name" class="form-label">Name</label>
                        <input type="text" name="party_name" value="${party.name}"
                               class="form-control" id="party_name" required
                               pattern="^([^0-9]*)$"
                               title="Name should not contain numbers">
                        <div class="invalid-feedback">Please enter a valid name. Name should not contain numbers.</div>
                    </div>
                    <div class="mb-3">
                      <label for="party_description" class="form-label">Description</label>
                      <textarea class="form-control" id="party_description"
                                name="party_description" rows="3" required></textarea>
                        <div class="invalid-feedback">Please enter a valid description for the party.</div>
                    </div>
                    <br>
                    <button type="submit" class="btn btn-success">Save</button>
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
window.onload = function () {
    party_description.value = "${party.description}";
}
</script>

</body>
</html>
