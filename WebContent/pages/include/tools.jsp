<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<br>
<c:if test="${Credential.employee}">
<!--教職員的公用程式-->	
<table width="95%" cellpadding="0" cellspacing="0" id="myTable" 
onMouseOver="showHelpMessage('本學期授課時間表', 'inline', this.id)" 
onMouseOut="showHelpMessage('', 'none', this.id)">	  
	<tr>
		<td width="1%" nowrap align="right"><img src="images/folder_page.gif"></td>
	  	<td width="1%" nowrap align="left">			  
	  	&nbsp;<html:link page="/TechTimetable4One" style="font-size:18px;">我的課表</html:link> &nbsp;
		</td>
		<td width="50%" align="left"><hr noshade class="myHr"></td>
	</tr>			
</table>

<table width="95%" cellpadding="0" cellspacing="0" id="buildingView"
onMouseOver="showHelpMessage('各樓層教室(場地)位置導覽及預約', 'inline', this.id)" 
onMouseOut="showHelpMessage('', 'none', this.id)">	  
	<tr>
		<td width="1%" nowrap align="right"><img src="images/building.gif"></td>
	  	<td width="1%" nowrap align="left">
	  
	  	&nbsp;<html:link page="/Personnel/RooManager.do" style="font-size:18px;">教室課表</html:link> &nbsp;
	  
		</td>
		<td width="50%" align="left"><hr noshade class="myHr"></td>
	</tr>			
</table>



<table width="95%" cellpadding="0" cellspacing="0" id="email_go"
onMouseOver="showHelpMessage('發送群組郵件', 'inline', this.id)" 
onMouseOut="showHelpMessage('', 'none', this.id)">	  
	<tr>
		<td width="1%" nowrap align="right"><img src="images/email_go.gif"></td>
	  	<td width="1%" nowrap align="left">
	  
	  	&nbsp;<html:link page="/Personnel/MailManager.do" style="font-size:18px;">群組郵件</html:link> &nbsp;
	  
		</td>
		<td width="50%" align="left"><hr noshade class="myHr"></td>
	</tr>			
</table>

<table width="95%" cellpadding="0" cellspacing="0" id="count"
onMouseOver="showHelpMessage('學生人數即時查詢', 'inline', this.id)" 
onMouseOut="showHelpMessage('', 'none', this.id)">	  
	<tr>
		<td width="1%" nowrap align="right"><img src="images/icon_Member_add.gif"></td>
	  	<td width="1%" nowrap align="left">
	  
	  	&nbsp;<html:link page="/Personnel/ListStudent.do" style="font-size:18px;">學生人數即時查詢</html:link> &nbsp;
	  
		</td>
		<td width="50%" align="left"><hr noshade class="myHr"></td>
	</tr>			
</table>
</c:if>

<table width="95%" cellpadding="0" cellspacing="0" id="qcs"
onMouseOver="showHelpMessage('課程查詢', 'inline', this.id)" 
onMouseOut="showHelpMessage('', 'none', this.id)">	  
	<tr>
		<td width="1%" nowrap align="right"><img src="images/icon/folder_magnify.gif"></td>
	  	<td width="1%" nowrap align="left">
	  
	  	&nbsp;<html:link page="/Personnel/ListCourse.do" style="font-size:18px;">本學期課程查詢</html:link> &nbsp;
	  
		</td>
		<td width="50%" align="left"><hr noshade class="myHr"></td>
	</tr>			
</table>



