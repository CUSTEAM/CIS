<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
// -->
</style>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/Racing/Cleanning.do" method="post" name="cleanForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.Cleanning" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="1" class="empty-border">
      <!--DWLayoutTable-->
     <tr>
        <td height="30"  width="50" align="left" valign="middle" bgcolor="#ffa07a">週次：</td>
        <td>
        	<c:choose>
          		<c:when test="${! empty CleanAddInEdit}">
         		<input name="week_no" type="text" size="2" maxlength="3" value="${CleanAddInEdit.week_no}"/>
          		</c:when>
        		<c:when test="${! empty ClazzList}">
         		<input name="week_no" type="text" size="2" maxlength="3"/>
          		</c:when>
          	</c:choose>
         </td>
      </tr>
	</table>
		</td>		
	</tr>
		<tr><td>
		<table width="100%" cellpadding="2" cellspacing="5">
		<tr>
		<td width="100">班級</td>
		<td width="150">班級名稱</td>
		<td width=50>分數</td>
		<td>&nbsp;</td>
		</tr>
        	<c:choose>
          		<c:when test="${! empty CleanAddInEdit}">
          		<c:set var="rcnt" value="0"/>
          		<c:set var="departClassName" value="${CleanAddInEdit.departClassName}"/>

				<c:forEach items="${CleanAddInEdit.depart_class}" var="departClass">
					<input type="hidden" name="departClassName" value="${departClassName[rcnt]}">
					<input type="hidden" name="depart_class" value="${departClass}">
					<tr>
						<td width="80" bgcolor="#ffa07a">${departClass}</td>
						<td width="150" bgcolor="#add8e6">${departClassName[rcnt]}</td>
						<td width="50">
		          		<input name="score" type="text" size="4" maxlength="4"/>
		          		</td>
						<td>&nbsp;</td>
					</tr>
					<c:set var="rcnt" value="${rcnt + 1}"/>
				</c:forEach>          		
          		</c:when>
          		
        		<c:when test="${! empty ClazzList}">
				<c:forEach items="${ClazzList}" var="clazz">
					<input type="hidden" name="departClassName" value="${clazz.classFullName}">
					<input type="hidden" name="depart_class" value="${clazz.classNo}">
					<tr>
						<td width="80" bgcolor="#ffa07a">${clazz.classNo}</td>
						<td width="150" bgcolor="#add8e6">${clazz.classFullName}</td>
						<td width="50">
		          		<input name="score" type="text" size="4" maxlength="4"/>
		          		</td>
						<td>&nbsp;</td>
					</tr>
				</c:forEach>
          		</c:when>
          	</c:choose>
		</table></td></tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='AddSubmit'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="method" value="<bean:message key='Cancel'/>" >');</script>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
