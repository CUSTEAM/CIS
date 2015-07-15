<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
function check() {
	var doc = document.forms[0];
	var feeType = '';
	var xlsFile = doc.xlsFile.value;
	if (document.getElementsByName("feeType")[0].checked)
		feeType = document.getElementsByName("feeType")[0].value;
	else if (document.getElementsByName("feeType")[1].checked)
		feeType = document.getElementsByName("feeType")[1].value;
	
	if (xlsFile != '' && feeType != '')
		return true;
	else {
		if (xlsFile == '') {
			alert("請選擇欲解析的Excel檔案");
			doc.xlsFile.focus();
		} else if (feeType == '') {
			alert("請選擇欲解析的繳費類型");
		}
		return false;				
	}		
}

function makeSure() {
	if (confirm("確定要開始更新資料(開始前請核對解析資料是否正確?)"))
		return true;
		
	return false;	
}
</script>

<html:form action="/FEE/BankFeeRegister4C" method="post" enctype="multipart/form-data" onsubmit="init('執行中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td> 銀 行 繳 費 資 料 更 新 註 冊 檔 (學 雜 代 辦 費)</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td class="hairlineTdF">&nbsp;&nbsp;學年學期&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<html:select property="year" size="1">
	    					<html:options property="years" labelProperty="years" />	    						
	    				</html:select>學年
	    				<html:select property="sterm" styleId="stermS">
    						<html:option value="1">1</html:option>
    						<html:option value="2">2</html:option>
    					</html:select>學期
					</td>
				</tr>
				<tr>
					<td class="hairlineTdF">&nbsp;&nbsp;銀行繳費註冊檔&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<html:file property="xlsFile" styleId="xlsFile" size="50"/>
					</td>
				</tr>
				<tr>
					<td class="hairlineTdF">&nbsp;&nbsp;校區&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<html:radio property="campusCode" value="1"/>台北
						<html:radio property="campusCode" value="2"/>新竹
					</td>
				</tr>
				<tr>
					<td class="hairlineTdF">&nbsp;&nbsp;繳費類型&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<html:radio property="feeType" value="0"/>學雜費
						<html:radio property="feeType" value="1"/>代辦費
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" class="fullColorTable">
			<table align="center">
				<tr>
					<td>
						<html:submit styleId="previewB" property="method" styleClass="CourseButton" onclick="return check();"><bean:message key="Parse" /></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${registerUpdateList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="學年" property="year" sortable="true" class="center" />
				<display:column title="學期" property="term" sortable="true" class="center" />
				<display:column title="學號" property="studentNo" sortable="true" class="center" />
				<display:column title="身分證字號" property="idno" sortable="true" class="center" />
				<display:column title="虛擬帳號" property="accountNo" sortable="true" class="center" />
				<display:column title="繳費金額" property="amount" sortable="true" class="center" />
				<display:column title="繳費日期" sortable="true" class="center">
					<fmt:formatDate type="date" pattern="yyyy/MM/dd" value="${row.map.payDate}"/>
				</display:column>
				<display:footer>
					<c:if test="${not empty registerUpdateList}">
					<tr>
						<td>共解析筆數: <fmt:formatNumber value="${sessionScope.registerUpdateCount}" /> 筆</td>
					</tr>
					</c:if>	
				</display:footer>
			</display:table>
    	</td>
	</tr>
	
	<c:if test="${not empty registerUpdateList}">
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='makeSure' />" onclick="return makeSure()" class="CourseButton">');
   	</script>
   	</c:if>
	
</html:form>	
</table>
