<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/CodeSelection.js" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
// -->
</style>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/StudInspectedCreate.do" method="post" name="ceForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudInspectedCreate" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <c:set var="tcnt" value="1"/>
      <tr>
        <td  height="30" colspan="6" align="left" valign="middle">請選擇部制 ：
	  		<c:set var="codeId" 		 	value="campus"/>
	  		<c:set var="codeIdInitValue" 	value=""/>
	  		<c:set var="codeSel"		 	value="CodeSel"/>
	  		<c:set var="codeList"			value="${CampusDepartment}"/>
	  		<%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='OK'/>" onClick="return reconfirm();">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' />" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudTermInspectedList != null}" >
	    <tr><td height="10"></td></tr>
		<tr>
		    <td><table width="50%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudTermInspectedList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudTermInspectedList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
 	        	<display:column title="班級"		property="departClass"		sortable="true" 	class="left" />
 	        	<display:column title="班級名稱"	property="deptClassName"	sortable="false"  	class="left" />
	        	<display:column title="學號"		property="studentNo"		sortable="true"  	class="left" />
	        	<display:column title="姓名" 	property="studentName" 		sortable="false"  	class="left" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	    <c:if test="${! empty StudTermInspectedList}">
    	<tr>
    		<td><br>預覽列印:
    			<a href="studaffair/StudInspectedReport-viewer.jsp" target="_blank">
    			<img src="images/ico_file_excel.png" border="0">
    			</a>
    		</td>
    	</tr>
	  	</c:if>
	    
	</c:if>
	

<!-- Begin Content Page Table Footer -->
</table>
<script type="text/javascript">
function reconfirm(){
	return confirm("確定要建立本學期定察學生資料?");
}
</script>		
</form>
