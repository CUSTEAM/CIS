<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Registration/CheckeOutCredit" method="post" onsubmit="init('程式執行中')">
	
	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/bug_magnify.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">學分資格審查</font></div>		
		</td>
	</tr>
	
	<tr>
		<td>
		<%@ include file="CheckOutCredit/search.jsp"%>		
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
							<li>以上方欄位查詢畢業資格
							<li>以上方欄位建立畢業資格								
								<ul>
									<li>相同學制、科系，若資格資格相同可利用期間
									<li>期間以學生入學年為標準
									<li>請輸入正確的資料
								</ul>							
							<li>查詢列表可以修改
							<li>查詢列表不提供刪除
							<li>請輸入格式正確的資料
										
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
		<INPUT type="submit"
			   name="method"
			   id="continue"
			   onMouseOver="showHelpMessage('列出上列條件的查詢結果', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Query'/>"
			   class="gSubmit">   
			   
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message key='Create'/>"
			   class="gGreen" 
			   onMouseOver="showHelpMessage('以上列條件建立一筆新資料', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />		   
		
		<INPUT type="submit"
			   name="method" id="Clear"
			   value="<bean:message
			   key='Clear'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('重新設定所有條件', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
			   
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
	<c:if test="${!empty result}">
	<tr>
		<td>		
		<%@ include file="CheckOutCredit/list.jsp"%>		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message key='Modify'/>"
			   class="gSubmit" 
			   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		</td>
	</tr>
	</c:if>
</html:form>
</table>