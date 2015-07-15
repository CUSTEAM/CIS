<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<script>
			generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/sport_soccer.gif"></td>'+
										'<td>成績輸入 (體育課程)</td>'+
									'</tr>'+
								'</table>');
	</script>
	<html:form action="/Teacher/SportRat" method="post" onsubmit="init('儲存中, 請稍後')">
	<tr>
		<td width="100%">

		<script>
			function showTable(tableId){
				if(document.getElementById(tableId).style.display=='none'){
					document.getElementById(tableId).style.display='inline';
				}else{
					document.getElementById(tableId).style.display='none';
				}
			}
		</script>

		<table width="100%">
						<tr>
							<td>
								<table class="hairLineTable" width="99%">
								<tr>
								<td class="hairLineTdF">
									<table >
										<tr>
											<td valign="top">
											&nbsp;&nbsp;<img src="images/icon_info.gif">
											</td>
											<td width="100%" style="cursor:pointer;" onclick="if(document.getElementById('help').style.display=='none'){document.getElementById('help').style.display='inline'
											}else{document.getElementById('help').style.display='none'}">
											&nbsp;點擊此處查看說明


											<table id="help" style="display:none" width="100%">
												<tr>
													<td>
													1. 點擊課程或班級名稱的任意空白處開始對該班學生進行評分，展開後點擊則會收回(輸入的資料不受影響)<br>
													2. 輸入"術科、平時、學科 成績" 以進行運算 <font color="red">學期成績</font> (您可以善用鍵盤左側的Tab鍵以減少移動滑鼠的時間)<br>
													3. 將頁面捲軸移至最下方點擊儲存即完成評分<br>
													4. 評分完畢<font color="red">請先儲存後</font>再點 <img src="images/ico_file_excel.png" border="0"> excel, <img src="images/ico_file_word.png" border="0"> word 可下載成績冊以供簽署<br>
													<br>
													<font color="red">請注意</font>: 校務資訊系統被會在頁面閒置過久時自動登出，請儘可能在告一段落時按下"<font color="red" size="+1"><b>儲存</b></font>" 以避免資料需要重複輸入的問題
													<font size=-2>(這是整個系統的問題, 非本支程式的特殊規定)</font>

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







		<c:if test="${empty mystudents}">
		<table width="100%">
			<tr>
				<td>
					<table class="hairLineTable" width="99%">
					<tr>
					<td class="hairLineTdF">
					
					
					
					<table>
						<tr>
							<td>&nbsp;&nbsp;<img src="images/action_stop.gif"></td>
							<td>您未教授體育課程</td>
						</tr>
					</table>
					
					
					</td>
					</tr>
					</table>
				</td>
			</tr>
		</table>
		</c:if>


		<c:forEach items="${mystudents}" var="m">
		<table width="100%">
			<tr>
				<td width="100%">
				<table class="hairLineTable" width="99%">
				<tr>
				<td class="hairLineTdF">
				<table width="100%">
					<tr>
						<td>

						<table onClick="showTable('${m.dtimeOid}Id')" width="100%" style="cursor:pointer;">
							<tr>
								<td width="1"><img src="images/sport_soccer.gif"></td><td align="left" width="100">${m.ClassName}</td><td align="left">${m.chi_name}</td>
							</tr>
						</table>

			<c:if test="${empty m.students}">
			<table border="0"  width="100%" id="${m.dtimeOid}Id" style="display:none">
			<tr>
				<td width="1">
				<img src="images/blocked1.png">
				</td>
				<td align="left">
				本課程沒有選課學生
				</td>
			</tr>
			</table>
			</c:if>
			<c:if test="${not empty m.students}">
			<table class="hairLineTable" id="${m.dtimeOid}Id" style="display:none">
				<tr>
				<td colspan="7" class="hairLineTdF">
				<table width="100%">
					<tr>
						<td bgcolor="#ffffff" width="60">
						 列印檢視:
						</td>
						<td align="left">
						<a href="/CIS/list4SportRat?dtimeOid=${m.dtimeOid}&departClass=${m.ClassName}&cscode=${m.chi_name}">
						<img src="images/ico_file_excel.png" border="0"> Excel
						</a>
						</td>
					</tr>
				</table>
				</td>
			</tr>
				<tr>
					<td align="center" class="hairLineTd">班級</td>
					<td align="center" class="hairLineTd">學號</td>
					<td align="center" class="hairLineTd">姓名</td>
					<td align="center" class="hairLineTd">術科成績(50%)</td>
					<td align="center" class="hairLineTd">平時成績(30%)</td>
					<td align="center" class="hairLineTd">學科成績(20%)</td>
					<td align="center" class="hairLineTd">學期成績(100%)</td>
				</tr>



			<%int i=0;%>
			<c:forEach items="${m.students}" var="s">
			<%i=i+1;%>
			<tr>
					<td align="center" <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>
					<input type="hidden" size="2" name="seldOid" value="${s.Oid}"/>
					<input type="hidden" value="${s.student_no}" name="studentNo"/>
					<input type="hidden" value="${s.student_no}" name="${m.dtimeOid}studentNo" />
					<input type="hidden" value="${m.dtimeOid}" name="dtimeOid"/>
					${s.ClassName}</td>
					<td align="center" <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>${s.student_no}</td>
					<td align="center" <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>${s.student_name}</td>
					<td align="center" <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>
					<input type="text" size="2" name="score1" value="${s.score1}" id="${s.Oid}score1"
					onKeyUp="sum('${s.Oid}score1', '${s.Oid}score2', '${s.Oid}score3', '${s.Oid}score')"/>
					</td>
					<td align="center" <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>
					<input type="text" size="2" name="score2" value="${s.score2}" id="${s.Oid}score2"
					onKeyUp="sum('${s.Oid}score1', '${s.Oid}score2', '${s.Oid}score3', '${s.Oid}score')"/>
					</td>
					<td align="center" <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>
					<input type="text" size="2" name="score3" value="${s.score3}" id="${s.Oid}score3"
					onKeyUp="sum('${s.Oid}score1', '${s.Oid}score2', '${s.Oid}score3', '${s.Oid}score')"/>
					</td>
					<td align="center" <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>
					<input type="text" size="2" name="score" value="${s.score}" id="${s.Oid}score" />
					</td>
			</tr>
			</c:forEach>
			<tr bgcolor="#ffffff">
				<td colspan="7">
				<table width="100%">
					<tr>
						<td bgcolor="#ffffff" width="60">
						 列印檢視:
						</td>
						<td align="left">
						<a href="/CIS/list4SportRat?dtimeOid=${m.dtimeOid}&departClass=${m.ClassName}&cscode=${m.chi_name}">
						<img src="images/ico_file_excel.png" border="0"> Excel
						</a>
						</td>
					</tr>
				</table>
				</td>
			</tr>


			</table>



			</c:if>

				</td>
			</tr>
		</table>
			</td>
			</tr>
			</table>
			</td>
			</tr>
		</table>
		</c:forEach>

		</td>
	</tr>
	<tr height="30">
	<td class="fullColorTable" align="center">
	<c:if test="${!empty mystudents}"><INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="CourseButton"></c:if>
	</td>
	</tr>
	</html:form>
</table>
<script>
	function sum(score1, score2, score3, score){
		score1=document.getElementById(score1);
		score2=document.getElementById(score2);
		score3=document.getElementById(score3);
		score=document.getElementById(score);



		score.value=(score1.value*0.5)+(score2.value*0.3)+(score3.value*0.2)

	}
</script>
