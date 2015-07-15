<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

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
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>
<form action="/CIS/AMS/DocManager.do" method="post" name="inputForm">
<input name="opmode" type="hidden" value="add"/>
<input name="docType" type="hidden" value="${DocOverTimeEditInit.docType}"/>
<!-- for auto input select item use -->
	<div id="popup" style="position:absolute;">
		 <table id="name_table" bgcolor="#FFFAFA" border="0" cellspacing="0" cellpadding="0">
		 	  <tbody id="name_body"></tbody>
		 </table>
	</div> 


<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="2" cellspacing="2">
<script>generateTableBanner('加班管理->新增');</script>	  
<!-- End Content Page Table Header -->
	<tr><td><table>
	<c:choose>
	<c:when test="${DocOverTimeEditForm != null}">
	<c:set var="formback" value="${DocOverTimeEditForm}"></c:set>
	<tr>
		<td width="75" class="title">加班原因</td>
		<td class="input"><input type="text" name="reason" class="mustInput" value="${formback.reason}"></td>
	</tr>
	<tr>
		<td width="75" class="title">開始日期</td>
		<td class="input">西元 
		<input type="text" name="startYear" class="mustInput" size="4" value="${formback.startYear}"> 年&nbsp;&nbsp;
		<input type="text" name="startMonth" class="mustInput" size="2" value="${formback.startMonth}"> 月&nbsp;&nbsp;
		<input type="text" name="startDay" class="mustInput" size="2" value="${formback.startDay}"> 日&nbsp;&nbsp;
		<select name="startHour" id="startHour">
			<c:forEach begin="0" end="23" var="cnt">
				<c:if test="${cnt == formback.startHour}">
				<option value="${cnt}" selected="selected">${cnt}</option>
				</c:if>
				<c:if test="${cnt != formback.startHour}">
				<option value="${cnt}">${cnt}</option>
				</c:if>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="startMinute" id="startMinute">
			<c:if test="${0 == formback.startMinute}">
			<option value="0" selected="selected">0</option>
			<option value="30">30</option>
			</c:if>
			<c:if test="${30 == formback.startMinute}">
			<option value="0">0</option>
			<option value="30" selected="selected">30</option>
			</c:if>
		</select> 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">結束日期</td>
		<td class="input">西元 
		<input type="text" name="endYear" class="mustInput" size="4" value="${formback.endYear}"> 年&nbsp;&nbsp;
		<input type="text" name="endMonth" class="mustInput" size="2" value="${formback.endMonth}"> 月&nbsp;&nbsp;
		<input type="text" name="endDay" class="mustInput" size="2" value="${formback.endDay}"> 日&nbsp;&nbsp;
		<select name="endHour" id="endHour">
			<c:forEach begin="0" end="23" var="cnt">
				<c:if test="${cnt == formback.endHour}">
				<option value="${cnt}" selected="selected">${cnt}</option>
				</c:if>
				<c:if test="${cnt != formback.endHour}">
				<option value="${cnt}">${cnt}</option>
				</c:if>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="endMinute" id="endMinute">
			<c:if test="${0 == formback.endMinute}">
			<option value="0" selected="selected">0</option>
			<option value="30">30</option>
			</c:if>
			<c:if test="${30 == formback.endMinute}">
			<option value="0">0</option>
			<option value="30" selected="selected">30</option>
			</c:if>
		</select> 分
		</td>
	</tr>
	
	<c:if test="${AmsIsTeacher == '1'}">
	<tr>
		<td width="75" class="title">加班日數</td>
		<td class="input">
		<select name="totalDay">
			<c:if test="${formback.totalDay == '0.5'}">
			<option value="0.5" selected="selected">0.5</option>
			<option value="1">1</option>
			</c:if>
			<c:if test="${formback.totalDay == '1'}">
			<option value="0.5">0.5</option>
			<option value="1" selected="selected">1</option>
			</c:if>
		</select>
		</td>
	</tr>
	</c:if>

	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input"><textarea rows="3" cols="60" name="memo">${formback.memo}</textarea>
		</td>
	</tr>
	
	</c:when>
	<c:otherwise>
	<tr>
		<td width="75" class="title">加班原因</td>
		<td class="input"><input type="text" name="reason" class="mustInput"></td>
	</tr>
	<tr>
		<td width="75" class="title">開始日期</td>
		<td class="input">西元 
		<input type="text" name="startYear" class="mustInput" size="4"> 年&nbsp;&nbsp;
		<input type="text" name="startMonth" class="mustInput" size="2"> 月&nbsp;&nbsp;
		<input type="text" name="startDay" class="mustInput" size="2"> 日&nbsp;&nbsp;
		<select name="startHour" id="startHour">
			<c:forEach begin="0" end="23" var="cnt">
				<option value="${cnt}">${cnt}</option>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="startMinute" id="startMinute">
			<option value="0">0</option>
			<option value="30">30</option>
		</select> 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">結束日期</td>
		<td class="input">西元 
		<input type="text" name="endYear" class="mustInput" size="4"> 年&nbsp;&nbsp;
		<input type="text" name="endMonth" class="mustInput" size="2"> 月&nbsp;&nbsp;
		<input type="text" name="endDay" class="mustInput" size="2"> 日&nbsp;&nbsp;
		<select name="endHour" id="endHour">
			<c:forEach begin="0" end="23" var="cnt">
				<option value="${cnt}">${cnt}</option>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="endMinute" id="endMinute">
			<option value="0">0</option>
			<option value="30">30</option>
		</select> 分
		</td>
	</tr>
	
	<c:if test="${AmsIsTeacher == '1'}">
	<tr>
		<td width="75" class="title">加班日數</td>
		<td class="input">
		<select name="totalDay">
			<option value="0.5">0.5</option>
			<option value="1">1</option>
		</select>
		</td>
	</tr>
	</c:if>
	
	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input"><textarea rows="3" cols="60" name="memo"></textarea>
		</td>
	</tr>
	</c:otherwise>
	</c:choose>
	<tr>
		<td width="75" class="title" valign="top">說明</td>
		<td>
		<table class="descript" width="100%">
			<tr>
			<td width="20" valign="top">1.</td>
			<td>
加班單請於加班日前或加班後三日內送至人事室核備，逾期不予受理。</td>
			</tr>
			<tr>
			<td width="20" valign="top">2.</td>
			<td>
加班時數必須為0.5日或1日，加班超過一日請分開填寫。</td>
			</tr>
			<tr>
			<td width="20" valign="top">3.</td>
			<td>
上班日加班請於加班完畢在刷下班卡，假日加班請記得刷上下班卡。</td>
			</tr>
			<tr>
			<td width="20" valign="top">4.</td>
			<td>
未依加班單填寫加班時間刷卡，加班時數不予列入補休假計算。
(例：加班單填08:00-12:00，請在08:00前刷卡12:00後刷退)。
</td>
			</tr>
			<tr>
			<td width="20" valign="top">5.</td>
			<td>
加班單一經人事室刷條碼後即無法修正。</td>
			</tr>
			<tr>
			<td width="20" valign="top">6.</td>
			<td>
以上若有未盡事宜，依人事室網頁公告事項為準。</td>
			</tr>
		</table>
		</td>
	</tr>
	</table></td></tr>
	
	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</table>
</form>
<script type="text/javascript" src="<%=basePath%>pages/AMS/include/docQuery.js"></script>
