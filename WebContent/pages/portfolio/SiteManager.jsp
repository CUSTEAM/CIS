<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Portfolio/SiteManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">					
				<tr height="30">
					<td class="fullColorTable">		
					<div style="float:left; padding:0 5 0 5;"><img src="images/icon/folder_home.gif"/></div>
					<div nowrap style="float:left;"><font class="gray_15">網站管理</font></div>		
					</td>
	
					<td>
						<c:if test="${!notExist}">
						<table align="right">
							<tr>							
								<td width="1"><img src="images/icon/zoomout.gif"></td>
								<td><a href="${myUrl}" target="_blank">立即檢視</a></td>
							</tr>
						</table>
						</c:if>
						
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
		<br>
<script>
function checkView(id){
	
	var stat=document.getElementById(id).style.display;
	
	document.getElementById("basic").style.display="none";
	document.getElementById("editIndex").style.display="none";
	document.getElementById("editHeader").style.display="none";
	document.getElementById("editBanner").style.display="none";
	document.getElementById("editTimer").style.display="none";
	document.getElementById("editFooter").style.display="none";
	document.getElementById("editTemplate").style.display="none";
	document.getElementById("selectTemplate").style.display="none";
	
	if(stat=="none"){
		document.getElementById(id).style.display="inline";	
	}else{
		document.getElementById(id).style.display="none";
	}
	
	
}
</script>
		<table width="100%" cellpadding="0" cellspacing="0" onClick="checkView('basic')">
			<tr>
				<td width="10" align="left" nowrap>
		  			<hr noshade class="myHr"/>
					</td>
					<td width="24" align="center" nowrap>
					<img src="images/user.gif" />
					</td>
					<td nowrap style="cursor:pointer;">
					<font class="gray_15">基本資料</font>
					</td>
					<td width="100%" align="left">
		  			<hr noshade class="myHr"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td id="basic" style="display:inline;">
		<input type="hidden" name="Oid" value="${mySite.Oid}"/>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif"/></td>
				<td class="hairLineTdF">編輯下方欄位(建立)改變網站名稱和網站說明</td>
			</tr>
		</table>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="100"><font class="gray_15">網站路徑</font></td>
				<td class="hairLineTd"><input type="text" style="width:100%" name="path" id="path" onFocus="chInput(this.id)"
	  			   onMouseOver="showHelpMessage('這是網址列上特有的識別字母，設定後將不能更改', 'inline', this.id)" onkeypress = "return regInput(this, /^[a-z]*$/, String.fromCharCode(event.keyCode))"
					onpaste = "return regInput(this, /^[a-z]*$/, window.clipboardData.getData('Text'))"
					ondrop = "return regInput(this, /^[a-z]*$/, event.dataTransfer.getData('Text'))"
					style="ime-modeisabled"
				   onMouseOut="showHelpMessage('', 'none', this.id)"<c:if test="${mySite.path!=null}">disabled</c:if> value="${mySite.path}" 
				   autocomplete="off" style="ime-mode:disabled"/></td>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon/link.gif"/></td>
			</tr>
		</table>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="100"><font class="gray_15">網站名稱</font></td>
				<td class="hairLineTd"><input type="text" style="width:100%" name="siteName" id="siteName" onFocus="chInput(this.id)" 
				onMouseOver="showHelpMessage('這將是各種搜尋引擎中的關鍵字之一，也是您的網站標題', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"value="${mySite.siteName}"/></td>
				<td class="hairLineTdF" width="30" align="center"><img src="images/icon/house.gif"/></td>
			</tr>
		</table>
		<table class="hairLineTable" width="99%">		
			<tr>
				<td class="hairLineTdF" width="100"><font class="gray_15">網站敘述</font></td>
				<td class="hairLineTd"><input type="text" style="width:100%" name="siteDescript" id="siteDescript" onFocus="chInput(this.id)" 
				onMouseOver="showHelpMessage('這將是各種搜尋引擎中的關鍵字之一，也是您的網站子標題', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"
				value="${mySite.siteDescript}"/></td>
				<td class="hairLineTdF" width="30" align="center"><img src="images/comment.gif"/></td>
			</tr>
		</table>		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" align="right">
				<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="gSubmit" id="Save"
				<c:if test="${notExist}">
				onMouseOver="showHelpMessage('<font size=-2><b><font color=red>刪除</font>網站的按鈕</b><br>會在建立網站後浮現<br>若不滿意可重建</font>', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"value="${mySite.siteName}"</c:if>>
				
				<c:if test="${!notExist}">
				<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" class="gCancle"
				onClick="return confirm('確定刪除網站嗎？');">
				</c:if>
				</td>
			</tr>
		</table>		
		</td>
	</tr>
	
	<c:if test="${!notExist}">
	<tr>
		<td>
		<%@ include file="SiteManager/editIndex.jsp" %>
		</td>
	</tr>
	
	<tr>
		<td>		
		<%@ include file="SiteManager/editHeader.jsp" %>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="SiteManager/editBanner.jsp" %>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="SiteManager/ediTimer.jsp" %>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="SiteManager/editFooter.jsp" %>		
		</td>
	</tr>
	</c:if>
</html:form>
	
	<c:if test="${!notExist}">
<html:form action="Portfolio/TemplateManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<tr>
		<td>		
		<%@ include file="TemplateManager/select.jsp" %>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="TemplateManager/edit.jsp" %>
		<br>
		</td>
	</tr>	
</html:form>
	</c:if>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		
		</td>
	</tr>
	
	

</table>

<script>
function regInput(obj, reg, inputStr)
{
var docSel = document.selection.createRange()
if (docSel.parentElement().tagName != "INPUT") return false
oSel = docSel.duplicate()
oSel.text = ""
var srcRange = obj.createTextRange()
oSel.setEndPoint("StartToStart", srcRange)
var str = oSel.text + inputStr + srcRange.text.substr(oSel.text.length)
return reg.test(str)
}
</script>