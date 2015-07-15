<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/tooltips.js" %>

<DIV id="TipLayer" style="visibility:hidden;position:absolute;z-index:1000;top:-100"></DIV>
<script type="text/javascript">
Style[9]=["white","black","#000099","#E8E8FF","","","","","","","","","","",200,"",2,2,10,10,51,0.5,75,"simple","gray"];
Text[0]=["課程中英文簡介", "點選此處可檢視課程中英文說明"];
Text[1]=["課程教學大綱", "點選此處可檢視課程教學大綱說明"];
var FiltersEnabled = 1;
history.go(1);
applyCssFilter();
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/StudentCourseSearch" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="student.onlineAddDelCourse.selectedCourseOpt1" bundle="STD"/></B>&nbsp;&nbsp;[<font color="red"><a href="http://www.cust.edu.tw/www/info/code_class.html" target="_blank">節次時間對照表</a></font>]</div>');</script>
	<script>generateTableBanner('<font size="-1" color="blue">依本校大學部學則第十四條規定：學生如重讀已修習成績及格而名稱相同之科目者，其原修習名稱相同之該一科目，應予註銷。如已加選者，請於規定時間內辦理退選!</font>')</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${courseInfo}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>
		        <display:column title="開課班級" property="ClassName" sortable="false" class="center" />        
		        <display:column titleKey="Course.label.courseName" sortable="false" class="left">
		        	<c:out value="${row['chi_Name']}" />&nbsp;
		        	<html:link page="${readCourseIntro}.do?method=readCourseIntro" paramName="row" paramId="oid" paramProperty="oid" onmouseover="stm(Text[0], Style[9])" onmouseout="htm()">
						[<font color="blue">中英文簡介</font>]
					</html:link>&nbsp;
					<html:link page="${readCourseSyllabus}.do?method=readCourseSyllabus" paramName="row" paramId="oid" paramProperty="oid" onmouseover="stm(Text[1], Style[9])" onmouseout="htm()">
						[<font color="blue">教學大綱</font>]
					</html:link>
				</display:column>	
				<display:column titleKey="Course.label.techName" property="cname" sortable="false" class="center" />
		        <display:column titleKey="Course.label.opt" property="opt2" sortable="false" class="center" />	        
		        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
		        <display:column titleKey="Course.label.hours" property="thour" sortable="false" class="center" />
		        <display:column titleKey="Course.onlineAddRemoveCourse.stuSel" property="stu_Select" sortable="false" class="center" />
		        <display:column titleKey="Course.label.classTime" property="time" sortable="false" class="left"/>
		        <display:column titleKey="Course.label.place" sortable="false" class="center">
		        	<c:out value="${row['name2']}" />&nbsp;&nbsp;<c:out value="${row['room_Id']}" />
		        </display:column>
		    </display:table>
		</td>
	</tr>
	<c:if test="${not empty courseInfo}">
	<tr>
		<td>
			<table width="100%" cellpadding="2" cellspacing="2" border="1">
				<tr>
					<th height="30" bgcolor="#99CC99">&nbsp;</th>
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status2">
					<th align="center" bgcolor="#99CC99">
						<font color="white"><c:out value="${weekdayList[status2.index]}" /></font>
					</th>
					</c:forEach>
				</tr>
				<c:if test="${rowsCols['mode'] == 'D'}">
				<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="#99CC99">
						<font color="white"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font>
					</td>	
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
					<td height="100" width="100"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
						<!-- c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>-->
						<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
				</c:if>
				
				<c:if test="${rowsCols['mode'] == 'N'}">
				<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="#99CC99">
						<b><c:out value="${nodeList[status.index]}" escapeXml="false" /><b/>
					</td>	
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
					<td height="100" width="100"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['chi_name']}" /><br/>
						<!-- c:out value="${courseList[(status1.index * 15) + status.index + 5]['cname']}" /><br/>-->
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['name2']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
				</c:if>
				
				<c:if test="${rowsCols['mode'] == 'H'}">
				<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="#99CC99">
						<b><c:out value="${nodeList[status.index]}" escapeXml="false" /><b/>
					</td>	
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
					<td height="100" width="100"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
						<!-- c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>-->
						<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
				</c:if>
				
				<c:if test="${rowsCols['mode'] == 'S'}">
				<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="#99CC99">
						<b><c:out value="${nodeList[status.index]}" escapeXml="false" /><b/>
					</td>	
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
					<td height="100" width="100"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
						<!-- c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>-->
						<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
				</c:if>
			</table>
		</td>
	</tr>
	</c:if>
</html:form>
</table>