<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<SELECT name="status">
	<OPTION value="">在職</OPTION>
	<c:forEach items='${EmployeeStatus}' var='code5'>
		<OPTION value='${code5.idno}' <c:if test='${code5.idno==statusSel}'>selected</c:if>>${code5.name}</OPTION>
	</c:forEach></SELECT>