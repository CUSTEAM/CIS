<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>

<%
  response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-disposition","attachment;filename=Article_StatisticsSel.xls");
%>
<html><head></head>
<body>
<table>
<c:if test="${not empty ArticleList}">
  <tr>
    <td>
	  <table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
		<tr height="20">
		  <td align="center" bgcolor="#f0fcd7">年度</td>
		  <!-- <td align="center" bgcolor="#f0fcd7">校區</td>  -->
		  <td align="center" bgcolor="#f0fcd7">系所名稱</td>
		  <td align="center" bgcolor="#f0fcd7">教師姓名</td>
		  <td align="center" bgcolor="#f0fcd7">學術活動</td>
		  <td align="center" bgcolor="#f0fcd7">計畫與產學</td>
		  <td align="center" bgcolor="#f0fcd7">期刊論文</td>
		  <td align="center" bgcolor="#f0fcd7">研討會論文</td>
		  <td align="center" bgcolor="#f0fcd7">專書(篇章)</td>
		  <td align="center" bgcolor="#f0fcd7">專利/新品種</td>
		  <td align="center" bgcolor="#f0fcd7">獲獎與榮譽</td>
		</tr>
		<%int i=2;%>
		<c:forEach items="${ArticleList}" var="RL">		
		<%i=i+1;%>
		<tr height="20">
		  <td align="left" align="center" style="mso-number-format:\@" bgcolor="#ffffff" >${Year}</td>
		  <!-- <td align="left" align="center" style="mso-number-format:\@" bgcolor="#ffffff">${RL.Campus}</td> -->
		  <td align="left" align="center" style="mso-number-format:\@" bgcolor="#ffffff">${RL.Tch_Unit}</td>
		  <td align="left" align="center" style="mso-number-format:\@" bgcolor="#ffffff">${RL.TCH_Cname}</td>
		  <td align="left" align="center" bgcolor="#ffffff">${RL.Rcact}</td>
		  <td align="left" align="center" bgcolor="#ffffff">${RL.Rcporj}</td>
		  <td align="left" align="center" bgcolor="#ffffff">${RL.Rcjour}</td>
		  <td align="left" align="center" bgcolor="#ffffff">${RL.Rcconf}</td>
		  <td align="left" align="center" bgcolor="#ffffff">${RL.Rcbook}</td>
		  <td align="left" align="center" bgcolor="#ffffff">${RL.Rcpet}</td>
		  <td align="left" align="center" bgcolor="#ffffff">${RL.Rchono}</td>
		</tr>
	    </c:forEach>
	    <c:forEach items="${total}" var="Ta">	
	    <tr>
	      <td align="center" bgcolor="#f0fcd7" colspan="4">合計</td>
	      <td align="center" bgcolor="#f0fcd7">${Ta.Ra }</td>
	      <td align="center" bgcolor="#f0fcd7">${Ta.Rp }</td>
	      <td align="center" bgcolor="#f0fcd7">${Ta.Rj }</td>
	      <td align="center" bgcolor="#f0fcd7">${Ta.Rc }</td>
	      <td align="center" bgcolor="#f0fcd7">${Ta.Rb }</td>
	      <td align="center" bgcolor="#f0fcd7">${Ta.Re }</td>
	      <td align="center" bgcolor="#f0fcd7">${Ta.Rh }</td>	      
	    </tr>
	    </c:forEach>
	  </table>
    </td>
  </tr>
</c:if>
</table>
</body></html>