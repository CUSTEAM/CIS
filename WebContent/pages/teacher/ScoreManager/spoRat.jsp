<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<script src="/CIS/pages/include/decorate.js"></script>
<input type="hidden" name="Dtime_oid" id="Dtime_oid" value="${Dtime_oid}"/>
<input type="hidden" name="type" id="type" value="${type}"/>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
			<table >
				<tr>
					
					<td width="100%" class="gray_15">
					

					<table id="help" width="100%">
						<tr>
							<td >
							
							<table width="100%">
							
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>若是您已經完成計算，您可以跳過所有計算過程直接輸入學期成績欄位，但成績冊必須與系統相同，格式須合乎教務處規定。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>可利用方向鍵↑↓進行游標垂直移動</td></tr>
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

<table class="hairLineTable" id="class${e.Oid}" width="99%">	
	<tr>
		<td class="hairLineTdF" style="font-size:16;" nowrap>您正在編輯 ${csinfo.ClassName}, ${csinfo.chi_name} 的學生成績</td>
	</tr>
</table>
			
			
			
			
			
			
			
<table class="hairLineTable">
				
	<tr>
		<td align="center" class="hairLineTdF">學號</td>
		<td align="center" class="hairLineTdF">姓名</td>
		<td align="center" class="hairLineTdF">術科成績(50%)</td>
		<td align="center" class="hairLineTdF">平時成績(30%)</td>
		<td align="center" class="hairLineTdF">學科成績(20%)</td>
		<td align="center" class="hairLineTdF">學期成績(100%)</td>
	</tr>



	<c:forEach items="${students}" var="s" varStatus="c">
	
	<tr>
		<td align="center" class="hairLineTdF">
		<input type="hidden" size="2" name="seldOid" value="${s.Oid}"/>
		<input type="hidden" value="${s.student_no}" name="studentNo"/>
		<input type="hidden" value="${s.student_no}" name="${m.dtimeOid}studentNo" />${s.student_no}</td>
		
		<td align="center" class="hairLineTdF">${s.student_name}</td>
		
		<td align="center" class="hairLineTdF">
		<input type="text" size="2" name="score1" value="${s.score1}" id="${s.Oid}score1" style="ime-mode:disabled;" class="FocusTextBlue"
		onClick="cc(this.name);" onKeyUp="if(ck(this)){ck(this); km(event, '${students[c.index+1].Oid}score1', '${students[c.index-1].Oid}score1'); sum('${s.Oid}score1', '${s.Oid}score2', '${s.Oid}score3', '${s.Oid}score')}"
		
		/>
		</td>
		
		<td align="center" class="hairLineTdF">
		<input type="text" size="2" name="score2" value="${s.score2}" id="${s.Oid}score2" style="ime-mode:disabled;" class="FocusTextBlue" onClick="cc(this.name);" 
		onKeyUp="if(ck(this)){km(event, '${students[c.index+1].Oid}score2', '${students[c.index-1].Oid}score2'); sum('${s.Oid}score1', '${s.Oid}score2', '${s.Oid}score3', '${s.Oid}score')}"/>
		</td>
		
		<td align="center" class="hairLineTdF">
		<input type="text" size="2" name="score3" value="${s.score3}" id="${s.Oid}score3" style="ime-mode:disabled;" class="FocusTextBlue" onClick="cc(this.name);" 
		onKeyUp="if(ck(this)){km(event, '${students[c.index+1].Oid}score3', '${students[c.index-1].Oid}score3'); sum('${s.Oid}score1', '${s.Oid}score2', '${s.Oid}score3', '${s.Oid}score')}"/>
		</td>
		
		<td align="center" class="hairLineTdF">
		<input type="text" size="2" name="score" value="${s.score}" id="${s.Oid}score" style="ime-mode:disabled;" 
		onKeyUp="if(ck(this)){km(event, '${students[c.index+1].Oid}score', '${students[c.index-1].Oid}score');}"
		class="FocusTextBlue" onClick="cc(this.name);" />
		</td>
	</tr>
	</c:forEach>
	<tr class="hairLineTdF" align="center">
		<td class="hairLineTdF" colspan="100">
		<INPUT type="submit" name="method" <c:if test="${date2!=null}">disabled</c:if> value="<bean:message key='OK'/>" class="gSubmit">
		<input type="button" name="method" value="離開" onClick="location.href='/CIS/Teacher/ScoreManager.do'" class="gCancel">
		</td>
	</tr>
</table>
<script>
//補捉方向鍵和enter鍵
function km(e, nextObj, precObj){
	if(e.keyCode==38){document.getElementById(precObj).focus();}
	if(e.keyCode==40){document.getElementById(nextObj).focus();}
}

function cc(name){
	for(i = 0; current = document.getElementsByTagName('input')[i]; i++) {
		if(current.type=="text")
    	current.className="FocusTextBlue";
	}
	
	for(i=0; i<document.getElementsByName(name).length; i++){
		document.getElementsByName(name)[i].className="FocusTextRed";
	}
}

var re = /^[0-9]+$/;
function ck(obj){
	if(obj.value=="")return true;
 	if (!re.test(obj.value)){obj.value=""; return false;}
 	if (parseInt(obj.value)>100){obj.value=""; return false;}
 	return true;
}

function sum(score1, score2, score3, score){
	score1=document.getElementById(score1);
	score2=document.getElementById(score2);
	score3=document.getElementById(score3);
	score=document.getElementById(score);
	score.value=(score1.value*0.5)+(score2.value*0.3)+(score3.value*0.2);
}
</script>
