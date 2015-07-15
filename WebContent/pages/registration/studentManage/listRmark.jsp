<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<html:form action="/Registration/RmarkManager" method="post" onsubmit="init('系統處理中...')">
<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
			<td class="hairLineTdF">
				
			<table width="100%">
				<tr>
					<td width="1">
					<img id="folder_orange" src="images/folder_orange.png" style="display:inline;"><img id="folder_orange_open" src="images/folder_orange_open.png" style="display:none;">
					</td>
					<td width="100%" onClick="folderOrange(), showInfo('Rmark')" style="cursor:pointer;">
					身份備註
					</td>
				</tr>
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
				<tr id="Rmark" style="display:none">
					<td colspan="2">					
					
					<table class="hairLineTable">
						<tr>
							<td class="hairLineTdF">
							一般註記
							</td>
							<td class="hairLineTd">
							<textarea name="remark_name" cols="50" rows="1">${myRmark.remark_name}</textarea>
							</td>
							
						</tr>
					</table>
					
					<table class="hairLineTable">
						<tr>
							<td class="hairLineTdF">
							軍種註記
							</td>
							<td class="hairLineTd">
							<textarea cols="50" rows="1" name="military">${myRmark.military}</textarea>
							</td>
							
							
						</tr>
					</table>
					
					<table class="hairLineTable">
						<tr>
							<td class="hairLineTdF">
							證照註記
							</td>							
							<td class="hairLineTd">
							<textarea cols="50" rows="1" name="certificate">${myRmark.certificate}</textarea>
							</td>
							
						</tr>
					</table>
					<INPUT type="submit" name="method" value="<bean:message key='EditRmark' bundle="REG"/>" class="CourseButton">
						
					</td>
				</tr>
					
			</table>
				
			</td>
			</tr>
			</table>
		
		</td>
	</tr>
</html:form>