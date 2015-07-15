<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>
<script type="text/javascript">
	var basePath='<%=basePath%>';
</script>

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

<!-- for auto input select item use -->
	<div id="popup" class="ds_box" style="position:absolute;">
		 <table id="name_table" bgcolor="#FFFAFA" border="1" cellspacing="2" cellpadding="2">
		 	  <tbody id="name_body"></tbody>
		 </table>
	</div> 


<form action="/CIS/Score/ScoreHistEdit.do" method="post" name="histForm">

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<input type="hidden" name="mode" value="${ScoreHistEdit.mode}">
<input type="hidden" name="student_name" value="${ScoreHistEdit.student_name}">
<input type="hidden" name="student_class" value="${ScoreHistEdit.student_class}">
<input type="hidden" name="student_classname" value="${ScoreHistEdit.student_classname}">
<input type="hidden" name="cscode_name" value="${ScoreHistEdit.cscode_name}">
<input type="hidden" name="stdeptclass_name" value="${ScoreHistEdit.stdepclass_name}">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="scrTitle.ScoreHistCreate" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td align="left">
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border">
      <!--
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="成績資料維護"/> -&gt;歷年成績資料維護 -&gt; 
        <span class="style2">
        <c:if test="${ScoreHistEdit.mode=='Create'}">
        	<c:out value="新增"/>
        </c:if>
        <c:if test="${ScoreHistEdit.mode=='Modify'}">
        	<c:out value="修改"/>
        </c:if>
        </span></td>
  	    </tr>
  	  -->
      <tr>
        <td align="left" valign="middle">學號：
          <input name="student_no" value="${ScoreHistEdit.student_no}" type="text" size="7" maxlength="10" <c:out value="${readOnly}" />
          		onchange="if(this.value!='') document.forms[0].submit();" >
          			<c:if test="${ScoreHistEdit.studentfound == true}">
				 	 &nbsp;&nbsp;
				 	 <font class="blue_13"><c:out value="${ScoreHistEdit.student_name}" />&nbsp;
				 	 					   <c:out value="${ScoreHistEdit.student_classname}"/></font>
				 	</c:if>
				 	<c:if test="${ScoreHistEdit.studentfound == false}">
				 	 <font class="red_13"><c:out value="* 查無此人" /></font>
				 	</c:if>
		  </td></tr>
	  <tr>
          <!-- <img src="spacer.gif" alt="spacer" width="48" height="6"/> -->
		<td align="left" valign="middle">學年：
          <input name="school_year" type="text" size="3" maxlength="5" value="${ScoreHistEdit.school_year}">&nbsp;&nbsp;
          	學期：
          <input name="school_term" type="text" size="1" maxlength="1" value="${ScoreHistEdit.school_term}"></td>
      </tr>
      <tr>
        <td align="left" valign="middle">科目：
        	<input name="cscode" value="${ScoreHistEdit.cscode}" id="cscode" type="text" size="5" maxlength="5" autocomplete="off" 
								onMouseOver="showHelpMessage('代碼!', 'inline', this.id)" 
				   				onMouseOut="showHelpMessage('', 'none', this.id)"
				   				onClick = "this.value=''; document.getElementById('cscodeName').value='';";
							 	onkeyup="if(this.value.length>0)getCscodeName(this, true);"/>
			<input type="text" name="cscodeName" id="cscodeName" size="16" autocomplete="off" 
								onMouseOver="showHelpMessage('名稱!', 'inline', this.id)" 
				   				onMouseOut="showHelpMessage('', 'none', this.id)"
				   				onClick = "this.value=''; document.getElementById('cscode').value='';";
							 	onkeyup="if(this.value.length>0)getCscode(this, true);"/>
		 	
          			<c:if test="${ScoreHistEdit.cscodefound == true}">
				 	 &nbsp;&nbsp;
				 	 <font class="blue_13"><c:out value="${ScoreHistEdit.cscode_name}" />&nbsp;
				 	</c:if>
				 	<c:if test="${ScoreHistEdit.cscodefound == false}">
				 	 <font class="red_13"><c:out value="* 無此科目" /></font>
				 	</c:if></td>
		<td align="left" valign="middle">
			<select name="evgr_type">
	          <c:forEach items="${CourseType}" var="csrtype">
	          	<option value='${csrtype.sequence}'
	          		<c:choose>
	          			<c:when test="${ScoreHistEdit.evgr_type == csrtype.sequence}"> selected</c:when>
	          			<c:when test="${ScoreInEdit.evgrType == csrtype.sequence}"> selected</c:when>
	          		</c:choose>
	          	 >${csrtype.name}
	          	</option>
	          </c:forEach>
        	</select></td>
        <td align="left" valign="middle">
	        <select name="opt">
	          <c:forEach items="${CourseOpt}" var="csropt">
	          	<option value='${csropt.sequence}'
	          		<c:choose>
	          			<c:when test="${ScoreHistEdit.opt == csropt.sequence}"> selected</c:when>
	          			<c:when test="${ScoreInEdit.opt == csropt.sequence}"> selected</c:when>
	          		</c:choose>
	          	 >${csropt.name}
	          	</option>
	          </c:forEach>
	        </select></td>
	  </tr>
	  <tr>
        <td colspan="3" align="left" valign="middle">學分：
        	<input name="credit" type="text" size="1" maxlength="3" value="${ScoreHistEdit.credit}">&nbsp;&nbsp;
        	成績：
        	<input name="score" type="text" size="3" maxlength="6" value="${ScoreHistEdit.score}"></td>
      </tr>
      <tr>
        <td colspan="3" align="left" valign="middle">開課班級：
        	<input name="stdepart_class" value="${ScoreHistEdit.stdepart_class }"type="text" size="6" maxlength="8" <c:out value="${readOnly}" />
            	   onchange="if(this.value!='') document.forms[0].submit();"/>
          			<c:if test="${ScoreHistEdit.classfound == true}">
				 	 &nbsp;&nbsp;
				 	 <font class="blue_13"><c:out value="${ScoreHistEdit.stdeptclass_name}" />&nbsp;
				 	</c:if>
				 	<c:if test="${ScoreHistEdit.classfound == false}">
				 	 <font class="red_13"><c:out value="* 無此班級" /></font>
				 	</c:if>
        </td>
      </tr>
	</table></td></tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="scrHistEditAction" value="確認" >&nbsp;&nbsp;'+
						'<INPUT type="submit" name="scrHistEditAction" value="取消" >');
	</script>
</table>
</form>
<script type="text/javascript" src="<%=basePath%>pages/score/include/getData.js"></script>

