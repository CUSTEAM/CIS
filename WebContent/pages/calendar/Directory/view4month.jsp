<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<%@ include file="../navigator/navigator.jsp"%>
<script>
function showList(id){
	document.getElementById("key1").className="dirTd";
	document.getElementById("key2").className="dirTd";
	document.getElementById("key3").className="dirTd";
	document.getElementById("key4").className="dirTd";
	document.getElementById("key5").className="dirTd";
	document.getElementById("key6").className="dirTd";
	document.getElementById("key7").className="dirTd";
	document.getElementById("key"+id).className="dirTdB";
	
	document.getElementById("value1").style.display="none";
	document.getElementById("value2").style.display="none";
	document.getElementById("value3").style.display="none";
	document.getElementById("value4").style.display="none";
	document.getElementById("value5").style.display="none";
	document.getElementById("value6").style.display="none";
	document.getElementById("value7").style.display="none";
	document.getElementById("value"+id).style.display="inline";
}
</script>
<table width="99%" align="center" class="dirTable">
	<tr>
		<c:forEach items="${myCalendar}" var="m" varStatus="v">
		
		<td nowrap style="cursor:pointer;" id="key${v.count}"
		<c:if test="${m.weekDay==weekDay}">class="dirTdB"</c:if> 
		<c:if test="${m.weekDay!=weekDay}">class="dirTd"</c:if>
		onClick="showList('${v.count}');"
		width="14%">		
		<c:if test="${fn:length(m.cs)>0}">
		<div style="padding:5px; position:absolute; zindex:327;"><img src="images/icon/bell.gif"/></div>
		</c:if>		
		<table width="100%" >
			<tr>
				<td align="center"><font size="2">${m.month}月</font></td>
			</tr>
			<tr>
				<td align="center"><font size="+2"><b>${m.day}</b></font></td>
			</tr>
			<tr>
				<td align="center"><font <c:if test="${m.myWeekDay=='六'||m.myWeekDay=='日'}">color="red"</c:if> size="2">
				星期${m.myWeekDay}</font></td>
			</tr>
		</table>
		
		</td>		
		</c:forEach>
	</tr>
	
	<tr height="200">
		<td colspan="7" class="dirTdF" valign="top">
		
		
		
		<c:forEach items="${myCalendar}" var="m" varStatus="v">		
		<%@ include file="subList.jsp"%>
		</c:forEach>		
		</td>
	</tr>
</table>
<div id="ocolorPopup" style="position:absolute; z-index:0;"></div>
<script language="JavaScript" src="include/calendarAdd.js"></script>