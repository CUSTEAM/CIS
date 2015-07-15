<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="SysAdmin/SendTestMail" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/note_go.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">寄送郵件</font></div>		
		</td>
	</tr>	
	<tr>
		<td>
		
		<table cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td>		
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" width="30" align="center"><img src="images/icon/note_go.gif"/></td>
						<td class="hairLineTdF" width="30" align="center">主旨</td>
						<td class="hairLineTd"><textarea rows="1" name="subject" cols="35" id="subject"
						onMouseOver="showHelpMessage('郵件上顯示的主旨, 若空白則顯示系統預設主旨', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)">${MailManagerForm.map.subject}</textarea></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" width="30" align="center"><img src="images/email_go.gif"/></td>
						<td class="hairLineTdF" class="hairLineTable" nowrap>預約寄送</td>
						<td class="hairLineTd" valign="baseline" nowrap>
						<input type="text" name="dsplDate" id="myDate" onclick="ds_sh(this), this.value='';"
						onMouseOver="showHelpMessage('寄件日期, 空白則顯示系統日期(建議空白)', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)" size="6" value="${MailManagerForm.map.myDate}"/>
						</td>
					</tr>
				</table>
				
				<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
					<tr>
						<td id="ds_calclass"></td>
					</tr>
				</table>
				
				</td>
			</tr>
			<tr>
				<td>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" width="30" align="center"><img src="images/email_go.gif"/></td>
						<td class="hairLineTdF" class="hairLineTable" nowrap>顯示寄件人</td>
						<td class="hairLineTd" valign="baseline" nowrap>
						<input type="text" size="8" name="dsplName" value="${me.cname}" id="myName" value="${MailManagerForm.map.myName}"
						onMouseOver="showHelpMessage('郵件上顯示的寄件人名稱, 若空白則顯示系統預設名稱', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)"/>
						</td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" width="30" align="center"><img src="images/email_go.gif"/></td>
						<td class="hairLineTdF" class="hairLineTable" nowrap>顯示寄件信箱</td>
						<td class="hairLineTd" valign="baseline" nowrap>
						<input type="text" name="dsplEmail" value="${me.Email}" id="myEmail" value="${MailManagerForm.map.myEmail}"
						onMouseOver="showHelpMessage('郵件上顯示的寄件人郵件地址, 若空白則顯示系統郵件地址', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)"/>
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
		    	<td>
				<FCK:editor instanceName="content" toolbarSet="NoImage" basePath="/pages/include/fckeditor">
					<jsp:attribute name="value">${MailManagerForm.map.content}</jsp:attribute>
					<jsp:body><FCK:config SkinPath="skins/office2003/"/></jsp:body>
				</FCK:editor>
				</td>
			</tr>
			
			<tr height="30">
				<td class="fullColorTable" align="center"></td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable" width="99%">
					<c:forEach items="${units}" var="u">
					<tr>
						<td class="hairLineTdF" width="30" align="center">
						<input type="checkBox" onClick="checkUnit('unit${u.idno}', '${u.idno}')" /><input type="hidden" id="unit${u.idno}" name="unit"/>
						</td>
						<td class="hairLineTdF" align="left">${u.name}</td>
					</tr>
					</c:forEach>
				</table>
				
				</td>
			</tr>
			
			<tr height="30">
				<td class="fullColorTable" align="center">
				
				<INPUT type="submit" name="method" value="<bean:message bundle="PSN" key='SendMail'/>" class="gSubmit">
				<INPUT type="submit" name="method" value="<bean:message bundle="PSN" key='SendCancel'/>" class="gCancel">
				
				</td>
			</tr>
					    
		</table>
		
		</td>
	</tr>
</html:form>
</table>

<script>
function checkUnit(id, value){
	if(document.getElementById(id).value==""){
		document.getElementById(id).value=value;
	}else{
		document.getElementById(id).value="";
	}
}
</script>


<%@ include file="/pages/include/MyCalendar.jsp" %>