<%@ page language="java" contentType="text/html;charset=UTF-8"
	%>
<%@ include file="/taglibs.jsp"%>
<table class="hairlineTable" align="left">
	<tr>
		<td class="hairlineTdf" align="center">
			選擇大樓
		</td>
		<td class="hairlineTd">
			<select name="building" id="building">
				<option value=""
					<c:if test="${RooManagerForm.map.building==b.idno}">selected</c:if>>
					所有大樓
				</option>
				<c:forEach items="${build}" var="b">
					<option value="${b.idno}"
						<c:if test="${RooManagerForm.map.building==b.idno}">selected</c:if>>
						${b.name}
					</option>
				</c:forEach>
			</select>
		<td class="hairLineTdF">
			選擇樓層
		</td>
		<td class="hairLineTd">
			<select name="floor">
				<option value=""
					<c:if test="${RooManagerForm.map.floor==''}">selected</c:if>
					selected>
					所有樓層
				</option>
				<option value="Z"
					<c:if test="${RooManagerForm.map.floor=='Z'}">selected</c:if>>
					B3
				</option>
				<option value="Y"
					<c:if test="${RooManagerForm.map.floor=='Y'}">selected</c:if>>
					B2
				</option>
				<option value="X"
					<c:if test="${RooManagerForm.map.floor=='X'}">selected</c:if>>
					B1
				</option>



				<option value="1"
					<c:if test="${RooManagerForm.map.floor=='1'}">selected</c:if>>
					&nbsp;1F
				</option>
				<option value="2"
					<c:if test="${RooManagerForm.map.floor=='2'}">selected</c:if>>
					&nbsp;2F
				</option>
				<option value="3"
					<c:if test="${RooManagerForm.map.floor=='3'}">selected</c:if>>
					&nbsp;3F
				</option>
				<option value="4"
					<c:if test="${RooManagerForm.map.floor=='4'}">selected</c:if>>
					&nbsp;4F
				</option>
				<option value="5"
					<c:if test="${RooManagerForm.map.floor=='5'}">selected</c:if>>
					&nbsp;5F
				</option>
				<option value="6"
					<c:if test="${RooManagerForm.map.floor=='6'}">selected</c:if>>
					&nbsp;6F
				</option>
				<option value="7"
					<c:if test="${RooManagerForm.map.floor=='7'}">selected</c:if>>
					&nbsp;7F
				</option>
				<option value="8"
					<c:if test="${RooManagerForm.map.floor=='8'}">selected</c:if>>
					&nbsp;8F
				</option>
				<option value="9"
					<c:if test="${RooManagerForm.map.floor=='9'}">selected</c:if>>
					&nbsp;9F
				</option>
				<option value="A"
					<c:if test="${RooManagerForm.map.floor=='A'}">selected</c:if>>
					10F
				</option>
				<option value="B"
					<c:if test="${RooManagerForm.map.floor=='B'}">selected</c:if>>
					11F
				</option>
				<option value="C"
					<c:if test="${RooManagerForm.map.floor=='C'}">selected</c:if>>
					12F
				</option>
			</select>
		</td>
		<td class="hairLineTdF">
			是否預約
		</td>
		<td class="hairLineTd">
			<select name="boro">
				
				<option <c:if test="${RooManagerForm.map.boro==''}">selected</c:if> value="">不拘</option>
							
				<option <c:if test="${RooManagerForm.map.boro=='N'}">selected</c:if> value="N">不預約</option>
				<option <c:if test="${RooManagerForm.map.boro=='Y'}">selected</c:if> value="Y">可預約</option>

			</select>
		</td>
		<td class="hairlineTdf" align="center">
			<INPUT type="submit" name="method"
				value="<bean:message key='ListRoom'/>" class="gSubmit">
		</td>

	</tr>
</table>