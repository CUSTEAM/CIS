<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Personnel/QuitEmployeeInfoEdit" method="post">
    <input type="hidden" name="mode" value="${QuitEmployeeInfoEdit.mode}">
    <c:if test="${QuitEmployeeInfoEdit.mode == 'Create'}">
      <script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.CreateQuitEmployeeInfo" bundle="PSN"/></B></div>');</script></c:if>
    <c:if test="${QuitEmployeeInfoEdit.mode == 'Modify'}">
      <script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.ModifyQuitEmployeeInfo" bundle="PSN"/></B></div>');</script></c:if>
    <c:if test="${QuitEmployeeInfoEdit.mode == 'View'}">
      <script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.ViewQuitEmployeeInfo"   bundle="PSN"/></B></div>');</script></c:if>
	<tr><td>
       <table width="100%" cellspacing="5" class="empty-border">
		 <tr><td colspan="3">中文姓名&nbsp;
		 	   <input type="text" name="cname" 	size="12" value="${QuitEmployeeInfoEdit.cname}">
		 	   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  			   <bean:message key="Idno"/>&nbsp;
  			   <input type="text" name="idno"	size="12" value="${QuitEmployeeInfoEdit.idno}">
  			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  			   <bean:message key="Sex"/>&nbsp;
 		 	   <c:set var="sexSel"  value="${QuitEmployeeInfoEdit.sex}"/>
 		 	   <%@ include file="/pages/include/SexSelect.jsp" %></td>
  			 <td>員工分類&nbsp;
			   <c:set var="categorySel"  value="${QuitEmployeeInfoEdit.category}"/>
	  		   <%@ include file="/pages/include/EmpCategorySelect.jsp" %></td></tr>
 		 <tr><td colspan="3">英文姓名&nbsp;
		       <input type="text" name="ename" 	size="20" value="${QuitEmployeeInfoEdit.ename}">&nbsp;&nbsp;&nbsp;&nbsp;
 		 	   <bean:message key="BirthDate"/>&nbsp;
 		 	   <input type="text" name="bdate2" size="8"  value="${QuitEmployeeInfoEdit.bdate2}">
 		 	   (<bean:message key="ChineseDateFormat"/>)</td>
 		 	 <td>保險證號&nbsp;
		 	   <input type="text" name="insno" 	size="12" value="${QuitEmployeeInfoEdit.insno}"></td></tr>
		 <tr><td colspan="3">電郵信箱&nbsp;
		 	   <INPUT type="text" name="email"  size="40" value="${QuitEmployeeInfoEdit.email}"></td></tr>
		 	 
		 <tr><td colspan="3">現居地址&nbsp;
		 	   <INPUT type="text" name="czip"  size="2"  value="${QuitEmployeeInfoEdit.czip}">
		 	   <INPUT type="text" name="caddr" size="60" value="${QuitEmployeeInfoEdit.caddr}"></td>
		 	 <td>住宅電話&nbsp;
		 	   <INPUT type="text" name="telephone"   size="12" value="${QuitEmployeeInfoEdit.telephone}"></td></tr>
		 <tr><td colspan="3">戶籍地址&nbsp;
		 	   <INPUT type="text" name="pzip"  size="2"  value="${QuitEmployeeInfoEdit.pzip}">
		 	   <INPUT type="text" name="paddr" size="60" value="${QuitEmployeeInfoEdit.paddr}"></td>
		 	 <td>手機號碼&nbsp;
		 	   <INPUT type="text" name="cellPhone"   size="12" value="${QuitEmployeeInfoEdit.cellPhone}"></td></tr>
	   </table></td></tr>
	 <tr><td height="2" class="fullColorTable"></td></tr>
	 <tr><td>
	   <table width="100%" cellspacing="5" class="empty-border">
	     <%@ include file="/pages/include/CodeSelection.js" %>
	     <tr><td>聘任單位&nbsp;
	      	   <c:set var="codeId" 		    value="unit"/>
  		 	   <c:set var="codeIdInitValue" value="${QuitEmployeeInfoEdit.unit}"/>
  		 	   <c:set var="codeSel"		    value="unitSel"/>
  		 	   <c:set var="codeList"		value="${EmployeeUnit}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %>&nbsp;
  		 	   <A class="blue_13"><bean:message key='Details'/>...</A>
  		 	   </td>
  		 	 <td>職級&nbsp;
	      	   <c:set var="codeId" 		    value="pcode"/>
  		 	   <c:set var="codeIdInitValue" value="${QuitEmployeeInfoEdit.pcode}"/>
  		 	   <c:set var="codeSel"		    value="pcodeSel"/>
  		 	   <c:set var="codeList"		value="${EmployeeRole}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %>&nbsp;
  		 	   <A class="blue_13"><bean:message key='Details'/>...</A>
  		 	   </td>
  		 	 <td>學歷&nbsp;
	      	   <c:set var="codeId" 		    value="degree"/>
  		 	   <c:set var="codeIdInitValue" value="${QuitEmployeeInfoEdit.degree}"/>
  		 	   <c:set var="codeSel"		    value="degreeSel"/>
  		 	   <c:set var="codeList"		value="${EmployeeDegree}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %>&nbsp;
  		 	   <A class="blue_13"><bean:message key='Details'/>...</A>
  		 	   </td></tr>
  		 <tr><td>兼任導師&nbsp;
	      	   <c:set var="codeId" 		    value="tutor"/>
  		 	   <c:set var="codeIdInitValue" value="${QuitEmployeeInfoEdit.tutor}"/>
  		 	   <c:set var="codeSel"		    value="tutorSel"/>
  		 	   <c:set var="codeList"		value="${EmployeeTutor}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %>
  		 	   </td></tr>
	     <tr><td>兼任主管&nbsp;
	      	   <c:set var="codeId" 		    value="director"/>
  		 	   <c:set var="codeIdInitValue" value="${QuitEmployeeInfoEdit.director}"/>
  		 	   <c:set var="codeSel"		    value="directorSel"/>
  		 	   <c:set var="codeList"		value="${EmployeeDirector}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %>&nbsp;
  		 	   <A class="blue_13"><bean:message key='Details'/>...</A>
  		 	   </td>
  		 	 <td colspan="2">本校職稱&nbsp;
	      	   <INPUT type="text" name="sname"  size="30" value="${QuitEmployeeInfoEdit.sname}">&nbsp;
  		 	   <A class="blue_13"><bean:message key='Details'/>...</A>
  		 	   </td></tr>
	   </table></td></tr>
	 <tr><td height="2" class="fullColorTable"></td></tr>
	 <tr><td>
	   <table width="100%" cellspacing="5" class="empty-border">
  		 <tr><td colspan="3">任職狀態&nbsp;
			   <c:set var="statusSel"  value="${QuitEmployeeInfoEdit.status}"/>
	  		   <%@ include file="/pages/include/EmpStatusSelect.jsp" %>&nbsp;&nbsp;&nbsp;
  		 	      起&nbsp;
  		 	   <input type="text" name="startDate2" size="8" value="${QuitEmployeeInfoEdit.startDate2}">&nbsp;
  		 	      訖&nbsp;
  		 	   <input type="text" name="endDate2"   size="8" value="${QuitEmployeeInfoEdit.endDate2}">&nbsp;
  		 	   (<bean:message key="ChineseDateFormat"/>)&nbsp;&nbsp;&nbsp;
  		 	   <bean:message key="Cause" bundle="REG"/>&nbsp; 
  		 	   <c:set var="codeId" 		    value="statusCause"/>
  		 	   <c:set var="codeIdInitValue" value="${QuitEmployeeInfoEdit.statusCause}"/>
  		 	   <c:set var="codeSel"		    value="statusCauseSel"/>
  		 	   <c:set var="codeList"		value="${EmployeeStatusCause}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %>&nbsp; 		 	   
  		 	   <A class="blue_13"><bean:message key='Details'/>...</A></td></tr>
  		 <tr><td colspan="2">任教職日&nbsp;
  		 	   <INPUT type="text" name="teachStartDate2"  size="8"  value="${QuitEmployeeInfoEdit.teachStartDate2}">&nbsp;
			   (<bean:message key="ChineseDateFormat"/>)</td></tr>
	   </table></td></tr>
	<c:if test="${QuitEmployeeInfoEdit.mode != 'View'}">
	  <script>generateTableBanner('<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'
     						    + '<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='Cancel'/>" >');</script>
	</c:if>  
	<c:if test="${QuitEmployeeInfoEdit.mode == 'View'}">
      <script language="javascript">
	  <!--
	  	  function enableControls(btnControl) {
	  	    if (btnControl.value == '<bean:message key="OK"/>') return true;
	  	    var aControl;
	  	  	for (i=0; i < document.forms[0].length; i++) {
	  	  	  aControl = document.forms[0].elements[i];
	  	  	  if (aControl.type == 'text' ) {
	  	  	    aControl.readOnly = false;
	  	  	  } else if (aControl.type == 'select-one' ) {
	  	  	    aControl.disabled = false;
	  	  	  } else if (aControl.type == 'hidden' && aControl.name == 'mode') {
	  	  	  	aControl.value = 'Modify';
	  	  	  } else if (aControl.type == 'submit' || aControl.type == 'button') {
	  	  	    if (aControl.value == '<bean:message key="Modify"/>') {
	  	  	      aControl.value = '<bean:message key="OK"/>';
	  	  	    } else if (aControl.value == '<bean:message key="Back"/>') {
	  	  	      aControl.value = '<bean:message key="Cancel"/>';
	  	  	    }
	  	  	  }
	  	  	}
	  	  	return false;
	  	  };
	  //-->
	  </script>
	  <script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return enableControls(this);">&nbsp;&nbsp;'
     						    + '<INPUT type="submit" name="method" value="<bean:message key='Back'  />">');</script>
	</c:if>  
  </html:form>
  <c:if test="${QuitEmployeeInfoEdit.mode == 'View'}">
    <script language="javascript">
	<!--
		var aControl;
		for (i=0; i < document.forms[0].length; i++) {
			aControl = document.forms[0].elements[i];
			if (aControl.type == 'text' ) {
				aControl.readOnly = true;
			} else if (aControl.type == 'select-one' ) {
				aControl.disabled = true;
			} 
		}
	//-->
	</script>
  </c:if>
</table>
<script>history.go(1);</script>