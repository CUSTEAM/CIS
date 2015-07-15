<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<script type="text/javascript">
<!--
noMenu = false;
openMenu();
function resetTimeOut(){
	if(myTimeOut != null) {
		globalTimeOut = myTimeOut;
		myTimeOut = null;
	}else{
		globalTimeOut = 1800000;
	}
}

resetTimeOut;

//-->
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<script>generateTableBanner('<div class="gray_15"><B>${DirectoryBanner}</B></div>');</script>
	<tr height="25">
		<td></td>
	</tr>
	<tr align="center" valign="top">
		<td>
			<table border="0">
			<c:set var="total" scope="page" value="${sessionScope.ModuleList}"></c:set>
			<c:if test="${not empty ModuleList}">
				<c:forEach items="${ModuleList}" var="module" varStatus="status">
				<c:if test="${status.index % 2 == 0}">
				<tr height="30">
				<td><html:link page="${module.action}"><font class="gray_15">${module.label}</font></html:link></td>
				<td width="20"></td>
				</c:if>
				<c:if test="${status.index % 2 == 1}">
				<td>${module.icon}<html:link page="${module.action}"><font class="gray_15">${module.label}</font></html:link></td>				
				</c:if>									
				</c:forEach>				
			</c:if>
			</table>
		</td>
	</tr>
	<tr height="5">
		<td></td>
	</tr> 
	<tr height="1">
		<td bgcolor="blue"></td>
	</tr> 
	<tr height="5">
		<td></td>
	</tr> 
	<tr align="left" valign="top">
		<td>
		<table>
			<tr>
				<td>&nbsp;</td>
				<td><a href="/CIS/pages/downloads/ScoreNotice.doc" target="_blank"><font color="red" size="+1">日間部教師成績上網登錄注意事項下載</font></a></td>
			</tr>
			<tr><td width="30%" align="right" valign="top">成績上傳時間</td>
			<td>
			<c:set var="depart" scope="page" value=""></c:set>
			<table width="399" border="0">
			<c:if test="${not empty optime1}">
				<c:forEach items="${optime1}" var="optime" varStatus="status">
					<c:if test="${depart != optime.depart}">
					  <tr>
					    <td width="50" bgcolor="lightblue"><div align="left">部別</div></td>
					    <td colspan="3" bgcolor="lightblue"><div align="center">${optime.departName}</div></td>
					  </tr>
					</c:if>
					<c:if test="${status.index % 3 == 0}">
					  <tr>
						<td rowspan="3" bgcolor="lightblue"><div align="left">考試別</div></td>
				    	<td bgcolor="silver"><div align="left">${optime.levelName}</div></td>
				    	<td colspan="2" bgcolor="silver"><div align="center">開始：${optime.beginDate}&nbsp;&nbsp;&nbsp;截止：${optime. endDate}</div></td>
				  	  </tr>
					</c:if>
					<c:if test="${status.index % 3 != 0}">
						<tr>
				    		<td bgcolor="silver"><div align="left">${optime.levelName}</div></td>
				    		<td colspan="2" bgcolor="silver"><div align="center">開始：${optime.beginDate}&nbsp;&nbsp;&nbsp;截止：${optime. endDate}</div></td>
				  		</tr>
					</c:if>
					<c:set var="depart" scope="page" value="${optime.depart}"></c:set>
				</c:forEach>				
			</c:if>
			</table>
			</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr height="25">
		<td></td>
	</tr> 
	<script>generateTableBanner('');</script>
</table>