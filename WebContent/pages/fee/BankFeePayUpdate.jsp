<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function check() {
	var iCount = document.getElementById("studentNo").value;
	if (iCount == '') {
		alert("學號不可為空白,謝謝!!");
		document.getElementById("studentNo").focus();
		return false;
	}	
	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/FEE/BankFeePay" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>虛 擬 帳 號  資 料 維 護</B></div>');</script>
	<html:hidden property="feePayOid" value="${feePay4U.oid}"/>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;學年學期&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="schoolYear" styleId="schoolYear" size="1" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;
                     	<html:text property="schoolTerm" styleId="schoolTerm" size="1" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;虛擬帳號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                    	<html:text property="accountNo" styleId="accountNo" size="12" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                     <td class="hairlineTdF">&nbsp;&nbsp;繳費日期&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                    	<html:text property="payDate" styleId="payDate" size="7" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;學號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="studentNo" styleId="studentNo" size="10" maxlength="10"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
			</table>
    	</td>
	</tr>
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.update.sure' bundle="FEE" />" onclick="return check()" class="CourseButton">' + 
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.back' bundle="FEE" />" class="CourseButton">');
   	</script>
	
</html:form>
</table>