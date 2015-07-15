<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table width="200" class="hairLineTable" style="position:absolute; right:30px; top:20px; filter:Alpha(opacity: 0.8);filter:alpha(opacity=80);-moz-opacity:0.8; z-index:32768;">
	<tr height="50">
		<td class="hairLineTdF" onMouseOver="document.getElementById('onlineWorks').style.display='inline';" 
		onMouseOut="document.getElementById('onlineWorks').style.display='none';" style="cursor:pointer;">
		
		<table align="center">
			<tr>				
				<td>
				
				<table>
					<tr height="30">
						<td><img src="images/bell.gif"></td>
						<td>						
						<a href="/CIS/OnlineService/CaseManager.do">共有  <b>${fn:length(myOnlineWork)}</b>個待辦事項</a>
						</td>
					</tr>
				</table>
				<table style="display:none;" id="onlineWorks">
				<c:forEach items="${myOnlineWork}" var="m">
					<tr>
						<td><img src="images/16-IChat-bubble.jpg"></td>
						<td>						
						<a href="/CIS/OnlineService/CaseManager.do?docNo=${m.doc_no}">${m.doc_no}</a>
						</td>
					</tr>
				</c:forEach>
				</table>
				
				</td>
			</tr>
		</table>				
		</td>
	</tr>
</table>