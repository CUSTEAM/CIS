<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/AllCampusSchool.js" %>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>
<form action="/CIS/Course/QueryByWeekTime.do" method="post" name="inputForm">
<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header
    <script>generateTableBanner('<div class="gray_15"><B><bean:message key="stfTitle.StudTimeOffInputClass" bundle="SAF"/></B></div>');</script>
-->
 	<tr><td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <tr><td align="center">
      <c:set var="campusSel" value="${QBWeekTimeInit.campus}"/>
      <c:set var="schoolSel" value="${QBWeekTimeInit.school}"/>
      	校區學制：
		<select name="campusInCharge" onchange="fillSchools();">
		<option value="All"><bean:message key="AllCampuses" /></option>
		<c:forEach items="${AllCampuses}" var="campus">
			<option value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</option>	
		</c:forEach>
		</select>
		<select name="schoolInCharge"></select>
		<script>restoreSelections('${schoolSel}');</script>
		&nbsp;&nbsp;星期：<input type="text" name="fweek" value="${QBWeekTimeInit.fweek}" size="1" maxlength="1"/>
		&nbsp;&nbsp;節次：<input type="text" name="period" value="${QBWeekTimeInit.period}" size="2" maxlength="3"/>
        </td>
      </tr>
      <tr>
      	<td>
      		<script>
			generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query'/>" onclick="return checkData();" >&nbsp;&nbsp;'+
			'<INPUT type="reset" name="method" value="重新輸入" >');
			</script>
      	</td>
      </tr>
	</table>
		</td>		
	</tr>

	<!-- Test if have Query Result  -->
	<c:if test="${QBWeekTimeResult != null}" >
	<c:set var="cpName" value="${QBWeekTimeInit.campusName}"/> 
	<tr>
		<td>
			<table width="100%" border="1" cellpadding="2" cellspacing="1" bgcolor="#FFFFCC" id="tb1">
			<tr>
				<td bgcolor="#FFf8C00">校區</td>
				<td bgcolor="#FFf8C00">開課班級</td>
				<td bgcolor="#FFf8C00">課程編號</td>
				<td bgcolor="#FFf8C00">課程名稱</td>
				<td bgcolor="#FFf8C00">任課教師</td>
				<td bgcolor="#FFf8C00">選別</td>
				<td bgcolor="#FFf8C00">學分</td>
				<td bgcolor="#FFf8C00">時數</td>
				<td bgcolor="#FFf8C00">人數</td>
				<td bgcolor="#FFf8C00">上課時間</td>
				<td bgcolor="#FFf8C00">備註</td>
			</tr>
			<c:forEach items="${QBWeekTimeResult}" var="dtimes">
			<tr>
				<td>${cpName}</td>
				<td>${dtimes.depClassName}</td>
				<td>${dtimes.Oid}</td>
				<td>${dtimes.chiName}</td>
				<td>${dtimes.techName}</td>
				<td>${dtimes.optName}</td>
				<td>${dtimes.credit}</td>
				<td>${dtimes.thour}</td>
				<td>${dtimes.selected}</td>
				<td>${dtimes.times}</td>
				<c:if test="${dtimes.opened == '0'}">
					<td>&nbsp;</td>
				</c:if>
				<c:if test="${dtimes.opened == '1'}">
					<td>開放選修</td>
				</c:if>
			</tr>				
			</c:forEach>
	        </table>
	   </td>
	</tr>
	
	</c:if>
</table>
</form>

<script language="javascript">
function checkData(){
	var msg = "";
	if(document.getElementById("fweek").value == ""){
		msg = msg + "星期必須輸入!";
	}
	if(document.getElementById("period").value == ""){
		msg = msg + "節次必須輸入!";
	}
	if(msg != ""){
		window.alert(msg);
		return false;
	}
	return true;
}
</script>