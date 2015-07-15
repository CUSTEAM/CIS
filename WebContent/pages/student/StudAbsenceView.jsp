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



<form action="/CIS/Student/StudAbsenceApply.do" method="post" name="inputForm">
<input name="opmode" type="hidden" value="delete"/>
<input name="studentNo" type="hidden" value="${studentNo}"/>

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="2" cellspacing="2">
<script>generateTableBanner('請假管理->檢視');</script>	  
<!-- End Content Page Table Header -->
	<c:forEach items="${StudAskLeaveView}" var="doc">
	<tr><td><table width="100%" >
		<tr>
			<td width="75" class="title">請假事由</td>
			<td class="input">${doc.reason}</td>
		</tr>
		<tr>
			<td width="75" class="title">假別</td>
			<td class="input">${doc.askLeaveName}</td>
		</tr>
		<tr>
			<td class="hairlineTdF">開始日期</td>
	        <td class="hairlineTdF">${doc.simpleStartDate}</td>
		</tr>
		<tr>
	        <td class="hairlineTdF">結束時間</td>
	        <td class="hairlineTdF">${doc.simpleEndDate}</td>
		</tr>
		<tr>
			<td width="75" class="title">請假節次</td>
			<td class="input">
				<table class="style2">
				<tr><td rowspan="2" align="center" class="style2">
				<font class="blue_13">日期</font></td>
				<td colspan="15" align="center" class="style2"><font class="blue_13">節次</font>
				</td></tr>
				<tr>
				<c:forEach begin="1" end="15" var="j">
					<td align="center" class="style2">
					<c:choose>
					<c:when test="${j==11}">
						<c:out value="N1"></c:out>
					</c:when>
					<c:when test="${j==12}">
						<c:out value="N2"></c:out>
					</c:when>
					<c:when test="${j==13}">
						<c:out value="N3"></c:out>
					</c:when>
					<c:when test="${j==14}">
						<c:out value="N4"></c:out>
					</c:when>
					<c:when test="${j==15}">
						<c:out value="N5"></c:out>
					</c:when>
					<c:otherwise>
						<c:out value="${j}"></c:out>
					</c:otherwise>
					</c:choose>
					</td>
				</c:forEach>
				</tr>
				<c:forEach items="${doc.details}" var="detail">
					<tr><td class="style2">${detail.idate}</td>
					<c:set var="periods" value="${detail.infos}"/>
					<c:forEach items="${periods}" var="period">
					<td class="style2">
					<c:choose>
					<c:when test="${period=='1'}">
						<c:out value="V"></c:out>
					</c:when>
					<c:otherwise>
						<c:out value=" "></c:out>
					</c:otherwise>
					</c:choose>
					</td>
					</c:forEach>
					
					</tr>
				
				</c:forEach>
				</table>
			</td>
		</tr>
		 
		<tr>
			<td width="75" class="title" valign="top">上傳附件</td>
			<td class="input">
			<p>已上傳檔案:</p>
			<div id="fileList">
			<c:forEach items="${doc.uploadFileInfo}" var="attach">
				<div id="${attach.fileOid}">
				<a href="<%=basePath%>AjaxGlobal?method=getDocAttachUpload&oid=${attach.fileOid}"  target="_blank" style="color:blue; text-decoration:none;">${attach.fileName}</a>
				</div>
	  		</c:forEach>
			</div>
			</td>
		</tr>
		
		<tr>
			<td width="75" class="title" valign="top">備註</td>
			<td class="input">${doc.memo}
			</td>
		</tr>
	
		<tr>
			<td width="75" class="title">審核</td>
			<td class="input"><table class="style2" width="100%">
			<tr>
			<td width="35" class="title">狀態</td>
			<td class="descript">${doc.statusName}</td>
			</tr>
			<tr>
			<td width="35" class="title">說明</td>
			<td class="descript">${doc.exam.description}</td>
			</tr>
			<tr>
			<td width="35" class="title">日期</td>
			<td class="descript">${doc.processDate}</td>
			</tr>
			</table>
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
