<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/ClinicService/Appointment" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30" valign="">
		<td class="fullColorTable" valign="middle">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/pharmacy.gif"/></div>
		<div nowrap style="float:left; padding:4 0 0 0;" ><font class="gray_15">預約掛號管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<input type="hidden" name="cdate" id="cdate"/>
		<input type="hidden" name="sequence" id="sequence"/>
		<input type="hidden" name="Oid" id="Oid"/>
<script>
function addReg(cdate, sequence){
	document.getElementById("cdate").value=cdate;
	document.getElementById("sequence").value=sequence;
}

function cancelReg(Oid){
	document.getElementById("Oid").value=Oid;
}
</script>
		
		<table width="100%">
			<tr>
				<td width="50%">
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF"><a href="/CIS/Print/publicAccess/AppointmentView.do" target="_blank">產生歷史清單</a></td>
						
					</tr>
				</table>
				</td>
				
			</tr>
		</table>
		
		
		<c:forEach items="${table}" var="t">		
		<table width="100%">
			<tr>
				<td width="50%" valign="top">
				
				<table height="250" width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF" align="center" valign="middle">
						<font color="#666666" size="+4"><b>${t.date4tw}</b></font>						
						</td>
					</tr>
				</table>
				
				</td>
				
				
				
				<td width="50%">
				
				<table height="250" width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF" width="30" align="center">順序</td>
						<td class="hairLineTdF" width="100" align="center">報到時間</td>
						<td class="hairLineTdF" align="center">預約</td>
					</tr>
					<c:forEach items="${t.list}" var="l">
					<tr>
						<td class="hairLineTdF" align="center">${l.sequence}</td>
						<td class="hairLineTdF" align="center">${l.time}</td>
						<td class="hairLineTdF" align="center">
						
						${l.unit} - ${l.name}
						
						
						
						
						</td>
					</tr>
					</c:forEach>
				</table>
				
				</td>
				
				
			</tr>
		</table>
		
		
		
		
		
		
		
		
		</c:forEach>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center"></td>
	</tr>
</html:form>
</table>