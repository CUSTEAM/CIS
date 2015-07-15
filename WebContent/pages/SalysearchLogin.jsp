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

out.println("<Form name='myForm' Action='http://192.192.230.168/salypay/salysearch.php' method='post'>");
out.println("<Input name='managerid' type='hidden' value='" + username + "'>");
//out.println("<Input name='command' type='submit' value='實驗室管理系統'>");
out.println("</Form>");
%>

</body>
</html>