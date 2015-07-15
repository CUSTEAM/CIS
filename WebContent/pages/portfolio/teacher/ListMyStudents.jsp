<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Portfolio/PageManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/user.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">授課學生列表</font></div>		
		</td>
	</tr>
	
	
	<c:if test="${type==null}">
	<tr>
		<td>
		<%@ include file="ListMyStudents/aboutClass.jsp" %>
		</td>
	</tr>
	</c:if>
	
	
	<c:if test="${type!=null}">
	
		<c:if test="${type=='select'}">	
		<tr>
			<td>
			<%@ include file="ListMyStudents/selected.jsp" %>
			</td>
		</tr>
		</c:if>
	
		<c:if test="${type=='score'}">	
		<tr>
			<td>
			<%@ include file="ListMyStudents/score.jsp" %>
			</td>
		</tr>
		</c:if>
	
		<c:if test="${type=='core'}">	
		<tr>
			<td>
			<%@ include file="ListMyStudents/core.jsp" %>
			</td>
		</tr>
		</c:if>
	
	
	</c:if>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
	
</html:form>
</table>
	