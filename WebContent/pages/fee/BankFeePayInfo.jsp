<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
function check4Del() {
	var iCount = getCookie("BankFeePayInfoCount");
	if (iCount == 0 || iCount == null) {
		alert("請勾選至少一筆資料進行刪除,謝謝!!");
		return false;
	} else {
		if(confirm("確定刪除[" + iCount + "]筆資料?"))
			return true;
		else
			return false;	
	}
	return true;
}

function check4Update() {
	var iCount = getCookie("BankFeePayInfoCount");
	if (iCount == 0) {
		alert("請勾選一筆資料進行修改,謝謝!!");
		return false;
	} else if (iCount > 1) {
		alert("只可以勾選一筆資料進行修改,謝謝!!");
		return false;
	}
	return true;
}
</script>

<html:form action="/FEE/BankFeePay" method="post" onsubmit="init('執行中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>虛 擬 帳 號  資 料 維 護</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td class="hairlineTdF">&nbsp;&nbsp;虛擬帳號&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<html:text property="accountNo" styleId="accountNo" size="14" maxlength="14"/>
					</td>
					<td class="hairlineTdF">&nbsp;&nbsp;學生學號&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<html:text property="studentNo" styleId="studentNo" size="10" maxlength="10"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" class="fullColorTable">
			<table align="center">
				<tr>
					<td>
						<html:submit styleId="previewB" property="method" styleClass="CourseButton"><bean:message key="fee.class.search" bundle="FEE"/></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${bankFeePays}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="" class="center" >
					<script>generateCheckbox("${row.oid}", "BankFeePayInfo");</script>
				</display:column>
				<display:column title="學年" property="schoolYear" sortable="true" class="center" />
				<display:column title="學期" property="schoolTerm" sortable="true" class="center" />
				<display:column title="虛擬帳號" property="accountNo" sortable="true" class="center" />
				<display:column title="繳費日期" sortable="true" class="center">
					<fmt:formatDate type="date" pattern="yyyy/MM/dd" value="${row.payDate}"/>
				</display:column>
				<display:column title="學號" property="studentNo" sortable="true" class="center" />
				<display:column title="最後更新時間" sortable="true" class="center">
					<fmt:formatDate type="both" pattern="yyyy/MM/dd hh:mm:ss" value="${row.lastModified}"/>
				</display:column>
			</display:table>
    	</td>
	</tr>
	
	<c:if test="${not empty bankFeePays}">
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.update.choose' bundle="FEE" />" onclick="return check4Update()" class="CourseButton">' +
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.delete' bundle="FEE" />" onclick="return check4Del()" class="CourseButton">');
   	</script>
   	</c:if>
	
</html:form>	
</table>
