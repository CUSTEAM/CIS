<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/ConductMarkCode.do" method="post" name="cmcForm">
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.ConductMarkCodeMaintain" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" align="center" valign="middle">代碼：
          <input name="no" type="text" size="5" maxlength="20" value="${ConductMarkCodeInit.no}" />
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="method" value="<bean:message key='Create'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${ConductMarkCodeList != null}" >
	    <tr><td height="10"></td></tr>
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">  
	      <display:table name="${ConductMarkCodeList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		<c:if test="${empty ConductMarkCodeList}">
	     		<%@ include file="../include/NoBanner.jsp" %>
	  		</c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(ConductMarkCodeList)}, 'cooki_ConductMarkCode');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "cooki_ConductMarkCode");</script></display:column>
 	        <display:column title="代碼"		property="no"		sortable="true" class="left" />
	        <display:column title="評語"		property="name"		sortable="true" class="left" />
 	      </display:table></td></tr>	      
	      			</table>
	      		</td>
	      	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'cooki_ConductMarkCode\');">');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
