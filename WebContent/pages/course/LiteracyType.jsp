<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function check4Del() {
	var iCount = getCookie("CalCodeInfoCount");
	if (iCount == 0) {
		alert("請勾選至少一項事項進行刪除,謝謝!!");
		return false;
	} else {
		if(confirm("確定刪除[" + iCount + "]項事項?"))
			return true;
		else
			return false;	
	}
	return true;
}

function check4Update() {
	var iCount = getCookie("CalCodeInfoCount");
	if (iCount == 0) {
		alert("請勾選一項事項進行修改,謝謝!!");
		return false;
	} else if (iCount > 1) {
		alert("只可以勾選一項事項進行修改,謝謝!!");
		return false;
	}
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/LiteracyType" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>通 識 課 程 分 類 管 理</B></div>');</script>
	
	<tr>
		<td>
		
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${cscodes}" export="false" id="row" pagesize="60" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="科目代碼" property="cscode" sortable="false" class="center" />
				<display:column title="科目名稱" property="chiName" sortable="false" class="center" />
				<display:column title="通識分類" sortable="false" class="center">
					<html:select property="types" value="${row.map.literacyType}">
						<html:option value=""></html:option>
						<html:options property="typeCodes" labelProperty="typeNames" />
					</html:select>
				</display:column>
			</display:table>
			
    		</td>
	</tr>
	
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.update.choose' bundle="FEE" />" onclick="return check4Update()" class="CourseButton">');
   	</script>
</html:form>
</table>