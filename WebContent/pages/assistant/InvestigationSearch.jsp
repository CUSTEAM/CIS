<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/DeptAssistant/InvestigationSearch" method="post" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B>應 屆 畢 業 生 出 路 調 查</B></div>');
	</script>
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${invesList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="班級代碼" property="no" sortable="true" class="center" />
        					<display:column title="班級名稱" property="name" sortable="true" class="center" />
        					<display:column title="出路下載" sortable="false" class="center" href="/CIS/Teacher/Tutor/GraduateInvestigationList.do" paramId="no" paramProperty="no">
        						<img src='images/ico_file_excel1.png' border='0' alt="應屆畢業班出路下載">
        					</display:column>    					
      					</display:table>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>    
</html:form>
</table>