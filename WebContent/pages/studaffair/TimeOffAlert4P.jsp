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
		var buttonObj = document.getElementById("method");
		buttonObj.disabled = true;
		var isValidate = true;
	
		var campus = document.getElementsByName("campusInChargeSAF")[0];
		var campusv = campus.value;
		var school = document.getElementsByName("schoolInChargeSAF")[0];
		var schoolv = school.value;
		var depart = document.getElementsByName("deptInChargeSAF")[0];
		var departv = depart.value;
		var clazz = document.getElementsByName("classInChargeSAF")[0];
		var clazzv = clazz.value;
		var WeekNo = document.getElementsByName("WeekNo")[0];
		var WeekNov = WeekNo.value;
		var DateStart = document.getElementsByName("DateStart")[0];
		var DateStartv = DateStart.value;
		var DateEnd = document.getElementsByName("DateEnd")[0];
		var DateEndv = DateEnd.value;
		var th1 = document.getElementsByName("threshold1")[0];
		var th1v = th1.value;
		var th2 = document.getElementsByName("threshold2")[0];
		var th2v = th2.value;
		var th3 = document.getElementsByName("threshold3")[0];
		var th3v = th3.value;
		var ltp1v = 0;
		var ltp1 = document.getElementsByName("letterType1");
		for(i=0; i<ltp1.length; i++){
			if(ltp1[i].checked) ltp1v=ltp1[i].value;
		}
		var ltp2v = 0;
		var ltp2 = document.getElementsByName("letterType2");
		for(i=0; i<ltp2.length; i++){
			if(ltp2[i].checked) ltp2v=ltp2[i].value;
		}
		var ltp3v = 0;
		var ltp3 = document.getElementsByName("letterType3");
		for(i=0; i<ltp3.length; i++){
			if(ltp3[i].checked) ltp3v=ltp3[i].value;
		}
		var pmodev = 0;
		var pmode = document.getElementsByName("pmode");
		for(i=0; i<pmode.length; i++){
			if(pmode[i].checked) pmodev=pmode[i].value;
		}
				
		//if ("All" == campusv || "All" == schoolv || "All" == departv) {
		if ("All" == campusv || "All" == schoolv) {
			alert("班級選擇範圍過大");
			campus.focus();
			isValidate = false;
			buttonObj.disabled = false;
			return false;
		}
		if(isNaN(WeekNov)){
			alert("週次只能輸入數字");
			WeekStart.focus();
			isValidate = false;
			buttonObj.disabled = false;
			return false;
		}
		
		if(!chkDate(DateStartv)){
			alert("起始日期輸入錯誤!");
			DateStart.focus();
			isValidate = false;
			buttonObj.disabled = false;
			return false;
		}
		
		if(!chkDate(DateEndv)){
			alert("結束日期輸入錯誤!");
			DateEnd.focus();
			isValidate = false;
			buttonObj.disabled = false;
			return false;
		}
		if(isNaN(th1v)){
			alert("節次只能輸入數字");
			th1.focus();
			isValidate = false;
			buttonObj.disabled = false;
			return false;
		}
		if(isNaN(th2v)){
			alert("節次只能輸入數字");
			th2.focus();
			isValidate = false;
			buttonObj.disabled = false;
			return false;
		}
		if(isNaN(th3v)){
			alert("節次只能輸入數字");
			th3.focus();
			isValidate = false;
			buttonObj.disabled = false;
			return false;
		}
		
		if(isValidate) {
			var str = "&campusInChargeSAF=" + campusv + 
			"&schoolInChargeSAF=" + schoolv + 
			"&deptInChargeSAF=" + departv +
			"&classInChargeSAF=" + clazzv +
			"&WeekNo=" + WeekNov + 
			"&DateStart=" + DateStartv +
			"&DateEnd=" + DateEndv +
			"&threshold1=" + th1v +
			"&threshold2=" + th2v +
			"&threshold3=" + th3v +
			"&letterType1=" + ltp1v +
			"&letterType2=" + ltp2v +
			"&letterType3=" + ltp3v +
			"&pmode=" + pmodev;
		
			subwin = window.open("/CIS/StudAffair/TimeOffAlert4P.do?method=PrintPreview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
			
			buttonObj.disabled = false;
			
		}
	}

//-->
</script>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/TimeOffAlert4P.do" method="post" name="ceForm" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.TimeOffAlert4P" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
      <td colspan="2">
           	<font style="color:red; font-size: 15px;" >印表前請記得先執行： [印表前曠缺資料轉換]</font><br/>
           	<font style="color:blue; font-size: 15px;" >由於增加寄送E-mail給導師功能，執行時間較久，請耐心等候!!!</font>
      </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">班級：</td>
        <td  height="30" align="left" valign="middle">
        	<c:set var="campusSel" value="${CantExamInit.campus}"/>
	  		<c:set var="schoolSel" value="${CantExamInit.school}"/>
	  		<c:set var="deptSel"   value="${CantExamInit.dept}"/>
	  		<c:set var="classSel"  value="${CantExamInit.departClass}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %>
        </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">週別： </td>    
      	<td>
      		<input type="text" name="WeekNo" value="" size="2" maxlength="3">
      	</td>
      </tr>
      <tr><td colspan="2"><font color="red">
      請注意：如非列印上次的舊資料，本週(次)新列印報表之起始與結束日期不可與上次列印之日期重疊，否則將造成資料不正確！！！
      </font></td></tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">起始日期：    </td>  
      	<td>
      		<input type="text" name="DateStart" value="" maxlength="9">(格式:YY/MM/DD)
      	</td>
      </tr>
      <tr>
         <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">結束日期：     </td> 
      	<td>
      		<input type="text" name="DateEnd" value="" maxlength="9">(格式:YY/MM/DD)
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">節次：    </td>  
      	<td>
      	<table>
      		<tr>
      			<td>
      			<input type="text" name="threshold1" value="" size="2" maxlength="3">
      			<input type="radio" name="letterType1" value="0" checked="checked">平信
     			<input type="radio" name="letterType1" value="1">掛號
      			</td>
      		</tr>
      		<tr>
      			<td>
      			<input type="text" name="threshold2" value="" size="2" maxlength="3">
      			<input type="radio" name="letterType2" value="0">平信
     			<input type="radio" name="letterType2" value="1" checked="checked">掛號
      			</td>
      		</tr>
      		<tr>
      			<td>
      			<input type="text" name="threshold3" value="" size="2" maxlength="3">
      			<input type="radio" name="letterType3" value="0">平信
     			<input type="radio" name="letterType3" value="1" checked="checked">掛號
      			</td>
      		</tr>
      	</table>
      	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">作業模式：      </td>
      	<td>
      		<input type="radio" name="pmode" value="1" checked="checked">再次列印<font style="color:red;" >(不更新通知紀錄,也不會寄送Email給導師)</font>
     		<input type="radio" name="pmode" value="0">第一次列印<font style="color:red;" >(更新通知紀錄並且會寄送Email給導師)</font>
     	</td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<!-- script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='PrintOption'/>" >&nbsp;&nbsp;'+
	'<Input type="button" name="method" onClick="preview();" value="<bean:message key='Preview'/>" >');</script -->
	<script>generateTableBanner(
	'<Input type="button" name="method" id="method" onClick="preview();" value="<bean:message key='PreviewPrint'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
