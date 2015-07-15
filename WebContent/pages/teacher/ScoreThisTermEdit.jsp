<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.div1 {
	font-size: 13px;
	font-weight: normal;
	height: auto;
	width: auto;
	border: 1px solid #FF6600;
	margin: 3px;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>
<script type="text/javascript">
<!--
function d2int(tagName)
{
	var ips = document.getElementsByName(tagName);

  	for(var i=0; i<=ips.length-1;i++)
  	{
  		if(ips[i].value != "")
      		ips[i].value=Math.round(ips[i].value);
  	}

};

//-->
</script>
<!-- <html:form action="/Score/ScoreHist" method="post"> -->
<form action="/CIS/Teacher/ScoreThisTermEdit.do" method="post" name="inputForm">
<input name="scoretype" type="hidden" value="${TchScoreUploadInfo.scoretype}"/>
<input name="yn" type="hidden" value="yes"/>

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('教師資訊系統');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${TchScoreInEdit != null or TchScoreUploadFormMap != null}"  >
	<tr>
		<td>
		<font color=red>
		使用本項功能上傳學期成績,將會忽略之前上傳平時,期中及期末成績所計算出之學期成績,<br>
		輸入欄位中所顯示之成績為目前系統中計算出之學期成績,僅供參考!</font><br><br>
		輸入時請按TAB鍵移動游標到下一欄位, 並請將成績調整為整數<br>
		如果沒有調整將自動四捨五入<br>
		<br>
		</td>
	</tr>
	<tr align=center>
	<td>
		<table width="80%">
			<tr>
			<td>
			<div class="div1"><font color=red>如果上傳成績後沒有出現列印成績的視窗,
			表示您的瀏覽器有開啟阻擋彈跳視窗的功能,
			請解除或暫時允許彈跳視窗功能,謝謝!</font></div>
			<br>
			</td>
			</tr>
		</table>
	<td>
	</td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#FFFFCC" id="tb1">
      		<!--DWLayoutTable-->
      			<tr>
      			<td colspan="10">班別：<c:out value="${TchScoreUploadInfo.depClassName}"/>&nbsp;
      			<c:out value="${TchScoreUploadInfo.departClass}"/>&nbsp;&nbsp;
      			科目：<c:out value="${TchScoreUploadInfo.cscodeName}"/>&nbsp;
      			<c:out value="${TchScoreUploadInfo.cscode}"/>&nbsp;&nbsp;
      				學期成績
				<c:if test="${TchScoreUploadFormMap != null}">&nbsp;&nbsp;表格重填</c:if>
      			</td>
        		</tr>
      			<tr>
        			<td height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        			<td width="68" align="left" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="55" align="left" valign="middle" bgcolor="#FFFF77">姓名</td>
        			<td width="66" valign="middle" bgcolor="#FFCCFF">分數</td>
        			<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        			<td width="68" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="55" align="left" valign="middle" bgcolor="#FFFF77">姓名</td>
        			<td width="66" valign="middle" bgcolor="#FFCCFF">分數</td>
        			<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        			<td width="68" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="55" align="left" valign="middle" bgcolor="#FFFF77">姓名</td>
        			<td width="67" valign="middle" bgcolor="#FFCCFF">分數</td>
        			<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        		</tr>
		  <!-- <script language="JavaScript">generateScoreInput(scrinput);</script> -->
		  <c:set var="count" scope="page" value="0"/>
		  <c:set var="rcount" scope="page" value="0"/>
		  
	<c:choose>
	<c:when test="${TchScoreUploadFormMap != null}">
          	<c:set var="frmscore" scope="page" value="${TchScoreUploadFormMap.scr23}"/>
          	<c:set var="rcount" scope="page" value="${0}"/>
          <c:forEach items="${TchScoreInEdit}" var="stuscore">
          	<c:set var="count" scope="page" value="${count+1}"/>
          	<c:if test="${count == 1}">
          		<tr><td  height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
          	</c:if>
          	<td width="68" align="left" valign="middle" bgcolor="#CCFFFF">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
        		<td width="55" align="left" valign="middle" bgcolor="#FFFF77">${stuscore.studentName}</td>
          		<td width="66" valign="middle" bgcolor="#FFCCFF">
          			<input name="scr23" type="text" size="5" maxlength="5" value="${frmscore[rcount]}"/>
         		</td>
          	<c:if test="${count != 3}">
          		<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
          	</c:if>
          	<c:if test="${count == 3}">
        		<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        		</tr>
        		<c:set var="count" scope="page" value="0"/>
          	</c:if>
          	<c:set var="rcount" scope="page" value="${rcount+1}"/>
          </c:forEach>
          <c:if test="${count != 3}">
          	<c:choose>
          		<c:when test="${count == 1}">
          				<td width="68" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="55" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
          				<td width="66" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        				<td width="68" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="55" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td width="67" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        				</tr>
          		</c:when>
          		<c:when test="${count == 2}">
        				<td width="68" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="55" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td width="67" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        				</tr>
          		</c:when>
          	</c:choose>
          </c:if>
	</c:when>
	<c:when test="${TchScoreInEdit != null}">
          <c:forEach items="${TchScoreInEdit}" var="stuscore">
          	<c:set var="count" scope="page" value="${count+1}"/>
          	<c:if test="${count == 1}">
          		<tr><td  height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
          	</c:if>
          	<td width="68" align="left" valign="middle" bgcolor="#CCFFFF">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
        		<td width="55" align="left" valign="middle" bgcolor="#FFFF77">${stuscore.studentName}</td>
          		<td width="66" valign="middle" bgcolor="#FFCCFF">
          		<c:if test="${TchScoreUploadInfo.scoreNotUpload == '1'}">
          			<!-- input name="scr23" type="text" size="5" maxlength="5" value="${stuscore.score23}"/-->
          			<input name="scr23" type="text" size="5" maxlength="5" value=""/>
          		</c:if>
          		<c:if test="${TchScoreUploadInfo.scoreNotUpload != '1'}">
          			<!-- input name="scr23" type="text" size="5" maxlength="5" value="${stuscore.score23}"/-->
          			<input name="scr23" type="text" size="5" maxlength="5" value="${stuscore.score23}"/>
          		</c:if>
         		</td>
         	          	
          	<c:if test="${count != 3}">
          		<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
          	</c:if>
          	<c:if test="${count == 3}">
        		<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        		</tr>
        		<c:set var="count" scope="page" value="0"/>
          	</c:if>
          </c:forEach>
          <c:if test="${count != 3}">
          	<c:choose>
          		<c:when test="${count == 1}">
          				<td width="68" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="55" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
          				<td width="66" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        				<td width="68" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="55" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td width="67" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        				</tr>
          		</c:when>
          		<c:when test="${count == 2}">
        				<td width="68" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="55" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td width="67" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        				</tr>
          		</c:when>
          	</c:choose>
          </c:if>
	</c:when>
	</c:choose>
	
          </table>
	   </td>
	</tr>
	
	<script>
	d2int("scr23");
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='StartUpload' bundle='SCR'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >');
	</script>
	
 	
</c:if>
	
<!-- Begin Content Page Table Footer -->
				
</table>
<!-- End Content Page Table Footer -->
</form>
<script>history.go(1);</script>
<!-- </html:form> -->