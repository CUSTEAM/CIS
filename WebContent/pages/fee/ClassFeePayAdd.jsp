<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function check() {
	var iCount = document.getElementById("money").value;
	if (iCount == '') {
		alert("收費金額不可為空白,謝謝!!");
		document.getElementById("money").focus();
		return false;
	}	
	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/FEE/ClassFee" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>班 級 代 辦 費 資 料 維 護</B></div>');</script>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;種類&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:select property="kind" size="1" disabled="true">
	    					<html:option value="1">學雜費</html:option>
	    					<html:option value="2">代辦費</html:option>		
	    				</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;收費名稱&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                    	<html:select property="feeCode" size="1">
	    					<html:options property="feeCodes" labelProperty="feeCodeNames" />					
	    				</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;收費金額&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="money" styleId="money" size="6" maxlength="5"/>&nbsp;&nbsp;&nbsp;&nbsp;
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