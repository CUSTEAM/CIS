<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Course/Seld3ScheduleManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/bell_error.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">通識課程選課上限管理</font></div>		
		</td>
		
		
		
		
		
		
		
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>範圍</td>
				<td class="hairLineTdF" nowrap>上限</td>
				<td class="hairLineTdF" nowrap>最後修改</td>
				<td class="hairLineTdF"></td>
			</tr>
			
			
			
			
			<tr>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" />		
				<select name="Cidno" style="font-size:16px;">					
					<c:forEach items="${AllCampus}" var="ac">					
					<option value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>				
				<select name="Sidno" style="font-size:16px;">
					<option value="">所有學制</option>
					<c:forEach items="${AllSchool}" var="ac">					
					<option value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>						
				<select name="Didno"style="font-size:16px;">
					<option value="">所有科系</option>
					<c:forEach items="${AllDept}" var="ac">					
					<option value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>				
				<select name="Grade" style="font-size:16px;">
					<option value="">所有年級</option>
					<option value="1">一年級</option>
					<option value="2">二年級</option>
					<option value="3">三年級</option>
					<option value="4">四年級</option>
					<option value="5">五年級</option>
				</select>			
				<select name="ClassNo" style="font-size:16px;">
					<option value="">所有班級</option>
					<option value="1">甲班</option>
					<option value="2">乙班</option>
					<option value="3">丙班</option>
					<option value="4">丁班</option>
				</select>
				</td>
				
				<td class="hairLineTdF">		
				<select style="font-size:18px;" name="max">
					<c:forEach begin="0" end="14" var="m">
					<option <c:if test="${Seld3ScheduleManagerForm.map.max[0]==m}">selected</c:if> value="${m}">${m}</option>
					</c:forEach>
				</select>		
				</td>
				
				<td class="hairLineTdF" nowrap></td>
				<td class="hairLineTdF" nowrap>
				
				<input type="submit"
					   name="method" id="Save"
					   value="<bean:message
					   key='Save'/>"
					   class="gSubmit" 
					   onMouseOver="showHelpMessage('存', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)" />		
				</td>
				
			</tr>	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		<c:forEach items="${allRules}" var="a">
			
			
			
			
			<tr>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value="${a.Oid}"/>		
				<select name="Cidno" style="font-size:16px;">
					<option  value="">刪除規則</option>
					<c:forEach items="${AllCampus}" var="ac">					
					<option <c:if test="${a.cidno==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>				
				<select name="Sidno" style="font-size:16px;">
					<option <c:if test="${a.sidno==''}">selected</c:if> value="">所有學制</option>
					<c:forEach items="${AllSchool}" var="ac">					
					<option <c:if test="${a.sidno==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>						
				<select name="Didno"style="font-size:16px;">
					<option <c:if test="${a.didno==''}">selected</c:if> value="">所有科系</option>
					<c:forEach items="${AllDept}" var="ac">					
					<option <c:if test="${a.didno==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>				
				<select name="Grade" style="font-size:16px;">
					<option <c:if test="${a.grade==''}">selected</c:if> value="">所有年級</option>
					<option <c:if test="${a.grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${a.grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${a.grade=='3'}">selected</c:if> value="3">三年級</option>
					<option <c:if test="${a.grade=='4'}">selected</c:if> value="4">四年級</option>
					<option <c:if test="${a.grade=='5'}">selected</c:if> value="5">五年級</option>
				</select>			
				<select name="ClassNo" style="font-size:16px;">
					<option <c:if test="${a.classno=='%'}">selected</c:if> value="">所有班級</option>
					<option <c:if test="${a.classno=='1'}">selected</c:if> value="1">甲班</option>
					<option <c:if test="${a.classno=='2'}">selected</c:if> value="2">乙班</option>
					<option <c:if test="${a.classno=='3'}">selected</c:if> value="3">丙班</option>
					<option <c:if test="${a.classno=='4'}">selected</c:if> value="4">丁班</option>
				</select>
				</td>
				
				<td class="hairLineTdF">		
				<select style="font-size:18px;" name="max">
					<c:forEach begin="0" end="14" var="m">
					<option <c:if test="${a.max==m}">selected</c:if> value="${m}">${m}</option>
					</c:forEach>
				</select>		
				</td>
				<td class="hairLineTdF" nowrap>${a.cname}</td>
				<td class="hairLineTdF" nowrap>
				
				<input type="submit"
					   name="method" id="Save"
					   value="<bean:message
					   key='Save'/>"
					   class="gGreen" 
					   onMouseOver="showHelpMessage('存', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)" />		
				</td>
				
			</tr>
		</c:forEach>
		
		
		</table>
		
		
		
		
		
		
		
		
		
		
		
		
		</td>
	</tr>	
	</html:form>
</table>
	