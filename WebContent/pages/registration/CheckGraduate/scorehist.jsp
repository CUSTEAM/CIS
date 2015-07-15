<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td width="55%" valign="top" >
		<table width="100%" style="display:none" id="hist${g.student_no}"><tr><td>
		<c:forEach items="${g.myScoreHist}" var="s">
		
		<c:set var="countCredit" value="0" />	
		<table class="hairLineTable" width="99%">
			<tr>
				<td colspan="8" class="hairLineTd" onClick="showObj('scores${s.student_no}${s.school_year}${s.school_term}');" style="cursor:pointer;">
				${s.school_year}學年, 第 ${s.school_term}學期成績
				</td>
			</tr>
		</table>
		<table class="hairLineTable" width="99%" id="scores${s.student_no}${s.school_year}${s.school_term}" style="display:none;">
			<c:forEach items="${s.score}" var="ss">
			<c:if test="${ss.score>=g.pa}"><c:set var="countCredit">${countCredit+ss.credit}</c:set></c:if>
			<tr onClick="openEx('${ss.Oid}')" style="cursor:pointer;">
				<td width="50%" class="hairLineTdF" onClick="this.className='hairLineTd', inpuText(this.innerHTML)">${ss.chi_name}</td>
				<td width="20" class="hairLineTdF" onClick="this.className='hairLineTd', inpuText(this.innerHTML)" nowrap>${ss.cscode}</td>
				<td nowrap class="hairLineTdF" onClick="this.className='hairLineTd', inpuText(this.innerHTML)" nowrap>${ss.opt}</td>
				<td width="20" class="hairLineTdF" onClick="this.className='hairLineTd', inpuText(this.innerHTML)" nowrap>${ss.credit}</td>
				<td width="20" class="hairLineTdF" onClick="this.className='hairLineTd', inpuText(this.innerHTML)" nowrap><c:if test="${ss.score<g.pa}"><font color="red"></c:if>${ss.score}<c:if test="${ss.score<g.pa}"></font></c:if></td>
				<td width="40" class="hairLineTdF" onClick="this.className='hairLineTd', inpuText(this.innerHTML)" nowrap>${ss.evgr_type}${ss.other}</td>
			</tr>
			<tr id="extr${ss.Oid}" <c:if test="${ss.note==null || ss.note==''}">style="display:none"</c:if> >
				<td colspan="8" class="hairLineTdF">
				<table>
					<tr>
						<td><img src="images/accept.gif"/></td>
						<td>
						<input type="checkbox" onClick="closeEx('${ss.Oid}')" />
						</td>
						<td>完成</td>
						<td>
						<input type="text" name="exNote" id="exNote${ss.Oid}" class="smallInput" value="${ss.note}" size="50" />
						<input type="hidden" name="exSHOid" id="exSHOid${ss.Oid}" value="${ss.Oid}" />
						<input type="hidden" name="exSHNOid" id="exSHNOid${ss.Oid}" value="${ss.shnOid}" />
						</td>
					</tr>
				</table>
				</td>
			</tr>			
			</c:forEach>
			<tr>
				<td colspan="8" class="hairLineTd" align="right">
				以上小計 <b>${countCredit}</b>學分
				</td>
			</tr>
		</table>
		
		
		
		
		
		</c:forEach>
		</td></tr></table>
		</td>				
		<td width="45%" valign="top">				
		<table width="100%">
			<tr>
				<td style="display:none" id="cour${g.student_no}">
				
				</td>
			</tr>
		</table>				
		</td>
	</tr>
</table>