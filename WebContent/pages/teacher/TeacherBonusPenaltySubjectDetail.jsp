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
	<c:if test="${subjectList != null}">
    <tr>
    	<td height="10">
			學號：${studentInfo.studentNo}&nbsp;&nbsp;&nbsp;&nbsp;姓名：${studentInfo.studentName}&nbsp;&nbsp;&nbsp;&nbsp;
			班級：${studentInfo.departClass2}
		</td>
	</tr>
	<tr>
    	<td height="10">
			查詢成績僅供導師輔導參考，以教務單位最後公告為正確成績。<font color="red">*請勿將查詢之成績告知學生，謝謝。</font>
		</td>
	</tr>
	<tr>		
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
		      			<display:table name="${requestScope.subjectList}" export="false" id="row" sort="list" excludedParams="*" class="list">
		        			<c:if test="${empty dilgList}" >
		        				<%@ include file="../include/NoBanner.jsp" %>
		        			</c:if>			        			
					        <display:column title="科目" property="subjectName" sortable="true" class="left" />
					        <display:column title="期中考" property="mid" sortable="true" class="center" />
					        <display:column title="學期成績" property="final" sortable="true" class="center" />
					        <display:column title="每週節數" property="period" sortable="true" class="center" />
					        <display:column title="扣考節數" property="tfLimit" sortable="true" class="center" />
					        <display:column title="缺課節數" property="timeOff" sortable="true" class="center" />
					        <display:column title="備註" sortable="true" class="center">
					        	<c:choose><c:when test="${row.warnning == 'yes'}"><font color="red">*</font></c:when></c:choose>
					        </display:column>					        
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