<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<!-- 標題start -->
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/bell.gif" />
				</td>
				<td align="left">
				&nbsp;服務狀態查詢&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<html:form action="/OnlineService/CaseView" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">

	<tr>
		<td>		
		<%@ include file="CaseView/search.jsp" %>		
		</td>
	</tr>
	<tr>
		<td>
		<br>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">		
		<INPUT type="submit" id="Search" name="method" value="<bean:message key='Search'/>" class="CourseButton" 
		onMouseOver="showHelpMessage('開始查詢','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"><INPUT 
		type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">
		</td>
	</tr>

	
<c:if test="${empty cases && cases!=null}">	
	<tr>
		<td>
		<table width="99%" cellpadding="0" cellspacing="0"  class="hairLineTable">
			<tr height="35">
				<td width="100" align="center" class="hairLineTdF">
				沒有申請案件
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">		
		
		</td>
	</tr>
</c:if>	
	
	
<c:if test="${!empty cases}">
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
		  		<td class="hairLineTdF" colspan="0" rowspan="0">		  		
 				<select onchange="jumpMenu('parent',this,0)">
 					<option value="javascript:void(0)">選擇報表</option>
 					<option value="/CIS/List4OnlineServices?type=all">標準報表</option>
 				</select>
 				
 				&nbsp;<img src="images/icon/application_cascade.gif" border="0">
 				<c:set value="0" var="begin" />
				<c:set value="24" var="end" />
 				<c:if test="${fn:length(cases)>25}">
 				<a href="/CIS/OnlineService/CaseView.do?page=0">1</a>				
 				<c:forEach begin="1" end="${fn:length(cases)/25}" var="it"> 				
 				, <a href="/CIS/OnlineService/CaseView.do?page=${it}">${it+1}</a>				
 				</c:forEach>
 				頁
 				</c:if>
 				
 				<c:if test="${param.page!=null}">
 				
 				<c:set value="${begin+(param.page*25)}" var="begin" />
				<c:set value="${begin+25}" var="end" />
 				 				
 				</c:if>
				
				
				
				
				
				
				
				
				
		  		</td>		  		
		  	</tr>
		</table>
		
		</td>
	</tr>

	<tr>
		<td>		
		<%@ include file="CaseView/list.jsp" %>
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
		  		<td class="hairLineTdF">
		  		<img src="images/printer.gif" border="0">
 				<select onchange="jumpMenu('parent',this,0)">
 					<option value="javascript:void(0)">選擇報表</option>
 					<option value="/CIS/List4OnlineServices?type=all">標準報表</option>
 				</select>
		  		</td>
		  	</tr>
		</table>		
<script>
function jumpMenu(targ,selObj,restore){
			eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
			eval(targ+".location.target='_blank'");
			if (restore) selObj.selectedIndex=0;
}
</script>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">		
		<INPUT type="submit" name="method" value="<bean:message key='OK'/>" class="CourseButton"><INPUT 
		type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">
		</td>
	</tr>
</c:if>	
	
</html:form>
</table>

<script>
function checkButton(id, Oid){
	if(document.getElementById(id).value==""){
		document.getElementById(id).value="*";
		document.getElementById("status"+Oid).value="O";
		document.getElementById("checkOid"+Oid).value='*';
	}else{
		document.getElementById(id).value="";
	}
}

function checkService(id){	
	document.getElementById("checkOid"+id).value="*";	 
	document.getElementById("check"+id).checked=true;
}

function unCheckService(id){
	if(document.getElementById("check"+id).checked==true){
		document.getElementById("checkOid"+id).value="*";	 
		document.getElementById("check"+id).checked=true;
	}else{
		document.getElementById("checkOid"+id).value="";	 
		document.getElementById("check"+id).checked=false;
	}
}
</script>

<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
<%@ include file="/pages/include/MyCalendarAD.jsp" %>