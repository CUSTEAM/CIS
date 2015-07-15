<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
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
function setcscode(cscode, dtimeoid)
{
	var ips = document.getElementsByName("cscode");
	var ips2 = document.getElementsByName("dtimeoid");

  	for(var i=0; i<=ips.length-1;i++)
  	{
      	ips[i].value=cscode;
  	}
  	for(var i=0; i<=ips2.length-1;i++)
  	{
      	ips2[i].value=dtimeoid;
  	}
  	// alert(ips[0].value + " , " + cscode);
};



//-->
</script>
<c:if test="${TchScoreMidPrint != null}">
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ScoreMidTermPrint.jsp","期中成績上傳列印",
	"width=600,height=600,scrollbars=yes,resizable=no,left=0,top=0");
};
window.onload=subwin2print;
// -->
</script>
</c:if>
<!-- <html:form action="/Score/ScoreHist" method="post"> -->
<form action="/CIS/Teacher/ScoreMidTerm.do" method="post" name="inputForm">
<input name="scoretype" type="hidden" value="${TchScoreMidInfo.scoretype}"/>
<input name="cscode" type="hidden" value=""/>
<input name="dtimeoid" type="hidden" value=""/>
<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('教師資訊系統');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex">
        <c:out value="教師輸入成績系統"/> -&gt;<span class="style2"> 期中成績上傳
        </span></td>
  	    </tr>
      <tr>
        <td  height="35" colspan="6" align="left" valign="middle" bgcolor="#CCCCFF">
       	<font color="blue">${TchScoreMidInfo.teacherName}&nbsp;老師</font>:本程式為上傳期中考成績<br/><br/>
　　		請點選要輸入的班級, 再按底下的鍵就可以了
        </td>
      </tr>
      <tr>
      	<td>
		&nbsp;</td>
      </tr>
      <tr>
      	<td>
		  <c:if test="${TchScoreMidInChoose != null}" >
      	   <c:forEach items="${TchScoreMidInChoose}" var="classes">
      			<input type="radio" name="selclass" value="${classes.departClass}"  onclick='setcscode("${classes.cscode}","${classes.oid}");'/>
      			${classes.className2}(${classes.oid})&nbsp;&nbsp;${classes.chiName2}<br/>
          </c:forEach>
          </c:if>
		  <c:if test="${TchScoreMidInChoose == null}" >
		  	您這學期沒有授課班級!!!
		  </c:if>
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
