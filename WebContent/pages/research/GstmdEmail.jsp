<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function changeTerm() {
	var term = document.getElementById("term").value;
	if(term == "1") {
		document.getElementById("term1").style.display = "inline";
		document.getElementById("term2").style.display = "none";
	} else {
		document.getElementById("term1").style.display = "none";
		document.getElementById("term2").style.display = "inline";
	}
}

function check() {
	var doc = document.forms[0];
	if (doc.year.value == '') {
		alert("畢業年度不可為空白");
		doc.year.focus();
		return false;
	} else if (doc.image.value == '') {
		alert("上傳檔案不可為空白");
		doc.image.focus();
		return false;
	} else if (doc.replyTo.value == '' || doc.replyToName.value == '') {
		alert("畢業生回信的Email不可為空白");
		doc.replyTo.focus();
		return false;
	}
	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Research/GstmdEmail" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B> 檔 案 寄 送 - 畢 業 生 </B></div>');
	</script>
	<!-- c:if test="${not empty teacherDtime}" -->	
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
    <tr>
    	<td class="hairlineTdF">
    		<span id="year" style="display:inline;">畢業年度：&nbsp;第&nbsp;
				<html:select property="year" size="1">
 					<html:options property="years" labelProperty="years" />	    						
 				</html:select>&nbsp;學年度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 			</span>
 			主旨：&nbsp;<html:text property="subject" size="20" maxlength="30" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 			<html:file property="image" />    		
    	</td>  
    </tr>
    <tr>	 
    	<td class="hairlineTdF" colspan="3">
    		寄發單位：&nbsp;<html:text property="replyToName" size="20" maxlength="50" />&nbsp;&nbsp;&nbsp;
    		寄發單位Email：&nbsp;<html:text property="replyTo" size="50" maxlength="100" />
    	</td> 	
    </tr>
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" onclick="return check()" value="<bean:message key='fee.add.courseFeeAdd' bundle="FEE" />" class="CourseButton">');
   	</script>
	<!-- /c:if -->
</html:form>
</table>
<script type="text/javascript">
	/*changeTerm();*/
</script>