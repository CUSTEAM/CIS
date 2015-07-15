<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<br>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td width="10" align="left" nowrap>
  			<hr noshade class="myHr"/>
			</td>
			<td width="24" align="center" nowrap>
			<img src="images/icon/table_multiple.gif" />
			</td>
			<td nowrap>
			編輯欄位
			</td>
			<td width="100%" align="left">
  			<hr noshade class="myHr"/>
		</td>
	</tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">		
	<tr>
		<td valign="top">
		
<script>
function editTable(id){
	showObj("field"+id);
	showObj("save"+id);
}
</script>					
		<c:forEach items="${allTable}" var="a">
		<table class="hairLineTable" width="99%">
			<tr>				
				<td class="hairLineTdF" style="cursor:pointer;" onClick="editTable('${a.Oid}');">				
				<input type="text" name="tableSeq" style="width:100%;" value="${a.name}" />
				<input type="hidden" name="Oid" size="2" value="${a.Oid}"/><!-- 表格 -->
				
				</td>				
				<td class="hairLineTd" width="1">
				
				<select disabled>
					<option <c:if test="${a.sys==false}">selected</c:if> value="0">由使用者輸入資料</option>
					<option <c:if test="${a.sys==true}">selected</c:if> value="1">校務系統帶出資料</option>
				</select>
				
				</td>
				<td class="hairLineTdF" width="60" align="right" nowrap style="cursor:pointer;" onClick="editTable('${a.Oid}');">表格順序</td>
				<td class="hairLineTd" width="1">				
				<input type="text" name="tableSeq" size="2" value="${a.sequence}" />
				</td>
			</tr>
		</table>		
		<table class="hairLineTable" width="99%" id="field${a.Oid}" style="display:none;">	
		<c:forEach items="${a.field}" var="f">		
			<tr>
				<td class="hairLineTdF" width="100%">				
				<input type="text" name="fieldNames" style="width:100%;" value="${f.name}" />				
				</td>
				<td class="hairLineTd" width="1">				
				<select name="type">
					<c:forEach items="${format}" var="ff">
					<option <c:if test="${ff.Oid==f.type}">selected</c:if> value="${ff.Oid}">${ff.name}</option>
					</c:forEach>
				</select>				
				</td>
				<td class="hairLineTdF" width="50" align="right" nowrap>長度</td>
				<td class="hairLineTd" width="1">
				
				<input type="text" name="size" size="2" value="${f.size}" />
				<input type="hidden" name="fieldOid" size="2" value="${f.Oid}"/><!-- 欄位 -->
				<input type="hidden" name="tableOid" value="${a.Oid}" size="2" />
				</td>
				
				<td class="hairLineTdF" width="70" align="right" nowrap>欄位順序</td>
				<td class="hairLineTd" width="1">				
				<input type="text" name="fieldSeq" size="2" value="${f.sequence}" />
				</td>
			</tr>			
		</c:forEach>			
		</table>		
		<div id="addField${a.Oid}">
		<!-- 這是裝新增的 -->		
		</div>
		
		<table class="hairLineTable" cellpadding="0" cellspacing="0" id="save${a.Oid}" style="display:none;" width="99%">
			<tr>
				<td class="hairLineTdF" colspan="8" align="right" width="100%">
				
				<input type="button" value="增加" class="CourseButton" onClick="addField('${a.Oid}', '${f.Oid}'), this.style.display='none';"/><INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="CourseButton">
				
				</td>
			</tr>		
		</table>		
		</c:forEach>		
		</td>
	</tr>
</table>