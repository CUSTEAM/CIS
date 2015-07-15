<%@ page language="java" contentType="text/html;charset=UTF-8"
	%>
<%@ include file="/taglibs.jsp"%>
<tr>
	<td>

		<table>
			<tr>	
				<td>
				<table class="hairlineTable">
					<tr>
						<td class="hairlineTdF">課程編號</td>
						<td class="hairlineTdF"><input type="text" onClick="this.value=''" name="Oid" value="${OpenCourseForm.map.Oid}" /></td>
					</tr>
				</table>
				
				</td>
			</tr>
			<tr>
				<td>

					<table class="hairlineTable">
						<tr>
							<td class="hairlineTdF">
								開課學期
							</td>
							<td class="hairlineTd">
								<div name="selectBox" type="border" id="select_sterm">
									<select name="sterm" id="sterm" onFocus="chInput(this.id)">
										<option value="%"
											<c:if test="${OpenCourseForm.map.sterm==''}" > selected</c:if>>
											全部
										</option>
										<option value="1"
											<c:if test="${OpenCourseForm.map.sterm=='1'}" > selected</c:if>>
											上學期
										</option>
										<option value="2"
											<c:if test="${OpenCourseForm.map.sterm=='2'}" > selected</c:if>>
											下學期
										</option>
									</select>
								</div>
							</td>
							<td class="hairlineTdF">
								<div name="selectBox" type="border" id="select_choseType">
									<select name="choseType" id="choseType"
										onFocus="chInput(this.id)">
										<option value="%"
											<c:if test="${OpenCourseForm.map.choseType==''}" > selected</c:if>>
											選別
										</option>
										<option value="1"
											<c:if test="${OpenCourseForm.map.choseType=='1'}" > selected</c:if>>
											必修
										</option>
										<option value="2"
											<c:if test="${OpenCourseForm.map.choseType=='2'}" > selected</c:if>>
											選修
										</option>
										<option value="3"
											<c:if test="${OpenCourseForm.map.choseType=='3'}" > selected</c:if>>
											通識
										</option>
									</select>
								</div>
							</td>
							<td class="hairlineTd">
								<div name="selectBox" type="border" id="select_open">
									<select name="open" id="open" onFocus="chInput(this.id)">
										<option value="%"
											<c:if test="${OpenCourseForm.map.open=='%'}" > selected</c:if>>
											開放規則
										</option>
										<option value="1"
											<c:if test="${OpenCourseForm.map.open=='1'}" > selected</c:if>>
											開放選修
										</option>
										<option value="0"
											<c:if test="${OpenCourseForm.map.open=='0'}" > selected</c:if>>
											非開放選修
										</option>
									</select>
								</div>
							</td>

							<td class="hairlineTdF">
								<div name="selectBox" type="border" id="select_elearning">
									<select name="elearning" id="elearning"
										onFocus="chInput(this.id)"
										onMouseOver="showHelpMessage('遠距課程嗎？', 'inline', this.id)"
										onMouseOut="showHelpMessage('', 'none', this.id)">
										<option value="%"
											<c:if test="${OpenCourseForm.map.elearning=='%'}" > selected</c:if>>
											授課形態
										</option>
										<option value="0"
											<c:if test="${OpenCourseForm.map.elearning=='0'}" > selected</c:if>>
											一般課程
										</option>
										<option value="1"
											<c:if test="${OpenCourseForm.map.elearning=='1'}" > selected</c:if>>
											遠距課程
										</option>
										<option value="2"
											<c:if test="${OpenCourseForm.map.elearning=='2'}" > selected</c:if>>
											輔助課程
										</option>
										<option value="3"
											<c:if test="${OpenCourseForm.map.elearning=='3'}" > selected</c:if>>
											多媒體
										</option>
									</select>
								</div>
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td>

					<table class="hairlineTable">
						<tr>
							<td class="hairlineTdF">
								<bean:message key="OpenCourse.label.classNumber" bundle="COU" />
							</td>
							<td class="hairlineTd">
								<c:set var="campusSel"
									value="${OpenCourseForm.map.campusInCharge2}" />
								<c:set var="schoolSel"
									value="${OpenCourseForm.map.schoolInCharge2}" />
								<c:set var="deptSel" value="${OpenCourseForm.map.deptInCharge2}" />
								<c:set var="classSel"
									value="${OpenCourseForm.map.classInCharge2}" />
								<%@include file="/pages/include/ClassSelect4.jsp"%>
							</td>
							</td>
							<td width="30" align="center" class="hairlineTdF">
								<img src="images/icon_component.gif" />
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td>

					<table class="hairlineTable">
						<tr>
							<td class="hairlineTdF">
								<bean:message key="setCourse.label.courseNumber" bundle="COU" />
							</td>
							<td class="hairlineTd">
								<input type="text" name="courseNumber" id="cscodeS" size="8"
									autocomplete="off" style="ime-mode:disabled" autocomplete="off"
									value="${OpenCourseForm.map.courseNumber}"
									onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
									onclick="this.value='', courseName.value=''"
									onFocus="chInput(this.id)"
									onMouseOver="showHelpMessage('請輸入科目代碼, 如: 50000', 'inline', this.id)"
									onMouseOut="showHelpMessage('', 'none', this.id)" />
								<input type="text" autocomplete="off" name="courseName"
									id="csnameS" size="16" value="${OpenCourseForm.map.courseName}"
									onFocus="chInput(this.id)"
									onMouseOver="showHelpMessage('請輸入課程名稱, 如: 國文, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)"
									onMouseOut="showHelpMessage('', 'none', this.id)"
									onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
									onclick="this.value='', courseNumber.value=''" />
							</td>
							<td width="30" align="center" class="hairlineTdF">
								<img src="images/16-exc-mark.gif" />
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td>

					<table class="hairlineTable">
						<tr>
							<td class="hairlineTdF">
								<bean:message key="OpenCourse.label.teacherNumber" bundle="COU" />
							</td>
							<td class="hairlineTd">
								<input type="text" name="teacherId" id="techidS" size="8"
									style="ime-mode:disabled" autocomplete="off"
									value="${OpenCourseForm.map.teacherId}"
									onMouseOver="showHelpMessage('請輸入教師代碼', 'inline', this.id)"
									onMouseOut="showHelpMessage('', 'none', this.id)"
									onFocus="chInput(this.id)"
									onkeyup="if(this.value.length>2)getAny(this.value, 'techidS', 'technameS', 'empl', 'no')"
									onclick="this.value='', document.getElementById('technameS').value=''" />
								<input type="text"
									onkeyup="getAny(this.value, 'technameS', 'techidS', 'empl', 'name')"
									autocomplete="off"
									onclick="this.value='', document.getElementById('techidS').value=''"
									name="teacherName"
									onMouseOver="showHelpMessage('請輸入教師姓名, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)"
									onMouseOut="showHelpMessage('', 'none', this.id)"
									onFocus="chInput(this.id)" id="technameS" size="12"
									value="${OpenCourseForm.map.teacherName}" />
							</td>
							<td width="30" align="center" class="hairlineTdF">
								<img src="images/16-exc-mark.gif" />
							</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>

	</td>
</tr>

<tr height="30">
	<td class="fullColorTable" align="center">

		<INPUT type="submit" name="method" id="searchButtom"
			onMouseOver="showHelpMessage('查詢是根據以上欄位所輸入的資料進行搜尋, <br>'+ 
				   '同時會即時統計人數, 因此若是範圍過大可能會延遲', 'inline', this.id)"
			onMouseOut="showHelpMessage('', 'none', this.id)"
			value="<bean:message key='Query'/>" class="gSubmit" />

		<INPUT type="submit" name="method" id="createButtom"
			onMouseOver="showHelpMessage('新增是根據以上欄位所輸入的資料進行偵錯, <br>'+ 
					'如果有基本的邏輯錯誤會無法進行下一步', 'inline', this.id)"
			onMouseOut="showHelpMessage('', 'none', this.id)"
			value="<bean:message
					key='Create'/>" class="gGreen" disabled>

		<INPUT type="submit" name="method" id="resetButtom"
			onMouseOver="showHelpMessage('清除所有欄位', 'inline', this.id)"
			onMouseOut="showHelpMessage('', 'none', this.id)"
			value="<bean:message key='Clear'/>" class="gCancel" />

		<INPUT type="checkbox" id="readonlyBox"
			onclick="readonly(document.getElementById('readOnlyType').value)"
			onMouseOver="showHelpMessage('全域搜尋提供無管理權限的班級課程檢視, <br>以班級代碼查詢，例:<br>台北校區:1, 台北日四技:164<br>台北日四技資1甲:164D11<br>所有2年級課程____2', 'inline', this.id)"
			onMouseOut="showHelpMessage('', 'none', this.id)">
		全域搜尋

		<INPUT type="hidden" name="readOnlyType" id="readOnlyType"
			value="false" />

		<script>
					function readonly(type){
						if(type=="true"){
							document.getElementById('readOnlyType').value="false"
							document.getElementById('campusInCharge2').style.display="inline";
							document.getElementById('schoolInCharge2').style.display="inline";
							document.getElementById('deptInCharge2').style.display="inline";
							document.getElementById('classInCharge2').style.display="inline";

							document.getElementById('classLess').value="";
							}
						if(type=="false"){
							document.getElementById('readOnlyType').value="true"
							document.getElementById('campusInCharge2').style.display="none";
							document.getElementById('schoolInCharge2').style.display="none";
							document.getElementById('deptInCharge2').style.display="none";
							document.getElementById('classInCharge2').style.display="none";

							document.getElementById('classLess').value="";
							}
					}
					</script>
	</td>
</tr>
