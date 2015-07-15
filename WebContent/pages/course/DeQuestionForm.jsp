<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<tr>
<html:form action="/Course/DeAnsw.do" method="post" onsubmit="init('儲存中, 請稍後...')">
	<td>


	<table width="98%" align="center">
		
		<tr>
			<td>		
			
			<table width="100%">
			<tr>
			<td>							
			<div class="modulecontainer filled nomessages">
			<div class="first">
			<span class="first"></span>
			<span class="last"></span>
			</div>
			<div>
			<div>
				<table>
				
				<tr>
			<td>
			
			<table>
										
			
			
			</td>
		</tr>
				
				
				<tr>
					<td>
					
					<table>
						<tr>
							<td>系上對課程的安排</td>							
						</tr>
						<tr>
							<td>
							
							<table width="95%" align="right">
								<tr>
									<td nowrap><input type="radio" name="Q1" value="5" checked="checked" />非常滿意</td>
									
									<td nowrap><input type="radio" name="Q1" value="4" />滿意</td>
									
									<td nowrap><input type="radio" name="Q1" value="3" />普通</td>
									
									<td nowrap><input type="radio" name="Q1" value="2" />不滿意</td>
									
									<td nowrap><input type="radio" name="Q1" value="1" />非常不滿意</td>
									
								</tr>
							</table>
							
							</td>
						</tr>
					</table>
					
					
					<table>
						<tr>
							<td>系上的教學環境</td>							
						</tr>
						<tr>
							<td>
							
							<table width="95%" align="right">
								<tr>
									<td nowrap><input type="radio" name="Q2" value="5" checked="checked" />非常滿意</td>
									
									<td nowrap><input type="radio" name="Q2" value="4" />滿意</td>
									
									<td nowrap><input type="radio" name="Q2" value="3" />普通</td>
									
									<td nowrap><input type="radio" name="Q2" value="2" />不滿意</td>
									
									<td nowrap><input type="radio" name="Q2" value="1" />非常不滿意</td>
									
								</tr>
							</table>
							
							</td>
						</tr>
					</table>
					
					
					<table>
						<tr>
							<td>系上的師生互動</td>							
						</tr>
						<tr>
							<td>
							
							<table width="95%" align="right">
								<tr>
									<td nowrap><input type="radio" name="Q3" value="5" checked="checked" />非常滿意</td>
									
									<td nowrap><input type="radio" name="Q3" value="4" />滿意</td>
									
									<td nowrap><input type="radio" name="Q3" value="3" />普通</td>
									
									<td nowrap><input type="radio" name="Q3" value="2" />不滿意</td>
									
									<td nowrap><input type="radio" name="Q3" value="1" />非常不滿意</td>
									
								</tr>
							</table>
							
							</td>
						</tr>
					</table>
					
					<table>
						<tr>
							<td>系上的讀書風氣</td>							
						</tr>
						<tr>
							<td>
							
							<table width="95%" align="right">
								<tr>
									<td nowrap><input type="radio" name="Q4" value="5" checked="checked" />非常滿意</td>
									
									<td nowrap><input type="radio" name="Q4" value="4" />滿意</td>
									
									<td nowrap><input type="radio" name="Q4" value="3" />普通</td>
									
									<td nowrap><input type="radio" name="Q4" value="2" />不滿意</td>
									
									<td nowrap><input type="radio" name="Q4" value="1" />非常不滿意</td>
									
								</tr>
							</table>
							
							</td>
						</tr>
					</table>
					
					<table>
						<tr>
							<td>對系上未來的期望</td>							
						</tr>
						<tr>
							<td>
							
							<table width="95%" align="right">
								<tr>
									<td nowrap><input type="radio" name="Q5" value="5" checked="checked" />非常大</td>
									
									<td nowrap><input type="radio" name="Q5" value="4" />很大</td>
									
									<td nowrap><input type="radio" name="Q5" value="3" />普通</td>
									
									<td nowrap><input type="radio" name="Q5" value="2" />很小</td>
									
									<td nowrap><input type="radio" name="Q5" value="1" />非常小</td>
									
								</tr>
							</table>
							
							</td>
						</tr>
					</table>
	
	
					<INPUT type="submit"id="submitButtom"
					onMouseOver="showHelpMessage('按我啊', 'inline', this.id)" class="CourseButton"
					onMouseOut="showHelpMessage('', 'none', this.id)" value="填入問卷" />
					</form>				
					
					</td>
				</tr>
			</table>
			</div>
			</div>
			<div class="last">
			<span class="first"></span>
			<span class="last"></span>
			</div>
			</div>
			</td>
			</tr>
			</table>
			
			</td>
		</tr>
	</table>

	</td>
</html:form>
</tr>		
