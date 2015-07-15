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
<!-- Begin Content Page Table Header -->
<form action="/CIS/Score/ScoreHist.do" method="post" name="histForm">
<table width="100%" cellpadding="0" cellspacing="0">
<input type="hidden" name="student_name" value="${ScoreStuMap.studentName}">
<input type="hidden" name="student_class" value="${ScoreStuMap.departClass}">
<input type="hidden" name="student_classname" value="${ScoreStuMap.depClassName}">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="scrTitle.ScoreHistMaintain" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <!-- 
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="成績資料維護"/> -&gt; <span class="style2">歷年成績資料維護</span></td>
  	    </tr>
  	  -->
      <tr>
        <td  height="30" colspan="6" align="center" valign="middle">學號：
        <c:if test="${ScoreStuMap.studentNo != ''}">
          <input name="student_no" type="text" size="7" maxlength="10" value="${ScoreStuMap.studentNo}" />
        </c:if>
        <c:if test="${ScoreStuMap.studentNo == ''}">
          <input name="student_no" type="text" size="7" maxlength="10" />
        </c:if>
          <!-- <img src="spacer.gif" alt="spacer" width="20" height="6"/>姓名：
          <input name="student_name" type="text" size="10" maxlength="20" /> -->
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>學年：
          <input name="school_year" type="text" size="3" maxlength="5" value="${ScoreStuMap.schoolYear}" />
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>學期：
          <input name="school_term" type="text" size="2" maxlength="1" value="${ScoreStuMap.schoolTerm}"/>
        </td>
      </tr>
      <!-- 
      <tr>
        <td  height="34" colspan="6" align="center" valign="middle" bgcolor="#CCCCFF">
			<Input type="submit" name="scrHistAction" value="<bean:message key='score.histadd' bundle='SCR'/>" >
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>
		<Input type="submit" name="scrHistAction" value="<bean:message key='score.histqry' bundle='SCR'/>" >
        </tr>
      <tr>
        <td  height="33" colspan="6" align="left" valign="middle">&nbsp;</td>
        </tr>
      -->
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="scrHistAction" value="<bean:message key='score.histadd' bundle='SCR'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="scrHistAction" value="<bean:message key='score.histqry' bundle='SCR'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${ScoreInfoList != null}" >
	    <tr><td height="10"></td></tr>
	    <tr><td height="10"><font class="blue_13">&nbsp;
		    <c:out value="${ScoreStuMap.studentNo}"/>&nbsp;&nbsp;	    	
	    	<c:out value="${ScoreStuMap.studentName}" />&nbsp;&nbsp;
	    	<c:out value="${ScoreStuMap.depClassName}" /></font>
	    </td></tr>
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">  
	      <display:table name="${ScoreInfoList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		<c:if test="${empty ScoreInfoList}">
	     		<%@ include file="../include/NoBanner.jsp" %>
	  		</c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(ScoreInfoList)}, 'ScoreHist');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "ScoreHist");</script></display:column>
 	        <display:column title="學年"		property="schoolYear"		sortable="true" class="center" />
	        <display:column title="學期"		property="schoolTerm"		sortable="true" class="center" />
	        <display:column title="型態"		property="evgrTypeName"		sortable="true" class="center" />
	        <display:column title="科目" 	property="cscodeName" 			sortable="true" class="left" />
	        <display:column title="科目代碼" 	property="cscode" 			sortable="true" class="center" />
	        <display:column title="選別" 	property="optName" 				sortable="true" class="center" />
	        <display:column title="學分" 	property="credit"			sortable="true" class="center" />
	        <display:column title="分數" 	property="score"			sortable="true" class="right" />
	        <display:column title="開課班級" 	property="stDepClassName"	sortable="true" class="center" />
 	      </display:table></td></tr>	      
	      			</table>
	      		</td>
	      	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="scrHistAction" value="<bean:message key='score.histdel' bundle='SCR'/>" onclick="return checkSelectForDelete(\'ScoreHist\');">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="scrHistAction" value="<bean:message key='score.histmodify' bundle='SCR'/>" onclick="return checkSelectForModify(\'ScoreHist\');">');
	</script>
	<!-- Leo Start 學期平均分數區 -->
	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<tr>
	  <td>
	    <table width="100%" cellpadding="0" cellspacing="0" >
          <tr onClick="show('Stavg')" style="cursor:pointer;">
            <td width="10" align="left" nowrap>
              <hr class="myHr"/>
            </td>
            <td width="24" align="center" nowrap>
              <img src="images/folder_explore.gif" id="searchEx" 
                   onMouseOver="showHelpMessage('點擊此處 開啟/關閉 學期平均分數', 'inline', this.id)" 
				   onMouseOut="showHelpMessage('', 'none', this.id)">
            </td>
            <td nowrap>學期平均分數</td>
            <td width="100%" align="left">
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
	    <table width="100%" cellpadding="0" cellspacing="0">
		  <tr style="display:none" id="Stavg">
		    <td align="center">  
	          <display:table name="${StavgList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		    <c:if test="${empty StavgList}">
	     		  <%@ include file="../include/NoBanner.jsp" %>
	  		    </c:if>
 	            <display:column title="學年"		property="school_year"		sortable="true" class="center" />
	            <display:column title="學期"		property="school_term"		sortable="true" class="center" />
	            <display:column title="平均分數"	property="score"   		    sortable="true" class="center" />
	            <display:column title="總學分數" 	property="total_credit" 	sortable="true" class="center" />
 	          </display:table>
 	        </td>
 	      </tr>	      
	    </table>
	  </td>
	</tr>
	<!-- Leo End -->
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
<!-- Leo Start -->
<script>
  function show(ID) 
  {
    var id = ID;
    if(document.getElementById(id).style.display == 'none') 
    {
      document.getElementById(id).style.display = 'inline';      
    } 
    else 
    {
      document.getElementById(id).style.display = 'none';				
    }
  }
</script>
<!-- Leo End -->
