<SELECT name="sex">
	<OPTION value=""></OPTION>
	<OPTION value="1" <c:if test='${sexSel=="1"}'>selected</c:if>><bean:message key="Male"/></OPTION>
	<OPTION value="2" <c:if test='${sexSel=="2"}'>selected</c:if>><bean:message key="Female"/></OPTION>
</SELECT>