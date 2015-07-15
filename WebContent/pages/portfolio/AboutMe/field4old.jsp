<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${md.type=='0'}">
<!-- 0=布林文字-->
<table cellspacing="0" cellpadding="0" align="left" width="${md.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td class="colorInput">
			<select name="contents" id="contents${ss.count}">
				<option <c:if test="${md.content=='0'}">selected</c:if> value="0">不開放</option>
				<option <c:if test="${md.content=='1'}">selected</c:if> value="1">開放</option>
			</select>			
			<input type="hidden" name="Oid" value="${md.ecOid}"/>
		</td>
		<td width="1"><img src="images/input_right.gif"/></td>
	</tr>	
</table>
<table align="left">
	<tr>
		<td>
			<img src="images/comment.gif"/>
		</td>
	</tr>
</table>
</c:if>

<c:if test="${md.type=='1'}">
<!-- 1=單行文字-->
<table cellspacing="0" cellpadding="0" align="left" width="${md.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td>
			<input type="text" class="colorInput" name="contents" id="contents${ss.count}" style="width:100%;" value="${md.content}"/>
			<input type="hidden" name="Oid" value="${md.ecOid}"/>
		</td>
		<td width="1"><img src="images/input_right.gif"/></td>
	</tr>	
</table>
<table align="left">
	<tr>
		<td>
			<img src="images/comment.gif"/>
		</td>
	</tr>
</table>
</c:if>

<c:if test="${md.type=='2'}">
<table cellspacing="0" cellpadding="0" align="left" width="${md.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td>
			<input type="text" name="contents" id="contents${ss.count}" style="width:100%;" class="colorInput"
			onclick="ds_sh(this);" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
			onMouseOver="showHelpMessage('點擊開啟月曆', 'inline', this.id)" value="${md.content}"
			onMouseOut="showHelpMessage('', 'none', this.id)"/>			
			<input type="hidden" name="Oid" value="${md.ecOid}"/>
		</td>
		<td width="1"><img src="images/input_right.gif"/></td>
	</tr>	
</table>
<table align="left">
	<tr>
		<td>
			<img src="images/comment.gif"/>
		</td>
	</tr>
</table>
</c:if>

<c:if test="${md.type=='3'}">
<!-- 3=檔案-->
<table cellspacing="0" cellpadding="0" align="left" width="${md.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td>
			<input type="text" class="colorInput" name="contents" id="contents${ss.count}" style="width:100%;" value="${md.content}"/>
			<input type="hidden" name="Oid" value="${md.ecOid}"/>
		</td>
		<td width="1"><img src="images/input_right.gif"/></td>
	</tr>	
</table>
<table align="left">
	<tr>
		<td>
			<img src="images/folder_lightbulb.gif"/>
		</td>
	</tr>
</table>
</c:if>

<c:if test="${md.type=='4'}">
<!-- 4=長文字 -->
<table cellspacing="0" cellpadding="0" align="left" width="${md.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td class="colorInput">
			<textarea rows="1" name="contents" style="width:100%; border:0px; height:12px;">${md.content}</textarea>
			<input type="hidden" name="Oid" value="${md.ecOid}"/>
		</td>
		<td width="1"><img src="images/input_right.gif"/></td>
	</tr>	
</table>
<table align="left">
	<tr>
		<td>
			<img src="images/comments.gif"/>
		</td>
	</tr>
</table>
</c:if>