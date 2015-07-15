<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>	
<!-- 在職進修 -->
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
			<img id="folder_grey" src="images/folder_grey.png" style="display:inline;"><img id="folder_grey_open" src="images/folder_grey_open.png" style="display:none;">
			</td>
			<td width="100%" onClick="folderGrey(), showInfo('pestud')" style="cursor:pointer;" align="left">
			在職進修<img src="images/16-message-warn1.png" />
			</td>
		</tr>
	</table>
	
<script>
function folderGrey(){	
	if(document.getElementById("folder_grey").style.display=="inline"){
		document.getElementById("folder_grey").style.display="none";
		document.getElementById("folder_grey_open").style.display="inline";
	}else{
		document.getElementById("folder_grey").style.display="inline";
		document.getElementById("folder_grey_open").style.display="none";
	}
}
</script>
	<table width="100%">
		<tr>
			<td colspan="2" id="pestud" style="display:none;">
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












	