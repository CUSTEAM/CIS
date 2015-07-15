<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon_component.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;班級管理&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<html:form action="/Summer/ClassManager" method="post" onsubmit="init('系統處理中...')">
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				班級代碼
				</td>
				<td class="hairLineTdF">
				班級名稱
				</td>
			</tr>
			<tr>
				<td class="hairLineTd">
				<input type="text" name="aNo" />				
				</td>
				<td class="hairLineTd">
				<input type="text" name="aName" />				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton">
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				班級代碼
				</td>
				<td class="hairLineTdF">
				班級名稱
				</td>
			</tr>
<c:forEach items="${sabbrs}" var="s">
			<tr>
				<td class="hairLineTd">
				<input type="text" name="no" value="${s.no}" /><input type="hidden" name="Oid" value="${s.Oid}"/>
				</td>
				<td class="hairLineTd">
				<input type="text" name="name" value="${s.name}" />				
				</td>
			</tr>




</c:forEach>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" class="CourseButton">
		</td>
	</tr>
</html:form>
</table>

<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>