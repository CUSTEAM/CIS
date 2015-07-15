<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>

<html:html locale="true">
<HEAD><TITLE>期中成績上傳列印</TITLE>
  <html:base/>
  <LINK href="images/home.css" type=text/css rel=stylesheet>
  <link href="images/decorate.css" type="text/css" rel="stylesheet" media="screen">
  <Link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
<script type="text/javascript">
<!--
function printpage() {
	window.print();
};
//-->
</script>
</HEAD>  
<Body>
<c:set var="stuNos" value="${TchScoreUploadPrint.studentNo}" />
<c:set var="stuNames" value="${TchScoreUploadPrint.studentName}"/>
<c:set var="rcount" value="0"/>
中華科技大學 ${TchScoreUploadPrint.schoolYear} 學年第  ${TchScoreUploadPrint.schoolTerm} 學期期中考成績<br/>
班級 : ${TchScoreUploadPrint.depClassName} ( ${TchScoreUploadPrint.departClass} )<br/>
科目 : ${TchScoreUploadPrint.cscodeName} ( ${TchScoreUploadPrint.cscode} )<br/>
${TchScoreUploadPrint.teacherName}老師    請簽名:__________________ <br/>
<table border=1 cellpadding=0 cellspacing=0 rules=all>
<tr>
<td>
學號
</td>
<td>
姓名
</td>
<td>
期中考成績
</td>
<td>
&nbsp;&nbsp
</td>
<td>
學號
</td>
<td>
姓名
</td>
<td>
期中考成績
</td>
</tr>
<c:set var="rcount" scope="page" value="0"/>
<c:set var="rcount" scope="page" value="0"/>
<c:set var="stuNos" scope="page" value="${TchScoreUploadPrint.studentNo}"/>
<c:set var="stuNames" scope="page" value="${TchScoreUploadPrint.studentName}"/>

<c:forEach items="${TchScoreUploadPrint.score.scr2f}" var="stuscore">
	<c:set var="count" scope="page" value="${count+1}"/>
    	<c:if test="${count == 1}">
          	<tr>
        	<td>${stuNos[rcount]}</td>
			<td>${stuNames[rcount]}</td>
			<c:if test="${stuscore != -1.0 && stuscore >= 60}">
  				<td align="center">${stuscore}</td>
  			</c:if>
			<c:if test="${stuscore != -1.0 && stuscore < 60}">
  				<td align="center">${stuscore}*</td>
  			</c:if>
			<c:if test="${stuscore == -1.0}">
  				<td align="center">&nbsp;</td>
  			</c:if>
  			<td>&nbsp;&nbsp;</td>
        </c:if>
        
        <c:if test="${count == 2}">
        	<td>${stuNos[rcount]}</td>
			<td>${stuNames[rcount]}</td>
			<c:if test="${stuscore != -1.0 && stuscore >= 60}">
  				<td align="center">${stuscore}</td>
  			</c:if>
			<c:if test="${stuscore != -1.0 && stuscore < 60}">
  				<td align="center">${stuscore}*</td>
  			</c:if>
			<c:if test="${stuscore == -1.0}">
  				<td align="center">&nbsp;</td>
  			</c:if>
        	</tr>
        	<c:set var="count" scope="page" value="0"/>
        </c:if>
        <c:set var="rcount" scope="page" value="${rcount+1}"/>
</c:forEach>
	
    <c:if test="${count == 1}">
       <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
       </tr>
    </c:if>
    
<table border="1">
<tr>
<td>全班人數</td><td>${TchScoreUploadPrint.totalstu}</td>
<td>全班平均</td><td>${TchScoreUploadPrint.avgscore}</td>
<td>及格人數</td><td>${TchScoreUploadPrint.pass}</td>
<td>不及格人數</td><td>${TchScoreUploadPrint.nopass}</td>
<td>時間</td><td>${TchScoreUploadPrint.now}</td>
</tr>
</table>

<br/>
<form action="/CIS/Teacher/ScoreEditAll.do" method="post" name="inputForm">
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="列印" onclick="printpage();">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >&nbsp;&nbsp;');
	</script>
</form>
</Body>
</html:html>