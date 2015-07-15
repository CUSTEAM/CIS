<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
			<table width="99%" class="hairLineTable">
				<tr>
					<td class="hairLineTdF">
					<table width="100%" onclick="showInfo('scoreHist')">
						<tr>
							<td><img src="images/folder_page_zero.gif">
							</td>
							<td align="left" width="100%" style="cursor:pointer;">
							查看歷年不及格成績
							</td>
						</tr>
						<tr>
							<td colspan="2" id="scoreHist" style="display:none">
							<display:table name="${scoreHist}" export="true" id="row" sort="list" excludedParams="*" class="list">
							<display:column title="學年" property="school_year" nulls="false" />
							<display:column title="學期" property="school_term" nulls="false" />
							<display:column title="學號" property="student_no" nulls="false" />
							<display:column title="姓名" property="student_name" nulls="false" />
							<display:column title="課程名稱" property="chi_name" nulls="false" />
							<display:column title="課程代碼" property="cscode" nulls="false" />
							<display:column title="選別" property="opt" nulls="false" />	 
							<display:column title="學分" property="credit" nulls="false" />
							<display:column title="成績" property="score" nulls="false" />	        
   									</display:table>											
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%">
			<tr>
				<td>
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF">
						<table width="100%" onclick="showInfo('selected')">
							<tr>
								<td><img src="images/folder_bug.gif">
								</td>
								<td align="left" width="100%" style="cursor:pointer;">
								查看第${Sterm}學期已選課程
								</td>
							</tr>
							<tr>
								<td colspan="2" id="selected" style="display:none">
								<display:table name="${selected}" export="true" id="row" sort="list" excludedParams="*" class="list">											
								<display:column title="學號" property="student_no" nulls="false" />
								<display:column title="姓名" property="student_name" nulls="false" />
								<display:column title="課程名稱" property="chi_name" nulls="false" />
								<display:column title="課程代碼" property="cscode" nulls="false" />
								<display:column title="選別" property="opt" nulls="false" />	 
								<display:column title="學分" property="credit" nulls="false" />
								<display:column title="時數" property="thour" nulls="false" />
								<display:column title="已選人數" property="stdSelected" nulls="false" />
								      
    							</display:table>											
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>