<!-- 
	THIS JSP IS USED TO GENERATE A TEXT/SELECTION PAIR TO HELP USER INPUT CODE OR SELECT NAME FOR PARAMETERS
	RECORDED IN DATABASE TABLE "code5", INCLUDING Status, Identity, Group, Cause etc.
	
	TO INCLUDE THIS JSP, YOU MUST SET THE FOLLOWING VARIABLES FIRST TO PASS ARGUMENTS INTO:
	
	var="codeId"		   value="TextInputControlName"
	var="codeIdInitValue"  value="${FormPropertyForCode}"
	var="codeSel"		   value="SelectInputControlName"
  	var="codeList"		   value="${AppScopeListName}"   
  	
  	APPLIED AppScopeListName ARE:
  	
  	StudentStatus   - ALL Status   
  	StudentIdentity - ALL Identities 
  	StudentGroup    - ALL Groups    
  	StatusCause     - All Causes
  	
  	AN JS FILE "/pages/include/CodeSelection.js" MUST BE INCLUDED FIRST TOO.
  	(THE REASON KEEPING THIS JavaScript FILE OUT IS THAT THERE MAY BE MORE THAN ONE OCCURANCE WITHIN A PAGE)
 -->
	<INPUT type="text" id="${tcnt}" name="${codeId}" size="3"
  	   value="${codeIdInitValue}" onkeyup="updateSelect('${codeSel}',this.value);nextfocus(${tcnt+1});">
  	<SELECT id="${codeSel}" name="${codeSel}" onchange="updateCode('${tcnt}', this.value);">
	 <OPTION value=""></OPTION>
	 <c:forEach items='${codeList}' var='code2'>
	   <OPTION value='${code2.no}' <c:if test='${code2.no==codeIdInitValue}'>selected</c:if>>${code2.name}</OPTION>
	 </c:forEach></SELECT>