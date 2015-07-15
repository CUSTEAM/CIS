<!DOCTYPE html PUBLIC"-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>  
<script src="/CIS/pages/include/decorate.js"></script>
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
function searchCheck() {
	var iCount;
	iCount = getCookie("emplListCount");
	if (iCount == 0) {
		alert("請選擇一個教師作班級課表查詢!!");
		return false;
	} else if(iCount > 1) {
		alert("不可複選教師作班級課表查詢!!");
		return false;
	} else {
		return true;
	}
}
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
	    	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	    	<html:form action="/TeacherCourseSearch" method="post">
	    		<tr>
					<td align="center">
						<table cellspacing="2" class="empty-border">
							<tr>
								<td>
									教師姓名：&nbsp;<html:text property="cname2" size="15" maxlength="20" />
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<script>
			  		generateTableBanner('<INPUT type="submit" name="method" id="s1" value="<bean:message key='teacher.classCadreSearch.btn.searchTeacher' bundle="TCH"/>" class="CourseButton">');			  
			 	</script>
			 	
			 	<c:if test="${not empty emplList}">
	      		<tr>
					<td>
						<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
						<display:table name="${emplList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
					        <display:column title="<script>generateTriggerAll(${fn:length(emplList)}, 'emplList');</script>" class="center">
				          		<script>generateCheckbox("${row.oid}", "emplList");</script>
				          	</display:column>
					        <display:column titleKey="courseSearch.teacherName" property="cname" sortable="false" class="center" />
		        			<display:column titleKey="deptInCharge" property="unit2" sortable="false" class="center" />
		        			<display:column titleKey="courseSearch.teacherTitle" property="sname" sortable="false" class="center" />	        
					    </display:table>
					</td>
				</tr>
			  	<script>
			  		generateTableBanner('<INPUT type="submit" name="method" id="s1" value="<bean:message key='teacher.classCadreSearch.btn.searchCourse' bundle="TCH"/>" onclick="return searchCheck();" class="CourseButton">');			  
			 	</script> 
			 	</c:if>
			 	
			 	<c:if test="${not empty courseList}">
				<script>
					generateTableBanner('<div class="gray_15"><B>上 課 時 間 依 各 校 區 各 部 制 公 告 時 間 為 準</B></div>');
				</script>	
				<tr>
					<td>
						<table width="100%" cellpadding="3" cellspacing="2" id="infosearch" border="1">
							<thead>
							<tr>
								<th height="30" bgcolor="#99CC66">&nbsp;</th>
								<c:forEach begin="0" end="6" varStatus="status2">
								<th align="center" bgcolor="#99CC66">
									<b><font color="black"><c:out value="${weekdayList[status2.index]}" /></font></b>
								</th>
								</c:forEach>
							</tr>
							</thead>
							<c:forEach begin="0" end="14" varStatus="status">
							<tr>	
								<td align="center" width="100" bgcolor="#99CC66">
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="6" varStatus="status1">
								<td height="100" width="100"><strong>		
									<c:out value="${courseList[(status1.index * 15) + status.index]['ClassName']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['ClassNo']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" />&nbsp;
									<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" /><br/></strong>
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
						</table>
					</td>
				</tr>
				</c:if>
			</html:form> 	    
	    	</table>
	    </td>
  	</tr>
</table>

</body>
</html:html>
