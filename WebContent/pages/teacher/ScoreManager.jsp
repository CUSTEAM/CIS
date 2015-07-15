<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/ScoreManager" method="post" onsubmit="init('執行中, 請稍後')">	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/icon_full_score.gif"></div>
		<div nowrap style="float:left;"><font class="gray_15">成績輸入與列印</font></div>		
		</td>
	</tr>
	
	
	<c:if test="${type==null}">
	<tr>
		<td>		
		<%@ include file="ScoreManager/ListClass.jsp" %>		
		</td>
	</tr>
	</c:if>
	
	
	
	<c:if test="${type==''}">
	<tr>
		<td>		
		<%@ include file="ScoreManager/norRat.jsp" %>		
		</td>
	</tr>
	<tr height="40">
		<td colspan="100" class="fullColorTd" align="center">
		
		</td>
	</tr>
	</c:if>
	
	
	<c:if test="${type=='e'}">
	<tr>
		<td>		
		<%@ include file="ScoreManager/engRat.jsp" %>		
		</td>
	</tr>
	</c:if>
	
	
	<c:if test="${type=='s'}">
	<tr>
		<td>		
		<%@ include file="ScoreManager/spoRat.jsp" %>		
		</td>
	</tr>
	</c:if>
	
	
	
	<tr height="30">	
		<td class="fullColorTable" align="center"></td>
	</tr>
	
</html:form>
</table>
