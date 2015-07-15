<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>

<html:html locale="true">
<HEAD><TITLE>空白成績冊列印</TITLE>
  <html:base/>
  <LINK href="images/home.css" type=text/css rel=stylesheet>
  <link href="images/decorate.css" type="text/css" rel="stylesheet" media="screen">
  <Link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
<style type="text/css">
.table1 {
	border: 1px solid ;
	font-size: 15px;
}
.text16px {
	font-size: 15px;
}
</style>
<script type="text/javascript">
<!--
function printpage() {
	window.print();
};
//-->
</script>
</HEAD>  
<Body>
<c:set var="stuNos" value="${TchScoreBlankPrint.studentNo}" />
<c:set var="stuNames" value="${TchScoreBlankPrint.studentName}"/>
<c:set var="rcount" value="0"/>
<font class="text16px">
中華科技大學${TchScoreBlankPrint.schoolYear}學年第${TchScoreBlankPrint.schoolTerm}學期&nbsp;&nbsp;學期成績冊
&nbsp;&nbsp;班級: ${TchScoreBlankPrint.depClassName}( ${TchScoreBlankPrint.departClass} )
&nbsp;&nbsp;科目: ${TchScoreBlankPrint.cscodeName}( ${TchScoreBlankPrint.cscode} )</font><br/>
${TchScoreBlankPrint.teacherName}老師    請簽名:__________________ <br/>
<table border=1 cellpadding=0 cellspacing=0 rules=all class="table1">

	<tr>
		<td bgcolor=#add8e6 colspan="2" align="center"><font color=black>成績比例</font></td>
			<c:set var="count" value="1"/>
			<c:choose>
				<c:when test="${TchScoreRateArray != null}">
					<c:set var="rates" value="${TchScoreRateArray}"/>
				</c:when>
				<c:otherwise>
					<c:set var="rates" value="${defaultRate}"/>
				</c:otherwise>
			</c:choose>
					
			<c:forEach items="${rates}" var="rrate">
				<c:if test="${(count % 2) != 0 }">
					<td bgcolor=#add8e6 width="10">${rrate}
						<input name="scorerate" type="hidden" value="${rrate}"></td>
				</c:if>
				<c:if test="${(count % 2) == 0 }">
					<td bgcolor=#add8e6 width="10">${rrate}
						<input name="scorerate" type="hidden" value="${rrate}"></td>
				</c:if>

				<!-- for normal,middle,final exam score ratio setting mode -->
				<c:if test="${count >= 10}">
					<td bgcolor="#add8e6" width="10">&nbsp;</td>
					<c:set var="count" value="${count + 1}"/>
				</c:if>
						
				<c:set var="count" value="${count + 1}"/>
			</c:forEach>
	</tr>
	<tr>
  		<td bgcolor=#99FFFF align="center" width="60"><font color=black>學號</font></td>
    	<td bgcolor=#FFD9D9 align="center" width="50"><font color=black>姓名</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之一</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之二</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之三</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之四</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之五</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之六</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之七</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之八</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之九</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之十</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>平時<br />平均</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />比例</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>期中<br />成績</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>期中<br />比例</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>期末<br />成績</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>期末<br />比例</font></td>
		<td bgcolor=#FFFF99 align="center"><font color=black>學期<br />成績</font></td>
	</tr>
	<c:set var="rcount" scope="page" value="0"/>
	<c:set var="stuNames" scope="page" value="${TchScoreBlankPrint.studentName}"/>

	<c:forEach items="${TchScoreBlankPrint.studentNo}" var="stuNos">
	<tr>
        <td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuNos}</td>
        <td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuNames[rcount]}</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
    </tr>
    <c:if test="${(rcount+1)%5==0}"><tr><td colspan="19" height=5 bgcolor="blue"></td></tr></c:if>

    <c:set var="rcount" value="${rcount+1}"/>
    </c:forEach>
</table>

<br/><br/>
<form action="/CIS/Teacher/ScoreEditAll.do" method="post" name="inputForm">
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="列印" onclick="printpage();">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >&nbsp;&nbsp;');
	</script>
</form>
</Body>
</html:html>