<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Salary/TaxtableManager" enctype="multipart/form-data" method="post" onsubmit="init('執行中, 請稍後')">
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/calculator.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;扣繳稅額設定&nbsp;<input type="hidden" name="exSearch" value="${HRmanagerForm.map.exSearch}" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table width="100%">
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">載入新設定檔</td>
						<td class="hairLineTd">
						<input type="file" name="inputExcel" class="CourseButton"/>
						<INPUT type="submit"
							   name="method"
							   id="continue"
							   onMouseOver="showHelpMessage('查詢', 'inline', this.id)" 
							   onMouseOut="showHelpMessage('', 'none', this.id)"
							   value="<bean:message key='Update'/>" class="CourseButton">
						</td>
					</tr>
				</table>
				
				<table class="hairLineTable" width="99%">
				
					<tr>
						<td class="hairLineTdF" align="center"><font size="-2">薪資低標</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">薪資高標</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">0人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">1人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">2人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">3人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">4人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">5人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">6人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">7人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">8人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">9人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">10人</font></td>
						<td class="hairLineTdF" align="center"><font size="-2">11人</font></td>
					</tr>
				
					<c:forEach items="${taxTable}" var="t">
					<tr>
						<td class="hairLineTdF">${t.mini}</td>
						<td class="hairLineTdF">${t.max}</td>
						<td class="hairLineTdF">${t.p0}</td>
						<td class="hairLineTdF">${t.p1}</td>
						<td class="hairLineTdF">${t.p2}</td>
						<td class="hairLineTdF">${t.p3}</td>
						<td class="hairLineTdF">${t.p4}</td>
						
						<td class="hairLineTdF">${t.p5}</td>
						<td class="hairLineTdF">${t.p6}</td>
						<td class="hairLineTdF">${t.p7}</td>
						<td class="hairLineTdF">${t.p8}</td>
						<td class="hairLineTdF">${t.p9}</td>
						<td class="hairLineTdF">${t.p10}</td>
						<td class="hairLineTdF">${t.p11}</td>
					</tr>			
					</c:forEach>
				</table>				
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
</html:form>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
</table>