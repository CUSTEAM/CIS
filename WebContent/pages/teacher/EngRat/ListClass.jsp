<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<c:forEach items="${engRats}" var="e">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
			
			<table width="100%">
			<c:choose>
				<c:when test="${locale=='en'}">
				<tr>
					<td width="1" style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')"><img src="images/icon/user.gif"></td>
					<td style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')" nowrap>${e.EngName}</td>
					<td style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')" width="1"> - </td>
					<td style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')" align="left" nowrap>${e.Grade}</td>
					<td width="100%" style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')"></td>
					<td><a href="/CIS/EngRatReport?dtimeOid=${e.Oid}"><img src="images/ico_file_excel.png" border="0"></a></td>
					<td align="left" nowrap class="gray_15"><a href="/CIS/EngRatReport?dtimeOid=${e.Oid}">下載成績冊</a></td>
				</tr>
				
				</c:when>
				<c:otherwise>
				<tr>
					<td width="1" style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')"><img src="images/icon/user.gif"></td>
					<td style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')" nowrap class="gray_15">${e.ClassName}</td>
					<td style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')" width="1" class="gray_15"> - </td>
					<td style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')" align="left" nowrap class="gray_15">${e.chi_name}</td>
					<td width="100%" style="cursor:pointer;" onClick="showInfo('cs${e.Oid}')"></td>
					<td><a href="/CIS/EngRatReport?dtimeOid=${e.Oid}"><img src="images/ico_file_excel.png" border="0"></a></td>
					<td align="left" nowrap><a href="/CIS/EngRatReport?dtimeOid=${e.Oid}" class="gray_15">下載成績冊</a></td>				
				</tr>						
				</c:otherwise>
			</c:choose>
			</table>
			
			<table id="cs${e.Oid}" class="hairLineTable" width="99%" align="center" style="display:none">
				<tr class="hairLineTdF">
					<td align="center" width="120" style="cursor:default;"><bean:message key="EngRat.student" bundle="COU"/></td>
					<td name="onlineTd${e.Oid}" style="cursor:pointer;" id="onlineTd${e.Oid}" align="center" onClick="changEdit('online${e.Oid}', '${e.Oid}'), chengColor('onlineTd${e.Oid}', '${e.Oid}')"><bean:message key="EngRat.onlinetest" bundle="COU"/></td>
					<td name="dayTd${e.Oid}" style="cursor:pointer;" id="dayTd${e.Oid}" align="center" onClick="changEdit('day${e.Oid}', '${e.Oid}'), chengColor('dayTd${e.Oid}', '${e.Oid}')"><bean:message key="EngRat.normal" bundle="COU"/></td>
					<td align="center"><bean:message key="EngRat.saveme" bundle="COU"/></td>
					<td align="center"><bean:message key="EngRat.activate" bundle="COU"/></td>
					<td align="center"><bean:message key="engRat.med" bundle="COU"/></td>
					<td align="center">TOEIC</td>
					<td align="center">校內期末考</td>
					<td align="center"><bean:message key="EngRat.score" bundle="COU"/></td>
				</tr>						
				
				<c:forEach items="${e.students}" var="s">				
				<tr class="hairLineTdF">
					<td id="stImage${s.student_no}" onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', this.id)" 
		 		 	 style="cursor:default;" onMouseOut="showHelpMessage('', 'none', this.id)">
						<table>
						<tr>
						<td nowrap id="stuNo${s.student_no}" onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', this.id)" 
		 		 		 onMouseOut="showHelpMessage('', 'none', this.id)">${s.student_no}</td>
						<td nowrap id="stuName${s.student_no}" onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', this.id)" 
		 		 		 onMouseOut="showHelpMessage('', 'none', this.id)">${s.student_name}</td>
						</tr>
						</table>
					</td>
					
					<td align="center">
					<table name="online${e.Oid}" id="online${e.Oid}" style="display:none" align="left">
					<tr>
					<td>
					<input type="hidden" name="seldOid" value="${s.Oid}" />
					<input type="text" size="2" maxlength="3"
					name="score11" id="score11${s.Oid}" value="${s.score11}" onKeyUp="getOnlines(this.id), countScore('${s.Oid}')" /><input type="text" size="2"
					name="score12" id="score12${s.Oid}" value="${s.score12}" onKeyUp="getOnlines(this.id), countScore('${s.Oid}')" /><input type="text" size="2"
					name="score13" id="score13${s.Oid}" value="${s.score13}" onKeyUp="getOnlines(this.id), countScore('${s.Oid}')" /><input type="text" size="2"
					name="score14" id="score14${s.Oid}" value="${s.score14}" onKeyUp="getOnlines(this.id), countScore('${s.Oid}')" />
					
					</td>
					</tr>
					</table>							
					<table align="right"><tr><td width="1"><img src="images/fx_button.png"></td><td>
					<c:set var="onlineTmp" value="${0}" />
					<c:set var="online" value="${0}" />
					
					<c:if test="${s.score11!=''}">
					<c:set var="onlineTmp" value="${onlineTmp+1}" />
					<c:set var="online" value="${online+s.score11}" />
					</c:if>
					
					<c:if test="${s.score12!=''}">
					<c:set var="onlineTmp" value="${onlineTmp+1}" />
					<c:set var="online" value="${online+s.score12}" />
					</c:if>
					
					<c:if test="${s.score13!=''}">
					<c:set var="onlineTmp" value="${onlineTmp+1}" />
					<c:set var="online" value="${online+s.score13}" />
					</c:if>
					
					<c:if test="${s.score14!=''}">
					<c:set var="onlineTmp" value="${onlineTmp+1}" />
					<c:set var="online" value="${online+s.score14}" />
					</c:if>
					
					<input type="text" size="2" id="onlineAvg${s.Oid}" <c:if test="${online>0}">value="${((online/240)*100)/onlineTmp}"</c:if> 
					onKeyUp="getUsual(this.id), countScore('${s.Oid}')" readonly/>
					</td></tr></table>							
					</td>
					
					<td align="center" nowrap>
					<table name="day${e.Oid}" id="day${e.Oid}" align="left" style="display:none">
					<tr>
					<td nowrap>
					<input type="text" size="2" name="score01" id="score01${s.Oid}" value="${s.score01}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" />
					<input type="text" size="2"
					name="score02" id="score02${s.Oid}" value="${s.score02}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /><input type="text" size="2"
					name="score03" id="score03${s.Oid}" value="${s.score03}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /><input type="text" size="2"
					name="score04" id="score04${s.Oid}" value="${s.score04}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /><input type="text" size="2"
					name="score05" id="score05${s.Oid}" value="${s.score05}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /><input type="text" size="2"
					name="score06" id="score06${s.Oid}" value="${s.score06}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /></td>
					</tr>
					</table>
					
					<table align="right"><tr><td width="1"><img src="images/fx_button.png"></td><td>
					<input type="text" size="2" name="score1" id="score1${s.Oid}" value="${s.score1}" onKeyUp="getUsual(this.id), countScoreUsual('${s.Oid}')"/>
					</td></tr></table>
					
					</td>
					
					<td align="center"><input type="text" size="2" name="score17" id="score17${s.Oid}" value="${s.score08}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /></td>
					<td align="center"><input type="text" size="2" name="score18" id="score18${s.Oid}" value="${s.score09}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /></td>
					<td align="center"><input type="text" size="2" name="score2" id="score2${s.Oid}" value="${s.score2}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /></td>
					<td align="center"><input type="text" size="2" name="score15" id="score15${s.Oid}" value="${s.score15}" onKeyUp="countScore('${s.Oid}')" /></td>
					<td align="center"><input type="text" size="2" name="score3" id="score3${s.Oid}" value="${s.score3}" onKeyUp="getUsual(this.id), countScore('${s.Oid}')" /></td>
					<td align="center"><input type="text" size="2" name="score" id="score${s.Oid}" value="${s.score}" /></td>
					
				</tr>
				</c:forEach>
			</table>

			</td>
		</tr>
	</table>

</c:forEach>