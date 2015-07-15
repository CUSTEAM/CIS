<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Course/DtimestampManager" method="post" onsubmit="init('系統處理中...')">
	<tr>
		<td class="fullColorTable" width="100%">
			
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
					<img src="images/icon/folder_bell.gif">
				</td>
				<td align="left">
					&nbsp;節次管理&nbsp;
				</td>
			</tr>
		</table>
			
		</td>
	</tr>
	<tr height="40">
		<td>		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
				<c:set var="schoolSel" value="${DtimestampManagerForm.map.schoolInCharge2}"/>
				<%@ include file="/pages/include/SelectClass/CampusSchool.jsp" %>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
				<select name="Sweek">
					<option <c:if test="${DtimestampManagerForm.map.Sweek==''}">selected</c:if> value="">每天</option>
					<option <c:if test="${DtimestampManagerForm.map.Sweek=='1'}">selected</c:if> value="1">星期一</option>
					<option <c:if test="${DtimestampManagerForm.map.Sweek=='2'}">selected</c:if> value="2">星期二</option>
					<option <c:if test="${DtimestampManagerForm.map.Sweek=='3'}">selected</c:if> value="3">星期三</option>
					<option <c:if test="${DtimestampManagerForm.map.Sweek=='4'}">selected</c:if> value="4">星期四</option>
					<option <c:if test="${DtimestampManagerForm.map.Sweek=='5'}">selected</c:if> value="5">星期五</option>
					<option <c:if test="${DtimestampManagerForm.map.Sweek=='6'}">selected</c:if> value="6">星期六</option>
					<option <c:if test="${DtimestampManagerForm.map.Sweek=='7'}">selected</c:if> value="7">星期日</option>
				</select>
				</td>
			</tr>
		</table>
		
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Search'/>" class="CourseButton"><INPUT 
		type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton">
		</td>
		
	</tr>
	
<c:if test="${!empty times}">
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF"nowrap>
				<table><tr><td><img src="images/icon/icon_info_exclamation.gif"></td><td>點最下面的「儲存」後, 欄位有修改會自動更新記錄, 欄位清空則自動刪除記錄</td></tr></table>
				</td>
			</tr>
		</table>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF"nowrap>所屬校區</td>
				<td class="hairLineTdF"nowrap>所屬學制</td>
				<td class="hairLineTdF"nowrap>星期</td>
				<td class="hairLineTdF"nowrap>顯示文字</td>
				<td class="hairLineTdF"nowrap>實際節次</td>
				<td class="hairLineTdF"nowrap>開始時間</td>
				<td class="hairLineTdF"nowrap>結束時間</td>
				<td class="hairLineTdF">備註</td>
			</tr>
			<c:forEach items="${times}" var="t">
			<tr>
				<td class="hairLineTdF" onClick="checkthis('check${t.Oid}');">
				<input type="hidden" name="Oid" value="${t.Oid}" size="1"/>
				<input type="hidden" name="checked" id="check${t.Oid}"/>
				<select disabled>
					<c:forEach items="${allCampus}" var="a">
					<option <c:if test="${t.Cidno==a.idno}">selected</c:if> value="">${a.name}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<select disabled>
					<c:forEach items="${allSchool}" var="a">
					<option <c:if test="${t.Sidno==a.idno}">selected</c:if> value="">${a.name}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<select disabled>
					<option <c:if test="${t.DSweek==''}">selected</c:if> value="">每天</option>
					<option <c:if test="${t.DSweek=='1'}">selected</c:if> value="1">星期一</option>
					<option <c:if test="${t.DSweek=='2'}">selected</c:if> value="2">星期二</option>
					<option <c:if test="${t.DSweek=='3'}">selected</c:if> value="3">星期三</option>
					<option <c:if test="${t.DSweek=='4'}">selected</c:if> value="4">星期四</option>
					<option <c:if test="${t.DSweek=='5'}">selected</c:if> value="5">星期五</option>
					<option <c:if test="${t.DSweek=='6'}">selected</c:if> value="6">星期六</option>
					<option <c:if test="${t.DSweek=='7'}">selected</c:if> value="7">星期日</option>
				</select>
				</td>
				<td class="hairLineTdF"><input onKeydown="checkthis('check${t.Oid}');" type="text" name="DSvalue" value="${t.DSvalue}" size="6"/></td>
				<td class="hairLineTdF"><input onKeydown="checkthis('check${t.Oid}');" type="text" name="DSreal" value="${t.DSreal}" size="1"/></td>
				<td class="hairLineTdF"><input onKeydown="checkthis('check${t.Oid}');" type="text" name="DSbegin" value="${t.DSbegin}" size="4"/></td>
				<td class="hairLineTdF"><input onKeydown="checkthis('check${t.Oid}');" type="text" name="DSend" value="${t.DSend}" size="4"/></td>
				<td class="hairLineTdF" align="right" width="100%">
				<c:if test="${t.cname!=null && t.cname!=''}">
				<font size="-2">最後由${t.cname}編輯</font>
				</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
		
<script>
function checkthis(id){
	document.getElementById(id).value="*";
}
</script>
		
		
		
		
		
		</td>
	</tr>
	
	
	<tr>
		<td class="fullColorTable" align="center">
		
		<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="CourseButton">
		</td>
	</tr>
</c:if>	
	
</html:form>	
</table>
