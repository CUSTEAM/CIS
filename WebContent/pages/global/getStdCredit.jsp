<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/taglibs.jsp"%>
<%@ include file="../Main/style.jsp"%>
<html:html locale="true">
<head>
	<html:base />
	<title><bean:message key="LoginForm.title" /></title>
	<link href="/CIS/pages/images/css/Istyle.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/decorate_blue.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/home_blue.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/global.css" type="text/css" rel="stylesheet">
	
	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="-1">
	<meta name="ROBOTS" content="none">
</head>

<body>
	<table class="hairLineTable" width="98%">
		<tr>
			<td class="hairLineTdF">請以您登記入學的學年度比對下列資料, <br><font size=-1>(資料僅提供參考, 實際狀況以權責單位最終公佈為標準)</font></td>
		</tr>
	</table>
	<table class="hairLineTable" width="98%">
		<tr>
			<td class="hairLineTdF">${DeptName.school_name}${DeptName.fname}</td>
		</tr>
	</table>
	
	<table class="hairLineTable" width="98%">
		<tr>
			<td class="hairLineTdF">入學期間</td>
			<td class="hairLineTdF">必修應修</td>
			<td class="hairLineTdF">選修應修</td>
			<td class="hairLineTdF">通識應修</td>
		</tr>
		
		<c:forEach items="${StmdGrdeCredit}" var="s">
		<tr>
			<td class="hairLineTdF">${s.start_year}學年 至 ${s.end_year}學年</td>
			<td class="hairLineTdF">${s.opt1}學分</td>
			<td class="hairLineTdF">${s.opt2}學分</td>
			<td class="hairLineTdF">${s.opt3}學分</td>
		</tr>

		</c:forEach>
	</table>







</body>
</html:html>
