<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<%@include file="/pages/include/Displaytag4Checkbox.inc"%>




<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		
		<display:table name="${allSaly}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list" >
			<display:column title="<script>generateTriggerAll(${fn:length(allSaly)}, 'salys'); </script>" class="center" >
			<script>generateCheckbox("${row.Oid}", "salys")</script></display:column>
			<display:column title="薪資日期" property="sdate" sortable="true" class="center" />
			<display:column title="序號" property="seqno" sortable="true" class="center" />
			<display:column title="姓名" property="cname" sortable="true" class="center" />
			<display:column title="職稱" property="sname" sortable="true" class="center" />
			<display:column title="扶養" property="family_no" sortable="true" class="right" />
			<display:column title="應發金額" property="payamt" sortable="true" class="right" />
			<display:column title="應扣金額" property="dedamt" sortable="true" class="center" />  
			<display:column title="實發金額" property="real_pay" sortable="true" class="right" />
		</display:table>

		</td>
	</tr>
	<tr height="30">
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" align="left">
				共 ${fn:length(allSaly)}筆, ${realPay}元
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" class="CourseButton">
		</td>
	</tr>
</table>







