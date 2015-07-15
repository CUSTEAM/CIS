<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<tr>
		<td>
		
		<table width="100%">
			<tr>
				<td width="50%" valign="top">
				
				<table width="100%">
				<tr>
				<td>
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
				
				<table width="100%">
					<tr>
						<td width="1">
						<img src="images/24-member.png">
						</td>
						<td width="100%" onClick="showHelp()">
						建立學籍資料
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								
																
								
								姓名: ${aStudent.studentName}<br>
								學號: ${aStudent.studentNo} ${aStudent.studentName}<br>
								班級: ${aStudent.classNo} ${aStudent.className}<br>
								身份證字號: ${aStudent.idno}<br>
								性別: ${aStudent.sex}<br>
								出生日期: ${showBirthday}<br>
								省籍: ${aStudent.birth_province}, ${aStudent.birth_county}<br>
								通訊地址: ${aStudent.curr_post}, ${aStudent.curr_addr}<br>
								永久地址: ${aStudent.perm_post}, ${aStudent.perm_addr}<br>
								連絡電話: ${aStudent.telephone}<br>
								家長姓名: ${aStudent.parent_name}<br>
								入學年度: ${aStudent.entrance}<br>
								前學程畢業年度: ${aStudent.gradyear}<br>
								身份: ${aStudent.ident}<br>
								組別: ${aStudent.divi}<br>
								畢業學歷: ${aStudent.schl_code}, ${aStudent.schl_name}, ${aStudent.grad_dept}, ${aStudent.gradu_status}<br>
								年度: ${aStudent.occur_year}, 第${aStudent.occur_term}學期<br>
								日期: ${aStudent.occur_date}<br>
								狀態: ${aStudent.occur_status}<br>
								原因: 不明<br>
								畢業號: 不可能有畢業號<br>
								文號: ${aStudent.occur_docno}
								
								
								
								
								
								</td>
							</tr>
						</table>
						
						
						</td>
					</tr>				
				</table>
				
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				</td>
				</tr>
				</table>
				
				</td>
				
				
				<td width="50%" valign="top">
				
				<table width="100%">
				<tr>
				<td>
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
				
				<table width="100%">
					<tr>
						<td width="1">
						<img src="images/16-image-check.png">
						</td>
						<td width="100%" onClick="showHelp()">
						轉入資格
						</td>
					</tr>	
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								<c:if test="${aStudent.iType=='tra'}">
								畢業學歷: ${aStudent.TOL_schl_name}<br>
								畢業證號: ${aStudent.TOL_permission_no}
								</c:if>
								<c:if test="${aStudent.iType=='new'}">
								入學核準號:${aStudent.entrno}
								</c:if>								
								</td>
							</tr>
						</table>
						
						
						</td>
					</tr>			
				</table>
				
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				</td>
				</tr>
				</table>
				
				<table width="100%">
				<tr>
				<td>
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
				
				<table width="100%">
					<tr>
						<td width="1">
						<img src="images/16-security-key.png">
						</td>
						<td width="100%">
						系統帳號
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								帳號: ${username}<br>
								密碼: ${password}
								</td>
							</tr>
						</table>
						
						
						</td>
					</tr>				
				</table>
				
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				</td>
				</tr>
				</table>
				
				
				
				<table width="100%">
				<tr>
				<td>
				<div class="modulecontainer filled nomessages">
				<div class="first">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				<div>
				<div>
				
				<table width="100%">
					<tr>
						<td width="1">
						<img src="images/16-books-blue.gif">
						</td>
						<td width="100%" align="left">
						基本選課
						</td>
					</tr>	
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								<c:forEach items="${myDtime1}" var="d1">
								<input name="myDtime1" value="d1.Oid" type="checkbox" checked disabled/>${d1.cscode}${d1.chi_name}<br>
								</c:forEach>
								
								<c:forEach items="${myDtime2}" var="d2">
								<input type="checkbox" name="myDtime2" value="${d2.Oid}"/>${d2.cscode}${d2.chi_name}<br>
								</c:forEach>
								</td>
							</tr>
						</table>
						
						</td>
					</tr>			
				</table>
				
				</div>
				</div>
				<div class="last">
				<span class="first"></span>
				<span class="last"></span>
				</div>
				</div>
				</td>
				</tr>
				</table>
				
				</td>
			</tr>
			
		</table>		
		
		</td>
	</tr>	
	
	<tr>
		<td class="fullColorTable" align="center" width="100%">
		
		<table>
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='CrateNewStudent'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Back' />" disabled
													   class="CourseButton"><INPUT type="submit"
													   						 name="method"
													   						 value="<bean:message
													   						 key='Cancel'/>"
													   						 class="CourseButton">
				</td>
			</tr>
		</table>
		
		</td>
	</tr>