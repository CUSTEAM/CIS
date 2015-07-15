<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=TeachUploadPrintScore.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=TeachUploadPrintScore.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${TchScoreUploadPrint != null}">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
			<c:choose>
				<c:when test="${TchScoreUploadPrint.scoretype == '1'}">
					<tr>
						<td colspan="7">
						中華科技大學 ${TchScoreUploadPrint.schoolYear} 學年第  ${TchScoreUploadPrint.schoolTerm} 學期期中考成績<br>
						班級 : ${TchScoreUploadPrint.depClassName} ( ${TchScoreUploadPrint.departClass} )<br>
						科目 : ${TchScoreUploadPrint.cscodeName} ( ${TchScoreUploadPrint.cscode} )<br>
						${TchScoreUploadPrint.teacherName}老師    請簽名:__________________ <br>
						</td>
					</tr>
					<tr>
						<td>學號</td>
						<td>姓名</td>
						<td>期中考成績</td>
						<td>&nbsp;&nbsp;</td>
						<td>學號</td>
						<td>姓名</td>
						<td>期中考成績</td>
					</tr>
					<c:set var="rcount" scope="page" value="0"/>
					<c:set var="stuNos" scope="page" value="${TchScoreUploadPrint.studentNo}"/>
					<c:set var="stuNames" scope="page" value="${TchScoreUploadPrint.studentName}"/>
					
					<c:forEach items="${TchScoreUploadPrint.score.scr2f}" var="stuscore">
						<c:set var="count" scope="page" value="${count+1}"/>
					    	<c:if test="${count == 1}">
					          	<tr>
					        	<td>${stuNos[rcount]}</td>
								<td>${stuNames[rcount]}</td>
								<c:if test="${stuscore != -1.0 && stuscore >= 60}">
					  				<td align="center">${stuscore}</td>
					  			</c:if>
								<c:if test="${stuscore != -1.0 && stuscore < 60}">
					  				<td align="center">${stuscore}*</td>
					  			</c:if>
								<c:if test="${stuscore == -1.0}">
					  				<td align="center">&nbsp;</td>
					  			</c:if>
					  			<td>&nbsp;&nbsp;</td>
					        </c:if>
					        
					        <c:if test="${count == 2}">
					        	<td>${stuNos[rcount]}</td>
								<td>${stuNames[rcount]}</td>
								<c:if test="${stuscore != -1.0 && stuscore >= 60}">
					  				<td align="center">${stuscore}</td>
					  			</c:if>
								<c:if test="${stuscore != -1.0 && stuscore < 60}">
					  				<td align="center">${stuscore}*</td>
					  			</c:if>
								<c:if test="${stuscore == -1.0}">
					  				<td align="center">&nbsp;</td>
					  			</c:if>
					        	</tr>
					        	<c:set var="count" scope="page" value="0"/>
					        </c:if>
					        <c:set var="rcount" scope="page" value="${rcount+1}"/>
					</c:forEach>
						
					<c:if test="${count == 1}">
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						</tr>
					</c:if>
					    
					<tr>
					<td colspan=7>全班人數：${TchScoreUploadPrint.totalstu}
					&nbsp;&nbsp;&nbsp;全班平均：${TchScoreUploadPrint.avgscore}
					&nbsp;&nbsp;&nbsp;及格人數：${TchScoreUploadPrint.pass}
					&nbsp;&nbsp;&nbsp;不及格人數：${TchScoreUploadPrint.nopass}
					&nbsp;&nbsp;&nbsp;時間：${TchScoreUploadPrint.now}
					</td>
					</tr>
				
				</c:when>

				<c:when test="${TchScoreUploadPrint.scoretype == '2'}">
					<tr>
					<td colspan="19">
					中華科技大學 ${TchScoreUploadPrint.schoolYear} 學年第  ${TchScoreUploadPrint.schoolTerm} 學期 平時及期末成績<br>
					班級 : ${TchScoreUploadPrint.depClassName} ( ${TchScoreUploadPrint.departClass} )<br>
					科目 : ${TchScoreUploadPrint.cscodeName} ( ${TchScoreUploadPrint.cscode} )<br>
					${TchScoreUploadPrint.teacherName}老師    請簽名:__________________ <br>
					</td>
					</tr>
					<tr>
						<td bgcolor=#add8e6 colspan="2" align="center"><font color=black>平時比例</font></td>
						<c:set var="rcount" value="0"/>
						<c:forEach items="${TchScoreUploadPrint.score.rratef}" var="rrate">
							<td bgcolor=#add8e6 width="10">${rrate}</td>
							<c:set var="rcount" value="${rcount + 1}"/>
						</c:forEach>
						<c:forEach begin="1" end="${17-rcount}">
							<td bgcolor=#add8e6>&nbsp;</td>
						</c:forEach>
					</tr>
					<tr>
					  	<td bgcolor=#99FFFF align="center" width="60"><font color=black>學號</font></td>
					    <td bgcolor=#FFD9D9 align="center" width="50"><font color=black>姓名</font></td>
						<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之一</font></td>
						<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之二</font></td>
						<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之三</font></td>
						<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之四</font></td>
						<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之五</font></td>
						<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之六</font></td>
						<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之七</font></td>
						<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之八</font></td>
						<td bgcolor=#99FFFF align="center"><font color=black>平時<br />之九</font></td>
						<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />之十</font></td>
						<td bgcolor=#99FFFF align="center"><font color=black>平時<br />平均</font></td>
						<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />30%</font></td>
						<td bgcolor=#99FFFF align="center"><font color=black>期中<br />成績</font></td>
						<td bgcolor=#FFD9D9 align="center"><font color=black>期中<br />30%</font></td>
						<td bgcolor=#99FFFF align="center"><font color=black>期末<br />成績</font></td>
						<td bgcolor=#FFD9D9 align="center"><font color=black>期末<br />40%</font></td>
						<td bgcolor=#FFFF99 align="center"><font color=black>學期<br />成績</font></td>
					</tr>
						<c:set var="rcount" scope="page" value="0"/>
						<c:set var="stuNames" scope="page" value="${TchScoreUploadPrint.studentName}"/>
						<c:set var="scr01f" scope="page" value="${TchScoreUploadPrint.score.scr01f}"/>
						<c:set var="scr02f" scope="page" value="${TchScoreUploadPrint.score.scr02f}"/>
						<c:set var="scr03f" scope="page" value="${TchScoreUploadPrint.score.scr03f}"/>
						<c:set var="scr04f" scope="page" value="${TchScoreUploadPrint.score.scr04f}"/>
						<c:set var="scr05f" scope="page" value="${TchScoreUploadPrint.score.scr05f}"/>
						<c:set var="scr06f" scope="page" value="${TchScoreUploadPrint.score.scr06f}"/>
						<c:set var="scr07f" scope="page" value="${TchScoreUploadPrint.score.scr07f}"/>
						<c:set var="scr08f" scope="page" value="${TchScoreUploadPrint.score.scr08f}"/>
						<c:set var="scr09f" scope="page" value="${TchScoreUploadPrint.score.scr09f}"/>
						<c:set var="scr10f" scope="page" value="${TchScoreUploadPrint.score.scr10f}"/>
						<c:set var="scr1f" scope="page" value="${TchScoreUploadPrint.score.scr1f}"/>
						<c:set var="scr16f" scope="page" value="${TchScoreUploadPrint.score.scr16f}"/>
						<c:set var="scr2f" scope="page" value="${TchScoreUploadPrint.score.scr2f}"/>
						<c:set var="scr17f" scope="page" value="${TchScoreUploadPrint.score.scr17f}"/>
						<c:set var="scr3f" scope="page" value="${TchScoreUploadPrint.score.scr3f}"/>
						<c:set var="scr18f" scope="page" value="${TchScoreUploadPrint.score.scr18f}"/>
						<c:set var="scri" scope="page" value="${TchScoreUploadPrint.score.scri}"/>
					
						<c:forEach items="${TchScoreUploadPrint.studentNo}" var="stuNos">
						<tr>
					        <td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuNos}</td>
					        <td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuNames[rcount]}</td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scr01f[rcount] != -1.0}">${scr01f[rcount]}</c:if>
					        <c:if test="${scr01f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">
					        <c:if test="${scr02f[rcount] != -1.0}">${scr02f[rcount]}</c:if>
					        <c:if test="${scr02f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scr03f[rcount] != -1.0}">${scr03f[rcount]}</c:if>
					        <c:if test="${scr03f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">
					        <c:if test="${scr04f[rcount] != -1.0}">${scr04f[rcount]}</c:if>
					        <c:if test="${scr04f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scr05f[rcount] != -1.0}">${scr05f[rcount]}</c:if>
					        <c:if test="${scr05f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">
					        <c:if test="${scr06f[rcount] != -1.0}">${scr06f[rcount]}</c:if>
					        <c:if test="${scr06f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scr07f[rcount] != -1.0}">${scr07f[rcount]}</c:if>
					        <c:if test="${scr07f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">
					        <c:if test="${scr08f[rcount] != -1.0}">${scr08f[rcount]}</c:if>
					        <c:if test="${scr08f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scr09f[rcount] != -1.0}">${scr09f[rcount]}</c:if>
					        <c:if test="${scr09f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">
					        <c:if test="${scr10f[rcount] != -1.0}">${scr10f[rcount]}</c:if>
					        <c:if test="${scr10f[rcount] == -1.0}">&nbsp;</c:if>
					       	</td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scr1f[rcount] != -1.0}">${scr1f[rcount]}</c:if>
					        <c:if test="${scr1f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">
					        <c:if test="${scr16f[rcount] != -1.0}">${scr16f[rcount]}</c:if>
					        <c:if test="${scr16f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scr2f[rcount] != -1.0}">${scr2f[rcount]}</c:if>
					        <c:if test="${scr2f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">
					        <c:if test="${scr17f[rcount] != -1.0}">${scr17f[rcount]}</c:if>
					        <c:if test="${scr17f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scr3f[rcount] != -1.0}">${scr3f[rcount]}</c:if>
					        <c:if test="${scr3f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">
					        <c:if test="${scr18f[rcount] != -1.0}">${scr18f[rcount]}</c:if>
					        <c:if test="${scr18f[rcount] == -1.0}">&nbsp;</c:if>
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">
					        <c:if test="${scri[rcount] < 60 && scri[rcount] != -1.0}">
					        	<font color="red">${scri[rcount]}&nbsp;*</font>
					        </c:if>
					        <c:if test="${scri[rcount] >= 60 && scri[rcount] != -1.0}">
					          	<font color="black">${scri[rcount]}</font>
					        </c:if>
					        <c:if test="${scri[rcount] == -1.0}">
					          	&nbsp;
					        </c:if>
					        </td>
					    </tr>
					    <c:set var="rcount" value="${rcount+1}"/>
					    </c:forEach>
					<tr>
					<td colspan=19>全班人數：${TchScoreUploadPrint.totalstu}
					&nbsp;&nbsp;&nbsp;全班平均：${TchScoreUploadPrint.avgscore}
					&nbsp;&nbsp;&nbsp;及格人數：${TchScoreUploadPrint.pass}
					&nbsp;&nbsp;&nbsp;不及格人數：${TchScoreUploadPrint.nopass}
					&nbsp;&nbsp;&nbsp;時間：${TchScoreUploadPrint.now}
					</td>
					</tr>
				</c:when>

				<c:when test="${TchScoreUploadPrint.scoretype == '3'}">
					<tr>
						<td colspan="7">
						中華科技大學 ${TchScoreUploadPrint.schoolYear} 學年第  ${TchScoreUploadPrint.schoolTerm} 學期 學期成績<br>
						班級 : ${TchScoreUploadPrint.depClassName} ( ${TchScoreUploadPrint.departClass} )<br>
						科目 : ${TchScoreUploadPrint.cscodeName} ( ${TchScoreUploadPrint.cscode} )<br>
						${TchScoreUploadPrint.teacherName}老師    請簽名:__________________ <br>
						</td>
					</tr>
					<tr>
						<td>學號</td>
						<td>姓名</td>
						<td>學期成績</td>
						<td>&nbsp;&nbsp;</td>
						<td>學號</td>
						<td>姓名</td>
						<td>學期成績</td>
					</tr>
					<c:set var="rcount" scope="page" value="0"/>
					<c:set var="stuNos" scope="page" value="${TchScoreUploadPrint.studentNo}"/>
					<c:set var="stuNames" scope="page" value="${TchScoreUploadPrint.studentName}"/>
					
					<c:forEach items="${TchScoreUploadPrint.score.scri}" var="stuscore">
						<c:set var="count" scope="page" value="${count+1}"/>
					    	<c:if test="${count == 1}">
					          	<tr>
					        	<td>${stuNos[rcount]}</td>
								<td>${stuNames[rcount]}</td>
								<c:if test="${stuscore != -1.0 && stuscore >= 60}">
					  				<td align="center">${stuscore}</td>
					  			</c:if>
								<c:if test="${stuscore != -1.0 && stuscore < 60}">
					  				<td align="center">${stuscore}*</td>
					  			</c:if>
								<c:if test="${stuscore == -1.0}">
					  				<td align="center">&nbsp;</td>
					  			</c:if>
					  			<td>&nbsp;&nbsp;</td>
					        </c:if>
					        
					        <c:if test="${count == 2}">
					        	<td>${stuNos[rcount]}</td>
								<td>${stuNames[rcount]}</td>
								<c:if test="${stuscore != -1.0 && stuscore >= 60}">
					  				<td align="center">${stuscore}</td>
					  			</c:if>
								<c:if test="${stuscore != -1.0 && stuscore < 60}">
					  				<td align="center">${stuscore}*</td>
					  			</c:if>
								<c:if test="${stuscore == -1.0}">
					  				<td align="center">&nbsp;</td>
					  			</c:if>
					        	</tr>
					        	<c:set var="count" scope="page" value="0"/>
					        </c:if>
					        <c:set var="rcount" scope="page" value="${rcount+1}"/>
					</c:forEach>
					
				    <c:if test="${count == 1}">
				       <td>&nbsp;</td>
				       <td>&nbsp;</td>
				       <td>&nbsp;</td>
				       </tr>
				    </c:if>
				    
					<tr>
					<td colspan=7>全班人數：${TchScoreUploadPrint.totalstu}
					&nbsp;&nbsp;&nbsp;全班平均：${TchScoreUploadPrint.avgscore}
					&nbsp;&nbsp;&nbsp;及格人數：${TchScoreUploadPrint.pass}
					&nbsp;&nbsp;&nbsp;不及格人數：${TchScoreUploadPrint.nopass}
					&nbsp;&nbsp;&nbsp;時間：${TchScoreUploadPrint.now}
					</td>
					</tr>
				

				</c:when>

			</c:choose>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>