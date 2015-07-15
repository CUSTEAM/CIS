<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.text.*"%>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=list4Coansw.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4Coansw.xls");
}
SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
String date=sf.format(new Date());
%>
<html>
<head>
</head>
<body>
<table border="0" align="left" cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td colspan="14" align="left">
		<font face="標楷體" size="+2">${SchoolName_ZH} ${schoolYear}學年 第${schoolTerm}學期 課程學習滿意度調查統計表</font>
		</td>
	</tr>
	<tr>
		<td colspan="14" align="left">
		<font size="-2">課程管理系統 <%=date%></font>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td>
		
		<c:set var="five" value="0"/>
		<c:set var="four" value="0"/>
		<c:set var="three" value="0"/>
		<c:set var="two" value="0"/>
		<c:set var="one" value="0"/>
		
		<c:set var="sumPer" value="0"/>
		<c:set var="sumCou" value="0"/>
		<c:set var="sumSco" value="0"/>
		
		<table align="center" border="1" align="left" cellpadding="0" cellspacing="1" width="100%">
		<tr height="20">
			<td align="center" bgcolor="#f0fcd7">課程編號</td>
			<td align="center" bgcolor="#f0fcd7">教師姓名</td>
			<td align="center" bgcolor="#f0fcd7">班級名稱</td>
			<td align="center" bgcolor="#f0fcd7">班級代碼</td>
			<td align="center" bgcolor="#f0fcd7">課程名稱</td>
			<td align="center" bgcolor="#f0fcd7">課程代碼</td>
			<td align="center" bgcolor="#f0fcd7">樣本數</td>
			<td align="center" bgcolor="#f0fcd7">平均值 </td>
			<td align="center" bgcolor="#f0fcd7">細節 </td>
		</tr>
			<%int i=0;%>
			<c:forEach items="${allCoansw}" var="dl">
			
			<c:if test="${fn:indexOf(dl.chi_name, '論文')<0}">
			
			
			<%i=i+1;%>
		<tr height="20" <%if(i%2==0){%>bgcolor="#f0fcd7"<%}%> >
				<td align="left" style="mso-number-format:\@">${dl.Oid}</td>
					<td align="left" style="mso-number-format:\@">${dl.cname}</td>
					<td align="left" style="mso-number-format:\@">${dl.ClassName}</td>
					<td align="left" style="mso-number-format:\@">${dl.depart_class}</td>
					<td align="left" style="mso-number-format:\@">${dl.chi_name}</td>
					<td align="left" style="mso-number-format:\@">${dl.cscode}</td>
					<td align="left">${dl.sumAns}</td>
					<td align="left">${dl.total}</td>
					
					<c:forEach items="${dl.score}" var="sc">
					<td align="left" style="mso-number-format:\@" nowrap>${sc.options}</td>
					<td align="left">${sc.score}</td>
					</c:forEach>
					
					
					<c:set var="tmp" value="${dl.total}"/>
					<c:set var="sumPer" value="${dl.sumAns+sumPer}"/>
					<c:set var="sumCou" value="${sumCou+1}"/>
					<c:set var="sumSco" value="${dl.total+sumSco}"/>
					
					<c:if test="${tmp>=0&&tmp<70}">
					<c:set var="one" value="${one+1}"/>
					</c:if>
					
					<c:if test="${tmp>=70&&tmp<80}">
					<c:set var="two" value="${two+1}"/>
					</c:if>
					
					<c:if test="${tmp>=80&&tmp<90}">
					<c:set var="three" value="${three+1}"/>
					</c:if>
					
					<c:if test="${tmp>=90}">
					<c:set var="four" value="${four+1}"/>
					</c:if>					
				</tr>
				</c:if>
			</c:forEach>			
				<tr>
					<td></td>
					<td></td>
					<td align="left"><b>加總(總樣本數): </b></td>
					<td align="left"><fmt:formatNumber  value="${sumPer}"  pattern="#,###,###,###.#"/></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td align="left"><b>平均樣本數:</b></td>					
					<td align="left">
					<fmt:formatNumber  value="${sumPer/sumCou}"  pattern="#,###,###,###.#"/>
					</td>
					<td align="left">
					<fmt:formatNumber  value="${sumSco/sumCou}"  pattern="#,###,###,###.#"/>
					</td>
				</tr>
	</table>
		
	<table border="1">
			<tr bgcolor="f0fcd7">
				<td>範圍(平均值)</td>
				<td align="right">課程(門)</td>
			</tr>
			<tr>
				<td>0~69.9</td>
				<td>${one}</td>
			</tr>
			<tr bgcolor="f0fcd7">
				<td>70~79.9</td>
				<td>${two}</td>
			</tr>
			<tr>
				<td>80~89.9</td>
				<td>${three}</td>
			</tr>
			<tr bgcolor="f0fcd7">
				<td>90+</td>
				<td>${four}</td>
			</tr>
			<tr>
				<td>總計</td>
				<td>${five+four+three+two+one}</td>
			</tr>
		</table>		
		
		</td>
	</tr>
</table>
</body>
</html>