<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
</script>

<html:form action="/Registration/ImageBrowser" method="post" onsubmit="init('產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>照片預覽</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${ImageBrowserForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${ImageBrowserForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel" value="${ImageBrowserForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel" value="${ImageBrowserForm.map.classInCharge2}"/>
	  			   		<c:set var="classLess" value="${ImageBrowserForm.map.classLess}"/>
	  			   		<%@include file="/pages/include/ClassSelect5.jsp"%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" class="fullColorTable">
			<table align="center">
				<tr>
					<td>
						<html:submit styleId="previewB" property="method" styleClass="CourseButton"><bean:message key="Preview" /></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	
	<c:if test="${not empty studentList}">
	<script>generateTableBanner('<div class="gray_15"><B>學 生 清 單 與 照 片 預 覽</B></div>');</script>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${studentList}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
	        				<%@ include file="../include/NoBanner.jsp" %>
							<display:column title="學號" sortable="false" class="center">
								<font size="+2"><b>${row.studentNo}</b></font>
							</display:column>
					        <display:column title="姓名" sortable="false" class="center">
					        	<font size="+2"><b>${row.studentName}</b></font>
					        </display:column>
					        <display:column title="照片預覽"sortable="false" class="center">
					        	<img src="/CIS/SM?no=${row.studentNo}" border="0" width="120" height="170" />
					        </display:column>
      					</display:table>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>
	</c:if>
</table>	
</html:form>