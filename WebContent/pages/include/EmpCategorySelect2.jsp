<SELECT name="category2">
	<OPTION value=""></OPTION>
	<c:forEach items='${EmployeeCategory}' var='code5'>
		<OPTION value='${code5.idno}' <c:if test='${code5.idno==categorySel}'>selected</c:if>>${code5.name}</OPTION>
	</c:forEach></SELECT>
