<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Example/SelectStmd_leo" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_view_icons.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">表單輸入範例</font></div>		
		</td>
	</tr>
	
	<tr>
	  <td>
		<table class="hairLineTable" width="99%" id="table1">
			<tr>
				<td class="hairLineTdF" width="100">學號</td>
				<td class="hairLineTd"><input type="text" name="stmd_no" id="stmd_no" value=""/></td>			
				<td class="hairLineTdF" width="100">姓名</td>
				<td class="hairLineTd"><input type="text" name="stmd_name" id="stmd_name" value=""/></td>
			</tr>
		</table>
	  </td>
	</tr>
	<tr>
	  <td>
		<table class="hairLineTable" width="99%" id="table1">
			<tr>
				<td class="hairLineTdF" width="100">班級編號</td>
				<td class="hairLineTd"><input type="text" name="class_no" id="class_no" value=""/></td>			
				<td class="hairLineTdF" width="100">班級名稱</td>
				<td class="hairLineTd"><input type="text" name="class_name" id="class_name" value=""/></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		  <input type="submit" name="method" class="gCancle" value="<bean:message key='Create'/>" />
		  <input type="submit" name="method" class="green"   value="<bean:message key='Query'/>" />
		  
		</td>
	</tr>
	
	<c:if test="${Fn != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${Fn}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty Fn}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(Fn)}, 'list');</script>" class="center" >
	                <script>generateCheckbox("${row.Oid}", "list");</script></display:column> 	              
	              <display:column title="班級"	property="ClassName"  sortable="true" class="center" />
	              <display:column title="學號"	property="Stmd_No"	  sortable="true" class="center" />
	              <display:column title="姓名"	property="Stmd_Name"  sortable="true" class="center" />   
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
	  <tr>
		<td class="fullColorTable" align="center">
		  <input type="submit" class="gCancle" name="method" value="<bean:message key='Modify'/>" onClick="return myTest()" class="CourseButton"/> 
          <input type="submit" class="gSubmit" name="method" value="<bean:message key='Delete'/>" onClick="return myTest()" class="CourseButton"/>		  
		</td>
	</tr>
	</c:if>
	<input type="hidden" name="myOpen" value="${myOpen}" />
	<input type="hidden" name="showType" value="${showType}" />  
	<c:if test="${myOpen == 'open'}" >                                 
      <tr>
	  <td>
		<table class="hairLineTable" width="99%" id="table1">
			<tr>
				<td class="hairLineTdF" width="100">學號</td>
				<td class="hairLineTd"><input type="text" name="stmd_no" id="stmd_no" value=""/></td>			
				<td class="hairLineTdF" width="100">姓名</td>
				<td class="hairLineTd"><input type="text" name="stmd_name" id="stmd_name" value=""/></td>
			</tr>
		</table>
	  </td>
	</tr>
	<tr>
	  <td>
		<table class="hairLineTable" width="99%" id="table1">
			<tr>
				<td class="hairLineTdF" width="100">班級編號</td>
				<td class="hairLineTd"><input type="text" name="class_no" id="class_no" value=""/></td>			
				<td class="hairLineTdF" width="100">班級名稱</td>
				<td class="hairLineTd"><input type="text" name="class_name" id="class_name" value=""/></td>
			</tr>
		</table>
		</td>
	</tr>
<!-- 資料輸入列 End -->
<!-- 存檔按鍵列 Start -->          
    <tr>
      <td class="hairLineTd" align="center">
        <input type="submit" name="method" value="<bean:message key='Save'/>" onClick="return mySave()" class="CourseButton"/>
        <hr class="myHr"/>
      </td>
    </tr>
  </c:if>
	
</html:form>
</table>
<script>
  function myTest()   //執行修改 刪除前的檢查作業
  {
    var iCount;
    iCount = getCookie("FNListCount");
    ID = getCookie("FNList");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料!!");
      return false;
    }
    return true;    
  }
</script>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>