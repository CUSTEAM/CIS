<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>

<script>
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Personnel/StayTimeRelief" method="post" onsubmit="init('系統處理中...')">
	<script>generateTableBanner('<div class="gray_15"><B>留 校 時 間 產 學 減 免 維 護</B></div>');</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
       			<span id="year">開課學年: 第 
					<html:select property="year" size="1">
   						<html:options property="year" labelProperty="year" />	    						
   					</html:select>學年&nbsp;&nbsp;&nbsp;&nbsp;
   				</span>
 		 		<tr>
		 			<td align="center">起始時間：
						
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td align="center">截止時間：
							
		 	   		</td>
		 	   	</tr>
	   		</table>
   	  </td>
	</tr>
	<!--
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.update' bundle='COU'/>" class="CourseButton">');</script>
	-->
</html:form>
</table>