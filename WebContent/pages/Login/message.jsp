<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%@ page import="org.apache.struts.Globals" %>
<logic:present name="<%=Globals.ERROR_KEY%>">
<table width="1%" id="sysmsg" style="position:absolute; z-index:32768; right:15px; top:10px;ilter:Alpha(opacity:0.85);filter:alpha(opacity=85);
	opacity:0.85; -moz-opacity:0.85; -khtml-opacity:0.85;" align="center"><tr><td width="45%"></td><td nowrap width="1%">
<div class="out">
	<div class="inred ltin tpin" style="padding:50px;">
	<input type="button" onClick="showObj('sysmsg')" style="position:absolute; right:10px; top:5px;" class="gSubmitSmall" value="關閉" />
	<font size="+2"><html:errors/></font>
	</div>
</div>
</td>
</td></tr></table>
</logic:present>
<logic:present name="<%=Globals.MESSAGE_KEY%>">
<table width="1%" id="syserr" style="position:absolute; z-index:32768; right:15px; top:10px; ilter:Alpha(opacity:0.85);filter:alpha(opacity=85);
	opacity:0.85; -moz-opacity:0.85; -khtml-opacity:0.85;" align="center"><tr><td width="45%"></td><td nowrap width="1%">
<div class="out">
	<div class="inyel ltin tpin" style="padding:50px;">
	<input type="button" onClick="showObj('syserr')" style="position:absolute; right:10px; top:5px;" class="gSubmitSmall" value="關閉" />
	<font size="+2"><html:messages id="msg" message="true">${msg}</html:messages></font>
	</div>
</div>
</td>
</td></tr></table>		
</logic:present>

<table width="1%" id="pagemsg" style="position:absolute; z-index:32768; left:15px; top:10px;ilter:Alpha(opacity:0.85);filter:alpha(opacity=85);
opacity:0.85; -moz-opacity:0.85; -khtml-opacity:0.85; display:none;" align="center"><tr><td width="45%"></td><td nowrap width="1%">
<div class="out">
	<div class="ingreen ltin tpin" style="padding:50px;">
	<input type="button" onClick="showObj('pagemsg');document.getElementById('loadMsg').style.display='none';" style="position:absolute; right:10px; top:5px;" class="gSubmitSmall" value="關閉" />
	<div id="pagemsgInfo" style="font-size:18px;">
	已30分鐘未更新畫面, 系統將於5分鐘後登出<br>是否要延長？<br><br>
	<input type="button" class="gGreen" value="延長30分" onClick="clearTimeout(logoutTimer); timepluse=window.open('/CIS/getOnleneMsg','tp','width=400,height=300,location=no,menubar=no,resizable=yes,screenX=175,screenY=175,status=no,toolbar=no'); timepluse.close(); showObj('pagemsg');document.getElementById('loadMsg').style.display='none';"/> 或
	<input type="button" class="gCancel" value="立即登出" onClick="window.location.href='/CIS/Logout.do'"/><br>
	<input type="checkbox" checked disabled/>不再提醒
	</div>
	</div>
</div>
</td>
</td></tr></table>