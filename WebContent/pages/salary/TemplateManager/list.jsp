<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellpadding="0" cellspacing="0" border="0">	
	<tr>
		<td>
		
		<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<display:table name="${templates}" id="row" sort="list" class="list">
		<display:column title="<script>generateTriggerAll(${fn:length(templates)}, 'tmps'); </script>" class="center" >
         		<script>generateCheckbox("${row.idno}", "tmps")</script>
         	</display:column>
         	<display:column title="序號" property="sno" sortable="true" class="center" />
         	<display:column title="身份" property="category" sortable="true" class="center" />
         	<display:column title="姓名" property="cname" sortable="true" class="center" />
			<display:column title="身分證字號" property="idno" sortable="true" class="center" />
			<display:column title="單位" property="name" sortable="true" class="left" />
			<display:column title="職稱" property="sname" sortable="true" class="left" />
			<display:column title="人口" property="family_no" sortable="true" class="center" />
		</display:table>
		
		</td>
	</tr>
	<tr class="fullColorTr" height="30">
		<td align="center">
		<INPUT type="submit"
			   name="method"
			   id="Modify"
			   onMouseOver="showHelpMessage('學號完整並對應姓名會按照學生目前狀態進行下一步驟,<br>'+
			   '若無對應則認定為新生直接進入轉入模式', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Modify'/>"
			   class="gSubmit">
						   
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message
			   key='Cancel'/>"
			   class="gCancle"
			   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
											   
		<INPUT type="submit"
			   name="method" id="Delete"
			   value="<bean:message
			   key='Delete'/>"
			   class="gCancle"
			   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		</td>
	</tr>
</table>