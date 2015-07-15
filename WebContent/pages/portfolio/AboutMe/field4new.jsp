<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${f.type=='0'}">
<!-- 0=布林欄位-->
<table cellspacing="0" cellpadding="0" align="left" width="${f.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td class="colorInput">
			<select name="aContent" id="content${ff.count}">
				<option value="0">不開放</option>
				<option value="1">開放</option>
			</select>			
			<input type="hidden" name="field_oid" value="${f.Oid }"/>
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

<c:if test="${f.type=='1'}">
<!-- 1=單行文字-->
<table cellspacing="0" cellpadding="0" align="left" width="${f.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td>
			<input type="text" class="colorInput" name="aContent" id="content${ff.count}" style="width:100%;"/>
			<input type="hidden" name="field_oid" value="${f.Oid }"/>
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



<c:if test="${f.type=='2'}">
<!-- 2=日期 -->
<table cellspacing="0" cellpadding="0" align="left" width="${f.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td>
			<input type="hidden" name="field_oid" value="${f.Oid }"/>
			<input type="text" name="aContent" id="content${ff.count}" class="colorInput"
			onclick="ds_sh(this);" autocomplete="off" style="width:100%; ime-mode:disabled"; autocomplete="off";
			onMouseOver="showHelpMessage('點擊開啟月曆','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
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

<c:if test="${f.type=='3'}">
<!-- 3=檔案 -->
<table cellspacing="0" cellpadding="0" align="left" width="${f.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td>
		<input type="hidden" name="field_oid" value="${f.Oid }"/>
		<input type="text" class="colorInput" name="aContent" style="width:100%;"/>		
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







<c:if test="${f.type=='4'}">
<!-- 4=長文字 -->
<table cellspacing="0" cellpadding="0" align="left" width="${f.size-10}%">
	<tr>
		<td width="1"><img src="images/input_left.gif"/></td>
		<td  class="colorInput">
		<input type="hidden" name="field_oid" value="${f.Oid }"/>		
		<textarea rows="1" name="aContent" style="width:100%; border:0px; height:12px;"></textarea>
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

