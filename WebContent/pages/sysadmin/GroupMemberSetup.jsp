<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/SysAdmin/GroupMemberSetup" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.GroupMemberSetup" bundle="ADM"/></B></div>');</script>	  
		<tr><td align="center">
	       <table cellspacing="5" class="empty-border">
			 <tr><td>群組&nbsp;
			 		<SELECT name="group" onchange="forms[0].submit();">
			 			<OPTION value=""></OPTION>
			 		  <c:forEach items="${UserGroups}" var="code5">
						<OPTION value="${code5.idno}" <c:if test="${code5.idno==GroupMemberSetup.group}">selected</c:if>>${code5.name}</OPTION>
	  				  </c:forEach>
			 		</SELECT></td></tr>
   <c:if test="${GroupMemberSetup.group == ''}">
   		   </table></td></tr>
   	<script>generateTableBanner('');</script>
   </c:if>  	   
   <c:if test="${GroupMemberSetup.group != ''}">
			<tr><td>新成員帳號&nbsp;
				   <input type="text"   name="empIdno" size="12" value="${GroupMemberSetup.empIdno}" >
				   <input type="submit" name="method"  value="<bean:message key='Add'/>" ></td></tr>
		  </table></td></tr>
		<tr><td width="100%">
		  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	      <display:table name="${MemberList}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
	        <%@ include file="../include/NoBanner.jsp" %>
	        <display:column title="<script>generateTriggerAll(${fn:length(MemberList)}, 'GroupMember');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "GroupMember");</script></display:column>
	        <display:column titleKey="Student.Name"	property="cname"	sortable="true" class="center" />
	        <display:column titleKey="Student.Idno"	property="idno"		sortable="true" class="center" />
	        <display:column title="單     位" 			property="unit2" 	sortable="true" class="center"/>
	        <display:column title="職     級" 			property="pcode2" 	sortable="true" class="center"/>
	      </display:table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Remove'/>" class="CourseButton" onclick="return checkSelectForDelete(\'GroupMember\');" >');</script>
   </c:if>  
  </html:form>
</table>
