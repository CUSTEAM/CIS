<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-disposition","attachment;filename=list4Dtime.xls");
%>
<!-- 
<table>
	<tr>
		<td width="50%">
-->		
<table>
	<tr>
		<td>
		
		<table>
		<c:if test="${not empty fail}">
			<tr>
				<td>
					<table border="1" align="left" cellpadding="0" cellspacing="1" width="100%">
		
						<tr height="20" bgcolor="#f0fcd7">
						<td align="center">課程代碼</td>
						<td align="center">科系</td>
							<td align="center">課程名稱</td>
							<td align="center">選別</td>
							<td align="center">時數</td>
							<td align="center">學分</td>
							<td align="center">二年內被當人數</td>
						</tr>
							<%int i=0;%>
							<c:forEach items="${fail}" var="dl">
							<%i=i+1;%>
							<%if(i%2==1){%>
								<tr height="20" bgcolor="#ffffff">
							<%}else{%>
								<tr height="20" bgcolor="#f0fcd7">					 
							<%}%>
								
									<td align="left">${dl.cscode}</td>
									<td align="left" style="mso-number-format:\@">${dl.DeptNo}</td>
									<td align="left" style="mso-number-format:\@">${dl.chi_name}</td>
									<td align="left" style="mso-number-format:\@">${dl.opt}</td>
									<td align="left" style="mso-number-format:\@">${dl.thour}</td>
									<td align="left" style="mso-number-format:\@">${dl.credit}</td>
									<td align="center">${dl.sumSt}</td>
								</tr>
							</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		</table>
		
		
<!-- 
		</td>
		
		<td width="50%">
		
		<table align="left">
		<c:if test="${not empty retry}">
			<tr>
				<td>
					<table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
		
						<tr height="20" bgcolor="#f0fcd7">
						<td align="center">課程代碼</td>
						<td align="center">科系</td>
							<td align="center">課程名稱</td>
							<td align="center">學分</td>
							<td align="center">時數</td>
							<td align="center">四年內暑修取回學分人數</td>
						</tr>
							<%int i=0;%>
							<c:forEach items="${retry}" var="dl">
							<%i=i+1;%>
							<%if(i%2==1){%>
								<tr height="20" bgcolor="#ffffff">
							<%}else{%>
								<tr height="20" bgcolor="#f0fcd7">					 
							<%}%>
								
									<td align="left">${dl.cscode}</td>
									<td align="left" style="mso-number-format:\@">${dl.ClassName}</td>
									<td align="left" style="mso-number-format:\@">${dl.chi_name}</td>
									<td align="left" style="mso-number-format:\@">${dl.credit}</td>
									<td align="left" style="mso-number-format:\@">${dl.opt}</td>
									<td align="center">${dl.sumSt}</td>
								</tr>
							</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		</table>
-->		
		</td>
	</tr>
</table>