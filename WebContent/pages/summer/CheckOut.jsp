<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Summer/CheckOut" method="post" onsubmit="init('查詢進行中, 請稍後')">
<script>
	generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/icon/application_cascade.gif"></td>'+
										'<td>暑修批次處理</td>'+
									'</tr>'+
								'</table>');
</script>
<tr>
	<td>
		<table>
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">
						選擇梯次</td>
						
						<td class="hairLineTd">
						<select name="seqno">
							<option <c:if test="${SummerCheckOutForm.map.seqno=='%'}" > selected</c:if> value="">所有</option>
							<c:forEach items="${sweek}" var="sweek">
		    					<option <c:if test="${SummerCheckOutForm.map.seqno==sweek.seqno}" > selected</c:if> value="${sweek.seqno}">第${sweek.seqno}梯次</option>
		    				</c:forEach>
		    			</select>
						</td>
						<td class="hairLineTdF" width="30" align="center">
							<img src="images/icon_component.gif" />
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">班級代碼</td>
						
						<td class="hairLineTd">
						<input type="text" id="departClass" name="departClass"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						 value="${SummerCheckOutForm.map.departClass}"
						 onkeyup="getAny(this.value, 'departClass', 'classLess', 'Sabbr', 'no')"
						 onclick="this.value='', document.getElementById('classLess').value=''"/><input 
						 type="text" name="classLess" id="classLess"
						value="${SummerCheckOutForm.map.classLess}" size="12"
						 onkeyup="getAny(this.value, 'classLess', 'departClass', 'Sabbr', 'name')"
						 onclick="this.value='', document.getElementById('departClass').value=''"/>
						</td>
						<td class="hairLineTdF" width="30" align="center">
							<img src="images/16-exc-mark.gif" />
						</td>
					</tr>
			
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF"><bean:message key="setCourse.label.courseNumber" bundle="COU"/></td>
						<td class="hairLineTd">
						<input type="text" name="courseNumber" id="cscodeS" size="8"
						autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						value="${SummerCheckOutForm.map.courseNumber}"
						onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
						onclick="this.value='', courseName.value=''"/><input type="text" autocomplete="off"
						name="courseName" id="csnameS" size="16"
						value="${SummerCheckOutForm.map.courseName}"
						onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
						onkeydown="document.getElementById('Acsname').style.display='none';"
						onclick="this.value='', courseNumber.value=''"/>
						</td>
						<td class="hairLineTdF" width="30" align="center">
							<img src="images/16-exc-mark.gif" />
						</td>
					</tr>	
				</table>
				
				</td>
			</tr>
			
		</table>
		
	</td>
</tr>









<tr valign="middle" height="30">
	<td class="fullColorTable" align="center" width="100%">
		<table>
			<tr>
				<td>
				<SELECT name="checkOpt" onchange="showOpt(this.value)" class="CourseButton">
					<OPTION value="">選擇批次處理項目</OPTION>
					<OPTION <c:if test="${checkOpt=='checkExceed'}" >selected</c:if> value="checkExceed">扣考名單</OPTION>
					<OPTION <c:if test="${checkOpt=='checkReturnMoney'}" >selected</c:if> value="checkReturnMoney">退費名單</OPTION>
					<OPTION <c:if test="${checkOpt=='checkScoreHist'}" >selected</c:if> value="checkScoreHist">成績建檔</OPTION>
					<OPTION <c:if test="${checkOpt=='checkReOption'}" >selected</c:if> value="checkReOption">正常學期選課衝堂</OPTION>					
				</SELECT>
				</td>
				
				<!--各項查核的附加選單 start-->
				<td id="expLess" style="display:none">
				差 
				<input type="text" name="expLess" value="999" onclick="this.value=''" size="1"> 
				節扣考				
				</td>
				
				<td id="pay" style="display:none">
				每學分$<input type="text" name="creditPay" value="0" onclick="this.value=''" size="1"> 
				每小時$<input type="text" name="hourPay" value="0" onclick="this.value=''" size="1">
							
				</td>
				
				<!--各項查核的附加選單 end-->
				
				<td>
				<INPUT type="submit"
						   name="method" id="checkout"
						   value="<bean:message key='CourseCheckOpt'/>"
						   class="gSubmit">
				</td>
				<td id="inyear" style="display:none">
				
				</td>
			</tr>
		</table>
	</td>
</tr>




<!--查扣考 start -->
<c:if test="${checkOpt=='checkExceed'}">
<tr>
	<td>
		<display:table name="${dilgs}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="梯次" property="seqno" sortable="true" class="left" />
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="開課班級代碼" property="csdepart_class" sortable="true" class="left" />
		<display:column title="開課班級名稱" property="name" sortable="true" class="left" />
		<display:column title="課程代碼"  property="cscode" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="上課時數" property="thour" sortable="true" class="left" />
		<display:column title="扣考時數" property="overthour" sortable="true" class="left" />
		<display:column title="已曠時數" property="nonthour" sortable="true" class="left" />
		<display:column title="狀態" property="status" sortable="true" class="left" />
		</display:table>
	</td>
</tr>
	<tr>
		<td colspan="7" align="center" height="40">
			<table border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F" width="98%" align="center">
  				<tr>
    				<td bgcolor="#FFFFFF">
    					<table width="100%">
    						<tr>
    							<td align="left" width="400">
    							&nbsp;<a href="/CIS/List4SummerSseld"><img src="images/ico_file_excel1.png" border="0"> 學生報表</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
</c:if>
<!-- 查扣考 end -->

<!-- 成績納入歷史 start -->
<c:if test="${checkOpt=='checkScoreHist'}">
<tr>
	<td>
	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<display:table name="${fileList}" export="false" id="row" sort="list" excludedParams="*" class="list" >
		<display:column title="<script>generateTriggerAll(${fn:length(fileList)}, 'fileList'); </script>" class="center" >
	    	<script>generateCheckbox("${row.Oid}", "fileList")</script></display:column>
		<display:column title="梯次" property="seqno" sortable="true" class="center" />
		<display:column title="開課班級代碼" property="no" sortable="true" class="center" />
		<display:column title="開課班級名稱" property="name" sortable="true" class="center" />
		<display:column title="課程代碼"  property="cscode" sortable="true" class="center" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="任課教師" property="cname" sortable="true" class="center" />
		<display:column title="上課時數" property="thour" sortable="true" class="right" />
		<display:column title="選課人數" property="stu_select" sortable="true" class="right" />
		<display:column title="已評分" property="filed" sortable="true" class="right" />
		<display:column title="轉歷年" property="descripton" sortable="true" class="center" />
		</display:table>
	</td>
</tr>
<tr height="40">
	<td class="fullColorTable" align="center" width="100%">
	匯入學年<input type="text" name="syear" onclick="this.value=''" size="3"> 
	<INPUT type="submit"
						   name="method" id="checkout"
						   value="<bean:message key='FiledSummerScore'/>"
						   class="gGreen">
	</td>
</tr>
</c:if>
<!-- 成績納入歷史 end -->











<!--查衝堂 start -->
<c:if test="${checkOpt=='checkReOption'}">
<tr>
	<td>
	
	<display:table name="${reOption}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	<display:column title="學號" property="student_no" sortable="true" class="left" />
	<display:column title="姓名" property="student_name" sortable="true" class="left" />
	<display:column title="學生班級"  property="scName" sortable="true" class="left" />
	<display:column title="課程代碼" property="cscode" sortable="true" class="left" />
	<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	<display:column title="開課班級" property="ccName" sortable="true" class="left" />
	</display:table>
	
	
	</td>
</tr>
	<tr>
		<td colspan="7" align="center" height="40">
			<table border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F" width="98%" align="center">
  				<tr>
    				<td bgcolor="#FFFFFF">
    					<table width="100%">
    						<tr>
    							<td align="left" width="400">
    							&nbsp;<img src="images/ico_file_excel1.png" border="0"> <a href="/CIS/pages/summer/export/checkReChoose.jsp">學生報表</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
</c:if>
<!-- 查衝堂 end -->


















</html:form>
</table>

<script>
function showOpt(opt){
	//查扣考
	if(opt=='checkExceed'){
		document.getElementById('expLess').style.display="inline";
		document.getElementById('pay').style.display="none";
		document.getElementById('checkout').disabled=false;
			
	}
	
	//查退費
	if(opt=='checkReturnMoney'){
		document.getElementById('pay').style.display="inline";
		document.getElementById('expLess').style.display="none";
		document.getElementById('checkout').disabled=true;
		
	}
	
	//查成績
	if(opt=='checkScoreHist'){
		document.getElementById('expLess').style.display="none";
		document.getElementById('pay').style.display="none";
		document.getElementById('checkout').disabled=false;
		
	}
	
	//查衝堂
	if(opt=='checkReOption'){
		document.getElementById('expLess').style.display="none";
		document.getElementById('pay').style.display="none";
		document.getElementById('checkout').disabled=false;
		
	}
	
	if(opt==''){
		document.getElementById('expLess').style.display="none";
		document.getElementById('pay').style.display="none";
		
	}

}
</script>


<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>