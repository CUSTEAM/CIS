<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Registration/StudentManager" method="post" enctype="multipart/form-data" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/16-manager-st.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;學籍管理&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>	
<!-- 標題列 end -->
<!-- 主查詢界面 start-->
<c:if test="${Gtype==null}">

<!-- 快速查詢 start -->	

	<tr>
		<td><input type="hidden" name="exSearch" value="${StudentManagerForm.map.exSearch}" /><br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr onClick="showSearch()" style="cursor:pointer;">
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/folder_find.gif" id="searchNorm" 
    			 onMouseOver="showHelpMessage('點擊此處 開啟/關閉 進階搜尋', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)">
    			</td>
    			<td nowrap style="">
    			快速搜索&nbsp;
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
		<table width="100%">
			<tr>
				<td>	
				<table width="100%">
					<tr>
						<td>
						<table width="100%">
														
							<tr <c:if test="${StudentManagerForm.map.exSearch!=''}">style="display:none"</c:if> id="onlyEdit">
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
										onkeyup="if(this.value.length>=6)GgetAny(this.value, 'studentNo', 'studentName', 'gstmd', 'no')" 
										onMouseOver="showHelpMessage('學號輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成姓名', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)"
										onClick="clearQuery()" /><input onMouseOver="showHelpMessage('姓名輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成學號', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)"
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

<!-- 快速查詢 end-->

<!-- 進階查詢 start-->
	<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0" onClick="showSearch()">
  			<tr style="cursor:pointer;">
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/folder_explore.gif" id="searchEx" 
    			 onMouseOver="showHelpMessage('點擊此處 開啟/關閉 進階搜尋', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)">
    			</td>
    			<td nowrap>
    			進階搜索&nbsp;
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
				
			<table width="100%">
				<tr <c:if test="${StudentManagerForm.map.exSearch==''}">style="display:none"</c:if> id="searchBar">
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
				 		 			autocomplete="off" style="ime-mode:disabled" autocomplete="off" 
				 		 			value="${StudentManagerForm.map.studentNo}" <c:if test="${StudentManagerForm.map.exSearch==''}">disabled</c:if> 
				 		 			id="SstudentNo" /><input type="text" name="studentName" size="6" onClick="this.value=''"
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
				 		 			value="${StudentManagerForm.map.classNo}"
				 		 			onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 		 			onclick="this.value='', document.getElementById('className').value=''"/><input 
				 		 			type="text" name="className" id="className"
				 		 			value="${StudentManagerForm.map.className}"
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
				 					<select name="GorS">
				 						<option <c:if test="${StudentManagerForm.map.GorS=='s'}">selected</c:if> value="s">在校</option>
				 						<option <c:if test="${StudentManagerForm.map.GorS=='g'}">selected</c:if> value="g">離校</option>
				 						<option <c:if test="${StudentManagerForm.map.GorS=='a'}">selected</c:if> value="a">全部</option>
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
								<td class="hairLineTdF">身分證</td>
								<td class="hairLineTd">
								<input type="text" name="idno" autocomplete="off" size="8"
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
								<td class="hairLineTdF">入學年度</td>
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
	
	
	<tr>
		<td class="fullColorTable" align="center" width="100%">
		<table>
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   id="continue"
						   onMouseOver="showHelpMessage('學號完整並對應姓名會按照學生目前狀態進行下一步驟,<br>'+
						   '若無對應則認定為新生直接進入轉入模式', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)"
						   value="<bean:message key='Continue'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method" id="Cancel"
													   value="<bean:message
													   key='Cancel'/>"
													   class="CourseButton" 
													   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
													   onMouseOut="showHelpMessage('', 'none', this.id)" />
				<a href="/CIS/pages/course/UploadStdImage.jsp" TARGET="_TOP"><img src="images/page_user.gif" id="picture" border="0" 
							onMouseOver="showHelpMessage('<font color=red>批次</font>更新<font color=red>多筆</font>照片', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)"></a>
				</td>
			</tr>
		</table>
		</td>
	</tr>


<!-- 列表 start -->	
<c:if test="${students!=null}">
	<tr>
		<td>
		<table width="100%">
			<tr>
				<td>
				<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
				<display:table name="${students}" pagesize="10" id="row" sort="list" class="list">
				<display:column title="<script>generateTriggerAll(${fn:length(students)}, 'stus'); </script>" class="center" >
	          		<script>generateCheckbox("${row.student_no}", "stus")</script>
	          	</display:column>
	          	<display:column title="學號" property="student_no" sortable="true" class="center" />
	          	<display:column title="姓名" property="student_name" sortable="true" class="center" />
				<display:column title="班級名稱" property="ClassName" sortable="true" class="center" />
				<display:column title="狀態" property="name" sortable="true" class="center" />				
				</display:table>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 列表 end-->	
	<tr>
		<td align="left">

		<table class="hairLineTable" width="99%">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>	    							
    							
    							<img src="images/printer.gif" border="0">
    							<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool"
						   		 onMouseOver="showHelpMessage('校內用的表格', 'inline', this.id)"
						   		 onMouseOut="showHelpMessage('', 'none', this.id)">
    								<option value="javascript:void(0)">校內表格</option>
    								<option value="/CIS/List4Student">通用報表</option>
    								<option value="/CIS/StudentCardForOne">學生證</option>
    								<option value="/CIS/List4NewStudents">新生名冊</option>
									<option value="/CIS/CountStudents4StudentManager">人數統計表</option>
									<option value="/CIS/CountStudentAge">年齡統計表</option>
									<option value="/CIS/GraduateReportPoi?type=confirm">畢業生名冊</option>
									<option value="/CIS/GraduateReportPoi?type=ready">應屆畢業生名冊</option>
									<option value="/CIS/Score4Class">學期成績(總表)</option>
									<option value="/CIS/Score4Personal">學期成績(個人)</option>
									<option value="/CIS/Diploma">畢業證書</option>
									<option value="/CIS/Diploma4NoGraduate">修業證書(中文)</option>
									<option value="/CIS/Diploma4NoGraduate">修業證書(英文)</option>
									<option value="/CIS/StudentList">各班級名條</option>
    							</select>
    							<!--
								<img src="images/icon_pdf.gif" border="0">
    							<select name="reportType" onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">報部表格</option>
    								<option value="javascript:void(0)">囧</option>
    								<option value="javascript:void(0)">囧囧</option>
    								<option value="javascript:void(0)">囧囧囧</option>
    								<option value="javascript:void(0)">囧囧</option>
    								<option value="javascript:void(0)">囧</option>						
    							</select>    							
    							-->
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>

		</td>

	</tr>
	
	<tr>
		<td class="fullColorTable">
		<table align="center">
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Modify'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Cancel'/>"
													   class="CourseButton" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
</c:if>	
	
</c:if>
<!-- 主查詢界面 end-->

<!-- 新生轉入作業 start -->
<c:if test="${Gtype=='add'}">




<!-- 轉學生文號設定 start-->
	<tr>
		<td>
		
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-green.png" />
    			</td>
    			<td nowrap>來源&nbsp;</td>
    			<td nowrap>
    			<select name="iType" onChange="takeTraNo(this.value)">
					<option <c:if test="${StudentManagerForm.map.iType=='tra'||StudentManagerForm.map.iType==''}">selected</c:if> value="tra">他校轉入</option>
					<option <c:if test="${StudentManagerForm.map.iType=='new'}">selected</c:if> value="new">本校新生</option>
				</select>				 
				
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		
		<td>
	</tr>
<!-- 轉學生文號設定 end-->	





<!-- 基本資料設定 start-->	
	<tr>
		<td>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-green.png" />
    			</td>
    			<td nowrap >
    			基本資料&nbsp;
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

		<table width="100%">
			<tr>
				<td>
				
				<table>
					<tr>
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>班級</td>
						<td>
						<input type="text" id="classNo" name="classNo" onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 		 onclick="this.value='', document.getElementById('className').value=''"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 		 value="${StudentManagerForm.map.classNo}" onclick="this.value='', document.getElementById('className').value=''" /><input 
				 		 type="text" name="className" id="className" 
				 		 value="${StudentManagerForm.map.className}" size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
				 		 onclick="this.value='', document.getElementById('classNo').value=''"/>
				 		<img src="images/16-exc-mark.gif" />
				 		
				 		</td>
				 		</tr>
				 		</table>
				 		
						</td>
						<td>
				 			
				 		<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>學號</td>
						<td>	
				 		<input type="text" name="studentNo" size="10" id="studentNo" value="${StudentManagerForm.map.studentNo}" /><input 
				 		type="text" name="studentName" size="10" onClick="this.value=''"
				 		 value="${StudentManagerForm.map.studentName}" />	
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
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>身分證</td>
						<td>
						<input type="text" name="idno" autocomplete="off" size="8"
						 style="ime-mode:disabled" autocomplete="off" value="${StudentManagerForm.map.idno}" />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>性別</td>
						<td>
						<select name="sex">
							<option <c:if test="${StudentManagerForm.map.sex=='1'}">selected</c:if> value="1">男 </option>
							<option <c:if test="${StudentManagerForm.map.sex=='2'}">selected</c:if> value="2">女</option>
						</select>
						</td>
						</tr>
						</table>
						</td>
				
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>生日</td>
						<td>
						<input type="text" name="birthday"  size="4" value="${StudentManagerForm.map.birthday}"
						 onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
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
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>省籍</td>
						<td>
						<select name="birth_province">
						<option value="">國/省/縣</option>
						<c:forEach items="${birth_province}" var="b">
							<option <c:if test="${StudentManagerForm.map.birth_province==b.no}">selected</c:if> value="${b.no}">${b.name}</option>
						</c:forEach>
						</select>
						<select name="birth_county">
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
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>通訊地址</td>
						<td>
						<input type="text" name="curr_post" size="1" value="${StudentManagerForm.map.curr_post}" id="curr_post" />
						<input type="text" name="curr_addr" value="${StudentManagerForm.map.curr_addr}" id="curr_addr" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>永久地址</td>
								<td><input type="checkbox" id="AddrBox" onClick="copyAddr()" 
									 onMouseOver="showHelpMessage('永久地址同通訊地址, 請先輸入通訊地址再點', 'inline', this.id)" 
				 					 onMouseOut="showHelpMessage('', 'none', this.id)" /></td>
							</tr>
						</table>
						</td>
						<td>
						<input type="text" name="perm_post" value="${StudentManagerForm.map.perm_post}" id="perm_post" size="1" />
						<input type="text" name="perm_addr" value="${StudentManagerForm.map.perm_addr}" id="perm_addr" />
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
						
						<table>
						<tr>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>入學照片</td>
						<td>
						<input type="file" name="myImage" size="8" id="myImage" value=""
						 onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${StudentManagerForm.map.studentNo} width=134>', 'inline', this.id)" 
				 		 onMouseOut="showHelpMessage('', 'none', this.id)" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>連絡電話</td>
						<td>
						<input type="text" name="telephone" size="10" value="${StudentManagerForm.map.telephone}" />
						</td>
						</tr>
						</table>
						
						</td>
						</tr>
						</table>
						
						</td>
					
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>行動電話</td>
						<td>
						<input type="text" name="CellPhone" size="10" value="${StudentManagerForm.map.CellPhone}" />
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
							<!--td>
								<table>
									<tr>
										<td>
											<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
												<tr bgcolor="#f0fcd7">
													<td>入學照片</td>
													<td>
														<input type="file" name="myImage" size="8" id="myImage" value=""
													 		onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${StudentManagerForm.map.studentNo} width=134>', 'inline', this.id)" 
											 		 		onMouseOut="showHelpMessage('', 'none', this.id)" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
												<tr bgcolor="#f0fcd7">
													<td>連絡電話</td>
													<td>
														<input type="text" name="telephone" size="10" value="${StudentManagerForm.map.telephone}" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td-->
							<td>
								<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
									<tr bgcolor="#f0fcd7">
										<td>電子郵件</td>
										<td>
											<input type="text" name="email" size="50" value="${StudentManagerForm.map.Email}" />
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
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>家長姓名</td>
						<td>
						<input type="text" name="parent_name" size="10" value="${StudentManagerForm.map.parent_name}" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>英譯姓名</td>
						<td>
						<input type="text" name="student_ename" size="10" value="${StudentManagerForm.map.student_ename}" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>
						<select name="ExtraStatus" onChange="document.getElementById('ExtraDept').value='哪一系?'">
							<option <c:if test="${StudentManagerForm.map.ExtraStatus==''}">selected</c:if> value="">輔(雙)修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='輔修'}">selected</c:if> value="輔修">輔修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='雙修'}">selected</c:if> value="雙修">雙主修</option>
						</select><input 
						type="text" name="ExtraDept" id="ExtraDept" size="10" value="${StudentManagerForm.map.ExtraDept}" onClick="this.value=''" />
						</td>
						</tr>
						</table>
						
						</td>	
						
						<td>				
						
					</tr>
				</table>			
			
		</table>
		</td>
	</tr>	

<!-- 基本資料設定 end-->	
		
<!-- 入學資料設定 start-->	
	<tr>
		<td>	
					
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-green.png" />
    			</td>
    			<td nowrap >
    			入學資料&nbsp;
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
		
		<table width="100%">
			
			<tr>
				<td>
				
				<table>
					<tr>
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>入學年度</td>
						<td>
						<input type="text" name="entrance" value="${entrance}" size="2" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" 
						 value=""/>
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>前學程畢業年度</td>
						<td>
						<input type="text" name="gradyear" size="2" value="${gradyear}" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>入學身份</td>
						<td>
						<select name="ident">
							<c:forEach items="${ident}" var="i">
								<option <c:if test="${StudentManagerForm.map.ident==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>
							</c:forEach>
						</select>
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>組別</td>
						<td>
						<select name="divi">
							<option <c:if test="${StudentManagerForm.map.divi==''}">selected</c:if> value="">選擇組別<option>
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
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>畢業學歷</td>
						<td>
						<input type="text" name="schl_code" id="schl_code" size="2" value="${StudentManagerForm.map.schl_code}" readonly/>
						 
						<input type="text" name="schl_name" id="schl_name" value="${StudentManagerForm.map.schl_name}"
						 onkeyup="getAny(this.value, 'schl_name', 'schl_code', 'gradSchool', 'name')"
						 onClick="this.value='', document.getElementById('schl_code').value=''" />
						<img src="images/16-exc-mark.gif" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>科系</td>
						<td>
						<input type="text" name="grad_dept" size="6" value="${StudentManagerForm.map.grad_dept}" />
						</td>
						</tr>
						</table>
						
						</td>
											
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>畢/肄業</td>
						<td>
						<select name="gradu_status">
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
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
						<table cellpadding="0" cellspacing="0" border="0">
							<tr id="traStu" <c:if test="${StudentManagerForm.map.iType=='new'}">style="display:none"</c:if> >
								<td>
				
								<table>
									<tr>
										<td>
						
										<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
										<tr bgcolor="#f0fcd7">
										<td>轉學資格<input type="checkbox" onClick="document.getElementById('TOL_schl_name').value=document.getElementById('schl_name').value" /></td>
										<td>
						
										<input type="text" name="TOL_schl_name" onClick="this.value=''" size="10" value="${StudentManagerForm.map.TOL_schl_name}" 
						 				id="TOL_schl_name" onClick="this.value=''" />
						
										</td>
										</tr>
										</table>
						
										</td>
						
										<td>
						
										<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
										<tr bgcolor="#f0fcd7">
										<td>資格證號</td>
										<td>
						
										<input type="text" name="TOL_permission_no" size="12" value="${StudentManagerForm.map.TOL_permission_no}" onClick="this.value=''" />
						
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
						
						<td>
						
						<table>
							<tr id="newStu" <c:if test="${StudentManagerForm.map.iType=='tra'||StudentManagerForm.map.iType==''}">style="display:none"</c:if> >
								<td>
				
								<table>
									<tr>
										<td>
						
										<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
										<tr bgcolor="#f0fcd7">
										<td>入學文號</td>
										<td>
						
										<input type="text" name="entrno" id="entrno" value="${StudentManagerForm.map.entrno}" onClick="this.value=''" />
						
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
						
						<td>
				
						<table>
							<tr>
								<td>
						
								<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
								<tr bgcolor="#f0fcd7">
								<td>本校文號</td>
								<td>
						
								<input type="text" name="occur_docno" id="occur_docno" size="12" value="${StudentManagerForm.map.occur_docno}" onClick="this.value=''" />
						
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
<!-- 入學資料設定 end-->










<!-- 私人資料 start-->	
	<tr>
	<td>
	<table width="100%">
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
					<td width="1">
					<img src="images/16-tag-pencil.png">
					</td>
					<td width="100%" onClick="showStudentInfo()">
					點擊此處任意空白以建立備註
					</td>
				</tr>
					
				<tr id="stuInfo" style="display:none">
					<td colspan="2">
					
					<table>
						<tr>
							<td valign="top">身份備註</td>
							<td><textarea name="ident_remark" cols="75"></textarea></td>
						</tr>
					</table>
						
					</td>
				</tr>
					
			</table>
<script>
	function showStudentInfo(){
		if(document.getElementById('stuInfo').style.display=='none'){
			document.getElementById('stuInfo').style.display='inline';
		}else{
			document.getElementById('stuInfo').style.display='none';
		}
	}
</script>				
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
<!-- 私人資料 end-->	
	
	<tr>
		<td bgcolor="#cfe69f" align="center" width="100%">
		
		<table>
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Create'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Cancel'/>"
													   class="CourseButton" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>	
</c:if>
<!-- 新生轉入作業 end -->

<!-- 休退畢作業 start -->
<c:if test="${Gtype=='editStmd'}"><tr>
		<td>
		<input type="hidden" name="Oid" value="${StudentManagerForm.map.Oid}" />
		
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-blue.png" />
    			</td>
    			<td nowrap >
    			<!-- 假的現在狀態 -->
    			<input type="hidden" name="deadOccur_status" value="${StudentManagerForm.map.occur_status}" />
    			<select disabled><!-- 假的(死的)現在狀態 -->
    				<option value="">正常</option>
    				<c:forEach items="${occur_status}" var="ocs">
					<option <c:if test="${StudentManagerForm.map.occur_status==ocs.idno}">selected</c:if> value="${ocs.idno}">${ocs.name}</option>
					</c:forEach>
				</select>
				<img src="images/12-em-right.png"><!-- 假的預備狀態 -->
    			<select id="Soccur_status" 
    			onChange="changStatus(this.value)"
    			onMouseOver="showHelpMessage('請選擇欲辦理的項目', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
					<option value="">正常</option>
    				<c:forEach items="${occur_status}" var="ocs">
					<option <c:if test="${StudentManagerForm.map.occur_status==ocs.idno}">selected</c:if> value="${ocs.idno}">${ocs.name}</option>
					</c:forEach>
				</select>

				<input type="hidden" name="occur_status" value="${StudentManagerForm.map.occur_status}" /><!-- 真要變的狀態 -->
				<select name="occur_cause" id="occur_cause" style="display:none" 
				 onMouseOver="showHelpMessage('請選擇原因', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)">
					<option value="">選擇原因</option>
					<c:forEach items="${cause}" var="oc">
					<option <c:if test="${StudentManagerForm.map.occur_cause==oc.idno}">selected</c:if> value="${oc.idno}">${oc.name}</option>
					</c:forEach>
				</select>
				<input type="text" name="occur_docno" id="occur_docno" size="9" onClick="this.value=''"
				 autocomplete="off" style="ime-mode:disabled" autocomplete="off" style="display:none"
				 value="${StudentManagerForm.map.occur_docno}"
				 onMouseOver="showHelpMessage('請在這裡輸入文號', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)" />
				
				<input type="text" name="occur_graduate_no" id="occur_graduate_no" size="11" onClick="this.value=''"
				 autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="${StudentManagerForm.map.occur_graduate_no}" 
				 <c:if test="${StudentManagerForm.map.occur_status!='6'}">style="display:none"</c:if>
				 onMouseOver="showHelpMessage('請在這裡輸入畢業號', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)" />
				 
				<input type="text" name="occur_date" id="occur_date" value="${StudentManagerForm.map.occur_date}" size="4"
				 onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off" 
				 onMouseOver="showHelpMessage('預定生效日期', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)" <c:if test="${StudentManagerForm.map.occur_status!='6'}">style="display:none"</c:if> />
				
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>

<script>
	function changStatus(status){
		document.getElementById('occur_status').value=document.getElementById('Soccur_status').value;
		
		if(status=="6"){
			document.getElementById("occur_graduate_no").style.display="inline";
			document.getElementById("occur_cause").style.display="none";
			document.getElementById("occur_docno").style.display="none";
			document.getElementById("occur_date").style.display="inline";
		}else{
			document.getElementById("occur_cause").style.display="inline";
			document.getElementById("occur_docno").style.display="inline";
			document.getElementById("occur_graduate_no").style.display="none";
			document.getElementById("occur_date").style.display="inline";
		}
		
	}
</script>

		
		<td>
	</tr>
<!-- 轉學生文號設定 end-->	
	
	
<!-- 基本資料設定 start-->	
	<tr>
		<td>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-blue.png" />
    			</td>
    			<td nowrap >
    			基本資料&nbsp;
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

		<table width="100%">
			<tr>
				<td>
				
				<table>
					<tr>
						<td>
						
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">班級</td>
						<td class="hairLineTd">
						<input type="text" id="classNo" name="classNo"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 		 value="${StudentManagerForm.map.classNo}" 
				 		 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 		 onclick="this.value='', document.getElementById('className').value=''"
    					 onMouseOver="showHelpMessage('可以同時復學並轉班喔', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)"/><!-- 死的原本班級 --><input type="hidden"
				 		name="deadClassNo" value="${StudentManagerForm.map.classNo}"
				 		/><input type="text" name="className" id="className"
				 		 value="${StudentManagerForm.map.className}" size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
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
						<td class="hairLineTdF">學號</td>
						<td class="hairLineTd">	
				 		<input type="text" name="studentNo" size="10" readonly id="studentNo"
    					 onMouseOver="showHelpMessage('不可以改學號', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)" 
				 		 value="${StudentManagerForm.map.studentNo}" /><!-- 死的原本姓名 --><input type="hidden"
				 		name="deadStudentName" value="${StudentManagerForm.map.studentName}"
				 		/><input type="text" name="studentName" size="10" onClick="this.value=''"
				 		 value="${StudentManagerForm.map.studentName}" />	
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
						<input type="text" name="idno" autocomplete="off" size="8"
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
						<input type="text" name="birthday"  size="4" value="${StudentManagerForm.map.birthday}"
						 onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
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
						</select>
						<select name="birth_county">
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
						<input type="text" name="curr_post" size="1" value="${StudentManagerForm.map.curr_post}" id="curr_post" />
						<input type="text" name="curr_addr" value="${StudentManagerForm.map.curr_addr}" id="curr_addr" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">
						永久地址<input type="checkbox" id="AddrBox" onClick="copyAddr()" 
						onMouseOver="showHelpMessage('永久地址同通訊地址, 請先輸入通訊地址再點', 'inline', this.id)" 
				 		onMouseOut="showHelpMessage('', 'none', this.id)" />
						</td>
						<td class="hairLineTd">
						<input type="text" name="perm_post" value="${StudentManagerForm.map.perm_post}" id="perm_post" size="1" />
						<input type="text" name="perm_addr" value="${StudentManagerForm.map.perm_addr}" id="perm_addr" />
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
						<td class="hairLineTdF">入學照片</td>
						<td class="hairLineTd">
						<input type="file" name="myImage" size="8" id="myImage" value=""
						 onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${StudentManagerForm.map.studentNo} width=134>', 'inline', this.id)" 
				 		 onMouseOut="showHelpMessage('', 'none', this.id)" />
						</td>
						</tr>
						</table>
						
						</td>
						<td>
						
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">連絡電話</td>
						<td class="hairLineTd">
						<input type="text" name="telephone" size="10" value="${StudentManagerForm.map.telephone}" />
						</td>
						</tr>
						</table>
						
						</td>
						<td>						
						
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">行動電話</td>
						<td class="hairLineTd">
						<input type="text" name="CellPhone" size="10" value="${StudentManagerForm.map.CellPhone}" />
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
							<!--td>
								<table>
									<tr>
										<td>
											<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
												<tr bgcolor="#f0fcd7">
													<td>入學照片</td>
													<td>
														<input type="file" name="myImage" size="8" id="myImage" value=""
													 		onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${StudentManagerForm.map.studentNo} width=134>', 'inline', this.id)" 
											 		 		onMouseOut="showHelpMessage('', 'none', this.id)" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
												<tr bgcolor="#f0fcd7">
													<td>連絡電話</td>
													<td>
														<input type="text" name="telephone" size="10" value="${StudentManagerForm.map.telephone}" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td-->
							<td>
								<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
									<tr bgcolor="#f0fcd7">
										<td>電子郵件</td>
										<td>
											<input type="text" name="email" size="50" value="${StudentManagerForm.map.Email}" />
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
						<td class="hairLineTdF">家長姓名</td>
						<td class="hairLineTd">
						<input type="text" name="parent_name" size="10" value="${StudentManagerForm.map.parent_name}" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">英譯姓名</td>
						<td class="hairLineTd">
						<input type="text" name="student_ename" size="10" value="${StudentManagerForm.map.student_ename}" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">
						<select name="ExtraStatus" onChange="document.getElementById('ExtraDept').value='哪一系?'">
							<option <c:if test="${StudentManagerForm.map.ExtraStatus==''}">selected</c:if> value="">輔(雙)修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='輔修'}">selected</c:if> value="輔修">輔修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='雙修'}">selected</c:if> value="雙修">雙主修</option>
						</select>
						</td>
						<td class="hairLineTd">
						<input 
						type="text" name="ExtraDept" id="ExtraDept" size="10" value="${StudentManagerForm.map.ExtraDept}" onClick="this.value=''" />
						</td>
						</tr>
						</table>
						
						</td>	
						
						<td>				
						
					</tr>
				</table>			
			
		</table>
		</td>
	</tr>	

<!-- 基本資料設定 end-->	
		
<!-- 入學資料設定 start-->	
	<tr>
		<td>	
					
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-blue.png" />
    			</td>
    			<td nowrap >
    			入學資料&nbsp;
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
		
		<table width="100%">
			
			<tr>
				<td>
				
				<table>
					<tr>
						<td>
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">入學年度</td>
						<td class="hairLineTd">
						<input type="text" name="entrance" value="${StudentManagerForm.map.entrance}" size="2" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" 
						 value=""/>
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">前學程畢業年度</td>
						<td class="hairLineTd">
						<input type="text" name="gradyear" size="2" value="${StudentManagerForm.map.gradyear}" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" />
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
						<td class="hairLineTdF">畢業學歷</td>
						<td class="hairLineTd">
						<input type="text" name="schl_code" id="schl_code" size="2" value="${StudentManagerForm.map.schl_code}" readonly/>
						 
						<input type="text" name="schl_name" id="schl_name" value="${StudentManagerForm.map.schl_name}"
						 onkeyup="getAny(this.value, 'schl_name', 'schl_code', 'gradSchool', 'name')"
						 onClick="this.value='', document.getElementById('schl_code').value=''" />
						</td>
						<td class="hairLineTdF" align="center" width="30">
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
		</table>
		
		</td>
	</tr>
<!-- 入學資料設定 end-->
	<tr id="modifyHistory"
    			onMouseOver="showHelpMessage('這裡只用來看的, 欲產生新的狀態或號碼請用最上面有藍色箭頭的那邊', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
		<td>
<!-- 變更歷史 start -->					
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-blue.png" />
    			</td>
    			<td nowrap>
    			異動資訊&nbsp;
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
		
		<table width="100%">
			
			<tr>
				<td>
				
				<table>
					<tr>
					
						<td>
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">
						
						<select disabled><!-- 假的(死的)現在狀態 -->
    					<option value="">正常</option>
    					<c:forEach items="${occur_status}" var="ocs">
						<option <c:if test="${StudentManagerForm.map.occur_status==ocs.idno}">selected</c:if> value="${ocs.idno}">${ocs.name}</option>
						</c:forEach>
						</select>
						</td>
						<td class="hairLineTd">
						<select name="occur_cause" disabled>
						<option <c:if test="${StudentManagerForm.map.occur_cause==''}">selected</c:if> value="">原因<option>
							<c:forEach items="${cause}" var="oc">
								<option <c:if test="${StudentManagerForm.map.occur_cause==oc.idno}">selected</c:if> value="${oc.idno}">${oc.name}</option>
							</c:forEach>
						</option>
						</select>
						
						</td>
						
						</tr>
						</table>						
						</td>
						
						<td>
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">文號</td>
						<td class="hairLineTd">
						<input type="text" name="occur_status" value="${StudentManagerForm.map.occur_docno}" size="6" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="" disabled />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">畢業號</td>
						<td class="hairLineTd">
						<input type="text" name="occur_status" value="${StudentManagerForm.map.occur_graduate_no}" size="6" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="" disabled />
						</td>
						</tr>
						</table>						
						</td>					
						
					</tr>					
				
				</table>
				
				<table>
					
					
					<tr>
						
						<td>
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">異動學年(期)</td>
						<td class="hairLineTd">
						<input type="text" name="occur_year" value="${StudentManagerForm.map.occur_year}" size="1" onClick="this.value=''" id="occur_year"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off"  autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="occur_term"
						onMouseOver="showHelpMessage('點擊修正發生學年, 空白視為未修改', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
						<input type="text" name="occur_term" value="${StudentManagerForm.map.occur_term}" size="1" onClick="this.value=''" id="occur_term"
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
						<input type="text" value="${StudentManagerForm.map.occur_date}" size="6" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" disabled />
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
<!-- 變更歷史 end-->	
	
<!-- 私人資料 start-->	
	<tr>
	<td>
	<table width="100%">
		<tr>
<!-- 私人左邊 start-->
			<td width="50%" valign="top">
			
			<table width="100%">
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
					<td width="1">
					<img src="images/tag_orange.gif">
					</td>
					<td width="100%" onClick="showInfo('Sinfo')" style="cursor:pointer;">
					點擊此處任意空白以建立其它資訊
					</td>
				</tr>
					
				<tr id="Sinfo" style="display:none">
					<td colspan="2">
						
					<table>
						<tr>
							<td>身份備註</td>
							<td><select name="ident_basic" disabled>
								<option <c:if test="${StudentManagerForm.map.ident_basic==''}">selected</c:if> value="">新身份</option>
							<c:forEach items="${ident}" var="i">
								<option <c:if test="${StudentManagerForm.map.ident_basic==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>
							</c:forEach>
						</select><input type name="ident_remark" disabled></td>
						</tr>
						<tr>
							<td>電子郵件</td>
							<td><input type="text" name="Email" value="${StudentManagerForm.map.Email}" size="33" disabled /></td>
						</tr>
					</table>
						
						
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
		
			<td width="50%" valign="top">
			
			<table width="100%">
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
					<td width="1">
					<img src="images/tag_purple.gif">
					</td>
					<td width="100%" onClick="showInfo('pif')" style="cursor:pointer;">
					此處沒有功能
					</td>
				</tr>
					
				<tr id="pif" style="display:none">
					<td colspan="2">
						
					<table>
						<tr>
							<td>
							
							真的沒有功能...
														
							</td>
						</tr>
					</table>
						
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
	</table>
	</td>
	</tr>
	
	
	<tr>
		<td>
		
		
		
		
		<table width="100%">
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
					<td width="1">
					<img src="images/tag_green.gif">
					</td>
					<td width="100%" onClick="showInfo('Hinfo')" style="cursor:pointer;">
					點擊此處任意空白檢視學生歷年異動
					</td>
				</tr>
					
				<tr id="Hinfo" style="display:none">
					<td colspan="2">
						
					<table>
						<tr>
							<td>
							
							
							<c:forEach items="${myGmark}" var="myG">
							<table class="hairLineTable">
								<tr>
									<td width="30" align="center" class="hairLineTdF">
										<img src="images/tag_green.gif">
									</td>
									
									<td class="hairLineTd"><input type="hidden" name="gmark_oid" size="2" value="${myG.Oid}" />
										<input type="text" name="gmark_school_year" size="2" value="${myG.school_year}" /><select 
										name="gmark_school_term">
										<option value="1" <c:if test="${myG.school_term=='1'}">selected</c:if>>第1學期</option>
										<option value="2" <c:if test="${myG.school_term=='2'}">selected</c:if>>第2學期</option>
										</select>
									</td>
									<td class="hairLineTdF">
										
										<select name="gmark_occur_status">
										<option <c:if test="${myG.occur_status==''}">selected</c:if> value="">狀態</option>
										<c:forEach items="${occur_status}" var="i">
											<option <c:if test="${myG.occur_status==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>
										</c:forEach>
										</select>
									</td>
									<td class="hairLineTd">
										<select name="gmark_cause">
										<option <c:if test="${myG.occur_cause==''}">selected</c:if> value="">未選擇原因</option>
										<c:forEach items="${cause}" var="i">
										<option <c:if test="${myG.occur_cause==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>
										</c:forEach>
										</select>
									</td>
									<td class="hairLineTdF" width="30" align="center">備註</td>
									<td class="hairLineTd">
										<input type="text" name="gmark_remark" value="${myG.remark}" />
									</td>
									<td class="hairLineTdF" width="30" align="center"><img src="images/12-em-pencil.gif"></td>
								</tr>
							</table>
							</c:forEach>							
														
							</td>
						</tr>
						
						<tr>
							<td>
							
							<table class="hairLineTable">
								<tr>
									<td width="30" align="center" class="hairLineTdF">
										<img src="images/tag_green.gif">
									</td>
									
									<td class="hairLineTd">
										<input type="text" name="a_gmark_school_year" size="2" /><select 
										name="a_gmark_school_term">
										<option value="1">第1學期</option>
										<option value="2">第2學期</option>
										</select>
									</td>
									<td class="hairLineTdF">
										
										<select name="a_gmark_occur_status">
										<option value="">狀態</option>
										<c:forEach items="${occur_status}" var="i">
											<option value="${i.idno}">${i.name}</option>
										</c:forEach>
										</select>
									</td>
									<td class="hairLineTd">
										<select name="a_gmark_cause">
										<option value="">未選擇原因</option>
										<c:forEach items="${cause}" var="i">
										<option value="${i.idno}">${i.name}</option>
										</c:forEach>
										</select>
									</td>
									<td class="hairLineTdF" width="30" align="center">備註</td>
									<td class="hairLineTd">
										<input type="text" name="a_gmark_remark" />
									</td>
									<td class="hairLineTdF" width="30" align="center"><img src="images/12-em-pencil.gif"></td>
								</tr>
							</table>
							
							</td>
						</tr>
					</table>
						
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
	
	
	
<!-- 私人資料 end-->
	<tr height="40">
		<td align="center">

		<table width="99%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							
    							<img src="images/printer.gif" border="0">
    							<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool"
						   		 onMouseOver="showHelpMessage('校內用的表格', 'inline', this.id)"
						   		 onMouseOut="showHelpMessage('', 'none', this.id)">
    								<option value="javascript:void(0)">校內表格</option>
    								<option value="/CIS/Diploma">畢業證書</option>
    								<option value="/CIS/StudentCardForOne">學生證</option>
									<option value="/CIS/Diploma4NoGraduate">修業證書(中文)</option>
									<option value="/CIS/Diploma4NoGraduate">修業證書(英文)</option>
    							</select>
    							</td>
    							<td> 
    							<html:link page="/Course/StudentScoreHistory.do" paramId="no" paramName="NO">
    							<img src="images/vcard.png" border="0"> 歷年成績表
    							</html:link>
    							<html:link page="/Course/StudentScoreHistoryEnglish.do">
    							<img src="images/vcard.png" border="0"> 英文歷年成績表
    							</html:link>
    							<html:link page="/Course/ForeignExchangeStudentScoreHistory.do">
    							<img src="images/vcard.png" border="0"> 交換學生歷年成績表
    							</html:link>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>

		</td>

	</tr>	
	<tr>
		<td class="hairLineTable" align="center" width="100%">
		
		<table>
			<tr>
				<td>
					<INPUT type="submit" name="method" value="<bean:message key='First'/>" class="CourseButton"><INPUT 
					type="submit" name="method" value="<bean:message key='Prev' />" class="CourseButton"><INPUT 
					type="submit" name="method" value="<bean:message key='ChangeStmd'/>" class="CourseButton"><INPUT 
					type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton"><INPUT 
					type="submit" name="method" value="<bean:message key='Next'/>" class="CourseButton"><INPUT 
					type="submit" name="method" value="<bean:message key='Last'/>" class="CourseButton">
													   							   
													   							   
				</td>
			</tr>
		</table>
		
		
		
		
		
		
		
		
		
		
		
		</td>
	</tr>
</c:if>
<!-- 休退畢作業 end -->









<!-- 復學作業 start -->
<c:if test="${Gtype=='editGstmd'}"><tr>
		<td>
		<input type="hidden" name="Oid" value="${StudentManagerForm.map.Oid}" />
		
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade size="1" color="cfe69f"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-orange.png" />
    			</td>
    			<td nowrap >
    			<!-- 假的現在狀態 -->
    			<input type="hidden" name="deadOccur_status" value="${StudentManagerForm.map.occur_status}" />
    			<select disabled><!-- 假的(死的)現在狀態 -->
    				<c:forEach items="${occur_status}" var="ocs">
					<option <c:if test="${StudentManagerForm.map.occur_status==ocs.idno}">selected</c:if> value="${ocs.idno}">${ocs.name}</option>
					</c:forEach>
				</select>
				<img src="images/12-em-right.png"><!-- 假的預備狀態 -->
    			<select id="Soccur_status" <c:if test="${StudentManagerForm.map.occur_status=='6'||StudentManagerForm.map.occur_status=='2'}">disabled</c:if> 
    			onChange="changStatus(this.value)"
    			onMouseOver="showHelpMessage('請選擇欲辦理的項目', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
    				<c:forEach items="${occur_status}" var="ocs">
					<option <c:if test="${StudentManagerForm.map.occur_status==ocs.idno}">selected</c:if> value="${ocs.idno}">${ocs.name}</option>
					</c:forEach>
				</select>

				<input type="hidden" name="occur_status" value="${StudentManagerForm.map.occur_status}" /><!-- 真要變的狀態 -->
				<select name="occur_cause" id="occur_cause" style="display:none" 
				 onMouseOver="showHelpMessage('請選擇原因', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)">
					<option value="">選擇原因</option>
					<c:forEach items="${cause}" var="oc">
					<option <c:if test="${StudentManagerForm.map.occur_cause==oc.idno}">selected</c:if> value="${oc.idno}">${oc.name}</option>
					</c:forEach>
				</select>
				<input type="text" name="occur_docno" id="occur_docno" size="9" onClick="this.value=''"
				 autocomplete="off" style="ime-mode:disabled" autocomplete="off" style="display:none"
				 value="${StudentManagerForm.map.occur_docno}"
				 onMouseOver="showHelpMessage('請在這裡輸入文號', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)" />	
				
				<input type="text" name="occur_graduate_no" id="occur_graduate_no" size="11" onClick="this.value=''"
				 autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="${StudentManagerForm.map.occur_graduate_no}" style="display:none"
				 onMouseOver="showHelpMessage('請輸入畢業號', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)" style="display:none" />
				 
				<input type="text" name="occur_date" id="occur_date" value="${StudentManagerForm.map.occur_date}" size="4"
				 onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off" 
				 onMouseOver="showHelpMessage('預定生效日期', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)" style="display:none" />
				
    			</td>
    			<td width="100%" align="left">
      			<hr noshade size="1" color="cfe69f"/>
    			</td>
  			</tr>
		</table>

<script>
	function changStatus(status){
		document.getElementById('occur_status').value=document.getElementById('Soccur_status').value;
		
		if(status=="6"){
			document.getElementById("occur_graduate_no").style.display="inline";
			document.getElementById("occur_cause").style.display="none";
			document.getElementById("occur_docno").style.display="none";
			document.getElementById("occur_date").style.display="inline";
		}else{
			document.getElementById("occur_cause").style.display="inline";
			document.getElementById("occur_docno").style.display="inline";
			document.getElementById("occur_date").style.display="inline";
			document.getElementById("occur_graduate_no").style.display="none";
		}
		
	}
</script>

		
		<td>
	</tr>
<!-- 轉學生文號設定 end-->	
	
	
<!-- 基本資料設定 start-->	
	<tr>
		<td>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade size="1" color="cfe69f"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-orange.png" />
    			</td>
    			<td nowrap >
    			基本資料&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr noshade size="1" color="cfe69f"/>
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
				
				<table>
					<tr>
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>班級</td>
						<td>
						<input type="text" id="classNo" name="classNo"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 		 value="${StudentManagerForm.map.classNo}" 
				 		 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 		 onclick="this.value='', document.getElementById('className').value=''"
    					 onMouseOver="showHelpMessage('可以同時復學並轉班喔', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)"/><!-- 死的原本班級 --><input type="hidden"
				 		name="deadClassNo" value="${StudentManagerForm.map.classNo}"
				 		/><input type="text" name="className" id="className"
				 		 value="${StudentManagerForm.map.className}" size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
				 		 onclick="this.value='', document.getElementById('classNo').value=''"/>
				 		<img src="images/16-exc-mark.gif" />
				 		
				 		</td>
				 		</tr>
				 		</table>
				 		
						</td>
						<td>
				 			
				 		<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>學號</td>
						<td>	
				 		<input type="text" name="studentNo" size="10" readonly id="studentNo"
    					 onMouseOver="showHelpMessage('不可以改學號', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)" 
				 		 value="${StudentManagerForm.map.studentNo}" /><!-- 死的原本姓名 --><input type="hidden"
				 		name="deadStudentName" value="${StudentManagerForm.map.studentName}"
				 		/><input type="text" name="studentName" size="10" onClick="this.value=''"
				 		 value="${StudentManagerForm.map.studentName}" />	
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
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>身分證</td>
						<td>
						<input type="text" name="idno" autocomplete="off" size="8"
						 style="ime-mode:disabled" autocomplete="off" value="${StudentManagerForm.map.idno}" />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>性別</td>
						<td>
						<select name="sex">
							<option <c:if test="${StudentManagerForm.map.sex=='1'}">selected</c:if> value="1">男 </option>
							<option <c:if test="${StudentManagerForm.map.sex=='2'}">selected</c:if> value="2">女</option>
						</select>
						</td>
						</tr>
						</table>
						</td>
				
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>生日</td>
						<td>
						<input type="text" name="birthday"  size="4" value="${StudentManagerForm.map.birthday}"
						 onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
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
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>省籍</td>
						<td>
						<select name="birth_province">
						<option value="">國/省/縣</option>
						<c:forEach items="${birth_province}" var="b">
							<option <c:if test="${StudentManagerForm.map.birth_province==b.no}">selected</c:if> value="${b.no}">${b.name}</option>
						</c:forEach>
						</select>
						<select name="birth_county">
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
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>通訊地址</td>
						<td>
						<input type="text" name="curr_post" size="1" value="${StudentManagerForm.map.curr_post}" id="curr_post" />
						<input type="text" name="curr_addr" value="${StudentManagerForm.map.curr_addr}" id="curr_addr" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>永久地址</td>
								<td><input type="checkbox" id="AddrBox" onClick="copyAddr()" 
									 onMouseOver="showHelpMessage('永久地址同通訊地址, 請先輸入通訊地址再點', 'inline', this.id)" 
				 					 onMouseOut="showHelpMessage('', 'none', this.id)" /></td>
							</tr>
						</table>
						</td>
						<td>
						<input type="text" name="perm_post" value="${StudentManagerForm.map.perm_post}" id="perm_post" size="1" />
						<input type="text" name="perm_addr" value="${StudentManagerForm.map.perm_addr}" id="perm_addr" />
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
						
						<table>
						<tr>
						
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>入學照片</td>
						<td>
						<input type="file" name="myImage" size="8" id="myImage" value=""
						 onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${StudentManagerForm.map.studentNo} width=134>', 'inline', this.id)" 
				 		 onMouseOut="showHelpMessage('', 'none', this.id)" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>連絡電話</td>
						<td>
						<input type="text" name="telephone" size="10" value="${StudentManagerForm.map.telephone}" />
						</td>
						</tr>
						</table>
						
						</td>
						</tr>
						</table>
						
						</td>
					
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>行動電話</td>
						<td>
						<input type="text" name="CellPhone" size="10" value="${StudentManagerForm.map.CellPhone}" />
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
							<!--td>
								<table>
									<tr>
										<td>
											<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
												<tr bgcolor="#f0fcd7">
													<td>入學照片</td>
													<td>
														<input type="file" name="myImage" size="8" id="myImage" value=""
													 		onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${StudentManagerForm.map.studentNo} width=134>', 'inline', this.id)" 
											 		 		onMouseOut="showHelpMessage('', 'none', this.id)" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
												<tr bgcolor="#f0fcd7">
													<td>連絡電話</td>
													<td>
														<input type="text" name="telephone" size="10" value="${StudentManagerForm.map.telephone}" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td-->
							<td>
								<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
									<tr bgcolor="#f0fcd7">
										<td>電子郵件</td>
										<td>
											<input type="text" name="email" size="50" value="${StudentManagerForm.map.Email}" />
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
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>家長姓名</td>
						<td>
						<input type="text" name="parent_name" size="10" value="${StudentManagerForm.map.parent_name}" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>英譯姓名</td>
						<td>
						<input type="text" name="student_ename" size="10" value="${StudentManagerForm.map.student_ename}" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>
						<select name="ExtraStatus" onChange="document.getElementById('ExtraDept').value='哪一系?'">
							<option <c:if test="${StudentManagerForm.map.ExtraStatus==''}">selected</c:if> value="">輔(雙)修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='輔修'}">selected</c:if> value="輔修">輔修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='雙修'}">selected</c:if> value="雙修">雙主修</option>
						</select><input 
						type="text" name="ExtraDept" id="ExtraDept" size="10" value="${StudentManagerForm.map.ExtraDept}" onClick="this.value=''" />
						</td>
						</tr>
						</table>
						
						</td>	
						
						<td>				
						
					</tr>
				</table>			
			
		</table>
		</td>
	</tr>	

<!-- 基本資料設定 end-->	
		
<!-- 入學資料設定 start-->	
	<tr>
		<td>	
					
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade size="1" color="cfe69f"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-orange.png" />
    			</td>
    			<td nowrap >
    			入學資料&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr noshade size="1" color="cfe69f"/>
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
				
				<table>
					<tr>
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>入學年度</td>
						<td>
						<input type="text" name="entrance" value="${StudentManagerForm.map.entrance}" size="2" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" 
						 value=""/>
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>前學程畢業年度</td>
						<td>
						<input type="text" name="gradyear" size="2" value="${StudentManagerForm.map.gradyear}" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>入學身份</td>
						<td>
						<select name="ident">
							<c:forEach items="${ident}" var="i">
								<option <c:if test="${StudentManagerForm.map.ident==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>
							</c:forEach>
						</select>
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>組別</td>
						<td>
						<select name="divi">
							<option <c:if test="${StudentManagerForm.map.divi==''}">selected</c:if> value="">選擇組別<option>
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
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>畢業學歷</td>
						<td>
						<input type="text" name="schl_code" id="schl_code" size="2" value="${StudentManagerForm.map.schl_code}" readonly/>
						 
						<input type="text" name="schl_name" id="schl_name" value="${StudentManagerForm.map.schl_name}"
						 onkeyup="getAny(this.value, 'schl_name', 'schl_code', 'gradSchool', 'name')"
						 onClick="this.value='', document.getElementById('schl_code').value=''" />
						<img src="images/16-exc-mark.gif" />
						</td>
						</tr>
						</table>
						
						</td>
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>科系</td>
						<td>
						<input type="text" name="grad_dept" size="6" value="${StudentManagerForm.map.grad_dept}" />
						</td>
						</tr>
						</table>
						
						</td>
											
						
						<td>
						
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>畢/肄業</td>
						<td>
						<select name="gradu_status">
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
		</table>
		
		</td>
	</tr>
<!-- 入學資料設定 end-->
	<tr id="modifyHistory"
    			onMouseOver="showHelpMessage('這裡只用來看的, 欲產生新的狀態或號碼請用最上面有藍色箭頭的那邊', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
		<td>
<!-- 變更歷史 start -->					
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade size="1" color="cfe69f"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-orange.png" />
    			</td>
    			<td nowrap>
    			異動資訊&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr noshade size="1" color="cfe69f"/>
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
				
				<table>
					<tr>
					
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>
						
						<select disabled><!-- 假的(死的)現在狀態 -->
    					<option value="">正常</option>
    					<c:forEach items="${occur_status}" var="ocs">
						<option <c:if test="${StudentManagerForm.map.occur_status==ocs.idno}">selected</c:if> value="${ocs.idno}">${ocs.name}</option>
						</c:forEach>
						</select>
						
						<select disabled>
						<option <c:if test="${StudentManagerForm.map.occur_cause==''}">selected</c:if> value="">原因<option>
							<c:forEach items="${cause}" var="oc">
								<option <c:if test="${StudentManagerForm.map.occur_cause==oc.idno}">selected</c:if> value="${oc.idno}">${oc.name}</option>
							</c:forEach>
						</option>
						</select>
						
						</td>
						
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>文號</td>
						<td>
						<input type="text" value="${StudentManagerForm.map.occur_docno}" size="6" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" disabled />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>畢業號</td>
						<td>
						<input type="text" value="${StudentManagerForm.map.occur_graduate_no}" size="6" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" disabled />
						</td>
						</tr>
						</table>						
						</td>
						
					</tr>				
				
				</table>
				
				<table>
				
					<tr>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>學年/期</td>
						<td>
						<input type="text" name="occur_year" id="occur_year" value="${StudentManagerForm.map.occur_year}" size="1" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" onclick="ds_sh(this), this.value='';" 
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" 
				 		onMouseOver="showHelpMessage('點擊修正發生學年度, 空白視為未修改', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" />
						<input type="text" name="occur_term" value="${StudentManagerForm.map.occur_term}" size="1" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="occur_term"
						onMouseOver="showHelpMessage('點擊修正發生學期, 空白視為未修改', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
						<tr bgcolor="#f0fcd7">
						<td>異動日期</td>
						<td>
						<input type="text" value="${StudentManagerForm.map.occur_date}" size="6" onClick="this.value=''" disabled
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off" />
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
<!-- 變更歷史 end-->	
	
<!-- 私人資料 start-->	
	<tr>
	<td>
	<table width="100%">
		<tr>
<!-- 私人左邊 start-->
			<td width="50%" valign="top">
			
			<table width="100%">
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
					<td width="1">
					<img src="images/overlays.png">
					</td>
					<td width="100%" onClick="showInfo('Sinfo')">
					點擊此處任意空白以建立其它資訊
					</td>
				</tr>
					
				<tr id="Sinfo" style="display:none">
					<td colspan="2">
						
					<table>
						<tr>
							<td>身份備註</td>
							<td><select name="ident_basic">
								<option <c:if test="${StudentManagerForm.map.ident_basic==''}">selected</c:if> value="">新身份</option>
							<c:forEach items="${ident}" var="i">
								<option <c:if test="${StudentManagerForm.map.ident_basic==i.idno}">selected</c:if> value="${i.idno}">${i.name}</option>
							</c:forEach>
						</select><input type name="ident_remark"></td>
						</tr>
						<tr>
							<td>電子郵件</td>
							<td><input type="text" name="Email" value="${StudentManagerForm.map.Email}" size="33" /></td>
						</tr>
					</table>
						
						
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
		
			<td width="50%" valign="top">
			
			<table width="100%">
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
					<td width="1">
					<img src="images/group_edit.png">
					</td>
					<td width="100%" onClick="showInfo('Hinfo')">
					點擊此處任意空白檢視學生歷年異動
					</td>
				</tr>
					
				<tr id="Hinfo" style="display:none">
					<td colspan="2">
						
					<table>
						<tr>
							<td>
							
							<table>
							<c:forEach items="${myGmark}" var="myG">
								<tr>
									<td width="1"><img src="images/award_star_bronze_2.png"></td>
									<td>${myG.school_year}-${myG.school_term}</td>
									<td>&nbsp;${myG.remark}${myG.occur_cause}${myG.occur_status}&nbsp;</td>
								</tr>
							</c:forEach>
							</table>
														
							</td>
						</tr>
					</table>
						
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
	</table>
	</td>
	</tr>
<!-- 私人資料 end-->
	<tr height="40">
		<td align="center">

		<table width="99%" border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F">
  				<tr>
    				<td bgcolor="#FFFFFF">
    					<table>
    						<tr>    							
    							
    							<td>	    							
    							
    							<img src="images/printer.gif" border="0">
    							<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool"
						   		 onMouseOver="showHelpMessage('校內用的表格', 'inline', this.id)"
						   		 onMouseOut="showHelpMessage('', 'none', this.id)">
    								<option value="javascript:void(0)">校內表格</option>
									<option value="/CIS/Diploma">畢業證書</option>
									<option value="/CIS/Diploma4NoGraduate">修業證書(中文)</option>
									<option value="/CIS/Diploma4NoGraduate">修業證書(英文)</option>
									<option value="/CIS/StudentList">各班級名條</option>
    							</select>
    							<!--
								<img src="images/icon_pdf.gif" border="0">
    							<select name="reportType" onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">報部表格</option>
    								<option value="javascript:void(0)">囧</option>
    								<option value="javascript:void(0)">囧囧</option>
    								<option value="javascript:void(0)">囧囧囧</option>
    								<option value="javascript:void(0)">囧囧</option>
    								<option value="javascript:void(0)">囧</option>						
    							</select>    							
    							-->
    							</td>
    							
    							
    							<td>
    							<html:link page="/Course/StudentScoreHistory.do" paramId="no" paramName="NO">
    							<img src="images/vcard.png" border="0"> 歷年成績表
    							</html:link>
    							<html:link page="/Course/StudentScoreHistoryEnglish.do">
    							<img src="images/vcard.png" border="0"> 英文歷年成績表
    							</html:link>
    							<html:link page="/Course/ForeignExchangeStudentScoreHistory.do">
    							<img src="images/vcard.png" border="0"> 交換學生歷年成績表
    							</html:link>
    							<html:link page="/Course/StudentDiploma.do" target="_blank">
    							<img src="images/vcard.png" border="0"> 學位證明書補發
    							</html:link>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>

		</td>	
	<tr>
		<td bgcolor="#cfe69f" align="center" width="100%">
		
		<table>
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='ChangeGstmd'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Back' />" disabled
													   class="CourseButton"><INPUT type="submit"
													   							   name="method"
													   							   value="<bean:message
													   							   key='Cancel'/>"
													   							   class="CourseButton">
				</td>
			</tr>
		</table>
		
		
		
		
		
		
		
		
		
		
		
		</td>
	</tr>
</c:if>
<!-- 復學作業 end -->
	
	
	







<!-- 確定轉入 start -->
<c:if test="${Gtype=='confirmAdd'}">
	<tr>
		<td>
		
		
		<table width="100%">
			<tr>
				<td width="50%" valign="top">
				
				<table width="100%">
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
						<td width="1">
						<img src="images/24-member.png">
						</td>
						<td width="100%" onClick="showHelp()">
						建立學籍資料
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								
																
								
								姓名: ${aStudent.studentName}<br>
								學號: ${aStudent.studentNo} ${aStudent.studentName}<br>
								班級: ${aStudent.classNo} ${aStudent.className}<br>
								身分證字號: ${aStudent.idno}<br>
								性別: ${aStudent.sex}<br>
								出生日期: ${showBirthday}<br>
								省籍: ${aStudent.birth_province}, ${aStudent.birth_county}<br>
								通訊地址: ${aStudent.curr_post}, ${aStudent.curr_addr}<br>
								永久地址: ${aStudent.perm_post}, ${aStudent.perm_addr}<br>
								連絡電話: ${aStudent.telephone}<br>
								家長姓名: ${aStudent.parent_name}<br>
								入學年度: ${aStudent.entrance}<br>
								前學程畢業年度: ${aStudent.gradyear}<br>
								身份: ${aStudent.ident}<br>
								組別: ${aStudent.divi}<br>
								畢業學歷: ${aStudent.schl_code}, ${aStudent.schl_name}, ${aStudent.grad_dept}, ${aStudent.gradu_status}<br>
								年度: ${aStudent.occur_year}, 第${aStudent.occur_term}學期<br>
								日期: ${aStudent.occur_date}<br>
								狀態: ${aStudent.occur_status}<br>
								原因: 不明<br>
								畢業號: 不可能有畢業號<br>
								文號: ${aStudent.occur_docno}
								
								
								
								
								
								</td>
							</tr>
						</table>
						
						
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
				
				
				<td width="50%" valign="top">
				
				<table width="100%">
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
						<td width="1">
						<img src="images/16-image-check.png">
						</td>
						<td width="100%" onClick="showHelp()">
						轉入資格
						</td>
					</tr>	
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								<c:if test="${aStudent.iType=='tra'}">
								畢業學歷: ${aStudent.TOL_schl_name}<br>
								畢業證號: ${aStudent.TOL_permission_no}
								</c:if>
								<c:if test="${aStudent.iType=='new'}">
								入學核準號:${aStudent.entrno}
								</c:if>								
								</td>
							</tr>
						</table>
						
						
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
				
				<table width="100%">
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
						<td width="1">
						<img src="images/16-security-key.png">
						</td>
						<td width="100%">
						系統帳號
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								帳號: ${username}<br>
								密碼: ${password}
								</td>
							</tr>
						</table>
						
						
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
				
				
				
				<table width="100%">
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
						<td width="1">
						<img src="images/16-books-blue.gif">
						</td>
						<td width="100%" align="left">
						基本選課
						</td>
					</tr>	
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								<c:forEach items="${myDtime1}" var="d1">
								<input name="myDtime1" value="d1.Oid" type="checkbox" checked disabled/>${d1.cscode}${d1.chi_name}<br>
								</c:forEach>
								
								<c:forEach items="${myDtime2}" var="d2">
								<input type="checkbox" name="myDtime2" value="${d2.Oid}"/>${d2.cscode}${d2.chi_name}<br>
								</c:forEach>
								</td>
							</tr>
						</table>
						
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
			
		</table>		
		
		</td>
	</tr>	
	
	<tr>
		<td bgcolor="#cfe69f" align="center" width="100%">
		
		<table>
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='CrateNewStudent'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Back' />" disabled
													   class="CourseButton"><INPUT type="submit"
													   						 name="method"
													   						 value="<bean:message
													   						 key='Cancel'/>"
													   						 class="CourseButton">
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
</c:if>
<!-- 確定轉入 end -->


<!-- 確定復學 start-->
<c:if test="${Gtype=='confirmBack'}">
	<tr>
		<td>
		
		
		<table width="100%">
			<tr>
				<td colspan="2" width="100%">
				<table width="100%">
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
						<td width="1">
						<img src="images/allowed1.png">
						</td>
						<td width="100%">
						${aStudent.studentName}要${workType}了
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
			<tr>
				<td width="50%" valign="top">
				
				<table width="100%">
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
						<td width="1">
						<img src="images/24-member.png">
						</td>
						<td width="100%" onClick="showHelp()">
						學籍資料
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								
																
								
								姓名: ${aStudent.studentName}<br>
								學號: ${aStudent.studentNo} ${aStudent.studentName}<br>
								班級: ${aStudent.classNo} ${aStudent.className}<br>
								身分證字號: ${aStudent.idno}<br>
								性別: ${aStudent.sex}<br>
								出生日期: ${showBirthday}<br>
								省籍: ${aStudent.birth_province}, ${aStudent.birth_county}<br>
								通訊地址: ${aStudent.curr_post}, ${aStudent.curr_addr}<br>
								永久地址: ${aStudent.perm_post}, ${aStudent.perm_addr}<br>
								連絡電話: ${aStudent.telephone}<br>
								家長姓名: ${aStudent.parent_name}<br>
								入學年度: ${aStudent.entrance}<br>
								前學程畢業年度: ${aStudent.gradyear}<br>
								身份: ${aStudent.ident}<br>
								組別: ${aStudent.divi}<br>
								畢業學歷: ${aStudent.schl_code}, ${aStudent.schl_name}, ${aStudent.grad_dept}, ${aStudent.gradu_status}<br>
								年度: ${aStudent.occur_year}, 第${aStudent.occur_term}學期<br>
								日期: ${aStudent.occur_date}<br>
								狀態: ${aStudent.occur_status}<br>
								文號: ${aStudent.occur_docno}
								
								
								
								
								
								</td>
							</tr>
						</table>
						
						
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
				
				
				<td width="50%" valign="top">			
				
<c:if test="${wwpass!=null}">
				<table width="100%">
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
						<td width="1">
						<img src="images/16-security-key.png">
						</td>
						<td width="100%">
						系統帳號
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								帳號: ${wwpass.username}<br>
								密碼: ${wwpass.password}
								</td>
							</tr>
						</table>
						
						
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
</c:if>				
				


<c:if test="${myDtime1!=null && myDtime2!=null}">
				<table width="100%">
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
						<td width="1">
						<img src="images/16-books-blue.gif">
						</td>
						<td width="100%" align="left">
						基本選課
						</td>
					</tr>	
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								<c:forEach items="${myDtime1}" var="d1">
								<input name="myDtime1" value="d1.Oid" type="checkbox" checked disabled/>${d1.cscode}${d1.chi_name}<br>
								</c:forEach>
								
								<c:forEach items="${myDtime2}" var="d2">
								<input type="checkbox" name="myDtime2" value="${d2.Oid}"/>${d2.cscode}${d2.chi_name}<br>
								</c:forEach>
								</td>
							</tr>
						</table>
						
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
</c:if>				
				
				


<c:if test="${gmarkList!=null}">			
				<table width="100%">
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
						<td width="1">
						<img src="images/16-tag-pencil.png">
						</td>
						<td width="100%">
						Gmark
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								<c:forEach items="${gmarkList}" var="gm">
									${gm.schoolYear}-${gm.schoolTerm}: ${gm.occurCause}${gm.occurStatus}${gm.remark}<br>
								</c:forEach>
								
								</td>
							</tr>
						</table>
						
						
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
</c:if>
				
<c:if test="${tran!=null}">
				<table width="100%">
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
						<td width="1">
						<img src="images/group_gear.png">
						</td>
						<td width="100%">
						Tran
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								${tran.occurYear}-${tran.occurTerm}: 由${tran.oldDepartClass}轉${tran.newDepartClass}
								
								</td>
							</tr>
						</table>
						
						
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
</c:if>
				
				
				
				
				
				
<c:if test="${quitresume!=null}">
				<table width="100%">
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
						<td width="1">
						<img src="images/group_gear.png">
						</td>
						<td width="100%">
						QuitResume
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								${quitresume.occurYear}-${quitresume.occurTerm}休, ${quitresume.recovYear}-${quitresume.recovTerm}復
								
								</td>
							</tr>
						</table>
						
						
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
</c:if>
				
				</td>
			</tr>
			
		</table>
		
		
		</td>
	</tr>	
	
	<tr>
		<td bgcolor="#cfe69f" align="center" width="100%">
		
		<table>
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='ChangeGstmdConfirm'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Back' />" disabled
													   class="CourseButton"><INPUT type="submit"
													   						 name="method"
													   						 value="<bean:message
													   						 key='Cancel'/>"
													   						 class="CourseButton">
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
</c:if>
<!-- 確定復學 end-->




<!-- 確定休退畢移 start-->
<c:if test="${Gtype=='confirmGoodBye'}">
	<tr>
		<td>
		
		
		<table width="100%">
			<tr>
				<td colspan="2" width="100%">
				<table width="100%">
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
						<td width="1">
						<img src="images/allowed1.png">
						</td>
						<td width="100%">
						${aStudent.studentName}要${workType}了
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
			<tr>
				<td width="50%" valign="top">
				
				<table width="100%">
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
						<td width="1">
						<img src="images/24-member.png">
						</td>
						<td width="100%" onClick="showHelp()">
						學籍資料
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								
																
								
								姓名: ${aStudent.studentName}<br>
								學號: ${aStudent.studentNo} ${aStudent.studentName}<br>
								班級: ${aStudent.classNo} ${aStudent.className}<br>
								身分證字號: ${aStudent.idno}<br>
								性別: ${aStudent.sex}<br>
								出生日期: ${aStudent.birthday}<br>
								省籍: ${aStudent.birth_province}, ${aStudent.birth_county}<br>
								通訊地址: ${aStudent.curr_post}, ${aStudent.curr_addr}<br>
								永久地址: ${aStudent.perm_post}, ${aStudent.perm_addr}<br>
								連絡電話: ${aStudent.telephone}<br>
								家長姓名: ${aStudent.parent_name}<br>
								入學年度: ${aStudent.entrance}<br>
								前學程畢業年度: ${aStudent.gradyear}<br>
								身份: ${aStudent.ident}<br>
								組別: ${aStudent.divi}<br>
								畢業學歷: ${aStudent.schl_code}, ${aStudent.schl_name}, ${aStudent.grad_dept}, ${aStudent.gradu_status}<br>
								年度: ${aStudent.occur_year}, 第${aStudent.occur_term}學期<br>
								日期: ${aStudent.occur_date}<br>
								狀態: ${aStudent.occur_status}<br>
								文號: ${aStudent.occur_docno}
								
								
								
								
								
								</td>
							</tr>
						</table>
						
						
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
				
				
				<td width="50%" valign="top">				
				
<c:if test="${wwpass!=null}">
				<table width="100%">
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
						<td width="1">
						<img src="images/16-security-key.png">
						</td>
						<td width="100%">
						預備刪除系統帳號
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								帳號: ${wwpass.username}<br>
								密碼: ${wwpass.password}
								</td>
							</tr>
						</table>
						
						
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
</c:if>				
				


<c:if test="${myDtime1!=null}">
				<table width="100%">
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
						<td width="1">
						<img src="images/16-books-blue.gif">
						</td>
						<td width="100%" align="left">
						預備刪除選課
						</td>
					</tr>	
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								<c:forEach items="${myDtime1}" var="d1">
								<input name="myDtime1" value="d1.Oid" type="checkbox" checked disabled/>${d1.cscode}${d1.chi_name}<br>
								</c:forEach>
								</td>
							</tr>
						</table>
						
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
</c:if>				

<c:if test="${gmarkList!=null}">			
				<table width="100%">
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
						<td width="1">
						<img src="images/16-tag-pencil.png">
						</td>
						<td width="100%">
						預備建立學生記錄
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								<c:forEach items="${gmarkList}" var="gm">
									${gm.schoolYear}-${gm.schoolTerm}: ${gm.occurCause}${gm.occurStatus}${gm.remark}<br>
								</c:forEach>
								
								</td>
							</tr>
						</table>
						
						
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
</c:if>
				
<c:if test="${tran!=null}">
				<table width="100%">
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
						<td width="1">
						<img src="images/group_gear.png">
						</td>
						<td width="100%">
						預備建立轉班記錄
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								${tran.occurYear}-${tran.occurTerm}: 由${tran.oldDepartClass}轉${tran.newDepartClass}
								
								</td>
							</tr>
						</table>
						
						
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
</c:if>
				
				
				
				
				
				
<c:if test="${quitresume!=null}">
				<table width="100%">
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
						<td width="1">
						<img src="images/group_gear.png">
						</td>
						<td width="100%">
						預備建立休學記錄
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								${quitresume.occurYear}-${quitresume.occurTerm}休, ${quitresume.recovYear}-${quitresume.recovTerm}復
								
								</td>
							</tr>
						</table>
						
						
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
</c:if>
				
				</td>
			</tr>
			
		</table>
		
		
		</td>
	</tr>	
	
	<tr>
		<td class="fullColorTable" align="center" width="100%">
		
		<table>
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='ChangeStmdConfirm'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Back'/>" disabled
													   class="CourseButton"><INPUT type="submit"
													   						 name="method"
													   						 value="<bean:message
													   						 key='Cancel'/>"
													   						 class="CourseButton">
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
</c:if>
<!-- 確定休退畢移 end-->

</html:form>
</table>

<script>
	function takeTraNo(type){
		document.getElementById('traStu').style.display="none";
		document.getElementById('newStu').style.display="inline";
		if(type=='tra'){
			document.getElementById('traStu').style.display="inline";
			document.getElementById('newStu').style.display="none";
		}
	}
</script>

<script>
	function showInfo(id){
		if(document.getElementById(id).style.display=='none'){
			document.getElementById(id).style.display='inline';
		}else{
			document.getElementById(id).style.display='none';
		}
	}
</script>

<script>
	function copyAddr(){
		document.getElementById('perm_post').value=document.getElementById('curr_post').value;
		document.getElementById('perm_addr').value=document.getElementById('curr_addr').value;
	}
</script>

<script>
	function clearQuery(){
		document.getElementById("studentNo").value="";
		document.getElementById("studentName").value="";
	}							
</script>

<script>
	function showSearch(){
		if(document.getElementById('searchBar').style.display=='none'){
			document.getElementById('searchBar').style.display='inline';
			document.getElementById('onlyEdit').style.display='none';
			
			document.getElementById('exSearch').value='1'; //進階搜尋開關
			document.getElementById('SstudentNo').disabled=false; //進階搜尋學號
			document.getElementById('SstudentName').disabled=false; //進階搜尋姓名
			document.getElementById('studentNo').disabled=true; //一般搜尋學號
			document.getElementById('studentName').disabled=true; //一般搜尋姓名
		}else{
			document.getElementById('searchBar').style.display='none';
			document.getElementById('onlyEdit').style.display='inline';
			//document.getElementById('studentNo').value='';
			//document.getElementById('studentName').value='';
			
			document.getElementById('exSearch').value='';
			document.getElementById('SstudentNo').disabled=true;
			document.getElementById('SstudentName').disabled=true;
			document.getElementById('studentNo').disabled=false;
			document.getElementById('studentName').disabled=false;
		}
	}
</script>

<script>
	function jumpMenu(targ,selObj,restore){
		eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
		eval(targ+".location.target='_blank'");
		if (restore) selObj.selectedIndex=0;
}
</script>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>
<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
