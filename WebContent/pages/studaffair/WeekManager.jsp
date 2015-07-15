<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script language="javascript" src="include/util.js"></script>
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
	<tr>
		<td id="ds_calclass"></td>
	</tr>
</table>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/StudAffair/WeekManager" method="post" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/date.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">開學日期設定</font></div>		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">名稱</td>
				<td class="hairLineTdF">代碼</td>
				<td class="hairLineTdF">日期</td>
				<td class="hairLineTdF">修改者</td>
				<td class="hairLineTdF"></td>
			</tr>
			
			<c:forEach items="${weeks}" var="w">
			
			
			<tr>
				<td class="hairLineTdF"><input type="text" name="sname" value="${w.sname}" size="18" disabled/></td>
				<td class="hairLineTdF">
				<input type="text" name="daynite" value="${w.daynite}" size="3" disabled/>
				<input type="hidden" name="daynite" value="${w.daynite}" size="3" /></td>
				<td class="hairLineTdF">
				
				
				
				<input id="wdate${w.daynite}" name="wdate" type="text" size="8" value="${w.wdate}"
				onclick="ds_sh(this);" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				
				</td>
				<td class="hairLineTdF">
				<input type="text" name="lastedit" value="${w.cname}" size="8" disabled />
				
				</td>
				<td class="hairLineTdF">
				<input type="text" name="check" id="check${w.daynite}" />				
				<input type="submit" name="method" 
				OnMouseDown="checkuser('check${w.daynite}');"
				value="<bean:message key='OK'/>" 
				id="Create${w.daynite}" class="gSubmit"
				
				onMouseOver="showHelpMessage('儲存', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
			</tr>
		
			
			</c:forEach>
			
		</table>
		
		</td>
	</tr>
<script>
function checkuser(id){
	document.getElementById(id).value='1';	
}

</script>		
</html:form>
</table>

<%@ include file="/pages/include/MyCalendar.jsp" %>