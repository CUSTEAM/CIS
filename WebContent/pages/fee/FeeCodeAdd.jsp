<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function check() {
	var iCount = document.getElementById("feeNo").value;
	if (iCount == '') {
		alert("收費代碼不可為空白,謝謝!!");
		document.getElementById("feeNo").focus();
		return false;
	}	
	
	iCount = document.getElementById("feeName").value;
	if (iCount == '') {
		alert("收費名稱不可為空白,謝謝!!");
		document.getElementById("feeName").focus();
		return false;
	}
	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/FEE/FeeCode" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>收 費 代 碼 表 維 護</B></div>');</script>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;代碼&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="feeNo" styleId="feeNo" size="1" maxlength="3"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;收費名稱&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="feeName" styleId="feeName" size="30" maxlength="20"/>&nbsp;&nbsp;&nbsp;&nbsp;
                     </td>
				</tr>
			</table>
    	</td>
	</tr>
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.add.sure' bundle="FEE" />" onclick="return check()" class="CourseButton">' + 
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.back' bundle="FEE" />" class="CourseButton">');
   	</script>
	
</html:form>
</table>