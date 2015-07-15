<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/EntranceDocNoEdit" method="post">
    <input type="hidden" name="mode" value="${EntranceDocNoEdit.mode}">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.EntranceDocNo" bundle="REG"/></B></div>');</script>	  
		<tr><td>
	       <table width="100%" cellspacing="5" class="empty-border">
			 <tr><td>起始學號&nbsp;
			 		<input type="text" name="startNo" size="12" value="${EntranceDocNoEdit.startNo}"></td>
			 	 <td>終止學號&nbsp;
			 	 	<input type="text" name="endNo"   size="12" value="${EntranceDocNoEdit.endNo}"></td></tr>
  			 <tr><td colspan="2">核准文號&nbsp;
					<input type="text" name="docNo"   size="40" value="${EntranceDocNoEdit.docNo}"></td></tr>
     	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'
     						  + '<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Cancel' />" >');</script>
  </html:form>
</table>
