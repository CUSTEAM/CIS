<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/StudentLoanInput" method="post" onsubmit="init('系統處理中...')">
	<script>generateTableBanner('<div class="gray_15"><B>學 生 就 學 貸 款</B></div>');</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
       			<tr>
       				<td>&nbsp;</td>
       				<td>身分證字號</td>
       				<td>姓名</td>
       			</tr>
       			<tr>
       				<td>配偶</td>
       				<td>&nbsp;</td>
       				<td>&nbsp;</td>
       			</tr>
       			<tr>
       				<td>父親</td>
       				<td>&nbsp;</td>
       				<td>&nbsp;</td>
       			</tr>
       			<tr>
       				<td>母親</td>
       				<td>&nbsp;</td>
       				<td>&nbsp;</td>
       			</tr>
       			
	   		</table>
   	  	</td>
	</tr>
	<c:if test="${VALID == '0'}">
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="CourseButton">&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">');</script>
    </c:if> 						  
</html:form>
</table>