<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/taglibs.jsp"%>
<%@ include file="Main/style.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<html:html locale="true">
<head>
	<html:base />
	<title><bean:message key="LoginForm.title" />
	</title>
	<link href="images/css/Istyle.css" type="text/css" rel="stylesheet">
	<link href="images/css/<%=home%>.css" type="text/css" rel="stylesheet">
	<link href="images/css/<%=decorate%>.css" type="text/css" rel="stylesheet">
	<link href="images/css/global.css" type="text/css" rel="stylesheet">
	<link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">

	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="-1">
	<meta name="ROBOTS" content="none">
</head>

<body>
	
	<%@ include file="Main/init.jsp"%>
	<center>
	
	<table width="910" cellspacing="0" cellpadding="0">
		<tr height="100%">
			<!-- 主要區域 -->
			<td align="left" valign="top" width="100%" style="padding:10px 0px 0px 0px;">
							
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td style="padding:5px 0px 5px 0px;"><%@ include file="Login/message.jsp" %></td>
					</tr>
					<tr>
						<td align="left" valign="top" width="100%">							
							<b class="xb1"></b>
							<b class="xb2"></b>
							<b class="xb3"></b>
							<b class="xb4"></b>
							<table width="100%" cellspacing="0" cellpadding="0" border="0">
								<tr height="400">
									<td width="3" class="fullColorTable"></td>
									<td height="100%" class="fullColorTableSub"  align="left" valign="top">									
									<!-- Here comes content page -->
									<%@ include file="Freshman/Main.jsp"%>
									
									</td>

									<td width="3" class="fullColorTable"></td>
								</tr>
							</table>							
							<b class="xb4"></b>
							<b class="xb3"></b>
							<b class="xb2"></b>
							<b class="xb1"></b>
						</td>
					</tr>
				</table>
				<c:import url="floored.jsp" />
			</td>
		</tr>
	</table>
	</center>
	
	
</body>
</html:html>



