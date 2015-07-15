<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function check() {
	var iCount = document.getElementById("studentNo").value;
	if (iCount == '') {
		alert("學號不可為空白,謝謝!!");
		document.getElementById("studentNo").focus();
		return false;
	}	
	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/FEE/ReliefVulnerableAmount" method="post" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>更 新 學 生 註 冊 檔( 減 免 與 弱 勢) 維 護</B></div>');</script>
	<html:hidden property="regOid" value="${regInfo.oid}"/>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;學年學期&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF" colspan="3">
                     	<html:text property="schoolYear" styleId="schoolYear" size="1" disabled="true" value="${regInfo.schoolYear}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                     	<html:text property="schoolTerm" styleId="schoolTerm" size="1" disabled="true" value="${regInfo.schoolTerm}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
                <tr>    
                    <td class="hairlineTdF">&nbsp;&nbsp;姓名&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                    	<input type="text" name="studentName" size="12" disabled value="${regInfo.studentName}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;身分證字號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                    	<html:text property="idno" styleId="idno" size="12" disabled="true" value="${regInfo.idno}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
                <tr>    
                     <td class="hairlineTdF">&nbsp;&nbsp;減免學雜費金額&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                    	<html:text property="reliefTuitionAmount" styleId="reliefTuitionAmount" size="7" maxlength="6" value="${regInfo.reliefTuitionAmount}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;弱勢貸款金額&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="vulnerableAmount" styleId="vulnerableAmount" size="7" maxlength="6" value="${regInfo.vulnerableAmount}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
			</table>
    	</td>
	</tr>
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='fee.update.sure' bundle="FEE" />" onclick="return check()" class="CourseButton">' + 
   		'<INPUT type="submit" name="method" value="<bean:message key='fee.back' bundle="FEE" />" class="CourseButton">');
   	</script>
	
</html:form>
</table>