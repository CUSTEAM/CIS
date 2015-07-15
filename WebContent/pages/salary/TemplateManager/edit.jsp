<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr height="2">
		<td>
		
		</td>
	</tr>
	<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;
 				<c:choose>
 					<c:when test="${aTemplete.sex==1}">
 					<img src="images/user_suit.gif">
 					</c:when>
 					<c:when test="${aTemplete.sex==2}">
 					<img src="images/user_female.gif">
 					</c:when>
 				</c:choose>
 				</td>
 				<td nowrap style="font-size:18px;"> 				
 				&nbsp基本資料&nbsp
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->

<!-- 第1階 -->
	<tr>
		<td>
		
		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80" nowrap>證號姓名</td>
						<td class="hairLineTd">
						<input type="text" size="10" value="${aTemplete.idno}" class="bigInput" disabled /><input 
						type="text" size="6" value="${aTemplete.cname}" class="bigInput" disabled /></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" width="80" align="center"  nowrap>職稱</td>
						<td class="hairLineTd"><input type="text" size="12" value="${aTemplete.sname}" disabled class="bigInput"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" width="80" align="center" nowrap>序號</td>
						<td class="hairLineTd">
							<input type="text" size="4" name="sno" value="${aTemplete.sno}" id="sno" onFocus="chBigInput(this.id)" class="bigInput"/>
							<input type="hidden" size="4" name="idno" value="${aTemplete.idno}"/>
						</td>
					</tr>
				</table>
				
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/icon/money_add.gif">&nbsp;
 				</td>
 				<td nowrap style="font-size:18px;"> 				
 				&nbsp;應發項目&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->


<!-- 第1階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">薪俸</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" class="bigInput" name="monthly_pay" id="monthly_pay" 
						value="${aTemplete.monthly_pay}" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80" nowrap>學術研究費</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="study" id="study" class="bigInput" value="${aTemplete.study}" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80" nowrap>專業加給</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="technical" id="technical" class="bigInput" value="${aTemplete.technical}" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">津貼</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="transport" id="transport" value="${aTemplete.transport}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
		
		
			
		</td>
	</tr>
<!-- 第1階end -->


<!-- 第2階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font size="-2">日間部超支鐘點</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="overhour_pay" id="overhour_pay" value="${aTemplete.overhour_pay}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font size="-2">進修部超支鐘點</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="overhour_pay_n" id="overhour_pay_n" value="${aTemplete.overhour_pay_n}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font color="green">日間部導師費</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="class_teacher_pay" id="class_teacher_pay" value="${aTemplete.class_teacher_pay}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font color="green">進修部導師費</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="class_teacher_pay_n" id="class_teacher_pay_n" value="${aTemplete.class_teacher_pay_n}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
					
				</td>
			</tr>
		</table> 			
		</td>
	</tr>
<!-- 第2階end -->	
	
<!-- 第3階end -->
	<tr>
		<td width="100%" align="left">

 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">代課費</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="substitute_course" id="substitute_course" value="${aTemplete.substitute_course}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80" nowrap><font color="green">主管特支</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="hspecial" id="hspecial" value="${aTemplete.hspecial}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80" nowrap>其它應支</td>
						<td class="hairLineTd" nowrap>
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="others_in" id="others_in" value="${aTemplete.others_in}" class="bigInput" onFocus="chBigInput(this.id)"/>
						
						<td class="hairLineTdF" align="center" width="40" nowrap>說明</td>
						<td class="hairLineTd" nowrap>
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="10" name="others_in_note" id="others_in_note" value="${aTemplete.others_in_note}" class="bigInput" onFocus="chBigInput(this.id)"/>
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第3階end -->

<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/icon/money_add.gif">&nbsp;
 				</td>
 				<td nowrap style="font-size:18px;"> 				
 				&nbsp;補發項目&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->

<!-- 第4階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font size="-2">日間部超支鐘點</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="overhour_pay_dif" id="overhour_pay_dif" value="${aTemplete.overhour_pay_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font size="-2">進修部超支鐘點</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="overhour_pay_n_dif" id="overhour_pay_n_dif" value="${aTemplete.overhour_pay_n_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font color="green">日間部導師費</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="class_teacher_pay_dif" id="class_teacher_pay_dif" value="${aTemplete.class_teacher_pay_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font color="green">進修部導師費</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="class_teacher_pay_n_dif" id="class_teacher_pay_n__dif" value="${aTemplete.class_teacher_pay_n_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
					
				</td>
			</tr>
		</table> 			
		</td>
	</tr>
<!-- 第4階end -->

<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/icon/money_delete.gif">&nbsp;
 				</td>
 				<td nowrap style="font-size:18px;"> 				
 				&nbsp;應扣項目&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->

<!-- 第5階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">公保</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="public_insure" id="public_insure" value="${aTemplete.public_insure}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">建保</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="teach_over" id="teach_over" value="${aTemplete.teach_over}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">眷保</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="spouse_insure" id="spouse_insure" value="${aTemplete.spouse_insure}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">勞保</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="hour_pay" id="hour_pay" value="${aTemplete.borrow}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第5階end -->

<!-- 第6階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">自提儲金</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="deposit" id="deposit" value="${aTemplete.deposit}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">自提勞退</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="labor" id="labor" value="${aTemplete.labor}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">強制執行</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="banditry" id="banditry" value="${aTemplete.banditry}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">所得稅</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="tax_pay" id="tax_pay" value="${aTemplete.tax_pay}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第6階end -->

<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/icon/money_delete.gif">&nbsp;
 				</td>
 				<td nowrap style="font-size:18px;"> 				
 				&nbsp;補扣項目&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->

<!-- 第7階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">公保</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="public_insure_dif" id="public_insure_dif" value="${aTemplete.public_insure_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">建保</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="teach_over_dif" id="teach_over_dif" value="${aTemplete.teach_over_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">眷保</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="spouse_insure_dif" id="spouse_insure_dif" value="${aTemplete.spouse_insure_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">勞保</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="hour_pay_dif" id="hour_pay_dif" value="${aTemplete.borrow_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">自提儲金</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="deposit_dif" id="deposit_dif" value="${aTemplete.deposit_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">自提勞退</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="labor_dif" id="labor_dif" value="${aTemplete.labor_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>				
				 				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">所得稅</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="tax_pay_dif" id="tax_pay_dif" value="${aTemplete.tax_pay_dif}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第7階end -->





<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/icon/calculator.gif">&nbsp;
 				</td>
 				<td nowrap> 				
 				&nbsp;項目總計&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->


<!-- 第10階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center">扶養親屬數</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="2" name="family_no" id="family_no" value="${aTemplete.family_no}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center"><font color="green">免稅額</font></td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTax()" type="text" size="4" name="notax" id="notax" value="${aTemplete.notax}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" nowrap>應發總額</td>
						<td class="hairLineTd">
						<input type="text" size="6" name="payamt" id="payamt" value="${aTemplete.payamt}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" nowrap>實發金額</td>
						<td class="hairLineTd">
						<input type="text" size="6" name="real_pay" id="real_pay" value="${aTemplete.real_pay}" class="bigInput" onFocus="chBigInput(this.id)"/></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第10階end -->

	<tr height="30" class="fullColorTr">
		<td align="center">
		<INPUT type="submit"
			   name="method"
			   id="Update"
			   onMouseOver="showHelpMessage('儲存設定', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Update'/>"
			   class="gSubmit">
			   
		<c:if test="${fn:length(templates)>1}">
		<INPUT type="submit"
			   name="method"
			   id="Prev"
			   onMouseOver="showHelpMessage('前一個人', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Prev'/>"
			   class="gGreen">
						   
		<INPUT type="submit"
			   name="method"
			   id="Next"
			   onMouseOver="showHelpMessage('後一個人', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Next'/>"
			   class="gGreen">
		</c:if>		
						   
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message
			   key='Cancel'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		</td>
	</tr>
</table>