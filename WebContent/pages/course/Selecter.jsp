<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/Selecter" method="post" onsubmit="init('處理中, 請稍後')">
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/user_add.gif">
				</td>
				<td align="left">
				&nbsp;學生選課管理&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	
<tr>
	<td>
	<%@ include file="Selecter/search.jsp" %>
		
	</td>
</tr>


<!-- 加選作業 Start -->
<c:if test="${mode=='add'}">
<tr>
	<td>
		
	
	<%@ include file="Selecter/hist.jsp" %>
	
	
	
	</td>
</tr>




<c:if test="${sselds!=null}">
<tr>
	<td>
	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<display:table name="${sselds}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="<script>generateTriggerAll(${fn:length(sselds)}, 'sselds'); </script>" class="center" >
	    <script>generateCheckbox("${row.Oid}", "sselds")</script></display:column>
	    <display:column title="班級代碼" property="ClassNo" nulls="true" />
		<display:column title="班級名稱" property="ClassName" nulls="false" />
		
		<display:column title="課程代碼" property="cscode" nulls="false" />
		<display:column title="課程名稱" property="chi_name" nulls="false" />
		<display:column title="授課教師" property="cname" nulls="false" />
		
		<display:column title="選別" property="opt" nulls="false" />	 
		<display:column title="學分" property="credit" nulls="false" />
		<display:column title="時數" property="thour" nulls="false" />
		<display:column title="人數" property="stdSelected" nulls="true" />
		
	</display:table>
	</td>
</tr>
</c:if>
<tr height="30">
	<td class="fullColorTable" align="center" width="100%">
	<c:if test="${sselds!=null}">
		<INPUT type="submit" name="method" value="<bean:message key='AddConfirmed'/>" class="gSubmit">
		<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="gCancle" disabled>
	</c:if>
	</td>
</tr>

</c:if>
<!-- 加選作業 End -->


<!-- 退選作業 Start -->
<c:if test="${mode=='remove'}">
<tr>
	<td>
	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<display:table name="${selected}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	<display:column title="<script>generateTriggerAll(${fn:length(selected)}, 'selected'); </script>" class="center" >
	<script>generateCheckbox("${row.Oid}", "selected")</script></display:column>
	<display:column title="梯次" property="seqno" nulls="false" />
	<display:column title="學號" property="student_no" nulls="false" />
	<display:column title="姓名" property="student_name" nulls="false" />
	<display:column title="課程名稱" property="chi_name" nulls="false" />
	<display:column title="課程代碼" property="cscode" nulls="false" />
	<display:column title="選別" property="opt" nulls="false" />	 
	<display:column title="學分" property="credit" nulls="false" />
	<display:column title="狀態" property="status" nulls="false" />
	<display:column title="週一" property="day1" nulls="false" />
	<display:column title="週二" property="day2" nulls="false" />
	<display:column title="週三" property="day3" nulls="false" />
	<display:column title="週四" property="day4" nulls="false" />
	<display:column title="週五" property="day5" nulls="false" />
	<display:column title="週六" property="day6" nulls="false" />
	<display:column title="週日" property="day7" nulls="false" />	        
	</display:table>				
	</td>
</tr>
<tr height="30">
	<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='RemoveConfirmed'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Cancel'/>"
													   class="CourseButton" disabled>
	</td>
</tr>
</c:if>
<!-- 退選作業 End -->
</html:form>
</table>


<script>
	function showInfo(id){
		if(document.getElementById(id).style.display=="inline"){
			document.getElementById(id).style.display="none"
		}else{
			document.getElementById(id).style.display="inline"
		}
		
	}
</script>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>