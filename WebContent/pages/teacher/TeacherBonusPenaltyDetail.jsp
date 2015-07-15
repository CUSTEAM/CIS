<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="${actionName}" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="${displayName}" bundle="TCH"/></B></div>');
	</script>
	<c:if test="${dilgList != null}">
    <tr>
    	<td height="10">
			學號：${studentInfo.studentNo}&nbsp;&nbsp;&nbsp;&nbsp;姓名：${studentInfo.studentName}&nbsp;&nbsp;&nbsp;&nbsp;
			班級：${studentInfo.departClass2}
		</td>
	</tr>
	<tr>		
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
		      			<display:table name="${requestScope.dilgList}" export="false" id="row" sort="list" excludedParams="*" class="list">
		        			<c:if test="${empty dilgList}" >
		        				<%@ include file="../include/NoBanner.jsp" %>
		        			</c:if>	
		        			<display:column title="日期" property="pdate" sortable="true" class="center" />		        			
					        <display:column title="升旗" property="absName0" sortable="true" class="center" />
					        <display:column title="一" property="absName1" sortable="true" class="center" />
					        <display:column title="二" property="absName2" sortable="true" class="center" />
					        <display:column title="三" property="absName3" sortable="true" class="center" />
					        <display:column title="四" property="absName4" sortable="true" class="center" />
					        <display:column title="五" property="absName5" sortable="true" class="center" />
					        <display:column title="六" property="absName6" sortable="true" class="center" />
					        <display:column title="七" property="absName7" sortable="true" class="center" />
					        <display:column title="八" property="absName8" sortable="true" class="center" />
					        <display:column title="九" property="absName9" sortable="true" class="center" />
					        <display:column title="十" property="absName10" sortable="true" class="center" />
					        <display:column title="十一" property="absName11" sortable="true" class="center" />
					        <display:column title="十二" property="absName12" sortable="true" class="center" />
					        <display:column title="十三" property="absName13" sortable="true" class="center" />
					        <display:column title="十四" property="absName14" sortable="true" class="center" />
					        <display:column title="十五" property="absName15" sortable="true" class="center" />
					     </display:table>
					</td>
				</tr>
		    </table>
		</td>
	</tr> 
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Back' />" class="CourseButton">')
   	</script>      		      	
	</c:if>
</html:form>
</table>