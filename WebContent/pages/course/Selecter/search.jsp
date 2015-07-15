<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">目標學期</td>
				<td class="hairLineTd">
					<select name="Sterm">
						<option <c:if test="${SelecterForm.map.Sterm=='1'}" >selected</c:if> value="1">上學期</option>
						<option <c:if test="${SelecterForm.map.Sterm=='2'}" >selected</c:if> value="2">下學期</option>
					</select>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">學生學號</td>
				<td class="hairLineTd">
				<input type="text" name="studentId" id="studentId" size="8" style="ime-mode:disabled" autocomplete="off"
				value="${SelecterForm.map.studentId}"
				onkeyup="if(this.value.length>4)getAny(this.value, 'studentId', 'studentName', 'stmd', 'no')"
				onclick="this.value='', document.getElementById('studentName').value=''"
				/><input type="text"
				onkeyup="getAny(this.value, 'studentName', 'studentId', 'stmd', 'name')"
				onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
				onclick="this.value='', document.getElementById('studentId').value=''"
				name="studentName" id="studentName" size="12" value="${SelecterForm.map.studentName}"/>
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
				 value="${SelecterForm.map.departClass}"
				 onkeyup="getAny(this.value, 'departClass', 'classLess', 'Sabbr', 'no')"
				 onclick="this.value='', document.getElementById('classLess').value=''"/><input 
				 type="text" name="classLess" id="classLess"
				value="${SelecterForm.map.classLess}" size="12"
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
				value="${SelecterForm.map.courseNumber}"
				onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
				onclick="this.value='', courseName.value=''"/><input type="text" autocomplete="off"
				name="courseName" id="csnameS" size="16"
				value="${SelecterForm.map.courseName}"
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
	<tr>
		<td id="hpmsg" style="display:none;">
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				
				<OL>
				
					<li>指定一個班級或一名學生，若產生矛盾系統會自動排除
					<li>查看學生必修、選修的數字是否達到標準						
						
					<ul>
						<li>若不符標準可用手動折抵，做過的折抵會留存記錄，下次無需再折抵
						<li>若無本校成績可折抵請將待折抵的方塊中「轉學抵免」勾選，系統自動建立該年度抵免(無分數)
						<li>若學生先修或其它狀況可在下方「查看歷年成績」任何地方加上註記，以便日後折抵
					</ul>
					
					
					
					
					
					<li>準畢業生只要將「審核確認」勾選即可
					<li>確定畢業只要將「審核確認」旁邊的框框按一下即可自動產生「畢業號碼」
					<li>按下最底端的「繼續」按鈕將會做一次確認，若學生已取得「畢業號」將會自動將成績、操行、缺曠...等建檔，
					同時電子郵件通知圖書館，最後將選課和加退選歷程、CIS系統帳號...等相關資料清除....很多很多，最後將學生移至離校名單。
								
				</OL>
				
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">
			<INPUT type="submit" name="method" value="<bean:message key='AddCourse'/>" class="gSubmit">
			<INPUT type="submit" name="method" value="<bean:message key='RemoveCourse'/>" class="gCancle">
			<INPUT type="button" id="help" value="說明" class="gCancle" onClick="showObj('hpmsg');">
		</td>
	</tr>
</table>