<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>				
		<br>				
		<table width="100%" cellpadding="0" cellspacing="0">
		
			<tr>
				<td>
				
				<table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/16-cube-blue.png" />
		    			</td>
		    			<td nowrap >
		    			在職員工基本資料&nbsp;
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				</td>
			</tr>
			
		<!-- column 1 start -->								  			
			<tr>
				<td nowrap>
				
				
				<table class="hairLineTable" align="left">											
					<tr>
						<td class="hairLineTdF" nowrap>
						編號姓名
						</td>
						<td class="hairLineTd" nowrap>
						<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						name="idno" id="idno" size="12" value="${aEmpl.idno}"
						onkeyup="if(this.value.length>=2)GgetAny(this.value, 'idno', 'cname', '', 'no')"/><input 
						
						type="text" name="cname" id="cname" size="10" value="${aEmpl.cname}"
			 			onkeyup="if(this.value.length>0)GgetAny(this.value, 'cname', 'idno', '', 'name')" />
						</td>
						
						
						<td class="hairLineTdF" align="center" nowrap>
						英文姓名
						</td>
						<td class="hairLineTd" align="center">
						<input type="text" size="10" name="ename" value="${aEmpl.ename}" />
						</td>
						<td class="hairLineTdF">
						照片
						</td>
						<td class="hairLineTd">
						<input size="4" type="file" name="image" id="image" value="${showPath}/${aEmpl.idno}"
						onMouseOver="showHelpMessage('<img src=/CIS/Personnel/getFTPhoto4Empl?idno=${aEmpl.idno} width=134>', 'inline', this.id)" 
				 		onMouseOut="showHelpMessage('', 'none', this.id)"/>
						</td>
					</tr>
				</table>
		
				</td>
			</tr>
		<!-- column 1 end -->
		
			
			
			
			
		<!-- column 2 start -->
		<tr>
			<td nowrap>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					性別
					</td>
					<td class="hairLineTd">
					<select name="sex">
						<option <c:if test="${aEmpl.sex=='1'}">selected</c:if> value="1">男</option>
						<option <c:if test="${aEmpl.sex=='2'}">selected</c:if> value="2">女</option>
					</select>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>生日</td>
					<td class="hairLineTd">
					<input type="text" name="bdate" id="bdate"
					size="4" value="${aEmpl.bdate}"
				 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
					</td>
					<td class="hairLineTdF" width="30" align="center">
					<img src="images/date.gif" />
		
					<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
						<tr>
							<td id="ds_calclass"></td>
						</tr>
					</table>
					
					</td>
				</tr>
			</table>										
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF">
					保險證號
					</td>
					<td class="hairLineTd">
					<input type="text" name="insno" value="${aEmpl.insno}" size="6"/>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					初到職
					</td>
					<td class="hairLineTd">
					<input type="text" name="Adate" id="Adate"
					size="4" value="${aEmpl.Adate}"
			 		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
					</td>
					<td class="hairLineTdF" width="30" align="center">
					<img src="images/date.gif" />
					</td>
				</tr>
			</table>
		
			</td>
		</tr>
		<!-- column 2 end -->
		
		
		
		<!-- column 3 start -->
		<tr>
			<td nowrap>
			
			<table class="hairLineTable" align="left" align="left">
				<tr>
					<td class="hairLineTdF" class="hairLineTdF" nowrap>戶籍地址
					</td>
					<td class="hairLineTd">
					
					<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
					name="pzip" id="pzipNo" size="3" value="${aEmpl.pzip}" />
					     <input type="text" size="16" name="paddr" value="${aEmpl.paddr}"/>												
					
					</td>
					<td class="hairLineTdF" width="30" align="center">
					<input type="checkbox" onClick="getPaddr()"/>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left" align="left">
				<tr>
					<td class="hairLineTdF" onClick="showInfo('czipName')" style="cursor:pointer;" id="searchCZip" 
 									onMouseOver="showHelpMessage('點擊此處 開啟/關閉 郵遞區號查詢工具', 'inline', this.id)" 
							onMouseOut="showHelpMessage('', 'none', this.id)" nowrap>現居地址
					</td>
					<td class="hairLineTd">												
					
					<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
					name="czip" id="czipNo" size="3" value="${aEmpl.czip}"
					 /><input 
  						
					type="text" id="czipName" size="3" style="display:none;"
		 			/><input type="text" size="16" name="caddr" 
		 			id="caddr" value="${aEmpl.caddr}"/>
					
					</td>
					<td class="hairLineTdF" width="30" align="center">
					<input type="checkbox" onClick="getCaddr()"/>
					</td>
				</tr>
			</table>
		
			</td>
		</tr>
		<!-- column 3 End -->
		
		
		<!-- column 4 start -->
		<tr>
			<td nowrap>
		
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					電話
					</td>
					<td class="hairLineTd">
					<input type="text" name="telephone" value="${aEmpl.telephone}"/>
					</td>
				</tr>
			</table>
			
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					手機
					</td>
					<td class="hairLineTd">
					<input type="text" name="CellPhone" value="${aEmpl.CellPhone}"/>
					</td>
				</tr>
			</table>
			
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					電郵
					</td>
					<td class="hairLineTd">
					<input type="text" name="Email" value="${aEmpl.Email}" />
					</td>
				</tr>
			</table>
											
		
			</td>
		</tr>
		<!-- column 4 End -->
		
		
		
		
		
		
		
		
		<tr>
			<td>
			<br>
			<table width="100%" cellpadding="0" cellspacing="0">
	  			<tr>
	  				<td width="10" align="left" nowrap>
	      			<hr noshade class="myHr"/>
	    			</td>
	    			<td width="24" align="center" nowrap>
	    			<img src="images/16-cube-blue.png" />
	    			</td>
	    			<td nowrap>
	    			任職狀態&nbsp;
	    			</td>
	    			<td width="100%" align="left">
	      			<hr noshade class="myHr"/>
	    			</td>
	  			</tr>
			</table>
			
			</td>
		</tr>
		
		
		<!-- column 5 start -->
		<tr>
			<td nowrap>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					任職狀態
					</td>
					<td class="hairLineTd">
					<select name="Status">
						<option value="">在職</option>
						<c:forEach items="${allStatus}" var="c">
						<option <c:if test="${aEmpl.Status==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					員工分類
					</td>
					<td class="hairLineTd">
					<select name="category">
						<c:forEach items="${allCategory}" var="c">
						<option <c:if test="${aEmpl.category==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
																	
					</td>
				</tr>
			</table>
			
												
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>學術單位</td>
					<td class="hairLineTd">
					<select name="unit">
						<option value=""></option>
						<c:forEach items="${allUnit}" var="c">
						<option <c:if test="${aEmpl.unit==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
					</td>
					
					<td class="hairLineTdF" nowrap>行政單位</td>
					<td class="hairLineTd">
					<select name="unit_module">
						<option value=""></option>
						<c:forEach items="${allUnitModule}" var="c">
						<option <c:if test="${aEmpl.unit_module==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					職稱
					</td>
					<td class="hairLineTd">
					<input type="text" size="10" value="${aEmpl.sname}" name="sname" id="sname" />
					</td>
					
					<td class="hairLineTdF" width="30" align="center">
					<input type="checkbox" onClick="getSname()"/>
					</td>
					
				</tr>
			</table>
		
			</td>
		</tr>
		<!-- column 5 End -->								
		
		
		<!-- column 6 start -->
		<tr>
			<td nowrap>									
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					兼任導師
					</td>
					<td class="hairLineTd">
					<select name="Tutor">
						<option value=""></option>
						<c:forEach items="${allTutor}" var="c">
						<option <c:if test="${aEmpl.Tutor==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					專任職位
					</td>
					<td class="hairLineTd">
					<select name="pcode" id="pcode">
						<option value=""></option>
						<c:forEach items="${allPcode}" var="c">
						<option <c:if test="${aEmpl.pcode==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					兼任職位
					</td>
					<td class="hairLineTd">
					<select name="Director" id="Director">
						<option value=""></option>
						<c:forEach items="${allDirector}" var="c">
						<option <c:if test="${aEmpl.Director==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF">
					學歷
					</td>
					<td class="hairLineTd">
					<select name="Degree">
						<option value=""></option>
						<c:forEach items="${allDegree}" var="c">
						<option <c:if test="${aEmpl.Degree==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
					</td>
					<td class="hairLineTdF" width="30" align="center">
					<img src="images/icon_component.gif" />
					</td>
				</tr>
			</table>
		
			</td>
		</tr>
		<!-- column 6 End -->
		
		
		
		
		
		
		<!-- column 7 start -->
		<tr>
			<td nowrap>
			
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					職務最後異動
					</td>
					<td class="hairLineTd">
					<input type="text" name="StartDate" id="StartDate"
					size="4" value="${aEmpl.StartDate}"
			 		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
					</td>
				
					<td class="hairLineTdF" nowrap>
					離職日
					</td>
					<td class="hairLineTd">
					<input type="text" name="EndDate" id="EndDate"
					size="4" value="${aEmpl.EndDate}"
			 		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
					</td>
					<td class="hairLineTdF" width="30" align="center">
					<img src="images/date.gif" />
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					離職原因
					</td>
					<td class="hairLineTd">
					<select name="StatusCause">
						<option value=""></option>
						<c:forEach items="${allStatusCause}" var="c">
						<option <c:if test="${aEmpl.StatusCause==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF" nowrap>
					任教職日
					</td>
					<td class="hairLineTd">
					<input type="text" name="TeachStartDate" id="TeachStartDate"
					size="4" value="${aEmpl.TeachStartDate}"
			 		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
					</td>
					<td class="hairLineTdF" width="30" align="center">
					<img src="images/date.gif" />
					</td>
				</tr>
			</table>
		
			</td>
		</tr>
		<!-- column 7 End -->
		
		
		
		<tr>
			<td>
			<br>
			<table width="100%" cellpadding="0" cellspacing="0">
	  			<tr>
	  				<td width="10" align="left" nowrap>
	      			<hr noshade class="myHr"/>
	    			</td>
	    			<td width="24" align="center" nowrap>
	    			<img src="images/16-cube-blue.png" />
	    			</td>
	    			<td nowrap>
	    			其它備註&nbsp;
	    			</td>
	    			<td width="100%" align="left">
	      			<hr noshade class="myHr"/>
	    			</td>
	  			</tr>
			</table>
			
			</td>
		</tr>
		<!-- column 8 start -->
		<tr>
			<td>
			
				
						
			<table class="hairLineTable" align="left">
				<tr>
					<td class="hairLineTdF">
					<textarea name="Remark" id="Remark" rows="1" cols="76" ondblclick="minMax(this.id)">${aEmpl.Remark}</textarea>
					</td>
				</tr>
			</table>
				
										
			
			</td>
		</tr>
		<!-- column 8 end -->
		
		</table>
		</td>		
	</tr>
	
	
	
	
	<tr>
		<td>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-blue.png" />
    			</td>
    			<td nowrap>
    			報表列印&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
			
		</td>
	</tr>
	<tr>
		<td align="left">
		
		<table class="hairLineTable" align="left" width="95%">
			<tr>
				<td class="hairLineTdF">	    							
				
				<img src="images/printer.gif" border="0">
				
				<select onchange="jumpMenu('parent',this,0)">
					<option value="javascript:void(0)">個人證明書</option>
					<option value="/CIS/EmplCard">職員證</option>
				</select>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>								
	<tr height="5">
		<td></td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		
		<INPUT type="submit"
			   name="method"
			   id="continue"
			   onMouseOver="showHelpMessage('修改資料', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='EditEmpl'/>"
			   class="gSubmit">
						   
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message
			   key='Cancel'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('取消所有變更', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
			   
		<c:if test="${editOver!=null}">			   
		<INPUT type="submit"
			   name="method" id="Complete"
			   value="<bean:message
			   key='Complete'/>"
			   class="gGreen" 
			   onMouseOver="showHelpMessage('清除本次作業結果', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		</c:if>
		
		</td>
	</tr>


</table>