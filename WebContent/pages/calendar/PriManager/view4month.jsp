<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<%@ include file="../navigator/navigator4manager.jsp"%>
<c:forEach items="${myCalendar}" var="m" varStatus="v">
<table width="99%" class="hairLineTable" alngn="center">
<tr>
	<td class="hairLineTdF">
	
	<table>
		<tr>			
			<c:if test="${fn:length(m.cs)>0}">
			<td width="30" nowrap ><img src="images/icon/comment_dull.gif"/></td>
			</c:if>
			<c:if test="${fn:length(m.cs)<1}">
			<td width="30" nowrap></td>
			</c:if>
			
			<td align="left" nowrap nowrap style="cursor:pointer;" onClick="showObj('old${v.count}');" width="75">${m.month}月 ${m.day}日</td>
			<td nowrap valign="bottom" style="cursor:pointer;" onClick="showObj('old${v.count}');">		
			<font <c:if test="${m.myWeekDay=='六'||m.myWeekDay=='日'}">color="red"</c:if> size="1">${m.myWeekDay}</font>
			</td>			
			<td width="30" nowrap ></td>
			<c:if test="${fn:length(m.cs)>0}">			
			<td style="cursor:pointer;" onClick="showObj('old${v.count}');" nowrap>			
			<c:if test="${fn:length(m.cs)<=4}">
			<c:forEach begin="1" end="${fn:length(m.cs)}">
			<img src="images/icon/bell.gif"/>
			</c:forEach>
			</td>			
			</c:if>
			
			<c:if test="${fn:length(m.cs)>4}">
			<td><img src="images/icon/bell_error.gif"/></td>			
			<td nowrap width="100" style="cursor:pointer;" onClick="showObj('old${v.count}');"> <b>${fn:length(m.cs)}</b>件事</td>
			</c:if>
			
			</c:if>
			
			<td width="100%" style="cursor:pointer;" onClick="showObj('old${v.count}');"></td>
			<td nowrap><img src="images/icon/plus.gif"/></td>
			<td nowrap style="cursor:pointer;" onClick="showObj('new${v.count}');">建立事項</td>			
		</tr>
	</table>	
	
	<%@ include file="../Directory/add.jsp"%>
	
	<%@ include file="subList.jsp"%>
	
	</td>
</tr>
</table>
</c:forEach>
<div id="ocolorPopup" style="position:absolute; z-index:0;"></div>
<script language="JavaScript" src="include/calendarAdd.js"></script>