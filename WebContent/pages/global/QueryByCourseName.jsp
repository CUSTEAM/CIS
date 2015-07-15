<!DOCTYPE html PUBLIC"-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/AllCampusSchool.js" %>

<html:html locale="true">
<head>
<link rel="stylesheet" href="/CIS/pages/images/home.css" type="text/css" />
<link rel="stylesheet" href="http://www.cust.edu.tw/www/info/info.css" type="text/css" />
<link rel="stylesheet" href="http://www.cust.edu.tw/www/info/infotable.css" type="text/css" />
<title><bean:message key="LoginForm.title" /></title>
<style type="text/css">
<!--
.outline1 {
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-right-color: #FF0000;
	border-bottom-color: #FF0000;
	border-left-color: #FF0000;
}
A.sidelink, A.sidelink:visited, A.sidelink:active {
	color:#640000;
}
body {
	background-color: #FFF0F0;
	background-image: url(/CIS/pages/global/images/grey.gif);
	background-repeat: repeat-y;
}
-->
</style>
<script>
history.go(1);
</script>
</head>

<body>
<form action="javascript:void%200" method="post"></form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td id="nav_top_bar"><a href="http://www.cust.edu.tw">${SchoolName_ZH}</a> &gt; 資訊查詢系統</td>
  	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">  
  	<tr>
    	<td colspan="2" background="http://www.cust.edu.tw/www/info/images/bg.png"><div class="top_logo"><img src="http://www.cust.edu.tw/www/info/images/info.png" border="0" /></div></td>
  	</tr>
</table>
<table class="outline1" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  	<tr>
    	<td width="160" valign="top" bgcolor="#CCCCCC"><p>&nbsp;</p>
      		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<td><a class="sidelink" href="http://www.cust.edu.tw/www/info/code_class.html">節次時間對照表</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://www.cust.edu.tw/www/info/code_building.html">大樓樓層代碼表</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://ap.cust.edu.tw/CIS/ClassCourseSearch.do">班級課表</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://ap.cust.edu.tw/CIS/TeacherCourseSearch.do">教師課表</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://www.cust.edu.tw/www/info/intro_s.php">班級課程中英簡介</a></td>
        		</tr>
        		<tr>
         	 		<td><a class="sidelink" href="http://www.cust.edu.tw/www/info/intro_s.php">班級課程教學大綱</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://ap.cust.edu.tw/CIS/LiteracyClassSearch.do">通識課程上課地點</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://ap.cust.edu.tw/CIS/OpenCourseSearch.do">開放選修科目</a></td>
        		</tr>        		
        		<tr>
          			<td><a class="sidelink" href="http://www.cust.edu.tw/www/info">考試日程</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://ap.cust.edu.tw/CIS/QueryByCourseName.do">科目名稱查詢課程</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://ap.cust.edu.tw/CIS/QueryByWeekTime.do">星期節次查詢課程</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://ap.cust.edu.tw/CIS">學生專區</a> </td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://www-ap.cust.edu.tw/CIS4P">家長專區</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://ap.cust.edu.tw/CIS">教師專區</a></td>
        		</tr>
        		<tr>
          			<td><a class="sidelink" href="http://cap.cust.edu.tw/CIS/Login.do">行政人員專區</a></td>
        		</tr>
      		</table>
      	</td>
    	<td valign="top">
<form action="/CIS/QueryByCourseName.do" method="post" name="inputForm">
<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header
    <script>generateTableBanner('<div class="gray_15"><B><bean:message key="stfTitle.StudTimeOffInputClass" bundle="SAF"/></B></div>');</script>
-->
 	<tr><td colspan=2>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <tr><td align="center">
      <c:set var="campusSel" value="${QBCourseNameInit.campus}"/>
      <c:set var="schoolSel" value="${QBCourseNameInit.school}"/>
      	校區學制：
		<select name="campusInCharge" onchange="fillSchools();">
		<option value="All"><bean:message key="AllCampuses" /></option>
		<c:forEach items="${AllCampuses}" var="campus">
			<option value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</option>	
		</c:forEach>
		</select>
		<select name="schoolInCharge"></select>
		<script>restoreSelections('${schoolSel}');</script>
		科目名稱：<input type="text" name="courseName" value="${QBCourseNameInit.courseName}"/>
        </td>
      </tr>
      <tr>
      	<td>
      		<script>
			generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query'/>" >&nbsp;&nbsp;'+
			'<INPUT type="reset" name="method" value="重新輸入" >');
			</script>
      	</td>
      </tr>
	</table>
		</td>		
	</tr>

	<!-- Test if have Query Result  -->
	<c:if test="${QBCourseNameResult != null}" >
	<c:set var="cpName" value="${QBCourseNameInit.campusName}"/> 
	<tr><td width="3%">&nbsp;</td>
		<td>
			<table align="left" width="97%" border="1" cellpadding="2" cellspacing="1" bgcolor="#FFFFCC" id="tb1">
			<tr>
				<td bgcolor="#FFf8C00">校區</td>
				<td bgcolor="#FFf8C00">班級代碼</td>
				<td bgcolor="#FFf8C00">開課班級</td>
				<td bgcolor="#FFf8C00">課程編號</td>
				<td bgcolor="#FFf8C00">科目中文名稱</td>
				<td bgcolor="#FFf8C00">任課教師</td>
				<td bgcolor="#FFf8C00">選別</td>
				<td bgcolor="#FFf8C00">學分</td>
				<td bgcolor="#FFf8C00">時數</td>
				<td bgcolor="#FFf8C00">人數</td>
				<td bgcolor="#FFf8C00">上課時間</td>
				<td bgcolor="#FFf8C00">備註</td>
			</tr>
			<c:forEach items="${QBCourseNameResult}" var="dtimes">
			<tr>
				<td>${cpName}</td>
				<td>${dtimes.depClass}</td>
				<td>${dtimes.depClassName}</td>
				<td>${dtimes.Oid }</td>
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
	    </td>
  	</tr>
</table>

</body>
</html:html>
