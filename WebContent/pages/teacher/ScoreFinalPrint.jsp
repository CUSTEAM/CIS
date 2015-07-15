<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>

<html:html locale="true">
<HEAD><TITLE>平時及期末成績列印</TITLE>
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
中華科技大學 ${TchScorePrint.schoolYear} 學年第  ${TchScorePrint.schoolTerm} 學期 平時及期末成績<br/>
班級 : ${TchScorePrint.depClassName} ( ${TchScorePrint.departClass} )<br/>
科目 : ${TchScorePrint.cscodeName} ( ${TchScorePrint.cscode} )<br/>
<c:if test="${TchScorePrint.now == ''}">
平時及期末成績上傳步驟尚未完成,本報表僅供參考!
</c:if>
<c:if test="${TchScorePrint.now != ''}">
${TchScorePrint.teacherName}老師    請簽名:__________________ <br/>
</c:if>
<table border=1 cellpadding=0 cellspacing=0 rules=all class="table1">

	<tr>
		<td bgcolor=#add8e6 colspan="2" align="center"><font color=black>平時比例</font></td>
		<c:set var="rcount" value="0"/>
		<c:forEach items="${TchScoreRateArray}" var="rrate">
			<td bgcolor=#add8e6 width="10">${rrate}</td>
			<c:set var="rcount" value="${rcount + 1}"/>
		</c:forEach>
		<c:forEach begin="1" end="${17-rcount}">
			<td bgcolor=#add8e6>&nbsp;</td>
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
		<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />30%</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>期中<br />成績</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>期中<br />30%</font></td>
		<td bgcolor=#99FFFF align="center"><font color=black>期末<br />成績</font></td>
		<td bgcolor=#FFD9D9 align="center"><font color=black>期末<br />40%</font></td>
		<td bgcolor=#FFFF99 align="center"><font color=black>學期<br />成績</font></td>
	</tr>
	<c:set var="rcount" scope="page" value="0"/>
	<c:set var="stuNames" scope="page" value="${TchScorePrint.studentName}"/>
	<c:set var="scr01f" scope="page" value="${TchScorePrint.score01}"/>
	<c:set var="scr02f" scope="page" value="${TchScorePrint.score02}"/>
	<c:set var="scr03f" scope="page" value="${TchScorePrint.score03}"/>
	<c:set var="scr04f" scope="page" value="${TchScorePrint.score04}"/>
	<c:set var="scr05f" scope="page" value="${TchScorePrint.score05}"/>
	<c:set var="scr06f" scope="page" value="${TchScorePrint.score06}"/>
	<c:set var="scr07f" scope="page" value="${TchScorePrint.score07}"/>
	<c:set var="scr08f" scope="page" value="${TchScorePrint.score08}"/>
	<c:set var="scr09f" scope="page" value="${TchScorePrint.score09}"/>
	<c:set var="scr10f" scope="page" value="${TchScorePrint.score10}"/>
	<c:set var="scr1f" scope="page" value="${TchScorePrint.score1}"/>
	<c:set var="scr16f" scope="page" value="${TchScorePrint.score16}"/>
	<c:set var="scr2f" scope="page" value="${TchScorePrint.score2}"/>
	<c:set var="scr17f" scope="page" value="${TchScorePrint.score17}"/>
	<c:set var="scr3f" scope="page" value="${TchScorePrint.score3}"/>
	<c:set var="scr18f" scope="page" value="${TchScorePrint.score18}"/>
	<c:set var="scri" scope="page" value="${TchScorePrint.score}"/>

	<c:forEach items="${TchScorePrint.studentNo}" var="stuNos">
	<tr>
        <td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuNos}</td>
        <td align="left" valign="middle" bgcolor="#FFD9D9" width="50" NOWARP>${stuNames[rcount]}</td>
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
        <td valign="middle" bgcolor="#99FFFF">${scri[rcount]}
        </td>
    </tr>
    <c:set var="rcount" value="${rcount+1}"/>
    </c:forEach>
</table>
<br/>
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