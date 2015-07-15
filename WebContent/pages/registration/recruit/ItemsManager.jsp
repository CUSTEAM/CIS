<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="/Regstration/Recruit/Config/ItemsManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題start -->	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_side_boxes.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">活動項目管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF" width="50">代碼</td>
				<td class="hairLineTdF">名稱</td>
			</tr>
			<tr>
				<td class="hairLineTdF" width="50"><input type="text" name="id" size="3"/></td>
				<td class="hairLineTdF"><input type="text" name="name" style="width:100%"/></td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">		
		<%@ include file="ItemsManager/help.jsp"%>
		</td>
	</tr>
	<tr class="fullColorTr">
		<td align="center">		
					
		<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="Create" class="gGreen"
					onMouseOver="showHelpMessage('以上資料為新增條件', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="button" class="gCancle" value="返回" id="back" onclick="location='/CIS/Recruit/RecruitDirectory.do';"
					onMouseOver="showHelpMessage('返回功能列表', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF" width="50">代碼</td>
				<td class="hairLineTdF">名稱</td>
			</tr>
			
			
			
			
			<c:forEach items="${items}" var="i">
			<tr>
				<td class="hairLineTdF">
				<input type="hidden" name="id" value="${i.id}"/>
				<input type="text" disabled value="${i.id}" size="3"/>
				</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" name="name" value="${i.name}" style="width:100%"/>
				</td>
			</tr>
			</c:forEach>
			
			
			
		</table>
		
		</td>
	</tr>
	<tr height="30" class="fullColorTr">
		<td align="center">		
					
		<input type="submit" name="method" 
					value="<bean:message key='Save'/>" 
					id="Save" class="gSubmit"
					onMouseOver="showHelpMessage('以上資料為新增條件', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
	
</html:form>
</table>