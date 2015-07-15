<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>			
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" nowrap>開課年度</td>
				<td class="hairLineTdF" nowrap>
				
				<input type="text" name="year" id="year" value="${DtimeReserveManagerForm.map.year}" style="font-size:18px;" size="3"/>
				
				</td>
				<td class="hairLineTdF" nowrap>
				開課學期
				</td>
				<td class="hairLineTdF" nowrap>
				<select name="term" style="font-size:18px;" id="term">
					<option <c:if test="${DtimeReserveManagerForm.map.term=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${DtimeReserveManagerForm.map.term=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				</td>
				<td class="hairLineTdF" nowrap style="font-size:12px;">
				<img src="images/16-IChat-bubble.jpg" />
				課程開設的學年學期
				</td>
			</tr>
		</table>	
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" nowrap>				
				<c:set var="campusSel" value="${DtimeReserveManagerForm.map.campusInCharge2}"/>
	  			<c:set var="schoolSel" value="${DtimeReserveManagerForm.map.schoolInCharge2}"/>
	  			<c:set var="deptSel"   value="${DtimeReserveManagerForm.map.deptInCharge2}"/>
	  			<c:set var="classSel"  value="${DtimeReserveManagerForm.map.classInCharge2}"/>
	  			<c:set var="classLess"  value="${DtimeReserveManagerForm.map.classLess}"/>
	  			<%@include file="/pages/include/ClassSelect4_dept4all.jsp"%>
	  			
				</td>
				<td class="hairLineTdF" nowrap style="font-size:12px;">				
				<img src="images/16-IChat-bubble.jpg" />
	  			系統自動對應入學年度
				</td>			
			</tr>
			
		</table>
		
		<!-- table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>				
				
				
				列出全校
				<a href="/CIS/Print/reserve/CheckList.do?type=c">
				已規劃開課與教師的對應(班級)</a>，或是列出全校
				<a href="/CIS/Print/reserve/CheckList.do?type=t">
				教師與已規劃開課的對應(教師)</a>				
				</td>				
			</tr>
		</table-->
		
		
		
		
		</td>
	</tr>	
	<tr>
		<td class="fullColorTable" align="center">		
		<input type="submit" name="method" value="<bean:message key='Query'/>" 
		id="Query" class="gSubmit" onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">		
		</td>
	</tr>
</table>
		