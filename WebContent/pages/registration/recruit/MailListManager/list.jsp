<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:forEach items="${mails}" var="m">
<table class="hairLineTable" width="99%">
	<tr>	
		<td class="hairLineTdF" width="100">${m.name}</td>
		<td class="hairLineTdF">${m.mailaddress} - <a href="/CIS/Regstration/Recruit/Config/MailListManager.do?delOid=${m.Oid}">刪除</a></td>
		
	</tr>		
</table>
</c:forEach>