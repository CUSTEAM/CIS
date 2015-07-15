<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/user_tick.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">系所開課狀況</font></div>		
		</td>
	</tr>
	<!-- 標題列 end -->

	<html:form action="/DeptAssistant/DeptCourseInfo" method="post" onsubmit="init('執行中, 請稍後')">
	<!-- 主查詢界面 start-->
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="1">
				<html:select property="term">
	    		<html:option value="1">第1學期</html:option>
	    		<html:option value="2">第2學期</html:option>
	    		</html:select>
	    		</td>
				<td class="hairLineTdF" nowrap>
				<c:set var="campusSel" value="${DeptCourseInfoForm.map.campusInCharge2}"/>
	  			   	<c:set var="schoolSel" value="${DeptCourseInfoForm.map.schoolInCharge2}"/>
	  			   	<c:set var="deptSel"   value="${DeptCourseInfoForm.map.deptInCharge2}"/>
	  			   	<c:set var="classSel"  value="${DeptCourseInfoForm.map.classInCharge2}"/>
	  			   	<%@ include file="/pages/include/ClasSelectAll4John.jsp"%>
				</td>
			</tr>
			
		</table>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="100%">
				<select name="opt1">
					<option <c:if test="${DeptCourseInfoForm.map.opt1==''}">selected</c:if> value="%">不指定選別</option>
					<option <c:if test="${DeptCourseInfoForm.map.opt1=='1'}">selected</c:if> value="1">必修</option>
					<option <c:if test="${DeptCourseInfoForm.map.opt1=='2'}">selected</c:if> value="2">選修</option>
					<option <c:if test="${DeptCourseInfoForm.map.opt1=='3'}">selected</c:if> value="3">通識</option>
				</select>
				
				<select name="elearning">
					<option <c:if test="${DeptCourseInfoForm.map.elearning==''}">selected</c:if> value="%">不指定遠距或輔助</option>
					<option <c:if test="${DeptCourseInfoForm.map.elearning=='1'}">selected</c:if> value="1">遠距</option>
					<option <c:if test="${DeptCourseInfoForm.map.elearning=='2'}">selected</c:if> value="2">輔助</option>
					<option <c:if test="${DeptCourseInfoForm.map.elearning=='3'}">selected</c:if> value="3">多媒體</option>
				</select>
				</td>
			</tr>
		</table>
		
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>教師姓名</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" size="8" onMouseDown="clearid(this.id), clearid('techid')" value="${DeptCourseInfoForm.map.cname}"
				onkeyup="getAny(this.value, 'cname', 'techid', 'empl', 'name')" name="cname" id="cname"/>				
				<input type="text" name="techid" id="techid" value="${DeptCourseInfoForm.map.techid}"/>
				</td>
			</tr>
		</table>
		
		
		
		
		
		
		
		
		
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">請指定學期、科系、選別、教師和其他欄位，查詢各系所目前的實際開課與選課狀況。</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" id="continue" value="<bean:message key='Search' bundle="TCH"/>" class="gSubmit">
	</tr>

	<!-- 列表 start -->	
	<c:if test="${not empty dtimeList}">
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td>
						<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
						<display:table name="${dtimeList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list" >
	        
						<display:column title="<script>generateTriggerAll(${fn:length(dtimeList)}, 'dtimes'); </script>" class="center" >
					          <script>generateCheckbox("${row.oid}", "dtimes")</script></display:column>
					        <display:column titleKey="Course.label.className" property="departClass2" sortable="true" class="left" />
					        <display:column titleKey="Course.label.classNo" property="departClass" sortable="true" class="left" />
					        <display:column titleKey="Course.label.courseName" property="chiName2" sortable="true" class="left" />
					        <display:column titleKey="Course.label.courseNumber" property="cscode" sortable="true" class="left" />
					        <display:column title="教師姓名" property="techName" sortable="true" class="left" />
					        <display:column titleKey="Course.label.opt" property="opt2" sortable="true" class="center" />
					        <display:column titleKey="Course.label.credit" property="credit" sortable="true" class="center" />
					        <display:column titleKey="Course.label.hours" property="thour" sortable="true" class="center" />
					        <display:column titleKey="Course.label.stuSelect" property="stuSelect" sortable="true" class="center" />
					        <display:column titleKey="Course.label.opencs" property="openName" sortable="true" class="center" />
					        <display:column titleKey="Course.label.elearning" property="elearningName" sortable="true" class="center" />
					        <display:column titleKey="Course.label.open2" property="open2" sortable="true" class="center" />
					        <display:column title="<img src='images/16-cube-pc.png' border='0' title='電腦實習費'>" property="extrapay2" sortable="true" class="center" />
					        <display:column title="細節" property="icon" nulls="false" class="center" href="/CIS/StudentSel" paramId="Oid" paramProperty="oid" />
					      </display:table>
					</td>
				</tr>
			</table>		
		</td>
	</tr>
	<!-- 列表 end-->
	
	
	<tr height="40">
		<td align="center">
		<table class="hairLineTable" align="center" width="99%">
  				<tr>
    				<td class="hairLineTdF">
    					<table width="100%"> 
    						<tr>
    							<td align="left" width="400">
    							&nbsp;
    							<img src="images/printer.gif" border="0">
    							<select name="reportType" onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">校內表格</option>
    								<option value="/CIS/pages/course/export/list4Dtime.jsp?type=excel">通用報表</option>
									<option value="/CIS/TeacherCounterTime">教師任教時數</option>
									<option value="/CIS/TeacherCounterCourse">科目教師對照</option>
									<option value="/CIS/CourseCounterTeacher">教師科目對照</option>
									<!-- option value="/CIS/ExamStudentList">考場記載表</option-->
									<option value="/CIS/ExamPacketFace">試卷封袋</option>
									<option value="/CIS/ClassTimetable">班級課表</option>
									<option value="/CIS/TechTimetable">教師課表</option>
									<option value="../Course/TeachersTimeTable">一科目多教師課表</option>									
									<option value="/CIS/StuSeltable">學生選課清單</option>
									<option value="/CIS/StuSelConfirmTable">學生選課簽收單</option>
    							</select>    							

    							<script>
    							<!--
								function jumpMenu(targ,selObj,restore){
  									eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
  									eval(targ+".location.target='_blank'");
  									if (restore) selObj.selectedIndex=0;
									}
								//-->
								</script>
    							<select name="reportType" onchange="jumpMenu('parent',this,0)" >
    								<option value="javascript:void(0)">報部表格</option>
    								<option value="/CIS/List4Dtime13">表1-3</option>
    								<option value="/CIS/List4Dtime32">表3-2</option>
    								<option value="/CIS/List4Seld52">表5-1</option>
    								<option value="/CIS/List4DtimeG">技職</option>
    								<option value="/CIS/List4Course35">表3-5</option>    								
    							</select>
    							
    							<select name="reportType" onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">遠距表格</option>
    								<option value="/CIS/ElearningPer">班級對應</option>
    								<option value="/CIS/ElearningCou">課程對應</option>
    								<option value="/CIS/ElearningOrg">學生對應</option>
    								<option value="/CIS/ElearningSel">選課對應</option>
    								<option value="/CIS/ElearningSel">中輟名單</option>
    								<option value="/CIS/ElearningSel">復學名單</option>
    							</select>

    							</td>
    							<td align="right">
    							<c:set var="cTotal" value="0"/>
    							<c:set var="cCredit" value="0.0F"/>
    							<c:set var="cHour" value="0"/>
    							<c:set var="cStuSlt" value="0"/>
    							<c:forEach items="${dtimeList}" var="dTLst" varStatus="dTLstS">
    								<c:set var="cCredit" value="${cCredit+dTLst.credit}"/>
    								<c:set var="cHour" value="${cHour+dTLst.thour}"/>
    								<c:set var="cStuSlt" value="${cStuSlt+dTLst.stuSelect}"/>
    								<c:set var="cTotal" value="${cTotal+1}"/>
    							</c:forEach>
    							共計: ${cTotal}筆, <font title="學分數是以浮點數運算, 精確度因虛擬機器而異, 小數點以下數字不一定需要被考慮.">${cCredit}</font>學分, <font title="時數是以浮點數運算, 精確度因虛擬機器而異, 小數點以下數字不一定需要被考慮.">${cHour}</font>小時, ${cStuSlt}人次
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='View' bundle="TCH" />" onclick="return classCheck();" class="gGreen" />
	</tr>
	
	</c:if>		
	<!-- 主查詢界面 end-->
	
	<c:if test="${not empty stdResult}">
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td>
						<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
						<display:table name="${stdResult}" pagesize="10" id="row" sort="list" class="list">
							<%@ include file="../include/NoBanner.jsp" %>
							<display:column title="學號" property="studentNo" sortable="true" class="center" />
	          				<display:column title="姓名" property="studentName" sortable="true" class="center" />							
							<display:column title="性別" property="sex2" sortable="true" class="center" />
							<display:column title="班級代碼" property="departClass" sortable="true" class="center" />		
							<display:column title="班級名稱" property="departClass2" sortable="true" class="center" />					
						</display:table>
					</td>
				</tr>
			</table>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Back' />" class="gCancel" />
	</tr>
	</c:if>		
	
	</html:form>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>