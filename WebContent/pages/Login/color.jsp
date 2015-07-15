<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairlineTable" width="98%">
	<tr>
		<td class="fullColorTablePad" onClick="showObj('color');" style="cursor:pointer;">		
		<div style="float:left; padding:2 5 0 0;"><img src="images/color_swatch.gif"/></div>
		<div nowrap style="float:left;" style="float:left; padding:2 5 0 0;"><font class="gray_15">顏色選擇</font></div>	
		</td>
	</tr>	
	<tr>
		<td align="center" class="hairlineTdF">
		
		<div id="color" style="display:inline;">
		<c:import url="/pages/include/ColorSelect.jsp"/>
		</div>
		
		</td>
	</tr>
</table>