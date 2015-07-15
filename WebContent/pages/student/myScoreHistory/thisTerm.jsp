<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<tr>
	<td>

<c:if test="${!empty term2Score}">
	<table class="hairLineTable" width="99%">
		<tr>
			<td class="hairLineTdF">
			<table>
				<tr>
					<td width="1">
						<c:if test="${aStudent.sex=='2'}"><img src="images/user_female.gif"/></c:if>
						<c:if test="${aStudent.sex=='1'}"><img src="images/user.gif"/></c:if>
					</td>
					<td width="100%">
					${aStudent.schoolName}, ${aStudent.deptName}${aStudent.Grade}年級的${aStudent.student_name}同學您好
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</c:if>

			<table class="hairLineTable" width="99%">
				<tr>
					<td class="hairLineTdF">
						
						<table width="100%">						
						<c:if test="${empty term2Score}">
							<tr>
								<td>
									<table width="100%" align="center">
										<tr>
											<td>

											<table width="100%">
												<tr>
													<td>&nbsp;&nbsp;<img src="images/icon/icon_info_exclamation.gif"></td>
													<td align="left" width="100%">如果您有選課而沒有成績資訊請洽各部制教務單位</td>
												</tr>
											</table>

											</td>
										</tr>
									</table>
							</c:if>

							<c:if test="${not empty term2Score}">
								<tr>
									<td>
										<table id="thisTermTitle" onClick="showObj('thisTerm')"
											style="cursor: pointer;" width="100%"
											onMouseOver="showHelpMessage('點一下即可展開本學期的成績, 展開後再點即可關閉', 'inline', this.id)"
											onMouseOut="showHelpMessage('', 'none', this.id)">
											<tr>
												<td width="1">
													<img src="images/icon/icon_full_score.gif">
												</td>
												<td width="100%" align="left">本學期成績</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td id="thisTerm" style="display: inline;">

										<table align="center" class="hairLineTable" width="99%">
											<tr>
												<td align="center" class="hairLineTd">選別</td>
												<td align="center" class="hairLineTd">科目代碼</td>
												<td align="center" class="hairLineTd">科目名稱</td>
												<td align="center" class="hairLineTd">開課班級</td>
												<td align="center" class="hairLineTd">學分數</td>
												<td align="center" class="hairLineTd">每週時數</td>
												<td align="center" class="hairLineTd">期中成績</td>
												<!--td align="center" bgcolor="#f0fcd7">期末成績</td-->
												<!--td align="center" bgcolor="#f0fcd7">平時成績</td-->
												<td align="center" class="hairLineTd">學期成績</td>
											</tr>

											<c:set var="credit2" value="0.0" />
											<c:set var="credit21" value="0.0" />
											<c:set var="credit22" value="0.0" />
											<%int j=0;%>
											<c:forEach items="${term2Score}" var="d2">
											<%j =j+1;%>

												<c:choose>
													<c:when test="${d2.score>=pass}">
														<c:set var="credit21" value="${credit21+d2.credit}" />
													</c:when>
													<c:when test="${d2.score<pass&& d2.score!=null}">
														<c:set var="credit22" value="${credit22+d2.credit}" />
													</c:when>
												</c:choose>

												<c:set var="credit2" value="${credit2+d2.credit}" />
												<tr>
													<td align="center" <%if(j%2==1){%> class="hairLineTdF"
														<%}else{%> class="hairLineTd" <%}%>>
														${d2.opt}
													</td>
													<td align="left" <%if(j%2==1){%> class="hairLineTdF"
														<%}else{%> class="hairLineTd" <%}%>>
														&nbsp;&nbsp;${d2.cscode}
													</td>
													<td align="left" <%if(j%2==1){%> class="hairLineTdF"
														<%}else{%> class="hairLineTd" <%}%>>
														&nbsp;&nbsp;${d2.chi_name}
													</td>
													<td align="center" <%if(j%2==1){%> class="hairLineTdF"
														<%}else{%> class="hairLineTd" <%}%>>
														${d2.ClassName}
													</td>
													<td align="center" style="mso-number-format: \@"
														<%if(j%2==1){%> class="hairLineTdF" <%}else{%>
														class="hairLineTd" <%}%>>
														${d2.credit}
													</td>
													<td align="center" <%if(j%2==1){%> class="hairLineTdF"
														<%}else{%> class="hairLineTd" <%}%>>
														${d2.thour}
													</td>
													<td align="center" <%if(j%2==1){%> class="hairLineTdF"
														<%}else{%> class="hairLineTd" <%}%>>
														<c:if test="${fn:indexOf(d2.chi_name, '體育')<0}">${d2.score2}</c:if>
														<c:if test="${fn:indexOf(d2.chi_name, '體育')>=0}">
															<font size="-2">平時${d2.score2}, 學科${d2.score3},
																術科${d2.score1}</font>
														</c:if>
													</td>
													<td align="center" <%if(j%2==1){%> class="hairLineTdF"
														<%}else{%> class="hairLineTd" <%}%>>														
														<font <c:if test="${d2.score<pass}">color="red"</c:if>size="2">
														${d2.score}
														</font>
													</td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
								
								<tr>
									<td>									

										<table align="center" class="hairLineTable" width="99%">
											<tr>
												<td class="hairLineTd" align="center" width="30" align="center">
													<img src="images/icon/page_white_wrench.gif">
												</td>
												<td align="left" class="hairLineTd">
													本學期應修學分:${credit2} &nbsp; 實得學分: ${credit21} &nbsp; 不及格學分:
													${credit22} 我的及格標準為: ${pass}
												</td>
											</tr>
											<tr>
												<td class="hairLineTdF" align="center">
													<img src="images/date.gif">
												</td>
												<td align="left" class="hairLineTdF">
													期中成績開放日期為 ${OpenViewMid}
												</td>
											</tr>
											<tr>
												<td class="hairLineTd" width="30" align="center">
													<img src="images/date.gif">
												</td>
												<td align="left" class="hairLineTd">
													期末成績開放日期為 ${OpenViewEnd}
												</td>
											</tr>
											<tr>
												<td class="hairLineTdF" align="center">
													<img src="images/date.gif">
												</td>
												<td align="left" class="hairLineTdF">
													畢業成績開放日期為 ${OpenViewGra}
													<img src="images/16-message-warn.png">
													<font size="1">選修低年級課程則以${OpenViewEnd}成績為準</font>
												</td>
											</tr>
										</table>

									</td>
								</tr>
							</c:if>

						</table>
						
					</td>
				</tr>
			</table>
		
		
	</td>
</tr>