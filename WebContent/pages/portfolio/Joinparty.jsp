<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Portfolio/Joinparty" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">

	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr height="30">
					<td width="28" align="right">
						<img src="images/folder_lock.gif">
					</td>
					<td nowrap>&nbsp;競賽報名</td>
					<td>
						<table align="right">
							<tr>							
								<td width="1"><img src="images/icon/zoomout.gif"></td>
								<td><a href="${myUrl}" target="_blank">立即檢視</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="300">
		<td align="center" style="FONT-SIZE: 20px;">
		
		
		
		
		<table><tr><td>
		<div class="out narrow">		
		<table class="in2 ltin tpin" width="400" cellspacing="0" cellpadding="0">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td class="fullColorTable" style="padding:2px; FONT-SIZE: 20px;">目前開放報名的活動</td>
			</tr>
			<tr>	
				
				<td class="hairLineTdF">
				<c:if test="${empty contest}">目前沒有活動可報名</c:if>
				<c:if test="${!empty contest}">
				<select name="addOid" class="CourseButton">
				<c:forEach items="${contest}" var="c">
					<option value="${c.Oid}">${c.name} - ${c.sign_start}~${c.sign_end}</option>
				</c:forEach>
				</select>
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="hairLineTdF">
				<INPUT type="submit"name="method"value="<bean:message key='JoinParty'/>"class="gSubmit"
				id="OK">
				<input type="checkbox" checked disabled/>已閱讀並同意遵守所有規定
				</td>
			</tr>
		</table>
		</div>
		</div>		
		</td></tr></table>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		<c:if test="${!empty myParty}">
		<table><tr><td>
		<div class="out narrow">		
		<table class="in2 ltin tpin" width="400" cellspacing="0" cellpadding="0">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td class="fullColorTable" style="padding:2px; FONT-SIZE: 20px;">我已報名參加的活動</td>
			</tr>
			<tr>	
				
				<td class="hairLineTdF">
				<c:if test="${empty myParty}">目前沒有活動可報名</c:if>
				
				<table>
					
					<c:forEach items="${myParty}" var="p">
					<tr>
						<td><input type="checkbox" onClick="checkDel('${p.Oid}')"><input type="hidden" id="delOid${p.Oid}" name="delOid"></td>
						
						<td>${p.name}</td>
					</tr>
				</c:forEach>
				</table>
<script>
function checkDel(id){
	if(document.getElementById("delOid"+id).value==""){
		document.getElementById("delOid"+id).value=id;
	}else{
		document.getElementById("delOid"+id).value="";
	}
}
</script>
				
				</td>
			</tr>
			<tr>
				<td class="hairLineTdF">
				<INPUT type="submit"name="method"value="<bean:message key='LeaveParty'/>"class="gCancel"
				id="OK">
				<input type="checkbox" checked disabled/>確定放棄勾選的競賽(評分記錄一併清除)
				</td>
			</tr>
		</table>
		</div>
		</div>		
		</td></tr></table>
		</c:if>
		
		
		
		
		
		
		
		
		
		
		</td>
	</tr>
	<tr class="fullColorTr" height="30">
		<td>
		
		</td>
	</tr>	
</html:form>
</table>
