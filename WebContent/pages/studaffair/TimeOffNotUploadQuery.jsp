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

A.blue_t13:link {
	FONT-SIZE: 13.5px;
	COLOR: #3366cc;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION: none
}

A.blue_t13:visited {
	FONT-SIZE: 13.5px;
	COLOR: #3366dd;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION: none
}

A.blue_t13:hover {
	FONT-SIZE: 13.5px;
	COLOR: #ff99cc;
	LINE-HEIGHT: 18px
}

A.blue_t13:active {
	FONT-SIZE: 13.5px;
	COLOR: #333333;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION: none
}

// -->
</style>
<script>
	function generateTFUpload(dtOid, tdate, teacherId, label){
		var url = "/CIS/StudAffair/TeacherTimeOffInputPatch.do?mode=patch&dtOid=" + dtOid + "&tdate=" + tdate +  "&teacherId=" + teacherId + "&sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999);
		document.write("<a href=\"" + url + "\" class=\"blue_t13\">" + label + "</a>");	
	}
	
</script>


<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/TimeOffNotUploadQuery.do" method="post" name="bpForm" onsubmit="init('查詢中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.TimeOffNotUploadQuery" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">班級：      
        <td  height="30" align="left" valign="middle">
        	<c:set var="campusSel" value="${TFnotUploadQueryInit.campus}"/>
	  		<c:set var="schoolSel" value="${TFnotUploadQueryInit.school}"/>
	  		<c:set var="deptSel"   value="${TFnotUploadQueryInit.dept}"/>
	  		<c:set var="classSel"  value="${TFnotUploadQueryInit.clazz}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %>
        </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">教師ID：      
      	<td>
      		<input type="text" name="teacherId"  value="${TFnotUploadQueryInit.teacherId}" maxlength="11">
      	</td>
      </tr>
        <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">起始日期：      
      	<td>
      		<input type="text" name="DateStart" maxlength="9" value="${TFnotUploadQueryInit.DateStart}">(格式:YY/MM/DD)
      	</td>
      </tr>
      <tr>
         <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">結束日期：      
      	<td>
      		<input type="text" name="DateEnd" maxlength="9" value="${TFnotUploadQueryInit.DateEnd}">(格式:YY/MM/DD)
     	</td>
      </tr>
      <tr>
         <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">報表型態：      
      	<td>
      		<input type="radio" name="pmode" value="1">明細表&nbsp;&nbsp;
      		<input type="radio" name="pmode" value="2">統計表(僅限以班級查詢)
     	</td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'+
	'<Input type="submit" name="method" value="<bean:message key='Cancel'/>" >');</script>
	
	<c:if test="${TFnotUploadList != null}">
    <tr>
    	<td style="background-color: #ccbbff;"><br>選擇輸出格式:
    		<a href="studaffair/TimeOffNotUploadPrint.jsp?type=excel">
    		<img src="images/ico_file_excel.png" border="0"> Excel
    		</a>
    		<a href="studaffair/TimeOffNotUploadPrint.jsp?type=word">
    		<img src="images/ico_file_word.png" border="0"> Word
    		</a>
    	</td>
    </tr>
    <tr><td height="3"></td></tr>
	</c:if>
	
	<!-- Test if have Query Result  -->
	<c:if test="${TFnotUploadList != null}">
		<tr>
			<td>
			<c:if test="${TFnotUploadQueryInit.pmode=='1'}">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr><td align="center">  
		      		<display:table name="${TFnotUploadList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
		  			<c:if test="${empty TFnotUploadList}">
		     			<%@ include file="../include/NoBanner.jsp" %>
		  			</c:if>
 	 	        	<display:column title="教師"		property="teacherName"		sortable="true"  	class="left" />
 					<c:if test="${allowPatch=='yes'}">
	 	        		<display:column title="科目"		sortable="true" class="left">
	 	        		<script>generateTFUpload("${row.dtOid}", "${row.teachDate}", "${row.teacherId}", "${row.subjectName}");</script></display:column>
	 				</c:if>
 					<c:if test="${allowPatch!='yes'}">
 	        			<display:column title="科目"		property="subjectName"		sortable="true" class="left" />
	 				</c:if>
	 	        	<display:column title="班級"		property="deptClassName"	sortable="true"  	class="left" />
		        	<display:column title="上課日期"	property="teachDate"		sortable="true"  	class="left" />
		        	<display:column title="星期"		property="teachWeek"		sortable="true"  	class="left" />
		        	<display:column title="E-mail"	property="email"			sortable="true"  	class="left" />
		        	<display:column title="備註"		property="memo"				sortable="true"  	class="left" />
		        	</display:table>
				</td></tr>	      
		    </table>
			</c:if>
			<c:if test="${TFnotUploadQueryInit.pmode=='2'}">
			<table width="50%" cellpadding="0" cellspacing="0">
				<tr><td align="center">  
		      		<display:table name="${TFnotUploadList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
		  			<c:if test="${empty TFnotUploadList}">
		     			<%@ include file="../include/NoBanner.jsp" %>
		  			</c:if>
 	 	        	<display:column title="教師"		property="teacherName"		sortable="true"  	class="left" />
	 	        	<display:column title="未點名次數"		property="count"	sortable="true"  	class="left" />
		        	</display:table>
				</td></tr>	      
		    </table>
			</c:if>
			</td>
		</tr>
	</c:if>	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
