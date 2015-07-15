<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>		
		<table width="100%" cellspacing="0" cellpadding="0">			
		
			<tr>
				<td>				
				
				<table class="hairLineTable"align="left">
					<tr>
						<td class="hairLineTdF" nowrap>
						班級
						</td>
						<td class="hairLineTdF">
						<select name="CampusNo" style="font-size:16px;">
							<option <c:if test="${AutoCreateForm.map.CampusNo=='_'}">selected</c:if> value="_">${ac.name}校區</option>
							<c:forEach items="${AllCampus}" var="ac">					
							<option <c:if test="${AutoCreateForm.map.CampusNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
							</c:forEach>					
						</select>
						</td>				
					
								
					
						<td class="hairLineTdF">
						<select name="SchoolNo" style="font-size:16px;">
							<option <c:if test="${AutoCreateForm.map.SchoolNo=='__'}">selected</c:if> value="__">所有學制</option>
							<c:forEach items="${AllSchool}" var="ac">					
							<option <c:if test="${AutoCreateForm.map.SchoolNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
							</c:forEach>					
						</select>
						</td>				
					
						<td class="hairLineTdF">				
						<select name="DeptNo"style="font-size:16px;">
							<option <c:if test="${AutoCreateForm.map.DeptNo=='_'}">selected</c:if> value="_">所有科系</option>
							<c:forEach items="${AllDept}" var="ac">					
							<option <c:if test="${AutoCreateForm.map.DeptNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
							</c:forEach>					
						</select>
						</td>				
					
						<td class="hairLineTdF">
						
						<select name="Grade" style="font-size:16px;">
							<option <c:if test="${AutoCreateForm.map.Grade=='_'}">selected</c:if> value="_">所有年級</option>
							<option <c:if test="${AutoCreateForm.map.Grade=='1'}">selected</c:if> value="1">一年級</option>
							<option <c:if test="${AutoCreateForm.map.Grade=='2'}">selected</c:if> value="2">二年級</option>
							<option <c:if test="${AutoCreateForm.map.Grade=='3'}">selected</c:if> value="3">三年級</option>
							<option <c:if test="${AutoCreateForm.map.Grade=='4'}">selected</c:if> value="4">四年級</option>
							<option <c:if test="${AutoCreateForm.map.Grade=='4'}">selected</c:if> value="5">五年級</option>
						</select>
						
						</td>				
					
						<td class="hairLineTdF">
						<select name="ClassNo" style="font-size:16px;">
							<option <c:if test="${AutoCreateForm.map.ClassNo=='_'}">selected</c:if> value="_">所有班級</option>
							<option <c:if test="${AutoCreateForm.map.ClassNo=='1'}">selected</c:if> value="1">甲班</option>
							<option <c:if test="${AutoCreateForm.map.ClassNo=='2'}">selected</c:if> value="2">乙班</option>
							<option <c:if test="${AutoCreateForm.map.ClassNo=='3'}">selected</c:if> value="3">丙班</option>
							<option <c:if test="${AutoCreateForm.map.ClassNo=='4'}">selected</c:if> value="4">丁班</option>
						</select>
						</td>				
					</tr>
				</table>			
				
				</td>
			</tr>			
			
			
			
			<tr height="30">
				<td class="fullColorTable" align="center">
				<INPUT type="submit"name="method"value="<bean:message key='Established'/>"class="gSubmit"
				id="Established" onMouseOver="showHelpMessage('尋找已建立的學生', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
				
				<INPUT type="submit"name="method"value="<bean:message key='Unestablish'/>"class="gSubmit"
				id="Unestablish" onMouseOver="showHelpMessage('尋找未使用的學生', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
				<input type="button" class="gCancle" value="返回" id="back" onclick="location='/CIS/SysAdmin/PortfolioManager.do';"/>
				</td>
			</tr>
		</table>
		
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>