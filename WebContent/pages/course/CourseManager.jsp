<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/CourseManager" method="post" onsubmit="init('執行中, 請稍後')">

	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/folder_bookmark.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">歷年課程管理</font></div>		
		</td>
	</tr>



<!-- 搜尋列 start -->
	<tr>
		<td align="center">	
		<%@ include file="CourseManager/search.jsp"%>
		</td>
	</tr>
<!-- 搜尋列 end -->



	
	<tr height="30">
		<td class="fullColorTable">
		
		
		</td>
	</tr>

</html:form>
</table>
