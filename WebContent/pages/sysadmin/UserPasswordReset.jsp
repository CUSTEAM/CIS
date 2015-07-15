<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/SysAdmin/UserPasswordReset" method="post" focus="account">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.UserPasswordReset" bundle="ADM"/></B></div>');</script>	  
		<tr><td align="center">
	       <table cellspacing="5" class="empty-border">
			 <tr><td>帳號&nbsp;
			 		<input type="text" name="account" size="12" 
			 			<c:if test="${not empty(UserPasswordReset)}">
			 				value="${UserPasswordReset.account}"
			 			</c:if> ></td></tr>
     	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Query'/>" >');</script>
   <c:if test="${not empty(UserPasswordReset)}">
      <tr><td><table width="100%" cellspacing="5" class="empty-border">
		<tr><td height="20" valign="center" class="blue_13">
		  <c:out value="${UserPasswordReset.name}"/>&nbsp;
		  <c:out value="${UserPasswordReset.unit2}"/>&nbsp;</td></tr>
		<tr><td height="20" valign="center" class="blue_13">
		     身分證號: <c:out value="${UserPasswordReset.idno}"/>&nbsp;&nbsp;&nbsp;&nbsp;
		     生日: <c:out value="${UserPasswordReset.birthDate}"/></td></tr>
		<tr><td height="20" valign="center" class="blue_13">
		     原有密碼: <c:out value="${UserPasswordReset.password}"/>&nbsp;&nbsp;&nbsp;&nbsp;
		     舊系統密碼: <c:out value="${UserPasswordReset.informixPass}"/></td></tr>
	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='ResetPassword'/>" >&nbsp;&nbsp;&nbsp;&nbsp;'
							  + '<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='ResetInformixPassword'/>" >');</script>
   </c:if>  
  </html:form>
</table>
<script>history.go(1);</script>