<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>

<html:html locale="true">
<HEAD><TITLE>期中期末成績上傳列印</TITLE>
  <html:base/>
  <LINK href="images/home.css" type=text/css rel=stylesheet>
  <link href="images/decorate.css" type="text/css" rel="stylesheet" media="screen">
  <Link href="score/cust.css" type="text/css" rel="stylesheet" media="screen">
<style type="text/css">
.table1 {
	border: 1px solid ;
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
<c:set var="stuNos" value="${TchScoreUploadPrint.studentNo}" />
<c:set var="stuNames" value="${TchScoreUploadPrint.studentName}"/>
<c:set var="rcount" value="0"/>
中華科技大學 ${TchScoreUploadPrint.schoolYear} 學年第  ${TchScoreUploadPrint.schoolTerm} 學期&nbsp;
<c:if test="${TchScoreUploadPrint.scoretype == '1'}">
	<c:out value="期中考成績"/>
</c:if>
<c:if test="${TchScoreUploadPrint.scoretype == '2'}">
	<c:out value="平時及期末成績"/>
</c:if>
<br/>
班級 : ${TchScoreUploadPrint.depClassName} ( ${TchScoreUploadPrint.departClass} )<br/>
科目 : ${TchScoreUploadPrint.cscodeName} ( ${TchScoreUploadPrint.cscode} )<br/>
<c:if test="${TchScoreUploadPrint.now == ''}">
平時及期末成績上傳步驟尚未完成,本報表僅供參考!
</c:if>
<c:if test="${TchScoreUploadPrint.now != ''}">
${TchScoreUploadPrint.teacherName}老師    請簽名:__________________ <br/>
</c:if>
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
    	<td bgcolor=#FFD9D9 align="center" width="50" NOWARP><font color=black>姓名</font></td>
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
	<c:set var="stuNames" scope="page" value="${TchScoreUploadPrint.studentName}"/>
	<c:set var="scr01f" scope="page" value="${TchScoreUploadPrint.score.scr01f}"/>
	<c:set var="scr02f" scope="page" value="${TchScoreUploadPrint.score.scr02f}"/>
	<c:set var="scr03f" scope="page" value="${TchScoreUploadPrint.score.scr03f}"/>
	<c:set var="scr04f" scope="page" value="${TchScoreUploadPrint.score.scr04f}"/>
	<c:set var="scr05f" scope="page" value="${TchScoreUploadPrint.score.scr05f}"/>
	<c:set var="scr06f" scope="page" value="${TchScoreUploadPrint.score.scr06f}"/>
	<c:set var="scr07f" scope="page" value="${TchScoreUploadPrint.score.scr07f}"/>
	<c:set var="scr08f" scope="page" value="${TchScoreUploadPrint.score.scr08f}"/>
	<c:set var="scr09f" scope="page" value="${TchScoreUploadPrint.score.scr09f}"/>
	<c:set var="scr10f" scope="page" value="${TchScoreUploadPrint.score.scr10f}"/>
	<c:set var="scr1f" scope="page" value="${TchScoreUploadPrint.score.scr1f}"/>
	<c:set var="scr16f" scope="page" value="${TchScoreUploadPrint.score.scr16f}"/>
	<c:set var="scr2f" scope="page" value="${TchScoreUploadPrint.score.scr2f}"/>
	<c:set var="scr17f" scope="page" value="${TchScoreUploadPrint.score.scr17f}"/>
	<c:set var="scr3f" scope="page" value="${TchScoreUploadPrint.score.scr3f}"/>
	<c:set var="scr18f" scope="page" value="${TchScoreUploadPrint.score.scr18f}"/>
	<c:set var="scri" scope="page" value="${TchScoreUploadPrint.score.scri}"/>

	<c:forEach items="${TchScoreUploadPrint.studentNo}" var="stuNos">
	<tr>
        <td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuNos}</td>
        <td align="left" valign="middle" bgcolor="#FFD9D9" width="50" NOWARP>${stuNames[rcount]}</td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scr01f[rcount] != -1.0}">${scr01f[rcount]}</c:if>
        <c:if test="${scr01f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#FFD9D9">
        <c:if test="${scr02f[rcount] != -1.0}">${scr02f[rcount]}</c:if>
        <c:if test="${scr02f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scr03f[rcount] != -1.0}">${scr03f[rcount]}</c:if>
        <c:if test="${scr03f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#FFD9D9">
        <c:if test="${scr04f[rcount] != -1.0}">${scr04f[rcount]}</c:if>
        <c:if test="${scr04f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scr05f[rcount] != -1.0}">${scr05f[rcount]}</c:if>
        <c:if test="${scr05f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#FFD9D9">
        <c:if test="${scr06f[rcount] != -1.0}">${scr06f[rcount]}</c:if>
        <c:if test="${scr06f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scr07f[rcount] != -1.0}">${scr07f[rcount]}</c:if>
        <c:if test="${scr07f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#FFD9D9">
        <c:if test="${scr08f[rcount] != -1.0}">${scr08f[rcount]}</c:if>
        <c:if test="${scr08f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scr09f[rcount] != -1.0}">${scr09f[rcount]}</c:if>
        <c:if test="${scr09f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#FFD9D9">
        <c:if test="${scr10f[rcount] != -1.0}">${scr10f[rcount]}</c:if>
        <c:if test="${scr10f[rcount] == -1.0}">&nbsp;</c:if>
       </td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scr1f[rcount] != -1.0}">${scr1f[rcount]}</c:if>
        <c:if test="${scr1f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#FFD9D9">
        <c:if test="${scr16f[rcount] != -1.0}">${scr16f[rcount]}</c:if>
        <c:if test="${scr16f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scr2f[rcount] != -1.0}">${scr2f[rcount]}</c:if>
        <c:if test="${scr2f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#FFD9D9">
        <c:if test="${scr17f[rcount] != -1.0}">${scr17f[rcount]}</c:if>
        <c:if test="${scr17f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scr3f[rcount] != -1.0}">${scr3f[rcount]}</c:if>
        <c:if test="${scr3f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#FFD9D9">
        <c:if test="${scr18f[rcount] != -1.0}">${scr18f[rcount]}</c:if>
        <c:if test="${scr18f[rcount] == -1.0}">&nbsp;</c:if>
        </td>
        <td valign="middle" bgcolor="#99FFFF">
        <c:if test="${scri[rcount] < 60 && scri[rcount] != -1.0}">
        	<font color="red">${scri[rcount]}&nbsp;*</font>
        </c:if>
        <c:if test="${scri[rcount] >= 60 && scri[rcount] != -1.0}">
          	<font color="black">${scri[rcount]}</font>
        </c:if>
        <c:if test="${scri[rcount] == -1.0}">
          	&nbsp;
        </c:if>
        </td>
    </tr>
    <c:set var="rcount" value="${rcount+1}"/>
    </c:forEach>
</table>
<br/>
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