<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>		
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td>
<script>
function checkView(id){
	document.getElementById("normal").style.display="none";
	document.getElementById("extra").style.display="none";
	document.getElementById('searchType').value=id;
	document.getElementById(id).style.display="inline";
}
</script>
				<input type="hidden" name="searchType" id="searchType" value="${CheckCsGroupForm.map.searchType}" />
				<br>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="10" align="left" nowrap>
				  			<hr noshade class="myHr"/>
							</td>
							<td width="24" align="center" nowrap>
							<img src="images/folder_find.gif" />
							</td>
							<td nowrap>
							<input type="button" class="gCancel" value="簡易搜尋" onClick="checkView('normal')" 
							id="faset"onMouseOver="showHelpMessage('找尋符合資格的學生', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
							</td>
							<td width="100%" align="left">
				  			<hr noshade class="myHr"/>
						</td>
					</tr>
				</table>				
				
				</td>
			</tr>
		
			<tr>
				<td <c:if test="${CheckCsGroupForm.map.searchType=='normal'||CheckCsGroupForm.map.searchType==''}">style="display:inline;"</c:if>
				<c:if test="${CheckCsGroupForm.map.searchType!='normal'}">style="display:none;"</c:if> id="normal">				
				
				<table class="hairlineTable">
					<tr>
						<td align="center" class="hairlineTdF" nowrap>設定範圍</td>
						<td align="center" class="hairlineTdF" nowrap>						
						
						<select name="dept" onChange="document.getElementById('classLess').value='___'+this.value">
							<option <c:if test="${CheckCsGroupForm.map.dept==''}">selected</c:if> value="">請選擇系所</option>
							<c:forEach items="${allDept}" var="i">
							<option <c:if test="${CheckCsGroupForm.map.dept==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>					
							</c:forEach>
						</select>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				
				<br>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="10" align="left" nowrap>
				  			<hr noshade class="myHr"/>
							</td>
							<td width="24" align="center" nowrap>
							<img src="images/folder_magnify.gif" />
							</td>
							<td nowrap>
							<input type="button" class="gGreen" value="進階搜尋" onClick="checkView('extra')"
							id="exset"onMouseOver="showHelpMessage('以各項條件搜尋正在修課的學生', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
							
							
							</td>
							<td width="100%" align="left">
				  			<hr noshade class="myHr"/>
						</td>
					</tr>
				</table>				
				
				</td>
			</tr>
			<tr>
				<td id="extra" <c:if test="${CheckCsGroupForm.map.searchType=='extra'}">style="display:inline;"</c:if><c:if test="${CheckCsGroupForm.map.searchType!='extra'}">style="display:none;"</c:if>>
				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td>
						<table class="hairlineTable">
							<tr>
								<td align="center" class="hairlineTdF" nowrap>設定範圍</td>
								<td class="hairlineTd">
								<%@ include file="/pages/include/SelectClass/ClasSelectAllCsGroup.jsp" %>
								</td>
							</tr>
						</table>
						
						</td>
						<td>
						
						<table class="hairlineTable">
							<tr>
								<td align="center" class="hairlineTdF" nowrap>目標</td>				
								<td class="hairlineTd">
								<select name="type">
									<option <c:if test="${CheckCsGroupForm.map.type=='C'}">selected</c:if> value="C">學程符合資格(已取得)</option>	
									<option <c:if test="${CheckCsGroupForm.map.type=='A'}">selected</c:if> value="A">學程參與學生(未取得)</option>																	
								</select>
								</td>
								<td class="hairLineTdF" width="30" align="center">
								<img src="images/icon/icon_info.gif" id="typeHelp" 
								onMouseOver="showHelpMessage('全部學生: 包含尚未取得學程的學生<br>符合資格: 只顯示符合資格的學生', 'inline', this.id)" 
								onMouseOut="showHelpMessage('', 'none', this.id)"/>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>				
				
				<table class="hairlineTable">
					<tr bgcolor="#f0fcd7">
						<td align="center" class="hairlineTdF">學號</td>				
						<td class="hairlineTd">				
						<input type="text" name="student_no" id="student_no" size="16" style="ime-mode:disabled" autocomplete="off"
						 value="${CheckCsGroupForm.map.student_no}"
						 onMouseOver="showHelpMessage('請輸入學號, 若您是用貼上請點一下方向鍵以便自動完成學生姓名', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
						 onkeyup="if(this.value.length>1)getAny(this.value, 'student_no', 'student_name', 'stmd', 'no')"
						 onclick="this.value='', document.getElementById('student_name').value='';"/><input type="text" 
						 value="${CheckCsGroupForm.map.student_name}" onMouseOver="showHelpMessage('請輸入姓名, 若您是用貼上請動一下方向鍵以便自動完成學號', 
						 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
						 
						  onkeyup="if(this.value.length>1)getAny(this.value, 'student_name', 'student_no', 'stmd', 'name')"
						  onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
						  onclick="this.value='', document.getElementById('student_no').value='';" name="student_name" id="student_name" size="8" />
						 </td>
				 		 <td width="30" align="center" class="hairlineTdF">
				 		 <img src="images/16-exc-mark.gif" id="noHelp" onMouseOver="showHelpMessage('查詢單一學生時, 可以用不完整的學號或姓名來做為關鍵字', 'inline', this.id)" 
								onMouseOut="showHelpMessage('', 'none', this.id)"/>
						</td>
					</tr>
				</table>				
				
				</td>
			</tr>			
			<!-- 幫助列 start -->
			<tr>
				<td>		
				<table width="99%" align="center" id="help" style="display:inline;">
					<tr>
						<td class="hairlineTdF" nowrap>
						1. 簡易搜尋能以科系為條件，找到符合資格的學生。<br>
						2. 進階搜尋能以單一學生或特定條件，找到正在參與的學生(未取得)或是已符合資格的學生。<br>
						3. 縮小查詢條件能加快執行的效率。						
						</td>
					</tr>
	
				</table>
				</td>
			</tr>
			<!-- 幫助列 end -->
			
			<tr height="30">
				<td class="fullColorTable" align="center">
				<INPUT type="submit"name="method"value="<bean:message key='Query'/>"class="gSubmit"
				id="submit" onMouseOver="showHelpMessage('開始', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
				
				<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>