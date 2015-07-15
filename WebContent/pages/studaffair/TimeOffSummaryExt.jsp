<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/studaffair/include/timeOffSeriousQuery.js"%>

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
<form action="/CIS/StudAffair/TimeOffSummaryExt.do" method="post" name="ceForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.TimeOffSummaryExt" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" width="100" align="left" valign="middle" bgcolor="#d8bfd8">班級：      
        <td  height="30" align="left" valign="middle">
        	<c:set var="campusSel" value="${TFSummaryExtInit.campus}"/>
	  		<c:set var="schoolSel" value="${TFSummaryExtInit.school}"/>
	  		<c:set var="deptSel"   value="${TFSummaryExtInit.dept}"/>
	  		<c:set var="classSel"  value="${TFSummaryExtInit.departClass}"/>
			<!-- %@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %-->
			<%@ include file="/pages/include/AllClassSelect1.jsp" %>
			
        </td>
      </tr>
      <!-- tr>
        <td  height="30" width="100" align="left" valign="middle" bgcolor="#d8bfd8">週次：      
      	<td>
      		<input type="text" name="WeekNo" value="${TFSummaryExtInit.WeekNo}" size="2">
      	</td>
      </tr>
      <tr>
        <td  height="30" width="100" align="left" valign="middle" bgcolor="#d8bfd8">起始日期：      
      	<td>
      		<input type="text" name="DateStart" value="${TFSummaryExtInit.DateStart}" maxlength="9">(格式:YY/MM/DD)
      	</td>
      </tr-->
      <tr>
         <td  height="30" width="100" align="left" valign="middle" bgcolor="#d8bfd8">結束日期：      
      	<td>
      		<input type="text" name="DateEnd" value="${TFSummaryExtInit.DateEnd}" maxlength="9">(格式:YY/MM/DD)
     	</td>
      </tr>
      <tr>
        <td  height="30" width="100" align="left" valign="middle" bgcolor="#d8bfd8">嚴重曠缺節數：      
      	<td>
      		<input type="text" name="period" value="${TFSummaryExtInit.period}" size="2">
      	</td>
      </tr>
      <tr>
        <td  height="30" width="100" align="left" valign="middle" bgcolor="#d8bfd8">查詢範圍：      
      	<td>
      		<input type="radio" name="qscope" value="0" checked>全校
      		<input type="radio" name="qscope" value="1">畢業班
      	</td>
      </tr>
	</table>
		</td>		
	</tr>
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >&nbsp;&nbsp;'+
	'<Input type="reset" value="<bean:message key='Reset'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${TimeOffSummaryExt != null && !empty(TimeOffSummaryExt)}">
        <!-- c:set var="DateStart" value="${TFSummaryExtInit.DateStart}"/ -->
	  	<c:set var="DateEnd" value="${TFSummaryExtInit.DateEnd}"/>
	  	<c:set var="period"   value="${TFSummaryExtInit.period}"/>
	  	<c:set var="qscope"  value="${TFSummaryExtInit.qscope}"/>
	  <tr><td>
		<table width="100%" style="border: 1px solid #99ffff;">
			<tr>
				<td width="20%" align="center" valgin="middle" style="border: 1px solid #99ffff;">班<br><br>級</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">人<br><br>數</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">全<br>班<br>曠<br>課</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">每<br>人<br>曠<br>課</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">全<br>班<br>事<br>假</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">每<br>人<br>事<br>假</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">全<br>班<br>病<br>假</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">每<br>人<br>病<br>假</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">全<br>班<br>公<br>假</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">每<br>人<br>公<br>假</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">全<br>班<br>婚<br>假</td>
				<td width="5%" align="center" valgin="middle" style="border: 1px solid #99ffff;">每<br>人<br>婚<br>假</td>
				<td width="5%" align="center" valgin="middle" style="border: 1px solid #99ffff;">全<br>班<br>產<br>假</td>
				<td width="5%" align="center" valgin="middle" style="border: 1px solid #99ffff;">每<br>人<br>產<br>假</td>
				<td width="5%" align="center" valgin="middle" style="border: 1px solid #99ffff;">全<br>班<br>全<br>勤</td>
			</tr>				
		<c:forEach items="${TimeOffSummaryExt}" var="timeoff">
			<tr>
				<!-- 
				<td width="20%" align="center" valgin="middle"  style="border: 1px solid #99ffff;" 
				onClick="getTimeOffSerious('${timeoff.departClass}','${DateStart}','${DateEnd}','${period}',${qscope});">
				<img src="/CIS/pages/images/16-icon-plus.bmp">${timeoff.deptClassName}</td>
				 -->
				<td width="20%" align="center" valgin="middle"  style="border: 1px solid #99ffff;" 
				onClick="getTimeOffSerious('${timeoff.departClass}','${DateEnd}','${period}',${qscope});">
				<img src="/CIS/pages/images/16-icon-plus.bmp">${timeoff.deptClassName}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.totalStu}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind2}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind2Avg}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind4}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind4Avg}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind3}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind3Avg}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind6}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind6Avg}</td>
				<td width="6%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind8}</td>
				<td width="5%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind8Avg}</td>
				<td width="5%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind9}</td>
				<td width="5%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.kind9Avg}</td>
				<td width="5%" align="center" valgin="middle" style="border: 1px solid #99ffff;">${timeoff.goodStuNo}</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="14" id="${timeoff.departClass}" onClick="hiddenMe(this);"></td>
			</tr>
		</c:forEach>
		</table>
	  </td></tr>
	</c:if>
<!-- Begin Content Page Table Footer -->
</table>		
</form>