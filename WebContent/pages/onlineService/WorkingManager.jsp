<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="/OnlineService/WorkingManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題start -->
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/bell_stop.gif" />
				</td>
				<td align="left">
				&nbsp;服務設定
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		
		
		
		
		
		
		
		
		
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
				    			<img src="images/icon/house.gif" />
				    			</td>
				    			<td nowrap style="">
				    			校區基本資料&nbsp;
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
						<td class="hairLineTdF">台北校區</td>
						<td class="hairLineTdF">
						
						<table cellspacing="0" cellpadding="0" align="left">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td>								
								<input class="colorInput" size="5" type="text" name="SchoolPost_Taipei" value="${SchoolPost_Taipei}"/>
								</td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>	
						
						
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td>								
								<input class="colorInput" size="64" type="text" name="SchoolAddress_Taipei" value="${SchoolAddress_Taipei}"/>
								</td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>				
						</td>
						<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif" /></td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				<table class="hairLineTable">
					<tr>
						
						<td class="hairLineTdF">新竹校區</td>
						<td class="hairLineTdF">
						
						<table cellspacing="0" cellpadding="0" align="left">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td>								
								<input class="colorInput" size="5" type="text" name="SchoolPost_Hsinchu" value="${SchoolPost_Hsinchu}"/>
								</td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>
						
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td>
								<input class="colorInput" size="64" type="text" name="SchoolAddress_Hsinchu" value="${SchoolAddress_Hsinchu}"/>
								</td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>				
						</td>
						<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif"/></td>
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
				    			<img src="images/icon/neighbourhood.gif" />
				    			</td>
				    			<td nowrap style="">
				    			服務單位基本資料&nbsp;
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
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">日間部註冊組</td>
						<td class="hairLineTdF">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td>
								<input class="colorInput" size="64" type="text" name="day_working" value="${day_working}"/>
								</td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>				
						</td>
						<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif" /></td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">進修部教務組</td>
						<td class="hairLineTdF">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td>
								<input class="colorInput" size="64" type="text" name="night_working" value="${night_working}"/>
								</td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>				
						</td>
						<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif" /></td>
					</tr>
				</table>
				
				</td>
			</tr>
					
					
					
					
					
					
					
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">進修學院教務組</td>
						<td class="hairLineTdF">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td>
								<input class="colorInput" size="64" type="text" name="holiday_working" value="${holiday_working}"/>
								</td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>				
						</td>
						<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif" /></td>
					</tr>
				</table>
				
				</td>
			</tr>		
					
					
					
					
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">新竹教務組</td>
						<td class="hairLineTdF">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="images/input_left.gif"/></td>
								<td>
								<input class="colorInput" size="64" type="text" name="Hsinchu" value="${Hsinchu}"/>
								</td>
								<td><img src="images/input_right.gif"/></td>
							</tr>
						</table>				
						</td>
						<td class="hairLineTdF" width="30" align="center"><img src="images/accept.gif" /></td>
					</tr>
				</table>
				
				</td>
			</tr>		
					
					
					
					
					
					
					
				</table>			
			</tr>
		</table>		
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center"><INPUT 
		type="submit" name="method" value="<bean:message key='OK'/>" class="CourseButton">
		</td>
	</tr>
</html:form>
</table>

	