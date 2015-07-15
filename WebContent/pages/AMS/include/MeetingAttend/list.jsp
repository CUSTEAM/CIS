<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" align="center" width="20%">單位</td>
		<td class="hairLineTdF">姓名</td>
		<td class="hairLineTdF">出席狀況</td>		
	</tr>
    
  <c:forEach items="${myMeeting}" var="e">
	<tr>		
		<td class="hairLineTdF" align="center" width="20%">${e.name }</td>
		<td class="hairLineTdF">${e.sname }&nbsp;&nbsp;&nbsp;&nbsp;${e.cname}
		  <input type="hidden" name="Idno" value="${e.Idno}" />
		  <input type="hidden" name="MeetingOid" value="${e.MeetingOid}" />
		</td>		
		<td class="hairLineTdF">
		  <select name="Status">
			<option <c:if test="${e.Status=='null'}">selected</c:if> value="null">出席</option>
			<option <c:if test="${e.Status=='A'}">selected</c:if> value="A">上課</option>
			<option <c:if test="${e.Status=='99'}">selected</c:if> value="99">缺席</option>
			<c:forEach items="${askLeave}" var="c">
			<option <c:if test="${e.Status==c.ID}">selected</c:if> value="${c.ID}">${c.Name}</option>
			</c:forEach>
	      </select>
		</td>		
	</tr>
  </c:forEach>
</table>