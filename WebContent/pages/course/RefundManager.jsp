<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script>
history.go(1);
</script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="/Course/RefundManager" method="post" onsubmit="init('查詢進行中, 請稍後')">
<!-- 標題start -->
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/money_delete.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;退費管理&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 標題end -->
	<tr>
		<td>
		
		<%@ include file="Refund/search.jsp" %>
		
		</td>
	</tr>
	
	<c:if test="${!empty refunds}">
	<tr>
		<td>
		
		<%@ include file="Refund/list.jsp" %>
		
		</td>
	</tr>	
	</c:if>

</html:form>
</table>