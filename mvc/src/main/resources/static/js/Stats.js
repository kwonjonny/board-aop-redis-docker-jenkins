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