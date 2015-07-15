<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Registration/CheckeOut" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/folder_bug.gif">
				</td>
				<td align="left">
				&nbsp;學籍查核&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>	
<!-- 標題列 end -->
	<tr>
		<td>
<%@ include file="CheckOut/search.jsp"%>
		
		</td>
	</tr>
	
<!-- 重複修課(歷史) start -->
<c:if test="${checkOpt=='reSelect'}">
	<tr>
		<td>
		
		<%@ include file="CheckOut/reChose.jsp"%>
		
		</td>
	</tr>
<!-- 重複修課 end -->
</c:if>

<!-- 重複修課(現在) start -->
<c:if test="${checkOpt=='SelectException'}">
	<tr>
		<td>
		
		<%@ include file="CheckOut/reChoseNow.jsp"%>
		
		</td>
	</tr>
<!-- 重複修課 end -->
</c:if>

<!-- 重複修課(現在) start -->
<c:if test="${checkOpt=='entrno'}">
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%" id="reChoseList">
			<tr>
				<td class="hairLineTdF" nowrap width="30" align="center"><img src="images/16-cube-bug.png"></td>
				<td class="hairLineTd" nowrap>發生學號</td>
				<td class="hairLineTd" nowrap>開始學號</td>
				<td class="hairLineTd" nowrap>結束學號</td>
				<td class="hairLineTd" nowrap>文號</td>	
			</tr>
		<c:forEach items="${entrno}" var="r">
			
		
			<tr>
				<td class="hairLineTd" width="30" align="center" nowrap><img src="images/16-cube-bug.png"></td>
				<td class="hairLineTd" nowrap>${r.student_no }</td>
				<td class="hairLineTd" nowrap>${r.first_stno}</td>
				<td class="hairLineTd" nowrap>${r.second_stno}</td>
				<td class="hairLineTd" nowrap>${r.permission_no}</td>
			</tr>
		</c:forEach>
		</table>
		
		</td>
	</tr>
<!-- 重複修課 end -->
</c:if>
</html:form>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>