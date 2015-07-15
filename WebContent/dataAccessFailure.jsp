<%@ page language="java" contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ include file="taglibs.jsp" %>  
<html:html locale="true">
<HEAD><TITLE><bean:message key="LoginForm.title" /></TITLE>
  <html:base/>
  <LINK href="pages/images/home.css" type=text/css rel=stylesheet>
  <link href="pages/images/decorate.css" type="text/css" rel="stylesheet" media="screen">
  <Link href="pages/score/chit.css" type="text/css" rel="stylesheet" media="screen">
    
    <title>dataAccessFailure.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <table>
    	<tr>
    		<td>
    			<img src="pages/images/blocked1.png">
    		</td>
    		<td align="left">
    			data Access Failure
    		</td>
    	</tr>
    	<tr>
    		<td></td>
    		<td>
				<!-- %
				 Exception ex = (Exception)  
				 request.getAttribute("org.apache.struts.action.EXCEPTION");
				 ex.printStackTrace(new java.io.PrintWriter(out));
				 %-->
			</td>
		</tr>
    </table>
  </body>
</html:html>
