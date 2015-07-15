<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<tr>
		<td>
		
		
		<table width="100%">
			<tr>
				<td colspan="2" width="100%">
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
						<img src="images/allowed1.png">
						</td>
						<td width="100%">
						${aStudent.studentName}要${workType}了
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
						學籍資料
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
								身分證字號: ${aStudent.idno}<br>
								性別: ${aStudent.sex}<br>
								出生日期: ${aStudent.birthday}<br>
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
				
<c:if test="${wwpass!=null}">
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
						預備刪除系統帳號
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								帳號: ${wwpass.username}<br>
								密碼: ${wwpass.password}
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
</c:if>				
				


<c:if test="${!empty myDtime1}">
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
						預備刪除選課
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
</c:if>

<c:if test="${!empty myDtimeAdd}">
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
								<c:forEach items="${myDtimeAdd}" var="d1">
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
</c:if>







<c:if test="${!empty scoreList}">
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
						預備建立抵免
						</td>
					</tr>	
					<tr>
						<td colspan="2">
						
						<table>
							<tr>
								<td>
								<c:forEach items="${scoreList}" var="s">
								${s.schoolYear}-${s.schoolTerm}: ${s.stdepartClass}-${s.cscode}, opt:${s.opt}, credit:${s.credit}<br>
								
								
								
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
</c:if>			


















<c:if test="${gmarkList!=null}">			
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
						<img src="images/16-tag-pencil.png">
						</td>
						<td width="100%">
						預備建立學生記錄
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								<c:forEach items="${gmarkList}" var="gm">
									${gm.schoolYear}-${gm.schoolTerm}: ${gm.occurCause}${gm.occurStatus}${gm.remark}<br>
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
</c:if>
				
<c:if test="${tran!=null}">
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
						<img src="images/group_gear.png">
						</td>
						<td width="100%">
						預備建立轉班記錄
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								${tran.occurYear}-${tran.occurTerm}: 由${tran.oldDepartClass}轉${tran.newDepartClass}
								
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
</c:if>
				
				
				
				
				
				
<c:if test="${quitresume!=null}">
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
						<img src="images/group_gear.png">
						</td>
						<td width="100%">
						預備建立休學記錄
						</td>
					</tr>
					<tr>
						<td colspan="2">
						
						
						<table>
							<tr>
								<td>
								
								${quitresume.occurYear}-${quitresume.occurTerm}休, ${quitresume.recovYear}-${quitresume.recovTerm}復
								
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
</c:if>
				
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
						   value="<bean:message key='ChangeStmdConfirm'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Back'/>" disabled
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