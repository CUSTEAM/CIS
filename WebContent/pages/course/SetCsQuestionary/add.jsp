<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>		
<!-- 新增普通課程題目及選項 start -->
<table width="100%">
	<tr>
		<td>
		新增一般課程題目及選項		
		</td>
	</tr>	
	<tr id="aNq">
		<td>		
		<table cellpadding="5" cellspacing="1" bgcolor="#cfe69f" onClick="addOne('coadd')">
			<tr bgcolor="f0fcd7">
				<td>新增</td>
				<td>順序</td>
				<td>分數</td>
				<td>題目 / 選項</td>
			</tr>
			<tr bgcolor="f0fcd7">
				<td><input type="checkBox" checked disabled />
					<input type="hidden" name="anType" value="M" />					
				</td>
				<td><input type="text" name="anSequence" size="1"/></td>
				<td><input type="hidden" name="anTextValue" size="1" value="0" />
					<input type="hidden" name="anValue" size="1" value="0" />
					<input type="text" size="1" value="N/A" disabled />
				</td>
				<td><input type="text" name="anOptions"/></td>
			</tr>
		</table>		
		<div id="coadd">
		<table><tr height="5"><td></td></tr></table>
		</div>
		
		</td>
	</tr>	
</table>
				
				
				
<script>
function addOne(id){
		var component=document.getElementById(id);
		
		component.innerHTML=component.innerHTML+"<table cellpadding='5' cellspacing='1' bgcolor='#cfe69f'><tr bgcolor='f0fcd7'>"+
		"<td><font color='f0fcd7'>王八</font>"+
		"<input type='hidden' name='anType' value='n'/><input type='hidden' name='anTextValue' value='0'/></td><td>"+
		"<input type='text' name='anSequence' size='1' />"+
		"</td><td><input type='text' name='anValue' size='1' /></td><td><input type='text' name='anOptions' /></td></tr></table>"+
		"<table><tr height='5'><td></td></tr></table>";
	}
</script>
				
				
				
				

				
				
				
				
<!-- 新增普通課程題目及選項 end -->		
		
<table width="100%" valign="top">
	<tr>
		<td>		
		新增遠距課程題目及選項		
		</td>
	</tr>	
	<tr id="eNq">
		<td>
<!-- 新增遠距課程題目及選項 start -->					
		<table cellpadding="5" cellspacing="1" bgcolor="#cfe69f" onClick="eaddOne('Ecoadd')">
			<tr bgcolor="f0fcd7">
				<td>新增</td>
				<td>順序</td>
				<td>分數</td>
				<td>題目 / 選項</td>
			</tr>
			<tr bgcolor="f0fcd7">
				<td><input type="checkBox" checked disabled />
					<input type="hidden" name="aeType" value="M" />
					
				</td>
				<td><input type="text" name="aeSequence" size="1"/></td>
				<td><input type="hidden" name="aeTextValue" size="1" value="1" />
					<input type="hidden" name="aeValue" size="1" value="0" />
					<input type="text" size="1" value="N/A" disabled />
				</td>
				<td><input type="text" name="aeOptions"/></td>
			</tr>
		</table>
		
		
		<div id="Ecoadd">
		<table><tr height="5"><td></td></tr></table>
		</div>
	
		</td>
	</tr>
</table>
<!-- 新增題目 end-->

<script>
function eaddOne(id){
		var ecomponent=document.getElementById(id);
		
		ecomponent.innerHTML=ecomponent.innerHTML+"<table cellpadding='5' cellspacing='1' bgcolor='#cfe69f'><tr bgcolor='f0fcd7'>"+
		"<td><font color='f0fcd7'>王八</font>"+
		"<input type='hidden' name='aeType' value='n'/><input type='hidden' name='aeTextValue' value='1'/></td><td>"+
		"<input type='text' name='aeSequence' size='1' />"+
		"</td><td><input type='text' name='aeValue' size='1' /></td><td><input type='text' name='aeOptions' /></td></tr></table>"+
		"<table><tr height='5'><td></td></tr></table>";
	}
</script>