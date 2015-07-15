<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>

<%
  response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-disposition","attachment;filename=1-12 Rcpet.xls");
%>
<html><head></head>
<body>
<table>
<c:if test="${not empty RcpetList}">
  <tr>
    <td>
	  <table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
		<tr height="20">
		  <td align="center" bgcolor="#f0fcd7">年度</td>
		  <td align="center" bgcolor="#f0fcd7">系所</td>
		  <td align="center" bgcolor="#f0fcd7">教師</td>
		  <td align="center" bgcolor="#f0fcd7">專利/新品種名稱</td>
		  <td align="center" bgcolor="#f0fcd7">國別</td>
		  <td align="center" bgcolor="#f0fcd7">類型</td>
		  <td align="center" bgcolor="#f0fcd7">技術報告分數</td>
		  <td align="center" bgcolor="#f0fcd7">進度狀況</td>
		  <td align="center" bgcolor="#f0fcd7">作者順序</td>
		  <td align="center" bgcolor="#f0fcd7">申請人/權利人</td>
		  <td align="center" bgcolor="#f0fcd7">申請人/權利人類型</td>
		  <td align="center" bgcolor="#f0fcd7">申請日期/生效日期</td>
		  <td align="center" bgcolor="#f0fcd7">終止日期</td>
		  <td align="center" bgcolor="#f0fcd7">發照機關</td>
		  <td align="center" bgcolor="#f0fcd7">證書字號</td>
		  <td align="center" bgcolor="#f0fcd7">技術轉移或授權</td>
		  <td align="center" bgcolor="#f0fcd7">授權廠商名稱</td>
		  <td align="center" bgcolor="#f0fcd7">技術轉移或授權金額</td>
		  <td align="center" bgcolor="#f0fcd7">技術轉移或授權起始日期</td>
		  <td align="center" bgcolor="#f0fcd7">技術轉移或授權終止日期</td>
		  <td align="center" bgcolor="#f0fcd7">所屬計畫案</td>
		  <td align="center" bgcolor="#f0fcd7">審核狀態</td>
		</tr>
		<%int i=0;%>
		<c:forEach items="${RcpetList}" var="RL">		
		<%i=i+1;%>
		<tr height="20">
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.school_year}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.Uname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.idno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.title}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.area}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.petType}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.score}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.schedule}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.authorno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.proposer}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.proposerType}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.bdate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.edate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.inst}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.certno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.depute}</td>		  
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.deputeBusiness}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.deputeMoney}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.deputeSdate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.deputeEdate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.projno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.approve}</td>
		</tr>
	    </c:forEach>
	  </table>
    </td>
  </tr>
</c:if>
</table>
</body></html>