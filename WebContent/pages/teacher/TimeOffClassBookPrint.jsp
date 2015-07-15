<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>

<html:html locale="true">
<HEAD><TITLE>點名單列印</TITLE>
  <html:base/>
  <LINK href="images/home.css" type=text/css rel=stylesheet>
  <link href="images/decorate.css" type="text/css" rel="stylesheet" media="screen">
  <Link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
<style type="text/css">
.table1 {
	border: 1px solid ;
	font-size:13px;
}
.td1{
	font-size:12px;
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
中華科技大學 ${TFClassBookPrintInfo.schoolYear} 學年第  ${TFClassBookPrintInfo.schoolTerm} 學期&nbsp;上課點名單
<br/>
<tr><td class="td1">
<div style="font-size:13px;">
班級 : ${TFClassBookPrintInfo.depClassName}(${TFClassBookPrintInfo.Oid}) ( ${TFClassBookPrintInfo.departClass} )&nbsp;&nbsp;&nbsp;
科目 : ${TFClassBookPrintInfo.cscodeName} ( ${TFClassBookPrintInfo.cscode} )<br/>
授課老師：${TFClassBookPrintInfo.teacherName}
</div>
</td></tr>
<tr>
<td align="center">
<table width="100%" cellpadding=0 cellspacing=0 rules=all class="table1">
	<tr>
		<td bgcolor=#add8e6 rowspan="2" align="center"><font color=black>學號</font></td>
		<td bgcolor=#add8e6 rowspan="2" align="center"><font color=black>姓名</font></td>
		<td bgcolor=#add8e6 colspan="16" align="center"><font color=black>日&nbsp;&nbsp;期</font></td>
		<td bgcolor=#add8e6 rowspan="2" align="center"><font color=black>備註</font></td>
	</tr>
	<tr>
		<c:forEach begin="1" end="16">
		<td bgcolor=#add8e6 align="center" class="td1">月/日<br>&nbsp;/&nbsp;</td>
		</c:forEach>
	</tr>
					
	<c:forEach items="${classBookPrint.classBook}" var="cb">
	<tr>
		<td width="120">${cb.studentNo}</td>
		<td width="120">${cb.studentName}</td>
		<c:forEach begin="1" end="16">
			<td width="50">&nbsp;</td>
		</c:forEach>
		<c:if test="${cb.memo != ''}">
		<td width="80">${cb.memo}</td>
		</c:if>
		<c:if test="${cb.memo == ''}">
		<td width="80">&nbsp;</td>
		</c:if>
	</tr>
	</c:forEach>
</table>
</td>
</tr>
<br/>
<form action="/CIS/Teacher/TimeOffClassBook.do" method="post" name="inputForm">
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="列印" onclick="printpage();">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Back' bundle='SCR'/>" >&nbsp;&nbsp;');
	</script>
</form>

</Body>
</html:html>