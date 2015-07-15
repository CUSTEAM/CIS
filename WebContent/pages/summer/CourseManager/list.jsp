<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<tr>
		<td>
		<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<display:table name="${SdtimeList}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="<script>generateTriggerAll(${fn:length(SdtimeList)}, 'sdtimes'); </script>" class="center" >
	        <script>generateCheckbox("${row.Oid}", "sdtimes")</script></display:column>
			<display:column title="梯次" property="seqno" sortable="true" class="left" />
			<display:column title="開課班級" property="name" sortable="true" class="left" />
			<display:column title="班級代碼" property="depart_class" sortable="true" class="left" />
			<display:column title="科目名稱" property="chi_name" sortable="true" class="left" />
			<display:column title="科目代碼" property="cscode" sortable="true" class="left" />
			<display:column title="教師姓名" property="cname" sortable="true" class="left" />
			<display:column title="選別" property="opt" sortable="true" class="left" />
			<display:column title="學分" property="credit" sortable="true" class="left" />
			<display:column title="時數" property="thour" sortable="true" class="left" />
			<display:column title="已選" property="stu_select" sortable="true" class="left" />
			<display:column title="狀態" property="status" sortable="true" class="left" />
			<display:column title="人數" property="icon" nulls="false" class="center" href="/CIS/SstudentSel" paramId="Oid" paramProperty="Oid" />
	      </display:table>
		</td>
	</tr>

	<tr>
		<td colspan="7" align="center" height="40">
			<table border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F" width="98%" align="center">
  				<tr>
    				<td bgcolor="#FFFFFF">
    					<table width="100%">
    						<tr>
    							<td align="left" width="400">
    							<img src="images/printer.gif" border="0">
    							<select onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">教務表格</option>
    								<option value="/CIS/pages/course/export/list4Sdtime.jsp?type=excel">課程報表</option>
    								<option value="/CIS/AdviceOfSummer">教師通知</option>
									<option value="/CIS/List4SummerSseld">學生報表</option>
    							</select>
    							
    							<select onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">學務表格</option>
    								<option value="/CIS/List4SummerSseld">學生報表</option>
									<option value="/CIS/RollCallBook">點名表</option>
    							</select>
    							
<script>
function jumpMenu(targ,selObj,restore){
			eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
			eval(targ+".location.target='_blank'");
			if (restore) selObj.selectedIndex=0;
	}
</script>
    							
    							</td>
    							<td align="right">
    							<c:set var="cTotal" value="0"/>
    							<c:set var="cCredit" value="0"/>
    							<c:set var="cHour" value="0"/>
    							<c:set var="cStuSlt" value="0"/>
    							<c:forEach items="${SdtimeList}" var="dTLst" varStatus="dTLstS">
    								<c:set var="cCredit" value="${cCredit+dTLst.credit}"/>
    								<c:set var="cHour" value="${cHour+dTLst.thour}"/>
    								<c:set var="cStuSlt" value="${cStuSlt+dTLst.stu_select}"/>
    								<c:set var="cTotal" value="${cTotal+1}"/>
    							</c:forEach>
    							共計: ${cTotal}筆, <font title="學分數是以浮點數運算, 精確度因虛擬機器而異, 小數點以下數字不一定需要被考慮.">${cCredit}</font>學分, <font title="時數是以浮點數運算, 精確度因虛擬機器而異, 小數點以下數字不一定需要被考慮.">${cHour}</font>小時, ${cStuSlt}人次
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" class="gSubmit">
		<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" class="gCancle">
		<INPUT type="submit" name="method" value="<bean:message key='View'/>" class="gCancle">
		</td>
	</tr>
	       	