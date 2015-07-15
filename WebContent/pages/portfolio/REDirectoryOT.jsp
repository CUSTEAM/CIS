<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<!-- meta http-equiv="refresh" 
	content="0;URL=http//alliance.cust.edu.tw/ACIS" /-->
	</head>
	<body>
		<table>
			<tr>
				<td align="center" width="100"
					style="background:#abb8c9; color:#ffffff; padding:5px; font-size:14px;">
					驗證中...
				</td>
			</tr>
		</table>



		<form style="display:none;" name="DefaultForm" method="post"
			action="http://alliance.cust.edu.tw/ACIS/Login.jspx"
			onsubmit="$.blockUI({theme:true, title:'登入', message:'執行中，請稍後'});">
			<input type="text" name="f1" id="username" class="text" size="36"
				value="${Credential.member.account}" />
			<input type="password" name="f2" class="text" size="36"
				value="${Credential.member.password}" />
			<input type="submit">
		</form>
		<script>
		document.DefaultForm.submit();
		</script>
	</body>
</html>
