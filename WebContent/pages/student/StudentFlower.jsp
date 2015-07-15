<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean1" %>

<script type="text/javascript">

</script>

<script type="text/javascript">
history.go(1);
function submitDate() {
	document.forms[0].submit();
}

function deleteCheck() {
	var iCount;
	iCount = getCookie("stmdFlwWorkListIndexCount");
	if (iCount == 0) {
		alert("請勾選一筆預約資料進行刪除!!");
		return false;
	} else {
		if(confirm("確定刪除[" + iCount + "]筆預約資料?"))
			return true;
		else 
			return false;	
	}
}

function sendCheck() {
	var pointedData = "<bean1:write name="flwWorkListPointedCounts" scope="session" ignore="true"/>";
	var totalCounts = "<bean1:write name="stmdFlwWorkListCounts" scope="session" ignore="true"/>";
	var iCount;
	iCount = getCookie("flwWorkListIndexCount");
	// alert(parseInt(iCount, 10) + parseInt(totalCounts, 10));
	if (iCount == 0) {
		alert("請勾選一筆資料進行預約!!");
		return false;
	} else if ((parseInt(iCount, 10) + parseInt(pointedData, 10)) > 2) {
		alert("一天之內不可預約超過2個班次(包括已預約的班次)!!");
		return false;
	} else if ((parseInt(iCount, 10) + parseInt(totalCounts, 10)) > 8) {
		alert("總共不可預約超過8個班次(包括已預約的班次)!!");
		return false;
	} else {
		if(confirm("確定進行[" + iCount + "]筆預約資料?"))
			return true;
		else 
			return false;	
	}
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0"
	border="0">
	<html:form action="/Student/StudFlower" method="post"
		onsubmit="init('處理中...')">
		<script>generateTableBanner('<div class="gray_15"><B> 花 博 園 藝 志 工 班 次 預 約 </B>');</script>
		<tr>
			<td>
				<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
				<display:table name="${stmdFlwWorkList}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
					<%@ include file="../include/NoBanner.jsp"%>
					<display:column title="" sortable="false" class="center">
						<script>generateCheckbox("${row.map.oid}", "stmdFlwWorkListIndex")</script>
					</display:column>
					<display:column title="學號" sortable="false" class="center">
						<c:out value="${sessionScope.studentInfo.studentNo}" />
					</display:column>
					<display:column title="姓名" sortable="false" class="center">
						<c:out value="${sessionScope.studentInfo.studentName}" />
					</display:column>
					<display:column title="預約日期" sortable="false" class="center">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${row.map.date}" />
					</display:column>
					<display:column title="預約班次" property="classNo" sortable="false" class="center" />
					<display:column title="預約分區" property="areaNo" sortable="false" class="center" />
					<display:footer>
						<tr>
							<td colspan="5">
								目前已選取班次:[${sessionScope.stmdFlwWorkListCounts}]&nbsp;次(最多8次)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:link page="/Student/StudFlowerWorkList.do">下載各班次預約狀況</html:link>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:link page="/Student/StudFlowerWorkListS.do" paramId="st" paramName="studentInfo" paramProperty="studentNo" paramScope="session">下載個人預約狀況</html:link>
							</td>
						</tr>
					</display:footer>
				</display:table>
			</td>
		</tr>
		
		<c:if test="${not sessionScope.stmdFlwIsOver}">
		<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" class="CourseButton" onclick="return deleteCheck();">');</script>
		</c:if>

		<c:if test="${not sessionScope.stmdFlwIsOver}">
		<c:if test="${sessionScope.stmdFlwWorkListCounts lt 8}">
		<tr>
			<td align="left">&nbsp;</td>
		</tr>	
			
		<tr>
			<td align="left">
				預約日期：
				<html:select property="workDate" size="1">
					<html:options property="workDateValues"
						labelProperty="workDateLabels" />
				</html:select>
			</td>
		</tr>

		<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Search'/>" class="CourseButton">');</script>

		<c:if test="${not empty flwWorkList}">
			<tr>
				<td>
					<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
					<display:table name="${flwWorkList}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
						<%@ include file="../include/NoBanner.jsp"%>
						<display:column title="" sortable="false" class="center">
							<script>generateCheckbox("${row.map.data.oid}", "flwWorkListIndex")</script>
						</display:column>
						<display:column title="日期" sortable="false" class="center">
							<fmt:formatDate pattern="yyyy-MM-dd" value="${row.map.data.date}" />
						</display:column>
						<display:column title="班次" sortable="false" class="center">
							<c:out value="${row.map.data.classNo}"/>
						</display:column>
						<display:column title="分區" sortable="false" class="center">
							<c:out value="${row.map.data.areaNo}"/>
						</display:column>
						<display:column title="人數" sortable="false" class="center">
							<c:out value="${row.map.data.counts}"/>
						</display:column>
						<display:column title="已預約人數" property="counts" sortable="false" class="center" />
					</display:table>
				</td>
			</tr>

			<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Send'/>" class="CourseButton" onclick="return sendCheck();">');</script>
		</c:if>
		</c:if>
		</c:if>

	</html:form>
</table>
