<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/EntranceDocNo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.DeleteEntranceDocNo" bundle="REG"/></B></div>');</script>	  
	  <tr height="50"><td align="center" class="blue_15">
	  	<bean:message key="DeleteEntranceDocNoVerify" bundle="REG"/>&nbsp;
	  	<input type="submit" name="method" class="CourseButton" value="<bean:message key='DeleteConfirm'/>">&nbsp;&nbsp;
	  	<input type="submit" name="method" class="CourseButton" value="<bean:message key='Cancel'/>"></td></tr>
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${EntranceDocNoDelete}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
	        <%@ include file="../include/NoBanner.jsp" %>
	        <display:column title="<script>generateTriggerAll(${fn:length(EntranceDocNoDelete)}, 'EntrnoDelete');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "EntrnoDelete");</script></display:column>
	        <display:column title="起始學號"			property="firstStno"	sortable="true" class="center" />
	        <display:column title="終止學號" 			property="secondStno" 	sortable="true" class="center" />
	        <display:column title="入 學 資 格 核 准 文 號" 	property="permissionNo" sortable="true" />
	      </display:table></td></tr>
	    </table></td></tr>
       	<script>generateTableBanner('');</script>
  </html:form>
</table>	      	
<script>history.go(1);</script>	  