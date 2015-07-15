<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>

<script>
history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  	<tr>
    	<td class="fullColorTable" width="100%">
      		<table width="100%" cellpadding="0" cellspacing="0" border="0">
        		<tr height="30">
          			<td width="28" align="right">
            			<img src="images/16-manager-st.gif">
          			</td>
          			<td align="left">&nbsp;<font color="red"> ${schoolYear}</font>  學 年 度 入 學 學 生 學 籍 資 料 卡 </td>
        		</tr>
      		</table>
    	</td>
  	</tr>	
  
	<html:form action="/Student/StudentRegistrationInput" method="post" focus="studentEName" onsubmit="init('執行中, 請稍後')">
  	<tr>
  		<td><br>
      		<table width="100%" cellpadding="0" cellspacing="0">
        		<tr>
          			<td width="10" align="left" nowrap>
            			<hr noshade size="1" color="cfe69f"/>
          			</td>
          			<td width="24" align="center" nowrap>
            			<img src="images/folder_find.gif" id="searchNorm">
          			</td>
          			<td nowrap>新生入學學籍資料&nbsp;	</td>
          			<td width="100%" align="left">
            			<hr noshade size="1" color="cfe69f"/>
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
            			<table width="100%">
              				<tr>
                				<td>
                  					<table width="100%">														
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                            							<td>姓名</td>
                            							<td>
							                            	<html:text property="studentNo" styleId="studentNo" size="10" readonly="true" disabled="true" value="${stdInfo.studentNo}"/>
							                              	<html:text property="studentName" styleId="studentName" size="10" readonly="true" disabled="true" value="${stdInfo.studentName}"/>
							                            </td>
							                            <td>身分證字號</td>
                            							<td>
							                            	<html:text property="idno" styleId="idno" size="10" readonly="true" disabled="true" value="${stdInfo.idno}"/>
							                            </td>
							                            <td>英文姓名</td>
                            							<td>
							                            	<html:text property="studentEName" styleId="studentEName" size="15" maxlength="20" value="${stdInfo.studentEname}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
							                            <td>系所</td>
                            							<td>
							                            	<html:text property="departClass" styleId="departClass" size="5" readonly="true" disabled="true" value="${stdInfo.departClass}"/>
							                              	<html:text property="departName" styleId="departName" size="15" readonly="true" disabled="true" value="${stdInfo.departClass2}"/>
							                            </td>
							                            <td>組別</td>
                            							<td>
							                            	<html:text property="divi" styleId="divi" size="15" maxlength="20" value="${stdInfo.registrationCard.diviName}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>	
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                            							<td>性別</td>
                            							<td>
							                            	<html:text property="sex2" styleId="sex2" size="1" readonly="true" disabled="true" value="${stdInfo.sex2}"/>
							                            </td>
							                            <td>生日</td>
                            							<td>
							                            	<html:text property="birth" styleId="birth" size="8" readonly="true" disabled="true" value="${stdInfo.birthday2}"/>
							                            </td>
							                             <td>國籍</td>
                            							<td>
							                            	<html:text property="birthCountry" styleId="birthCountry" size="8" maxlength="15" value="${stdInfo.registrationCard.birthCountry}"/>
							                            </td>
							                            <td>出生地</td>
                            							<td>
							                            	<html:text property="birthPlace" styleId="birthPlace" size="15" maxlength="25" value="${stdInfo.registrationCard.birthPlace}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>入學方式</td>
                            							<td>
							                            	<html:radio property="inWay" styleId="inWay" value="0" disabled="true"/>申請入學&nbsp;
							                            	<html:radio property="inWay" styleId="inWay" value="1" disabled="true"/>推薦甄選&nbsp;
							                            	<html:radio property="inWay" styleId="inWay" value="2" disabled="true"/>聯合分發&nbsp;
							                            	<html:radio property="inWay" styleId="inWay" value="3" disabled="true"/>獨立招生
							                            </td>
                            							<td>入伍日期</td>
                            							<td>
							                            	<html:text property="armyIn" styleId="armyIn" size="4" readonly="true" value="${stdInfo.registrationCard.armyInDateFormat}"/>
							                            	<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      														align="top" style="cursor:hand" 
	  	  														onclick="javascript:if(!armyIn.disabled)popCalFrame.fPopCalendar('armyIn','armyIn',event);">
							                            </td>
							                            <td>退伍日期</td>
                            							<td>
							                            	<html:text property="armyOut" styleId="armyOut" size="4" readonly="true" value="${stdInfo.registrationCard.armyOutDateFormat}"/>
							                            	<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      														align="top" style="cursor:hand" 
	  	  														onclick="javascript:if(!armyOut.disabled)popCalFrame.fPopCalendar('armyOut','armyOut',event);">
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>	
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>原住民族籍</td>
                            							<td>
							                            	<html:text property="aborigine" styleId="aborigine" size="10" maxlength="15" value="${stdInfo.registrationCard.aborigine}"/>
							                            </td>
                            							<td>僑生僑居地</td>
                            							<td>
							                            	<html:text property="foreignPlace" styleId="foreignPlace" size="10" maxlength="15" value="${stdInfo.registrationCard.foreignPlace}"/>
							                            </td>
							                            <td>外僑居留證統一證號</td>
                            							<td>
							                            	<html:text property="foreignNo" styleId="foreignNo" size="10" maxlength="15" value="${stdInfo.registrationCard.foreignNo}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>	
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>入學前學校</td>
                            							<td>
							                            	<html:text property="beforeSchool" styleId="beforeSchool" size="12" maxlength="20" value="${stdInfo.registrationCard.beforeSchool}"/>
							                            </td>
                            							<td>入學前科系</td>
                            							<td>
							                            	<html:text property="beforeDept" styleId="beforeDept" size="12" maxlength="15" value="${stdInfo.gradDept}"/>
							                            </td>
							                            <td>畢業年月</td>
                            							<td>
							                            	<html:text property="gradeYear" styleId="gradeYear" size="4" maxlength="5" value="${stdInfo.registrationCard.gradeYear}"/>&nbsp;&nbsp;
							                            	<html:radio property="gradeType" styleId="birth" value="0" />畢業&nbsp;
							                            	<html:radio property="gradeType" styleId="birth" value="1"/>肄業&nbsp;
							                            	<html:radio property="gradeType" styleId="birth" value="2"/>結業
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>戶籍郵遞區號</td>
                            							<td>
							                            	<html:text property="permPost" styleId="permPost" size="3" maxlength="5" value="${stdInfo.permPost}"/>
							                            </td>
                            							<td>電話</td>
                            							<td>
							                            	<html:text property="telephone" styleId="telephone" size="15" maxlength="20" value="${stdInfo.telephone}"/>
							                            </td>
							                            <td>手機(學生)</td>
                            							<td>
							                            	<html:text property="cellPhone" styleId="cellPhone" size="15" maxlength="20" value="${stdInfo.cellPhone}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                            							<td>戶籍地址</td>
                            							<td>
							                            	<html:text property="permAddr" styleId="permAddr" size="60" maxlength="100" value="${stdInfo.permAddr}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>	
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>家長姓名</td>
                            							<td>
							                            	<html:text property="parentName" styleId="parentName" size="5" maxlength="10" value="${stdInfo.parentName}"/>
							                            </td>
                            							<td>年齡</td>
                            							<td>
							                            	<html:text property="parentAge" styleId="parentAge" size="1" maxlength="3" value="${stdInfo.registrationCard.parentAge}"/>
							                            </td>
							                            <td>職業</td>
                            							<td>
							                            	<html:text property="parentCareer" styleId="parentCareer" size="15" maxlength="20" value="${stdInfo.registrationCard.parentCareer}"/>
							                            </td>
							                            <td>關係</td>
                            							<td>
							                            	<html:text property="parentRelationship" styleId="parentRelationship" size="10" maxlength="10" value="${stdInfo.registrationCard.parentRelationship}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>家長通訊郵遞區號</td>
                            							<td>
							                            	<html:text property="parentPost" styleId="parentPost" size="3" maxlength="5" value="${stdInfo.currPost}"/>
							                            </td>
                            							<td>緊急連絡電話</td>
                            							<td>
							                            	<html:text property="emergentPhone" styleId="emergentPhone" size="15" maxlength="20" value="${stdInfo.registrationCard.emergentPhone}"/>
							                            </td>
							                            <td>緊急連絡手機</td>
                            							<td>
							                            	<html:text property="emergentCell" styleId="emergentCell" size="15" maxlength="20" value="${stdInfo.registrationCard.emergentCell}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>	
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                            							<td>通訊地址</td>
                            							<td>
							                            	<html:text property="addr" styleId="addr" size="60" maxlength="60" value="${stdInfo.currAddr}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                            							<td>電郵信箱</td>
                            							<td>
							                            	<html:text property="email" styleId="email" size="60" maxlength="60" value="${stdInfo.email}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>入學前經歷１</td>
                          								<td>服務機關</td>
                            							<td>
							                            	<html:text property="workPlace1" styleId="workPlace1" size="10" maxlength="15" value="${stdInfo.registrationCard.workPlace1}"/>
							                            </td>
							                            <td>職務</td>
                            							<td>
							                            	<html:text property="workTitle1" styleId="workTitle1" size="10" maxlength="15" value="${stdInfo.registrationCard.workTitle1}"/>
							                            </td>
							                            <td>起迄時間</td>
                            							<td>
							                            	<html:text property="workBegin1" styleId="workBegin1" size="4" readonly="true" value="${stdInfo.registrationCard.workBegin1DateFormat}"/>
							                            	<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      														align="top" style="cursor:hand" 
	  	  														onclick="javascript:if(!workBegin1.disabled)popCalFrame.fPopCalendar('workBegin1','workBegin1',event);"> ～ 
							                            	<html:text property="workEnd1" styleId="workEnd1" size="4" readonly="true" value="${stdInfo.registrationCard.workEnd1DateFormat}"/>
							                            	<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      														align="top" style="cursor:hand" 
	  	  														onclick="javascript:if(!workEnd1.disabled)popCalFrame.fPopCalendar('workEnd1','workEnd1',event);">
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>	
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>入學前經歷２</td>
                          								<td>服務機關</td>
                            							<td>
							                            	<html:text property="workPlace2" styleId="workPlace2" size="10" maxlength="15" value="${stdInfo.registrationCard.workPlace2}"/>
							                            </td>
							                            <td>職務</td>	
                            							<td>
							                            	<html:text property="workTitle2" styleId="workTitle2" size="10" maxlength="15" value="${stdInfo.registrationCard.workTitle2}"/>
							                            </td>
							                            <td>起迄時間</td>
                            							<td>
							                            	<html:text property="workBegin2" styleId="workBegin2" size="4" readonly="true" value="${stdInfo.registrationCard.workBegin2DateFormat}"/>
							                            	<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      														align="top" style="cursor:hand" 
	  	  														onclick="javascript:if(!workBegin2.disabled)popCalFrame.fPopCalendar('workBegin2','workBegin2',event);"> ～ 
							                            	<html:text property="workEnd2" styleId="workEnd2" size="4" readonly="true" value="${stdInfo.registrationCard.workEnd2DateFormat}"/>
							                            	<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      														align="top" style="cursor:hand" 
	  	  														onclick="javascript:if(!workEnd2.disabled)popCalFrame.fPopCalendar('workEnd2','workEnd2',event);">
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>	
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>家庭成員１</td>
                          								<td>稱謂</td>
                            							<td>
							                            	<html:text property="memberTitle1" styleId="memberTitle1" size="10" maxlength="10" value="${stdInfo.registrationCard.memberTitle1}"/>
							                            </td>
							                            <td>姓名</td>
                            							<td>
							                            	<html:text property="memberName1" styleId="memberName1" size="10" maxlength="10" value="${stdInfo.registrationCard.memberName1}"/>
							                            </td>
							                            <td>年齡</td>
                            							<td>
							                            	<html:text property="memberAge1" styleId="memberAge1" size="1" maxlength="3" value="${stdInfo.registrationCard.memberAge1}"/>
							                            </td>
							                            <td>職業</td>
                            							<td>
							                            	<html:text property="memberCareer1" styleId="memberCareer1" size="20" maxlength="20" value="${stdInfo.registrationCard.memberCareer1}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>家庭成員２</td>
                          								<td>稱謂</td>
                            							<td>
							                            	<html:text property="memberTitle2" styleId="memberTitle2" size="10" maxlength="10" value="${stdInfo.registrationCard.memberTitle2}"/>
							                            </td>
							                            <td>姓名</td>
                            							<td>
							                            	<html:text property="memberName2" styleId="memberName2" size="10" maxlength="10" value="${stdInfo.registrationCard.memberName2}"/>
							                            </td>
							                           <td>年齡</td>
                            							<td>
							                            	<html:text property="memberAge2" styleId="memberAge2" size="1" maxlength="3" value="${stdInfo.registrationCard.memberAge2}"/>
							                            </td>
							                            <td>職業</td>
                            							<td>
							                            	<html:text property="memberCareer2" styleId="memberCareer2" size="20" maxlength="20" value="${stdInfo.registrationCard.memberCareer2}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>家庭成員３</td>
                          								<td>稱謂</td>
                            							<td>
							                            	<html:text property="memberTitle3" styleId="memberTitle3" size="10" maxlength="10" value="${stdInfo.registrationCard.memberTitle3}"/>
							                            </td>
							                            <td>姓名</td>
                            							<td>
							                            	<html:text property="memberName3" styleId="memberName3" size="10" maxlength="10" value="${stdInfo.registrationCard.memberName3}"/>
							                            </td>
							                            <td>年齡</td>
                            							<td>
							                            	<html:text property="memberAge3" styleId="memberAge3" size="1" maxlength="3" value="${stdInfo.registrationCard.memberAge3}"/>
							                            </td>
							                            <td>職業</td>
                            							<td>
							                            	<html:text property="memberCareer3" styleId="memberCareer3" size="20" maxlength="20" value="${stdInfo.registrationCard.memberCareer3}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                    					<tr id="onlyEdit">
                      						<td>
                        						<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
                          							<tr bgcolor="#f0fcd7">
                          								<td>家庭成員４</td>
                          								<td>稱謂</td>
                            							<td>
							                            	<html:text property="memberTitle4" styleId="memberTitle4" size="10" maxlength="10" value="${stdInfo.registrationCard.memberTitle4}"/>
							                            </td>
							                            <td>姓名</td>
                            							<td>
							                            	<html:text property="memberName4" styleId="memberName4" size="10" maxlength="10" value="${stdInfo.registrationCard.memberName4}"/>
							                            </td>
							                            <td>年齡</td>
                            							<td>
							                            	<html:text property="memberAge4" styleId="memberAge4" size="1" maxlength="3" value="${stdInfo.registrationCard.memberAge4}"/>
							                            </td>
							                            <td>職業</td>
                            							<td>
							                            	<html:text property="memberCareer4" styleId="memberCareer4" size="20" maxlength="20" value="${stdInfo.registrationCard.memberCareer4}"/>
							                            </td>
                          							</tr>
                        						</table>								
                      						</td>
                    					</tr>
                  					</table>
                				</td>
              				</tr>
            			</table>	
          			</td>
        		</tr>			
      		</table>
      		<script>
		  		generateTableBanner('<html:submit property="method" styleClass="CourseButton"><bean:message key="student.registrationCard.makeSure" bundle="STD" /></html:submit>&nbsp;'
		  						  + '<html:reset styleClass="CourseButton"><bean:message key="Clear" /></html:reset>');
		 	</script>	
    	</td>
  	</tr>
	</html:form>
</table>