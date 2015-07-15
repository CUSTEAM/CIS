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
			<iframe src="http://www.google.com/calendar/embed?showTitle=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;hl=zh_TW&amp;bgcolor=%23FFFFFF&amp;src=2ifduoc97dj6fn423g0t2r1kvg%40group.calendar.google.com&amp;color=%232952A3&amp;ctz=Asia%2FTaipei" style=" border-width:0 " width="800" height="600" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>	
</html:form>
</table>