<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<!-- 標題start -->
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/page_white_edit.gif" />
				</td>
				<td align="left">
				&nbsp;線上文件申請&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
<html:form action="/Student/MyOnlineServices4Reg" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<c:if test="${OnlineService4Reg==null}">
	<tr>
		<td>
		<c:if test="${myServices!=null}">
		<%@ include file="MyOnlineServices4Reg/setTop.jsp" %>
		</c:if>
		<%@ include file="MyOnlineServices4Reg/edit.jsp" %>
		</td>
	</tr>
</c:if>

<c:if test="${OnlineService4Reg!=null}">
	<tr>
		<td>		
		<%@ include file="MyOnlineServices4Reg/confirm.jsp" %>
		</td>
	</tr>	
</c:if>	
</html:form>
</table>