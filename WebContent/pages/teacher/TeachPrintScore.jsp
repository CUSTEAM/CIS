<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=TeachPrintScore.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=TeachPrintScore.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${TchScorePrint != null}">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
			<c:choose>
				<c:when test="${TchScorePrint.scoretype == '6'}">
					<tr>
						<td colspan="7">
						中華科技大學 ${TchScorePrint.schoolYear} 學年第  ${TchScorePrint.schoolTerm} 學期期中考成績<br/>
						班級 : ${TchScorePrint.depClassName} ( ${TchScorePrint.departClass} )<br/>
						科目 : ${TchScorePrint.cscodeName} ( ${TchScorePrint.cscode} )<br/>
						${TchScorePrint.teacherName}老師    請簽名:__________________ <br/>
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
					<c:set var="stuNos" scope="page" value="${TchScorePrint.studentNo}"/>
					<c:set var="stuNames" scope="page" value="${TchScorePrint.studentName}"/>
					
					<c:forEach items="${TchScorePrint.score2}" var="stuscore">
						<c:set var="count" scope="page" value="${count+1}"/>
					    	<c:if test="${count == 1}">
					          	<tr>
					        	<td>${stuNos[rcount]}</td>
								<td>${stuNames[rcount]}</td>
					  			<td align="center">${stuscore}</td>
					  			<td>&nbsp;&nbsp;</td>
					        </c:if>
					        
					        <c:if test="${count == 2}">
					        	<td>${stuNos[rcount]}</td>
								<td>${stuNames[rcount]}</td>
					  			<td align="center">${stuscore}</td>
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
					<td colspan=7>全班人數：${TchScorePrint.totalstu}
					&nbsp;&nbsp;&nbsp;全班平均：${TchScorePrint.avgscore}
					&nbsp;&nbsp;&nbsp;及格人數：${TchScorePrint.pass}
					&nbsp;&nbsp;&nbsp;不及格人數：${TchScorePrint.nopass}
					&nbsp;&nbsp;&nbsp;時間：${TchScorePrint.now}
					</td>
					</tr>
				
				</c:when>
				<c:when test="${TchScorePrint.scoretype == '7'}">
					<tr>
						<td colspan="7">
						中華科技大學 ${TchScorePrint.schoolYear} 學年第  ${TchScorePrint.schoolTerm} 學期 學期成績<br/>
						班級 : ${TchScorePrint.depClassName} ( ${TchScorePrint.departClass} )<br/>
						科目 : ${TchScorePrint.cscodeName} ( ${TchScorePrint.cscode} )<br/>
						<c:if test="${TchScorePrint.now == ''}">
						您並未經由學期成績上傳步驟上傳本學期成績,本報表僅供參考!
						</c:if>
						<c:if test="${TchScorePrint.now != ''}">
						${TchScorePrint.teacherName}老師    請簽名:__________________ <br/>
						</c:if>
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
					<c:set var="stuNos" scope="page" value="${TchScorePrint.studentNo}"/>
					<c:set var="stuNames" scope="page" value="${TchScorePrint.studentName}"/>
					
					<c:forEach items="${TchScorePrint.score}" var="stuscore">
						<c:set var="count" scope="page" value="${count+1}"/>
					    	<c:if test="${count == 1}">
					          	<tr>
					        	<td>${stuNos[rcount]}</td>
								<td>${stuNames[rcount]}</td>
					  			<td align="center">${stuscore}</td>
					  			<td>&nbsp;&nbsp;</td>
					        </c:if>
					        
					        <c:if test="${count == 2}">
					        	<td>${stuNos[rcount]}</td>
								<td>${stuNames[rcount]}</td>
					  			<td align="center">${stuscore}</td>
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
					<td colspan=7>全班人數：${TchScorePrint.totalstu}
					&nbsp;&nbsp;&nbsp;全班平均：${TchScorePrint.avgscore}
					&nbsp;&nbsp;&nbsp;及格人數：${TchScorePrint.pass}
					&nbsp;&nbsp;&nbsp;不及格人數：${TchScorePrint.nopass}
					&nbsp;&nbsp;&nbsp;時間：${TchScorePrint.now}
					</td>
					</tr>
				

				</c:when>
				<c:when test="${TchScorePrint.scoretype == '8'}">
					<tr>
					<td colspan="19">
					中華科技大學 ${TchScorePrint.schoolYear} 學年第  ${TchScorePrint.schoolTerm} 學期 平時及期末成績<br/>
					班級 : ${TchScorePrint.depClassName} ( ${TchScorePrint.departClass} )<br/>
					科目 : ${TchScorePrint.cscodeName} ( ${TchScorePrint.cscode} )<br/>
					<c:if test="${TchScorePrint.now == ''}">
					平時及期末成績上傳步驟尚未完成,本報表僅供參考!
					</c:if>
					<c:if test="${TchScorePrint.now != ''}">
					${TchScorePrint.teacherName}老師    請簽名:__________________ <br/>
					</c:if>
					</td>
					</tr>
					<tr>
						<td bgcolor=#add8e6 colspan="2" align="center"><font color=black>平時比例</font></td>
						<c:set var="rcount" value="0"/>
						<c:forEach items="${TchScoreRateArray}" var="rrate">
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
						<c:set var="stuNames" scope="page" value="${TchScorePrint.studentName}"/>
						<c:set var="scr01f" scope="page" value="${TchScorePrint.score01}"/>
						<c:set var="scr02f" scope="page" value="${TchScorePrint.score02}"/>
						<c:set var="scr03f" scope="page" value="${TchScorePrint.score03}"/>
						<c:set var="scr04f" scope="page" value="${TchScorePrint.score04}"/>
						<c:set var="scr05f" scope="page" value="${TchScorePrint.score05}"/>
						<c:set var="scr06f" scope="page" value="${TchScorePrint.score06}"/>
						<c:set var="scr07f" scope="page" value="${TchScorePrint.score07}"/>
						<c:set var="scr08f" scope="page" value="${TchScorePrint.score08}"/>
						<c:set var="scr09f" scope="page" value="${TchScorePrint.score09}"/>
						<c:set var="scr10f" scope="page" value="${TchScorePrint.score10}"/>
						<c:set var="scr1f" scope="page" value="${TchScorePrint.score1}"/>
						<c:set var="scr16f" scope="page" value="${TchScorePrint.score16}"/>
						<c:set var="scr2f" scope="page" value="${TchScorePrint.score2}"/>
						<c:set var="scr17f" scope="page" value="${TchScorePrint.score17}"/>
						<c:set var="scr3f" scope="page" value="${TchScorePrint.score3}"/>
						<c:set var="scr18f" scope="page" value="${TchScorePrint.score18}"/>
						<c:set var="scri" scope="page" value="${TchScorePrint.score}"/>
					
						<c:forEach items="${TchScorePrint.studentNo}" var="stuNos">
						<tr>
					        <td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuNos}</td>
					        <td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuNames[rcount]}</td>
					        <td valign="middle" bgcolor="#99FFFF">${scr01f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">${scr02f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">${scr03f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">${scr04f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">${scr05f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">${scr06f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">${scr07f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">${scr08f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">${scr09f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">${scr10f[rcount]}
					       </td>
					        <td valign="middle" bgcolor="#99FFFF">${scr1f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">${scr16f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">${scr2f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">${scr17f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">${scr3f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#FFD9D9">${scr18f[rcount]}
					        </td>
					        <td valign="middle" bgcolor="#99FFFF">${scri[rcount]}
					        </td>
					    </tr>
					    <c:set var="rcount" value="${rcount+1}"/>
					    </c:forEach>
					<tr>
					<td colspan=19>全班人數：${TchScorePrint.totalstu}
					&nbsp;&nbsp;&nbsp;全班平均：${TchScorePrint.avgscore}
					&nbsp;&nbsp;&nbsp;及格人數：${TchScorePrint.pass}
					&nbsp;&nbsp;&nbsp;不及格人數：${TchScorePrint.nopass}
					&nbsp;&nbsp;&nbsp;時間：${TchScorePrint.now}
					</td>
					</tr>
				</c:when>

				<c:when test="${TchScorePrint.scoretype == '9'}">
					<tr>
					<td colspan="19">
					中華科技大學 ${TchScorePrint.schoolYear} 學年第  ${TchScorePrint.schoolTerm} 學期 平時及期末成績<br/>
					班級 : ${TchScorePrint.depClassName} ( ${TchScorePrint.departClass} )<br/>
					科目 : ${TchScorePrint.cscodeName} ( ${TchScorePrint.cscode} )<br/>
					${TchScorePrint.teacherName}老師    請簽名:__________________ <br/>
					</td>
					</tr>
					<tr>
						<td bgcolor=#add8e6 colspan="2" align="center"><font color=black>平時比例</font></td>
						<c:set var="rcount" value="0"/>
						<c:forEach begin="1" end="10">
							<td bgcolor=#add8e6 width="10">&nbsp;</td>
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
					<c:set var="stuNames" scope="page" value="${TchScorePrint.studentName}"/>
				
					<c:forEach items="${TchScorePrint.studentNo}" var="stuNos">
					<tr>
				        <td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuNos}</td>
				        <td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuNames[rcount]}</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				        <td valign="middle" bgcolor="#FFD9D9">&nbsp;</td>
				        <td valign="middle" bgcolor="#99FFFF">&nbsp;</td>
				    </tr>
				    <c:set var="rcount" value="${rcount+1}"/>
				    </c:forEach>
				    <tr>
					<td colspan=19>全班人數：${TchScorePrint.totalstu}
					&nbsp;&nbsp;&nbsp;全班平均：&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;及格人數：&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;不及格人數：&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;時間：&nbsp;&nbsp;&nbsp;
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