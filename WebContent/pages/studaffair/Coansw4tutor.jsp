<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/StudAbsence/Coansw4tutor" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/chart_line.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">導師評量</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		
		
		
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF">
				
				<table>
					<tr>
						<td>
						<select name="school_year" class="CourseButton">
							<c:forEach items="${years}" var="y">
							<option <c:if test="${Coansw4tutorForm.map.school_year==y.school_year}" >selected</c:if> value="${y.school_year}">${y.school_year}學年</option>
							</c:forEach>
						</select>
						</td>
						<td>
						<select name="school_term" class="CourseButton">
							<option <c:if test="${Coansw4tutorForm.map.school_term=='1'}" >selected</c:if> value="1">上學期</option>
							<option <c:if test="${Coansw4tutorForm.map.school_term=='2'}" >selected</c:if> value="2">下學期</option>
						</select>
						</td>
						<td>
						<INPUT type="submit" name="method" id="Query" onMouseOver="showHelpMessage('查詢學年及學期資料', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value="<bean:message key='Query'/>" class="gSubmit">
					</td>
					</tr>
				</table>
								
				</td>
			</tr>
		</table>		
		</td>		
	</tr>
	
	<c:if test="${!empty cs}">
	<tr>
		<td>
		
		
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF">班級代碼</td>
				<td class="hairlineTdF">班級名稱</td>
				<td class="hairlineTdF">教師代碼</td>
				<td class="hairlineTdF">教師名稱</td>
				<td class="hairlineTdF">樣本數</td>
				<td class="hairlineTdF">平均值</td>
			</tr>
			<c:forEach items="${cs}" var="c">
			<tr>
				<td class="hairlineTdF">${c.depart_class}</td>
				<td class="hairlineTdF">${c.ClassName}</td>
				<td class="hairlineTdF">${c.techid}</td>
				<td class="hairlineTdF">${c.cname}</td>
				<td class="hairlineTdF">${c.smp}</td>
				<td class="hairlineTdF">${c.avg}</td>
			</tr>			
			</c:forEach>
		</table>
		
		
		
		</td>
	</tr>
	</c:if>
</table>
</html:form>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>