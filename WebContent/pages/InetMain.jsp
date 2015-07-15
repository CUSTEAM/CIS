<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<html:html locale="true">
<HEAD><TITLE><bean:message key="LoginForm.title" /></TITLE>
  <html:base/>
  <LINK href="images/home.css" type=text/css rel=stylesheet>
  <link href="images/decorate.css" type="text/css" rel="stylesheet" media="screen">
  <Link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
</HEAD>
<script language="javascript">
<!--
  var globalTimeOut = 1800000;
  var logoutTimer;
  isAutoLogout = new Boolean(true);
  
  function autoLogout(){
  	try {
	  	objNewWindow = window.open('', 'tools', '');
	  	objNewWindow.close();
    } catch(err) {}
	var loc = new String(window.location);
	var tokens = loc.split("/");
	var path = "InetLogout.do";
	for (var i=5; i<tokens.length; i++) {
		path = "../" + path;
	}
	window.parent.location = path;
  };
  
  function setGlobalTimeOut(){
  	logoutTimer=setTimeout('autoLogout()', globalTimeOut);
  };
// -->
</script>
<BODY>
<!-- <BODY onload="window.setTimeout('autoLogout()', 1800000)"> -->
<table height="100%" width="100%" class="transparent" cellpadding="0" cellspacing="0" name="loadMsg" id="loadMsg" style="display: none;">
		<tr>
			<td align="center" valign="middle" >
				<!-- 漸層背景 -->
				<img src="images/indicator.gif" style="display: none;"/>
			</td>
		</tr>
	</table>
	<table class="non_transparent"  cellpadding="0" cellspacing="0" name="loadIco" id="loadIco" style="display: none; ">
		<tr>
			<td id="loadMsgSub">
			<!-- 等待訊息 -->
			</td>
		</tr>
	</table>
  <TABLE cellSpacing=0 cellPadding=0 width=98% border=0>
    <TR>
   	  <td colspan="2" width="100%"><c:import url="/pages/header.jsp"/></td>
 	</TR>
    <TR>
      <!-- Here comes menu bar -->
      <TD vAlign="top" align="right" width="20%">
        <jsp:include page="InetMenu.jsp"/>
      </TD>
      <TD align="left" valign="top" width="100%">
        <TABLE width="100%" cellspacing="0" cellpadding="0" border="0">

          <TR>
          	<TD align="left" valign="top" width="100%">
         	  <div id="nifty">
 				<div class="rtop">
 					<div class="r1f"></div>
 					<div class="r2f"></div>
 					<div class="r3f"></div>
 					<div class="r4f"></div>
 				</div>
 			  </div>
 			  <table width="100%" cellspacing="0" cellpadding="0" border="0">
 				 <tr>
 					<td width="3" class="fullColorTable"></td>
 					<td height="100%" class="fullColorTableSub" align="left" valign="top">
 						<!-- Here comes content page -->
          				<jsp:include page="${contentPage}"/>
          			</td>
          			<td width="3" class="fullColorTable"></td>
          		 </tr>
        	  </table>
              <div class="rtop">
					<div class="r4f"></div>
					<div class="r3f"></div>
					<div class="r2f"></div>
					<div class="r1f"></div>
			  </div>
			</TD>
		  </TR>
		</TABLE>
		<c:import url="floored.jsp"/>
      </TD>
    </TR>
  </TABLE>
<script type="text/javascript">
<!--
if(isAutoLogout){
	setGlobalTimeOut();
};
// -->
</script>
</BODY>
</html:html>