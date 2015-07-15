<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>	
		<td width="1" align="center">		
		
		
		<c:choose>
			<c:when test="${g.CreditPa==0}">
			<c:set var="fontColor" value="#666666"/>
			</c:when>
			<c:when test="${g.totalCredit>=g.CreditPa}">
			<c:set var="fontColor" value="#3d7900"/>
			</c:when>
			<c:when test="${g.totalCredit<g.CreditPa}">
			<c:set var="fontColor" value="#bf0000"/>
			</c:when>			
		</c:choose>
		
		<table class="hairLineTable" cellspacing="0" cellpadding="0" width="135">			
			<tr>
				<td class="hairLineTdF" nowrap align="right">
				
				<table cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td align="left">
						<font size=-2>歷年(成績/開課)比對</font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<font color="${fontColor}" size="+4" face="Arial"><b>${g.opt1+g.opt2+g.opt3}</b></font>
						
						</td>
					</tr>
					<tr>
						<td align="right" nowrap>
						
						<font size=-2>
						由<c:if test="${!filed}">本學期  ${g.thisTerm}, </c:if>歷年 ${g.totalCredit}計算而得
						
						</font>
						</td>
					</tr>
				</table>				
				
				</td>
			</tr>			
		</table>
		
		</td>	
		<td align="left" valign="top">		
		<table>
			<tr>						
				<td width="30%">
				
				<table>
					<tr>
						<td  width="1" nowrap onClick="showObj('s${g.student_no}info');" style="cursor:pointer;">
						<c:if test="${g.occur_status!='6'}">
						<img src="images/user.gif" >
						</c:if>
						<c:if test="${g.occur_status=='6'}">
						<img src="images/tux.gif" id="tux${g.student_no}"
									onMouseOver="showHelpMessage('ㄎ', 'inline', this.id)" 
			 					 	onMouseOut="showHelpMessage('', 'none', this.id)">
						</c:if>				
						</td>
						<td nowrap>
						<b>${g.student_no} - ${g.student_name}</b>
						</td>
						<td nowrap>
						<b>${g.trance}</b>級 
						</td>
						<td nowrap>
						低標 <b>${g.pa}</b> 分
						</td>
					</tr>
				</table>
						
				</td>
				<td width="70%">
						
				<table align="left" >
					<tr height="1">
						<td>			
						<table>
							<tr>
								<td width="1">
								<img src="images/icon_txt.gif" id="piftitle">
								</td>
								<td nowrap>
								註記
								</td>
								<td>
								<input type="checkBox" onClick="showObj('remark${g.student_no}')" <c:if test="${g.ident_remark!=null&&g.ident_remark!=''}">checked</c:if>>
								</td>
								<td colspan="2" <c:if test="${g.ident_remark==null||g.ident_remark==''}">style="display:none;"</c:if> id="remark${g.student_no}">						
								<input type="hidden" name="ident_remarkNo" value="${g.student_no}"/>
								<textarea name="ident_remark" id="Remark" rows="1" cols="20" class="smallInput">${g.ident_remark}</textarea>
								</td>
							</tr>
						</table>
						</td>
									
						<td>
						<table>
							<tr>
								<td>
								
								<img src="images/accept.png" />
								
								<input type="checkbox" <c:if test="${g.occur_status=='6'}">checked</c:if> 
								onClick="checkGradeForOne('${g.student_no}')"/>								
								
								<input type="hidden" name="aGrade"								
								<c:if test="${g.occur_status=='6'}">value="${g.student_no}"
								</c:if>								
								id="aGrade${g.student_no}" />
								
								</td>
								
								<td>審核</td>											
								
								<td>
								
								<input style="cursor:pointer;" type="text" id="docNo${g.student_no}"								
								onClick="this.value='${g.student_no}'" 
								
								<c:if test="${g.graMod}">disabled</c:if>
								
								<c:if test="${g.occur_status!='6'}">style="display:none"</c:if>
								class="smallInput" name="docNo" 
								size="10"/>
								
								</td>
							</tr>
						</table>
						
						</td>
																		
					</tr>
				</table>
							
				</td>									
			</tr>
			<tr>
				<td colspan="4">
				
				<table>
					<tr>
						<td nowrap>必修已得 <b>${g.opt1}</b>學分, </td>
						<td nowrap>選修已得 <b>${g.opt2}</b>學分, </td>
						<td nowrap>通識已得 <b>${g.opt3}</b>學分, </td>
						<td nowrap>總共已得 <b>${g.opt1+g.opt2+g.opt3}</b>學分 </td>
						<td nowrap>已自動折抵 <b>${g.myPa}</b>門課</td>										
					</tr>
				</table>
				
				<table>
					<tr>
						<td>本學期已選 必修:<b>${g.opt11}</b>學分, 選修: <b>${g.opt21}</b>學分, 通識: <b>${g.opt31}</b>學分</td>
						<td>						
						<input type="button" value="成績折抵" class="gSubmitlSmall" onClick="showObj('s${g.student_no}info')"/>
						<input type="button" value="歷年成績" class="gCancelSmall" onClick="showObj('hist${g.student_no}')"/>						
						<input type="button" value="歷年應修" class="gCancelSmall" onClick="showMyshold('cour${g.student_no}')"/>	
						</td>
															
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>					
</table>