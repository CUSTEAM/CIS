<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF">範圍</td>
		<td class="hairLineTdF">課程代碼 </td>
		<td class="hairLineTdF">星期</td>
		<td class="hairLineTdF">開始</td>
		<td class="hairLineTdF">結束</td>
	</tr>
	<tr>
		<td class="hairLineTdF">				
		<select name="Cidno" style="font-size:16px;">
			
			<c:forEach items="${AllCampus}" var="ac">					
			<option <c:if test="${BatchStaticTableingForm.map.Cidno[0]==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
			</c:forEach>					
		</select>				
		<select name="Sidno" style="font-size:16px;">
			<option <c:if test="${BatchStaticTableingForm.map.Sidno[0]==''}">selected</c:if> value="">所有學制</option>
			<c:forEach items="${AllSchool}" var="ac">					
			<option <c:if test="${BatchStaticTableingForm.map.Sidno[0]==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
			</c:forEach>					
		</select>						
		<select name="Didno"style="font-size:16px;">
			<option <c:if test="${BatchStaticTableingForm.map.Didno[0]==''}">selected</c:if> value="">所有科系</option>
			<c:forEach items="${AllDept}" var="ac">					
			<option <c:if test="${BatchStaticTableingForm.map.Didno[0]==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
			</c:forEach>					
		</select>				
		<select name="Grade" style="font-size:16px;">
			<option <c:if test="${BatchStaticTableingForm.map.Grade[0]==''}">selected</c:if> value="">所有年級</option>
			<option <c:if test="${BatchStaticTableingForm.map.Grade[0]=='1'}">selected</c:if> value="1">一年級</option>
			<option <c:if test="${BatchStaticTableingForm.map.Grade[0]=='2'}">selected</c:if> value="2">二年級</option>
			<option <c:if test="${BatchStaticTableingForm.map.Grade[0]=='3'}">selected</c:if> value="3">三年級</option>
			<option <c:if test="${BatchStaticTableingForm.map.Grade[0]=='4'}">selected</c:if> value="4">四年級</option>
			<option <c:if test="${BatchStaticTableingForm.map.Grade[0]=='4'}">selected</c:if> value="5">五年級</option>
		</select>			
		<select name="ClassNo" style="font-size:16px;">
			<option <c:if test="${BatchStaticTableingForm.map.ClassNo[0]=='%'}">selected</c:if> value="">所有班級</option>
			<option <c:if test="${BatchStaticTableingForm.map.ClassNo[0]=='1'}">selected</c:if> value="1">甲班</option>
			<option <c:if test="${BatchStaticTableingForm.map.ClassNo[0]=='2'}">selected</c:if> value="2">乙班</option>
			<option <c:if test="${BatchStaticTableingForm.map.ClassNo[0]=='3'}">selected</c:if> value="3">丙班</option>
			<option <c:if test="${BatchStaticTableingForm.map.ClassNo[0]=='4'}">selected</c:if> value="4">丁班</option>
		</select>
		</td>				
		</td>
		<td class="hairLineTdF" nowrap>
		<input type="hidden" name="Oid" />				
		<input type="text" autocomplete="off" size="4" style="font-size:18px;" onMouseDown="clearid(this.id), clearid('chi_name')"
		name="cscode" value="${BatchStaticTableingForm.map.cscode[0]}" id="cscode" onkeyup="if(this.value.length>2)getAny(this.value, 'cscode', 'chi_name', 'Csno', 'no')"/>
		<input type="text" autocomplete="off" size="6" style="font-size:18px;" onMouseDown="clearid(this.id), clearid('cscode')"
		name="chi_name" value="${BatchStaticTableingForm.map.chi_name[0]}" id="chi_name" onkeyup="getAny(this.value, 'chi_name', 'cscode', 'Csno', 'name')"/>
		</td>
		<td class="hairLineTdF">
		<select style="font-size:18px;" name="week">
			<c:forEach begin="1" end="7" var="w">
			<option <c:if test="${BatchStaticTableingForm.map.week[0]==w}">selected</c:if> value="${w}">${w}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF">
		<select style="font-size:18px;" name="begin">
			<c:forEach begin="1" end="14" var="w">
			<option <c:if test="${BatchStaticTableingForm.map.begin[0]==w}">selected</c:if> value="${w}">${w}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF">
		<select style="font-size:18px;" name="end">
			<c:forEach begin="1" end="14" var="w">
			<option <c:if test="${BatchStaticTableingForm.map.end[0]==w}">selected</c:if> value="${w}">${w}</option>
			</c:forEach>
		</select>
		</td>
	</tr>
</table>