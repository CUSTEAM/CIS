<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<table width="100%" style="display:none" id="s${g.student_no}info">				
	<tr>
		<td width="50%" valign="top">
		<c:if test="${filed}">
		<table class="hairLineTable" width="99%">
			<tr height="100%">
				<td class="hairLineTdF">本學期成績已建立</td>
			</tr>
		</table>
		</c:if>
		<table width="100%">
			<tr>
				<td>
				
				<c:forEach items="${g.myCs}" var="m">
						
				<table class="hairLineTable" width="99%" id="my${m.Oid}">
					<tr 
					<c:if test="${m.now==null}">
					onClick="clickCou('${m.Oid}')" style="cursor:pointer;"
					</c:if>
					>
						<td id="up1${m.Oid}" width="40" class="hairLineTdF">${m.cscode}</td>
						<td id="up2${m.Oid}" colspan="9" class="hairLineTdF">
						
						<table>
						<tr>
						<td>
						${m.chi_name}<input type="hidden" id="check${m.Oid}" />								
						</td>								
						</tr>
						</table>
						
						</td>
					</tr>
					
					<tr class="hairLineTable" id="down${m.Oid}">
						<td class="hairLineTdF">學年度</td>
						<td class="hairLineTdF" width="30">${m.school_year}-${m.school_term}</td>										
						<td class="hairLineTdF" width="80">學分: ${m.credit}</td>
						<td class="hairLineTdF">成績: ${m.score} 
						
						<input type="hidden" size="1" id="simNote${m.Oid}" name="simNote" value="${m.note}" />
						<input type="hidden" size="1" id="simSHOid${m.Oid}" name="simSHOid" value="${m.Oid}" />
						<input type="hidden" size="1" id="simSHNOid${m.Oid}" name="simSHNOid" value="${m.shnOid}" />
						<input type="hidden" size="1" id="SavedtimeOid${m.Oid}" name="SavedtimeOid" value="${m.SavedtimeOid}" />
						
						</td>
					</tr>
				</table>
				<br>
				
				</c:forEach>
				
				</td>
			</tr>
		</table>
				
		</td>
		<td width="50%" valign="top">
										
		<c:forEach items="${g.noMg}" var="n">
		<table class="hairLineTable" width="99%" id="my${n.Oid}${g.student_no}">
			<tr height="30" class="fullColorTrF" id="tr${n.Oid}${g.student_no}"
			<c:if test="${n.now==null}">
			onClick="selCou${g.student_no}('${n.Oid}${g.student_no}', '${n.school_year}-${n.school_term}:${n.cscode}${n.chi_name}', '${n.Oid}')"					
			style="cursor:pointer;"
			</c:if>>
				<td width="70" align="center" nowrap>
				<input type="checkbox" onClick="discount('${n.Oid}${g.student_no}', '${g.student_no}', '${n.Oid}')"/><font size="1">轉學抵免</font>
				<input type="hidden" name="simDiscount" id="simDiscount${n.Oid}${g.student_no}" />
				<input type="hidden" name="simDisDtime" id="simDisDtime${n.Oid}${g.student_no}" />
				<input type="hidden" name="simThisTerm" <c:if test="${n.now!=null}">value="Y"</c:if> />						
				</td>
				<td width="35" align="center">${n.school_year}-${n.school_term}</td>
				<td width="45" align="center"><b>${n.cscode}</b></td>
				<td width="35" align="center"><b>${n.credit}</b></td>
				<td>${n.chi_name}</td>
			</tr>

			<tr bgcolor="#ffffff">
				<td colspan="6" id="off${n.Oid}${g.student_no}" class="hairLineTdF">
				待折抵
				</td>
			</tr>
		</table>
		<br>
		</c:forEach>
				
		</td>
	</tr>
				
</table>