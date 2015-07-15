<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				目標學期
				</td>
				<td class="hairLineTdF" nowrap>
				<select name="term">
					<option <c:if test="${ListCourseForm.map.term eq''}">selected</c:if> value="">選擇學期</option>
					<option <c:if test="${ListCourseForm.map.term eq'1'}">selected</c:if> value="1">第1學期</option>
					<option <c:if test="${ListCourseForm.map.term eq'2'}">selected</c:if> value="2">第2學期</option>
				</select>
				</td>
				<td class="hairLineTdF" nowrap>
				查詢次學期課程可能與次學期實際開課不同
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		
		
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF" nowrap>開課班級</td>
				<td class="hairLineTdF">
				<select name="CampusNo" style="font-size:16px;">					
					<c:forEach items="${AllCampus}" var="ac">					
					<option <c:if test="${ListCourseForm.map.CampusNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="SchoolNo" style="font-size:16px;">
					<option <c:if test="${ListCourseForm.map.SchoolNo==''}">selected</c:if> value="">所有學制</option>
					<c:forEach items="${AllSchool}" var="ac">					
					<option <c:if test="${ListCourseForm.map.SchoolNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>
				<td class="hairLineTdF">				
				<select name="DeptNo"style="font-size:16px;">
					<option <c:if test="${ListCourseForm.map.DeptNo==''}">selected</c:if> value="">所有科系</option>
					<c:forEach items="${AllDept}" var="ac">					
					<option <c:if test="${ListCourseForm.map.DeptNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>
				<td class="hairLineTdF">				
				<select name="Grade" style="font-size:16px;">
					<option <c:if test="${ListCourseForm.map.Grade==''}">selected</c:if> value="">所有年級</option>
					<option <c:if test="${ListCourseForm.map.Grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${ListCourseForm.map.Grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${ListCourseForm.map.Grade=='3'}">selected</c:if> value="3">三年級</option>
					<option <c:if test="${ListCourseForm.map.Grade=='4'}">selected</c:if> value="4">四年級</option>
					<option <c:if test="${ListCourseForm.map.Grade=='4'}">selected</c:if> value="5">五年級</option>
				</select>				
				</td>
				<td class="hairLineTdF">
				<select name="ClassNo" style="font-size:16px;">
					<option <c:if test="${ListCourseForm.map.ClassNo=='%'}">selected</c:if> value="">所有班級</option>
					<option <c:if test="${ListCourseForm.map.ClassNo=='1'}">selected</c:if> value="1">甲班</option>
					<option <c:if test="${ListCourseForm.map.ClassNo=='2'}">selected</c:if> value="2">乙班</option>
					<option <c:if test="${ListCourseForm.map.ClassNo=='3'}">selected</c:if> value="3">丙班</option>
					<option <c:if test="${ListCourseForm.map.ClassNo=='4'}">selected</c:if> value="4">丁班</option>
				</select>
				</td>				
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				授課教師
				</td><td class="hairLineTdF" nowrap>
				<input type="text" size="6" style="font-size:18px;" onClick="this.value=''"
				value="${ListCourseForm.map.cname}" name="cname"
				onkeyup="getAny(this.value, 'cname0', 'techid0', 'empl_noid', 'name')" name="cname" id="cname0"/>				
				<input type="hidden" name="techid" id="techid0"/>
				</td>
				<td class="hairLineTdF" nowrap>
				上課時間
				</td>
				<td class="hairLineTdF">
				<select name="week" style="font-size:16px;">
					<option <c:if test="${ListCourseForm.map.week==''}">selected</c:if> value="">每天</option>
					<option <c:if test="${ListCourseForm.map.week=='1'}">selected</c:if> value="1">星期一</option>
					<option <c:if test="${ListCourseForm.map.week=='2'}">selected</c:if> value="2">星期二</option>
					<option <c:if test="${ListCourseForm.map.week=='3'}">selected</c:if> value="3">星期三</option>
					<option <c:if test="${ListCourseForm.map.week=='4'}">selected</c:if> value="4">星期四</option>
					<option <c:if test="${ListCourseForm.map.week=='5'}">selected</c:if> value="5">星期五</option>
					<option <c:if test="${ListCourseForm.map.week=='6'}">selected</c:if> value="6">星期六</option>
					<option <c:if test="${ListCourseForm.map.week=='7'}">selected</c:if> value="7">星期日</option>		
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="begin" style="font-size:16px;">
				<option <c:if test="${ListCourseForm.map.begin==''}">selected</c:if> value="">開始節次</option>
				<c:forEach var="b" begin="1" end="15" step="1">
         		<option <c:if test="${ListCourseForm.map.begin==b}">selected</c:if> value="${b}">${b}</option>
				</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="end" style="font-size:16px;">
				<option <c:if test="${ListCourseForm.map.end==''}">selected</c:if> value="">結束節次</option>
				<c:forEach var="b" begin="1" end="15" step="1">
         		<option <c:if test="${ListCourseForm.map.end==b}">selected</c:if> value="${b}">${b}</option>
				</c:forEach>
				</select>
				
				</td>
				
				
				
								
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	
	<tr>
		<td>
		
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				課程名稱
				</td>
				<td class="hairLineTdF" nowrap>							
				<input type="hidden" id="cscodeS"/>				
				<input type="text" autocomplete="off"
				name="chi_name" id="csnameS" style="font-size:16px;"
				value="${ListCourseForm.map.chi_name}" onClick="this.value=''"
				onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"/>
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				</td>
				<td class="hairLineTdF">
				<select name="opt" style="font-size:16px;">
					<option <c:if test="${ListCourseForm.map.opt==''}">selected</c:if> value="">選別</option>
					<option <c:if test="${ListCourseForm.map.opt=='1'}">selected</c:if> value="1">必修</option>
					<option <c:if test="${ListCourseForm.map.opt=='2'}">selected</c:if> value="2">選修</option>
					<option <c:if test="${ListCourseForm.map.opt=='3'}">selected</c:if> value="3">通識</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="open" style="font-size:16px;">
					<option <c:if test="${ListCourseForm.map.open==''}">selected</c:if> value="">選修規則</option>
					<option <c:if test="${ListCourseForm.map.open=='1'}">selected</c:if> value="1">開放選修</option>
					<option <c:if test="${ListCourseForm.map.open=='0'}">selected</c:if> value="0">非開放選修</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="elearning" style="font-size:16px;">
					<option <c:if test="${ListCourseForm.map.elearning==''}">selected</c:if> value="">上課方式</option>
					<option <c:if test="${ListCourseForm.map.elearning=='0'}">selected</c:if> value="0">一般</option>
					<option <c:if test="${ListCourseForm.map.elearning=='1'}">selected</c:if> value="1">遠距</option>
					<option <c:if test="${ListCourseForm.map.elearning=='2'}">selected</c:if> value="2">輔助</option>
					<option <c:if test="${ListCourseForm.map.elearning=='3'}">selected</c:if> value="3">多媒體</option>
				</select>
				</td>
			</tr>
		</table>
		
		
		</td>
	</tr>
		
	
	<tr>
		<td>
		<table width="99%" class="hairLineTable" id="help" style="display:none;">
			<tr>
				<td class="hairLineTdF">
				查詢條件能夠進行交叉比對查詢，例如: <br>
				「台北校區→所有學制→資訊管理系→所有年級→所有班級」<b>可以查詢到所有資管系的課程</b>，<br>
				授課教師欄位會對應鍵盤輸入，利用滑鼠可以直接完成授課教師欄位，若只輸入姓或名亦可進行查詢。<br>
				課程名稱欄位會對應鍵盤輸入，利用滑鼠可以直接完成課程名稱欄位，若只輸入課程中包含的文字亦可進行查詢。</b>。<br>
				您可以利用各種條件進行查詢，若不想看到過多的查詢結果，<b>請增加查詢條件過濾</b>。<br><br>
				<b>正在進行的變動或是查詢條件例外的課程，仍以各部制課務單位最終公佈為標準</b>。
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				<input type="submit"
					   name="method"
					   id="Query" onClick="disp(),enlb()"
					   onMouseOver="showHelpMessage('查詢後可供列表', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)"
					   value="<bean:message key='Query'/>"
					   class="gSubmit">
					   
				
					   
				<input type="button"
					   name="method" id="Create"
					   value="查看說明" onClick="showObj('help');"
					   class="gCancel" 
					   onMouseOver="showHelpMessage('查看說明能更快獲得所需要的資料', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)" />
					   
					   
				<input type="submit"
					   name="method"
					   id="Clear"
					   onMouseOver="showHelpMessage('清除查詢結果', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)"
					   value="<bean:message key='Clear'/>"
					   class="gGreen">
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">


		  
			   
		</td>
	</tr>
	
</table>
<script>
function disp(){
	setTimeout("document.getElementById('Query').disabled=false",20000);
}

function enlb(){
	setTimeout("document.getElementById('Query').disabled=true",500);
	
}
</script>