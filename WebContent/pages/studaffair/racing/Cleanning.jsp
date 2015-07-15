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

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
     <tr>
        <td  height="30" colspan="6" align="center" valign="middle">週次：
          <input name="week_no" type="text" size="2" maxlength="3"/>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="method" value="<bean:message key='Create'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${! empty CleanInEdit || ! empty CleanList}">
		<tr><td><table width="100%" cellpadding="2" cellspacing="1">
	<c:choose>
		<c:when test="${! empty CleanInEdit}">
       <c:set var="rcnt" value="0"/>
       <c:set var="departClassName" value="${CleanInEdit.departClassName}"/>
       <c:set var="weekno" value="${CleanInEdit.week_nos}"/>
       <c:set var="score" value="${CleanInEdit.score}"/>
		<tr><td align="center" colspan="5">第${CleanInEdit.week_nos[0]}週</td></tr>
		<c:forEach items="${CleanInEdit.depart_class}" var="departClass">
			<input type="hidden" name="departClassName" value="${departClassName[rcnt]}">
			<input type="hidden" name="depart_class" value="${departClass}">
			<input type="hidden" name="week_nos" value="${weekno[rcnt]}">
			<tr>
				<td>&nbsp;</td>
				<td width="80" bgcolor="#add8e6">${departClass}</td>
				<td width="150" bgcolor="#add8e6">${departClassName[rcnt]}</td>
				<td align="center" width="50" bgcolor="#f5deb3">
		       <input name="score" type="text" size="4" maxlength="4" value="${score[rcnt]}"/>
		       </td>
				<td>&nbsp;</td>
			</tr>
			<c:set var="rcnt" value="${rcnt + 1}"/>
		</c:forEach>          		
	</c:when>
	
	<c:when test="${ ! empty CleanList}" >
		<tr><td align="center" colspan="5">第${CleanList[0].weekNo}週</td></tr>
		<c:forEach items="${CleanList}" var="clean">
			<input type="hidden" name="week_nos" value="${clean.weekNo}">
			<input type="hidden" name="depart_class" value="${clean.departClass}">
			<input type="hidden" name="departClassName" value="${clean.departClassName}">
			<tr>
				<td>&nbsp;</td>
				<td width="80" bgcolor="#add8e6">${clean.departClass}</td>
				<td width="150" bgcolor="#add8e6">${clean.departClassName}</td>
				<td align="center" width="50" bgcolor="#f5deb3">
          		<input name="score" type="text" size="4" maxlength="4" value="${clean.score}"/>
          		</td>
				<td>&nbsp;</td>
			</tr>	
		</c:forEach>
	</c:when>
	</c:choose>
		</table></td></tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" >&nbsp;&nbsp;');
	</script>
	</c:if>
<!-- Begin Content Page Table Footer -->
</table>		
</form>
