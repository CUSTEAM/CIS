<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<html:form action="/Student/MyCsTable" method="post" onsubmit="init('處理中, 請稍後')">
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">

	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_view_icons.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">網路選課</font></div>		
		</td>
	</tr>
	<c:if test="${empty schedule}">
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
		<tr><td class="hairLineTdF">目前非選課期間</td></tr></table>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${!empty schedule}">
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">
				第${schedule.term}學期, 第${schedule.level}階段選課, 已選 ${fn:length(allClass)}門課程<br>
				本學期學分上限 ${schedule.max}學分, 下限 ${schedule.min}學分,已選${mycredit}學分, 每週 ${mythour}時數
				<c:if test="${schedule.level!='1'}">
				<br>此階段依規定依照<font color="red">人數上限</font>選課，因此不顯示人數已滿的課程。
				</c:if>
				<input type='submit' name='method' value=<bean:message key='Clear'/> style="display:none;"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap width="75" nowrap>快速加選</td>
				<td class="hairLineTdF">
				<table><tr><td nowrap>
				<input class="selectOnFocus" type="text" style="font-size:18px;" id="search" onClick="this.value=''"/>
				<input type="button" onClick="searchCs(${schedule.term}, document.getElementById('search').value, 'search')" 
				class="gGreen" value="快速加選" />
				</td>
				<td><img src="images/icon_info.gif"/></td>
				<td>直接點選"快速加選"出現所有可選課程,<br>或輸入關鍵字過濾出有興趣的課程</td></table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap width="75" nowrap>課表加選</td>
				<td class="hairLineTdF">
				<table><tr>
				<td><img src="images/icon_info.gif"/></td>
				<td>也可利用星期節次列出課程加選,可以搭配快速加選有助於學習規劃</td></table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td><%@ include file="MyCsTable/edit.jsp"%></td>
	</tr>
	</c:if>
	<tr height="30">
		<td class="fullColorTable"></td>
	</tr>	

</table>
</html:form>