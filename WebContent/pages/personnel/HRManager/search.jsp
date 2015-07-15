<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<!-- 快速查詢 start -->	

	<tr>
		<td><br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/folder_find.gif">
    			</td>
    			<td>    			
    			<input type="button" onClick="showSearch()" class="gCancel" value="快速搜尋" />
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td>
		<table width="100%">
			<tr>
				<td>	
				<table width="100%">
					<tr>
						<td>
						<table width="100%" <c:if test="${HRmanagerForm.map.exSearch!=''}">style="display:none"</c:if> id="fast">
														
							<tr>
								<td>
								
								
								
								<table class="hairLineTable">
									<tr>
										<td class="hairLineTdF" id="pifname" onMouseOver="showHelpMessage('無論是否在職皆可查到', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)">
										編號姓名
										</td>
										<td class="hairLineTd" align="left">
										<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
										name="fsidno" id="fsidno" size="12" value="" onFocus="chInput(this.id)"
										onkeyup="if(this.value.length>=2)GgetAny(this.value, 'fsidno', 'fscname', 'dempl', 'no')" 
										onMouseOver="showHelpMessage('身分證號輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成姓名', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)"
										onClick="clearQuery()" /><input onFocus="chInput(this.id)"
										
										onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成身分證號', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)"
										type="text" name="fscname" id="fscname" size="10" value=""
							 			onkeyup="if(this.value.length>0)GgetAny(this.value, 'fscname', 'fsidno', 'dempl', 'name')" onClick="clearQuery()" />
										</td>
										<td class="hairLineTdF" width="30" align="center">
										<img src="images/icon/icon_info.gif"  id="help"
										onMouseOver="showHelpMessage('身分證號輸入左側空格, 姓名輸入右側,<br>若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成輸入', 'inline', this.id)" 
				   						onMouseOut="showHelpMessage('', 'none', this.id)"/>
										</td>
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
			
		</table>
		
		</td>
	</tr>

<!-- 快速查詢 end-->

<!-- 進階查詢 start-->
	<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr style="cursor:pointer;">
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/folder_explore.gif">
    			</td>
    			<td nowrap>
    			<input type="button" onClick="showSearch()" class="gGreen" value="進階搜尋" />
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td>
				
			
						
		<c:import url="/pages/personnel/HRManager/exSearch.jsp"/>

						
						
		
		</td>
	</tr>
<!-- 進階查詢 end -->	
	<tr height="10">
		<td>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		
		<INPUT type="submit"
			   name="method"
			   id="continue"
			   onMouseOver="showHelpMessage('身分證字號完整並對應姓名會按照目前狀態進行下一步驟,<br>'+
			   '若無對應則認定為新同事直接進入新增模式', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Continue'/>"
			   class="gSubmit">
						   
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message
			   key='Cancel'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		
		</td>
	</tr>