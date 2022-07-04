<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="cf"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Seat Management</title>
</head>
<body>
<jsp:include page="staff_banner.jsp"></jsp:include>

<form action="${pageContext.request.contextPath}/staff/seat_management/update_seats"
      method="post" class="row g-3">
    <select name="seat" class="form-select" id="seat" required onchange="selectionChanged()">
        <c:forEach var="seat" items="${election.seats}">
            <option>${seat.id}</option>
        </c:forEach>
    </select>
    <div class="col-auto">
    <label for="num_contesters" class="visually-hidden">Num Contesters</label>
    <input type="number" name="num_contesters" onchange="numberChanged()" value=""
           class="form-control" id="num_contesters" placeholder="Num Contesters">
    <input type="hidden" name="election_id" value="${election.id}">
  </div>
  <div class="col-auto">
    <input type="submit" class="btn btn-primary mb-3" value="Confirm Modification">
  </div>
</form>

<script>
    function numberChanged() {
        if (num_contesters.value < 2) {
            num_contesters.value = 2;
        }
    }
</script>

</body>
</html>
