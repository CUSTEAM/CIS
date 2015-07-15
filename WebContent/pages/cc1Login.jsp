<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>InformixLogin</title>
</head>
<body onLoad="document.myForm.submit()">
<%
String username = request.getParameter("username");
String password = request.getParameter("password");
String url = request.getParameter("linkurl");

out.println("<Form name='myForm' Action='http://cc1.cust.edu.tw/cgi-bin/" + url + ".cgi' method='post'>");
out.println("<Input name='username' type='hidden' value='" + username + "'>");
out.println("<Input name='password' type='hidden' value='" + password + "'>");
out.println("</Form>");
%>

</body>
</html>