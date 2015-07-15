<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script>
history.go(1);
</script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="/Course/GroupManager" method="post" onsubmit="init('查詢進行中, 請稍後')">
<!-- 標題start -->
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon_component.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;學程管理&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 標題end -->


<!-- 課程查詢start -->
<c:if test="${aGroup==null}">
	<tr>
		<td>		
		<%@ include file="GroupManager/listGroup.jsp"%>
		</td>
	</tr>
</c:if>
<!-- 課程查詢end -->

<!-- 課程編輯start -->
<c:if test="${aGroup!=null}">
	<tr>
		<td>		
		<%@ include file="GroupManager/editGroup.jsp"%>
		</td>
	</tr>
</c:if>
<!-- 課程編輯end -->

</html:form>
</table>