<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {color: #0000FF}
.style3 {
	font-size: 24px;
	font-weight: bold;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>
<form action="/CIS/Teacher/ScoreFinalTermEdit.do" method="post" name="inputForm">
<input name="scoretype" type="hidden" value="${TchScoreUploadInfo.scoretype}"/>
<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
	<input name="yn" type="hidden" value="no"/>
</c:if>
<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
	<input name="yn" type="hidden" value="yes"/>
</c:if>

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('教師資訊系統');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${TchScoreInEdit != null or TchScoreUploadFormMap != null}"  >
	<tr>
		<td align="left">
		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
		<font color="red" size="4">請老師注意:平時成績至少應有四次<br></font>
		如要改變平時成績的比例,可以自行輸入,例如有五次平時成績,可以輸入<br>
		0.2, 0.2, 0.1, 0.3, 0.2, 如果沒有輸入比例,則將以總分除以次數計算平均<br>
		輸入時可以利用TAB鍵移到下一欄位或使用滑鼠<br>
		<font style="font-size:15px;color=#ff0000;">請注意:平時成績如為零分,一定要輸入0,不可留空白,空白表示該次平時成績不計算!<br></font>
		<br>
		</c:if>
		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
		老師如果要調整成績,請按底下的按鍵重新調整<br></c:if>
		</td>
	</tr>
	<tr>
		<td>
		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFCC" id="tb1">
		</c:if>
		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
			<table width="100%" border="1" cellpadding="0" cellspacing="0" bgcolor="#FFFFCC" id="tb1">
		</c:if>
      		<!--DWLayoutTable-->
      			<tr>
      			<td colspan="19">班別：<c:out value="${TchScoreUploadInfo.depClassName}"/> 
      			<c:out value="${TchScoreUploadInfo.departClass}"/>&nbsp;&nbsp;
      			科目：<c:out value="${TchScoreUploadInfo.cscodeName}"/>
      			<c:out value="${TchScoreUploadInfo.cscode}"/>&nbsp;&nbsp;
      			<c:if test="${TchScoreUploadInfo.scoretype == '1'}">
      				期中成績&nbsp;&nbsp;
      			</c:if>
      			<c:if test="${TchScoreUploadInfo.scoretype == '2'}">
      				期末成績&nbsp;&nbsp;
      			</c:if>
				<font color=blue>學生人數：</font>
				<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
					<input type="text" name="total" size="3" id="stu_total" readOnly="true" value="0">&nbsp;&nbsp;
				</c:if>
				<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
					${TchScoreUploadFormMap.total}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
				<font color=blue>及格人數：</font>
				<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
					<input type="text" name="pass" size="3" id="stu_pass" readOnly="true" value="0">&nbsp;&nbsp;
				</c:if>
				<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
					${TchScoreUploadFormMap.pass}
				</c:if>
				<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
					<c:if test="${TchScoreUploadFormMap != null}">&nbsp;&nbsp;表格重填</c:if>
				</c:if>
      			</td>
        		</tr>
				<tr>
					<td bgcolor=#add8e6 colspan="2" align="center"><font color=black>平時比例</font></td>
				<c:set var="count" value="1"/>
				<c:if test="${TchScoreUploadFormMap != null}">
					<c:forEach items="${TchScoreUploadFormMap.scorerate}" var="rrate">
						<c:if test="${(count % 2) != 0 }">
							<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
								<td bgcolor=#add8e6 width="10"><input name="scorerate" type="text" size="1" maxlength="4" value="${rrate}" onchange="if(checknum(this)) checkrate(); else this.focus();"></td>
							</c:if>
							<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
								<td bgcolor=#add8e6 width="10">${rrate}</td>
							</c:if>
						</c:if>
						<c:if test="${(count % 2) == 0 }">
							<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
								<td bgcolor=#add8e6 width="10"><input name="scorerate" type="text" size="1" maxlength="4" value="${rrate}" onchange="if(checknum(this)) checkrate(); else this.focus();"></td>
							</c:if>
							<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
								<td bgcolor=#add8e6 width="10">${rrate}</td>
							</c:if>
						</c:if>
						<c:set var="count" value="${count + 1}"/>
					</c:forEach>
				</c:if>
				<c:if test="${TchScoreRateArray != null && TchScoreUploadFormMap == null}">
					<c:forEach items="${TchScoreRateArray}" var="rrate">
						<c:if test="${(count % 2) != 0 }">
							<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
								<td bgcolor=#add8e6 width="10"><input name="scorerate" type="text" size="1" maxlength="4" value="${rrate}" onchange="if(checknum(this)) checkrate(); else this.focus();"></td>
							</c:if>
							<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
								<td bgcolor=#add8e6 width="10">${rrate}</td>
							</c:if>
						</c:if>
						<c:if test="${(count % 2) == 0 }">
							<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
								<td bgcolor=#add8e6 width="10"><input name="scorerate" type="text" size="1" maxlength="4" value="${rrate}" onchange="if(checknum(this)) checkrate(); else this.focus();"></td>
							</c:if>
							<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
								<td bgcolor=#add8e6 width="10">${rrate}</td>
							</c:if>
						</c:if>
						<c:set var="count" value="${count + 1}"/>
					</c:forEach>
				</c:if>
				<c:if test="${TchScoreUploadFormMap == null && TchScoreRateArray == null}">
					<c:forEach begin="0" end="9">
						<c:if test="${(count % 2) != 0 }">
							<td bgcolor=#add8e6 width="10"><input name="scorerate" type="text" size="1" maxlength="4" value="0.0" onchange="if(checknum(this)) checkrate(); else this.focus();"></td>
						</c:if>
						<c:if test="${(count % 2) == 0 }">
							<td bgcolor=#add8e6 width="10"><input name="scorerate" type="text" size="1" maxlength="4" value="0.0" onchange="if(checknum(this)) checkrate(); else this.focus();"></td>
						</c:if>
						<c:set var="count" value="${count + 1}"/>
					</c:forEach>
				</c:if>
				
					<td bgcolor=#add8e6>&nbsp;</td>
					<td bgcolor=#add8e6>&nbsp;</td>
					<td bgcolor=#add8e6>&nbsp;</td>
					<td bgcolor=#add8e6>&nbsp;</td>
					<td bgcolor=#add8e6>&nbsp;</td>
					<td bgcolor=#add8e6>&nbsp;</td>
					<td bgcolor=#add8e6>&nbsp;</td>
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
		  <!-- <script language="JavaScript">generateScoreInput(scrinput);</script> -->
		  
		  <c:set var="rcount" scope="page" value="0"/>
		  
	<c:choose>
	<c:when test="${TchScoreUploadFormMap != null}">

          <c:forEach items="${TchScoreInEdit}" var="stuscore">
          	<tr>
          	<td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
        	<td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuscore.studentName}</td>
        	
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr01" value="${TchScoreUploadFormMap.scr01[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr01[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr02" value="${TchScoreUploadFormMap.scr02[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr02[rcount]}
				</c:if>
         	</td>
         	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr03" value="${TchScoreUploadFormMap.scr03[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
 				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr03[rcount]}
 				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr04" value="${TchScoreUploadFormMap.scr04[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr04[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr05" value="${TchScoreUploadFormMap.scr05[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr05[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr06" value="${TchScoreUploadFormMap.scr06[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
 				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr06[rcount]}
 				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr07" value="${TchScoreUploadFormMap.scr07[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
 				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr07[rcount]}
 				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr08" value="${TchScoreUploadFormMap.scr08[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr08[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr09" value="${TchScoreUploadFormMap.scr09[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr10" value="${TchScoreUploadFormMap.scr10[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr10[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr16" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr16[rcount]}" readOnly="true"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr16[rcount]}
				</c:if>
         	</td>
           	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr17" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr17[rcount]}" readOnly="true"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr17[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">${TchScoreUploadFormMap.scr19[rcount]}
          			<input name="scr19" type="hidden" value="${TchScoreUploadFormMap.scr19[rcount]}"/>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">${TchScoreUploadFormMap.scr20[rcount]}
          			<input name="scr20" type="hidden" value="${TchScoreUploadFormMap.scr20[rcount]}"/>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr21" value="${TchScoreUploadFormMap.scr21[rcount]}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr21[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr22" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr22[rcount]}" readOnly="true"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			${TchScoreUploadFormMap.scr22[rcount]}
				</c:if>
        	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<input name="scr23" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr23[rcount]}" readOnly="true"/>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${TchScoreUploadFormMap.scr23[rcount] < 60}">
          				<font color="red">${TchScoreUploadFormMap.scr23[rcount]}&nbsp;*</font>
          			</c:if>
          			<c:if test="${TchScoreUploadFormMap.scr23[rcount] >= 60}">
          				${TchScoreUploadFormMap.scr23[rcount]}
          			</c:if>
				</c:if>
         	</td>
        	</tr>
          	<c:set var="rcount" scope="page" value="${rcount+1}"/>
          </c:forEach>
	</c:when>
	<c:when test="${TchScoreInEdit != null}">
          <c:forEach items="${TchScoreInEdit}" var="stuscore">
          	<tr>
          	<td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
        		<td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuscore.studentName}</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score01 == ''}">
          				<input name="scr01" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this.value)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score01 != ''}">
          				<input name="scr01" value="${stuscore.score01}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score01 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score01 != ''}">${stuscore.score01}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score02 == ''}">
          				<input name="scr02" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score02 != ''}">
          				<input name="scr02" value="${stuscore.score02}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score02 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score02 != ''}">${stuscore.score02}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score03 == ''}">
          				<input name="scr03" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score03 != ''}">
          				<input name="scr03" value="${stuscore.score03}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score03 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score03 != ''}">${stuscore.score03}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score04 == ''}">
          				<input name="scr04" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score04 != ''}">
          				<input name="scr04" value="${stuscore.score04}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score04 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score04 != ''}">${stuscore.score04}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score05 == ''}">
          				<input name="scr05" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score05 != ''}">
          				<input name="scr05" value="${stuscore.score05}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score05 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score05 != ''}">${stuscore.score05}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score06 == ''}">
          				<input name="scr06" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score06 != ''}">
          				<input name="scr06" value="${stuscore.score06}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score06 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score06 != ''}">${stuscore.score06}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score07 == ''}">
          				<input name="scr07" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score07 != ''}">
          				<input name="scr07" value="${stuscore.score07}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score07 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score07 != ''}">${stuscore.score07}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score08 == ''}">
          				<input name="scr08" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score08 != ''}">
          				<input name="scr08" value="${stuscore.score08}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score08 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score08 != ''}">${stuscore.score08}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score09 == ''}">
          				<input name="scr09" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score09 != ''}">
          				<input name="scr09" value="${stuscore.score09}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score09 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score09 != ''}">${stuscore.score09}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score10 == ''}">
          				<input name="scr10" value="0.0" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
          			<c:if test="${stuscore.score10 != ''}">
          				<input name="scr10" value="${stuscore.score10}" type="text" size="1" maxlength="5" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score10 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score10 != ''}">${stuscore.score10}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score16 == ''}">
          				<input name="scr16" size="1" maxlength="5" type="text" value="0.0" readOnly="true"/>
					</c:if>
          			<c:if test="${stuscore.score16 != ''}">
          				<input name="scr16" size="1" maxlength="5" type="text" value="${stuscore.score16}" readOnly="true"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score16 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score16 != ''}">${stuscore.score16}</c:if>
				</c:if>
         		</td>
           		<td valign="middle" bgcolor="#FFD9D9">          		
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
           			<c:if test="${stuscore.score17 == ''}">
           				<input name="scr17" size="1" maxlength="5" type="text" value="0.0" readOnly="true"/>
					</c:if>
           			<c:if test="${stuscore.score17 != ''}">
           				<input name="scr17" size="1" maxlength="5" type="text" value="${stuscore.score17}" readOnly="true"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score17 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score17 != ''}">${stuscore.score17}</c:if>
				</c:if>
           		</td>
          		<td valign="middle" bgcolor="#99FFFF">          		
          		<c:if test="${stuscore.score19 == ''}">0.0
          			<input name="scr19" type="hidden" value="0.0"/>
				</c:if>
           		<c:if test="${stuscore.score19 != ''}">${stuscore.score19}
          			<input name="scr19" type="hidden" value="${stuscore.score19}"/>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">          		
          		<c:if test="${stuscore.score20 == ''}">0.0
          			<input name="scr20" size="1" maxlength="5" type="hidden" value="0.0"/>
				</c:if>
          		<c:if test="${stuscore.score20 != ''}">${stuscore.score20}
          			<input name="scr20" type="hidden" value="${stuscore.score20}"/>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score21 == ''}">
          				<input name="scr21" type="text" size="1" maxlength="5" value="0.0" onChange="if (checknum(this)) avgcal(${rcount});"/>
					</c:if>
          			<c:if test="${stuscore.score21 != ''}">
          				<input name="scr21" type="text" size="1" maxlength="5" value="${stuscore.score21}" onChange="if (checknum(this)) avgcal(${rcount});"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score21 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score21 != ''}">${stuscore.score21}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">          		
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score22 == ''}">
          				<input name="scr22" size="1" maxlength="5" type="text" value="0.0" readOnly="true"/>
					</c:if>
          			<c:if test="${stuscore.score22 != ''}">
          				<input name="scr22" size="1" maxlength="5" type="text" value="${stuscore.score22}" readOnly="true"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score22 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score22 != ''}">${stuscore.score22}</c:if>
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">          		
          		<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
          			<c:if test="${stuscore.score23 == ''}">
          				<input name="scr23" size="1" maxlength="5" type="text" value="0.0" readOnly="true"/>
					</c:if>
          			<c:if test="${stuscore.score23 != ''}">
          				<input name="scr23" size="1" maxlength="5" type="text" value="${stuscore.score23}" readOnly="true"/>
					</c:if>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
          			<c:if test="${stuscore.score23 == ''}">0.0</c:if>
          			<c:if test="${stuscore.score23 != ''}">${stuscore.score23}</c:if>
				</c:if>
         		</td>
        	</tr>
          	<c:set var="rcount" scope="page" value="${rcount+1}"/>
          </c:forEach>
	</c:when>
	</c:choose>
	
          </table>
	   </td>
	</tr>
	
	<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='SaveTemp' bundle='TCH' />" onClick="return checkrate2();" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='TCH'/>" >');
	</script>
	</c:if>
	<c:if test="${TchScoreUploadInfo.opmode == 'saveseld' && TchScoreUploadInfo.canUpload}">	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='BackSaveTemp' bundle='TCH'/>" >' + 
	'<INPUT type="submit" name="method" value="<bean:message key='SaveSeld' bundle='TCH' />">&nbsp;&nbsp;');
	</script>
	</c:if>
	<c:if test="${TchScoreUploadInfo.opmode == 'saveseld' && !TchScoreUploadInfo.canUpload}">	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='BackSaveTemp' bundle='TCH'/>" >' + 
	'<INPUT type="submit" name="method" value="<bean:message key='BackMenu' bundle='TCH' />">&nbsp;&nbsp;');
	</script>
	</c:if>
		
 	
</c:if>
	
<!-- Begin Content Page Table Footer -->
				
</table>
<!-- End Content Page Table Footer -->
<script type="text/javascript">
<!--
var myTimeOut = globalTimeOut;
globalTimeOut = 7200000;
var ncount = 0;
var hadtune = 0;
var stu_total = 0;
var stu_pass = 0;

function checknum(obj) {
	data = obj.value.match(/^\d{1}|^\d{2}|^\d{1}\.\d{1}|^\d{2}\.\d{1}|^100[\.0]/);
	if(isNaN(obj.value)) {
		alert("欄位資料須為數字!");
		obj.value = "0.0";
		obj.focus();
		return false;
	} else if(obj.value > 100) {
		alert("分數不可大於100分!");
		obj.value = "";
		obj.focus();
		return false;
	}
	return true;
};

function checkrate() {
	var objs = document.getElementsByName("scorerate");
	var total_rate = 0;
	
	for (i=0; i< objs.length-1; i++) {
		total_rate = total_rate + parseFloat(objs[i].value);
		if(total_rate > 1) {
			alert("平時成績比例之總合不可大於1");
			objs[i].value = "0.0";
			objs[i].focus();
			return false;
		}
	}
	
	if(total_rate == 1 || total_rate == 0) {
		recalavg(total_rate, -1);
	}
	
	return true;
};

function checkrate2() {
	var objs = document.getElementsByName("scorerate");
	var total_rate = 0;
	
	for (i=0; i< objs.length-1; i++) {
		total_rate = total_rate + parseFloat(objs[i].value);
		if(total_rate > 1) {
			alert("平時成績比例之總合不可大於1");
			objs[i].value = "0.0";
			objs[i].focus();
			return false;
		}
	}
	
	if(total_rate == 1 || total_rate == 0) {
		recalavg(total_rate, -1);
	} else {
		alert("平時成績比例之總合應等於1或0");
		return false;
	}
	
	return true;
};

function recalavg(trate, rcount) {
	// alert("recalculate score !!!");
	var allrate0 = 1;
	var total_rate = 0;
	var kount = 0;
	var mybegin = 0;
	var myend = 0;
	
	var total_n = 0;
	var percent_n = 0;
	var percent_m = 0;
	var percent_f = 0;
	var score_end = 0;
	var old_score_end = 0;
	var scrlength = 0;
	
	var ips01 = document.getElementsByName("scr01");
	var ips02 = document.getElementsByName("scr02");
	var ips03 = document.getElementsByName("scr03");
	var ips04 = document.getElementsByName("scr04");
	var ips05 = document.getElementsByName("scr05");
	var ips06 = document.getElementsByName("scr06");
	var ips07 = document.getElementsByName("scr07");
	var ips08 = document.getElementsByName("scr08");
	var ips09 = document.getElementsByName("scr09");
	var ips10 = document.getElementsByName("scr10");
	var ips16 = document.getElementsByName("scr16");
	var ips17 = document.getElementsByName("scr17");
	var ips19 = document.getElementsByName("scr19");
	var ips20 = document.getElementsByName("scr20");
	var ips21 = document.getElementsByName("scr21");
	var ips22 = document.getElementsByName("scr22");
	var ips23 = document.getElementsByName("scr23");
	var ipsrate = document.getElementsByName("scorerate");
	var ipstotal = document.getElementById("stu_total");
	var ipspass = document.getElementById("stu_pass");
	
	ipstotal.value = "" + ips01.length;

	// 平時成績次數
	for(z=0; z<ips01.length; z++){
		kount = 0;
		if(ips01[z].value != "") kount++;
		if(ips02[z].value != "") kount++;
		if(ips03[z].value != "") kount++;
		if(ips04[z].value != "") kount++;
		if(ips05[z].value != "") kount++;
		if(ips06[z].value != "") kount++;
		if(ips07[z].value != "") kount++;
		if(ips08[z].value != "") kount++;
		if(ips09[z].value != "") kount++;
		if(ips10[z].value != "") kount++;
		if(kount > ncount) ncount=kount;
	}
	
	// 傳入的trate不等於1或0則重新計算
	trate = Math.floor(trate);
	if(trate != 1 && trate != 0) {
		for(i=0; i<10; i++) {
			if(parseFloat(ipsrate[i].value) > 0) {
				allrate0 = 0;
				total_rate = total_rate + parseFloat(ipsrate[i].value);
			}
			else if(parseFloat(ipsrate[i].value) == 0) {
				if(!allrate0 == 0)allrate0 = 1;
			}
		}
		// alert("trate:" + trate);
	
		if(allrate0 == 0 && total_rate != 1) {
			alert("[平時成績比例之總合必須等於1]");
			return false;
		}
	} else {
		if(trate == 0) {
			allrate0 = 1;
		} else if (trate == 1) {
			allrate0 = 0;
		}
	}
		
	// rcount == -1 recalculate all record
	if(rcount == -1) {
		mybegin = 0;
		myend = ips01.length - 1;
		stu_total = ips01.length;
		stu_pass = 0;
	} else {
		mybegin = rcount;
		myend = mybegin;
	}
	
	var nscore = new Array(10);
	var isScoreLoss = 0;
	// alert("begin:" + mybegin + " , end:" + myend + " trate type:" + trate +" allrate0:" + allrate0);
	for(k=mybegin; k <= myend; k++) {
		total_n = 0.0;
		percent_n = 0.0;
		percent_f = 0.0;
		score_end = 0.0;
		kount = 0;
		
		if(ips20[k].value != "")
			percent_m = parseFloat(ips20[k].value);
		else 
			percent_m = 0.0;
		nscore[0] = ips01[k].value;
		nscore[1] = ips02[k].value;
		nscore[2] = ips03[k].value;
		nscore[3] = ips04[k].value;
		nscore[4] = ips05[k].value;
		nscore[5] = ips06[k].value;
		nscore[6] = ips07[k].value;
		nscore[7] = ips08[k].value;
		nscore[8] = ips09[k].value;
		nscore[9] = ips10[k].value;

		for(i=0; i<10; i++) {
			if(allrate0 == 1){
				if (nscore[i] != ""){
					total_n = total_n + parseFloat(nscore[i]);
					kount++;
				}
			}else if(ipsrate[i].value != "0.0" && nscore[i] != "") {
				total_n = total_n + parseFloat(nscore[i] * parseFloat(ipsrate[i].value));
			}
		}
	
		if(kount != ncount){
			isScoreLoss = 1;
		}
		
		if(total_n == 0 || kount == 0) {
			percent_n = 0.0;
		} else {
			if(allrate0 == 1) total_n = total_n / kount;
		}
	
		// 取小數點一位並四捨五入
		total_n = Math.round(total_n * 10) / 10;
		percent_n = Math.round(total_n * 3) / 10;
	
		ips16[k].value = "" + total_n;
		ips17[k].value = "" + percent_n;
	
		if(ips21[k].value != "") {
			percent_f = Math.round(parseFloat(ips21[k].value) * 4) / 10;
			ips22[k].value = "" + percent_f;
		} else {
			ips22[k].value = "";
		}
		
		old_score_end = parseFloat(ips23[k].value);
		score_end = Math.round(percent_n + percent_m + percent_f);
		//if(score_end < 60) ips23[k].value = score_end + " *";
		//else ips23[k].value = "" + score_end;
		ips23[k].value = "" + score_end;
		
		if(score_end >= 60){
			ips23[k].className = "style1";
		} else if (score_end < 60) {
			ips23[k].className = "style2";
		}
		
		if(rcount != -1) {
			if(score_end >= 60 && old_score_end < 60) stu_pass = parseInt(ipspass.value) + 1;
			else if(score_end < 60 && old_score_end >= 60) stu_pass = parseInt(ipspass.value) - 1;
		}
		else if(rcount == -1) {
			if(score_end >= 60) stu_pass = stu_pass + 1;
		}
	}
	if(rcount == -1 && isScoreLoss == 1){
		alert("輸入之平時成績有欄位是空白的未輸入成績\n請注意如成績為零分一定要輸入0!");
	}
	ipspass.value = "" + stu_pass;
	return null;
};

function avgcal(rcount) {
	recalavg(2, rcount);	
};

/* function chgFocus() 
{
	k=event.keyCode;
	
};
*/
function inputtune(mytagname, mylength) {
	var objs = document.getElementsByName(mytagname);
	if(isNaN(mylength)) {
		alert("inputtune->Input elements length must be a Integer!");
		return;
	}
	if(objs.length == 0) {
		alert("inputtune->no inputs name:" + mytagname + "found!");
		return;
	}
	for(i=0; i < objs.length; i++)
	{
		objs[i].size = mylength;
	}
};

function optimize(recal) {
	if(navigator.appName.toLowerCase() == "netscape") {
		inputtune("scorerate", 3);
		inputtune("scr01", 3);
		inputtune("scr02", 3);
		inputtune("scr03", 3);
		inputtune("scr04", 3);
		inputtune("scr05", 3);
		inputtune("scr06", 3);
		inputtune("scr07", 3);
		inputtune("scr08", 3);
		inputtune("scr09", 3);
		inputtune("scr10", 3);
		inputtune("scr16", 3);
		inputtune("scr17", 3);
		inputtune("scr21", 3);
		inputtune("scr22", 3);
		inputtune("scr23", 3);
	}
	if(recal==1) recalavg(2, -1);
};

function d2int(tagName)
{
	var ips = document.getElementsByName(tagName);

  	for(i=0; i<=ips.length-1;i++)
  	{
  		if(ips[i].value != "")
      		ips[i].value=Math.round(ips[i].value);
  	}

};

//-->
</script>

<script>

<c:if test="${TchScoreUploadInfo.opmode == 'saveregs'}">
optimize(1);
</c:if>

<c:if test="${TchScoreUploadInfo.opmode == 'saveseld'}">
optimize(0);
</c:if>

history.go(1);</script>
</form>
