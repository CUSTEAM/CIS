<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/OpenCourseBatch" method="post" onsubmit="init('查詢進行中, 請稍後')">
<!--welcomeMode Start-->
	    <script>
			generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/24-book-green-open.png"></td>'+
										'<td>課程管理</td>'+
									'</tr>'+
								'</table>');
		</script>
	<tr>
		<td>
		<table class="empty-border">
			<tr>
				<td>開課學期: 第<select name="sterm">
							<option value="%" <c:if test="${OpenCourseBatchForm.map.sterm==''}" > selected</c:if>>全部</option>
    						<option value="1" <c:if test="${OpenCourseBatchForm.map.sterm=='1'}" > selected</c:if>>1</option>
    						<option value="2" <c:if test="${OpenCourseBatchForm.map.sterm=='2'}" > selected</c:if>>2</option>
    				</select>學期
    							<select name="choseType">
										<option value="%"
											<c:if test="${OpenCourseBatchForm.map.choseType==''}" > selected</c:if>>
											選別
										</option>
										<option value="1" <c:if test="${OpenCourseBatchForm.map.choseType=='1'}" > selected</c:if>>必修</option>
    							<option value="2" <c:if test="${OpenCourseBatchForm.map.choseType=='2'}" > selected</c:if>>選修</option>
    							<option value="3" <c:if test="${OpenCourseBatchForm.map.choseType=='3'}" > selected</c:if>>通識</option>
    							</select>

    							<select name="open">
    								<option value="%" <c:if test="${OpenCourseBatchForm.map.open=='%'}" > selected</c:if>>開放規則</option>
    								<option value="1" <c:if test="${OpenCourseBatchForm.map.open=='1'}" > selected</c:if>>開放選修</option>
    								<option value="0" <c:if test="${OpenCourseBatchForm.map.open=='0'}" > selected</c:if>>非開放選修</option>

    							</select>


			</tr>
			<tr>
				<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   <c:set var="campusSel" value="${OpenCourseBatchForm.map.campusInCharge2}"/>
	  			   <c:set var="schoolSel" value="${OpenCourseBatchForm.map.schoolInCharge2}"/>
	  			   <c:set var="deptSel"   value="${OpenCourseBatchForm.map.deptInCharge2}"/>
	  			   <c:set var="classSel"  value="${OpenCourseBatchForm.map.classInCharge2}"/>
	  			   <%@include file="/pages/include/ClassSelect7.jsp"%>

	  			   <select name="elearning">
	  			   			<option value="%" <c:if test="${OpenCourseBatchForm.map.elearning=='%'}" > selected</c:if>>授課形態</option>
  							<option value="0" <c:if test="${OpenCourseBatchForm.map.elearning=='0'}" > selected</c:if>>一般課程</option>
  							<option value="1" <c:if test="${OpenCourseBatchForm.map.elearning=='1'}" > selected</c:if>>遠距課程</option>
  							<option value="2" <c:if test="${OpenCourseBatchForm.map.elearning=='2'}" > selected</c:if>>輔助課程</option>

  					</select>
				</td>
			</tr>
			<tr>
				<td><bean:message key="setCourse.label.courseNumber" bundle="COU"/>:

				<input type="text" name="courseNumber" id="cscodeS" size="8"  autocomplete="off"
				value="${OpenCourseBatchForm.map.courseNumber}" onkeyup="getCourseNo(this.value)"/>




				<input type="text" autocomplete="off"
				name="courseName" id="csnameS" size="16" value="${OpenCourseBatchForm.map.courseName}" onkeyup="getCourseName(this.value, this.name)"
				onkeydown="document.getElementById('Acsname').style.display='none';" onclick="this.value='', courseNumber.value=''"/><img src="images/16-exc-mark.gif" />


				</td>
				<td>

				</td>
			</tr>
			<tr>
				<td><bean:message key="OpenCourse.label.teacherNumber" bundle="COU"/>:
				<input type="text" name="teacherId" id="techidS" size="8" style="ime-mode:disabled" autocomplete="off"
				value="${OpenCourseBatchForm.map.teacherId}"  readonly/>


				<input type="text" onkeyup="getTeacherName(this.value)"
				onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
				onclick="this.value='', document.getElementById('techidS').value=''"
				name="teacherName" id="technameS" size="12" value="${OpenCourseBatchForm.map.teacherName}"/><img src="images/16-exc-mark.gif" />
				</td>
				<td>

				</td>
			</tr>
		</table>
		</td>
	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query'/>" disabled class="CourseButton">'+
	'<INPUT type="submit" name="method" value="<bean:message key='Clear'/>" class="CourseButton">');
	</script>
	</html:form>

<!--查詢結果出爐-->

	<c:if test="${dtimeBatchList!=null}">
	<tr>
		<td>
<!--什麼也沒查到-->
			<c:if test="${empty dtimeBatchList}" >
			<table width="100%" align="center">
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
											&nbsp;&nbsp;<img src="images/24-book-green-error.png"/> 什麼也沒查到!
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
<!--什麼也沒查到-->
			<form action="/CIS/Course/OpenCourseBatchServlet.do" method="post" onsubmit="init('課程批次重建進行中, 請稍後')">


<!--根據查到的結果排列出大象-->
			<c:forEach items="${dtimeBatchList}" var="dtl">
			<table width="100%" align="center" id="opencsTable">
						<tr>
							<td>
								<div class="modulecontainer filled nomessages">
								<div class="first">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								<div>
								<div>

<!--漂亮的框框-->
									<table width="100%">
										<tr>
<!--按兩下看看以後要幹麻-->
											<td id="${dtl.oid}id" ondblclick="showInfo(this.id)">


<!--課程大大大標題-->
											<table width="100%">
											<tr>
												<td>
												<input type="hidden" name="checkBasic${dtl.oid}" id="checkBasic${dtl.oid}" value="0" size="1"/>
												<input type="hidden" name="checkTecher${dtl.oid}" id="checkTecher${dtl.oid}" value="0" size="1"/>
												<input type="hidden" name="checkClass${dtl.oid}" id="checkClass${dtl.oid}" value="0" size="1"/>
												<input type="hidden" name="checkExam${dtl.oid}" id="checkExam${dtl.oid}" value="0" size="1"/>
												<input type="hidden" name="checkCross${dtl.oid}" id="checkCross${dtl.oid}" value="0" size="1"/>

												<table width="100%">
													<tr>
														<td width="1">
														<c:if test="${dtl.opt=='0'}" >
														<img src="images/24-settings-orange.png" title="???"
														onclick="showEditCross('bs${dtl.oid}id'), showEditCross('cs${dtl.oid}id'), showEditCross('ex${dtl.oid}id'),
																 showEditCross('dt${dtl.oid}id'), showEditCross('te${dtl.oid}id'),
																 setAllCheck('checkBasic${dtl.oid}', 'checkTecher${dtl.oid}', 'checkClass${dtl.oid}', 'checkExam${dtl.oid}', 'checkCross${dtl.oid}')">
														</c:if>
														<c:if test="${dtl.opt=='1'}">
														<img src="images/24-settings.png" title="必修"
														onclick="showEditCross('bs${dtl.oid}id'),showEditCross('cs${dtl.oid}id'), showEditCross('ex${dtl.oid}id'),
																 showEditCross('dt${dtl.oid}id'), showEditCross('te${dtl.oid}id'),
																 setAllCheck('checkBasic${dtl.oid}', 'checkTecher${dtl.oid}', 'checkClass${dtl.oid}', 'checkExam${dtl.oid}', 'checkCross${dtl.oid}')">
														</c:if>
														<c:if test="${dtl.opt=='2'}" >
														<img src="images/24-settings-blue.png" title="選修"
														onclick="showEditCross('bs${dtl.oid}id'),showEditCross('cs${dtl.oid}id'), showEditCross('ex${dtl.oid}id'),
																 showEditCross('dt${dtl.oid}id'), showEditCross('te${dtl.oid}id'),
																 setAllCheck('checkBasic${dtl.oid}', 'checkTecher${dtl.oid}', 'checkClass${dtl.oid}', 'checkExam${dtl.oid}', 'checkCross${dtl.oid}')">
														</c:if>
														<c:if test="${dtl.opt=='3'}" >
														<img src="images/24-settings-silver.png" title="通識"
														onclick="showEditCross('bs${dtl.oid}id'),showEditCross('cs${dtl.oid}id'), showEditCross('ex${dtl.oid}id'),
																 showEditCross('dt${dtl.oid}id'), showEditCross('te${dtl.oid}'), setAllCheck('checkBasic${dtl.oid}', 'checkTecher${dtl.oid}', 'checkClass${dtl.oid}', 'checkExam${dtl.oid}', 'checkCross${dtl.oid}')">
														</c:if>
														</td>
														<td align="left">
														<b>${dtl.departClass2}&nbsp;&nbsp;${dtl.cscode}&nbsp;${dtl.chiName2}&nbsp;</b>
														</td>
														<td align="right">
														<INPUT type="button" value="基本&nbsp;&nbsp;" onclick="showEditCross('bs${dtl.oid}id'), setOneCheck('checkBasic${dtl.oid}')"
														class="myButton" style="text-align:right"/><INPUT type="button" value="教師&nbsp;&nbsp;" onclick="showEditCross('te${dtl.oid}id'), setOneCheck('checkTecher${dtl.oid}')"
														class="myButton" style="text-align:right"/><INPUT type="button" value="排課&nbsp;&nbsp;" onclick="showEditCross('dt${dtl.oid}id'), setOneCheck('checkClass${dtl.oid}')"
														class="myButton" style="text-align:right"/><INPUT type="button" value="排考&nbsp;&nbsp;" onclick="showEditCross('ex${dtl.oid}id'), setOneCheck('checkExam${dtl.oid}')"
														class="myButton" style="text-align:right"/><INPUT type="button" value="跨選&nbsp;&nbsp;" onclick="showEditCross('cs${dtl.oid}id'), setOneCheck('checkCross${dtl.oid}')"
														class="myButton" style="text-align:right"/>
														</td>
													</tr>
												</table>


												</td>
												<td align="right">
												</td>
											</tr>
											</table>

<!--排基本資料-->
	<table width="100%" id="bs${dtl.oid}id" style="display:none">
		<tr>
			<td>
			<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
    			<td width="1%" nowrap>
      			&nbsp;<img src="images/16-exc-mark.gif">&nbsp;開課基本資料設定
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
			<select name="choseType${dtl.oid}">
				<option value="1" <c:if test="${dtl.opt=='1'}" > selected</c:if>>必修</option>
    			<option value="2" <c:if test="${dtl.opt=='2'}" > selected</c:if>>選修</option>
    			<option value="3" <c:if test="${dtl.opt=='3'}" > selected</c:if>>通識</option>
    			</select>

    			<select name="open${dtl.oid}">
				<option value="1" <c:if test="${dtl.open==true}" > selected</c:if>>開放選修</option>
				<option value="0" <c:if test="${dtl.open==false}" > selected</c:if>>非開放選修</option>
    			</select>

    			<select name="elearning${dtl.oid}">
  					<option value="0" <c:if test="${dtl.elearning=='0'}" > selected</c:if>>一般課程</option>
  					<option value="1" <c:if test="${dtl.elearning=='1'}" > selected</c:if>>遠距課程</option>
  					<option value="2" <c:if test="${dtl.elearning=='2'}" > selected</c:if>>輔助課程</option>

  				</select>

  				<input type="text" name="thour${dtl.oid}" size="1" value="${dtl.thour}"/>小時

  				<input type="text" name="credit${dtl.oid}" size="1" value="${dtl.credit}"/>學分

  				上限<input type="text" name="selectLimit${dtl.oid}" size="1" value="${dtl.Select_Limit}"/>人

  				<select name="extrapay${dtl.oid}">
  					<option value="0" <c:if test="${dtl.extrapay2==''}" > selected</c:if>>無電腦實習費</option>
  					<option value="1" <c:if test="${dtl.extrapay2!=''}" > selected</c:if>>*有電腦實習費</option>
  				</select>

			</td>
		</tr>
	</table>
<!--排基本資料-->

<!--排教師-->
	<table width="100%" id="te${dtl.oid}id" style="display:none">
		<tr>
			<td>
			<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
    			<td width="1%" nowrap>
      			<table width="100%" cellpadding="0" cellspacing="0">
  					<tr>
    					<td width="1%" nowrap>
      					&nbsp;<img src="images/16-exc-mark.gif">&nbsp;教師及多教師設定
    					</td>
    					<td width="100%" align="left">
      					<hr noshade size="1" color="cfe69f"/>
    					</td>
  					</tr>
				</table>
    			</td>
    			<td width="100%" align="left">
      			<hr noshade size="1" color="cfe69f"/>
    			</td>
  			</tr>
  			<tr>
  				<td>
  				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
  						<td width="100%">
  						<input type="text" name="techidP${dtl.oid}" id="techidS" size="8" style="ime-mode:disabled" autocomplete="off"
						 	   value="${dtl.techid}"  readonly/><input type="text" onkeyup="getTeacherName(this.value)"
						 	   onkeydown="document.getElementById('Acsname').style.display='none';"
							   autocomplete="off" onclick="this.value='', document.getElementById('techidP${dtl.oid}').value=''" name="technameP${dtl.oid}"
							   id="technameS" size="12" value="${dtl.techName}"/>

  						</td>
  						<td>
  						<img src="images/24-member1.png" />
  						</td>
  					</tr>


<!--已存的一科目多教師-->
					<c:forEach items="${dtl.teacherS}" var="Teachers" varStatus="teachers">
  					<tr>
  						<td width="100%">
  						<input type="text" name="teachId" size="8" style="ime-mode:disabled" autocomplete="off"
						 	   value="${Teachers.teachId}"  readonly/><input type="text"
							   autocomplete="off" size="12" value="${Teachers.chiName2}"/>

  						</td>
  						<td>
  						<img src="images/24-member-add.png" />
  						</td>
  					</tr>
					</c:forEach>




				</table>
  				</td>
  			</tr>
		</table>
			</td>
		</tr>
	</table>
<!--排教師-->

<!--排課-->
	<table width="100%" id="dt${dtl.oid}id" style="display:none">
		<tr>
			<td>
			<table width="100%" cellpadding="0" cellspacing="0">
  					<tr>
    					<td width="1%" nowrap>
      					&nbsp;<img src="images/16-exc-mark.gif">&nbsp;排課設定
    					</td>
    					<td width="100%" align="left">
      					<hr noshade size="1" color="cfe69f"/>
    					</td>
  					</tr>
				</table>
			</td>
		</tr>
	</table>
<!--排課-->

<!--排考-->
	<table width="100%" id="ex${dtl.oid}id" style="display:none">
		<tr>
			<td>
			<table width="100%" cellpadding="0" cellspacing="0">
  					<tr>
    					<td width="1%" nowrap>
      					&nbsp;<img src="images/16-exc-mark.gif">&nbsp;排考設定
    					</td>
    					<td width="100%" align="left">
      					<hr noshade size="1" color="cfe69f"/>
    					</td>
  					</tr>
				</table>
			</td>
		</tr>
	</table>
<!--排考-->



<!--排跨選-->
	<table id="cs${dtl.oid}id" style="display:none" width="100%">
		<tr>
			<td>
			<table width="100%" cellpadding="0" cellspacing="0">
  					<tr>
    					<td width="1%" nowrap>
      					&nbsp;<img src="images/16-exc-mark.gif">&nbsp;網路跨選規則設定
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

<table>
<!--已存在的丟這裡-->

			<c:forEach items="${dtl.opencsList}" var="Opcs" varStatus="opcs">
			<tr>
				<td>
				<select name="cidno${dtl.oid}">
					<option value="">清除校區</option>
					<option value="*" <c:if test="${Opcs.cidno=='*'}" > selected</c:if>>所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.cidno==code5.idno}"> selected</c:if>>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="sidno${dtl.oid}">
					<option value="">清除學制</option>
					<option value="*"<c:if test="${Opcs.sidno=='*'}"> selected</c:if>>所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.sidno==code5.idno}"> selected</c:if>
						>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="didno${dtl.oid}">
					<option value="">清除科系</option>
					<option value="*" <c:if test="${Opcs.didno=='*'}"> selected</c:if>>所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.didno==code5.idno}"> selected</c:if>
						>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grade${dtl.oid}">
					<option value="">清除年級</option>
					<option value="*" <c:if test="${Opcs.grade=='*'}" > selected</c:if>>所有年級</option>
					<option value="1" <c:if test="${Opcs.grade=='1'}" > selected</c:if>>1年級</option>
					<option value="2" <c:if test="${Opcs.grade=='2'}" > selected</c:if>>2年級</option>
					<option value="3" <c:if test="${Opcs.grade=='3'}" > selected</c:if>>3年級</option>
					<option value="4" <c:if test="${Opcs.grade=='4'}" > selected</c:if>>4年級</option>
					<option value="5" <c:if test="${Opcs.grade=='5'}" > selected</c:if>>5年級</option>
				</select>
				<select name="departClass${dtl.oid}">
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

<!--批次建立3+N個跨選規則丟這裡-->
			<%
			for(int i=0; i<6; i++){
			%>

			<tr>
				<td>
				<select name="cidno${dtl.oid}">
					<option value="">選擇校區</option>
					<option value="*">所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="sidno${dtl.oid}">
					<option value="">選擇學制</option>
					<option value="*">所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="didno${dtl.oid}">
					<option value="">選擇科系</option>
					<option value="*">所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grade${dtl.oid}">
					<option value="">選擇年級</option>
					<option value="*">所有年級</option>
					<option value="1">1年級</option>
					<option value="2">2年級</option>
					<option value="3">3年級</option>
					<option value="4">4年級</option>
					<option value="5">5年級</option>
				</select>
				<select name="departClass${dtl.oid}">
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
			<%}%>


			</table>

		</td>
	</tr>
</table>
<!--跨選gameover-->


<!--該死的錯誤提示-->
<c:if test="${dtl.open2=='*'&& dtl.open==false}">
<table>
	<tr>
		<td><img src="images/24-book-green-error.png" title="嚴重問題"></td><td>這門課有設定跨選規則卻沒有開放選修</td>
		<td>&nbsp;&nbsp;&nbsp;<img src="images/16-tool-b.png" title="修理它" onclick="showEditCross('cs${dtl.oid}id'), showEditCross('bs${dtl.oid}id')"></td>
	</tr>
</table>
</c:if>
<c:if test="${dtl.open2=='a'}">
<table>
	<tr>
		<td><img src="images/24-book-green-error.png" title="嚴重問題"></td><td>這門課時數有問題</td><td>&nbsp;&nbsp;&nbsp;<img src="images/16-tool-b.png"title="修理它"></td>
	</tr>
</table>
</c:if>
<c:if test="${dtl.techid==''}">
<table>
	<tr>
		<td><img src="images/24-book-green-message.png" title="小事"></td><td>這門課沒有任課教師</td><td>&nbsp;&nbsp;&nbsp;<img src="images/16-tool-b.png" title="修理它"></td>
	</tr>
</table>
</c:if>
<!--該死的錯誤提示-->











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
			</c:forEach>



		</td>
	</tr>
	<tr height="40">
	<td bgcolor="#cfe69f" align="center">

	<input type="hidden" name="courseNumber" value=<%=request.getParameter("courseNumber")%> />
	<input type="hidden" name="teacherId" value=<%=request.getParameter("teacherId")%> />
	<input type="hidden" name="sterm" value=<%=request.getParameter("sterm")%> />
	<input type="hidden" name="choseType" value=<%=request.getParameter("choseType")%> />
	<input type="hidden" name="open" value=<%=request.getParameter("open")%> />
	<input type="hidden" name="elearning" value=<%=request.getParameter("elearning")%> />
	<input type="hidden" name="classLess" value=<%=request.getParameter("classLess")%> />
	<c:if test="${!empty dtimeBatchList}" >
	<input type="submit" value="確定修改" class="CourseButton"/>
	</c:if></td>
	</form></tr>

</c:if>
</table>
<table class="ds_box" cellpadding="0" cellspacing="0" name="edit" id="edit" style="display: none;">
	<tr>
		<td id="AcsnameInfo" onclick="this.style.display='none'">
		
		</td>
	</tr>
</table>




<script>
function showInfo(tableId){
	// 放置顯示座標
	document.getElementById('edit').style.left=gt_cnameLeft1(document.getElementById(tableId));
	document.getElementById('edit').style.top=gt_cnameTop1(document.getElementById(tableId));
	// 取得x座標
	function gt_cnameLeft1(el) {
		var tmp = el.offsetLeft;
		el = el.offsetParent
	while(el) {
		tmp += el.offsetLeft;
		el = el.offsetParent;
		}
	return tmp;
	}

	// 取得y座標
	function gt_cnameTop1(el) {
		var tmp = el.offsetTop;
		el = el.offsetParent
	while(el) {
		tmp += el.offsetTop;
		el = el.offsetParent;
		}
	return tmp+24;
	}
	document.getElementById('edit').style.display='inline'


}
</script>

<script>
/**
*秀跨選規則選項
*/
function showEditCross(editId){
	if(document.getElementById(editId).style.display=='none'){
	document.getElementById(editId).style.display='inline';
	}else{
	document.getElementById(editId).style.display='none';
	}
}
</script>

<script>
function hideEditCross(shitId){
	document.getElementById(shitId).style.display='none';
}
</script>

<script>
	/**
	*全部設定成已修改
	*/
	function setAllCheck(checkBasic, checkTecher, checkClass, checkExam, checkCross){
		document.getElementById(checkBasic).value='1';
		document.getElementById(checkTecher).value='1';
		document.getElementById(checkClass).value='1';
		document.getElementById(checkExam).value='1';
		document.getElementById(checkCross).value='1';
		}
</script>

<script>
	/**
	*單一設定為已修改
	*/
	function setOneCheck(checkId){
		document.getElementById(checkId).value='1';
		}
</script>

