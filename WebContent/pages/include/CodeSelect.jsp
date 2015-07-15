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
<INPUT type="text" id="${codeId}" name="${codeId}" size="1"
  	   value="${codeIdInitValue}" onkeyup="updateSelect('${codeSel}',this.value);"><SELECT id="${codeSel}" name="${codeSel}" onchange="updateCode('${codeId}', this.value);">
	 <OPTION value=""></OPTION>
	 <c:forEach items='${codeList}' var='code5'>
	   <OPTION value='${code5.idno}' <c:if test='${code5.idno==codeIdInitValue}'>selected</c:if>>${code5.name}</OPTION>
	 </c:forEach></SELECT>