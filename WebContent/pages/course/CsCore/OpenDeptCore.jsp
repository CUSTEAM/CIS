<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/taglibs.jsp"%>
<%@ include file="/pages/Main/style.jsp"%>
<html:html locale="true">
<head>
	<html:base />
	<title><bean:message key="LoginForm.title" />
	</title>
	<link href="/CIS/pages/images/css/Istyle.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/home_orange.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/decorate_orange.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/global.css" type="text/css" rel="stylesheet">

	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="-1">
	<meta name="ROBOTS" content="none">
</head>


<body>
	<!-- 預載項目 -->
	<%@ include file="/pages/Main/init.jsp"%>

	

	<table cellSpacing=0 cellPadding=0 width=100% border=0>
	
		<tr height="1">
			<td colspan="3" width="100%"></td>
		</tr>
		<tr height="1">
			<td colspan="3" width="100%" class="fullColorTable"></td>
		</tr>
		<tr height="20">
			<td></td>
		</tr>
	</table>

	<table cellSpacing=0 cellPadding=0 width=98% border=0>
		<tr height="100%">

			<td id="nouseinfo" width="2%">
			</td>

			

			<td align="left" valign="top" width="100%">

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
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										<script src="/CIS/pages/include/decorate.js"></script>
										<table cellspacing="0" cellpadding="0" width="100%">
										<html:form action="/Course/CsCoreSystem/OpenStudentsCore" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
										<!-- 標題start -->
											<tr>
												<td class="fullColorTable">
												
												<table width="100%" cellpadding="0" cellspacing="0" border="0">
													<tr height="30">
														<td width="28" align="right">
														<img src="/CIS/pages/images/chart_line.gif">
														</td>
														<td align="left">
														&nbsp;各系核心能力指標查詢&nbsp;
														</td>
													</tr>
												</table>
												
												</td>
											</tr>
											<tr>
												<td width="100%">
												<%@ include file="DeptCore/search.jsp"%>		
												</td>
											</tr>
											
											<tr>
												<td id="help" style="display:none;">		
												<%@ include file="DeptCore/help.jsp"%>	
												</td>
											</tr>
											
											<tr>
												<td class="fullColorTable" align="center">		
												<input type="submit" name="method" class="gSubmit" value="<bean:message key='Query'/>" id="Query"
															onMouseOver="showHelpMessage('查詢相關核心課程', 'inline', this.id)" 
															onMouseOut="showHelpMessage('', 'none', this.id)"/>					
												<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
															onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
															onMouseOut="showHelpMessage('', 'none', this.id)"/>		
												</td>
											</tr>
											
											<c:if test="${!empty dcores}">
											<tr>
												<td width="100%">		
												<%@ include file="DeptCore/list.jsp"%>												
												</td>
											</tr>
											<tr height="30">
												<td class="fullColorTable" align="center">
												
												</td>
											</tr>
											</c:if>
											
											<c:if test="${empty dcores && DeptName!=null}">
											<tr>
												<td>
												<table class="hairLineTable" width="99%">
													<tr>
														<td class="hairLineTdF" width="100%">${DeptName}尚未建立任何核心課程</td>
													</tr>
												</table>
												</td>
											</tr>
											<tr height="30">
												<td class="fullColorTable" align="center">
												
												</td>
											</tr>	
											</c:if>
											
											
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
				<c:import url="/pages/floored.jsp" />
			</td>
		</tr>
	</table>
</body>
</html:html>



