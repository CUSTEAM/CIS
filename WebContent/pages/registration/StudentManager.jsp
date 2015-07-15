<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Registration/StudentManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/16-manager-st.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;學籍管理&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>	
<!-- 標題列 end -->
<!-- 主查詢界面 start-->
<c:if test="${Gtype==null}">

<!-- 查詢 start -->
<c:import url="/pages/registration/studentManage/search.jsp"/>
<!-- 查詢 end-->
	<!-- 幫助列 start -->
	<tr>
		<td>		
		<table width="100%" align="center" id="help" style="display:none;">
			<tr>
				<td>				
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF">										
						<OL>						
							<li>標題
							<li>標題								
								<ul>
									<li>子標題
									<li>子標題
									<li>子標題
								</ul>							
							<li>標題
							<li>標題
							<li>標題
										
						</OL>
						</td>
					</tr>
				</table>				
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- 幫助列 end -->
	<tr>
		<td class="fullColorTable" align="center" width="100%">
		<table>
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   id="continue"
						   onMouseOver="showHelpMessage('學號完整並對應姓名會按照學生目前狀態進行下一步驟,<br>'+
						   '若無對應則認定為新生直接進入轉入模式', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)"
						   value="<bean:message key='Continue'/>"
						   class="gSubmit">   
						   
						   <INPUT type="submit"
						  	name="method" id="Cancel"
						   	value="<bean:message
						   	key='Cancel'/>"
						   	class="gCancle" 
						   	onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
						   	onMouseOut="showHelpMessage('', 'none', this.id)" />
						   
						   <input type="button" class="gCancle" value="返回" id="back"
							onclick="location='/CIS/Registration/StudentDirectory.do';"
							onMouseOver="showHelpMessage('返回上層功能列表', 'inline', this.id)" 
							onMouseOut="showHelpMessage('', 'none', this.id)"/>
						   
						   <input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
						    onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
							onMouseOut="showHelpMessage('', 'none', this.id)"/>
							
													
							
							<!-- div style="display:none;" id="print">							
							<select name="printoption">
								<option>清單表格</option>
								<option value="countClass">班級數量統計表</option>
							</select>													
							<INPUT type="submit" onclick="setTimeout(function(){uninit();}, 1000);"
						   name="method" id="Print"
						   value="<bean:message
						   key='Print'/>"
						   class="gCancle" 
						   onMouseOver="showHelpMessage('開啟指定報表', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)" />						   	
							</div>						   
						   <INPUT type="button"
						   name="method" 
						   id="openPrint"
						   value="找報表"
						   class="gCancle" 
						   onClick="this.style.display='none'; document.getElementById('print').style.display='inline';"
						   onMouseOver="showHelpMessage('列出所有可能列印的報表', 'inline', this.id)" 
						   onMouseOut="showHelpMessage('', 'none', this.id)" /-->
						   
						   
				
				</td>
			</tr>
		</table>
		</td>
	</tr>

<!-- 列表 start -->	
<c:if test="${students!=null}">
<c:import url="/pages/registration/studentManage/list.jsp"/>
</c:if>	
	
</c:if>
<!-- 主查詢界面 end-->

<!-- 新生轉入作業 start -->
<c:if test="${Gtype=='add'}">
<c:import url="/pages/registration/studentManage/join_ready.jsp"/>
</c:if>
<!-- 新生轉入作業 end -->

<!-- 休退畢作業 start -->
<c:import url="/pages/registration/studentManage/leave_ready.jsp"/>
<!-- 休退畢作業 end -->

<!-- 復學作業 start -->
<c:if test="${Gtype=='editGstmd'}">
<c:import url="/pages/registration/studentManage/back_ready.jsp"/>
</c:if>
<!-- 復學作業 end -->

<!-- 確定轉入 start -->
<c:if test="${Gtype=='confirmAdd'}">
<c:import url="/pages/registration/studentManage/join_confirm.jsp"/>
</c:if>
<!-- 確定轉入 end -->

<!-- 確定復學 start-->
<c:if test="${Gtype=='confirmBack'}">
<c:import url="/pages/registration/studentManage/back_confirm.jsp"/>
</c:if>
<!-- 確定復學 end-->

<!-- 確定休退畢移 start-->
<c:if test="${Gtype=='confirmGoodBye'}">
<c:import url="/pages/registration/studentManage/leave_confirm.jsp"/>	
</c:if>
<!-- 確定休退畢移 end-->
</html:form>

<!-- 私人資料 start-->	
<c:if test="${Gtype!=null}">
<c:import url="/pages/registration/studentManage/listScore.jsp"/>
<c:import url="/pages/registration/studentManage/listGmark.jsp"/>
<c:import url="/pages/registration/studentManage/listRmark.jsp"/>
<c:import url="/pages/registration/studentManage/QuitResume.jsp"/>
<c:import url="/pages/registration/studentManage/lisTran.jsp"/><!-- 轉班  -->
<c:import url="/pages/registration/studentManage/lisTraNo.jsp"/><!-- 轉學入  -->
	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">
		
		</td>
	</tr>
</c:if>
<!-- 私人資料 end-->
</table>

<script>
	function takeTraNo(type){
		document.getElementById('traStu').style.display="none";
		document.getElementById('newStu').style.display="inline";
		if(type=='tra'){
			document.getElementById('traStu').style.display="inline";
			document.getElementById('newStu').style.display="none";
		}
	}
</script>

<script>
	function showInfo(id){
		if(document.getElementById(id).style.display=='none'){
			document.getElementById(id).style.display='inline';
		}else{
			document.getElementById(id).style.display='none';
		}
	}
</script>

<script>
	function copyAddr(){
		document.getElementById('perm_post').value=document.getElementById('curr_post').value;
		document.getElementById('perm_addr').value=document.getElementById('curr_addr').value;
	}
</script>

<script>
	function clearQuery(){
		document.getElementById("studentNo").value="";
		document.getElementById("studentName").value="";
	}							
</script>

<script>
	function showSearch(){
		if(document.getElementById('searchBar').style.display=='none'){
			document.getElementById('searchBar').style.display='inline';
			document.getElementById('onlyEdit').style.display='none';
			
			document.getElementById('exSearch').value='1'; //進階搜尋開關
			document.getElementById('SstudentNo').disabled=false; //進階搜尋學號
			document.getElementById('SstudentName').disabled=false; //進階搜尋姓名
			document.getElementById('studentNo').disabled=true; //一般搜尋學號
			document.getElementById('studentName').disabled=true; //一般搜尋姓名
		}else{
			document.getElementById('searchBar').style.display='none';
			document.getElementById('onlyEdit').style.display='inline';
			//document.getElementById('studentNo').value='';
			//document.getElementById('studentName').value='';
			
			document.getElementById('exSearch').value='';
			document.getElementById('SstudentNo').disabled=true;
			document.getElementById('SstudentName').disabled=true;
			document.getElementById('studentNo').disabled=false;
			document.getElementById('studentName').disabled=false;
		}
	}
</script>

<script>
	function jumpMenu(targ,selObj,restore){
		eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
		eval(targ+".location.target='_blank'");
		if (restore) selObj.selectedIndex=0;
}
</script>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>
