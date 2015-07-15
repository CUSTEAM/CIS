<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<br>
<table width="100%" cellpadding="0" cellspacing="0" onClick="showEdit('groupBase')">
	<tr style="cursor:pointer;">
		<td width="10" align="left" nowrap>
    		<hr noshade class="myHr"/>
  		</td>
  		<td width="24" align="center" nowrap onClick="showMod('fast')">
 		<img src="images/icon/image_edit.gif" id="add" onMouseOver="showHelpMessage('...', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
  		</td>
  		<td nowrap style="cursor:pointer;">
  		學程基本資料&nbsp;
  		</td>
  		<td width="100%" align="left">
    		<hr noshade class="myHr"/>
  		</td>
	</tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td <c:if test="${groupType!='editGroup'}">style="display:none"</c:if> id="groupBase">
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				
				學程名稱
				</td>
				<td class="hairLineTd">				
				<input type="hidden" name="groupOid" id="groupOid" value="${aGroup.Oid}" />
				<input type="text" name="cname" id="cname" value="${aGroup.cname}" />			
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				
				英文名稱
				</td>
				<td class="hairLineTd">
				<input type="text" name="ename" id="ename" value="${aGroup.ename}" />
							
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">				
				適用最後入學年
				</td>
				<td class="hairLineTd">
				<input type="text" name="groupEnd" id="groupEnd" value="${aGroup.entrance}" maxlength="3" size="3"/>				
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td width="100%">				
				
				<FCK:editor instanceName="note" toolbarSet="Basic" basePath="/pages/include/fckeditor">
					<jsp:attribute name="value">${aGroup.note}</jsp:attribute>
					<jsp:body><FCK:config SkinPath="skins/office2003/"/></jsp:body>
				</FCK:editor>
									
				</td>
			</tr>
		</table>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTd">
				<INPUT type="submit" name="method" value="<bean:message key='EditGroup' bundle='COU'/>" class="CourseButton" />				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>