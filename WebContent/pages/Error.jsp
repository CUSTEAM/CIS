<%@ page language="java" contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@page import="java.io.PrintWriter"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/taglibs.jsp"%>
<%@ include file="Main/style.jsp"%>
<html:html locale="true">
<head>
	<html:base />
	<title><bean:message key="LoginForm.title" /></title>
	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="-1">
	<meta name="ROBOTS" content="none">
	<link href="images/css/Istyle.css" type="text/css" rel="stylesheet">
	<link href="images/css/<%=home%>.css" type="text/css" rel="stylesheet">
	<link href="images/css/<%=decorate%>.css" type="text/css" rel="stylesheet">
	<link href="images/css/global.css" type="text/css" rel="stylesheet">
	<link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
	<!-- script language="javascript" src="include/OnlineMsg.js"></script-->
</head>


<body>
	<%@ include file="Main/init.jsp"%>	
	
	
	
	
	
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
			
			<td vAlign="top" align="right" width="18%" nowrap>
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
										
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<html:form action="/Student/MyCsTable" method="post" onsubmit="init('處理中, 請稍後')">
										<tr height="30">
											<td class="fullColorTable">		
											<div style="float:left; padding:0 5 0 5;"><img src="images/icon/exclamation.gif"/></div>
											<div nowrap style="float:left;"><font class="gray_15">Exception StackTrace</font></div>		
											</td>
										</tr>
										<tr>
											<td>
											<table width="99%" class="hairLineTable">
												<tr>
													<td class="hairLineTdF">
													<table>
														<tr>
															<td>
															<img src="images/Cat.gif" />															
															</td>
															<td width="100%" nowrap>															
															<font size="+3"><b>執行發生錯誤</b></font>
															</td>
															<td nowrap>
															<input type="button" class="gSubmit" value="報告追蹤" onClick="alert('已記錄')"/>													
															<input type="button" class="gCancel" onClick="javascript:history.back()" value="返回" />
															
															</td>
														</tr>
													</table>
													
													
													
													</td>
												</tr>
												<tr>
													<td class="hairLineTdF">
													
													<%if(exception!=null)exception.printStackTrace(new PrintWriter(out)); %>
													</td>
												</tr>
											</table>
											
											</td>
										</tr>

 									
										<tr height="30">
											<td class="fullColorTable">
										</td>
										
										</html:form>
										</table>
										
										
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