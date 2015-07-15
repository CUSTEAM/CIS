<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:forEach items="${myDate}" var="m">
<table width="100%">
	<tr>
		<td width="10" align="left" nowrap>
  			<hr noshade class="myHr"/>
			</td>
			<td width="24" align="center" nowrap>
			<img src="images/16-cube-blue.png" />
			</td>
			<td nowrap style="cursor:pointer;" onClick="showObj('myDate${m.seq}')">
			編輯${m.tableName}
			</td>
			<td width="100%" align="left">
  			<hr noshade class="myHr"/>
		</td>
	</tr>
</table>
<table class="hairLineTable" width="98%" id="myDate${m.seq}" style="display:inline;">	
	<c:forEach items="${m.detail}" var="md" varStatus="ss">
	<tr>
		<td class="hairLineTdF" nowrap>												
		${md.fieldName}												
		</td>
		<td class="hairLineTdF" width="100%">
		<%@ include file="field4old.jsp"%>
								
		</td>
	</tr>
	
	</c:forEach>
	<tr>
		<td class="hairLineTdF" align="right" colspan="2">
		<INPUT type="submit" name="method" value="<bean:message key='Update'/>" class="gSubmit">
		</td>
	</tr>
</table>
	
</c:forEach>
