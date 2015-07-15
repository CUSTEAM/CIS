<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<c:if test="${!empty cores}">



<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>


		<display:table name="${cores}" export="false" sort="list" excludedParams="*" class="list" style="width:100%;">
			<display:column title="狀態" property="status" sortable="true" class="left" />
			<display:column title="最後適用學年" property="entrance" sortable="true" class="center" />
			<display:column title="系所名稱" property="dname" href="/CIS/Course/CsCoreManager.do" paramId="Oid" paramProperty="Oid" sortable="true" class="left" />
			<display:column title="關鍵字" property="key_word" href="/CIS/Course/CsCoreManager.do" paramId="Oid" paramProperty="Oid" sortable="true" class="left" />
			<display:column title="最後修改" property="empl" sortable="true" class="left" />	
			<display:column title="修改日期" property="editime" sortable="true" class="left" />
		</display:table>

		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		
		
		</td>
	</tr>
</table>

</c:if>

