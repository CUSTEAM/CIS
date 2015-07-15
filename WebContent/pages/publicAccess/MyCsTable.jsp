<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>
<html:form action="/PublicAccess/MyCsTable" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">

<!-- 標題列 start-->	
	<tr height="30">
		<td class="fullColorTable" width="100%">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/email_start.gif" id="piftitle"></div>
		<div nowrap style="float:left;"><font class="gray_15">課表查詢</font></div>
		</td>
	</tr>

		
		<tr>
			<td>
			<!-- table width="99%" class="hairLineTable">
				<tr>
					<td class="hairLineTdF" nowrap>教師或學生</td>
					<td class="hairLineTdF">
					<input type="text" style="font-size:18px;" size="8" disabled />
					</td>
					<td class="hairLineTdF">
					<input type="button" class="gSubmitSmall" value="查詢"/>					
					</td>
					<td class="hairLineTdF" width="100%"><font size="-1">輸入後請點選列表中的人員</font></td>
				</tr>
			</table-->
			
			<table width="99%" class="hairLineTable">
				
					
					<c:forEach items="${weekday}" var="w" begin="1" end="7">
					<td class="hairLineTdF" width="14%" align="center">${w}</td>
					</c:forEach>
				</tr>
			
				<tr>
					<td class="hairLineTdF"></td>	
					<td class="hairLineTdF" width="14%" align="center">星期一</td>
					<td class="hairLineTdF" width="14%" align="center">星期二</td>
					<td class="hairLineTdF" width="14%" align="center">星期三</td>
					<td class="hairLineTdF" width="14%" align="center">星期四</td>
					<td class="hairLineTdF" width="14%" align="center">星期五</td>
					<td class="hairLineTdF" width="14%" align="center">星期六</td>
					<td class="hairLineTdF" width="14%" align="center">星期日</td>
				</tr>
			
				<c:forEach begin="1" end="14" var="c">
				
				<c:if test="${c==11}">
				<tr>			
					
					<c:forEach items="${weekday}" var="w" begin="1" end="7">
					<td class="hairLineTdF" width="14%" align="center">${w}</td>
					</c:forEach>
				</tr>
				<tr>
					<td class="hairLineTdF">夜</td>	
					<td class="hairLineTdF" width="14%" align="center">星期一</td>
					<td class="hairLineTdF" width="14%" align="center">星期二</td>
					<td class="hairLineTdF" width="14%" align="center">星期三</td>
					<td class="hairLineTdF" width="14%" align="center">星期四</td>
					<td class="hairLineTdF" width="14%" align="center">星期五</td>
					<td class="hairLineTdF" width="14%" align="center">星期六</td>
					<td class="hairLineTdF" width="14%" align="center">星期日</td>
				</tr>
				</c:if>
				
				<tr height="75">
					<td class="hairLineTdF">${c}</td>		
					<c:forEach begin="1" end="7" var="w">		
					<td class="hairLineTdF" style="font-size:14px;" valign="top">					
						<c:forEach items="${allClass}" var="a">
						<c:if test="${a.week==w && (c>=a.begin && c<=a.end)}">					
						
						${a.ClassName}<br>
						${a.chi_name}<br>
						${a.place}教室
						
						</c:if>
						</c:forEach>
						</div>
					</td>		
					</c:forEach>
				</tr>
				</c:forEach>		
			</table>
			
			</td>
		</tr>
	
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>

</table>
</html:form>