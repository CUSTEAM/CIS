<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>

<script type="text/javascript">

</script>

<script type="text/javascript" language="JavaScript1.2">
history.go(1);
var testresults
function checkEmail() {
	var str = document.forms[0].email.value;
	if (str !== '') {
		var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		if (filter.test(str))
			return true;
		else {
			alert("電子郵件格式不正確,請重新輸入!");
			document.forms[0].email.focus();
			return false;
		}
	}
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/OpinionSuggestion" method="post" onsubmit="init('Email寄送處理中...')">
<script>generateTableBanner('<div class="gray_15"><B>學 生 意 見 反 映</B></div>');</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
 		 		<tr>
 		 			<td width="80" align="right">學生姓名：</td>
					<td>
		       			<html:text property="name" size="10" value="${studentInfo.studentName}" readonly="true" />&nbsp;&nbsp;&nbsp;&nbsp;
	       		 		電子信箱：
		 	   			<html:text property="email" size="40" value="${studentInfo.email}" />&nbsp;
		 	   			<font color="red">(未輸入則以學校電子信箱替代)</font>
		 	   		</td>
		 	   	</tr>
		 		<tr>
		 			<td align="right">反映主旨：</td>
					<td>
		 	   			<html:text property="topic" size="60" />
		 	   		</td>
		 	   	</tr>
		 	   	<!-- 
		 	   	<tr>
		 			<td align="right">反映日期：</td>
					<td>
						<html:text property="date" size="8" maxlength="8" readonly="true" />&nbsp;
		 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      					align="top" style="cursor:hand" alt="點選此處選擇日期"
	  	  					onclick="javascript:if(!date.disabled)popCalFrame.fPopCalendar('date','date',event);">
		 	   		</td>
		 	   	</tr>
		 	   	 -->
		 	   	<tr>
		 			<td align="right">反映對象：</td>
					<td>
		 	   			<html:select property="whoOid" size="1">
    						<html:options property="whoOidData" labelProperty="who" />	    						
    					</html:select>
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td align="right">反映地點：</td>
					<td>
		 	   			<html:text property="place" size="20" />
		 	   		</td>
		 	   	</tr>	 		
		 		<tr>
		 			<td align="right" valign="middle">反映內容：</td>
					<td>
		 	   			<html:textarea property="suggestion" rows="5" cols="40" />
		 	   		</td>
		 	   	</tr>
	   		</table>
	   	</td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Send'/>" onclick="return checkEmail();" class="CourseButton">&nbsp;&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">');</script>
</html:form>
</table>
