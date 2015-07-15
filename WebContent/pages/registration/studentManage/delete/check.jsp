<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Registration/StudentManager" method="post" onsubmit="init('系統處理中...')">
	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr height="30">
					<td width="28" align="right">
					<img src="images/folder_lock.gif">
					</td>
					<td align="left">
						&nbsp;學籍管理&nbsp;
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
		
		
		<table width="100%">
		
			<c:forEach items="${mdelete}" var="m" varStatus="cm">
			<tr>
				<td align="left" valign="top">
				
				<table>
					<tr>
						<td><img src="images/user.gif"/></td>
						<td>${m.student_no} 即將刪除, 系統將一併清除相關資料</td>
					</tr>
				</table>
				
				
				</td>
			</tr>
			<tr>
				<td>				
				<c:if test="${!empty m.Seld}">
				<table class="hairLineTable" align="left" width="24%">
					<c:forEach items="${m.Seld}" var="d">
					<tr>
						<td class="hairLineTdF">										
						<input type="checkBox" chedked disabled/>												
						</td>
						<td class="hairLineTdF">
						${d.chi_name}
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
				
				<c:if test="${!empty m.adce}">
				<table class="hairLineTable" align="left" width="24%">
					<c:forEach items="${m.adcd}" var="d">
					<tr>
						<td class="hairLineTdF">										
						<input type="checkBox" chedked disabled/>												
						</td>
						<td class="hairLineTdF">
						${d}
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
				
				<c:if test="${!empty m.ScoreHist}">
				<table class="hairLineTable" align="left" width="24%">
					<c:forEach items="${m.ScoreHist}" var="d">
					<tr>
						<td class="hairLineTdF">										
						<input type="checkBox" chedked chedked disabled/>												
						</td>
						<td class="hairLineTdF">
						${d.chi_name}: ${d.score}
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
				
				<c:if test="${!empty m.wwpass}">
				<table class="hairLineTable" align="left" width="24%">
					<c:forEach items="${m.wwpass}" var="d">
					<tr>
						<td class="hairLineTdF">										
						<input type="checkBox" chedked disabled/>												
						</td>
						<td class="hairLineTdF">
						${d}
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>				
				
				</td>
			</tr>			
			
			<tr>
				<td align="left" valign="top">
				
				<c:if test="${!empty m.Tran}">
				<table class="hairLineTable" align="left" width="24%">
					<c:forEach items="${m.Tran}" var="d">
					<tr>
						<td class="hairLineTdF">										
						<input type="checkBox" chedked disabled/>												
						</td>
						<td class="hairLineTdF">
						${d}
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>				
				
				<c:if test="${!empty m.Gmark}">
				<table class="hairLineTable" align="left" width="24%">
					<c:forEach items="${m.Gmark}" var="d">
					<tr>
						<td class="hairLineTdF">
										
						<input type="checkBox" chedked disabled/>
												
						</td>
						<td class="hairLineTdF">
						${d}
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
				
				<c:if test="${!empty m.Rmark}">
				<table class="hairLineTable" align="left" width="24%">
					<c:forEach items="${m.Rmark}" var="d">
					<tr>
						<td class="hairLineTdF">										
						<input type="checkBox" chedked disabled/>												
						</td>
						<td class="hairLineTdF">
						${d}
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
				
				<c:if test="${!empty m.just}">
				<table class="hairLineTable" align="left" width="24%">
					<c:forEach items="${m.just}" var="d">
					<tr>
						<td class="hairLineTdF">
										
						<input type="checkBox" chedked disabled/>
												
						</td>
						<td class="hairLineTdF">
						${d}
						</td>
					</tr>
					</c:forEach>
				</table>		
				</c:if>
				
				</td>
			</tr>			
			
			</c:forEach>
		</table>
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit"
		name="method"
	   	value="<bean:message
	   	key='DeleteConfirm'/>"
	   	class="CourseButton" id="DeleteConfirm"
	  	onMouseOver="showHelpMessage('確定刪除學生及相關資料', 'inline', this.id)" 
	  	onMouseOut="showHelpMessage('', 'none', this.id)"/><INPUT type="submit"
		name="method"
	   	value="<bean:message
	   	key='Cancel'/>"
	   	class="CourseButton" id="Cancel"
	  	onMouseOver="showHelpMessage('重新查詢', 'inline', this.id)" 
	  	onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
</html:form>
</table>
