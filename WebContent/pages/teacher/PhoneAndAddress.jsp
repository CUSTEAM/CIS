<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/PhoneAndAddress" method="post">
    <script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.ModifyPhoneAndAdress" bundle="TCH"/></B></div>');</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
       	 		<tr><td colspan="3"><font color="red">如需修改資料,請與人事室(分機112)聯繫,謝謝</font></td></tr>
       	 		<tr>
 		 			<td colspan="2">姓名&nbsp;
		       			<input type="text" name="ename" readonly disabled size="20" value="${member.name}">
		       		</td>
		       	</tr>
		       	<tr>
 		 			<td colspan="2">身分證字號&nbsp;
		       			<input type="text" name="ename" readonly disabled size="20" value="${member.idno}">
		       		</td>
		       	</tr>
		       	<tr>
 		 			<td colspan="2">出生年月日&nbsp;
		       			<input type="text" name="ename" readonly disabled size="20" value="${member.birthDate}">
		       		</td>
		       	</tr>
 		 		<tr>
 		 			<td colspan="2">英文姓名&nbsp;
		       			<input type="text" name="ename" readonly disabled size="20" value="${PhoneAndAddress.ename}">
		       		</td>
		       	</tr>
		 		<tr>
		 			<td colspan="2">電郵信箱&nbsp;
		 	   			<INPUT type="text" name="email" readonly disabled size="40" value="${PhoneAndAddress.email}">
		 	   		</td>
		 	   	</tr>
		 		<tr>
		 			<td>住宅電話&nbsp;
		 	   			<INPUT type="text" name="telephone" readonly disabled size="12" value="${PhoneAndAddress.telephone}">
		 	   		</td>
		 	   	</tr>		 	   		 	 
		 		<tr>
		 			<td>手機號碼&nbsp;
		 	   			<INPUT type="text" name="cellPhone" readonly disabled size="12" value="${PhoneAndAddress.cellPhone}">
		 	   		</td>
		 	   	</tr>
		 		<tr>
		 			<td colspan="3">現居地址&nbsp;
		 	   			<INPUT type="text" name="czip"  size="2"  value="${PhoneAndAddress.czip}" readonly disabled>
		 	   			<INPUT type="text" name="caddr" size="60" value="${PhoneAndAddress.caddr}" readonly disabled>
		 	   		</td>
		 	   	</tr>
		 		<tr>
		 			<td colspan="3">戶籍地址&nbsp;
		 	   			<INPUT type="text" name="pzip"  size="2"  value="${PhoneAndAddress.pzip}" readonly disabled>
		 	   			<INPUT type="text" name="paddr" size="60" value="${PhoneAndAddress.paddr}" readonly disabled>
		 	   		</td>
		 	   	</tr>
	   		</table>
	   	</td>
	</tr>
	<!--script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="CourseButton">&nbsp;&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">');</script-->
</html:form>
</table>