<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:forEach items="${summerStudents}" var="m">

<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF">

		<table onClick="showTable('${m.cscode}${m.depart_class}Id')" width="100%" style="cursor:pointer;">
			<tr>
				<td width="1"><img src="images/icon/book.gif"></td><td align="left" nowrap>點選此處建立 <b><font size="+1">${m.ClassName}</font></b> </td>
				
				<td align="left" width="100%" nowrap>
				<b><font size="+1">${m.chi_name}</font></b>	成績		
				</td>
			</tr>
		</table>

		
		
		<c:if test="${empty m.students}">
		<table border="0"  width="100%" id="${m.dtimeOid}Id" style="display:none">
		<tr>
			<td width="1">
			<img src="images/blocked1.png">
			</td>
			<td align="left">
			本課程沒有選課學生
			</td>
		</tr>
		</table>
		</c:if>
	
	
		
	
	
		
		
		
		
		
		<c:if test="${not empty m.students}">
		<table class="hairLineTable" width="99%" id="${m.cscode}${m.depart_class}Id" style="display:none">
			
			<tr>
				<td align="center" class="hairLineTd">班級</td>
				<td align="center" class="hairLineTd">學號</td>
				<td align="center" class="hairLineTd">姓名</td>
				<td align="center" class="hairLineTd">平時考</td>
				<td align="center" class="hairLineTd">平時考</td>
				<td align="center" class="hairLineTd">平時考</td>
				<td align="center" class="hairLineTd">平時考</td>
				<td align="center" class="hairLineTd">平時成績<font size="-2">(30%)</font></td>
				<td align="center" class="hairLineTd">期中成績<font size="-2">(30%)</font></td>
				<td align="center" class="hairLineTd">期末成績<font size="-2">(40%)</font></td>
				<td align="center" class="hairLineTd">學期成績</td>
			</tr>
		<%int i=0;%>
			
		<c:forEach items="${m.students}" var="s">
		<%i=i+1;%>
		<tr <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>
				<td align="center" <%if(i%2==1){%>class="hairLineTdF"<%}else{%> class="hairLineTd" <%}%>>				
				<input type="hidden" size="2" value="${s.student_no}" name="studentNo"/>				
				<input type="hidden" size="2" value="${m.cscode}" name="cscode"/>
				<input type="hidden" size="2" value="${m.depart_class}" name="departClass"/>
				${s.ClassName}</td>
				<td align="center" >${s.student_no}</td>
				<td align="center">${s.student_name}</td>
				<td align="center"><input type="text" size="1" onclick="this.value=''" id="${s.Oid}sc1" name="sc1" value="${s.sc1}" onkeyup="count('${s.Oid}sc1', '${s.Oid}sc2', '${s.Oid}sc3', '${s.Oid}sc4', '${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score'), 
				countAll('${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score')"/></td>
				<td align="center"><input type="text" size="1" onclick="this.value=''" id="${s.Oid}sc2" name="sc2" value="${s.sc2}" onkeyup="count('${s.Oid}sc1', '${s.Oid}sc2', '${s.Oid}sc3', '${s.Oid}sc4', '${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score'), 
				countAll('${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score')"/></td>
				<td align="center"><input type="text" size="1" onclick="this.value=''" id="${s.Oid}sc3" name="sc3" value="${s.sc3}" onkeyup="count('${s.Oid}sc1', '${s.Oid}sc2', '${s.Oid}sc3', '${s.Oid}sc4', '${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score'), 
				countAll('${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score')"/></td>
				<td align="center"><input type="text" size="1" onclick="this.value=''" id="${s.Oid}sc4" name="sc4" value="${s.sc4}" onkeyup="count('${s.Oid}sc1', '${s.Oid}sc2', '${s.Oid}sc3', '${s.Oid}sc4', '${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score'), 
				countAll('${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score')"/></td>
				<td align="center"><input type="text" size="1" onclick="this.value=''" name="freeScore" id="${s.Oid}freeScore" value="${s.free_score}" onkeyup="countAll('${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score'), 
				countAll('${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score')"/></td>
				<td align="center"><input type="text" size="1" onclick="this.value=''" name="medScore" id="${s.Oid}medScore" value="${s.med_score}" onkeyup="countAll('${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score')"/></td>
				<td align="center"><input type="text" size="1" onclick="this.value=''" name="endScore" id="${s.Oid}endScore" value="${s.end_score}" onkeyup="countAll('${s.Oid}medScore', '${s.Oid}endScore', '${s.Oid}freeScore', '${s.Oid}score')"/></td>
				<td align="center">
				<input type="text" size="2" onclick="this.value=''" name="score" id="${s.Oid}score" value="${s.score}" />
				</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="11" class="hairLineTdF">
			<table cellspacing="5" cellpadding="5">
				<tr>
					
					<td><INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="gSubmit">					
					<a href="/CIS/List4SummerRat?dtimeOid=${m.dtimeOid}&departClass=${m.depart_class}&cscode=${m.cscode}">
					<img src="images/ico_file_excel.png" border="0">&nbsp;成績冊, 請儲存後再點選列印</a>
					</td>
					
				</tr>
			</table>					
			</td>
		</tr>
		</table>	
		</c:if>
		</td>
	</tr>
</table>
</c:forEach>