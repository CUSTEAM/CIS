<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellpadding="0" cellspacing="0">
		
<!-- 1樓start -->
	<tr>
		<td nowrap>
		
		<table class="hairlineTable" align="left">
			<tr>
				<td class="hairlineTdF"><bean:message key="OpenCourse.label.teacherNumber" bundle="COU"/></td>
				<td class="hairlineTd">
				<input type="text" name="idno" id="idno" size="8" style="ime-mode:disabled" autocomplete="off"
				value="${ContractManagerForm.map.idno}" onMouseOver="showHelpMessage('請輸入教師代碼', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"
				onkeyup="if(this.value.length>2)getAny(this.value, 'idno', 'technameS', 'empl', 'no')"
				onclick="this.value='', document.getElementById('technameS').value=''"/><input type="text"
				onkeyup="getAny(this.value, 'technameS', 'idno', 'empl', 'name')" autocomplete="off"
				onclick="this.value='', document.getElementById('idno').value=''" name="teacherName" 
				onMouseOver="showHelpMessage('請輸入教師姓名, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"
				id="technameS" size="12" value="${ContractManagerForm.map.teacherName}"/>
				</td>
				<td width="30" align="center" class="hairlineTdF">	
				<img src="images/16-exc-mark.gif" />
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				系所單位
				</td>
				<td class="hairLineTd">
				<select name="unit">
					<option value=""></option>
					<c:forEach items="${allUnit}" var="c">
					<option <c:if test="${ContractManagerForm.map.unit==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 1樓end -->





<!-- 3樓start -->
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				教師分類
				</td>
				<td class="hairLineTd">
				<select name="type">
				<option value=""></option>
				<c:forEach items="${type}" var="t">
				<option <c:if test="${ContractManagerForm.map.type==t.id}">selected</c:if> value="${t.id}">${t.name}</option>
				</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				職級
				</td>
				<td class="hairLineTd">
				<select name="level">
				<option value=""></option>
				<c:forEach items="${level}" var="t">
				<option <c:if test="${ContractManagerForm.map.level==t.id}">selected</c:if> value="${t.id}">${t.name}</option>
				</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				審定
				</td>
				<td class="hairLineTd">
				<select name="markup">
				<option value=""></option>
				<c:forEach items="${markup}" var="t">
				<option <c:if test="${ContractManagerForm.map.markup==t.id}">selected</c:if> value="${t.id}">${t.name}</option>
				</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 3樓end -->
<!-- 2樓start -->
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>專 / 兼任</td>
				<td class="hairLineTd">
				<select name="category">
				
				<option <c:if test="${ContractManagerForm.map.category=='0'}">selected</c:if> value="0">專任</option>
				<option <c:if test="${ContractManagerForm.map.category=='1'}">selected</c:if> value="1">兼任</option>
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				聘書開始日期
				</td>
				<td class="hairLineTd">
				<input type="text" name="start_date" id="end_date"
				size="10" value="${ContractManagerForm.map.start_date}"
		 		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
				</td>
			
				<td class="hairLineTdF" nowrap>
				聘書到期日
				</td>
				<td class="hairLineTd">
				<input type="text" name="end_date" id="end_date"
				size="10" value="${ContractManagerForm.map.end_date}"
		 		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/date.gif" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>

<!-- 2樓end -->

	<tr>
		<td id="help" style="display:none;">		
		<table width="99%" align="center">
			<tr>
				<td>
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>			
				
				<table width="100%">
					<tr>
						<td>
						
						How to....
						
						</td>
					</tr>
				</table>		
				
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				</td>
			</tr>
		</table>		
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit"
				   name="method" id="SearchContract"
				   onMouseOver="showHelpMessage('查詢聘書是根據以上欄位所輸入的資料進行搜尋<br>可利用搜尋結果直接修正聘約或是列印聘書及報表', 'inline', this.id)" 
			 	   onMouseOut="showHelpMessage('', 'none', this.id)"
				   value="<bean:message key='SearchContract' bundle="PSN"/>"
				   class="CourseButton" /><INPUT type="submit"
				   name="method" id="CreateNewContract"
				   onMouseOver="showHelpMessage('建立聘書是根據以上欄位所輸入的資料進行搜尋<br>再勾選欲建立的聘約', 'inline', this.id)" 
			 	   onMouseOut="showHelpMessage('', 'none', this.id)"
				   value="<bean:message key='CreateNewContract' bundle="PSN"/>"
				   class="CourseButton" /><input type="button" value="說明(H)" onClick="showObj('help')" class="CourseButton" />
		
		</td>
	</tr>
	
</table>