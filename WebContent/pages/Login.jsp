<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp"%>
<%@ include file="Main/style.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<html:html locale="true">
<head>
	<title><bean:message key="LoginForm.title" /></title>
	<html:base />
	<link href="images/css/<%=home%>.css" type="text/css" rel="stylesheet">
	<link href="images/css/<%=decorate%>.css" type="text/css" rel="stylesheet">
	<link href="images/css/Istyle.css" type="text/css" rel="stylesheet">
	<link href="images/css/global.css" type="text/css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="images/css/login.css">
	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="-1">
	<meta name="ROBOTS" content="none">
	<link rel="shortcut icon" href="images/cust.ico">	
	<link rel="shortcut icon" href="/CIS/pages/images/cust.ico">
</head>
<body>
	<%@ include file="Main/init.jsp"%>

	<table width="100%" height="100%" cellpadding="0" cellspacing="0">
		
		<tr height="75">
			<td colspan="2" align="right" valign="bottom">
				<table width="100%" height="100%" class="loginTitleBg">
					<tr>
						<td align="left" valign="top">
							
							</font>&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td align="right" valign="bottom">
							<font size="1" color="#ffffff"><bean:message key="Login.chit" bundle="IND" /></font>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr height="1">
			<td colspan="2" width="100%"></td>
		</tr>
		<tr height="1">
			<td colspan="2" width="100%" class="fullColorTable"></td>
		</tr>
		
		<tr>			
			<td width="70%" align="center" valign="top">
			</td>
			<td width="30%" valign="top" align="left" style="padding-right:3px;">
				<%@ include file="Login/message.jsp"%>
				<table width="100%" cellpadding="0" cellspacing="0">					
					<tr>
						<td align="left">
							<c:import url="/pages/Login/login.jsp" />
						</td>
					</tr>
					<tr>
						<td align="left">
							<c:import url="/pages/Login/login4Parent.jsp" />
						</td>
					</tr>			
				</table>
			</td>
		</tr>
		<tr height="1">
			<td colspan="2" width="100%" class="fullColorTable"></td>
		</tr>
		<tr height="1">
			<td colspan="2" width="100%"></td>
		</tr>

		<tr height="30">
			<td colspan="2" width="100%" class="loginTitleBg"></td>
		</tr>
	</table>
</body>

</html:html>

<script language="JavaScript" src="include/cookie_functions.js"></script>
<script>
	function checkId4Return() {
		if (window.event.keyCode == 13 && document.forms[0].username.value.length > 0) {
			document.forms[0].password.focus();
		}
		return false;
	};

	function checkPwd4Return() {
		if (window.event.keyCode == 13) {
			checkRememberMe();
		}
	};

	function checkRememberMe() {
		if (document.forms[0].rememberme.checked) {
			setCookie('loginusername', document.forms[0].username.value, 12*60*60*999999);
		} else {
			deleteCookie('loginusername');
		}
		return true;
	};
</script>