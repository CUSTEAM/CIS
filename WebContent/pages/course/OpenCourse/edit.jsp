<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>

<c:if test="${viewMode==null||viewMode!=true}" >
<script>
	generateTableBanner('<table align="left">'+
							'<tr><td align="left">&nbsp;&nbsp;<img src="images/24-book-green-edit.png"></td>'+
								'<td>課程管理 - 修改模式</td></tr></table>');
</script>
</c:if>
<c:if test="${viewMode==true}" >
<script>
	generateTableBanner('<table align="left">'+
							'<tr><td align="left">&nbsp;&nbsp;<img src="images/24-zoom-actual.png"></td>'+
								'<td>課程管理 - 檢視模式</td></tr></table>');
</script>
</c:if>


<tr>
	<td>
	<br>
	<table width="100%" cellpadding="0" cellspacing="0">
 			<tr>
 				<td width="5%" align="left">
     			<hr noshade class="myHr"/>
   			</td>
   			<td width="1%" nowrap>
     			開課基本資料
   			</td>
   			<td width="99%" align="left">
     			<hr noshade class="myHr"/>
   			</td>
 			</tr>
	</table>



<c:if test="${Stechid==''}">
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
						<td>
						&nbsp;&nbsp;<img src="images/24-book-green-error.png">
						</td>
						<td align="left" width="100%">
						注意: 未指定授課教師
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


<table class="ds_box" cellpadding="0" cellspacing="0" name="Acsname" id="Acsname" style="display: none;">
	<tr>
		<td id="AcsnameInfo" onclick="document.getElementById('Acsname').style.display='none'">

		</td>
	</tr>
</table>

<table>
	<tr>
		<td valign="top">
			<table>
				<tr>
					
					<td align="left" width="50%">					
					
					<table class="hairLineTable">
						<tr>
							<td class="hairLineTdF" nowrap><bean:message key="OpenCourse.label.className" bundle="COU"/></td>
							<td class="hairLineTd">
	
							<input type="text" id="classNo" name="classNo"
							 size="8" autocomplete="off" style="ime-mode:disabled"
								 value="${SdepartClass}" <c:if test="${viewMode==true}" >disabled</c:if>
								 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
								 onclick="this.value='', document.getElementById('className').value=''"/><input type="text" 
								 name="className" id="className"
								 value="${SclassName2}" size="12" <c:if test="${viewMode==true}" >disabled</c:if>
									 onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
									 onclick="this.value='', document.getElementById('classNo').value=''"/>
							</td>
							<td width="30" align="center" class="hairLineTdF"><img src="images/16-exc-mark.gif" /></td>
						</tr>
					</table>
					
					
				<tr>
					<td>
					
					<table class="hairLineTable">
						<tr>
							<td class="hairLineTdF">
							<bean:message key="setCourse.label.courseNumber" bundle="COU"/>: 
							</td>
							<td class="hairLineTd">							
							<input type="text" name="courseNumber" id="cscodeS" size="8"
						 	autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						 	value="${Scscode}" <c:if test="${viewMode==true}" >disabled</c:if>
						 	onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
						 	onclick="this.value='', courseName.value=''"/><input type="text" autocomplete="off"
						 	name="courseName" id="csnameS" size="16"
						 	value="${Scscode2}" <c:if test="${viewMode==true}" >disabled</c:if>
							onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
						 	onkeydown="document.getElementById('Acsname').style.display='none';"
						 	onclick="this.value='', courseNumber.value=''"/></td>
							<td width="30" align="center" class="hairLineTdF"><img src="images/16-exc-mark.gif" /></td>
						</tr>
					</table>
					
					</td>					
	
				</tr>
				<tr>
					<td>
										
					<table class="hairLineTable">
						<tr>
							<td class="hairLineTdF"><bean:message key="OpenCourse.label.teacherNumber" bundle="COU"/></td>
							<td class="hairLineTd">
							<input type="text" name="teacherId" id="techidS" size="8" style="ime-mode:disabled" autocomplete="off"
							 value="${Stechid}" <c:if test="${viewMode==true}" >disabled</c:if>
							 onkeyup="if(this.value.length>2)getAny(this.value, 'techidS', 'technameS', 'empl', 'no')"
							 onclick="this.value='', document.getElementById('technameS').value=''"/><input type="text" 
							 onkeyup="getAny(this.value, 'technameS', 'techidS', 'empl', 'name')"
							 onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
							 onclick="this.value='', document.getElementById('techidS').value=''"
							 name="teacherName" id="technameS" size="12" value="${StechName}"
							 <c:if test="${viewMode==true}" >disabled</c:if>
							 /></td>
							<td width="30" align="center" class="hairLineTdF"><img src="images/16-exc-mark.gif" /></td>
						 </tr>
					 </table>
			
					</td>
				</tr>
			</table>

			</td>
				
				
			<td valign="top">

			<table>
				<tr height="30">
					<td><bean:message key="OpenCourse.label.no" bundle="COU"/>&nbsp;
					<select name="sterm" <c:if test="${viewMode==true}">disabled</c:if>>
						<option value="1" <c:if test="${Sterm==1}" > selected</c:if>>1</option>
						<option value="2" <c:if test="${Sterm==2}" > selected </c:if>>2</option>
					</select>
						<bean:message key="OpenCourse.label.term" bundle="COU"/> </td><td>
					<select name="choseType" <c:if test="${viewMode==true}">disabled</c:if>>
					  	<option value="1" <c:if test="${Sopt==1}" > selected </c:if>>必修</option>
					  	<option value="2" <c:if test="${Sopt==2}" > selected </c:if>>選修</option>
					  	<option value="3" <c:if test="${Sopt==3}" > selected</c:if>>通識</option>
					</select>
			
					<input type="checkbox" name="noname11" onClick="getCheckBox('tl')" <c:if test="${Sopen==1}" > checked </c:if> <c:if test="${viewMode==true}">disabled</c:if>>
					<input type="hidden" name="open" id="tl" <c:if test="${Sopen==1}" > value="1" </c:if> <c:if test="${Sopen!=1}" > value="0" </c:if>>開放選修
					</td>
				</tr>
			</table>
				
				
			<c:if test="${viewMode!=true}">
			<table>
				<tr height="30">
					<td>時數: </td>
					<td align="left">
					<script>
					UpDownObj.mkUpDown("thour",1,'${Sthour}',1,50,1,true,3,"","class=counterButton")
					</script></td>
					<td><script>UpDownObj.mkUpDown("credit",1,'${Scredit}',0,50,0.5,true,3,"","class=counterButton")</script></td>
					<td align="left">學分
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
					</td>
				</tr>
				<tr height="30">
					<td valign="top">上限: </td>
					<td align="left" valign="top">
					<script>UpDownObj.mkUpDown("selectLimit",1,'${SselectLimit}',0,100,1,true,3,"","class=counterButton")</script>
					/ ${0+SstuSelect}</td>
					<td colspan="3">
					<select name="elearning" <c:if test="${viewMode==true}">disabled</c:if>>
									<option value="0" <c:if test="${Selearning==0}" > selected</c:if>>一般課程</option>
									<option value="1" <c:if test="${Selearning==1}" > selected</c:if>>遠距課程</option>
									<option value="2" <c:if test="${Selearning==2}" > selected</c:if>>輔助課程</option>
									<option value="3" <c:if test="${Selearning==3}" > selected</c:if>>多媒體</option>
								</select>
			
								<input type="checkbox" name="noname19" onClick="getCheckBox('qb')" <c:if test="${Sextrapay==1}" > checked </c:if> <c:if test="${viewMode==true}">disabled</c:if>>
					<input type="hidden" name="extrapay" id="qb" <c:if test="${Sextrapay==1}" > value="1" </c:if> <c:if test="${Sextrapay!=1}" > value="0" </c:if>>電腦實習費
					</td>
				</tr>
			</table>
			</c:if>

			<c:if test="${viewMode==true}">
			<table>
				<tr height="30">
					<td>時數: </td>
					<td align="left">
					<input type="text" name="thour" value="${Sthour}" readonly size="1">
					</td>
					<td><input type="text" name="credit" value="${Scredit}" readonly size="1">學分</td>
			
				</tr>
				<tr height="30">
					<td>上限: </td>
					<td align="left">
					<input type="text" name="selectLimit" readonly value="${SselectLimit}" size="1"> / ${0+SstuSelect}</td>
					<td><select name="elearning" <c:if test="${viewMode==true}">disabled</c:if>>
									<option value="0" <c:if test="${Selearning==0}" > selected</c:if>>一般課程</option>
									<option value="1" <c:if test="${Selearning==1}" > selected</c:if>>遠距課程</option>
									<option value="2" <c:if test="${Selearning==2}" > selected</c:if>>輔助課程</option>
									<option value="3" <c:if test="${Selearning==3}" > selected</c:if>>多媒體</option>
								</select>
			
								<input type="checkbox" name="noname19" onClick="getCheckBox('qb')" <c:if test="${Sextrapay==1}" > checked </c:if> <c:if test="${viewMode==true}">disabled</c:if>>
					<input type="hidden" name="extrapay" id="qb" <c:if test="${Sextrapay==1}" > value="1" </c:if> <c:if test="${Sextrapay!=1}" > value="0" </c:if>>電腦實習費
								</td>
					<td align="left">
					</td>
				</tr>
			</table>
			</c:if>
				
				
			</td>
		</table>


<br>
		<table width="100%">
			<tr>
				<td width="50%" align="left" valign="top">

		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="5%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="1%" nowrap>
      			上課時段 / 地點*
    			</td>
    			<td width="99%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		<c:if test="${dtimeClassCount>0}">
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
					<table>
						<tr>
							<td>
							&nbsp;&nbsp;<img src="images/24-book-green-error.png">
							</td>
							<td>
							時數不足, 尚差 <b><c:out value="${dtimeClassCount}"/></b> 節
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

	<table width="100%">
	<c:forEach items="${dtimeClasses}" var="dTime" varStatus="dTimeS">

	<tr id=numInput${dTimeS.index} style=display:inline>
			<td><select name="week" <c:if test="${viewMode==true}">disabled</c:if>>
			<option value=""></option>
						<option value="1" <c:if test="${dTime.week==1}" > selected</c:if>>星期一</option>
						<option value="2" <c:if test="${dTime.week==2}" > selected</c:if>>星期二</option>
						<option value="3" <c:if test="${dTime.week==3}" > selected</c:if>>星期三</option>
						<option value="4" <c:if test="${dTime.week==4}" > selected</c:if>>星期四</option>
						<option value="5" <c:if test="${dTime.week==5}" > selected</c:if>>星期五</option>
						<option value="6" <c:if test="${dTime.week==6}" > selected</c:if>>星期六</option>
						<option value="7" <c:if test="${dTime.week==7}" > selected</c:if>>星期日</option>
				</select><select name="begin" <c:if test="${viewMode==true}">disabled</c:if>>
				<option value=""></option>
							<option value="1" <c:if test="${dTime.begin=='1'}" > selected</c:if>>1</option>
							<option value="2" <c:if test="${dTime.begin=='2'}" > selected</c:if>>2</option>
							<option value="3" <c:if test="${dTime.begin=='3'}" > selected</c:if>>3</option>
							<option value="4" <c:if test="${dTime.begin=='4'}" > selected</c:if>>4</option>
							<option value="5" <c:if test="${dTime.begin=='5'}" > selected</c:if>>5</option>
							<option value="6" <c:if test="${dTime.begin=='6'}" > selected</c:if>>6</option>
							<option value="7" <c:if test="${dTime.begin=='7'}" > selected</c:if>>7</option>
							<option value="8" <c:if test="${dTime.begin=='8'}" > selected</c:if>>8</option>
							<option value="9" <c:if test="${dTime.begin=='9'}" > selected</c:if>>9</option>
							<option value="10" <c:if test="${dTime.begin=='10'}" > selected</c:if>>10</option>
							<option value="11" <c:if test="${dTime.begin=='11'}" > selected</c:if>>11</option>
							<option value="12" <c:if test="${dTime.begin=='12'}" > selected</c:if>>12</option>
							<option value="13" <c:if test="${dTime.begin=='13'}" > selected</c:if>>13</option>
							<option value="14" <c:if test="${dTime.begin=='14'}" > selected</c:if>>14</option>
							<option value="15" <c:if test="${dTime.begin=='15'}" > selected</c:if>>15</option>
							<option value="011" <c:if test="${dTime.begin=='011'}" > selected</c:if>>N1</option>
							<option value="012" <c:if test="${dTime.begin=='012'}" > selected</c:if>>N2</option>
							<option value="013" <c:if test="${dTime.begin=='013'}" > selected</c:if>>N3</option>
							<option value="014" <c:if test="${dTime.begin=='014'}" > selected</c:if>>N4</option>
				</select><select 
				
					name="end" <c:if test="${viewMode==true}">disabled</c:if>>
				<option value=""></option>
							<option value="1"<c:if test="${dTime.end=='1'}" > selected</c:if>>1</option>
							<option value="2"<c:if test="${dTime.end=='2'}" > selected</c:if>>2</option>
							<option value="3"<c:if test="${dTime.end=='3'}" > selected</c:if>>3</option>
							<option value="4"<c:if test="${dTime.end=='4'}" > selected</c:if>>4</option>
							<option value="5"<c:if test="${dTime.end=='5'}" > selected</c:if>>5</option>
							<option value="6"<c:if test="${dTime.end=='6'}" > selected</c:if>>6</option>
							<option value="7"<c:if test="${dTime.end=='7'}" > selected</c:if>>7</option>
							<option value="8"<c:if test="${dTime.end=='8'}" > selected</c:if>>8</option>
							<option value="9"<c:if test="${dTime.end=='9'}" > selected</c:if>>9</option>
							<option value="10"<c:if test="${dTime.end=='10'}" > selected</c:if>>10</option>
							<option value="11"<c:if test="${dTime.end=='11'}" > selected</c:if>>11</option>
							<option value="12"<c:if test="${dTime.end=='12'}" > selected</c:if>>12</option>
							<option value="13" <c:if test="${dTime.end=='13'}" > selected</c:if>>13</option>
							<option value="14" <c:if test="${dTime.end=='14'}" > selected</c:if>>14</option>
							<option value="15" <c:if test="${dTime.end=='15'}" > selected</c:if>>15</option>
							<option value="011" <c:if test="${dTime.end=='011'}" > selected</c:if>>N1</option>
							<option value="012" <c:if test="${dTime.end=='012'}" > selected</c:if>>N2</option>
							<option value="013" <c:if test="${dTime.end=='013'}" > selected</c:if>>N3</option>
							<option value="014" <c:if test="${dTime.end=='014'}" > selected</c:if>>N4</option>
				</select><input type="text" name="place" id="place${dTimeS.index}" size="5"
			 autocomplete="off" style="ime-mode:disabled" autocomplete="off"
			 value="${dTime.place}"
			 onkeyup="if(this.value.length>3)getAny(this.value, 'place${dTimeS.index}', 'name2${dTimeS.index}', 'Nabbr', 'no')"
			 onclick="this.value='', place.value=''"
			 onMouseOver="showHelpMessage('教室代碼', 'inline', this.id)" 
			 onMouseOut="showHelpMessage('', 'none', this.id)"/><input type="text" autocomplete="off"
			 name="name2" id="name2${dTimeS.index}" size="4"				
			 readonly/>
 			 
			</td>
		</tr>

	</c:forEach>

	<%
	int star=((Integer)session.getAttribute("dtimeClasSize")).intValue();
	for(int i=star; i<10; i++){
	%>

		<tr id=numInput<%=i%> style=display:none>
				<td><select name="week" id=week<%=i%>>
				<option value=""></option>
 						<option value="1">星期一</option>
 						<option value="2">星期二</option>
 						<option value="3">星期三</option>
 						<option value="4">星期四</option>
 						<option value="5">星期五</option>
 						<option value="6">星期六</option>
 						<option value="7">星期日</option>
					</select><select name="begin">
					<option value=""></option>
 							<option value="1">1</option>
 							<option value="2">2</option>
 							<option value="3">3</option>
 							<option value="4">4</option>
 							<option value="5">5</option>
 							<option value="6">6</option>
 							<option value="7">7</option>
 							<option value="8">8</option>
 							<option value="9">9</option>
 							<option value="10">10</option>
 							<option value="11">11</option>
 							<option value="12">12</option>
 							<option value="13">13</option>
 							<option value="14">14</option>
 							<option value="15">15</option>
 							<option value="011">N1</option>
 							<option value="012">N2</option>
 							<option value="013">N3</option>
 							<option value="014">N4</option>
					</select><select name="end">
					<option value=""></option>
 							<option value="1">1</option>
 							<option value="2">2</option>
 							<option value="3">3</option>
 							<option value="4">4</option>
 							<option value="5">5</option>
 							<option value="6">6</option>
 							<option value="7">7</option>
 							<option value="8">8</option>
 							<option value="9">9</option>
 							<option value="10">10</option>
 							<option value="11">11</option>
 							<option value="12">12</option>
 							<option value="13">13</option>
 							<option value="14">14</option>
 							<option value="15">15</option>
 							<option value="011">N1</option>
 							<option value="012">N2</option>
 							<option value="013">N3</option>
 							<option value="014">N4</option>
					</select><input type="text" name="place" id="place<%=i%>" size="5"
				 autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 value="${dTime.place}"
				 onkeyup="if(this.value.length>3)getAny(this.value, 'place<%=i%>', 'name2<%=i%>', 'Nabbr', 'no')"
				 onclick="this.value='', place.value=''"
				 onMouseOver="showHelpMessage('教室代碼', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)"/><input type="text" autocomplete="off"
				 name="name2" id="name2<%=i%>" size="4"				
				 readonly/>  						
					
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		<%} %>
						
						
<c:if test="${viewMode!=true}">
		
			<tr id=addMoreNum style=display:inline>
				<td align="left" width="300">
				<input value="&nbsp;&nbsp;&nbsp;增加上課時段" type="button" onClick="javascript:AddNumInput()" class="addCourseButton"/>
				</td>
			</tr>
			<tr id=delMoreNum style=display:none>
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
										<td>
										&nbsp;&nbsp;<img src="images/24-book-green-message.png">
										</td>
										<td align="left" width="100%">
										多餘的欄位請維持空白
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
</c:if>
	</table>
	
	
	</td>
	<td width="50%" align="left" valign="top">

	<table width="100%" cellpadding="0" cellspacing="0">
 			<tr>
 				<td width="5%" align="left">
     			<hr noshade class="myHr"/>
   			</td>
   			<td width="1%" nowrap>
     			&nbsp;指定一科目多教師
   			</td>
   			<td width="99%" align="left">
     			<hr noshade class="myHr"/>
   			</td>
 			</tr>
	</table>
<c:if test="${dtimeTeacherSize>0}">
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
						<td>
						&nbsp;&nbsp;<img src="images/24-book-green-message.png">
						</td>
						<td align="left" width="100%">
						這是一門由多位教師共同授課的課程.
						</td>
					</tr>
					<c:if test="${Stechid!=''}">
					<tr>
						<td>
						&nbsp;&nbsp;<img src="images/24-book-green-error.png">
						</td>
						<td align="left" width="100%">
						這門課有設定掛名教師.
						</td>
					</tr>
					</c:if>
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




	<table width="100%">

	<c:forEach items="${dtimeTeachers}" var="dTimeTeachers" varStatus="dTimeT">

	<tr id=numInput2${dTimeT.index} style=display:inline>
		<td>
			<input type="text" autocomplete="off" name="techidM" readonly
			id="techidM${dTimeT.index}" size="8" style="ime-mode:disabled"
			value="${dTimeTeachers.teachId}">
	
			<input autocomplete="off" type="text" name="techNameM" size="4" id="techNameM${dTimeT.index}"
			value="${dTimeTeachers.chiName2}" <c:if test="${viewMode==true}">readonly</c:if> 							
			onkeyup="getAny(this.value, 'techNameM${dTimeT.index}', 'techidM${dTimeT.index}', 'empl', 'name')"							
			<c:if test="${viewMode!=true}" >onclick="this.value='', document.getElementById('techidM${dTimeT.index}').value=''"</c:if>
			>


			時數:<input type="text" name="hoursM" onmousedown="mouseCounter('ti${dTimeT.index}')"
			onmouseover="window.status='輕點滑鼠左鍵時數增加0.5, 右鍵滅少0.5, 小於0將不再增減'; return true;"
			onmouseout="window.status='';" style="ime-mode:disabled"
			id="ti${dTimeT.index}"
			size="1" value="${dTimeTeachers.hours}" <c:if test="${viewMode==true}">readonly</c:if>>
			授課:<input type="checkbox" name="noname1" <c:if test="${dTimeTeachers.teach=='1'}"> checked</c:if>
			<c:if test="${viewMode==true}"> disabled</c:if> onClick="getCheckBox('t${dTimeT.index}')">
			評分:<input type="checkbox" name="noname2" <c:if test="${dTimeTeachers.fillscore=='1'}"> checked</c:if>
			<c:if test="${viewMode==true}"> disabled</c:if> onClick="getCheckBox('f${dTimeT.index}')">
			<input type="hidden" name="techM" id="t${dTimeT.index}"
			<c:if test="${dTimeTeachers.teach=='1'}"> value="1"</c:if>>
			<input type="hidden" name="fillscoreM" id="f${dTimeT.index}"
			<c:if test="${dTimeTeachers.fillscore=='1'}"> value="1"</c:if>>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
	</c:forEach>
	
	<%
	int star2=((Integer)session.getAttribute("dtimeTeacherSize")).intValue();
	for(int i=star2; i<10; i++){
	%>
	<tr id=numInput2<%=i%> style=display:none>
		<td>
		<input autocomplete="off" type="text" name="techidM" id="techidM<%=i%>" size="8" style="ime-mode:disabled"
		readonly >
		<input autocomplete="off" type="text" name="techNameM<%=i%>" id="techNameM<%=i%>" size="4" 
		
		onkeyup="getAny(this.value, 'techNameM<%=i%>', 'techidM<%=i%>', 'empl', 'name')"
		
		onclick="this.value='', document.getElementById('techidM<%=i%>').value=''">
		時數:<input type="text" onmousedown="mouseCounter('ti<%=i%>')"
		onmouseover="window.status='輕點滑鼠左鍵時數增加0.5, 右鍵滅少0.5, 小於0將不再增減'; return true;"
		onmouseout="window.status='';" style="ime-mode:disabled"
		id="ti<%=i%>" name="hoursM" size="1" value="0">
		授課:<input type="checkbox" name="noname1" onClick="getCheckBox('t<%=i%>')">
		評分:<input type="checkbox" name="noname2" onClick="getCheckBox('f<%=i%>')">
		<input type="hidden" name="techM" id="t<%=i%>">
		<input type="hidden" name="fillscoreM" id="f<%=i%>">
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<%} %>
	
	
	<c:if test="${viewMode!=true}">
	<tr id=addMoreNum2 style=display:inline>
		<td align="left">
		<input value="&nbsp;&nbsp;&nbsp;&nbsp;指定多教師授課"
		type="button" onClick="javascript:AddNumInput2()" class="addTeacherButton"/>
		</td>
	</tr>
	<tr id=delMoreNum2 style=display:none><td align="left">
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
						<td>
						&nbsp;&nbsp;<img src="images/24-book-green-message.png">
						</td>
						<td align="left" width="100%">
						多餘的欄位請維持空白, 資料不完整將視為未輸入!
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
</c:if>

</table>
				</td>
			</tr>
		</table>

		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="5%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="1%" nowrap>
      			&nbsp;考試時間 / 地點
    			</td>
    			<td width="99%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
  			<tr>
  				<td><br></td>
  			</tr>
		</table>
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr><td id="ds_calclass">
			</td></tr>
		</table>
		<table >
		<c:forEach items="${dtimeExamList}" var="dTEime" varStatus="dTEimeS">

		<tr id=numInput4${dTEimeS.index} style=display:inline>
				<td>日期:
			<INPUT type="text" size="10" name="examDate" <c:if test="${viewMode!=true}" >onclick="ds_sh(this), this.value='';"</c:if> style="cursor: text"
			 value="${dTEime.examDate2}" readonly />
			</td>
					<td>第 <select name="examTime" <c:if test="${viewMode==true}">disabled</c:if>>
					<option value="" <c:if test="${dTEime.ebegin==''||dTEime.ebegin==null}" > selected</c:if>></option>
					<option value="1" <c:if test="${dTEime.ebegin=='1'}" > selected</c:if>>1</option>
 							<option value="2" <c:if test="${dTEime.ebegin=='2'}" > selected</c:if>>2</option>
 							<option value="3" <c:if test="${dTEime.ebegin=='3'}" > selected</c:if>>3</option>
 							<option value="4" <c:if test="${dTEime.ebegin=='4'}" > selected</c:if>>4</option>
 							<option value="5" <c:if test="${dTEime.ebegin=='5'}" > selected</c:if>>5</option>
 							<option value="6" <c:if test="${dTEime.ebegin=='6'}" > selected</c:if>>6</option>
 							<option value="7" <c:if test="${dTEime.ebegin=='7'}" > selected</c:if>>7</option>
 							<option value="8" <c:if test="${dTEime.ebegin=='8'}" > selected</c:if>>8</option>
 							<option value="9" <c:if test="${dTEime.ebegin=='9'}" > selected</c:if>>9</option>
 							<option value="10" <c:if test="${dTEime.ebegin=='10'}" > selected</c:if>>10</option>
 							<option value="11" <c:if test="${dTEime.ebegin=='11'}" > selected</c:if>>11</option>
 							<option value="12" <c:if test="${dTEime.ebegin=='12'}" > selected</c:if>>12</option>
 							<option value="13" <c:if test="${dTEime.ebegin=='13'}" > selected</c:if>>13</option>
 							<option value="14" <c:if test="${dTEime.ebegin=='14'}" > selected</c:if>>14</option>
 							<option value="15" <c:if test="${dTEime.ebegin=='15'}" > selected</c:if>>15</option>
 							<option value="011" <c:if test="${dTEime.ebegin=='011'}" > selected</c:if>>N1</option>
 							<option value="012" <c:if test="${dTEime.ebegin=='012'}" > selected</c:if>>N2</option>
 							<option value="013" <c:if test="${dTEime.ebegin=='013'}" > selected</c:if>>N3</option>
 							<option value="014" <c:if test="${dTEime.ebegin=='014'}" > selected</c:if>>N4</option>
			</select>節
				</td>
				<td>
				     監考老師: <input autocomplete="off" type="text" name="examEmplId" id="examEmplId${dTEimeS.index}"
						size="10" value="${dTEime.examEmpl}" style="ime-mode:disabled" readonly value="${dTEime.examEmpl}">
						<input autocomplete="off" type="text" name="examEmplName" id="examEmplName${dTEimeS.index}" size="10"
						<c:if test="${viewMode==true}"> readonly</c:if>
						onkeyup="getAnyNo(this.value, 'examEmplId${dTEimeS.index}', this.id)"
						onclick="this.value='', document.getElementById('examEmplId${dTEimeS.index}').value=''"
						value="${dTEime.techName}">
				</td>
				<td>
				     考試地點: <input autocomplete="off" type="text" name="examPlace" size="10" value="${dTEime.place}"
			<c:if test="${viewMode==true}"> readonly</c:if> style="ime-mode:disabled" <c:if test="${viewMode!=true}" >onclick="this.value=''"</c:if>/>
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>

		</c:forEach>

		<%
		int star4=((Integer)session.getAttribute("dtimeExamListSize")).intValue();
		for(int f=star4; f<10; f++){
		%>

		<tr id=numInput4<%=f%> style=display:none>
				<td>日期: <INPUT type="text" size="10" name="examDate" onclick="ds_sh(this);" style="cursor: text"
			 value="${dTEime.examDate2}" readonly/>
			</td>
			<td>第 <select name="examTime" <c:if test="${viewMode==true}">disabled</c:if>>
					<option value=""></option>
					<option value="1">1</option>
 							<option value="2">2</option>
 							<option value="3">3</option>
 							<option value="4">4</option>
 							<option value="5">5</option>
 							<option value="6">6</option>
 							<option value="7">7</option>
 							<option value="8">8</option>>
 							<option value="9">9</option>>
 							<option value="10">10</option>>
 							<option value="11">11</option>>
 							<option value="12">12</option>>
 							<option value="13">13</option>>
 							<option value="14">14</option>>
 							<option value="15">15</option>>
 							<option value="011">N1</option>>
 							<option value="012">N2</option>>
 							<option value="013">N3</option>>
 							<option value="014">N4</option>>
			</select>節
				</td>
				<td>
				     監考老師: <input autocomplete="off" type="text" name="examEmplId" id="examEmplId<%=f%>"
						size="10" value="" style="ime-mode:disabled" readonly value="">
						<input autocomplete="off" type="text" name="examEmplName" id="examEmplName<%=f%>" size="10"
						<c:if test="${viewMode==true}"> readonly</c:if>
						onkeyup="getAnyNo(this.value, 'examEmplId<%=f%>', this.id)"
						onclick="this.value='', document.getElementById('examEmplId<%=f%>').value=''"
						value="">
				</td>
				<td>
				考試地點: <input autocomplete="off" type="text" name="examPlace" size="10"
			<c:if test=""> readonly</c:if> style="ime-mode:disabled">
				</td>
			</tr>
			<tr>
				<td>

				</td>
			</tr>
		<%} %>
			<c:if test="${viewMode!=true}">
			<tr id=addMoreNum4 style=display:inline>
				<td align="right" colspan="8">
				<input value="&nbsp;&nbsp;&nbsp;增加時間地點" type="button" onClick="javascript:AddNumInput4()" class="addCourseButton"/>
				</td>
			</tr>
			<tr id=delMoreNum4 style=display:none><td colspan="8">

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
								<td>
								&nbsp;&nbsp;<img src="images/24-book-green-message.png">
								</td>
								<td align="left" width="100%">
								多餘的欄位請維持空白
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
			</td></tr>
			</c:if>
	</table>
<br>
	<table width="100%" cellpadding="0" cellspacing="0">
 			<tr>
 				<td width="5%" align="left">
     			<hr noshade class="myHr"/>
   			</td>
   			<td width="1%" nowrap>
     			&nbsp;跨選設定
   			</td>
   			<td width="99%" align="left">
     			<hr noshade class="myHr"/>
   			</td>
 			</tr>
 			<tr>
 				<td><br></td>
 			</tr>
	</table width="100%">
	