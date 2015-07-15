<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="SysAdmin/PortfolioManager/UserManager" method="post" onsubmit="init('系統處理中...')">

	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr height="30">
					<td width="28" align="right">
						<img src="images/user.gif">
					</td>
					<td align="left">
						&nbsp;使用者管理&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="UserManager/search.jsp" %>
		</td>
	</tr>
	<c:if test="${!empty students}">	
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="99%">
				<img src="images/printer.gif" border="0">
				<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool"
		   		 onMouseOver="showHelpMessage('各項個人證明', 'inline', this.id)"
		   		 onMouseOut="showHelpMessage('', 'none', this.id)">
					<option value="javascript:void(0)">選擇報表</option>
					<option value="/CIS/Print/portfolio/List4UserManager.do">通用報表</option>			
				</select>
<script>
	function jumpMenu(targ,selObj,restore){
		eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
		eval(targ+".location.target='_blank'");
		if (restore) selObj.selectedIndex=0;
}
</script>	
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
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
					<a href="${portfolioServer}/myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/chart_bar.gif"/>
					</a>
				</td>
				<td class="hairLineTdF" align="center">
					<a href="${portfolioServer}myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/chart_line.gif"/>
					</a>
				</td>
				<td class="hairLineTdF" align="center">
					<a href="${portfolioServer}myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/icon/chart_pie.gif"/>
					</a>
				</td>
				<td class="hairLineTdF" align="center">
					<a href="${portfolioServer}myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/icon/folder_camera.gif"/>
					</a>
				</td>
				
				<td class="hairLineTdF" align="center">
					<a href="${portfolioServer}myPortfolio?Uid=${s.student_no}" target="_blank">
						<img border="0" src="images/icon/house.gif"/>
					</a>
				</td>
			</tr>
			</c:forEach>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
	</c:if>
	
</html:form>
</table>