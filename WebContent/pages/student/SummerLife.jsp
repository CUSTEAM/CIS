<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/folder_sum.gif" />
				</td>
				<td align="left">
				&nbsp;暑修資訊&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>

<!-- 缺曠 - 核對 start -->
<tr>
	<td>
		<table width="100%">
						<tr>
							<td>
								<table class="hairLineTable" width="99%">
								<tr>
								<td class="hairLineTdF">
									<table width="100%" onclick="showInfo('myDilg'), showGreen()">
										<tr>
											<td>
											&nbsp;&nbsp;<img id="green" src="images/folder_green.png"><img id="green_open" src="images/folder_green_open.png" style="display:none;">
											</td>
											<td align="left" width="100%" style="cursor:pointer;">
											點擊此處任意空白開啟缺曠記錄 (核對用)
											</td>
										</tr>
										<tr>
											<td colspan="2" id="myDilg" style="display:none">
												<table width="100%">
													<tr>
														<td>
															<display:table name="${myDilg}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        												<display:column title="日期" property="ddate" sortable="true" class="left" />
	        												<display:column title="第1節" property="abs1" sortable="true" class="left" />
	        												<display:column title="第2節"  property="abs2" sortable="true" class="left" />
	        												<display:column title="第3節" property="abs3" sortable="true" class="left" />
	        												<display:column title="第4節" property="abs4" sortable="true" class="left" />
	        												<display:column title="第5節" property="abs5" sortable="true" class="left" />
	        												<display:column title="第6節" property="abs6" sortable="true" class="left" />
	        												<display:column title="第7節" property="abs7" sortable="true" class="left" />
	        												<display:column title="第8節" property="abs8" sortable="true" class="left" />
	        												<display:column title="第9節" property="abs9" sortable="true" class="left" />
	        												<display:column title="第10節" property="abs10" sortable="true" class="left" />
	        												<display:column title="第11節" property="abs11" sortable="true" class="left" />
	      													</display:table>
	      												</td>
	      											</tr>
	      											<tr>
	      												<td align="right">
	      												<img src="images/16-IChat-bubble.jpg"> 點擊此處任意空白可關閉清單
	      												</td>
	      											</tr>
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
	</td>
</tr>
<!-- 缺曠 end -->


<!-- 缺曠 - 查扣考 start -->
<tr>
	<td>
		<table width="100%">
						<tr>
							<td>
<script>
function showBlue(){

	if(document.getElementById('blue').style.display=='none'){
		document.getElementById('blue').style.display='inline';
		document.getElementById('blue_open').style.display='none';
	}else{
		document.getElementById('blue').style.display='none';
		document.getElementById('blue_open').style.display='inline';
	}
}
</script>
								<table class="hairLineTable" width="99%">
								<tr>
								<td class="hairLineTdF">
									<table width="100%">
										<tr>
											<td>
											&nbsp;&nbsp;<img id="blue" src="images/folder_blue.png"><img id="blue_open" src="images/folder_blue_open.png" style="display:none;">
											</td>
											<td align="left" width="100%" style="cursor:pointer;" onClick="showObj('dilg'), showBlue()">
											點擊此處任意空白查看缺曠記錄 (查扣考)
											</td>
										</tr>
										<tr>
											<td colspan="2" id="dilg" style="display:none">
											<table width="100%">
													<tr>
														<td>
											
											<display:table name="${dilgs}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        								<display:column title="開課班級代碼" property="csdepart_class" sortable="true" class="left" />
	        								<display:column title="開課班級名稱" property="name" sortable="true" class="left" />
	        								<display:column title="課程代碼"  property="cscode" sortable="true" class="left" />
	        								<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	        								<display:column title="上課時數" property="thour" sortable="true" class="left" />
	        								<display:column title="扣考時數" property="overthour" sortable="true" class="left" />
	        								<display:column title="已曠時數" property="nonthour" sortable="true" class="left" />
	        								<display:column title="扣考狀態" property="status" sortable="true" class="left" />
	      									</display:table>
	      												</td>
	      											</tr>
	      											<tr>
	      												<td align="right">
	      												<img src="images/16-IChat-bubble.jpg"> 點擊此處任意空白可關閉清單
	      												</td>
	      											</tr>
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
	</td>
</tr>
<!-- 缺曠 end -->

<!-- 成績 start -->
<tr>
	<td>
		<table width="100%">
						<tr>
							<td>
								<table class="hairLineTable" width="99%">
								<tr>
								<td class="hairLineTdF">
<script>
function showOrange(){

	if(document.getElementById('orange').style.display=='none'){
		document.getElementById('orange').style.display='inline';
		document.getElementById('orange_open').style.display='none';
	}else{
		document.getElementById('orange').style.display='none';
		document.getElementById('orange_open').style.display='inline';
	}
}
</script>
									<table width="100%" onclick="showInfo('score'), showOrange()">
										<tr>
											<td>
											&nbsp;&nbsp;<img id="orange" src="images/folder_orange.png"><img id="orange_open" src="images/folder_orange_open.png" style="display:none;">
											</td>
											<td align="left" width="100%" style="cursor:pointer;">
											點擊此處任意空白查看暑修成績
											</td>
										</tr>
										<tr>
											<td colspan="2" id="score" style="display:none">
											<table width="100%">
													<tr>
														<td>
											
											<display:table name="${myScore}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        								<display:column title="梯次" property="seqno" sortable="true" class="left" />
	        								<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	        								<display:column title="課程代碼"  property="cscode" sortable="true" class="left" />
	        								<display:column title="開課班級" property="name" sortable="true" class="left" />
	        								<display:column title="班級代碼" property="no" sortable="true" class="left" />
	        								<display:column title="選別" property="opt" sortable="true" class="left" />
	        								<display:column title="時數" property="thour" sortable="true" class="left" />
	        								<display:column title="學分" property="credit" sortable="true" class="left" />
	        								<display:column title="成績" property="score" sortable="true" class="left" />
	      									</display:table>
														</td>
	      											</tr>
	      											<tr>
	      												<td align="right" style="cursor:pointer;">
	      												<img src="images/16-IChat-bubble.jpg"> 點擊此處任意空白可關閉清單
	      												</td>
	      											</tr>
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
	</td>
</tr>
<!-- 成績 over -->
<tr height="30">
	<td class="fullColorTable">
	
	</td>
</tr>
</table>



<script>
	function showInfo(id){
		if(document.getElementById(id).style.display=="inline"){
			document.getElementById(id).style.display="none"
		}else{
			document.getElementById(id).style.display="inline"
		}
		
	}
</script>



<script>
function showGreen(){

	if(document.getElementById('green').style.display=='none'){
		document.getElementById('green').style.display='inline';
		document.getElementById('green_open').style.display='none';
	}else{
		document.getElementById('green').style.display='none';
		document.getElementById('green_open').style.display='inline';
	}
}
</script>