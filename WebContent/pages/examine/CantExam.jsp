<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

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
<form action="/CIS/Examine/CantExam.do" method="post" name="ceForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudCantExam" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
       	<td><font color=red><b>列印前請先詢問學務單位是否曠缺輸入完畢，並記得先執行 [更新學生選課曠缺課資料] </b></font></td>
      </tr>
      <tr>
        <td  height="30" colspan="6" align="left" valign="middle">班級：
        	<c:set var="campusSel" value="${CantExamInit.campus}"/>
	  		<c:set var="schoolSel" value="${CantExamInit.school}"/>
	  		<c:set var="deptSel"   value="${CantExamInit.dept}"/>
	  		<c:set var="classSel"  value="${CantExamInit.departClass}"/>
			<%@ include file="/pages/include/ClassSelect.jsp" %>
        </td>
      </tr>
      <tr>
      	<td>查詢範圍：
      		<input type="radio" name="scope" value="0" checked>全部&nbsp;&nbsp;&nbsp;
      		<input type="radio" name="scope" value="1">畢業班&nbsp;&nbsp;&nbsp;
      		<input type="radio" name="scope" value="2">除畢業班&nbsp;&nbsp;&nbsp;
      	</td>
      </tr>
      <tr>
      	<td>排序方式：
      		<input type="radio" name="sorttype" value="0">以學制開課為主&nbsp;&nbsp;&nbsp;
      		<input type="radio" name="sorttype" value="1" checked>以學制學生為主&nbsp;&nbsp;&nbsp;
      	</td>
      </tr>
      <tr>
      	<td>差幾節就會扣考：
      		<input type="text" name="range" value="0" size="5">
      	</td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${CantExamList != null}" >
	    <tr><td height="10"></td></tr>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${CantExamList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty CantExamList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	  			<c:choose>
	  			<c:when test="${CantExamInit.sorttype == '0'}">
 	        	<display:column title="扣考科目"	property="subjectName"		sortable="true" 	class="left" />
 	        	<display:column title="班級"		property="stDeptClassName"	sortable="true"  	class="left" />
	        	<display:column title="學號"		property="studentNo"		sortable="true"  	class="left" />
	        	<display:column title="姓名"		property="studentName"	sortable="false"  	class="left" />
	        	<display:column title="每週節數" 	property="period" 		sortable="false" 	class="center" />
	        	<display:column title="曠缺節數" 	property="timeOff" 		sortable="false"  	class="center" />
	        	<display:column title="扣考節數" 	property="tfLimit"		sortable="false" 	class="center" />
	  			</c:when>
	  			<c:when test="${CantExamInit.sorttype == '1'}">
 	        	<display:column title="班級"		property="stDeptClassName"	sortable="true"  	class="left" />
	        	<display:column title="學號"		property="studentNo"		sortable="true"  	class="left" />
	        	<display:column title="姓名"		property="studentName"	sortable="false"  	class="left" />
 	        	<display:column title="扣考科目"	property="subjectName"	sortable="true" 	class="left" />
	        	<display:column title="每週節數" 	property="period" 		sortable="false" 	class="center" />
	        	<display:column title="曠缺節數" 	property="timeOff" 		sortable="false"  	class="center" />
	        	<display:column title="扣考節數" 	property="tfLimit"		sortable="false" 	class="center" />
	  			</c:when>
	  			</c:choose>
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	    <c:if test="${! empty CantExamList}">
    	<tr>
    		<td><br>選擇輸出格式:
    			<a href="examine/CantExamExport.jsp?type=excel">
    			<img src="images/ico_file_excel.png" border="0"> Excel
    			</a>
    			<a href="examine/CantExamExport.jsp?type=word">
    			<img src="images/ico_file_word.png" border="0"> Word
    			</a>
    		</td>
    	</tr>
	  	</c:if>
	    
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
