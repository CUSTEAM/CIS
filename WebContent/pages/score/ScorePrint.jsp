<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/score/include/scoreinput.js" %>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.div1 {
	font-size: 13px;
	font-weight: normal;
	height: auto;
	width: auto;
	border: 1px solid #FF6600;
	margin: 3px;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>

<script type="text/javascript">
<!--
function setcscode(cscode)
{
	var ips = document.getElementsByName("cscode");

  	for(var i=0; i<=ips.length-1;i++)
  	{
      	ips[i].value=cscode;
  	}
  	// alert(ips[0].value + " , " + cscode);
};


//-->
</script>

<c:if test="${ScoreInPrint != null && ScoreInPrint.scoretype == '1'}">
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/score/ScoreMidTermPrint.jsp","期中成績列印",
	"width=600,height=600,scrollbars=yes,resizable=no,left=0,top=0");
};
window.onload=subwin2print;
// -->
</script>
</c:if>

<c:if test="${ScoreInPrint != null && ScoreInPrint.scoretype == '2' && ScoreInPrint.now == ''}">
<script language="javaScript">
<!-- 
//alert("平時及期末成績上傳步驟尚未完成,\n請老師先上傳再補列印!\n如有疑問請洽電算中心!");
alert("!!! 請注意 !!!\n老師尚未完成平時及期末成績上傳步驟!");
function subwin2print() {
	subwin = window.open("/CIS/pages/score/ScoreFinalTermPrint.jsp","平時及期末成績列印",
	"width=750,height=600,scrollbars=yes,resizable=no,left=0,top=0");
};

window.onload=subwin2print;
// -->
</script>
</c:if>
<c:if test="${ScoreInPrint != null && ScoreInPrint.scoretype == '2' && ScoreInPrint.now != ''}">
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/score/ScoreFinalTermPrint.jsp","平時及期末成績列印",
	"width=750,height=600,scrollbars=yes,resizable=no,left=0,top=0");
};

window.onload=subwin2print;
// -->
</script>
</c:if>

<c:if test="${ScoreInPrint != null && ScoreInPrint.scoretype == '3' && ScoreInPrint.now == ''}">
<script language="javaScript">
<!-- 
//alert("學期成績上傳步驟尚未完成,\n或老師不是以上傳學期成績之步驟上傳成績,\n請老師先上傳再補列印!\n如有疑問請洽電算中心!");
alert("!!! 請注意 !!!\n老師尚未完成學期成績上傳步驟!\n或老師不是以上傳學期成績之步驟上傳成績!");
function subwin2print() {
	subwin = window.open("/CIS/pages/score/ScoreThisTermPrint.jsp","學期成績列印",
	"width=750,height=600,scrollbars=yes,resizable=no,left=0,top=0");
};

window.onload=subwin2print;
// -->
</script>
</c:if>
<c:if test="${ScoreInPrint != null && ScoreInPrint.scoretype == '3' && ScoreInPrint.now != ''}">
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/score/ScoreThisTermPrint.jsp","學期成績列印",
	"width=750,height=600,scrollbars=yes,resizable=no,left=0,top=0");
};

window.onload=subwin2print;
// -->
</script>
</c:if>

<!-- <html:form action="/Score/ScoreHist" method="post"> -->
<form action="/CIS/Score/ScorePrint.do" method="post" name="inputForm">
<input name="scoretype" type="hidden" value="${ScoreInPrintInit.scoretype}"/>
<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('成績資訊系統');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex">
        <c:out value="成績列印系統"/> -&gt;<span class="style2">
        	<c:if test="${ScoreInPrint.scoretype == '1'}"> 期中成績列印</c:if>
        	<c:if test="${ScoreInPrint.scoretype == '2'}"> 平時及期末成績列印</c:if>
        </span></td>
  	    </tr>
      <tr>
        <td  height="35" colspan="6" align="left" valign="middle" bgcolor="#CCCCFF">
　　			請點選要列印的班級, 再按底下的鍵就可以了<br><br>
			<div class="div1"><font color=red>如果沒有出現列印成績的視窗,
			表示您的瀏覽器有開啟阻擋彈跳視窗的功能,
			請解除或暫時允許彈跳視窗功能,謝謝!</font></div>
			<br>
        </td>
      	</tr>
      <tr>
      	<td>
		&nbsp;</td>
      </tr>
      <tr>
        <td height="25" colspan="6" align="left" valign="bottom">
       	<font class="blue_13">請依序選擇校區、學制、科系、班級和科目</font>
       	</td>
      </tr>
      <tr><td>
      	班別：
	  		<c:set var="campusSel" value="${ScoreInPrintInit.campus}"/>
	  		<c:set var="schoolSel" value="${ScoreInPrintInit.school}"/>
	  		<c:set var="deptSel"   value="${ScoreInPrintInit.dept}"/>
	  		<c:set var="classSel"  value="${ScoreInPrintInit.clazz}"/>
			<%@ include file="/pages/score/include/ClassSelect2.jsp" %>
	  		
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>科目：
          <select name="cscode" id="cscode" onChange="setdtimeoid(this);">
          <option value="">請選擇科目</option>
          <c:if test="${ScorePrintInfo != null }">
          	<c:forEach items="${ScorePrintInfo}" var="cscodes">
          		<c:if test="${ScoreInPrintInit.cscode==cscodes.cscode}">
          			<option value='${cscodes.cscode}_${cscodes.dtimeoid}' selected="selected">${cscodes.chiName}(${cscodes.dtimeoid})</option>
          		</c:if>
          		<c:if test="${ScoreInPrintInit.cscode!=cscodes.cscode}">
          			<option value='${cscodes.cscode}_${cscodes.dtimeoid}'>${cscodes.chiName}(${cscodes.dtimeoid})</option>
          		</c:if>
          	</c:forEach>
          </c:if>
          <c:if test="${ScorePrintInfo == null }">
          	<option value=""></option>
          </c:if>
          </select>
        </td>
      </tr>
 	</table>
		</td>		
	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK' bundle='SCR'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >');
	</script>
<!-- Begin Content Page Table Footer -->
				
</table>
<!-- End Content Page Table Footer -->
</form>
<!-- </html:form> -->
