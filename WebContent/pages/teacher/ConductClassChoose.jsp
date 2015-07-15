<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/CodeSelection.js" %>

<style type="text/css">
<!--
.input {
	width:20px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>

<c:if test="${ConductUploadPrint != null}">
<script language="javaScript">
<!-- 
function resetTimeOut(){
	if(myTimeOut != null) {
		globalTimeOut = myTimeOut;
		myTimeOut = null;
	}
}

function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ConductUploadPrint.jsp","操行成績上傳列印",
	"width=750,height=600,scrollbars=yes,resizable=no,left=0,top=0");
};

resetTimeOut;
window.onload=subwin2print;
// -->
</script>
</c:if>

<form action="/CIS/Teacher/StudConductUpload.do" method="post" name="upForm">
<input type="hidden" name="opmode" value="" id="opmode">	

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudConductUpload" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->
	<table cellspacing="5" class="empty-border" width="100%">
	<tr>
	<td>
	<font color="red">因計算操行及獎懲加減分耗時，按下確定按鈕後，請稍待!!!</font>
	</td>
	</tr>
	<c:if test="${not empty studConductUploadInit}">
		<c:set var="rcnt" value="1"/>
		<c:set var="clazzA" value="${studConductUploadInit}"/>
		<c:forEach items="${clazzA}" var="clazz">
			<c:choose>
			<c:when test="${rcnt==1}">
			<tr>
				<td>
      			<input type="radio" name="selclass" value="${clazz.clazzNo}"/>
      			${clazz.clazzName}
      			<c:choose>
      			<c:when test="${clazz.kind == 'Tutor'}">
      				<c:out value=" (導師)"/>
      			</c:when>
      			<c:when test="${clazz.kind == 'Chairman'}">
      				<c:out value=" (系主任)"/>
      			</c:when>
      			<c:when test="${clazz.kind == 'Drillmaster'}">
      				<c:out value=" (教官)"/>
      			</c:when>
      			</c:choose>
      			</td>
      			<c:set var="rcnt" value="2"/>
			</c:when>
			<c:when test="${rcnt==2}">
				<td>
      			<input type="radio" name="selclass" value="${clazz.clazzNo}"/>
      			${clazz.clazzName}
      			<c:choose>
      			<c:when test="${clazz.kind == 'Tutor'}">
      				<c:out value=" (導師)"/>
      			</c:when>
      			<c:when test="${clazz.kind == 'Chairman'}">
      				<c:out value=" (系主任)"/>
      			</c:when>
      			<c:when test="${clazz.kind == 'Drillmaster'}">
      				<c:out value=" (教官)"/>
      			</c:when>
      			</c:choose>
      			</td>
			</tr>
			<c:set var="rcnt" value="1"/>
			</c:when>
			</c:choose>
		</c:forEach>
	</c:if>
	
	<c:if test="${empty studConductUploadInit}">
		<tr>
		<td>
		<br>
		<font color="red">您沒有任何負責的班級需要上傳操行成績!</font></td>
		<br>
		</tr>
	</c:if>
	</table>

	  </td>
	</tr>		
	
	<c:if test="${not empty studConductUploadInit}">
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" id="ok" onClick="rstopmode(1);">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="rstopmode(1);">');
	</script>
	</c:if>
	<c:if test="${empty studConductUploadInit}">
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Back'/>" id="back" onClick="rstopmode(1);">');
	</script>
	</c:if>
</table>
</form>
<script  language="javascript">
//<!--
	function rstopmode(mode){
		var obj = document.getElementById("opmode");
		var botton1 = document.getElementById("ok");
		obj.value="";
		//document.upForm.submit();
		//botton1.disabled = true;
		
		
	}
//-->
</script>