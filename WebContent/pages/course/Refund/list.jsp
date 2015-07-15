<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>

		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">班級代碼</td>
				<td class="hairLineTdF">班級名稱</td>
				<td class="hairLineTdF">班級人數</td>
				<td class="hairLineTdF">班級開課</td>
			</tr>
		<c:forEach items="${refunds}" var="r">
			<tr>
				<td class="hairLineTdF">${r.ClassNo}</td>
				<td class="hairLineTdF">${r.ClassName}</td>
				<td class="hairLineTdF">${r.countStu} ( 人 )</td>
				<td class="hairLineTdF">${r.countCou} ( 門 )</td>
			</tr>
			</c:forEach>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
</table>