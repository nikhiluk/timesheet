<html>
<head>
    <title></title>
</head>
<body>
hello world
<form action="/timesheetApp/add/today" method="post">
    <input type="submit" value="Worked today">
</form>


<form action="/timesheetApp/add/month-till-today" method="post">
    <input type="submit" value="Worked all days this month">
</form>

Today = ${currentDate}
Days Worked This Month = ${daysWorked}

</body>
</html>
