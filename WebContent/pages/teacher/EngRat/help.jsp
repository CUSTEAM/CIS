<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
			<table >
				<tr>
					<td valign="top" width="30" align="center"><img src="images/icon_info.gif"></td>
					<td width="100%" onclick="showObj('help')" style="cursor:pointer;" class="gray_15">
					&nbsp;<bean:message key="EngRat.help.click" bundle="COU"/> 

					<table id="help" style="display:inline" width="100%">
						<tr>
							<td >
							<!-- bean:message key="EngRat.help" bundle="COU"/-->
							<table width="100%">
							<tr><td><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">線上測驗共4個(滿分 240)欄位, 輸入任一欄立即自動運算。如果不滿意自動運算的結果，或是您已經完成計算，您可以跳過運算過程直接輸入<b>平時考</b>欄位。</td></tr>
							<tr><td><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">平時成績共6個欄位, 輸入任一欄立即自動運算，佔總成績30%。如果不滿意自動運算的結果，或是您已經完成計算，您可以跳過運算過程直接輸入<b>平時考</b>欄位。</td></tr>
							<tr><td><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">補救教學佔總平均 15%, 倘若沒有補救教學請留空白, 則不會進行15%運算。依規定若補救教學造成不及格將以60計算，但是補救教學得0分則仍然計算。(注意:<b>補救教學0分有可能會造成不及格</b>)</td></tr>
							<tr><td><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">活動 欄位的輸入的分數將會直接影響 總成績。</td></tr>
							<tr><td><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">期中考佔總成績 30%。</td></tr>
							<tr><td><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">期末考佔總成績 40%，<b>※TOEIC分數由0~225共分14階，輸入成績會佔期末考成績50%，無TOEIC測驗請留空白</b>。</td></tr>
							<tr><td><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15"><b>如果不滿意以上自動運算的結果，或是您已經完成計算，<b>您可以跳過所有運算過程直接輸入總成績欄位。</td></tr>
							<tr><td><img src="images/icon/exclamation.gif" /></td><td class="gray_15"><b>全班的期末考成績欄位輸入完成後，將會自動在一般成績系統上註明「成績已上傳」。</b></td></tr>
							<tr><td><img src="images/icon/exclamation.gif" /></td><td class="gray_15"><b>英文成績系統與一般成績系統計算方式完全不同，使用英文成績系統儲存之後請不要重複執行一般成績系統。</b></td></tr>
							
							</table>
							</td>
						</tr>
					</table>

					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<c:if test="${empty engRats}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
		
		<table>
			<tr>
				<td>&nbsp;&nbsp;<img src="images/action_stop.gif"></td>
				<td class="gray_15">您未教授英語課程</td>
			</tr>
		</table>					
		
		</td>
	</tr>
</table>
</c:if>