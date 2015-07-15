<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td>
				
						
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF">教室編號</td>
				<td class="hairlineTd">
				<input type="text" size="8" name="roomid" id="room_id" value="${SetRoomForm.map.roomid}" /></td>
				
				<td class="hairlineTdF">教室名稱</td>
				<td class="hairlineTd">
				<input type="text" size="12" name="name2" id="name2" value="${SetRoomForm.map.name2}" /><select 
					name="boro">
					<option value="" <c:if test="${SetRoomForm.map.boro==''}">selected</c:if>>租借選項</option>
					<option value="Y" <c:if test="${SetRoomForm.map.boro=='Y'}">selected</c:if>>開放租借</option>
					<option value="N" <c:if test="${SetRoomForm.map.boro=='N'}">selected</c:if>>不可租借</option>
				</select>
				</td>				
			</tr>
		</table>
										
					
								
		<table class="hairlineTable"">	
			<tr>
				<td class="hairlineTdF">所屬系所</td>
				<td class="hairlineTd">
				<select name="dept" id="dept">
					<option value="">選擇系所</option>
					<c:forEach items="${dept}" var="d">
					<option value="${d.idno}" <c:if test="${SetRoomForm.map.dept==d.idno}">selected</c:if> >${d.name}</option>
					</c:forEach>
				</select>
				</td>
				</td>
				<td class="hairlineTdF">所在位置</td>
				<td class="hairlineTd">
				<select name="building" id="building">
					<option value="">選擇建築物</option>
					<c:forEach items="${build}" var="b">
					<option value="${b.idno}" <c:if test="${SetRoomForm.map.building==b.idno}">selected</c:if>>${b.name}</option>
					</c:forEach>
				</select>
				</td>
				</td>
			</tr>
		</table>
					
					
								
		<table class="hairlineTable">	
			<tr>
				<td class="hairlineTdF">管理單位</td>
				<td class="hairlineTd">
				<select name="unit">
					<option value="">選擇單位</option>
					<c:forEach items="${unit}" var="u">
					<option value="${u.idno}" <c:if test="${SetRoomForm.map.unit==u.idno}">selected</c:if>>${u.name}</option>
					</c:forEach>
				</select>
				</td>
				</td>
			
				<td class="hairlineTdF">所在樓層</td>
				<td class="hairlineTd">
				<select name="floor">
					<option value=""  <c:if test="${SetRoomForm.map.floor==''}">selected</c:if>selected>幾樓</option>
					<option value="Z" <c:if test="${SetRoomForm.map.floor=='Z'}">selected</c:if>>B3樓</option>
					<option value="Y" <c:if test="${SetRoomForm.map.floor=='Y'}">selected</c:if>>B2樓</option>
					<option value="X" <c:if test="${SetRoomForm.map.floor=='X'}">selected</c:if>>B1樓</option>
					
					<option value="1" <c:if test="${SetRoomForm.map.floor=='1'}">selected</c:if>>&nbsp;1樓</option>
					<option value="2" <c:if test="${SetRoomForm.map.floor=='2'}">selected</c:if>>&nbsp;2樓</option>
					<option value="3" <c:if test="${SetRoomForm.map.floor=='3'}">selected</c:if>>&nbsp;3樓</option>
					<option value="4" <c:if test="${SetRoomForm.map.floor=='4'}">selected</c:if>>&nbsp;4樓</option>
					<option value="5" <c:if test="${SetRoomForm.map.floor=='5'}">selected</c:if>>&nbsp;5樓</option>
					<option value="6" <c:if test="${SetRoomForm.map.floor=='6'}">selected</c:if>>&nbsp;6樓</option>
					<option value="7" <c:if test="${SetRoomForm.map.floor=='7'}">selected</c:if>>&nbsp;7樓</option>
					<option value="8"<c:if test="${SetRoomForm.map.floor=='8'}">selected</c:if>>&nbsp;8樓</option>
					<option value="9"<c:if test="${SetRoomForm.map.floor=='9'}">selected</c:if>>&nbsp;9樓</option>
					<option value="A"<c:if test="${SetRoomForm.map.floor=='A'}">selected</c:if>>10樓</option>
					<option value="B"<c:if test="${SetRoomForm.map.floor=='B'}">selected</c:if>>11樓</option>
					<option value="C"<c:if test="${SetRoomForm.map.floor=='C'}">selected</c:if>>12樓</option>
				</select>
				</td>
				</td>
			</tr>
		</table>
						
						
				
				
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="CourseButton" ><INPUT type="submit" 
		name="method" value="<bean:message key='Create'/>" class="CourseButton" ><INPUT type="submit" 
		name="method" value="<bean:message key='Clear'/>" class="CourseButton" >
		</td>
	</tr>
</table>