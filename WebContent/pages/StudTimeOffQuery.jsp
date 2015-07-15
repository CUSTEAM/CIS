<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/studTimeOffQuery.js"%>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
.table1 {
	font-size: 12px;
	border: 1px solid #778899;
	background-color: #778899;
}
// -->
</style>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudTimeOffQuery.do" method="post" name="timeoffForm">

<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.StudTimeOffQuery" bundle="STD"/></B></div>');</script>	  
	<tr>
		<td align="center" height="50">
		請輸入學生學號：<input type="text" name="studentno" id="studno">
		</td>
	</tr>
	<tr>
		<td  align="center" height="50">
	<input type="button" name="all" value="曠缺課紀錄查詢" onclick="getTimeOff('all');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" name="subject" value="科目曠缺查詢" onclick="getTimeOff('subject');">
		</td>
	</tr>	
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="80%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <!-- 
      <tr>
        <td width="120" height="30" align="center" valign="middle" class="tdbutton" onclick="getTimeOff('all');">曠缺課紀錄查詢
        </td>
        <td  width="120" height="30" align="center" valign="middle" class="tdbutton" onclick="getTimeOff('subject');">科目曠缺查詢
        </td>
        <td>&nbsp;</td>
      </tr>
       -->
	</table>
		</td>		
	</tr>
	
	<tr>
		<td align="center">
		<table width="90%" cellpadding="0" cellspacing="0"  id="container">
			<tr>
			<td id="content" align="center">
			</td>
			</tr>
		</table>
		</td>
	</tr>
<!-- Begin Content Page Table Footer -->
</table>		
</form>

