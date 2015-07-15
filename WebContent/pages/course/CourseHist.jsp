<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/CourseHist" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/book_red.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">歷年課程資料</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="CourseHist/search.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">

		<INPUT type="submit"
			   name="method"
			   id="Query"
			   onMouseOver="showHelpMessage('以上列條件進行查詢', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Query'/>"
			   class="gSubmit">
		</td>
	</tr>
	<c:if test="${!empty sdtim}">
	
	
	
	<tr>
		<td>
		
		<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<display:table name="${sdtim}" export="false" id="row" sort="list" excludedParams="*" class="list" >
	        
	        <display:column title="班級名稱" property="ClassName" sortable="true" class="left" />
	        <display:column title="科目代碼" property="cscode" sortable="true" class="left" />
	        <display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	        <display:column title="教師姓名" property="cname" sortable="true" class="left" />
	        <display:column title="選別" property="opt" sortable="true" class="left" />
	        <display:column title="學分" property="credit" sortable="true" class="left" />
	        <display:column title="時數" property="thour" sortable="true" class="left" />
	        <display:column title="樣本數" property="samples" sortable="true" class="right" />
	        <display:column title="評量" property="avg" sortable="true" class="right" />
	        <display:column title="下載大綱" property="syll" nulls="false" class="right" href="/CIS/Print/teacher/SylDoc.do" paramId="sOid" paramProperty="Oid" />
	        <display:column title="每週大綱" property="syll_sub" nulls="false" class="right" href="/CIS/Print/teacher/SylDoc.do" paramId="sOid" paramProperty="Oid" />
	        <display:column title="下載簡介" property="intro" nulls="false" class="right" href="/CIS/Print/teacher/IntorDoc.do" paramId="sOid" paramProperty="Oid" />
	      </display:table>
		
		<br>
		
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		</td>
	</tr>
	</c:if>
	
	
	
</html:form>
</table>
