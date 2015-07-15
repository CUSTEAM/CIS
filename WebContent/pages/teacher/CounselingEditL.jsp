<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/CodeSelection.js" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

<!-- Calendar view 1.2 -->
<link type="text/css" href="<%=basePath%>pages/include/calendarview-1.2/stylesheets/calendarview.css" rel="stylesheet" />	
<script type="text/javascript" src="<%=basePath%>pages/include/calendarview-1.2/javascripts/prototype.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/include/calendarview-1.2/javascripts/calendarview.js"></script>
<script>
      function setupCalendars() {
        // Embedded Calendar
        //Calendar.setup(
        //  {
        //    dateField: 'embeddedDateField',
        //    parentElement: 'embeddedCalendar'
        //  }
        //)

        // Popup Calendar
        Calendar.setup(
          {
            //dateField: 'startDate',
            dateField: 'cdate',
            triggerElement: 'sCalImg',
            dateFormat: '%Y-%m-%d'
          }
        )
      }

      Event.observe(window, 'load', function() { setupCalendars() })
</script>
<!-- End Calendar view 1.2 -->


<style type="text/css">
<!--
.input {
	width:20px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>
			<!-- for calendar use -->
			<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none; z-index:65530;">
				<tr>
					<td id="ds_calclass"></td>
				</tr>
			</table>
			

<form action="/CIS/Teacher/CounselingEdit.do" method="post" name="bpForm">
<input type="hidden" name="studentNo" value="${StudCounselStudentL.studentNo}"/>
<input type="hidden" name="departClass" value="${StudCounselStudentL.departClass}"/>
<input type="hidden" name="cscode" value="${StudCounselStudentL.cscode}"/>
<input type="hidden" name="courseName" value="${StudCounselStudentL.courseName}"/>
<input type="hidden" name="courseClass" value="${StudCounselStudentL.courseClass}"/>

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordEdit" bundle="TCH"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	
	<c:choose>
	<c:when test="${StudCounselingEditL != null}">
	<c:set var="formback" value="${StudCounselingEditL}"/>
		<tr>
			<td><table width="100%"><tr>
			<td width="80" align="right">學生：</td>
	        <td  height="30" colspan="4" align="left" valign="middle" class="hairLineTd">
	        	${StudCounselStudentL.className}&nbsp;&nbsp;${StudCounselStudentL.studentNo} ${StudCounselStudentL.studentName}
	        </td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
        <c:if test="${StudCounselStudentL.cscode != ''}">
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="right" valign="middle">課程：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        	${StudCounselStudentL.courseName}
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
        </c:if>
        <tr>
	      	<td><table width="100%"><tr>
			<td width="80" align="right" class="td_lable_normal">輔導日期：</td>
	        <td  width="90">
	            <input type="text" name="cdate" id="cdate" size="10" readonly="readonly" maxlength="10" value="${formback.cdate}">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="sCalImg" id="sCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<!--
			<td width="90">
	            <input type="text" name="cdate" size="10" value="${StudCounselingEditL.cdate}" onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
	                           autocomplete="off" style="ime-mode:disabled" readonly="ture"/>
			</td>
			<td width="20">	  
				<img src="<%=basePath%>pages/images/date.gif" align="middle" style="text-align:center;" onClick="ds_sh(document.getElementById('cdate')), document.getElementById('cdate').value='';" />
			</td>
			-->
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table></td>
        </tr>
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="left" valign="middle">輔導項目：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        	<select name="itemNo" id="itemNo">
        	<c:forEach items="${StudCounselCodeL}" var="code">
        		<c:if test="${code.itemNo==StudCounselingEditL.itemNo}">
        		<option value="${code.itemNo}" selected="selected">${code.itemName}</option>
        		</c:if>
        		<c:if test="${code.itemNo!=StudCounselingEditL.itemNo}">
        		<option value="${code.itemNo}">${code.itemName}</option>
        		</c:if>
        	</c:forEach>
        	</select>
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="left" valign="middle">輔導內容：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        	<textarea name="content" rows="3" cols="60">${StudCounselingEditL.content}</textarea>
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
        </tr></table></td>
        </tr>
	</c:when>
	
	<c:otherwise>
		<tr>
			<td><table width="100%"><tr>
				<td width="80" align="right">學生：</td>
		        <td  height="30" colspan="4" align="left" valign="middle" class="hairLineTd">
	        	${StudCounselStudentL.className}&nbsp;&nbsp;${StudCounselStudentL.studentNo} ${StudCounselStudentL.studentName}
		        </td>
			<td>&nbsp;</td>
		    </tr></table></td>
        </tr>
        <c:if test="${StudCounselStudentL.cscode != ''}">
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="right" valign="middle">課程：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        	${StudCounselStudentL.courseName}
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
        </c:if>
        <tr>
	      	<td><table width="100%"><tr>
			<td width="80" align="right" class="td_lable_normal">輔導日期：</td>
	        <td  width="90">
	            <input type="text" name="cdate" id="cdate" size="10" readonly="readonly" maxlength="10" value="">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="sCalImg" id="sCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
	      	</tr>
	      	</table></td>
        </tr>
        <tr>
	        <td align="left"><table width="100%"><tr>
	        	<td width="80" align="right" valign="middle">輔導項目：</td>
	        	<td align="left" valign="middle">
	        	<select name="itemNo" id="itemNo">
	        	<c:forEach items="${StudCounselCodeL}" var="code">
	        		<option value="${code.itemNo}">${code.itemName}</option>
	        	</c:forEach>
	        	</select>
	        	</td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
        <tr>
	        <td align="left"><table width="100%"><tr>
	        	<td width="80" align="right" valign="middle">輔導內容：</td>
	        	<td align="left" valign="middle">
	        	<textarea name="content" rows="3" cols="60"></textarea>
	        	</td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
        <tr>
	        <td align="left"><table width="100%"><tr>
	        	<td width="80" align="right" valign="middle">注意事項：</td>
	        	<td align="left" valign="middle">102-1起期中考不及格者，請教師給予輔導後，輸入輔導紀錄，並勾選「預警補救教學」</td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
	</c:otherwise>
	
	</c:choose>	
	</table>
	</td>
	</tr>
	
	<input type="hidden" name="opmode" value="" id="opmode">	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" id="ok">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" id="cancel">');
	//generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
</table>
</form>

<script>history.go(1);</script>
<%@ include file="/pages/studaffair/include/MyCalendarAD.inc" %>	
