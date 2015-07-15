<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/tooltips.js" %>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/SchoolCalendar" method="post">
	<script>generateTableBanner('<div class="gray_15"><B>學 校 行 事 曆</B></div>');</script>
	<tr align="center">
		<td>
			<iframe src="http://www.google.com/calendar/embed?showTabs=0&amp;showCalendars=0&amp;height=600&amp;wkst=1&amp;hl=zh_TW&amp;bgcolor=%23FFFFFF&amp;src=r4rtqj0pd5bnvpett4g7sgrvo4%40group.calendar.google.com&amp;color=%23A32929" style=" border-width:0 " width="800" height="600" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>	
</html:form>
</table>