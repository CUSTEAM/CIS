<%@ page language="java" contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ include file="taglibs.jsp" %>  
<html:html locale="true">
<HEAD>
	<title></title>
	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="-1">
	<meta name="ROBOTS" content="none">
	<link href="pages/images/css/Istyle.css" type="text/css" rel="stylesheet">
	<link href="pages/images/css/home_blue.css" type="text/css" rel="stylesheet">
	<link href="pages/images/css/decorate_blue.css" type="text/css" rel="stylesheet">
	<link href="pages/images/css/global.css" type="text/css" rel="stylesheet">
	<link href="pages/score/chit.css" type="text/css" rel="stylesheet" media="screen">
	<!-- script language="javascript" src="include/OnlineMsg.js"></script-->
  </head>
  
  <body>
	
	
	<%@ include file="/pages/Login/message.jsp" %>
	<table cellSpacing=0 cellPadding=0 width=100% border=0>
		<tr>
			<td width="100%">
				<c:import url="/pages/header.jsp" />
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			
			<td vAlign="top" align="right" width="18%" nowrap>
				<jsp:include page="/pages/Menu.jsp" />
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
										
										
										
										111
												
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
				
				
				
				
				<c:import url="/pages/floored.jsp" />
			</td>
		</tr>
	</table>	
</body>
</html:html>