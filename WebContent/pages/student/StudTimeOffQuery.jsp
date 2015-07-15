<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/student/include/studTimeOffQuery.js"%>

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
<table width="100%" cellpadding="0" cellspacing="0">
  <form action="/CIS/Student/StudTimeOffQuery.do" method="post" name="timeoffForm">
    <input type="hidden" name="studentNo" id="studno" value="${studentNo}"/>
    <script>
      generateTableBanner('<div class="gray_15"><B><bean:message key="Title.StudTimeOffQuery" bundle="STD"/></B></div>');
    </script>
	<tr>
	  <td align="center" valign="bottom" height="40">
	    <input type="button" name="all"     value="曠缺課查詢" class="CourseButton" onclick="getTimeOff('all');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <input type="button" name="subject" value="科目曠缺查詢"   class="CourseButton" onclick="getTimeOff('subject');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <!-- <input type="button" name="desd" value="獎懲查詢"   class="CourseButton" onclick="getTimeOff('desd');"> -->
	  </td>
	</tr>

	<tr>
	  <td align="center">
	    <table width="90%" cellpadding="0" cellspacing="0"  id="container">
		  <tr>
		    <td id="content" align="center" valgn="top"></td>
		  </tr>
		</table>
	  </td>
	</tr>

	<tr>
	  <td height="20"></td>
	</tr>
    <!-- Begin Content Page Table Footer -->

    <script>generateTableBanner('');</script>
  </form>  
</table>	
<script>getTimeOff('all');</script>