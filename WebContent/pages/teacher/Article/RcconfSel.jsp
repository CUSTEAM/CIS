<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>

<%
  response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-disposition","attachment;filename=1-10 Rcconf.xls");
%>
<html><head></head>
<body>
<table>
<c:if test="${not empty RcconfList}">
  <tr>
    <td>
	  <table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
		<tr height="20">
		  <td align="center" bgcolor="#f0fcd7">年度</td>
		  <td align="center" bgcolor="#f0fcd7">系所</td>
		  <td align="center" bgcolor="#f0fcd7">教師</td>
		  <td align="center" bgcolor="#f0fcd7">論文名稱</td>		  
		  <td align="center" bgcolor="#f0fcd7">作者順序</td>
		  <td align="center" bgcolor="#f0fcd7">通訊作者</td>
		  <td align="center" bgcolor="#f0fcd7">研討會名稱</td>
		  <td align="center" bgcolor="#f0fcd7">舉行之國家</td>
		  <td align="center" bgcolor="#f0fcd7">舉行之城市</td>
		  <td align="center" bgcolor="#f0fcd7">開始日期</td>
		  <td align="center" bgcolor="#f0fcd7">結束日期</td>
		  <td align="center" bgcolor="#f0fcd7">發表年份</td>
		  <td align="center" bgcolor="#f0fcd7">所屬計畫案</td>
		  <td align="center" bgcolor="#f0fcd7">審核狀態</td>
		</tr>
		<%int i=0;%>
		<c:forEach items="${RcconfList}" var="RL">		
		<%i=i+1;%>		
		<tr height="20">
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.school_year}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.Uname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.idno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.title}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.authorno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.COM_authorno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.jname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.nation}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.city}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.bdate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.edate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.pyear}</td>
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