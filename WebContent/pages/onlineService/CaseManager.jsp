<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<!-- 標題start -->	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/bell.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">線上申請管理</font></div>		
		</td>
	</tr>
	
	
	
<html:form action="/OnlineService/CaseManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">	
	<tr>
		<td>
		<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%" style="cursor:pointer;" onClick="showObj('online')">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/icon/bell_start.gif" />
		    			</td>
		    			<td nowrap>
		    			待辦事項&nbsp;
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td id="online">
		
		<%@ include file="CaseManager/online.jsp" %>
		
		</td>
	</tr>
	
	<tr>
		<td>
		<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%" style="cursor:pointer;" onClick="showObj('offline')">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/icon/bell_stop.gif" />
		    			</td>
		    			<td nowrap style="">
		    			已完成事項&nbsp;
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td <c:if test="${!empty myOnlineWork}">style="display:none;"</c:if> id="offline">
		
		<%@ include file="CaseManager/offline.jsp" %>
		
		</td>
	</tr>
	<tr>
		<td>
		
		
		
		
		<table class="hairLineTable" style="position:absolute; left:550; top:30; z-index:2">
			<tr>
				<td class="hairLineTdF" width="30" align="center">				
				<img src="images/16-star-hot.png"/>				
				</td>
				<td class="hairLineTdF" width="60" align="center">				
				${countMySrvs}				
				</td>
			</tr>
		</table>		
		</td>
	</tr>
	<!-- 幫助列 start -->
	<tr>
		<td>		
		<table width="100%" align="center" id="help" style="display:none;">
			<tr>
				<td>				
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF">										
						<OL>						
							<li>標題
							<li>標題								
								<ul>
									<li>子標題
									<li>子標題
									<li>子標題
								</ul>							
							<li>標題
							<li>標題
							<li>標題
										
						</OL>
						</td>
					</tr>
				</table>				
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- 幫助列 end -->
	
	<tr height="30">
		<td class="fullColorTable" align="center">		
		<INPUT type="submit" name="method" value="<bean:message key='Complete'/>" class="gSubmit">
		
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
		onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
	
	
	
</html:form>
</table>

