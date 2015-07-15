<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${checkReSelds!=null }">
<tr>
	<td>
	<display:table name="${checkReSelds}" export="false" id="row" pagesize="10" sort="list" defaultsort="3" excludedParams="*" class="list">
		<display:column title="<img src='images/16-cube-bug.png'>" property="box" nulls="false" class="center"/>
	    <display:column title="學生班級" property="className2" sortable="true" class="left" />
	    <display:column title="學號" property="student_no" sortable="true" class="left" />
	    <display:column title="姓名" property="student_name" sortable="true" class="left" />
	    <display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	    <display:column title="星期" property="week" sortable="true" class="center" />
	    <display:column title="開始節" property="begin" sortable="true" class="center" />
	    <display:column title="結束節" property="end" sortable="true" class="center" />
	    <display:column title="重複課程" property="chi_name2" sortable="true" class="left" />
	    <display:column title="星期" property="week2" sortable="true" class="center" />
	    <display:column title="開始" property="begin2" sortable="true" class="center" />
	    <display:column title="結束" property="end2" sortable="true" class="center" />
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
    							<a href="course/export/list4CheckOutSeld.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/list4CheckOutSeld.jsp?type=word">
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
