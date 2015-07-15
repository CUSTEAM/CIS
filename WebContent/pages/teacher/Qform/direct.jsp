<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">

	<c:if test="${!empty qlist}">
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">已完成的問卷</td>
			</tr>
			<c:forEach items="${qlist}" var="q">		
			<tr>
				<td class="hairLineTdF">
				<input type="button" value="刪除" class="gCancelSmall" onClick="window.location.replace('/CIS/Teacher/AQ/Qform.do?doid=${q.Oid}')" />
				${q.name}, ${q.anser}</td>
			</tr>
			</c:forEach>
		</table>
		
		
			
				
		
	</c:if>
	
	<c:if test="${!empty mydept}">
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" onClick="showObj('mydept')" style="cursor: pointer;">請點選開啟任教科系的問卷</td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%" id="mydept" style="display:none;">
			
				
		<tr>
			<td class="hairLineTdF">
			
			<input type="submit" name="method" value="<bean:message key='Continue'/>" class="gGreenSmall">			
			<select name="dept" style="font-size:18px;">
				<c:forEach items="${mydept}" var="u">
				<option <c:if test="${dept==u.id2}">selected</c:if> value="${u.id2}">${u.name}</option>
				</c:forEach>
			</select>			
			若非您所屬的系，此欄位可自由調整
							
			</td>
		</tr>		
		
		</table>
		
		
		</td>
	</tr>
	</c:if>
	
	
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" onClick="showObj('other')" style="cursor: pointer;">請點選開啟各科系的問卷</td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%" id="other" style="display:none;">
			<tr>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF">問卷名稱</td>
			</tr>
		<c:forEach items="${other}" var="u">		
		<tr>
			<td class="hairLineTdF" width="1">
			<input type="button" value="填寫" class="gCancelSmall" onClick="window.location.replace('/CIS/Teacher/AQ/Qform.do?uid=${u.id}&id2=${u.id2}')" />
			</td>
			<td class="hairLineTdF">${u.name }</td>
		</tr>		
		</c:forEach>
		</table>
		
		</td>
	</tr>
	<tr>
		<td >
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" onClick="showObj('units')" style="cursor: pointer;">請點選開啟行政單位的問卷</td>
			</tr>
		</table>
		
		
		<table class="hairLineTable" width="99%" id="units" style="display:none;">
			<tr>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF">問卷名稱</td>
			</tr>
		<c:forEach items="${allunit}" var="u">		
		<tr>
			<td class="hairLineTdF" width="1">
			<input type="button" value="填寫" class="gSubmitSmall" onClick="window.location.replace('/CIS/Teacher/AQ/Qform.do?uid=${u.id}')" />
			</td>
			<td class="hairLineTdF">${u.name }</td>
		</tr>		
		</c:forEach>
		</table>
		
		
		
		
		</td>
	</tr>
</table>