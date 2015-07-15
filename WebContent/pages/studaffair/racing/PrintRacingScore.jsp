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
	
		var WeekStart = document.getElementsByName("WeekStart")[0];
		var WeekStartv = WeekStart.value;
		var WeekEnd = document.getElementsByName("WeekEnd")[0];
		var WeekEndv = WeekEnd.value;
		var GroupNo = document.getElementsByName("GroupNo")[0];
		var GroupNov = GroupNo.value;
		
		if(isNaN(WeekStartv)||isNaN(WeekEndv)){
			alert("週次只能輸入數字");
			WeekStart.focus();
			isValidate = false;
		}
		if(WeekStartv > WeekEndv){
			alert("開始週次必須小於或等於結束週次");
			WeekStart.focus();
			isValidate = false;
		}
		
		if(isValidate) {
			var str = "&WeekStart=" + WeekStartv + 
			"&WeekEnd=" + WeekEndv + 
			"&GroupNo=" + GroupNov;
		
			subwin = window.open("/CIS/StudAffair/Racing/PrintRacingScore.do?method=PrintPreview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
		}
	}

//-->
</script>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/Racing/PrintRacingScore.do" method="post" name="ceForm" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.PrintRacingScore" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">開始週次：      
      	<td>
      		<input type="text" name="WeekStart" value="" size="2">
      	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">結束週次：      
      	<td>
      		<input type="text" name="WeekEnd" value="" size="2">
      	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8"> 組  別 ：      
      	<td>
      		<Select name="GroupNo">
      			<option Value="1">台北校區大一班級</option>
      			<option Value="2">台北校區大二班級</option>
      			<option Value="3">台北校區大三班級</option>
      			<option Value="4">台北校區大四班級</option>
      			
      			<option Value="5">新竹校區班級</option>
      		</Select>
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
