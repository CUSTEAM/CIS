<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${CheckCredit!=null}">
<tr>
	<td align="center">
	<display:table name="${CheckCredit}" export="true" id="row" sort="list" excludedParams="*" class="list">
		<display:column title="學號" property="occur_status" sortable="true" class="left" />
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="班級代碼" property="ClassNo" sortable="true" class="left" />
		<display:column title="班級名稱" property="ClassName" sortable="true" class="left" />
		<display:column title="已選" property="cnt" sortable="true" class="left" />
		<display:column title="上限" property="max" sortable="true" class="left" />
		<display:column title="下限" property="min" sortable="true" class="left" />
		<display:column title="下限" property="ident_remark" sortable="true" class="left" />
		
		
		
	</display:table>



	</td>
</tr>
<tr height="40">
	<td align="center">

	<table width="98%" class="hairLineTable">
 				<tr>
   				<td class="hairLineTdF">
   					<table>
   						<tr>
   							<td>
   							 列印:
   							</td>
   							<td>
   							<a href="course/export/list4CheckCredit.jsp?type=excel">
   							<img src="images/ico_file_excel.png" border="0"> Excel
   							</a>
   							
   							</td>
   						</tr>
   					</table>
   				</td>
 				</tr>
		</table>

	</td>

</tr>
<tr height="30">
	<td class="fullColorTable">

	</td>
</tr>
</c:if>
