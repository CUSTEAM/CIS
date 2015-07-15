<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>		
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td>
				<table class="hairLineTable" width="99%">
					<tr>
						<td class="hairLineTdF">
						<img src="images/printer.gif" border="0">
		 				<select onchange="jumpMenu('parent',this,0)">
		 					<option value="javascript:void(0)">選擇報表</option>
		 					<option value="/CIS/List4CheckCsGroup">通用報表</option>
		 					<!-- option value="/CIS/Print/registration/CsGroupStdCount.do">報部統計表</option-->
		 					<option value="/CIS/CsGroupDoc">學生申請表</option>
		 					<!-- option value="">學程證書</option-->
		 					<option value="/CIS/Print/registration/Diploma4Csgroup.do">學程證明書</option>
							<option value="/CIS/Print/registration/Transcript4Csgroup.do">學程歷年成績表</option>
		 				</select>
		 				<script>
						<!--
						function jumpMenu(targ,selObj,restore){
								eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
								eval(targ+".location.target='_blank'");
								if (restore) selObj.selectedIndex=0;
							}
						//-->
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
						<td class="hairLineTdF">學號</td>
						<td class="hairLineTdF">姓名</td>
						<td class="hairLineTdF">學程名稱</td>						
						<td class="hairLineTdF">必修應修</td>
						<td class="hairLineTdF">選修應修</td>
						<td class="hairLineTdF">外系應修</td>
						<td class="hairLineTdF">必修已得</td>
						<td class="hairLineTdF">選修已得</td>
						<td class="hairLineTdF">外系已得</td>	
						<td class="hairLineTdF">狀況</td>	
					</tr>
					
				<c:forEach items="${relult}" var="r">					
					<tr>
						<td class="hairLineTdF">${r.student_no}</td>
						<td class="hairLineTdF">${r.student_name}</td>
						<td class="hairLineTdF">${r.cname}</td>
						<td class="hairLineTdF">${r.major1}</td>
						<td class="hairLineTdF">${r.minor}</td>
						<td class="hairLineTdF">${r.outdept}</td>
						<td class="hairLineTdF">${r.opt1}</td>
						<td class="hairLineTdF">${r.opt2}</td>
						<td class="hairLineTdF">${r.optOut}</td>
						<td class="hairLineTdF">
						<c:if test="${r.igot}">已取得</c:if>
						<c:if test="${!r.igot}">進修中</c:if>
						</td>
					</tr>								
				</c:forEach>
				
				
				</table>
				
				</td>
			</tr>	
			<tr height="30">
				<td class="fullColorTable" align="center">
				
				</td>
			</tr>
		</table>