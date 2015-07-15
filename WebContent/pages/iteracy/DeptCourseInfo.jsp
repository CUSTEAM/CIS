<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
function classCheck() {	
	var iCount;
	iCount = getCookie("resultDataCount");
	if (iCount == 0) {
		alert("請勾選一個班級進行檢視!!");
		return false;
	} else if(iCount > 1) {
		alert("請僅勾選一個班級進行檢視!!");
		return false;
	}
	return true;
}
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr height="30">
					<td width="28" align="right"><img src="images/16-manager-st.gif"></td>
					<td align="left">&nbsp;通識選課狀況<!--－<font color="red">${schoolYear}</font>學年度&nbsp;--></td>
				</tr>
			</table>
		</td>
	</tr>	

	<!-- 列表 start -->	
	<c:if test="${not empty dtimeResult}">
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td>
						<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
						<display:table name="${dtimeResult}" pagesize="30" id="row" sort="list" class="list">
							<%@ include file="../include/NoBanner.jsp" %>
	          				<display:column title="班級名稱" property="departClass2" sortable="true" class="center" />
	          				<display:column title="班級代碼" property="departClass" sortable="true" class="center" />
							<display:column title="科目名稱" property="chiName2" sortable="true" class="center" />
							<display:column title="班級代碼" property="cscode" sortable="true" class="center" />
							<display:column title="教師姓名" property="techName" sortable="true" class="center" />
							<display:column title="選別" property="opt2" sortable="true" class="center" />
							<display:column title="學分" property="credit" sortable="true" class="center" />
							<display:column title="時數" property="thour" sortable="true" class="center" />
							<display:column title="已選人數" property="stuSelect" sortable="true" class="center" />
						</display:table>
					</td>
				</tr>
			</table>		
		</td>
	</tr>
	</c:if>
</table>