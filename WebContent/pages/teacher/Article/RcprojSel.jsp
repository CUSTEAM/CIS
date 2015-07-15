<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>

<%
  response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-disposition","attachment;filename=1-8 Rcproj.xls");
%>
<html><head></head>
<body>
<table>
<c:if test="${not empty RcprojList}">
  <tr>
    <td>
	  <table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
		<tr height="20">
		  <td align="center" bgcolor="#f0fcd7">年度</td>
		  <td align="center" bgcolor="#f0fcd7">系所</td>
		  <td align="center" bgcolor="#f0fcd7">教師</td>
		  <td align="center" bgcolor="#f0fcd7">案號</td>
		  <td align="center" bgcolor="#f0fcd7">案名</td>
		  <td align="center" bgcolor="#f0fcd7">類型</td>
		  <td align="center" bgcolor="#f0fcd7">執行起始日期</td>
		  <td align="center" bgcolor="#f0fcd7">執行結束日期</td>
		  <td align="center" bgcolor="#f0fcd7">工作類別</td>
		  <td align="center" bgcolor="#f0fcd7">經費狀況</td>
		  <td align="center" bgcolor="#f0fcd7">金額(元)</td>
		  <td align="center" bgcolor="#f0fcd7">政府出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">企業出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">其他單位出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">學校出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">主要經費來源</td>
		  <td align="center" bgcolor="#f0fcd7">單位名稱</td>
		  <td align="center" bgcolor="#f0fcd7">次要經費來源</td>
		  <td align="center" bgcolor="#f0fcd7">受惠機構名稱</td>
		  <td align="center" bgcolor="#f0fcd7">委託單位(國內)</td>
		  <td align="center" bgcolor="#f0fcd7">委託單位(國外)</td>
		  <td align="center" bgcolor="#f0fcd7">合作單位(國內)</td>
		  <td align="center" bgcolor="#f0fcd7">合作單位(國外)</td>
		  <!-- 
		  <td align="center" bgcolor="#f0fcd7">專案聘任之專任人員</td>
		  <td align="center" bgcolor="#f0fcd7">專案聘任之兼職人員</td>
		  <td align="center" bgcolor="#f0fcd7">國科會、教育部或是政府委訓計畫受訓人次</td>
		  <td align="center" bgcolor="#f0fcd7">企業委訓計畫受訓人數</td>
		  <td align="center" bgcolor="#f0fcd7">其他單位委訓計畫受訓人次</td>
		  -->
		  <td align="center" bgcolor="#f0fcd7">他校轉入的專案</td>
		  <td align="center" bgcolor="#f0fcd7">他校轉入的政府出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">他校轉入的企業出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">他校轉入的其他單位出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">專案已轉至他校</td>
		  <td align="center" bgcolor="#f0fcd7">已轉至他校的政府出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">已轉至他校的企業出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">已轉至他校的其他單位出資金額</td>
		  <td align="center" bgcolor="#f0fcd7">審核狀態</td>
		</tr>
		<%int i=0;%>
		<c:forEach items="${RcprojList}" var="RL">		
		<%i=i+1;%>		
		<tr height="20">
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.school_year}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.Uname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.idno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.projno}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.projname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.kindid}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.bdate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.edate}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.jobid}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.budgetidState}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.money}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.G_money}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.B_money}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.O_money}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.S_money}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.budgetid1}</td>		  
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.unitname}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%> >${RL.budgetid2}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.favorunit}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.authorunit1}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.authorunit2}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.coopunit1}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.coopunit2}</td>
		  <!--  
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.FullTime}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.PartTime}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.G_trainee}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.B_trainee}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.O_trainee}</td>
		  -->
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.turnIn}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.turnIn_G}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.turnIn_B}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.turnIn_O}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.turnOut}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.turnOut_G}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.turnOut_B}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${RL.turnOut_O}</td>
		  <td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${RL.approve}</td>
		</tr>
	    </c:forEach>
	  </table>
    </td>
  </tr>
</c:if>
</table>
</body></html>