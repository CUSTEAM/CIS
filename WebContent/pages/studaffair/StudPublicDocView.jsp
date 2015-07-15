<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/tooltips.js" %>

<DIV id="TipLayer" style="visibility:hidden;position:absolute;z-index:1000;top:-100"></DIV>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<script type="text/javascript">
	var basePath='<%=basePath%>';
</script>


<style type="text/css">
<!--
.wrapper {
	width: 133px;
	margin: 0 auto;
}

div.button {
	height: 29px;	
	width: 133px;
	background: url(<%=basePath%>pages/images/button.png) 0 0;
	
	font-size: 14px;
	color: #C7D92C;
	text-align: center;
	padding-top: 8px;
}
/* 
We can't use ":hover" preudo-class because we have
invisible file input above, so we have to simulate
hover effect with javascript. 
 */
div.button.hover {
	background: url(<%=basePath%>pages/images/button.png) 0 56px;
	color: #95A226;	
}
#button2.hover, #button4.hover {
	text-decoration:underline;
}
-->
</style>

<style type="text/css">
<!--
.style1 {color: #0000FF}
.style3 {
	font-size: 24px;
	font-weight: bold;
}
.title {
	font-size:13px;
	background-color:#ddffdd;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.input {
	font-size:13px;
	background-color:#ffffee;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.mustInput {
	font-size:13px;
	background-color:#ffcccc;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:#cc6666;
}
.descript {
	font-size:12px;
	background-color:#eeffff;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.textsmallred {
	font-size:8px;
	color: #FF0000;
}
-->
</style>
<style type="text/css">
<!--
.style2 {
	color: #FF0000;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:#cc6666;
}
-->
</style>
<script type="text/javascript">
history.go(1);
</script>

<style>
<!--
span.spanTextDropdown { 
	position: absolute;
	top: 0px;
	left: 0px;
	width: 150px;
	z-index: 101;
	background-color: #ECEBAE;
	border: 2px solid #8C9024;
	padding-left: 2px;
	overflow: visible;
	display: none;
	font-size: 13px;
}

span.spanMatchText { 
	text-decoration: underline;
	color: blue;
}

span.spanNormalElement { 
	background: #FFFFFF; 
}

span.spanHighElement { 
	background: red;
	color: white;
	cursor: pointer; 
}
-->
</style>



<form action="/CIS/StudAffair/StudPublicAbsenceApply.do" method="post" name="inputForm">
<input name="opmode" type="hidden" value="delete"/>

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="2" cellspacing="2">
<script>generateTableBanner('公假管理->檢視');</script>	  
<!-- End Content Page Table Header -->
	<c:forEach items="${StudPubDocView}" var="doc">
	<tr><td><table width="100%" >
	<tr>
		<td width="100" align="left" class="title">班級</td>
	    <td  height="30" align="left" valign="middle">${doc.deptClassName}</td>
	</tr>
	
	<tr>
		<td width="100" class="title">公假事由</td>
		<td class="input">${doc.reason}</td>
	</tr>

	<tr>
		<td width="100" class="title">開始日期、節次</td>
        <td class="input">日期 ：${doc.simpleStartDate}&nbsp;&nbsp;&nbsp;節次：
		<c:if test="${doc.startPeriod<11}">${doc.startPeriod}</c:if>
		<c:if test="${doc.startPeriod==11}">N1</c:if>
		<c:if test="${doc.startPeriod==12}">N2</c:if>
		<c:if test="${doc.startPeriod==13}">N3</c:if>
		<c:if test="${doc.startPeriod==14}">N4</c:if>
		<c:if test="${doc.startPeriod==15}">N5</c:if>
		</td>
	</tr>
	<tr>
		<td width="100" class="title">結束時間、節次</td>
        <td class="input">日期 ：${doc.simpleEndDate}&nbsp;&nbsp;&nbsp;節次：
		<c:if test="${doc.endPeriod<11}">${doc.endPeriod}</c:if>
		<c:if test="${doc.endPeriod==11}">N1</c:if>
		<c:if test="${doc.endPeriod==12}">N2</c:if>
		<c:if test="${doc.endPeriod==13}">N3</c:if>
		<c:if test="${doc.endPeriod==14}">N4</c:if>
		<c:if test="${doc.endPeriod==15}">N5</c:if>
        </td>
	</tr>
	<tr>
		<td width="100" class="title">學生</td>
		<td class="input">
		<table width="100%">
		<tr><td>
			<DIV id="studentList">
			<c:forEach items="${doc.studDoc}" var="stdoc">
					<div  class="style2" id="div_${stdoc.studentNo}">
					<table width="100%" id="tb_${stdoc.studentNo}">
					<tr><td>${stdoc.deptClassName}&nbsp;&nbsp;${stdoc.studentName}(${stdoc.studentNo})</td>
					</tr></table></div>
			</c:forEach>
			</DIV>
		</td></tr>
		</table>
		</td>
	</tr>
	 
	<tr>
		<td width="100" class="title" valign="top">附件</td>
		<td class="input">
		<p>已上傳檔案:</p>
		<div id="fileList">
		<c:forEach items="${doc.uploadFileInfo}" var="attach">
			<div id="${attach.fileOid}" style="height:25px">
			<a href="<%=basePath%>AjaxGlobal?method=getPublicDocAttachUpload&oid=${attach.fileOid}"  target="_blank" style="color:blue; text-decoration:none;">${attach.fileName}</a>
			</div>
  		</c:forEach>
		</div>
		</td>
	</tr>
	
	<tr>
		<td width="100" class="title" valign="top">備註</td>
		<td class="input">${doc.memo}
		</td>
	</tr>
	

	</table></td></tr>	
	</c:forEach>
	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Back'/>" >');
	</script>
</table>
</form>
