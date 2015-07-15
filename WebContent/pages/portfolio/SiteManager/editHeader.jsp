<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<br>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">					
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0" style="cursor:pointer;" onClick="checkView('editHeader')">
		  			<tr>
		  				<td width="10" align="left" nowrap>
		      			<hr noshade class="myHr"/>
		    			</td>
		    			<td width="24" align="center" nowrap>
		    			<img src="images/icon/application_get.gif" />
		    			</td>
		    			<td nowrap>
		    			<font class="gray_15">頁首設定</font> <font size="-2">(導覽列)</font>&nbsp;
		    			</td>
		    			<td width="100%" align="left">
		      			<hr noshade class="myHr"/>
		    			</td>
		  			</tr>
				</table>
				
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td id="editHeader" style="display:none;">
						<table class="hairLineTable" width="99%">
							<tr>
								<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif"/></td>
								<td class="hairLineTdF">點擊下方圓圈可以改變預設的樣式</td>
							</tr>
						</table>
						<c:forEach items="${headers}" var="h">
						
						<table class="hairLineTable" width="99%">
							<tr>
								<td class="hairLineTdF" width="1" nowrap>
								<input name="headerStyle" type="radio" 
								<c:if test="${h.Oid==mySite.headerStyle}">checked</c:if> value="${h.Oid}" >
								</td>
								<td class="hairLineTdF" nowrap>
								${h.name}
								</td>
								<td class="hairLineTdF" width="100%">
								<img src="images/template/${h.icon}"/>					
								</td>
							</tr>
						</table>
						
						</c:forEach>					
						<table class="hairLineTable" width="99%">
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