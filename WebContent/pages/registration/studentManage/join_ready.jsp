<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
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
						
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">班級</td>
						<td class="hairLineTd">
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
				 			
				 		<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">學號</td>
						<td class="hairLineTd">	
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
						<td class="hairLineTdF">省籍</td>
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
						永久地址
				 		</td>
						<td class="hairLineTdF" width="30" align="center">
						<input type="checkbox" id="AddrBox" onClick="copyAddr()" 
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
								<table class="hairLineTable">
									<tr>
										<td class="hairLineTdF">電子郵件</td>
										<td class="hairLineTd">
											<input type="text" name="Email" size="50" value="${StudentManagerForm.map.Email}" />
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
						
						<select name="ExtraStatus">
							<option value="">輔系雙主修</option>
							<option value="1">輔修</option>
							<option value="2">雙主修</option>
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
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">入學年月</td>
						<td class="hairLineTd">
						<input type="text" name="entrance" value="${entrance}" size="2" onClick="this.value=''"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="entrance"
						onMouseOver="showHelpMessage('4位數字，例: 9408 ', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
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
						<input type="text" name="gradyear" size="2" value="${gradyear}" onClick="this.value=''"
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
						
										<table class="hairLineTable">
										<tr>
										<td class="hairLineTdF">轉學資格<input type="checkbox" id="cright"
										onClick="document.getElementById('TOL_schl_name').value=document.getElementById('schl_name').value" 
										onMouseOver="showHelpMessage('轉學資格同畢業學歷', 'inline', this.id)" 
	 					 				onMouseOut="showHelpMessage('', 'none', this.id)"
										/></td>
										<td class="hairLineTd">
						
										<input type="text" name="TOL_schl_name" onClick="this.value=''" size="10" value="${StudentManagerForm.map.TOL_schl_name}" 
						 				id="TOL_schl_name" onClick="this.value=''" />
						
										</td>
										</tr>
										</table>
						
										</td>
						
										<td>
						
										<table class="hairLineTable">
										<tr>
										<td class="hairLineTdF">資格證號</td>
										<td class="hairLineTd">
						
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
						
										<table class="hairLineTable">
										<tr>
										<td class="hairLineTdF">入學文號</td>
										<td class="hairLineTd">
						
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
						
								<table class="hairLineTable">
								<tr>
								<td class="hairLineTdF">本校文號</td>
								<td class="hairLineTd">
						
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
	
<!-- 私人資料 end-->	
	
	<tr>
		<td class="fullColorTable" align="center" width="100%">
		
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
<!-- 新生轉入作業 end -->