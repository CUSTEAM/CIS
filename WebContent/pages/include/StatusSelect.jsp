<SELECT name="status">
	<OPTION value=""></OPTION>
	<c:forEach items='${StudentStatus}' var='code5'>
		<OPTION value='${code5.idno}' <c:if test='${code5.idno==statusSel}'>selected</c:if>>${code5.name}</OPTION>
	</c:forEach></SELECT>
