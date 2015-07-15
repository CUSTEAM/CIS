<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<table class="hairlineTable" width="98%">
	<tr>
		<td class="fullColorTablePad" style="cursor:pointer;">		
		<div style="float:left; padding:2 5 0 0;"><img src="images/icon/security-lock.gif"/></div>
		<div nowrap style="float:left;" style="float:left; padding:2 5 0 0;"><font class="gray_15">家長登入</font></div>	
		</td>
	</tr>
	
	
	<tr>
		
		<td align="left" class="hairlineTd">
	
		<table align="left">
		<form action="/CIS/InetLogin4Parent.do" method="post" onsubmit="init('驗證中, 請稍後...')">
  			<tr>
  				<td>貴子弟身分證字號 <img src="/CIS/pages/images/icon/icon_info.gif" id="pnameHelp" style="cursor:pointer;" 
  				onMouseOver="showHelpMessage('貴子弟身分證字號, 英文不分大小寫', 'inline', this.id);" 
				onMouseOut="showHelpMessage('', 'none', this.id);"/></td>
  				<td>貴子弟出生日期  <img src="/CIS/pages/images/icon/icon_info.gif" id="ppassHelp" style="cursor:pointer;" 
  				onMouseOver="showHelpMessage('貴子弟出生日期,<br>例如:<br>民國88年8月8日出生<br>則輸入880808', 'inline', this.id);" 
				onMouseOut="showHelpMessage('', 'none', this.id);"/></td>
  			</tr>
  			<tr>
  				<td>
  				<input type="text" class="username" name="idno" size="18" value="" id="pusername" value="" />
		        </td>
  				<td><input type="password" class="password" class="Pass" name="birthdate" size="18" id="ppassword" value=""/></td>
  			</tr>
  			<tr>
  				<td colspan="2">
  				
     			</td>
  			</tr>
  			<tr>
  				
  				<td colspan="2">
  				<input type="submit" value="家長登入" class="gGreen"/>
  				
  				</td>
  				
  			</tr>
		</form>
		</table>
		
		</td>
	</tr>
</table>