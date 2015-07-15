<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
history.go(1);
function check() {
	if (document.getElementById("email").value == "") {
		alert("請輸入您的Email");
		document.getElementById("email").focus();
		return false;
	}
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/PhoneAndAddress" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<script>generateTableBanner('<div class="gray_15"><B>個 人 聯 絡 資 料</B></div>');</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
       			<tr>
       				<td align="center" colspan="2">
       					<font size="+1" color="red">如需修改各項欄位資料，請親至教務單位進行修改，謝謝。</font>
       				</td>
       			</tr>
       			<tr>
 		 			<td width="600">中文姓名&nbsp;
		 	   			<input type="text" name="studentNo" size="8" value="${sessionScope.stdInfo.studentNo}" readonly disabled>
		 	   			<input type="text" name="studentName" size="6" value="${sessionScope.stdInfo.studentName}" readonly disabled>
		 	   		</td>
 		 		</tr>
 		 		<tr>
 		 			<td width="600">英文姓名&nbsp;
		       			<input type="text" name="ename" size="20" value="${sessionScope.stdInfo.studentEname}" readonly disabled>
		       		</td>
		       	    <td rowspan="6">
		       	    	<img src="/CIS/SM?no=${sessionScope.stdInfo.studentNo}" border="0" width="120" height="170" /><br/>
		       	    	<!--c:if test="${isNewStudent == 'yes'}"--><!--html:file property="image" /--><!--/c:if-->
		       	    </td>
 		 		</tr>
		 		<tr>
		 			<td>電郵信箱&nbsp;
		 	   			<input type="text" name="email" size="50" value="${sessionScope.stdInfo.email}">
		 	   		</td>
		 		</tr>
		 		<tr>
		 			<td>住宅電話&nbsp;
		 	   			<input type="text" name="telephone" size="12" value="${sessionScope.stdInfo.telephone}" readonly disabled>
		 	   		</td>
		 	   	</tr>
		 		<tr>
		 			<td>手機號碼&nbsp;
		 	   			<input type="text" name="cellPhone" size="12" value="${sessionScope.stdInfo.cellPhone}" readonly disabled>
		 	   		</td>
		 	   	</tr>
		 		<tr>
		 			<td width="600">現居地址&nbsp;
		 	   			<input type="text" name="czip" size="2" value="${sessionScope.stdInfo.currPost}" readonly disabled>
		 	   			<input type="text" name="caddr" size="60" value="${sessionScope.stdInfo.currAddr}" readonly disabled>
		 	   		</td>
		 	   	</tr>
		 		<tr>
		 			<td width="600">戶籍地址&nbsp;
		 	   			<input type="text" name="pzip" size="2" value="${sessionScope.stdInfo.permPost}" readonly disabled>
		 	   			<input type="text" name="paddr" size="60" value="${sessionScope.stdInfo.permAddr}" readonly disabled>
		 	   		</td>
		 	   	</tr>
	   		</table>
   	  </td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" onclick="return check()" class="CourseButton">&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">');</script>
</html:form>
</table>