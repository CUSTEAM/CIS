<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>

<%
  response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-disposition","attachment;filename=1-7 Rcact.xls");
%>
<html><head></head>
<body>
<table>
<c:if test="${not empty RcactList}">
  <tr>
    <td>
	  <table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
		<tr height="20">
		  <td align="center" bgcolor="#f0fcd7">年度</td>
		  <td align="center" bgcolor="#f0fcd7">系所名稱</td>
		  <td align="center" bgcolor="#f0fcd7">教師姓名</td>
		  <td align="center" bgcolor="#f0fcd7">主辦單位</td>
		  <td align="center" bgcolor="#f0fcd7">活動名稱</td>
		  <td align="center" bgcolor="#f0fcd7">活動種類</td>
		  <td align="center" bgcolor="#f0fcd7">活動類型</td>
		  <td align="center" bgcolor="#f0fcd7">活動地點</td>
		  <td align="center" bgcolor="#f0fcd7">參與情形</td>
		  <td align="center" bgcolor="#f0fcd7">開始日期</td>
		  <td align="center" bgcolor="#f0fcd7">結束日期</td>
		  <td align="center" bgcolor="#f0fcd7">時數</td>
		  <td align="center" bgcolor="#f0fcd7">研習證明</td>
		  <td align="center" bgcolor="#f0fcd7">證書字號</td>
		  <td align="center" bgcolor="#f0fcd7">學校補助情形</td>
		  <td align="center" bgcolor="#f0fcd7">審核狀態</td>
		</tr>
		<%int i=0;%>
		<c:forEach items="${RcactList}" var="RL">		
		<%i=i+1;%>
		<tr height="20">
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.school_year}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.Uname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.idno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.actname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.sponoff}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.kindid}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.typeid}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.placeid}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.joinid}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.bdate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.edate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.hour}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.certyn}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.certno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.schspon}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.approve}</td>
		</tr>
	    </c:forEach>
	  </table>
    </td>
  </tr>
</c:if>
</table>
</body></html>