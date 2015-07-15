<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
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
<form action="/CIS/Teacher/ScoreMidTermEdit.do" method="post" name="inputForm">
<input name="scoretype" type="hidden" value="${TchScoreMidInfo.scoretype}"/>
<input name="yn" type="hidden" value="yes"/>

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('教師資訊系統');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${TchScoreMidInEdit != null or TchScoreInputFormMap != null}"  >
	<tr>
		<td>
		輸入時請按TAB鍵移動游標到下一欄位, 並請將成績調整為整數<br>
		如果沒有調整將自動四捨五入<br>
		<br>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#FFFFCC" id="tb1">
      		<!--DWLayoutTable-->
      			<tr>
      			<td colspan="10">班別：<c:out value="${TchScoreMidInfo.depClassName}"/>&nbsp;&nbsp;
      			科目：<c:out value="${TchScoreMidInfo.cscodeName}"/>&nbsp;&nbsp;
      			<c:if test="${TchScoreMidInfo.scoretype == '1'}">
      				期中成績
      			</c:if>
      			<c:if test="${TchScoreMidInfo.scoretype == '2'}">
      				期末成績
      			</c:if>
				<c:if test="${ScoreInputFormMap != null}">&nbsp;&nbsp;表格重填</c:if>
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
	<c:when test="${TchScoreInputFormMap != null}">
          	<c:set var="frmscore" scope="page" value="${TchScoreInputFormMap.scrinput}"/>
          	<c:set var="rcount" scope="page" value="${0}"/>
          <c:forEach items="${TchScoreMidInEdit}" var="stuscore">
          	<c:set var="count" scope="page" value="${count+1}"/>
          	<c:if test="${count == 1}">
          		<tr><td  height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
          	</c:if>
          	<td width="68" align="left" valign="middle" bgcolor="#CCFFFF">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
        		<td width="55" align="left" valign="middle" bgcolor="#FFFF77">${stuscore.studentName}</td>
          		<td width="66" valign="middle" bgcolor="#FFCCFF">
          			<input name="scrinput" type="text" size="5" maxlength="5" value="${frmscore[rcount]}"/>
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
	<c:when test="${TchScoreMidInEdit != null}">
          <c:forEach items="${TchScoreMidInEdit}" var="stuscore">
          	<c:set var="count" scope="page" value="${count+1}"/>
          	<c:if test="${count == 1}">
          		<tr><td  height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
          	</c:if>
          	<td width="68" align="left" valign="middle" bgcolor="#CCFFFF">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
        		<td width="55" align="left" valign="middle" bgcolor="#FFFF77">${stuscore.studentName}</td>
          		<td width="66" valign="middle" bgcolor="#FFCCFF">
          		<c:if test="${TchScoreMidInfo.scoretype=='1'}">
          			<input name="scrinput" type="text" size="5" maxlength="5" value="${stuscore.score2}"/>
          		</c:if>
          		<c:if test="${TchScoreMidInfo.scoretype=='2'}">
          			<input name="scrinput" type="text" size="5" maxlength="5" value="${stuscore.score3}"/>
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
	d2int("scrinput");
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