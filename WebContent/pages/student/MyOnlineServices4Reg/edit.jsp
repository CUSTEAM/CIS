<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/icon/user.gif" />
		    			</td>
		    			<td nowrap style="">
		    			我的基本資料&nbsp;
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				</td>
			</tr>
		</table>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">我的姓名</td>
				<td class="hairLineTdF">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input class="colorInput" type="text" value="${me.name}" disabled/></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>				
				</td>
				<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif" /></td>
				<td class="hairLineTdF">我的學號</td>
				<td class="hairLineTdF">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input type="hidden" name="student_no" value="${me.studentNo}" />
						<input type="text" class="colorInput" value="${me.studentNo}" disabled/>
						</td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>				
				</td>
				<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif"/></td>
			</tr>
		</table>
<script>
function checkItem(id, size){
	if(document.getElementById(id).value.length>size){
		document.getElementById(id+"_off").style.display="none";
		document.getElementById(id+"_on").style.display="inline";
	}else{
		document.getElementById(id+"_off").style.display="inline";
		document.getElementById(id+"_on").style.display="none";
	}
}
</script>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">我的電話</td>
				<td class="hairLineTdF">
				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input type="text" class="colorInput" name="telphone" id="telphone" onKeyUp="checkItem(this.id, 6)" value="${me.telephone}" /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img id="telphone_off" src="images/icon/exclamation.gif" <c:if test="${me.telephone!='' && me.telephone!=null}">style="display:none;"</c:if> />
				<img id="telphone_on" src="images/accept.gif"  <c:if test="${me.telephone=='' || me.telephone==null}">style="display:none;"</c:if> /></td>
				<td class="hairLineTdF">我的手機</td>
				<td class="hairLineTdF">
				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input type="text" class="colorInput" name="cellphone" id="cellphone" onKeyUp="checkItem(this.id, 6)" value="${me.cellPhone}" /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img id="cellphone_off" src="images/icon/exclamation.gif" <c:if test="${me.cellPhone!='' && me.cellPhone!=null}">style="display:none;"</c:if> />
				<img id="cellphone_on" src="images/accept.gif" <c:if test="${me.cellPhone=='' || me.cellPhone==null}">style="display:none;"</c:if> /></td>
			</tr>
		</table>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">連絡地址</td>
				<td class="hairLineTdF">
				
				<table cellspacing="0" cellpadding="0" align="left">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input class="colorInput" type="text" size="3" name="curr_post" value="${me.currPost}" /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" align="left">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input class="colorInput" type="text" id="curr_addr" name="curr_addr" onKeyUp="checkItem(this.id, 6)" value="${me.currAddr}" /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img id="curr_addr_off" src="images/icon/exclamation.gif" <c:if test="${me.currAddr!='' && me.currAddr!=null}">style="display:none;"</c:if> />
				<img id="curr_addr_on" src="images/accept.gif" <c:if test="${me.currAddr=='' || me.currAddr==null}">style="display:none;"</c:if>/></td>
				<td class="hairLineTdF">電子郵件</td>
				<td class="hairLineTdF">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input class="colorInput" type="text" id="email" name="email" value="${me.email}" onKeyUp="checkItem(this.id, 6)" /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img id="email_off" src="images/icon/exclamation.gif" <c:if test="${me.email!='' && me.email!=null}">style="display:none;"</c:if> />
				<img id="email_on" src="images/accept.gif" <c:if test="${me.email=='' || me.email==null}">style="display:none;"</c:if>/></td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/icon_txt.gif" />
		    			</td>
		    			<td nowrap style="">
		    			申請中文事項&nbsp;
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				</td>
			</tr>
		</table>
	</tr>
	<tr>
		<td>		
		
<!-- 中文成績 start -->
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" nowrap>
				<table cellspacing="0" cellpadding="0" width="30">
					<tr>
						<td align="center"><input type="checkBox" <c:if test="${!noScore}">disabled</c:if> onClick="showObj('tcv_1'), clearBox('tcv')"/></td>
						<td id="tcv_1" style="display:none" align="left">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>&nbsp;</td>
								<td><img src="images/input_left.gif"/></td>
								<td><input type="text" name="tcv" id="tcv" size="2" class="colorInput" 
								onKeyUp="value=value.replace(/[^\d]/g,''), checkNum(this.id)" 
								onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/></td>
								<td><img src="images/input_right.gif"/></td>
								<td>&nbsp;份</td>
							</tr>
						</table>
						
						</td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF">				
				中文成績單【若遇承辦單位下班時間申請需隔日取件，10元/份】
				</td>				
			</tr>		
		</table>
<!-- 中文成績 end -->





<!-- 中文成績 折役期 start -->
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" nowrap>
				<table cellspacing="0" cellpadding="0" width="30">
					<tr>
						<td align="center"><input type="checkBox" <c:if test="${!noScore}">disabled</c:if> <c:if test="${me.sex=='2'}">disabled</c:if> onClick="showObj('tcv_army_1'), clearBox('tcv_army')"/></td>
						<td id="tcv_army_1" style="display:none" align="left">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>&nbsp;</td>
								<td><img src="images/input_left.gif"/></td>
								<td><input type="text" name="tcv_army" id="tcv_army" size="2" class="colorInput" onKeyUp="value=value.replace(/[^\d]/g,''), getNumArmy(this.id)" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/></td>
								<td><img src="images/input_right.gif"/></td>
								<td>&nbsp;份</td>
							</tr>
						</table>
						
						</td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF">				
				中文成績單（折抵役期證明）【若遇承辦單位下班時間申請需隔日取件，10元/份，兵役用需2份】
				</td>				
			</tr>		
		</table>
<!-- 中文成績單 end -->

<script>
function checkNum(id){
	if(document.getElementById(id).value<1&&document.getElementById(id).value!=''){
		document.getElementById(id).value='1';
	}
}
</script>

<!-- 中文成績附排名 start -->		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" nowrap>
				<table cellspacing="0" cellpadding="0" width="30">
					<tr>
						<td align="center"><input type="checkBox" <c:if test="${!noScore}">disabled</c:if> onClick="showObj('tcvigr_1'), clearBox('tcvigr')"/></td>
						<td id="tcvigr_1" style="display:none" align="left">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>&nbsp;</td>
								<td><img src="images/input_left.gif"/></td>
								<td><input type="text" name="tcvigr" id="tcvigr" size="2" class="colorInput" onKeyUp="value=value.replace(/[^\d]/g,''), checkNum(this.id)" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/></td>
								<td><img src="images/input_right.gif"/></td>
								<td>&nbsp;份</td>
							</tr>
						</table>
						
						</td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF">				
				中文成績附排名【不含例假日5個工作天，15元/份】
				</td>				
			</tr>		
		</table>		
<!-- 中文成績附排名 end -->	


		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" align="center">
				<input type="checkBox" onClick="getNum('gcr')" <c:if test="${me.occurStatus!='6'}">disabled</c:if> />
				</td>
				<td class="hairLineTdF">				
				畢業證明書（繳身分證影本）【若遇承辦單位下班時間申請需隔日取件，費用50元】<input type="text" name="gcr" id="gcr" style="display:none;"/>
				</td>
			</tr>		
		</table>
	
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" align="center">
				<input type="checkBox" onClick="getNum('cscna')" <c:if test="${(me.occurStatus!='1'&& me.occurStatus!='2')&&!noScore }">disabled</c:if>/>
				</td>
				<td class="hairLineTdF">				
				補發修業證明（繳身分證影本）【若遇承辦單位下班時間申請需隔日取件，費用50元】<input type="text" name="cscna" id="cscna" style="display:none;"/>
				</td>
			</tr>		
		</table>
		
		
		
		</td>
	</tr>	
	<tr>
		<td>
		<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/icon_txt.gif" />
		    			</td>
		    			<td nowrap style="">
		    			申請英文事項&nbsp;
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				</td>
			</tr>
		</table>
	</tr>
	<tr>
		<td>		
		
<!-- 英文成績 start -->		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" nowrap>
				<table cellspacing="0" cellpadding="0" width="30">
					<tr>
						<td align="center"><input id="tev_2" <c:if test="${!noScore}">disabled</c:if> type="checkBox" onClick="showObj('tev_1'), clearBox('tev'), showEng()"/></td>
						<td id="tev_1" style="display:none" align="left">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>&nbsp;</td>
								<td><img src="images/input_left.gif"/></td>
								<td><input type="text" name="tev" id="tev" size="2" class="colorInput" onKeyUp="value=value.replace(/[^\d]/g,''), checkNum(this.id)" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/></td>
								<td><img src="images/input_right.gif"/></td>
								<td>&nbsp;份</td>
							</tr>
						</table>
						
						</td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF">				
				英文成績單Transcript in English Version 【需傳真護照影本】
				</td>				
			</tr>		
		</table>		
<!-- 英文成績end -->	

<!-- 英文畢業證書 start -->		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" nowrap>
				<table cellspacing="0" cellpadding="0" width="30">
					<tr>
						<td align="center"><input id="gcev_2" type="checkBox" <c:if test="${me.occurStatus!='6'}">disabled</c:if> onClick="showObj('gcev_1'), clearBox('gcev'), showEng()"/></td>
						<td id="gcev_1" style="display:none" align="left">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>&nbsp;</td>
								<td><img src="images/input_left.gif"/></td>
								<td><input type="text" name="gcev" id="gcev" size="2" class="colorInput" 
								onKeyUp="value=value.replace(/[^\d]/g,''), checkNum(this.id)" 
								onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/></td>
								<td><img src="images/input_right.gif"/></td>
								<td>&nbsp;份</td>
							</tr>
						</table>
						
						</td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF">				
				英文畢業證書（繳護照影本）【不含例假日5個工作天，費用50元/份，彌封需加收5元/份】
				</td>				
			</tr>		
		</table>		
<!-- 英文畢業證書 end -->
		
		
		
<script>
function showEng(){
	if(document.getElementById('tev_2').checked==true|| document.getElementById('gcev_2').checked==true){
		document.getElementById("extra").style.display="inline";	
	}else{
		document.getElementById("extra").style.display="none";	
	}
}
function clearBox(id){
	if(document.getElementById(id).value!=''){
		document.getElementById(id).value='';
	}
}

function getNum(id){
	if(document.getElementById(id).value==''){
		document.getElementById(id).value='1';
	}else{
		document.getElementById(id).value='';
	}
}
function getNumArmy(id){
	if(document.getElementById(id).value=='1'){
		document.getElementById(id).value='2';
	}
	
	if(document.getElementById(id).value=='0'){
		document.getElementById(id).value='2';
	}
}
</script>
		</td>
	</tr>
	<tr>
		<td >
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				【※申請英文類文件者必填部份資料，並請務必按護照上資料確實填寫English Personal Details】
				</td>
			</tr>
			<tr id="extra" style="display:none;" >
				<td class="hairLineTdF">				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF" nowrap>Name</td>
						<td class="hairLineTdF" width="99%"><input type="text" value="${me.studentEname}" size="75" name="tev_n" id="tev_n" onKeyUp="checkItem(this.id, 3)" /></td>
						
						<td class="hairLineTdF" width="80" align="center" nowrap>必填項目</td>
						<td class="hairLineTdF" width="30" align="center" nowrap>
						<img id="tev_n_off" src="images/icon/exclamation.gif" />
						<img id="tev_n_on" src="images/accept.gif" style="display:none;"/>
						</td>
					</tr>
					<tr>
						<td class="hairLineTdF" nowrap>Date of Birth</td>
						<td class="hairLineTdF" ><input type="text" size="75" disabled value="${me.birthday}"/></td>
						<td class="hairLineTdF" width="80" align="center" nowrap>選填項目</td>
						<td class="hairLineTdF" width="30" align="center" nowrap><img id="tev_ak_on" src="images/accept.gif" /></td>
					</tr>
					<tr>
						<td class="hairLineTdF" nowrap>Also Known as</td>
						<td class="hairLineTdF" ><input type="text" name="tev_ak" size="75" /></td>
						<td class="hairLineTdF" width="80" align="center" nowrap>選填項目</td>
						<td class="hairLineTdF" width="30" align="center" nowrap><img id="tev_ak_on" src="images/accept.gif" /></td>
					</tr>
					<tr>
						<td class="hairLineTdF" nowrap>Date of Enrollment</td>
						<td class="hairLineTdF" ><input type="text" name="tev_ak" size="75" disabled value="${me.entrance}" /></td>
						<td class="hairLineTdF" width="80" align="center" nowrap>選填項目</td>
						<td class="hairLineTdF" width="30" align="center" nowrap><img id="tev_ak_on" src="images/accept.gif" /></td>
					</tr>
					<tr>
						<td class="hairLineTdF" nowrap>Place of Birth</td>
						<td class="hairLineTdF" ><input type="text" size="75" name="tev_pb" /></td>
						<td class="hairLineTdF" width="80" align="center" nowrap>選填項目</td>
						<td class="hairLineTdF" width="30" align="center" nowrap><img id="tev_ak_on" src="images/accept.gif" /></td>
					</tr>
					<tr>
						<td class="hairLineTdF" nowrap>Graduation Date</td>
						<td class="hairLineTdF" ><input type="text" size="75" disabled <c:if test="${me.occurYear!=null}">value="${me.occurYear}"</c:if> /></td>
						
						<td class="hairLineTdF" width="80" align="center" nowrap>選填項目</td>
						<td class="hairLineTdF" width="30" align="center" nowrap><img id="tev_ak_on" src="images/accept.gif" /></td>
					</tr>
				</table>
				
				</td>
			</tr>
			
		</table>		
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<INPUT type="submit" name="method" value="<bean:message key='Continue'/>" class="CourseButton"><INPUT 
		type="submit" name="method" value="<bean:message key='Cancel'/>" disabled class="CourseButton">
		</td>
	</tr>
</table>