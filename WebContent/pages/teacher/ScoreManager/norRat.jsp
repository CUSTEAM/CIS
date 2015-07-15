<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>平時考共10個欄位，平均值預設佔總成績30%。若您已經完成計算，您可以直接輸入<b>平時成績</b>欄位。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>預設期中考佔總成績 30%、期末考佔總成績 40%。當平時、期中、期末均有成績時，即產生<b>總成績</b>。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>變更比例按鍵按下後比例生效，不建議在<b>期末考成績輸入後</b>修改成績分配比例。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>若是您已經完成計算，您可以直接輸入總成績欄位，但成績冊必須與系統相同，格式須合乎教務處規定。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>可利用方向鍵↑↓進行游標垂直移動</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td nowrap>
							點擊 <INPUT type="submit" name="method" value="<bean:message key='EditPro'/>" class="gSubmitSmall"> 變更成績比例, 
							<input type="button" onClick="showall();" value="其餘5次" class="gGreenSmall" /> 顯示所有平時考欄位, 
							<INPUT type="submit" name="method" <c:if test="${date2!=null}">disabled</c:if> value="<bean:message key='Save' bundle="COU"/>" class="gSubmitSmall"> 儲存成績, 
							<input type="button" name="method" value="離開" onClick="location.href='/CIS/Teacher/ScoreManager.do'" class="gCancelSmall"> 不儲存離開
							</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>截止日期: 期中考${sdate1}, 期末考${sdate2}<c:if test="${sdate3!=null}">, 畢業考${sdate3}</c:if></td></tr>
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

<table class="hairLineTable" id="class${e.Oid}" width="99%">
	<tr>
		<td class="hairLineTdF" style="font-size:16;"></td>
		<td class="hairLineTdF" style="font-size:16;"></td>
		<td class="hairLineTdF" style="font-size:16;"></td>
		<td class="hairLineTdF" style="font-size:16;" nowrap><input type="text" name="p1" id="p1" class="FocusTextBlue" size="1" value="${seldpro.score1}"/>%</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap><input type="text" id="p2" name="p2" class="FocusTextBlue" size="1" value="${seldpro.score2}"/>%</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap><input type="text" id="p3" name="p3" class="FocusTextBlue" size="1" value="${seldpro.score3}"/>%</td>
		<td class="hairLineTdF" style="font-size:16;">
		<c:if test="${empty edper}">
		<input type="submit" name="method" value="<bean:message key='EditPro'/>" class="gSubmitSmall">
		</c:if>
		</font><c:if test="${!empty edper}"><font size="-2">${edper}<br>截止變更比例</font></c:if>
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF" style="font-size:16;" nowrap>學號</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>姓名</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>平時考  <input type="button" onClick="showall();" value="其餘5次" class="gGreenSmall" /></td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>平時成績</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap align="center">期中考</td>
		<td class="hairLineTdF" align="center" style="font-size:16;" nowrap align="center">期末考</td>
		<td class="hairLineTdF" align="center" style="font-size:16;" nowrap align="center">總成績</td>
	</tr>
	
	
	<c:forEach items="${students}" var="s" varStatus="c">
	<c:if test="${c.index%5==0&&c.index!=0}">	
	<tr>		
		<td class="hairLineTdF" style="font-size:16;" nowrap>學號</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>姓名</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>平時考 </td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>平時成績</td>		
		<td class="hairLineTdF" style="font-size:16;" nowrap align="center">期中考</td>
		<td class="hairLineTdF" align="center" style="font-size:16;" nowrap align="center">期末考</td>
		<td class="hairLineTdF" align="center" style="font-size:16;" nowrap align="center">總成績</td>
	</tr>
	
	</c:if>				
	<tr class="hairLineTdF">
		<td class="hairLineTdF" style="font-size:16;cursor:pointer;" id="stImage${s.student_no}" nowrap>${s.student_no}</td>
		<td class="hairLineTdF" style="font-size:16;cursor:pointer;" id="stImage${s.student_no}" nowrap>${s.student_name}</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="hidden" name="seldOid" value="${s.Oid}" />		
		<input type="text" name="score01" style="ime-mode:disabled;" id="score01${s.Oid}" value="${s.score01}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){ck(this); km(event, 'score01${students[c.index+1].Oid}', 'score01${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score02" style="ime-mode:disabled;" id="score02${s.Oid}" value="${s.score02}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score02${students[c.index+1].Oid}', 'score02${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score03" style="ime-mode:disabled;" id="score03${s.Oid}" value="${s.score03}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score03${students[c.index+1].Oid}', 'score03${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score04" style="ime-mode:disabled;" id="score04${s.Oid}" value="${s.score04}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score04${students[c.index+1].Oid}', 'score04${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score05" style="ime-mode:disabled;" id="score05${s.Oid}" value="${s.score05}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score05${students[c.index+1].Oid}', 'score05${students[c.index-1].Oid}');countScore('${s.Oid}')}"/>
		<input type="text" name="score06" style="ime-mode:disabled; display:none;" id="score06${s.Oid}" value="${s.score06}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score06${students[c.index+1].Oid}', 'score06${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score07" style="ime-mode:disabled; display:none;" id="score07${s.Oid}" value="${s.score07}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score07${students[c.index+1].Oid}', 'score07${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score08" style="ime-mode:disabled; display:none;" id="score08${s.Oid}" value="${s.score08}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score08${students[c.index+1].Oid}', 'score08${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score09" style="ime-mode:disabled; display:none;" id="score09${s.Oid}" value="${s.score09}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score09${students[c.index+1].Oid}', 'score09${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score10" style="ime-mode:disabled; display:none;" id="score10${s.Oid}" value="${s.score10}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score10${students[c.index+1].Oid}', 'score10${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		</td>		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score1" style="ime-mode:disabled;" id="score1${s.Oid}" value="${s.score1}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="km(event, 'score1${students[c.index+1].Oid}', 'score1${students[c.index-1].Oid}'); if(ck(this)){finScore('${s.Oid}');}"/>
		</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score2" style="ime-mode:disabled;" <c:if test="${date1!=null}">readonly</c:if> id="score2${s.Oid}" value="${s.score2}" class="FocusTextBlue" size="1" <c:if test="${date1==null}">onClick="cc(this.name);" onKeyUp="km(event, 'score2${students[c.index+1].Oid}', 'score2${students[c.index-1].Oid}'); if(ck(this)){finScore('${s.Oid}');}"</c:if>/>
		</td>		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score3" style="ime-mode:disabled;" <c:if test="${date2!=null}">readonly</c:if> id="score3${s.Oid}" value="${s.score3}" class="FocusTextBlue" size="1" <c:if test="${date2==null}">onClick="cc(this.name);" onKeyUp="km(event, 'score3${students[c.index+1].Oid}', 'score3${students[c.index-1].Oid}'); if(ck(this)){finScore('${s.Oid}');}"</c:if>/>
		</td>		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score" style="ime-mode:disabled;" <c:if test="${date2!=null}">readonly</c:if> id="score${s.Oid}" value="${s.score}" class="FocusTextBlue" size="1" <c:if test="${date2==null}">onClick="cc(this.name);" onKeyUp="km(event, 'score${students[c.index+1].Oid}', 'score${students[c.index-1].Oid}');"</c:if> />
		</td>
	</tr>	
	</c:forEach>	
	<tr class="hairLineTdF" align="center">
		<td class="hairLineTdF" colspan="100">
		<INPUT type="submit" name="method" <c:if test="${date2!=null}">disabled</c:if> value="<bean:message key='Save' bundle="COU"/>" class="gSubmit">
		<input type="button" name="method" value="離開" onClick="location.href='/CIS/Teacher/ScoreManager.do'" class="gCancel">
		</td>
	</tr>
</table>

<script>
var p1=parseInt(document.getElementById("p1").value);
var p2=parseInt(document.getElementById("p2").value);
var p3=parseInt(document.getElementById("p3").value);

//顯示或關閉多餘平時考
function showall(){
	var stat="inline";
	if(document.getElementsByName("score06")[0].style.display=="inline"){
		stat="none";
	}
	for(i=0; i<document.getElementsByName("score06").length; i++){	
		document.getElementsByName("score06")[i].style.display=stat;
		document.getElementsByName("score07")[i].style.display=stat;
		document.getElementsByName("score08")[i].style.display=stat;
		document.getElementsByName("score09")[i].style.display=stat;
		document.getElementsByName("score10")[i].style.display=stat;	
	}
}

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
function countScore(Oid){
	
	score01=document.getElementById("score01"+Oid).value;	
	score02=document.getElementById("score02"+Oid).value;
	score03=document.getElementById("score03"+Oid).value;
	score04=document.getElementById("score04"+Oid).value;
	score05=document.getElementById("score05"+Oid).value;
	score06=document.getElementById("score06"+Oid).value;
	
	score07=document.getElementById("score07"+Oid).value;
	score08=document.getElementById("score08"+Oid).value;
	score09=document.getElementById("score09"+Oid).value;
	score10=document.getElementById("score10"+Oid).value;
	
	counTmp=0;
	score1=0;
	
	if(score01!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score01)}
	if(score02!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score02)}
	if(score03!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score03)}
	if(score04!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score04)}
	if(score05!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score05)}
	if(score06!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score06)}
	
	if(score07!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score07)}
	if(score08!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score08)}
	if(score09!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score09)}
	if(score10!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score10)}
	
	
	if(counTmp>0){
		score1=score1/counTmp;
		document.getElementById("score1"+Oid).value=score1;//寫入平時成績
		
		finScore(Oid);
	}else{
		document.getElementById("score1"+Oid).value="";
	}	
}

function finScore(Oid){
	score=0;
	score1=document.getElementById("score1"+Oid).value;
	score2=document.getElementById("score2"+Oid).value;
	score3=document.getElementById("score3"+Oid).value;
	
	if(score1!=""){score=score+parseInt(score1)*(p1/100)}
	if(score2!=""){score=score+parseInt(score2)*(p2/100)}
	if(score3!=""){score=score+parseInt(score3)*(p3/100)}
	
	if(score1!=""&&score2!=""&&score3!=""){
		document.getElementById("score"+Oid).value=Math.round(score*100)/100;
	}else{
		document.getElementById("score"+Oid).value="";
	}
}
</script>
