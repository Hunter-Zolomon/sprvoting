<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="cf"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Election Reports</title>
    <script src="${pageContext.request.contextPath}/js/chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/chartjs-plugin-autocolors.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
</head>
<body>
<jsp:include page="staff_banner.jsp"></jsp:include>

<div class="row row-cols-1 row-cols-md-2 g-4">
    <button class="btn btn-primary" onclick="onReportDownload()" >Download All Reports As PDF</button>
    <div class="col">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Voter Age</h5>
            <h6 class="card-subtitle mb-2 text-muted">DESCRIPTION</h6>
              <canvas id="chartAge" style="width: 100%"></canvas>
          </div>
        </div>
    </div>
    <div class="col">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Voter Gender</h5>
                <h6 class="card-subtitle mb-2 text-muted">DESCRIPTION</h6>
                <canvas id="chartGender" style="width: 100%"></canvas>
            </div>
        </div>
    </div>
    <div class="col">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Voter Race</h5>
                <h6 class="card-subtitle mb-2 text-muted">DESCRIPTION</h6>
                <canvas id="chartRace" style="width: 100%"></canvas>
            </div>
        </div>
    </div>
    <div class="col">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Voter Religion</h5>
                <h6 class="card-subtitle mb-2 text-muted">DESCRIPTION</h6>
                <canvas id="chartReligion" style="width: 100%"></canvas>
            </div>
        </div>
    </div>
    <div class="col">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Voter Education</h5>
                <h6 class="card-subtitle mb-2 text-muted">DESCRIPTION</h6>
                <canvas id="chartEducation" style="width: 100%"></canvas>
            </div>
        </div>
    </div>
    <div class="col">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Voter Income</h5>
                <h6 class="card-subtitle mb-2 text-muted">DESCRIPTION</h6>
                <canvas id="chartIncome" style="width: 100%"></canvas>
            </div>
        </div>
    </div>
</div>


<script>

const autocolors = window['chartjs-plugin-autocolors'];
window.jsPDF = window.jspdf.jsPDF;

// Chart.register(autocolors);

function getRandomColor(num) { //generates random colours and puts them in string
    const colors = [];
    for (let i = 0; i < num; i++) {
        const letters = '0123456789ABCDEF'.split('');
        let color = '#';
        for (let x = 0; x < 6; x++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        colors.push(color);
    }
    return colors;
}

const AgeChart = document.getElementById('chartAge').getContext('2d');
const GenderChart = document.getElementById('chartGender').getContext('2d');
const RaceChart = document.getElementById('chartRace').getContext('2d');
const ReligionChart = document.getElementById('chartReligion').getContext('2d');
const EducationChart = document.getElementById('chartEducation').getContext('2d');
const IncomeChart = document.getElementById('chartIncome').getContext('2d');

const ageData = {
    labels: ['18-28', '29-39', '40-50', '51-61', '62-72', '73-83', '84-94', '95-99'],
    datasets: [{
        label: 'Number of Votes By Age Group',
        data: ${ageData},
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)',
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
        ],
        borderWidth: 1
    }]
};

const AgeChartObj = new Chart(AgeChart, {
    type: 'bar',
    data: ageData,
    options: {
        // plugins: {
        //   autocolors: {
        //     mode: 'data'
        //   }
        // },
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

const genderData = {
    labels: [
        'Male',
        'Female'
    ],
    datasets: [{
        label: 'Number of Voters By Gender',
        data: ${genderData},
        backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(255, 205, 86)'
        ],
        hoverOffset: 4
    }]
};

const GenderChartObj = new Chart(GenderChart, {
    type: 'pie',
    data: genderData,
    options: {
        // plugins: {
        //     autocolors: {
        //         mode: 'data'
        //     }
        // }
    }
})

const raceData = {
    labels: [
        <c:forEach items="${raceDataKeys}" var="key">
        '<c:out value="${key}" />',
        </c:forEach>
    ],
    datasets: [{
        label: 'Votes By Race',
        data: ${raceDataValues},
        backgroundColor: getRandomColor(${cf:length(raceDataValues)}),
        borderWidth: 1
    }]
};

const RaceChartObj = new Chart(RaceChart, {
    type: 'bar',
    data: raceData,
    options: {
        // plugins: {
        //     autocolors: {
        //         mode: 'data'
        //     }
        // },
        scales: {
            y: {
                beginAtZero: true
            }
        }
    },
});

const religiondata = {
    labels: [
        <c:forEach items="${religionDataKeys}" var="key">
        '<c:out value="${key}" />',
        </c:forEach>
    ],
    datasets: [{
        label: 'Votes By Religion',
        data: ${religionDataValues},
        backgroundColor: getRandomColor(${cf:length(religionDataValues)}),
    }]
};

const ReligionChartObj = new Chart(ReligionChart, {
    type: 'polarArea',
    data: religiondata,
    options: {
        // plugins: {
        //     autocolors: {
        //         mode: 'data'
        //     }
        // }
    }
});

const educationData = {
    labels: [
        'High School Diploma',
        'Associate Degree',
        'Bachelor Degree',
        'Master Degree',
        'Ph.D'
    ],
    datasets: [{
        label: 'Female',
        data: ${educationDataValuesF},
        fill: true,
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgb(255, 99, 132)',
        pointBackgroundColor: 'rgb(255, 99, 132)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgb(255, 99, 132)'
    }, {
        label: 'Male',
        data: ${educationDataValuesM},
        fill: true,
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgb(54, 162, 235)',
        pointBackgroundColor: 'rgb(54, 162, 235)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgb(54, 162, 235)'
    }]
};

const EducationChartObj = new Chart(EducationChart, {
    type: 'radar',
    data: educationData,
    options: {
        // plugins: {
        //     autocolors: {
        //         mode: 'data'
        //     }
        // },
        elements: {
            line: {
                borderWidth: 3
            }
        }
    },
});

const incomeData = {
    labels: ["<10K", "10K-20K", "20K-30K", "30K-40K", "40K-50K", "50K-60K",
        "60K-70K", "70K-80K", "80K-90K", "90K-100K", "100K-110K", "110K-120K", ">120K"],
    datasets: [{
        label: 'Number of Voters By Income',
        data: ${incomeData},
        fill: false,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
    }]
};

const IncomeChartObj = new Chart(IncomeChart, {
    type: 'line',
    data: incomeData,
    options: {
        // plugins: {
        //     autocolors: {
        //         mode: 'data'
        //     }
        // }
    }
});

function onReportDownload() {
    var index = 1;
    var pdf = new jsPDF({
        orientation: 'landscape',
        unit: 'px',
        format: 'a4',
        compress: true,
    });
    var canvas = document.querySelectorAll("canvas");
    var pageWidth = 600;
    var pageHeight = 400;
    var index = 1;
    canvas.forEach(function (canva) {
        pdf.addImage(canva, 'PNG', 10, 10, pageWidth, pageHeight, "img".concat(index), "FAST");
        if (index < canvas.length) {
            pdf.addPage();
        }
        index++;
    });
    pdf.save('Election_${election_id}_Report.pdf');
}

</script>

</body>
</html>
