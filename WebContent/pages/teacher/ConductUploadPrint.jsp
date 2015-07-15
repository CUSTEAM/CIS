<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  

<html:html locale="true">
<HEAD><TITLE>操行成績上傳列印</TITLE>
  <html:base/>
  <LINK href="images/home.css" type=text/css rel=stylesheet>
  <link href="images/decorate.css" type="text/css" rel="stylesheet" media="screen">
  <Link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
<script type="text/javascript">
//<!--
function printpage() {
	window.print();
};
//-->
</script>
<style type="text/css">
.table1 {
	border: 1px solid ;
	font-size: 15px;
}
.td1 {
	font-size: 11px;
}
</style>
</HEAD>  
<Body>
<c:set var="rcount" value="0"/>
${ConductUploadPrint.schoolYear} 學年度第  ${ConductUploadPrint.schoolTerm} 學期&nbsp;操行加減分
<c:if test="${ConductUploadPrint.uploadMode == 'Teacher'}">
	<c:out value="  導師：${ConductUploadPrint.Name}"/>
</c:if>
<c:if test="${ConductUploadPrint.uploadMode == 'Chairman'}">
	<c:out value="  系主任：${ConductUploadPrint.Name}"/>
</c:if>
<c:if test="${ConductUploadPrint.uploadMode == 'Drillmaster'}">
	<c:out value="  教官：${ConductUploadPrint.Name}"/>
</c:if>
<c:out value="    班級 : ${ConductUploadPrint.deptClassName}"/>
<br/>

<table border=1 cellpadding=0 cellspacing=0 rules=all class="table1">
	<tr>
		<td width="60" valign="middle" align="left" bgcolor="#add8e6">學號</td>
		<td width="50" valign="middle" align="left" bgcolor="#add8e6">姓名</td>
		<td width="30" valign="middle" align="center" bgcolor="#add8e6">導師</td>
		<td width="30" valign="middle" align="center" bgcolor="#add8e6">系主任</td>
		<td width="30" valign="middle" align="center" bgcolor="#add8e6">教官</td>
		<td width="30" valign="middle" align="center" bgcolor="#add8e6">勤惰</td>
		<td width="30" valign="middle" align="center" bgcolor="#add8e6">獎懲</td>
		<td width="30" valign="middle" align="center" bgcolor="#add8e6">評審會</td>
		<td width="35" valign="middle" align="center" bgcolor="#add8e6">總分</td>
		<td valign="middle" align="left" bgcolor="#add8e6">評語一 </td>
		<td valign="middle" align="left" bgcolor="#add8e6">評語二 </td>
		<td valign="middle" align="left" bgcolor="#add8e6">評語三 </td>
    </tr>

	<c:set var="rcount" scope="page" value="0"/>

	<c:forEach items="${ConductUploadPrint.Conduct2Print}" var="conduct">
	<tr>
        <td align="left" valign="middle" bgcolor="#99FFFF" width="60">${conduct.studentNo}</td>
        <td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${conduct.studentName}</td>
        <td align="right" valign="middle" bgcolor="#99FFFF">${conduct.teacherScore}</td>
        <td align="right" valign="middle" bgcolor="#FFD9D9">${conduct.deptheaderScore}</td>
        <td align="right" valign="middle" bgcolor="#99FFFF">${conduct.militaryScore}</td>
        <td align="right" valign="middle" bgcolor="#FFD9D9">${conduct.dilgScore}</td>
        <td align="right" valign="middle" bgcolor="#99FFFF">${conduct.desdScore}</td>
        <td align="right" valign="middle" bgcolor="#FFD9D9">${conduct.meetingScore}</td>
        <td align="right" valign="middle" bgcolor="#99FFFF">${conduct.totalScore}</td>
        <td align="left" valign="middle" bgcolor="#FFD9D9" class="td1">${conduct.comCode1}&nbsp;${conduct.comName1}</td>
        <td align="left" valign="middle" bgcolor="#99FFFF" class="td1">${conduct.comCode2}&nbsp;${conduct.comName2}</td>
        <td align="left" valign="middle" bgcolor="#FFD9D9" class="td1">${conduct.comCode3}&nbsp;${conduct.comName3}</td>
    </tr>
    <c:set var="rcount" value="${rcount+1}"/>
    </c:forEach>
</table>
<br/>
<br/>
<input type="button" value="列印" onclick="printpage();"/> 
</Body>
</html:html>