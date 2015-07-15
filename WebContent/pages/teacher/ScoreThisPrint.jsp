<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>

<html:html locale="true">
<HEAD><TITLE>學期成績列印</TITLE>
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
中華科技大學 ${TchScorePrint.schoolYear} 學年第  ${TchScorePrint.schoolTerm} 學期 學期成績<br/>
班級 : ${TchScorePrint.depClassName} ( ${TchScorePrint.departClass} )<br/>
科目 : ${TchScorePrint.cscodeName} ( ${TchScorePrint.cscode} )<br/>
<c:if test="${TchScorePrint.now == ''}">
您並未經由學期成績上傳步驟上傳本學期成績,本報表僅供參考!
</c:if>
<c:if test="${TchScorePrint.now != ''}">
${TchScorePrint.teacherName}老師    請簽名:__________________ <br/>
</c:if>

<table border=1 cellpadding=0 cellspacing=0 rules=all>
<tr>
<td>
學號
</td>
<td>
姓名
</td>
<td>
學期成績
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
學期成績
</td>
</tr>
<c:set var="rcount" scope="page" value="0"/>
<c:set var="stuNos" scope="page" value="${TchScorePrint.studentNo}"/>
<c:set var="stuNames" scope="page" value="${TchScorePrint.studentName}"/>

<c:forEach items="${TchScorePrint.score}" var="stuscore">
	<c:set var="count" scope="page" value="${count+1}"/>
    	<c:if test="${count == 1}">
          	<tr>
        	<td>${stuNos[rcount]}</td>
			<td>${stuNames[rcount]}</td>
  			<td align="center">${stuscore}</td>
  			<td>&nbsp;&nbsp;</td>
        </c:if>
        
        <c:if test="${count == 2}">
        	<td>${stuNos[rcount]}</td>
			<td>${stuNames[rcount]}</td>
  			<td align="center">${stuscore}</td>
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
<td>全班人數</td><td>${TchScorePrint.totalstu}</td>
<td>全班平均</td><td>${TchScorePrint.avgscore}</td>
<td>及格人數</td><td>${TchScorePrint.pass}</td>
<td>不及格人數</td><td>${TchScorePrint.nopass}</td>
<td>時間</td><td>${TchScorePrint.now}</td>
</tr>
</table>

<br/>
請印出成績後簽名送至教務單位
<br/><br/>
<form action="/CIS/Teacher/ScoreEditAll.do" method="post" name="inputForm">
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="列印" onclick="printpage();">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >&nbsp;&nbsp;');
	</script>
</form>
</Body>
</html:html>