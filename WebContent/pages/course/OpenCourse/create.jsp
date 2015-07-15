<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%><%@ include file="/pages/include/Counter.js" %>
		<script>
			generateTableBanner('<table align="left"><tr>'+
									'<td align="left">&nbsp;&nbsp;<img src="images/24-book-green-add.png"></td>'+
									'<td>開課作業 - 新增模式</td></tr></table>');
		</script>
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
		<table>
			<tr>
				<td>
					<table>
						<tr>
							<td><bean:message key="OpenCourse.label.className" bundle="COU"/>: </td><td align="left">
							<input autocomplete="off" type="text" name="classNo" id="classNo" size="12"
							 value="<c:out value="${SdepartClass}"/>" readonly>
							<input autocomplete="off" type="text" name="className" size="12" id="classname"
							value="${SclassName2}" readonly="true" readonly>
							<img src="images/16-security-lock.png"></td>
						</tr>
						<tr>
							<td><bean:message key="setCourse.label.courseNumber" bundle="COU"/>: </td><td>
							<input autocomplete="off" type="text" name="courseNumber" id="csnoE"
							size="12" value="<c:out value="${Scscode}"/>" readonly>
							<input autocomplete="off" type="text" name="courseName" size="12" id="csnameE"
							value="<c:out value="${Scscode2}"/>" readonly>
							<img src="images/16-security-lock.png"></td>
						</tr>
						<tr height="30">
							<td><bean:message key="OpenCourse.label.teacherNumber" bundle="COU"/>: </td>
							<td>
							
							
							
							<input type="text" name="teacherId" id="techidE" size="12" style="ime-mode:disabled" autocomplete="off"
							value="${Stechid}"
							onkeyup="if(this.value.length>2)getAny(this.value, 'techidE', 'technameE', 'empl', 'no')"
							onclick="this.value='', document.getElementById('technameE').value=''"
							/>

							<input type="text"							
							onkeyup="getAny(this.value, 'technameE', 'techidE', 'empl', 'name')"
							onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
							onclick="this.value='', document.getElementById('techidE').value=''"
							name="teacherName" id="technameE" size="12" value="${StechName}"/>							
							
							</td>
						</tr>
					</table>

				</td>
				<td>


				<table>
					<tr height="30">
						<td><bean:message key="OpenCourse.label.no" bundle="COU"/>&nbsp;
						<select name="sterm" disabled>
							<option value="1" <c:if test="${Sterm==1}" > selected</c:if>>1</option>
							<option value="2" <c:if test="${Sterm==2}" > selected </c:if>>2</option>
						</select><img src="images/16-security-lock.png">
							<bean:message key="OpenCourse.label.term" bundle="COU"/> </td><td>
						<select name="choseType" disabled>
						  	<option value="1" <c:if test="${Sopt==1}" > selected </c:if>>必修</option>
						  	<option value="2" <c:if test="${Sopt==2}" > selected </c:if>>選修</option>
						  	<option value="3" <c:if test="${Sopt==3}" > selected</c:if>>通識</option>
						</select><input type="checkbox" name="noname11" onClick="getCheckBox('tl')" <c:if test="${Sopen==1}" > checked </c:if> disabled>開放選修
						<img src="images/16-security-lock.png">
						<input type="hidden" name="open" id="tl" <c:if test="${Sopen==1}" > value="1" </c:if> <c:if test="${Sopen!=1}" > value="0" </c:if>></td>
					</tr>
				</table>
				<table>
					<tr height="30">
						<td>時數: </td>
						<td align="left">
						<script>
						UpDownObj.mkUpDown("thour",1,'1',1,50,1,true,3,"readonly='true'","class=counterButton")
						</script></td>
						<td><script>UpDownObj.mkUpDown("credit",1,'0',0,50,0.5,true,3,"readonly='true'","class=counterButton")</script></td>
						<td>學分
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr height="30">
						<td valign="top">上限: </td>
						<td align="left" valign="top">
						<script>UpDownObj.mkUpDown("selectLimit",1,'60',0,100,1,true,3,"readonly='true'","class=counterButton")</script>
						</td>
						<td colspan="3">
						<select name="elearning" disabled>
    						<option value="0" <c:if test="${Selearning==0}" > selected</c:if>>一般課程</option>
    						<option value="1" <c:if test="${Selearning==1}" > selected</c:if>>遠距課程</option>
    						<option value="2" <c:if test="${Selearning==2}" > selected</c:if>>輔助課程</option>
    						<option value="3" <c:if test="${Selearning==3}" > selected</c:if>>多媒體</option>
    					</select>
    					<input type="checkbox" name="noname19" onClick="getCheckBox('qb')" >電腦實習費
    					<input type="hidden" name="extrapay" id="qb" value="0">
						</td>
					</tr>
				</table>
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
      			上課時段 / 地點
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
				<table >
					<c:forEach items="${dtimeClasses}" var="dTime" varStatus="dTimeS">

					<tr id=numInput${dTimeS.index} style=display:inline>
							<td>星期 <select name="week">
							<option value=""></option>
    						<option value="1" <c:if test="${dTime.week==1}" > selected</c:if>>一</option>
    						<option value="2" <c:if test="${dTime.week==2}" > selected</c:if>>二</option>
    						<option value="3" <c:if test="${dTime.week==3}" > selected</c:if>>三</option>
    						<option value="4" <c:if test="${dTime.week==4}" > selected</c:if>>四</option>
    						<option value="5" <c:if test="${dTime.week==5}" > selected</c:if>>五</option>
    						<option value="6" <c:if test="${dTime.week==6}" > selected</c:if>>六</option>
    						<option value="7" <c:if test="${dTime.week==7}" > selected</c:if>>日</option>
  						</select> 第
							<select name="begin">
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
  						</select> ~
							<select name="end">
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
  						</select> 節 在
							<input autocomplete="off" type='text' name='place' value="${dTime.place}" size="6">
							</td>
						</tr>
						<tr>
							<td></td>
						</tr>

					</c:forEach>

					<%
					int star=((Integer)session.getAttribute("dtimeClasSize")).intValue();
					for(int i=star; i<10; i++){
					%>

					<tr id=numInput<%=i%> style=display:none>
							<td>星期 <select name="week" id=week<%=i%>>
							<option value=""></option>
    						<option value="1">一</option>
    						<option value="2">二</option>
    						<option value="3">三</option>
    						<option value="4">四</option>
    						<option value="5">五</option>
    						<option value="6">六</option>
    						<option value="7">日</option>
  						</select> 第
							<select name="begin">
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
  						</select> ~
							<select name="end">
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
  						</select> 節 在
							<input autocomplete="off" type="text" name="place" size="6">
							</td>
						</tr>
						<tr>
							<td></td>
						</tr>
					<%} %>
						<tr id=addMoreNum style=display:inline>
							<td align="right" colspan="8">
							<input value="&nbsp;&nbsp;&nbsp;增加上課時段" type="button" onClick="javascript:AddNumInput()" class="addCourseButton"/>
							</td>
						</tr>
						<tr id=delMoreNum style=display:none><td>

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

		<table width="100%">
					<%
					int star2=((Integer)session.getAttribute("dtimeTeacherSize")).intValue();
					for(int i=star2; i<10; i++){
					%>

					<tr id=numInput2<%=i%> style=display:none>
						<td>
						<input autocomplete="off" type="text" name="techidM" id="techidM<%=i%>" size="8"
						onkeyup="if(this.value.length>2)getAny(this.value, 'techidM<%=i%>', 'techNameM<%=i%>', 'empl', 'no')"
						
						>
						<input autocomplete="off" type="text" name="techNameM" id="techNameM<%=i%>" size="4"
						onkeyup="getAny(this.value, 'techNameM<%=i%>', 'techidM<%=i%>', 'empl', 'name')"
						/>
						時數:<input type="text" name="hoursM" size="1"
						onmousedown="mouseCounter('ti<%=i%>')"
						onmouseover="window.status='輕點滑鼠左鍵時數增加0.5, 右鍵滅少0.5, 小於0將不再增減'; return true;"
						onmouseout="window.status='';"
						id="ti<%=i%>" value="0"
						>
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
		<table>
		<c:forEach items="${opencsList}" var="Opcs" varStatus="opcs">
			<tr id=numInput3${opcs.index} style=display:inline>
				<td>
				<select name="cidno">
					<option value="">清除校區</option>
					<option value="*" <c:if test="${Opcs.cidno=='*'}" > selected</c:if>>所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.cidno==code5.idno}"> selected</c:if>>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="sidno">
					<option value="">清除學制</option>
					<option value="*"<c:if test="${Opcs.sidno=='*'}"> selected</c:if>>所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.sidno==code5.idno}"> selected</c:if>
						>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="didno">
					<option value="">清除科系</option>
					<option value="*" <c:if test="${Opcs.didno=='*'}"> selected</c:if>>所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.didno==code5.idno}"> selected</c:if>
						>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grade">
					<option value="">清除年級</option>
					<option value="*" <c:if test="${Opcs.grade=='*'}" > selected</c:if>>所有年級</option>
					<option value="1" <c:if test="${Opcs.grade=='1'}" > selected</c:if>>1年級</option>
					<option value="2" <c:if test="${Opcs.grade=='2'}" > selected</c:if>>2年級</option>
					<option value="3" <c:if test="${Opcs.grade=='3'}" > selected</c:if>>3年級</option>
					<option value="4" <c:if test="${Opcs.grade=='4'}" > selected</c:if>>4年級</option>
					<option value="5" <c:if test="${Opcs.grade=='5'}" > selected</c:if>>5年級</option>
				</select>
				<select name="departClass">
					<option value="">清除班級</option>
					<option value="*" <c:if test="${Opcs.classNo=='*'}" > selected</c:if>>所有班級</option>
					<option value="1" <c:if test="${Opcs.classNo=='1'}" > selected</c:if>>甲</option>
					<option value="2" <c:if test="${Opcs.classNo=='2'}" > selected</c:if>>乙</option>
					<option value="3" <c:if test="${Opcs.classNo=='3'}" > selected</c:if>>丙</option>
					<option value="4" <c:if test="${Opcs.classNo=='4'}" > selected</c:if>>丁</option>
				</select>
				</td>
			</tr>

			</c:forEach>
			<%
			int star3=((Integer)session.getAttribute("opencsListSize")).intValue();
			for(int i=star3; i<10; i++){
			%>

			<tr id=numInput3<%=i%> style=display:none>
				<td>
				<select name="cidno">
					<option value="">選擇校區</option>
					<option value="*">所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="sidno">
					<option value="">選擇學制</option>
					<option value="*">所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="didno">
					<option value="">選擇科系</option>
					<option value="*">所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grade">
					<option value="">選擇年級</option>
					<option value="*">所有年級</option>
					<option value="1">1年級</option>
					<option value="2">2年級</option>
					<option value="3">3年級</option>
					<option value="4">4年級</option>
					<option value="5">5年級</option>
				</select>
				<select name="departClass">
					<option value="">選擇班級</option>
					<option value="*">所有班級</option>
					<option value="1">甲</option>
					<option value="2">乙</option>
					<option value="3">丙</option>
					<option value="4">丁</option>
					<option value="5">戊</option>
					<option value="6">己</option>
					<option value="7">更</option>
					<option value="8">辛</option>
					<option value="9">申</option>
				</select>
				</td>
			</tr>
			<%} %>
			<tr id=addMoreNum3 style=display:inline>
				<td align="left">
				<input value="&nbsp;&nbsp;&nbsp;&nbsp;增加跨選規則"
				type="button" onClick="javascript:AddNumInput3()" class="addTeacherButton"/>
				</td>
			</tr>
			<tr id=delMoreNum3 style=display:none>
				<td align="left">
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
											多餘的欄位請維持未選狀態
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
		<script>
       		generateTableBanner('<INPUT type="submit"  disabled name="method" value="<bean:message key='Cancel'/>" class="CourseButton">'+
       		'<INPUT type="submit" name="method" value="<bean:message key='CreateRecord'/>" class="CourseButton">');
       	</script>