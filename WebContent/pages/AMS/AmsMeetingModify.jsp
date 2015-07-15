<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>

<script>
history.go(1);
function check() {
	if (document.getElementById("meetingDate").value == "") {
		alert("集會日期不可為空白");
		document.getElementById("meetingDate").focus();
		return false;	
	} else if (document.getElementById("meetingName").value == "") {
		alert("集會名稱不可為空白");
		document.getElementById("meetingName").focus();
		return false;
	}
	
	var startNode = document.getElementById("startNode").value;
	var endNode = document.getElementById("endNode").value;
	if (startNode > endNode) {
		alert("起訖節次輸入有誤,起始節次不該大於結束節次");
		document.getElementById("startNode").focus();
		return false;
	}
	return true;
}
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/AMS/AmsMeeting" method="post" onsubmit="init('系統建立資料中,請稍候...')">
<!-- 標題列 Start -->
	<tr>
		<td class="fullColorTable" width="100%">
	    	<table width="100%" cellpadding="0" cellspacing="0" border="0">
	      		<tr height="30">
	        		<script>generateTableBanner('<div class="gray_15"><B>重 要 集 會 修 改</B></div>');</script>
	      		</tr>
	    	</table>
	  	</td>
	</tr>
<!-- 標題列 End -->

	<tr>
    	<td>      
        	<table width="100%" cellpadding="0" cellspacing="0">
          		<tr height="35" align="center" valign="middle" bgcolor="#CCCCFF">
            		<!-- <font color="blue">${TeacherUnit}&nbsp;&nbsp;${TeacherName}同仁&nbsp;您好</font> -->
          		</tr>
        	</table>        
		</td>
	</tr>

	<tr>
    	<td>      
        	<table width="100%" cellpadding="0" cellspacing="0">
          		<tr>
            		<td>            
              			<table width="99%" class="hairLineTable">
                			<tr>
                  				<td width="10%" align="center" class="hairLineTdF">學年學期</td>
                  				<td class="hairLineTd">
                    				<html:select property="schoolYear" size="1">
	    								<html:options property="years" labelProperty="years" />					
	    							</html:select>&nbsp;&nbsp;學年度&nbsp;第
	    							<html:select property="schoolTerm" size="1">
	    								<html:option value="1">1</html:option>
	    								<html:option value="2">2</html:option>
	    							</html:select>&nbsp;學期
                  				</td>
                  				<td width="10%" align="center" class="hairLineTdF">集會日期</td>
                  				<td class="hairLineTd">
                    				<html:text property="meetingDate" size="8" maxlength="8" readonly="true" />&nbsp;
					 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
				      					align="top" style="cursor:hand" alt="點選此處選擇日期"
				  	  					onclick="javascript:if(!meetingDate.disabled)popCalFrame.fPopCalendar('meetingDate','meetingDate',event);">
                  				</td>
                  				<td width="10%" align="center" class="hairLineTdF">起訖節次</td>
                  				<td class="hairLineTd">
                    				<html:select property="startNode" size="1">
	    								<html:option value="1">1</html:option>
	    								<html:option value="2">2</html:option>
	    								<html:option value="3">3</html:option>
	    								<html:option value="4">4</html:option>
	    								<html:option value="5">5</html:option>
	    								<html:option value="6">6</html:option>
	    								<html:option value="7">7</html:option>
	    								<html:option value="8">8</html:option>
	    								<html:option value="9">9</html:option>
	    								<html:option value="11">夜一</html:option>
	    								<html:option value="12">夜二</html:option>
	    								<html:option value="13">夜三</html:option>
	    								<html:option value="14">夜四</html:option>
	    							</html:select>&nbsp;節~
	    							<html:select property="endNode" size="1">
	    								<html:option value="1">1</html:option>
	    								<html:option value="2">2</html:option>
	    								<html:option value="3">3</html:option>
	    								<html:option value="4">4</html:option>
	    								<html:option value="5">5</html:option>
	    								<html:option value="6">6</html:option>
	    								<html:option value="7">7</html:option>
	    								<html:option value="8">8</html:option>
	    								<html:option value="9">9</html:option>
	    								<html:option value="11">夜一</html:option>
	    								<html:option value="12">夜二</html:option>
	    								<html:option value="13">夜三</html:option>
	    								<html:option value="14">夜四</html:option>
	    							</html:select>&nbsp;節
                  				</td>
                			</tr>
                			<tr>
                				<td width="10%" align="center" class="hairLineTdF">集會名稱</td>
                  				<td class="hairLineTd">
                  					<html:text property="meetingName" size="30" maxlength="50"/>
                  				</td>
                  				<td width="10%" align="center" class="hairLineTdF">參加人員</td>
                  				<td class="hairLineTd">
                  					<html:select property="emplType" size="1">
	    								<html:option value="A">全體教職員</html:option>
	    								<html:option value="1">專任教師</html:option>
	    								<html:option value="2">兼任教師</html:option>
	    								<html:option value="3">職員工</html:option>
	    								<html:option value="4">軍護教師</html:option>
	    							</html:select>
                  				</td>
                  				<td width="10%" align="center" class="hairLineTdF">基數</td>
                  				<td class="hairLineTd">
                  					<html:select property="base" size="1">
	    								<html:option value="1">1</html:option>
	    								<html:option value="0.5">0.5</html:option>
	    							</html:select>
                  				</td>
                			</tr>
              			</table>
            		</td>
          		</tr>
          		<tr>
            		<td class="hairLineTd" align="center">
              			<input type="submit" name="method" value="<bean:message key='makeSure'/>" onClick="return check()" class="CourseButton"/>
              			<input type="submit" name="method" value="<bean:message key='Back'/>" class="CourseButton"/>
            		</td>
          		</tr>
        	</table>		
      	</td>
	</tr>
</html:form>
</table>