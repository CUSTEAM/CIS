<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %><br><br>
<FORM>
    <TABLE cellSpacing=0 cellPadding=0 border=0 align="right" width="90%">
    	<logic:present name="Menu">
    	  <c:forEach items="${Menu.items}" var="menuItem" varStatus="status">
			<c:if test="${not empty menuItem.subMenu}" >
       	  	  <c:if test="${menuItem.collapse}" >
       	        <!-- Let round frame appear -->
    			<TR>
 			    <TD width="100%">			
				  <div id="nifty">
					<div class="rtop">
						<div class="r1"></div>
						<div class="r2"></div>
						<div class="r3"></div>
						<div class="r4"></div>
					</div>
				  </div>
				  <table width="100%" cellspacing="0" cellpadding="0" border="0"><tr>
				    <TD class="fullColorTable">
			  </c:if>
			</c:if>
			<c:if test="${empty menuItem.subMenu || not menuItem.collapse}">
			  <TR height="35" valign="center">
		 		<TD class="table-boder" width="100%">
		 	</c:if>   				  				
			&nbsp;<html:link page="${menuItem.module.action}"><font class="gray_15"><B>${menuItem.module.label}</B></font></html:link>
		  	</TD>
       	  </TR>
       <c:if test="${not empty menuItem.subMenu}" >
        <c:if test="${menuItem.collapse}" >
        </table>
         <TR><TD>
         	  <TABLE cellSpacing=0 cellPadding=0 border=0>
         	  	<c:forEach items="${menuItem.subMenu.items}" var="subItem">
         	  	  <TR height="35">
         	  	    <TD width=17>
         	  	   <c:if test="${not empty subItem.module.icon}">

		        <TD width=30>
		          <html:link page="${subItem.module.action}.do"><IMG border=0 height=32 width=26 src="images/${subItem.module.icon}" 
		          	   onclick='javascript:window.location="${application.context}${subItem.module.action}.do"'/></html:link></TD>
		       </c:if>
		        <TD class="table-boder" width="100%" valign="center">
		          &nbsp;&nbsp;<html:link page="${subItem.module.action}"><font class="gray_15">${subItem.module.label}</font></html:link></TD>
         	  	  </TR>
         	  	</c:forEach>
         	  	
         	  	<!-- Left round frame -->
         	  	<div class="rtop">
				<div class="r4"></div>
				<div class="r3"></div>
				<div class="r2"></div>
				<div class="r1"></div>
			</div>
         	  </TABLE></TD></TR>
         </c:if>
       </c:if>
     </c:forEach>
      </logic:present>
      
   	    <TR height="35">
  	  <TD class="table-boder" width="100%" valign="center">
          &nbsp;<html:link page="/InetLogout.do" onclick="CloseWindow('tools');"><font class="gray_15"><B><bean:message key="Logout"/></B></font></html:link></TD>
      </TR>
	</TABLE>
</FORM>        
  <script>
  	  var objNewWindow;
	  function OpenWindow(page,name) {
		  //this.pages=new String(page);
		  window.status = "";
		  strFeatures = "top=0,left=0,width=390,height=307,z-look=yes,toolbar=0,titlebar=0,location=0,directories=0,menubar=0,scrollbars=1";
		  if (objNewWindow != null) {
		  	if (!objNewWindow.closed) {
		  		objNewWindow.focus();
		  		return
		  	}
		  }
		  objNewWindow = window.open('', name, strFeatures);
		  //alert(objNewWindow.location.href);
		  if (objNewWindow.location.href == 'about:blank' || objNewWindow.location.href == '') {
		  	objNewWindow = window.open(page, name, strFeatures);
		  } else {
		  	objNewWindow.focus();
		  }
	  };
	  
	  function CloseWindow(name) {
	  	objNewWindow = window.open('', name, 'width=10,height=10,z-look=no,toolbar=0,titlebar=0,location=0,directories=0,menubar=0,scrollbars=0');
	  	objNewWindow.close();
	  };
	  
  </script>

	

