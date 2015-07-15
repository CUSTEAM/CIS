<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>

<%
  response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-disposition","attachment;filename=1-13 Rchono.xls");
%>
<html><head></head>
<body>
<table>
<c:if test="${not empty RchonoList}">
  <tr>
    <td>
	  <table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
		<tr height="20">
		  <td align="center" bgcolor="#f0fcd7">年度</td>
		  <td align="center" bgcolor="#f0fcd7">系所</td>
		  <td align="center" bgcolor="#f0fcd7">教師</td>
		  <td align="center" bgcolor="#f0fcd7">獲獎名稱</td>
		  <td align="center" bgcolor="#f0fcd7">國別</td>
		  <td align="center" bgcolor="#f0fcd7">頒獎機構名稱</td>
		  <td align="center" bgcolor="#f0fcd7">作者順序</td>
		  <td align="center" bgcolor="#f0fcd7">獲獎日期</td>
		  <td align="center" bgcolor="#f0fcd7">所屬計畫案</td>
		  <td align="center" bgcolor="#f0fcd7">審核狀態</td>
		</tr>
		<%int i=0;%>
		<c:forEach items="${RchonoList}" var="RL">		
		<%i=i+1;%>
		<tr height="20">
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.school_year}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.Uname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.idno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.title}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.nation}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.inst}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.authorno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.bdate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.projno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.approve}</td>
		</tr>
	    </c:forEach>
	  </table>
    </td>
  </tr>
</c:if>
</table>
</body></html>