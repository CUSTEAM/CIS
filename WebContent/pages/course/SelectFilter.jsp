<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/SelectFilter" method="post" onsubmit="init('查詢進行中, 請稍後')">
<!--welcomeMode Start-->
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/user_delete.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">選課篩選作業</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF">				
				<select name="sterm">
    				<option value="1" <c:if test="${SelectFilterForm.map.sterm=='1'}" > selected</c:if>>上學期</option>
    				<option value="2" <c:if test="${SelectFilterForm.map.sterm=='2'}" > selected</c:if>>下學期</option>
    			</select>
    			</td>
    			<td class="hairlineTd">
    			<select name="choseType">
					<option value="%"
					<c:if test="${SelectFilterForm.map.choseType==''}" > selected</c:if>>選別</option>
					<option value="1" <c:if test="${SelectFilterForm.map.choseType=='1'}" > selected</c:if>>必修</option>
    				<option value="2" <c:if test="${SelectFilterForm.map.choseType=='2'}" > selected</c:if>>選修</option>
    				<option value="3" <c:if test="${SelectFilterForm.map.choseType=='3'}" > selected</c:if>>通識</option>
    			</select>
				</td>
				<td class="hairlineTd">
    			<select name="open">
    				<option value="%" <c:if test="${SelectFilterForm.map.open=='%'}" > selected</c:if>>開放規則</option>
    				<option value="1" <c:if test="${SelectFilterForm.map.open=='1'}" > selected</c:if>>開放選修</option>
    				<option value="0" <c:if test="${SelectFilterForm.map.open=='0'}" > selected</c:if>>非開放選修</option>
				</select>
				</td>
				<td width="30" align="center" class="hairlineTdF">
				 	<img src="images/icon_component.gif" />
				</td>
			</tr>
		</table>
		
		
		
		
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF">
				   <c:set var="campusSel" value="${SelectFilterForm.map.campusInCharge2}"/>
	  			   <c:set var="schoolSel" value="${SelectFilterForm.map.schoolInCharge2}"/>
	  			   <c:set var="deptSel"   value="${SelectFilterForm.map.deptInCharge2}"/>
	  			   <c:set var="classSel"  value="${SelectFilterForm.map.classInCharge2}"/>
	  			   <%@include file="/pages/include/ClassSelect8.jsp"%>
				</td>
				<td class="hairlineTd">
	  				<select name="elearning">
	  			   		<option value="%" <c:if test="${SelectFilterForm.map.elearning=='%'}" > selected</c:if>>授課形態</option>
  						<option value="0" <c:if test="${SelectFilterForm.map.elearning=='0'}" > selected</c:if>>一般課程</option>
  						<option value="1" <c:if test="${SelectFilterForm.map.elearning=='1'}" > selected</c:if>>遠距課程</option>
  						<option value="2" <c:if test="${SelectFilterForm.map.elearning=='2'}" > selected</c:if>>輔助課程</option>
					</select>
				</td>
				<td width="30" align="center" class="hairlineTdF">
				 	<img src="images/icon_component.gif" />
				</td>
			</tr>
		</table>
		</td>
	</tr>	
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit"
		name="method"
		id="CourseFilterSearch"
		onMouseOver="showHelpMessage('選擇範圍並進行下一步驟', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"
		value="<bean:message key='CourseFilterSearch'/>"
		class="gSubmit">   
						   
	   <INPUT type="submit"
	   name="method" id="CourseFilterList"
	   value="<bean:message
	   key='CourseFilterList'/>"
	   class="gSubmit" 
	   onMouseOver="showHelpMessage('檢視範圍內的篩選清單, 並列出各種簽收單或報表', 'inline', this.id)" 
	   onMouseOut="showHelpMessage('', 'none', this.id)" />
		
		
		<INPUT type="submit"
	   name="method" id="Clear"
	   value="<bean:message
	   key='Clear'/>"
	   class="gCancel" 
	   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
	   onMouseOut="showHelpMessage('', 'none', this.id)" />
	   
		</td>
	</tr>
	


<!-- 
	<c:if test="${table!=null}" >
	<tr>
		<td>		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				<table width="100%">
					<tr>
						<td>
						&nbsp;&nbsp;<img src="images/16-star-hot.png">
						</td>
						<td align="left" width="100%">
						中選
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>		
		<display:table name="${table}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="學生班級" property="ClassName" sortable="true" class="left" />
			<display:column title="學號" property="student_no" sortable="true" class="left" />
			<display:column title="姓名" property="student_name" sortable="true" class="left" />
			<display:column title="開課班級" property="ClassName2" sortable="true" class="left" />
			<display:column title="科目名稱" property="chi_name" sortable="true" class="left" />
			<display:column title="科目代碼" property="cscode" sortable="true" class="left" />
			<display:column title="選別" property="opt" sortable="true" class="left" />
			<display:column title="時數" property="thour" sortable="true" class="left" />
			<display:column title="學分" property="credit" sortable="true" class="left" />
		</display:table>

		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
	-->
	</c:if>
	<c:if test="${chair!=null}" >
	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				<table width="100%">
					<tr>
						<td>
						&nbsp;&nbsp;<img src="images/tu.png">
						</td>
						<td align="left" width="100%">
						被篩掉的學生
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<display:table name="${chair}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="學生班級" property="ClassName" sortable="true" class="left" />
			<display:column title="學號" property="student_no" sortable="true" class="left" />
			<display:column title="姓名" property="student_name" sortable="true" class="left" />
			<display:column title="開課班級" property="ClassName2" sortable="true" class="left" />
			<display:column title="科目名稱" property="chi_name" sortable="true" class="left" />
			<display:column title="科目代碼" property="cscode" sortable="true" class="left" />
			<display:column title="選別" property="opt" sortable="true" class="left" />
			<display:column title="時數" property="thour" sortable="true" class="left" />
			<display:column title="學分" property="credit" sortable="true" class="left" />
		</display:table>

		</td>
	</tr>	
	</c:if>



<!--查詢模式 有查到東西才秀查詢 -->
<c:if test="${filterMode=='search'}">
	<c:if test="${selectFilterList!=null}" >
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${selectFilterList}" export="false" id="row" sort="list" excludedParams="*" class="list" >
	        <c:if test="${empty selectFilterList}" >
	        	<%@ include file="../include/NoBanner.jsp" %>
	        </c:if>
			<display:column title="<script>generateTriggerAll(${fn:length(selectFilterList)}, 'selectFilter'); </script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "selectFilter")</script></display:column>
	        	<display:column titleKey="Course.label.className" property="departClass2" sortable="true" class="left" />
	        	<display:column titleKey="Course.label.classNo" property="departClass" sortable="true" class="left" />
	        	<display:column titleKey="Course.label.courseName" property="chiName2" sortable="true" class="left" />
	        	<display:column titleKey="Course.label.courseNumber" property="cscode" sortable="true" class="left" />
	        	<display:column title="教師姓名" property="techName" sortable="true" class="left" />
	        	<display:column titleKey="Course.label.opt" property="opt2" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.credit" property="credit" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.hours" property="thour" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.stuSelect" property="stuSelect" sortable="true" class="center" />
	        	<display:column title="上限" property="Select_Limit" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.opencs" property="openName" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.elearning" property="elearningName" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.open2" property="open2" sortable="true" class="center" />
	        	<display:column title="<img src='images/ico_file_excel1.png' border='0'>" property="icon" nulls="false" class="center" href="/CIS/StudentSel" paramId="Oid" paramProperty="oid" />
	      	</display:table>

		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='CourseFilter'/>" class="gCancel" >
		<INPUT type="submit" name="method" value="<bean:message key='StudentFilter'/>" class="gGreen" >
		</td>
	</tr>
		
	</c:if>
</c:if>


<!--篩選查詢模式 有查到東西才秀查詢 -->
<c:if test="${filterMode=='FilterSearch'}">
	<c:if test="${selectFilterCouList!=null}" >
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<display:table name="${selectFilterCouList}" export="false" id="row" sort="list" excludedParams="*" class="list" >
	        <c:if test="${empty selectFilterCouList}" >
	        	<%@ include file="../include/NoBanner.jsp" %>
	        </c:if>
		<display:column title="<script>generateTriggerAll(${fn:length(selectFilterCouList)}, 'selectFilterCouList'); </script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "selectFilterCouList")</script></display:column>
	        <display:column titleKey="Course.label.className" property="departClass2" sortable="true" class="left" />
	        <display:column titleKey="Course.label.classNo" property="departClass" sortable="true" class="left" />
	        <display:column titleKey="Course.label.courseName" property="chiName2" sortable="true" class="left" />
	        <display:column titleKey="Course.label.courseNumber" property="cscode" sortable="true" class="left" />
	        <display:column title="教師姓名" property="techName" sortable="true" class="left" />
	        <display:column titleKey="Course.label.opt" property="opt2" sortable="true" class="center" />
	        <display:column titleKey="Course.label.credit" property="credit" sortable="true" class="center" />
	        <display:column titleKey="Course.label.hours" property="thour" sortable="true" class="center" />
	        <display:column titleKey="Course.label.opencs" property="openName" sortable="true" class="center" />
	        <display:column titleKey="Course.label.elearning" property="elearningName" sortable="true" class="center" />

	        <display:column title="上限" property="Select_Limit" sortable="true" class="center" />
			<display:column title="<img src='images/tu.png' border='0'>" property="stuSelect" sortable="true" class="center" />

	        <display:column title="原因" property="type" sortable="true" class="center" />
	      </display:table>
		</td>
	</tr>
	<tr>
		<td colspan="7" align="center" height="40">
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F">
  				<tr>
    				<td bgcolor="#FFFFFF">
    					<table>
    						<tr>
    							<td> 選擇輸出格式:</td>
    							<td>
    							<a href="course/export/list4CouFilter.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							
    							<a href="/CIS/FilterAdvice" TARGET="_blank">
    							<img src="images/icon_pdf.gif" border="0"> 通知單
    							</a>
    							<a href="/CIS/FilterAdvice1" TARGET="_blank">
    							<img src="images/icon_pdf.gif" border="0"> 簽收單
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='CourseFilterComeback'/>" class="gCancel" >
		<INPUT type="submit" name="method" value="<bean:message key='SendMail'/>" class="gGreen" >
		</td>
	</tr>
	</c:if>
</c:if>


<c:if test="${filterMode=='delete'}">
<tr>
	<td>
	<c:if test="${filterType=='stu'}">		
	<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
			<table width="100%">
				<tr>
					<td>
					&nbsp;&nbsp;<img src="images/24-book-green-remove.png">
					</td>
					<td align="left" width="100%">
					下列課程因為選課人數超過上限，因此必須對已選課的同學進行篩選
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</c:if>
	<!--如果是課程篩選模式-->
	<c:if test="${filterType=='cou'}">		
	<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
			<table width="100%">
				<tr>
					<td>
					&nbsp;&nbsp;<img src="images/24-book-green-remove.png">
					</td>
					<td align="left" width="100%">
					下列課程將會直接從開課主檔刪除
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</c:if>
	</td>
</tr>



<tr>
	<td>
	<display:table name="${selFilter}" export="false" id="row" sort="list" excludedParams="*" class="list">
		<display:column titleKey="Course.label.className" property="departClass2" sortable="true" class="left" />
	        <display:column titleKey="Course.label.classNo" property="departClass" sortable="true" class="left" />
	        <display:column titleKey="Course.label.courseName" property="chiName2" sortable="true" class="left" />
	        <display:column titleKey="Course.label.courseNumber" property="cscode" sortable="true" class="left" />


	        <display:column titleKey="Course.label.stuSelect" property="stuSelect" sortable="true" class="center" />
	        <display:column title="上限" property="selectLimit" sortable="true" class="center" />

	</display:table>
</tr>


<tr height="30">
	<td class="fullColorTable">
	
	</td>
</tr>


<tr>
	<td>
	<!--如果是學生篩選模式-->
	<c:if test="${filterType=='stu'}">
	<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
			<table width="100%">
				<tr>
					<td>
					&nbsp;&nbsp;<img src="images/24-book-green-remove.png">
					</td>
					<td align="left" width="100%">
					下列學生會先進行虛擬升級作業後依亂數進行退選, 若為必修將以1.本班優先, 2.本系高年級優先, 3.本部高年級優先
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>	
	</c:if>
	<!--如果是課程篩選模式-->
	<c:if test="${filterType=='cou'}">
	<table width="99%" class="hairLineTable">
		<tr>
			<td class="hairLineTdF">
			<table width="100%">
				<tr>
					<td>
					&nbsp;&nbsp;<img src="images/24-book-green-remove.png">
					</td>
					<td align="left" width="100%">
					下列學生將直接進行退選
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>	
	</c:if>
	</td>
</tr>
<tr>
	<td>

	<display:table name="${stuFilter}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="<input type='checkbox' checked disabled>" property="nullValue" nulls="false" />
	        <display:column title="學生學號" property="student_no" sortable="true" class="right" />
	        <display:column title="學生姓名" property="student_name" sortable="true" class="left" />
	        <display:column title="學生班級代碼"  property="ClassNo" sortable="true" class="right" />
	        <display:column title="學生所屬班級" property="ClassName" sortable="true" class="left" />
	        <display:column title="開課班級代碼" property="depart_class" sortable="true" class="right" />
	        <display:column title="開課班級名稱" property="ClassName2" sortable="true" class="left" />
	        <display:column title="課程代碼" property="cscode" sortable="true" class="right" />
	        <display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	        <display:column title="學分" property="credit" sortable="true" class="left" />
	        <display:column title="時數" property="thour" sortable="true" class="left" />
	      </display:table>



	</td>
</tr>
<tr>
	<td class="fullColorTabled" align="center">
	<INPUT type="submit" name="method" value="<bean:message key='CourseFiltration'/>" class="CourseButton" >
		<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton" >
	
	</td>
</tr>
		
</c:if>
	</html:form>

</table>



