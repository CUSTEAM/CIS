<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table class="hairLineTable" cellspacing="0" cellpadding="0">
	<tr>
		<td class="hairLineTdF">
		
		<SELECT style="font-size:14;" name="checkOpt" onchange="showHelp(this.value)" class="CourseButton">
			<option value="none">▉▉▉▉選擇查核項目▉▉▉▉</option>			
			
			<option value=""></option>
			<option value="">▉▉▉▉▉選課規則▉▉▉▉▉</option>
			<option value="CheckSeld" <c:if test="${CheckOutForm.map.checkOpt=='CheckSeld'}">selected</c:if> >學生衝堂</option>
			<option value="CheckHeight" <c:if test="${CheckOutForm.map.checkOpt=='CheckHeight'}">selected</c:if> >學生低修高</option>
			<option value="CheckCredit" <c:if test="${CheckOutForm.map.checkOpt=='CheckCredit'}">selected</c:if> >學生學分異常</option>
			<option value="ReSelectedNow" <c:if test="${CheckOutForm.map.checkOpt=='ReSelectedNow'}">selected</c:if> >重複選課(學期)</option>
			<option value="ReSelected" <c:if test="${CheckOutForm.map.checkOpt=='ReSelected'}">selected</c:if> >重複修課(歷史)</option>			
			<option value="CheckSelimit" <c:if test="${CheckOutForm.map.checkOpt=='CheckSelimit'}">selected</c:if> >課程人數不足</option>
			<option value="CheckGeneral" <c:if test="${CheckOutForm.map.checkOpt=='CheckGeneral'}">selected</c:if> >通識未選</option>
			<option value="CheckSport" <c:if test="${CheckOutForm.map.checkOpt=='CheckSport'}">selected</c:if> >體育未選</option>
			
			<option value=""></option>
			<option value="">▉▉▉▉▉現在開課▉▉▉▉▉</option>			
			<option value="CheckGist" <c:if test="${CheckOutForm.map.checkOpt=='CheckGist'}">selected</c:if> >課程大綱與簡介</option>
			<option value="tripos" <c:if test="${CheckOutForm.map.checkOpt=='tripos'}">selected</c:if> >不及格比例與未評分</option>
			<option value="CsGroupDoc" <c:if test="${CheckOutForm.map.checkOpt=='CsGroupDoc'}">selected</c:if> >學程課程選課學生</option>
			<option value="ListSeld4Class" <c:if test="${CheckOutForm.map.checkOpt=='ListSeld4Class'}">selected</c:if> >班級學生選課清單</option>
			<option value="ListCounseling" <c:if test="${CheckOutForm.map.checkOpt=='ListCounseling'}">selected</c:if> >不及格者輔導紀錄</option>
			
			<option value=""></option>
			<option value="">▉▉▉▉▉歷年開課▉▉▉▉▉</option>
			<option value="ReOpenHist" <c:if test="${CheckOutForm.map.checkOpt=='ReOpenHist'}">selected</c:if> >四年/二年內重複開課</option>
			<option value="SummerOpen" <c:if test="${CheckOutForm.map.checkOpt=='SummerOpen'}">selected</c:if> >歷年不及格人數</option>
			
			<option value=""></option>
			<option value="">▉▉▉▉▉重複開課▉▉▉▉▉</option>
			<option value="CheckTech" <c:if test="${CheckOutForm.map.checkOpt=='CheckTech'}">selected</c:if> >教師衝堂</option>
			<option value="CheckRoom" <c:if test="${CheckOutForm.map.checkOpt=='CheckRoom'}">selected</c:if> >教室衝堂</option>
			<option value="CheckClass" <c:if test="${CheckOutForm.map.checkOpt=='CheckClass'}">selected</c:if> >班級衝堂</option>
			
			<option value=""></option>
			<option value="">▉▉▉▉▉其它問題▉▉▉▉▉</option>
			<option value="CheckNoname" <c:if test="${CheckOutForm.map.checkOpt=='CheckNoname'}">selected</c:if> >學籍遺留選課資料</option>
			<option value="CheckThour" <c:if test="${CheckOutForm.map.checkOpt=='CheckThour'}">selected</c:if> >教師任課時數</option>			
			<!-- Leo 20120215 Open -->
			<option value="CheckPay" <c:if test="${CheckOutForm.map.checkOpt=='CheckPay'}">selected</c:if> >學分費印表</option>
			
			
		</SELECT>			
		
		
		<!-- SELECT name="schoolType" <c:if test="${CheckOutForm.map.checkOpt!='CheckGist'}">style="display:none;"</c:if>
		 onchange="document.getElementById('classLess').value='', document.getElementById('classLess').value=this.value"
		 onclick="document.getElementById('pif').style.display='none'">
			<option value="">不分部制</option>
			<option value="164%' OR d.depart_class LIKE '142%' OR d.depart_class LIKE '112%' OR d.depart_class LIKE '11G">台北日間部</option>
			<option value="122%' OR d.depart_class LIKE '152%' OR d.depart_class LIKE '154%' OR d.depart_class LIKE '182%' OR d.depart_class LIKE '18G%' OR d.depart_class LIKE '192%'">台北進修部</option>
			<option value="172%' OR d.depart_class LIKE '132">台北進修學院</option>
			
			<option value="264%' OR d.depart_class LIKE '242%' OR d.depart_class LIKE '212%' OR d.depart_class LIKE '21G">新竹日間部</option>
			<option value="222%' OR d.depart_class LIKE '252%' OR d.depart_class LIKE '254%' OR d.depart_class LIKE '282%' OR d.depart_class LIKE '28G%' OR d.depart_class LIKE '292%'">新竹進修部</option>
		</SELECT>

		<select id="didno" name="didno" style="display:none;" onclick="document.getElementById('pif').style.display='none'">
				<option value="">不分科系</option>
				<option value="*" <c:if test="${Opcs.didno=='*'}"> selected</c:if>>所有科系</option>
				<c:forEach items="${AllDepts}" var="code5">
					<option value="${code5.idno}">${code5.name}</option>
				</c:forEach>
			</select>
		<input type="hidden" id="departLess" name="departLess" style="display:none;"/>


		<select id="sidno" name="sidno" <c:if test="${CheckOutForm.map.checkOpt!='coansw'}">style="display:none;"</c:if> >
				<option value="depart_class LIKE '164%' OR depart_class LIKE '142%' OR depart_class LIKE '112%'">日間部</option>
				<option value="depart_class LIKE '154%' OR depart_class LIKE '152%' OR depart_class LIKE '182%' OR depart_class LIKE '192%'">進修部</option>
				<option value="depart_class LIKE '132%' OR depart_class LIKE '172%'">進修學院</option>
		</select-->
		</td>
		
		<!-- 班級選課清單，增加單一學生的查詢條件 -->
		<td <c:if test="${CheckOutForm.map.checkOpt!='ListSeld4Class'}">style="display:none;"</c:if> id="ListSeld4Class" class="hairLineTdf">		
		<table align="left" class="hairLineTable">			
		      <tr>
				<td nowrap class="hairLineTdF">學生</td>
				<td class="hairLineTd" colspan="7">
				  <input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						 name="studentNo_0" id="studentNo_0" size="12" value="${StudentManagerForm.map.studentNo}"
						 onkeyup="if(this.value.length>=2)GgetAny(this.value, 'studentNo_0', 'studentName_0', 'gstmd', 'no')" 
						 onMouseOver="showHelpMessage('學號輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成姓名', 'inline', this.id)" 
				   		 onMouseOut="showHelpMessage('', 'none', this.id)" onFocus="chInput(this.id)"
						 onClick="clearQuery()" />
				  <input type="text" name="studentName_0" id="studentName_0" size="10" value="${StudentManagerForm.map.studentName}"
				         onMouseOver="showHelpMessage('姓名輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成學號', 'inline', this.id)" 
				   		 onMouseOut="showHelpMessage('', 'none', this.id)" 
				   		 onFocus="chInput(this.id)"						 
						 onkeyup="if(this.value.length>1)GgetAny(this.value, 'studentName_0', 'studentNo_0', 'gstmd', 'name')" 
						 onClick="clearQuery()" />
				</td>
			  </tr>		
		</table>		
		</td>
		
		<td class="hairLineTd">
		<INPUT type="submit" name="method" value="<bean:message key='CourseCheckOpt'/>" class="gSubmit" id="checkOutButton" 
		onMouseOver="showHelpMessage('輸入班級如: 1643, 264A, _64_, 1__3, 2__A...等多種組合', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
</table>

<div <c:if test="${CheckOutForm.map.checkOpt!='CheckPay'}">style="display:none;"</c:if> id="CheckPay">	

<table class="hairLineTable">
	<tr>
		<td nowrap class="hairLineTdF">收費時數</td>
		<td class="hairLineTd"><input name="payHour" type="text" size="2" value="${CheckOutForm.map.payHour}" /></td>
		<td nowrap class="hairLineTdF">學分費</td>
		<td class="hairLineTd"><input name="payMoney" type="text" size="4" value="${CheckOutForm.map.payMoney}" /></td>
		<td nowrap class="hairLineTdF">實習費</td>
		<td class="hairLineTd"><input name="extraPay" type="text" size="3" value="${CheckOutForm.map.extraPay}" /></td>
		<td nowrap class="hairLineTdF">保險費</td>
		<td class="hairLineTd"><input name="insurance" type="text" size="3" value="${CheckOutForm.map.insurance}" /></td>
	</tr>
	<tr>
		<td nowrap class="hairLineTdF" colspan="8">1.未收實習費，請輸入負數金額(如: -850)<br />2.查詢結果的「時數」已排除"班會"<br />3.非延修生保險費，請輸入0</td>
	</tr>
</table>
</div>