<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Summer/OnlineClassSelection" method="post" onsubmit="init('查詢進行中, 請稍後')">
<script>
	generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/icon_Member_add.gif"></td>'+
										'<td>加退選作業</td>'+
									'</tr>'+
								'</table>');
</script>
<tr>
	<td>
		<table>
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">學生學號</td>
						<td class="hairLineTd">
						<input type="text" name="studentId" id="studentId" size="8" style="ime-mode:disabled" autocomplete="off"
						value="${OnlineClassSelectionForm.map.studentId}"
						onkeyup="if(this.value.length>4)getAny(this.value, 'studentId', 'studentName', 'stmd', 'no')"
						onclick="this.value='', document.getElementById('studentName').value=''"
						/><input type="text"
						onkeyup="getAny(this.value, 'studentName', 'studentId', 'stmd', 'name')"
						onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
						onclick="this.value='', document.getElementById('studentId').value=''"
						name="studentName" id="studentName" size="12" value="${OnlineClassSelectionForm.map.studentName}"/>
						</td>
						<td class="hairLineTdF" width="30" align="center">
						<img src="images/16-exc-mark.gif" />
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			
			
			<tr>
				<td>
				
				<table class="hairLineTable">	
					<tr>
						<td class="hairLineTdF">開課班級</td>
						<td class="hairLineTd">
						<input type="text" id="departClass" name="departClass"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						 value="${OnlineClassSelectionForm.map.departClass}"
						 onkeyup="getAny(this.value, 'departClass', 'classLess', 'Sabbr', 'no')"
						 onclick="this.value='', document.getElementById('classLess').value=''"/><input 
						 type="text" name="classLess" id="classLess"
						value="${OnlineClassSelectionForm.map.classLess}" size="12"
						 onkeyup="getAny(this.value, 'classLess', 'departClass', 'Sabbr', 'name')"
						 onclick="this.value='', document.getElementById('departClass').value=''"/>
						</td>
						<td class="hairLineTdF" width="30" align="center">
						<img src="images/16-exc-mark.gif" />
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF"><bean:message key="setCourse.label.courseNumber" bundle="COU"/></td>
						<td class="hairLineTd">
						<input type="text" name="courseNumber" id="cscodeS" size="8"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						value="${OnlineClassSelectionForm.map.courseNumber}"
						onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
						onclick="this.value='', courseName.value=''"/><input type="text" autocomplete="off"
						name="courseName" id="csnameS" size="16"
						value="${OnlineClassSelectionForm.map.courseName}"
						onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
						onkeydown="document.getElementById('Acsname').style.display='none';"
						onclick="this.value='', courseNumber.value=''"/>
		
						</td>
						<td class="hairLineTdF" width="30" align="center">
						<img src="images/16-exc-mark.gif" />
						</td>
					</tr>					
				</table>
				
				
				
				
				</td>
			</tr>
			
		</table>
		
	</td>
</tr>
<tr height="30">
	<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='AddCourse'/>"
						   class="gSubmit">
						   
		<INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='RemoveCourse'/>"
													   class="gCancle">
	</td>
</tr>

<!-- 加選作業 Start -->
<c:if test="${mode=='add'}">
<tr>
	<td>
		<table width="100%">
			<tr>
				<td>
				
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF">
						<table width="100%" onclick="showInfo('scoreHist')">
							<tr>
								<td><img src="images/folder_page_zero.gif">
								</td>
								<td align="left" width="100%" style="cursor:pointer;">
								歷年不及格成績 (點擊可關閉)
								</td>
							</tr>
							<tr>
								<td colspan="2" id="scoreHist" style="display:inline">
								<display:table name="${scoreHist}" export="true" id="row" sort="list" excludedParams="*" class="list">
								<display:column title="學年" property="school_year" nulls="false" />
								<display:column title="學期" property="school_term" nulls="false" />
								<display:column title="學號" property="student_no" nulls="false" />
								<display:column title="姓名" property="student_name" nulls="false" />
								<display:column title="課程名稱" property="chi_name" nulls="false" />
								<display:column title="課程代碼" property="cscode" nulls="false" />
								<display:column title="選別" property="opt" nulls="false" />	 
								<display:column title="學分" property="credit" nulls="false" />
								<display:column title="成績" property="score" nulls="false" />	        
	   									</display:table>											
								</td>
							</tr>
						</table>
					
					
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<table width="100%">
			<tr>
				<td>
				
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF">
						<table width="100%" onclick="showInfo('selected')">
							<tr>
								<td><img src="images/folder_bug.gif">
								</td>
								<td align="left" width="100%" style="cursor:pointer;">
								學生暑修已選課程 (點擊可關閉)
								</td>
							</tr>
							<tr>
								<td colspan="2" id="selected" style="display:inline">
								<display:table name="${selected}" export="true" id="row" sort="list" excludedParams="*" class="list">
								<display:column title="梯次" property="seqno" nulls="false" />
								<display:column title="學號" property="student_no" nulls="false" />
								<display:column title="姓名" property="student_name" nulls="false" />
								<display:column title="班級" property="ClassName" nulls="false" />
								<display:column title="課程名稱" property="chi_name" nulls="false" />
								<display:column title="課程代碼" property="cscode" nulls="false" />
								<display:column title="選別" property="opt" nulls="false" />	 
								<display:column title="狀態" property="status" nulls="false" />	
								<display:column title="學分" property="credit" nulls="false" />
								<display:column title="週一" property="day1" nulls="false" />
								<display:column title="週二" property="day2" nulls="false" />
								<display:column title="週三" property="day3" nulls="false" />
								<display:column title="週四" property="day4" nulls="false" />
								<display:column title="週五" property="day5" nulls="false" />
								<display:column title="週六" property="day6" nulls="false" />
								<display:column title="週日" property="day7" nulls="false" />	        
    							</display:table>											
								</td>
							</tr>
						</table>
					
						</td>
					</tr>
				</table>
					
				</td>
			</tr>
		</table>
	</td>
</tr>
<c:if test="${sselds!=null}">
<tr>
	<td>
	
	<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
	
			<table width="100%">
				<tr>
					<td><img src="images/book_add.gif">
					</td>
					<td align="left" width="100%">
					學生可選修課程 
					</td>
				</tr>
				<tr>
					<td colspan="2">
	
					<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
					<display:table name="${sselds}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
						<display:column title="<script>generateTriggerAll(${fn:length(sselds)}, 'sselds'); </script>" class="center" >
					    <script>generateCheckbox("${row.Oid}", "sselds")</script></display:column>
						<display:column title="梯次" property="seqno" nulls="false" />
						<display:column title="班級名稱" property="name" nulls="false" />
						<display:column title="班級代碼" property="no" nulls="false" />
						<display:column title="課程名稱" property="chi_name" nulls="false" />
						<display:column title="課程代碼" property="cscode" nulls="false" />
						<display:column title="選別" property="opt" nulls="false" />	 
						<display:column title="學分" property="credit" nulls="false" />
						<display:column title="時數" property="thour" nulls="false" />
						<display:column title="週一" property="day1" nulls="false" />
						<display:column title="週二" property="day2" nulls="false" />
						<display:column title="週三" property="day3" nulls="false" />
						<display:column title="週四" property="day4" nulls="false" />
						<display:column title="週五" property="day5" nulls="false" />
						<display:column title="週六" property="day6" nulls="false" />
						<display:column title="週日" property="day7" nulls="false" />
					</display:table>
					
					</td>
				</tr>
			</table>
	
			</td>
		</tr>
	</table>
	
	
	
	</td>
</tr>
</c:if>
<tr height="30">
	<td class="fullColorTable" align="center" width="100%">
	<c:if test="${sselds!=null}">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='AddConfirmed'/>"
						   class="gSubmit">
	</c:if>
	</td>
</tr>

</c:if>
<!-- 加選作業 End -->


<!-- 退選作業 Start -->
<c:if test="${mode=='remove'}">
<tr>
	<td>
	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<display:table name="${selected}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	<display:column title="<script>generateTriggerAll(${fn:length(selected)}, 'selected'); </script>" class="center" >
	<script>generateCheckbox("${row.Oid}", "selected")</script></display:column>
	<display:column title="梯次" property="seqno" nulls="false" />
	<display:column title="學號" property="student_no" nulls="false" />
	<display:column title="姓名" property="student_name" nulls="false" />
	<display:column title="課程名稱" property="chi_name" nulls="false" />
	<display:column title="課程代碼" property="cscode" nulls="false" />
	<display:column title="選別" property="opt" nulls="false" />	 
	<display:column title="學分" property="credit" nulls="false" />
	<display:column title="狀態" property="status" nulls="false" />
	<display:column title="週一" property="day1" nulls="false" />
	<display:column title="週二" property="day2" nulls="false" />
	<display:column title="週三" property="day3" nulls="false" />
	<display:column title="週四" property="day4" nulls="false" />
	<display:column title="週五" property="day5" nulls="false" />
	<display:column title="週六" property="day6" nulls="false" />
	<display:column title="週日" property="day7" nulls="false" />	        
	</display:table>				
	</td>
</tr>
<tr height="30">
	<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='RemoveConfirmed'/>"
						   class="gGreen">
	</td>
</tr>
</c:if>
<!-- 退選作業 End -->
</html:form>
</table>


<script>
	function showInfo(id){
		if(document.getElementById(id).style.display=="inline"){
			document.getElementById(id).style.display="none"
		}else{
			document.getElementById(id).style.display="inline"
		}
		
	}
</script>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>