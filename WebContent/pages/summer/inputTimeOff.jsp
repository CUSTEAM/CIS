<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/24-tag-pencil.png"></td>'+
										'<td>學生請假資料維護</td>'+
									'</tr>'+
								'</table>');
</script>
	<tr>
	<html:form action="/Summer/inputTimeOff" method="post" onsubmit="init('查詢進行中, 請稍後')">
		<td>
<!-- 主要查詢工具 start-->
<c:if test="${welcome}">
		<table class="empty-border">
			<tr>
				<td>
				
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">開課梯次</td>
						<td class="hairLineTd">
						<select name="seqno">
									<option <c:if test="${inputTimeOffForm.map.seqno=='%'}" > selected</c:if> value="">所有</option>
									<c:forEach items="${sweek}" var="sweek">
		    							<option <c:if test="${inputTimeOffForm.map.seqno==sweek.seqno}" > selected</c:if> value="${sweek.seqno}">第${sweek.seqno}梯次</option>
		    						</c:forEach>
		    						</select>
		    						<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
										<tr>
											<td id="ds_calclass"></td>
										</tr>
									</table>
						</td>
						<td class="hairLineTdF" width="30" align="center">
						
		    						<img src="images/icon_calendar.gif">
		    			</td>
		    			<td class="hairLineTd">
		    						<input type="text" name="ddate"" value="${inputTimeOffForm.map.ddate}" 
		    						onclick="ds_sh(this), this.value='';" readonly size="8"/>
						</td>
						<td class="hairLineTdF" width="30" align="center">
							<img src="images/16-exc-mark.gif" />
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">開課班級</td>
						<td class="hairLineTd">
						<input type="text" id="departClass" name="departClass"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						 value="${inputTimeOffForm.map.departClass}"
						 onkeyup="getAny(this.value, 'departClass', 'classLess', 'Sabbr', 'no')"
						 onclick="this.value='', document.getElementById('classLess').value=''"/><input 
						 type="text" name="classLess" id="classLess"
						value="${inputTimeOffForm.map.classLess}" size="16"
						 onkeyup="getAny(this.value, 'classLess', 'departClass', 'Sabbr', 'name')"
						 onclick="this.value='', document.getElementById('departClass').value=''"/>
						</td>
						<td class="hairLineTdF" width="30" align="center">
							<img src="images/16-exc-mark.gif" />
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable">			
					<tr>
						<td class="hairLineTdF">
						<bean:message key="setCourse.label.courseNumber" bundle="COU"/>
						</td>
						
						<td class="hairLineTd">
						<input type="text" name="courseNumber" id="cscodeS" size="8"  
						autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						value="${inputTimeOffForm.map.courseNumber}" 
						onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
						onclick="this.value='', courseName.value=''"/><input type="text" autocomplete="off"
						name="courseName" id="csnameS" size="16" 
						value="${inputTimeOffForm.map.courseName}" 
						onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
						onkeydown="document.getElementById('Acsname').style.display='none';" 
						onclick="this.value='', courseNumber.value=''"/>
						
						</td>
						<td class="hairLineTdF" width="30" align="center">
							<img src="images/16-exc-mark.gif" />
						</td>
					</tr>	
					
				</table>
				
				</td>
			</tr>
			
		</table>
		
		
		
		
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Query'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Create'/>" 
													   class="CourseButton"><INPUT type="submit"
																				   name="method"
																				   value="<bean:message key='Clear'/>"
																				   class="CourseButton" disabled>
		</td>
	</tr>
</c:if>
<!-- 主要查詢工具 end-->




<!--查詢模式 start-->
<c:if test="${mode=='search'}">
<tr>
	<td>
	<display:table name="${sdilgs}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="學號" property="student_no" nulls="false" sortable="true" class="center"/>
		<display:column title="姓名" property="student_name" nulls="false" class="center"/>
		<display:column title="班級" property="DdepartClass" nulls="false" class="center"/>
		<display:column title="課程" property="cscode" nulls="false" class="center"/>
		<display:column title="日期" property="ddate" nulls="false" class="center"/>
		<display:column title="第1節" property="abs1" nulls="false" class="center" />
		<display:column title="第2節" property="abs2" nulls="false" class="center"/>
		<display:column title="第3節" property="abs3" nulls="false" class="center"/>
		<display:column title="第4節" property="abs4" nulls="false" class="center"/>
		<display:column title="第5節" property="abs5" nulls="false" class="center"/>
		<display:column title="第6節" property="abs6" nulls="false" class="center"/>
		<display:column title="第7節" property="abs7" nulls="false" class="center"/>
		<display:column title="第8節" property="abs8" nulls="false" class="center"/>
		<display:column title="第9節" property="abs9" nulls="false" class="center"/>
		<display:column title="第10節" property="abs10" nulls="false" class="center"/>
	</display:table>
	</td>
</tr>
<tr>
	<td colspan="7" align="center" height="40">
		<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F">
  			<tr>
    			<td bgcolor="#FFFFFF">
    				<table>
    					<tr>
    						<td>
    						 選擇輸出格式:
    						</td>
    						<td>
    						<a href="course/export/delStu4DelDtime.jsp?type=excel">
    						<img src="images/ico_file_excel.png" border="0"> Excel
    						</a>
    						<a href="course/export/delStu4DelDtime.jsp?type=word">
    						<img src="images/ico_file_word.png" border="0"> Word
    						</a>
    						</td>
    					</tr>
    				</table>
    			</td>
  			</tr>
		</table>
	</td>
</tr>
<script>
	generateTableBanner("");
</script>
</c:if>


<!--查詢模式 end-->

<!-- 新增模式 Start -->
<c:if test="${mode=='create'}">
<tr>
	<td>
		<table width="100%">
						<tr>
							<td>
								<div class="modulecontainer filled nomessages">
								<div class="first">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								<div>
								<div>
									<table width="100%" onclick="showInfo('help')">
										<tr>
											<td>
											&nbsp;&nbsp;<img src="images/24-book-green-message.png">
											</td>
											<td align="left" width="100%">
											輸入說明
											</td>
										</tr>
										<tr>
											<td colspan="2" id="help">
											&nbsp;&nbsp;&nbsp;&nbsp;數值對應顯示: 2-曠課, 3-病假, 4-事假, 5-遲到/早退, 6-公假, 7-喪假<br>
											&nbsp;&nbsp;&nbsp;&nbsp;操作注意事項: <font color='red'>假別會自動對應, 輸入欄位不可有多餘字元</font><br>
											&nbsp;&nbsp;&nbsp;&nbsp;請假注意事項: 日後請假只要<font color='red'>回到這一天</font>更改缺課狀態即可
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
	</td>
</tr>
<tr>
	<td>
	<table class="hairLineTable">
		<tr>
			<td class="hairLineTdF" width="30" align="center">班級</td>
			<td class="hairLineTd">${titleInfo.name}</td>
			<td class="hairLineTdF" width="30" align="center">課程</td>
			<td class="hairLineTd">${titleInfo.chi_name}</td>
		</tr>
	</table>
	
	<table class="hairLineTable" width="100%">
		<tr>
			<td class="hairLineTdF">學號</td>
			<td class="hairLineTdF">姓名</td>
			<td class="hairLineTdF">第1節</td>
			<td class="hairLineTdF">第2節</td>
			<td class="hairLineTdF">第3節</td>
			<td class="hairLineTdF">第4節</td>
			<td class="hairLineTdF">第5節</td>
			<td class="hairLineTdF">第6節</td>
			<td class="hairLineTdF">第7節</td>
			<td class="hairLineTdF">第8節</td>
			<td class="hairLineTdF">第9節</td>
			<td class="hairLineTdF">第10節</td>
			<td class="hairLineTdF">第11節</td>
		</tr>
		
		<c:forEach items="${sdilgs}" var="sd">
		<c:set var="abs1value" value=""/>
		<c:set var="abs2value" value=""/>
		<c:set var="abs3value" value=""/>
		<c:set var="abs4value" value=""/>
		<c:set var="abs5value" value=""/>
		<c:set var="abs6value" value=""/>
		<c:set var="abs7value" value=""/>
		<c:set var="abs8value" value=""/>
		<c:set var="abs9value" value=""/>
		<c:set var="abs10value" value=""/>
		<c:set var="abs11value" value=""/>
		<c:choose>
			<c:when test="${sd.abs1=='2'}">
			<c:set var="abs1value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs1=='3'}">
			<c:set var="abs1value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs1=='4'}">
			<c:set var="abs1value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs1=='5'}">
			<c:set var="abs1value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs1=='6'}">
			<c:set var="abs1value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs1=='7'}">
			<c:set var="abs1value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs1==''}">
			<c:set var="abs1value" value=""/>
			</c:when>
		</c:choose>
			
		<c:choose>	
			<c:when test="${sd.abs2=='2'}">
			<c:set var="abs2value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs2=='3'}">
			<c:set var="abs2value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs2=='4'}">
			<c:set var="abs2value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs2=='5'}">
			<c:set var="abs2value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs2=='6'}">
			<c:set var="abs2value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs2=='7'}">
			<c:set var="abs2value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs2==''}">
			<c:set var="abs2value" value=""/>
			</c:when>
		</c:choose>
		<c:choose>	
			<c:when test="${sd.abs3=='2'}">
			<c:set var="abs3value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs3=='3'}">
			<c:set var="abs3value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs3=='4'}">
			<c:set var="abs3value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs3=='5'}">
			<c:set var="abs3value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs3=='6'}">
			<c:set var="abs3value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs3=='7'}">
			<c:set var="abs3value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs3==''}">
			<c:set var="abs3value" value=""/>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${sd.abs4=='2'}">
			<c:set var="abs4value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs4=='3'}">
			<c:set var="abs4value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs4=='4'}">
			<c:set var="abs4value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs4=='5'}">
			<c:set var="abs4value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs4=='6'}">
			<c:set var="abs4value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs4=='7'}">
			<c:set var="abs4value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs4==''}">
			<c:set var="abs4value" value=""/>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${sd.abs5=='2'}">
			<c:set var="abs5value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs5=='3'}">
			<c:set var="abs5value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs5=='4'}">
			<c:set var="abs5value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs5=='5'}">
			<c:set var="abs5value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs5=='6'}">
			<c:set var="abs5value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs5=='7'}">
			<c:set var="abs5value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs5==''}">
			<c:set var="abs5value" value=""/>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${sd.abs6=='2'}">
			<c:set var="abs6value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs6=='3'}">
			<c:set var="abs6value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs6=='4'}">
			<c:set var="abs6value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs6=='5'}">
			<c:set var="abs6value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs6=='6'}">
			<c:set var="abs6value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs6=='7'}">
			<c:set var="abs6value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs6==''}">
			<c:set var="abs6value" value=""/>
			</c:when>
		</c:choose>
		<c:choose>	
			<c:when test="${sd.abs7=='2'}">
			<c:set var="abs7value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs7=='3'}">
			<c:set var="abs7value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs7=='4'}">
			<c:set var="abs7value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs7=='5'}">
			<c:set var="abs7value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs7=='6'}">
			<c:set var="abs7value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs7=='7'}">
			<c:set var="abs7value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs7==''}">
			<c:set var="abs7value" value=""/>
			</c:when>		
		</c:choose>
		<c:choose>	
			<c:when test="${sd.abs8=='2'}">
			<c:set var="abs8value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs8=='3'}">
			<c:set var="abs8value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs8=='4'}">
			<c:set var="abs8value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs8=='5'}">
			<c:set var="abs8value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs8=='6'}">
			<c:set var="abs8value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs8=='7'}">
			<c:set var="abs8value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs8==''}">
			<c:set var="abs8value" value=""/>
			</c:when>		
		</c:choose>
		<c:choose>	
			<c:when test="${sd.abs9=='2'}">
			<c:set var="abs9value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs9=='3'}">
			<c:set var="abs9value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs9=='4'}">
			<c:set var="abs9value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs9=='5'}">
			<c:set var="abs9value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs9=='6'}">
			<c:set var="abs9value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs9=='7'}">
			<c:set var="abs9value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs9==''}">
			<c:set var="abs9value" value=""/>
			</c:when>		
		</c:choose>
		<c:choose>	
			<c:when test="${sd.abs10=='2'}">
			<c:set var="abs10value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs10=='3'}">
			<c:set var="abs10value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs10=='4'}">
			<c:set var="abs10value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs10=='5'}">
			<c:set var="abs10value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs10=='6'}">
			<c:set var="abs10value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs10=='7'}">
			<c:set var="abs10value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs10==''}">
			<c:set var="abs10value" value=""/>
			</c:when>		
		</c:choose>
		<c:choose>	
			<c:when test="${sd.abs11=='2'}">
			<c:set var="abs11value" value="曠課"/>
			</c:when>
			<c:when test="${sd.abs11=='3'}">
			<c:set var="abs11value" value="病假"/>
			</c:when>
			<c:when test="${sd.abs11=='4'}">
			<c:set var="abs11value" value="事假"/>
			</c:when>
			<c:when test="${sd.abs11=='5'}">
			<c:set var="abs11value" value="遲到/早退"/>
			</c:when>
			<c:when test="${sd.abs11=='6'}">
			<c:set var="abs11value" value="公假"/>
			</c:when>
			<c:when test="${sd.abs11=='7'}">
			<c:set var="abs11value" value="喪假"/>
			</c:when>
			<c:when test="${sd.abs11==''}">
			<c:set var="abs11value" value=""/>
			</c:when>		
		</c:choose>
		
		<tr>
			<td class="hairLineTd">${sd.student_no}<input type="hidden" name="studentNo" value="${sd.student_no}"/></td>
			<td class="hairLineTd">${sd.student_name}</td>
			<td class="hairLineTd">
			<input type="hidden" name="wdepartClass" value="${sd.depart_class}"/>
			<input type="hidden" name="wddate" value="${ddate}"/>
			<input type="hidden" name="Oid" value="${sd.Oid}"/>
			<input type="hidden" name="wcscode" value="${sd.cscode}"/>
			<input type="hidden" name="wseqno" value="${sd.seqno}"/>
			<input type="text" value="${abs1value}" id="${sd.student_no}ab1" size="1" onclick="clearvalue('${sd.student_no}ab1', '${sd.student_no}abs1')" onkeyup="chText('${sd.student_no}ab1', '${sd.student_no}abs1', this.value)"/><input type="hidden" id="${sd.student_no}abs1" name="abs1" value="${sd.abs1}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs2value}" id="${sd.student_no}ab2" size="1" onclick="clearvalue('${sd.student_no}ab2', '${sd.student_no}abs2')" onkeyup="chText('${sd.student_no}ab2', '${sd.student_no}abs2', this.value)"/><input type="hidden" id="${sd.student_no}abs2" name="abs2" value="${sd.abs2}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs3value}" id="${sd.student_no}ab3" size="1" onclick="clearvalue('${sd.student_no}ab3', '${sd.student_no}abs3')" onkeyup="chText('${sd.student_no}ab3', '${sd.student_no}abs3', this.value)"/><input type="hidden" id="${sd.student_no}abs3" name="abs3" value="${sd.abs3}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs4value}" id="${sd.student_no}ab4" size="1" onclick="clearvalue('${sd.student_no}ab4', '${sd.student_no}abs4')" onkeyup="chText('${sd.student_no}ab4', '${sd.student_no}abs4', this.value)"/><input type="hidden" id="${sd.student_no}abs4" name="abs4" value="${sd.abs4}"></td>
			<td class="hairLineTd"><input type="text" value="${abs5value}" id="${sd.student_no}ab5" size="1" onclick="clearvalue('${sd.student_no}ab5', '${sd.student_no}abs5')" onkeyup="chText('${sd.student_no}ab5', '${sd.student_no}abs5', this.value)"/><input type="hidden" id="${sd.student_no}abs5" name="abs5" value="${sd.abs5}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs6value}" id="${sd.student_no}ab6" size="1" onclick="clearvalue('${sd.student_no}ab6', '${sd.student_no}abs6')" onkeyup="chText('${sd.student_no}ab6', '${sd.student_no}abs6', this.value)"/><input type="hidden" id="${sd.student_no}abs6" name="abs6" value="${sd.abs6}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs7value}" id="${sd.student_no}ab7" size="1" onclick="clearvalue('${sd.student_no}ab7', '${sd.student_no}abs7')" onkeyup="chText('${sd.student_no}ab7', '${sd.student_no}abs7', this.value)"/><input type="hidden" id="${sd.student_no}abs7" name="abs7" value="${sd.abs7}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs8value}" id="${sd.student_no}ab8" size="1" onclick="clearvalue('${sd.student_no}ab8', '${sd.student_no}abs8')" onkeyup="chText('${sd.student_no}ab8', '${sd.student_no}abs8', this.value)"/><input type="hidden" id="${sd.student_no}abs8" name="abs8" value="${sd.abs8}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs9value}" id="${sd.student_no}ab9" size="1" onclick="clearvalue('${sd.student_no}ab9', '${sd.student_no}abs9')" onkeyup="chText('${sd.student_no}ab9', '${sd.student_no}abs9', this.value)"/><input type="hidden" id="${sd.student_no}abs9" name="abs9" value="${sd.abs9}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs10value}" id="${sd.student_no}ab10" size="1" onclick="clearvalue('${sd.student_no}ab10', '${sd.student_no}abs10')" onkeyup="chText('${sd.student_no}ab10', '${sd.student_no}abs10', this.value)"/><input type="hidden" id="${sd.student_no}abs10" name="abs10" value="${sd.abs10}"/></td>
			<td class="hairLineTd"><input type="text" value="${abs11value}" id="${sd.student_no}ab11" size="1" onclick="clearvalue('${sd.student_no}ab11', '${sd.student_no}abs11')" onkeyup="chText('${sd.student_no}ab11', '${sd.student_no}abs11', this.value)"/><input type="hidden" id="${sd.student_no}abs11" name="abs11" value="${sd.abs11}"/></td>
		</tr>
		
		</c:forEach>
	</table><br>
	</td>
</tr>
<tr height="30" class="fullColorTr">
	<td align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Save'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Cancel'/>"
													   class="CourseButton">
	</td>
</tr>
</c:if>
<!-- 新增模式 End -->

	</html:form>
</table>




<script>
	function chText(oid, id, no){
		if(document.getElementById(oid).value=='2'){
			document.getElementById(oid).value='曠課';
		}
		if(document.getElementById(oid).value=='3'){
			document.getElementById(oid).value='病假';
		}
		if(document.getElementById(oid).value=='4'){
			document.getElementById(oid).value='事假';
		}
		if(document.getElementById(oid).value=='5'){
			document.getElementById(oid).value='遲到/早退';
		}
		if(document.getElementById(oid).value=='6'){
			document.getElementById(oid).value='公假';
		}
		if(document.getElementById(oid).value=='7'){
			document.getElementById(oid).value='喪假';
		}		
		document.getElementById(id).value=no;
	}
</script>
<script>
	function clearvalue(show, value){
		document.getElementById(show).value='';
		document.getElementById(value).value='';
		
	}
</script>
<script>
	function showInfo(id){
		if(document.getElementById(id).style.display=="inline"){
			document.getElementById(id).style.display="none"
		}else{
			document.getElementById(id).style.display="inline"
		}
		
	}
</script>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>