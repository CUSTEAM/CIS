<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
<!--
function openView(type) {
	if (parseInt(type, 10) == type) { // 驗證是否為數字
		if (0 == type) {
			document.getElementById('learnType').style.display = 'inline';
			document.getElementById('workType').style.display = 'none';
			document.getElementById('armyType').style.display = 'none';
			document.getElementById('examType').style.display = 'none';
			document.getElementById('otherType').style.display = 'none';
		} else if (1 == type) {
			document.getElementById('learnType').style.display = 'none';
			document.getElementById('workType').style.display = 'inline';
			document.getElementById('armyType').style.display = 'none';
			document.getElementById('examType').style.display = 'none';
			document.getElementById('otherType').style.display = 'none';
		} else if (2 == type) {
			document.getElementById('learnType').style.display = 'none';
			document.getElementById('workType').style.display = 'none';
			document.getElementById('armyType').style.display = 'inline';
			document.getElementById('examType').style.display = 'none';
			document.getElementById('otherType').style.display = 'none';
		} else if (3 == type) {
			document.getElementById('learnType').style.display = 'none';
			document.getElementById('workType').style.display = 'none';
			document.getElementById('armyType').style.display = 'none';
			document.getElementById('examType').style.display = 'inline';
			document.getElementById('otherType').style.display = 'none';
		} else if (4 == type) {
			document.getElementById('learnType').style.display = 'none';
			document.getElementById('workType').style.display = 'none';
			document.getElementById('armyType').style.display = 'none';
			document.getElementById('examType').style.display = 'none';
			document.getElementById('otherType').style.display = 'inline';
		}
	}
}

function check() {
	var flag = false;
	for (var i = 0; i < document.getElementsByName("invesType").length; i++) {
		if (document.getElementsByName("invesType")[i].checked) {
			flag = true;
			break;
		}	
	}
	if (!flag) {
		alert("請選擇其中一種出路調查的條件,謝謝");
		return false;
	}
	return true;
}
//-->
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Tutor/GraduateInvestigation" method="post" focus="zip" onsubmit="init('系統處理中...')">
<script>generateTableBanner('<div class="gray_15"><B>應 屆 畢 業 生 通 訊 與 出 路 調 查 表</B></div>');</script>
<input type="hidden" name="stdOid" value="${sessionScope.stdOid}" />
<input type="hidden" name="studentNo" value="${StudentInfoEdit.studentNo}" />
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td width="5%" align="left"><hr noshade size="1" color="cfe69f"/></td>
	   				<td width="1%" nowrap>學生基本資料</td>
		   			<td width="99%" align="left"><hr noshade size="1" color="cfe69f"/></td>
	 			</tr>	 			
			</table>
       		<table width="99%" cellspacing="5" class="hairLineTable">
		 		<tr>
		 			<td class="hairLineTdF"><bean:message key="Name"/>：&nbsp;<c:out value="${StudentInfoEdit.studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;
		 	 		<bean:message key="StudentNo"/>：&nbsp;<c:out value="${StudentInfoEdit.studentNo}" />&nbsp;&nbsp;&nbsp;&nbsp;
		 	 		<bean:message key="Sex"/>：&nbsp;<c:out value="${StudentInfoEdit.sex2}" />&nbsp;&nbsp;&nbsp;&nbsp;
		     		所屬班級：&nbsp;<c:out value="${StudentInfoEdit.departClass2}" /></td>
  			   	</tr>
  		 	</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td width="5%" align="left"><hr noshade size="1" color="cfe69f"/></td>
	   				<td width="1%" nowrap>通訊資料調查<c:if test="${!isAllow}">&nbsp;(<font color="red">通訊資料調查輸入時間已過</font>)</c:if></td>
		   			<td width="99%" align="left"><hr noshade size="1" color="cfe69f"/></td>
	 			</tr>	 			
			</table>
       		<table width="99%" cellspacing="5" class="hairLineTable">
		 		<tr>
		 			<td colspan="3" class="hairLineTdF"><bean:message key="CommAddress"/>：&nbsp;
		 	   			<input type="text" name="zip" size="2" maxlength="5" value="${StudentInfoEdit.currPost}" <c:if test="${!isAllow}">readonly="true"</c:if>>
		 	   			<input type="text" name="addr" size="60" maxlength="100" value="${StudentInfoEdit.currAddr}" <c:if test="${!isAllow}">readonly="true"</c:if>>
		 	   		</td>
		 	 		<td class="hairLineTdF">手機號碼：&nbsp;
		 	   			<input type="text" name="cellPhone" size="10" maxlength="15" value="${StudentInfoEdit.cellPhone}" <c:if test="${!isAllow}">readonly="true"</c:if>>
		 	   		</td>
		 		</tr>
		 		<tr>
		 			<td colspan="3" class="hairLineTdF">電郵信箱：&nbsp;
		 	   			<input type="text" name="email" size="50" maxlength="50" value="${StudentInfoEdit.email}" <c:if test="${!isAllow}">readonly="true"</c:if>>
		 	   		</td>
		 	   		<td colspan="3" class="hairLineTdF">住家電話：&nbsp;
	      	   			<input type="text" name="phone"	size="12" maxlength="15" value="${StudentInfoEdit.telephone}" <c:if test="${!isAllow}">readonly="true"</c:if>>
	      	   		</td>
		 		</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td width="5%" align="left"><hr noshade size="1" color="cfe69f"/></td>
	   				<td width="1%" nowrap>&nbsp;&nbsp;&nbsp;出路資料調查(<font color="red">注意:依學生目前狀態勾選其中一項,謝謝</font>)</td>
		   			<td width="99%" align="left"><hr noshade size="1" color="cfe69f"/></td>
	 			</tr>	 			
			</table>
			
			<table>
				<tr>
					<td>出路符合條件：
						<html:radio property="invesType" styleId="invesType" value="0" onclick="openView(this.value)"/>升學
						<html:radio property="invesType" styleId="invesType" value="1" onclick="openView(this.value)"/>就業
						<html:radio property="invesType" styleId="invesType" value="2" onclick="openView(this.value)"/>服兵役
						<html:radio property="invesType" styleId="invesType" value="3" onclick="openView(this.value)"/>準備考試
						<html:radio property="invesType" styleId="invesType" value="4" onclick="openView(this.value)"/>待業
					</td>
				</tr>
			</table>
			
	   		<table width="100%" cellspacing="5" class="hairLineTable">
	   			
	     		<tr>
	     			<td colspan="4" class="hairLineTdF">
	     				<span id="learnType" style="display:none;">
     					<html:select property="foreignOrNot">
     						<html:option value="0">國內</html:option>
     						<html:option value="1">國外</html:option>
     					</html:select>&nbsp;&nbsp;
	     				學校名稱：&nbsp;<html:text property="schoolName" size="30" maxlength="50"/>&nbsp;&nbsp;
	     				系所名稱：&nbsp;<html:text property="canpus" size="10" maxlength="20"/>&nbsp;&nbsp;
	     				學位類別：&nbsp;<html:text property="level" size="10" maxlength="20"/>
	      	   			</span>
	      	   		</td>
	      	   	</tr>	      	   	
	      	   	
	      	   	<tr>
	     			<td colspan="4" class="hairLineTdF">
	     				<span id="workType" style="display:none;">
	      	   			公司名稱：<html:text property="company" size="20" maxlength="50"/>&nbsp;&nbsp;
	      	   			公司類型：
      	   				<html:select property="companyType">
      	   					<html:option value="0">私人企業</html:option>
      	   					<html:option value="1">政府部門</html:option>
      	   					<html:option value="2">學校</html:option>
      	   					<html:option value="3">非營利機構</html:option>
      	   					<html:option value="4">其他</html:option>
      	   				</html:select>&nbsp;&nbsp;
	      	   			工作性質：
      	   				<html:select property="workNatureCode">
      	   					<html:options property="workNatureCodes" labelProperty="workNatureNames" />
      	   				</html:select>&nbsp;&nbsp;
	      	   			職務性質：
      	   				<html:select property="workDutyCode">
      	   					<html:options property="workDutyCodes" labelProperty="workDutyNames" />
      	   				</html:select><br/>
	      	   			公司電話：<html:text property="companyPhone" size="10" maxlength="20"/>&nbsp;&nbsp;
	      	   			工作職稱：<html:text property="workTitle" size="10" maxlength="20"/><br>
	      	   			主管姓名：<html:text property="bossName" size="10" maxlength="15"/>&nbsp;&nbsp;
	      	   			聯絡方式：Email&nbsp;<html:text property="bossEmail" size="30" maxlength="100"/>&nbsp;&nbsp;
	      	   			地址&nbsp;<html:text property="bossAddr" size="30" maxlength="100"/><br>
	      	   			工作起始年月：
      	   				<html:select property="workStartYear">
      	   					<html:options property="years" labelProperty="years" />
      	   				</html:select>
      	   				<html:select property="workStartMonth">
      	   					<html:option value="0">1月</html:option>
      	   					<html:option value="1">2月</html:option>
      	   					<html:option value="2">3月</html:option>
      	   					<html:option value="3">4月</html:option>
      	   					<html:option value="4">5月</html:option>
      	   					<html:option value="5">6月</html:option>
      	   					<html:option value="6">7月</html:option>
      	   					<html:option value="7">8月</html:option>
      	   					<html:option value="8">9月</html:option>
      	   					<html:option value="9">10月</html:option>
      	   					<html:option value="10">11月</html:option>
      	   					<html:option value="11">12月</html:option>
      	   				</html:select>&nbsp;&nbsp;
      	   				年薪：&nbsp;新台幣&nbsp;
      	   				<html:select property="salaryRange">
      	   					<html:option value="0">30萬以下</html:option>
      	   					<html:option value="1">31萬~60萬</html:option>
      	   					<html:option value="2">61萬~90萬</html:option>
      	   					<html:option value="3">91萬~120萬</html:option>
      	   					<html:option value="4">121萬~150萬</html:option>
      	   					<html:option value="5">151萬以上</html:option>
      	   				</html:select>
	      	   			</span>	
	      	   		</td>
	      	   	</tr>
	      	   	
	     		<tr>
	     			<td colspan="4" class="hairLineTdF">
	     				<span id="armyType" style="display:none;">
     					<html:radio property="armyType" styleId="armyType" value="0"/>義務役
						<html:radio property="armyType" styleId="armyType" value="1"/>自願役
						<html:radio property="armyType" styleId="armyType" value="2"/>替代役&nbsp;&nbsp;
						單位名稱：<html:text property="armyName" size="10" maxlength="20"/>&nbsp;&nbsp;
	      	   			軍種階級：<html:text property="armyLevel" size="10" maxlength="20"/><br>
	     				</span>
	      	   		</td>
	      	   	</tr>
	      	   	
	      	   	<tr>
	     			<td colspan="4" class="hairLineTdF">
	     				<span id="examType" style="display:none;">
	     	   				準備考試：<input type="checkbox" name="exam" <c:if test="${xxxxx.exam eq '是'}">checked</c:if>>
	     	   			</span>
	      	   		</td>
	      	   	</tr>
	      	   	
	      	   	<tr>
	     			<td colspan="4" class="hairLineTdF">
	     				<span id="otherType" style="display:none;">
	     	   				待業：<input type="checkbox" name="other" <c:if test="${xxxxx.other eq '是'}">checked</c:if>>
	     	   			</span>
	      	   		</td>
	      	   	</tr>
	   		</table>
	   	</td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='teacher.stayTime.btn.update' bundle='TCH'/>" onclick="return check()">&nbsp;&nbsp;'
     						    + '<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Back'/>" >');
	</script>
</html:form>
</table>

<script type="text/javascript">
var type = "";
for (var i = 0; i < document.getElementsByName("invesType").length; i++) {
	if (document.getElementsByName("invesType")[i].checked) {
		type = i;
		break;
	}	
}

if (0 == type) {
	document.getElementById('learnType').style.display = 'inline';
	document.getElementById('workType').style.display = 'none';
	document.getElementById('armyType').style.display = 'none';
	document.getElementById('examType').style.display = 'none';
	document.getElementById('otherType').style.display = 'none';
} else if (1 == type) {
	document.getElementById('learnType').style.display = 'none';
	document.getElementById('workType').style.display = 'inline';
	document.getElementById('armyType').style.display = 'none';
	document.getElementById('examType').style.display = 'none';
	document.getElementById('otherType').style.display = 'none';
} else if (2 == type) {
	document.getElementById('learnType').style.display = 'none';
	document.getElementById('workType').style.display = 'none';
	document.getElementById('armyType').style.display = 'inline';
	document.getElementById('examType').style.display = 'none';
	document.getElementById('otherType').style.display = 'none';
} else if (3 == type) {
	document.getElementById('learnType').style.display = 'none';
	document.getElementById('workType').style.display = 'none';
	document.getElementById('armyType').style.display = 'none';
	document.getElementById('examType').style.display = 'inline';
	document.getElementById('otherType').style.display = 'none';
} else if (4 == type) {
	document.getElementById('learnType').style.display = 'none';
	document.getElementById('workType').style.display = 'none';
	document.getElementById('armyType').style.display = 'none';
	document.getElementById('examType').style.display = 'none';
	document.getElementById('otherType').style.display = 'inline';
}
</script>