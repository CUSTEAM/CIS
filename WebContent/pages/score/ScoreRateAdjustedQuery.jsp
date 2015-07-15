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
<form action="/CIS/Score/ScoreRateAdjustedQuery.do" method="post" name="ceForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="scrTitle.ScoreRateAdjusted" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <c:set var="tcnt" value="1"/>
      <tr><td>班別：
        	<c:set var="campusSel" value="${ScoreRateAdjustedInit.campus}"/>
	  		<c:set var="schoolSel" value="${ScoreRateAdjustedInit.school}"/>
	  		<c:set var="deptSel"   value="${ScoreRateAdjustedInit.dept}"/>
	  		<c:set var="classSel"  value="${ScoreRateAdjustedInit.departClass}"/>
			<%@ include file="/pages/include/ClassSelect.jsp" %>
        </td>
      </tr>
      <tr>
      	<td>授課教師：
      		身分證號 <input type="text" name="teacherId" value="">
      		<!-- &nbsp;&nbsp;&nbsp;或 &nbsp;&nbsp;&nbsp;姓名 <input type="text" name="teacherName" value="" -->
      	</td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${RateAdjusted != null}" >
	    <tr><td height="10"></td></tr>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${RateAdjusted}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty RateAdjusted}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
 	        	<display:column title="班級名稱"	property="deptClassName"	sortable="false"  	class="left" />
	        	<display:column title="課程名稱"	property="cscodeName"		sortable="false"  	class="left" />
	        	<display:column title="教師ID" 	property="teacherId" 		sortable="true" 	class="left" />
	        	<display:column title="教師姓名" 	property="teacherName" 		sortable="false"  	class="left" />
	        	<display:column title="平時比例" 	property="rateN" 			sortable="false"  	class="center" />
	        	<display:column title="期中比例" 	property="rateM" 			sortable="false"  	class="center" />
	        	<display:column title="期末比例" 	property="rateF" 			sortable="false"  	class="center" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
		<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='PreviewPrint'/>" >');</script>
	    
	</c:if>
<!-- Begin Content Page Table Footer -->
</table>		
<script language="javascript">
//<!--
	var iplimit = ${tcnt-1};
		
	function nextfocus(ntab) {
		var code = event.keyCode;
		var iptx1s = document.getElementById("1");
		var thisElem, nextElem;
		//code:left(37),right(39),up(38),down(40),Enter(13),del(46),backspace(8)
		//alert("code:" + code + ",ntab:" + ntab);
		thisElem = document.getElementById(ntab-1);
		//if(thisElem.value.length >= thisElem.maxlength || code==39 || code==40 || code==13) {
		if(thisElem.value.length >= thisElem.maxlength || code==40 || code==13) {
			if(ntab <= iplimit) {
				nextElem = document.getElementById(ntab);
				nextElem.focus();
			} else if(ntab > iplimit){
				chgopmode(1);
				document.forms[0].submit();
			}
		//}else if(code==37 || code==38) {
		}else if(code==38) {
			if(ntab==2) return;
			ntab = ntab -2;
			nextElem = document.getElementById(ntab);
			nextElem.focus();
			nextElem.select();
		}else if(code==46 || code==8) {
			return;
		}else if(ntab > iplimit) {
			return;
		}
		return;
	};	
	
//-->
</script>
</form>
