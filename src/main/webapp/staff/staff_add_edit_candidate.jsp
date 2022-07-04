<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Candidate Add/Edit</title>
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

<c:if test="${candidate != null}">
<form class="needs-validation" novalidate
      action="${pageContext.request.contextPath}/staff/candidate_management/update_candidate" method="post">
    </c:if>
    <c:if test="${candidate == null}">
    <form class="needs-validation" novalidate
          action="${pageContext.request.contextPath}/staff/candidate_management/add_candidate" method="post">
        </c:if>
        <div class="container col-md-10">
            <div class="card">
                <div class="card-body">
                    <caption>
                        <c:if test="${candidate != null}">
                            <h2>Edit Candidate</h2>
                        </c:if>
                        <c:if test="${candidate == null}">
                            <h2>Add New Candidate</h2>
                        </c:if>
                    </caption>
                    <c:if test="${candidate != null}">
                    <div class="col-md-2">
                        <label for="candidate_id" class="form-label">ID</label>
                        <input type="text" name="candidate_id" value="${candidate.id}"
                               class="form-control" id="candidate_id" readonly>
                    </div>
                    </c:if>
                    <div class="col-md-4">
                        <label for="candidate_fname" class="form-label">First name</label>
                        <input type="text" name="candidate_fname" value="${candidate.first_name}"
                               class="form-control" id="candidate_fname" required
                               pattern="^([^0-9]*)$"
                               title="First name should not contain numbers">
                        <div class="invalid-feedback">First name should not contain numbers.</div>
                    </div>
                    <div class="col-md-4">
                        <label for="candidate_lname" class="form-label">Last name</label>
                        <input type="text" name="candidate_lname" value="${candidate.last_name}"
                               class="form-control" id="candidate_lname" required
                               pattern="^([^0-9]*)$"
                               title="Last name should not contain numbers">
                        <div class="invalid-feedback">Last name should not contain numbers.</div>
                    </div>
                    <div class="col-md-4">
                        <label for="candidate_quals" class="form-label">Qualifications</label>
                        <input type="text" name="candidate_quals" value="${candidate.qualifications}"
                               class="form-control" id="candidate_quals" required>
                        <div class="invalid-feedback">Please enter valid qualifications.</div>
                    </div>
                    <div class="col-md-3">
                        <label for="party" class="form-label">Party</label>
                        <select name="party_id" class="form-select" id="party" required>
                            <c:forEach var="party" items="${listParties}">
                                <option value="${party.id}">${party.name}</option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback">Please select a valid party.</div>
                    </div>
                    <c:if test="${candidate != null}">
                        <div class="col-md-3">
                            <label for="seats" class="form-label">Seats</label>
                            <select name="seat_id" class="form-select" id="seats">
                                <c:forEach var="seat" items="${candidate.seats}">
                                    <option value="${seat.id}">${seat.id}</option>
                                </c:forEach>
                            </select>
                            <a class="btn btn-danger"
                               href="/staff/candidate_management/delete_candidate_from_seat">Delete</a>
                        </div>
                    </c:if>
                    <c:if test="${candidate != null}">
                        <div class="col-md-3">
                            <label for="elections" class="form-label">Elections</label>
                            <select name="election_id" class="form-select" id="elections">
                                <c:forEach var="election" items="${candidate.elections}">
                                    <option value="${election.id}">${election.name}</option>
                                </c:forEach>
                            </select>
                            <a class="btn btn-danger"
                               href="/staff/candidate_management/delete_candidate_from_election">Delete</a>
                        </div>
                    </c:if>
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

<c:if test="${candidate != null}">
    party.value = "${candidate.party.id}";
</c:if>
</script>
</body>
</html>
