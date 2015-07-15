<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Chief/CoanswReview" method="post" onsubmit="init('處理中, 請稍後')">
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/chart_line.gif">
				</td>
				<td align="left">
				&nbsp;教學評量&nbsp;<input type="hidden" name="searchType" value="sim" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table width="100%">
		<tr>
		<td>
		
		<table width="100%" cellpadding="0" cellspacing="0" style="cursor:pointer;">
  			<tr onClick="showSearch('sim', 'exp')">
  				<td width="10" align="left" nowrap onClick="showSearch('sim')">
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/folder_find.gif" id="searchNorm" 
    			 onMouseOver="showHelpMessage('點擊此處 開啟/關閉 進階搜尋', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)">
    			</td>
    			<td nowrap>
    			快速搜索&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
		
		<table id="sim">
			<tr>
				<td>
				
				<table class="hairLineTable">
				<tr>
				<td class="hairLineTdF">
				設定範圍
				</td>
				<td class="hairLineTd">
					<c:set var="campusSel" value="${OpenCourseForm.map.campusInCharge2}"/>
	  			   	<c:set var="schoolSel" value="${OpenCourseForm.map.schoolInCharge2}"/>
	  			   	<c:set var="deptSel"   value="${OpenCourseForm.map.deptInCharge2}"/>
	  			   	<c:set var="classSel"  value="${OpenCourseForm.map.classInCharge2}"/>
	  			   	<%@ include file="/pages/include/ClasSelectAll4John.jsp"%>
				</td>
				</td>
				<td class="hairLineTdF" width="30" align="center">
					<img src="images/icon_component.gif">
				</td>
				</tr>
				
				</table>
				
				</td>
			</tr>
		</table>		
		
		</td>
		</tr>
		
		<tr>
		<td>
		
		<table width="100%" cellpadding="0" cellspacing="0" style="cursor:pointer;">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/folder_explore.gif" id="searchEx" 
    			 onMouseOver="showHelpMessage('點擊此處 開啟/關閉 進階搜尋', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)">
    			</td>
    			<td nowrap onClick="showSearch('exp', 'sim')">
    			進階搜索&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>		
		
		<table id="exp" style="display:none">
			<tr>
				<td>
				
				<table class="hairLineTable">
				<tr>
				<td class="hairLineTdF" width="60" align="center">
				學制
				</td>
				<td class="hairLineTd">
					<SELECT name="schoolType" id="schoolType" onChange="clearAll(this.id, this.value)">
						<option value="">所有學制</option>
						<c:forEach items="${allSchoolType}" var="as">
						<option value="${as.idno}" <c:if test="${SetCsquestionaryForm.map.schoolType==as.idno}">selected</c:if>>${as.name}</option>
					</c:forEach>
					</SELECT>
				</td>
				</td>
				
				<td class="hairLineTdF" width="30" align="center">
					<img src="images/icon_component.gif">
				</td>				
				</tr>
				
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable">
				<tr>
				<td class="hairLineTdF" width="60" align="center">
				系所
				</td>
				<td class="hairLineTd">
					<SELECT name="dept" id="dept" onChange="clearAll(this.id, this.value)">
						<option value="">所有系所</option>
						<c:forEach items="${allDept}" var="ad">
						<option value="${ad.idno}" <c:if test="${SetCsquestionaryForm.map.schoolType==ad.idno}">selected</c:if>>${ad.name}</option>
					</c:forEach>
					</SELECT>
				</td>
				</td>
				
				<td class="hairLineTdF" width="30" align="center">
					<img src="images/icon_component.gif">
				</td>				
				</tr>
				
				</table>
				
				</td>
			</tr>
			
			<tr>
				<td>
				
				<table class="hairLineTable">
				<tr>
				<td class="hairLineTdF" width="60" align="center">
				教師
				</td>
				<td class="hairLineTd">
				<input type="text" size="6"
				onkeyup="getAny(this.value, 'technameS', 'techidS', 'empl', 'name')" autocomplete="off"
				onclick="this.value='', document.getElementById('techidS').value='', clearAll(this.id, this.value)" name="teacherName" 
				onMouseOver="showHelpMessage('請輸入教師姓名, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"
				id="technameS" value="${sOpenCourseForm.map.teacherName}"/><input type="text" name="teacherId" id="techidS" 
				size="8" style="ime-mode:disabled" autocomplete="off"
				value="${sOpenCourseForm.map.teacherId}" onMouseOver="showHelpMessage('請輸入教師代碼', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"
				onkeyup="if(this.value.length>2)getAny(this.value, 'techidS', 'technameS', 'empl', 'no')"
				onclick="this.value='', document.getElementById('technameS').value='', clearAll(this.id, this.value)"/>
				</td>
				</td>
				
				<td class="hairLineTdF" width="30" align="center">
					<img src="images/16-exc-mark.png">
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
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Query'/>"
						   class="CourseButton"><input type="checkBox"disabled>我只想看我的系
		</td>
	</tr>
	
	
	
	
	<c:if test="${allCoansw!=null}">
	<tr>
		<td>
		<display:table name="${allCoansw}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list" >
			<display:column title="開課班級" property="ClassName" sortable="true" class="center" />
			<display:column title="班級代碼" property="depart_class" sortable="true" class="center" />
			<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
			<display:column title="課程代碼" property="cscode" sortable="true" class="center" />
			
			<display:column title="選別" property="opt" sortable="true" class="center" />
			<display:column title="學分" property="credit" sortable="true" class="center" />
			<display:column title="型態" property="elearning" sortable="true" class="center" />
			
			<display:column title="任課教師" property="cname" sortable="true" class="center" />
			
			
			<display:column title="樣本數" property="sumAns" sortable="true" class="center" />
			<display:column title="總平均" property="total" sortable="true" class="center" />
		</display:table>
		</td>
	</tr>
	<tr height="40">
		<td>
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="CFE69F" width="98%" align="center">
  			<tr>
    			<td bgcolor="#FFFFFF">
    				<table width="100%"> 
    					<tr>
    						<td align="left" width="1">
    						<img src="images/ico_file_excel.png" border="0">
    						</td>
    						<td>
    						<a target="" href="course/export/list4Coansw.jsp?type=excel">點擊下載細節報表</a>
    						</td>
    					</tr>
    				</table>
    			</td>
    		</tr>
    	</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
</c:if>
	
	
	
	
</html:form>
</table>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<script>
function showSearch(a, b, type){
	object=document.getElementById(a);
	object1=document.getElementById(b);
	
	if(object.style.display=="none"){
		object.style.display="inline";
		object1.style.display="none";
	}else{
		object.style.display="none";
		object1.style.display="inline";
	}
	
	if(document.getElementById("sim").style.display=="inline"){
		document.getElementById("searchType").value="sim";
	}else{
		document.getElementById("searchType").value="exp";
	}
}

function clearAll(id, value){
	
	document.getElementById("schoolType").value="";
	document.getElementById("dept").value="";
	document.getElementById("techidS").value="";
	document.getElementById("teacherName").value="";
	
	document.getElementById(id).value=value;
}
</script>