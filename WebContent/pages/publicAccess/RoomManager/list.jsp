<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<table width="98%">

	<c:forEach items="${nabbrs}" var="n">
		<tr>
			<td>






				<table class="hairLineTable">
					<tr>
					
						<td class="hairLineTdF" nowrap width="65">						
						<input type="button" value="查看" class="gCancelSmall" id="map${n.room_id}" onClick="showMapTimeTable('<table><tr><td onClick=closeTimeTable() style=cursor:pointer; align=right>點擊此處關閉*</td></tr><tr><td>'+
						'<iframe src=/CIS/RoomTimetable4HTML?room_id=${n.room_id} width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
						'</td></tr></table>', 'inline', this.id)"  valign="baseline" onMouseOver="showHelpMessage('點擊此處檢視教室資訊', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
						</td>
						
						<td class="hairLineTdF" nowrap width="65">
						<c:if test="${n.boro=='Y'}">	
						<input type="button" value="預約" class="gGreenSmall" id="nabbr${n.nabbrOid}"
						<c:if test="${n.boro=='Y'}">onClick="showForm('${n.nabbrOid}', '${n.room_id}')"</c:if>
						onMouseOver="showHelpMessage('${n.room_id}教室, <c:if test="${n.boro=='Y'}">點擊此處開始預約<br></c:if> 目前使用者:<br>'+
						'<c:forEach items="${n.dtimeClass}" var="d">星期${d.week} 第${d.begin}節 ~ 第${d.end}節 ${d.ClassName}: ${d.chi_name}<br></c:forEach>', 
						'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"></c:if>
						</td>
						
						
						
						
						
						
						
						
						
						
						
						<td class="hairLineTdF" width="80" nowrap>
							${n.room_id}
						</td>
						<td class="hairLineTdF" nowrap>
							${n.buildName}
						</td>
						<td class="hairLineTdF" width="40" nowrap>
							${n.floor}F
						</td>
						<td class="hairLineTdF" width="50" nowrap>
							${n.seat}人
						</td>
						<td class="hairLineTdF" width="150" nowrap>
							<font size=1>${n.unitName}</font>
						</td>
						<td class="hairLineTdF" width="150" nowrap>
							<font size=1>${n.deptName}</font>
						</td>

						<td class="hairLineTdF" width="100%" nowrap>
							<font size=1>${n.name2}</font>
						</td>


						</td>
					</tr>
				</table>

			</td>
		</tr>










		<tr>
			<td id="info${n.nabbrOid}" style="display: none;">

				<table>
					<tr>
						<td class="hairLineTdF">
							<c:if test="${n.remark!=''}">${n.remark}</c:if>
							<c:if test="${n.remark==''}">未設定借用條件</c:if>
							
							
						</td>
					</tr>
					<tr>
						<td id="accept${n.nabbrOid}" align="center">
							<input type="button" value="同意" onClick="agree('${n.nabbrOid}')"
								class="gSubmit" />
							<input type="button" value="不同意"
								onClick="unagree('${n.nabbrOid}')" class="gCancel" />
						</td>
					</tr>
				</table>

			</td>
		</tr>

	</c:forEach>





</table>