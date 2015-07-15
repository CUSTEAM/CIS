<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="Regstration/Recruit/ScheduleManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題start -->	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/cal.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">招生工作日程表資料管理</font></div>		
		</td>
	</tr>	
<c:if test="${aSchedule==null}">
	<tr>
		<td>
		
		<%@ include file="ScheduleManager/search.jsp"%>
		
		</td>
	<tr>
		<td id="help" style="display:none;">		
		<%@ include file="ScheduleManager/help.jsp"%>
		</td>
	</tr>
	</tr>
	<tr class="fullColorTr">
		<td align="center">
		
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td>
				
				<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Save" class="gSubmit"
					onMouseOver="showHelpMessage('以上資料為查詢條件', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
				<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="Create" class="gGreen"
					onMouseOver="showHelpMessage('以上資料為新增條件', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">				
				
								
				<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
				
				<!--td>
				<table class="hairLineTable">
					<tr>						
						<td class="hairLineTdF">
							<input type="hidden" name="checkMail" id="check" value="" />
							<input type="checkbox" onClick="mailSwitch()" />	
						</td>
						<td class="hairLineTdF">&nbsp新增時以郵件通知&nbsp</td>
					</tr>
				</table>
				</td-->
				
				
			</tr>
		</table>		
		
		</td>
	</tr>
</c:if>	
	
<c:if test="${!empty schedules && aSchedule==null}">	
	<tr>
		<td><%@ include file="ScheduleManager/list.jsp"%></td>
	</tr>
	<tr class="fullColorTr" height="30">
		<td align="center">
		
		</td>
	</tr>
</c:if>

<c:if test="${aSchedule!=null}">
	<tr>
		<td><%@ include file="ScheduleManager/edit.jsp"%></td>
	</tr>
	<tr class="fullColorTr" height="30">
		<td align="center">		
		<table cellspacing="0" border="0">
			<tr>
				<td>
				<input type="submit" name="method" 
					value="<bean:message key='Save'/>" 
					id="Save" class="gSubmit"
					onMouseOver="showHelpMessage('儲存以上資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
				<td>
				<input type="button" class="gCancle" value="返回" id="back" onclick="location='/CIS/Regstration/Recruit/ScheduleManager.do';"
					onMouseOver="showHelpMessage('返回功能列表', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
				<td>
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF" width="30" align="center">
						<img src="images/16-icon_calendar-point.gif"/>
						</td>			
						<td class="hairLineTdF">						
						<select name="checkMail">
							<option value="0">不通知任何人</option>
							<option value="1">通知所有人</option>
							<option value="2">只通知招生委員</option>
							<option value="3">只通知參與者</option>
						</select>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>					
		</td>
	</tr>
</c:if>
<script>
function mailSwitch(){
	
	if(document.getElementById("check").value==""){
		document.getElementById("check").value="*";
	}else{
		document.getElementById("check").value="";
	}
}
</script>	
</html:form>
</table>
<%@ include file="/pages/include/MyCalendarAD.jsp" %>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>