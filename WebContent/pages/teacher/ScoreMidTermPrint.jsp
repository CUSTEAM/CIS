<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  

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
<c:set var="stuNos" value="${TchScoreMidPrint.studentNo}" />
<c:set var="stuNames" value="${TchScoreMidPrint.studentName}"/>
<c:set var="rcount" value="0"/>
中華科技大學 ${TchScoreMidPrint.schoolYear} 學年第  ${TchScoreMidPrint.schoolTerm} 學期期中考成績<br/>
班級 : ${TchScoreMidPrint.depClassName} ( ${TchScoreMidPrint.departClass} )<br/>
科目 : ${TchScoreMidPrint.cscodeName} ( ${TchScoreMidPrint.cscode} )<br/>
${TchScoreMidPrint.teacherName}老師    請簽名:__________________ <br/>
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
<c:set var="stuNos" scope="page" value="${TchScoreMidPrint.studentNo}"/>
<c:set var="stuNames" scope="page" value="${TchScoreMidPrint.studentName}"/>

<c:forEach items="${TchScoreMidPrint.score}" var="stuscore">
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
<td>全班人數</td><td>${TchScoreMidPrint.totalstu}</td>
<td>全班平均</td><td>${TchScoreMidPrint.avgscore}</td>
<td>及格人數</td><td>${TchScoreMidPrint.pass}</td>
<td>不及格人數</td><td>${TchScoreMidPrint.nopass}</td>
<td>時間</td><td>${TchScoreMidPrint.now}</td>
</tr>
</table>

<br/>
<input type="button" value="列印" onclick="printpage();"/> 
</Body>
</html:html>