<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<c:if test="${not empty myTran}" >
<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
		<tr>
		<td class="hairLineTdF">
				
			<table width="100%">
				<tr>
					<td width="1">
					<img id="folder_violet" src="images/folder_violet.png" style="display:inline;"><img id="folder_violet_open" src="images/folder_violet_open.png" style="display:none;">
					</td>
					<td width="100%" onClick="folderViolet(); showInfo('folderViolet')" style="cursor:pointer;">
					轉班歷程
					</td>
				</tr>
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
				<tr id="folderViolet" style="display:none">
					<td colspan="2">
					
					
					<table width="100%">
					<tr>
					<td nowrap>
					
					<table class="hairLineTable" width="98%">
						<tr>
							<td class="hairLineTd">學年</td>
							<td class="hairLineTd">學期</td>
							<td class="hairLineTd">轉出班級</td>
							<td class="hairLineTd">轉入班級</td>
							<td class="hairLineTd">文號</td>
							<td class="hairLineTd">日期</td>
						</tr>
						<c:forEach items="${myTran}" var="mt">
						<tr>
							<td class="hairLineTdF">${mt.occur_year}</td>
							<td class="hairLineTdF">${mt.occur_term}</td>
							<td class="hairLineTdF">${mt.oClass}</td>
							<td class="hairLineTdF">${mt.nClass}</td>
							<td class="hairLineTdF">${mt.occur_docno}</td>
							<td class="hairLineTdF">${mt.occur_date}</td>
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
</c:if>