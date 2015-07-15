<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function changeTerm() {
	var term = document.getElementById("term").value;
	if(term == "1") {
		document.getElementById("term1").style.display = "inline";
		document.getElementById("term2").style.display = "none";
	} else {
		document.getElementById("term1").style.display = "none";
		document.getElementById("term2").style.display = "inline";
	}
}

function courseCheck() {
	var iCount;
	iCount = getCookie("TeachDtimeInfoCount");
	if (iCount == 0) {
		alert("請勾選至少一門科目進行中英文簡介之新增或修改!!");
		return false;
	} 
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/TeachIntroduction" method="post" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.introduction.banner" bundle="TCH"/> - <font color="red" size="+1">${schoolYear}</font>&nbsp;學年度 第<font color="red" size="+1">${TechIntroductionForm.map.term}</font>學期 </B></div>');
	</script>
	<!-- c:if test="${not empty teacherDtime}" -->	
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
    <tr>
    	<td>
    		<span id="year" style="display:inline;">開課學年學期：  
				<html:select property="year" size="1">
 					<html:options property="years" labelProperty="years" />	    						
 				</html:select>&nbsp;學年度&nbsp;
    			第&nbsp;<html:select property="term" size="1" style="width:40px;"><!-- onclick="changeTerm()" -->
    				<html:option value="1">1</html:option>
    				<html:option value="2">2</html:option>
    			</html:select>&nbsp;學期&nbsp;<img src="images/gauge_leftarrow.png">&nbsp;<font color="red">可以查詢歷年資料</font>
    		</span>
    	</td>    	
    </tr>
    <!--
    <tr>
    	<td align="center">
    		<font color="red" size="+1">輸入課程中英文簡介之起迄時間：${begin} ~ ${end}</font>
    	</td>    	
    </tr>
    -->
    <tr>
    	<td align="center">
    		<font color="red" size="+1">如果以下部分科目簡介內容相同或類似，則可以勾選多門科目進行複製</font>
    	</td>    	
    </tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
	 					<div id="term1" style="display:inline;">
      					<display:table name="${teacherDtime}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="" class="center" >
          						<script>generateCheckbox("${row.oid}", "TeachDtimeInfo");</script>
          					</display:column>
          					<display:column title="上課時間" property="dtimeClass" sortable="true" class="left" />
        					<display:column titleKey="Course.label.className" property="className" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseName" property="chiName" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseEname" property="engName" sortable="true" class="left" />
        					<display:column titleKey="Course.label.lastModify" property="dateFormat" sortable="true" class="center" />
        					<display:column title="下載" sortable="false" class="center" href="/CIS/Course/IntrosPrint.do" paramId="Oid" paramProperty="oid">
        						<img src="images/tag_pink.gif" border="0" alt="下載簡介" />
        					</display:column>   					
      					</display:table>
      					</div>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>    
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='teacher.introductionSearch.btn.search' bundle="TCH" />" class="CourseButton">&nbsp;' + 
   		'<INPUT type="submit" name="method" value="<bean:message key='teacher.introduction.btn.add' bundle="TCH" />" onclick="return courseCheck();" class="CourseButton">');
   	</script>
	<!-- /c:if -->
</html:form>
</table>
<script type="text/javascript">
	/*changeTerm();*/
</script>