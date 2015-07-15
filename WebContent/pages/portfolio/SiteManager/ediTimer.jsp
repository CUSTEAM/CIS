<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0" style="cursor:pointer;" onClick="checkView('editTimer')">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/16-clock.png" />
		    			</td>
		    			<td nowrap>
		    			<font class="gray_15">日期時間</font>
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td id="editTimer" style="display:none;">						
						<table width="100%">						
						<c:forEach items="${timers}" var="t" varStatus="ts">
						
						<c:if test="${ts.count%2==1||ts.count==0}"><tr></c:if>					
						
						<td>
						<table class="hairLineTable" align="left">
							<tr>
								<td class="hairLineTdF" width="1" nowrap>
								<input name="timer" type="radio"<c:if test="${t.Oid==mySite.timer}">checked</c:if> value="${t.Oid}">
								</td>
								<td class="hairLineTdF" width="100%">
								
								<table>
									<tr>
										<td>										
										${t.template}
										</td>
									</tr>
								</table>
								
								</td>
							</tr>
						</table>
						</td>
						
						<c:if test="${ts.count%4==0||ts.count==0}"><tr></c:if>
						</c:forEach>
						
						</table>						
						<table class="hairLineTable" width="99%">
							<tr>
								<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif"/></td>
								<td class="hairLineTdF">沒有喜歡的嗎? 沒關係, 到「模板管理」中的「樣式設計」自訂一個時鐘吧</td>
							</tr>
						</table>
						<table class="hairLineTable" width="99%" align="center">
							<tr>
								<td class="hairLineTdF" align="right">
								<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="gSubmit">
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>