<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>	
	
<table width="100%">
	<tr>
<html:form action="/Personnel/LicenceManager" method="post" onsubmit="init('記錄中, 請稍後')">
		<td>
		<input type="hidden" name="aIdno" value="${aEmpl.idno}" />
		<table width="99%" class="hairLineTable">
		<tr>
		<td class="hairLineTdF">



	<table width="100%">
		<tr>
			<td width="1">
			<img id="folder_blue" src="images/folder_blue.png" style="display:inline;"><img id="folder_blue_open" src="images/folder_blue_open.png" style="display:none;">
			</td>
			<td width="100%" onClick="folderBlue(), showInfo('licence')" style="cursor:pointer;" align="left">
			取得證照
			</td>
		</tr>
	</table>
	
<script>
function folderBlue(){	
	if(document.getElementById("folder_blue").style.display=="inline"){
		document.getElementById("folder_blue").style.display="none";
		document.getElementById("folder_blue_open").style.display="inline";
	}else{
		document.getElementById("folder_blue").style.display="inline";
		document.getElementById("folder_blue_open").style.display="none";
	}
}
</script>

	<table width="100%">
		<tr>
			<td colspan="2" id="licence" style="display:none;">
			<table width="100%">
				<tr>
					<td>														
																						
					
<c:if test="${!empty licences}" >					
					<table class="hairLineTable" width="99%" align="center">
						<tr>
							
							<td class="hairLineTdF" align="center"><font size="1">系所</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證照類型</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證照名稱</font></td>
							<td class="hairLineTdF" align="center"><font size="1">發照機構</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證照字號</font></td>
						</tr>
					<c:forEach items="${licences}" var="l">
						<tr>
							
							<td class="hairLineTdF" align="center">
							<input type="hidden" name="Oid" value="${l.Oid}" />
							<select name="deptNo" class="smallinput">
								<option value=""></option>
							<c:forEach items="${allUnit}" var="ad">
								<option <c:if test="${l.deptNo==ad.idno}">selected</c:if> value="${ad.idno}">${ad.name}</option>
							</c:forEach>
							</td>
							<td class="hairLineTdF" align="center">							
							<select name="licence_type" class="smallinput">
								<option value=""></option>
							<c:forEach items="${allLicence}" var="ad">
								<option <c:if test="${l.licence_type==ad.id}">selected</c:if> value="${ad.id}">${ad.name}</option>
							</c:forEach>
							</td>
							<td class="hairLineTdF" align="center">
							<input class="smallinput" type="text" name="licence_name" value="${l.licence_name}"/>
							</td>
							<td class="hairLineTdF" align="center">
							<input class="smallinput" type="text" name="organ" value="${l.organ}"/>
							</td>
							<td class="hairLineTdF" align="center">
							<input class="smallinput" type="text" name="licence_no" value="${l.licence_no}"/>
							</td>
						</tr>
					</c:forEach>	
					</table>
</c:if>										
										
										
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
				<tr>
					<td id="newLicence" style="display:none;">
					
					
					
					
					
					<table class="hairLineTable" width="100%" align="center">
						<tr>
							<td class="hairLineTdF" align="center"><font size="1">系所</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證照類型</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證照名稱</font></td>
							<td class="hairLineTdF" align="center"><font size="1">發照機構</font></td>
							<td class="hairLineTdF" align="center"><font size="1">證照字號</font></td>
						</tr>					
						<tr>
							
							<td class="hairLineTdF" align="center">
							<input type="hidden" name="Oid" value="" />
							<select name="deptNo" class="smallinput">
								<option value=""></option>
							<c:forEach items="${allUnit}" var="ad">
								<option value="${ad.idno}">${ad.name}</option>
							</c:forEach>
							</td>
							<td class="hairLineTdF" align="center">							
							<select name="licence_type" class="smallinput">
								<option value=""></option>
							<c:forEach items="${allLicence}" var="ad">
								<option value="${ad.id}">${ad.name}</option>
							</c:forEach>
							</td>
							<td class="hairLineTdF" align="center">
							<input class="smallinput" type="text" name="licence_name" />
							</td>
							<td class="hairLineTdF" align="center">
							<input class="smallinput" type="text" name="organ" />
							</td>
							<td class="hairLineTdF" align="center">
							<input class="smallinput" type="text" name="licence_no" />
							</td>
						</tr>					
					</table>					
					</td>
				</tr>
			</table>
			
			<table cellspacing="5" cellpadding="0">
			<tr>
			<td>
			<input type="button" value="新增證照" id="setLicence" style="cursor:pointer;" onMouseOver="showHelpMessage('新增', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)" onClick="showObj('newLicence')" class="gGreen"/>
								
								
			<input type="submit" name="method" id="EditLicence" 
			onMouseOver="showHelpMessage('將以上資訊寫入資料庫', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)"
			value="<bean:message key='EditLicence' bundle='PSN'/>"
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
</html:form>
	</tr>
</table>












	