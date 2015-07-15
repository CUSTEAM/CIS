<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Chairman/StudentInfo" method="post" onsubmit="init('系統處理中...')">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.QueryStudentInfo" bundle="REG"/></B></div>');</script>	  
	<tr>
		<td>
	    	<table width="100%" cellspacing="5" class="empty-border">
			 	<tr>
			 		<td><bean:message key="Name" />&nbsp;
			 	   		<input type="text" name="nameC" size="12" value="${StudentListFormC.map.nameC}">
			 	   	</td>
			 	 	<td><bean:message key="StudentNo"/>&nbsp;
			 	   		<input type="text" name="studentNoC" size="12" value="${StudentListFormC.map.studentNoC}">
			 	   	</td>
			     	<td><bean:message key="Idno" />&nbsp;
			       		<input type="text" name="idNoC" size="12" value="${StudentListFormC.map.idNoC}">
			       	</td>
			    </tr>
  			 	<tr>
  			 		<td colspan="2"><bean:message key="Class"/>&nbsp;
	  			   		<c:set var="campusSel" value="${StudentListFormC.map.campusInChargeC}"/>
	  			   		<c:set var="schoolSel" value="${StudentListFormC.map.schoolInChargeC}"/>
	  			   		<c:set var="deptSel"   value="${StudentListFormC.map.deptInChargeC}"/>
	  			   		<c:set var="classSel"  value="${StudentListFormC.map.classInChargeC}"/>
	  			   		<%@ include file="/pages/include/ClassSelectC.jsp" %>
	  			   	</td>
	  			 	<td><bean:message key="Status" />&nbsp;
	  			   		<c:set var="statusSel"  value="${StudentListFormC.map.status2}"/>
	  			   		<%@ include file="/pages/include/StatusSelect2.jsp" %>
	  			   	</td>
	  			</tr>
	  		</table>
	  	</td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Query' />" >');</script>
	
	<c:if test="${StudentInfoListC != null}" >
	<tr><td height="10"></td></tr>
	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
		 		<tr>
		 			<td align="center">
	      				<display:table name="${StudentInfoListC}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
					        <c:if test="${empty StudentInfoListC}" >
					        	<%@ include file="../include/NoBanner.jsp" %>
					        </c:if>
	        				<display:column title="<script>generateTriggerAll(${fn:length(StudentInfoListC)}, 'StudentInfo');</script>" class="center" >
	          					<script>generateCheckbox("${row.oid}", "StudentInfo");</script>
	          				</display:column>
	        				<display:column titleKey="Student.Class" property="departClass2" sortable="true" />  
	        				<display:column titleKey="Student.Name"	property="studentName" sortable="true" class="center" />
	        				<display:column titleKey="Student.No" property="studentNo" sortable="true" class="center" />
	        				<display:column titleKey="Student.Sex" property="sex2" sortable="true" class="center" />
	        				<display:column title="畢業學校" property="gradSchlName"	sortable="true" />
	        				<display:column title="畢業科系" property="gradDept"	sortable="true" />
	        				<display:column title="入學身份" property="identBasicName" sortable="true" class="center" />
	        				<display:column titleKey="Student.Status" 	property="occurStatus2"	sortable="true" class="center" />
	      				</display:table>
	      			</td>
	      		</tr>
	    	</table>
	    </td>
	</tr>
	<tr height="40">
		<td align="center">
			<table width="99%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    								<html:link page="/DeptAssistant/StudentList.do">
    								<img src="images/vcard.png" border="0"> 學生清單</html:link>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>

		</td>

	</tr>
    <script>
    	generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Details'/>" onclick="return checkSelectForView(\'StudentInfo\');"');
    </script>	      	
	</c:if>
</html:form>
</table>