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
<html:form action="/Secretary/Calendar" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>秘 書 室 行 事 曆 管 理</B></div>');</script>
	<script>generateTableBanner('<div class="gray_15"><B>查 詢 範 圍 : ${fromDate} ~ ${toDate}</B></div>');</script>
	
	<tr align="center">
		<td>
			<table class="empty-border">
				<tr>
					<td>
						<html:select property="fromYear" styleId="fromYear">
							<html:options property="fromYears" labelProperty="fromYears" />
						</html:select>&nbsp;&nbsp;年
						<html:select property="fromMonth" styleId="fromMonth">
							<html:option value="1">1</html:option>
   							<html:option value="2">2</html:option>
   							<html:option value="3">3</html:option>
   							<html:option value="4">4</html:option>
   							<html:option value="5">5</html:option>
   							<html:option value="6">6</html:option>
   							<html:option value="7">7</html:option>
   							<html:option value="8">8</html:option>
   							<html:option value="9">9</html:option>
   							<html:option value="10">10</html:option>
   							<html:option value="11">11</html:option>
   							<html:option value="12">12</html:option>
						</html:select>&nbsp;&nbsp;月
						<html:select property="fromDay" styleId="fromDay">
							<html:options property="fromDays" labelProperty="fromDays" />
						</html:select>&nbsp;&nbsp;日 ~ 
						<html:select property="toYear" styleId="toYear">
							<html:options property="toYears" labelProperty="toYears" />
						</html:select>&nbsp;&nbsp;年
						<html:select property="toMonth" styleId="toMonth">
							<html:option value="1">1</html:option>
   							<html:option value="2">2</html:option>
   							<html:option value="3">3</html:option>
   							<html:option value="4">4</html:option>
   							<html:option value="5">5</html:option>
   							<html:option value="6">6</html:option>
   							<html:option value="7">7</html:option>
   							<html:option value="8">8</html:option>
   							<html:option value="9">9</html:option>
   							<html:option value="10">10</html:option>
   							<html:option value="11">11</html:option>
   							<html:option value="12">12</html:option>
						</html:select>&nbsp;&nbsp;月
						<html:select property="toDay" styleId="toDay">
							<html:options property="toDays" labelProperty="toDays" />
						</html:select>&nbsp;&nbsp;日 
						
    				</td>    
    				<td>
						<html:submit property="method" styleClass="CourseButton"><bean:message key='course.onlineAddRemoveCourse.query' bundle="COU" /></html:submit>
					</td>				
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
		
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${calendarEvents}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="" class="center" >
					<script>generateCheckbox("${row.map.oid}", "CalCodeInfo");</script>
				</display:column>
				<display:column title="主持人" property="host" sortable="true" class="center" />
				<display:column title="起始時間" property="startTime" sortable="true" class="center" />
				<display:column title="結束時間" property="endTime" sortable="false" class="center" />
				<display:column title="事項" property="title" sortable="false" class="left" />
				<display:column title="地點" property="location" sortable="false" class="left" />
				<display:column title="承辦單位" property="content" sortable="false" class="left" />					
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