<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Portfolio/UcanReport" method="post">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/chart_line.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">UCAN統計資料</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
				統計期間
				</td>
				<td class="hairLineTdF">
				<input id="startDate" name="begin" type="text" size="4" value="${UcanReportForm.map.begin}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				至
				</td>
				<td class="hairLineTdF">
				<input id="endDate" name="end" type="text" size="4" value="${UcanReportForm.map.end}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="helpDate"
				onMouseOver="showHelpMessage('開始日期輸入左邊, 結束日期輸入右邊', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>統計範圍</td>
				<td class="hairLineTdF">
				<select name="CampusNo" style="font-size:16px;">
					<option <c:if test="${UcanReportForm.map.CampusNo=='_'}">selected</c:if> value="_">所有校區</option>
					<c:forEach items="${AllCampus}" var="ac">					
					<option <c:if test="${UcanReportForm.map.CampusNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>
				</td>
			
				<td class="hairLineTdF">
				<select name="SchoolNo" style="font-size:16px;">
					<option <c:if test="${UcanReportForm.map.SchoolNo=='__'}">selected</c:if> value="__">所有學制</option>
					<c:forEach items="${AllSchool}" var="ac">					
					<option <c:if test="${UcanReportForm.map.SchoolNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			
				<td class="hairLineTdF">				
				<select name="DeptNo"style="font-size:16px;">
					<option <c:if test="${UcanReportForm.map.DeptNo=='_'}">selected</c:if> value="_">所有科系</option>
					<c:forEach items="${AllDept}" var="ac">					
					<option <c:if test="${UcanReportForm.map.DeptNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			
				<td class="hairLineTdF">
				
				<select name="Grade" style="font-size:16px;">
					<option <c:if test="${UcanReportForm.map.Grade=='_'}">selected</c:if> value="_">所有年級</option>
					<option <c:if test="${UcanReportForm.map.Grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${UcanReportForm.map.Grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${UcanReportForm.map.Grade=='3'}">selected</c:if> value="3">三年級</option>
					<option <c:if test="${UcanReportForm.map.Grade=='4'}">selected</c:if> value="4">四年級</option>
					<option <c:if test="${UcanReportForm.map.Grade=='4'}">selected</c:if> value="5">五年級</option>
				</select>
				
				</td>				
			
				<td class="hairLineTdF">
				<select name="ClassNo" style="font-size:16px;">
					<option <c:if test="${UcanReportForm.map.ClassNo=='_'}">selected</c:if> value="_">所有班級</option>
					<option <c:if test="${UcanReportForm.map.ClassNo=='1'}">selected</c:if> value="1">甲班</option>
					<option <c:if test="${UcanReportForm.map.ClassNo=='2'}">selected</c:if> value="2">乙班</option>
					<option <c:if test="${UcanReportForm.map.ClassNo=='3'}">selected</c:if> value="3">丙班</option>
					<option <c:if test="${UcanReportForm.map.ClassNo=='4'}">selected</c:if> value="4">丁班</option>
				</select>
				</td>				
			</tr>			
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>統計項目</td>
				<td class="hairLineTdF">
				<select name="type" style="font-size:16px;">
					<option <c:if test="${UcanReportForm.map.type=='1'}">selected</c:if> value="1">職業興趣探索結果</option>
					<option <c:if test="${UcanReportForm.map.type=='2'}">selected</c:if> value="2">職場共通職能診斷結果</option>
					<option <c:if test="${UcanReportForm.map.type=='3'}">selected</c:if> value="3">專業職能-就業途徑的總分</option>
					<option <c:if test="${UcanReportForm.map.type=='4'}">selected</c:if> value="4">專業職能診斷第一層平均分數</option>
					<option <c:if test="${UcanReportForm.map.type=='5'}">selected</c:if> value="5">指標對照表</option>
					<option <c:if test="${UcanReportForm.map.type=='6'}">selected</c:if> value="6">職類對照表</option>
					<option <c:if test="${UcanReportForm.map.type=='7'}">selected</c:if> value="7">職類對照表</option>
				</select>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit"name="method"value="<bean:message key='Query'/>"class="gSubmit"
		id="Established">
		
		</td>
	</tr>
</html:form>
</table>
<%@ include file="/pages/include/MyCalendar.jsp" %>
