<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Personnel/HRChecker" method="post" enctype="multipart/form-data" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/folder_bug.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;人事基本資料查核&nbsp;<input type="hidden" name="exSearch" value="${HRmanagerForm.map.exSearch}" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td nowrap style="padding:5px;">
    			選擇查核項目&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" align="center" nowrap>項目</td>
				<td class="hairLineTd">
				
				<select name="checkOpt" style="font-size:16px;">
					<option value=""></option>
					<option value="checkTutor">本學期導師更新</option>
					<option value="checkId">身分證字號查核</option>
				</select>
				
				</td>
				<td class="hairLineTdF">
				<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Execute'/>"
						   class="CourseButton">
				</td>
			</tr>
		</table>		
		
		</td>
	</tr>
	
	
	
<c:if test="${checkType=='checkTutor'}">	
	<tr>
		<td>
		
		
		<display:table name="${checkTutor}" export="true" id="row" sort="list" excludedParams="*" class="list">
	        <display:column title="教師編號" property="idno" sortable="true" class="left" />
	        <display:column title="教師姓名" property="cname" sortable="true" class="left" />
	        <display:column title="班級代碼"  property="ClassNo" sortable="true" class="left" />
	        <display:column title="班級名稱" property="ClassName" sortable="true" class="left" />	        
	      </display:table>		
		</td>
	</tr>
</c:if>

<c:if test="${checkType=='checkId'}">
	<tr>
		<td>		
		<display:table name="${checkId}" export="true" id="row" sort="list" excludedParams="*" class="list">
	        <display:column title="教師編號" property="idno" sortable="true" class="left" />
	        <display:column title="教師姓名" property="cname" sortable="true" class="left" />
	        <display:column title="查核結果"  property="check" sortable="true" class="left" />        
	      </display:table>		
		</td>
	</tr>
</c:if>
	
	
	
	
	
	
	
	
	
	
	
	
	
	<tr height="30">
		<td class="fullColorTable">
		
		
		</td>
	</tr>	
</html:form>
</table>