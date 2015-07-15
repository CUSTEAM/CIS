<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>	
<table width="100%">
<html:form action="/Personnel/JobHistManager" method="post" onsubmit="init('任職歷程記錄中, 請稍後')">
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
		<tr>
		<td class="hairLineTdF">


	<input type="hidden" name="idno" value="${aEmpl.idno}" />
	<table width="100%">
		<tr>
			<td width="1">
			<img id="folder_red" src="images/folder_red.png" style="display:inline;"><img id="folder_red_open" src="images/folder_red_open.png" style="display:none;">
			</td>
			<td width="100%" onClick="folderRed(), showInfo('leveHist')" style="cursor:pointer;" align="left">
			任職歷程
			</td>
		</tr>
	</table>
	
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

	<table width="100%">
		<tr>
			<td colspan="2" id="leveHist" style="display:none;">
			<table width="100%">
				<tr>
					<td>														
					<c:if test="${empty aEmplSave}" >
					<table class="hairLineTable" width="99%">
						<tr>
							<td align="center" width="30" class="hairLineTdF"><img src="images/16-message-warn1.png"></td>
							<td class="hairLineTdF">查無歷年異動資訊</td>
						</tr>
					</table>
					</c:if>														
					<c:if test="${!empty aEmplSave}" >																	
					
					
					<c:forEach items="${aEmplSave}" var="ae">
					<table class="hairLineTable" width="99%">
						<tr>
							<!--td align="center" width="30" class="hairLineTdF"><img src="images/tag_orange.gif"></td-->
							<td class="hairLineTdF"><font size="1">員工分類</font></td>
							<td class="hairLineTdF"><font size="1">主聘單位<input type="hidden" value="${ae.Oid}" name="saveOid" /></font></td>
							<td class="hairLineTdF"><font size="1">標準職稱</font></td>
							<td class="hairLineTdF"><font size="1">職稱</font></td>	
							<td class="hairLineTdF"><font size="1">兼任導師</font></td>
							<td class="hairLineTdF"><font size="1">最高學歷</font></td>
							<td class="hairLineTdF" align="center"><font size="1">期間</font></td>
						</tr>
						
						<tr>
							<!--td align="center" width="30" class="hairLineTdF"><img src="images/tag_orange.gif"></td-->
							<td class="hairLineTd">
							<select class="smallInput" name="saveCategory">
								<option value="">刪除記錄</option>
								<c:forEach items="${allCategory}" var="c">
								<option <c:if test="${ae.category==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							
							
							</td>
							<td class="hairLineTd">										
							<select class="smallInput" name="saveUnit">
								<option value="">*刪除記錄</option>
								<c:forEach items="${allUnit}" var="c">
								<option <c:if test="${ae.unit==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>										
							</td>
							
							<td class="hairLineTd" nowrap>										
							<select class="smallInput" name="savePcode">
								<option value=""></option>
								<c:forEach items="${allPcode}" var="c">
								<option <c:if test="${ae.pcode==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select><select class="smallInput" name="saveDirector">
								<option value=""></option>
								<c:forEach items="${allDirector}" var="c">
								<option <c:if test="${ae.Director==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							</td>
							<td class="hairLineTd"><input type="text" name="saveSname" class="smallInput" value="${ae.sname}"/></font></td>
							
							<td class="hairLineTd">
							<select class="smallInput" name="saveTutor">
								<option value=""></option>
								<c:forEach items="${allTutor}" var="c">
								<option <c:if test="${ae.Tutor==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							</td>
							<td class="hairLineTd">
							
							<select class="smallInput" name="saveDegree">
								<option value=""></option>
								<c:forEach items="${allDegree}" var="c">
								<option <c:if test="${ae.Degree==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							
							</td>
							<td class="hairLineTd" align="center">
							<input type="text" class="smallInput" value="${ae.StartDate}" size="8" 
							name="saveStartDate" id="saveStartDate${ae.Oid}" onclick="ds_sh(this), this.value='';" /><input 
							type="text" value="${ae.EndDate}" name="saveEndDate" id="saveEndDate" size="8" 
							class="smallInput" onclick="ds_sh(this), this.value='';"/>
							</td>
						</tr>
						<tr>
							<td class="hairLineTdF"><font size="1">任職狀態</font></td>
							<td class="hairLineTdF" colspan="6"><font size="1">備註事項</font></td>
						</tr>
						<tr>
							<td class="hairLineTd">
							<select name="saveStatus" class="smallInput">
								<option value=""></option>
								<c:forEach items="${allStatus}" var="c">
								<option <c:if test="${ae.Status==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							</td>
							<td colspan="6" class="hairLineTd">
							<textarea rows="1" cols="75" name="saveRemark" class="smallInput">${ae.remark}</textarea>
							</td>
						</tr>			
					</table>
					</c:forEach>
					
					</c:if>
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
<%for(int i=0; i<5; i++){ %>
					<table class="hairLineTable" width="99%" id="hist<%=i%>" style="display:none;">
						<tr>
							<!--td align="center" width="30" class="hairLineTdF"><img src="images/tag_orange.gif"></td-->
							<td class="hairLineTdF"><font size="1">員工分類</font></td>
							<td class="hairLineTdF"><font size="1">主聘單位<input type="hidden" value="" size="4" class="smallInput" name="saveOid" /></font></td>
							<td class="hairLineTdF"><font size="1">標準職稱</font></td>
							<td class="hairLineTdF"><font size="1">職稱</font></td>	
							<td class="hairLineTdF"><font size="1">兼任導師</font></td>
							<td class="hairLineTdF"><font size="1">最高學歷</font></td>
							<td class="hairLineTdF" align="center"><font size="1">期間</font></td>
						</tr>
						
						<tr>
							<!--td align="center" width="30" class="hairLineTdF"><img src="images/tag_orange.gif"></td-->
							<td class="hairLineTd">
							<select class="smallInput" name="saveCategory">
								<c:forEach items="${allCategory}" var="c">
								<option value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							
							
							</td>
							<td class="hairLineTd">										
							<select class="smallInput" name="saveUnit">
								<c:forEach items="${allUnit}" var="c">
								<option value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>										
							</td>
							
							<td class="hairLineTd" nowrap>										
							<select class="smallInput" name="savePcode">
								<c:forEach items="${allPcode}" var="c">
								<option value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select><select class="smallInput" name="saveDirector">
								<option value=""></option>
								<c:forEach items="${allDirector}" var="c">
								<option value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							</td>
							<td class="hairLineTd"><input type="text" name="saveSname" class="smallInput" value="${ae.sname}"/></font></td>
							
							<td class="hairLineTd">
							<select class="smallInput" name="saveTutor">
								<option value=""></option>
								<c:forEach items="${allTutor}" var="c">
								<option value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							</td>
							<td class="hairLineTd">
							
							<select class="smallInput" name="saveDegree">
								<c:forEach items="${allDegree}" var="c">
								<option value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							
							
							</td>
							<td class="hairLineTd" align="center">
							<input type="text" class="smallInput" size="8" 
							name="saveStartDate" id="saveStartDate<%=i%>" onclick="ds_sh(this), this.value='';" /><input 
							type="text" name="saveEndDate" id="saveEndDate<%=i%>" size="8" 
							class="smallInput" onclick="ds_sh(this), this.value='';"/>
							</td>
						</tr>
						<tr>
							<td class="hairLineTdF"><font size="1">任職狀態</font></td>
							<td class="hairLineTdF" colspan="6"><font size="1">備註事項</font></td>
						</tr>
						<tr>
							<td class="hairLineTd">
							<select name="saveStatus" class="smallInput">
								<option value=""></option>
								<c:forEach items="${allStatus}" var="c">
								<option value="${c.idno}">${c.name}</option>
								</c:forEach>
							</select>
							</td>
							<td colspan="6" class="hairLineTd">
							<textarea rows="1" cols="75" name="saveRemark" class="smallInput"></textarea>
							</td>
						</tr>			
					</table>
<%} %>					
					
					
					<table cellspacing="5" cellpadding="0">
						<tr>
							<td>
					<input type="button" id="setHist" value="新增歷程" style="cursor:pointer;" onMouseOver="showHelpMessage('新增歷程', 'inline', this.id)" 
								onMouseOut="showHelpMessage('', 'none', this.id)" onClick="showHist()" class="gGreen"/>
								
					<input type="hidden" id="histid" value="0"><INPUT type="submit" name="method" id="EditJobHist" 
					onMouseOver="showHelpMessage('將以上任職歷程寫入資料庫', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"
					value="<bean:message key='EditJobHist' bundle='PSN'/>"
					class="gSubmit">

					</td>
						</tr>
					</table>
					
					
					
					
					
												
					</td>
				</tr>
			</table>
				
			</td>
		</tr>
			
	</table>
	
	
		</td>
		</tr>
		</table>
		</td>
	</tr>
</html:form>
</table>
<script>
	function showHist(){
		var count=(document.getElementById('histid').value);		
		document.getElementById("histid").value=(count-1+2);
		if(count==4){
			document.getElementById("setHist").style.display="none";
		}	
		document.getElementById("hist"+count).style.display="inline";
	}
</script>	