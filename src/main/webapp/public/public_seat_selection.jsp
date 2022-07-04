<%@ page import="java.util.Map" %>
<%@ page import="com.spr.votingsystem.model.Seat" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="cf"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Seat Selection</title>
    <script src="${pageContext.request.contextPath}/js/chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/chartjs-plugin-autocolors.js"></script>
</head>
<body>
<jsp:include page="public_banner.jsp"></jsp:include>

<%
    Map<Integer, Map<String, Long>> report = (Map<Integer, Map<String, Long>>) request.getAttribute("voteReport");
%>

<c:forEach var="seat" items="${listSeats}">
    <div class="accordion accordion-flush" id="accordionFlushExample">
        <div class="accordion-item">
            <h2 class="accordion-header" id="flush-headingOne">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
                    Seat ID: ${seat.id}
                </button>
            </h2>
            <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                <div class="accordion-body">
                    <c:if test="${cf:length(seat.contesters) != 0}">
                        <canvas id="chart_${seat.id}" style="width: 300vw; height: 200vh"></canvas>
                        <br>
                        <c:forEach var="contester" items="${seat.contesters}">
                            ${contester.first_name} ${contester.last_name} -
                        </c:forEach>
                        <br>
                        <c:if test="${voteStatus}">
                            <a href="${pageContext.request.contextPath}/public/voting_management/grab_seat_candidates?seat_id=${seat.id}" class="btn btn-primary">Pick Seat</a>
                        </c:if>
                        <c:if test="${error != null}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${cf:length(seat.contesters) == 0}">
                        No Contesters.
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
<script>

    const autocolors = window['chartjs-plugin-autocolors'];

    Chart.register(autocolors);

    <c:forEach var="seat" items="${listSeats}">
    <c:set var="current_seat_id" value="${seat.id}" scope="request" />

    <c:if test="${cf:length(seat.contesters) != 0}">
    new Chart(document.getElementById('chart_${seat.id}').getContext('2d'), {
        type: 'bar',
        data: {
            labels: [
                <c:forEach items="<%= report.get((Integer) request.getAttribute("current_seat_id")).keySet() %>" var="key">
                '<c:out value="${key}" />',
                </c:forEach>
            ],
            datasets: [{
                label: 'Number of Votes By Candidate',
                data: <%= report.get((Integer) request.getAttribute("current_seat_id")).values() %>,
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
    </c:if>
    </c:forEach>
</script>

</body>
</html>
