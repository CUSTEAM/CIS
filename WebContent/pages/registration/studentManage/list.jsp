<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<tr>
		<td>
		<table width="100%">
			<tr>
				<td>
				<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
				<display:table name="${students}" pagesize="10" id="row" sort="list" class="list">
				<display:column title="<script>generateTriggerAll(${fn:length(students)}, 'stus'); </script>" class="center" >
	          		<script>generateCheckbox("${row.student_no}", "stus")</script>
	          	</display:column>
	          	<display:column title="學號" property="student_no" sortable="true" class="center" />
	          	<display:column title="姓名" property="student_name" sortable="true" class="center" />
				<display:column title="班級名稱" property="ClassName" sortable="true" class="center" />
				<display:column title="狀態" property="name" sortable="true" class="center" />				
				</display:table>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 列表 end-->	
	<tr>
		<td align="left">

		<%@ include file="printMenu4List.jsp"%>
		</td>

	</tr>
	
	<tr>
		<td class="fullColorTable">
		<table align="center">
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Modify'/>"
						   class="gSubmit">
						   
						   
					<INPUT type="submit"
						   name="method"
						   value="<bean:message
						   key='Cancel'/>"
						   class="gCancle" />
													   
					
				</td>
			</tr>
		</table>
		</td>
	</tr>