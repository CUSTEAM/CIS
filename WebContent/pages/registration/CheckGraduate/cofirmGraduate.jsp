<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%">
			<tr>
<!-- 準備畢業 start-->			
				<td width="50%" valign="top">
				
				
				
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF" align="center">			
						<table width="99%">
							<tr>
								<td width="1">
								<img src="images/16-cube-blue.png" />
								</td>
								<td>
								準備畢業
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<c:if test="${empty ready}">
				&nbsp;沒有人
				</c:if>
				<c:if test="${!empty ready}">
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF">班級</td>
						<td class="hairLineTdF">學號</td>
						<td class="hairLineTdF">姓名</td>
					</tr>
					<c:forEach items="${ready}" var="r">
					<tr>
						<td class="hairLineTdF" align="center">${r.ClassName}</td>
						<td class="hairLineTdF">${r.student_no}</td>
						<td class="hairLineTdF">${r.student_name}</td>
					</tr>
					
					<c:if test="${r.ident_remark!=''&&r.ident_remark!=null}">
					<tr>
						<td colspan="3" class="hairLineTdF">
						<table>
							<tr>
								<td width="1"><img src="images/tag_red.gif"></td>
								<td width="100%" align="left">${r.ident_remark}</td>
							</tr>
						</table>
						</td>
					</tr>
					</c:if>
					</c:forEach>
				</table>
				</c:if>
				
				
				
				</td>
<!-- 準備畢業 end-->		
		


<!-- 確定畢業 start-->	
				<td width="50%" valign="top">
				
				
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF" align="center">			
						<table width="99%">
							<tr>
								<td width="1">
								<img src="images/16-cube-orange.png" />
								</td>
								<td>
								確定畢業
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				
				
				<c:if test="${empty goNow}">
				&nbsp;沒有人
				</c:if>
				<c:if test="${!empty goNow}">
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF" align="center">資格/畢業號</td>
						<td class="hairLineTdF" align="center">班級</td>
						<td class="hairLineTdF">學號</td>
						<td class="hairLineTdF">姓名</td>
					</tr>
					<c:forEach items="${goNow}" var="r">
					<tr>
						<td class="hairLineTdF" align="center">
						<input type="checkBox" checked onClick="goNow('${r.student_no}');" />
						<input type="text" name="docNo" id="docNo${r.student_no}" value="${r.student_no}" class="smallInput"/>
						</td>
						<td class="hairLineTdF" align="center">${r.ClassName}</td>
						<td class="hairLineTdF">${r.student_no}
						<input type="hidden" name="aGrade" value="${r.student_no}" />
						</td>
						<td class="hairLineTdF">${r.student_name}</td>
					</tr>
					</c:forEach>
				</table>
				</c:if>
				
				
				
				
				</td>
<!-- 確定畢業 end-->	
				
			</tr>
		</table>
<script>
function goNow(id){
	if(document.getElementById("docNo"+id).value==""){
		document.getElementById("docNo"+id).value=id;		
	}else{
		document.getElementById("docNo"+id).value="";
	}
}
</script>