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

<c:if test="${JLSumPrintReport != null}">
<c:set var="reportPath" value="${JLSumPrintReport.reportPath}" />
<c:set var="reportTitle" value="${JLSumPrintReport.reportTitle}" />
<script language="javaScript">
<!-- 
function pdf2print() {
	subwin = window.open("${reportPath}","${reportTitle}",
	"width=600,height=600,scrollbars=yes,resizable=yes,left=0,top=0");
};
window.onload=pdf2print;
// -->
</script>
</c:if>

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
		var isValidate = true;
	
		var campus = document.getElementsByName("campusInChargeSAF")[0];
		var campusv = campus.value;
		var school = document.getElementsByName("schoolInChargeSAF")[0];
		var schoolv = school.value;
		var depart = document.getElementsByName("deptInChargeSAF")[0];
		var departv = depart.value;
		var clazz = document.getElementsByName("classInChargeSAF")[0];
		var clazzv = clazz.value;
				
		//if ("All" == campusv || "All" == schoolv || "All" == departv) {
		if ("All" == campusv || "All" == schoolv) {
			alert("班級選擇範圍過大");
			campus.focus();
			isValidate = false;
			return false;
		}
		
		if(isValidate) {
			var str = "&campusInChargeSAF=" + campusv + 
			"&schoolInChargeSAF=" + schoolv + 
			"&deptInChargeSAF=" + departv +
			"&classInChargeSAF=" + clazzv;
		
			subwin = window.open("/CIS/StudAffair/JustLevelSum.do?method=PrintPreview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
		}
	}

//-->
</script>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/JustLevelSum.do" method="post" name="ceForm" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.JustLevelSum" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
      	<td colspan="2">
      		<font color="red">
      		<p> !!!  請注意：本程式不再計算曠缺課及獎懲的加減分，如要重新計算，可執行重新計算全校學生操行成績的程式，然後再來列印操行丁等名單 !</p>
      		 </font>
      	</td>
      </tr>
      <tr>
        <td  height="30" width="8%" align="left" valign="middle" bgcolor="#d8bfd8">班級：      
        <td  height="30" width="92%" align="left" valign="middle">
        	<c:set var="campusSel" value="${JLevelSumInit.campus}"/>
	  		<c:set var="schoolSel" value="${JLevelSumInit.school}"/>
	  		<c:set var="deptSel"   value="${JLevelSumInit.dept}"/>
	  		<c:set var="classSel"  value="${JLevelSumInit.departClass}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<!-- script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='PrintOption'/>" >&nbsp;&nbsp;'+
	'<Input type="button" name="method" onClick="preview();" value="<bean:message key='Preview'/>" >');</script -->
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='PreviewPrint'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
<c:if test="${JLSumPrintReport != null}">
    <a href="${reportPath}" target="_blank">
    <img src="images/ico_file_pdf.png" border="0"> PDF
    </a>
</c:if>