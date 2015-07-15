<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js" %>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="${sessionScope.actionName}" method="post" focus="classNo">
	<input type="hidden" name="oid" value="" />
	<input type="hidden" name="optId" value="" />
	<script>
		generateTableBanner('<div class="gray_15"><B>學 生 低 修 高 年 級 課 程 確 認</B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">
				<tr>
					<td>學號：&nbsp;
			 			<html:text property="stdNo" size="10" maxlength="8" disabled="true" />
					</td>
					<td>姓名：&nbsp;
			 			<html:text property="stdName" size="10" maxlength="8" disabled="true" />
					</td>
					<td>班級：&nbsp;
			 			<html:text property="stdClassName" size="20" disabled="true" />
					 </td>
				</tr>
				<tr>
					<td>班級代碼：&nbsp;
			 			<html:text property="classNo" size="6" maxlength="6" value="${seldInfoForOnline.departClass}" disabled="true" />&nbsp;&nbsp;
			 		</td>
					<td>班級名稱：&nbsp;
			 			<html:text property="className" size="20" maxlength="20" value="${seldInfoForOnline.departClassName}" disabled="true" />
			 		</td>
					 <td><span id="classInfo"></span></td>
				</tr>
				<tr>
					<td>科目代碼：&nbsp;
			 			<html:text property="csCode" size="5" maxlength="5" value="${seldInfoForOnline.cscode}" disabled="true" />
					 </td>
					 <td>科目名稱：&nbsp;
			 			<html:text property="csName" size="20" maxlength="20" value="${seldInfoForOnline.cscodeName}" disabled="true" />&nbsp;&nbsp;
			 			<html:select property="sterm" disabled="true" onchange="checkTerm(this);">
			 				<html:option value="1">1</html:option>
			 				<html:option value="2">2</html:option>
			 			</html:select>&nbsp;學期
					 
					 </td>
					 <td><span id="courseInfo"></span></td>
				</tr>				
			</table>
		</td>
	</tr>
	<script>
		generateTableBanner(
			'<INPUT type="submit" name="method" id="s1" value="<bean:message key='course.onlineAddRemoveCourse.makeSure1' bundle="COU" />" class="CourseButton">&nbsp;&nbsp;' +
			'<INPUT type="submit" name="method" value="<bean:message key='Cancel' />" class="CourseButton">'
		);
	</script>
</html:form>
</table>