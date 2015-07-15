<%@ page language="java" contentType="text/html;charset=BIG5"  %>
<script type="text/javascript">
<!--
	function openInformixNewWin() {
		subwin = window.open('${InformixLogin.url}','InformixLogin');
		//subwin.document.forms[0].username = '${InformixLogin.username}';
		//subwin.document.forms[0].password = '${InformixLogin.password}';
	};
//-->

openInformixNewWin();
</script>
<form name="myForm" action="" method="post">
<input type="hidden" name="username" value="${InformixLogin.username}">
<input type="hidden" name="password" value="${InformixLogin.password}">
</form>

