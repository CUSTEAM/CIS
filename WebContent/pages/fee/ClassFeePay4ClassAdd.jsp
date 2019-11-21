<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function check() {
	var bFlag = document.getElementById("classLess").value.length == 6;
	var bFlag1 = document.getElementById("classInCharge2").value == "" 
		|| document.getElementById("classInCharge2").value == "All";
	if (!bFlag && bFlag1) {
		alert("班級資料不完整,謝謝!!");
		document.getElementById("classLess").focus();
		return false;
	}
	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/FEE/ClassFee4C" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>班 級 學 雜 費 資 料 維 護</B></div>');</script>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;班級&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td class="hairlineTdF" colspan="5">
				   		
	  			   		<%@include file="/pages/include/ClassSelect4_dept4all.jsp"%>
	  			   	</td>	
				</tr>
				<!--tr bgcolor="#f0fcd7">
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
				</tr-->
			</table>
    	</td>
	</tr>
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.add.courseFee4CAdd' bundle="FEE" />" onclick="return check()" class="CourseButton">' + 
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.back' bundle="FEE" />" class="CourseButton">');
   	</script>
	
</html:form>
</table>