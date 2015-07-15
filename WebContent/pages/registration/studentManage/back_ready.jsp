<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<tr>
		<td>
		<input type="hidden" name="Oid" value="${StudentManagerForm.map.Oid}" />
		
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-orange.png" />
    			</td>
    			<td nowrap >
    			
    			<table class="hairLineTable">
    				<tr>
    					<td class="hairLineTdF" nowrap>    					
    					
    			
    			
		    			<!-- 假的現在狀態 -->
		    			<input type="hidden" name="deadOccur_status" value="${StudentManagerForm.map.occur_status}" />
		    			<select disabled><!-- 假的(死的)現在狀態 -->
		    				<option value="">正常</option>
		    				<c:forEach items="${occur_status}" var="ocs">
							<option <c:if test="${StudentManagerForm.map.occur_status==ocs.idno}">selected</c:if> value="${ocs.idno}">${ocs.name}</option>
							</c:forEach>
						</select>
						<img src="images/12-em-right.png"><!-- 假的預備狀態 -->						
						
		    			<select id="Soccur_status" onChange="changStatus(this.value)" onMouseOver="showHelpMessage('請選擇欲辦理的項目', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)">
							<option value="">正常</option>
		    				<c:forEach items="${occur_status}" var="ocs">
							<option <c:if test="${StudentManagerForm.map.occur_status==ocs.idno}">selected</c:if> value="${ocs.idno}">${ocs.name}</option>
							</c:forEach>
						</select>
						
						
<script>
	function changStatus(status){
		document.getElementById('occur_status').value=document.getElementById('Soccur_status').value;		
		document.getElementById("mainTd").style.display="inline";
		document.getElementById("occurYear").style.display="inline";
		document.getElementById("yearNterm").style.display="inline";
		if(status=="6"){//畢業顯示畢業用欄位
			document.getElementById("occur_graduate_no").style.display="inline";
			document.getElementById("occur_cause").style.display="none";
			document.getElementById("occur_docno").style.display="none";
			document.getElementById("occur_date").style.display="inline";
			document.getElementById("gradCause").style.display="inline";
			document.getElementById("leaveCause").style.display="none";			
		}else{//非畢業顯示非畢業欄位
			document.getElementById("occur_cause").style.display="inline";
			document.getElementById("occur_docno").style.display="inline";
			document.getElementById("occur_graduate_no").style.display="none";
			document.getElementById("occur_date").style.display="inline";
			document.getElementById("leaveCause").style.display="inline";
			document.getElementById("gradCause").style.display="none";
		}
		
		if(status==""){//畢業顯示畢業用欄位
			document.getElementById("occur_graduate_no").style.display="none";
			document.getElementById("occur_cause").style.display="none";
			document.getElementById("occur_docno").style.display="none";
			document.getElementById("occur_date").style.display="none";
			document.getElementById("gradCause").style.display="none";
			document.getElementById("leaveCause").style.display="none";
			document.getElementById("mainTd").style.display="none";
			document.getElementById("occurYear").style.display="none";
			document.getElementById("yearNterm").style.display="none";			
		}
		
	}
</script>
						</td>
						
						
						
						
						
						
						
						<td class="hairLineTdF" id="leaveCause" style="display:none" nowrap>
						原因
						</td>
						<td class="hairLineTdF" id="gradCause" <c:if test="${StudentManagerForm.map.occur_status!='6'||
						(StudentManagerForm.map.occur_status==''||StudentManagerForm.map.occur_status==null)}">style="display:none"</c:if> nowrap>
						畢業證號
						</td>
						
						<td id="mainTd" class="hairLineTd" <c:if test="${StudentManagerForm.map.occur_status!='6'||
						(StudentManagerForm.map.occur_status==''||StudentManagerForm.map.occur_status==null)}">style="display:none"</c:if> nowrap>
		
						<input type="hidden" name="occur_status" value="${StudentManagerForm.map.occur_status}" /><!-- 真要變的狀態 -->
						<select name="occur_cause" id="occur_cause" style="display:none" 
						 onMouseOver="showHelpMessage('請選擇原因', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)">
							<option value="">選擇原因</option>
							<c:forEach items="${cause}" var="oc">
							<option <c:if test="${StudentManagerForm.map.occur_cause==oc.idno}">selected</c:if> value="${oc.idno}">${oc.name}</option>
							</c:forEach>
						</select><input 
						type="text" name="occur_docno" id="occur_docno" size="2" onClick="this.value=''"
						 autocomplete="off" style="ime-mode:disabled" autocomplete="off" style="display:none"
						 value="${StudentManagerForm.map.occur_docno}"
						 onMouseOver="showHelpMessage('請在這裡輸入文號', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)" /><input 
						 type="text" name="occur_graduate_no" id="occur_graduate_no" size="11" onClick="this.value=''"
						 autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="${StudentManagerForm.map.occur_graduate_no}" 
						 <c:if test="${StudentManagerForm.map.occur_status!='6'}">style="display:none"</c:if>
						 onMouseOver="showHelpMessage('請在這裡輸入畢業號', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)" />						
				 		
				 		</td>
				 		
				 		<td id="occurYear" class="hairLineTdF" style="display:none" nowrap>
				 		生效學期
				 		</td>
				 		<td class="hairLineTd" id="yearNterm" style="display:none" nowrap>
				 		<input type="text" name="occur_year" value="${StudentManagerForm.map.occur_year}" size="1" onClick="this.value=''" id="occur_year"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off" autocomplete="off" style="ime-mode:disabled" autocomplete="off" id="occur_term"
						onMouseOver="showHelpMessage('變更發生學年', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/><select 
						name="occur_term" id="occur_term" onMouseOver="showHelpMessage('變更發生學期', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
							<option value=""></option>
							<option <c:if test="${StudentManagerForm.map.occur_term=='1'}">selected</c:if> value="1">1</option>
							<option <c:if test="${StudentManagerForm.map.occur_term=='2'}">selected</c:if> value="2">2</option>						
						</select><input 
						type="text" name="occur_date" id="occur_date" value="${StudentManagerForm.map.occur_date}" size="4"
						 onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off" 
						 onMouseOver="showHelpMessage('預定生效日期', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)" <c:if test="${StudentManagerForm.map.occur_status!='6'}">style="display:none"</c:if> />
				 		
				 		
				 		
						 </td>
		    		</tr>
		    	</table>
				
    			</td>
    			<td width="100%" align="left">
      			<hr class="myHr"/>
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
      			<hr class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-orange.png" />
    			</td>
    			<td nowrap >
    			基本資料&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr class="myHr"/>
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
						<td class="hairLineTdF" align="center" nowrap>班級</td>
						<td class="hairLineTd">
						<input type="text" id="classNo" name="classNo"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 		 value="${StudentManagerForm.map.classNo}" 
				 		 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 		 onclick="this.value='', document.getElementById('className').value=''"
    					 onMouseOver="showHelpMessage('可同時復學並轉班', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)"/><!-- 死的原本班級 --><input type="hidden"
				 		name="deadClassNo" value="${StudentManagerForm.map.classNo}"/><input type="text" name="className" id="className"
				 		 value="${StudentManagerForm.map.className}" size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
				 		 onclick="this.value='', document.getElementById('classNo').value=''"/>
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
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>永久地址</td>
								<td><input type="checkbox" id="AddrBox" onClick="copyAddr()" 
									 onMouseOver="showHelpMessage('永久地址同通訊地址, 請先輸入通訊地址再點', 'inline', this.id)" 
				 					 onMouseOut="showHelpMessage('', 'none', this.id)" /></td>
							</tr>
						</table>
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
							<option <c:if test="${StudentManagerForm.map.ExtraStatus==''}">selected</c:if> value="">輔系雙主修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='1'}">selected</c:if> value="1">輔修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='2'}">selected</c:if> value="2">雙主修</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='E'}">selected</c:if> value="E">取得輔修資格</option>
							<option <c:if test="${StudentManagerForm.map.ExtraStatus=='T'}">selected</c:if> value="T">取得雙主修資格</option>
						</select>
						</td>
						<td class="hairLineTd">
						
						<select name="ExtraDept">
							<option value=""></option>
							<c:forEach items="${Dept}" var="d">
							<option <c:if test="${StudentManagerForm.map.ExtraDept==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>						
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
      			<hr class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-orange.png" />
    			</td>
    			<td nowrap >
    			入學資料&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr class="myHr"/>
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
						<input type="text" name="entrance" value="${StudentManagerForm.map.entrance}" size="2" onClick="this.value=''"
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
	<tr id="modifyHistory">
		<td>
<!-- 變更歷史 start -->					
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-orange.png" />
    			</td>
    			<td nowrap>
    			異動資訊&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr class="myHr"/>
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
						
						<select disabled>
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
						
						<td class="hairLineTd">
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">文號</td>
						<td class="hairLineTd">
						<input type="text" value="${StudentManagerForm.map.occur_docno}" size="6" disabled />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">畢業號</td>
						<td class="hairLineTd">
						<input type="text" value="${StudentManagerForm.map.occur_graduate_no}" size="6" disabled />
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
						<td class="hairLineTdF">學年/期</td>
						<td class="hairLineTd">
						<input type="text" value="${StudentManagerForm.map.occur_year}" size="1" disabled /><input type="text" value="${StudentManagerForm.map.occur_term}" size="1" disabled />
						</td>
						</tr>
						</table>						
						</td>
						
						<td>
						<table class="hairLineTable">
						<tr>
						<td class="hairLineTdF">異動日期</td>
						<td class="hairLineTd">
						<input type="text" value="${StudentManagerForm.map.occur_date}" size="6" disabled />
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
	

	<tr height="40">
		<td align="center">
		
		<%@ include file="printMenu.jsp"%>

		</td>	
	<tr>
		<td class="fullColorTable" align="center">
		
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
																			   key='SingleDelete'/>"
																			   class="CourseButton" id="SingleDelete"
						   onMouseOver="showHelpMessage('檢查學生的在校資料並準備進行下一步驟,<br>'+
						   '若認定為無資料將直接刪除', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)"/><INPUT type="submit"
													   							   name="method"
													   							   value="<bean:message
													   							   key='Cancel'/>"
													   							   class="CourseButton">
				</td>
			</tr>
		</table>
		
		</td>
	</tr>