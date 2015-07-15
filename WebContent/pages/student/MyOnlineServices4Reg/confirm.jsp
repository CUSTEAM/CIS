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
		    			基本資料&nbsp;
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
						<td>
						<input type="text" class="colorInput" name="student_no" value="${me.studentNo}" disabled/>
						</td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>				
				</td>
				<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif"/></td>
			</tr>
		</table>

		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">我的電話</td>
				<td class="hairLineTdF">
				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input type="text" name="telphone" class="colorInput" value="${OnlineService4Reg.telphone}" disabled /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="30" align="center">				
				<img id="telphone_on" src="images/accept.gif" /></td>
				<td class="hairLineTdF">我的手機</td>
				<td class="hairLineTdF">				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input name="cellphone" type="text" class="colorInput" value="${OnlineService4Reg.cellphone}" disabled /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img id="cellphone_on" src="images/accept.gif" /></td>
			</tr>
		</table>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">連絡地址</td>
				<td class="hairLineTdF">
				
				<table cellspacing="0" cellpadding="0" align="left">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input name="curr_post" class="colorInput" type="text" size="3" value="${OnlineService4Reg.curr_post}" disabled/></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" align="left">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input name="curr_addr" class="colorInput" type="text" value="${OnlineService4Reg.curr_addr}" disabled/></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img id="curr_addr_on" src="images/accept.gif"/></td>
				<td class="hairLineTdF">電子郵件</td>
				<td class="hairLineTdF">
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input name="email" class="colorInput" type="text"  value="${OnlineService4Reg.email}" disabled/></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img id="email_on" src="images/accept.gif" /></td>
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
		    			<img src="images/icon/user.gif" />
		    			</td>
		    			<td nowrap style="">
		    			申請資料&nbsp;
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
		
		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">項目</td>
				<td class="hairLineTdF" width="60">數量</td>
				<td class="hairLineTdF" width="60">金額</td>
			</tr>
			
			<c:if test="${OnlineService4Reg.tcv_m!=null}">
			<tr>
				<td class="hairLineTdF">中文成績單Transcript in Chinese Version</td>
				<td class="hairLineTdF">${OnlineService4Reg.tcv} 份</td>
				<td class="hairLineTdF">${OnlineService4Reg.tcv_m} 元</td>
			</tr>
			</c:if>
			
			<c:if test="${OnlineService4Reg.tcv_army_m!=null}">
			<tr>
				<td class="hairLineTdF">中文成績單（折抵役期證明）Transcript in Chinese Version</td>
				<td class="hairLineTdF">${OnlineService4Reg.tcv_army} 份</td>
				<td class="hairLineTdF">${OnlineService4Reg.tcv_army_m} 元</td>
			</tr>
			</c:if>
			
			<c:if test="${OnlineService4Reg.tcvigr_m!=null}">
			<tr>
				<td class="hairLineTdF">中文成績附排名Transcript in Chinese Version Including Graduation Ranking</td>
				<td class="hairLineTdF">${OnlineService4Reg.tcvigr} 份</td>
				<td class="hairLineTdF">${OnlineService4Reg.tcvigr_m} 元</td>
			</tr>
			</c:if>
			
			<c:if test="${OnlineService4Reg.gcr_m!=null}">
			<tr>
				<td class="hairLineTdF">畢業證明書（繳身分證影本）Graduation Certificate（ID Card）Reissue</td>
				<td class="hairLineTdF">${OnlineService4Reg.gcr} 份</td>
				<td class="hairLineTdF">${OnlineService4Reg.gcr_m} 元</td>
			</tr>
			</c:if>
			
			<c:if test="${OnlineService4Reg.cscna_m!=null}">
			<tr>
				<td class="hairLineTdF">補發修業證明（繳身分證影本）Certificate of Study, Certificate of Non-graduate Attendee（ID Card）Reissue</td>
				<td class="hairLineTdF">${OnlineService4Reg.cscna} 份</td>
				<td class="hairLineTdF">${OnlineService4Reg.cscna_m} 元</td>
			</tr>
			</c:if>
			
			<c:if test="${OnlineService4Reg.tev_m!=null}">
			<tr>
				<td class="hairLineTdF">英文成績單Transcript in English Version（繳護照影本） </td>
				<td class="hairLineTdF">${OnlineService4Reg.tev} 份</td>
				<td class="hairLineTdF">${OnlineService4Reg.tev_m} 元</td>
			</tr>
			</c:if>
			
			<c:if test="${OnlineService4Reg.gcev_m!=null}">
			<tr>
				<td class="hairLineTdF">英文畢業證書Graduation Certificate in English Version（繳護照影本）</td>
				<td class="hairLineTdF">${OnlineService4Reg.gcev} 份</td>
				<td class="hairLineTdF">${OnlineService4Reg.gcev_m} 元</td>
			</tr>
			</c:if>			
		</table>			
		</td>
	</tr>
	
	
	
	
<c:if test="${OnlineService4Reg.tev_m!=null && OnlineService4Reg.gcev_m!=null}">	
	<tr>
		<td>		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>Name</td>
				<td class="hairLineTdF" width="99%">				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input type="text" class="colorInput" value="${OnlineService4Reg.tev_n}" size="75" disabled /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="60" align="center" nowrap>
				<img id="tev_n_on" src="images/accept.gif" /></td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>Date of Birth</td>
				<td class="hairLineTdF" >				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input type="text" size="75" class="colorInput" value="${OnlineService4Reg.tev_ak}" disabled/></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="60" align="center"><img src="images/accept.gif" /></td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>Also Known as</td>
				<td class="hairLineTdF" >				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input type="text" class="colorInput" size="75" disabled value="${OnlineService4Reg.tev_ak}" /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="60" align="center">
				<img id="tev_ak_on" src="images/accept.gif" /></td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>Date of Enrollment</td>
				<td class="hairLineTdF" >				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input class="colorInput" type="text" size="75" disabled value="${OnlineService4Reg.tev_pb}" /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="60" align="center"><img id="tev_ak_on" src="images/accept.gif" /></td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>Place of Birth</td>
				<td class="hairLineTdF" >				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input class="colorInput" type="text" size="75" disabled value="${OnlineService4Reg.tev_pb}" /></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="60" align="center">
				<img id="tev_pb_on" src="images/accept.gif" /></td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>Graduation Date</td>
				<td class="hairLineTdF" >				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="images/input_left.gif"/></td>
						<td><input class="colorInput" type="text" size="75" disabled value="${me.occurYear}"/></td>
						<td><img src="images/input_right.gif"/></td>
					</tr>
				</table>
				</td>
				<td class="hairLineTdF" width="60" align="center" nowrap><img id="tev_ak_on" src="images/accept.gif" /></td>
			</tr>
		</table>		
		</td>
	</tr>
</c:if>
	
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
		    			取件資料&nbsp;
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
		<table width="99%" class="hairLineTable">
			<tr height="50">
				<td class="hairLineTdF" align="center">				
				
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td>
						
						<table>
							<tr>
								<td>
								<font size="+3" color="#7777777"><b>請選擇取件地點</b></font>&nbsp;
								</td>
								<td>
								<div name="selectBox" class="selectOnFocus" >
								<select name="get_method" class="CourseButton">
									<option value="1">台北校區聯合服務中心</option>
									<option value="2">新竹校區聯合服務中心</option>
									<option value="M">郵寄(郵資另計)</option>				
								</select>
								</div>
								</td>
							</tr>
						</table>								
						</td>
					</tr>
					<tr>
						<td valign="top"><font size="-2" color="#777777">&nbsp;郵寄將參考連絡地址</font></td>
					</tr>
				</table>
				
				
				
				
				
				
				
								
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table width="100%">
			<tr>
				
				<td width="50%">				
				<table width="100%" class="hairLineTable">
					<tr height="100">
						<td class="hairLineTdF" align="center">
						
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>估計<font size="+4" color="#7777777"><b>${OnlineService4Reg.day}</b></font> 個工作天</td>
							</tr>
							<tr>
								<td valign="top"><font size="-2" color="#777777">郵寄以行政單位通知為準</font></td>
							</tr>
						</table>
						
						
						</td>
					</tr>
				</table>				
				</td>
				
				<td width="50%">
				<table width="99%" class="hairLineTable">
					<tr height="100">
						<td class="hairLineTdF" align="center">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><input type="hidden" name="total_pay" value="${OnlineService4Reg.total}"/>共 <font size="+4" color="#777777"><b>${OnlineService4Reg.total}</b></font> 元</td>
							</tr>
							<tr>
								<td valign="top"><font size="-2" color="#777777">郵寄以行政單位通知為準</font></td>
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
	<tr>
		<td>
		<br>
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable" align="center"><input 
		type="submit" name="method" value="<bean:message key='OK'/>" class="CourseButton"><input 
		type="submit" name="method" value="<bean:message key='Modify'/>" class="CourseButton">
		</td>
	</tr>


</table>