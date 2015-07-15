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

<form action="/CIS/StudAffair/StudTimeOff.do" method="post" name="tfdForm">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<bean:message key="stfTitle.StudTimeOffDelete" bundle="SAF"/>');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="學生請假資料維護"/> -&gt; 
        <span class="style2">刪除確認
        </span></td>
  	    </tr>
      <tr>
        <td  height="35" colspan="6" align="left" valign="middle" bgcolor="#CCCCFF">學號：
        	<c:out value="${ScoreStuMap.studentNo}"/>&nbsp;&nbsp;
				 	 <font class="blue_13"><c:out value="${ScoreStuMap.studentName}" />&nbsp;
				 	 					   <c:out value="${ScoreStuMap.depClassName}"/></font>
		</td>
      </tr>
	</table>
				
		</td>		
	</tr>
	<!-- Test if have Query Result  -->
	<c:if test="${StudTimeOffDelete != null}" >
	    <tr><td height="10"></td></tr>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">  
	  		<c:choose>
	  		<c:when test="${StudTimeoffInit.daynite == '1'}">
	      		<display:table name="${StudTimeOffDelete}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        	<display:column title="日期"	property="sddate"	sortable="true" class="left" />
 	        	<display:column title="班級"	property="deptClassName"	sortable="true" class="left" />
	        	<display:column title="姓名"	property="studentName"	sortable="true" class="left" />
 	        	<display:column title="升旗"	property="absName0"		sortable="false" class="center" />
	        	<display:column title="１"	property="absName1"		sortable="false" class="center" />
	        	<display:column title="２"	property="absName2"		sortable="false" class="center" />
	        	<display:column title="３" 	property="absName3" 	sortable="false" class="center" />
	        	<display:column title="４" 	property="absName4" 	sortable="false" class="center" />
	        	<display:column title="５" 	property="absName5"		sortable="false" class="center" />
	        	<display:column title="６" 	property="absName6"		sortable="false" class="center" />
	        	<display:column title="７" 	property="absName7"		sortable="false" class="center" />
	        	<display:column title="８" 	property="absName8"		sortable="false" class="center" />
	        	<display:column title="９" 	property="absName9"		sortable="false" class="center" />
	        	<display:column title="10" 	property="absName10"	sortable="false" class="center" />
	        	<display:column title="11" 	property="absName11"	sortable="false" class="center" />
	        	<display:column title="12" 	property="absName12"	sortable="false" class="center" />
	        	<display:column title="13" 	property="absName13"	sortable="false" class="center" />
	        	<display:column title="14" 	property="absName14"	sortable="false" class="center" />
	        	<display:column title="15" 	property="absName15"	sortable="false" class="center" />
	        	</display:table>
	        </c:when>
	  		<c:when test="${StudTimeoffInit.daynite == '2'}">
	      		<display:table name="${StudTimeOffDelete}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
 	        	<display:column title="日期"	property="sddate"	sortable="true" class="left" />
 	        	<display:column title="班級"	property="deptClassName"	sortable="true" class="left" />
	        	<display:column title="姓名"	property="studentName"	sortable="true" class="left" />
  	        	<display:column title="升旗"	property="abs0"		sortable="false" class="center" />
	        	<display:column title="１"	property="absName1"		sortable="false" class="center" />
	        	<display:column title="２"	property="absName2"		sortable="false" class="center" />
	        	<display:column title="３" 	property="absName3" 	sortable="false" class="center" />
	        	<display:column title="４" 	property="absName4" 	sortable="false" class="center" />
	        	<display:column title="５" 	property="absName5"		sortable="false" class="center" />
	        	<display:column title="６" 	property="absName6"		sortable="false" class="center" />
	        	<display:column title="７" 	property="absName7"		sortable="false" class="center" />
	        	<display:column title="８" 	property="absName8"		sortable="false" class="center" />
	        	<display:column title="９" 	property="absName9"		sortable="false" class="center" />
	        	<display:column title="10" 	property="absName10"	sortable="false" class="center" />
	        	<display:column title="N1" 	property="absName11"	sortable="false" class="center" />
	        	<display:column title="N2" 	property="absName12"	sortable="false" class="center" />
	        	<display:column title="N3" 	property="absName13"	sortable="false" class="center" />
	        	<display:column title="N4" 	property="absName14"	sortable="false" class="center" />
	        	<display:column title="N5" 	property="absName15"	sortable="false" class="center" />
	        	</display:table>
	        </c:when>
	  		<c:when test="${StudTimeoffInit.daynite == '3'}">
	      		<display:table name="${StudTimeOffDelete}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
 	        	<display:column title="日期"	property="sddate"	sortable="true" class="left" />
 	        	<display:column title="班級"	property="deptClassName"	sortable="true" class="left" />
	        	<display:column title="姓名"	property="studentName"	sortable="true" class="left" />
  	        	<display:column title="升旗"	property="absName0"		sortable="false" class="center" />
	        	<display:column title="１"	property="absName1"		sortable="false" class="center" />
	        	<display:column title="２"	property="absName2"		sortable="false" class="center" />
	        	<display:column title="３" 	property="absName3" 	sortable="false" class="center" />
	        	<display:column title="４" 	property="absName4" 	sortable="false" class="center" />
	        	<display:column title="５" 	property="absName5"		sortable="false" class="center" />
	        	<display:column title="６" 	property="absName6"		sortable="false" class="center" />
	        	<display:column title="７" 	property="absName7"		sortable="false" class="center" />
	        	<display:column title="８" 	property="absName8"		sortable="false" class="center" />
	        	<display:column title="９" 	property="absName9"		sortable="false" class="center" />
	        	<display:column title="10" 	property="absName10"	sortable="false" class="center" />
	        	<display:column title="11" 	property="absName11"	sortable="false" class="center" />
	        	<display:column title="12" 	property="absName12"	sortable="false" class="center" />
	        	<display:column title="13" 	property="absName13"	sortable="false" class="center" />
	        	<display:column title="14" 	property="absName14"	sortable="false" class="center" />
	        	<display:column title="15" 	property="absName15"	sortable="false" class="center" />
	        	</display:table>
	        </c:when>
	        <c:otherwise>
	     		<%@ include file="../include/NoBanner.jsp" %>
	        </c:otherwise>
	        </c:choose>
 	      
 	      </td></tr>	      
	      </table>
	     </td>
	    </tr>
<!-- Begin Content Page Table Footer -->
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='DeleteConfirm'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</c:if>
	
</table>
<script>history.go(1);</script>
<!-- End Content Page Table Footer -->
</form>
