<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>	
	
<table width="100%">
<html:form action="/Personnel/ContractHistManager" method="post" onsubmit="init('任職歷程記錄中, 請稍後')">
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
		<tr>
		<td class="hairLineTdF">



	<table width="100%">
		<tr>
			<td width="1">
			<img id="folder_orange" src="images/folder_orange.png" style="display:inline;"><img id="folder_orange_open" src="images/folder_orange_open.png" style="display:none;">
			</td>
			<td width="100%" onClick="folderOrange(), showInfo('contract')" style="cursor:pointer;" align="left">
			聘書歷程
			</td>
		</tr>
	</table>
	
<script>
function folderOrange(){	
	if(document.getElementById("folder_orange").style.display=="inline"){
		document.getElementById("folder_orange").style.display="none";
		document.getElementById("folder_orange_open").style.display="inline";
	}else{
		document.getElementById("folder_orange").style.display="inline";
		document.getElementById("folder_orange_open").style.display="none";
	}
}
</script>

	<table width="100%">
		<tr>
			<td colspan="2" id="contract" style="display:none;">
			<table width="100%">
				<tr>
					<td>														
					<input type="hidden" name="aIdno" value="${aEmpl.idno}" />
					<c:if test="${!empty contracts}" >																	
					
					
					<table class="hairLineTable" width="99%" align="center">
						<tr>
							<td class="hairLineTdF" align="center"><font size="1">主聘單位</font></td>
							<td class="hairLineTdF" align="center"><font size="1">分類/轉兼任</font></td>
							<td class="hairLineTdF" align="center"><font size="1">職級</font></td>
							<td class="hairLineTdF" align="center"><font size="1">審定</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證書/聘書字號</font></td>
							<td class="hairLineTdF" align="center"><font size="1">期間</font></td>
						</tr>
					<c:forEach items="${contracts}" var="o">
					<tr>
						<td class="hairLineTd" nowrap align="center">
						<input type="hidden" name="Oid" value="${o.Oid}" />
						<select name="deptNo" class="smallinput">
							<c:forEach items="${allUnit}" var="ad">
							<option <c:if test="${o.deptNo==ad.idno}">selected</c:if> value="${ad.idno}">${ad.name}</option>
							</c:forEach>
						</select>
						</td>
						<td class="hairLineTd">
						
						<select name="partime" class="smallinput">
							<option <c:if test="${o.partime==0}">selected</c:if> value="0">專</option>
							<option <c:if test="${o.partime==1}">selected</c:if> value="1">兼</option>
						</select><select 
						name="type" class="smallinput">
							<c:forEach items="${allType}" var="ad">
							<option <c:if test="${o.type==ad.id}">selected</c:if> value="${ad.id}">${ad.name}</option>
							</c:forEach>
						</select>
						
						</td>
						<td class="hairLineTd">
						
						<select name="level" class="smallinput">
							<c:forEach items="${allLevel}" var="ad">
							<option <c:if test="${o.level==ad.id}">selected</c:if> value="${ad.id}">${ad.name}</option>
							</c:forEach>
						</select>
						
						</td>
						<td class="hairLineTd">
						
						<select name="markup" class="smallinput">
							<c:forEach items="${allMarkup}" var="ad">
							<option <c:if test="${o.markup==ad.id}">selected</c:if> value="${ad.id}">${ad.name}</option>
							</c:forEach>
						</select>
						
						</td>
						<td class="hairLineTd" nowrap>						
						<input size="10" type="text" class="smallinput" name="license_no" value="${o.license_no}" /><input 
						size="14" type="text" class="smallinput" name="contract_no" value="${o.contract_no}" />
						</td>
						<td class="hairLineTd">
						<input size="10" type="text" class="smallinput" name="start_date" value="${o.start_date}" /><input 
						size="10" type="text" class="smallinput" name="end_date" value="${o.end_date}" />
						</select>
						</td>							
							
						</tr>
					</c:forEach>	
					</table>
					
</c:if>
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					<table class="hairLineTable" width="99%" align="center" id="newContract" style="display:none;">
						<tr>
							<td class="hairLineTdF" align="center"><font size="1">主聘單位</font></td>
							<td class="hairLineTdF" align="center"><font size="1">分類</font></td>
							<td class="hairLineTdF" align="center"><font size="1">職級</font></td>
							<td class="hairLineTdF" align="center"><font size="1">審定</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證書/聘書字號</font></td>
							<td class="hairLineTdF" align="center"><font size="1">期間</font></td>
						</tr>
					
						<tr>
						<td class="hairLineTd" nowrap align="center">
						<input type="hidden" name="Oid" />
						<select name="deptNo" class="smallinput">
							<c:forEach items="${allUnit}" var="ad">
							<option value="${ad.idno}">${ad.name}</option>
							</c:forEach>
						</select>
						</td>
						<td class="hairLineTd">
						<select name="partime" class="smallinput">
							<option value="0">專</option>
							<option value="1">兼</option>
						</select><select 
						name="type" class="smallinput">
							<c:forEach items="${allType}" var="ad">
							<option value="${ad.id}">${ad.name}</option>
							</c:forEach>
						</select>
									
						
						</td>
						<td class="hairLineTd">
						
						<select name="level" class="smallinput">
							<c:forEach items="${allLevel}" var="ad">
							<option value="${ad.id}">${ad.name}</option>
							</c:forEach>
						</select>
						
						</td>
						<td class="hairLineTd">
						
						<select name="markup" class="smallinput">
							<c:forEach items="${allMarkup}" var="ad">
							<option value="${ad.id}">${ad.name}</option>
							</c:forEach>
						</select>
						
						</td>
						<td class="hairLineTd" nowrap>						
						<input size="12" type="text" class="smallinput" name="license_no" /><input 
						size="14" type="text" class="smallinput" name="contract_no" />
						</td>
						<td class="hairLineTd">
						<input size="10" type="text" class="smallinput" name="start_date" /><input 
						size="10" type="text" class="smallinput" name="end_date" />
						</td>							
							
						</tr>
					</table>
					
					
					
					
					
					
					
					
					
					
					
					<table cellspacing="5" cellpadding="0">
						<tr>
							<td>
						
								
										
										
										
										<input type="button" value="新增聘書" id="setContract" onClick="showObj('newContract')" 
										onMouseOver="showHelpMessage('新增', 'inline', this.id)" 
										onMouseOut="showHelpMessage('', 'none', this.id)" 
										onClick="showObj('newContract')" class="gGreen">
																				
										
										<INPUT type="submit" name="method" id="EditContractHist" 
											onMouseOver="showHelpMessage('將以上聘書資訊寫入資料庫', 'inline', this.id)" 
											onMouseOut="showHelpMessage('', 'none', this.id)"
											value="<bean:message key='EditContractHist' bundle='PSN'/>"
											class="gSubmit">
								
														
							</td>
						</tr>
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












	