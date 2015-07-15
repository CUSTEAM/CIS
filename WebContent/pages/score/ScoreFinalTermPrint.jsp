<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  

<html:html locale="true">
<HEAD><TITLE>平時及期末成績列印</TITLE>
  <html:base/>
  <LINK href="images/home.css" type=text/css rel=stylesheet>
  <link href="images/decorate.css" type="text/css" rel="stylesheet" media="screen">
  <Link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
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
<c:set var="stuNos" value="${ScoreInPrint.studentNo}" />
<c:set var="stuNames" value="${ScoreInPrint.studentName}"/>
<c:set var="rcount" value="0"/>
中華科技大學 ${ScoreInPrint.schoolYear} 學年第  ${ScoreInPrint.schoolTerm} 學期&nbsp;平時及期末成績
<br/>
班級 : ${ScoreInPrint.depClassName} ( ${ScoreInPrint.departClass} )<br/>
科目 : ${ScoreInPrint.cscodeName} ( ${ScoreInPrint.cscode} )<br/>
<c:if test="${ScoreInPrint.now == ''}">
平時及期末成績上傳步驟尚未完成,本報表僅供參考!
<br>
</c:if>

${ScoreInPrint.teacherName}老師    請簽名:__________________ <br/>

<table border=1 cellpadding=0 cellspacing=0 rules=all class="table1">

	<tr>
		<td bgcolor=#add8e6 colspan="2" align="center"><font color=black>成績比例</font></td>
			<c:set var="count" value="1"/>
			<c:choose>
				<c:when test="${ScoreRateArray != null}">
					<c:set var="rates" value="${ScoreRateArray}"/>
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
	<c:set var="stuNames" scope="page" value="${ScoreInPrint.studentName}"/>
	<c:set var="scr01f" scope="page" value="${ScoreInPrint.score01}"/>
	<c:set var="scr02f" scope="page" value="${ScoreInPrint.score02}"/>
	<c:set var="scr03f" scope="page" value="${ScoreInPrint.score03}"/>
	<c:set var="scr04f" scope="page" value="${ScoreInPrint.score04}"/>
	<c:set var="scr05f" scope="page" value="${ScoreInPrint.score05}"/>
	<c:set var="scr06f" scope="page" value="${ScoreInPrint.score06}"/>
	<c:set var="scr07f" scope="page" value="${ScoreInPrint.score07}"/>
	<c:set var="scr08f" scope="page" value="${ScoreInPrint.score08}"/>
	<c:set var="scr09f" scope="page" value="${ScoreInPrint.score09}"/>
	<c:set var="scr10f" scope="page" value="${ScoreInPrint.score10}"/>
	<c:set var="scr16f" scope="page" value="${ScoreInPrint.score16}"/>
	<c:set var="scr17f" scope="page" value="${ScoreInPrint.score17}"/>
	<c:set var="scr18f" scope="page" value="${ScoreInPrint.score18}"/>
	<c:set var="scrf" scope="page" value="${ScoreInPrint.score}"/>
	<c:set var="scr1f" scope="page" value="${ScoreInPrint.score1}"/>
	<c:set var="scr2f" scope="page" value="${ScoreInPrint.score2}"/>
	<c:set var="scr3f" scope="page" value="${ScoreInPrint.score3}"/>

	<c:forEach items="${ScoreInPrint.studentNo}" var="stuNos">
	<tr>
        <td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuNos}</td>
        <td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuNames[rcount]}</td>
        <td valign="middle" bgcolor="#99FFFF">${scr01f[rcount]}
        </td>
        <td valign="middle" bgcolor="#FFD9D9">${scr02f[rcount]}
        </td>
        <td valign="middle" bgcolor="#99FFFF">${scr03f[rcount]}
        </td>
        <td valign="middle" bgcolor="#FFD9D9">${scr04f[rcount]}
        </td>
        <td valign="middle" bgcolor="#99FFFF">${scr05f[rcount]}
        </td>
        <td valign="middle" bgcolor="#FFD9D9">${scr06f[rcount]}
        </td>
        <td valign="middle" bgcolor="#99FFFF">${scr07f[rcount]}
        </td>
        <td valign="middle" bgcolor="#FFD9D9">${scr08f[rcount]}
        </td>
        <td valign="middle" bgcolor="#99FFFF">${scr09f[rcount]}
        </td>
        <td valign="middle" bgcolor="#FFD9D9">${scr10f[rcount]}
       </td>
        <td valign="middle" bgcolor="#99FFFF">${scr1f[rcount]}
        </td>
        <td valign="middle" bgcolor="#FFD9D9">${scr16f[rcount]}
        </td>
        <td valign="middle" bgcolor="#99FFFF">${scr2f[rcount]}
        </td>
        <td valign="middle" bgcolor="#FFD9D9">${scr17f[rcount]}
        </td>
        <td valign="middle" bgcolor="#99FFFF">${scr3f[rcount]}
        </td>
        <td valign="middle" bgcolor="#FFD9D9">${scr18f[rcount]}
        </td>
        <td valign="middle" bgcolor="#99FFFF">${scrf[rcount]}
        </td>
    </tr>
    <c:set var="rcount" value="${rcount+1}"/>
    </c:forEach>
</table>
<br/>
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