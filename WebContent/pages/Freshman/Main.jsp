<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" height="480" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Freshman" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/chit_s.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">中華科技大學新生學籍簡表</font></div>		
		</td>
	</tr>
	<tr height="1">
		<td valign="top">
		<%@ include file="search.jsp"%>
		</td>
	</tr>
	
	<tr>
		<td valign="top">
		<%@ include file="info.jsp"%>
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable">		
		
		</td>
	</tr>
	</html:form>
</table>