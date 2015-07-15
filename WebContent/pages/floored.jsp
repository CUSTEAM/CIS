<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<c:if test="${!empty myOnlineWork}">
<!-- 線上服務 -->
<%@ include file="include/OnlineWorks.jsp" %>
<!-- 線上服務 -->
</c:if>
<table align="right">
	<tr>
		<td>
		<img src="images/chit_s.png"/>
		</td>
		<td>
		<bean:message key="Login.chit" bundle="IND"/>
		</td>
	</tr>
</table>