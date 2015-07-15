<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

<!-- Calendar view 1.2 -->
<link type="text/css" href="<%=basePath%>pages/include/calendarview-1.2/stylesheets/calendarview.css" rel="stylesheet" />	
<script type="text/javascript" src="<%=basePath%>pages/include/calendarview-1.2/javascripts/prototype.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/include/calendarview-1.2/javascripts/calendarview.js"></script>
<script>
      function setupCalendars() {
        // Embedded Calendar
        //Calendar.setup(
        //  {
        //    dateField: 'embeddedDateField',
        //    parentElement: 'embeddedCalendar'
        //  }
        //)

        // Popup Calendar
        Calendar.setup(
          {
            //dateField: 'startDate',
            dateField: 'sdate',
            triggerElement: 'sCalImg',
            dateFormat: '%Y-%m-%d'
          }
        )
        Calendar.setup(
          {
            //dateField: 'startDate',
            dateField: 'edate',
            triggerElement: 'eCalImg',
            dateFormat: '%Y-%m-%d'
          }
        )
      }

      Event.observe(window, 'load', function() { setupCalendars() })
</script>
<!-- End Calendar view 1.2 -->


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
<form action="/CIS/Personnel/AssessPaperReport.do" method="post" name="ceForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.AssessPaperReport" bundle="PSN"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<c:choose>
	<c:when test="${AssessPaperReport != null}">
	<c:set var="formback" value="${AssessPaperReport}"/>
	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
       <td height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">報表類型： </td> 
        <td>
        	<c:if test="${formback.reportType=='1'}">
      		<input type="radio" name="reportType" value="1" checked="checked">統計報表&nbsp;&nbsp;&nbsp;
      		<input type="radio" name="reportType" value="2">優劣明細
        	</c:if>
        	<c:if test="${formback.reportType=='2'}">
      		<input type="radio" name="reportType" value="1">統計報表&nbsp;&nbsp;&nbsp;
      		<input type="radio" name="reportType" value="2" checked="checked">優劣明細
        	</c:if>
        </td>
      </tr>
      <tr>
        <td height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">查詢單位： </td> 
      	<td>
      		<select name="unit">
      		<c:forEach items="${emplUnits}" var="unit" >
      			<c:if test="${formback.unit==unit.idno}">
      			<option value="${unit.idno}" selected="selected">${unit.name}</option>
      			</c:if>
       			<c:if test="${formback.unit!=unit.idno}">
      			<option value="${unit.idno}">${unit.name}</option>
      			</c:if>
      			
      		</c:forEach>
      		</select>
      	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">起始日期： </td> 
      	<td>
			<table width="100%"><tr>
	        <td  width="90" class="hairlineTdF">
	            <input type="text" name="sdate" id="sdate" size="10" readonly="readonly" maxlength="10" value="${formback.sdate}">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="sCalImg" id="sCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table>
	     </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">結束日期： </td> 
      	<td>
			<table width="100%"><tr>
	        <td  width="90" class="hairlineTdF">
	            <input type="text" name="edate" id="edate" size="10" readonly="readonly" maxlength="10" value="${formback.edate}">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="eCalImg" id="eCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table>
	     </td>
      </tr>
	</table>
	</c:when>
	<c:otherwise>
	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
       <td height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">報表類型： </td> 
        <td>
      		<input type="radio" name="reportType" value="1" checked="checked">統計報表&nbsp;&nbsp;&nbsp;
      		<input type="radio" name="reportType" value="2">優劣明細
        </td>
      </tr>
      <tr>
        <td height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">查詢單位： </td> 
      	<td>
      		<select name="unit">
      		<c:forEach items="${emplUnits}"var="unit">
      			<option value="${unit.idno}">${unit.name}</option>
      		</c:forEach>
      		</select>
      	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">起始日期： </td> 
      	<td>
			<table width="100%"><tr>
	        <td  width="90" class="hairlineTdF">
	            <input type="text" name="sdate" id="sdate" size="10" readonly="readonly" maxlength="10" value="${formback.sdate}">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="sCalImg" id="sCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table>
	     </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">結束日期： </td> 
      	<td>
			<table width="100%"><tr>
	        <td  width="90" class="hairlineTdF">
	            <input type="text" name="edate" id="edate" size="10" readonly="readonly" maxlength="10" value="${formback.edate}">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="eCalImg" id="eCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table>
	     </td>
      </tr>
	</table>
	</c:otherwise>
	</c:choose>
	
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${assessReport != null}" >
	    <tr><td height="10"></td></tr>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${assessReport}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty assessReport}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	  			<c:choose>
	  			<c:when test="${assessReportType == '1'}">
 	        	<display:column title="單位"		property="unitName"	sortable="true"  	class="left" />
 	        	<display:column title="姓名"		property="cname"	sortable="false" 	class="left" />
	        	<display:column title="份數"		property="replied"	sortable="true"  	class="left" />
	        	<display:column title="總分"		property="total"	sortable="true"  	class="left" />
	        	<display:column title="平均" 	property="average" 	sortable="true" 	class="center" />
	        	<display:column title="5分" 		sortable="true"  	class="center">${row.scores[4]}</display:column>
	        	<display:column title="4分" 		sortable="true"  	class="center">${row.scores[3]}</display:column>
	        	<display:column title="3分" 		sortable="true"  	class="center">${row.scores[2]}</display:column>
	        	<display:column title="2分" 		sortable="true"  	class="center">${row.scores[1]}</display:column>
	        	<display:column title="1分" 		sortable="true"  	class="center">${row.scores[0]}</display:column>
	  			</c:when>
	  			<c:when test="${assessReportType == '2'}">
 	        	<display:column title="服務編號"	property="serviceNo"	sortable="true"  	style="width:50px;" class="left" />
	        	<display:column title="單位"		property="attUnitName"	sortable="true"  	style="width:50px;" class="left" />
	        	<display:column title="姓名"		property="attCname"		sortable="false"  	style="width:50px;" class="left" />
 	        	<display:column title="分數"		property="score"		sortable="true" 	style="width:20px;" class="left" />
	        	<display:column title="回覆者" 	property="reporterKind" sortable="true" 	style="width:30px;" class="left" />
	        	<display:column title="服務日期" 	property="simpleDate" 	sortable="true"  	style="width:50px;" class="left" />
	        	<display:column title="洽辦事項" 	property="srvEvent" 	sortable="false"  	class="left" />
	        	<display:column title="具體事實" 	property="description"	sortable="false" 	class="left" />
	        	<display:column title="建議事項" 	property="suggestion"	sortable="false" 	class="left" />
	  			</c:when>
	  			</c:choose>
	        	</display:table>
 	         </td></tr>
			<c:if test="${assessReportType == '1'}">
  	         <tr><td height="15">
  	         </td></tr>
 	         <tr><td>
 	         <table width="100%" border="1" style="border: 1px solid #810541; background-color:#FCDFFF;">
 	         	<tr><td rowspan="2" align="center">總人數</td><td rowspan="2" align="center">實際受測</td><td rowspan="2" align="center">總份數</td><td rowspan="2" align="center">總和</td><td rowspan="2" align="center">平均值</td>
 	         	<td colspan="2" align="center">5分</td><td colspan="2" align="center">4分</td><td colspan="2" align="center">3分</td>
 	         	<td colspan="2" align="center">2分</td><td colspan="2" align="center">1分</td></tr>
 	         	<tr><td>教職員</td><td>學生</td><td>教職員</td><td>學生</td><td>教職員</td><td>學生</td><td>教職員</td><td>學生</td><td>教職員</td><td>學生</td></tr>
				<tr>
				<td align="right">${assessReportSum.totalEmp}</td><td align="right">${assessReportSum.replied}</td><td align="right">${assessReportSum.totalScrs}</td><td align="right">${assessReportSum.totalSum}</td><td align="right">${assessReportSum.avgSum}</td>
				<td align="right">${assessReportSum.scoreRepli[4][0]}</td><td align="right">${assessReportSum.scoreRepli[4][1]}</td>
				<td align="right">${assessReportSum.scoreRepli[3][0]}</td><td align="right">${assessReportSum.scoreRepli[3][1]}</td>
				<td align="right">${assessReportSum.scoreRepli[2][0]}</td><td align="right">${assessReportSum.scoreRepli[2][1]}</td>
				<td align="right">${assessReportSum.scoreRepli[1][0]}</td><td align="right">${assessReportSum.scoreRepli[1][1]}</td>
				<td align="right">${assessReportSum.scoreRepli[0][0]}</td><td align="right">${assessReportSum.scoreRepli[0][1]}</td>
				</tr>	
 	         </table>
 	         </td></tr>	      
 	         </c:if>
	      	</table>
	      	</td>
	    </tr>
	    <c:if test="${! empty assessReport}">
    	<tr>
    		<td><br>輸出Excel報表:
    			<a href="personnel/AssessPaperRpt.jsp?type=excel">
    			<img src="images/ico_file_excel.png" border="0">
    			</a>
    		</td>
    	</tr>
	  	</c:if>
	    
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
