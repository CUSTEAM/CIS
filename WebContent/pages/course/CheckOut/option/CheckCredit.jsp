<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${CheckCredit!=null}">
<tr>
	<td align="center">
	<display:table name="${CheckCredit}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="班級代碼" property="ClassNo" sortable="true" class="left" />
		<display:column title="班級名稱" property="ClassName" sortable="true" class="left" />
		<display:column title="學分數" property="creditLess" sortable="true" class="left" />
		<display:column title="時數" property="thourLess" sortable="true" class="left" />
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
   							 選擇輸出格式:
   							</td>
   							<td>
   							<a href="course/export/list4CheckCredit.jsp?type=excel">
   							<img src="images/ico_file_excel.png" border="0"> Excel
   							</a>
   							<a href="course/export/list4CheckCredit.jsp?type=word">
   							<img src="images/ico_file_word.png" border="0"> Word
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
