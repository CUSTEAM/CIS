<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table cellpadding="0" cellspacing="0" width="100%" align="left">
	
	<tr>
		<td width="50%" valign="top">
		<table class="hairLineTable" width="99%">
			
			<tr>
				<td class="hairLineTdF" nowrap>一般課程題目列表</td>
			</tr>
		</table>
		<c:forEach items="${normal}" var="n">
		<table class="hairLineTable" width="99%" >
			
			<tr>
				<td class="hairLineTdF" nowrap>題目順序</td>
				<td class="hairLineTdF" colspan="4">題目內容</td>
			</tr>
			
			<tr>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value="${n.Oid}">
				<input type="hidden" name="type" value="${n.type}">
				<input type="text" name="sequence" value="${n.sequence}" style="font-size:18px;" size="1">
				</td>
				<td class="hairLineTdF" width="100%" colspan="4">
				<input type="text" name="options" value="${n.options}" style="width:100%; font-size:18px;">
				<input type="hidden" name="parentOid" value="${n.parentOid}">
				<input type="hidden" name="value" value="${n.value}" style="font-size:18px;" size="1">					
				</td>
			</tr>
			
			
			<tr>
				<td class="hairLineTdF" nowrap>答案順序</td>
				<td class="hairLineTdF" colspan="4">答案內容</td>
			</tr>
			<c:forEach items="${n.option}" var="q">
			
			<tr>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value="${q.Oid}">
				<input type="hidden" name="type" value="${q.type}">
				<input type="text" name="sequence" value="${q.sequence}" style="font-size:18px;" size="1">
				</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" name="options" value="${q.options}" style="width:100%; font-size:18px;">
				<input type="hidden" name="parentOid" value="${q.parentOid}">									
				</td>
				<td class="hairLineTdF" nowrap>
				分數
				</td>
				<td class="hairLineTdF">
				<input type="text" name="value" value="${q.value}" style="font-size:18px;" size="1">
				</td>
			</tr>
			</c:forEach>
		</table>
		
		</c:forEach>
		</td>
		
		
		
		<td width="50%" valign="top">		
		<table class="hairLineTable" width="99%">
			
			<tr>
				<td class="hairLineTdF" nowrap>遠距課程題目列表</td>
			</tr>
		</table>
		
		<c:forEach items="${elearinig}" var="n">
		<table class="hairLineTable" width="99%" >
			
			<tr>
				<td class="hairLineTdF" nowrap>題目順序</td>
				<td class="hairLineTdF" colspan="4">題目內容</td>
			</tr>
			
			<tr>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value="${n.Oid}">
				<input type="hidden" name="type" value="${n.type}">
				<input type="text" name="sequence" value="${n.sequence}" style="font-size:18px;" size="1">
				</td>
				<td class="hairLineTdF" width="100%" colspan="4">
				<input type="text" name="options" value="${n.options}" style="width:100%; font-size:18px;">
				<input type="hidden" name="parentOid" value="${n.parentOid}">
				<input type="hidden" name="value" value="${n.value}" style="font-size:18px;" size="1">					
				</td>
			</tr>
			
			
			<tr>
				<td class="hairLineTdF" nowrap>答案順序</td>
				<td class="hairLineTdF" colspan="4">答案內容</td>
			</tr>
			<c:forEach items="${n.option}" var="q">
			
			<tr>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value="${q.Oid}">
				<input type="hidden" name="type" value="${q.type}">
				<input type="text" name="sequence" value="${q.sequence}" style="font-size:18px;" size="1">
				</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" name="options" value="${q.options}" style="width:100%; font-size:18px;">
				<input type="hidden" name="parentOid" value="${q.parentOid}">									
				</td>
				<td class="hairLineTdF" nowrap>
				分數
				</td>
				<td class="hairLineTdF">
				<input type="text" name="value" value="${q.value}" style="font-size:18px;" size="1">
				</td>
			</tr>
			</c:forEach>
		</table>
		
		</c:forEach>
		
		</td>
		
		
		
		
		
		
		
		
	</tr>		
	<tr height="30">
		<td class="fullColorTable" align="center" colspan="2">
		</td>
	</tr>
</table>
