<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
function makeSure() {
	var doc = document.forms[0];
	var xlsFile = doc.xlsFile.value;
	if (xlsFile == '') {
		alert("檔案欄位不可為空白");
		doc.xlsFile.focus();
		return false;
	}

	return true;
}

function makeSureTransfer() {
	if (confirm("確定轉入新生基本資料?")) 
		return true;
	else
		return false;	
}
</script>

<html:form action="/Registration/ImportNewStudent" method="post" enctype="multipart/form-data" onsubmit="init('資料建檔中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td>' +
	'<td><font size="+2"> 匯入並建立新 生 基 本 資 料</font> <br><font size="+1" color="red">如果正式學籍已存在，則會建立帳號密碼。</font></td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<!--
				<tr>
					<td class="hairlineTdF">&nbsp;&nbsp;學年學期&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    			<td class="hairlineTdF">
	    				<html:select property="year" size="1">
	    					<html:options property="years" labelProperty="years" />
	    				</html:select>學年&nbsp;&nbsp;
						 第
						<html:select property="sterm" styleId="stermS">
    						<html:option value="1">1</html:option>
    						<html:option value="2">2</html:option>
    					</html:select>學期
    				</td>    				
				</tr>
				-->
				<tr>
					<td class="hairlineTdF">&nbsp;&nbsp;新生基本資料檔&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td class="hairlineTdF">
						<html:file property="xlsFile" styleId="xlsFile" size="50"/>
					</td>
				</tr>
				
				<tr>
					<td>
	  			   		<table width="100%" id="alert" style="display:none">
							<tr>
								<td><br>
									<div class="modulecontainer filled nomessages">
										<div class="first">
											<span class="first"></span>
											<span class="last"></span>
									</div>
									<div>
										<div>
											<table width="100%">
												<tr>
													<td id="helpMsg">
														&nbsp;&nbsp;<img src="images/24-book-green-message.png">
													</td>
													<td align="left"></td>
													<td width="150">
														<a href="javascript:void(0)" onclick="if(document.getElementById('alert').style.display='none'){document.getElementById('alert').style.display='none';}">
														<img src="images/16-tag-check.png" border="0"><font size="1">隱藏說明</font></a>
													</td>
												</tr>
											</table>
										</div>
									</div>
									<div class="last">
										<span class="first"></span>
										<span class="last"></span>
									</div><br>
								</td>
							</tr>
						</table>
					</span></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" class="fullColorTable">
			<table align="center">
				<tr>
					<td>
						<html:submit property="method" styleClass="CourseButton" onclick="return makeSure();"><bean:message key="parseNewStmd" /></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>	
	
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${importStmdList}" export="false" id="row" pagesize="30" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="班級" property="departClass" sortable="true" class="center" />
				<display:column title="學號" property="studentNo" sortable="true" class="center" />
				<display:column title="姓名" property="studentName" sortable="true" class="center" />
				<display:column title="身分證字號" property="idno" sortable="true" class="center" />
				<display:column title="出生日" sortable="true" class="center">
					<fmt:formatDate value="${row.birthday}" pattern="yyyy/MM/dd"/>
				</display:column>
				<display:footer>
					<c:if test="${not empty importStmdList}">
					<tr>
						<td>共解析筆數: <fmt:formatNumber value="${sessionScope.newImpStmdCount}" /> 筆</td>
					</tr>
					</c:if>	
				</display:footer>
			</display:table>
		</td>
	</tr>
	
	<c:if test="${not empty importStmdList}">
	<tr>
		<td align="center" class="fullColorTable">
			<table align="center">
				<tr>
					<td>
						<html:submit property="method" styleClass="CourseButton" onclick="return makeSureTransfer();"><bean:message key="makeSureTransfer" /></html:submit>
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	</c:if>
	
</html:form>
</table>   
