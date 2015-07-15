<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<tr>
		<td>
		
			
		<table class="hairLineTable" width="99%">
			<tr>
			<td class="hairLineTdF">
	
			<table width="100%">
				<tr>
					<td width="1">
					<img id="folder_green" src="images/folder_green.png" style="display:inline;"><img id="folder_green_open" src="images/folder_green_open.png" style="display:none;">
					</td>
					<td width="100%" onClick="folderGreen(), showInfo('pif')" style="cursor:pointer;">
					選課及成績
					</td>
				</tr>

				<tr id="pif" style="display:none">
					<td colspan="2">
						
					<table width="100%">
						<tr>
							<td>
							
							<c:forEach items="${myScore}" var="s">
							<table width="98%" class="hairLineTable">
								<tr>
									<td class="hairLineTd" colspan="6">
									<b>${s.school_year}</b> 學年
									</td>
								</tr>
								<c:forEach items="${s.scores}" var="ss">
								<tr>
									<td class="hairLineTdF" align="left">${ss.ClassName}</td>
									<td class="hairLineTdF" width="50%">${ss.cscode} - ${ss.chi_name}</td>
									<td class="hairLineTdF" align="center">${ss.opt}</td>
									<td class="hairLineTdF" align="right">${ss.score}</td>
									<td class="hairLineTdF" align="center">${ss.evgr_type}</td>
								</tr>
								</c:forEach>
							</table>
							
							</c:forEach>
									
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