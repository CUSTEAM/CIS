<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>


<!-- 快速搜尋 start -->
<br>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr style="cursor:pointer;">
  				<td width="10" align="left" nowrap>
      				<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap onClick="showMod('fast')">
    				<img src="images/folder_find.gif" id="searchNorm" 
    				onMouseOver="showHelpMessage('點擊此處設定快速搜尋條件', 'inline', this.id)" 
				 	onMouseOut="showHelpMessage('', 'none', this.id)">
    			</td>
    			<td nowrap style="cursor:pointer;">
    				快速搜索&nbsp;
    			</td>
    			<td width="100%" align="left">
      				<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0" width="100%" id="fast" style="display:none;">
	<tr>
		<td align="left">
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
					開課班級
				</td>
				<td class="hairLineTd">
					<input type="text" size="6"/><input type="text" size="12"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
					課程代碼
				</td>
				<td class="hairLineTd">
					<input type="text" size="6"/><input type="text" size="12"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
<!-- 快速搜尋 end -->




<!-- 進階搜尋 start -->
<br>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		
		<table width="100%" cellpadding="0" cellspacing="0" onClick="showMod('expert')">
  			<tr style="cursor:pointer;">
  				<td width="10" align="left" nowrap>
      				<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    				<img src="images/folder_explore.gif" id="searchExpert" 
    				onMouseOver="showHelpMessage('點擊此處設定進階搜尋條件', 'inline', this.id)" 
				 	onMouseOut="showHelpMessage('', 'none', this.id)">
    			</td>
    			<td nowrap style="cursor:pointer;">
    				進階搜索&nbsp;
    			</td>
    			<td width="100%" align="left">
      				<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>	
		
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0" width="100%" id="expert" style="display:none;">
	<tr>
		<td align="left">
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
					開課班級
				</td>
				<td class="hairLineTd">
					<input type="text" size="6"/><input type="text" size="12"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
					課程代碼
				</td>
				<td class="hairLineTd">
					<input type="text" size="6"/><input type="text" size="12"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
<!-- 進階搜尋 end -->





<!-- 歷史搜尋 start -->
<br>
<table cellspacing="0" cellpadding="0" width="100%" onClick="showMod('expert')">
	<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr style="cursor:pointer;">
  				<td width="10" align="left" nowrap>
      				<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    				<img src="images/icon/folder_database.gif" id="searchHist" 
    				onMouseOver="showHelpMessage('點擊此處設定歷史搜尋', 'inline', this.id)" 
				 	onMouseOut="showHelpMessage('', 'none', this.id)">
    			</td>
    			<td nowrap style="cursor:pointer;">
    				歷年資料&nbsp;
    			</td>
    			<td width="100%" align="left">
      				<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0" width="100%" id="hist" style="display:none;">
	<tr>
		<td align="left">
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
					開課班級
				</td>
				<td class="hairLineTd">
					<input type="text" size="6"/><input type="text" size="12"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
					課程代碼
				</td>
				<td class="hairLineTd">
					<input type="text" size="6"/><input type="text" size="12"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
<!-- 歷史搜尋 end -->

<script>
//顯示搜尋模式
function showMod(id){
	
	document.getElementById('fast').style.display="none";
	document.getElementById('expert').style.display="none";
	document.getElementById('hist').style.display="none";
	
	document.getElementById(id).style.display="inline";

}

</script>