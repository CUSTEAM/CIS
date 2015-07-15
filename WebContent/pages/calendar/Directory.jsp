<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<!-- html:form action="/Calendar/SubDirectory" enctype="multipart/form-data" method="post" onsubmit="init('處理中, 請稍後')"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">

	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/calendar_3.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">行事曆</font></div>		
		</td>
	</tr>
	<tr>
		<td>		
		
		<!-- iframe id="embeddedhtml" src ="/CIS/pages/calendar/calendar.jsp" width="100%" height="760" scrolling="no" frameborder="0"></iframe-->		
		<object id="embeddedhtml" type="text/html" data="/CIS/pages/calendar/calendar.jsp" width="100%" height="800"></object>
		</td>	
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">		
		
				
		</td>	
	</tr>
</table>
<!--/html:form-->

