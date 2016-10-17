<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Portfolio/AutoCreate" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon_Member_add.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">ePortfolio強制建立</font></div>		
		</td>
	</tr>
	
	
	<tr>
		<td>
		
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				建立範圍
				</td>
				<td class="hairLineTdF">
				<select name="CampusNo" style="font-size:16px;">
					<option <c:if test="${AutoCreateForm.map.CampusNo=='_'}">selected</c:if> value="_">所有校區</option>
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
		<INPUT type="submit"name="method"value="<bean:message key='Query'/>"class="gSubmit"
		id="Established">
		
		</td>
	</tr>
	
	<c:if test="${!empty students}">
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="100">班級代碼</td>
				<td class="hairLineTdF" width="100">班級名稱</td>
				<td class="hairLineTdF" width="100">學號</td>
				<td class="hairLineTdF" >姓名</td>
				<td class="hairLineTdF" width="30" align="center"></td>
				<td class="hairLineTdF" width="30" align="center"></td>
				<td class="hairLineTdF" width="30" align="center"></td>
				<td class="hairLineTdF" width="30" align="center"></td>
				<td class="hairLineTdF" width="30" align="center"></td>
			</tr>
			<c:forEach items="${students}" var="s">
			<tr>
				<td class="hairLineTdF">${s.ClassNo}</td>
				<td class="hairLineTdF">${s.ClassName}</td>
				<td class="hairLineTdF">${s.student_no}</td>
				<td class="hairLineTdF">${s.student_name}</td>
				<td class="hairLineTdF" align="center">
					<a href="${portfolioServer}/portfolio/myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/chart_bar.gif"/>
					</a>
				</td>
				<td class="hairLineTdF" align="center">
					<a href="http://${portfolioServer}/portfolio/myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/chart_line.gif"/>
					</a>
				</td>
				<td class="hairLineTdF" align="center">
					<a href="http://${portfolioServer}/portfolio/myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/icon/chart_pie.gif"/>
					</a>
				</td>
				<td class="hairLineTdF" align="center">
					<a href="http://${portfolioServer}/portfolio/myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/icon/folder_camera.gif"/>
					</a>
				</td>
				
				<td class="hairLineTdF" align="center">
					<a href="http://${portfolioServer}/portfolio/myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/icon/house.gif"/>
					</a>
				</td>
			</tr>
			</c:forEach>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit"name="method"value="<bean:message key='Create'/>"class="gSubmit"
		id="Established" onMouseOver="showHelpMessage('立即強制建立', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
		
		</td>
	</tr>
	</c:if>
	
	
	
</html:form>
	
</table>