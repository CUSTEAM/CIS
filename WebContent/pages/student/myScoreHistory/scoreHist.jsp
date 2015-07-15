<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:forEach items="${myScoreHist}" var="s">
	<tr>
		<td>
			<table class="hairLineTable" width="99%">
				<tr>
					<td class="hairLineTdF">

						<table width="100%">
							<tr>
								<td>
									<table id="show${s.school_year}"
										onClick="showObj('thisTerm${s.school_year}')"
										style="cursor: pointer;" width="100%"
										onMouseOver="showHelpMessage('點一下即可展開${s.school_year}學年的成績, 展開後再點即可關閉', 'inline', this.id)"
										onMouseOut="showHelpMessage('', 'none', this.id)">
										<tr>
											<td width="30" align="center">
												<img src="images/icon/icon_full_score.gif">
											</td>
											<td nowrap width="100%" align="left">
												<b>${s.school_year}</b> 學年成績
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td id="thisTerm${s.school_year}" style="display: none;">

									<table class="hairLineTable" width="99%">

										<tr>
											<td align="center" class="hairLineTd">
												科目代碼
											</td>
											<td width="300" class="hairLineTd">
												科目名稱
											</td>
											<td class="hairLineTd">
												選別
											</td>
											<td class="hairLineTd">
												學分
											</td>
											<td class="hairLineTd">
												成績
											</td>
											<td class="hairLineTd">
												備註
											</td>
										</tr>
										<c:forEach items="${s.scores}" var="ss">
										<!-- 抵免的成績以"空白"做顯示 但JSTL0.0與"空白" 比對後竟然為 equal -->
										<c:if test="${ss.score>=pass||ss.score==null}">
											<c:if test="${ss.opt1=='1'}">
												<c:set var="myCredit1" value="${myCredit1+ss.credit}" />
											</c:if>
											<c:if test="${ss.opt1=='2'}">
												<c:set var="myCredit2" value="${myCredit2+ss.credit}" />
											</c:if>
											<c:if test="${ss.opt1=='3'}">
												<c:set var="myCredit3" value="${myCredit3+ss.credit}" />
											</c:if>
										</c:if>
											
											<tr>
												<td align="center" class="hairLineTdF">
													${ss.cscode}
												</td>
												<td class="hairLineTdF">
													${ss.chi_name}
												</td>
												<td class="hairLineTdF">
													${ss.opt}
												</td>
												<td class="hairLineTdF">
													${ss.credit}
												</td>
												<td class="hairLineTdF">
													<font <c:if test="${ss.score<pass}">color="red"</c:if>size="2">														
													${ss.score}
													</font>
												</td>												
												<td class="hairLineTdF">
													${ss.evgr_type}													
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
			
		</td>
	</tr>

</c:forEach>
	<tr>
		<td>
			<table class="hairLineTable" width="99%">
				<tr>
					<td class="hairLineTdF">

						<table>
							<tr>
								<td width="30" align="center">
									<img src="images/icon/calculator.gif" />
								</td>
								<td>
									必修共計:
									<b>${myCredit1}</b>學分,
								</td>
								<td>
									選修共計:
									<b>${myCredit2}</b>學分,
								</td>
								<td>
									通識共計:
									<b>${myCredit3}</b>學分
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</td>
	</tr>