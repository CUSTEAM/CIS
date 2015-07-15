<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<tr>
		<td><input type="hidden" name="exSearch" value="${StudentManagerForm.map.exSearch}" /><br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/folder_find.gif">
    			</td>
    			<td nowrap>
    			<input type="button" onClick="showSearch(), clearStudent()" class="gCancel" value="快速搜尋" />
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		</td>
	</tr>
<script>
function clearStudent(){
	document.getElementById('studentNo').value="";
	document.getElementById('studentName').value="";
	document.getElementById('SstudentNo').value="";
	document.getElementById('SstudentName').value="";
}
</script>

	<tr>
		<td>
		<table width="100%">
			<tr>
				<td>	
				<table width="100%">
					<tr>
						<td>
						<table width="100%" <c:if test="${StudentManagerForm.map.exSearch!=''}">style="display:none"</c:if> id="onlyEdit">
														
							<tr>
								<td>
								<table class="hairLineTable">
									<tr>
										<td class="hairLineTdF" id="pifname" onMouseOver="showHelpMessage('無論是否在學皆可查到', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)">
										學號姓名
										</td>
										<td class="hairLineTd">
										<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
										name="studentNo" id="studentNo" size="12" value="${StudentManagerForm.map.studentNo}"
										onkeyup="if(this.value.length>=2)GgetAny(this.value, 'studentNo', 'studentName', 'gstmd', 'no')" 
										onMouseOver="showHelpMessage('學號輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成姓名', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)" onFocus="chInput(this.id)"
										onClick="clearQuery()" /><input onMouseOver="showHelpMessage('姓名輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成學號', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)" onFocus="chInput(this.id)"
										type="text" name="studentName" id="studentName" size="10" value="${StudentManagerForm.map.studentName}"
							 			onkeyup="if(this.value.length>1)GgetAny(this.value, 'studentName', 'studentNo', 'gstmd', 'name')" onClick="clearQuery()" />
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
				</table>
				
				</td>
			</tr>
			
		</table>
		
		</td>
	</tr>
	
<!-- 進階搜尋 -->
<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr style="cursor:pointer;">
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/folder_explore.gif">
    			</td>
    			<td nowrap>
    			<input type="button" onClick="showSearch()" class="gGreen" value="進階搜尋" />
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td>
				
			<table width="100%" <c:if test="${StudentManagerForm.map.exSearch==''}">style="display:none"</c:if> id="searchBar">
				<tr>
					<td>
					<table>
					
						<tr>
							<td>							
							<table>
								<tr>
									
									<td>
									<table class="hairLineTable">
									<tr>
									<td class="hairLineTdF">學號</td>
									<td class="hairLineTd" nowrap>	
				 					<input type="text" name="studentNo" size="10" onClick="this.value=''" 
				 		 			autocomplete="off" style="ime-mode:disabled" autocomplete="off" onFocus="chInput(this.id)"
				 		 			value="${StudentManagerForm.map.studentNo}" <c:if test="${StudentManagerForm.map.exSearch==''}">disabled</c:if> 
				 		 			id="SstudentNo" /><input type="text" name="studentName" size="6" onClick="this.value=''" onFocus="chInput(this.id)"
				 		 			value="${StudentManagerForm.map.studentName}" <c:if test="${StudentManagerForm.map.exSearch==''}">disabled</c:if> id="SstudentName" />	
				 					</td>
				 					<td class="hairLineTdF" width="30" align="center">
										<img src="images/16-exc-mark.gif" />
									</td>
				 					</tr>
				 					</table>
									</td>
									
									
									<td>
									<table class="hairLineTable">
									<tr>
									<td class="hairLineTdF">班級</td>
									<td class="hairLineTd" nowrap>
									<input type="text" id="classNo" name="classNo" size="6" 
									autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 		 			value="${StudentManagerForm.map.classNo}" onFocus="chInput(this.id)"
				 		 			onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 		 			onclick="this.value='', document.getElementById('className').value=''"/><input 
				 		 			type="text" name="className" id="className"
				 		 			value="${StudentManagerForm.map.className}" onFocus="chInput(this.id)"
				 		 			size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
				 		 			onclick="this.value='', document.getElementById('classNo').value=''"/>
				 					</td>
				 					<td class="hairLineTdF" width="30" align="center">
										<img src="images/16-exc-mark.gif" />
									</td>
				 					</tr>
				 					</table>
									</td>		
														
									<td>
									<table class="hairLineTable">
									<tr>
									<td class="hairLineTdF">目標</td>
									<td class="hairLineTd">
									<div name="selectBox" type="border" id="select_GorS">
				 					<select name="GorS" id="GorS" onFocus="chInput(this.id)">
				 						<option <c:if test="${StudentManagerForm.map.GorS=='s'}">selected</c:if> value="s">在校</option>
				 						<option <c:if test="${StudentManagerForm.map.GorS=='g'}">selected</c:if> value="g">離校</option>
				 						<option <c:if test="${StudentManagerForm.map.GorS=='a'}">selected</c:if> value="a">全部</option>
				 					</select>
				 					</div>
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
		
							<table>
							<tr>
							<td>
							<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">身分證</td>
								<td class="hairLineTd">
								<input type="text" name="idno" autocomplete="off" size="8" id="idno" onFocus="chInput(this.id)"
						 		style="ime-mode:disabled" autocomplete="off" value="${StudentManagerForm.map.idno}" />
								</td>
								</tr>
							</table>
							</td>
						
							<td>						
							<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">性別</td>
								<td class="hairLineTd">
								<select name="sex">
									<option <c:if test="${StudentManagerForm.map.sex==''}">selected</c:if> value=""></option>
									<option <c:if test="${StudentManagerForm.map.sex=='1'}">selected</c:if> value="1">男 </option>
									<option <c:if test="${StudentManagerForm.map.sex=='2'}">selected</c:if> value="2">女</option>
								</select>
								</td>
								</tr>
							</table>
							</td>
				
							<td>
							<table class="hairLineTable">
							<tr>
							<td class="hairLineTdF">生日</td>
							<td class="hairLineTd">
							<input type="text" name="birthday" id="birthday"
							size="4" value="${StudentManagerForm.map.birthday}"
						 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						 	onMouseOver="showHelpMessage('查詢請用西元年, 例:<br>1978-07-08(67/7/8的學生)<br>1978 (67年次的學生)<br>%-07 (7月的學生)<br>%-%-08 (8號的學生)',
						 	'inline', this.id)" 
				 			onMouseOut="showHelpMessage('', 'none', this.id)">
							</td>
							<td class="hairLineTdF" width="30" align="center">
								<img src="images/date.gif" />
								</td>
							</tr>
							</table>
							<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
							<tr>
							<td id="ds_calclass"></td>
							</tr>
							</table>
						
							</td>
				
							<td>
							<table class="hairLineTable">
							<tr>
							<td class="hairLineTdF">出生地</td>
							<td class="hairLineTd">
							<select name="birth_province">
							<option value="">國/省/縣</option>
							<c:forEach items="${birth_province}" var="b">
							<option <c:if test="${StudentManagerForm.map.birth_province==b.no}">selected</c:if> value="${b.no}">${b.name}</option>
							</c:forEach>
							</select><select name="birth_county">
							<option value="">鄉/鎮/市</option>
							<c:forEach items="${birth_province}" var="b1">
								<option <c:if test="${StudentManagerForm.map.birth_county==b1.no}">selected</c:if> value="${b1.no}">${b1.name}</option>
							</c:forEach>
							</select>
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
				
						<table>
							<tr>
								<td>
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">通訊地址</td>
								<td class="hairLineTd">
								<input type="text" name="curr_post" size="1" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
								 value="${StudentManagerForm.map.curr_post}" id="curr_post" /><input 
								 type="text" name="curr_addr" value="${StudentManagerForm.map.curr_addr}" id="curr_addr" />
								</td>
								</tr>
								</table>
						
								</td>
						
								<td>
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">永久地址</td>
									
								<td class="hairLineTd">
								<input type="text" name="perm_post" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
								 value="${StudentManagerForm.map.perm_post}" id="perm_post" size="1" /><input 
								 type="text" name="perm_addr" value="${StudentManagerForm.map.perm_addr}" id="perm_addr" />
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
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>
								<table>
								<tr>						
								<td>
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">連絡電話</td>
								<td class="hairLineTd">
								<input type="text" name="telephone" size="10" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
								 value="${StudentManagerForm.map.telephone}" onClick="this.value=''" />
								</td>
								
								<td class="hairLineTdF">行動電話</td>
								<td class="hairLineTd">
								<input type="text" name="CellPhone" size="10" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
								 value="${StudentManagerForm.map.CellPhone}" onClick="this.value=''" />
								</td>
								</tr>
								</table>
								</td>
								</tr>
								</table>
						
								</td>
								<td>
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">家長姓名</td>
								<td class="hairLineTd">
								<input type="text" name="parent_name" size="10" value="${StudentManagerForm.map.parent_name}" />
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
				
						<table>
							<tr>
					
								<td>
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">入學年月</td>
								<td class="hairLineTd">
								<input type="text" name="entrance" value="${StudentManagerForm.map.entrance}" size="2" onClick="this.value=''"
								autocomplete="off" style="ime-mode:disabled" autocomplete="off" onClick="this.value=''" />
								</td>
								
								<td class="hairLineTdF">前學程畢業年度</td>
								<td class="hairLineTd">
								<input type="text" name="gradyear" id="gradyear" size="2" value="${StudentManagerForm.map.gradyear}" onClick="this.value=''"
								autocomplete="off" style="ime-mode:disabled" autocomplete="off"
								onMouseOver="showHelpMessage('請輸入民國年',
						 		'inline', this.id)" 
				 				onMouseOut="showHelpMessage('', 'none', this.id)" />
								</td>
								</tr>
								</table>						
								</td>
						
								<td>
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">入學身份</td>
								<td class="hairLineTd">
								<select name="ident">
										<option <c:if test="${StudentManagerForm.map.ident==''}">selected</c:if> value=""></option>
									<c:forEach items="${ident}" var="i">
										<option <c:if test="${StudentManagerForm.map.ident==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>
									</c:forEach>
								</select>
								</td>
								</tr>
								</table>						
								</td>
						
								<td>
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">組別</td>
								<td class="hairLineTd">
								<select name="divi">
									<option <c:if test="${StudentManagerForm.map.divi==''}">selected</c:if> value="">選擇組別<option>
										<option <c:if test="${StudentManagerForm.map.divi==''}">selected</c:if> value=""></option>
									<c:forEach items="${Group}" var="g">
										<option <c:if test="${StudentManagerForm.map.divi==g.idno}">selected</c:if> value="${g.idno}">${g.name}</option>
									</c:forEach>
								</select>
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
						<table>
							<tr>
							<td>
							<table class="hairLineTable">
								<tr>
									<td class="hairLineTdF">前次異動</td>
									<td class="hairLineTd">	
				 					<select name="occur_status">
				 						<option value=""></option>
				 						<c:forEach items="${occur_status}" var="stat">
				 						<option <c:if test="${StudentManagerForm.map.occur_status==stat.idno}">selected</c:if> value="${stat.idno}">${stat.name}</option>
				 						</c:forEach>
				 					</select><select name="occur_cause">
				 								<option <c:if test="${StudentManagerForm.map.occur_cause==''}">selected</c:if> value="">原因<option>
												<c:forEach items="${cause}" var="oc">
												<option <c:if test="${StudentManagerForm.map.occur_cause==oc.idno}">selected</c:if> value="${oc.idno}">${oc.name}</option>
												</c:forEach>
												</option>
				 							</select>
				 					</td>
				 				
								<td class="hairLineTdF">異動學年(期)</td>
								<td class="hairLineTd">
								<input type="text" name="occur_year" value="${StudentManagerForm.map.occur_year}" size="1" onClick="this.value=''" id="occur_year"
								autocomplete="off" style="ime-mode:disabled" autocomplete="off"  autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="occur_term"
								onMouseOver="showHelpMessage('點擊修正發生學年, 空白視為未修改', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/><input type="text" 
								name="occur_term" value="${StudentManagerForm.map.occur_term}" size="1" onClick="this.value=''" id="occur_term"
								autocomplete="off" style="ime-mode:disabled" autocomplete="off"  autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="occur_term"
								onMouseOver="showHelpMessage('點擊修正發生學期, 空白視為未修改', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
								</td>
								</tr>
								</table>						
							</td>
						
							<td>
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">異動日期</td>
								<td class="hairLineTd">	
				 				<input type="text" name="occur_date" id="occur_date"
								size="4" value="${StudentManagerForm.map.occur_date}"
						 		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						 		onMouseOver="showHelpMessage('查詢請用西元年, 例:<br>1978-07-08(民國67/7/8發生的事情)<br>1978 (民國67年發生的事情)<br>%-07 (7月的學生)<br>%-%-08 (8號的學生)',
						 		'inline', this.id)" 
				 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				 				</td>
				 				<td class="hairLineTdF" width="30" align="center">
								<img src="images/date.gif" />
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
				
						<table>
							<tr>
								<td>
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">畢業學歷</td>
								<td class="hairLineTd">
								<input type="text" name="schl_code" id="schl_code" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
								 onkeyup="getAny(this.value, 'schl_code', 'schl_name', 'gradSchool', 'no')" onClick="this.value=''"
								 size="2" value="${StudentManagerForm.map.schl_code}" /><input type="text" name="schl_name" 
								 id="schl_name" value="${StudentManagerForm.map.schl_name}" onClick="this.value=''"
						 		onkeyup="getAny(this.value, 'schl_name', 'schl_code', 'gradSchool', 'name')" />
								</td>
								<td class="hairLineTdF" width="30" align="center">
								<img src="images/16-exc-mark.gif" />
								</td>
								</tr>
								</table>
						
								</td>
						
								<td>
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">科系</td>
								<td class="hairLineTd">
								<input type="text" name="grad_dept" size="6" value="${StudentManagerForm.map.grad_dept}" />
								</td>
								</tr>
								</table>
						
								</td>
						
								<td>
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">畢/肄業</td>
								<td class="hairLineTd">
								<select name="gradu_status">
									<option <c:if test="${StudentManagerForm.map.gradu_status==''}">selected</c:if> value=""></option>
									<option <c:if test="${StudentManagerForm.map.gradu_status=='1'}">selected</c:if> value="1">畢業</option>
									<option <c:if test="${StudentManagerForm.map.gradu_status=='2'}">selected</c:if> value="2">肄業</option>
								</select>
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
						<table>
							<tr>
								<td>	
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">本校文號</td>
								<td class="hairLineTd">
								<input type="text" name="occur_docno" id="occur_docno" size="12" value="${StudentManagerForm.map.occur_docno}" onClick="this.value=''" />
						
								</td>
								</tr>
								</table>
								
								</td>
								
								<td>
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">畢業文號</td>
								<td class="hairLineTd">
						
								<input type="text" name="occur_graduate_no" id="occur_graduate_no" size="12" value="${StudentManagerForm.map.occur_graduate_no}" onClick="this.value=''" />
						
								</td>
								</tr>
								</table>
								
								</td>
								
								<td>
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">輔系雙修</td>
								<td class="hairLineTd">
								
								
								
								
								
								
								<select name="ExtraStatus">
							<option value="">輔系雙主修</option>
							<option value="1">正在輔修</option>
							<option value="2">正在雙主修</option>
							<option value="E">取得輔修資格</option>
							<option value="T">取得雙主修資格</option>
						</select>
						</td>
						<td class="hairLineTd">
						
						<select name="ExtraDept">
							<option value=""></option>
							<c:forEach items="${Dept}" var="d">
							<option value="${d.idno}">${d.name}</option>						
							</c:forEach>
						</select>
								
								
								
								
								
								
								
								
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
			
		</table>
		</td>
	</tr>
<!-- 進階查詢 end -->
