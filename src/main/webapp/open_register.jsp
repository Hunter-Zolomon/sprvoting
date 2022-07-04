<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Public Account Registration Page</title>
    <link href="${pageContext.request.contextPath}/styles/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">SPR Voting - Public Registration</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/main">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<c:if test="${error != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<form class="row g-3 needs-validation" novalidate
      action="${pageContext.request.contextPath}/pub_register" method="post">
  <div class="col-md-4">
    <label for="fname" class="form-label">First name</label>
    <input type="text" name="fname" class="form-control" id="fname" required
           pattern="^([^0-9]*)$"
           title="First name should not contain numbers">
      <div class="invalid-feedback">First name should not contain numbers</div>
  </div>
  <div class="col-md-4">
    <label for="lname" class="form-label">Last name</label>
    <input type="text" name="lname" class="form-control" id="lname" required
           pattern="^([^0-9]*)$"
           title="Last name should not contain numbers">
      <div class="invalid-feedback">Last name should not contain numbers</div>
  </div>
  <div class="col-md-4">
    <label for="username" class="form-label">Username</label>
    <div class="input-group">
      <span class="input-group-text" id="atSign">@</span>
      <input type="text" name="username" class="form-control" id="username"  aria-describedby="inputGroupPrepend2" required
             pattern="[A-Za-z0-9_]{1,15}"
             title="Lowercase, Uppercase, and Numbers Allowed. Maximum of 15 characters">
        <div class="invalid-feedback">Lowercase, Uppercase, and Numbers Allowed. Maximum of 15 characters</div>
    </div>
  </div>
  <div class="col-md-4">
     <label for="password" class="form-label">Password</label>
     <input type="password" name="password" class="form-control" id="password" required
              pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
              title="Minimum of 8 characters. At least one uppercase and one number">
      <div class="invalid-feedback">Minimum of 8 characters. At least one uppercase and one number</div>
  </div>
    <div class="col-md-4">
      <label for="ic" class="form-label">IC Number</label>
      <input type="text" name="ic" class="form-control" id="ic"
             pattern="(([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01]))-([0-9]{2})-([0-9]{4})"
             title="IC Number Must Be of Format: xxxxxx-xx-xxx"
             required>
        <div class="invalid-feedback">IC Number Must Be of Format: xxxxxx-xx-xxxx</div>
  </div>
  <div class="col-md-4">
      <label for="email" class="form-label">Email</label>
      <input type="email" name="email" class="form-control" id="email" required
             pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"
             title="Valid email address is of the form: example@example.com">
      <div class="invalid-feedback">Valid email address is of the form: example@example.com</div>
  </div>
  <div class="col-md-12">
      <label for="address" class="form-label">Address</label>
      <input type="text" name="address" class="form-control" id="address" required>
      <div class="invalid-feedback">Please Enter A Valid Address.</div>
  </div>
  <div class="col-md-4">
      <label for="phone_no" class="form-label">Phone number</label>
      <input type="text" name="phone_no" class="form-control" id="phone_no" required
             pattern="^(\+?6?01)[02-46-9]-*[0-9]{7}$|^(\+?6?01)[1]-*[0-9]{8}$"
             title="Valid Malaysian phone number is of the form: +60xxxxxxxxx">
      <div class="invalid-feedback">Valid Malaysian phone number is of the form: +60xxxxxxxxx</div>
  </div>
  <div class="col-md-2">
      <label for="age" class="form-label">Age</label>
      <input type="text" name="age" class="form-control" id="age" required
             pattern="^(1[89]|[2-9]\d)$"
             title="You must be older than 18 and younger than 99 to vote">
      <div class="invalid-feedback">You must be older than 18 and younger than 99 to vote</div>
  </div>
  <div class="col-md-3">
    <label for="gender" class="form-label">Gender</label>
    <select class="form-select" name="gender" id="gender" required>
      <option selected disabled value="">Choose...</option>
      <option value="Male">Male</option>
      <option value="Female">Female</option>
    </select>
    <div class="invalid-feedback">
      Please select a valid gender.
    </div>
  </div>
  <div class="col-md-3">
      <label for="race" class="form-label">Race</label>
      <input type="text" name="race" class="form-control" id="race" required
             pattern="^([^0-9]*)$"
             title="Race Should Not Contain Numbers">
      <div class="invalid-feedback">Race Should Not Contain Numbers.</div>
  </div>
  <div class="col-md-3">
      <label for="religion" class="form-label">Religion</label>
      <select class="form-select" name="religion" id="religion" required>
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
  <div class="col-md-3">
      <label for="education" class="form-label">Education</label>
      <select class="form-select" name="education" id="education" required>
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
  <div class="col-md-4">
      <label for="income" class="form-label">Average Income</label>
      <div class="input-group">
          <span class="input-group-text" id="dollarSign">$</span>
          <input type="text" name="income" class="form-control" id="income"  aria-describedby="inputGroupPrepend2" required
                 pattern="^(?!0*[.,]0*$|[.,]0*$|0*$)\d+[,.]?\d{0,2}$"
                 title="Valid income must be a positive number with a maximum of 2 decimal places">
          <div class="invalid-feedback">Valid income must be a positive number with a maximum of 2 decimal places</div>
      </div>
  </div>
  <div class="col-12">
    <div class="form-check">
      <input class="form-check-input" type="checkbox" value="" id="invalidCheck2" required>
      <label class="form-check-label" for="invalidCheck2">
        Agree to terms and conditions
      </label>
        <div class="invalid-feedback">You Must Agree To Terms And Conditions.</div>
    </div>
  </div>
  <div class="col-12">
    <button class="btn btn-primary" type="submit">Submit</button>
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
</script>

</body>
</html>
