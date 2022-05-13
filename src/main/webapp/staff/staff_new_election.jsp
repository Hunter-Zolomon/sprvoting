<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Election</title>
</head>
<body>
<jsp:include page="staff_banner.jsp"></jsp:include>

<form>
    <label for="election_date">Election Date:</label>
    <input type="date" id="election_date" name="election_date">
    <br>
    <label for="number_seats">Number of Seats:</label>
    <input type="number" id="number_seats" name="number_seats">
    <br>
    <label for="seat_number">Number of Contenders:</label>
    <select id="seat_number" name="seat_number">
        <option value="test">Test</option>
    </select>
    <input type="number" id="number_contender" name="number_contender">
    <br>
    <input type="submit" value="Create Election">
</form>
</body>
</html>
