<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0"/>
	<tr>
		<td>		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>主聘科系</td>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>教師職級</td>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>本校職稱</td>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>
				<input type="button" class="gCancel" id="downcopy" onMouseOver="showHelpMessage('將時數與超時同時複製至下列所有空格', 'inline', this.id)" value="全部填滿"
				onMouseOut="showHelpMessage('', 'none', this.id)" onClick="setAll('time', 'set'), setAll('time_over', 'over_set');"/>
				</td>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>
				<input type="button" class="gCancel" id="downcopy2" onMouseOver="showHelpMessage('只將時數複製至下列所有空格', 'inline', this.id)" value="時數"
				onMouseOut="showHelpMessage('', 'none', this.id)" onClick="setAll('time', 'set')"/>
				</td>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>
				<input type="button" class="gCancel" id="downcopy2" onMouseOver="showHelpMessage('只將超時複製至下列所有空格', 'inline', this.id)" value="可超時數"
				onMouseOut="showHelpMessage('', 'none', this.id)" onClick="setAll('time_over', 'over_set');"/>
				</td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" colspan="3" align="right">				
				<input type="button" class="gGreen" value="列印" id="print"
				onMouseOver="showHelpMessage('儲存後列印', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"
				onClick="window.open('/CIS/pages/personnel/TechlimitManager/export.jsp')" />
				<input type="submit" name="method" 
				value="<bean:message key='Save'/>" 
				id="Save" class="gSubmit"
				onMouseOver="showHelpMessage('儲存以下設定', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
				<td class="hairLineTdF" nowrap>教師姓名
				</td>
				<td class="hairLineTdF"><input type="text" name="set" style="me-mode:disabled; font-size:20px;" size="4" /></td>
				<td class="hairLineTdF">
				<input type="text" name="over_set" style="me-mode:disabled; font-size:20px;" size="4"/></td>
			</tr>
			
			<c:forEach items="${empls}" var="e">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>${e.unitname}</td>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>${e.name}</td>
				<td class="hairLineTdF" style="font-size:20px;" width="100%">${e.sname}</td>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>${e.cname}</td>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>
				<input type="hidden" name="codeEmpl" />
				<input type="hidden" name="idno" value="${e.idno}" />
				<input type="hidden" name="Oid" value="${e.Oid}" />
				<input type="text" name="time" id="time" value="${e.time}" style="me-mode:disabled; font-size:20px;" size="4" />
				</td>
				<td class="hairLineTdF" style="font-size:20px;">
				<input type="text" name="time_over" id="time_over" value="${e.time_over}" style="me-mode:disabled; font-size:20px;" size="4" />
				</td>
			</tr>
			</c:forEach>
		</table>		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">		
		<input type="submit" name="method" 
		value="<bean:message key='Save'/>" 
		id="Save" class="gSubmit"
		onMouseOver="showHelpMessage('儲存以上設定', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
</table>

<script>
function setAll(name, value){
	value=document.getElementById(value).value;
	var a=document.getElementsByName(name);
	for(i=0; i<a.length; i++){
		a[i].value=value;
	}
}
</script>