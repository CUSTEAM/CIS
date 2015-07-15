<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/StudentInfoEdit" method="post" onsubmit="init();">
    <input type="hidden" name="mode" value="${StudentInfoEdit.mode}">
    <c:if test="${StudentInfoEdit.mode == 'Create'}">
      <script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.CreateStudentInfo" bundle="REG"/></B></div>');</script></c:if>
    <c:if test="${StudentInfoEdit.mode == 'Modify'}">
      <script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.ModifyStudentInfo" bundle="REG"/></B></div>');</script></c:if>
    <c:if test="${StudentInfoEdit.mode == 'View'}">
      <script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.ViewStudentInfo"   bundle="REG"/></B></div>');</script></c:if>
	<tr><td>
       <table width="100%" cellspacing="5" class="empty-border">
		 <tr><td><bean:message key="Name"/>&nbsp;
		 	   <input type="text" name="name" 		size="12" value="${StudentInfoEdit.name}"></td>
		 	 <td><bean:message key="StudentNo"/>&nbsp;
		 	   <input type="text" name="studentNo" 	size="12" value="${StudentInfoEdit.studentNo}"></td>
		     <td colspan="2" align="right"><bean:message key="Class"/>&nbsp;
  			   <c:set var="campusSel" value="${StudentInfoEdit.campusInCharge}"/>
  			   <c:set var="schoolSel" value="${StudentInfoEdit.schoolInCharge}"/>
  			   <c:set var="deptSel"   value="${StudentInfoEdit.deptInCharge}"/>
  			   <c:set var="classSel"  value="${StudentInfoEdit.classInCharge}"/>
  			   <c:if test="${StudentInfoEdit.mode == 'Create'}">
  			   	 <%@ include file="/pages/include/ClassSelect.jsp" %></c:if>
  			   <c:if test="${StudentInfoEdit.mode != 'Create'}">
  			     <%@ include file="/pages/include/AllClassSelect1.jsp" %></c:if></td></tr>
 		 <tr><td><bean:message key="Sex"/>&nbsp;
 		 	   <c:set var="sexSel"  value="${StudentInfoEdit.sex}"/>
 		 	   <%@ include file="/pages/include/SexSelect.jsp" %></td>
 		 	 <td colspan="2"><bean:message key="BirthDate"/>&nbsp;
 		 	   <input type="text" name="birthDate" size="8" value="${StudentInfoEdit.birthDate}">
 		 	   (<bean:message key="ChineseDateFormat"/>)</td>
  			 <td><bean:message key="Idno"/>&nbsp;
  			   <input type="text" name="idNo"	size="12" value="${StudentInfoEdit.idNo}"></td></tr>
		 <tr><td colspan="2"><bean:message key="Group"  bundle="REG"/>&nbsp;
  		 	   <c:set var="codeId" 		    value="group"/>
  		 	   <c:set var="codeIdInitValue" value="${StudentInfoEdit.group}"/>
  		 	   <c:set var="codeSel"		    value="groupSel"/>
  		 	   <c:set var="codeList"		value="${StudentGroup}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %></td>
			 <td align="right"><bean:message key="BasicIdentity"   bundle="REG"/>&nbsp;
  		 	   <c:set var="codeId" 		     value="basicIdentity"/>
  		 	   <c:set var="codeIdInitValue"  value="${StudentInfoEdit.basicIdentity}"/>
  		 	   <c:set var="codeSel"		     value="basicIdentSel"/>
  		 	   <c:set var="codeList"		 value="${StudentIdentity}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %></td>
  		 	 <td>
  		 	        身份備註&nbsp;
  		 	   <input type="text" name="identityRemark" size="12" value="${StudentInfoEdit.identityRemark}"></td></tr>
  		 <tr><td colspan="2"><bean:message key="EntranceYearMonth"  bundle="REG"/>&nbsp;
  		 	   <input type="text" name="entrance" size="5" value="${StudentInfoEdit.entrance}">
  		 	   (<bean:message key="ChineseYearMonthFormat" bundle="REG"/>)</td>
  		 	 <%@ include file="/pages/include/CodeSelection.js" %>
  		 	 <td align="right"><bean:message key="EntranceIdentity"   bundle="REG"/>&nbsp;
  		 	   <c:set var="codeId" 		     value="entranceIdentity"/>
  		 	   <c:set var="codeIdInitValue"  value="${StudentInfoEdit.entranceIdentity}"/>
  		 	   <c:set var="codeSel"		     value="entrIdentSel"/>
  		 	   <c:set var="codeList"		 value="${StudentIdentity}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %></td></tr>
	     <tr><td colspan="3"><bean:message key="GradSchool"  bundle="REG"/>&nbsp;
	     	   <INPUT type="text" name="gradSchool" size="20" value="${StudentInfoEdit.gradSchool}">&nbsp;&nbsp;
	     	        畢業科系&nbsp;<INPUT type="text" name="gradDept" size="15" value="${StudentInfoEdit.gradDept}">&nbsp;&nbsp;&nbsp;
	     	   <bean:message key="GradOrNot"   bundle="REG"/>&nbsp;
	     	   <SELECT name="gradOrNot">
	     	     <OPTION value=""></OPTION>
	     	     <OPTION value="1" <c:if test='${StudentInfoEdit.gradOrNot=="1"}'>selected</c:if>><bean:message key="Graduate"    bundle="REG"/></OPTION>
	     	     <OPTION value="2" <c:if test='${StudentInfoEdit.gradOrNot=="2"}'>selected</c:if>><bean:message key="NotGraduate" bundle="REG"/></OPTION>
	     	   </SELECT>&nbsp;&nbsp;&nbsp;
	     	      年度&nbsp;
  		 	   <input type="text" name="gradYear"    size="1"  value="${StudentInfoEdit.gradYear}"></td>
  		 	 <td>英文姓名&nbsp;
  		 	   <INPUT type="text" name="ename"   	 size="20" value="${StudentInfoEdit.ename}"></td></tr>
		 <tr><td colspan="3"><bean:message key="CommAddress"/>&nbsp;
		 	   <INPUT type="text" name="commZip"     size="2"  value="${StudentInfoEdit.commZip}">
		 	   <INPUT type="text" name="commAddress" size="60" value="${StudentInfoEdit.commAddress}"></td>
		 	 <td>手機號碼&nbsp;
		 	   <INPUT type="text" name="cellPhone"   size="20" value="${StudentInfoEdit.cellPhone}"></td></tr>
		 <tr><td colspan="3">電郵信箱&nbsp;
		 	   <INPUT type="text" name="email"       size="40" value="${StudentInfoEdit.email}"></td></tr>
	   </table></td></tr>
	 <tr><td height="2" class="fullColorTable"></td></tr>
	 <tr><td>
	   <table width="100%" cellspacing="5" class="empty-border">
	     <tr><td colspan="3"><bean:message key="ParentName" bundle="REG"/>&nbsp;
	      	   <INPUT type="text" name="parentName"  size="12" value="${StudentInfoEdit.parentName}">&nbsp;&nbsp;&nbsp;
	      	   <bean:message key="PhoneNum" bundle="REG"/>&nbsp;
	      	   <INPUT type="text" name="phone"		 size="12" value="${StudentInfoEdit.phone}"></td></tr>
	     <tr><td colspan="3"><bean:message key="PermAddress" bundle="REG"/>&nbsp;
	     	   <INPUT type="text" name="permZip"     size="2"  value="${StudentInfoEdit.permZip}">
	      	   <INPUT type="text" name="permAddress" size="60" value="${StudentInfoEdit.permAddress}"></td></tr>
	   </table></td></tr>
	 <tr><td height="2" class="fullColorTable"></td></tr>
	 <tr><td>
	   <table width="100%" cellspacing="5" class="empty-border">
  		 <tr><td><bean:message key="Status"/>&nbsp;
   		 	   <c:set var="codeId" 		    value="status"/>
  		 	   <c:set var="codeIdInitValue" value="${StudentInfoEdit.status}"/>
  		 	   <c:set var="codeSel"		    value="statusSel"/>
  		 	   <c:set var="codeList"		value="${StudentStatus}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %></td>
  		 	 <td colspan="3"><bean:message key="Date"/>&nbsp;
  		 	   <input type="text" name="statusDate"  size="8"  value="${StudentInfoEdit.statusDate}">(<bean:message key="ChineseDateFormat"/>)</td></tr>
  		 <tr><td><bean:message key="Year"  bundle="REG"/>&nbsp;
  		 	   <INPUT type="text" name="statusYear"  size="1"  value="${StudentInfoEdit.statusYear}">&nbsp;
  		 	 	 <bean:message key="Term"  bundle="REG"/>&nbsp;
  		 	   <INPUT type="text" name="statusTerm"  size="1"  value="${StudentInfoEdit.statusTerm}"></td>
  		 	 <td colspan="3"><bean:message key="Cause" bundle="REG"/>&nbsp; 
  		 	   <c:set var="codeId" 		    value="statusCause"/>
  		 	   <c:set var="codeIdInitValue" value="${StudentInfoEdit.statusCause}"/>
  		 	   <c:set var="codeSel"		    value="statusCauseSel"/>
  		 	   <c:set var="codeList"		value="${StatusCause}"/>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %></td></tr>
  		 <tr><td><bean:message key="DocNum"  bundle="REG"/>&nbsp;	 
  		 	   <INPUT type="text" name="docNo"  size="12"  value="${StudentInfoEdit.docNo}"></td>
  		     <td><bean:message key="GraduateNum"  bundle="REG"/>&nbsp;	 
  		 	   <INPUT type="text" name="graduateNo"  size="12"  value="${StudentInfoEdit.graduateNo}"></td>
  		 	 <td colspan="2" align="right"><bean:message key="AuxDeptOr2ndMajor" bundle="REG"/>&nbsp; 
	     	   <SELECT name="extraStatus">
	     	     <OPTION value=""></OPTION>
	     	     <OPTION value="輔系"   <c:if test='${StudentInfoEdit.extraStatus=="輔系"}' >selected</c:if>><bean:message key="AuxDept"  bundle="REG"/></OPTION>
	     	     <OPTION value="雙主修" <c:if test='${StudentInfoEdit.extraStatus=="雙主修"}'>selected</c:if>><bean:message key="2ndMajor" bundle="REG"/></OPTION>
	     	   </SELECT>&nbsp;&nbsp;
	     	     <bean:message key="Dept" bundle="REG"/>&nbsp;
	     	   <INPUT type="text" name="extraDept"  size="10"  value="${StudentInfoEdit.extraDept}"></td></tr>	 	   
	   </table></td></tr>
	<c:if test="${StudentInfoEdit.mode != 'View'}">
	 <c:if test="${not SubmitAutoClear}">
	  <c:if test="${StudentInfoEdit.mode == 'Modify'}">
	   <script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" 		  class="CourseButton">&nbsp;&nbsp;'
     						     + '<INPUT type="submit" name="method" value="<bean:message key='CreateWWPass'/>" class="CourseButton">&nbsp;&nbsp;'
     						     + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" 	  class="CourseButton">');</script>	  
	  </c:if>
	  <c:if test="${StudentInfoEdit.mode != 'Modify'}">
	   <script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" class="CourseButton" >&nbsp;&nbsp;'
     						     + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton" >');</script>
	  </c:if>
	 </c:if>
	 <c:if test="${SubmitAutoClear}">
	  <script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='ModifyWithAutoDeselect'/>" class="CourseButton" >&nbsp;&nbsp;'
     						    + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton" >');</script>	 
	 </c:if>
	</c:if>  
	<c:if test="${StudentInfoEdit.mode == 'View'}">
      <script language="javascript">
	  <!--
	  	  function enableControls(btnControl) {
	  	    if (btnControl.value == '<bean:message key="OK"/>') return true;
	  	    var aControl;
	  	  	for (i=0; i < document.forms[0].length; i++) {
	  	  	  aControl = document.forms[0].elements[i];
	  	  	  // 學號與身分證不允許更改
	  	  	  if (aControl.type == 'text' && aControl.name != 'studentNo' && aControl.name != 'idNo') {
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
	  <script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" 		 class="CourseButton" onclick="return enableControls(this);">&nbsp;&nbsp;'
     						    + '<INPUT type="submit" name="method" value="<bean:message key='CreateWWPass'/>" class="CourseButton">&nbsp;&nbsp;'
     						    + '<INPUT type="submit" name="method" value="<bean:message key='Back'/>" 		 class="CourseButton">');</script>
	</c:if>  
  </html:form>
  <c:if test="${StudentInfoEdit.mode == 'View'}">
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