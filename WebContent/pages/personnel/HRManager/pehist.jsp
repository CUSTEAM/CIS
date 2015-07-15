<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>	
<!-- 實務經驗 -->
<table width="100%">
<html:form action="/Personnel/PehistManager" method="post" onsubmit="init('記錄中, 請稍後')">
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
		<tr>
		<td class="hairLineTdF">



	<table width="100%">
		<tr>
			<td width="1">
			<img id="folder_violet" src="images/folder_violet.png" style="display:inline;"><img id="folder_violet_open" src="images/folder_violet_open.png" style="display:none;">
			</td>
			<td width="100%" onClick="folderViolet(), showInfo('pehist')" style="cursor:pointer;" align="left">
			實務經驗
			</td>
		</tr>
	</table>
	
<script>
function folderViolet(){	
	if(document.getElementById("folder_violet").style.display=="inline"){
		document.getElementById("folder_violet").style.display="none";
		document.getElementById("folder_violet_open").style.display="inline";
	}else{
		document.getElementById("folder_violet").style.display="inline";
		document.getElementById("folder_violet_open").style.display="none";
	}
}
</script>
	<table width="100%">
		<tr>
			<td colspan="2" id="pehist" style="display:none;">
			<table width="100%">
				<tr>
					<td>														
					<input type="hidden" name="aIdno" value="${aEmpl.idno}" />
																						
					
<c:if test="${!empty pehists}" >					
					<table class="hairLineTable" width="99%" align="center">
						<tr>
							<td class="hairLineTdF" align="center"><font size="1">系所</font></td>
							<td class="hairLineTdF" align="center"><font size="1">工作機構</font></td>
							<td class="hairLineTdF" align="center"><font size="1">期間</font></td>
							<td class="hairLineTdF" align="center"><font size="1">附註1</font></td>
							<td class="hairLineTdF" align="center"><font size="1">附註2</font></td>
						</tr>
					<c:forEach items="${pehists}" var="p">
						<tr>
							<td class="hairLineTdF" align="center">
							<input type="hidden" name="Oid" value="${p.Oid}" />
							<select name="deptNo" class="smallinput">
								<option value=""></option>
							<c:forEach items="${allUnit}" var="ad">
								<option <c:if test="${p.deptNo==ad.idno}">selected</c:if> value="${ad.idno}">${ad.name}</option>
							</c:forEach>
							</select>
							
							</td>
							<td class="hairLineTdF" align="center"><input class="smallinput" type="text" name="inst" value="${p.inst}"/></td>
							<td class="hairLineTdF" align="center">
							<input size="10" class="smallinput" type="text" name="begin" value="${p.begin}"/>
							<input size="10" class="smallinput" type="text" name="end" value="${p.end}"/>
							</td>
							<td class="hairLineTdF" align="center"><font size="1"><input class="smallinput" type="text" name="bdate_certname" value="${p.bdate_certname}"/></font></td>
							<td class="hairLineTdF" align="center"><font size="1"><input class="smallinput" type="text" name="edate_certno" value="${p.edate_certno}"/></font></td>
						</tr>
					</c:forEach>	
					</table>
					
</c:if>					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					<table class="hairLineTable" width="99%" align="center" id="newPehist" style="display:none;">
						<tr>
							<td class="hairLineTdF" align="center"><font size="1">系所/單位</font></td>
							<td class="hairLineTdF" align="center"><font size="1">工作機構</font></td>
							<td class="hairLineTdF" align="center"><font size="1">期間</font></td>
							<td class="hairLineTdF" align="center"><font size="1">附註1</font></td>
							<td class="hairLineTdF" align="center"><font size="1">附註2</font></td>
						</tr>
					
						<tr>
							<td class="hairLineTdF" align="center">
							<input type="hidden" name="Oid" />
							<select name="deptNo" class="smallinput">
								<option value=""></option>
							<c:forEach items="${allUnit}" var="ad">
								<option value="${ad.idno}">${ad.name}</option>
							</c:forEach>
							</select>
							
							</td>
							<td class="hairLineTdF" align="center"><input class="smallinput" type="text" name="inst" /></td>
							<td class="hairLineTdF" align="center">
							<input size="10" class="smallinput" type="text" name="begin" />
							<input size="10" class="smallinput" type="text" name="end" />
							</td>
							<td class="hairLineTdF" align="center"><font size="1"><input class="smallinput" type="text" name="bdate_certname" /></font></td>
							<td class="hairLineTdF" align="center"><font size="1"><input class="smallinput" type="text" name="edate_certno" /></font></td>
						</tr>
					</table>
					
					<table cellspacing="5" cellpadding="0">
					<tr>
						<td>
						<input type="button" value="新增實務" id="setPehist" style="cursor:pointer;" onMouseOver="showHelpMessage('新增', 'inline', this.id)" 
								onMouseOut="showHelpMessage('', 'none', this.id)" onClick="showObj('newPehist')" class="gGreen" />
								
								
								<INPUT type="submit" name="method" id="EditPeist" 
								onMouseOver="showHelpMessage('將以上資訊寫入資料庫', 'inline', this.id)" 
								onMouseOut="showHelpMessage('', 'none', this.id)"
								value="<bean:message key='EditPeist' bundle='PSN'/>"
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












	