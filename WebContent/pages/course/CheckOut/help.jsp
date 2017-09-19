<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" id="alert" style="display:none">
	<tr>
		<td>
			
		<table width="99%" class="hairLineTable">
			<tr>
				<td id="helpMsg" class="hairLineTdF">
				&nbsp;&nbsp;<img src="images/24-book-green-message.png">
				</td>
			</tr>
		</table>
				
		</td>
	</tr>	
</table>

<script>
	function showHelp(type){
		document.getElementById('helpMsg').innerHTML=('');
		if(type=='CheckSeld'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('&nbsp;學生選課查核的時間較長');
			//document.getElementById('pif').style.display='inline';
		}
		
		if(type=='CheckThour'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('&nbsp;查詢所有教職員任課時數，這項查核無須指定「班級」欄');
		}
		
		if(type=='ReSelected'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('&nbsp;查詢學生過去所有已得學分是否有重複，這項查核可以忽略「學期」欄');
		}
		
		if(type=='ReSelectedNow'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('&nbsp;查詢學生本學期選課學分與過去已得學分是否有重複');
			//document.getElementById('pif').style.display='inline';
		}		

		if(type=='CheckTech'){
			closeAll();
			//document.getElementById('pif').style.display='inline';
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('&nbsp;不包含一科目多教師');
		}

		if(type=='CheckGist'){		
			closeAll();
			//document.getElementById('helpMsg').innerHTML=('&nbsp;查核範圍不限, 查核完成後，可利用「未編輯」欄位、或「字數」等方式進行排序<br>'+
			//'依照規定排除科目代碼: 50000, T0001, T0002, TB070, TA620, TCAM0, TN0G0, TEB00, GE008 , GE009 , GC009 , GC010, GC011, GC012 , TG990, TM121, '+
			//'TM122, TN0G2, TB072, TA622, TA621, TB071, TB072, TN0G1,TN0G2, 以及科目名稱中包含 「論文」, 「書報」, 「專題」, 「實習」的課程');
			//document.getElementById('pif').style.display='inline';
			//document.getElementById('schoolType').style.display='inline';
			//document.getElementById('departLess').style.display='inline';
			//document.getElementById('alert').style.display='inline';
			document.getElementById('alert').style.display='none';
			//document.getElementById('didno').style.display='inline';
		}

		if(type=='CheckCredit'){
			closeAll();
			document.getElementById('helpMsg').innerHTML=('&nbsp;請選擇查核範圍, 並且在彈出的方塊中輸入下限數字 (目前尚未將各學制年級學分下限建檔儲存)');
			//document.getElementById('pif').style.display='inline';
			//document.getElementById('checkCredit').style.display='inline';
			document.getElementById('alert').style.display='inline';
			document.getElementById('point').style.display='inline';

		}

		if(type=='CheckSelimit'){
			closeAll();
			//document.getElementById('pif').style.display='inline';
			document.getElementById('alert').style.display='inline';
			document.getElementById('point').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('&nbsp;請選擇查核範圍，範圍至少為一種學制，按下查核後會產生範圍內目前超過上限的課程清單');
		}
		
		if(type=='coansw'){
			closeAll();
			document.getElementById('sidno').style.display='inline';
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('&nbsp;請選擇下方右側彈出的部制選單，<font color=red>此項查核位在上方的 開課學期/開課班級 下拉選單無效</font><br>'+
			'&nbsp;按下"查核"按鈕得到清單後，點選excel或word圖示，在報表最下方會得到統計值');
			
		}
		
		if(type=='tripos'){
			closeAll();
			document.getElementById('helpMsg').innerHTML=('&nbsp;請選擇上方四層式下拉選單<br>&nbsp;按下"查核"按鈕得到清單後，點選excel或word圖示可得到報表');
			//document.getElementById('pif').style.display='inline';
			document.getElementById('alert').style.display='inline';
		}
		
		if(type=='ListSeld4Class'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('ListSeld4Class').style.display='inline';			
			document.getElementById('helpMsg').innerHTML=('可以透過整班或是單一學生執行查核');
			//document.getElementById('pif').style.display='inline';
		}
		
		if(type=='ListCounseling'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('期中考不及格同學，只有任課老師填寫輔導紀錄，資料只取「預警補救教學」');
			//document.getElementById('pif').style.display='inline';
		}
		
		if(type=='CheckPay'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('CheckPay').style.display='inline';			
			document.getElementById('helpMsg').innerHTML=('填滿所有欄位，按下"查核"後得到加選清單，點選報表名稱即可');
			//document.getElementById('pif').style.display='inline';
		}
		
		if(type=='CheckHeight'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('&nbsp;按下"查核"按鈕得到低修高清單，點選excel或word圖示可得到報表');
			//document.getElementById('pif').style.display='inline';
		}
				
		if(type=='CheckRoom'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('按下"查核"按鈕得到教室衝堂清單，點選excel或word圖示可得到報表');
			//document.getElementById('pif').style.display='inline';
		}
		
		if(type=='CheckClass'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('按下"查核"按鈕得到班級開課衝堂清單，點選excel或word圖示可得到報表');
			//document.getElementById('pif').style.display='inline';
		}
		
		if(type=='SummerOpen'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('按下"查核"按鈕得到查詢範圍內 ( 二年制2年內, 四年制4年內, 選別不拘 ) 課程不及格達5人以上的課程<br><b>這項查核可以忽略"學期"欄</b>、這項查核會顯示選別以供各種用途');
			//document.getElementById('pif').style.display='inline';
		}
		
		if(type=='ReOpenHist'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('按下"查核"按鈕得到查詢範圍內的學程 ( 二年制2年內, 四年制4年內 ) 相同學程課程代碼重複開課，這項查核可以忽略"學期"欄');
			//document.getElementById('pif').style.display='inline';
		}
		
		if(type=='CheckSport'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('按下"查核"按鈕得到查詢範圍內沒選體育的學生');
			//document.getElementById('pif').style.display='inline';
		}
		if(type=='CheckNoname'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('學生基本資料若遭到不正常刪除時, 會遺留許多無意義的選課, 請期初期末定期檢查');
			//document.getElementById('pif').style.display='inline';
		}
		if(type=='CheckGeneral'){
			closeAll();
			document.getElementById('alert').style.display='inline';
			document.getElementById('helpMsg').innerHTML=('按下"查核"按鈕得到查詢範圍內沒選通識的學生');
			//document.getElementById('pif').style.display='inline';
		}			
	}
</script>

<script>
function closeAll(){
			//document.getElementById('checkCredit').style.display='none';
			//document.getElementById('checkCredit').style.display='none';
			//document.getElementById('schoolType').style.display='none';
			//document.getElementById('sidno').style.display='none';
			document.getElementById('point').style.display='none';
			//document.getElementById('didno').style.display='none';
			//document.getElementById('departLess').style.display='none';
			//document.getElementById('CheckPay').style.display='none';
			document.getElementById('ListSeld4Class').style.display='none';	
}
</script>