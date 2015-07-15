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
	function preview(){
		var isValidate = true;
	
		var year = document.getElementById("schoolYear");
		var yearv = year.value;
		var term = document.getElementsByName("schoolTerm");
		var termv = 0;
		for(i=0; i<term.length; i++){
			if(term[i].checked){
				termv = term[i].value;
				//alert("pmode value:" + pmodev);
				break;
			}
		}
		if(isNaN(yearv)){
			alert("學年只能輸入數字");
			year.focus();
			isValidate = false;
		}
		
		if(isValidate) {
			var str = "&schoolYear=" + yearv + 
			"&schoolTerm=" + termv;
		
			subwin = window.open("/CIS/StudAffair/BonusPenaltySum4edu.do?method=PrintPreview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
		}
	}

//-->
</script>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/BonusPenaltySum4edu.do" method="post" name="ceForm" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.BonusPenaltySum4edu" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">學年：</td>
      	<td>
      		<input type="text" name="schoolYear" id="schoolYear" value="" size="3">
      	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">學期：     </td>
      	<td>
      		<input type="radio" name="schoolTerm" value="0">全部
      		<input type="radio" name="schoolTerm" value="1">第1學期
      		<input type="radio" name="schoolTerm" value="2"/>第2學期
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
