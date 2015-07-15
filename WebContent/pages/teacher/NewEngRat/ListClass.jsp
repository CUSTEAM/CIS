<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<c:forEach items="${engRats}" var="e">

<table class="hairLineTable" width="99%">	
	<tr>
		<td class="hairLineTdF" style="cursor:pointer;" onClick="showObj('class${e.Oid}');">
		<table>	
			<tr>
				<td width="1"><img src="images/icon/user.gif"></td>
				<td nowrap style="font-size:16;">${e.ClassName}</td>
				<td width="1" style="font-size:16;"> - </td>
				<td align="left" nowrap style="font-size:16;">${e.chi_name}</td>
				<td></td>
				<td><a href="/CIS/EngRatReport?dtimeOid=${e.Oid}"><img src="images/ico_file_excel.png" border="0"></a></td>
				<td align="left" nowrap><a href="/CIS/EngRatReport?dtimeOid=${e.Oid}" class="gray_15">下載成績冊</a></td>				
			</tr>
		</table>
		</td>
	</tr>
</table>

<table class="hairLineTable" style="display:none;" id="class${e.Oid}" width="99%">
	<tr class="hairLineTdF">
		<td class="hairLineTdF" style="font-size:16;">學號</td>
		<td class="hairLineTdF" style="font-size:16;">姓名</td>
		<td class="hairLineTdF" style="font-size:16;">平時考</td>
		<td class="hairLineTdF" style="font-size:16;">平時成績</td>
		<td class="hairLineTdF" style="font-size:16;">補救教學</td>
		
		<td class="hairLineTdF" style="font-size:16;">期中考</td>
		<td align="center" style="font-size:16;">英檢</td>
		<td align="center" style="font-size:16;">期末考</td>
		<td class="hairLineTdF" style="font-size:16;">活動加分</td>
		<td align="center" style="font-size:16;">總成績</td>
	</tr>
	
	
	<c:forEach items="${e.students}" var="s">				
	<tr class="hairLineTdF">
		<td class="hairLineTdF" style="font-size:16;cursor:pointer;" id="stImage${s.student_no}" onMouseOut="showHelpMessage('', 'none', this.id)"
		onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', this.id)" nowrap>${s.student_no}</td>
		<td class="hairLineTdF" style="font-size:16;cursor:pointer;" id="stImage${s.student_no}" onMouseOut="showHelpMessage('', 'none', this.id)"
		onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', this.id)" nowrap>${s.student_name}</td>
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="hidden" name="seldOid" value="${s.Oid}" />
		<input type="text" name="score01" id="score01${s.Oid}" value="${s.score01}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		<input type="text" name="score02" id="score02${s.Oid}" value="${s.score02}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		<input type="text" name="score03" id="score03${s.Oid}" value="${s.score03}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		<input type="text" name="score04" id="score04${s.Oid}" value="${s.score04}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		<input type="text" name="score05" id="score05${s.Oid}" value="${s.score05}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		<input type="text" name="score06" id="score06${s.Oid}" value="${s.score06}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		
		</td>
		
		<td class="hairLineTdF" align="center" style="font-size:16;" nowrap>
		<input type="text" name="score1" id="score1${s.Oid}" value="${s.score1}" class="FocusText" size="1" onKeyUp="countScore('${s.Oid}')"/>
		</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score08" id="score08${s.Oid}" value="${s.score08}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		</td>
		
		
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score2" id="score2${s.Oid}" value="${s.score2}" class="FocusText" size="1" onKeyUp="countScore('${s.Oid}')"/>
		</td>
		
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score16" id="score16${s.Oid}" value="${s.score16}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score3" id="score3${s.Oid}" value="${s.score3}" class="FocusText" size="1" onKeyUp="countScore('${s.Oid}')"/>
		</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score09" id="score09${s.Oid}" value="${s.score09}" class="CourseButton" size="1" onKeyUp="countScore('${s.Oid}')"/>
		</td>
		
		<td class="hairLineTdF" style="font-size:16;" nowrap>
		<input type="text" name="score" id="score${s.Oid}" value="${s.score}" class="FocusText" size="1" />
		</td>
	</tr>	
	</c:forEach>	
	<tr height="40">
		<td colspan="100" class="fullColorTd" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Save' bundle="COU"/>" class="gSubmit">
		</td>
	</tr>
</table>

</c:forEach>


<script>

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
