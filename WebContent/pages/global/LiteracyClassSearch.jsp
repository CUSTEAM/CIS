<!DOCTYPE html PUBLIC"-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection0.js" %>
<script src="/CIS/pages/include/decorate.js"></script>
<html:html locale="true">
<head>
<link rel="stylesheet" href="/CIS/pages/images/home.css" type="text/css" />
<link rel="stylesheet" href="http://www.cust.edu.tw/www/info/info.css" type="text/css" />
<link rel="stylesheet" href="http://www.cust.edu.tw/www/info/infotable.css" type="text/css" />
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
</head>

<body>
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
    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
      			<tr>
        			<td valign="top" id="maincontent"><div>
        				<html:form action="/LiteracyClassSearch" method="post">
        				開課班級：
							<select name="campusInCharge" onchange="fillSchoolsForLiteracyClassSearch();">
								<option value="All"><bean:message key="AllCampuses" /></option>
								<c:forEach items="${AllCampuses}" var="campus">
								<option value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</option>	
								</c:forEach>
							</select>
							<select name="schoolInCharge"></select>
							<input type="submit" name="method" value="<bean:message key='course.courseSearch.btn.search' bundle="COU" />" class="CourseButton" />
          				</html:form>
          			</td>	
          		</tr>
          		<c:if test="${not empty literacyClassList}">
				<tr>
					<td>
						<table width="100%" cellpadding="2" cellspacing="2" border="1">
							<tr>
								<td>
									<display:table name="${literacyClassList}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
			       						<%@ include file="../include/NoBanner.jsp" %>
			        					<display:column titleKey="Course.label.classNo" property="ClassNo" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.className" property="ClassName" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.courseNumber" property="Oid" sortable="false" class="center" />  
			        					<display:column titleKey="Course.label.courseName" property="chi_name" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.techName" property="cname" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.opt" property="opt2" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.hours" property="thour" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.stuSelect" property="stu_select" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.classTime" property="time" sortable="false" class="center" />
			        					<display:column titleKey="Course.label.place" sortable="false" class="center">
			        						<c:out value="${row.name2}" />&nbsp;<c:out value="${row.room_id}" />
			        					</display:column>       					     					
			      					</display:table>
								</td>								
							</tr>			
						</table>
					</td>
				</tr>
				</c:if>
			</table>
		</td>
	</tr>
</table>
</body>
</html:html>