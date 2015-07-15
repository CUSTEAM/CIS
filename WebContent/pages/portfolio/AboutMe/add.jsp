<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<input type="hidden" name="next" value="${next}" />
<table width="100%" >
	<tr>
		<td width="10" align="left" nowrap>
  			<hr noshade class="myHr"/>
			</td>
			<td width="24" align="center" nowrap>
			<img src="images/16-cube-green.png" />
			</td>
			<td nowrap style="cursor:pointer;" onClick="showObj('add')">
			新增${table_name}
			</td>
			<td width="100%" align="left">
  			<hr noshade class="myHr"/>
		</td>
	</tr>
</table>

<table class="hairLineTable" width="98%" style="display:inline;" id="add">
	<c:forEach items="${format}" var="f" varStatus="ff">
	<tr>
		<td class="hairLineTdF" nowrap>												
		${f.name}												
		</td>
		<td class="hairLineTdF" width="100%">						
		<%@ include file="field4new.jsp"%>												
		</td>
	</tr>
	</c:forEach>
	<tr>
		<td class="hairLineTdF" align="right" colspan="2">
		<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="gSubmit">
		</td>
	</tr>
</table>