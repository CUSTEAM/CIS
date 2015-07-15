<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>	
	
<table width="100%">
<html:form action="/Personnel/DegreeHistManager" method="post" onsubmit="init('記錄中, 請稍後')">
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
		<tr>
		<td class="hairLineTdF">



	<table width="100%">
		<tr>
			<td width="1">
			<img id="folder_green" src="images/folder_green.png" style="display:inline;"><img id="folder_green_open" src="images/folder_green_open.png" style="display:none;">
			</td>
			<td width="100%" onClick="folderGreen(), showInfo('degrees')" style="cursor:pointer;" align="left">
			最高學歷
			</td>
		</tr>
	</table>
	
<script>
function folderGreen(){	
	if(document.getElementById("folder_green").style.display=="inline"){
		document.getElementById("folder_green").style.display="none";
		document.getElementById("folder_green_open").style.display="inline";
	}else{
		document.getElementById("folder_green").style.display="inline";
		document.getElementById("folder_green_open").style.display="none";
	}
}
</script>
	<table width="100%">
		<tr>
			<td colspan="2" id="degrees" style="display:none;">
			<table width="100%">
				<tr>
					<td>														
					<input type="hidden" name="aIdno" value="${aEmpl.idno}" />
																						
					
<c:if test="${!empty degrees}" >					
					<table class="hairLineTable" width="99%" align="center">
						<tr>
							<td class="hairLineTdF" align="center"><font size="1">順序</font></td>
							<td class="hairLineTdF" align="center"><font size="1">畢業學校</font></td>
							<td class="hairLineTdF" align="center"><font size="1">畢業科系</font></td>
							<td class="hairLineTdF" align="center"><font size="1">取得學位</font></td>
							<td class="hairLineTdF" align="center"><font size="1">畢肆業</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證書字號</font></td>
						</tr>
					<c:forEach items="${degrees}" var="d">
						<tr>
							<td class="hairLineTdF" align="center">
							<input type="hidden" name="Oid" value="${d.Oid}" />
							<select name="sequence" class="smallInput">
								<option <c:if test="${d.sequence==1}">selected</c:if> value="1">最近1個</option>
								<option <c:if test="${d.sequence==2}">selected</c:if> value="2">最近2個</option>
								<option <c:if test="${d.sequence==3}">selected</c:if> value="3">最近3個</option>
								<option <c:if test="${d.sequence==4}">selected</c:if> value="4">最近4個</option>
								<option <c:if test="${d.sequence==5}">selected</c:if> value="5">最近5個</option>
								
							</select>
							</td>
							<td class="hairLineTdF" align="center">
							<input type="text" name="school_name" value="${d.school_name}" class="smallInput" />
							</td>
							<td class="hairLineTdF" align="center">
							<input type="text" name="dept_name" value="${d.dept_name}" class="smallInput" />
							</td>
							<td class="hairLineTdF" align="center">
							<select name="no" class="smallInput">
							<c:forEach items="${allPeDegree}" var="ad">
								<option <c:if test="${d.no==ad.id}">selected</c:if> value="${ad.id}">${ad.name}</option>
							</c:forEach>
							</select>
							</td>
							<td class="hairLineTdF" align="center">
							<select name="chk" class="smallInput">
								<option value="">肆</option>
								<option <c:if test="${d.chk=='Y'}">selected</c:if> value="Y">畢</option>
							</select>
							</td>
							<td class="hairLineTdF" align="center">
							<input type="text" name="chkno" value="${d.chkno}" class="smallInput" />
							</td>
						</tr>
					</c:forEach>	
					</table>
					
</c:if>					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					<table class="hairLineTable" width="99%" align="center" id="newDegree" style="display:none;">
						<tr>
							<td class="hairLineTdF" align="center"><font size="1">順序</font></td>
							<td class="hairLineTdF" align="center"><font size="1">畢業學校</font></td>
							<td class="hairLineTdF" align="center"><font size="1">畢業科系</font></td>
							<td class="hairLineTdF" align="center"><font size="1">取得學位</font></td>
							<td class="hairLineTdF" align="center"><font size="1">畢肆業</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證書字號</font></td>
						</tr>
					
						<tr>
							<td class="hairLineTdF" align="center">
							<input type="hidden" name="Oid" />
							<select name="sequence" class="smallInput">
								<option value="1">最近1個</option>
								<option value="2">最近2個</option>
								<option value="3">最近3個</option>
								<option value="4">最近4個</option>
								<option value="5">最近5個</option>
								
							</select>
							</td>
							<td class="hairLineTdF" align="center">
							<input type="text" name="school_name" class="smallInput" />
							</td>
							<td class="hairLineTdF" align="center">
							<input type="text" name="dept_name" class="smallInput" />
							</td>
							<td class="hairLineTdF" align="center">
							<select name="no" class="smallInput">
							<c:forEach items="${allPeDegree}" var="ad">
								<option value="${ad.id}">${ad.name}</option>
							</c:forEach>
							</select>
							</td>
							<td class="hairLineTdF" align="center">
							<select name="chk" class="smallInput">
								<option value="">肆</option>
								<option value="Y">畢</option>
							</select>
							</td>
							<td class="hairLineTdF" align="center">
							<input type="text" name="chkno" class="smallInput" />
							</td>
						</tr>
					</table>
					
					
					
					
					
					
					
					
					
					
					
					<table cellspacing="5" cellpadding="0">
					<tr>
						<td>
						<input type="button" value="新增學歷" id="setDegree" style="cursor:pointer;" onMouseOver="showHelpMessage('新增', 'inline', this.id)" 
								onMouseOut="showHelpMessage('', 'none', this.id)" onClick="showObj('newDegree')" class="gGreen" />
								
						<INPUT type="submit" name="method" id="DegreeContractHist" 
								onMouseOver="showHelpMessage('將以上學歷資訊寫入資料庫', 'inline', this.id)" 
								onMouseOut="showHelpMessage('', 'none', this.id)"
								value="<bean:message key='EditDegreeHist' bundle='PSN'/>"
								class="gSubmit"/>	
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












	