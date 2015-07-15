<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
<display:table name="${dtimeList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list" >
	        
		<display:column title="<script>generateTriggerAll(${fn:length(dtimeList)}, 'dtimes'); </script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "dtimes")</script></display:column>
	        <display:column title="課程編號" property="oid" sortable="true" class="left" />
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
	      
	      </td></tr>
	      <tr>
		<td colspan="7" align="center" height="40">
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
									<option value="/CIS/ClassTimetable40">通識課表</option>
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
    							
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
<c:if test="${readonly==false}">
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" disabled value="<bean:message key='Delete'/>" class="gCancel" >
		
		<INPUT type="submit" name="method" disabled value="<bean:message key='Modify'/>" class="gSubmit" >
		
		<INPUT type="submit" name="method" value="<bean:message key='View'/>" class="gGreen">
		
		<input type="button" disabled value="建立選課" onClick="createSeld(), this.style.display='none'" class="gGreen">
		<select id="createSeldType" style="display:none" name="createSeldType" class="CourseButton">
		<option value="this">為本學年同學</option>
		<option value="next">為下學年同學</option>
		<option value="delete">清除本班學生</option>
		<option value="deleteAll">清除全部學生</option>
		</select>
		<INPUT style="display:none" type="submit" name="method" id="createSeldSubmit" value="<bean:message key='CreateBaseld'/>" class="gSubmit">
		<!-- NPUT type="submit"
   		name="method"
   		id="ChangeOpenType"
						   		onMouseOver="showHelpMessage('將課程的開放規則設為相反', 'inline', this.id)" 
							   		onMouseOut="showHelpMessage('', 'none', this.id)"
   		value="<bean:message key='ChangeOpenType'/>"
   		class="CourseButton"/-->
		<script>
		function createSeld(){
			if(document.getElementById('createSeldType').style.display=='none'){
				document.getElementById('createSeldType').style.display='inline'
				document.getElementById('createSeldSubmit').style.display='inline'
			}	
		}
		</script>
		</td>
	</tr>       	
	</c:if>