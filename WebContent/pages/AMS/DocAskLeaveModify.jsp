<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>
<script type="text/javascript">
	var basePath='<%=basePath%>';
</script>

<style type="text/css">
<!--
.style1 {color: #0000FF}
.style3 {
	font-size: 24px;
	font-weight: bold;
}
.title {
	font-size:13px;
	background-color:#ddffdd;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.input {
	font-size:13px;
	background-color:#ffffee;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.mustInput {
	font-size:13px;
	background-color:#ffcccc;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:#cc6666;
}
.descript {
	font-size:12px;
	background-color:#eeffff;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>
<form action="/CIS/AMS/DocManager.do" method="post" name="inputForm">
<input name="opmode" type="hidden" value="modify"/>
<input name="docType" type="hidden" value="${DocAskLeaveModifyInit.docType}"/>
<!-- for auto input select item use -->
	<div id="popup" class="ds_box" style="position:absolute;">
		 <table id="name_table" bgcolor="#FFFAFA" border="1" cellspacing="2" cellpadding="2">
		 	  <tbody id="name_body"></tbody>
		 </table>
	</div> 



<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="2" cellspacing="2">
<script>generateTableBanner('請假管理->修改');</script>	  
<!-- End Content Page Table Header -->
	<tr><td><table>
	<c:choose>
	<c:when test="${DocAskLeaveModifyForm != null}">
	<c:set var="formback" value="${DocAskLeaveModifyForm}"></c:set>
	<input name="oid" type="hidden" value="${formback.oid}"/>
	<tr>
		<td width="75" class="title">請假事由</td>
		<td class="input"><input type="text" maxlength="13" name="reason" class="mustInput" value="${formback.reason}">(最多可輸入13個中文字)</td>
	</tr>
	<tr>
		<td width="75" class="title">假別</td>
		<td class="input">
		<select name="askLeaveType" id="askLeaveType">
			<c:forEach items="${askLeaveList}" var="askType">
				<c:if test="${askType.id == formback.askLeaveType}">
				<option value="${askType.id}" Selected="selected">${askType.name}</option>
				</c:if>
				<c:if test="${askType.id != formback.askLeaveType}">
					<c:if test="${askType.id != 09}">
						<option value="${askType.id}">${askType.name}</option>
					</c:if>
				</c:if>
			</c:forEach> 
		</select>
		</td>
	</tr>
	<tr>
		<td width="75" class="title"><font color=red>請注意:</font></td>
		<td class="input">
		<font color=red>日班:請上半天假，起始時間應填08:00；請下半天假，起始時間應填12:30<br/>
		夜班:請上半天假，起始時間應填14:00；請下半天假，起始時間應填18:00<br/>
		公差假：得支領差旅費<br/>
		公假：不得支領差旅費
		</font>
		</td>
	</tr>
	<tr>
		<td width="75" class="title">開始日期</td>
		<td class="input">西元 
		<input type="text" name="startYear" class="mustInput" size="4" value="${formback.startYear}"> 年&nbsp;&nbsp;
		<input type="text" name="startMonth" class="mustInput" size="2" value="${formback.startMonth}"> 月&nbsp;&nbsp;
		<input type="text" name="startDay" class="mustInput" size="2" value="${formback.startDay}"> 日&nbsp;&nbsp;
		<select name="startHour" id="startHour">
			<c:forEach begin="0" end="23" var="cnt">
				<c:if test="${cnt == formback.startHour}">
				<option value="${cnt}" selected="selected">${cnt}</option>
				</c:if>
				<c:if test="${cnt != formback.startHour}">
				<option value="${cnt}">${cnt}</option>
				</c:if>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="startMinute" id="startMinute">
			<c:if test="${0 == formback.startMinute}">
			<option value="0" selected="selected">0</option>
			<option value="30">30</option>
			</c:if>
			<c:if test="${30 == formback.startMinute}">
			<option value="0">0</option>
			<option value="30" selected="selected">30</option>
			</c:if>
		</select> 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">結束日期</td>
		<td class="input">西元 
		<input type="text" name="endYear" class="mustInput" size="4" value="${formback.endYear}"> 年&nbsp;&nbsp;
		<input type="text" name="endMonth" class="mustInput" size="2" value="${formback.endMonth}"> 月&nbsp;&nbsp;
		<input type="text" name="endDay" class="mustInput" size="2" value="${formback.endDay}"> 日&nbsp;&nbsp;
		<select name="endHour" id="endHour">
			<c:forEach begin="0" end="23" var="cnt">
				<c:if test="${cnt == formback.endHour}">
				<option value="${cnt}" selected="selected">${cnt}</option>
				</c:if>
				<c:if test="${cnt != formback.endHour}">
				<option value="${cnt}">${cnt}</option>
				</c:if>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="endMinute" id="endMinute">
			<c:if test="${0 == formback.endMinute}">
			<option value="0" selected="selected">0</option>
			<option value="30">30</option>
			</c:if>
			<c:if test="${30 == formback.endMinute}">
			<option value="0">0</option>
			<option value="30" selected="selected">30</option>
			</c:if>
		</select> 分
		</td>
	</tr>
	
	<c:if test="${AmsIsTeacher == '1'}">
	<tr>
		<td width="75" class="title">請假日數</td>
		<td class="input">
		<input type="text" name="totalDay" class="mustInput" size="2" value="${formback.totalDay}"> 日&nbsp;&nbsp;
		<select name="totalHour" id="totalHour">
			<c:forEach begin="0" end="23" var="cnt">
				<c:if test="${cnt == formback.totalHour}">
				<option value="${cnt}" selected="selected">${cnt}</option>
				</c:if>
				<c:if test="${cnt != formback.totalHour}">
				<option value="${cnt}">${cnt}</option>
				</c:if>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="totalMinute" id="totalMinute">
			<c:if test="${0 == formback.totalMinute}">
			<option value="0" selected="selected">0</option>
			<option value="30">30</option>
			</c:if>
			<c:if test="${30 == formback.totalMinute}">
			<option value="0">0</option>
			<option value="30" selected="selected">30</option>
			</c:if>
		</select> 分
		&nbsp;(1日等於8小時,寒暑假1日等於7小時)
		</td>
	</tr>
	</c:if>
	
	<tr>
		<td width="75" class="title">授課節數</td>
		<td class="input">共：<input type="text" name="teachPeriod" class="notMustInput" size="2" value="${formback.teachPeriod}"> 節 
		</td>
	</tr>
	<tr>
		<td width="75" class="title">代理人</td>
		<td class="input">
			<table class="hairLineTable">
				<tr>
					<td class="input" align="left" class="mustInput">
					姓名 <input type="text" autocomplete="off" name="fscname" id="fscname"  class="mustInput" size="10" value="${formback.fscname}"
								onMouseOver="showHelpMessage('姓名輸入這裡!', 'inline', this.id)" 
				   				onMouseOut="showHelpMessage('', 'none', this.id)"
							 	onkeyup="if(this.value.length>0)getEmpl(this, true);"/>
					<input type="text" name="ids" id="ids" size="10" readonly="readonly" value="${fn:substring(formback.agent,0, 5)}#####"/>		 	
					<input type="text" style="display: none;" name="agent" id="agent" readonly="readonly" value="${formback.agent}"/> 
					</td>
				</tr>
			</table>
		
		<!-- input type="text" name="agent" class="mustInput" size="10" -->
		</td>
	</tr>
	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input"><textarea rows="3" cols="60" name="memo">${formback.memo}</textarea>
		</td>
	</tr>
	
	</c:when>
	<c:when test="${DocAskLeaveInEdit != null}">
	<c:set var="formback" value="${DocAskLeaveInEdit}"></c:set>
	<input name="oid" type="hidden" value="${formback.oid}"/>
	<tr>
		<td width="75" class="title">請假事由</td>
		<td class="input"><input type="text" maxlength="13" name="reason" class="mustInput" value="${formback.reason}">(最多可輸入13個中文字)</td>
	</tr>
	<tr>
		<td width="75" class="title">假別</td>
		<td class="input">
		<select name="askLeaveType" id="askLeaveType">
			<c:forEach items="${askLeaveList}" var="askType">
				<c:if test="${askType.id == formback.askLeaveType}">
				<option value="${askType.id}" Selected="selected">${askType.name}</option>
				</c:if>
				<c:if test="${askType.id != formback.askLeaveType}">
					<c:if test="${askType.id != 09}">
						<option value="${askType.id}">${askType.name}</option>
					</c:if>
				</c:if>
			</c:forEach> 
		</select>
		</td>
	</tr>
	<tr>
		<td width="75" class="title"><font color=red>請注意:</font></td>
		<td class="input">
		<font color=red>日班:請上半天假，起始時間應填08:00；請下半天假，起始時間應填12:30<br/>
		夜班:請上半天假，起始時間應填14:00；請下半天假，起始時間應填18:00<br/>
		公差假：得支領差旅費<br/>
		公假：不得支領差旅費
		</font>
		</td>
	</tr>
	<tr>
		<td width="75" class="title">開始日期</td>
		<td class="input">西元 
		<input type="text" name="startYear" class="mustInput" size="4" value="${formback.startYear}"> 年&nbsp;&nbsp;
		<input type="text" name="startMonth" class="mustInput" size="2" value="${formback.startMonth}"> 月&nbsp;&nbsp;
		<input type="text" name="startDay" class="mustInput" size="2" value="${formback.startDay}"> 日&nbsp;&nbsp;
		<select name="startHour" id="startHour">
			<c:forEach begin="0" end="23" var="cnt">
				<c:if test="${cnt == formback.startHour}">
				<option value="${cnt}" selected="selected">${cnt}</option>
				</c:if>
				<c:if test="${cnt != formback.startHour}">
				<option value="${cnt}">${cnt}</option>
				</c:if>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="startMinute" id="startMinute">
			<c:if test="${0 == formback.startMinute}">
			<option value="0" selected="selected">0</option>
			<option value="30">30</option>
			</c:if>
			<c:if test="${30 == formback.startMinute}">
			<option value="0">0</option>
			<option value="30" selected="selected">30</option>
			</c:if>
		</select> 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">結束日期</td>
		<td class="input">西元 
		<input type="text" name="endYear" class="mustInput" size="4" value="${formback.endYear}"> 年&nbsp;&nbsp;
		<input type="text" name="endMonth" class="mustInput" size="2" value="${formback.endMonth}"> 月&nbsp;&nbsp;
		<input type="text" name="endDay" class="mustInput" size="2" value="${formback.endDay}"> 日&nbsp;&nbsp;
		<select name="endHour" id="endHour">
			<c:forEach begin="0" end="23" var="cnt">
				<c:if test="${cnt == formback.endHour}">
				<option value="${cnt}" selected="selected">${cnt}</option>
				</c:if>
				<c:if test="${cnt != formback.endHour}">
				<option value="${cnt}">${cnt}</option>
				</c:if>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="endMinute" id="endMinute">
			<c:if test="${0 == formback.endMinute}">
			<option value="0" selected="selected">0</option>
			<option value="30">30</option>
			</c:if>
			<c:if test="${30 == formback.endMinute}">
			<option value="0">0</option>
			<option value="30" selected="selected">30</option>
			</c:if>
		</select> 分
		</td>
	</tr>

	<c:if test="${AmsIsTeacher == '1'}">
	<tr>
		<td width="75" class="title">請假日數</td>
		<td class="input">
		<input type="text" name="totalDay" class="mustInput" size="2" value="${formback.totalDay}"> 日&nbsp;&nbsp;
		<select name="totalHour" id="totalHour">
			<c:forEach begin="0" end="23" var="cnt">
				<c:if test="${cnt == formback.totalHour}">
				<option value="${cnt}" selected="selected">${cnt}</option>
				</c:if>
				<c:if test="${cnt != formback.totalHour}">
				<option value="${cnt}">${cnt}</option>
				</c:if>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="totalMinute" id="totalMinute">
			<c:if test="${0 == formback.totalMinute}">
			<option value="0" selected="selected">0</option>
			<option value="30">30</option>
			</c:if>
			<c:if test="${30 == formback.totalMinute}">
			<option value="0">0</option>
			<option value="30" selected="selected">30</option>
			</c:if>
		</select> 分
		&nbsp;(1日等於8小時,寒暑假1日等於7小時)
		</td>
	</tr>
	</c:if>
	
	<tr>
		<td width="75" class="title">授課節數</td>
		<td class="input">共：<input type="text" name="teachPeriod" class="notMustInput" size="2" value="${formback.teachPeriod}"> 節 
		</td>
	</tr>
	<tr>
		<td width="75" class="title">代理人</td>
		<td class="input">
			<table class="hairLineTable">
				<tr>
					<td class="input" align="left" class="mustInput">
					姓名 <input type="text" autocomplete="off" name="fscname" id="fscname"  class="mustInput" size="10" value="${formback.fscname}"
								onMouseOver="showHelpMessage('姓名輸入這裡!', 'inline', this.id)" 
				   				onMouseOut="showHelpMessage('', 'none', this.id)"
							 	onkeyup="if(this.value.length>0)getEmpl(this, true);"/>
					<input type="text" name="ids" id="ids" size="10" readonly="readonly" value="${fn:substring(formback.agent,0, 5)}#####"/>		 	
					<input type="text" style="display: none;" name="agent" id="agent" readonly="readonly" value="${formback.agent}"/> 
					</td>
				</tr>
			</table>
		
		<!-- input type="text" name="agent" class="mustInput" size="10" -->
		</td>
	</tr>
	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input"><textarea rows="3" cols="60" name="memo">${formback.memo}</textarea>
		</td>
	</tr>
	
	</c:when>
	<c:otherwise>
	<tr>
		<td width="75" class="title">請假事由</td>
		<td class="input"><input type="text" maxlength="13" name="reason" class="mustInput">(最多可輸入13個中文字)</td>
	</tr>
	<tr>
		<td width="75" class="title">假別</td>
		<td class="input">
		<select name="askLeaveType" id="askLeaveType">
			<c:forEach items="${askLeaveList}" var="askType">
				<c:if test="${askType.id != 09}">
					<option value="${askType.id}">${askType.name}</option>
				</c:if>
			</c:forEach> 
		</select>
		</td>
	</tr>
	<tr>
		<td width="75" class="title"><font color=red>請注意:</font></td>
		<td class="input">
		<font color=red>日班:請上半天假，起始時間應填08:00；請下半天假，起始時間應填12:30<br/>
		夜班:請上半天假，起始時間應填14:00；請下半天假，起始時間應填18:00<br/>
		公差假：得支領差旅費<br/>
		公假：不得支領差旅費
		</font>
		</td>
	</tr>
	<tr>
		<td width="75" class="title">開始日期</td>
		<td class="input">西元 
		<input type="text" name="startYear" class="mustInput" size="4"> 年&nbsp;&nbsp;
		<input type="text" name="startMonth" class="mustInput" size="2"> 月&nbsp;&nbsp;
		<input type="text" name="startDay" class="mustInput" size="2"> 日&nbsp;&nbsp;
		<select name="startHour" id="startHour">
			<c:forEach begin="0" end="23" var="cnt">
				<option value="${cnt}">${cnt}</option>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="startMinute" id="startMinute">
			<option value="0">0</option>
			<option value="30">30</option>
		</select> 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">結束日期</td>
		<td class="input">西元 
		<input type="text" name="endYear" class="mustInput" size="4"> 年&nbsp;&nbsp;
		<input type="text" name="endMonth" class="mustInput" size="2"> 月&nbsp;&nbsp;
		<input type="text" name="endDay" class="mustInput" size="2"> 日&nbsp;&nbsp;
		<select name="endHour" id="endHour">
			<c:forEach begin="0" end="23" var="cnt">
				<option value="${cnt}">${cnt}</option>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="endMinute" id="endMinute">
			<option value="0">0</option>
			<option value="30">30</option>
		</select> 分
		</td>
	</tr>
	<c:if test="${AmsIsTeacher == '1'}">
	<tr>
		<td width="75" class="title">請假日數</td>
		<td class="input">
		<input type="text" name="totalDay" class="mustInput" size="2"> 日&nbsp;&nbsp;
		<select name="totalHour" id="totalHour">
			<c:forEach begin="0" end="23" var="cnt">
				<option value="${cnt}">${cnt}</option>
			</c:forEach> 
		</select> 時&nbsp;&nbsp;
		<select name="totalMinute" id="totalMinute">
			<option value="0">0</option>
			<option value="30">30</option>
		</select> 分
		&nbsp;(1日等於8小時,寒暑假1日等於7小時)
		</td>
	</tr>
	</c:if>
	
	<tr>
		<td width="75" class="title">授課節數</td>
		<td class="input">共：<input type="text" name="teachPeriod" class="notMustInput" size="2"> 節 
		</td>
	</tr>
	<tr>
		<td width="75" class="title">代理人</td>
		<td class="input">
			<table class="hairLineTable">
				<tr>
					<td class="input" align="left" class="mustInput">
					姓名 <input type="text" autocomplete="off" name="fscname" id="fscname"  class="mustInput" size="10" value=""
								onMouseOver="showHelpMessage('姓名輸入這裡!', 'inline', this.id)" 
				   				onMouseOut="showHelpMessage('', 'none', this.id)"
							 	onkeyup="if(this.value.length>0)getEmpl(this, true);"/>
					<input type="text" name="ids" id="ids" size="10" readonly="readonly"/>		 	
					<input type="text" style="display: none;" name="agent" id="agent" readonly="readonly"/> 
					</td>
				</tr>
			</table>
		
		<!-- input type="text" name="agent" class="mustInput" size="10" -->
		</td>
	</tr>
	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input"><textarea rows="3" cols="60" name="memo"></textarea>
		</td>
	</tr>
	</c:otherwise>
	</c:choose>
	<tr>
		<td width="75" class="title" valign="top">說明</td>
		<td>
		<table class="descript">
			<tr>
			<td width="20" valign="top">1.</td>
			<td>
【請假期限】：教師請假須填寫假單一至三聯，職員工只須填二、三聯。假單第一聯專供教師因涉及授課之異動與調整時，須將此聯交由教務處以備存查外，第二聯則由各單位（或本人）留存，第三聯送交人事室登記。所有假單或出勤證明在經批准後，需於請假日之隔日起7日內（含例假日）送交人事室才算完成請假手續，逾時恕不受理。</td>
			</tr>
			<tr>
			<td width="20" valign="top">2.</td>
			<td>
【主管請假程序】：各單位一級主管請假，需經校長簽准後再送人事室登記。</td>
			</tr>
			<tr>
			<td width="20" valign="top">3.</td>
			<td>
【教職員工請假程序】：除公差、公假以外之各種假別，於請假二日（含）內，由單位一級主管核准即可。教師若於請假期間有課者，經系主任同意後，須再會簽教務處（擔任導師者需再加會學務處），才可送交人事室登記。請假日數在二天（不含）以上者，除須經單位一級主管同意外，還需再經校長核准。</td>
			</tr>
			<tr>
			<td width="20" valign="top">4.</td>
			<td>
【公差與公假】：教職員工公差一日（含）內-因奉主管之命於校內加班或派遣至校外出差者，由單位一級主管核准即可。至於公差達二日（含）以上、公假-代表學校對外出席會議等，或出差須申請差旅費者，均需再經校長核准後，才可送至人事室登記。</td>
			</tr>
			<tr>
			<td width="20" valign="top">5.</td>
			<td>
【年假與補休】：同組職員工不可同時請年假或補休。年假最少以半天為單位；補休最少以一小時為單位。在非規定上班時間內，因奉學校或單位主管之命，加班或出差達四小時以上者（時數不得跨日累計），才得申請補休四小時，並須以加班當日之刷卡紀錄或校外出差之公文假單為憑。</td>
			</tr>
			<tr>
			<td width="20" valign="top">6.</td>
			<td>
【證明文件】：請長病假、婚喪產假與公假者，須隨附證明文件之正本簽請核示，再一併送人事室登記。</td>
			</tr>
			<tr>
			<td width="20" valign="top">7.</td>
			<td>
【新竹分部教職員工請假程序】：無論主管或一般教職員工，假單除須經分部主任簽核外，其他均按一般請假規定。  </td>    
			</tr>
		</table>
		</td>
	</tr>
	</table></td></tr>
	
	
	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</table>
</form>
<script type="text/javascript" src="<%=basePath%>pages/AMS/include/docQuery.js"></script>
