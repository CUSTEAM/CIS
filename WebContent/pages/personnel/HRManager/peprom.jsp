<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>	
<!-- 教師昇等 -->
<table width="100%">
<html:form action="/Personnel/PehistManager" method="post" onsubmit="init('記錄中, 請稍後')">
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
			<img id="folder_yellow" src="images/folder_yellow.png" style="display:inline;"><img id="folder_yellow_open" src="images/folder_yellow_open.png" style="display:none;">
			</td>
			<td width="100%" onClick="folderYellow(), showInfo('peprom')" style="cursor:pointer;" align="left">
			教師升等<img src="images/16-message-warn1.png" />
			</td>
		</tr>
	</table>
	
<script>
function folderYellow(){	
	if(document.getElementById("folder_yellow").style.display=="inline"){
		document.getElementById("folder_yellow").style.display="none";
		document.getElementById("folder_yellow_open").style.display="inline";
	}else{
		document.getElementById("folder_yellow").style.display="inline";
		document.getElementById("folder_yellow_open").style.display="none";
	}
}
</script>
	<table width="100%">
		<tr>
			<td colspan="2" id="peprom" style="display:none;">
			<table width="100%">
				<tr>
					<td>														
					<input type="hidden" name="aIdno" value="${aEmpl.idno}" />
																						
					
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
</html:form>
</table>












	