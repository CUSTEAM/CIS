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
	isAutoLogout = false;
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
		var pmodev = 0;
		var pmode = document.getElementsByName("pmode");
		var totalintv = 1;
		var totalint = document.getElementsByName("totalint");
		
		for(i=0; i<pmode.length; i++){
			if(pmode[i].checked) pmodev=pmode[i].value;
		}
		var cmodev = 0;
		var cmode = document.getElementsByName("cmode");
		for(i=0; i<cmode.length; i++){
			if(cmode[i].checked) cmodev=cmode[i].value;
		}
		
		for(i=0; i<totalint.length; i++){
			if(totalint[i].checked) totalintv=totalint[i].value;
		}
				
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
			"&classInChargeSAF=" + clazzv +
			"&pmode=" + pmodev +
			"&cmode=" + cmodev +
			"&totalint=" + totalintv;
		
			subwin = window.open("/CIS/StudAffair/JustScoreThisTerm.do?method=PrintPreview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
		}
	}

//-->
</script>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/JustScoreThisTerm.do" method="post" name="ceForm" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.JustScoreThisTerm" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">班級：      
        <td  height="30" align="left" valign="middle">
        	<c:set var="campusSel" value="${JScoreThisTermInit.campus}"/>
	  		<c:set var="schoolSel" value="${JScoreThisTermInit.school}"/>
	  		<c:set var="deptSel"   value="${JScoreThisTermInit.dept}"/>
	  		<c:set var="classSel"  value="${JScoreThisTermInit.departClass}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %>
        </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">評語格式：      
      	<td>
      		<input type="radio" name="cmode" value="1" checked>評語
     		<input type="radio" name="cmode" value="0">代碼
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">列印格式：      
      	<td>
      		<input type="radio" name="pmode" value="0" checked>單行
     		<input type="radio" name="pmode" value="1">雙行
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">總分四捨五入：      
      	<td>
      		<input type="radio" name="totalint" value="1" checked>是
     		<input type="radio" name="totalint" value="0">否
     	</td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<!-- script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='PrintOption'/>" >&nbsp;&nbsp;'+
	'<Input type="button" name="method" onClick="preview();" value="<bean:message key='Preview'/>" >');</script -->
	<script>generateTableBanner(
	'<Input type="button" name="method" onClick="preview();" value="<bean:message key='PreviewPrint'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
