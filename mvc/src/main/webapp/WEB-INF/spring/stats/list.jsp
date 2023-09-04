<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="kr">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Stats List Page</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<h3>월별 일별 통계</h3>
<div class="card">
  <div class="bg-light rounded h-100">
    <div style="display: flex;">
      <div id="curve_chart_member" style="width: 50%; height: 600px"></div>
      <div id="column_chart_month_member" style="width: 50%; height: 600px;"></div>
    </div>
    <div style="display: flex;">
        <div id="curve_chart_board" style="width: 50%; height: 600px"></div>
        <div id="colum_chart_month_board" style="width: 50%; height: 600px;"></div>
      </div>
      <div style="display: flex;">
        <div id="curve_chart_views" style="width: 50%; height: 600px"></div>
        <div id="colum_chart_month_views" style="width: 50%; height: 600px;"></div>
      </div>
      <div style="display: flex;">
        <div id="curve_chart_like" style="width: 50%; height: 600px"></div>
        <div id="colum_chart_month_like" style="width: 50%; height: 600px"></div>
      </div>
      <div style="display: flex;">
        <div id="curve_chart_reply" style="width: 50%; height: 600px"></div>
        <div id="colum_chart_month_reply" style="width: 50%; height: 600px"></div>
      </div>
  </div>
</div>
<%@ include file="../include/footer.jsp" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script>
    // Google Chart Day Member 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartDayMember);

    function drawChartDayMember() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '날짜');
    data.addColumn('number', '회원수');
    <c:forEach var="entry" items="${memberDayStats}">
    data.addRow(['${entry.createDate}', ${entry.memberDayEntry}]);
    </c:forEach>
    var options = {
        title: '일별 회원가입 통계',
        curveType:'function',
        legend: {position: 'botton'},
        colors: ["#ae2d23"]
    }
    var chart = new google.visualization.LineChart(document.getElementById('curve_chart_member'));
    chart.draw(data, options);
    }
    // Google Chart Month Member 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartMonthMember);

    function drawChartMonthMember() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '월');
    data.addColumn('number', '회원수');
    <c:forEach var="entry" items="${memberMonthStats}">
    data.addRow(['${entry.createDate}', ${entry.memberMonthEntry}]);
    </c:forEach>

    var options = {
        title: '월별 회원가입 통계',
        bars: 'vertical', // 막대 방향 (가로: 'horizontal', 세로: 'vertical')
        legend: {position: 'bottom'},
        colors: ["#ae2d33"]
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('column_chart_month_member'));
    chart.draw(data, options);
    }
    // Google Chart Day Board 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartDayBoard);

    function drawChartDayBoard() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '날짜');
    data.addColumn('number', '게시물 수');
    <c:forEach var="entry" items="${boardDayStats}">
    data.addRow(['${entry.createDate}', ${entry.boardDayEntry}]);
    </c:forEach>
    var options = {
        title: '일별 게시물 생성 통계',
        curveType:'function',
        legend: {position: 'botton'},
        colors: ["#ae2d23"]
    }
    var chart = new google.visualization.LineChart(document.getElementById('curve_chart_board'));
    chart.draw(data, options);
    }
    // Google Chart Month Board 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(darwChartMonthBoard);

    function darwChartMonthBoard() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '월');
    data.addColumn('number', '게시물 수');
    <c:forEach var="entry" items="${boardMonthStats}">
    data.addRow(['${entry.createDate}', ${entry.boardMonthEntry}]);
    </c:forEach>

    var options = {
    title: '월별 게시물 생성 통계',
    bars: 'vertical', // 막대 방향 (가로: 'horizontal', 세로: 'vertical')
    legend: {position: 'bottom'},
    colors: ["#ae2d33"]
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('colum_chart_month_board'));
    chart.draw(data, options);
    }
    // Google Chart Day Views 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartDayViews);

    function drawChartDayViews() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '날짜');
    data.addColumn('number', '조회수');
    <c:forEach var="entry" items="${viewsDayStats}">
    data.addRow(['${entry.viewDayEntry}', ${entry.viewCount}]);
    </c:forEach>
    var options = {
    title: '일별 조회수 통계',
    curveType:'function',
    legend: {position: 'botton'},
    colors: ["#ae2d23"]
    }
    var chart = new google.visualization.LineChart(document.getElementById('curve_chart_views'));
    chart.draw(data, options);
    }
    // Google Chart Month Views 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartMonthViews);

    function drawChartMonthViews() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '월');
    data.addColumn('number', '조회수');
    <c:forEach var="entry" items="${viewsMonthStats}">
    data.addRow(['${entry.viewMonthEntry}', ${entry.viewCount}]);
    </c:forEach>

    var options = {
    title: '월별 조회수 통계',
    bars: 'vertical', // 막대 방향 (가로: 'horizontal', 세로: 'vertical')
    legend: {position: 'bottom'},
    colors: ["#ae2d33"]
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('colum_chart_month_views'));
    chart.draw(data, options);
    }
    // Google Chart Day Like 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartDayLike);

    function drawChartDayLike() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '날짜');
    data.addColumn('number', '좋아요');
    <c:forEach var="entry" items="${likeDayStats}">
    data.addRow(['${entry.likeEntryDay}', ${entry.likeCount}]);
    </c:forEach>
    var options = {
    title: '일별 좋아요 통계',
    curveType:'function',
    legend: {position: 'botton'},
    colors: ["#ae2d23"]
    }
    var chart = new google.visualization.LineChart(document.getElementById('curve_chart_like'));
    chart.draw(data, options);
    }
    // Google Chart Month Like 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartMonthLike);

    function drawChartMonthLike() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '월');
    data.addColumn('number', '좋아요');
    <c:forEach var="entry" items="${likeMonthStats}">
    data.addRow(['${entry.likeEntryMonth}', ${entry.likeCount}]);
    </c:forEach>

    var options = {
    title: '월별 좋아요 통계',
    bars: 'vertical', // 막대 방향 (가로: 'horizontal', 세로: 'vertical')
    legend: {position: 'bottom'},
    colors: ["#ae2d33"]
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('colum_chart_month_like'));
    chart.draw(data, options);
    }
    // Google Chart Day Reply 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartDayReply);

    function drawChartDayReply() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '날짜');
    data.addColumn('number', '댓글수');
    <c:forEach var="entry" items="${replyDayStats}">
    data.addRow(['${entry.replyDayEntry}', ${entry.replyCount}]);
    </c:forEach>
    var options = {
    title: '일별 댓글수 통계',
    curveType:'function',
    legend: {position: 'botton'},
    colors: ["#ae2d23"]
    }
    var chart = new google.visualization.LineChart(document.getElementById('curve_chart_reply'));
    chart.draw(data, options);
    }
    // Google Chart Month Like 
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartMonthReply);

    function drawChartMonthReply() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', '월');
    data.addColumn('number', '댓글수');
    <c:forEach var="entry" items="${replyMonthStats}">
    data.addRow(['${entry.replyMonthEntry}', ${entry.replyCount}]);
    </c:forEach>

    var options = {
    title: '월별 댓글수 통계',
    bars: 'vertical', // 막대 방향 (가로: 'horizontal', 세로: 'vertical')
    legend: {position: 'bottom'},
    colors: ["#ae2d33"]
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('colum_chart_month_reply'));
    chart.draw(data, options);
    }
</script>

</body>
</html>