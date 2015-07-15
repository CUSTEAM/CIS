<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/tooltips.js" %>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Secretary/SecretaryCalendar" method="post">
	<script>generateTableBanner('<div class="gray_15"><B>秘 書 室 行 事 曆</B></div>');</script>
	<tr align="center">
		<td>
			<iframe src="http://www.google.com/calendar/embed?showTitle=0&amp;showTz=0&amp;height=600&amp;wkst=2&amp;bgcolor=%23FFFFFF&amp;src=cc%40www.cust.edu.tw&amp;color=%232952A3&amp;src=p%23weather%40group.v.calendar.google.com&amp;color=%23A32929&amp;ctz=Asia%2FTaipei" style=" border-width:0 " width="800" height="600" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>	
</html:form>
</table>