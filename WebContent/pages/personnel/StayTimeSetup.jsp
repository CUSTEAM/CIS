<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>

<script>
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Personnel/StayTimeSetup" method="post" onsubmit="init('系統處理中...')">
	<script>generateTableBanner('<div class="gray_15"><B>輸 入 留 校 時 間 起 迄 時 間</B></div>');</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
 		 		<tr>
		 			<td align="right">起始時間：</td>
		 			<td align="left">
						<html:text property="begin" size="8" maxlength="8" readonly="true" />&nbsp;
		 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      					align="top" style="cursor:hand" alt="點選此處選擇日期"
	  	  					onclick="javascript:if(!begin.disabled)popCalFrame.fPopCalendar('begin','begin',event);">
	  	  				&nbsp;&nbsp;&nbsp;&nbsp;AM 00:00:00	
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td align="right">截止時間：</td>
		 			<td align="left">
						<html:text property="end" size="8" maxlength="8" readonly="true" />&nbsp;
		 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      					align="top" style="cursor:hand" alt="點選此處選擇日期"
	  	  					onclick="javascript:if(!end.disabled)popCalFrame.fPopCalendar('end','end',event);">
	  	  				&nbsp;&nbsp;&nbsp;&nbsp;PM 23:59:59		
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td align="right">統計起始時間：</td>
		 			<td align="left">
						<html:text property="dead" size="8" maxlength="8" readonly="true" />&nbsp;
		 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      					align="top" style="cursor:hand" alt="點選此處選擇日期"
	  	  					onclick="javascript:if(!end.disabled)popCalFrame.fPopCalendar('dead','dead',event);">
	  	  				&nbsp;&nbsp;&nbsp;&nbsp;AM 00:00:00		
		 	   		</td>
		 	   	</tr>
	   		</table>
   	  </td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.update' bundle='COU'/>" class="CourseButton">');</script>
</html:form>
</table>