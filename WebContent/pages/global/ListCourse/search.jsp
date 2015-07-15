<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				開課班級
				</td>
				<td class="hairLineTdF">
				<select name="CampusNo" style="font-size:16px;">
					
					<c:forEach items="${AllCampus}" var="ac">					
					<option <c:if test="${ClassCourseSearchForm.map.CampusNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>
				</td>				
			
						
			
				<td class="hairLineTdF">
				<select name="SchoolNo" style="font-size:16px;">
					<option <c:if test="${ClassCourseSearchForm.map.SchoolNo==''}">selected</c:if> value="">所有學制</option>
					<c:forEach items="${AllSchool}" var="ac">					
					<option <c:if test="${ClassCourseSearchForm.map.SchoolNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			
				<td class="hairLineTdF">				
				<select name="DeptNo"style="font-size:16px;">
					<option <c:if test="${ClassCourseSearchForm.map.DeptNo==''}">selected</c:if> value="">所有科系</option>
					<c:forEach items="${AllDept}" var="ac">					
					<option <c:if test="${ClassCourseSearchForm.map.DeptNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			
				<td class="hairLineTdF">
				
				<select name="Grade" style="font-size:16px;">
					<option <c:if test="${ClassCourseSearchForm.map.Grade==''}">selected</c:if> value="">所有年級</option>
					<option <c:if test="${ClassCourseSearchForm.map.Grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${ClassCourseSearchForm.map.Grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${ClassCourseSearchForm.map.Grade=='3'}">selected</c:if> value="3">三年級</option>
					<option <c:if test="${ClassCourseSearchForm.map.Grade=='4'}">selected</c:if> value="4">四年級</option>
					<option <c:if test="${ClassCourseSearchForm.map.Grade=='4'}">selected</c:if> value="5">五年級</option>
				</select>
				
				</td>				
			
				<td class="hairLineTdF">
				<select name="ClassNo" style="font-size:16px;">
					<option <c:if test="${ClassCourseSearchForm.map.ClassNo=='%'}">selected</c:if> value="">所有班級</option>
					<option <c:if test="${ClassCourseSearchForm.map.ClassNo=='1'}">selected</c:if> value="1">甲班</option>
					<option <c:if test="${ClassCourseSearchForm.map.ClassNo=='2'}">selected</c:if> value="2">乙班</option>
					<option <c:if test="${ClassCourseSearchForm.map.ClassNo=='3'}">selected</c:if> value="3">丙班</option>
					<option <c:if test="${ClassCourseSearchForm.map.ClassNo=='4'}">selected</c:if> value="4">丁班</option>
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
				value="${ClassCourseSearchForm.map.cname}" name="cname" onMouseOver="showHelpMessage('授課教師的姓氏或名字皆可比對', 'inline', this.id)" 
			    onMouseOut="showHelpMessage('', 'none', this.id)"
				onkeyup="getAny(this.value, 'cname0', 'techid0', 'empl_noid', 'name')" name="cname" id="cname0"/>				
				<input type="hidden" name="techid" id="techid0"/>
				</td>
				<td class="hairLineTdF" nowrap>
				上課時間
				</td>
				<td class="hairLineTdF">
				<select name="week" style="font-size:16px;">
					<option <c:if test="${ClassCourseSearchForm.map.week==''}">selected</c:if> value="">每天</option>
					<option <c:if test="${ClassCourseSearchForm.map.week=='1'}">selected</c:if> value="1">星期一</option>
					<option <c:if test="${ClassCourseSearchForm.map.week=='2'}">selected</c:if> value="2">星期二</option>
					<option <c:if test="${ClassCourseSearchForm.map.week=='3'}">selected</c:if> value="3">星期三</option>
					<option <c:if test="${ClassCourseSearchForm.map.week=='4'}">selected</c:if> value="4">星期四</option>
					<option <c:if test="${ClassCourseSearchForm.map.week=='5'}">selected</c:if> value="5">星期五</option>
					<option <c:if test="${ClassCourseSearchForm.map.week=='6'}">selected</c:if> value="6">星期六</option>
					<option <c:if test="${ClassCourseSearchForm.map.week=='7'}">selected</c:if> value="7">星期日</option>		
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="begin" style="font-size:16px;">
				<option <c:if test="${ClassCourseSearchForm.map.begin==''}">selected</c:if> value="">開始節次</option>
				<c:forEach var="b" begin="1" end="15" step="1">
         		<option <c:if test="${ClassCourseSearchForm.map.begin==b}">selected</c:if> value="${b}">${b}</option>
				</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="end" style="font-size:16px;">
				<option <c:if test="${ClassCourseSearchForm.map.end==''}">selected</c:if> value="">結束節次</option>
				<c:forEach var="b" begin="1" end="15" step="1">
         		<option <c:if test="${ClassCourseSearchForm.map.end==b}">selected</c:if> value="${b}">${b}</option>
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
				value="${ClassCourseSearchForm.map.chi_name}" onClick="this.value=''"
				onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
				onMouseOver="showHelpMessage('課程中所包的任何文字皆可比對', 'inline', this.id)" 
			    onMouseOut="showHelpMessage('', 'none', this.id)"/>
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				</td>
				<td class="hairLineTdF">
				<select name="opt" style="font-size:16px;">
					<option <c:if test="${ClassCourseSearchForm.map.opt==''}">selected</c:if> value="">選別</option>
					<option <c:if test="${ClassCourseSearchForm.map.opt=='1'}">selected</c:if> value="1">必修</option>
					<option <c:if test="${ClassCourseSearchForm.map.opt=='2'}">selected</c:if> value="2">選修</option>
					<option <c:if test="${ClassCourseSearchForm.map.opt=='3'}">selected</c:if> value="3">通識</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="open" style="font-size:16px;">
					<option <c:if test="${ClassCourseSearchForm.map.open==''}">selected</c:if> value="">選修規則</option>
					<option <c:if test="${ClassCourseSearchForm.map.open=='1'}">selected</c:if> value="1">開放選修</option>
					<option <c:if test="${ClassCourseSearchForm.map.open=='0'}">selected</c:if> value="0">非開放選修</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="elearning" style="font-size:16px;">
					<option <c:if test="${ClassCourseSearchForm.map.elearning==''}">selected</c:if> value="">上課方式</option>
					<option <c:if test="${ClassCourseSearchForm.map.elearning=='0'}">selected</c:if> value="0">一般</option>
					<option <c:if test="${ClassCourseSearchForm.map.elearning=='1'}">selected</c:if> value="1">遠距</option>
					<option <c:if test="${ClassCourseSearchForm.map.elearning=='2'}">selected</c:if> value="2">輔助</option>
					<option <c:if test="${ClassCourseSearchForm.map.elearning=='3'}">selected</c:if> value="3">多媒體</option>
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
		
		</td>
	</tr>
	
	
	
</table>