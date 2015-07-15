<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc"%>

<script type="text/javascript">
history.go(1);
function check() {
	var iCount = document.getElementById("title").value;
	if (iCount == '') {
		alert("事項不可為空白,謝謝!!");
		document.getElementById("title").focus();
		return false;
	}
	
	/*
	iCount = document.getElementById("host").value;
	if (iCount == '') {
		alert("主持人不可為空白,謝謝!!");
		document.getElementById("host").focus();
		return false;
	}
	*/
	
	iCount = document.getElementById("content").value;
	if (iCount == '') {
		alert("內容不可為空白,謝謝!!");
		document.getElementById("content").focus();
		return false;
	}
	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Secretary/Calendar" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>秘 書 室 行 事 曆 管 理 維 護</B></div>');</script>
	<html:hidden property="oid" value="${calendarInfo.map.oid}"/>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;事項&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="title" styleId="title" size="20" maxlength="80" value="${calendarInfo.map.title}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;地點&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF" colspan="3">
                     	<html:text property="location" styleId="location" size="20" maxlength="50" value="${calendarInfo.map.location}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;起始日期&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<input type="text" name="startDate" size="10" maxlength="8" readonly value="${calendarInfo.map.startDate}">
    						<img src="images/cal.gif" name="calendar" width="20" height="20" 
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!startDate.disabled)popCalFrame.fPopCalendar('startDate','startDate',event);">&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;主持人&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF" colspan="3">
                     	<html:text property="host" styleId="host" size="20" maxlength="20" value="${calendarInfo.map.host}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
				<tr bgcolor="#f0fcd7">    
                    <td class="hairlineTdF">&nbsp;&nbsp;起始時間&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
              			<html:select property="startTime" value="${calendarInfo.map.startTime}">
		  	  				<html:option value="08">08</html:option>
		  	  				<html:option value="09">09</html:option>
		  	  				<html:option value="10">10</html:option>
		  	  				<html:option value="11">11</html:option>
		  	  				<html:option value="12">12</html:option>
		  	  				<html:option value="13">13</html:option>
		  	  				<html:option value="14">14</html:option>
		  	  				<html:option value="15">15</html:option>
		  	  				<html:option value="16">16</html:option>
		  	  				<html:option value="17">17</html:option>
		  	  				<html:option value="18">18</html:option>
		  	  				<html:option value="19">19</html:option>
		  	  				<html:option value="20">20</html:option>
		  	  				<html:option value="21">21</html:option>
		  	  				<html:option value="22">22</html:option>
		  	  				<html:option value="23">23</html:option>
		  	  			</html:select>&nbsp;時&nbsp;&nbsp;&nbsp;
		  	  			<html:select property="startMin" value="${calendarInfo.map.startMin}">
		  	  				<html:option value="0">00</html:option>
		  	  				<html:option value="10">10</html:option>
		  	  				<html:option value="20">20</html:option>
		  	  				<html:option value="30">30</html:option>
		  	  				<html:option value="40">40</html:option>
		  	  				<html:option value="50">50</html:option>
		  	  			</html:select>&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	  		</td>
		  	  		<td class="hairlineTdF">&nbsp;&nbsp;區間&nbsp;&nbsp;&nbsp;&nbsp;</td>
		  	  		<td class="hairlineTdF">
		  	  			<html:select property="range" value="${calendarInfo.map.range}">
		  	  				<html:option value="1">1</html:option>
		  	  				<html:option value="2">2</html:option>
		  	  				<html:option value="3">3</html:option>
		  	  				<html:option value="4">4</html:option>
		  	  				<html:option value="5">5</html:option>
		  	  				<html:option value="6">6</html:option>
		  	  			</html:select>&nbsp;小時
              		</td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;承辦單位&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF" colspan="5">
                     	<html:textarea property="content" styleId="content" cols="60" rows="5" value="${calendarInfo.map.content}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
			</table>
    	</td>
	</tr>
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.update.sure' bundle="FEE" />" onclick="return check()" class="CourseButton">' + 
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.back' bundle="FEE" />" class="CourseButton">');
   	</script>
	
</html:form>
</table>