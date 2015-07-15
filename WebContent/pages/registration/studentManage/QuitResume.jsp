<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<c:if test="${not empty myQuitResume}" >
<html:form action="/Registration/QuitResumeManager" method="post" onsubmit="init('系統處理中...')">
<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
		<tr>
		<td class="hairLineTdF">
				
			<table width="100%">
				<tr>
					<td width="1">
					<img id="folder_red" src="images/folder_red.png" style="display:inline;"><img id="folder_red_open" src="images/folder_red_open.png" style="display:none;">
					</td>
					<td width="100%" onClick="folderRed(), showInfo('quitresume')" style="cursor:pointer;" align="left">
					休學歷程
					</td>
				</tr>
<script>
function folderRed(){
	
	if(document.getElementById("folder_red").style.display=="inline"){
		document.getElementById("folder_red").style.display="none";
		document.getElementById("folder_red_open").style.display="inline";
	}else{
		document.getElementById("folder_red").style.display="inline";
		document.getElementById("folder_red_open").style.display="none";
	}

}
</script>					
				<tr >
					<td colspan="2" id="quitresume" style="display:none">
					<c:forEach items="${myQuitResume}" var="q">
					
					<table width="100%">
					<tr>
					<td nowrap>
					<table class="hairLineTable" align="left">
						<tr class="fullColorTrF">
							<td class="hairLineTdF" align="center" nowrap>
							<font size="1">離校學年</font>
							</td>
							<td class="hairLineTd" align="center" nowrap>
							<input type="hidden" class="smallInput" name="Oid" value="${q.Oid}" size="1" />
							<input type="text" class="smallInput" name="occur_year" value="${q.occur_year}" size="1" /><input 
							type="text" class="smallInput" name="occur_term" value="${q.occur_term}" size="1" />							
							</td>						
							<td class="hairLineTdF" class="smallInput" align="center" nowrap>
							<font size="1">班級</font>
							</td>
							<td class="hairLineTd" align="center">
							<input type="text" class="smallInput" name="depart_class" value="${q.depart_class}" size="6"/>							
							</td>
							<td class="hairLineTdF" align="center" nowrap>
							<font size="1">文號</font>
							</td>
							<td class="hairLineTd" align="center">
							<input type="text" class="smallInput" name="occur_docno" value="${q.occur_docno}" size="4"/>
							
							</td>
							<td class="hairLineTdF" align="center" nowrap>
							<font size="1">日期</font>
							</td>
							<td class="hairLineTd" align="center">
							<input type="text" class="smallInput" name="occur_date" value="${q.occur_date}" size="4" id="occur_date"
							onclick="ds_sh(this)" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>							
							</td>
						</tr>
					</table>
					
					
					
					<table class="hairLineTable" align="left">
						<tr class="fullColorTrF">
							<td class="hairLineTdF" align="center" nowrap>
							<font size="1">返校學年</font>
							</td>
							<td class="hairLineTd" align="center" nowrap>
							<input type="text" class="smallInput" name="recov_year" value="${q.recov_year}" size="1" /><input type="text" 
							class="smallInput" name="recov_term" value="${q.recov_term}" size="1" />							
							</td>						
							
							<td class="hairLineTdF" align="center" nowrap>
							<font size="1">文號</font>
							</td>
							<td class="hairLineTd" align="center">
							<input type="text" class="smallInput" name="recov_docno" value="${q.recov_docno}" size="4"/>
							
							</td>
							<td class="hairLineTdF" align="center" nowrap>
							<font size="1">日期</font>
							</td>
							<td class="hairLineTd" align="center">
							<input type="text" class="smallInput" name="recov_date" value="${q.recov_date}" size="4" id="recov_date"
							onclick="ds_sh(this)" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>							
							</td>
						</tr>
					</table>
					
					
					
					</td>
					</tr>
					</table>
					
					</c:forEach>
					
					<INPUT type="submit" name="method" value="<bean:message key='EditQuitResume' bundle="REG"/>" class="CourseButton">
					</td>
				</tr>
					
			</table>
				
			</td>
		</tr>
		</table>
		
					
		</td>
	</tr>
</html:form>
</c:if>