<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>

<table width="200" id="showTable" class="hairLineTable" style="display:none; position:absolute; right:20px; top:20px; filter:Alpha(opacity: 0.8);filter:alpha(opacity=80);-moz-opacity:0.8; z-index:32768;">
	<tr height="50">
		<td class="hairLineTdF" id="showInfo">
		
		
		
		</td>
	</tr>
</table>



<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr height="2">
		<td>
		
		</td>
	</tr>
	<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/user_gray.gif">&nbsp;
 				</td>
 				<td nowrap> 				
 				&nbsp;&nbsp;基&nbsp;&nbsp;本&nbsp;&nbsp;資&nbsp;&nbsp;料&nbsp;&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->

<!-- 第1階 -->
	<tr>
		<td>
		
		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" nowrap>證號姓名</td>
						<td class="hairLineTd">
						<input type="text" size="10" value="${aSaly.idno}" disabled /><input 
						type="text" size="8" value="${aSaly.cname}" disabled /></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" width="30" align="center" nowrap>職稱</td>
						<td class="hairLineTd"><input type="text" value="${aSaly.sname}" disabled /></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" width="30" align="center" nowrap>序號</td>
						<td class="hairLineTd">
							<input type="text" size="4" name="sno" value="${aSaly.sno}" id="sno" onFocus="chInput(this.id)"/>
							<input type="hidden" size="4" name="idno" value="${aSaly.idno}"/>
						</td>
					</tr>
				</table>
				
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/icon/money_add.gif">&nbsp;
 				</td>
 				<td nowrap> 				
 				&nbsp;&nbsp;應&nbsp;&nbsp;發&nbsp;&nbsp;明&nbsp;&nbsp;細&nbsp;&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->



<!-- 第2階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">薪俸</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="monthly_pay" id="monthly_pay" 
						value="${aSaly.monthly_pay}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">工餉</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="inslast" id="inslast" value="${aSaly.inslast}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">學術研究費</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="study" id="study" value="${aSaly.study}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">專業加給</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="technical" id="technical" value="${aSaly.technical}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
			
		</td>
	</tr>
<!-- 第2階end -->

<!-- 第3階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>

				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font color="green">主管特支</font></td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="hspecial" id="hspecial" value="${aSaly.hspecial}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">特別研究費</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="spec_study" id="spec_study" value="${aSaly.spec_study}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">薪資補差額</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="difference" id="difference" value="${aSaly.difference}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">代課費</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="substitute_course" id="substitute_course" value="${aSaly.substitute_course}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第3階end -->

<!-- 第4階 -->
	<tr>
		<td width="100%" align="left">

 		<table>
			<tr>
				<td>
								
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">超支鐘點</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="overhour_pay" id="overhour_pay" value="${aSaly.overhour_pay}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80"><font color="green">導師費</font></td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="class_teacher_pay" id="class_teacher_pay" value="${aSaly.class_teacher_pay}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">津貼</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="transportation" id="transportation" value="${aSaly.transportation}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">交通費(夜)</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="night_transport" id="night_transport" value="${aSaly.night_transport}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第4階end -->

<!-- 第6階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">監考費</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="supervisor_test" id="supervisor_test" value="${aSaly.supervisor_test}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">夜間部超支</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="night_overhour_pay" id="night_overhour_pay" value="${aSaly.night_overhour_pay}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">其它應支</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="others_in_1" id="others_in_1" value="${aSaly.others_in_1}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">其它應支2</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="others_in_2" id="others_in_2" value="${aSaly.others_in_2}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第6階end -->

<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/icon/money_delete.gif">&nbsp;
 				</td>
 				<td nowrap> 				
 				&nbsp;&nbsp;應&nbsp;&nbsp;扣&nbsp;&nbsp;明&nbsp;&nbsp;細&nbsp;&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->

<!-- 第8階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">扣公保費用</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" onClick="countMinus()" name="public_insure" id="public_insure" value="${aSaly.public_insure}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">扣建保費用</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="teach_over" id="teach_over" value="${aSaly.teach_over}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">扣眷保費用</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="spouse_insure" id="spouse_insure" value="${aSaly.spouse_insure}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">扣借支費用</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="borrow" id="borrow" value="${aSaly.borrow}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第8階end -->

<!-- 第9階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>
								
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">其它應扣1</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="others_out_1" id="others_out_1" value="${aSaly.others_out_1}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">其它應扣2</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="others_out_2" id="others_out_2" value="${aSaly.others_out_2}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80">扣勞保費用</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="6" name="hour_pay" id="hour_pay" value="${aSaly.hour_pay}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" width="80" nowrap>綜合所得稅</td>
						<td class="hairLineTd">
						<input onKeyUp="nullValue(this.id), countTaxNonAjax()" type="text" size="6" name="tax_pay" id="tax_pay" value="${aSaly.tax_pay}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第9階end -->



<!-- 線 -->
	<tr>
		<td>
 		<table cellpadding="0" cellspacing="0" border="0" width="100%">
 			<tr>
 				<td width="25"> 				
 				<hr noshade width="25" class="myHr"/> 				
 				</td>
 				<td nowrap> 				
 				&nbsp;<img src="images/icon/calculator.gif">&nbsp;
 				</td>
 				<td nowrap> 				
 				&nbsp;&nbsp;項&nbsp;&nbsp;目&nbsp;&nbsp;總&nbsp;&nbsp;計&nbsp;&nbsp;
 				</td>
 				<td width="100%"> 				
 				<hr noshade class="myHr"/> 				
 				</td>
 			</tr>
 		</table>
 		
		</td>
	</tr>
<!-- 線end -->

<!-- 第10階 -->
	<tr>
		<td width="100%" align="left">
 			
 		<table>
			<tr>
				<td>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center">扶養親屬數</td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="2" name="family_no" id="family_no" value="${aSaly.family_no}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center"><font color="green">免稅額</font></td>
						<td class="hairLineTd">
						<input onChange="nullValue(this.id), countTax()" type="text" size="4" name="notax" id="notax" value="${aSaly.notax}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>				
				
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" nowrap>應發總額</td>
						<td class="hairLineTd">
						<input type="text" size="6" name="payamt" id="payamt" value="${aSaly.payamt}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				<table class="hairLineTable" align="left">
					<tr>
						<td class="hairLineTdF" align="center" nowrap>實發金額</td>
						<td class="hairLineTd">
						<input type="text" size="6" name="real_pay" id="real_pay" value="${aSaly.real_pay}" onFocus="chInput(this.id)"/></td>
					</tr>
				</table>
				
				
				</td>
			</tr>
		</table>
 			
		</td>
	</tr>
<!-- 第10階end -->
		
	<tr height="30" class="fullColorTr">
		<td align="center">
		
		
		
		
		<INPUT type="submit"
						   name="method"
						   id="Prev"
						   onMouseOver="showHelpMessage('前一個人', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)"
						   value="<bean:message key='Prev'/>"
						   class="CourseButton"><INPUT type="submit"
						   name="method"
						   id="Next"
						   onMouseOver="showHelpMessage('後一個人', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)"
						   value="<bean:message key='Next'/>"
						   class="CourseButton"><INPUT type="submit"
						   name="method"
						   id="Update"
						   onMouseOver="showHelpMessage('儲存設定', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)"
						   value="<bean:message key='Update'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method" id="Cancel"
													   value="<bean:message
													   key='Cancel'/>"
													   class="CourseButton" 
													   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
													   onMouseOut="showHelpMessage('', 'none', this.id)" />
		</td>
	</tr>
</table>


<script>
//定義物件

	
	//第2階
	var monthly_pay;//薪俸
	var inslast;//工餉
	var study;//學術研究費
	var technical;//工餉
	
	//第3階
	var hspecial;//主管特支
	var spec_study;//特別研究費
	var difference;//薪資補差額
	var substitute_course;//代課費
	
	//第4階
	var overhour_pay;//超支鐘點
	var class_teacher_pay;//導師費
	var transportation;//津貼
	var night_transport;//交通費夜
	
	//第6階
	var supervisor_test;//監考費
	var night_overhour_pay;//夜間部超支
	var others_in_1;//其它應支1
	var others_in_2;//其它應支2

	
	//第8階
	var public_insure;//扣工保
	var teach_over;//扣健保
	var spouse_insure;//扣軍保
	var borrow;//扣借支
	
	//第9階
	var others_out_1;//其它應扣
	var others_out_2;//其它應扣2
	var hour_pay;//扣勞保
	
	//第10階
	var family_no;//扶養數
	var notax;//免稅額
	var payamt;//應廢
	var real_pay;//實發
	
	var noTax;//自算免稅
	var total_plus;
	var total_minus;
	
	
	try{
		var XMLHttpReqDyna=new XMLHttpRequest();
	}catch(e){
		var XMLHttpReqDyna=false;
	}
	var XMLHttpRequest=null;	
	if(window.ActiveXObject){
			try{
				XMLHttpReqDyna=new ActiveXObject("Msxml2.XMLHTTP");
				}catch(e){
				  alert('shit! AJAX contect ERROR!');
					try{
						XMLHttpReqDyna=new ActiveXObject("Microsoft.XMLHTTP");
						}catch(e){
               				alert('shit! AJAX contect ERROR!');
            }
		}
	}

//賺錢
function countPlus(){
	
	//第2階
	monthly_pay=parseInt(document.getElementById("monthly_pay").value);//薪俸
	inslast=parseInt(document.getElementById("inslast").value);//工餉
	study=parseInt(document.getElementById("study").value);//學術研究費
	technical=parseInt(document.getElementById("technical").value);//工餉
	
	//第3階
	hspecial=parseInt(document.getElementById("hspecial").value);//主管特支
	spec_study=parseInt(document.getElementById("spec_study").value);//特別研究費
	difference=parseInt(document.getElementById("difference").value);//薪資補差額
	substitute_course=parseInt(document.getElementById("substitute_course").value);//代課費
	
	//第4階
	overhour_pay=parseInt(document.getElementById("overhour_pay").value);//超支鐘點
	class_teacher_pay=parseInt(document.getElementById("class_teacher_pay").value);//導師費
	transportation=parseInt(document.getElementById("transportation").value);//津貼
	night_transport=parseInt(document.getElementById("night_transport").value);//交通費夜
	
	//第6階
	supervisor_test=parseInt(document.getElementById("supervisor_test").value);//監考費
	night_overhour_pay=parseInt(document.getElementById("night_overhour_pay").value);//夜間部超支
	others_in_1=parseInt(document.getElementById("others_in_1").value);//其它應支1
	others_in_2=parseInt(document.getElementById("others_in_2").value);//其它應支2
	
	return monthly_pay+inslast+study+technical+	
		   hspecial+spec_study+difference+substitute_course+		   
		   overhour_pay+class_teacher_pay+transportation+night_transport+
		   supervisor_test+night_overhour_pay+others_in_1+others_in_2;
}


//扣錢
function countMinus(){
	
	
	//第8階
	public_insure=parseInt(document.getElementById("public_insure").value);//扣工保
	teach_over=parseInt(document.getElementById("teach_over").value);//扣健保
	spouse_insure=parseInt(document.getElementById("spouse_insure").value);//扣軍保
	borrow=parseInt(document.getElementById("borrow").value);//扣借支
	
	//第9階
	others_out_1=parseInt(document.getElementById("others_out_1").value);//其它應扣
	others_out_2=parseInt(document.getElementById("others_out_2").value);//其它應扣2
	hour_pay=parseInt(document.getElementById("hour_pay").value);//扣勞保
	
	//第10階
	family_no=parseInt(document.getElementById("family_no").value);//扶養數
	notax=parseInt(document.getElementById("notax").value);//免稅額
	payamt=parseInt(document.getElementById("payamt").value);//應廢
	real_pay=parseInt(document.getElementById("real_pay").value);//實發
	
	//其它
	//免稅加總
	
	return public_insure+teach_over+spouse_insure+borrow+					
		   others_out_1+others_out_2+hour_pay;
}

//取稅額
function countTax(){

	total_minus=countMinus();
	total_plus=countPlus();

	noTax=hspecial+class_teacher_pay+notax;//自算免稅		
	document.getElementById("showTable").style.display="inline";
	document.getElementById("showInfo").innerHTML="申報薪資: "+(total_plus-noTax)+"<br>扶養人口: "+family_no+"人";	
	XMLHttpReqDyna.open("GET","/CIS/AjaxGetTaxPay?taxPay="+(total_plus-noTax)+"&family_no="+family_no+"&"+Math.floor(Math.random()*999), true);
	XMLHttpReqDyna.onreadystatechange=proceDyna;
	XMLHttpReqDyna.send(null);
}

//取稅額
function countTaxNonAjax(){

	total_minus=countMinus();
	total_plus=countPlus()

	noTax=hspecial+class_teacher_pay+notax;//自算免稅	
	
	document.getElementById("showTable").style.display="inline";
	document.getElementById("showInfo").innerHTML="申報薪資: "+(total_plus-noTax)+"<br>扶養人口: "+family_no+"人";	
	
	//document.getElementById("tax_pay").value=XMLHttpReqDyna.responseXML.getElementsByTagName("tax")[0].firstChild.data;
	getPay(total_plus, (total_plus-(parseInt(document.getElementById("tax_pay").value)+total_minus)) );
	
}

//取資料庫
function proceDyna(){	
	if(XMLHttpReqDyna.readyState==4){	
		if(XMLHttpReqDyna.status==200){		
			document.getElementById("tax_pay").value=XMLHttpReqDyna.responseXML.getElementsByTagName("tax")[0].firstChild.data;
			getPay(total_plus, (total_plus-(parseInt(document.getElementById("tax_pay").value)+total_minus)) );
		}else{
			alert("shit!");
		}
	}	
}

//取應發實發
function getPay(should, real){
	document.getElementById("payamt").value=should;
	document.getElementById("real_pay").value=real;	
}

function nullValue(id){
	if(document.getElementById(id).value==""){
		document.getElementById(id).value="0";
	}
}
</script>
