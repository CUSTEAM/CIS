<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/AMS/LeaveDocManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/user_gray.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">審核者管理</font></div>		
		</td>
	</tr>
	
	
	
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">單位名稱</td>
				<td class="hairLineTdF">單位人數</td>
				<td class="hairLineTdF">審核者</td>
				<td class="hairLineTdF"></td>
			</tr>
			<c:forEach items="${leaders}" var="a">
			<tr>
				<td class="hairLineTdF" nowrap>${a.uname}</td>
				<td class="hairLineTdF" align="right">${a.cnt}</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value="${a.aOid}"/>
				<input type="text"  autocomplete="off"
				name="empl_name" id="empl_id${a.aOid}" size="12" value="${a.cname}" onFocus="chInput(this.id)"
				onkeyup="if(this.value.length>=1)GgetAny(this.value, 'empl_id${a.aOid}', 'empl_name${a.aOid}', 'dempl', 'name')" />
 				
 				
 				<input onFocus="chInput(this.id)"
 				autocomplete="off" style="ime-mode:disabled"
				onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成身分證號', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"
				type="text" name="empl_id" id="empl_name${a.aOid}" size="10" value="${a.idno}"
	 			onkeyup="if(this.value.length>0)GgetAny(this.value, 'empl_name${a.aOid}', 'empl_id${a.aOid}', 'dempl', 'no')" />
				
				
				
				</td>
				
				<td class="hairLineTdF">
				<input type="submit" name="method" 
					value="<bean:message key='Save'/>" 
					id="Continue" class="gSubmitsmall">
				</td>
			</tr>
			</c:forEach>
		</table>
		
		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		help
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
							
		
		<input type="button" class="gCancle" value="返回" id="back"
					onclick="location='/CIS/AMS/Directory.do';"
					onMouseOver="showHelpMessage('返回', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
				
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>


	
	
	
</html:form>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>