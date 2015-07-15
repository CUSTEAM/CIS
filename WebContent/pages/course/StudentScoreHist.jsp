<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/ScoreHistDownload" method="post" focus="no" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>學 生 歷 年 成 績 下 載</B></div>');</script>
	<tr>
		<td align="center">
			<table cellspacing="2" class="empty-border">
				<tr>
					<td>
						學號：&nbsp;
						<html:text property="no" size="8" maxlength="10" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						身分證字號：&nbsp;
						<html:text property="idno" size="8" maxlength="10" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="seldInfo"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<script>
		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query' />" class="CourseButton">');
	</script>
	
	<c:if test="${not empty seldList}">
	<script>generateTableBanner('<div class="gray_15"><B>查 詢 結 果</B></div>');</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${seldList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="學號" property="no" sortable="true" class="center" />
				<display:column title="姓名" property="name" sortable="true" class="center" />
				<display:column title="性別" property="sex" sortable="true" class="center" />
				<display:column title="班級" property="class" sortable="true" class="center" />
				<display:column title="下載歷年成績" sortable="true" class="center" href="/CIS/Course/StudentScoreHistory.do" paramId="no" paramProperty="no">
					<img src="images/vcard.png" border="0">
				</display:column>
			</display:table>
    		</td>
	</tr>
	</c:if>
	
</html:form>
</table>