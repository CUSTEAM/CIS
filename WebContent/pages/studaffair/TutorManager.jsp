<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/StudAffair/TutorManager" method="post" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/user_suit.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;導師設定&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				
				查詢範圍
				</td>
				<td class="hairLineTd">
				
				<select name="allDept">
					<c:forEach items="${allDept}" var="ad">
					<option <c:if test="${TutorManagerForm.map.allDept==ad.deptNo}">selected</c:if> value="${ad.deptNo}">${ad.deptName}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="CourseButton">
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	
	
	
	
	
	
	
	
<c:if test="${tutors!=null}">
	<tr>
		<td>
		
		
		
		<table class="hairLineTable">		
			<tr>
				<td class="hairLineTdF">班級代碼</td>
				<td class="hairLineTdF">班級名稱</td>
				<td class="hairLineTdF">教職員代碼</td>
				<td class="hairLineTdF">教職員名稱</td>
			</tr>
		<c:forEach items="${tutors}" var="t">
			<tr>
				<td class="hairLineTd">
				<input type="hidden" name="Oid" value="${t.Oid}" />
				<input type="text" size="6" name="ClassNo" value="${t.ClassNo}" readonly />
				</td>
				<td class="hairLineTd">				
				<input type="text" name="ClassName" value="${t.ClassName}" readonly />				
				</td>
				<td class="hairLineTd">				
				<input type="text" name="idno" id="idno${t.Oid}${t.ClassNo}" value="${t.idno}" readonly	/>				
				</td>
				<td class="hairLineTd">				
				<input type="text" name="cname" id="cname${t.Oid}${t.ClassNo}" value="${t.cname}" 
				onkeyup="getAny(this.value, 'cname${t.Oid}${t.ClassNo}', 'idno${t.Oid}${t.ClassNo}', 'empl', 'name')"
				onClick="this.value='', document.getElementById('idno${t.Oid}${t.ClassNo}').value=''"
				/>				
				</td>
			</tr>
		</c:forEach>
		
		</table>
		
		
		
		
		
		
		</td>
	</tr>
	<tr>
		<td><table class="hairLineTable">		
			<tr>
				<td class="hairLineTdF">
				修正為以上設定
				</td>
				<td class="hairLineTd">
				<INPUT type="submit" name="method" value="<bean:message key='OK'/>" class="CourseButton">
				</td>				
			</tr>
		</table>
		</td>
	</tr>
	
	
	<tr>
		<td><table class="hairLineTable">		
			<tr>
				<td class="hairLineTdF">
				輸出以上報表
				</td>
				<td class="hairLineTd">
				<img src="images/printer.gif" border="0">
				</td>				
			</tr>
		</table>
		</td>
	</tr>
</c:if>
	
	
	
	
	
	
	<tr height="30">
		<td class="fullColorTable">
		
		
		
		</td>
	</tr>
	
	
	
	
</html:form>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>