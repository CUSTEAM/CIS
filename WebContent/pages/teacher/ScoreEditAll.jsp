<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<c:if test="${TchScoreUploadInfo.methodTransfer != null}">
<script language="javaScript">
<!-- 
function methodAlert() {
	alert("${TchScoreUploadInfo.methodTransfer}");
};

window.onload=methodAlert;
// -->
</script>
</c:if>


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
<form action="/CIS/Teacher/ScoreEditAll.do" method="post" name="inputForm">
<input name="scoretype" type="hidden" value="${TchScoreUploadInfo.scoretype}"/>
<input name="openMode" type="hidden" value="${TchScoreUploadInfo.openMode}"/>
<input name="dtimeoid" type="hidden" value="${TchScoreUploadInfo.Dtime_oid}"/>

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('教師資訊系統');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${TchScoreInEdit != null or TchScoreUploadFormMap != null}"  >
	  <c:if test="${TchScoreUploadInfo.openMode == '101' or TchScoreUploadInfo.openMode == '001'}">
		<tr>
			<td align="left">
			<font color="red" size="4">請老師注意:平時成績至少應有四次<br></font>
			如要改變平時成績的比例,可以自行輸入,例如有五次平時成績,可以輸入<br>
			0.2, 0.2, 0.1, 0.3, 0.2, 如果沒有輸入比例,則將以總分除以次數計算平均<br>
			輸入時可以利用TAB鍵移到下一欄位或使用滑鼠<br>
			<font style="font-size:15px;color=#ff0000;">請注意:平時成績如為零分,一定要輸入0,不可留空白,空白表示該次平時成績不計算!<br></font>
			<br>
			</td>
		</tr>
	  </c:if>
	<tr>
		<td>
			<table width="100%" border="1" cellpadding="0" cellspacing="0" bgcolor="#FFFFCC" id="tb1">
      		<!--DWLayoutTable-->
      			<tr>
      			<td colspan="19">班別：<c:out value="${TchScoreUploadInfo.depClassName}"/> 
      			<c:out value="${TchScoreUploadInfo.departClass}"/>&nbsp;&nbsp;
      			科目：<c:out value="${TchScoreUploadInfo.cscodeName}"/>
      			<c:out value="${TchScoreUploadInfo.cscode}"/>&nbsp;&nbsp;
      			<c:if test="${TchScoreUploadInfo.openMode == '011'}">
      				期中成績&nbsp;&nbsp;
      			</c:if>
      			<c:if test="${TchScoreUploadInfo.openMode == '101'}">
      				期末成績&nbsp;&nbsp;
      			</c:if>
      			<c:if test="${TchScoreUploadInfo.openMode == '000'}">
      				學期成績&nbsp;&nbsp;
      			</c:if>
      			
				<font color=blue>學生人數：</font>
					<input type="text" name="total" size="3" id="stu_total" readOnly="true" value="0">&nbsp;&nbsp;
				<font color=blue>及格人數：</font>
					<input type="text" name="pass" size="3" id="stu_pass" readOnly="true" value="0">&nbsp;&nbsp;
					<c:if test="${TchScoreUploadFormMap != null}">&nbsp;&nbsp;表格重填</c:if>
      			</td>
        		</tr>
        		
 				<!-- score rate processing -->
        		<!-- nmode: 0:only display rrate , 1:Input rrate -->
				<c:if test="${TchScoreUploadInfo.openMode != '000'}">
					<c:set var="nmode" scope="page" value="${fn:substring(TchScoreUploadInfo.openMode,2,3)}"/>
				</c:if>
				<c:if test="${TchScoreUploadInfo.openMode == '000'}">
					<c:set var="nmode" scope="page" value="0"/>
				</c:if>
				
				<tr>
					<td bgcolor=#add8e6 colspan="2" align="center"><font color=black>成績比例</font></td>
					<c:set var="count" value="1"/>
					
					<c:choose>
					<c:when test="${TchScoreUploadFormMap != null}">
						<c:set var="rates" value="${TchScoreUploadFormMap.scorerate}"/>
					</c:when>
					<c:when test="${TchScoreRateArray != null}">
						<c:set var="rates" value="${TchScoreRateArray}"/>
					</c:when>
					<c:otherwise>
						<c:set var="rates" value="${defaultRate}"/>
					</c:otherwise>
					</c:choose>
					
					<c:forEach items="${rates}" var="rrate">
						<c:if test="${nmode == '1'}">
								<td bgcolor=#add8e6 width="10" align="center">
								<c:choose>
									<c:when test="${count<=10}">
										<input name="scorerate" id="rate_${count}" type="text" size="1" maxlength="4" value="${rrate}" onKeyUp="nextfocus(this);" onchange="if(checknum(this)) checkrate(); else this.focus();">
									</c:when>
									<c:when test="${count>10}">
										<c:if test="${TchScoreRateUpdatable=='1'}">
											<select name="scorerate">
											<c:forEach begin="2" end="6" var="myrate">
											<c:set var="trate" value="${myrate/10}"/>
											<c:if test="${trate == rrate}">
												<option value="${trate}" selected>${trate}</option>
											</c:if>
											<c:if test="${trate != rrate}">
												<option value="${trate}">${trate}</option>
											</c:if>
											</c:forEach>
											</select>
										</c:if>
										<c:if test="${TchScoreRateUpdatable=='0'}">
											${rrate}
											<input name="scorerate" type="hidden" value="${rrate}">
										</c:if>
									</c:when>
								</c:choose>
								</td>
						</c:if>
						<c:if test="${nmode == '0'}">
							<c:if test="${(count % 2) != 0 }">
								<td bgcolor=#add8e6 width="10">${rrate}
								<input name="scorerate" type="hidden" value="${rrate}"></td>
							</c:if>
							<c:if test="${(count % 2) == 0 }">
								<td bgcolor=#add8e6 width="10">${rrate}
								<input name="scorerate" type="hidden" value="${rrate}"></td>
							</c:if>
						</c:if>

						<!-- for normal,middle,final exam score ratio setting mode -->
						<c:if test="${count >= 10}">
							<td bgcolor="#add8e6" width="10">&nbsp;</td>
							<c:set var="count" value="${count + 1}"/>
						</c:if>
						
						<c:set var="count" value="${count + 1}"/>
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
					<td bgcolor=#FFD9D9 align="center"><font color=black>平時<br />比例</font></td>
					<td bgcolor=#99FFFF align="center"><font color=black>期中<br />成績</font></td>
					<td bgcolor=#FFD9D9 align="center"><font color=black>期中<br />比例</font></td>
					<td bgcolor=#99FFFF align="center"><font color=black>期末<br />成績</font></td>
					<td bgcolor=#FFD9D9 align="center"><font color=black>期末<br />比例</font></td>
					<td bgcolor=#FFFF99 align="center"><font color=black>學期<br />成績</font></td>
				</tr>
		  <!-- <script language="JavaScript">generateScoreInput(scrinput);</script> -->
		  
		  <c:set var="rcount" scope="page" value="0"/>
		  <c:set var="cols" scope="page" value="0"/>
		  
	<c:choose>
	<c:when test="${TchScoreUploadFormMap != null}">
          <c:forEach items="${TchScoreInEdit}" var="stuscore">
          	<tr>
          	<td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
        	<td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuscore.studentName}</td>
        	
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          			<input name="scr01" id="scr01_${rcount}" value="${TchScoreUploadFormMap.scr01[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			${TchScoreUploadFormMap.scr01[rcount]}
          			<input name="scr01" value="${TchScoreUploadFormMap.scr01[rcount]}" type="hidden"/>
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          			<input name="scr02" id="scr02_${rcount}" value="${TchScoreUploadFormMap.scr02[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			${TchScoreUploadFormMap.scr02[rcount]}
          			<input name="scr02" value="${TchScoreUploadFormMap.scr02[rcount]}" type="hidden"/>
				</c:if>
         	</td>
         	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          			<input name="scr03"  id="scr03_${rcount}" value="${TchScoreUploadFormMap.scr03[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
 				</c:if>
          		<c:if test="${nmode == '0'}">
          			${TchScoreUploadFormMap.scr03[rcount]}
          			<input name="scr03" value="${TchScoreUploadFormMap.scr03[rcount]}" type="hidden"/>
 				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
           		<c:if test="${nmode == '1'}">
          			<input name="scr04" id="scr04_${rcount}" value="${TchScoreUploadFormMap.scr04[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
          			${TchScoreUploadFormMap.scr04[rcount]}
          			<input name="scr04" value="${TchScoreUploadFormMap.scr04[rcount]}" type="hidden"/>
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          			<input name="scr05" id="scr05_${rcount}" value="${TchScoreUploadFormMap.scr05[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			${TchScoreUploadFormMap.scr05[rcount]}
          			<input name="scr05" value="${TchScoreUploadFormMap.scr05[rcount]}" type="hidden"/>
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          			<input name="scr06" id="scr06_${rcount}" value="${TchScoreUploadFormMap.scr06[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
 				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr06" value="${TchScoreUploadFormMap.scr06[rcount]}" type="hidden"/>
          			${TchScoreUploadFormMap.scr06[rcount]}
 				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          			<input name="scr07" id="scr07_${rcount}" value="${TchScoreUploadFormMap.scr07[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
 				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr07" value="${TchScoreUploadFormMap.scr07[rcount]}" type="hidden"/>
          			${TchScoreUploadFormMap.scr07[rcount]}
 				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          			<input name="scr08" id="scr08_${rcount}" value="${TchScoreUploadFormMap.scr08[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr08" value="${TchScoreUploadFormMap.scr08[rcount]}" type="hidden"/>
          			${TchScoreUploadFormMap.scr08[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          			<input name="scr09" id="scr09_${rcount}" value="${TchScoreUploadFormMap.scr09[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr09" value="${TchScoreUploadFormMap.scr09[rcount]}" type="hidden"/>
          			${TchScoreUploadFormMap.scr09[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          			<input name="scr10" id="scr10_${rcount}" value="${TchScoreUploadFormMap.scr10[rcount]}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr10" value="${TchScoreUploadFormMap.scr10[rcount]}" type="hidden"/>
          			${TchScoreUploadFormMap.scr10[rcount]}
				</c:if>
         	</td>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          			<input name="scr1" id="scr1_${rcount}" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr1[rcount]}"  onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr1" value="${TchScoreUploadFormMap.scr1[rcount]}" type="hidden"/>
          			${TchScoreUploadFormMap.scr1[rcount]}
				</c:if>
         	</td>
           	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          			<input name="scr16" id="scr16_${rcount}" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr16[rcount]}" readOnly="true"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr16" value="${TchScoreUploadFormMap.scr16[rcount]}" type="hidden"/>
          			${TchScoreUploadFormMap.scr16[rcount]}
				</c:if>
         	</td>
         	<c:set var="mmode" scope="page" value="${fn:substring(TchScoreUploadInfo.openMode,1,2)}"/>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${mmode == '1'}">
          			<input name="scr2" id="scr2_${rcount}" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr2[rcount]}" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${mmode == '0'}">
          			<input name="scr2" value="${TchScoreUploadFormMap.scr2[rcount]}" type="hidden"/>
          			<div name="scr2v">${TchScoreUploadFormMap.scr2[rcount]}</div>
				</c:if>
         	</td>
          		<c:if test="${mmode == '1'}">
           			<td id="scr17v${rcount}" valign="middle" bgcolor="#FFD9D9">
          			<input name="scr17" id="scr17_${rcount}" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr17[rcount]}" readOnly="true"/>
					</td>
				</c:if>
          		<c:if test="${mmode == '0'}">
          			<input name="scr17" value="${TchScoreUploadFormMap.scr17[rcount]}" type="hidden"/>
          			<td id="scr17v${rcount}" valign="middle" bgcolor="#FFD9D9">
          			${TchScoreUploadFormMap.scr17[rcount]}
          			</td>
				</c:if>
         	<c:set var="fmode" scope="page" value="${fn:substring(TchScoreUploadInfo.openMode,0,1)}"/>
          	<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${fmode == '1'}">
          			<input name="scr3" id="scr3_${rcount}" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr3[rcount]}" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${fmode == '0'}">${TchScoreUploadFormMap.scr3[rcount]}
          			<input name="scr3" value="${TchScoreUploadFormMap.scr3[rcount]}" type="hidden"/>
				</c:if>
         	</td>
           	<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${fmode == '1'}">
          			<input name="scr18" id="scr18_${rcount}" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr18[rcount]}" readOnly="true"/>
				</c:if>
          		<c:if test="${fmode == '0'}">${TchScoreUploadFormMap.scr18[rcount]}
          			<input name="scr18" value="${TchScoreUploadFormMap.scr18[rcount]}" type="hidden"/>
				</c:if>
         	</td>
          		<c:if test="${TchScoreUploadInfo.openMode == '000'}">
          			<td id="scrv${rcount}" valign="middle" bgcolor="#99FFFF">
          			<input name="scr" id="scr_${rcount}" type="text" size="1" maxlength="5" value="${TchScoreUploadFormMap.scr[rcount]}" onKeyUp="nextfocus(this);" onChange="checknum(this);"/>
					</td>
				</c:if>
          		<c:if test="${TchScoreUploadInfo.openMode != '000'}">
          			<input name="scr" type="hidden" value="${TchScoreUploadFormMap.scr[rcount]}"/>
          			<td id="scrv${rcount}" valign="middle" bgcolor="#99FFFF">
          			${TchScoreUploadFormMap.scr[rcount]}
          			</td>
				</c:if>
        	</tr>
          	<c:set var="rcount" scope="page" value="${rcount+1}"/>
          	<c:if test="${(rcount % 5) == 0}">
          	<tr height="5"><td colspan="19" bgcolor="red"></td></tr>
          	</c:if>
          </c:forEach>
	</c:when>
	<c:when test="${TchScoreInEdit != null}">
		<c:if test="${TchScoreUploadInfo.openMode != '000'}">
			<c:set var="nmode" scope="page" value="${fn:substring(TchScoreUploadInfo.openMode,2,3)}"/>
		</c:if>
		<c:if test="${TchScoreUploadInfo.openMode == '000'}">
			<c:set var="nmode" scope="page" value="0"/>
		</c:if>
        <c:set var="mmode" scope="page" value="${fn:substring(TchScoreUploadInfo.openMode,1,2)}"/>
        <c:set var="fmode" scope="page" value="${fn:substring(TchScoreUploadInfo.openMode,0,1)}"/>
        
          <c:forEach items="${TchScoreInEdit}" var="stuscore">
          	<tr>
          	<td align="left" valign="middle" bgcolor="#99FFFF" width="60">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
        		<td align="left" valign="middle" bgcolor="#FFD9D9" width="50">${stuscore.studentName}</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          				<input name="scr01" id="scr01_${rcount}" value="${stuscore.score01}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr01" type="hidden" value="${stuscore.score01}">
          			${stuscore.score01}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          				<input name="scr02" id="scr02_${rcount}" value="${stuscore.score02}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr02" type="hidden" value="${stuscore.score02}">
          			${stuscore.score02}
 				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          				<input name="scr03" id="scr03_${rcount}" value="${stuscore.score03}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          		    <input name="scr03" type="hidden" value="${stuscore.score03}">
          			${stuscore.score03}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          				<input name="scr04" id="scr04_${rcount}" value="${stuscore.score04}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
          		<c:if test="${nmode == '0'}">
          			<input name="scr04" type="hidden" value="${stuscore.score04}">
         			${stuscore.score04}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
           		<c:if test="${nmode == '1'}">
          				<input name="scr05" id="scr05_${rcount}" value="${stuscore.score05}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
          			<input name="scr05" type="hidden" value="${stuscore.score05}">
          			${stuscore.score05}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          				<input name="scr06" id="scr06_${rcount}" value="${stuscore.score06}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
          			<input name="scr06" type="hidden" value="${stuscore.score06}">
          			${stuscore.score06}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          				<input name="scr07" id="scr07_${rcount}" value="${stuscore.score07}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
          			<input name="scr07" type="hidden" value="${stuscore.score07}">
          			${stuscore.score07}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          				<input name="scr08" id="scr08_${rcount}" value="${stuscore.score08}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
          			<input name="scr08" type="hidden" value="${stuscore.score08}">
          			${stuscore.score08}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
          		<c:if test="${nmode == '1'}">
          				<input name="scr09" id="scr09_${rcount}" value="${stuscore.score09}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
          			<input name="scr09" type="hidden" value="${stuscore.score09}">
          			${stuscore.score09}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#FFD9D9">
          		<c:if test="${nmode == '1'}">
          				<input name="scr10" id="scr10_${rcount}" value="${stuscore.score10}" type="text" size="1" maxlength="5" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
          			<input name="scr10" type="hidden" value="${stuscore.score10}">
          			${stuscore.score10}
				</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">
           		<c:if test="${nmode == '1'}">
          				<input name="scr1" size="1" id="scr1_${rcount}" maxlength="5" type="text" value="${stuscore.score1}" readOnly="true"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
           			<input name="scr1" type="hidden" value="${stuscore.score1}"/>
          			${stuscore.score1}
				</c:if>
         		</td>
           		<td valign="middle" bgcolor="#FFD9D9">          		
          		<c:if test="${nmode == '1'}">
           				<input name="scr16" id="scr16_${rcount}" size="1" maxlength="5" type="text" value="${stuscore.score16}" readOnly="true"/>
				</c:if>
           		<c:if test="${nmode == '0'}">
          			<input name="scr16" type="hidden" value="${stuscore.score16}"/>
          			${stuscore.score16}
				</c:if>
           		</td>
          		<td valign="middle" bgcolor="#99FFFF">          		
           		<c:if test="${mmode == '1'}">
          				<input name="scr2" id="scr2_${rcount}" size="1" maxlength="5" type="text" value="${stuscore.score2}" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) changeM(${rcount});"/>
				</c:if>
           		<c:if test="${mmode == '0'}">
           			<input name="scr2" type="hidden" value="${stuscore.score2}"> 
          			${stuscore.score2}
				</c:if>
         		</td>
          		<input name="scr17" type="hidden" value="${stuscore.score17}"> 
           		<td id="scr17v${rcount}" align="left" valign="middle" bgcolor="#FFD9D9">          		
          			<c:if test="${stuscore.score17 == ''}">&nbsp;
          			</c:if>
          			<c:if test="${stuscore.score17 != ''}">${stuscore.score17}
          			</c:if>
         		</td>
          		<td valign="middle" bgcolor="#99FFFF">          		
           		<c:if test="${fmode == '1'}">
          				<input name="scr3" id="scr3_${rcount}" size="1" maxlength="5" type="text" value="${stuscore.score3}" onKeyUp="nextfocus(this);" onChange="if (checknum(this)) avgcal(${rcount}); else this.focus();"/>
				</c:if>
           		<c:if test="${fmode == '0'}">
          			<input name="scr3" value="${stuscore.score3}" type="hidden"/>
          			${stuscore.score3}
				</c:if>
         		</td>
          		<input name="scr18" type="hidden" value="${stuscore.score18}"> 
           		<td id="scr18v${rcount}" align="left" valign="middle" bgcolor="#FFD9D9">          		
          			<c:if test="${stuscore.score18 == ''}">&nbsp;
          			</c:if>
          			<c:if test="${stuscore.score18 != ''}">${stuscore.score18}
          			</c:if>
         		</td>
         		
          		<c:if test="${TchScoreUploadInfo.openMode == '000'}">
          		    <td id="scrv${rcount}" valign="middle" bgcolor="#99FFFF">          		
          				<input name="scr" id="scr_${rcount}" size="1" maxlength="5" type="text" value="${stuscore.score}" onKeyUp="nextfocus(this);" onChange="checknum(this);"/>
					</td>
				</c:if>
           		<c:if test="${TchScoreUploadInfo.openMode != '000'}">
          			<input name="scr" type="hidden" value="${stuscore.score}"/>
          			<td id="scrv${rcount}" align="right" valign="middle" bgcolor="#99FFFF" width="20">          		
          			${stuscore.score}
          			</td>
				</c:if>
        	</tr>
          	<c:set var="rcount" scope="page" value="${rcount+1}"/>
          	<c:if test="${(rcount % 5) == 0}">
          	<tr height="5"><td colspan="19" bgcolor="red"></td></tr>
          	</c:if>
          </c:forEach>
	</c:when>
	</c:choose>
	
          </table>
	   </td>
	</tr>
	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='SaveAndUpload' bundle='TCH' />" onSubmit="return checkrate2();" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='TCH'/>" >');
	</script>
 	
</c:if>
	
<!-- Begin Content Page Table Footer -->
				
</table>
<!-- End Content Page Table Footer -->
<script>
var rows = ${fn:length(TchScoreInEdit) -1};
	<c:if test="${TchScoreUploadInfo.openMode != '000'}">
		var nmode="${fn:substring(TchScoreUploadInfo.openMode,2,3)}";
		var tmode="0";
	</c:if>
	<c:if test="${TchScoreUploadInfo.openMode == '000'}">
		var nmode="0";
		var tmode="1";
	</c:if>
    var mmode = "${fn:substring(TchScoreUploadInfo.openMode,1,2)}";
    var fmode="${fn:substring(TchScoreUploadInfo.openMode,0,1)}";
</script>

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

function nextfocus(inObj) {
	var nextElem = null;
	if(inObj.id != null){
		nowPo = new String(inObj.id);
		nowStr = nowPo.split("_");
		nowRow = eval(nowStr[1]);
		var myid = nowStr[0].substring(0,3);
		
		var code = event.keyCode;
	//del:46, backSpace:8 or -8, left:37, right:39, up:38, down:40
		if(code==39) {	//right:39
			if(myid=='scr'){
				if(nowStr[0]=='scr01'){
					nextElem = document.getElementById("scr02_" + (nowRow));
				}else if(nowStr[0]=='scr02'){
					nextElem = document.getElementById("scr03_" + (nowRow));
				}else if(nowStr[0]=='scr03'){
					nextElem = document.getElementById("scr04_" + (nowRow));
				}else if(nowStr[0]=='scr04'){
					nextElem = document.getElementById("scr05_" + (nowRow));
				}else if(nowStr[0]=='scr05'){
					nextElem = document.getElementById("scr06_" + (nowRow));
				}else if(nowStr[0]=='scr06'){
					nextElem = document.getElementById("scr07_" + (nowRow));
				}else if(nowStr[0]=='scr07'){
					nextElem = document.getElementById("scr08_" + (nowRow));
				}else if(nowStr[0]=='scr08'){
					nextElem = document.getElementById("scr09_" + (nowRow));
				}else if(nowStr[0]=='scr09'){
					nextElem = document.getElementById("scr10_" + (nowRow));
				}else if(nowStr[0]=='scr10'){
					if(mmode!='0'){
						nextElem = document.getElementById("scr2_" + (nowRow));
					}else if(fmode!='0'){
						nextElem = document.getElementById("scr3_" + (nowRow));
					}else if(tmode!='0'){
						nextElem = document.getElementById("scr_" + (nowRow));
					}else if(nowRow<rows){
						nextElem = document.getElementById("scr01_" + (nowRow+1));
					}
				}else if(nowStr[0]=='scr2'){
					if(nowRow<rows){
						nextElem = document.getElementById("scr2_" + (nowRow+1));
					}
				}else if(nowStr[0]=='scr3'){
					if(nmode!='0'){
						if(nowRow<rows){
							nextElem = document.getElementById("scr01_" + (nowRow+1));
						}
					}else{
						if(nowRow<rows){
							nextElem = document.getElementById("scr3_" + (nowRow+1));
						}
					}
				}else if(nowStr[0]=='scr'){
					if(nowRow<rows){
						nextElem = document.getElementById("scr_" + (nowRow+1));
					}
				}
			}else if(myid=='rat'){
				if(nowRow<10){
					nextElem = document.getElementById("rate_" + (nowRow+1));
				}else{
					if(nmode!="0"){
						nextElem = document.getElementById("scr01_0");
					}else if(mmode!='0'){
						nextElem = document.getElementById("scr2_0");
					}else if(fmode!='0'){
						nextElem = document.getElementById("scr3_0");
					}else if(tmode!='0'){
						nextElem = document.getElementById("scr_0");
					}
				}
			}
			
		}else if(code==40) {	//down
			if(myid=='scr'){
				if(nowRow<rows){
					nowRow = nowRow+1;
				}
				if(nowStr[0]=='scr01'){
					nextElem = document.getElementById("scr01_" + nowRow);
				}else if(nowStr[0]=='scr02'){
					nextElem = document.getElementById("scr02_" + nowRow);
				}else if(nowStr[0]=='scr03'){
					nextElem = document.getElementById("scr03_" + nowRow);
				}else if(nowStr[0]=='scr04'){
					nextElem = document.getElementById("scr04_" + nowRow);
				}else if(nowStr[0]=='scr05'){
					nextElem = document.getElementById("scr05_" + nowRow);
				}else if(nowStr[0]=='scr06'){
					nextElem = document.getElementById("scr06_" + nowRow);
				}else if(nowStr[0]=='scr07'){
					nextElem = document.getElementById("scr07_" + nowRow);
				}else if(nowStr[0]=='scr08'){
					nextElem = document.getElementById("scr08_" + nowRow);
				}else if(nowStr[0]=='scr09'){
					nextElem = document.getElementById("scr09_" + nowRow);
				}else if(nowStr[0]=='scr10'){
					nextElem = document.getElementById("scr10_" + nowRow);
				}else if(nowStr[0]=='scr2'){
					nextElem = document.getElementById("scr2_" + nowRow);
				}else if(nowStr[0]=='scr3'){
					nextElem = document.getElementById("scr3_" + nowRow);
				}else if(nowStr[0]=='scr'){
					nextElem = document.getElementById("scr_" + nowRow);
				}
			}else if(myid=='rat'){
				if(nmode!="0"){
					nextElem = document.getElementById("scr01_0");
				}else if(mmode!='0'){
					nextElem = document.getElementById("scr2_0");
				}else if(fmode!='0'){
					nextElem = document.getElementById("scr3_0");
				}else if(tmode!='0'){
					nextElem = document.getElementById("scr_0");
				}
			}
			
		}else if(code==37) {	//left:37
			if(myid=='scr'){
				if(nowStr[0]=='scr01'){
					if(nowRow > 0){
						nowRow = nowRow - 1;
					}
					if(tmode!='0'){
						nextElem = document.getElementById("scr_" + (nowRow));
					}else if(fmode!='0'){
						nextElem = document.getElementById("scr3_" + (nowRow));
					}else if(mmode!='0'){
						nextElem = document.getElementById("scr2_" + (nowRow));
					}else if(nowRow>=0){
						nextElem = document.getElementById("scr10_" + (nowRow));
					}
				}else if(nowStr[0]=='scr02'){
					nextElem = document.getElementById("scr01_" + (nowRow));
				}else if(nowStr[0]=='scr03'){
					nextElem = document.getElementById("scr02_" + (nowRow));
				}else if(nowStr[0]=='scr04'){
					nextElem = document.getElementById("scr03_" + (nowRow));
				}else if(nowStr[0]=='scr05'){
					nextElem = document.getElementById("scr04_" + (nowRow));
				}else if(nowStr[0]=='scr06'){
					nextElem = document.getElementById("scr05_" + (nowRow));
				}else if(nowStr[0]=='scr07'){
					nextElem = document.getElementById("scr06_" + (nowRow));
				}else if(nowStr[0]=='scr08'){
					nextElem = document.getElementById("scr07_" + (nowRow));
				}else if(nowStr[0]=='scr09'){
					nextElem = document.getElementById("scr08_" + (nowRow));
				}else if(nowStr[0]=='scr10'){
					nextElem = document.getElementById("scr09_" + (nowRow));
				}else if(nowStr[0]=='scr2'){
					if(nowRow > 0){
						nextElem = document.getElementById("scr2_" + (nowRow-1));
					}
				}else if(nowStr[0]=='scr3'){
					if(nmode!='0'){
						nextElem = document.getElementById("scr10_" + (nowRow));
					}else{
						if(nowRow > 0){
							nextElem = document.getElementById("scr3_" + (nowRow-1));
						}
					}
				}else if(nowStr[0]=='scr'){
					if(nowRow > 0){
						nextElem = document.getElementById("scr_" + (nowRow-1));
					}
				}
			}else if(myid=='rat'){
				if(nowRow>0){
					nextElem = document.getElementById("rate_" + (nowRow-1));
				}
			}
		}else if(code==38) {	//up
			if(myid=='scr'){
				if(nowRow>0){
					nowRow = nowRow-1;
				}else{
					nowRow = 0;
				}
				if(nowStr[0]=='scr01'){
					nextElem = document.getElementById("scr01_" + nowRow);
				}else if(nowStr[0]=='scr02'){
					nextElem = document.getElementById("scr02_" + nowRow);
				}else if(nowStr[0]=='scr03'){
					nextElem = document.getElementById("scr03_" + nowRow);
				}else if(nowStr[0]=='scr04'){
					nextElem = document.getElementById("scr04_" + nowRow);
				}else if(nowStr[0]=='scr05'){
					nextElem = document.getElementById("scr05_" + nowRow);
				}else if(nowStr[0]=='scr06'){
					nextElem = document.getElementById("scr06_" + nowRow);
				}else if(nowStr[0]=='scr07'){
					nextElem = document.getElementById("scr07_" + nowRow);
				}else if(nowStr[0]=='scr08'){
					nextElem = document.getElementById("scr08_" + nowRow);
				}else if(nowStr[0]=='scr09'){
					nextElem = document.getElementById("scr09_" + nowRow);
				}else if(nowStr[0]=='scr10'){
					nextElem = document.getElementById("scr10_" + nowRow);
				}else if(nowStr[0]=='scr2'){
					nextElem = document.getElementById("scr2_" + nowRow);
				}else if(nowStr[0]=='scr3'){
					nextElem = document.getElementById("scr3_" + nowRow);
				}else if(nowStr[0]=='scr'){
					nextElem = document.getElementById("scr_" + nowRow);
				}
			}else if(myid=='rat'){
			}
		}
		
		if(nextElem != null){
			nextElem.focus();
		}
	}
};


function countPass(){
	var ipstotal = document.getElementById("stu_total");
	var ipspass = document.getElementById("stu_pass");
	var ips = document.getElementsByName("scr");
	var total = ips.length;
	var pass = 0;
	
	for(i=0; i<total; i++){
		if(ips[i] >=60) pass++;
	}
	ipstotal.value = total;
	ipspass.value = pass;
}
	
function changeM(knt){
	var ips17v = "";
	var ips2 = document.getElementsByName("scr2");
	var ips17 = document.getElementsByName("scr17");
	var ips = document.getElementsByName("scr");
	var ips16 = document.getElementsByName("scr16");
	var ipstotal = document.getElementById("stu_total");
	var ipspass = document.getElementById("stu_pass");
	var ipsrate = document.getElementsByName("scorerate");
	var rate_mdl = parseFloat(ipsrate[11].value);
	var total = 0, mbegin = 0, mend = 0;
	if(knt == -1){
		mbegin = 0;
		mend = ips.length;
	}else{
		mbegin = knt;
		mend = knt + 1;
	}
	
	for(i=mbegin; i<mend; i++){
		total = 0;
		if(ips2[i].value != ""){
			percent_m = Math.round(parseFloat(ips2[i].value) * rate_mdl * 10) / 10;
			//percent_m = Math.round(parseFloat(ips2[i].value) * 3) / 10;
		}else{ 
			percent_m = 0.0;
		}
		if(ips16[i].value != ""){
			total = total + parseFloat(ips16[i].value) + percent_m;
		}else{
			total = total + percent_m;
		}
		ips17[i].value=percent_m;
		document.getElementById("scr17v" + i).innerHTML = percent_m + "";
		document.getElementById("scrv" + i).innerHTML = Math.round(total) + "";
		ips[i].value = total;
	}
	countPass();
}

function checkrate() {
	var objs = document.getElementsByName("scorerate");
	var total_rate = 0;
	isRateOk = new Boolean(false);
	for (i=0; i< objs.length-3; i++) {
		total_rate = total_rate + parseFloat(objs[i].value);
		if(total_rate > 1) {
			objs[i].value = "0.0";
			objs[i].focus();
			alert("平時成績比例之總合不可大於1");
			break;
			//return false;
		}
	}
	var rate_nrm = parseFloat(objs[10].value);
	var rate_mdl = parseFloat(objs[11].value);
	var rate_fnl = parseFloat(objs[12].value);
	var all_rate = rate_nrm + rate_mdl + rate_fnl;
	if(all_rate > 1){
		window.alert("平時 + 期中 + 期末成績比利之總和不可大於１");
	}else if(all_rate < 1){
		window.alert("平時 + 期中 + 期末成績比利之總和不可小於１");
	}
	
	if(all_rate == 1 && (total_rate == 1 || total_rate == 0)) {
		recalavg(total_rate, -1);
		isRateOk = true;
	}
	
	return isRateOk;
};

function checkrate2() {
	var objs = document.getElementsByName("scorerate");
	var total_rate = 0;
	isRateOk = new Boolean(false);
	
	for (i=0; i< objs.length-3; i++) {
		total_rate = total_rate + parseFloat(objs[i].value);
		if(total_rate > 1) {
			objs[i].value = "0.0";
			objs[i].focus();
			alert("平時成績比例之總合不可大於1");
			break;
		}
	}
	var rate_nrm = parseFloat(objs[10].value);
	var rate_mdl = parseFloat(objs[11].value);
	var rate_fnl = parseFloat(objs[12].value);
	var all_rate = rate_nrm + rate_mdl + rate_fnl;
	if(all_rate > 1){
		window.alert("平時 + 期中 + 期末成績比利之總和不可大於１");
	}else if(all_rate < 1){
		window.alert("平時 + 期中 + 期末成績比利之總和不可小於１");
	}
		
	if(all_rate == 1 && (total_rate == 1 || total_rate == 0)) {
		isRateOk = true;
		if( tomde != "1"){
			recalavg(total_rate, -1);
		}
	}
	
	return isRateOk;
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
	
	var ips = document.getElementsByName("scr");
	var ips1 = document.getElementsByName("scr1");
	var ips2 = document.getElementsByName("scr2");
	var ips3 = document.getElementsByName("scr3");
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
	var ips18 = document.getElementsByName("scr18");
	var ipsrate = document.getElementsByName("scorerate");
	var ipstotal = document.getElementById("stu_total");
	var ipspass = document.getElementById("stu_pass");
	var ips17v = "", scrv;
	
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
	var rate_nrm = parseFloat(ipsrate[10].value);
	var rate_mdl = parseFloat(ipsrate[11].value);
	var rate_fnl = parseFloat(ipsrate[12].value);
	var all_rate = rate_nrm + rate_mdl + rate_fnl;
	if(all_rate > 1){
		window.alert("平時 + 期中 + 期末成績比利之總和不可大於１");
		return false;
	}else if(all_rate < 1){
		window.alert("平時 + 期中 + 期末成績比利之總和不可小於１");
		return false;
	}
	
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
	//alert("begin:" + mybegin + " , end:" + myend + " trate type:" + trate +" allrate0:" + allrate0);
	for(k=mybegin; k <= myend; k++) {
		total_n = 0.0;
		percent_n = 0.0;
		percent_f = 0.0;
		score_end = 0.0;
		kount = 0;
		
		if(ips2[k].value != ""){
			percent_m = Math.round(parseFloat(ips2[k].value) * rate_mdl * 10) / 10;
		}else{ 
			percent_m = 0.0;
		}
		ips17[k].value=percent_m;
		document.getElementById("scr17v" + k).innerHTML = percent_m + "";
		
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
		percent_n = Math.round(total_n * rate_nrm * 10) / 10;
	
		ips1[k].value = "" + total_n;
		ips16[k].value = "" + percent_n;
	
		if(ips3[k].value != "") {
			percent_f = Math.round(parseFloat(ips3[k].value) * rate_fnl * 10) / 10;
			ips18[k].value = "" + percent_f;
		} else {
			ips18[k].value = "";
		}
		document.getElementById("scr18v" + k).innerHTML = percent_f + "";
		
		//alert("K value:" + k + " ; ips length:" + ips.length);
		old_score_end = parseFloat(ips[k].value);
		score_end = Math.round(percent_n + percent_m + percent_f);
		
		ips[k].value = "" + score_end;
		if(tmode != "1"){
			scrv = document.getElementById("scrv" + k);
			scrv.innerHTML = score_end + "";
			if(score_end >= 60){
				scrv.className = "style1";
			} else if (score_end < 60) {
				scrv.className = "style2";
			}
		}else{
			if(score_end >= 60){
				ips[k].className = "style1";
			} else if (score_end < 60) {
				ips[k].className = "style2";
			}
		}
		
		if(rcount != -1) {
			if(score_end >= 60 && old_score_end < 60) stu_pass = parseInt(ipspass.value) + 1;
			else if(score_end < 60 && old_score_end >= 60) stu_pass = parseInt(ipspass.value) - 1;
		}
		else if(rcount == -1) {
			if(score_end >= 60) stu_pass = stu_pass + 1;
		}
	}
	if(rcount == -1 && isScoreLoss == 1 && nmode == "1"){
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
		inputtune("scr18", 3);
		inputtune("scr", 3);
		inputtune("scr1", 3);
		inputtune("scr2", 3);
		inputtune("scr3", 3);
	}
	countPass();
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

<c:if test="${TchScoreUploadInfo.openMode != '000'}">
optimize(1);
</c:if>

<c:if test="${TchScoreUploadInfo.openMode == '000'}">
optimize(0);
</c:if>
<c:if test="${TchScoreUploadInfo.openMode == '010'}">
changeM(-1);
</c:if>

closeMenu();
noMenu = true;
history.go(1);</script>

</form>
