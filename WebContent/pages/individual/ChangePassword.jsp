<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Individual/ChangePassword" method="post">
	<script>generateTableBanner('<div class="gray_15"><B>更&nbsp;&nbsp;&nbsp;&nbsp;改&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;碼</B></div>');</script>	  
		<tr><td align="center">
	       <table cellspacing="5" class="empty-border">
			 <tr><td align="right">現用密碼</td>
  			 	 <td align="left" ><input type="password" name="oldPassword"  size="10" value="${ChangePasswordForm.map.oldPassword}"></td></tr>
			 <tr><td align="right">新密碼</td>
  			 	 <td align="left" ><input type="password" name="newPassword"  size="10" value="${ChangePasswordForm.map.newPassword}"></td></tr>
			 <tr><td align="right">確認新密碼</td>
  			 	 <td align="left" ><input type="password" name="newPassword2" size="10" value="${ChangePasswordForm.map.newPassword2}"></td></tr>
     	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'
     						  + '<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Cancel' />" >');</script>
  </html:form>
</table>
