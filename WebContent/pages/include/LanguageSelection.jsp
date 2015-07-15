<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<select onchange="jumpMenu('parent',this,0)">
	<option value="javascript:void(0)"><bean:message key="EngRat.language" bundle="COU"/></option>
	<!-- >option value="javascript:void(0)">Select a language</option-->
	<option value="/CIS/ChangeLanguage.do?locale=en">English</option>
	<option value="/CIS/ChangeLanguage.do?locale=ja">日本語</option>
	<option value="/CIS/ChangeLanguage.do?locale=zh-cn">简体中文</option>	
	<option value="/CIS/ChangeLanguage.do?locale=zh">繁體中文</option>
</select>
<script>
function jumpMenu(targ,selObj,restore){
			eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
			eval(targ+".location.target='_blank'");
			if (restore) selObj.selectedIndex=0;
	}
</script>