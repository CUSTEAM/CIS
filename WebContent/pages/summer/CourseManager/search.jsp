<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
	<tr>
		<td>
		
		<table>
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">開課梯次</td>
						
						<td class="hairLineTd">
						
						<select name="seqno">
									<option <c:if test="${sOpenCourseForm.map.seqno=='%'}" > selected</c:if> value="">所有</option>
									<c:forEach items="${sweek}" var="sweek">
		    							<option <c:if test="${sOpenCourseForm.map.seqno==sweek.seqno}" > selected</c:if> value="${sweek.seqno}">第${sweek.seqno}梯次</option>
		    						</c:forEach>
		    						</select>
		
		    						<select name="choseType">
									  	<option value="%"
										<c:if test="${sOpenCourseForm.map.choseType==''}" > selected</c:if>>選別
										</option>
										<option value="1" <c:if test="${sOpenCourseForm.map.choseType=='1'}" > selected</c:if>>必修</option>
		    							<option value="2" <c:if test="${sOpenCourseForm.map.choseType=='2'}" > selected</c:if>>選修</option>
		    							<option value="3" <c:if test="${sOpenCourseForm.map.choseType=='3'}" > selected</c:if>>通識</option>
		    							</select>
									<select name="status">
										<option value="0" <c:if test="${sOpenCourseForm.map.status=='0'}" > selected</c:if>>開課</option>
										<option value="1" <c:if test="${sOpenCourseForm.map.status=='1'}" > selected</c:if>>退費</option>
									</select>
						</td>
						<td class="hairLineTdF" width="30" align="center">
							<img src="images/icon_component.gif" />
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF"><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>
						</td>
						<td class="hairLineTd">
						<input type="text" id="departClass" name="departClass"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						 value="${sOpenCourseForm.map.departClass}"
						 onkeyup="getAny(this.value, 'departClass', 'classLess', 'Sabbr', 'no')"
						 onclick="this.value='', document.getElementById('classLess').value=''"/><input 
						 type="text" name="classLess" id="classLess"
						value="${sOpenCourseForm.map.classLess}" size="12"
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
						<td class="hairLineTdF"><bean:message key="setCourse.label.courseNumber" bundle="COU"/>
						</td>
						<td class="hairLineTd">
						<input type="text" name="courseNumber" id="cscodeS" size="8"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						value="${sOpenCourseForm.map.courseNumber}"
						onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
						onclick="this.value='', courseName.value=''"/><input type="text" autocomplete="off"
						name="courseName" id="csnameS" size="16"
						value="${sOpenCourseForm.map.courseName}"
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
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF"><bean:message key="OpenCourse.label.teacherNumber" bundle="COU"/>
						</td>
						<td class="hairLineTd">
						<input type="text" name="teacherId" id="techidS" size="8" style="ime-mode:disabled" autocomplete="off"
						value="${sOpenCourseForm.map.teacherId}"
						onkeyup="if(this.value.length>2)getAny(this.value, 'techidS', 'technameS', 'empl', 'no')"
						onclick="this.value='', document.getElementById('technameS').value=''"
						/><input type="text"
						onkeyup="getAny(this.value, 'technameS', 'techidS', 'empl', 'name')"
						onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
						onclick="this.value='', document.getElementById('techidS').value=''"
						name="teacherName" id="technameS" size="12" value="${sOpenCourseForm.map.teacherName}"/>
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
	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Query'/>"
						   class="gSubmit">
						   
						   <INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Create'/>" 
													   class="gCancle">
													   
													  
		</td>
	</tr>