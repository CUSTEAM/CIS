<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>








<table cellspacing="0" cellpadding="0" width="100%">
<c:forEach items="${allShifts}" var="a">
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">班別代碼</td>
				<td style="font-size:20px;font-weight:bold;" class="hairLineTdF" width="30" align="center" nowrap="nowrap" >
				${a.id}<input type="hidden" name="id" value="${a.id}"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">班別名稱</td>
				<td class="hairLineTdF"><input type="text" name="name" size="24" value="${a.name}" onKeyUp="checkDel(this.value, 'del${a.id}')"/></td>
				<td class="hairLineTdF" id="del${a.id}" width="30" align="center" style="display:none;"><img src="images/icon/action_stop.gif"/></td>
			</tr>
		</table>
<script>
function checkDel(name, id){
	if(name==""){
		showObj(id);
	}
}
</script>
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable"  width="99%" align="left">
			<tr>
				<td class="hairLineTdF" colspan="2">星期一</td>
				<td class="hairLineTdF" colspan="2">星期二</td>
				<td class="hairLineTdF" colspan="2">星期三</td>
				<td class="hairLineTdF" colspan="2">星期四</td>
				<td class="hairLineTdF" colspan="2">星期五</td>
				<td class="hairLineTdF" colspan="2">星期六</td>
				<td class="hairLineTdF" colspan="2">星期日</td>
			</tr>
			<tr>
				<%for(int i=0; i<7; i++){ %>
				<td class="hairLineTdF">應上</td>
				<td class="hairLineTdF">應下</td>
				<%} %>
			</tr>
			<tr>
				
				<td class="hairLineTdF"><input type="text" name="in1" size="5" class="smallInput" value="${a.in1}" disabled/></td>
				<td class="hairLineTdF"><input type="text" name="out1" size="5" class="smallInput" value="${a.out1}" disabled/></td>
				
				<td class="hairLineTdF"><input type="text" name="in2" size="5" class="smallInput" value="${a.in2}" disabled/></td>
				<td class="hairLineTdF"><input type="text" name="out2" size="5" class="smallInput" value="${a.out2}" disabled/></td>
				
				<td class="hairLineTdF"><input type="text" name="in3" size="5" class="smallInput" value="${a.in3}" disabled/></td>
				<td class="hairLineTdF"><input type="text" name="out3" size="5" class="smallInput" value="${a.out3}" disabled/></td>
				
				<td class="hairLineTdF"><input type="text" name="in4" size="5" class="smallInput" value="${a.in4}" disabled/></td>
				<td class="hairLineTdF"><input type="text" name="out4" size="5" class="smallInput" value="${a.out4}" disabled/></td>
				
				<td class="hairLineTdF"><input type="text" name="in5" size="5" class="smallInput" value="${a.in5}" disabled/></td>
				<td class="hairLineTdF"><input type="text" name="out5" size="5" class="smallInput" value="${a.out5}" disabled/></td>
				
				<td class="hairLineTdF"><input type="text" name="in6" size="5" class="smallInput" value="${a.in6}" disabled/></td>
				<td class="hairLineTdF"><input type="text" name="out6" size="5" class="smallInput" value="${a.out6}" disabled/></td>
				
				<td class="hairLineTdF"><input type="text" name="in7" size="5" class="smallInput" value="${a.in7}" disabled/></td>
				<td class="hairLineTdF"><input type="text" name="out7" size="5" class="smallInput" value="${a.out7}" disabled/></td>
				
			</tr>			
			
		</table>
		
		
		
		</td>
	</tr>
	<tr>
		<td>
		<hr class="myHr"/>
		</td>
	</tr>
</c:forEach>	
	
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Modify'/>" 
					id="Modify" class="gSubmit"
					onMouseOver="showHelpMessage('修改以上所做的變更', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		
		
		</td>
	</tr>
</table>
