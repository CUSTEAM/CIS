<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Tutor/BonusPenaltySearch" method="post" onsubmit="init('查詢進行中, 請稍後')">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.tutorBonusPenaltySearch.banner" bundle="TCH"/></B></div>');
	</script>	
	<c:if test="${stdList != null}">
    <tr><td height="10"></td></tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
		      			<display:table name="${sessionScope.stdList}" export="false" id="row" sort="list" excludedParams="*" class="list">
		        			<c:if test="${empty stdList}" >
		        				<%@ include file="../include/NoBanner.jsp" %>
		        			</c:if>		
		        			<display:column title="班級" property="departClass" sortable="true" class="center" />	        			
					        <display:column titleKey="Student.No" property="studentNo" sortable="true" class="center" />
					        <display:column titleKey="Student.Name" property="studentName" sortable="true" class="center" />
					        <display:column titleKey="Student.Detail" sortable="false" class="center">
					        	<html:link page="/Teacher/Tutor/BonusPenaltySearch.do?method=readDetail" paramName="row" paramId="no" paramProperty="studentNo">
									[<font color="blue">曠缺明細</font>]
								</html:link>&nbsp;
								<html:link page="/Teacher/Tutor/BonusPenaltySearch.do?method=readSubject" paramName="row" paramId="no" paramProperty="studentNo">
									[<font color="blue">扣考查詢</font>]
								</html:link>
					        </display:column>
					        <display:column titleKey="Student.Cutclass" property="cutClass" sortable="true" class="center" />
					        <display:column titleKey="Student.Sick" property="sick" sortable="true" class="center" />
					        <display:column titleKey="Student.Reason" property="reason" sortable="true" class="center" />
					        <display:column titleKey="Student.Business" property="business" sortable="true" class="center" />
					        <display:column titleKey="Student.Funeral" property="funeral" sortable="true" class="center" />
					        <display:column titleKey="Student.Late" property="late" sortable="true" class="center" />
					        <display:column titleKey="Student.Early" property="early" sortable="true" class="center" />
					     </display:table>
					</td>
				</tr>
		    </table>
		</td>
	</tr>       		      	
	</c:if>
</html:form>
</table>