<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="${actionName}" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B>導 生 歷 年 成 績</B></div>');
	</script>
	<c:if test="${scoreHistList != null}">
	<tr>		
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
		      			<display:table name="${requestScope.scoreHistList}" export="false" id="row" sort="list" excludedParams="*" class="list">
		        			<c:if test="${empty scoreHistList}" >
		        				<%@ include file="../include/NoBanner.jsp" %>
		        			</c:if>			        			
					        <display:column title="學年" property="schoolYear" sortable="false" class="center" />
					        <display:column title="學期" property="schoolTerm" sortable="false" class="center" />
					        <display:column title="科目名稱" property="cscodeName" sortable="false" class="center" />
					        <display:column title="選別" property="optName" sortable="false" class="center" />
					        <display:column title="學分數" property="credit" sortable="false" class="center" />
					        <display:column title="學期成績" property="score" sortable="false" class="center" />
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