<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/DepAssistant/DtimeReserveOption" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/book.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">時數規劃</font></div>		
		</td>
	</tr>
	
	

	
	<tr>
		<td>
		<%@ include file="DtimereserveOptioner/search.jsp"%>	
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" width="50%">
			<tr>
				<td colspan="5" style="font-size:18px;" class="hairLineTdF" nowrap>
				各年級上下學期學分與時數上限
				</td>
			</tr>
			
			<tr>
				<td style="font-size:18px;" class="hairLineTdF" align="right">年級</td>
				<td style="font-size:18px;" class="hairLineTdF" align="left">學期</td>
				<td style="font-size:18px;" class="hairLineTdF" align="left" nowrap>學分規劃</td>
				<td style="font-size:18px;" class="hairLineTdF" align="right" nowrap>時數規劃</td>
				<td style="font-size:18px;" class="hairLineTdF" align="left" width="100">狀態</td>							
			</tr>
			<c:forEach begin="${fn:length(times)}" end="7" var="t">
			
			<tr>				
				<td class="hairLineTdF" align="right">
				<input type="hidden" name="Oid" value=""/>
				<select name="grades" style="font-size:18px;">
					<option value="1">一年級</option>
					<option value="2">二年級</option>
					<option value="3">三年級</option>
					<option value="4">四年級</option>
				</select>
				</td>
				<td class="hairLineTdF" align="left">
				<select name="term" style="font-size:18px;">
					<option value="1">上學期</option>
					<option value="2">下學期</option>
				</select>
				</td>
				<td class="hairLineTdF" align="left"><input type="text" style="ime-mode:disabled" name="credit" value="" size="4"/></td>
				<td class="hairLineTdF" align="right"><input type="text" style="ime-mode:disabled" name="thour" value="" size="4"/></td>
				<td style="font-size:18px;" class="hairLineTdF" align="left">
				<select disabled style="font-size:18px;">
					<option value="1" nowrap>開放課程規劃</option>
				</select>
				<input type="hidden" name="open"/>
				</td>
			</tr>
			</c:forEach>
			<tr>
				<td class="hairLineTdF" colspan="5" align="center">
				<input type="submit" name="method"
				value="<bean:message key='Save'/>" 
				id="add" class="gSubmit"
				onMouseOver="showHelpMessage('建立新資料', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
			</tr>			
		</table>
		
		<c:if test="${!empty times}">
		<table class="hairLineTable" width="50%">
			<tr>
				<td colspan="5" style="font-size:18px;" class="hairLineTdF">
				管理 ${DtimereserveOptionForm.map.year}年入學之學生規劃基準
				</td>
			</tr>
			<tr>
				<td style="font-size:18px;" class="hairLineTdF" align="right">年級</td>
				<td style="font-size:18px;" class="hairLineTdF" align="left">學期</td>
				<td style="font-size:18px;" class="hairLineTdF" align="left" nowrap>學分規劃</td>
				<td style="font-size:18px;" class="hairLineTdF" align="right" nowrap>時數規劃</td>				
				<td style="font-size:18px;" class="hairLineTdF" align="left" width="100">狀態</td>
			</tr>
			<c:forEach items="${times}" var="t">
			<tr>				
				<td class="hairLineTdF" align="right">
				<input type="hidden" name="Oid" value="${t.Oid}"/>
				<select name="grades" style="font-size:18px;">
					<option value="">刪除</option>
					<option <c:if test="${t.grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${t.grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${t.grade=='3'}">selected</c:if> value="3">三年級</option>
					<option <c:if test="${t.grade=='4'}">selected</c:if> value="4">四年級</option>
				</select>
				</td>
				<td class="hairLineTdF" align="left">
				<select name="term" style="font-size:18px;">
					<option value="">刪除</option>
					<option <c:if test="${t.term=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${t.term=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				</td>
				<td class="hairLineTdF" align="left"><input type="text" name="credit" style="ime-mode:disabled" value="${t.credit}" size="4"/></td>
				<td class="hairLineTdF" align="right"><input type="text" name="thour" style="ime-mode:disabled" value="${t.thour}" size="4"/></td>				
				<td style="font-size:18px;" class="hairLineTdF" align="left" nowrap>
				
				
				
				<c:if test="${t.open=='1'}">
				<select name="open" style="font-size:18px;">
					<option <c:if test="${t.open=='0'}">selected</c:if> value="0">關閉課程規劃</option>
					<option <c:if test="${t.open=='1'}">selected</c:if> value="1">開放課程規劃</option>
				</select>
				</c:if>
				<c:if test="${t.open=='0'}">
				<select disabled style="font-size:18px;">
					<option selected value="0">關閉課程規劃</option>
				</select>
				<input type="hidden" name="open" value="${t.open}" />
				</c:if>
				
				</td>
			</tr>
			</c:forEach>
			<tr>
				<td class="hairLineTdF" colspan="5" align="center">
				<input type="submit" name="method"
				value="<bean:message key='Save'/>" 
				id="Save${t.Oid}" class="gGreen"
				onMouseOver="showHelpMessage('修改已存資料', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
			</tr>
		</table>		
		</td>
	</tr>	
	</c:if>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
	
</html:form>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>