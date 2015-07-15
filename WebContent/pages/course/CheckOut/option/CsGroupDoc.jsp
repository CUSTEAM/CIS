<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<tr>
	<td align="center">
	<display:table name="${CsGroupDoc}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
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
   							學程申請書
   							</td>
   							<td>
   							<a href="/CIS/CsGroupDoc">
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