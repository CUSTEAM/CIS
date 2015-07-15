<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Registration/CheckGraduate" method="post" onsubmit="init('審核中, 請稍後')">
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/folder_bell.gif">
				</td>
				<td align="left">
				&nbsp;畢業資格審查&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 標題 end -->

<c:if test="${mode==null}">
<!-- 搜尋列 start -->
	<tr>
		<td>
		
		<table>
			<tr>
				<td>
				<table class="hairlineTable">
					<tr>
						<td align="center" class="hairlineTdF" nowrap>班級</td>
						<td class="hairlineTd">
				
						<input type="text" id="departClass" name="classNo" value="${CheckGraduateForm.map.classNo}"
				 		 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 		 onkeyup="if(this.value.length>1)getAny(this.value, 'departClass', 'classLess', 'Class', 'no')"
				 		 onMouseOver="showHelpMessage('請輸入完整班級代碼, 如164341', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
				 		 onclick="this.value='', document.getElementById('classLess').value=''" /><input type="text" name="className" 
				 		 id="classLess" value="${CheckGraduateForm.map.className}" size="16"
				 		  onkeyup="getAny(this.value, 'classLess', 'departClass', 'Class', 'name')"
				 		  onclick="this.value='', document.getElementById('departClass').value=''"/>
				 		 </td>
				 		 <td align="center" class="hairlineTdF">
				 		 <img src="images/16-exc-mark.gif" />
						 </td>
						
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairlineTable">
					<tr bgcolor="#f0fcd7">
						<td align="center" class="hairlineTdF">學號</td>
				
						<td class="hairlineTd">
				
						<input type="text" name="studentNo" id="studentId" size="16" style="ime-mode:disabled" autocomplete="off"
						 value="${CheckGraduateForm.map.studentNo}"
						 onMouseOver="showHelpMessage('請輸入學號, 若您是用貼上請動一下方向鍵以便自動完成班級代碼', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
						 onkeyup="if(this.value.length>4)getAny(this.value, 'studentId', 'studentName', 'stmd', 'no')"
						 onclick="this.value='', document.getElementById('studentName').value='', 
						 document.getElementById('departClass').value='', document.getElementById('classLess').value=''"/><input type="text" 
						 value="${CheckGraduateForm.map.studentName}" onMouseOver="showHelpMessage('請輸入姓名, 若您是用貼上請動一下方向鍵以便自動完成學號', 
						 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
						 
						  onkeyup="if(this.value.length>1)getAny(this.value, 'studentName', 'studentId', 'stmd', 'name')"
						  onkeydown="document.getElementById('Acsname').style.display='none';" autocomplete="off"
						  onclick="this.value='', document.getElementById('studentId').value='', 
						  document.getElementById('departClass').value='', document.getElementById('classLess').value=''"
						  name="studentName" id="studentName" size="8" />
						 </td>
				 		 <td width="30" align="center" class="hairlineTdF">
				 		 <img src="images/16-exc-mark.gif" />
						</td>
					</tr>
				</table>
				
				</td>
			
			</tr>
			
		</table>
		
		</td>
	<tr>
<!-- 搜尋列 end -->
	<tr>
		<td>
		
<!-- 幫助列 start -->
<table width="100%" align="center" id="help" style="display:none;">
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">	
							
				<OL>
				
					<li>指定一個班級或一名學生，若產生矛盾系統會自動排除
					<li>查看學生必修、選修的數字是否達到標準						
						
					<ul>
						<li>若不符標準可用手動折抵，做過的折抵會留存記錄，下次無需再折抵
						<li>若無本校成績可折抵請將待折抵的方塊中「轉學抵免」勾選，系統自動建立該年度抵免(無分數)
						<li>若學生先修或其它狀況可在下方「查看歷年成績」任何地方加上註記，以便日後折抵
					</ul>
					
					
					
					
					
					<li>準畢業生只要將「審核確認」勾選即可
					<li>確定畢業只要將「審核確認」旁邊的框框按一下即可自動產生「畢業號碼」
					<li>按下最底端的「繼續」按鈕將會做一次確認，若學生已取得「畢業號」將會自動將成績、操行、缺曠...等建檔，
					同時電子郵件通知圖書館，最後將選課和加退選歷程、CIS系統帳號...等相關資料清除....很多很多，最後將學生移至離校名單。
								
				</OL>
				
				
				
				
							
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
<!-- 幫助列 end -->
		
		
		
		</td>
	</tr>


	
	<tr>
		<td class="fullColorTable" align="center">
		<table>
			<tr>
				<td>
					<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('設定審查班級或單一學生', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
										
					<input type="submit" name="method" 
					value="<bean:message key='Clear'/>" 
					id="Clear" class="gCancle"
					onMouseOver="showHelpMessage('清除畫面上的所有資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
					<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
					
					<c:if test="${graList!=null}">
					<input type="submit" name="method" 
					value="<bean:message key='CheckGraduate'/>" 
					id="CheckGraduate" class="gGreen"
					onMouseOver="showHelpMessage('將審查符合畢業資格的學生批次移轉至畢業檔<br>包含移轉在校期間的各種資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
					</c:if>
				</td>
			</tr>
		</table>
		</td>
	</tr>


<!-- 查詢結果-->
<c:if test="${graList!=null}">
	<tr>
		<td>
		<c:import url="/pages/registration/CheckGraduate/list.jsp"/>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<table>
			<tr>
				<td>
					<INPUT type="submit" name="method" value="<bean:message key='Continue'/>" class="gSubmit">
				</td>
			</tr>
		</table>
		</td>
	</tr>
</c:if>
<!-- 查詢結果 -->
</c:if>

<!-- 確認模式 start -->
<c:if test="${mode=='gra'||mode=='graBatch'}">
	<tr>
		<td>		
		<c:import url="/pages/registration/CheckGraduate/cofirmGraduate.jsp"/>
		</td>
	</tr>
	
	<tr>
		<td align="center" width="100%" class="fullColorTable">
		<table>
			<tr>
				<td>
					<INPUT type="submit" name="method" value="<bean:message key='Complete'/>" class="gSubmit">
						   
						   <INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="gCancle">
				</td>
			</tr>
		</table>
		</td>
	</tr>
</c:if>
<!-- 確認模式 end -->	
</html:form>	
</table>

<table style="display:none" width="100%">
	<tr>
		<td id="allDtime">
		
		<c:forEach items="${allDtime}" var="al">		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTd" onClick="showObj('dtime${al.school_year}${al.school_term}');" style="cursor:pointer;">
				${al.school_year}學年, 第 ${al.school_term}學期應修課程
				</td>
			</tr>
		</table>
		<c:set var="countDCredit" value="0" />
		<table class="hairLineTable" width="99%" id="dtime${al.school_year}${al.school_term}" style="display:none;">
			<c:forEach items="${al.savedtime}" var="all">
			<c:set var="countDCredit">${countDCredit+all.credit}</c:set>
			<tr bgcolor="#ffffff">
				<td class="hairLineTdF">${all.chi_name}</td>
				<td class="hairLineTdF">${all.cscode}</td>
				<td class="hairLineTdF">${all.opt}</td>
				<td class="hairLineTdF">${all.credit}</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="8" class="hairLineTd" align="right">
				以上小計 <b>${countDCredit}</b>學分
				</td>
			</tr>	
		</table>		
		</c:forEach>
		
		</td>
	</tr>	
</table>
<c:import url="/pages/include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<script>
	function clickCou(up){
		if(document.getElementById("check"+up).value==''){
		
			document.getElementById("check"+up).value='1';
			document.getElementById("up1"+up).className ="hairLineTd";
			document.getElementById("up2"+up).className ="hairLineTd";
			document.getElementById("down"+up).className ="hairLineTd";			
			
		}else{
			document.getElementById("check"+up).value='';
			document.getElementById("up1"+up).className ="hairLineTdF";
			document.getElementById("up2"+up).className ="hairLineTdF";
			document.getElementById("down"+up).className ="hairLineTdF";
		}
	}	
</script>

<script>
function showMyshold(id){
	
	if(document.getElementById(id).style.display=='none'){
		document.getElementById(id).style.display='inline';
		document.getElementById(id).innerHTML=document.getElementById('allDtime').innerHTML;
		}else{
		document.getElementById(id).style.display='none';
	}
}
</script>

<script>
function minMax(id){
	if(document.getElementById(id).rows==20){
		document.getElementById(id).rows=1;
		document.getElementById(id).cols=76;
	}else{
		document.getElementById(id).rows=20;
		document.getElementById(id).cols=76;
	}
		
}

function checkGradeForOne(studentNO){
	
	if(document.getElementById('aGrade'+studentNO).value==''){
		document.getElementById('aGrade'+studentNO).value=studentNO;
		//document.getElementById('gradeNo'+studentNO).style.display='inline'			
	
	}else{
		document.getElementById('aGrade'+studentNO).value='';
		//document.getElementById('gradeNo'+studentNO).style.display='none'
	}
}
</script>