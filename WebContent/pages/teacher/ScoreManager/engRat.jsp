<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<input type="hidden" name="Dtime_oid" id="Dtime_oid" value="${Dtime_oid}"/>
<input type="hidden" name="type" id="type" value="${type}"/>
<table class="hairLineTable" id="class${e.Oid}" width="99%">	
	<tr>
		<td class="hairLineTdF" style="font-size:16;" nowrap>您正在編輯 ${csinfo.ClassName}, ${csinfo.chi_name} 的學生成績</td>
	</tr>
</table>
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
							
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>平時考共6個欄位, 輸入任一欄立即計算<b>平時成績</b>，佔總成績30%。若是您已經完成計算，可以跳過計算過程直接輸入<b>平時成績</b>欄位。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>補救教學佔<b>平時成績</b> 30%, 若無補救教學請留空白, 則不會進行計算。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>期中考佔總成績 30%, 期末考佔總成績 40%。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>英檢成績佔<b>期末成績</b> 50%, 若沒有英檢成績請留空白, 則不會進行計算。</td></tr>
							<tr><td valign="top"><img src="images/icon/exclamation.gif" /></td><td>活動欄位會直接影響 總成績，請在總成績完成後進行。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>若是您已經完成計算，您可以跳過所有欄位直接輸入總成績。但成績冊必須與系統相同，格式須合乎教務處規定</td></tr>
							
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>可利用方向鍵↑↓進行游標垂直移動</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td>
							
							
							點擊<INPUT type="submit" name="method" <c:if test="${date2!=null}">disabled</c:if> value="<bean:message key='Save' bundle="COU"/>" class="gSubmitSmall"> 儲存成績, 
							點擊<input type="button" name="method" value="離開" onClick="location.href='/CIS/Teacher/ScoreManager.do'" class="gCancelSmall"> 不儲存離開
							
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
	<tr class="hairLineTdF">
		<td class="hairLineTdF" style="font-size:16;">學號</td>
		<td class="hairLineTdF" style="font-size:16;">姓名</td>
		<td class="hairLineTdF" style="font-size:16;">平時考</td>
		<td class="hairLineTdF" style="font-size:16;">平時</td>
		<td class="hairLineTdF" style="font-size:16;">補救</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>期中考</td>
		<td align="center" style="font-size:16;">英檢</td>
		<td align="center" style="font-size:16;" nowrap>期末考</td>
		<td class="hairLineTdF" style="font-size:16;">活動</td>
		<td align="center" style="font-size:16;" nowrap>總成績</td>
	</tr>
	
	
	<c:forEach items="${students}" var="s" varStatus="c">				
	
	<c:if test="${c.index%5==0&&c.index!=0}">
	
	<tr class="hairLineTdF">
		<td class="hairLineTdF" style="font-size:16;">學號</td>
		<td class="hairLineTdF" style="font-size:16;">姓名</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>平時考</td>
		<td class="hairLineTdF" style="font-size:16;">平時</td>
		<td class="hairLineTdF" style="font-size:16;">補救</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>期中考</td>
		<td align="center" style="font-size:16;">英檢</td>
		<td align="center" style="font-size:16;" nowrap>期末考</td>
		<td class="hairLineTdF" style="font-size:16;">活動</td>
		<td align="center" style="font-size:16;" nowrap>總成績</td>
	</tr>
	
	</c:if>
	
	
	<tr class="hairLineTdF">
		<td class="hairLineTdF" style="font-size:16;cursor:pointer;" id="stImage${s.student_no}" nowrap>${s.student_no}</td>
		<td class="hairLineTdF" style="font-size:16;cursor:pointer;" id="stImage${s.student_no}" nowrap>${s.student_name}</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="hidden" name="seldOid" value="${s.Oid}" />
		<input type="text" name="score01" id="score01${s.Oid}" value="${s.score01}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score01${students[c.index+1].Oid}', 'score01${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score02" id="score02${s.Oid}" value="${s.score02}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score02${students[c.index+1].Oid}', 'score02${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score03" id="score03${s.Oid}" value="${s.score03}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score03${students[c.index+1].Oid}', 'score03${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score04" id="score04${s.Oid}" value="${s.score04}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score04${students[c.index+1].Oid}', 'score04${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score05" id="score05${s.Oid}" value="${s.score05}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score05${students[c.index+1].Oid}', 'score05${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		<input type="text" name="score06" id="score06${s.Oid}" value="${s.score06}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score06${students[c.index+1].Oid}', 'score06${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		
		</td>
		
		<td class="hairLineTdF" align="center" style="font-size:16;" nowrap>
		<input type="text" name="score1" id="score1${s.Oid}" value="${s.score1}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score1${students[c.index+1].Oid}', 'score1${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score08" id="score08${s.Oid}" value="${s.score08}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score08${students[c.index+1].Oid}', 'score08${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		</td>
		
		
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score2" id="score2${s.Oid}" value="${s.score2}" <c:if test="${date1!=null}">readonly</c:if>  class="FocusTextBlue" size="1" onClick="cc(this.name);" <c:if test="${date1==null}">onKeyUp="if(ck(this)){km(event, 'score2${students[c.index+1].Oid}', 'score2${students[c.index-1].Oid}'); countScore('${s.Oid}')}"</c:if> />
		</td>
		
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score16" id="score16${s.Oid}" value="${s.score16}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score16${students[c.index+1].Oid}', 'score16${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score3" id="score3${s.Oid}" value="${s.score3}" <c:if test="${date2!=null}">readonly</c:if> class="FocusTextBlue" size="1" <c:if test="${date2==null}">onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score3${students[c.index+1].Oid}', 'score3${students[c.index-1].Oid}'); countScore('${s.Oid}')}"</c:if>/>
		</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score09" id="score09${s.Oid}" value="${s.score09}" class="FocusTextBlue" size="1" onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score09${students[c.index+1].Oid}', 'score09${students[c.index-1].Oid}'); countScore('${s.Oid}')}"/>
		</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score" id="score${s.Oid}" value="${s.score}" <c:if test="${date2!=null}">readonly</c:if> class="FocusTextBlue" size="1" <c:if test="${date2==null}">onClick="cc(this.name);" onKeyUp="if(ck(this)){km(event, 'score${students[c.index+1].Oid}', 'score${students[c.index-1].Oid}'); countScore('${s.Oid}')}"</c:if>/>
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
	
	counTmp=0;
	
	score08=document.getElementById("score08"+Oid).value;
	score09=document.getElementById("score09"+Oid).value;
	
	//score1=document.getElementById("score1"+Oid).value;
	score1=0;
	score2=document.getElementById("score2"+Oid).value;
	score3=document.getElementById("score3"+Oid).value;
	
	score16=document.getElementById("score16"+Oid).value;
	
	score=document.getElementById("score"+Oid).value;
	
	
	
	//平時考
	if(score01!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score01)}
	if(score02!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score02)}
	if(score03!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score03)}
	if(score04!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score04)}
	if(score05!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score05)}
	if(score06!=""){counTmp=counTmp+1; score1=parseInt(score1)+parseInt(score06)}
	
	
	
	score08=document.getElementById("score08"+Oid).value;
	if(counTmp>0){
		score1=score1/counTmp;
		document.getElementById("score1"+Oid).value=score1;//寫入平時成績
		
		//補救教學
		if(score08!=""){
			//score1=(parseInt(score1)+parseInt(score08))/2;
			score1=(((parseInt(score1)*0.7)+(parseInt(score08)*0.3)));
			
			document.getElementById("score1"+Oid).value=score1;//重寫平時成績
		}		
	}else{
		if(document.getElementById("score1"+Oid).value==""){
			score1=0;
		}else{
			score1=parseInt(document.getElementById("score1"+Oid).value);
		}
		
	}
	
	
	
	//第二階段
	if(score2!=""&&score3!=""){			
			
		//期末
		if(score3!=""){
			score3=parseInt(score3);
		}else{
			score3=0;
		}
		
		if(score16!=""){
			score3=(score3+parseInt(score16))/2;
		}
		
		//活動
		if(score09!=""){score09=parseInt(score09)}else{score09=0}
		
		score1=score1*0.3;
		score2=parseInt(score2)*0.3;
		score3=score3*0.4;
		
		score=score1+score2+score3+score09;
		
		if(score>100){score=100}
		document.getElementById("score"+Oid).value=score;//寫入總成績
		
	}
	
}



</script>
