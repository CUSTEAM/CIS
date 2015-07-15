<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/HelpMessage.jsp"%>
<script>
function chLoginInput(id){
	if(id=="username"){
		document.getElementById("username").className="username_on";
		document.getElementById("password").className="password";
	}else{
		document.getElementById("username").className="username";
		document.getElementById("password").className="password_on";
	}
}
</script>
<table class="hairlineTable" width="98%">
	<tr>
		<td class="fullColorTablePad" style="cursor:pointer;">		
		<div style="float:left; padding:2 5 0 0;"><img src="images/icon/security-lock.gif"/></div>
		<div nowrap style="float:left;" style="float:left; padding:2 5 0 0;"><font class="gray_15">教師與學生登入</font></div>	
		</td>
	</tr>
	
	
	<tr>
		
		<td align="left" class="hairlineTd">
	
		<table align="left">
		<html:form action="/Login.do" method="post" focus="username" onsubmit="init('驗證中, 請稍後...')">
  			<tr>
  				<td class="gray_15">帳號 <img src="/CIS/pages/images/icon/icon_info.gif" id="usernameHelp" style="cursor:pointer;" 
  				onMouseOver="showHelpMessage('學生身份以學號登入<br>教職員以員工編號登入', 'inline', this.id);" 
				onMouseOut="showHelpMessage('', 'none', this.id);"/></td>
  				<td class="gray_15">密碼 <img src="/CIS/pages/images/icon/icon_info.gif" id="passHelp" style="cursor:pointer;" 
  				onMouseOver="showHelpMessage('學生預設為身分證字號<br>教職員預設為生日', 'inline', this.id);" 
				onMouseOut="showHelpMessage('', 'none', this.id);"/></td>
  			</tr>
  			<tr>
  				<td>
  				<input type="text" class="username" name="username"size="18" "
  				value="${cookie['loginusername'].value}" id="username" onFocus="chLoginInput(this.id);"/>
		        </td>
  				<td>
  				<input type="password" class="password" class="Pass" name="password" 
  				size="18" id="password" value="" onFocus="chLoginInput(this.id);"/>
  				
  				</td>
  			</tr>
  			<tr>
  				<td colspan="2">
  				
     			</td>
  			</tr>
  			<tr>
  				
  				<td colspan="2">
  				<input type="submit" value="師生登入" class="gSubmit" onClick="checkRememberMe();"/>
  				<input type="checkbox" onClick="checkRememberMe()" <c:if test="${cookie['loginusername'].value!=null}">checked</c:if> name="rememberme" />記住帳號
  				</td>
  				
  			</tr>
		</html:form>
		</table>
		
		</td>
	</tr>
</table>