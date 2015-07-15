<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=AssessPaperReport.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=AssessPaperReport.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty assessReport}">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				<c:if test="${assessReportType == '1'}">
				<tr><td colspan="15" align="center">
				<font style="font-size:20px; font-weight:500;">中華科技大學&nbsp;&nbsp;&nbsp;行政人員服務滿意度調查結果統計表</font><br/>
				日期：${assessReportsdate} ～  ${assessReportedate}
				</td></tr>
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">單位</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">份數</td>
					<td align="center" bgcolor="#f0fcd7">總分</td>
					<td align="center" bgcolor="#f0fcd7">平均</td>
					<td align="center" bgcolor="#f0fcd7" colspan="2">5分</td>
					<td align="center" bgcolor="#f0fcd7" colspan="2">4分</td>
					<td align="center" bgcolor="#f0fcd7" colspan="2">3分</td>
					<td align="center" bgcolor="#f0fcd7" colspan="2">2分</td>
					<td align="center" bgcolor="#f0fcd7" colspan="2">1分</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${assessReport}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.unitName}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.cname}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.replied}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.total}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.average}</td>
							<td align="right" colspan="2" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.scores[4]}</td>
							<td align="right" colspan="2" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.scores[3]}</td>
							<td align="right" colspan="2" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.scores[2]}</td>
							<td align="right" colspan="2" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.scores[1]}</td>
							<td align="right" colspan="2" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.scores[0]}</td>
						</tr>
					</c:forEach>
						<%i=i+1;%>
						<tr height="20">
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >總人數</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>實際受測</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>總份數</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>總和</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>平均值</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>教職</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>學生</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>教職</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>學生</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>教職</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>學生</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>教職</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>學生</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>教職</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>學生</td>
						</tr>
						<%i=i+1;%>
						<tr height="20">
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${assessReportSum.totalEmp}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.replied}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.totalScrs}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.totalSum}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.avgSum}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[4][0]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[4][1]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[3][0]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[3][1]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[2][0]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[2][1]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[1][0]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[1][1]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[0][0]}</td>
							<td align="right" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${assessReportSum.scoreRepli[0][1]}</td>
						</tr>
				</c:if>
				
				<c:if test="${assessReportType == '2'}">
				<tr><td colspan="9" align="center">
				<font style="font-size:20px; font-weight:500;">中華科技大學&nbsp;&nbsp;&nbsp;行政人員服務滿意度調查優劣報表</font><br/>
				日期：${assessReportsdate} ～  ${assessReportedate}
				</td></tr>
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">服務編號</td>
					<td align="center" bgcolor="#f0fcd7">單位</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">分數</td>
					<td align="center" bgcolor="#f0fcd7">回覆者</td>
					<td align="center" bgcolor="#f0fcd7">服務日期</td>
					<td align="center" bgcolor="#f0fcd7">洽辦事項</td>
					<td align="center" bgcolor="#f0fcd7">具體事實</td>
					<td align="center" bgcolor="#f0fcd7">建議事項</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${assessReport}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.serviceNo}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.attUnitName}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.attCname}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.score}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.reporterKind}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.simpleDate}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.srvEvent}</td>
							<td align="left" style="word-wrap: break-word;" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.description}</td>
							<td align="left" style="word-wrap: break-word;" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.suggestion}</td>
						</tr>
					</c:forEach>
				</c:if>
				
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>