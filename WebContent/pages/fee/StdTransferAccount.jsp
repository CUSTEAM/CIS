<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
function check4Query() {
	var doc = document.forms[0];
	if ("All" == doc.campusInCharge2.value || "All" == doc.schoolInCharge2.value) {
		alert("開課班級選擇範圍過大");
		doc.deptInCharge2.focus();
		return false;
	}
	return true;
}

function check4Del() {
	var iCount = getCookie("dipostInfoCount");
	if (iCount == 0 || iCount == null) {
		alert("請勾選至少一項資料進行刪除,謝謝!!");
		return false;
	} else {
		if(confirm("確定刪除[" + iCount + "]項資料?"))
			return true;
		else
			return false;	
	}
	return true;
}

function check4Update() {
	var iCount = getCookie("dipostInfoCount");
	if (iCount == 0) {
		alert("請勾選一項資料進行修改,謝謝!!");
		return false;
	} else if (iCount > 1) {
		alert("只可以勾選一項資料進行修改,謝謝!!");
		return false;
	}
	return true;
}
</script>

<html:form action="/FEE/StdTransferAccount" method="post" onsubmit="init('執行中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>學 生 轉 帳 帳 號 資 料 維 護</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td>
						<html:select property="schoolYear">
							<html:options property="years" labelProperty="years"/>
						</html:select>&nbsp;&nbsp;學年度&nbsp;&nbsp;
						<html:select property="schoolTerm">
							<html:option value="1">第一學期</html:option>
							<html:option value="2">第二學期</html:option>
						</html:select>&nbsp;&nbsp;種類&nbsp;&nbsp;<html:select property="kind">
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
                     		<html:option value="12">生活助學金</html:option>
						</html:select>&nbsp;&nbsp;
						<bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${StdTransferAccountForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${StdTransferAccountForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel" value="${StdTransferAccountForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel" value="${StdTransferAccountForm.map.classInCharge2}"/>
	  			   		<c:set var="classLess" value="${StdTransferAccountForm.map.classLess}"/>
	  			   		<%@include file="/pages/include/ClassSelect5.jsp"%>
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
						<html:submit styleId="previewB" property="method" styleClass="CourseButton" onclick="return check4Query()"><bean:message key="fee.class.search" bundle="FEE"/></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>	
	
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${diposts}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="<script>generateTriggerAll(${fn:length(diposts)}, 'dipostInfo');</script>" class="center" >
		        	<script>generateCheckbox("${row.oid}", "dipostInfo");</script>
		        </display:column>
				<display:column title="學號" property="studentNo" sortable="false" class="center" />
				<display:column title="姓名" property="studentName" sortable="false" class="center" />
				<display:column title="班級名稱" property="departClass" sortable="true" class="center" />
				<display:column title="郵局局號" property="officeNo" sortable="true" class="center" />
				<display:column title="轉帳帳號" property="acctNo" sortable="true" class="center" />
				<display:column title="金額" sortable="false" class="center">
					<c:if test="${row.money != null}">
						<fmt:formatNumber value="${row.money}"/>
					</c:if>
				</display:column>
				<display:column title="種類" property="kindName" sortable="true" class="center" />
				<display:column title="最後更新時間" sortable="false" class="center">
					<c:if test="${row.lastModified != null}">
						<fmt:formatDate pattern="yyyy/MM/dd hh:mm" value="${row.lastModified}"/>
					</c:if>
				</display:column>
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
