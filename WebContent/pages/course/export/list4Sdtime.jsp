<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=list4Dtime.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4Dtime.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty SdtimeList}">
	<tr>
		<td>
			<table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">

				<tr height="20" bgcolor="#f0fcd7">
				<td align="center">梯次</td>
					<td align="center">班級名稱</td>
					<td align="center">班級代碼</td>
					<td align="center">科目名稱</td>
					<td align="center">科目代碼</td>
					<td align="center">教師姓名</td>
					<td align="center">選別</td>
					<td align="center">學分</td>
					<td align="center">時數</td>
					<td align="center">人數</td>
					<td align="center">週一</td>
					<td align="center">週二</td>
					<td align="center">週三</td>
					<td align="center">週四</td>
					<td align="center">週五</td>
					<td align="center">週六</td>
					<td align="center">周日</td>
					<td align="center">上課地點</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${SdtimeList}" var="dl">
					<%i=i+1;%>
					<%if(i%2==1){%>
						<tr height="20" bgcolor="#ffffff">
					<%}else{%>
						<tr height="20" bgcolor="#f0fcd7">					 
					<%}%>
						
							<td align="left">${dl.seqno}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.name}</td>
							<td align="left">${dl.depart_class}</td>
							<td align="left">${dl.chi_name}</td>
							<td align="left">${dl.cscode}</td>
							<td align="center">${dl.cname}</td>
							<td align="center">${dl.opt}</td>
							<td align="center">${dl.credit}</td>
							<td align="center">${dl.thour}</td>
							<td align="center">${dl.stu_select}</td>
							<td align="center">${dl.day1}</td>
							<td align="center">${dl.day2}</td>
							<td align="center">${dl.day3}</td>
							<td align="center">${dl.day4}</td>
							<td align="center">${dl.day5}</td>
							<td align="center">${dl.day6}</td>
							<td align="center">${dl.day7}</td>
							<td align="center">${dl.clascode}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>