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
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/Elearn2SeldTimeoff.do" enctype="multipart/form-data" method="post" name="ceForm" onsubmit="init('轉檔中, 請稍後.....')">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.Elearn2SeldTimeoff" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
       <tr>
        <td  height="30" align="left" valign="middle" bgcolor="lightblue">
			<font style="color: #FF0000; font-size: 24px;font-weight: bold;">
			Excel檔上傳前請先將資料依照學生學號欄位排序,以加快作業速度！<br/>
			表格第一行資料保持為欄位名稱,該行資料不會被轉入!  
      </tr>
      <tr>
        <td  height="30" align="left" valign="middle" bgcolor="#d8bfd8">
			<input type="file" name="trFile" size="30" id="uploadFile" value="">  
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'+
	'<Input type="submit" name="method" value="<bean:message key='Cancel'/>" >');</script>
		
<!-- Begin Content Page Table Footer -->
</table>		
</form>
<script type="text/javascript">
<!--
var myTimeOut = globalTimeOut;
globalTimeOut = 7200000;
// -->
</script>