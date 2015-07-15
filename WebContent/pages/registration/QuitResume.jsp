<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javascript">
<!--
	function clearStudentInfo() {
		//alert(document.getElementById("studentNameClass").innerHTML);
		with (document.forms[0]) {
			if ("${QuitResume.found}" == "true" && orgStudentNo.value != studentNo.value) {
				document.getElementById("studentNameClass").innerHTML = "";
				year1.value = "";
				term1.value = "";
				date1.value = "";
				docNo1.value = "";
			}
		}
	};
//-->
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/QuitResume" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.QuitResume" bundle="REG"/></B></div>');</script>	  
		<tr><td align="center">
		   <input type="hidden" name="orgStudentNo" value="${QuitResume.studentNo}">
	       <table cellspacing="5" class="empty-border">
			 <tr><td colspan="2">學&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 號&nbsp;
			 		<input type="text" name="studentNo" size="12" value="${QuitResume.studentNo}"
			 						   onkeyup="clearStudentInfo();"
			 		                   onchange="if(this.value!='') document.forms[0].submit();">
				 	<c:if test="${QuitResume.found == true}">
				 	 &nbsp;
				 	 <font class="blue_13"><span id="studentNameClass"><c:out value="${QuitResume.name}" />
				 	 					   		   					   <c:out value="${QuitResume.class}"/></span></font>
				 	</c:if>
				 	<c:if test="${QuitResume.found == false}">
				 	 <font class="red_13"><c:out value="* 查無此人" /></font>
				 	</c:if></td></tr>
			 <tr><td>休學年度&nbsp;
			 		<input type="text" name="year1"   size="1" value="${QuitResume.year1}" disabled></td>
			 	 <td>復學年度&nbsp;
			 		<input type="text" name="year2"   size="1" value="${QuitResume.year2}"></td>
			 <tr><td>休學學期&nbsp;
			 		<input type="text" name="term1"   size="1" value="${QuitResume.term1}" disabled></td>
			 	 <td>復學學期&nbsp;
			 		<input type="text" name="term2"   size="1" value="${QuitResume.term2}"></td>
			 <tr><td>休學日期&nbsp;
			 		<input type="text" name="date1"   size="8" value="${QuitResume.date1}" disabled></td>
			 	 <td>復學日期&nbsp;
			 		<input type="text" name="date2"   size="8" value="${QuitResume.date2}">
			 		(<bean:message key="ChineseDateFormat"/>)</td> 
  			 <tr><td>核准文號&nbsp;
  			 		<input type="text" name="docNo1"  size="20" value="${QuitResume.docNo1}" disabled>
  			 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  			     <td>核准文號&nbsp;
					<input type="text" name="docNo2"  size="20" value="${QuitResume.docNo2}"></td></tr>
     	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" class="CourseButton">');</script>
  </html:form>
</table>
