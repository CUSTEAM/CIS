<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>generateTableBanner('<p align="left">&nbsp;&nbsp;開課作業 - 檢視模式</p>');</script>
	<tr>
		<td><br>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td width="5%" align="left"><hr noshade size="1" color="cfe69f"/></td>
	   				<td width="1%" nowrap>開課基本資料	</td>
		   			<td width="99%" align="left"><hr noshade size="1" color="cfe69f"/></td>
	 			</tr>	 			
			</table>
			<table>
				<tr>
					<td>
						<table>
							<tr>
								<td><bean:message key="OpenCourse.label.className" bundle="COU"/>: </td>
								<td align="left">
									<input type="text" name="classNo" size="12" value="<c:out value="${departClass}"/>" readonly="true">
									<input type="text" name="className" size="12" value="${className2}" readonly="true">
								</td>
							</tr>
							<tr>
								<td><bean:message key="setCourse.label.courseNumber" bundle="COU"/>: </td>
								<td>
									<input type="text" name="courseNumber" size="12" value="<c:out value="${cscode}"/>"  readonly="true">
									<input type="text" name="courseName" size="12" value="<c:out value="${cscode2}"/>" readonly="true">
								</td>
							</tr>
							<tr height="30">
								<td><bean:message key="OpenCourse.label.teacherNumber" bundle="COU"/>: </td>
								<td>
									<input type="text" name="teacherId" size="12" value="<c:out value="${techid}"/>"  readonly="true">
									<input type="text" name="teacherName" size="12" value="<c:out value="${techName}"/>" readonly="true">
								</td>
							</tr>
						</table>								
					</td>
					<td>
						<table>
							<tr height="30">
								<td><bean:message key="OpenCourse.label.no" bundle="COU"/>&nbsp;
									<select name="term" disabled>
										<option value="1" <c:if test="${sterm==1}" > selected</c:if>>1</option>
										<option value="2" <c:if test="${sterm==2}" > selected </c:if>>2</option>
									</select>
									<bean:message key="OpenCourse.label.term" bundle="COU"/> 
								</td>
								<td>
									<select name="choseType" disabled>
										<option value="1" <c:if test="${opt==1}" > selected </c:if>>必修</option>
										<option value="2"<c:if test="${opt==2}" > selected </c:if>>選修</option>
									</select>
								</td>
							</tr>
						</table>
						<table>
							<tr height="30">
								<td>時數: </td>
								<td align="left">
									<input type="text" name="thour" size="2" value="${thour}" readonly="true"> 
									<input type="text" name="credit" size="2" value="${credit}" readonly="true"> 學分
								</td>
								<td></td>
							</tr>
							<tr height="30">
								<td>上限: </td><td align="left"><input type="text" name="selectLimit" size="4" value="${selectLimit}" readonly="true"> 人</td>
								<td></td>
							</tr>
						</table>				
					</td>			
				</table><br>
				<table width="100%" cellpadding="0" cellspacing="0">
  					<tr>
  						<td width="5%" align="left"><hr noshade size="1" color="cfe69f"/></td>
    					<td width="1%" nowrap>上課時段 / 地點</td>
    					<td width="99%" align="left"><hr noshade size="1" color="cfe69f"/></td>
  					</tr>
  					<tr>
  						<td><br></td>
  					</tr>
				</table>
				<table>
					<c:forEach items="${dtimeClasses}" var="dTime" varStatus="dTimeS">
					<tr id=numInput${dTimeS.index} style=display:inline>
						<td>星期 
							<select name="week" disabled>
    							<option value="1" <c:if test="${dTime.week==1}" > selected</c:if>>一</option>
    							<option value="2" <c:if test="${dTime.week==2}" > selected</c:if>>二</option>
    							<option value="3" <c:if test="${dTime.week==3}" > selected</c:if>>三</option>
    							<option value="4" <c:if test="${dTime.week==4}" > selected</c:if>>四</option>
    							<option value="5" <c:if test="${dTime.week==5}" > selected</c:if>>五</option>
    							<option value="6" <c:if test="${dTime.week==6}" > selected</c:if>>六</option>
    							<option value="7" <c:if test="${dTime.week==7}" > selected</c:if>>日</option>
  							</select> 第 
							<select name="begin" disabled>
    							<option value="1" <c:if test="${dTime.begin==1}" > selected</c:if>>1</option>
    							<option value="2" <c:if test="${dTime.begin==2}" > selected</c:if>>2</option>
    							<option value="3" <c:if test="${dTime.begin==3}" > selected</c:if>>3</option>
    							<option value="4" <c:if test="${dTime.begin==4}" > selected</c:if>>4</option>
    							<option value="5" <c:if test="${dTime.begin==5}" > selected</c:if>>5</option>
    							<option value="6" <c:if test="${dTime.begin==6}" > selected</c:if>>6</option>
    							<option value="7" <c:if test="${dTime.begin==7}" > selected</c:if>>7</option>
    							<option value="8" <c:if test="${dTime.begin==8}" > selected</c:if>>8</option>
    							<option value="9" <c:if test="${dTime.begin==9}" > selected</c:if>>9</option>
    							<option value="10" <c:if test="${dTime.begin==10}" > selected</c:if>>10</option>
    							<option value="11" <c:if test="${dTime.begin==11}" > selected</c:if>>11</option>
    							<option value="12" <c:if test="${dTime.begin==12}" > selected</c:if>>12</option>
  							</select> ~ 
							<select name="end" disabled>
    							<option value="1"<c:if test="${dTime.end==1}" > selected</c:if>>1</option>
    							<option value="2"<c:if test="${dTime.end==2}" > selected</c:if>>2</option>
    							<option value="3"<c:if test="${dTime.end==3}" > selected</c:if>>3</option>
    							<option value="4"<c:if test="${dTime.end==4}" > selected</c:if>>4</option>
    							<option value="5"<c:if test="${dTime.end==5}" > selected</c:if>>5</option>
    							<option value="6"<c:if test="${dTime.end==6}" > selected</c:if>>6</option>
    							<option value="7"<c:if test="${dTime.end==7}" > selected</c:if>>7</option>
    							<option value="8"<c:if test="${dTime.end==8}" > selected</c:if>>8</option>
    							<option value="9"<c:if test="${dTime.end==9}" > selected</c:if>>9</option>
    							<option value="10"<c:if test="${dTime.end==10}" > selected</c:if>>10</option>
    							<option value="11"<c:if test="${dTime.end==11}" > selected</c:if>>11</option>
    							<option value="12"<c:if test="${dTime.end==12}" > selected</c:if>>12</option>
  							</select> 節 在 
							<input type='text' name='place' value="${dTime.place}" size="6" readonly="true">
						</td>
					</tr> 
					<tr>
						<td></td>
					</tr>					
					</c:forEach>					
					<%					
						int star = ((Integer)request.getAttribute("dtimeClasSize")).intValue();
						for(int i = star; i < 10; i++) { 
					%>		
					
					<tr id=numInput<%=i%> style=display:none>
						<td>星期
							<select name="week" id="week<%= i %>" disabled>
								<option value=""></option>
    							<option value="1">一</option>
    							<option value="2">二</option>
    							<option value="3">三</option>
    							<option value="4">四</option>
    							<option value="5">五</option>
    							<option value="6">六</option>
    							<option value="7">日</option>
  							</select> 第 
							<select name="begin" disabled>
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
  							</select> ~ 
							<select name="end" disabled>
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
  							</select> 節 在 
							<input type="text" name="place" size="6" readonly="true">
						</td>
					</tr> 
					<tr>
						<td></td>
					</tr>
					<% } %>
					<!--tr id=addMoreNum style=display:inline>
						<td align="right">
							<input value="增加上課的時段" type="button" onClick="javascript:AddNumInput()" class="CourseButton"/>
						</td>
					</tr>
					<tr id="delMoreNum" style="display:none">
						<td align="right">多餘時段請維持空白</td>
					</tr-->
				</table><br>
				<table width="100%" cellpadding="0" cellspacing="0">
  					<tr>
  						<td width="5%" align="left"><hr noshade size="1" color="cfe69f"/></td>
    					<td width="1%" nowrap>&nbsp;考試時間 / 地點</td>
    					<td width="99%" align="left"><hr noshade size="1" color="cfe69f"/></td>
  					</tr>
  					<tr>
  						<td><br></td>
  					</tr>
				</table>
				<table>
					<tr height="30">
						<td>日期: 
							<input type="text" name="examDate" size="8" value="${Edate}" readonly="true">
							<!-- IMG src="images/cal.gif" name="calendar" width="20" height="20" align="top" style="cursor:hand" 
	  	  						onclick="javascript:if(!examDate.disabled)popCalFrame.fPopCalendar('examDate','examDate',event);"-->
						</td>
						<td>第: 
							<select name="examTime" disabled>
								<option value="" <c:if test="${dTime.end==''}" > selected</c:if>>幾</option>
								<option value="1"<c:if test="${dTime.end==1}" > selected</c:if>>1</option>
    							<option value="2"<c:if test="${Ebegin==2}" > selected</c:if>>2</option>
    							<option value="3"<c:if test="${Ebegin==3}" > selected</c:if>>3</option>
    							<option value="4"<c:if test="${Ebegin==4}" > selected</c:if>>4</option>
    							<option value="5"<c:if test="${Ebegin==5}" > selected</c:if>>5</option>
    							<option value="6"<c:if test="${Ebegin==6}" > selected</c:if>>6</option>
    							<option value="7"<c:if test="${Ebegin==7}" > selected</c:if>>7</option>
    							<option value="8"<c:if test="${Ebegin==8}" > selected</c:if>>8</option>
    							<option value="9"<c:if test="${Ebegin==9}" > selected</c:if>>9</option>
    							<option value="10"<c:if test="${Ebegin==10}" > selected</c:if>>10</option>
    							<option value="11"<c:if test="${Ebegin==11}" > selected</c:if>>11</option>
    							<option value="12"<c:if test="${Ebegin==12}" > selected</c:if>>12</option>
							</select>節
						 </td>
						 <td>監考老師: 
						 	<input type="text" name="examEmplId" size="10" value="${Etech}" value="" onChange="getExamTeacherName()" readonly="true">
							<input type="text" name="examEmplName" size="10" value="${examEmplName}" readonly="true">
						 </td>
						 <td>考試地點:
							<input type="text" name="examPlace" size="10" value="${Eplace}" readonly="true">
						</td>
					</tr>
				</table><br>
			</td>
		</tr>
		<script>
	    	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">&nbsp;&nbsp;'+
	       	'<INPUT type="submit" name="method" value="<bean:message key='ModifyRecord'/>" class="CourseButton">&nbsp;&nbsp;');
	    </script>
       	<script> 
				function getFullClass() { 
				//var className = document.OpenCourseForm.classNo.value;
				var className = ""; 
				"<c:forEach items='${classes}' var='cc'>"
				if (document.OpenCourseForm.classNo.value == "${cc.classNo}"){
				className="${cc.className}" }
				"</c:forEach>"
				document.OpenCourseForm.className.value = className; 
				}
			</script>
	      	<script>			
				function getExamTeacherName() { 
				//var examEmplName = document.OpenCourseForm.examEmplId.value;
				var examEmplName = ""; 
				"<c:forEach items='${employees}' var='gg'>"
				if (document.OpenCourseForm.examEmplId.value == "${gg.idno}"){
				examEmplName="${gg.cname}" }
				"</c:forEach>"
				document.OpenCourseForm.examEmplName.value = examEmplName; 
				}
			</script>
</table> 
	<script> 
				function getCourseName() { 
				//var courseName = document.OpenCourseForm.courseNumber.value;
				var courseName = ""; 
				"<c:forEach items='${coursesOp}' var='aa'>"
				if (document.OpenCourseForm.courseNumber.value == "${aa.cscode}"){
				courseName="${aa.chiName}" }
				"</c:forEach>"
				document.OpenCourseForm.courseName.value = courseName; 
				}
	</script>
	<script>			
				function getTeacherName() { 
				//var teacherName = document.OpenCourseForm.teacherId.value;
				var teacherName = ""; 
				"<c:forEach items='${employees}' var='bb'>"
				if (document.OpenCourseForm.teacherId.value == "${bb.idno}"){
				teacherName="${bb.cname}" }
				"</c:forEach>"
				document.OpenCourseForm.teacherName.value = teacherName; 
				}
	</script>
	<script>
  				var nextHiddenIndex=${dtimeClasSize}+1;
  				
				function AddNumInput(){
  
	  				var idxStart = nextHiddenIndex;
	  				var idxEnd   = (nextHiddenIndex+1);
	  				var idx;
	  				for  (idx=idxStart; idx<idxEnd; idx++){
	    				document.all["numInput" + idx].style.display = "inline";
	  				}
	  				//document.all["numInput" + idxStart].style.display = "inline";
	  				nextHiddenIndex++;  
	
	  				if (nextHiddenIndex >=9){
	    				document.all.addMoreNum.style.display = "none";
	  				}
	  				
  					if (nextHiddenIndex >=${dtimeClasSize}+1){
	    				document.all.delMoreNum.style.display = "inline";
	  				}
	  				  
  
				};
				
				function deleteNumInput(){
  				nextHiddenIndex--;  
  				var idxStart = nextHiddenIndex;
  				
  				document.all["numInput" + idxStart].style.display = "none";
  				with(document.forms[0]) {
  					elements["week" + idxStart].options[0].selected = true;
  				}
  				
  				if (nextHiddenIndex >=9){
	    				document.all.addMoreNum.style.display = "none";
	  				}
	  				
  					if (nextHiddenIndex >=9){
	    				document.all.delMoreNum.style.display = "inline";
	  				}
				}
				
				
	</script>
