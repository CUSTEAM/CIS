<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/ClassManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/neighbourhood.gif">
				</td>
				<td align="left">
				&nbsp;班級管理&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="ClassManager/search.jsp"%>		
		</td>
	</tr>
	
	<c:if test="${!empty classes}">
	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				<img src="images/printer.gif" border="0">
					<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool">
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
		<%@ include file="ClassManager/list.jsp"%>		
		</td>
	</tr>
	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				<img src="images/printer.gif" border="0">
					<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool">
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
		<td class="fullColorTable" align="center" width="100%">

		<INPUT type="submit"
			   name="method"
			   id="Save"
			   onMouseOver="showHelpMessage('儲存以上資料', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Save'/>"
			   class="gSubmit">
		</td>
	</tr>
	</c:if>
</html:form>
</table>

<script>
	function jumpMenu(targ,selObj,restore){
		eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
		eval(targ+".location.target='_blank'");
		if (restore) selObj.selectedIndex=0;
}
</script>