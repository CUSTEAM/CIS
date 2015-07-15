<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>



<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" nowrap>確定排入課程</td>
		
		
		<td class="hairLineTdF" nowrap><input type="text" name="year" style="font-size:18px;" size="1"/></td>
		<td class="hairLineTdF" nowrap>學年</td>
		<td class="hairLineTdF">
		<select name="term" style="font-size:18px;">
			<option value="1">第1學期</option>
			<option value="2">第2學期</option>
		</select>
		</td>
		<td class="hairLineTdF" width="100%">
		
		
		
		<input type="submit" name="method" onClick="return confirmSubmit('確定建立課程規劃排課？');"
		value="<bean:message key='ConfirmReTableing' bundle="COU"/>" 
		id="ConfirmReTableing" class="gSubmit"
		onMouseOver="showHelpMessage('將下列課程建立至各班的班級課表當中', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		
		</td>
	</tr>
</table>

<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" nowrap>修改下列資訊</td>
		<td class="hairLineTdF" width="100%">
		
		
		
		
		<input type="submit" name="method" value="<bean:message key='Modify'/>" 
		id="Save" class="gCancel">
		</td>
		
	</tr>
</table>


<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF">範圍</td>
		<td class="hairLineTdF">課程代碼 </td>
		<td class="hairLineTdF">星期</td>
		<td class="hairLineTdF">開始</td>
		<td class="hairLineTdF">結束</td>
	</tr>
	
	
	
	<c:forEach items="${allRules}" var="a">
	<tr>
		<td class="hairLineTdF">				
		<select name="Cidno" style="font-size:16px;">
			<option value="">${ac.name}刪除</option>
			<c:forEach items="${AllCampus}" var="ac">					
			<option <c:if test="${a.Cidno==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
			</c:forEach>					
		</select>				
		<select name="Sidno" style="font-size:16px;">
			<option <c:if test="${a.Sidno==''}">selected</c:if> value="">所有學制</option>
			<c:forEach items="${AllSchool}" var="ac">					
			<option <c:if test="${a.Sidno==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
			</c:forEach>					
		</select>						
		<select name="Didno"style="font-size:16px;">
			<option <c:if test="${a.Didno==''}">selected</c:if> value="">所有科系</option>
			<c:forEach items="${AllDept}" var="ac">					
			<option <c:if test="${a.Didno==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
			</c:forEach>					
		</select>				
		<select name="Grade" style="font-size:16px;">
			<option <c:if test="${a.Grade==''}">selected</c:if> value="">所有年級</option>
			<option <c:if test="${a.Grade=='1'}">selected</c:if> value="1">一年級</option>
			<option <c:if test="${a.Grade=='2'}">selected</c:if> value="2">二年級</option>
			<option <c:if test="${a.Grade=='3'}">selected</c:if> value="3">三年級</option>
			<option <c:if test="${a.Grade=='4'}">selected</c:if> value="4">四年級</option>
			<option <c:if test="${a.Grade=='4'}">selected</c:if> value="5">五年級</option>
		</select>			
		<select name="ClassNo" style="font-size:16px;">
			<option <c:if test="${a.ClassNo=='%'}">selected</c:if> value="">所有班級</option>
			<option <c:if test="${a.ClassNo=='1'}">selected</c:if> value="1">甲班</option>
			<option <c:if test="${a.ClassNo=='2'}">selected</c:if> value="2">乙班</option>
			<option <c:if test="${a.ClassNo=='3'}">selected</c:if> value="3">丙班</option>
			<option <c:if test="${a.ClassNo=='4'}">selected</c:if> value="4">丁班</option>
		</select>
		</td>				
		</td>
		<td class="hairLineTdF" nowrap>
		<input type="hidden" name="Oid" value="${a.Oid}"/>				
		<input type="text" autocomplete="off" size="4" style="font-size:18px;" onMouseDown="clearid(this.id), clearid('chi_name')"
		name="cscode" value="${a.cscode}" id="cscode${a.Oid}" onkeyup="if(this.value.length>2)getAny(this.value, 'cscode${a.Oid}', 'chi_name${a.Oid}', 'Csno', 'no')"/>
		<input type="text" autocomplete="off" size="6" style="font-size:18px;" onMouseDown="clearid(this.id), clearid('cscode')"
		name="chi_name" value="${a.chi_name}" id="chi_name${a.Oid}" onkeyup="getAny(this.value, 'chi_name${a.Oid}', 'cscode${a.Oid}', 'Csno', 'name')"/>
		</td>
		<td class="hairLineTdF">
		<select style="font-size:18px;" name="week">
			<c:forEach begin="1" end="7" var="w">
			<option <c:if test="${a.week==w}">selected</c:if> value="${w}">${w}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF">
		<select style="font-size:18px;" name="begin">
			<c:forEach begin="1" end="14" var="w">
			<option <c:if test="${a.begin==w}">selected</c:if> value="${w}">${w}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF">
		<select style="font-size:18px;" name="end">
			<c:forEach begin="1" end="14" var="w">
			<option <c:if test="${a.end==w}">selected</c:if> value="${w}">${w}</option>
			</c:forEach>
		</select>
		</td>
	</tr>
	</c:forEach>
</table>