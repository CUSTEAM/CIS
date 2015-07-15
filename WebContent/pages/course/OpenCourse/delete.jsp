<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script>
		generateTableBanner('<table align="left"><tr>'+
							'<td align="left">&nbsp;&nbsp;<img src="images/24-book-green-remove.png"></td>'+
							'<td>開課作業 - 刪除模式</td></tr></table>');
	</script>
	<tr>
		<td>
		<br>&nbsp;&nbsp;下列學生的選課記錄即將刪除, 請稍做檢查, 這些資料將無法復原.
		</td>
	</tr>
	<tr>
		<td>
			<display:table name="${delStudents}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="<input type='checkbox' checked disabled>" property="nullValue" nulls="false" />
	        <display:column title="學生學號" property="student_no" sortable="true" class="right" />
	        <display:column title="學生姓名" property="student_name" sortable="true" class="left" />
	        <display:column title="學生班級代碼"  property="ClassNo" sortable="true" class="right" />
	        <display:column title="學生所屬班級" property="ClassName" sortable="true" class="left" />
	        <display:column title="開課班級代碼" property="depart_class" sortable="true" class="right" />
	        <display:column title="開課班級名稱" property="ClassName2" sortable="true" class="left" />
	        <display:column title="課程代碼" property="cscode" sortable="true" class="right" />
	        <display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	        <display:column title="學分" property="credit" sortable="true" class="left" />
	        <display:column title="時數" property="thour" sortable="true" class="left" />
	      </display:table>
		</td>
	</tr>
	<tr>
		<td colspan="7" align="center" height="40">
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F">
  				<tr>
    				<td bgcolor="#FFFFFF">
    					<table>
    						<tr>
    							<td>
    							 選擇輸出格式:
    							</td>
    							<td>
    							<a href="course/export/delStu4DelDtime.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/delStu4DelDtime.jsp?type=word">
    							<img src="images/ico_file_word.png" border="0"> Word
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<script>
	generateTableBanner("");
	</script>
	<tr>
		<td>
		<br>&nbsp;&nbsp;下列教師的授課即將刪除, 請稍做檢查並列印通知單, 這些資料將無法回溯.
		</td>
	</tr>
	<tr>
		<td>
			<display:table name="${delTeachers}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="<input type='checkbox' checked disabled>" property="nullValue" nulls="false" />
	        <display:column title="教師編號" property="idno" sortable="true" class="left" />
	        <display:column title="教師姓名" property="cname" sortable="true" class="left" />
	        <display:column title="課程代碼"  property="cscode" sortable="true" class="left" />
	        <display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	        <display:column title="開課班級" property="depart_class" sortable="true" class="left" />
	        <display:column title="學生選修人數" property="stu_select" sortable="true" class="left" />
	      </display:table>
		</td>
	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="gCancel">'+
	' <INPUT type="submit" name="method" value="<bean:message key='DeleteConfirm'/>" class="gSubmit">');
	</script>