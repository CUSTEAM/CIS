<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/CoanswServlet" method="post" onsubmit="init('儲存中, 請稍後')">
    <script>
			generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/chart_line.gif"></td>'+
										'<td>本學期教學評量問卷</td>'+
									'</tr>'+
								'</table>');
		</script>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr height="30">
				<td></td>
			</tr>
		</table>
		<c:if test="${empty dList}" >
		<table width="85%" align="center">
						<tr>
							<td>
								<div class="modulecontainer filled nomessages">
								<div class="first">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								<div>
								<div>
								<table width="95%" align="center">
								<tr>
								<td width="1">
								<img src="images/16-exc-mark.gif">
								</td>
								<td width="100%">
								 感謝您完成問卷 !
								</td>
								</tr>
								</table>
								</div>
								</div>
								<div class="last">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								</div>
							</td>
						</tr>
					</table>
					<br>

		</c:if>
		<c:if test="${!empty dList}" >
		<table width="85%" align="center">
		<tr>
		<td width="1">
		<img src="images/16-exc-mark.gif">
		</td>
		<td width="100%">
		 點擊課程開始做答
		</td>
		</tr>
		</table>
		</c:if>
		<c:forEach items='${dList}' var='d'>
		<table width="85%" align="center">
						<tr>
							<td>
								<div class="modulecontainer filled nomessages">
								<div class="first">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								<div>
								<div>
									<table width="100%" onclick="document.getElementById('dtime${d.Oid}table').style.display='inline'">
										<tr>
											<td>
											&nbsp;&nbsp;<img src="images/24-tag-pencil.png">
											</td>
											<td align="left" width="100%">
											<table>
												<tr>
													<td>
													<img src="images/24-book-green.png">
													</td>
													<td><input type="hidden" value="dtime${d.Oid}"/>
													<b>${d.chi_name}</b>
													</td>
													<td><img src="images/24-member1.png">
													</td>
													<td><b>${d.cname}</b>
													</td>
												</tr>
											</table>

											<table style="display:none" id="dtime${d.Oid}table">
												<tr>
													<td>
											<c:forEach items='${qList}' var='q'>
											<table>
												<tr>
													<td width="1">
													<img src="images/8-em-pencil.png">
													</td>
													<td width="100%">
													${q.question}
													</td>
												<tr>
												<tr>
												<td colspan="2">
												<c:if test="${q.textValue!='Y'}">
												<c:forEach items='${q.option}' var='qoo' varStatus='qoos'>

												<input type="radio" <c:if test="${qoos.count==1}">checked="checked"</c:if> name="radio${d.Oid}${qoo.parentOid}" value="${qoo.value}"/>${qoo.options}
												<c:set var="shit" value="${qoo.parentOid}"/>
												</c:forEach>
												</c:if>

												<c:if test="${q.textValue=='Y'}">
												<c:forEach items='${q.option}' var='qoo1'>
												<textarea cols="44" name="radio${d.Oid}${qoo1.parentOid}"></textarea>
												</c:forEach>
												</c:if>
												<br>


												</td>
												</tr>
											</table>
											</c:forEach>
													</td>
												</tr>
											</table>



											</td>
										</tr>
									</table>
								</div>
								</div>
								<div class="last">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								</div>
							</td>
						</tr>
					</table>
		</c:forEach>
		<br>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr height="30">
			<td bgcolor="#CFE69F" clospan="2" align="center">
			<c:if test="${!empty dList}" >
			<INPUT type="submit"  value="確定送出評量問卷" class="CourseButton">
			</c:if>
			</td>
		</tr>
		</html:form>
	</table>

<script>
	function showDetail(cs, th, sh){

		<c:forEach items='${bookList}' var='c'>
		document.getElementById('cs${c.oid}').style.display='none';
		document.getElementById('th${c.oid}').style.display='none';
		document.getElementById('sh${c.oid}').style.display='none';
		</c:forEach>

		document.getElementById(cs).style.display='inline';
		document.getElementById(th).style.display='inline';
		document.getElementById(sh).style.display='inline';




	}
</script>