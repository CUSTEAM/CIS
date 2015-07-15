<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/eis/inc/js/jquery.js"></script>
<style>

#mainmenu ul, #mainmenu ul li {	
	list-style: none;
}

li {
	border-width:1px;	
	border-bottom-style:dotted;
}

#mainmenu ul {	
	font-weight: bold;
}

.nav-header{
	display:none;
}

.divider{
	display:none;
}

.dropdown-menu{
	display:none;
}

.brand{
	display:none;
}

#xLogout{
	display:none;
}
</style>



<script>
$(document).ready(function() { 	
	<c:if test="${fn:indexOf(cookie['unit'].value, 'A')!=-1}">
	$("#mainmenu").load("/eis/jsp/decorators/menu.jsp?r="+Math.floor(Math.random()*11));
	</c:if>
	<c:if test="${fn:indexOf(cookie['unit'].value, 'A')==-1}">
	$("#mainmenu").load("/stis/jsp/decorators/menu.jsp?r="+Math.floor(Math.random()*11));
	</c:if>
	
	
	
	$("#mainmenu").delegate(".dropdown","click",function(){
		$(this).children(".dropdown-menu").css("display","block");   
	});
	
	$("#mainmenu").delegate(".dropdown-submenu","click",function(){
		   $(this).children(".dropdown-menu").css("display","block");		   
	});	
});
</script>
<div id="mainmenu" style="font-size:18px;"></div>		