<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
// -->
</style>
<script type="text/javascript">
<!--
	var doc = document.ceForm;
	function chkDate(dstText){
		vdate = dstText.match(/(\d{2})\/(\d{2})\/(\d{2})/);
		if(!vdate || !dstText) return false;
		year = RegExp.$1;
		month = RegExp.$2;
		day = RegExp.$3;
		//alert("chkDate->" + year+":" + month+":"+day);
		wyear = year + 1911;
		if(((wyear%4 == 0)&&(wyear%100 != 0))||(wyear%400 == 0)) yflag = true;
		if(month > 12) return false;
		else{
			switch(month){
			case 2:
				if(yflag){
					if(day > 29) return false;
				}else{
					if(day > 28) return false;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if(day > 30) return false;
			default:
				if(day > 31) return false;
			}
		}
		return true;
	}
	
	function preview(){
		var qty = document.getElementById("qty");
		var qtyv = qty.value;
		
		var str = "&qty=" + qtyv ;
		
		document.getElementById("button1").disabled = true;
		logoutTimer=setTimeout('closeMask()', 5000);
		init("報表產生中, 請稍後...");

		subwin = window.open("/CIS/Personnel/AssessPaper.do?method=PrintPreview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
	}

	function closeMask(){
		document.getElementById('loadMsg').style.display='none';
	    document.getElementById('loadIco').style.display='none';
		document.getElementById("button1").disabled = false;
	}
//-->
</script>
<!-- Begin Content Page Table Header -->
<!-- form action="/CIS/Personnel/AssessPaper.do" method="post" name="ceForm" onsubmit="init('報表產生中, 請稍後')" -->
<form action="/CIS/Personnel/AssessPaper.do" method="post" name="ceForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.AssessPaper" bundle="PSN"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">列印數量： </td>     
        <td  height="30" align="left" valign="middle">
        	<select name="qty" id="qty">
        	<option value="10">10</option>
        	<option value="20" selected="selected">20</option>
        	<option value="30">30</option>
        	<option value="40">40</option>
        	<option value="50">50</option>
        	<option value="60">60</option>
        	<option value="70">70</option>
        	<option value="80">80</option>
        	<option value="90">90</option>
        	<option value="100">100</option>
       		</select>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner(
	'<Input type="button" name="method" id="button1" onClick="preview();" value="<bean:message key='Print'/>" >' +
	'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
	'<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>

	<!-- Test if have Query Result  -->
	<c:if test="${AssessPaperList != null}" >
	<table width="100%" cellpadding="0" cellspacing="0">
	    <tr><td height="10"></td></tr>
	    
	    <%@include file="/pages/personnel/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${AssessPaperList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty AssessPaperList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="服務編號"		property="serviceNo"	sortable="true"  	class="left" />
 	        	<display:column title="列印日期"		property="printDate"	format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true" 	class="left" />
 	        	<display:column title="服務日期"		property="serviceDate"	format="{0,date,yyyy-MM-dd}" sortable="true"  	class="left" />
	        	<display:column title="服務品質"		property="score"		sortable="true"  	class="center" />
	        	<display:column title="身分別"		property="reporterKind"	sortable="true"  	class="center" />
	        	<!-- display:column title="反應者" 		property="reporterCname" 		sortable="true" 	class="left" /-->
	        	<!-- display:column title="反應者單位" 	property="reporterUnitName" 	sortable="true" 	class="left" /-->
	        	<display:column title="洽辦事項" sortable="false"  	class="left">
	        	<script>showDetail("${row.serviceNo}", "${row.srvEvent}");</script></display:column>
	        	<display:column title="具體事實" sortable="false"  	class="left">
	        	<script>showDetail("${row.serviceNo}", "${row.description}");</script></display:column>
	        	<display:column title="建議事項" sortable="false"  	class="left">
	        	<script>showDetail("${row.serviceNo}", "${row.suggestion}");</script></display:column>
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	    <tr><td height="10"></td></tr>

	    <tr><td>
		    <table width="100%" style="background-color: #ADDFFF;">
		    <tr>
		    <td>已回應：${AssessPaperTotal}&nbsp;份</td>
		    <td>平均分數：${AssessPaperAvg}&nbsp;分</td><td width="80%"></td>
		    </tr>
		    </table>
	    </td></tr>
	    <tr><td height="10"></td></tr>

	</table> 


	</c:if>



