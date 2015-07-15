<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>		
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" align="center" colspan="2"><font size="+1">姓名</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">單位</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">職稱</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">班別</font></td>		
	</tr>
	<tr>		
		<td class="hairLineTd" colspan="4" align="center">
		人員列表
		<input type="hidden" name="idno"/>
		
		</td>
		<td class="hairLineTd" nowrap>
		
		<select name="WorkShift" id="allWorkShift" class="courseButton">
			<option value="">無須刷卡</option>
			<c:forEach items="${allShift}" var="as">
			<option value="${as.id}">${as.name}</option>				
			</c:forEach>
		</select>
		<input type="button" class="gGreen" value="向下填滿" onClick="setAll('WorkShift', document.getElementById('allWorkShift').value);"id="setAllId" 
		onMouseOver="showHelpMessage('填滿所有班別<br><font size=-2>(先選左邊欄位)</font>', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
	
<script>
function setAll(name, value){
	//alert(value);
	var a=document.getElementsByName(name);
	for(i=0; i<a.length; i++){
		a[i].value=value;
	}
}
</script>	
	
	<c:forEach items="${emplShifts}" var="e">
	<tr>
		<td class="hairLineTdF" align="center">
		<c:choose>
			<c:when test="${e.category=='1'}"><img src="images/user_gray.gif"/></c:when>
			<c:when test="${e.category=='2'}"><img src="images/user_suit.gif"/></c:when>
			<c:when test="${e.sex=='1'}"><img src="images/icon/user.gif"/></c:when>
			<c:when test="${e.sex=='2'}"><img src="images/user_female.gif"/></c:when>
			
		</c:choose>
		</td>
		<td class="hairLineTdF" nowrap><font size="+1">${e.cname}</font><input type="hidden" name="idno" value="${e.idno}"/></td>
		<td class="hairLineTdF" align="center"><font size="+1">${e.name}</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">${e.sname}</font></td>
		<!--td class="hairLineTdF" nowrap><font size="+1">
		<c:choose>
			<c:when test="${fn:length(e.sname)>6}">${fn:substring(e.sname, 0, 6)}..略</c:when>
			<c:when test="${fn:length(e.sname)<6}">${e.sname}</c:when>
		</c:choose>
		</font>
		</td-->
		<td class="hairLineTdF">		
		<select name="WorkShift" class="courseButton">
			<option <c:if test="${e.WorkShift==''||e.WorkShift==null}">selected</c:if> value="">無須刷卡</option>
			<c:forEach items="${allShift}" var="as">
			<option <c:if test="${e.WorkShift==as.id}">selected</c:if> value="${as.id}">${as.name}</option>				
			</c:forEach>
		</select>		
		</td>		
		
		
	</tr>

</c:forEach>
</table>