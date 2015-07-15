<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/score/include/scoreinput.js" %>

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
<form action="/CIS/Score/ScoreInput.do" method="post" name="inputForm">
<table width="100%" cellpadding="0" cellspacing="0">
<input name="scoretype" type="hidden" value="${ScoreInputInit.scoretype}"/>

<!-- Begin Content Page Table Header -->
<c:if test="${ScoreInputInit.scoretype=='1'}">
  <script>generateTableBanner('<div class="gray_15"><B><bean:message key="scrTitle.MidScoreInput" bundle="SCR"/></B></div>');</script>	  
</c:if>
<c:if test="${ScoreInputInit.scoretype=='2'}">
    <script>generateTableBanner('<div class="gray_15"><B><bean:message key="scrTitle.FinalScoreInput" bundle="SCR"/></B></div>');</script>
</c:if>
	<tr><td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <!--  
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex">
        <c:out value="成績資料維護"/> -&gt; <span class="style2">
        <c:if test="${ScoreInputInit.scoretype=='1'}">期中成績輸入</c:if>
        <c:if test="${ScoreInputInit.scoretype=='2'}">期末成績輸入</c:if>
        </span></td>
  	    </tr>
  	  -->
      <tr>
        <td height="25" colspan="6" align="left" valign="bottom">
       	<font class="blue_13">請依序選擇校區、學制、科系、班級和科目</font>
       	</td>
      </tr>
      <tr><td>
      	班別：
	  		<c:set var="campusSel" value="${ScoreInputInit.campus}"/>
	  		<c:set var="schoolSel" value="${ScoreInputInit.school}"/>
	  		<c:set var="deptSel"   value="${ScoreInputInit.dept}"/>
	  		<c:set var="classSel"  value="${ScoreInputInit.clazz}"/>
			<%@ include file="/pages/score/include/ClassSelect2.jsp" %>
	  		
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>科目：
          <select name="dtimeoid" >
          <c:if test="${ScoreInputInfo != null }">
          	<c:forEach items="${ScoreInputInfo}" var="cscodes">
          		<c:if test="${ScoreInputInit.dtimeoid==cscodes.dtimeoid}">
          			<option value="${cscodes.dtimeoid}" selected="selected">${cscodes.chiName}</option>
          		</c:if>
          		<c:if test="${ScoreInputInit.dtimeoid!=cscodes.dtimeoid}">
          			<option value="${cscodes.dtimeoid}">${cscodes.chiName}</option>
          		</c:if>
          	</c:forEach>
          </c:if>          
          
          <!-- 
          <c:if test="${ScoreInputInfo != null }">
          	<c:forEach items="${ScoreInputInfo}" var="cscodes">
          		<c:if test="${ScoreInputInit.cscode==cscodes.cscode}">
          			<option name="cscode" value="${cscodes.cscode}" selected>${cscodes.chiName}</option>
          		</c:if>
          		<c:if test="${ScoreInputInit.cscode!=cscodes.cscode}">
          			<option name="cscode" value="${cscodes.cscode}">${cscodes.chiName}</option>
          		</c:if>
          	</c:forEach>
          </c:if>
          -->
          <c:if test="${ScoreInputInfo == null }">
          	<option value=""></option>
          </c:if>
          
          </select>
        </td>
      </tr>
 	<!-- 
      <tr>
      	<td>
      	<c:choose>
      	<c:when test="${ScoreInputInit.yn=='yes'}">
      		<input type="checkbox" name="regsyn" checked="checked" onclick="checkyn();"/>
      	</c:when>
      	<c:when test="${ScoreInputInit.yn=='no'}">
      		<input type="checkbox" name="regsyn" onclick="checkyn();"/>
      	</c:when>
      	<c:otherwise>
      		<input type="checkbox" name="regsyn" onclick="checkyn();"/>
      	</c:otherwise>
      	</c:choose>
      	是否要寫入教師輸入期中期末考試成績檔中(教師未上線輸入考試成績)</td>
      </tr>
		<c:choose>
		<c:when test="${ScoreInputInit.yn=='no'}">
			<input type="hidden" name="yn" value="no" id="yn"/>
		</c:when>
		<c:when test="${ScoreInputInit.yn=='yes'}">
			<input type="hidden" name="yn" value="yes" id="yn"/>
		</c:when>
		<c:otherwise>
			<input type="hidden" name="yn" value="no" id="yn"/>
		</c:otherwise>
		</c:choose>
 		-->
		<input type="hidden" name="yn" value="yes" id="yn"/>
      <tr>
      	<td>
      	</td>
      </tr>
	</table>
		</td>		
	</tr>
	<script>generateTableBanner('<input type="submit" name="method" value="<bean:message key='scrInput' bundle='SCR'/>">&nbsp;&nbsp;'
							  + '<input type="submit" name="method" value="<bean:message key='scrCheck' bundle='SCR'/>">');</script>
	<!-- Test if have Query Result  -->
	<c:if test="${ScoreInEdit != null  or ScoreInputFormMap != null}" >
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#FFFFCC" id="tb1">
      		<!--DWLayoutTable-->
      			<tr>
      			<td colspan="10"><font class="blue_13"><c:out value="${ScoreInputInit.depClassName}"/>&nbsp;&nbsp;
      			<c:out value="${ScoreInputInit.cscodeName}"/>&nbsp;&nbsp;
      			<c:if test="${ScoreInputInit.scoretype == '1'}">
      				期中成績
      			</c:if>
      			<c:if test="${ScoreInputInit.scoretype == '2'}">
      				期末成績
      			</c:if>
				<c:if test="${ScoreInputFormMap != null}">&nbsp;&nbsp;表格重填</c:if></font>
      			</td>
      			</tr>
      			<c:if test="${ScoreInputInit.mode=='Input'}">
      			<tr>
        			<td height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        			<td width="88" align="center" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="66" align="center" valign="middle" bgcolor="#FFFF77">分數</td>
        			<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        			<td width="88" align="center" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="66" align="center" valign="middle" bgcolor="#FFFF77">分數</td>
        			<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        			<td width="88" align="center" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="67" align="center" valign="middle" bgcolor="#FFFF77">分數</td>
        			<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        		</tr>
        		</c:if>
      			<c:if test="${ScoreInputInit.mode=='Check'}">
      			<tr>
        			<td height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        			<td width="88" align="center" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="35" align="center" valign="middle" bgcolor="#FFFF77">分數</td>
        			<td width="66" align="center" valign="middle" bgcolor="#FFCCFF">輸入</td>
        			<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        			<td width="88" align="center" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="35" align="center" valign="middle" bgcolor="#FFFF77">分數</td>
        			<td width="66" align="center" valign="middle" bgcolor="#FFCCFF">輸入</td>
        			<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        			<td width="88" align="center" valign="middle" bgcolor="#CCFFFF">學號</td>
        			<td width="35" align="center" valign="middle" bgcolor="#FFFF77">分數</td>
        			<td width="67" align="center" valign="middle" bgcolor="#FFCCFF">輸入</td>
        			<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        		</tr>
        		</c:if>
		  <!-- <script language="JavaScript">generateScoreInput(scrinput);</script> -->
		  <c:set var="count" scope="page" value="0"/>
		  <c:set var="rcount" scope="page" value="0"/>
		  
	<c:choose>
	<c:when test="${ScoreInputFormMap != null}">
          	<c:set var="stuNos" scope="page" value="${ScoreInputFormMap.studentNo}"/>
          	<c:set var="rcount" scope="page" value="${0}"/>
          <c:forEach items="${ScoreInputFormMap.scrinput}" var="stuscore">
          	<c:set var="count" scope="page" value="${count+1}"/>
          	<c:if test="${count == 1}">
          		<tr><td  height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
          	</c:if>
          	<td width="88" align="left" valign="middle" bgcolor="#CCFFFF">${stuNos[rcount]}
          	<input name="studentNo" type="hidden" value="${stuNos[rcount]}" /></td>
          	<td width="66" valign="middle" bgcolor="#FFFF77">
          	<c:if test="${ScoreInputInit.mode == 'Input'}">
          		<input name="scrinput" type="text" value="${stuscore}" size="5" maxlength="5" />
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
          	<c:set var="rcount" scope="page" value="${rcount+1}"/>
          </c:forEach>
          <c:if test="${count != 3}">
          	<c:choose>
          		<c:when test="${count == 1}">
          			<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        			<td width="66" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        			<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        			<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        			<td width="67" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        			<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        			</tr>
          		</c:when>
          		<c:when test="${count == 2}">
        			<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        			<td width="67" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        			<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        			</tr>
          		</c:when>
          	</c:choose>
          </c:if>
	</c:when>
	<c:when test="${ScoreInEdit != null}">
          <c:forEach items="${ScoreInEdit}" var="stuscore">
          	<c:set var="count" scope="page" value="${count+1}"/>
          	<c:if test="${count == 1}">
          		<tr><td  height="30" valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
          	</c:if>
          	<td width="88" align="left" valign="middle" bgcolor="#CCFFFF">${stuscore.studentNo}
          	<input name="studentNo" type="hidden" value="${stuscore.studentNo}" /></td>
          	
          	<c:if test="${ScoreInputInit.mode == 'Check'}">
          		<c:if test="${ScoreInputInit.scoretype=='1'}">
        			<td width="35" align="left" valign="middle" bgcolor="#FFFF77"><c:out value="${fn:substring(stuscore.score2, 0, fn:indexOf(stuscore.score2,'.'))}"/></td>
          			<td width="66" valign="middle" bgcolor="#FFCCFF">
          			<input name="scrcheck" type="hidden" value="${stuscore.score2}"/>
          		</c:if>
          		<c:if test="${ScoreInputInit.scoretype=='2'}">
        			<td width="35" align="left" valign="middle" bgcolor="#FFFF77"><c:out value="${fn:substring(stuscore.score, 0, fn:indexOf(stuscore.score,'.'))}"/></td>
          			<td width="66" valign="middle" bgcolor="#FFCCFF">
          			<input name="scrcheck" type="hidden" value="${stuscore.score}"/>
          		</c:if>
           			<input name="scrinput" type="text" value="" size="5" maxlength="5"/>
          		</td>
          	</c:if>
          	
          	<c:if test="${ScoreInputInit.mode == 'Input'}">
          	<td width="66" valign="middle" bgcolor="#FFFF77">
          		<c:if test="${ScoreInputInit.scoretype=='1'}">
          			<input name="scrinput" type="text" value="${stuscore.score2}" size="5" maxlength="5" />
          		</c:if>
          		<c:if test="${ScoreInputInit.scoretype=='2'}">
          			<input name="scrinput" type="text" value="${stuscore.score}" size="5" maxlength="5" />
          		</c:if>
          		</td>
           	</td>
          	</c:if>
          	
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
          			<c:if test="${ScoreInputInit.mode == 'Check'}">
          				<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="35" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
          				<td width="66" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        				<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="35" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td width="67" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        				</tr>
          			</c:if>
          			<c:if test="${ScoreInputInit.mode == 'Input'}">
          				<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="66" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td width="2" valign="top"><img src="spacer.gif" width="8" /></td>
        				<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="67" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        				</tr>
        			</c:if>
          		</c:when>
          		<c:when test="${count == 2}">
          			<c:if test="${ScoreInputInit.mode == 'Check'}">
        				<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="25" align="left" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td width="67" valign="middle" bgcolor="#FFCCFF">&nbsp;</td>
        				<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        				</tr>
          			</c:if>
          			<c:if test="${ScoreInputInit.mode == 'Input'}">
        				<td width="88" valign="middle" bgcolor="#CCFFFF">&nbsp;</td>
        				<td width="67" valign="middle" bgcolor="#FFFF77">&nbsp;</td>
        				<td valign="top"><!--DWLayoutEmptyCell-->&nbsp;</td>
        				</tr>
        			</c:if>
          		</c:when>
          	</c:choose>
          </c:if>
	</c:when>
	</c:choose>
	
          </table>
	   </td>
	</tr>
	
    <c:if test="${ScoreInputInit.mode == 'Input'}">
	<script>
	d2int("scrinput");
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK' bundle='SCR'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >');
	</script>
	</c:if>
	
    <c:if test="${ScoreInputInit.mode == 'Check'}">
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="開始檢查" onclick="checkscore();" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >');
	</script>
	</c:if>
</c:if>
</table>
</form>
