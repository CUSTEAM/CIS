	<INPUT type="text" id="${tcnt}" name="${codeId}" size="1"
  	   value="${codeIdInitValue}" onkeyup="updateSelect('${codeSel}${tcnt}',this.value);nextfocus(${tcnt+1});">
  	<SELECT id="${codeSel}${tcnt}" name="${codeSel}" onchange="updateCode('${tcnt}', this.value);">
	 <OPTION value=""></OPTION>
	 <c:forEach items='${codeList}' var='code2'>
	   <OPTION value='${code2.no}' <c:if test='${code2.no==codeIdInitValue}'>selected</c:if>>${code2.name}</OPTION>
	 </c:forEach></SELECT>