<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
function check4Del() {
	var iCount = getCookie("FeeKindInfoCount");
	if (iCount == 0 || iCount == null) {
		alert("請勾選至少一項收費進行刪除,謝謝!!");
		return false;
	} else {
		if(confirm("確定刪除[" + iCount + "]項收費?"))
			return true;
		else
			return false;	
	}
	return true;
}

function check4Update() {
	var iCount = getCookie("FeeKindInfoCount");
	if (iCount == 0) {
		alert("請勾選一項收費進行修改,謝謝!!");
		return false;
	} else if (iCount > 1) {
		alert("只可以勾選一項收費進行修改,謝謝!!");
		return false;
	}
	return true;
}
</script>

<html:form action="/FEE/ClassFee4C" method="post" onsubmit="init('執行中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>班 級 學 雜 費 資 料 維 護</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<%@include file="/pages/include/ClassSelect4_dept4all.jsp"%>
	  			   		<table width="100%" id="alert" style="display:none">
							<tr>
								<td><br>
									<div class="modulecontainer filled nomessages">
										<div class="first">
											<span class="first"></span>
											<span class="last"></span>
									</div>
									<div>
										<div>
											<table width="100%">
												<tr>
													<td id="helpMsg">
														&nbsp;&nbsp;<img src="images/24-book-green-message.png">
													</td>
													<td align="left"></td>
													<td width="150">
														<a href="javascript:void(0)" onclick="if(document.getElementById('alert').style.display='none'){document.getElementById('alert').style.display='none';}">
														<img src="images/16-tag-check.png" border="0"><font size="1">隱藏說明</font></a>
													</td>
												</tr>
											</table>
										</div>
									</div>
									<div class="last">
										<span class="first"></span>
										<span class="last"></span>
									</div><br>
								</td>
							</tr>
						</table>
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
						<html:submit styleId="previewB" property="method" styleClass="CourseButton"><bean:message key="fee.add.courseFee4C" bundle="FEE"/></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>	
	
	<c:if test="${not empty feePays}">
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${feePays}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="班級代碼" property="departClass" sortable="true" class="center" />
				<display:column title="班級名稱" property="departClassName" sortable="true" class="center" />
				<display:column title="" sortable="false" class="center">
					<html:link page="/FEE/ClassFee4C.do?method=classFeePayList" paramName="row" paramId="dc" paramProperty="departClass">
						<font color="blue">[編輯]</font>
					</html:link>
				</display:column>
			</display:table>
    	</td>
	</tr>
	</c:if>
	
	<c:if test="${not empty feeKind1}">
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${feeKind1}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="" class="center" >
					<script>generateCheckbox("${row.oid}", "FeeKindInfo");</script>
				</display:column>
				<display:column title="班級名稱" property="departClassName" sortable="true" class="center" />
				<display:column title="收費種類" property="kindName" sortable="true" class="center" />
				<display:column title="收費名稱" property="feeCodeName" sortable="true" class="center" />
				<display:column title="收費金額" property="money" sortable="true" class="center" />
				<display:footer>
					<tr>
						<td>總學雜費: <fmt:formatNumber value="${total1}" /> 元</td>
					</tr>
				</display:footer>
			</display:table>
    	</td>
	</tr>
	</c:if>
	
	<c:if test="${not empty feeKind2}">
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${feeKind2}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="" class="center" >
					<script>generateCheckbox("${row.oid}", "FeeKindInfo");</script>
				</display:column>
				<display:column title="班級名稱" property="departClassName" sortable="true" class="center" />
				<display:column title="收費種類" property="kindName" sortable="true" class="center" />
				<display:column title="收費名稱" property="feeCodeName" sortable="true" class="center" />
				<display:column title="收費金額" property="money" sortable="true" class="center" />
				<display:footer>
					<tr>
						<td>總代辦費: <fmt:formatNumber value="${total2}" /> 元</td>
					</tr>	
				</display:footer>
			</display:table>
    	</td>
	</tr>
	</c:if>
	
	<c:if test="${not empty feeKind1 || not empty feeKind2}">
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.add.choose' bundle="FEE" />" class="CourseButton">' + 
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.update.choose' bundle="FEE" />" onclick="return check4Update()" class="CourseButton">' +
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.delete' bundle="FEE" />" onclick="return check4Del()" class="CourseButton">');
   	</script>
   	</c:if>
	
</html:form>	
</table>
