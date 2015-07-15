<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/24-tag-pencil.png">
				</td>
				<td align="left">
				&nbsp;成績輸入 (暑修課程)&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<html:form action="/Teacher/SummerRat" method="post" onsubmit="init('儲存中, 請稍後')">
	<tr>
		<td>
			
		<table style="cursor:pointer;" width="99%" class="hairLineTable">
			<tr>
				<td width="100%" onclick="if(document.getElementById('help').style.display=='none'){document.getElementById('help').style.display='inline'
				}else{document.getElementById('help').style.display='none'}" class="hairLineTdF">				
				<table><tr><td><img src="images/icon/icon_info.gif"></td><td>點擊此處可以查看說明</td></tr></table>
				<table id="help" style="display:none" width="100%">
					<tr>
						<td>
						1. 點擊課程或班級名稱的<font color="red">任意空白處</font>開始對該班學生進行評分，展開後點擊則會收回(輸入的資料不受影響)<br>
						2. 平時考、平時、期中、期末成績為<font color="red">選擇性</font>輸入欄位，如有鍵入資料則會進行相關運算，也可以選擇直接鍵入學期成績<br>
						3. 將頁面捲軸移至最下方點擊<font color="red">儲存</font>即完成評分<br>
						4. 評分完畢<font color="red">儲存後</font>點擊 <img src="images/ico_file_excel.png" border="0"> excel, <img src="images/ico_file_word.png" border="0"> word 可下載成績冊以供簽署<br>
						<br>
						<font color="red">請注意</font>: 校務資訊系統會在頁面閒置過久時自動登出，請儘可能在告一段落時按一下"<font color="red">儲存</font>" 以避免資料需要重複輸入的問題
						
						<table><tr><td><img src="images/icon/icon_info_exclamation.gif"></td><td>點擊此處可以關閉說明</td></tr></table>
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
<c:if test="${empty summerStudents}">
	<tr>
		<td>
					
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				<table width="100%">
					<tr>
						<td>
						&nbsp;&nbsp;<img src="images/icon/action_stop.gif">
						</td>
						<td align="left" width="100%">
						您沒有教授暑修課程...
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</c:if>	
	
	
	
	
	
	
	<tr>
		<td>
		<%@ include file="SummerRat/list.jsp"%>
		</td>
	</tr>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
	
	
	






		
	</html:form>
</table>

<script>
	function count(sc1, sc2, sc3, sc4, medScore, endScore, freeScore, score){
		
		sc1v=document.getElementById(sc1).value;
		sc2v=document.getElementById(sc2).value;
		sc3v=document.getElementById(sc3).value;
		sc4v=document.getElementById(sc4).value;
		
		medScorev=document.getElementById(medScore).value;
		endScorev=document.getElementById(endScore).value;
		freeScorev=document.getElementById(freeScore).value;
		scorev=document.getElementById(score).value;
		
		tmp=0;
		if(sc1v!=""){
			tmp++;
		}
		if(sc2v!=""){
			tmp++;
		}
		if(sc3v!=""){
			tmp++;
		}
		if(sc4v!=""){
			tmp++;
		}
		
		if(tmp==0){
			tmp=1;
		}
		
		document.getElementById(freeScore).value=((sc1v*1+sc2v*1+sc3v*1+sc4v*1)/tmp);
	
	}
</script>

<script>
	function countAll(medScore, endScore, freeScore, score){
		
		medScorev=document.getElementById(medScore).value;
		endScorev=document.getElementById(endScore).value;
		freeScorev=document.getElementById(freeScore).value;
		
		
		
		document.getElementById(score).value=((medScorev*0.3)+(freeScorev*0.3)+(endScorev*0.4));
	
	}
function showTable(tableId){
	if(document.getElementById(tableId).style.display=='none'){
		document.getElementById(tableId).style.display='inline';
	}else{
		document.getElementById(tableId).style.display='none';
	}
}
</script>
