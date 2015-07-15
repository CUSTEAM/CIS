<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorateJs.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Course/SeldScheduleManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/calendar_3.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">網路選課管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<c:forEach items="${slist}" var="s">
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>階段</td>
				<td class="hairLineTdF" nowrap>開課學期</td>
				<td class="hairLineTdF" nowrap>部制</td>
				<td class="hairLineTdF" nowrap>年趿</td>
				<td class="hairLineTdF" nowrap>學分上限</td>
				<td class="hairLineTdF" nowrap>學分下限</td>
				<td class="hairLineTdF" nowrap>通識上限</td>
				
				
			</tr>
			
			
			
			
			<tr>
				<td class="hairLineTdF">
				<select name="level" style="font-size:18px;">
					<option <c:if test="${s.level=='1'}">selected</c:if> value="1">第1階段</option>				
					<option <c:if test="${s.level=='2'}">selected</c:if> value="2">第2階段</option>
					<option <c:if test="${s.level=='3'}">selected</c:if> value="3">第3階段</option>
					
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="term" style="font-size:18px;">
					<option <c:if test="${s.term=='1'}">selected</c:if> value="1">第1學期</option>				
					<option <c:if test="${s.term=='2'}">selected</c:if> value="2">第2學期</option>
				</select>
				</td>
				<td class="hairLineTdF">
				
				<select name="depart" style="font-size:18px;">
					<option <c:if test="${s.depart=='D'}">selected</c:if> value="D">日間部</option>				
					<option <c:if test="${s.depart=='N'}">selected</c:if> value="N">夜間部</option>
					<option <c:if test="${s.depart=='H'}">selected</c:if> value="H">進修學院</option>
				</select>
				
				
				
				
				</td>
				<td class="hairLineTdF">
				<select name="grade" style="font-size:18px;">
					<option <c:if test="${s.grade=='1'}">selected</c:if> value="1">1年級</option>				
					<option <c:if test="${s.grade=='2'}">selected</c:if> value="2">2年級</option>
					<option <c:if test="${s.grade=='3'}">selected</c:if> value="3">3年級</option>
					<option <c:if test="${s.grade=='4'}">selected</c:if> value="4">4年級</option>
					<option <c:if test="${s.grade=='5'}">selected</c:if> value="5">5年級</option>
					<option <c:if test="${s.grade=='6'}">selected</c:if> value="6">6年級</option>
				</select>
				
				</td>
				<td class="hairLineTdF"><input type="text" style="font-size:18px;" size="2" name="max" value="${s.max}" /></td>
				<td class="hairLineTdF"><input type="text" style="font-size:18px;" size="2" name="min" value="${s.min}" /></td>
				<td class="hairLineTdF"><input type="text" style="font-size:18px;" size="2" name="nor" value="${s.nor}" /></td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>開始日期</td>
				<td class="hairLineTdF" nowrap>開始時間</td>
				
				<td class="hairLineTdF" nowrap>結束日期</td>
				<td class="hairLineTdF" nowrap>結束時間</td>
				<td class="hairLineTdF" nowrap colspan="3">最後修訂: ${s.cname}</td>
			</tr>
			<tr>
				<td class="hairLineTdF">
				<fmt:parseDate value="${s.begin}" var="b" pattern="yyyy-MM-dd HH:mm"/>
				<input type="text" size="7" style="font-size:18px;" name="begin" value="<fmt:formatDate value='${b}' type='both' pattern='yyyy-MM-dd'/>" />
				</td>
				<td class="hairLineTdF">
				
				<input type="text" size="3" style="font-size:18px;" name="begin_time" value="<fmt:formatDate value='${b}' type='both' pattern='HH:mm'/>" />
				</td>
				
				<fmt:parseDate value="${s.end}" var="e" pattern="yyyy-MM-dd HH:mm"/>
				<td class="hairLineTdF"><input type="text" size="7" style="font-size:18px;" name="end" value="<fmt:formatDate value='${e}' type='both' pattern='yyyy-MM-dd'/>" /></td>
				<td class="hairLineTdF">
				<input type="text" size="3" style="font-size:18px;" name="end_time" value="<fmt:formatDate value='${e}' type='both' pattern='HH:mm'/>" />
				</td>
				
				<td class="hairLineTdF" colspan="3">
				<input type="hidden" id="edit${s.Oid}" name="Oid" value="" />
				<input type="submit" name="method" onClick="edithis('${s.Oid}')" value="<bean:message key='Modify'/>" class="gSubmit" />
				</td>
			</tr>
			
			</table>
			<br>
			</c:forEach>
			
		
		
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
</html:form>
</table>

<script>
function edithis(Oid){
	document.getElementById("edit"+Oid).value=Oid;
}
</script>