<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>
<script>
history.go(1);
function searchCheck() {
	var iCount;
	iCount = getCookie("emplListCount");
	if (iCount == 0) {
		alert("請選擇一個教師作班級課表查詢!!");
		return false;
	} else if(iCount > 1) {
		alert("不可複選教師作班級課表查詢!!");
		return false;
	} else {
		return true;
	}
}
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Tutor/StudentInfo" method="post" focus="cname2" onsubmit="init('執行中, 請稍後')">
	<script>
		generateTableBanner('<div class="gray_15"><B>班 級 課 表</B></div>');
	</script>
 	<c:if test="${not empty courseList}">
	<tr>
		<td>
			<table width="100%" border="1">
				<c:if test="${not empty courseList}">
				<tr>
					<td>
						<table width="100%" border="1">
							<tr>
								<td height="30" bgcolor="#CCCCCC">&nbsp;</td>
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status2">
								<td align="center" bgcolor="#CCCCCC"><b><font color="black"><c:out value="${weekdayList[status2.index]}" /></font></b></td>
								</c:forEach>
							</tr>
							<c:if test="${rowsCols['mode'] == 'D'}">
							<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
							<tr>	
								<td align="center" width="100" bgcolor="#CCCCCC">
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
								<td height="100" width="100"><strong><font color="green">		
									<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['elearning']}" /></font></strong>
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
							</c:if>
	
							<c:if test="${rowsCols['mode'] == 'N'}">
							<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
							<tr>	
								<td align="center" width="100" bgcolor="#CCCCCC">
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
								<td height="100" width="100"><strong><font color="green">		
									<c:out value="${courseList[(status1.index * 15) + status.index + 5]['chi_name']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index + 5]['cname']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index + 5]['elearning']}" /></font></strong>
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
							</c:if>
									
							<c:if test="${rowsCols['mode'] == 'H'}">
							<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
							<tr>	
								<td align="center" width="100" bgcolor="#CCCCCC">
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
								<td height="100" width="100"><strong><font color="green">		
									<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['elearning']}" /></font></strong>
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
							</c:if>
							
							<c:if test="${rowsCols['mode'] == 'S'}">
							<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
							<tr>	
								<td align="center" width="100" bgcolor="#CCCCCC">
									<b><font color="black"><c:out value="${nodeList[status.index]}" escapeXml="false" /></font><b/>
								</td>	
								<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
								<td height="100" width="100"><strong><font color="green">		
									<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>
									<c:out value="${courseList[(status1.index * 15) + status.index]['elearning']}" /></font></strong>
								</td>						
								</c:forEach>
							</tr>						
							</c:forEach>
							</c:if>
						</table>
					</td>
				</tr>
				</c:if>
			</table>
			<script>
		   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Back' />" class="CourseButton">')
		   	</script>
		</td>
	</tr>
	</c:if>
</html:form>
</table>