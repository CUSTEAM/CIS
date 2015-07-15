<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Personnel/HRmanager" enctype="multipart/form-data" method="post" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/16-manager-st.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;人事基本資料管理&nbsp;<input type="hidden" name="exSearch" value="${HRmanagerForm.map.exSearch}" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 查詢 start -->
<c:if test="${Dtype==null}">
<c:import url="/pages/personnel/HRManager/search.jsp"/>
</c:if>
<!-- 查詢 end-->

<!-- 清單模式 -->
<c:if test="${editMode=='list'}">
<tr>
	<td>
	<c:import url="/pages/personnel/HRManager/list.jsp"/>
	</td>
</tr>
</c:if>
<!-- 清單模式 end -->


<!-- 個人模式 -->
<c:if test="${editMode=='edit'}">

<c:if test="${Dtype=='on'}">
<tr>
	<td>
	<c:import url="/pages/personnel/HRManager/leave_ready.jsp"/>
	</td>
</tr>
</c:if>

<c:if test="${Dtype=='new'}">
<tr>
	<td>
	<c:import url="/pages/personnel/HRManager/new.jsp"/>
	</td>
</tr>
</c:if>

<c:if test="${Dtype=='off'}">
<tr>
	<td>
	<c:import url="/pages/personnel/HRManager/back_ready.jsp"/>
	</td>
</tr>
</c:if>

</c:if>
<!-- 個人模式 end -->


<!-- 在職員工確認頁 start -->
<c:if test="${editMode=='leave'}">
<tr>
	<td>
<c:import url="/pages/personnel/HRManager/leave_confirm.jsp"/>
	</td>
</tr>
</c:if>
<!-- 在職員工確認頁 end -->

<!-- 離職員工確認頁 start -->
<c:if test="${editMode=='back'}">
<tr>
	<td>
<c:import url="/pages/personnel/HRManager/back_confirm.jsp"/>
	</td>
</tr>
</c:if>
<!-- 離職員工確認頁 end -->


<!-- 新進員工確認頁 start -->
<c:if test="${editMode=='add'}">
<tr>
	<td>
<c:import url="/pages/personnel/HRManager/add_confirm.jsp"/>
	</td>
</tr>
</c:if>
<!-- 離職員工確認頁 end -->

	
</html:form>



















<!-- 個人模式 (其他資訊) -->
<c:if test="${editMode=='edit'}">

<!-- column 9 start -->
<tr>
	<td>
		
	<c:import url="/pages/personnel/HRManager/jobHist.jsp"/>
	
	</td>
</tr>
<!-- column 9 end -->
<!-- column 10 聘書歷程 start -->

		<tr>
			<td>
				
			<c:import url="/pages/personnel/HRManager/contract.jsp"/>
			
			</td>
		</tr>

<!-- column 10 end -->

<!-- column 10.3 昇等 start>

		<tr>
			<td>
				
			c:import url="/pages/personnel/HRManager/peprom.jsp"/
			
			</td>
		</tr>
<column 10.3 昇等 END -->

<!-- column 10.5 學歷程  start -->

		<tr>
			<td>
				
			<c:import url="/pages/personnel/HRManager/degree.jsp"/>
			
			</td>
		</tr>

<!-- column 10.5 end -->
		
<!-- column 11 執照歷程  start -->

		<tr>
			<td>
				
			<c:import url="/pages/personnel/HRManager/licence.jsp"/>
			
			</td>
		</tr>
<!-- column 11 執照歷程  END -->

<!-- column 12 實務經驗  start -->

		<tr>
			<td>
				
			<c:import url="/pages/personnel/HRManager/pehist.jsp"/>
			
			</td>
		</tr>
<!-- column 12 實務經驗  END -->

<!-- column 13 進修  start>

		<tr>
			<td>
				
			c:import url="/pages/personnel/HRManager/pestud.jsp"/
			
			</td>
		</tr>
<column 13 進修END -->



<tr height="30">
	<td class="fullColorTable">
	
	</td>
</tr>

</c:if>
<!-- 個人模式 end -->














</table>
<script>
	function clearQuery(){
		document.getElementById("idno").value="";
		document.getElementById("cname").value="";
		
		
		<c:if test="${editMode==null}">
		document.getElementById("fscname").value="";
		document.getElementById("fsidno").value="";
		</c:if>
	}
	
	function showSearch(){
		if(document.getElementById("expand").style.display=="none"){
			document.getElementById("expand").style.display="inline";
			document.getElementById("fast").style.display="none";
			document.getElementById("exSearch").value="1";
		}else{
			document.getElementById("expand").style.display="none";
			document.getElementById("fast").style.display="inline";
			document.getElementById("exSearch").value="";
		}
	
	
	}
</script>

<script>
function getAddr(){
	document.getElementById("paddr").value=document.getElementById("caddr").value;
	document.getElementById("pzip").value=document.getElementById("czip").value;
}
</script>

<script>
function showInfo(id){
	if(document.getElementById(id).style.display=="none"){
		document.getElementById(id).style.display="inline";	
	}else{
		document.getElementById(id).style.display="none";
	}
}
</script>

<script>
function minMax(id){
	if(document.getElementById(id).rows==20){
		document.getElementById(id).rows=1;
		document.getElementById(id).cols=76;
	}else{
		document.getElementById(id).rows=20;
		document.getElementById(id).cols=76;
	}
		
}
</script>

<script>
function getPaddr(){
	document.getElementById("czipNo").value=document.getElementById("pzipNo").value;
	document.getElementById("caddr").value=document.getElementById("paddr").value;
}

function getCaddr(){
	document.getElementById("pzipNo").value=document.getElementById("czipNo").value;
	document.getElementById("paddr").value=document.getElementById("caddr").value;
}

function getSname(){
	document.getElementById("sname").value=document.getElementById("pcode").value+document.getElementById("Director").value;
}
</script>

<script>
	function jumpMenu(targ,selObj,restore){
		eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
		eval(targ+".location.target='_blank'");
		if (restore) selObj.selectedIndex=0;
}
</script>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>