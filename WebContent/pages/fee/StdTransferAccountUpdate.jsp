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
	
	iCount = document.getElementById("officeNo").value;
	if (iCount == '') {
		alert("郵局局號不可為空白,謝謝!!");
		document.getElementById("officeNo").focus();
		return false;
	}
	
	iCount = document.getElementById("accountNo").value;
	if (iCount == '') {
		alert("轉帳帳號不可為空白,謝謝!!");
		document.getElementById("accountNo").focus();
		return false;
	}
	
	iCount = document.getElementById("money").value;
	if (iCount == '') {
		alert("轉帳金額不可為空白,謝謝!!");
		document.getElementById("money").focus();
		return false;
	}
	
	iCount = document.getElementById("type").value;
	if (iCount == '') {
		alert("種類不可為空白,謝謝!!");
		document.getElementById("type").focus();
		return false;
	}
	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/FEE/StdTransferAccount" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>學 生 轉 帳 帳 號 資 料 維 護</B></div>');</script>
	<html:hidden property="dipostOid" value="${dipost4U.oid}"/>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;學號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="studentNo" styleId="studentNo" size="12" maxlength="10" value="${dipost4U.studentNo}" readonly="true" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;郵局局號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="officeNo" styleId="officeNo" size="12" maxlength="7" value="${dipost4U.officeNo}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;轉帳帳號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="accountNo" styleId="accountNo" size="12" maxlength="7" value="${dipost4U.acctNo}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;轉帳金額&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="money" styleId="money" size="8" maxlength="7" value="${dipost4U.money}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;種類&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF" colspan="3">
                     	<html:select property="type" size="1" value="${dipost4U.kind}">
                     		<html:option value=""></html:option>
                     		<html:option value="1">助學貸款</html:option>
                     		<html:option value="2">學雜費</html:option>
                     		<html:option value="3">工讀費</html:option>
                     		<html:option value="4">退費</html:option>
                     		<html:option value="5">其他</html:option>
                     		<html:option value="6">獎學金</html:option>
                     		<html:option value="7">網路選課退費</html:option>
                     		<html:option value="8">住宿生保證金退費</html:option>
                     		<html:option value="9">新生獎學金發放</html:option>
                     		<html:option value="10">原住民獎學金</html:option>
                     		<html:option value="11">學產助學金</html:option>
                     	</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
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