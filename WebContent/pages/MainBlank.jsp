<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%@ page import="org.apache.struts.Globals" %>

<html:html>
<HEAD><TITLE><bean:message key="LoginForm.title" /></TITLE>
  <html:base/>
  
<c:choose>

<c:when test="${bgColor==null}">
<LINK href="images/home.css" type=text/css rel=stylesheet>
<link href="images/decorate.css" type="text/css" rel="stylesheet" media="screen">
</c:when>

<c:when test="${bgColor!=null}">
<LINK href="images/${bgColor.home}.css" type=text/css rel=stylesheet>
<link href="images/${bgColor.decorate}.css" type="text/css" rel="stylesheet" media="screen">
</c:when>

</c:choose>

  <Link href="score/chit.css" type="text/css" rel="stylesheet" media="screen">
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
  <META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW">
  
</HEAD>
<body>
<script language="javascript">
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
	var path = "Logout.do";
	for (var i=5; i<tokens.length; i++) {
		path = "../" + path;
	}
	window.parent.location = path;
  };
  
  function setGlobalTimeOut(){
  	logoutTimer=setTimeout('autoLogout()', globalTimeOut);
  };
</script>
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
 <TABLE cellSpacing=0 cellPadding=0 width=100% border=0>
    <TR>
   	  <td colspan="2" width="100%"><c:import url="/pages/header.jsp"/></td>
 	</TR>

	<tr height="1">
		<td colspan="3" width="100%"></td>
	</tr>
	<tr height="1">
		<td colspan="3" width="100%" class="fullColorTable"></td>
	</tr>
	<tr height="20" >
		<td></td>
	</tr>
</TABLE>
<center>
  <TABLE cellSpacing=0 cellPadding=0 width=98% border=0>
    <TR>
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
</center>

<script type="text/javascript">
if(isAutoLogout){
	setGlobalTimeOut();
};
</script>
</body>	
</html:html>