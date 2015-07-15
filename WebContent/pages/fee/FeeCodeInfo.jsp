<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function check4Del() {
	var iCount = getCookie("FeeCodeInfoCount");
	if (iCount == 0) {
		alert("請勾選至少一項代碼進行刪除,謝謝!!");
		return false;
	} else {
		if(confirm("確定刪除[" + iCount + "]項代碼?"))
			return true;
		else
			return false;	
	}
	return true;
}

function check4Update() {
	var iCount = getCookie("FeeCodeInfoCount");
	if (iCount == 0) {
		alert("請勾選一項代碼進行修改,謝謝!!");
		return false;
	} else if (iCount > 1) {
		alert("只可以勾選一項代碼進行修改,謝謝!!");
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
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${feeCodes}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="" class="center" >
					<script>generateCheckbox("${row.oid}", "FeeCodeInfo");</script>
				</display:column>
				<display:column title="代碼" property="no" sortable="true" class="center" />
				<display:column title="收費名稱" property="name" sortable="true" class="center" />
			</display:table>
    	</td>
	</tr>
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.add.choose' bundle="FEE" />" class="CourseButton">' + 
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.update.choose' bundle="FEE" />" onclick="return check4Update()" class="CourseButton">' +
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.delete' bundle="FEE" />" onclick="return check4Del()" class="CourseButton">');
   	</script>
	
</html:form>
</table>