<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

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

.scoreprn {
	background-color: #cfe69f;
	border: 1px solid #cfe69f;
	position: absolute;
	margin-top: -25%;
	margin-left: -25%;
	margin: auto;
	z-index: 32767;
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
noMenu = false;
openMenu();
function resetTimeOut(){
	if(myTimeOut != null) {
		globalTimeOut = myTimeOut;
		myTimeOut = null;
	}
}

resetTimeOut;
//-->
</script>

<form action="/CIS/Teacher/TimeOffClassBook.do" method="post" name="inForm" id="inForm" onSubmit="init('資料傳送中.....');">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('教師上課點名單列印');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

		<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <!-- c:choose -->
      <!-- c:when test="${(TimeOffInEditB == null  && TimeOffFormMapB == null) && TeachClasses != null}" -->
			<tr>
	        <td  height="35" colspan="6" align="left" valign="middle" bgcolor="#CCCCFF">
	       	<font color="blue">${TFClassBookPrintInfo.teacherName}&nbsp;老師</font>
	　　			請選擇要列印的班級課程：
	        </td>
		  	</tr>
		  	<tr><td><font color=red >老師請注意:開學後仍可能有學生加退選!!!</font></td></tr>
		 	<tr><td>
		  		<Select name="dtOid" id="dtOid">
		  		<option value="">請選擇授課班級科目</option>
		  		<c:forEach items="${TeachClassesPrint}" var="clazz">
		  			<option value="${clazz.dtimeOid}">
		  			<font color=red>${clazz.className2}(${clazz.dtimeOid})</font><font color="blue>"> ${clazz.chiName2}</font>
		  			</option>
		  		</c:forEach>
		  		</Select>
		  	</td></tr>
 	    </table>
		</td>
	</tr>
      <!-- /c:when -->
 	<!-- /c:choose -->
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='PreviewPrint'/>">&nbsp;&nbsp;'
	);
	</script>
      
<!-- Begin Content Page Table Footer -->
				
</table>
<!-- End Content Page Table Footer -->
</form>

<script>
history.go(1);
</script>
