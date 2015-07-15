<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
// -->
</style>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/StudConduct.do" method="post" name="cdForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudConductMaintain" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" colspan="6" align="center" valign="middle">學號：
        <c:if test="${StudConductInit.studentNo != ''}">
          <input name="studentNo" type="text" size="7" maxlength="10" value="${StudBonusPenaltyInit.studentNo}" />
        </c:if>
        <c:if test="${StudConductInit.studentNo == ''}">
          <input name="studentNo" type="text" size="7" maxlength="10" />
        </c:if>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Create'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudConductList != null}" >
	    <tr><td height="10"></td></tr>
	    <c:if test="${ConductStuMap != null}">
	    	<tr><td height="10"><font class="blue_13">&nbsp;
		    	<c:out value="${ConductStuMap.studentNo}"/>&nbsp;&nbsp;	    	
	    		<c:out value="${ConductStuMap.studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<c:out value="${ConductStuMap.departClass}" />&nbsp;&nbsp;
	    		<c:out value="${ConductStuMap.depClassName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		基本分：82</font>
	    	</td></tr>
	    </c:if>
	    
		<tr>
		    <td>
		<table width="100%">
			<tr>
			<td id="students">
			<table width="90%" cellpadding="2" cellspacing="3">
				<tr>
				<td width="13%" align="center" bgcolor="#add8e6">導師</td>
				<td width="13%" align="center" bgcolor="#add8e6">系主任</td>
				<td width="13%" align="center" bgcolor="#add8e6">教官</td>
				<td width="13%" align="center" bgcolor="#add8e6">勤惰</td>
				<td width="16%" align="center" bgcolor="#add8e6">獎懲</td>
				<td width="16%" align="center" bgcolor="#add8e6">評審會</td>
				<td width="16%" align="center" bgcolor="#add8e6">合計</td>
				</tr>
				<c:set var="studentNo" value="${StudBonusPenaltyEdit.studentNo}"/>
				<c:set var="studentName" value="${StudBonusPenaltyEdit.studentName}"/>
				<c:set var="departClass" value="${StudBonusPenaltyEdit.departClass}"/>
				<c:set var="kind1" value="${StudBonusPenaltyEdit.kind1}"/>
       			<c:set var="cnt1" value="${StudBonusPenaltyEdit.cnt1}"/>
      			<c:set var="kind2" value="${StudBonusPenaltyEdit.kind2}"/>
      			<c:set var="cnt2" value="${StudBonusPenaltyEdit.cnt2}"/>
      			
     			<c:set var="rcnt" value="0"/>
     			
      			<c:forEach items="${StudConductList}" var="studConduct">
				<tr>
				<td width="13%" align="center" valign="middle" bgcolor="#99ee90">${studConduct.teacherScore}</td>
				<td width="13%" align="center" valign="middle" bgcolor="#99ee90">${studConduct.deptheaderScore}</td>
				<td width="13%" align="center" valign="middle" bgcolor="#99ee90">${studConduct.militaryScore}</td>
        		<td width="13%" align="center" valign="middle" bgcolor="#99ee90">${studConduct.dilgScore}</td>
        		<td width="16%" align="center" valign="middle" bgcolor="#99ee90">${studConduct.desdScore}</td>
        		<td width="16%" align="center" valign="middle" bgcolor="#99ee90">${studConduct.meetingScore}</td>
        		<td width="16%" align="center" valign="middle" bgcolor="#99ee90">${studConduct.totalScore}</td>
				</tr>
				<tr>
				<td colspan="7">
					<table width=100%">
					<tr>
						<td width="15%" align="center" bgcolor="#dcdcdc">評語代碼一</td>
						<td width="8%" align="left" bgcolor="#dcdcdc">［<c:out value="${studConduct.comCode1}" />］</td>
						<td align="left">［<c:out value="${studConduct.comName1}" />］</td>
					</tr>
					<tr>
						<td width="15%" align="center" bgcolor="#dcdcdc">評語代碼二</td>
						<td width="8%" align="left" bgcolor="#dcdcdc">［<c:out value="${studConduct.comCode2}" />］</td>
						<td align="left">［<c:out value="${studConduct.comName2}" />］</td>
					</tr>
					<tr>
						<td width="15%" align="center" bgcolor="#dcdcdc">評語代碼三</td>
						<td width="8%" align="left" bgcolor="#dcdcdc">［<c:out value="${studConduct.comCode3}" />］</td>
						<td align="left">［<c:out value="${studConduct.comName3}" />］</td>
					</tr>
					</table>
				</td>
				</tr>
				</c:forEach>
				</table>
				<br><center>
				
			</td>
			</tr>
		</table>
	      	</td>
	    </tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" >');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
