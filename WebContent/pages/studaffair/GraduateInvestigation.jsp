<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
history.go(1);
function selectCheck() {
	var iCount;
	iCount = getCookie("StudentInfoCount");
	if (iCount == 0) {
		alert("請勾選清單內一位學生進行檢視!!");
		return false;
	} else if (iCount > 1) {
		alert("只可以選擇一位學生檢視資料，謝謝!!");
		return false;	
	}
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Tutor/GraduateInvestigation" method="post" onsubmit="init('系統處理中...')">
<script>generateTableBanner('<div class="gray_15"><B>應 屆 畢 業 生 調 查 表</B></div>');</script>
	<tr>
    	<td align="center">
    		<font color="red" size="+1">輸入應屆畢業生出路調查表之起迄時間：${InvesBegin} ~ ${InvesEnd}</font>
    	</td> 	
    </tr>
    <tr>
    	<td align="center">
    		<font color="red" size="+1">輸入應屆畢業生通訊調查表之起迄時間：${InvesBegin} ~ ${InvesInfoEnd}</font>
    	</td> 	
    </tr>
	<tr>
		<td height="10"></td>
	</tr>
	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
		 		<tr>
		 			<td align="center">
	      				<display:table name="${StudentInfoListT}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        				<c:if test="${empty StudentInfoListT}">
	        					<%@ include file="../include/NoBanner.jsp" %>
	        				</c:if>
	        				<display:column title="" class="center">
	          					<script>generateCheckbox("${row.map.investigation.oid}", "StudentInfo");</script>
	          				</display:column>
	        				<display:column titleKey="Student.No" property="student.studentNo" sortable="true" class="center" />
	        				<display:column titleKey="Student.Name"	property="student.studentName" sortable="true" class="center" />
	        				<display:column title="電郵信箱" property="investigation.email" sortable="true" />
	        				<display:column title="手機號碼" property="investigation.cellPhone" sortable="true" class="center" />
	      				</display:table>
	      			</td>
	      		</tr>
	    	</table>
	    </td>
	</tr>
	<tr height="40">
		<td align="center">
			<table width="99%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td> 
    								<html:link page="/Teacher/Tutor/GraduateInvestigationList.do?t=i" paramId="no" paramName="NO">
    									<img src="images/vcard.png" border="0"> 畢業班學生清冊下載
    								</html:link>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
    <script>
    	generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Details'/>" onclick="return selectCheck();"');
    </script>	      	
</html:form>
</table>