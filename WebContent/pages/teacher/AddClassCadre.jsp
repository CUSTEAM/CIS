<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js" %>

<script type="text/javascript">
var oIndex = 0;
function autoStudentCheck(obj, index) {
	var arg = obj.value;
	oIndex = index;
	var path = "<%= request.getContextPath() %>";
	var url = path + "/AjaxLookupTool.do?method=lookupStudentByNo&key="
		+ encodeURIComponent(arg);
	httpRequest("GET", url, true, responseHandler);
}

function responseHandler() {	
	if(request.readyState == READ_STATE_COMPLETE) {
		if(request.status == RESPONSE_STATUS_OK) {
			var responseValue = request.responseText;
			if(responseValue.indexOf("查無學生資料") == -1) {
				var func = new Function("return " + responseValue);
				var obj = func();
				document.getElementById("name" + oIndex).value = obj.name;
				document.getElementById("email" + oIndex).value = obj.email;
				document.getElementById("cellPhone" + oIndex).value = obj.cellPhone;
				document.getElementById("homePhone" + oIndex).value = obj.homePhone;
				document.getElementById("oid" + oIndex).value = obj.oid;
				oIndex++;
				document.getElementById("stdNo" + oIndex).focus();
			} else {
				document.getElementById("email" + oIndex).value = '<bean:message key="Message.ScoreHist.CantFindStudent" />';
				document.getElementById("stdNo" + oIndex).focus();
				//document.getElementById("stdNo" + oIndex).value = "";
				//document.getElementById("stdNo" + oIndex).focus();
			}
		}
	}
}
</script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/ClassCadre" method="post" onsubmit="init('資料處理中, 請稍後')">
	<input type="hidden" name="oid0" />
	<input type="hidden" name="oid1" />
	<input type="hidden" name="oid2" />
	<input type="hidden" name="oid3" />
	<input type="hidden" name="oid4" />
	<input type="hidden" name="oid5" />
	<input type="hidden" name="oid6" />
	<input type="hidden" name="oid7" />
	<input type="hidden" name="oid8" />
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.classCadre.banner" bundle="TCH"/></B></div>');
	</script>
	<tr>
		<td align="center">
       		<table width="100%" cellspacing="5" class="empty-border">
 		 		<tr>
 		 			<td>班級名稱：&nbsp;
		       			<input type="text" name="ename" size="15" value="${classInfo.className}" readonly disabled>
		       		</td>
		       	</tr>
		 		<tr>
		 			<td>導師教師：&nbsp;
		 	   			<input type="text" name="cname" size="10" value="${sessionScope.emplInfo.cname}" readonly disabled>&nbsp;&nbsp;&nbsp;&nbsp;
		 	   			E-mail：&nbsp;<input type="text" name="email" size="30" value="${sessionScope.emplInfo.email}">
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td>住家電話：&nbsp;
		 	   			<input type="text" name="homePhone" size="10" maxlength="15" value="${sessionScope.emplInfo.telephone}">&nbsp;&nbsp;&nbsp;&nbsp;
		 	   			行動電話：&nbsp;<input type="text" name="cellPhone" size="8" maxlength="10" value="${sessionScope.emplInfo.cellPhone}">&nbsp;&nbsp;&nbsp;&nbsp;
		 	   			辦公室位置：&nbsp;
			 			<input type="text" name="roomId" size="5" maxlength="5" value="${sessionScope.emplInfo.location.roomId}">&nbsp;&nbsp;&nbsp;&nbsp;
		 				辦公室分機：&nbsp;
		 	   			<input type="text" name="extension" size="10" maxlength="10" value="${sessionScope.emplInfo.location.extension}">
		 	   		</td>
		 	   	</tr> 				 	   		 	 
		 		<tr>
		 			<td>
		 				<table width="100%" cellspacing="0" border="0" bordercolor="black">
				   			<tr>
				   				<td align="center" height="40">職&nbsp;&nbsp;稱</td>
				   				<td align="center">學&nbsp;&nbsp;號</td>
				   				<td align="center">姓&nbsp;&nbsp;名</td>				   				
				   				<td align="center">E-mail</td>
				   				<td align="center">行&nbsp;&nbsp;動&nbsp;&nbsp;電&nbsp;&nbsp;話</td>
				   				<td align="center">家&nbsp;&nbsp;中&nbsp;&nbsp;電&nbsp;&nbsp;話</td>
				   				<td align="center">備&nbsp;&nbsp;註</td>
				   			</tr>
				   			<c:if test="${empty stdInfo}">
				   			<c:forEach begin="0" end="8" var="index">
				   			<tr>
				   				<td align="center">${roleInfo[index]}</td>
				   				<td align="center"><input type="text" name="stdNo${index}" size="10" maxlength="10" onkeyup="if(this.value.length >= 8) autoStudentCheck(this, '${index}');">
				   				<td align="center"><input type="text" name="name${index}" size="8" readonly maxlength="5"></td>		   				
				   				<td align="center"><input type="text" name="email${index}" size="20" maxlength="30"></td>
				   				<td align="center"><input type="text" name="cellPhone${index}" size="8" maxlength="10"></td>
				   				<td align="center"><input type="text" name="homePhone${index}" size="8" maxlength="15"></td>
				   				<td align="center"><input type="text" name="remark${index}" size="10" maxlength="20"></td>

				   			</tr>
				   			</c:forEach>	
				   			</c:if>	
				   			<c:if test="${not empty stdInfo}">
				   			<c:forEach items="${stdInfo}" var="row" varStatus="status">
				   			<tr>
				   				<td align="center">${roleInfo[status.index]}</td>
				   				<td align="center"><input type="text" name="stdNo${status.index}" size="10" maxlength="10" value="${row.studentNo}" onkeyup="if(this.value.length >= 8) autoStudentCheck(this, '${status.index}');"></td>
				   				<td align="center"><input type="text" name="name${status.index}" size="8" maxlength="5" readonly value="${row.name}"></td>		   				
				   				<td align="center"><input type="text" name="email${status.index}" size="20" maxlength="30" value="${row.email}"></td>
				   				<td align="center"><input type="text" name="cellPhone${status.index}" size="8" maxlength="10" value="${row.cellPhone}"></td>
				   				<td align="center"><input type="text" name="homePhone${status.index}" size="8" maxlength="15" value="${row.homePhone}"></td>
				   				<td align="center"><input type="text" name="remark${status.index}" size="10" maxlength="20" value="${row.remark}"></td>
				   			</tr>
				   			</c:forEach>	
				   			</c:if>		   							   			
				   		</table>
		 			</td>
		 	   	</tr>
	   		</table>	   		
	   	</td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="CourseButton">&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">');</script>
</html:form>
</table>
<script>history.go(1);</script>