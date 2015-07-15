<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Portfolio/TemplateManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr height="30">
					<td width="28" align="right">
						<img src="images/folder_lock.gif">
					</td>
					<td align="left">
						&nbsp;模板管理&nbsp;
					</td>
					<td>
						<c:if test="${!notExist}">
						<table align="right">
							<tr>							
								<td width="1"><img src="images/icon/zoomout.gif"></td>
								<td><a href="${myUrl}" target="_blank">立即檢視</a></td>
							</tr>
						</table>
						</c:if>
						
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="TemplateManager/select.jsp" %>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="TemplateManager/edit.jsp" %>
		<br>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		
		</td>
	</tr>

	
</html:form>
</table>
<script>
function checkView(id){
	//document.getElementById("basic").style.display="none";
	document.getElementById("selectTemplate").style.display="none";
	document.getElementById("editTemplate").style.display="none";
	
	document.getElementById(id).style.display="inline";
}
</script>