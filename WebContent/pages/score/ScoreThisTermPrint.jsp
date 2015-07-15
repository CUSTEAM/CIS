<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  

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
<c:set var="stuNos" value="${ScoreInPrint.studentNo}" />
<c:set var="stuNames" value="${ScoreInPrint.studentName}"/>
<c:set var="rcount" value="0"/>
中華科技大學 ${ScoreInPrint.schoolYear} 學年第  ${ScoreInPrint.schoolTerm} 學期&nbsp;學期成績<br/>
班級 : ${ScoreInPrint.depClassName} ( ${ScoreInPrint.departClass} )<br/>
科目 : ${ScoreInPrint.cscodeName} ( ${ScoreInPrint.cscode} )<br/>
<c:if test="${ScoreInPrint.now == ''}">
學期成績上傳步驟尚未完成,或老師不是以上傳學期成績之步驟上傳成績,本報表僅供參考!
<br>
</c:if>

${ScoreInPrint.teacherName}老師    請簽名:__________________ <br/>

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
<c:set var="rcount" scope="page" value="0"/>
<c:set var="stuNos" scope="page" value="${ScoreInPrint.studentNo}"/>
<c:set var="stuNames" scope="page" value="${ScoreInPrint.studentName}"/>

<c:forEach items="${ScoreInPrint.score}" var="stuscore">
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
<td>全班人數</td><td>${ScoreInPrint.totalstu}</td>
<td>全班平均</td><td>${ScoreInPrint.avgscore}</td>
<td>及格人數</td><td>${ScoreInPrint.pass}</td>
<td>不及格人數</td><td>${ScoreInPrint.nopass}</td>
<td>時間</td><td>${ScoreInPrint.now}</td>
</tr>
</table>

<br/>
<input type="button" value="列印" onclick="printpage();"/> 
</Body>
</html:html>