<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<html:form action="/Teacher/CourseInfo" method="post" enctype="multipart/form-data" onsubmit="init('處理中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">

	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/book_addresses.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">課程大綱與簡介管理</font></div>		
		</td>
	</tr>
	
	
	<c:if test="${aDtime==null&&bDtime==null}">
	<tr>
		<td>
		
		

		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>第1學期課程</td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>課程名稱</td>
				<td class="hairLineTdF" nowrap>開課班級</td>
				<td class="hairLineTdF" nowrap>上課時間</td>
				<td class="hairLineTdF" nowrap>課程簡介</td>
				<td class="hairLineTdF" nowrap>課程大綱</td>
			</tr>
			
			<c:forEach items="${dtime1}" var="d1">
			<tr>
				<td class="hairLineTdF" width="50%">${d1.chi_name}</td>
				<td class="hairLineTdF" nowrap>${d1.ClassName}</td>
				<td class="hairLineTdF" width="50%">${d1.dtimeClass}</td>
				
				<td class="hairLineTdF" nowrap>
				<input type="button" class="gGreenSmall" onClick="window.location='/CIS/Teacher/CourseInfo.do?iOid=${d1.Oid}'" value="編輯">
				<input type="button" class="gCancelSmall" onClick="window.location='/CIS/Print/teacher/IntorDoc.do?Oid=${d1.Oid}'" value="檢視">
				</td>
				
				<td class="hairLineTdF" nowrap>
				<input type="button" class="gGreenSmall" onClick="window.location='/CIS/Teacher/CourseInfo.do?sOid=${d1.Oid}'" value="編輯">
				<input type="button" class="gCancelSmall" onClick="window.location='/CIS/Print/teacher/SylDoc.do?Oid=${d1.Oid}'" value="檢視">
				
				</td>
			</tr>		
			</c:forEach>
		</table>
		
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>第2學期課程</td>
			</tr>
		</table>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>課程名稱</td>
				<td class="hairLineTdF" nowrap>開課班級</td>
				<td class="hairLineTdF" nowrap>上課時間</td>
				<td class="hairLineTdF" nowrap>課程簡介</td>
				<td class="hairLineTdF" nowrap>課程大綱</td>
			</tr>
			
			<c:forEach items="${dtime2}" var="d1">
			<tr>
				<td class="hairLineTdF" width="50%">${d1.chi_name}</td>
				<td class="hairLineTdF" nowrap>${d1.ClassName}</td>
				<td class="hairLineTdF" width="50%">${d1.dtimeClass}</td>
				
				<td class="hairLineTdF" nowrap>
				<input type="button" class="gGreenSmall" onClick="window.location='/CIS/Teacher/CourseInfo.do?iOid=${d1.Oid}'" value="編輯">
				<input type="button" class="gCancelSmall" onClick="window.location='/CIS/Print/teacher/IntorDoc.do?Oid=${d1.Oid}'" value="檢視">
				</td>
				
				<td class="hairLineTdF" nowrap>
				<input type="button" class="gGreenSmall" onClick="window.location='/CIS/Teacher/CourseInfo.do?sOid=${d1.Oid}'" value="編輯">
				<input type="button" class="gCancelSmall" onClick="window.location='/CIS/Print/teacher/SylDoc.do?Oid=${d1.Oid}'" value="檢視">
				
				</td>
			</tr>		
			</c:forEach>
		</table>
		
		
		
		
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>歷年課程</td>
			</tr>
		</table>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>學年</td>
				<td class="hairLineTdF" nowrap>學期</td>
				<td class="hairLineTdF" nowrap>課程名稱</td>
				<td class="hairLineTdF" nowrap>課程簡介</td>
				<td class="hairLineTdF" nowrap>課程大綱</td>
			</tr>
			<c:forEach items="${cshist}" var="c">
			<tr>
				<td class="hairLineTdF" nowrap>${c.school_year}</td>
				<td class="hairLineTdF" nowrap>${c.school_term}</td>
				<td class="hairLineTdF" nowrap width="100%">${c.chi_name}</td>
				<td class="hairLineTdF" nowrap><a href="/CIS/Print/teacher/IntorDoc.do?sOid=${c.Oid}">檢視</a></td>
				<td class="hairLineTdF" nowrap><a href="/CIS/Print/teacher/SylDoc.do?sOid=${c.Oid}">檢視</a></td>
			</tr>
			</c:forEach>
		</table>
		
		
		</td>
	</tr>
	</c:if>
	
	
	<c:if test="${aDtime!=null}">
	<tr>
		<td>
		<%@ include file="CourseInfo/editIntro.jsp"%>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${bDtime!=null}">
	<tr>
		<td>
		<%@ include file="CourseInfo/editSyllabi.jsp"%>
		</td>
	</tr>
	</c:if>
	
	<tr height="30">
		<td class="fullColorTable">
		
		
		</td>
	</tr>
	

</table>
</html:form>