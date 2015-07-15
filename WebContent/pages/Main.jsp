<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/taglibs.jsp"%>
<%@ include file="Main/style.jsp"%>
<html:html locale="true">
<head>
	<html:base />
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="ROBOTS" content="none">
	<title><bean:message key="LoginForm.title" /></title>
	<link rel="shortcut icon" href="images/cust.ico">	
	<link rel="shortcut icon" href="/CIS/pages/images/cust.ico">	
	<link href="images/css/Istyle.css" type="text/css" rel="stylesheet">
	<link href="images/css/home_blue.css" type="text/css" rel="stylesheet">
	<link href="images/css/decorate_blue.css" type="text/css" rel="stylesheet">
	<link href="images/css/global.css" type="text/css" rel="stylesheet">
	<link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
	<!-- script language="javascript" src="include/OnlineMsg.js"></script-->	
</head>
<body>
	<%@ include file="Main/init.jsp"%>	
	<c:if test="${openCoans}"><!-- 學生評量 -->
	<%@ include file="/pages/course/CourseQuestionaryForm.jsp"%>
	</c:if>
	<c:if test="${tqst}"><!-- 教師評量-->
	
	<%@ include file="/pages/teacher/QuestionaryForm.jsp"%>
	</c:if>
	
	
	<!-- c:if test="${chpass}"-->
	<!-- %@ include file="/pages/Main/chpass.jsp"%-->
	<!--/c:if-->
	
	<%@ include file="Login/message.jsp" %>
	<table cellSpacing=0 cellPadding=0 width=100% border=0>
		<tr>
			<td width="100%">
				<c:import url="/pages/header.jsp" />
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			
			<td vAlign="top" width="1%" nowrap>
				<jsp:include page="Menu.jsp" />
			</td>

			
			<td align="left" valign="top" width="100%" style="padding:5px 5px 5px 0px;">				
				
				<table width="100%" cellspacing="0" cellpadding="0" border="0">					
					<tr>
						<td align="left" valign="top" width="100%">							
							<b class="xb1"></b>
							<b class="xb2"></b>
							<b class="xb3"></b>
							<b class="xb4"></b>
							<table width="100%" cellspacing="0" cellpadding="0" border="0">
								<tr>
									<td width="3" class="fullColorTable"></td>
									<td height="100%" class="fullColorTableSub" align="left" valign="top">									
										<!-- Here comes content page -->
										<c:import url="${contentPage}" />										
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
</body>
</html:html>

<script>
setTimeout("alertPageMsg()", 1500000);
function alertPageMsg(msg){
	if(msg!=null){document.getElementById('pagemsgInfo').innerHTML=msg;}
	document.getElementById('pagemsg').style.display="inline";
	scroll(0,0);
	logoutTimer=setTimeout("window.location.href='/CIS/Logout.do'", 150000);
}
</script>