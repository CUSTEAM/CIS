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

<form action="/CIS/StudAffair/StudConduct.do" method="post" name="tfdForm">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<bean:message key="SAFTitle.StudBonusPenaltyDelete" bundle="SAF"/>');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="學生操行成績維護"/> -&gt; 
        <span class="style2">刪除確認
        </span></td>
  	  </tr>
	</table>
				
		</td>		
	</tr>
	<!-- Test if have Query Result  -->
	<c:if test="${StudConductDelete != null}" >
	
	<tr>
	<td>
	<table>
	<c:forEach items="${StudConductDelete}" var="Conduct">
		<tr>
			<td align="left" valign="middle" colspan="7">學號 ：${Conduct.studentNo}
				<input name="studentNo" type="hidden" value="${Conduct.studentNo}">
       			&nbsp;&nbsp;${Conduct.studentName}
       			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${Conduct.departClass}
       			&nbsp;&nbsp;${Conduct.deptClassName}
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基本分：82 
        	</td>
        </tr>
        <tr>
			<td width="13%" align="center" bgcolor="#add8e6">導師</td>
			<td width="13%" align="center" bgcolor="#add8e6">系主任</td>
			<td width="13%" align="center" bgcolor="#add8e6">教官</td>
			<td width="13%" align="center" bgcolor="#add8e6">勤惰</td>
			<td width="16%" align="center" bgcolor="#add8e6">獎懲</td>
			<td width="16%" align="center" bgcolor="#add8e6">評審會</td>
			<td width="16%" align="center" bgcolor="#add8e6">合計</td>
        </tr>
        <tr>
        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.teacherScore}</td>
        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.deptheaderScore}</td>
        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.militaryScore}</td>
        	<td align="center" valign="middle" bgcolor="#99ee90" id="dilgScore">${Conduct.dilgScore}</td>
        	<td align="center" valign="middle" bgcolor="#99ee90" id="desdScore">${Conduct.desdScore}</td>
        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.meetingScore}</td>
        	<td align="center" valign="middle" bgcolor="#99ee90" id="totalScore">${Conduct.totalScore}</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">評語代碼一 ：</td>
        	<td align="left" valign="middle" colspan="6">[ ${Conduct.comCode1} ]&nbsp;&nbsp;
        	[ ${Conduct.comName1} ]</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">評語代碼二 ：</td>
        	<td align="left" valign="middle" colspan="6">[ ${Conduct.comCode2} ]&nbsp;&nbsp;
        	[ ${Conduct.comName2} ]</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">評語代碼三 ：</td>
        	<td align="left" valign="middle" colspan="6">[ ${Conduct.comCode3} ]&nbsp;&nbsp;
        	[ ${Conduct.comName3} ]</td>
        </tr>
		</c:forEach>
		</table>
     </td>
     </tr>
	
	
<!-- Begin Content Page Table Footer -->
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='DeleteConfirm'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</c:if>
	
</table>
<script>history.go(1);</script>
<!-- End Content Page Table Footer -->
</form>
