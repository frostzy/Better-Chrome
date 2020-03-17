<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>newsSearch</title>
</head>
<img src="https://i.imgur.com/Z6dCOJa.png" alt="logo" width="600" height="300" /><br />
<h1><strong><font face="微軟正黑體">按照網頁出現關鍵字次數由高至低做排列</font></strong></h1>
<body>
<form action='${requestUri}' method='get'>
<%
String[][] orderList = (String[][])  request.getAttribute("query");
for(int i =0 ; i < orderList.length;i++){%>
	<a href='<%= orderList[i][1] %>'><%= orderList[i][0] %></a><br><p><font size ="2"   color="#008000"><%= orderList[i][1] %></font></p><br><br>
<%
}
%>
<input type='submit' value='Back' />
</form>
</body>
</html>