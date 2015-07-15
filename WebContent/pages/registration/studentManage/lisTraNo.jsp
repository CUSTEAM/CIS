<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<c:if test="${not empty myTraNo}" >
<tr>
		<td>
			<table class="hairLineTable" width="99%">
			<tr>
			<td class="hairLineTdF">
				
			<table width="100%">
				<tr>
					<td width="1">
					<img id="folder_grey" src="images/folder_grey.png" style="display:inline;"><img id="folder_grey_open" src="images/folder_grey_open.png" style="display:none;">
					</td>
					<td width="100%" onClick="folderGrey(); showInfo('folderGrey')" style="cursor:pointer;">
					轉學歷程
					</td>
				</tr>
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
				<tr id="folderGrey" style="display:none">
					<td colspan="2">
					
					<table width="100%">
					<tr>
					<td nowrap>
					
					
					
					<table width="100%">
					<tr>
					<td nowrap>
					
					<table class="hairLineTable" width="98%">
						<tr>
							<td class="hairLineTd">轉入資格</td>
							<td class="hairLineTd">文號</td>
						</tr>
						<c:forEach items="${myTraNo}" var="mto">
						<tr>
							<td class="hairLineTdF">${mto.schl_name}</td>
							<td class="hairLineTdF">${mto.permission_no}</td>
						</tr>
						</c:forEach>
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
			</table>
		
		</td>
	</tr>
</c:if>