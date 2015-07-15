<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Personnel/ListStudent" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr height="30">
		<td class="fullColorTable" width="100%">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/folder_magnify.gif" id="piftitle"></div>
		<div nowrap style="float:left;"><font class="gray_15">班級查詢</font></div>
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="ListStudents/search.jsp"%>		
		</td>
	</tr>
	
	<c:if test="${!empty classes}">
	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				<img src="images/printer.gif" border="0">
					<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool" style="font-size:18px;">
						<option value="javascript:void(0)">選擇報表</option>
						<option value="/CIS/Print/CountClasses.do?filter=true">現有學生報表(過濾畫面上無學生班級)</option>
						<option value="/CIS/Print/CountClasses.do">所有班級報表(列出所有畫面上班級)</option>
					</select>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="ListStudents/list.jsp"%>		
		</td>
	</tr>
	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				<img src="images/printer.gif" border="0">
					<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool" style="font-size:18px;">
						<option value="javascript:void(0)">選擇報表</option>
						<option value="/CIS/Print/CountClasses.do?filter=true">現有學生報表(過濾畫面上無學生班級)</option>
						<option value="/CIS/Print/CountClasses.do">所有班級報表(列出所有畫面上班級)</option>
					</select>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center" width="100%"></td>
	</tr>
	</c:if>

	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">學生名單</td>
				<td class="hairLineTdF">
				<select name="week" style="font-size:18px;">
					<option value=""></option>
					<option value="1">星期一</option>
					<option value="2">星期二</option>
					<option value="3">星期三</option>
					<option value="4">星期四</option>
					<option value="5">星期五</option>
					<option value="6">星期六</option>
					<option value="7">星期日</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<INPUT type="submit" name="method" id="Print" value="<bean:message key='Print'/>" class="gSubmit" 
				
				onClick="setTimeout('uninit()', Math.floor(Math.random()*(300-1)));" />
				</td>
			</tr>
		</table>		
		</td>
	</tr>
</html:form>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>



</table>

<script>
	function jumpMenu(targ,selObj,restore){
		eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
		eval(targ+".location.target='_blank'");
		if (restore) selObj.selectedIndex=0;
}
</script>