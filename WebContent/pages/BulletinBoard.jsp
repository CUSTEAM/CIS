<%@ page language="java" import="tw.edu.chit.util.Toolket, java.util.*" contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/leaf.gif"></div>
		<div nowrap style="float:left;"><font class="gray_15"><bean:message key="Main.Message.message" bundle="IND"/></font></div>		
		</td>
	</tr>	
	<tr>
		<html:form action="/MessageFeedback.do" enctype="multipart/form-data" method="post">
		<td align="center" valign="top" >
			<table width="98%">
				<tr height="400">
					<td valign="top" width="100%">

				<c:if test="${Credential.employee}">					
				<!-- %@ include file="BulletinBoard/add.jsp" %-->   <!-- Leo_20120213學生以及老師登入出錯，因而註記掉這段 -->
				<!-- %@ include file="BulletinBoard/addDemand.jsp" %-->
				</c:if>	
				<c:forEach items="${MessageList}" var="msg">
				
				<table width="100%">
					<tr>
						<td>
						
						<table class="hairLineTable" width="99%">
						<tr>
						<td class="hairLineTdF">
		 				<table width="100%">
							<tr>
								<td>
								
								<table>
									<tr>
										<td width="1"><img src="images/comment.gif"></td>
										<td>標題</td>
										<td>${msg.title}</td>
									</tr>
								</table>
																	
								<table>
									<tr>
										<td width="1"><img src="images/user.gif"></td>
										
										<td>由 <b>${msg.Sender}</b>發佈, </td>
									
									
									<td> 發佈對象 </td>
									
									
									<c:if test="${msg.receiver!=''&&msg.receiver!=null}">
										<td>${msg.unit}</td>																						
									</c:if>
									<c:if test="${msg.receiver!=''&&msg.receiver!=null}">
										<td>${msg.receiver}</td>																						
									</c:if>
									
									
									
									</tr>
								</table>										
								
								<table>
									<tr>
										<td><img src="images/date.gif"></td>
										<td>發佈期間</td>
										<td>${msg.StartDate}至${msg.DueDate}</td>												
									</tr>
								</table>										
								
								<table width="100%">
									<tr>
										<td>${msg.Content}</td>
									</tr>
								</table>										
								
								</td>
							</tr>									
							
							<tr>
								<td style="padding:20px;">										
								
								<c:if test="${!empty msg.sub_message}">
								<table width="100%" style="cursor:pointer;">											
									<tr>
										<TD width="1" valign="top"><img src="images/icon/icon_info_exclamation.gif"></TD>
										<td onClick="showObj('msg${msg.Oid}');">點擊查看本訊息回覆列表 - 共 <b>${fn:length(msg.sub_message)}</b>篇</td>
									</tr>
								</table>
								</c:if>
								<div id="msg${msg.Oid}" style="display:none;">
								<c:forEach items="${msg.sub_message}" var="s">
								<table width="99%" >											
									<tr>
										<TD width="1" valign="top"><img src="images/user_comment.gif"></TD>												
										<TD valign="top" nowrap width="100%">${s.Sender} 於 ${s.edit_date}回覆:</TD>												
									</tr>
									<tr>
										<TD valign="top" colspan="3" width="100%">${s.Content}</TD>
									</tr>
									<tr>
										<TD valign="top" colspan="3" width="100%"><hr class="myHr"></TD>
									</tr>
								</table>
								</c:forEach>
								</div>
								</td>
							</tr>
						</table>
	
						<table width="100%">
							<TR>
								<TD width="100%" align="right">
								
								<c:if test="${Credential.employee}">
								<table style="cursor:pointer;" onClick="showInfo('sub${msg.Oid}')">
									<tr>
										<td width="1"><img src="images/comments_add.gif"></td>
										<td><font size="-2"><bean:message key="Main.Message.feedback" bundle="IND"/>...</font></td>
									</tr>
								</table>
								</c:if>
																	
								</TD>
							</TR>
							<tr id="sub${msg.Oid}" style="display:none">
								<td>
								
								<table width="100%">
									<tr>
										<td>
										
										<table class="hairLineTable" align="right">
											<tr>	
												<td class="hairLineTd"><input type="hidden" name="Oid" id="Oid${msg.Oid}" value="" />&nbsp;</td>
											</tr>
											<tr>	
												<td class="hairLineTdF">
												<textarea name="message" id="message${msg.Oid}" rows="1" width="100%" 
												onClick="minMax('message${msg.Oid}');document.getElementById('Oid${msg.Oid}').value='${msg.Oid}';"
												ondblclick="MiniOnly('message${msg.Oid}');"></textarea>
												</td>
											</tr>
											<tr>
												<td align="left" class="hairLineTd">
												<INPUT type="submit" name="method" value="<bean:message key='Feedback'/>" class="gSubmit">
												<input type="checkbox" disabled checked>遵守學術網路規範
												</td>
											</tr>
										</table>
										
										</td>
									</tr>
								</table>
								
								</td>
							</tr>
						</table>
	 				
					</td>
					</tr>
					</table>
						</td>
					</tr>
				</table>
			</c:forEach>
			
	  		</td>
	  	</tr>
	  </table>
		</td>
		</html:form>
	</tr>

	<script>generateTableBanner('');</script>
</table>
<script>
function showInfo(id){
	if(document.getElementById(id).style.display=="none"){
		document.getElementById(id).style.display="inline";
	}else{
		document.getElementById(id).style.display="none";
	}
}
</script>

<script>
function minMax(id){
	//if(document.getElementById(id).rows==20){
		//document.getElementById(id).rows=1;
		//document.getElementById(id).cols=14;
	//}else{
		document.getElementById(id).rows=20;
		document.getElementById(id).cols=85;
	//}		
}

function MiniOnly(id){
	document.getElementById(id).rows=1;
	document.getElementById(id).cols=18;
}
</script>

<%@ include file="/pages/include/MyCalendar.jsp" %>