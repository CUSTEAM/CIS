<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>
<form action="/CIS/Score/ScoreHistAvgEdit.do" method="post" name="histForm">

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<input type="hidden" name="mode" value="${ScoreHistAvgEdit.mode}">
<input type="hidden" name="student_name" value="${ScoreHistAvgEdit.student_name}">
<input type="hidden" name="student_class" value="${ScoreHistAvgEdit.student_class}">
<input type="hidden" name="student_classname" value="${ScoreHistAvgEdit.student_classname}">
<input type="hidden" name="stdeptclass_name" value="${ScoreHistAvgEdit.stdepclass_name}">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="scrTitle.ScoreHistCreate" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td align="left">
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border">
      <!--
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="成績資料維護"/> -&gt;歷年成績資料維護 -&gt; 
        <span class="style2">
        <c:if test="${ScoreHistEdit.mode=='Create'}">
        	<c:out value="新增"/>
        </c:if>
        <c:if test="${ScoreHistEdit.mode=='Modify'}">
        	<c:out value="修改"/>
        </c:if>
        </span></td>
  	    </tr>
  	  -->
      <tr>
        <td align="left" valign="middle">學號：
          <input name="student_no" value="${ScoreHistAvgEdit.student_no}" type="text" size="7" maxlength="10" <c:out value="${readOnly}" />
 forms[0] 		onchange="if(this.value!='') document.forms[0].submit();" >
          			<c:if test="${ScoreHistAvgEdit.studentfound == true}">
				 	 &nbsp;&nbsp;
				 	 <font class="blue_13"><c:out value="${ScoreHistAvgEdit.student_name}" />&nbsp;
				 	 					   <c:out value="${ScoreHistAvgEdit.student_classname}"/></font>
				 	</c:if>
				 	<c:if test="${ScoreHistAvgEdit.studentfound == false}">
				 	 <font class="red_13"><c:out value="* 查無此人" /></font>
				 	</c:if>
		  </td></tr>
	  <tr>
          <!-- <img src="spacer.gif" alt="spacer" width="48" height="6"/> -->
		<td align="left" valign="middle">學年：
          <input name="school_year" type="text" size="3" maxlength="5" value="${ScoreHistAvgEdit.school_year}">&nbsp;&nbsp;
          	學期：
          <input name="school_term" type="text" size="1" maxlength="1" value="${ScoreHistAvgEdit.school_term}"></td>
      </tr>
	  <tr>
        <td colspan="3" align="left" valign="middle">學分：
        	<input name="credit" type="text" size="1" maxlength="3" value="${ScoreHistAvgEdit.credit}">&nbsp;&nbsp;
        	成績：
        	<input name="score" type="text" size="3" maxlength="6" value="${ScoreHistAvgEdit.score}"></td>
      </tr>
	</table></td></tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="scrHistEditAction" value="確認" >&nbsp;&nbsp;'+
						'<INPUT type="submit" name="scrHistEditAction" value="取消" >');
	</script>
</table>
</form>


