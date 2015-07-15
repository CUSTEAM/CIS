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
	
		var campus = document.getElementsByName("campusInCharge")[0];
		var campusv = campus.value;
		var school = document.getElementsByName("schoolInCharge")[0];
		var schoolv = school.value;
		var depart = document.getElementsByName("deptInCharge")[0];
		var departv = depart.value;
		var clazz = document.getElementsByName("classInCharge")[0];
		var clazzv = clazz.value;
		
		if ("All" == campusv || "All" == schoolv) {
			alert("班級選擇範圍過大");
			campus.focus();
			isValidate = false;
		}
		
		if(isValidate) {
			var str = "&campusInCharge=" + campusv + 
			"&schoolInCharge=" + schoolv + 
			"&deptInCharge=" + departv +
			"&classInCharge=" + clazzv;
		
			subwin = window.open("/CIS/StudAffair/Racing/BP4RacingPrint.do?method=PrintPreview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
		}
	}

//-->
</script>

<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/Racing/BP4RacingPrint.do" method="post" name="ceForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.BP4RacingPrint" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" width="100" align="left" valign="middle" bgcolor="#d8bfd8">班級：      
        <td  height="30" align="left" valign="middle">
        	<c:set var="campusSel" value="${BP4RacingInit.campus}"/>
	  		<c:set var="schoolSel" value="${BP4RacingInit.school}"/>
	  		<c:set var="deptSel"   value="${BP4RacingInit.dept}"/>
	  		<c:set var="classSel"  value="${BP4RacingInit.departClass}"/>
			<!-- %@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %-->
			<%@ include file="/pages/include/AllClassSelect1.jsp" %>
			
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	<script>generateTableBanner(
	'<Input type="button" name="method" onClick="preview();" value="<bean:message key='PreviewPrint'/>" >');</script>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>