<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Create New Election</title>
</head>
<body>
<jsp:include page="staff_banner.jsp"></jsp:include>

<form class="needs-validation" novalidate
      action="${pageContext.request.contextPath}/staff/election_management/add_election" method="post">
  <div class="mb-3">
    <label for="election_name" class="form-label">Election Name</label>
    <div class="input-group">
      <input type="text" name="election_name" class="form-control" id="election_name"
             aria-describedby="inputGroupPrepend2" required>
      <div class="invalid-feedback">Please enter a valid election name.</div>
    </div>
  </div>
  <div class="mb-3">
    <label for="date" class="form-label">Commencement Date</label>
    <input type="date" name="election_date" class="form-control" id="date" required>
    <div class="invalid-feedback">Please select a valid date, starting today onwards.</div>
  </div>
  <div class="mb-3">
    <label for="number_seats" class="form-label">Number of seats</label>
    <input type="number" name="number_seats" onchange="checkNumber()"
           class="form-control" id="number_seats"
      pattern="^[1-9][0-9]*$"
      title="Valid number of seats must be a positive whole number" required>
    <div class="invalid-feedback">Please enter a valid number of seats. More than or equal to one.</div>
  </div>
    <button type="submit" class="btn btn-primary">Create Election</button>
</form>

<c:if test="${error != null}">
  <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ${error}
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
</c:if>

<script>
  date.min = new Date().toLocaleDateString('en-ca');

    function checkNumber() {
      if (number_seats.value <= 0) {
        number_seats.value = 1;
      }
    }

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
</script>

</body>
</html>
